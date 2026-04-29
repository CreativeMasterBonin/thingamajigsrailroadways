package net.rk.railroadways.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.railroadways.Thingamajigsrailroadways;

@SuppressWarnings("deprecated")
public class TRRItems{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thingamajigsrailroadways.MODID);

    public static final DeferredItem<Item> COMPONENT_LINKER = ITEMS.register("component_linker",
            () -> new ComponentLinker(new Item.Properties()));

    public static final DeferredItem<Item> COMPONENT_BREAKER = ITEMS.register("component_breaker",
            () -> new ComponentBreaker(new Item.Properties()));
}
