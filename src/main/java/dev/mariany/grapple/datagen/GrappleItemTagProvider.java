package dev.mariany.grapple.datagen;

import dev.mariany.grapple.item.GrappleItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class GrappleItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public GrappleItemTagProvider(FabricDataOutput output,
                                  CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ConventionalItemTags.COOKIE_FOODS).add(GrappleItems.GOLDEN_COOKIE);
    }
}
