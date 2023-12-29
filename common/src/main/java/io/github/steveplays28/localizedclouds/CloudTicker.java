package io.github.steveplays28.localizedclouds;

import io.github.steveplays28.localizedclouds.network.LocalizedCloudsNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CloudTicker {
	private static final List<Cloud> clouds = new ArrayList<>();

	public static void tickClouds(ServerWorld world) {
		clouds.parallelStream().forEach(Cloud::tick);
	}

	public static void spawnCloudsOnPlayer(@NotNull ServerPlayerEntity player) {
		spawnCloud(player.getServerWorld(), player.getBlockPos());
	}

	public static void spawnCloud(ServerWorld world, @NotNull BlockPos blockPos) {
		var cloud = new Cloud(world, blockPos.mutableCopy());

		clouds.add(cloud);
		LocalizedCloudsNetworking.sendCloudToAllPlayers(cloud);
	}
}
