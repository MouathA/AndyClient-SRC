package com.google.gson;

import com.google.gson.internal.*;
import java.util.*;

public final class JsonObject extends JsonElement
{
    private final LinkedTreeMap members;
    
    public JsonObject() {
        this.members = new LinkedTreeMap();
    }
    
    @Override
    JsonObject deepCopy() {
        final JsonObject jsonObject = new JsonObject();
        for (final Map.Entry<String, V> entry : this.members.entrySet()) {
            jsonObject.add(entry.getKey(), ((JsonElement)entry.getValue()).deepCopy());
        }
        return jsonObject;
    }
    
    public void add(final String s, JsonElement instance) {
        if (instance == null) {
            instance = JsonNull.INSTANCE;
        }
        this.members.put(s, instance);
    }
    
    public JsonElement remove(final String s) {
        return (JsonElement)this.members.remove(s);
    }
    
    public void addProperty(final String s, final String s2) {
        this.add(s, this.createJsonElement(s2));
    }
    
    public void addProperty(final String s, final Number n) {
        this.add(s, this.createJsonElement(n));
    }
    
    public void addProperty(final String s, final Boolean b) {
        this.add(s, this.createJsonElement(b));
    }
    
    public void addProperty(final String s, final Character c) {
        this.add(s, this.createJsonElement(c));
    }
    
    private JsonElement createJsonElement(final Object o) {
        return (o == null) ? JsonNull.INSTANCE : new JsonPrimitive(o);
    }
    
    public Set entrySet() {
        return this.members.entrySet();
    }
    
    public boolean has(final String s) {
        return this.members.containsKey(s);
    }
    
    public JsonElement get(final String s) {
        return (JsonElement)this.members.get(s);
    }
    
    public JsonPrimitive getAsJsonPrimitive(final String s) {
        return (JsonPrimitive)this.members.get(s);
    }
    
    public JsonArray getAsJsonArray(final String s) {
        return (JsonArray)this.members.get(s);
    }
    
    public JsonObject getAsJsonObject(final String s) {
        return (JsonObject)this.members.get(s);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof JsonObject && ((JsonObject)o).members.equals(this.members));
    }
    
    @Override
    public int hashCode() {
        return this.members.hashCode();
    }
    
    @Override
    JsonElement deepCopy() {
        return this.deepCopy();
    }
}
