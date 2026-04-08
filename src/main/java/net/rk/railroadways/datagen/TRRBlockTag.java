package net.rk.railroadways.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.block.TRRBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TRRBlockTag extends BlockTagsProvider{
    public TRRBlockTag(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Thingamajigsrailroadways.MODID, existingFileHelper);
    }

    public static final TagKey<Block> RAILROAD_CROSSING_BELLS = TagKey.create(Registries.BLOCK, ResourceLocation.parse("thingamajigs:railroad_crossing_bells"));
    public static final TagKey<Block> VERTICAL_REDSTONE_BLOCKS = TagKey.create(Registries.BLOCK, ResourceLocation.parse("thingamajigs:vertical_redstone_blocks"));
    public static final TagKey<Block> RR_CANTILEVERS = TagKey.create(Registries.BLOCK, ResourceLocation.parse("thingamajigs:rr_cantilevers"));

    @Override
    public void addTags(HolderLookup.Provider tc){
        this.tag(RAILROAD_CROSSING_BELLS)
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

        this.tag(VERTICAL_REDSTONE_BLOCKS)
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
                .add(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get())
                .add(TRRBlocks.POLE_WITH_CROSSING_STOP_LIGHT.get())
        ;

        this.tag(RR_CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_LADDER.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE_LADDER.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS.get())
        ;

        this.tag(TRRTag.CROSSING_BELLS)
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

        this.tag(TRRTag.VERTICAL_REDSTONE_COMPATIBLE)
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
                .add(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get())
                .add(TRRBlocks.POLE_WITH_CROSSING_STOP_LIGHT.get())
        ;

        this.tag(TRRTag.CANTILEVERS)
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
                .addTag(TRRTag.CROSSING_BELLS)
                .add(TRRBlocks.PURPLE_RAIL.get())
                .add(TRRBlocks.PURPLE_POWERED_RAIL.get())
                .add(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get())
                .add(TRRBlocks.PURPLE_DETECTOR_RAIL.get())
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
                .add(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get())
        ;

        this.tag(BlockTags.IMPERMEABLE)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CROSSING_BELLS)
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
                .add(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get())
        ;

        this.tag(BlockTags.WALL_POST_OVERRIDE)
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CROSSING_BELLS)
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
                .add(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get())
        ;

        this.tag(BlockTags.PREVENT_MOB_SPAWNING_INSIDE)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CROSSING_BELLS)
                .add(TRRBlocks.PURPLE_RAIL.get())
                .add(TRRBlocks.PURPLE_POWERED_RAIL.get())
                .add(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get())
                .add(TRRBlocks.PURPLE_DETECTOR_RAIL.get())
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
                .add(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get())
        ;

        this.tag(BlockTags.INVALID_SPAWN_INSIDE)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CROSSING_BELLS)
                .add(TRRBlocks.PURPLE_RAIL.get())
                .add(TRRBlocks.PURPLE_POWERED_RAIL.get())
                .add(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get())
                .add(TRRBlocks.PURPLE_DETECTOR_RAIL.get())
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
                .add(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get())
        ;

        this.tag(Tags.Blocks.RELOCATION_NOT_SUPPORTED)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CROSSING_BELLS)
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
                .add(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get())
        ;

        this.tag(BlockTags.DRAGON_TRANSPARENT)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CROSSING_BELLS)
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
                .add(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get())
        ;

        this.tag(BlockTags.WITHER_IMMUNE)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM.get())
                .add(TRRBlocks.CROSSBUCK.get())
                .add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get())
                .add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CROSSING_BELLS)
                .add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get())
                .add(TRRBlocks.TRI_RAILWAY_LIGHTS.get())
                .addTag(TRRTag.CANTILEVERS)
                .add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get())
                .add(TRRBlocks.RR_LADDER_POLE.get())
                .add(TRRBlocks.POLE_CAP.get())
                .add(TRRBlocks.CROSSBUCK_WITH_LADDER.get())
                .add(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get())
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
                .add(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get())
        ;
    }

    @Override
    public String getName() {
        return "Thingamajigs Addon RR Block Tags";
    }
}
