package com.destinedtowander.util;

import com.destinedtowander.Railworks;
import dev.doctor4t.trainmurdermystery.game.GameConstants;
import dev.doctor4t.trainmurdermystery.index.TMMSounds;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import org.jetbrains.annotations.NotNull;

public record GunMisfirePayload(boolean fake) implements CustomPayload {
    public static final CustomPayload.Id<GunMisfirePayload> ID = new CustomPayload.Id<>(Railworks.id("misfire"));
    public static final PacketCodec<PacketByteBuf, GunMisfirePayload> CODEC;

    public GunMisfirePayload() {
        this(false);
    }

    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

    static {
        CODEC = PacketCodec.tuple(PacketCodecs.BOOL, GunMisfirePayload::fake, GunMisfirePayload::new);
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<GunMisfirePayload> {
        public Receiver() {
        }

        public void receive(@NotNull GunMisfirePayload payload, ServerPlayNetworking.@NotNull Context context) {
            ServerPlayerEntity player = context.player();
            ItemStack mainHandStack = player.getMainHandStack();

            player.getWorld().playSound(null, player.getX(), player.getEyeY(), player.getZ(), TMMSounds.ITEM_REVOLVER_CLICK, SoundCategory.PLAYERS, 0.5F, 1.0F + player.getRandom().nextFloat() * 0.1F - 0.05F);
            player.getWorld().playSound(null, player.getX(), player.getEyeY(), player.getZ(), TMMSounds.ITEM_LOCKPICK_DOOR, SoundCategory.PLAYERS, 1.0F, 1.0F + player.getRandom().nextFloat() * 0.1F - 0.05F);

            player.getItemCooldownManager().set(mainHandStack.getItem(),  GameConstants.ITEM_COOLDOWNS.getOrDefault(mainHandStack.getItem(), 0) * 2);

        }
    }
}
