package net.rk.railroadways.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MinecartItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.entity.TRREntities;
import net.rk.railroadways.entity.custom.AbstractLocomotive;

@SuppressWarnings("deprecated")
public class TRRItems{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thingamajigsrailroadways.MODID);

    public static final DeferredItem<Item> COMPONENT_LINKER = ITEMS.register("component_linker",
            () -> new ComponentLinker(new Item.Properties()));

    public static final DeferredItem<Item> COMPONENT_BREAKER = ITEMS.register("component_breaker",
            () -> new ComponentBreaker(new Item.Properties()));

    public static final DeferredItem<Item> DIESEL_LOCOMOTIVE_PLACER = ITEMS.register("diesel_locomotive_placer",
            () -> new LocomotivePlacer(AbstractLocomotive.LocomotiveType.DIESEL,new Item.Properties()));
}
