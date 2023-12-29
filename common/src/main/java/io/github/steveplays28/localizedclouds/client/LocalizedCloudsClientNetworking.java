package io.github.steveplays28.localizedclouds.client;

import dev.architectury.networking.NetworkManager;
import io.github.steveplays28.localizedclouds.Cloud;
import io.github.steveplays28.localizedclouds.network.LocalizedCloudsNetworking;

public class LocalizedCloudsClientNetworking {
	public static void init() {
		NetworkManager.registerReceiver(NetworkManager.Side.S2C, LocalizedCloudsNetworking.CLOUD_ADD_PACKET_ID,
				(buf, context) -> ClientCloudTicker.spawnCloud(new Cloud(buf, context))
		);
	}
}
