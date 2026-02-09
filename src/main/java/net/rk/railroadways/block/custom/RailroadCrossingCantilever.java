package net.rk.railroadways.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.thingamajigs.block.TBlocks;
import net.rk.thingamajigs.block.custom.VerticalPoleRedstone;
import net.rk.thingamajigs.datagen.TTag;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class RailroadCrossingCantilever extends Block{
    public static final BooleanProperty POWERED = VerticalPoleRedstone.POWERED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final VoxelShape NORTH_SOUTH = Stream.of(
            Block.box(0, 0, 0, 16, 1, 16),
            Block.box(0, 1, 0, 1, 15, 1),
            Block.box(0, 15, 0, 16, 16, 1),
            Block.box(0, 1, 15, 1, 15, 16),
            Block.box(15, 1, 0, 16, 15, 1),
            Block.box(15, 1, 15, 16, 15, 16),
            Block.box(0, 15, 15, 16, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape EAST_WEST = Stream.of(
            Block.box(0, 0, 0, 16, 1, 16),
            Block.box(15, 1, 0, 16, 15, 1),
            Block.box(15, 15, 0, 16, 16, 16),
            Block.box(0, 1, 0, 1, 15, 1),
            Block.box(15, 1, 15, 16, 15, 16),
            Block.box(0, 1, 15, 1, 15, 16),
            Block.box(0, 15, 0, 1, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    // ending shapes
    public static final VoxelShape NORTH_END = Stream.of(
            Block.box(8, 0, 0, 16, 1, 16),
            Block.box(7, 1, 0, 8, 15, 1),
            Block.box(7, 15, 0, 16, 16, 1),
            Block.box(7, 1, 15, 8, 15, 16),
            Block.box(15, 1, 0, 16, 15, 1),
            Block.box(15, 1, 15, 16, 15, 16),
            Block.box(7, 15, 15, 16, 16, 16),
            Block.box(7, 1, 7, 9, 16, 9),
            Block.box(7, 0, 0, 8, 1, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape EAST_END = Stream.of(
            Block.box(0, 0, 8, 16, 1, 16),
            Block.box(15, 1, 7, 16, 15, 8),
            Block.box(15, 15, 7, 16, 16, 16),
            Block.box(0, 1, 7, 1, 15, 8),
            Block.box(15, 1, 15, 16, 15, 16),
            Block.box(0, 1, 15, 1, 15, 16),
            Block.box(0, 15, 7, 1, 16, 16),
            Block.box(7, 1, 7, 9, 16, 9),
            Block.box(0, 0, 7, 16, 1, 8)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SOUTH_END = Stream.of(
            Block.box(0, 0, 0, 8, 1, 16),
            Block.box(8, 1, 15, 9, 15, 16),
            Block.box(0, 15, 15, 9, 16, 16),
            Block.box(8, 1, 0, 9, 15, 1),
            Block.box(0, 1, 15, 1, 15, 16),
            Block.box(0, 1, 0, 1, 15, 1),
            Block.box(0, 15, 0, 9, 16, 1),
            Block.box(7, 1, 7, 9, 16, 9),
            Block.box(8, 0, 0, 9, 1, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape WEST_END = Stream.of(
            Block.box(0, 0, 0, 16, 1, 8),
            Block.box(0, 1, 8, 1, 15, 9),
            Block.box(0, 15, 0, 1, 16, 9),
            Block.box(15, 1, 8, 16, 15, 9),
            Block.box(0, 1, 0, 1, 15, 1),
            Block.box(15, 1, 0, 16, 15, 1),
            Block.box(15, 15, 0, 16, 16, 9),
            Block.box(7, 1, 7, 9, 16, 9),
            Block.box(0, 0, 8, 16, 1, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();


    public RailroadCrossingCantilever(Properties properties){
        super(properties.strength(1.1F,10F).noOcclusion().pushReaction(PushReaction.BLOCK));
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.block();
    }

    public VoxelShape getCollisionShape(BlockState bs, BlockGetter bg, BlockPos bp, CollisionContext cc){
        if(bs.getValue(FACING) == Direction.NORTH || bs.getValue(FACING) == Direction.SOUTH){
            return NORTH_SOUTH;
        }
        else{
            return EAST_WEST;
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(state.getValue(FACING) == Direction.NORTH || state.getValue(FACING) == Direction.SOUTH){
            return NORTH_SOUTH;
        }
        else{
            return EAST_WEST;
        }
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.block();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState bs, BlockGetter bg, BlockPos bp){return true;}

    @Override
    public boolean isSignalSource(BlockState bs){return false;}

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
         return false;
    }

    @Override
    public int getSignal(BlockState bs, BlockGetter bg, BlockPos bp, Direction dir){
        return 0;
    }

    @Override
    public void neighborChanged(BlockState bs, Level lvl, BlockPos bp, Block blk, BlockPos bp2, boolean p_55671_) {
        Block redstone = Blocks.REDSTONE_WIRE.defaultBlockState().getBlock();
        // check if the block below is tagged as a vertical redstone block
        // or if it's redstone, tick the block above (to update the redstone)
        if(!lvl.isClientSide){
            boolean allverticalredstoneblocks = lvl.getBlockState(bp.below()).is(TTag.VERTICAL_REDSTONE_BLOCKS);
            boolean allrrbells = lvl.getBlockState(bp.below()).is(TTag.RAILROAD_CROSSING_BELLS);
            boolean allrrbellsabove = lvl.getBlockState(bp.above()).is(TTag.RAILROAD_CROSSING_BELLS);

            // Bells and this block hate each other, so it's disabled.
            if(allrrbellsabove){
                if(allverticalredstoneblocks){
                    if(lvl.getBlockState(bp.below()).getValue(POWERED) == true){
                        lvl.setBlock(bp,bs.setValue(POWERED,true),3);
                    }
                    else if(lvl.getBlockState(bp.below()).getValue(POWERED) == false){
                        lvl.setBlock(bp,bs.setValue(POWERED,false),3);
                    }
                }
                else{
                    lvl.setBlock(bp,bs.setValue(POWERED,false),3);
                }
                return;
            }
            else if(lvl.getBlockState(bp.above()).is(Blocks.REDSTONE_WIRE)){
                lvl.scheduleTick(bp.above(),lvl.getBlockState(bp.above()).getBlock(),2,TickPriority.LOW);
                return;
            }
        }
    }

    @Override
    public void tick(BlockState bs, ServerLevel sl, BlockPos bp, RandomSource rs) {
        sl.updateNeighborsAt(bp.above(),this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,POWERED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(POWERED, false);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigsrailroadways.railroad_crossing_cantilever.desc")
                .withStyle(ChatFormatting.GRAY));
    }
}
