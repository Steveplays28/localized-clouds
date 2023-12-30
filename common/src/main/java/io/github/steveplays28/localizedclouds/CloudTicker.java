package io.github.steveplays28.localizedclouds;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex2DVariant;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex3DVariant;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex4DVariant;
import de.articdive.jnoise.pipeline.JNoise;
import io.github.steveplays28.localizedclouds.network.LocalizedCloudsNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CloudTicker {
	private static final List<Cloud> clouds = new ArrayList<>();

	private static MinecraftServer server;
	private static final JNoise noise = JNoise.newBuilder().perlin(3301, Interpolation.COSINE, FadeFunction.CUBIC_POLY).scale(
			0.01d).build();

	public static void init(MinecraftServer server) {
		CloudTicker.server = server;
	}

	public static void tickClouds(ServerWorld world) {
		clouds.parallelStream().forEach(Cloud::tick);
	}

	public static void spawnCloudsOnPlayer(@NotNull ServerPlayerEntity player) {
		var world = player.getServerWorld();
		var playerBlockPos = player.getBlockPos();

		for (var blockPos : BlockPos.iterateOutwards(playerBlockPos, server.getPlayerManager().getSimulationDistance() * 16, 1,
				server.getPlayerManager().getSimulationDistance() * 16
		)) {
			LocalizedClouds.LOGGER.info("noise at {}: {}", blockPos,
					noise.evaluateNoise(blockPos.getX(), blockPos.getY(), blockPos.getZ())
			);

			if (noise.evaluateNoise(blockPos.getX(), blockPos.getY(), blockPos.getZ()
			) < 0.25f) {
				continue;
			}

			blockPos.withY(world.getTopY(Heightmap.Type.MOTION_BLOCKING, blockPos.getX(), blockPos.getZ()));
			spawnCloud(world, blockPos);
		}

//		LocalizedClouds.LOGGER.info("{} clouds spawned, list: {}", clouds.size(), clouds);
	}

	public static void spawnCloud(ServerWorld world, @NotNull BlockPos blockPos) {
		var cloud = new Cloud(world, blockPos.mutableCopy());

		clouds.add(cloud);
		LocalizedCloudsNetworking.sendCloudToAllPlayers(cloud);
	}
}
