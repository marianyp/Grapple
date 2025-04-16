package dev.mariany.grapple.entity.effect;

import dev.mariany.grapple.Grapple;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class GrappleStatusEffects {
    public static final RegistryEntry<StatusEffect> ADRENALINE = register("adrenaline",
            new AdrenalineStatusEffect(StatusEffectCategory.BENEFICIAL, -13312));

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Grapple.id(id), statusEffect);
    }

    public static void registerStatusEffects() {
        Grapple.LOGGER.info("Registering status effects for " + Grapple.MOD_ID);
    }
}
