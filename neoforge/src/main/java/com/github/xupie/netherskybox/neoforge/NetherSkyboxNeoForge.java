package com.github.xupie.netherskybox.neoforge;

import com.github.xupie.netherskybox.NetherSkybox;
import com.github.xupie.netherskybox.config.ModConfig;
import com.github.xupie.netherskybox.config.ModConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.io.File;
import java.util.function.Supplier;


@Mod(value = "nether_skybox", dist = Dist.CLIENT)
public class NetherSkyboxNeoForge {

    public static ModConfig config;

    public NetherSkyboxNeoForge(FMLModContainer container, IEventBus modBus) {
        modBus.addListener(this::onClientSetup);

        container.registerExtensionPoint(
            IConfigScreenFactory.class,
            (Supplier<IConfigScreenFactory>) () -> (client, parent) -> new ModConfigScreen(parent, config)
        );
    }


    public void onClientSetup(final FMLClientSetupEvent event) {
        File configDir = FMLPaths.CONFIGDIR.get().toFile();
        File configFile = new File(configDir, "netherskybox.json");
        config = ModConfig.load(configFile);

        event.enqueueWork(() -> {
            NetherSkybox.init(config);
        });
    }
}