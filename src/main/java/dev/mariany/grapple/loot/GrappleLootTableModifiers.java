package dev.mariany.grapple.loot;

import dev.mariany.grapple.GrappleConstants;
import dev.mariany.grapple.enchantment.GrappleEnchantments;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetEnchantmentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;

public class GrappleLootTableModifiers {
    private static final List<RegistryKey<LootTable>> CHANCE_TABLES = List.of(LootTables.BURIED_TREASURE_CHEST,
            LootTables.UNDERWATER_RUIN_BIG_CHEST);
    private static final List<RegistryKey<LootTable>> ALWAYS_TABLES = List.of(LootTables.WOODLAND_MANSION_CHEST);

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, provider) -> {
            RegistryEntry.Reference<Enchantment> grappleEnchantment = provider.getOrThrow(RegistryKeys.ENCHANTMENT)
                    .getOrThrow(GrappleEnchantments.GRAPPLE);
            RegistryEntry.Reference<Enchantment> aerHookEnchantment = provider.getOrThrow(RegistryKeys.ENCHANTMENT)
                    .getOrThrow(GrappleEnchantments.AER_HOOK);

            if (CHANCE_TABLES.contains(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(UniformLootNumberProvider.create(0, 1))
                        .conditionally(RandomChanceLootCondition.builder(GrappleConstants.ENCHANTED_BOOK_CHANCE))
                        .with(ItemEntry.builder(Items.BOOK)
                                .apply(new SetEnchantmentsLootFunction.Builder().enchantment(grappleEnchantment,
                                        UniformLootNumberProvider.create(1, 3)))).with(ItemEntry.builder(Items.BOOK)
                                .apply(new SetEnchantmentsLootFunction.Builder().enchantment(aerHookEnchantment,
                                        ConstantLootNumberProvider.create(1))));

                tableBuilder.pool(poolBuilder.build());
            }

            if (ALWAYS_TABLES.contains(key)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(Items.BOOK)
                                .apply(new SetEnchantmentsLootFunction.Builder().enchantment(grappleEnchantment,
                                        UniformLootNumberProvider.create(2, 3)))).with(ItemEntry.builder(Items.BOOK)
                                .apply(new SetEnchantmentsLootFunction.Builder().enchantment(aerHookEnchantment,
                                        ConstantLootNumberProvider.create(1))));

                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
