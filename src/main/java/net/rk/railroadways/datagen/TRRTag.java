package net.rk.railroadways.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings("deprecated")
public class TRRTag{
    public static final TagKey<Item> RAILROAD_COMPONENT_ITEMS = thingamajigsRRItemTag("railroad_component_items");
    public static final TagKey<Item> CANTILEVER_HELD_ITEMS = thingamajigsRRItemTag("cantilever_held_items");

    public static final TagKey<Block> CANTILEVERS = thingamajigsRRBlockTag("cantilevers");
    public static final TagKey<Block> VERTICAL_REDSTONE_COMPATIBLE = thingamajigsRRBlockTag("vertical_redstone_compatible");
    public static final TagKey<Block> CROSSING_BELLS = thingamajigsRRBlockTag("crossing_bells");


    private static TagKey<Block> thingamajigsRRBlockTag(String name){
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("thingamajigsrailroadways", name));
    }

    private static TagKey<Item> thingamajigsRRItemTag(String name){
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("thingamajigsrailroadways", name));
    }

    // default tag registry

    private static TagKey<Block> mcBlockTag(String name){
        return BlockTags.create(ResourceLocation.withDefaultNamespace(name));
    }

    private static TagKey<Item> mcItemTag(String name){
        return ItemTags.create(ResourceLocation.withDefaultNamespace(name));
    }

    private static TagKey<Fluid> mcFluidTag(String name){
        return FluidTags.create(ResourceLocation.withDefaultNamespace(name));
    }

    // common tag registry

    private static TagKey<Fluid> commonFluidTag(String name){
        return FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }

    private static TagKey<Block> commonBlockTag(String name){
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }

    private static TagKey<Item> commonItemTag(String name){
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }
}

