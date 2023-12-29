package io.github.steveplays28.localizedclouds;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import io.github.steveplays28.localizedclouds.client.LocalizedCloudsClientNetworking;
import io.github.steveplays28.localizedclouds.network.LocalizedCloudsNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalizedClouds {
	public static final String MOD_ID = "localized-clouds";
	public static final String MOD_NAME = "Localized Clouds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void init() {
		LOGGER.info("Loading {}.", MOD_NAME);

		LifecycleEvent.SERVER_STARTING.register(LocalizedCloudsNetworking::init);
		TickEvent.SERVER_LEVEL_PRE.register(CloudTicker::tickClouds);
		PlayerEvent.PLAYER_JOIN.register(CloudTicker::spawnCloudsOnPlayer);
		LocalizedCloudsClientNetworking.init();
	}
}
