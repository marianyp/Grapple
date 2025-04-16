package dev.mariany.grapple;

import dev.mariany.grapple.component.GrappleEnchantmentEffectComponentTypes;
import dev.mariany.grapple.enchantment.GrappleEnchantments;
import dev.mariany.grapple.entity.effect.GrappleStatusEffects;
import dev.mariany.grapple.gamerule.GrappleGamerules;
import dev.mariany.grapple.item.GrappleItems;
import dev.mariany.grapple.loot.GrappleLootTableModifiers;
import dev.mariany.grapple.sound.GrappleSoundEvents;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Grapple implements ModInitializer {
    public static final String MOD_ID = "grapple";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        GrappleEnchantmentEffectComponentTypes.registerEnchantmentEffectComponentTypes();
        GrappleEnchantments.registerEnchantments();
        GrappleGamerules.registerGamerules();
        GrappleStatusEffects.registerStatusEffects();
        GrappleItems.registerItems();
        GrappleSoundEvents.registerSoundEvents();
        GrappleLootTableModifiers.modifyLootTables();
    }

    public static Identifier id(String resource) {
        return Identifier.of(Grapple.MOD_ID, resource);
    }
}