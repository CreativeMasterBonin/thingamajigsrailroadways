package net.rk.railroadways.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DetectorRailBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Predicate;

public class PurpleDetectorRailBlock extends DetectorRailBlock{
    public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public PurpleDetectorRailBlock(BlockBehaviour.Properties p) {
        super(p);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED,false)
                .setValue(SHAPE, RailShape.NORTH_SOUTH).setValue(WATERLOGGED,false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(SHAPE, POWERED, WATERLOGGED);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigsrailroadways.purple_detector_rail.desc")
                .withStyle(ChatFormatting.YELLOW));
    }

    // from mojang edited (reduced code)
    public <T extends AbstractMinecart> List<T> getMinecartOfType(Level lvl, BlockPos bp, Class<T> object, Predicate<Entity> predicateEntity) {
        return lvl.getEntitiesOfClass(object,
                new AABB((double)bp.getX() + 0.2D, bp.getY(), (double)bp.getZ() + 0.2D, (double)(bp.getX() + 1) - 0.2D, (double)(bp.getY() + 1) - 0.2D, (double)(bp.getZ() + 1) - 0.2D),
                predicateEntity);
    }

    @Override
    public void tick(BlockState bs, ServerLevel slvl, BlockPos bp, RandomSource rs) {
        if(bs.getValue(POWERED)){
            pressed(slvl,bp,bs);
        }
    }

    // from mojang; edited for extra functions
    public void pressed(Level lvl, BlockPos bp, BlockState bs) {
        if (this.canSurvive(bs, lvl, bp)) {
            boolean poweredState = bs.getValue(POWERED);
            boolean minecartExistingList = false;
            List<AbstractMinecart> list = getMinecartOfType(lvl,bp,AbstractMinecart.class,(predicate) -> true);
            if (!list.isEmpty()) {
                minecartExistingList = true;
            }

            if (minecartExistingList && !poweredState) {
                BlockState blockstate = bs.setValue(POWERED,false);
                lvl.setBlock(bp, blockstate, 3);
                this.updatePowerToConnected(lvl, bp, blockstate, true);
                lvl.updateNeighborsAt(bp, this);
                lvl.updateNeighborsAt(bp.below(), this);
                lvl.setBlocksDirty(bp, bs, blockstate);
                lvl.playSound(null,bp, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundSource.BLOCKS,0.5f,1.0f);
            }

            if (!minecartExistingList && poweredState) {
                BlockState blockstate1 = bs.setValue(POWERED,false);
                lvl.setBlock(bp, blockstate1, 3);
                this.updatePowerToConnected(lvl, bp, blockstate1, false);
                lvl.updateNeighborsAt(bp, this);
                lvl.updateNeighborsAt(bp.below(), this);
                lvl.setBlocksDirty(bp, bs, blockstate1);
                lvl.playSound(null,bp, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS,0.5f,1.0f);
            }

            if (minecartExistingList) {
                lvl.scheduleTick(bp, this, 10);
            }

            lvl.updateNeighbourForOutputSignal(bp, this);
        }
    }
}
