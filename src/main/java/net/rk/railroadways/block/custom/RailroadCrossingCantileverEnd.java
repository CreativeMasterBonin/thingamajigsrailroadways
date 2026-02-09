package net.rk.railroadways.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.rk.railroadways.block.TRRBlocks;

import java.util.List;
import java.util.stream.Stream;

public class RailroadCrossingCantileverEnd extends RailroadCrossingCantilever{
    public static final VoxelShape NORTH_NO_POLE_END = Stream.of(
            Block.box(8, 0, 0, 16, 1, 16),
            Block.box(7, 1, 0, 8, 15, 1),
            Block.box(7, 15, 0, 16, 16, 1),
            Block.box(7, 1, 15, 8, 15, 16),
            Block.box(15, 1, 0, 16, 15, 1),
            Block.box(15, 1, 15, 16, 15, 16),
            Block.box(7, 15, 15, 16, 16, 16),
            Block.box(7, 0, 0, 8, 1, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape EAST_NO_POLE_END = Stream.of(
            Block.box(0, 0, 8, 16, 1, 16),
            Block.box(15, 1, 7, 16, 15, 8),
            Block.box(15, 15, 7, 16, 16, 16),
            Block.box(0, 1, 7, 1, 15, 8),
            Block.box(15, 1, 15, 16, 15, 16),
            Block.box(0, 1, 15, 1, 15, 16),
            Block.box(0, 15, 7, 1, 16, 16),
            Block.box(-0.5, 0, 7.5, 15.5, 1, 8.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape SOUTH_NO_POLE_END = Stream.of(
            Block.box(0, 0, 0, 8, 1, 16),
            Block.box(8, 1, 15, 9, 15, 16),
            Block.box(0, 15, 15, 9, 16, 16),
            Block.box(8, 1, 0, 9, 15, 1),
            Block.box(0, 1, 15, 1, 15, 16),
            Block.box(0, 1, 0, 1, 15, 1),
            Block.box(0, 15, 0, 9, 16, 1),
            Block.box(7, 0, 0, 8, 1, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape WEST_NO_POLE_END = Stream.of(
            Block.box(0, 0, 0, 16, 1, 8),
            Block.box(0, 1, 8, 1, 15, 9),
            Block.box(0, 15, 0, 1, 16, 9),
            Block.box(15, 1, 8, 16, 15, 9),
            Block.box(0, 1, 0, 1, 15, 1),
            Block.box(15, 1, 0, 16, 15, 1),
            Block.box(15, 15, 0, 16, 16, 9),
            Block.box(-0.5, 0, 7.5, 15.5, 1, 8.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();


    public RailroadCrossingCantileverEnd(Properties properties) {
        super(properties);
    }

    public VoxelShape getCollisionShape(BlockState bs, BlockGetter bg, BlockPos bp, CollisionContext cc){
        switch(bs.getValue(FACING)){
            case NORTH -> {
                return NORTH_END;
            }
            case SOUTH -> {
                return SOUTH_END;
            }
            case EAST -> {
                return EAST_END;
            }
            case WEST -> {
                return WEST_END;
            }
            default -> {
                return Shapes.block();
            }
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(context.isHoldingItem(TRRBlocks.RAILROAD_CROSSING_CANTILEVER.asItem())){
            return Shapes.block();
        }
        else{
            switch(state.getValue(FACING)){
                case NORTH -> {
                    return NORTH_END;
                }
                case SOUTH -> {
                    return SOUTH_END;
                }
                case EAST -> {
                    return EAST_END;
                }
                case WEST -> {
                    return WEST_END;
                }
                default -> {
                    return Shapes.block();
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigsrailroadways.railroad_crossing_cantilever_end.desc")
                .withStyle(ChatFormatting.GRAY));
    }
}
