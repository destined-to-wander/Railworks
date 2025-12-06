package com.destinedtowander.mixin;

import dev.doctor4t.trainmurdermystery.index.TMMItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PlayerEntity.class})
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At(value = "HEAD"), method = "attack")
    public void attack(Entity target, CallbackInfo _ci) {
        PlayerEntity self = (PlayerEntity)(Object)this;
        if (self.getMainHandStack().isOf(TMMItems.KNIFE)) {
            Vec3d direction = new Vec3d(
                    -Math.sin(self.getYaw() * 0.017453292F),
                    0,
                    Math.cos(self.getYaw() * 0.017453292F)
            ).normalize();

            double knockback = 1;
            target.addVelocity(direction.x * knockback, 0.1, direction.z * knockback);
            target.velocityDirty = true;
        }

    }
}
