package net.rk.railroadways;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import net.rk.railroadways.item.TRRItems;
import net.rk.railroadways.menu.TRRMenu;
import net.rk.railroadways.network.TRRHandler;
import net.rk.railroadways.registry.MultipurposeSignType;
import net.rk.railroadways.registry.RegistryBootstrap;
import net.rk.railroadways.registry.TRRRegistries;
import net.rk.railroadways.util.TRRSound;
import org.slf4j.Logger;

import java.util.Set;

@Mod(Thingamajigsrailroadways.MODID)
public class Thingamajigsrailroadways{
    public static final String MODID = "thingamajigsrailroadways";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CMT_TRR = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TRR_TAB = CMT_TRR.register(
            "trr_main_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.thingamajigsrailroadways"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> TRRBlocks.PURPLE_RAIL.asItem().getDefaultInstance())
                    .build()
    );

    public static final DeferredRegister<DataComponentType<?>> COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE,Thingamajigsrailroadways.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> SELECTED_POSITION =
            COMPONENTS.register("selected_position",
                    () -> DataComponentType.<BlockPos>builder()
                            .persistent(BlockPos.CODEC).networkSynchronized(BlockPos.STREAM_CODEC).build());

    public Thingamajigsrailroadways(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        modEventBus.addListener(this::registerDatapackRegistries);
        modEventBus.addListener(this::onGatherData);

        modEventBus.addListener(TRRHandler::register);
        TRRSound.register(modEventBus);

        COMPONENTS.register(modEventBus);

        TRRBlocks.BLOCKS.register(modEventBus);
        TRRItems.ITEMS.register(modEventBus);
        TRRBlockEntity.register(modEventBus);
        CMT_TRR.register(modEventBus);
        TRRMenu.register(modEventBus);

        //modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    public void commonSetup(final FMLCommonSetupEvent event){}

    // datapack registries don't use streamcodecs
    @SubscribeEvent
    public void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event){
        event.dataPackRegistry(TRRRegistries.MULTIPURPOSE_SIGN_TYPE, MultipurposeSignType.CODEC,MultipurposeSignType.CODEC);
    }

    @SubscribeEvent
    public void onGatherData(GatherDataEvent event){
        PackOutput packOutput = event.getGenerator().getPackOutput();
        LogUtils.getLogger().debug("Railroadways data is being generated...");
        event.getGenerator().addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(
                        packOutput,
                        event.getLookupProvider(),
                        new RegistrySetBuilder()
                                .add(TRRRegistries.MULTIPURPOSE_SIGN_TYPE,RegistryBootstrap::bootstrap
                                ), Set.of(Thingamajigsrailroadways.MODID)));
    }
}
