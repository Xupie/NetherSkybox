package com.github.xupie.netherskybox.fabric;

import com.github.xupie.netherskybox.NetherSkybox;
import com.github.xupie.netherskybox.config.ModConfig;
import com.github.xupie.netherskybox.config.ModConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class NetherSkyboxFabric implements ClientModInitializer, ModMenuApi {

    public static ModConfig config;

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            File configDir = FabricLoader.getInstance().getConfigDir().toFile();
            File configFile = new File(configDir, "netherskybox.json");
            config = ModConfig.load(configFile);
            NetherSkybox.init(config);
        });
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new ModConfigScreen(parent, config);
    }
}
