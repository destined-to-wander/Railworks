package com.destinedtowander;

import com.destinedtowander.util.GunMisfirePayload;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Railworks implements ModInitializer {
	public static final String MOD_ID = "railworks";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static @NotNull Identifier id(String name) {
		return Identifier.of(MOD_ID, name);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Railworks In Progress");

		PayloadTypeRegistry.playC2S().register(GunMisfirePayload.ID, GunMisfirePayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(GunMisfirePayload.ID, new GunMisfirePayload.Receiver());
	}
}