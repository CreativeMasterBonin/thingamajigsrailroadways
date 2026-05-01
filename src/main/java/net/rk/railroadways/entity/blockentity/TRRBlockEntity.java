package net.rk.railroadways.entity.blockentity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.entity.blockentity.custom.*;

import java.util.function.Supplier;

public class TRRBlockEntity{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, Thingamajigsrailroadways.MODID);

    public static final Supplier<BlockEntityType<RailroadCrossingBE>> RAILROAD_CROSSING_ARM_BE = BLOCK_ENTITIES.register(
            "railroad_crossing_arm_be",() ->
                    BlockEntityType.Builder.of(RailroadCrossingBE::new, TRRBlocks.RAILROAD_CROSSING_ARM.get())
                            .build(null));

    public static final Supplier<BlockEntityType<DynamicSignBE>> DYNAMIC_SIGN_BE = BLOCK_ENTITIES.register(
            "dynamic_sign_be",() ->
                    BlockEntityType.Builder.of(DynamicSignBE::new,
                                    TRRBlocks.CROSSBUCK.get(),TRRBlocks.CROSSBUCK_WITH_LADDER.get())
                            .build(null));

    public static final Supplier<BlockEntityType<RailroadCrossingLightsBE>> RR_LIGHTS_BE = BLOCK_ENTITIES.register(
            "railroad_crossing_lights_be",() ->
                    BlockEntityType.Builder.of(RailroadCrossingLightsBE::new,
                                    TRRBlocks.RAILROAD_CROSSING_LIGHTS.get(),TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                            .build(null));

    public static final Supplier<BlockEntityType<BritRailwayLightsBE>> BRIT_RR_LIGHTS_BE = BLOCK_ENTITIES.register(
            "brit_railway_lights_be",() ->
                    BlockEntityType.Builder.of(BritRailwayLightsBE::new,TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                            .build(null));

    public static final Supplier<BlockEntityType<TriRailwayLightsBE>> TRI_RR_LIGHTS_BE = BLOCK_ENTITIES.register(
            "tri_railway_lights_be",() ->
                    BlockEntityType.Builder.of(TriRailwayLightsBE::new,TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                            .build(null));

    public static final Supplier<BlockEntityType<DualRailwayLightsBE>> DUAL_RR_LIGHTS_BE = BLOCK_ENTITIES.register(
            "dual_railway_lights_be",() ->
                    BlockEntityType.Builder.of(DualRailwayLightsBE::new,TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                            .build(null));

    public static final Supplier<BlockEntityType<RailroadCrossingCantLightsBE>> RR_CANTILEVER_LIGHTS_BE = BLOCK_ENTITIES.register(
            "railroad_crossing_cantilever_lights_be",() ->
                    BlockEntityType.Builder.of(RailroadCrossingCantLightsBE::new,
                                    TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.get(),TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS.get())
                            .build(null));

    public static final Supplier<BlockEntityType<RailroadCrossingArmWithLights>> RAILROAD_CROSSING_ARM_LIGHTED_BE = BLOCK_ENTITIES.register(
            "railroad_crossing_arm_lighted_be",() ->
                    BlockEntityType.Builder.of(RailroadCrossingArmWithLights::new,
                                    TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                            .build(null));

    public static final Supplier<BlockEntityType<PoleWithCrossingStopLightBE>> POLE_WITH_CROSSING_STOP_LIGHT_BE = BLOCK_ENTITIES.register(
            "pole_with_crossing_stop_light_be",() ->
                    BlockEntityType.Builder.of(PoleWithCrossingStopLightBE::new,TRRBlocks.POLE_WITH_CROSSING_STOP_LIGHT.get())
                            .build(null));

    public static final Supplier<BlockEntityType<CrossingComponentControllerBE>> CROSSING_COMPONENT_CONTROLLER_BE = BLOCK_ENTITIES.register(
            "crossing_component_controller_be",() ->
                    BlockEntityType.Builder.of(CrossingComponentControllerBE::new,TRRBlocks.CROSSING_COMPONENT_CONTROLLER.get())
                            .build(null));

    public static final Supplier<BlockEntityType<MultipurposeSignBE>> MULTIPURPOSE_SIGN_BE = BLOCK_ENTITIES.register(
            "multipurpose_sign_be",() ->
                    BlockEntityType.Builder.of(MultipurposeSignBE::new,TRRBlocks.MULTIPURPOSE_SIGN.get())
                            .build(null));

    public static final Supplier<BlockEntityType<EnhancedDirectionalCrossingLightBE>> ENHANCED_DIRECTIONAL_CROSSING_LIGHT_BE = BLOCK_ENTITIES.register(
            "enhanced_directional_crossing_light_be",() ->
                    BlockEntityType.Builder.of(EnhancedDirectionalCrossingLightBE::new,TRRBlocks.ENHANCED_DIRECTIONAL_CROSSING_LIGHT.get())
                            .build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
