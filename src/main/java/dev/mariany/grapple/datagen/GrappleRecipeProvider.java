package dev.mariany.grapple.datagen;

import dev.mariany.grapple.item.GrappleItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class GrappleRecipeProvider extends FabricRecipeProvider {
    public GrappleRecipeProvider(FabricDataOutput output,
                                 CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter exporter) {
        return new RecipeGenerator(wrapperLookup, exporter) {
            @Override
            public void generate() {
                createShaped(RecipeCategory.FOOD, GrappleItems.GOLDEN_COOKIE).input('#', Items.GOLD_INGOT)
                        .input('X', Items.COOKIE).pattern("###").pattern("#X#").pattern("###")
                        .criterion("has_gold_ingot", this.conditionsFromItem(Items.GOLD_INGOT)).offerTo(this.exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "Grapple Recipes";
    }
}
