package dev.mariany.grapple.gamerule;

import dev.mariany.grapple.Grapple;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class GrappleGamerules {
    public static final GameRules.Key<GameRules.BooleanRule> ADRENALINE_PREVENTS_FALL_DAMAGE = GameRuleRegistry.register(
            "adrenalinePreventsFallDamage", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> ADRENALINE_PREVENTS_COOLDOWN = GameRuleRegistry.register(
            "adrenalinePreventsCooldown", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

    public static void registerGamerules() {
        Grapple.LOGGER.info("Registering gamerules for " + Grapple.MOD_ID);
    }
}
