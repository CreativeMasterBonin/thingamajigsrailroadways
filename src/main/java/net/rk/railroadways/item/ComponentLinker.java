package net.rk.railroadways.item;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
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
import net.rk.railroadways.entity.blockentity.custom.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ComponentLinker extends Item{
    public ComponentLinker(Properties properties) {
        super(properties.fireResistant().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.has(Thingamajigsrailroadways.SELECTED_POSITION)){
            tooltipComponents.add(Component.translatable("item.crossing_component_linker.linked_to_pos",stack.get(Thingamajigsrailroadways.SELECTED_POSITION).toShortString()));
        }
        tooltipComponents.add(Component.translatable("item.crossing_component_linker.desc").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("item.crossing_component_linker.additional_info").withStyle(ChatFormatting.GOLD));
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

            if(linker.has(Thingamajigsrailroadways.SELECTED_POSITION)){
                if(!level.isLoaded(linker.get(Thingamajigsrailroadways.SELECTED_POSITION))){
                    player.displayClientMessage(Component.translatable("item.crossing_component_linker.too_far"),true);
                    return InteractionResult.CONSUME;
                }
                else{ // due to NBT restraints, we do not want to softlock a world with too much data, so hard limit
                    CrossingComponentControllerBE be = (CrossingComponentControllerBE)level.getBlockEntity(linker.get(Thingamajigsrailroadways.SELECTED_POSITION));
                    if(player.isSecondaryUseActive()){
                        if(be == null){
                            linker.remove(Thingamajigsrailroadways.SELECTED_POSITION);
                            player.playSound(SoundEvents.HORSE_ANGRY,0.75f,0.5f);
                            return InteractionResult.FAIL;
                        }
                        if(be.pairedPositions.size() >= CrossingComponentControllerBE.hardLimitPairs){
                            player.displayClientMessage(Component.translatable("item.crossing_component_linker.max_amount_components_reached"),true);
                            return InteractionResult.CONSUME;
                        }
                    }
                    else{
                        if(be instanceof CrossingComponentControllerBE controller){
                            player.displayClientMessage(Component.translatable("item.crossing_component_linker.controller_linked_positions"),true);
                            // the controller pos
                            player.sendSystemMessage(Component.literal(controller.getBlockPos().toString()
                                    .replaceAll("BlockPos","\uD83C\uDF9A\uD83C\uDF9B") // controls icons
                                    .replace('[',' ').replace(']',' ')
                                    .replace('{',' ').replace('}',' ')
                            ));
                            // the connected pos
                            player.sendSystemMessage(Component.literal(controller.pairedPositions.stream().collect(Collectors.toSet()).toString()
                                    .replace('[',' ').replace(']',' ')
                                    .replace('{',' ').replace('}',' ')
                                    .replaceAll("BlockPos","\uD83D\uDCE1 \uD83D\uDCE4") // satellite and upload-ish icons
                                    .replace(',','\n')
                            ));
                        }
                    }
                }
            }

            // todo: compress the functionality of the linker if possible

            if(blockEntity != null){
                // remove (if any) an existing controller and link a new one to the linker item, allowing compatible components to pair to the linked controller
                if(player.isSecondaryUseActive()){
                    if(blockEntity instanceof CrossingComponentControllerBE controller){
                        if(linker.has(Thingamajigsrailroadways.SELECTED_POSITION)){
                            linker.remove(Thingamajigsrailroadways.SELECTED_POSITION);
                        }
                        DataComponentMap map = DataComponentMap.builder().set(Thingamajigsrailroadways.SELECTED_POSITION,controller.getBlockPos()).build();
                        linker.applyComponents(map);
                        player.displayClientMessage(Component.translatable("item.crossing_component_linker.successful_link",controller.getBlockPos().toShortString()),true);
                        player.playSound(SoundEvents.NOTE_BLOCK_BANJO.value(),0.7f,1.0f);
                        return InteractionResult.CONSUME;
                    }
                    else{ // link block entities to the controller linked to the linker item
                        if(linker.has(Thingamajigsrailroadways.SELECTED_POSITION)){
                            CrossingComponentControllerBE be = (CrossingComponentControllerBE)level.getBlockEntity(Objects.requireNonNull(linker.get(Thingamajigsrailroadways.SELECTED_POSITION.value())));
                            if(be != null){
                                // make sure block entity is compatible before moving forward
                                switch (blockEntity) {
                                    case RailroadCrossingArmWithLights lightedGate -> {
                                        if (!lightedGate.linkedToController) {
                                            be.pairedPositions.add(blockPos);
                                            lightedGate.pairToLinkedPos(linker.getComponents().get(Thingamajigsrailroadways.SELECTED_POSITION.asOptional().get()));
                                            player.playSound(SoundEvents.NOTE_BLOCK_BELL.value(),0.7f,1.0f);
                                            // pass the lightedGate.getBlockPos() then be.getBlockPos()
                                            player.displayClientMessage(Component.translatable("item.crossing_component_linker.successful_pair", lightedGate.getBlockPos().toShortString(), be.getBlockPos().toShortString()), true);
                                        } else {
                                            lightedGate.unpair();
                                            be.removePosition(lightedGate.getBlockPos(), false);
                                            player.displayClientMessage(Component.translatable("item.crossing_component_linker.successful_unpair", lightedGate.getBlockPos().toShortString(), be.getBlockPos().toShortString()), true);
                                        }
                                        return InteractionResult.CONSUME;
                                    }
                                    case RailroadCrossingBE gate -> {
                                        if (!gate.linkedToController) {
                                            be.pairedPositions.add(blockPos);
                                            gate.pairToLinkedPos(linker.getComponents().get(Thingamajigsrailroadways.SELECTED_POSITION.asOptional().get()));
                                            player.playSound(SoundEvents.NOTE_BLOCK_BELL.value(),0.7f,1.0f);
                                            player.displayClientMessage(Component.translatable("item.crossing_component_linker.successful_pair", gate.getBlockPos().toShortString(), be.getBlockPos().toShortString()), true);
                                        } else {
                                            gate.unpair();
                                            be.removePosition(gate.getBlockPos(), false);
                                            player.displayClientMessage(Component.translatable("item.crossing_component_linker.successful_unpair", gate.getBlockPos().toShortString(), be.getBlockPos().toShortString()), true);
                                        }
                                        return InteractionResult.CONSUME;
                                    }
                                    case BritRailwayLightsBE britLights -> {
                                        if (!britLights.linkedToController) {
                                            be.pairedPositions.add(blockPos);
                                            britLights.pairToLinkedPos(linker.getComponents().get(Thingamajigsrailroadways.SELECTED_POSITION.asOptional().get()));
                                            player.playSound(SoundEvents.NOTE_BLOCK_BELL.value(),0.7f,1.0f);
                                            player.displayClientMessage(Component.translatable("item.crossing_component_linker.successful_pair", britLights.getBlockPos().toShortString(), be.getBlockPos().toShortString()), true);
                                        } else {
                                            britLights.unpair();
                                            be.removePosition(britLights.getBlockPos(), false);
                                            player.displayClientMessage(Component.translatable("item.crossing_component_linker.successful_unpair", britLights.getBlockPos().toShortString(), be.getBlockPos().toShortString()), true);
                                        }
                                        return InteractionResult.CONSUME;
                                    }
                                    case EnhancedDirectionalCrossingLightBE enhancedDirectionalLights -> {
                                        if (!enhancedDirectionalLights.linkedToController) {
                                            be.pairedPositions.add(blockPos);
                                            enhancedDirectionalLights.pairToLinkedPos(linker.getComponents().get(Thingamajigsrailroadways.SELECTED_POSITION.asOptional().get()));
                                            player.playSound(SoundEvents.NOTE_BLOCK_BELL.value(),0.7f,1.0f);
                                            player.displayClientMessage(Component.translatable("item.crossing_component_linker.successful_pair", enhancedDirectionalLights.getBlockPos().toShortString(), be.getBlockPos().toShortString()), true);
                                        } else {
                                            enhancedDirectionalLights.unpair();
                                            be.removePosition(enhancedDirectionalLights.getBlockPos(), false);
                                            player.displayClientMessage(Component.translatable("item.crossing_component_linker.successful_unpair", enhancedDirectionalLights.getBlockPos().toShortString(), be.getBlockPos().toShortString()), true);
                                        }
                                        return InteractionResult.CONSUME;
                                    }
                                    default ->{
                                        player.displayClientMessage(Component.translatable("item.crossing_component_linker.unsuccessful_pair_invalid"), true);
                                        player.playSound(SoundEvents.NOTE_BLOCK_COW_BELL.value(),0.7f,1.0f);
                                        return InteractionResult.CONSUME;
                                    }
                                }
                            }
                            else{
                                player.displayClientMessage(Component.translatable("item.crossing_component_linker.unsuccessful_pair_linked_controller_invalid"),true);
                            }
                        }
                        else{
                            player.displayClientMessage(Component.translatable("item.crossing_component_linker.unsuccessful_pair_need_controller"),true);
                        }
                    }
                    return InteractionResult.PASS;
                }
            }
            else{
                // shifting and no block entity clicked on, unpair the controller to prevent accidental assignment of components to controller
                if(player.isSecondaryUseActive()){
                    if(linker.has(Thingamajigsrailroadways.SELECTED_POSITION)){
                        linker.remove(Thingamajigsrailroadways.SELECTED_POSITION);
                        player.displayClientMessage(Component.translatable("item.crossing_component_linker.unpaired_linker"),true);
                        player.playSound(SoundEvents.NOTE_BLOCK_BANJO.value(),0.5f,0.75f);
                        return InteractionResult.CONSUME;
                    }
                }
                else{ // insta-break all vertical railroad crossing components for ease of use
                    if(level.getBlockState(blockPos).is(TRRTag.VERTICAL_REDSTONE_COMPATIBLE)){
                        level.destroyBlock(blockPos,true,player);
                        return InteractionResult.SUCCESS;
                    }
                    else{
                        // if clicking on any other block than a vertical redstone one, show the connections to the linked controller
                        if(linker.has(Thingamajigsrailroadways.SELECTED_POSITION)){
                            if(level.isLoaded(linker.get(Thingamajigsrailroadways.SELECTED_POSITION))){
                                BlockEntity be = level.getBlockEntity(linker.get(Thingamajigsrailroadways.SELECTED_POSITION));
                                if(be instanceof CrossingComponentControllerBE controller){
                                    player.displayClientMessage(Component.translatable("item.crossing_component_linker.controller_linked_positions"),true);
                                    // the controller pos
                                    player.sendSystemMessage(Component.literal(controller.getBlockPos().toString()
                                            .replaceAll("BlockPos","\uD83C\uDF9A\uD83C\uDF9B") // controls icons
                                            .replace('[',' ').replace(']',' ')
                                            .replace('{',' ').replace('}',' ')
                                    ));
                                    // the connected pos
                                    player.sendSystemMessage(Component.literal(controller.pairedPositions.stream().collect(Collectors.toSet()).toString()
                                            .replace('[',' ').replace(']',' ')
                                            .replace('{',' ').replace('}',' ')
                                            .replaceAll("BlockPos","\uD83D\uDCE1 \uD83D\uDCE4") // satellite and upload-ish icons
                                            .replace(',','\n')
                                    ));
                                }
                            }
                        }
                    }
                }
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }
}
