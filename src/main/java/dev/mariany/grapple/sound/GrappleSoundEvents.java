package dev.mariany.grapple.sound;

import dev.mariany.grapple.Grapple;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class GrappleSoundEvents {
    public static final SoundEvent GRAPPLE = register("grapple");

    private static SoundEvent register(String name) {
        Identifier id = Grapple.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSoundEvents() {
        Grapple.LOGGER.info("Registering sound events for " + Grapple.MOD_ID);
    }
}
