package io.github.steveplays28.localizedclouds.client;

import io.github.steveplays28.localizedclouds.Cloud;
import io.github.steveplays28.localizedclouds.network.LocalizedCloudsNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ClientCloudTicker {
	private static final List<Cloud> clouds = new ArrayList<>();

	public static List<Cloud> getClouds() {
		return clouds;
	}

	public static void spawnCloud(Cloud cloud) {
		clouds.add(cloud);
	}
}
