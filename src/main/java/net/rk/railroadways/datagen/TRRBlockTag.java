package net.rk.railroadways.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.thingamajigs.Thingamajigs;
import net.rk.thingamajigs.datagen.TTag;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TRRBlockTag extends BlockTagsProvider{
    public TRRBlockTag(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Thingamajigs.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider tc){
        this.tag(TTag.RAILROAD_CROSSING_BELLS)
                .add(TRRBlocks.ELECTRONIC_BELL_TYPE_1.get())
                .add(TRRBlocks.ELECTRONIC_BELL_TYPE_2.get())
                .add(TRRBlocks.MECHANICAL_BELL_TYPE_1.get())
                .add(TRRBlocks.MECHANICAL_BELL_TYPE_2.get())
                .add(TRRBlocks.BRITISH_RAILWAY_ALARM.get())
                .add(TRRBlocks.ELECTRONIC_BELL_TYPE_3.get())
                .add(TRRBlocks.ELECTRONIC_BELL_TYPE_4.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CLICKY_MECHANICAL_BELL.get())
                .add(TRRBlocks.ELECTRONIC_BELL_TYPE_5.get())
        ;

        this.tag(TTag.VERTICAL_REDSTONE_BLOCKS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
        ;

        this.tag(TTag.RR_CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_LADDER.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE_LADDER.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS.get())
        ;

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TTag.RAILROAD_CROSSING_BELLS)
                .add(TRRBlocks.PURPLE_RAIL.get())
                .add(TRRBlocks.PURPLE_POWERED_RAIL.get())
                .add(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get())
                .add(TRRBlocks.PURPLE_DETECTOR_RAIL.get())
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TTag.RR_CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
        ;

        this.tag(BlockTags.IMPERMEABLE)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TTag.RAILROAD_CROSSING_BELLS)
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TTag.RR_CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
        ;

        this.tag(BlockTags.WALL_POST_OVERRIDE)
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TTag.RAILROAD_CROSSING_BELLS)
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
        ;

        this.tag(BlockTags.PREVENT_MOB_SPAWNING_INSIDE)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TTag.RAILROAD_CROSSING_BELLS)
                .add(TRRBlocks.PURPLE_RAIL.get())
                .add(TRRBlocks.PURPLE_POWERED_RAIL.get())
                .add(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get())
                .add(TRRBlocks.PURPLE_DETECTOR_RAIL.get())
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TTag.RR_CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
        ;

        this.tag(BlockTags.INVALID_SPAWN_INSIDE)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TTag.RAILROAD_CROSSING_BELLS)
                .add(TRRBlocks.PURPLE_RAIL.get())
                .add(TRRBlocks.PURPLE_POWERED_RAIL.get())
                .add(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get())
                .add(TRRBlocks.PURPLE_DETECTOR_RAIL.get())
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TTag.RR_CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
        ;

        this.tag(Tags.Blocks.RELOCATION_NOT_SUPPORTED)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TTag.RAILROAD_CROSSING_BELLS)
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TTag.RR_CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
        ;

        this.tag(BlockTags.DRAGON_TRANSPARENT)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TTag.RAILROAD_CROSSING_BELLS)
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TTag.RR_CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
        ;

        this.tag(BlockTags.WITHER_IMMUNE)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TTag.RAILROAD_CROSSING_BELLS)
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TTag.RR_CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
        ;

        this.tag(BlockTags.RAILS)
                .add(TRRBlocks.PURPLE_RAIL.get())
                .add(TRRBlocks.PURPLE_POWERED_RAIL.get())
                .add(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get())
                .add(TRRBlocks.PURPLE_DETECTOR_RAIL.get())
        ;

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(TRRBlocks.PURPLE_RAIL.get())
                .add(TRRBlocks.PURPLE_POWERED_RAIL.get())
                .add(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get())
                .add(TRRBlocks.PURPLE_DETECTOR_RAIL.get())
        ;

        this.tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
                .add(TRRBlocks.PURPLE_RAIL.get())
                .add(TRRBlocks.PURPLE_POWERED_RAIL.get())
                .add(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get())
                .add(TRRBlocks.PURPLE_DETECTOR_RAIL.get())
        ;
        this.tag(BlockTags.CLIMBABLE)
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_LADDER.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE_LADDER.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
        ;
    }

    @Override
    public String getName() {
        return "Thingamajigs Addon RR Block Tags";
    }
}
