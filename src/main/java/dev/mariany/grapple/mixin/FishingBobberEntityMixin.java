package dev.mariany.grapple.mixin;

import dev.mariany.grapple.GrappleHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
    @Inject(at = @At("HEAD"), method = "use")
    private void injectUse(ItemStack usedItem, CallbackInfoReturnable<Integer> cir) {
        FishingBobberEntity hook = (FishingBobberEntity) (Object) this;
        PlayerEntity playerEntity = hook.getPlayerOwner();

        if (!hook.getWorld().isClient && playerEntity != null) {
            GrappleHelper.beforeFishingBobberUse(playerEntity, usedItem);
        }
    }
}