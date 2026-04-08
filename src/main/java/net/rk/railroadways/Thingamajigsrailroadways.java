package net.rk.railroadways;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import net.rk.railroadways.item.TRRItems;
import net.rk.railroadways.menu.TRRMenu;
import net.rk.railroadways.network.TRRHandler;
import net.rk.railroadways.util.TRRSound;
import org.slf4j.Logger;

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
    public Thingamajigsrailroadways(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        modEventBus.addListener(TRRHandler::register);
        TRRSound.register(modEventBus);

        TRRBlocks.BLOCKS.register(modEventBus);
        TRRItems.ITEMS.register(modEventBus);
        TRRBlockEntity.register(modEventBus);
        CMT_TRR.register(modEventBus);
        TRRMenu.register(modEventBus);

        //modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    private void commonSetup(final FMLCommonSetupEvent event){}
}
