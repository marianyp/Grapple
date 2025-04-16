package dev.mariany.grapple;

import dev.mariany.grapple.datagen.*;
import dev.mariany.grapple.enchantment.GrappleEnchantments;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class GrappleDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(GrappleEnchantGenerator::new);
        pack.addProvider(GrappleEnchantmentTagProvider::new);
        pack.addProvider(GrappleItemTagProvider::new);
        pack.addProvider(GrappleModelProvider::new);
        pack.addProvider(GrappleRecipeProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, GrappleEnchantments::bootstrap);
    }
}
