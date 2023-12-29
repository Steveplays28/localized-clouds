package io.github.steveplays28.localizedclouds.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.steveplays28.localizedclouds.LocalizedClouds;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LocalizedClouds.MOD_ID)
public class LocalizedCloudsForge {
	public LocalizedCloudsForge() {
		// Submit our event bus to let Architectury register our content on the right time
		EventBuses.registerModEventBus(LocalizedClouds.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
		LocalizedClouds.init();
	}
}
