package net.rk.railroadways.block.custom;

import com.mojang.serialization.MapCodec;
import io.netty.buffer.Unpooled;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingCantLightsBE;
import net.rk.railroadways.menu.RRCantLightsMenu;
import net.rk.thingamajigs.block.custom.VerticalPoleRedstone;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.logging.Logger;

public class RailroadCrossingCantileverLights extends BaseEntityBlock{
    public static final BooleanProperty POWERED = VerticalPoleRedstone.POWERED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final MapCodec<RailroadCrossingCantileverLights> CODEC = simpleCodec(RailroadCrossingCantileverLights::new);


    public RailroadCrossingCantileverLights(Properties properties) {
        super(properties.strength(1.1F,10F).noOcclusion().pushReaction(PushReaction.BLOCK));
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.block();
    }

    public VoxelShape getCollisionShape(BlockState bs, BlockGetter bg, BlockPos bp, CollisionContext cc){
        if(bs.getValue(FACING) == Direction.NORTH || bs.getValue(FACING) == Direction.SOUTH){
            return RailroadCrossingCantilever.NORTH_SOUTH;
        }
        else{
            return RailroadCrossingCantilever.EAST_WEST;
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(state.getValue(FACING) == Direction.NORTH || state.getValue(FACING) == Direction.SOUTH){
            return RailroadCrossingCantilever.NORTH_SOUTH;
        }
        else{
            return RailroadCrossingCantilever.EAST_WEST;
        }
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.block();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState bs, BlockGetter bg, BlockPos bp){return true;}

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RailroadCrossingCantLightsBE(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level lvl, BlockState bs, BlockEntityType<T> bet) {
        return createTickerHelper(bet, TRRBlockEntity.RR_CANTILEVER_LIGHTS_BE.get(),
                lvl.isClientSide ? RailroadCrossingCantLightsBE::clientTick : RailroadCrossingCantLightsBE::serverTick);
    }

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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,POWERED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(POWERED, false);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if(level.hasNeighborSignal(pos)){
            level.setBlock(pos,state.setValue(POWERED,true),3);
        }
        else{
            level.setBlock(pos,state.setValue(POWERED,false),3);
        }
        level.scheduleTick(pos.above(),level.getBlockState(pos.above()).getBlock(),2, TickPriority.VERY_LOW);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigsrailroadways.railroad_crossing_cantilever_lights.desc")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        try{
            if(player instanceof ServerPlayer){
                player.openMenu(new MenuProvider(){
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable("container.thingamajigsrailroadways.rr_lights.title")
                                .withStyle(ChatFormatting.WHITE);
                    }
                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                        return new RRCantLightsMenu(id, inventory,
                                new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                    }
                },pos);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        catch (Exception e){
            Logger.getAnonymousLogger().warning("TRailroadways Exception caught in Railroad Crossing Lights! Err: " + e.getMessage());
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }
}
