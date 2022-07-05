package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.init.Elements;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider {
    public ModRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer)
    {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Elements.RAW_VERITE_CHUNK.get()), Elements.VERITE_INGOT.get(), 1.0f, 100)
                .unlockedBy("has_ore", has(Elements.RAW_VERITE_CHUNK.get()))
                .save(consumer, "verite_ingot");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Elements.RAW_MYSTERIOUS_CHUNK.get()), Elements.MYSTERIOUS_INGOT.get(), 1.0f, 100)
                .unlockedBy("has_ore", has(Elements.RAW_MYSTERIOUS_CHUNK.get()))
                .save(consumer, "mysterious_ingot");

        ShapedRecipeBuilder.shaped(Elements.POWER_GENERATOR.get())
                .pattern("mmm")
                .pattern("x#x")
                .pattern("#x#")
                .define('x', Tags.Items.DUSTS_REDSTONE)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('m', Ingredient.of(Elements.VERITE_INGOT.get(), Elements.MYSTERIOUS_INGOT.get()))
                .group("mcjtylearning")
                .unlockedBy("compatible_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Elements.MYSTERIOUS_INGOT.get(), Elements.VERITE_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(Elements.GENERATOR.get())
                .pattern("mxm")
                .pattern("x#x")
                .pattern("#x#")
                .define('x', Tags.Items.GEMS_DIAMOND)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('m', Ingredient.of(Elements.VERITE_INGOT.get(), Elements.MYSTERIOUS_INGOT.get()))
                .group("mcjtylearning")
                .unlockedBy("compatible_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Elements.MYSTERIOUS_INGOT.get(), Elements.VERITE_INGOT.get()))
                .save(consumer);
    }
}
