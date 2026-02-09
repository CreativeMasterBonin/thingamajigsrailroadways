package net.rk.railroadways.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.thingamajigs.Thingamajigs;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TRRItemTag extends ItemTagsProvider{
    public TRRItemTag(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Thingamajigs.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider prvdr) {

    }
}
