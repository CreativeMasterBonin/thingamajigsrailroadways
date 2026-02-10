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
    public void registerModels() {
        defaultCustomSimpleItemOldBlock(TRRBlocks.PURPLE_RAIL.get(),"rails/purple");
        defaultCustomSimpleItemOldBlock(TRRBlocks.PURPLE_POWERED_RAIL.get(),"rails/purple_powered_off");
        defaultCustomSimpleItemOldBlock(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get(),"rails/purple_activator_off");
        defaultCustomSimpleItemOldBlock(TRRBlocks.PURPLE_DETECTOR_RAIL.get(),"rails/purple_detector_off");
        fromModelMod(TRRBlocks.RR_LADDER_POLE.get(),"block/pole_rr_ladder");
        fromModelMod(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_LADDER.get(),"block/rr_cantilever_end_ladder");
        fromModelMod(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE.get(),"block/rr_cantilever_end_no_pole");
        fromModelMod(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE_LADDER.get(),"block/rr_cantilever_end_no_pole_ladder");
        fromModelMod(TRRBlocks.POLE_CAP.get(),"block/pole_cap");
        fromModelMod(TRRBlocks.CLICKY_MECHANICAL_BELL.get(),"block/clicky_mechanical_bell");
        fromModelMod(TRRBlocks.ELECTRONIC_BELL_TYPE_5.get(),"block/railroad_crossing_ebell_type_five");
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
