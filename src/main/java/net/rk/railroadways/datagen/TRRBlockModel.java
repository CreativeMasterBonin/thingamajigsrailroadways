package net.rk.railroadways.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.rk.railroadways.Thingamajigsrailroadways;

public class TRRBlockModel extends BlockModelProvider {
    public TRRBlockModel(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Thingamajigsrailroadways.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    private BlockModelBuilder allSidedBlock(DeferredBlock<Block> block, String textureLocation){
        return withExistingParent(block.getId().getPath(),
                ResourceLocation.parse("minecraft:block/cube_all"))
                .texture("all", ResourceLocation.parse(textureLocation));
    }
}
