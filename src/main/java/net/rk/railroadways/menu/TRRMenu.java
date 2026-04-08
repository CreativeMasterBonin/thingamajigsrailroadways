package net.rk.railroadways.menu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.railroadways.Thingamajigsrailroadways;

public class TRRMenu{
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(
            BuiltInRegistries.MENU, Thingamajigsrailroadways.MODID);

    public static final DeferredHolder<MenuType<?>,MenuType<RailroadCrossingArmMenu>> RAILROAD_CROSSING_MENU =
            MENU_TYPES.register("railroad_crossing_menu", () ->
                    IMenuTypeExtension.create(RailroadCrossingArmMenu::new));

    public static final DeferredHolder<MenuType<?>,MenuType<DynamicSignMenu>> DYNAMIC_RR_SIGN_MENU =
            MENU_TYPES.register("dynamic_rr_sign_menu", () ->
                    IMenuTypeExtension.create(DynamicSignMenu::new));

    public static final DeferredHolder<MenuType<?>,MenuType<RailroadCrossingLightsMenu>> RR_LIGHTS_MENU =
            MENU_TYPES.register("rr_lights_menu", () ->
                    IMenuTypeExtension.create(RailroadCrossingLightsMenu::new));

    public static final DeferredHolder<MenuType<?>,MenuType<BritLightsMenu>> BRIT_LIGHTS_MENU =
            MENU_TYPES.register("brit_lights_menu", () ->
                    IMenuTypeExtension.create(BritLightsMenu::new));

    public static final DeferredHolder<MenuType<?>,MenuType<TriLightsMenu>> TRI_LIGHTS_MENU =
            MENU_TYPES.register("tri_lights_menu", () ->
                    IMenuTypeExtension.create(TriLightsMenu::new));

    public static final DeferredHolder<MenuType<?>,MenuType<DualLightsMenu>> DUAL_LIGHTS_MENU =
            MENU_TYPES.register("dual_lights_menu", () ->
                    IMenuTypeExtension.create(DualLightsMenu::new));

    public static final DeferredHolder<MenuType<?>,MenuType<RRCantLightsMenu>> RR_CANT_LIGHTS_MENU =
            MENU_TYPES.register("rr_cant_lights_menu", () ->
                    IMenuTypeExtension.create(RRCantLightsMenu::new));

    public static final DeferredHolder<MenuType<?>,MenuType<RailroadCrossingArmLightedMenu>> RAILROAD_CROSSING_LIGHTS_MENU =
            MENU_TYPES.register("railroad_crossing_lights_menu", () ->
                    IMenuTypeExtension.create(RailroadCrossingArmLightedMenu::new));

    public static final DeferredHolder<MenuType<?>,MenuType<PoleWithCrossingStopLightMenu>> POLE_WITH_CROSSING_STOP_LIGHT_MENU =
            MENU_TYPES.register("pole_with_crossing_stop_light_menu", () ->
                    IMenuTypeExtension.create(PoleWithCrossingStopLightMenu::new));

    public static void register(IEventBus eventBus){MENU_TYPES.register(eventBus);}
}
