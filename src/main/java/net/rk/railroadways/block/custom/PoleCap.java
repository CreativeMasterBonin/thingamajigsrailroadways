package net.rk.railroadways.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.thingamajigs.block.custom.VerticalPoleRedstone;
import net.rk.thingamajigs.datagen.TTag;

public class PoleCap extends Block{
    public static VoxelShape ALL = Shapes.join(
            Block.box(6, 0, 6, 10, 1, 10),
            Block.box(7, 1, 7, 9, 2, 9), BooleanOp.OR);

    public static final BooleanProperty POWERED = VerticalPoleRedstone.POWERED;
    public int bellSpeed = 32;

    public PoleCap(Properties properties) {
        super(properties.mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.LANTERN).strength(1f,5f).noOcclusion());
        this.defaultBlockState().setValue(POWERED,false);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return ALL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(context.isHoldingItem(TRRBlocks.POLE_CAP.asItem())){
            return Shapes.block();
        }
        return ALL;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState bs, BlockGetter bg, BlockPos bp) {
        return true;
    }

    @Override
    public int getSignal(BlockState bs, BlockGetter bg, BlockPos bp, Direction dir){
        return bs.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return true;
    }

    @Override
    public void neighborChanged(BlockState bs, Level lvl, BlockPos bp, Block blk, BlockPos bp2, boolean p_55671_) {
        if (!lvl.isClientSide) {
            boolean allverticalredstoneblocks = lvl.getBlockState(bp.below()).is(TTag.VERTICAL_REDSTONE_BLOCKS);
            boolean allrrbells = lvl.getBlockState(bp.below()).is(TTag.RAILROAD_CROSSING_BELLS);
            boolean isCant = lvl.getBlockState(bp.below()).is(TTag.RR_CANTILEVERS);

            if(!allrrbells){
                if(allverticalredstoneblocks){
                    if(lvl.getBlockState(bp.below()).getValue(POWERED) == true){
                        lvl.setBlock(bp,bs.setValue(POWERED,true),3);
                    }
                    else if(lvl.getBlockState(bp.below()).getValue(POWERED) == false){
                        lvl.setBlock(bp,bs.setValue(POWERED,false),3);
                    }
                    return;
                }
                else if(isCant){
                    if(lvl.getBlockState(bp.below()).getValue(POWERED) == true){
                        lvl.setBlock(bp,bs.setValue(POWERED,true),3);
                    }
                    else if(lvl.getBlockState(bp.below()).getValue(POWERED) == false){
                        lvl.setBlock(bp,bs.setValue(POWERED,false),3);
                    }
                    return;
                }
            }
        }
    }


    @Override
    public void tick(BlockState bs, ServerLevel slvl, BlockPos bp, RandomSource rs) {
        if(!slvl.isClientSide){
            if(bs.getValue(POWERED)){
                boolean allverticalredstoneblocks = slvl.getBlockState(bp.below()).is(TTag.VERTICAL_REDSTONE_BLOCKS);
                boolean isCant = slvl.getBlockState(bp.below()).is(TTag.RR_CANTILEVERS);

                boolean both = allverticalredstoneblocks || isCant;

                if(!both){
                    slvl.setBlock(bp,bs.setValue(POWERED,false),3);
                    return;
                }
                slvl.scheduleTick(bp,bs.getBlock(), bellSpeed, TickPriority.EXTREMELY_LOW);
            }
        }
    }

    @Override
    public void onPlace(BlockState bs, Level lvl, BlockPos bp, BlockState bsOri, boolean bo1) {
        if(!lvl.isClientSide()){
            lvl.scheduleTick(bp,bs.getBlock(), bellSpeed,TickPriority.EXTREMELY_LOW);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }
}
