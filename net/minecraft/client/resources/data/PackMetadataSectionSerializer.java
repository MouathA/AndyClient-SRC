package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class PackMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer
{
    private static final String __OBFID;
    
    @Override
    public PackMetadataSection deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final IChatComponent chatComponent = (IChatComponent)jsonDeserializationContext.deserialize(asJsonObject.get("description"), IChatComponent.class);
        if (chatComponent == null) {
            throw new JsonParseException("Invalid/missing description!");
        }
        return new PackMetadataSection(chatComponent, JsonUtils.getJsonObjectIntegerFieldValue(asJsonObject, "pack_format"));
    }
    
    public JsonElement serialize(final PackMetadataSection packMetadataSection, final Type type, final JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pack_format", packMetadataSection.getPackFormat());
        jsonObject.add("description", jsonSerializationContext.serialize(packMetadataSection.func_152805_a()));
        return jsonObject;
    }
    
    @Override
    public String getSectionName() {
        return "pack";
    }
    
    @Override
    public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
        return this.serialize((PackMetadataSection)o, type, jsonSerializationContext);
    }
    
    @Override
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
    
    static {
        __OBFID = "CL_00001113";
    }
}
