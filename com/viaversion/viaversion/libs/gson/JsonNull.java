package com.viaversion.viaversion.libs.gson;

public final class JsonNull extends JsonElement
{
    public static final JsonNull INSTANCE;
    
    @Deprecated
    public JsonNull() {
    }
    
    @Override
    public JsonNull deepCopy() {
        return JsonNull.INSTANCE;
    }
    
    @Override
    public int hashCode() {
        return JsonNull.class.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || o instanceof JsonNull;
    }
    
    @Override
    public JsonElement deepCopy() {
        return this.deepCopy();
    }
    
    static {
        INSTANCE = new JsonNull();
    }
}
