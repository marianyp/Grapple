package dev.mariany.grapple.component;

import dev.mariany.grapple.Grapple;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;

import java.util.function.UnaryOperator;

public interface GrappleEnchantmentEffectComponentTypes {
    ComponentType<Unit> ALLOW_GRAPPLE_NON_SOLID = register("allow_grapple_non_solid",
            builder -> builder.codec(Unit.CODEC));

    ComponentType<EnchantmentValueEffect> GRAPPLE_FORCE = register("grapple_force",
            builder -> builder.codec(EnchantmentValueEffect.CODEC));

    ComponentType<EnchantmentValueEffect> GRAPPLE_VERTICAL_FORCE_MULTIPLIER = register(
            "grapple_vertical_force_multiplier", builder -> builder.codec(EnchantmentValueEffect.CODEC));

    ComponentType<EnchantmentValueEffect> GRAPPLE_COOLDOWN = register("grapple_cooldown",
            builder -> builder.codec(EnchantmentValueEffect.CODEC));

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, id,
                builderOperator.apply(ComponentType.builder()).build());
    }

    static void registerEnchantmentEffectComponentTypes() {
        Grapple.LOGGER.info("Registering enchantment component types for " + Grapple.MOD_ID);
    }
}
