package com.github.xupie.netherskybox.config;

import net.minecraft.client.render.DimensionEffects;

public enum SkyTypeOptions {
    OVERWORLD(DimensionEffects.SkyType.NORMAL),
    END(DimensionEffects.SkyType.END);

    public final DimensionEffects.SkyType mappedSkyType;

    SkyTypeOptions(DimensionEffects.SkyType type) {
        this.mappedSkyType = type;
    }


    public String getDisplayName() {
        return switch (this) {
            case OVERWORLD -> "Overworld";
            case END -> "The End";
            default -> name();
        };
    }
}
