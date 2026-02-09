package net.rk.railroadways.block.custom;

import com.mojang.serialization.MapCodec;
import io.netty.buffer.Unpooled;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingArmWithLights;
import net.rk.railroadways.menu.RailroadCrossingArmLightedMenu;
import net.rk.thingamajigs.block.custom.VerticalPoleRedstone;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class RailroadCrossingArmLightedBlock extends BaseEntityBlock{
    public static final BooleanProperty POWERED = VerticalPoleRedstone.POWERED;

    public static final MapCodec<RailroadCrossingArmLightedBlock> CODEC = simpleCodec(RailroadCrossingArmLightedBlock::new);
    public static final VoxelShape ALL = Stream.of(
            Block.box(4, 0, 4, 12, 2, 12),
            Block.box(6, 2, 6, 10, 10, 10),
            Block.box(7, 10, 7, 9, 16, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public RailroadCrossingArmLightedBlock(Properties properties) {
        super(properties.noOcclusion().sound(SoundType.LANTERN).pushReaction(PushReaction.BLOCK).strength(1f,50f));
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return context.isHoldingItem(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.asItem()) ? Shapes.block() : ALL;
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.block();
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return ALL;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        try{
            if(player instanceof ServerPlayer){
                player.openMenu(new MenuProvider(){
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable("container.thingamajigsrailroadways.rr_arm.title")
                                .withStyle(ChatFormatting.WHITE);
                    }
                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                        return new RailroadCrossingArmLightedMenu(id, inventory,
                                new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                    }
                },pos);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        catch (Exception e){
            Logger.getAnonymousLogger().warning("TRailroadways Exception caught in Railroad Crossing Arm Lighted Block! Err: " + e.getMessage());
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RailroadCrossingArmWithLights(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level lvl, BlockState bs, BlockEntityType<T> bet) {
        return createTickerHelper(bet, TRRBlockEntity.RAILROAD_CROSSING_ARM_LIGHTED_BE.get(),
                lvl.isClientSide ? RailroadCrossingArmWithLights::clientTick : RailroadCrossingArmWithLights::serverTick);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(POWERED, false);
    }

    @Override
    public void neighborChanged(BlockState bs, Level lvl, BlockPos bp, Block blk, BlockPos bp2, boolean bool1) {
        if(!lvl.isClientSide){
            boolean flag = bs.getValue(POWERED);
            if(flag != lvl.hasNeighborSignal(bp)){
                if(flag){
                    lvl.scheduleTick(bp,this,4);
                }
                else{
                    lvl.setBlock(bp,bs.cycle(POWERED), 2);
                }
            }
        }
    }

    @Override
    public void tick(BlockState bs, ServerLevel sl, BlockPos bp, RandomSource rs){
        Block blk = sl.getBlockState(bp.above()).getBlock();
        boolean flag2 = sl.hasNeighborSignal(bp);

        if(flag2){
            sl.setBlock(bp,bs.setValue(POWERED,true),2);
            sl.scheduleTick(bp.above(),blk,7,TickPriority.LOW);
        }
        else{
            sl.setBlock(bp,bs.setValue(POWERED,false),2);
            sl.scheduleTick(bp.above(),blk,7,TickPriority.LOW);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigsrailroadways.rr_arm.desc")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.block();
    }
}
