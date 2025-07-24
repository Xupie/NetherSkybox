package com.github.xupie.netherskybox;

import com.github.xupie.netherskybox.config.ModConfig;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public final class NetherSkybox {
    private static DimensionEffects customEffect;

    public static void init(ModConfig config) {
        customEffect = createCustomEffect(config.settings);
    }

    public static DimensionEffects createCustomEffect(ModConfig.Settings config) {
        return new DimensionEffects(
            config.skyTypeOptions.mappedSkyType,
            config.alternateSkyColor,
            config.darkened
        ) {
            @Override
            public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
                Color c = new Color(config.fogColor, true);
                return new Vec3d(
                    c.getRed() / 255.0,
                    c.getGreen() / 255.0,
                    c.getBlue() / 255.0
                );
            }

            @Override
            public boolean useThickFog(int camX, int camY) {
                return config.useThickFog;
            }
        };
    }

    public static DimensionEffects getCustomEffect() {
        return customEffect;
    }
}
