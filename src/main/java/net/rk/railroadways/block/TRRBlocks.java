package net.rk.railroadways.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.block.custom.*;
import net.rk.railroadways.datagen.TRRBlockTag;
import net.rk.railroadways.item.TRRItems;
import net.rk.railroadways.util.TRRSound;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

@SuppressWarnings("deprecated")
public class TRRBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Thingamajigsrailroadways.MODID);

    public static final DeferredBlock<Block> RAILROAD_CROSSING_ARM = register("railroad_crossing_arm",
            () -> new RailroadCrossingArmBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> CROSSBUCK = register("crossbuck",
            () -> new CrossbuckBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> RAILROAD_CROSSING_LIGHTS = register("railroad_crossing_lights",
            () -> new RailroadCrossingLights(BlockBehaviour.Properties.of().lightLevel(railroadLightsEmission(15))));

    public static final DeferredBlock<Block> ELECTRONIC_BELL_TYPE_1 = register("railroad_crossing_ebell_type_one",
            () -> new BaseRailroadCrossingBell(BlockBehaviour.Properties.of(),true){
                @Override
                public boolean attemptPlaySound(Level lp, BlockPos bp) {
                    if (!lp.isClientSide){
                        lp.playSeededSound(null,bp.getX(),bp.getY(),bp.getZ(),
                                TRRSound.NEW_EBELL_ONE.get(), SoundSource.BLOCKS,0.35f,1.0f,lp.random.nextLong());
                        return true;
                    }
                    else {
                        return false;
                    }
                }

                @Override
                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                    return Block.box(6, 0, 6, 10, 14, 10);
                }
            });

    public static final DeferredBlock<Block> ELECTRONIC_BELL_TYPE_2 = register("railroad_crossing_ebell_type_two",
            () -> new BaseRailroadCrossingBell(BlockBehaviour.Properties.of(),true){
                @Override
                public boolean attemptPlaySound(Level lp, BlockPos bp) {
                    if (!lp.isClientSide){
                        lp.playSeededSound(null,bp.getX(),bp.getY(),bp.getZ(),
                               TRRSound.NEW_EBELL_TWO.get(), SoundSource.BLOCKS,0.35f,1.0f,lp.random.nextLong());
                        return true;
                    }
                    else {
                        return false;
                    }
                }

                @Override
                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                    return Stream.of(
                            Block.box(6, 0, 6, 10, 1, 10),
                            Block.box(5.5, 6, 5.5, 10.5, 12, 10.5),
                            Block.box(4, 12, 4, 12, 14, 12),
                            Block.box(7, 1, 7, 9, 6, 9)
                    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                }
            });

    public static final DeferredBlock<Block> MECHANICAL_BELL_TYPE_1 = register("railroad_crossing_bell_type_one",
            () -> new RotatingBaseRailroadCrossingBell(BlockBehaviour.Properties.of(),false){
                @Override
                public boolean attemptPlaySound(Level lp, BlockPos bp) {
                    if (!lp.isClientSide){
                        lp.playSeededSound(null,bp.getX(),bp.getY(),bp.getZ(),
                                TRRSound.MECH_BELL_ONE.get(),SoundSource.BLOCKS,0.35f,1.0f,lp.random.nextLong());
                        return true;
                    }
                    else {
                        return false;
                    }
                }

                @Override
                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                    return Block.box(2, 0, 2, 14, 13, 14);
                }
            });

    public static final DeferredBlock<Block> MECHANICAL_BELL_TYPE_2 = register("railroad_crossing_bell_type_two",
            () -> new RotatingBaseRailroadCrossingBell(BlockBehaviour.Properties.of(),false){
                @Override
                public boolean attemptPlaySound(Level lp, BlockPos bp) {
                    if (!lp.isClientSide){
                        lp.playSeededSound(null,bp.getX(),bp.getY(),bp.getZ(),
                                TRRSound.MECH_BELL_TWO.get(),SoundSource.BLOCKS,0.35f,1.0f,lp.random.nextLong());
                        return true;
                    }
                    else {
                        return false;
                    }
                }

                @Override
                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                    return Block.box(2, 0, 2, 14, 13, 14);
                }
            });

    public static final DeferredBlock<Block> PURPLE_RAIL = register("purple_rail",
            () -> new RailBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)
                    .noCollission().strength(0.7F).sound(SoundType.METAL)){
                public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
                    if(isFlexibleRail(state,level,pos)){
                        return 0.91f;
                    }
                    return 1.1F;
                }

                @Override
                protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
                    return true;
                }

                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("block.thingamajigsrailroadways.purple_rail.desc").withStyle(ChatFormatting.GRAY));
                }

                @Override
                public void neighborChanged(BlockState bs, Level lvl, BlockPos bp, Block block1, BlockPos bp2, boolean bool1) {
                    if (!lvl.isClientSide && lvl.getBlockState(bp).is(this)) {
                        RailShape railshape = getRailDirection(bs, lvl, bp, null);
                        this.updateState(bs, lvl, bp, block1);
                    }
                }
            });

    public static final DeferredBlock<Block> PURPLE_POWERED_RAIL = register("purple_powered_rail",
            () -> new PurplePoweredRailBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)
                    .noCollission().strength(0.7F).sound(SoundType.METAL)));

    public static final DeferredBlock<Block> PURPLE_ACTIVATOR_RAIL = register("purple_activator_rail",
            () -> new PurpleActivatorRailBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)
                    .noCollission().strength(0.7F).sound(SoundType.METAL)));

    public static final DeferredBlock<Block> PURPLE_DETECTOR_RAIL = register("purple_detector_rail",
            () -> new PurpleDetectorRailBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)
                    .noCollission().strength(0.7F).sound(SoundType.METAL)));

    public static final DeferredBlock<Block> BRITISH_RAILWAY_LIGHTS = register("british_railway_lights",
            () -> new BritRailwayLightsBlock(BlockBehaviour.Properties.of()
                    .lightLevel(railwayCrossingEmission(3))));

    public static final DeferredBlock<Block> BRITISH_RAILWAY_ALARM = register("british_railway_alarm",
            () -> new BritCrossingAlarmBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> TRI_RAILWAY_LIGHTS = register("tri_railway_lights",
            () -> new TriRailwayLightsBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> DUAL_RAILWAY_LIGHTS = register("dual_railway_lights",
            () -> new DualRailwayLightsBlock(BlockBehaviour.Properties.of()));


    public static final DeferredBlock<Block> ELECTRONIC_BELL_TYPE_3 = register("railroad_crossing_ebell_type_three",
            () -> new BaseRailroadCrossingBell(BlockBehaviour.Properties.of(),20){
                @Override
                public boolean attemptPlaySound(Level lp, BlockPos bp) {
                    if (!lp.isClientSide){
                        lp.playSeededSound(null,bp.getX(),bp.getY(),bp.getZ(),
                                TRRSound.EBELL_THREE.get(),SoundSource.BLOCKS,0.25f,1.0f,lp.random.nextLong());
                        return true;
                    }
                    else {
                        return false;
                    }
                }

                @Override
                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                    return Stream.of(
                            Block.box(6, 0, 6, 10, 1, 10),
                            Block.box(6, 3, 6, 10, 15, 10),
                            Block.box(7, 1, 7, 9, 3, 9)
                    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                }
            });

    public static final DeferredBlock<Block> ELECTRONIC_BELL_TYPE_4 = register("railroad_crossing_ebell_type_four",
            () -> new RotatingBaseRailroadCrossingBell(BlockBehaviour.Properties.of(),12){
                @Override
                public boolean attemptPlaySound(Level lp, BlockPos bp) {
                    if (!lp.isClientSide){
                        lp.playSeededSound(null,bp.getX(),bp.getY(),bp.getZ(),
                                TRRSound.EBELL_FOUR.get(),SoundSource.BLOCKS,0.5f,1.0f,lp.random.nextLong());
                        return true;
                    }
                    else {
                        return false;
                    }
                }

                @Override
                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                    return Block.box(7, 0, 7, 9, 8, 9);
                }
            });

    public static final DeferredBlock<Block> RAILROAD_CROSSING_CANTILEVER = register("railroad_crossing_cantilever",
            () -> new RailroadCrossingCantilever(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> RAILROAD_CROSSING_CANTILEVER_END = register("railroad_crossing_cantilever_end",
            () -> new RailroadCrossingCantileverEnd(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> RAILROAD_CROSSING_CANTILEVER_LIGHTS = register("railroad_crossing_cantilever_lights",
            () -> new RailroadCrossingCantileverLights(BlockBehaviour.Properties.of()));


    // 0.0.4
    public static final DeferredBlock<Block> RAILROAD_CROSSING_ARM_LIGHTED = register("railroad_crossing_arm_lighted",
            () -> new RailroadCrossingArmLightedBlock(BlockBehaviour.Properties.of()));


    // 0.0.5
    public static final DeferredBlock<Block> RR_LADDER_POLE = register("rr_ladder_pole",
            () -> new DirectionalVerticalRedstonePole(BlockBehaviour.Properties.of()){
                public VoxelShape NORTH = Stream.of(
                        Block.box(7, 0, 7, 9, 16, 9),
                        Block.box(12, 0, 6, 14, 7, 7),
                        Block.box(2, 0, 6, 4, 7, 7),
                        Block.box(4, 4, 6, 12, 6, 7),
                        Block.box(12, 7, 6, 14, 16, 7),
                        Block.box(2, 7, 6, 4, 16, 7),
                        Block.box(4, 11, 6, 12, 13, 7)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                public VoxelShape EAST = Stream.of(
                        Block.box(7, 0, 7, 9, 16, 9),
                        Block.box(9, 0, 12, 10, 7, 14),
                        Block.box(9, 0, 2, 10, 7, 4),
                        Block.box(9, 4, 4, 10, 6, 12),
                        Block.box(9, 7, 12, 10, 16, 14),
                        Block.box(9, 7, 2, 10, 16, 4),
                        Block.box(9, 11, 4, 10, 13, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                public VoxelShape SOUTH = Stream.of(
                        Block.box(7, 0, 7, 9, 16, 9),
                        Block.box(2, 0, 9, 4, 7, 10),
                        Block.box(12, 0, 9, 14, 7, 10),
                        Block.box(4, 4, 9, 12, 6, 10),
                        Block.box(2, 7, 9, 4, 16, 10),
                        Block.box(12, 7, 9, 14, 16, 10),
                        Block.box(4, 11, 9, 12, 13, 10)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                public VoxelShape WEST = Stream.of(
                        Block.box(7, 0, 7, 9, 16, 9),
                        Block.box(6, 0, 2, 7, 7, 4),
                        Block.box(6, 0, 12, 7, 7, 14),
                        Block.box(6, 4, 4, 7, 6, 12),
                        Block.box(6, 7, 2, 7, 16, 4),
                        Block.box(6, 7, 12, 7, 16, 14),
                        Block.box(6, 11, 4, 7, 13, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

                @Override
                public VoxelShape getShape(BlockState bs, BlockGetter bg, BlockPos bp, CollisionContext cc) {
                    switch(bs.getValue(DirectionalVerticalRedstonePole.FACING)){
                        case NORTH -> {
                            return NORTH;
                        }
                        case SOUTH -> {
                            return SOUTH;
                        }
                        case EAST -> {
                            return EAST;
                        }
                        case WEST -> {
                            return WEST;
                        }
                        default -> {
                            return Shapes.block();
                        }
                    }
                }
            });

    public static final DeferredBlock<Block> RAILROAD_CROSSING_CANTILEVER_END_LADDER = register("railroad_crossing_cantilever_end_ladder",
            () -> new RailroadCrossingCantileverEnd(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> RAILROAD_CROSSING_CANTILEVER_END_NO_POLE_LADDER = register("railroad_crossing_cantilever_end_no_pole_ladder",
            () -> new RailroadCrossingCantileverEnd(BlockBehaviour.Properties.of()){
                @Override
                public VoxelShape getCollisionShape(BlockState bs, BlockGetter bg, BlockPos bp, CollisionContext cc) {
                    switch(bs.getValue(FACING)){
                        case NORTH -> {
                            return NORTH_NO_POLE_END;
                        }
                        case SOUTH -> {
                            return SOUTH_NO_POLE_END;
                        }
                        case EAST -> {
                            return EAST_NO_POLE_END;
                        }
                        case WEST -> {
                            return WEST_NO_POLE_END;
                        }
                        default -> {
                            return Shapes.block();
                        }
                    }
                }

                @Override
                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                    if(context.isHoldingItem(this.asItem())){
                        return Shapes.block();
                    }
                    else{
                        switch(state.getValue(FACING)){
                            case NORTH -> {
                                return NORTH_NO_POLE_END;
                            }
                            case SOUTH -> {
                                return SOUTH_NO_POLE_END;
                            }
                            case EAST -> {
                                return EAST_NO_POLE_END;
                            }
                            case WEST -> {
                                return WEST_NO_POLE_END;
                            }
                            default -> {
                                return Shapes.block();
                            }
                        }
                    }
                }
            });

    public static final DeferredBlock<Block> RAILROAD_CROSSING_CANTILEVER_END_NO_POLE = register("railroad_crossing_cantilever_end_no_pole",
            () -> new RailroadCrossingCantileverEnd(BlockBehaviour.Properties.of()){
                @Override
                public VoxelShape getCollisionShape(BlockState bs, BlockGetter bg, BlockPos bp, CollisionContext cc) {
                    switch(bs.getValue(FACING)){
                        case NORTH -> {
                            return NORTH_NO_POLE_END;
                        }
                        case SOUTH -> {
                            return SOUTH_NO_POLE_END;
                        }
                        case EAST -> {
                            return EAST_NO_POLE_END;
                        }
                        case WEST -> {
                            return WEST_NO_POLE_END;
                        }
                        default -> {
                            return Shapes.block();
                        }
                    }
                }

                @Override
                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                    if(context.isHoldingItem(this.asItem())){
                        return Shapes.block();
                    }
                    else{
                        switch(state.getValue(FACING)){
                            case NORTH -> {
                                return NORTH_NO_POLE_END;
                            }
                            case SOUTH -> {
                                return SOUTH_NO_POLE_END;
                            }
                            case EAST -> {
                                return EAST_NO_POLE_END;
                            }
                            case WEST -> {
                                return WEST_NO_POLE_END;
                            }
                            default -> {
                                return Shapes.block();
                            }
                        }
                    }
                }
            });

    public static final DeferredBlock<Block> POLE_CAP = register("pole_cap",
            () -> new PoleCap(BlockBehaviour.Properties.of()));


    public static final DeferredBlock<Block> BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS = register("big_railroad_crossing_cantilever_lights",
            () -> new RailroadCrossingCantileverLights(BlockBehaviour.Properties.of()));


    public static final DeferredBlock<Block> BIG_RAILROAD_CROSSING_LIGHTS = register("big_railroad_crossing_lights",
            () -> new RailroadCrossingLights(BlockBehaviour.Properties.of().lightLevel(railroadLightsEmission(15))){
                @Override
                public RenderShape getRenderShape(BlockState bs) {
                    return RenderShape.ENTITYBLOCK_ANIMATED;
                }
            });

    public static final DeferredBlock<Block> CLICKY_MECHANICAL_BELL = register("clicky_mechanical_bell",
            () -> new RotatingBaseRailroadCrossingBell(BlockBehaviour.Properties.of(),11){
                public static final VoxelShape ALL = Shapes.join(
                        Block.box(7, 0, 7, 9, 14, 9),
                        Block.box(4, 7, 4, 12, 16, 12), BooleanOp.OR);
                @Override
                public boolean attemptPlaySound(Level lp, BlockPos bp) {
                    if (!lp.isClientSide){
                        lp.playSound(null, bp, TRRSound.CLICKY_MECHANICAL_BELL.get(), SoundSource.BLOCKS, 1.25F, 1.0F);
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                @Override
                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                    return ALL;
                }
            });

    public static final DeferredBlock<Block> CROSSBUCK_WITH_LADDER = register("crossbuck_with_ladder",
            () -> new RotatingCrossbuckBlock(BlockBehaviour.Properties.of()){
                @Override
                protected RenderShape getRenderShape(BlockState state) {
                    return RenderShape.MODEL;
                }
            });

    public static final DeferredBlock<Block> ELECTRONIC_BELL_TYPE_5 = register("railroad_crossing_ebell_type_five",
            () -> new RotatingBaseRailroadCrossingBell(BlockBehaviour.Properties.of(),12){
                @Override
                public boolean attemptPlaySound(Level lp, BlockPos bp) {
                    if (!lp.isClientSide){
                        lp.playSeededSound(null,bp.getX(),bp.getY(),bp.getZ(),
                                TRRSound.EBELL_FIVE.get(),SoundSource.BLOCKS,0.35f,1.0f,lp.random.nextLong());
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                public static final VoxelShape ALL = Stream.of(
                        Block.box(7, 0, 7, 9, 10, 9),
                        Block.box(5.5, 2, 5.5, 10.5, 4, 10.5),
                        Block.box(5.5, 6, 5.5, 10.5, 8, 10.5),
                        Block.box(5.5, 10, 5.5, 10.5, 12, 10.5),
                        Block.box(6.5, 4, 6.5, 9.5, 10, 9.5)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                @Override
                protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                    return ALL;
                }
            });

    public static final DeferredBlock<Block> VERTICAL_POLE_REDSTONE_RR = register("vertical_pole_redstone_rr",
            () -> new VerticalPoleRedstoneRR(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> POLE_WITH_CROSSING_STOP_LIGHT = register("pole_with_crossing_stop_light",
            () -> new PoleWithCrossingStopLight(BlockBehaviour.Properties.of()/*.lightLevel(poweredEmission(3))*/));

    public static final DeferredBlock<Block> CROSSING_COMPONENT_CONTROLLER = register("crossing_component_controller",
            () -> new CrossingComponentController(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> ELECTRONIC_BELL_TYPE_6 = register("railroad_crossing_ebell_type_six",
            () -> new RotatingBaseRailroadCrossingBell(BlockBehaviour.Properties.of(),true){
                @Override
                public boolean attemptPlaySound(Level lp, BlockPos bp) {
                    if (!lp.isClientSide){
                        lp.playSeededSound(null,bp.getX(),bp.getY(),bp.getZ(),
                                TRRSound.EBELL_SIX.get(), SoundSource.BLOCKS,0.4f,1.0f,lp.random.nextLong());
                        return true;
                    }
                    else {
                        return false;
                    }
                }

                public static final VoxelShape NORTH = Stream.of(
                        Block.box(6, 0, 6, 10, 2, 10),
                        Block.box(6, 14, 6, 10, 16, 10),
                        Block.box(7, 0, 10, 9, 16, 11),
                        Block.box(7, 2, 7, 9, 9, 9),
                        Block.box(7, 9, 7, 9, 9.75, 9)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

                public static final VoxelShape EAST = Stream.of(
                        Block.box(6, 0, 6, 10, 2, 10),
                        Block.box(6, 14, 6, 10, 16, 10),
                        Block.box(5, 0, 7, 6, 16, 9),
                        Block.box(7, 2, 7, 9, 9, 9),
                        Block.box(7, 9, 7, 9, 9.75, 9)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

                public static final VoxelShape SOUTH = Stream.of(
                        Block.box(6, 0, 6, 10, 2, 10),
                        Block.box(6, 14, 6, 10, 16, 10),
                        Block.box(7, 0, 5, 9, 16, 6),
                        Block.box(7, 2, 7, 9, 9, 9),
                        Block.box(7, 9, 7, 9, 9.75, 9)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

                public static final VoxelShape WEST = Stream.of(
                        Block.box(6, 0, 6, 10, 2, 10),
                        Block.box(6, 14, 6, 10, 16, 10),
                        Block.box(10, 0, 7, 11, 16, 9),
                        Block.box(7, 2, 7, 9, 9, 9),
                        Block.box(7, 9, 7, 9, 9.75, 9)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

                @Override
                public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                    switch(state.getValue(FACING)){
                        case NORTH -> {
                            return NORTH;
                        }
                        case SOUTH -> {
                            return SOUTH;
                        }
                        case EAST -> {
                            return EAST;
                        }
                        case WEST -> {
                            return WEST;
                        }
                        default -> {
                            return Shapes.block();
                        }
                    }
                }

                // this bell needs some more customization
                @Override
                public void tick(BlockState bs, ServerLevel slvl, BlockPos bp, RandomSource rs) {
                    if(!slvl.isClientSide){
                        if(bs.getValue(POWERED)){
                            boolean allverticalredstoneblocks = slvl.getBlockState(bp.below()).is(TRRBlockTag.VERTICAL_REDSTONE_BLOCKS);
                            boolean isCant = slvl.getBlockState(bp.below()).is(TRRBlockTag.RR_CANTILEVERS);

                            boolean both = allverticalredstoneblocks || isCant;

                            if(!both){
                                slvl.setBlock(bp,bs.setValue(POWERED,false),3);
                                return;
                            }
                            attemptPlaySound(slvl,bp);
                            slvl.scheduleTick(bp,bs.getBlock(),14,TickPriority.VERY_LOW);
                        }
                    }
                }
            });

    public static final DeferredBlock<Block> MULTIPURPOSE_SIGN = register("multipurpose_sign",
            () -> new MultipurposeSignBlock(BlockBehaviour.Properties.of()));

    //
    private static DeferredBlock<Block> register(String name, Supplier<Block> block) {
        DeferredBlock<Block> blk = BLOCKS.register(name,block);
        DeferredItem<Item> itm = TRRItems.ITEMS.register(name,() -> new BlockItem(blk.get(),new Item.Properties()));
        return blk;
    }

    private static DeferredBlock<Block> registerBlockWithoutItem(String name, Supplier<Block> block){
        return BLOCKS.register(name,block);
    }

    private static boolean always(BlockState bs, BlockGetter bg, BlockPos bp){return true;}
    private static boolean never(BlockState bs, BlockGetter bg, BlockPos bp){return false;}

    private static ToIntFunction<BlockState> customLitBlockEmission(int lv) {
        return (properties) -> {
            return properties.getValue(BlockStateProperties.LIT) ? lv : 0;
        };
    }

    private static ToIntFunction<BlockState> enabledLitBlockEmission(int i) {
        return (properties) -> {
            return properties.getValue(BlockStateProperties.ENABLED) ? i : 0;
        };
    }

    private static ToIntFunction<BlockState> poweredEmission(int i) {
        return (properties) -> {
            return properties.getValue(BlockStateProperties.POWERED) ? i : 0;
        };
    }

    private static ToIntFunction<BlockState> railroadLightsEmission(int i) {
        return (properties) -> {
            return properties.getValue(RailroadCrossingLights.POWERED) ? i : 0;
        };
    }

    private static ToIntFunction<BlockState> railwayCrossingEmission(int i) {
        return (properties) -> {
            return properties.getValue(BritRailwayLightsBlock.POWERED) ? i : 0;
        };
    }
}
