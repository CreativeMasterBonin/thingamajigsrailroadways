package net.rk.railroadways.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.datagen.TRRBlockTag;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import net.rk.railroadways.entity.blockentity.custom.EnhancedDirectionalCrossingLightBE;
import net.rk.railroadways.util.PoleShapes;
import net.rk.railroadways.util.Utilities;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnhancedDirectionalCrossingLight extends BaseEntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public enum RedLightStates implements StringRepresentable {
        NONE("none"),
        ON("on"),
        OFF("off");

        public final String name;

        RedLightStates(String name){
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public enum OrangeLightStates implements StringRepresentable {
        NONE("none"),
        LEFT("left"),
        RIGHT("right"),
        CENTER("center"),
        OFF("off");

        public final String name;

        OrangeLightStates(String name){
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
    public static final EnumProperty<RedLightStates> redLightState =
            EnumProperty.create("red_light_state",RedLightStates.class);

    public static final EnumProperty<OrangeLightStates> orangeLightState =
            EnumProperty.create("orange_light_state",OrangeLightStates.class);

    public static final MapCodec<EnhancedDirectionalCrossingLight> CODEC = simpleCodec(EnhancedDirectionalCrossingLight::new);

    public EnhancedDirectionalCrossingLight(Properties properties) {
        super(properties.mapColor(MapColor.COLOR_BLACK).strength(1f,10f)
                .noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(orangeLightState,OrangeLightStates.NONE)
                .setValue(POWERED,false).setValue(redLightState,RedLightStates.NONE));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.enhanced_directional_crossing_light.desc")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context){
        if(context.isHoldingItem(TRRBlocks.ENHANCED_DIRECTIONAL_CROSSING_LIGHT.asItem()) || context.isHoldingItem(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.asItem()))
            return Shapes.block();
        return PoleShapes.VERTICAL_ALL;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return PoleShapes.VERTICAL_ALL;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @javax.annotation.Nullable Direction direction) {
        return true;
    }

    @Override
    public void neighborChanged(BlockState bs, Level lvl, BlockPos bp, Block blk, BlockPos bp2, boolean p_55671_) {
        if (!lvl.isClientSide) {
            boolean allverticalredstoneblocks = lvl.getBlockState(bp.below()).is(TRRBlockTag.VERTICAL_REDSTONE_BLOCKS);
            boolean allrrbells = lvl.getBlockState(bp.below()).is(TRRBlockTag.RAILROAD_CROSSING_BELLS);

            boolean allrrbellsabove = lvl.getBlockState(bp.above()).is(TRRBlockTag.RAILROAD_CROSSING_BELLS);

            // seems to work pretty good for the check (bells, cantilevers, and other components tested)
            if(lvl.getBlockEntity(bp) instanceof EnhancedDirectionalCrossingLightBE be){
                if(be.linkedToController){
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

            boolean cant4 = lvl.getBlockState(bp.above()).is(TRRBlockTag.RR_CANTILEVERS);

            if(cant4){
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
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if(!level.isClientSide()){
            if(level.getBlockEntity(pos) instanceof EnhancedDirectionalCrossingLightBE lights){
                lights.yAngle = Utilities.radiansToDegrees(placer.getYHeadRot());
                lights.updateBlock();
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(POWERED, false)
                .setValue(redLightState,RedLightStates.NONE) // the block itself should never change the secret blockstates
                .setValue(orangeLightState,OrangeLightStates.NONE);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POWERED);
        builder.add(redLightState);
        builder.add(orangeLightState);
    }

    @Override
    public void tick(BlockState bs, ServerLevel sl, BlockPos bp, RandomSource rs) {
        sl.updateNeighborsAt(bp.above(),this);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level lvl, BlockState bs, BlockEntityType<T> type) {
        return createTickerHelper(type, TRRBlockEntity.ENHANCED_DIRECTIONAL_CROSSING_LIGHT_BE.get(),
                lvl.isClientSide ? EnhancedDirectionalCrossingLightBE::clientTick : EnhancedDirectionalCrossingLightBE::serverTick);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new EnhancedDirectionalCrossingLightBE(blockPos,blockState);
    }
}
