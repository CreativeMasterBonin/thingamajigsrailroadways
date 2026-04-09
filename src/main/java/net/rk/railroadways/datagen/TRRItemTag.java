package net.rk.railroadways.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.item.TRRItems;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TRRItemTag extends ItemTagsProvider{
    public TRRItemTag(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> tagFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, future, tagFuture, Thingamajigsrailroadways.MODID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider prvdr) {
        this.tag(TRRTag.RAILROAD_COMPONENT_ITEMS)
                .add(Items.POWERED_RAIL)
                .add(Items.ACTIVATOR_RAIL)
                .add(Items.DETECTOR_RAIL)
                .add(Items.TNT_MINECART)
                .add(Items.FURNACE_MINECART)
                .add(Items.HOPPER_MINECART)
        ;
        this.tag(ItemTags.VANISHING_ENCHANTABLE)
                .add(TRRItems.COMPONENT_LINKER.asItem())
        ;
        this.tag(Tags.Items.TOOLS)
                .add(TRRItems.COMPONENT_LINKER.asItem())
        ;
    }
}
