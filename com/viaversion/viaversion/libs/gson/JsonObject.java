package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.*;
import java.util.*;

public final class JsonObject extends JsonElement
{
    private final LinkedTreeMap members;
    
    public JsonObject() {
        this.members = new LinkedTreeMap();
    }
    
    @Override
    public JsonObject deepCopy() {
        final JsonObject jsonObject = new JsonObject();
        for (final Map.Entry<String, V> entry : this.members.entrySet()) {
            jsonObject.add(entry.getKey(), ((JsonElement)entry.getValue()).deepCopy());
        }
        return jsonObject;
    }
    
    public void add(final String s, final JsonElement jsonElement) {
        this.members.put(s, (jsonElement == null) ? JsonNull.INSTANCE : jsonElement);
    }
    
    public JsonElement remove(final String s) {
        return (JsonElement)this.members.remove(s);
    }
    
    public void addProperty(final String s, final String s2) {
        this.add(s, (s2 == null) ? JsonNull.INSTANCE : new JsonPrimitive(s2));
    }
    
    public void addProperty(final String s, final Number n) {
        this.add(s, (n == null) ? JsonNull.INSTANCE : new JsonPrimitive(n));
    }
    
    public void addProperty(final String s, final Boolean b) {
        this.add(s, (b == null) ? JsonNull.INSTANCE : new JsonPrimitive(b));
    }
    
    public void addProperty(final String s, final Character c) {
        this.add(s, (c == null) ? JsonNull.INSTANCE : new JsonPrimitive(c));
    }
    
    public Set entrySet() {
        return this.members.entrySet();
    }
    
    public Set keySet() {
        return this.members.keySet();
    }
    
    public int size() {
        return this.members.size();
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
    public JsonElement deepCopy() {
        return this.deepCopy();
    }
}
