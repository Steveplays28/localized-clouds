package io.github.steveplays28.localizedclouds;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class Cloud {
	private World world;
	private final BlockPos.Mutable blockPos;

	public Cloud(World world, BlockPos.Mutable blockPos) {
		this.world = world;
		this.blockPos = blockPos;
	}

	// Deserialize
	public Cloud(@NotNull PacketByteBuf buf, NetworkManager.PacketContext context) {
		// TODO: Parse world info
		buf.readIdentifier();
//		world = RegistryKey.of(RegistryKeys.WORLD, buf.readIdentifier());
		blockPos = buf.readBlockPos().mutableCopy();
	}

	public void serialize(@NotNull PacketByteBuf buf) {
		buf.writeIdentifier(world.getRegistryKey().getValue());
		buf.writeBlockPos(blockPos);
	}

	public BlockPos getBlockPos() {
		return blockPos;
	}

	public void tick() {

	}
}
