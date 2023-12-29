package io.github.steveplays28.localizedclouds.fabric;

import io.github.steveplays28.localizedclouds.Localized_clouds;
import net.fabricmc.api.ModInitializer;

public class Localized_cloudsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Localized_clouds.init();
    }
}