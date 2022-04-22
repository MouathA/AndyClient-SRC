package com.mojang.authlib.properties;

import com.google.common.collect.*;
import java.lang.reflect.*;
import java.util.*;
import com.google.gson.*;

public class PropertyMap extends ForwardingMultimap
{
    private final Multimap properties;
    
    public PropertyMap() {
        this.properties = LinkedHashMultimap.create();
    }
    
    @Override
    protected Multimap delegate() {
        return this.properties;
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    public static class Serializer implements JsonSerializer, JsonDeserializer
    {
        @Override
        public PropertyMap deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final PropertyMap propertyMap = new PropertyMap();
            if (jsonElement instanceof JsonObject) {
                for (final Map.Entry<K, JsonArray> entry : ((JsonObject)jsonElement).entrySet()) {
                    if (entry.getValue() instanceof JsonArray) {
                        final Iterator iterator2 = entry.getValue().iterator();
                        while (iterator2.hasNext()) {
                            propertyMap.put(entry.getKey(), new Property((String)entry.getKey(), iterator2.next().getAsString()));
                        }
                    }
                }
            }
            else if (jsonElement instanceof JsonArray) {
                for (final JsonElement jsonElement2 : (JsonArray)jsonElement) {
                    if (jsonElement2 instanceof JsonObject) {
                        final JsonObject jsonObject = (JsonObject)jsonElement2;
                        final String asString = jsonObject.getAsJsonPrimitive("name").getAsString();
                        final String asString2 = jsonObject.getAsJsonPrimitive("value").getAsString();
                        if (jsonObject.has("signature")) {
                            propertyMap.put(asString, new Property(asString, asString2, jsonObject.getAsJsonPrimitive("signature").getAsString()));
                        }
                        else {
                            propertyMap.put(asString, new Property(asString, asString2));
                        }
                    }
                }
            }
            return propertyMap;
        }
        
        public JsonElement serialize(final PropertyMap propertyMap, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonArray jsonArray = new JsonArray();
            for (final Property property : propertyMap.values()) {
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", property.getName());
                jsonObject.addProperty("value", property.getValue());
                if (property.hasSignature()) {
                    jsonObject.addProperty("signature", property.getSignature());
                }
                jsonArray.add(jsonObject);
            }
            return jsonArray;
        }
        
        @Override
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((PropertyMap)o, type, jsonSerializationContext);
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}
