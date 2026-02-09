package net.rk.railroadways.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.ticks.TickPriority;
import net.rk.thingamajigs.block.custom.VerticalPoleRedstone;
import net.rk.thingamajigs.datagen.TTag;

import java.util.List;

public class BaseRailroadCrossingBell extends Block{
    public static final BooleanProperty POWERED = VerticalPoleRedstone.POWERED;
    public static final int MECHANICAL_BELL_SPEED = 10;
    public static final int ELECTRONIC_BELL_SPEED = 12;
    public int bellSpeed = MECHANICAL_BELL_SPEED;
    public boolean isElectronic = false;

    public BaseRailroadCrossingBell(Properties p) {
        super(p.strength(1F,10F).sound(SoundType.LANTERN).noOcclusion().noCollission());
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    public BaseRailroadCrossingBell(Properties p, boolean electronicType) {
        super(p.strength(1F,10F).sound(SoundType.LANTERN).noOcclusion().noCollission());
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
        this.isElectronic = electronicType;
        this.bellSpeed = this.isElectronic ? ELECTRONIC_BELL_SPEED : MECHANICAL_BELL_SPEED;
    }

    public BaseRailroadCrossingBell(Properties p, int customBellSpeed) {
        super(p.strength(1F,10F).sound(SoundType.LANTERN).noOcclusion().noCollission());
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
        this.isElectronic = true;
        this.bellSpeed = customBellSpeed;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
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
                attemptPlaySound(slvl,bp);
                slvl.scheduleTick(bp,bs.getBlock(), bellSpeed, TickPriority.LOW);
            }
        }
    }

    @Override
    public void onPlace(BlockState bs, Level lvl, BlockPos bp, BlockState bsOri, boolean bo1) {
        if(!lvl.isClientSide()){
            lvl.scheduleTick(bp,bs.getBlock(), bellSpeed,TickPriority.LOW);
        }
    }

    public boolean attemptPlaySound(Level lp, BlockPos bp) {
        if (!lp.isClientSide){
            lp.playSound(null, bp, SoundEvents.SHEEP_AMBIENT, SoundSource.BLOCKS, 0.75F, 1.0F);
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
