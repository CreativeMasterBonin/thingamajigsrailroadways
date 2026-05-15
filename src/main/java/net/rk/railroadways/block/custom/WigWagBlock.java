package net.rk.railroadways.block.custom;

import com.mojang.serialization.MapCodec;
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
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.datagen.TRRBlockTag;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import net.rk.railroadways.entity.blockentity.custom.WigWagBE;
import net.rk.railroadways.util.PoleShapes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WigWagBlock extends BaseEntityBlock {
    public static final BooleanProperty POWERED = VerticalPoleRedstoneRR.POWERED;
    public static final MapCodec<WigWagBlock> CODEC = simpleCodec(WigWagBlock::new);

    public WigWagBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> bs) {
        super.createBlockStateDefinition(bs.add(POWERED));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(POWERED,false);
    }

    @Override
    public void neighborChanged(BlockState bs, Level lvl, BlockPos bp, Block blk, BlockPos bp2, boolean p_55671_) {
        if (!lvl.isClientSide) {
            boolean allverticalredstoneblocks = lvl.getBlockState(bp.below()).is(TRRBlockTag.VERTICAL_REDSTONE_BLOCKS);
            boolean allrrbells = lvl.getBlockState(bp.below()).is(TRRBlockTag.RAILROAD_CROSSING_BELLS);

            boolean allrrbellsabove = lvl.getBlockState(bp.above()).is(TRRBlockTag.RAILROAD_CROSSING_BELLS);

            if(lvl.getBlockEntity(bp) instanceof WigWagBE wigWagBE){
                if(wigWagBE.linkedToController){
                    return;
                }
            }

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

            if(lvl.getBlockState(bp.above()).is(TRRBlockTag.RR_CANTILEVERS)){
                if(allverticalredstoneblocks){
                    if(lvl.getBlockState(bp.below()).getValue(POWERED)){
                        lvl.setBlock(bp,bs.setValue(POWERED,true),3);
                    }
                    else if(!lvl.getBlockState(bp.below()).getValue(POWERED)){
                        lvl.setBlock(bp,bs.setValue(POWERED,false),3);
                    }
                }
                else{
                    lvl.setBlock(bp,bs.setValue(POWERED,false),3);
                }
                return;
            }

            if(!allrrbells){
                if(allverticalredstoneblocks){
                    if(lvl.getBlockState(bp.below()).getValue(POWERED) == true){
                        lvl.setBlock(bp,bs.setValue(POWERED,true),3);
                    }
                    else if(lvl.getBlockState(bp.below()).getValue(POWERED) == false){
                        lvl.setBlock(bp,bs.setValue(POWERED,false),3);
                    }
                }
                else{
                    if(lvl.hasNeighborSignal(bp) == true){
                        lvl.setBlock(bp,bs.setValue(POWERED,true),3);
                        lvl.scheduleTick(bp.above(),this,3, TickPriority.LOW);
                    }
                    else if(lvl.hasNeighborSignal(bp) == false){
                        lvl.setBlock(bp,bs.setValue(POWERED,false),3);
                        lvl.scheduleTick(bp.above(),this,3,TickPriority.LOW);
                    }
                }
            }
        }
    }

    @Override
    public void tick(BlockState bs, ServerLevel sl, BlockPos bp, RandomSource rs) {
        sl.updateNeighborsAt(bp.above(),this);
    }

    @Override
    public RenderShape getRenderShape(BlockState bs) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return context.isHoldingItem(TRRBlocks.WIGWAG.asItem()) ? Shapes.block() : PoleShapes.VERTICAL_ALL;
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return PoleShapes.VERTICAL_ALL;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.railroadways.wigwag.desc")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WigWagBE(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level lvl, BlockState bs, BlockEntityType<T> type) {
        return createTickerHelper(type, TRRBlockEntity.WIGWAG_BE.get(),
                lvl.isClientSide ? null : WigWagBE::serverTick);
    }
}
