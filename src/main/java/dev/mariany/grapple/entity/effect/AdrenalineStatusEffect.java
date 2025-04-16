package dev.mariany.grapple.entity.effect;

import dev.mariany.grapple.gamerule.GrappleGamerules;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class AdrenalineStatusEffect extends StatusEffect {
    protected AdrenalineStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (world.getGameRules().get(GrappleGamerules.ADRENALINE_PREVENTS_FALL_DAMAGE).get()) {
            entity.fallDistance = 0;
        }

        return true;
    }
}
