package com.github.xupie.netherskybox.config;

import com.github.xupie.netherskybox.NetherSkybox;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;

public class ModConfigScreen extends Screen implements ConfigPreviewRenderer {
    private final Screen parent;
    private final ModConfig config;

    private int skyTypeIndex;

    private int red, green, blue;
    private SliderWidget greenSlider;

    public ModConfigScreen(Screen parent, ModConfig config) {
        super(Text.literal("Nether Skybox Config"));
        this.parent = parent;
        this.config = config;
        this.skyTypeIndex = config.settings.skyTypeOptions.ordinal();
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 4;

        // Toggle alternate Sky Color
        this.addDrawableChild(
            ButtonWidget.builder(getToggleText("Alternate Sky Color", config.settings.alternateSkyColor), button -> {
                config.settings.alternateSkyColor = !config.settings.alternateSkyColor;
                button.setMessage(getToggleText("Alternate Sky Color", config.settings.alternateSkyColor));
            }).size(200, 20).position(centerX - 100, y).build()
        );
        y += 24;

        // Cycle SkyTypeOptions button
        this.addDrawableChild(
            ButtonWidget.builder(getSkyTypeText(SkyTypeOptions.values()[skyTypeIndex]), button -> {
                skyTypeIndex = (skyTypeIndex + 1) % SkyTypeOptions.values().length;
                config.settings.skyTypeOptions = SkyTypeOptions.values()[skyTypeIndex];
                button.setMessage(getSkyTypeText(config.settings.skyTypeOptions));
            }).size(200, 20).position(centerX - 100, y).build()
        );
        y += 24;

        // Toggle Darkened
        this.addDrawableChild(
            ButtonWidget.builder(getToggleText("Darkened", config.settings.darkened), button -> {
                config.settings.darkened = !config.settings.darkened;
                button.setMessage(getToggleText("Darkened", config.settings.darkened));
            }).size(200, 20).position(centerX - 100, y).build()
        );
        y += 24;

        // Toggle Use Thick Fog
        this.addDrawableChild(
            ButtonWidget.builder(getToggleText("Use Thick Fog", config.settings.useThickFog), button -> {
                config.settings.useThickFog = !config.settings.useThickFog;
                button.setMessage(getToggleText("Use Thick Fog", config.settings.useThickFog));
            }).size(200, 20).position(centerX - 100, y).build()
        );
        y += 24;

        Color initialColor = new Color(config.settings.fogColor, true);
        red = initialColor.getRed();
        green = initialColor.getGreen();
        blue = initialColor.getBlue();

        // Sliders for fog color
        this.addDrawableChild(new SliderWidget(centerX - 100, y, 200, 20, Text.literal("Red: " + red), red / 255f) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("Red: " + (int) (this.value * 255)));
            }

            @Override
            protected void applyValue() {
                red = (int) (this.value * 255);
                updateFogColor();
            }
        });
        y += 24;

        greenSlider = this.addDrawableChild(new SliderWidget(centerX - 100, y, 200, 20, Text.literal("Green: " + green), green / 255f) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("Green: " + (int) (this.value * 255)));
            }

            @Override
            protected void applyValue() {
                green = (int) (this.value * 255);
                updateFogColor();
            }
        });
        y += 24;

        this.addDrawableChild(new SliderWidget(centerX - 100, y, 200, 20, Text.literal("Blue: " + blue), blue / 255f) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("Blue: " + (int) (this.value * 255)));
            }

            @Override
            protected void applyValue() {
                blue = (int) (this.value * 255);
                updateFogColor();
            }
        });
        y += 24;

        // Done button
        this.addDrawableChild(
            ButtonWidget.builder(Text.literal("Done"), button -> {
                MinecraftClient.getInstance().worldRenderer.reload();
                config.save();
                NetherSkybox.init(config);
                this.client.setScreen(parent);
            }).size(200, 20).position(centerX - 100, y).build()
        );
    }


    private void updateFogColor() {
        Color newColor = new Color(red, green, blue);
        config.settings.fogColor = newColor.getRGB();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        renderConfigPreview(context, mouseX, mouseY, delta);
    }

    @Override
    public void renderConfigPreview(DrawContext context, int mouseX, int mouseY, float delta) {
        int previewX = this.width / 2 + 120;
        int previewSize = 40;
        int previewY = greenSlider.getY() + greenSlider.getHeight() / 2 - previewSize / 2;

        context.fill(previewX, previewY, previewX + previewSize, previewY + previewSize,
            config.settings.fogColor | 0xFF000000);
    }

    private Text getToggleText(String label, boolean value) {
        return Text.literal(label + ": " + (value ? "ON" : "OFF"))
            .formatted(value ? Formatting.GREEN : Formatting.RED);
    }

    private Text getSkyTypeText(SkyTypeOptions option) {
        return Text.literal("Sky Type: ").append(option.getDisplayName());
    }
}