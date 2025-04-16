package dev.mariany.grapple;

import dev.mariany.grapple.component.GrappleEnchantmentEffectComponentTypes;
import dev.mariany.grapple.entity.effect.GrappleStatusEffects;
import dev.mariany.grapple.gamerule.GrappleGamerules;
import dev.mariany.grapple.sound.GrappleSoundEvents;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;

public class GrappleHelper {
    public static void beforeFishingBobberUse(PlayerEntity player, ItemStack rod) {
        World world = player.getWorld();

        if (!player.isSpectator()) {
            if (!rod.isEmpty() && !player.getItemCooldownManager().isCoolingDown(rod) && player.fishHook != null) {
                float force = GrappleHelper.getGrappleForce(world, rod);
                float verticalMultiplier = GrappleHelper.getGrappleVerticalMultiplier(world, rod);

                if (force > 0) {
                    if (GrappleHelper.pullPlayer(player, rod, force, verticalMultiplier)) {
                        GrappleHelper.applyCooldown(player, rod);
                    }
                }
            }
        }
    }

    private static void applyCooldown(PlayerEntity player, ItemStack rod) {
        World world = player.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            boolean shouldCooldown = !serverWorld.getGameRules().get(GrappleGamerules.ADRENALINE_PREVENTS_COOLDOWN)
                    .get() || !player.hasStatusEffect(GrappleStatusEffects.ADRENALINE);

            if (shouldCooldown) {
                FishingBobberEntity hook = player.fishHook;
                int cooldown = Math.round(getGrappleCooldown(world, rod));
                boolean allowsNonSolid = EnchantmentHelper.hasAnyEnchantmentsWith(rod,
                        GrappleEnchantmentEffectComponentTypes.ALLOW_GRAPPLE_NON_SOLID);

                if (allowsNonSolid && hook != null) {
                    if (!isSolidBlockNearby(world, hook.getBlockPos())) {
                        cooldown = Math.round(cooldown * GrappleConstants.AER_HOOK_NON_SOLID_MULTIPLIER);
                    }
                }
                player.getItemCooldownManager().set(rod, cooldown);
            }
        }
    }

    private static boolean pullPlayer(PlayerEntity player, ItemStack rod, float force, float verticalMultiplier) {
        FishingBobberEntity hook = player.fishHook;

        if (hook != null) {
            if (canGrapple(player, rod)) {
                double dx = hook.getX() - player.getX();
                double dy = hook.getY() - player.getY();
                double dz = hook.getZ() - player.getZ();
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                if (distance > 0) {
                    double motionX = (dx / distance) * force;
                    double motionY = (dy / distance) * force * verticalMultiplier;
                    double motionZ = (dz / distance) * force;

                    player.setVelocity(new Vec3d(motionX, motionY, motionZ));
                    player.velocityModified = true;

                    playGrappleSound(player);

                    return true;
                }
            }
        }

        return false;
    }

    private static void playGrappleSound(PlayerEntity player) {
        World world = player.getWorld();
        BlockPos pos = player.getBlockPos();

        if (!world.isClient) {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), GrappleSoundEvents.GRAPPLE, SoundCategory.PLAYERS,
                    2F, 1F);
        }
    }

    private static float getGrappleCooldown(World world, ItemStack rod) {
        Random random = world.getRandom();
        MutableFloat mutableFloat = new MutableFloat();
        forEachEnchantment(rod, (enchantment, level) -> enchantment.value()
                .modifyValue(GrappleEnchantmentEffectComponentTypes.GRAPPLE_COOLDOWN, random, level, mutableFloat));
        return mutableFloat.floatValue();
    }

    private static float getGrappleForce(World world, ItemStack rod) {
        Random random = world.getRandom();
        MutableFloat mutableFloat = new MutableFloat();
        forEachEnchantment(rod, (enchantment, level) -> enchantment.value()
                .modifyValue(GrappleEnchantmentEffectComponentTypes.GRAPPLE_FORCE, random, level, mutableFloat));
        return mutableFloat.floatValue();
    }

    private static float getGrappleVerticalMultiplier(World world, ItemStack rod) {
        Random random = world.getRandom();
        MutableFloat mutableFloat = new MutableFloat();
        forEachEnchantment(rod, (enchantment, level) -> enchantment.value()
                .modifyValue(GrappleEnchantmentEffectComponentTypes.GRAPPLE_VERTICAL_FORCE_MULTIPLIER, random, level,
                        mutableFloat));
        return mutableFloat.floatValue();
    }

    private static boolean canGrapple(PlayerEntity player, ItemStack rod) {
        FishingBobberEntity hook = player.fishHook;
        boolean allowsNonSolid = EnchantmentHelper.hasAnyEnchantmentsWith(rod,
                GrappleEnchantmentEffectComponentTypes.ALLOW_GRAPPLE_NON_SOLID);

        return hook != null && !hook.isInFluid() && (allowsNonSolid || isSolidBlockNearby(player.getWorld(),
                hook.getBlockPos()));
    }

    private static boolean isSolidBlockNearby(World world, BlockPos origin) {
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = origin.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);

            if (!blockState.getCollisionShape(world, blockPos).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    private static void forEachEnchantment(ItemStack stack, Consumer consumer) {
        ItemEnchantmentsComponent itemEnchantmentsComponent = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS,
                ItemEnchantmentsComponent.DEFAULT);

        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
            consumer.accept(entry.getKey(), entry.getIntValue());
        }
    }

    @FunctionalInterface
    interface Consumer {
        void accept(RegistryEntry<Enchantment> enchantment, int level);
    }
}
