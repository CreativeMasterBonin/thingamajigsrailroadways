package net.rk.railroadways.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.stream.Stream;

public class PoleShapes{
    // single parts
    public static final VoxelShape HORIZONTAL_NORTHSOUTH = Optional.of(Block.box(0, 7, 7, 16, 9, 9)).get();
    public static final VoxelShape HORIZONTAL_EASTWEST = Optional.of(Block.box(7, 7, 0, 9, 9, 16)).get();
    public static final VoxelShape VERTICAL_ALL = Optional.of(Block.box(7, 0, 7, 9, 16, 9)).get();
    public static final VoxelShape SMALL_TOP_VERTICAL = Optional.of(Block.box(7, 9, 7, 9, 16, 9)).get();
    public static final VoxelShape SMALL_BOTTOM_VERTICAL = Optional.of(Block.box(7, 0, 7, 9, 7, 9)).get();
    public static final VoxelShape SMALL_NORTH = Optional.of(Block.box(7, 7, 0, 9, 9, 7)).get();
    public static final VoxelShape SMALL_SOUTH = Optional.of(Block.box(7, 7, 9, 9, 9, 16)).get();
    public static final VoxelShape SMALL_EAST = Optional.of(Block.box(9, 7, 7, 16, 9, 9)).get();
    public static final VoxelShape SMALL_WEST = Optional.of(Block.box(0, 7, 7, 7, 9, 9)).get();
    // multi shapes
    // T-Pole Shapes
    public static final VoxelShape T_NORTHSOUTH = Stream.of(SMALL_BOTTOM_VERTICAL, HORIZONTAL_NORTHSOUTH).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape T_EASTWEST = Stream.of(SMALL_BOTTOM_VERTICAL, HORIZONTAL_EASTWEST).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape T_INVERT_NORTHSOUTH = Stream.of(SMALL_TOP_VERTICAL, HORIZONTAL_NORTHSOUTH).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape T_INVERT_EASTWEST = Stream.of(SMALL_TOP_VERTICAL, HORIZONTAL_EASTWEST).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    // L-Horizontal
    public static final VoxelShape L_NORTH = Stream.of(SMALL_NORTH, SMALL_EAST).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape L_SOUTH = Stream.of(SMALL_EAST, SMALL_SOUTH).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape L_EAST = Stream.of(SMALL_SOUTH, SMALL_WEST).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape L_WEST = Stream.of(SMALL_WEST, SMALL_NORTH).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    // Axis Pole
    public static final VoxelShape AXIS_NORTH = Stream.of(L_NORTH, SMALL_BOTTOM_VERTICAL).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape AXIS_SOUTH = Stream.of(L_SOUTH, SMALL_BOTTOM_VERTICAL).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape AXIS_EAST = Stream.of(L_EAST, SMALL_BOTTOM_VERTICAL).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape AXIS_WEST = Stream.of(L_WEST, SMALL_BOTTOM_VERTICAL).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    // Plus Pole
    public static final VoxelShape PLUS_NORTHSOUTH = Stream.of(VERTICAL_ALL, HORIZONTAL_NORTHSOUTH).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape PLUS_EASTWEST = Stream.of(VERTICAL_ALL, HORIZONTAL_EASTWEST).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    //
}
