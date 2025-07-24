package com.github.xupie.netherskybox.config;

import net.minecraft.client.gui.DrawContext;

public interface ConfigPreviewRenderer {
    void renderConfigPreview(DrawContext context, int mouseX, int mouseY, float delta);
}