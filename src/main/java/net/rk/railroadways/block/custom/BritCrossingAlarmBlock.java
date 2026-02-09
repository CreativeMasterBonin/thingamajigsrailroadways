package net.rk.railroadways.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.thingamajigs.block.custom.VerticalPoleRedstone;
import net.rk.thingamajigs.datagen.TTag;
import net.rk.thingamajigs.xtras.TSoundEvent;

import java.util.List;

public class BritCrossingAlarmBlock extends Block{
    public static final BooleanProperty POWERED = VerticalPoleRedstone.POWERED;
    public static final VoxelShape ALL = Block.box(6, 0, 6, 10, 2, 10);
    public static final int britTickSpeed = 10;

    public BritCrossingAlarmBlock(Properties properties) {
        super(properties.strength(1f,3f).sound(SoundType.LANTERN).noOcclusion().noCollission());
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState bs, BlockGetter bg, BlockPos bp) {
        return true;
    }

    public VoxelShape getCollisionShape(BlockState bs, BlockGetter bg, BlockPos bp, CollisionContext cc) {
        return ALL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return context.isHoldingItem(TRRBlocks.BRITISH_RAILWAY_ALARM.asItem()) ? Shapes.block() : ALL;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 0;
    }

    public void neighborChanged(BlockState bs, Level lvl, BlockPos bp, Block blk, BlockPos bp2, boolean p_55671_) {
        if (!lvl.isClientSide) {
            boolean allverticalredstoneblocks = lvl.getBlockState(bp.below()).is(TTag.VERTICAL_REDSTONE_BLOCKS);
            boolean allrrbells = lvl.getBlockState(bp.below()).is(TTag.RAILROAD_CROSSING_BELLS);
            if(!allrrbells){
                if(allverticalredstoneblocks){
                    if(lvl.getBlockState(bp.below()).getValue(POWERED) == true){
                        lvl.setBlock(bp,bs.setValue(POWERED,true),3);
                    }
                    else if(lvl.getBlockState(bp.below()).getValue(POWERED) == false){
                        lvl.setBlock(bp,bs.setValue(POWERED,false),3);
                    }
                }
            }
        }
    }

    public void tick(BlockState bs, ServerLevel slvl, BlockPos bp, RandomSource rs) {
        if(!slvl.isClientSide()){
            if(bs.getValue(POWERED)){
                boolean allverticalredstoneblocks = slvl.getBlockState(bp.below()).is(TTag.VERTICAL_REDSTONE_BLOCKS);
                if(!allverticalredstoneblocks){
                    slvl.setBlock(bp,bs.setValue(POWERED,false),3);
                    return;
                }
                attemptPlaySound(slvl,bp);
                slvl.scheduleTick(bp,bs.getBlock(),britTickSpeed, TickPriority.LOW);
            }
        }
    }

    public void onPlace(BlockState bs, Level lvl, BlockPos bp, BlockState bsOri, boolean bo1) {
        if(!lvl.isClientSide()){
            lvl.scheduleTick(bp,bs.getBlock(),britTickSpeed,TickPriority.LOW);
        }
    }

    public boolean attemptPlaySound(Level lp, BlockPos bp) {
        if (!lp.isClientSide) {
            lp.playSound(null,bp, TSoundEvent.YODELER.get(), SoundSource.BLOCKS,1.25F,1.0F);
            return true;
        }
        else {
            return false;
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

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.rr_bell.desc")
                .withStyle(ChatFormatting.GRAY));
    }
}
