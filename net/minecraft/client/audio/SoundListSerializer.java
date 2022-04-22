package net.minecraft.client.audio;

import java.lang.reflect.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import com.google.gson.*;

public class SoundListSerializer implements JsonDeserializer
{
    private static final String __OBFID;
    
    public SoundList deserialize1(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
        final JsonObject elementAsJsonObject = JsonUtils.getElementAsJsonObject(jsonElement, "entry");
        final SoundList list = new SoundList();
        list.setReplaceExisting(JsonUtils.getJsonObjectBooleanFieldValueOrDefault(elementAsJsonObject, "replace", false));
        final SoundCategory func_147154_a = SoundCategory.func_147154_a(JsonUtils.getJsonObjectStringFieldValueOrDefault(elementAsJsonObject, "category", SoundCategory.MASTER.getCategoryName()));
        list.setSoundCategory(func_147154_a);
        Validate.notNull(func_147154_a, "Invalid category", new Object[0]);
        if (elementAsJsonObject.has("sounds")) {
            final JsonArray jsonObjectJsonArrayField = JsonUtils.getJsonObjectJsonArrayField(elementAsJsonObject, "sounds");
            while (0 < jsonObjectJsonArrayField.size()) {
                final JsonElement value = jsonObjectJsonArrayField.get(0);
                final SoundList.SoundEntry soundEntry = new SoundList.SoundEntry();
                if (JsonUtils.jsonElementTypeIsString(value)) {
                    soundEntry.setSoundEntryName(JsonUtils.getJsonElementStringValue(value, "sound"));
                }
                else {
                    final JsonObject elementAsJsonObject2 = JsonUtils.getElementAsJsonObject(value, "sound");
                    soundEntry.setSoundEntryName(JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject2, "name"));
                    if (elementAsJsonObject2.has("type")) {
                        final SoundList.SoundEntry.Type type2 = SoundList.SoundEntry.Type.getType(JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject2, "type"));
                        Validate.notNull(type2, "Invalid type", new Object[0]);
                        soundEntry.setSoundEntryType(type2);
                    }
                    if (elementAsJsonObject2.has("volume")) {
                        final float jsonObjectFloatFieldValue = JsonUtils.getJsonObjectFloatFieldValue(elementAsJsonObject2, "volume");
                        Validate.isTrue(jsonObjectFloatFieldValue > 0.0f, "Invalid volume", new Object[0]);
                        soundEntry.setSoundEntryVolume(jsonObjectFloatFieldValue);
                    }
                    if (elementAsJsonObject2.has("pitch")) {
                        final float jsonObjectFloatFieldValue2 = JsonUtils.getJsonObjectFloatFieldValue(elementAsJsonObject2, "pitch");
                        Validate.isTrue(jsonObjectFloatFieldValue2 > 0.0f, "Invalid pitch", new Object[0]);
                        soundEntry.setSoundEntryPitch(jsonObjectFloatFieldValue2);
                    }
                    if (elementAsJsonObject2.has("weight")) {
                        final int jsonObjectIntegerFieldValue = JsonUtils.getJsonObjectIntegerFieldValue(elementAsJsonObject2, "weight");
                        Validate.isTrue(jsonObjectIntegerFieldValue > 0, "Invalid weight", new Object[0]);
                        soundEntry.setSoundEntryWeight(jsonObjectIntegerFieldValue);
                    }
                    if (elementAsJsonObject2.has("stream")) {
                        soundEntry.setStreaming(JsonUtils.getJsonObjectBooleanFieldValue(elementAsJsonObject2, "stream"));
                    }
                }
                list.getSoundList().add(soundEntry);
                int n = 0;
                ++n;
            }
        }
        return list;
    }
    
    @Override
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
        return this.deserialize1(jsonElement, type, jsonDeserializationContext);
    }
    
    static {
        __OBFID = "CL_00001124";
    }
}
