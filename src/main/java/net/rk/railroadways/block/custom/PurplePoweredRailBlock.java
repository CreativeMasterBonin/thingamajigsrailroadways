package net.rk.railroadways.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.RailShape;

import java.util.List;

public class PurplePoweredRailBlock extends PoweredRailBlock{
    public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private final boolean isActivator;

    public PurplePoweredRailBlock(BlockBehaviour.Properties p_55218_) {
        this(p_55218_, false);
    }

    protected PurplePoweredRailBlock(Properties builder, boolean isPoweredRail) {
        super(builder, true);
        this.registerDefaultState(this.stateDefinition.any().setValue(SHAPE, RailShape.NORTH_SOUTH)
                .setValue(POWERED,false)
                .setValue(WATERLOGGED,false));
        this.isActivator = false;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(SHAPE, POWERED, WATERLOGGED);
    }

    public boolean isActivatorRail() {
        return false;
    }

    @Override
    public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
        boolean p = state.getValue(POWERED);
        if (!p){
            return 0.9F;
        }
        else {
            return 1.1F;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigsrailroadways.purple_powered_rail.desc")
                .withStyle(ChatFormatting.LIGHT_PURPLE));
    }
}
