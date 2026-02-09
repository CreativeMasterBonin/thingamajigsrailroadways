package net.rk.railroadways.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.block.TRRBlocks;

public class TRRItemModel extends ItemModelProvider{
    public TRRItemModel(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Thingamajigsrailroadways.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //defaultCustomSimpleItem(TRRBlocks.CROSSBUCK.get(),"crossbuck");
        //defaultCustomSimpleItem(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get(),"rr_lights");
        defaultCustomSimpleItemOldBlock(TRRBlocks.PURPLE_RAIL.get(),"rails/purple");
        defaultCustomSimpleItemOldBlock(TRRBlocks.PURPLE_POWERED_RAIL.get(),"rails/purple_powered_off");
        defaultCustomSimpleItemOldBlock(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get(),"rails/purple_activator_off");
        defaultCustomSimpleItemOldBlock(TRRBlocks.PURPLE_DETECTOR_RAIL.get(),"rails/purple_detector_off");
        //defaultCustomSimpleItem(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get(),"british_lights");
        //defaultCustomSimpleItem(TRRBlocks.DUAL_RAILWAY_LIGHTS.get(),"dual_lights");
        //defaultCustomSimpleItem(TRRBlocks.TRI_RAILWAY_LIGHTS.get(),"tri_lights");
        //defaultCustomSimpleItem(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.get(),"rr_cantilever_lights");
        //defaultCustomSimpleItem(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get(),"crossing_arm_with_lights");
        fromModelMod(TRRBlocks.RR_LADDER_POLE.get(),"block/pole_rr_ladder");
        fromModelMod(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_LADDER.get(),"block/rr_cantilever_end_ladder");
        fromModelMod(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE.get(),"block/rr_cantilever_end_no_pole");
        fromModelMod(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE_LADDER.get(),"block/rr_cantilever_end_no_pole_ladder");
        fromModelMod(TRRBlocks.POLE_CAP.get(),"block/pole_cap");
        //defaultCustomSimpleItem(TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS.get(),"big_rr_cantilever_lights");
        //defaultCustomSimpleItem(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get(),"big_rr_lights");
        fromModelMod(TRRBlocks.CLICKY_MECHANICAL_BELL.get(),"block/clicky_mechanical_bell");
    }

    private ItemModelBuilder fromModelMod(Block block2, String source){
        return withExistingParent(block2.asItem().toString(),
                ResourceLocation.fromNamespaceAndPath("thingamajigsrailroadways",source));
    }

    private ItemModelBuilder defaultCustomSimpleItem(Block block1, String source){
        return withExistingParent(block1.asItem().toString(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath("thingamajigsrailroadways","item/" + source));
    }

    private ItemModelBuilder defaultCustomSimpleItemOldBlock(Block block1, String source){
        return withExistingParent(block1.asItem().toString(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath("thingamajigs","block/" + source));
    }

    private ItemModelBuilder blockAll(Item item, String textureLocation){
        return withExistingParent(item.toString(),
                ResourceLocation.withDefaultNamespace("block/cube_all")).texture("all",
                ResourceLocation.parse(textureLocation));
    }
}
