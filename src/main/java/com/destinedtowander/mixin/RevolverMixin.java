package com.destinedtowander.mixin;

import com.destinedtowander.util.GunMisfirePayload;
import dev.doctor4t.trainmurdermystery.cca.PlayerMoodComponent;
import dev.doctor4t.trainmurdermystery.item.RevolverItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RevolverItem.class)
public class RevolverMixin {

    @Inject(at = @At(value = "HEAD"), method = "use", cancellable = true)
    public void cancelIfInsane(@NotNull World world, @NotNull PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> CiR) {
        if (world.isClient && !user.isCreative() && (PlayerMoodComponent.KEY.get(user).isLowerThanDepressed() || PlayerMoodComponent.KEY.get(user).isLowerThanMid() && user.getRandom().nextFloat() <= 0.5F)){

            ClientPlayNetworking.send(new GunMisfirePayload());
            CiR.setReturnValue(TypedActionResult.consume(user.getStackInHand(hand)));
            CiR.cancel();
        }
    }
}
