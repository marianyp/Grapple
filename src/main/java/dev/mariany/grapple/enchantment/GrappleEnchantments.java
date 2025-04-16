package dev.mariany.grapple.enchantment;

import dev.mariany.grapple.Grapple;
import dev.mariany.grapple.GrappleConstants;
import dev.mariany.grapple.component.GrappleEnchantmentEffectComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.ItemTags;

import java.util.List;

public class GrappleEnchantments {
    public static final RegistryKey<Enchantment> GRAPPLE = of("grapple");
    public static final RegistryKey<Enchantment> AER_HOOK = of("aer_hook");

    public static void bootstrap(Registerable<Enchantment> registry) {
        RegistryEntryLookup<Item> itemRegistryEntryLookup = registry.getRegistryLookup(RegistryKeys.ITEM);

        register(registry, AER_HOOK, Enchantment.builder(
                        Enchantment.definition(itemRegistryEntryLookup.getOrThrow(ItemTags.FISHING_ENCHANTABLE), 1, 1,
                                Enchantment.constantCost(25), Enchantment.constantCost(50), 4))
                .addEffect(GrappleEnchantmentEffectComponentTypes.ALLOW_GRAPPLE_NON_SOLID));

        register(registry, GRAPPLE, Enchantment.builder(
                        Enchantment.definition(itemRegistryEntryLookup.getOrThrow(ItemTags.FISHING_ENCHANTABLE), 1, 3,
                                Enchantment.constantCost(25), Enchantment.constantCost(50), 4))
                .addNonListEffect(GrappleEnchantmentEffectComponentTypes.GRAPPLE_FORCE, new AddEnchantmentEffect(
                        EnchantmentLevelBasedValue.lookup(
                                List.of(GrappleConstants.FORCE_1, GrappleConstants.FORCE_2, GrappleConstants.FORCE_3),
                                EnchantmentLevelBasedValue.constant(GrappleConstants.FORCE_3))))
                .addNonListEffect(GrappleEnchantmentEffectComponentTypes.GRAPPLE_VERTICAL_FORCE_MULTIPLIER,
                        new AddEnchantmentEffect(EnchantmentLevelBasedValue.lookup(
                                List.of(GrappleConstants.VERTICAL_FORCE_MULTIPLIER_1,
                                        GrappleConstants.VERTICAL_FORCE_MULTIPLIER_2,
                                        GrappleConstants.VERTICAL_FORCE_MULTIPLIER_3),
                                EnchantmentLevelBasedValue.constant(GrappleConstants.VERTICAL_FORCE_MULTIPLIER_3))))
                .addNonListEffect(GrappleEnchantmentEffectComponentTypes.GRAPPLE_COOLDOWN, new AddEnchantmentEffect(
                        EnchantmentLevelBasedValue.lookup(
                                List.of(GrappleConstants.GRAPPLE_COOLDOWN_1, GrappleConstants.GRAPPLE_COOLDOWN_2,
                                        GrappleConstants.GRAPPLE_COOLDOWN_3),
                                EnchantmentLevelBasedValue.constant(GrappleConstants.GRAPPLE_COOLDOWN_3)))));
    }

    private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key,
                                 Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }

    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Grapple.id(id));
    }

    public static void registerEnchantments() {
        Grapple.LOGGER.info("Registering enchantments for " + Grapple.MOD_ID);
    }
}
