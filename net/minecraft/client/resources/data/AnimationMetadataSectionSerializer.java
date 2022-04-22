package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import java.util.*;
import com.google.gson.*;

public class AnimationMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer
{
    private static final String __OBFID;
    
    @Override
    public AnimationMetadataSection deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
        final ArrayList arrayList = Lists.newArrayList();
        final JsonObject elementAsJsonObject = JsonUtils.getElementAsJsonObject(jsonElement, "metadata section");
        final int jsonObjectIntegerFieldValueOrDefault = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(elementAsJsonObject, "frametime", 1);
        if (jsonObjectIntegerFieldValueOrDefault != 1) {
            Validate.inclusiveBetween(1L, 2147483647L, jsonObjectIntegerFieldValueOrDefault, "Invalid default frame time");
        }
        if (elementAsJsonObject.has("frames")) {
            final JsonArray jsonObjectJsonArrayField = JsonUtils.getJsonObjectJsonArrayField(elementAsJsonObject, "frames");
            while (0 < jsonObjectJsonArrayField.size()) {
                final AnimationFrame animationFrame = this.parseAnimationFrame(0, jsonObjectJsonArrayField.get(0));
                if (animationFrame != null) {
                    arrayList.add(animationFrame);
                }
                int jsonObjectIntegerFieldValueOrDefault2 = 0;
                ++jsonObjectIntegerFieldValueOrDefault2;
            }
        }
        final int jsonObjectIntegerFieldValueOrDefault3 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(elementAsJsonObject, "width", -1);
        int jsonObjectIntegerFieldValueOrDefault2 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(elementAsJsonObject, "height", -1);
        if (jsonObjectIntegerFieldValueOrDefault3 != -1) {
            Validate.inclusiveBetween(1L, 2147483647L, jsonObjectIntegerFieldValueOrDefault3, "Invalid width");
        }
        if (0 != -1) {
            Validate.inclusiveBetween(1L, 2147483647L, 0, "Invalid height");
        }
        return new AnimationMetadataSection(arrayList, jsonObjectIntegerFieldValueOrDefault3, 0, jsonObjectIntegerFieldValueOrDefault, JsonUtils.getJsonObjectBooleanFieldValueOrDefault(elementAsJsonObject, "interpolate", false));
    }
    
    private AnimationFrame parseAnimationFrame(final int n, final JsonElement jsonElement) {
        if (jsonElement.isJsonPrimitive()) {
            return new AnimationFrame(JsonUtils.getJsonElementIntegerValue(jsonElement, "frames[" + n + "]"));
        }
        if (jsonElement.isJsonObject()) {
            final JsonObject elementAsJsonObject = JsonUtils.getElementAsJsonObject(jsonElement, "frames[" + n + "]");
            final int jsonObjectIntegerFieldValueOrDefault = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(elementAsJsonObject, "time", -1);
            if (elementAsJsonObject.has("time")) {
                Validate.inclusiveBetween(1L, 2147483647L, jsonObjectIntegerFieldValueOrDefault, "Invalid frame time");
            }
            final int jsonObjectIntegerFieldValue = JsonUtils.getJsonObjectIntegerFieldValue(elementAsJsonObject, "index");
            Validate.inclusiveBetween(0L, 2147483647L, jsonObjectIntegerFieldValue, "Invalid frame index");
            return new AnimationFrame(jsonObjectIntegerFieldValue, jsonObjectIntegerFieldValueOrDefault);
        }
        return null;
    }
    
    public JsonElement serialize(final AnimationMetadataSection animationMetadataSection, final Type type, final JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("frametime", animationMetadataSection.getFrameTime());
        if (animationMetadataSection.getFrameWidth() != -1) {
            jsonObject.addProperty("width", animationMetadataSection.getFrameWidth());
        }
        if (animationMetadataSection.getFrameHeight() != -1) {
            jsonObject.addProperty("height", animationMetadataSection.getFrameHeight());
        }
        if (animationMetadataSection.getFrameCount() > 0) {
            final JsonArray jsonArray = new JsonArray();
            while (0 < animationMetadataSection.getFrameCount()) {
                if (animationMetadataSection.frameHasTime(0)) {
                    final JsonObject jsonObject2 = new JsonObject();
                    jsonObject2.addProperty("index", animationMetadataSection.getFrameIndex(0));
                    jsonObject2.addProperty("time", animationMetadataSection.getFrameTimeSingle(0));
                    jsonArray.add(jsonObject2);
                }
                else {
                    jsonArray.add(new JsonPrimitive(animationMetadataSection.getFrameIndex(0)));
                }
                int n = 0;
                ++n;
            }
            jsonObject.add("frames", jsonArray);
        }
        return jsonObject;
    }
    
    @Override
    public String getSectionName() {
        return "animation";
    }
    
    @Override
    public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
        return this.serialize((AnimationMetadataSection)o, type, jsonSerializationContext);
    }
    
    @Override
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
    
    static {
        __OBFID = "CL_00001107";
    }
}
