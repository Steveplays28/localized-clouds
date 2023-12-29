package io.github.steveplays28.localizedclouds.network;

import dev.architectury.networking.NetworkManager;
import io.github.steveplays28.localizedclouds.Cloud;
import io.github.steveplays28.localizedclouds.LocalizedClouds;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class LocalizedCloudsNetworking {
	public static final Identifier CLOUD_ADD_PACKET_ID = new Identifier(LocalizedClouds.MOD_ID, "cloud_add_packet");

	private static MinecraftServer server;

	public static void init(MinecraftServer server) {
		LocalizedCloudsNetworking.server = server;
	}

	public static void sendCloudToAllPlayers(@NotNull Cloud cloud) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		cloud.serialize(buf);

		NetworkManager.sendToPlayers(server.getPlayerManager().getPlayerList(), CLOUD_ADD_PACKET_ID, buf);
	}
}
