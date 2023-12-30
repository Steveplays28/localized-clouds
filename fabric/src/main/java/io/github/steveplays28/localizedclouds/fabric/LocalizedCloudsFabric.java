package io.github.steveplays28.localizedclouds.fabric;

import io.github.steveplays28.localizedclouds.LocalizedClouds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class LocalizedCloudsFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		LocalizedClouds.init();

		WorldRenderEvents.END.register(context -> {
			LocalizedClouds.onWorldRenderingEnd(context.camera());
		});
	}
}
