package dev.mariany.grapple.datagen;

import dev.mariany.grapple.enchantment.GrappleEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class GrappleEnchantmentTagProvider extends FabricTagProvider.EnchantmentTagProvider {
    public GrappleEnchantmentTagProvider(FabricDataOutput output,
                                         CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(EnchantmentTags.TREASURE).add(GrappleEnchantments.GRAPPLE).add(GrappleEnchantments.AER_HOOK);
    }
}
