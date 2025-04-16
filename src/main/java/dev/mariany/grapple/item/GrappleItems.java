package dev.mariany.grapple.item;

import dev.mariany.grapple.Grapple;
import dev.mariany.grapple.entity.effect.GrappleStatusEffects;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.List;
import java.util.function.Function;

public class GrappleItems {
    public static final Item GOLDEN_COOKIE = register("golden_cookie", Item::new, new Item.Settings().food(
            new FoodComponent.Builder().nutrition(4).saturationModifier(0.2F).alwaysEdible().build(),
            ConsumableComponents.food().consumeEffect(new ApplyEffectsConsumeEffect(
                    List.of(new StatusEffectInstance(GrappleStatusEffects.ADRENALINE, 160, 0),
                            new StatusEffectInstance(StatusEffects.SPEED, 80, 0)))).build()));

    private static Item register(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Grapple.id(name));
        return Items.register(registryKey, factory, settings);
    }

    public static void registerItems() {
        Grapple.LOGGER.info("Registering items for " + Grapple.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(Items.COOKIE, GOLDEN_COOKIE);
        });
    }
}
