package com.github.xupie.netherskybox.config;

import com.google.gson.*;

import java.awt.*;
import java.lang.reflect.Type;

public class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {
    @Override
    public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
        // Serialize Color as hex string
        String hex = String.format("#%02X%02X%02X", src.getRed(), src.getGreen(), src.getBlue());
        return new JsonPrimitive(hex);
    }

    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String hex = json.getAsString();
        return Color.decode(hex);
    }
}