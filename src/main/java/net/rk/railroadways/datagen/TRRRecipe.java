package net.rk.railroadways.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.thingamajigs.block.TBlocks;
import net.rk.thingamajigs.item.TItems;

import java.util.concurrent.CompletableFuture;

public class TRRRecipe extends RecipeProvider{
    public TRRRecipe(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput rc) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.BRITISH_RAILWAY_ALARM.asItem(),2)
                .requires(Ingredient.of(TItems.RAILROAD_COMPONENT))
                .requires(Ingredient.of(Items.RED_CONCRETE))
                .requires(Ingredient.of(Items.REDSTONE))
                .unlockedBy("has_thingy",has(TItems.RAILROAD_COMPONENT)).group("railroad_crossing_components")
                .save(rc);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.BRITISH_RAILWAY_LIGHTS.asItem(),2)
                .requires(Ingredient.of(TItems.RAILROAD_COMPONENT))
                .requires(Ingredient.of(Items.RED_DYE))
                .requires(Ingredient.of(Items.WHITE_DYE))
                .requires(Ingredient.of(Items.GLOWSTONE_DUST))
                .unlockedBy("has_thingy",has(TItems.RAILROAD_COMPONENT)).group("railroad_crossing_components")
                .save(rc);
        stonecutterAny(TRRBlocks.RAILROAD_CROSSING_ARM.asItem(),
                Ingredient.of(TItems.RAILROAD_COMPONENT),
                TRRBlocks.RAILROAD_CROSSING_ARM.asItem(),1)
                .save(rc);
        stonecutterAny(TRRBlocks.RAILROAD_CROSSING_LIGHTS.asItem(),
                Ingredient.of(TItems.RAILROAD_COMPONENT),
                TRRBlocks.RAILROAD_CROSSING_LIGHTS.asItem(),1)
                .save(rc);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,TRRBlocks.CROSSBUCK.asItem(),1)
                .define('#',Items.IRON_INGOT)
                .define('p',TBlocks.VERTICAL_POLE_REDSTONE.asItem())
                .define('r',TItems.RAILROAD_COMPONENT)
                .pattern("# #")
                .pattern(" r ")
                .pattern("#p#")
                .unlockedBy("has_thingy",has(TItems.RAILROAD_COMPONENT))
                .save(rc);

        stonecutterAny(TRRBlocks.DUAL_RAILWAY_LIGHTS.asItem(),
                Ingredient.of(TRRBlocks.RAILROAD_CROSSING_LIGHTS.asItem()),
                TRRBlocks.DUAL_RAILWAY_LIGHTS.asItem(),1)
                .save(rc);
        stonecutterAny(TRRBlocks.TRI_RAILWAY_LIGHTS.asItem(),
                Ingredient.of(TRRBlocks.RAILROAD_CROSSING_LIGHTS.asItem()),
                TRRBlocks.TRI_RAILWAY_LIGHTS.asItem(),1)
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.MECHANICAL_BELL_TYPE_1.asItem(),2)
                .requires(Ingredient.of(TItems.RAILROAD_COMPONENT))
                .requires(Ingredient.of(Items.IRON_INGOT))
                .requires(Ingredient.of(Items.BUCKET))
                .unlockedBy("has_thingy",has(TItems.RAILROAD_COMPONENT)).group("railroad_crossing_components")
                .save(rc);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.MECHANICAL_BELL_TYPE_2.asItem(),2)
                .requires(Ingredient.of(TItems.RAILROAD_COMPONENT))
                .requires(Ingredient.of(Items.IRON_INGOT))
                .requires(Ingredient.of(Items.FLINT))
                .unlockedBy("has_thingy",has(TItems.RAILROAD_COMPONENT)).group("railroad_crossing_components")
                .save(rc);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.ELECTRONIC_BELL_TYPE_1.asItem(),2)
                .requires(Ingredient.of(TItems.RAILROAD_COMPONENT))
                .requires(Ingredient.of(Items.IRON_INGOT))
                .requires(Ingredient.of(TBlocks.SCREEN.asItem()))
                .unlockedBy("has_thingy",has(TItems.RAILROAD_COMPONENT)).group("railroad_crossing_components")
                .save(rc);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.ELECTRONIC_BELL_TYPE_2.asItem(),2)
                .requires(Ingredient.of(TItems.RAILROAD_COMPONENT))
                .requires(Ingredient.of(Items.IRON_INGOT))
                .requires(Ingredient.of(TBlocks.CIRCUITS.asItem()))
                .unlockedBy("has_thingy",has(TItems.RAILROAD_COMPONENT)).group("railroad_crossing_components")
                .save(rc);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.ELECTRONIC_BELL_TYPE_3.asItem(),2)
                .requires(Ingredient.of(TItems.RAILROAD_COMPONENT))
                .requires(Ingredient.of(Items.IRON_INGOT))
                .requires(Ingredient.of(Items.STICK))
                .unlockedBy("has_thingy",has(TItems.RAILROAD_COMPONENT)).group("railroad_crossing_components")
                .save(rc);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.ELECTRONIC_BELL_TYPE_4.asItem(),2)
                .requires(Ingredient.of(TItems.RAILROAD_COMPONENT))
                .requires(Ingredient.of(Items.IRON_INGOT))
                .requires(Ingredient.of(ItemTags.TRAPDOORS))
                .unlockedBy("has_thingy",has(TItems.RAILROAD_COMPONENT)).group("railroad_crossing_components")
                .save(rc);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,TRRBlocks.PURPLE_RAIL.asItem(),16)
                .define('#',Items.BREEZE_ROD)
                .define('i',Items.IRON_INGOT)
                .pattern("i i")
                .pattern("i#i")
                .pattern("i i")
                .unlockedBy("has_thingy",has(Items.BREEZE_ROD))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,TRRBlocks.PURPLE_POWERED_RAIL.asItem(),6)
                .define('#',Items.BREEZE_ROD)
                .define('i',Items.IRON_INGOT)
                .define('r',Items.REDSTONE)
                .define('g',Items.GOLD_INGOT)
                .pattern("igi")
                .pattern("i#i")
                .pattern("iri")
                .unlockedBy("has_thingy",has(Items.BREEZE_ROD))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,TRRBlocks.PURPLE_DETECTOR_RAIL.asItem(),6)
                .define('#',Items.BREEZE_ROD)
                .define('i',Items.IRON_INGOT)
                .define('r',Items.REDSTONE)
                .define('p',Items.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .pattern("ipi")
                .pattern("i#i")
                .pattern("iri")
                .unlockedBy("has_thingy",has(Items.BREEZE_ROD))
                .save(rc);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,TRRBlocks.PURPLE_ACTIVATOR_RAIL.asItem(),6)
                .define('#',Items.BREEZE_ROD)
                .define('i',Items.IRON_INGOT)
                .define('d',Items.DISPENSER)
                .define('r',Items.REDSTONE)
                .define('t',Items.REDSTONE_TORCH)
                .pattern("iti")
                .pattern("d#d")
                .pattern("iri")
                .unlockedBy("has_thingy",has(Items.BREEZE_ROD))
                .save(rc);

        stonecutterAny(TRRBlocks.RAILROAD_CROSSING_CANTILEVER.asItem(),
                Ingredient.of(TItems.RAILROAD_COMPONENT),
                TRRBlocks.RAILROAD_CROSSING_CANTILEVER.asItem(),1)
                .save(rc);
        stonecutterAny(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END.asItem(),
                Ingredient.of(TItems.RAILROAD_COMPONENT),
                TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END.asItem(),1)
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.asItem(),1)
                .requires(Ingredient.of(TRRBlocks.RAILROAD_CROSSING_CANTILEVER.asItem()))
                .requires(Ingredient.of(TRRBlocks.RAILROAD_CROSSING_LIGHTS.asItem()))
                .unlockedBy("has_thingy",has(TRRBlocks.RAILROAD_CROSSING_CANTILEVER.asItem())).group("railroad_crossing_components")
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.asItem(),1)
                .requires(Ingredient.of(TRRBlocks.RAILROAD_CROSSING_ARM.asItem()))
                .requires(Ingredient.of(Items.REDSTONE_TORCH))
                .requires(Ingredient.of(Items.REDSTONE_TORCH))
                .requires(Ingredient.of(Items.REDSTONE_TORCH))
                .requires(Ingredient.of(Items.IRON_INGOT))
                .unlockedBy("has_thingy",has(TRRBlocks.RAILROAD_CROSSING_ARM.asItem()))
                .save(rc);

        stonecutterAny(TRRBlocks.RR_LADDER_POLE.asItem(),
                Ingredient.of(TBlocks.VERTICAL_POLE_REDSTONE.asItem()),
                TRRBlocks.RR_LADDER_POLE.asItem(),1)
                .save(rc);

        stonecutterAny(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_LADDER.asItem(),
                Ingredient.of(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END.asItem()),
                TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_LADDER.asItem(),1)
                .save(rc);

        stonecutterAny(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE.asItem(),
                Ingredient.of(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END.asItem()),
                TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE.asItem(),1)
                .save(rc);

        stonecutterAny(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE_LADDER.asItem(),
                Ingredient.of(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END.asItem()),
                TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE_LADDER.asItem(),1)
                .save(rc);

        stonecutterAny(TRRBlocks.POLE_CAP.asItem(),
                Ingredient.of(TBlocks.STRAIGHT_POLE.asItem()),
                TRRBlocks.POLE_CAP.asItem(),1)
                .save(rc);

        stonecutterAny(TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS.asItem(),
                Ingredient.of(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.asItem()),
                TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS.asItem(),1)
                .save(rc);

        stonecutterAny(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.asItem(),
                Ingredient.of(TRRBlocks.RAILROAD_CROSSING_LIGHTS.asItem()),
                TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.asItem(),1)
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.CLICKY_MECHANICAL_BELL.asItem(),2)
                .requires(Ingredient.of(TItems.RAILROAD_COMPONENT))
                .requires(Ingredient.of(Items.IRON_INGOT))
                .requires(Ingredient.of(TItems.CIRCLE_SIGN_GLOB))
                .requires(Ingredient.of(Items.COMPARATOR))
                .unlockedBy("has_thingy",has(TItems.RAILROAD_COMPONENT)).group("railroad_crossing_components")
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.CROSSBUCK_WITH_LADDER.asItem(),1)
                .requires(Ingredient.of(TRRBlocks.CROSSBUCK.asItem()))
                .requires(Ingredient.of(Items.IRON_INGOT))
                .requires(Ingredient.of(Items.LADDER))
                .unlockedBy("has_thingy",has(TItems.RAILROAD_COMPONENT)).group("railroad_crossing_components")
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TRRBlocks.ELECTRONIC_BELL_TYPE_5.asItem(),2)
                .requires(Ingredient.of(TItems.RAILROAD_COMPONENT))
                .requires(Ingredient.of(Items.IRON_INGOT))
                .requires(Ingredient.of(ItemTags.FISHES))
                .unlockedBy("has_thingy",has(TItems.RAILROAD_COMPONENT)).group("railroad_crossing_components")
                .save(rc);
    }

    public static RecipeBuilder stonecutterAny(Item requiredItem, Ingredient inputItem, Item result, int amt) {
        return SingleItemRecipeBuilder.stonecutting(inputItem,RecipeCategory.MISC,result,amt)
                .unlockedBy("has_thingy",inventoryTrigger(ItemPredicate.Builder.item().of(requiredItem)));
    }
}

