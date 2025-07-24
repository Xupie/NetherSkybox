package com.github.xupie.netherskybox.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;

// https://github.com/FlashyReese/nuit/blob/1.21.4/dev/common/src/main/java/me/flashyreese/mods/nuit/config/NuitConfig.java
public class ModConfig {
    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Color.class, new ColorAdapter())
        .setPrettyPrinting()
        .excludeFieldsWithModifiers(Modifier.PRIVATE)
        .create();

    private File file;

    public Settings settings = new Settings();

    public static ModConfig load(File file) {
        ModConfig config;
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                config = GSON.fromJson(reader, ModConfig.class);
                if (config == null) config = new ModConfig();
            } catch (Exception e) {
                e.printStackTrace();
                config = new ModConfig();
            }
        } else {
            config = new ModConfig();
        }

        config.file = file;
        config.save();
        return config;
    }

    public void save() {
        File dir = this.file.getParentFile();
        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("Could not create parent directories");
        }
        try (FileWriter writer = new FileWriter(this.file)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            throw new RuntimeException("Could not save configuration file", e);
        }
    }

    public static class Settings {
        public boolean alternateSkyColor = false;
        public SkyTypeOptions skyTypeOptions = SkyTypeOptions.OVERWORLD;
        public boolean darkened = false;
        public int fogColor = 0xFF00FFFF;
        public boolean useThickFog = false;
    }
}
