package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Registration;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider {
    public ModRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer)
    {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registration.RAW_VERITE_CHUNK.get()), Registration.VERITE_INGOT.get(), 1.0f, 100)
                .unlockedBy("has_ore", has(Registration.RAW_VERITE_CHUNK.get()))
                .save(consumer, "verite_ingot");

        ShapedRecipeBuilder.shaped(Registration.POWER_GENERATOR.get())
                .pattern("mmm")
                .pattern("x#x")
                .pattern("#x#")
                .define('x', Tags.Items.DUSTS_REDSTONE)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('m', Registration.VERITE_INGOT.get())
                .group("mcjtylearning")
                .unlockedBy("verite", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.VERITE_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(Registration.GENERATOR.get())
                .pattern("mxm")
                .pattern("x#x")
                .pattern("#x#")
                .define('x', Tags.Items.GEMS_DIAMOND)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('m', Registration.VERITE_INGOT.get())
                .group("mcjtylearning")
                .unlockedBy("verite", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.VERITE_INGOT.get()))
                .save(consumer);
    }
}
