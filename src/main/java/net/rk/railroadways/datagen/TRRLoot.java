package net.rk.railroadways.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.rk.railroadways.block.TRRBlocks;

import java.util.ArrayList;
import java.util.List;

public class TRRLoot extends VanillaBlockLoot{
    public TRRLoot(HolderLookup.Provider p){super(p);}

    @Override
    protected void generate() {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        this.dropSelf(TRRBlocks.RAILROAD_CROSSING_ARM.get());
        this.dropSelf(TRRBlocks.CROSSBUCK.get());
        this.dropSelf(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get());
        this.dropSelf(TRRBlocks.ELECTRONIC_BELL_TYPE_1.get());
        this.dropSelf(TRRBlocks.ELECTRONIC_BELL_TYPE_2.get());
        this.dropSelf(TRRBlocks.MECHANICAL_BELL_TYPE_1.get());
        this.dropSelf(TRRBlocks.MECHANICAL_BELL_TYPE_2.get());
        this.dropSelf(TRRBlocks.PURPLE_RAIL.get());
        this.dropSelf(TRRBlocks.PURPLE_POWERED_RAIL.get());
        this.dropSelf(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get());
        this.dropSelf(TRRBlocks.PURPLE_DETECTOR_RAIL.get());
        this.dropSelf(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get());
        this.dropSelf(TRRBlocks.BRITISH_RAILWAY_ALARM.get());
        this.dropSelf(TRRBlocks.DUAL_RAILWAY_LIGHTS.get());
        this.dropSelf(TRRBlocks.TRI_RAILWAY_LIGHTS.get());
        this.dropSelf(TRRBlocks.ELECTRONIC_BELL_TYPE_3.get());
        this.dropSelf(TRRBlocks.ELECTRONIC_BELL_TYPE_4.get());
        this.dropSelf(TRRBlocks.RAILROAD_CROSSING_CANTILEVER.get());
        this.dropSelf(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END.get());
        this.dropSelf(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.get());
        this.dropSelf(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get());
        this.dropSelf(TRRBlocks.RR_LADDER_POLE.get());
        this.dropSelf(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_LADDER.get());
        this.dropSelf(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE.get());
        this.dropSelf(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE_LADDER.get());
        this.dropSelf(TRRBlocks.POLE_CAP.get());
        this.dropSelf(TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS.get());
        this.dropSelf(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get());
        this.dropSelf(TRRBlocks.CLICKY_MECHANICAL_BELL.get());
        this.dropSelf(TRRBlocks.CROSSBUCK_WITH_LADDER.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> knownBlocks = new ArrayList<>();
        knownBlocks.addAll(TRRBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).toList());
        return knownBlocks;
    }
}
