package net.rk.railroadways.block.custom;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.Unpooled;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.rk.railroadways.datagen.TRRBlockTag;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import net.rk.railroadways.entity.blockentity.custom.MultipurposeSignBE;
import net.rk.railroadways.menu.MultipurposeSignMenu;
import net.rk.railroadways.util.PoleShapes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MultipurposeSignBlock extends BaseEntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final MapCodec<MultipurposeSignBlock> CODEC = simpleCodec(MultipurposeSignBlock::new);

    public static final VoxelShape NORTH_SHAPE = Shapes.join(Block.box(0, 0, 3, 16, 16, 7),
            Block.box(7, 0, 7, 9, 16, 9), BooleanOp.OR);
    public static final VoxelShape EAST_SHAPE = Shapes.join(Block.box(9, 0, 0, 13, 16, 16),
            Block.box(7, 0, 7, 9, 16, 9), BooleanOp.OR);
    public static final VoxelShape SOUTH_SHAPE = Shapes.join(Block.box(0, 0, 9, 16, 16, 13),
            Block.box(7, 0, 7, 9, 16, 9), BooleanOp.OR);
    public static final VoxelShape WEST_SHAPE = Shapes.join(Block.box(3, 0, 0, 7, 16, 16),
            Block.box(7, 0, 7, 9, 16, 9), BooleanOp.OR);

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public MultipurposeSignBlock(Properties properties) {
        super(properties.noOcclusion().mapColor(MapColor.COLOR_BLACK).sound(SoundType.LANTERN)
                .strength(1f,10f)
                .pushReaction(PushReaction.BLOCK));
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false)
                .setValue(NORTH,false).setValue(SOUTH,false)
                .setValue(EAST,false).setValue(WEST,false));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){

    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        try{
            if(player instanceof ServerPlayer){
                player.openMenu(new MenuProvider(){
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable(MultipurposeSignMenu.translationKey)
                                .withStyle(ChatFormatting.WHITE);
                    }
                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                        return new MultipurposeSignMenu(id, inventory,
                                new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                    }
                },pos);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        catch (Exception e){
            LogUtils.getLogger().warn("Railroadways caught an exception with Multipurpose Sign: {}", e.getMessage());
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return PoleShapes.VERTICAL_ALL;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return PoleShapes.VERTICAL_ALL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context){
        if(level.getBlockEntity(pos) instanceof MultipurposeSignBE be){
            int roundedYAngle = (int)Math.abs(be.yAngle);
            switch(roundedYAngle){
                case 0 -> {
                    return NORTH_SHAPE;
                }
                case 90 -> {
                    return WEST_SHAPE;
                }
                case 180 -> {
                    return SOUTH_SHAPE;
                }
                case 270 -> {
                    return EAST_SHAPE;
                }
                default -> {
                    return PoleShapes.VERTICAL_ALL;
                }
            }
        }
        return PoleShapes.VERTICAL_ALL;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @javax.annotation.Nullable Direction direction) {
        return true;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        boolean n = level.getBlockState(pos.north()).getBlock() != Blocks.AIR;
        boolean s = level.getBlockState(pos.south()).getBlock() != Blocks.AIR;
        boolean e = level.getBlockState(pos.east()).getBlock() != Blocks.AIR;
        boolean w = level.getBlockState(pos.west()).getBlock() != Blocks.AIR;

        return state.setValue(NORTH,n).setValue(SOUTH,s).setValue(EAST,e).setValue(WEST,w);
    }

    @Override
    public void neighborChanged(BlockState bs, Level lvl, BlockPos bp, Block blk, BlockPos bp2, boolean p_55671_) {
        if (!lvl.isClientSide) {
            boolean allverticalredstoneblocks = lvl.getBlockState(bp.below()).is(TRRBlockTag.VERTICAL_REDSTONE_BLOCKS);
            boolean allrrbells = lvl.getBlockState(bp.below()).is(TRRBlockTag.RAILROAD_CROSSING_BELLS);

            boolean allrrbellsabove = lvl.getBlockState(bp.above()).is(TRRBlockTag.RAILROAD_CROSSING_BELLS);

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
    public void tick(BlockState bs, ServerLevel sl, BlockPos bp, RandomSource rs) {
        sl.updateNeighborsAt(bp.above(),this);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POWERED);
        builder.add(NORTH);
        builder.add(SOUTH);
        builder.add(EAST);
        builder.add(WEST);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(POWERED, false);
    }

    @Override
    public MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MultipurposeSignBE(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level lvl, BlockState bs, BlockEntityType<T> type) {
        return createTickerHelper(type, TRRBlockEntity.MULTIPURPOSE_SIGN_BE.get(),
                lvl.isClientSide ? MultipurposeSignBE::clientTick : MultipurposeSignBE::serverTick);
    }
}
