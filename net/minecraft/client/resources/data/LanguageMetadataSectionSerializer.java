package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import com.google.gson.*;
import java.util.*;

public class LanguageMetadataSectionSerializer extends BaseMetadataSectionSerializer
{
    private static final String __OBFID;
    
    @Override
    public LanguageMetadataSection deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final HashSet hashSet = Sets.newHashSet();
        for (final Map.Entry<String, V> entry : asJsonObject.entrySet()) {
            final String s = entry.getKey();
            final JsonObject elementAsJsonObject = JsonUtils.getElementAsJsonObject((JsonElement)entry.getValue(), "language");
            final String jsonObjectStringFieldValue = JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject, "region");
            final String jsonObjectStringFieldValue2 = JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject, "name");
            final boolean jsonObjectBooleanFieldValueOrDefault = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(elementAsJsonObject, "bidirectional", false);
            if (jsonObjectStringFieldValue.isEmpty()) {
                throw new JsonParseException("Invalid language->'" + s + "'->region: empty value");
            }
            if (jsonObjectStringFieldValue2.isEmpty()) {
                throw new JsonParseException("Invalid language->'" + s + "'->name: empty value");
            }
            if (!hashSet.add(new Language(s, jsonObjectStringFieldValue, jsonObjectStringFieldValue2, jsonObjectBooleanFieldValueOrDefault))) {
                throw new JsonParseException("Duplicate language->'" + s + "' defined");
            }
        }
        return new LanguageMetadataSection(hashSet);
    }
    
    @Override
    public String getSectionName() {
        return "language";
    }
    
    @Override
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
    
    static {
        __OBFID = "CL_00001111";
    }
}
