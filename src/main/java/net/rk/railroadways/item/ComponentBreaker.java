package net.rk.railroadways.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.rk.railroadways.entity.blockentity.custom.*;

import java.util.ArrayList;
import java.util.List;

public class ComponentBreaker extends Item {
    public ComponentBreaker(Properties properties) {
        super(properties.fireResistant().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.crossing_component_breaker.desc").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(!context.getLevel().isClientSide()){
            BlockEntity be = context.getLevel().getBlockEntity(context.getClickedPos());
            Level level = context.getLevel();
            Player player = context.getPlayer();
            BlockPos pos = context.getClickedPos();

            if(be instanceof RailroadCrossingArmWithLights lightedArm){
                lightedArm.ticks = 0;
                lightedArm.unpair();
                level.playSound(player,pos,SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.BLOCKS,0.9f,1.1f);
            }
            else if(be instanceof RailroadCrossingBE gate){
                gate.ticks = 0;
                gate.unpair();
                level.playSound(player,pos,SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.BLOCKS,0.9f,1.1f);
            }
            else if(be instanceof BritRailwayLightsBE britLights){
                britLights.ticks = 0;
                britLights.unpair();
                level.playSound(player,pos,SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.BLOCKS,0.9f,1.1f);
            }
            else if(be instanceof EnhancedDirectionalCrossingLightBE enhancedDirectionalLights){
                enhancedDirectionalLights.ticks = 0;
                enhancedDirectionalLights.unpair();
                level.playSound(player,pos,SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.BLOCKS,0.9f,1.1f);
            }
            else if(be instanceof CrossingComponentControllerBE controller){
                controller.pairedPositions.clear();
                controller.updateBlock();
                level.playSound(player,pos,SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.BLOCKS,0.9f,1.1f);
            }

            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }
}
