package net.rk.railroadways.item;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.block.custom.CrossingComponentController;
import net.rk.railroadways.datagen.TRRTag;
import net.rk.railroadways.entity.blockentity.custom.CrossingComponentControllerBE;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingArmWithLights;

import java.util.List;
import java.util.Objects;

public class ComponentLinker extends Item{
    public ComponentLinker(Properties properties) {
        super(properties.fireResistant().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.has(Thingamajigsrailroadways.SELECTED_POSITION)){
            tooltipComponents.add(Component.literal("Linked to: " + stack.get(Thingamajigsrailroadways.SELECTED_POSITION).toShortString()));
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(!context.getLevel().isClientSide){
            BlockPos blockPos = context.getClickedPos();
            Level level = context.getLevel();
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            ItemStack linker = context.getItemInHand();
            Player player = context.getPlayer();

            if(player == null){
                LogUtils.getLogger().error("Player was null somehow when trying to use ComponentLinker!");
                return InteractionResult.FAIL;
            }

            if(blockEntity != null){
                if(player.isSecondaryUseActive()){
                    if(blockEntity instanceof CrossingComponentControllerBE controller){
                        if(linker.has(Thingamajigsrailroadways.SELECTED_POSITION)){
                            linker.remove(Thingamajigsrailroadways.SELECTED_POSITION);
                        }
                        DataComponentMap map = DataComponentMap.builder().set(Thingamajigsrailroadways.SELECTED_POSITION,controller.getBlockPos()).build();
                        linker.applyComponents(map);
                        player.displayClientMessage(Component.literal("Added controller to linker: " + controller.getBlockPos().toShortString()),true);
                        player.playSound(SoundEvents.NOTE_BLOCK_BANJO.value(),0.5f,1.0f);
                    }
                    else{
                        if(linker.has(Thingamajigsrailroadways.SELECTED_POSITION)){
                            CrossingComponentControllerBE be = (CrossingComponentControllerBE)level.getBlockEntity(Objects.requireNonNull(linker.get(Thingamajigsrailroadways.SELECTED_POSITION.value())));
                            if(be != null){
                                be.pairedPositions.add(blockPos);
                                if(blockEntity instanceof RailroadCrossingArmWithLights lightedGate){
                                    if(!lightedGate.linkedToController){
                                        lightedGate.linkedToController = true;
                                        lightedGate.updateBlock();
                                        player.displayClientMessage(Component.literal("Paired " + lightedGate.getBlockPos() + " to " + be.getBlockPos()),true);
                                    }
                                    else{
                                        lightedGate.linkedToController = false;
                                        lightedGate.updateBlock();
                                        be.removePosition(lightedGate.getBlockPos());
                                        player.displayClientMessage(Component.literal("Unpaired " + lightedGate.getBlockPos() + " to " + be.getBlockPos()),true);
                                    }
                                }
                                player.playSound(SoundEvents.NOTE_BLOCK_COW_BELL.value(),0.5f,1.0f);
                            }
                            else{
                                player.displayClientMessage(Component.literal("Old controller is missing"),true);
                            }
                        }
                        else{
                            player.displayClientMessage(Component.literal("Link a controller first"),true);
                        }
                    }
                    return InteractionResult.CONSUME;
                }
            }
            else{
                if(player.isSecondaryUseActive()){
                    if(linker.has(Thingamajigsrailroadways.SELECTED_POSITION)){
                        linker.remove(Thingamajigsrailroadways.SELECTED_POSITION);
                        player.displayClientMessage(Component.literal("Removed last linked controller data"),true);
                        player.playSound(SoundEvents.NOTE_BLOCK_BANJO.value(),0.5f,0.75f);
                    }
                }
                else{
                    if(level.getBlockState(blockPos).is(TRRTag.VERTICAL_REDSTONE_COMPATIBLE)){
                        level.destroyBlock(blockPos,true,player);
                    }
                }
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }
}
