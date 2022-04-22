package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.gson.*;

public class TextureMetadataSectionSerializer extends BaseMetadataSectionSerializer
{
    private static final String __OBFID;
    
    @Override
    public TextureMetadataSection deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final boolean jsonObjectBooleanFieldValueOrDefault = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(asJsonObject, "blur", false);
        final boolean jsonObjectBooleanFieldValueOrDefault2 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(asJsonObject, "clamp", false);
        final ArrayList arrayList = Lists.newArrayList();
        if (asJsonObject.has("mipmaps")) {
            final JsonArray asJsonArray = asJsonObject.getAsJsonArray("mipmaps");
            while (0 < asJsonArray.size()) {
                final JsonElement value = asJsonArray.get(0);
                if (value.isJsonPrimitive()) {
                    arrayList.add(value.getAsInt());
                }
                else if (value.isJsonObject()) {
                    throw new JsonParseException("Invalid texture->mipmap->" + 0 + ": expected number, was " + value);
                }
                int n = 0;
                ++n;
            }
        }
        return new TextureMetadataSection(jsonObjectBooleanFieldValueOrDefault, jsonObjectBooleanFieldValueOrDefault2, arrayList);
    }
    
    @Override
    public String getSectionName() {
        return "texture";
    }
    
    @Override
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
    
    static {
        __OBFID = "CL_00001115";
    }
}
