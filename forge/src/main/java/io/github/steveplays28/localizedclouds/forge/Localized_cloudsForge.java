package io.github.steveplays28.localizedclouds.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.steveplays28.localizedclouds.Localized_clouds;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Localized_clouds.MOD_ID)
public class Localized_cloudsForge {
    public Localized_cloudsForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Localized_clouds.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
            Localized_clouds.init();
    }
}