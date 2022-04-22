package com.viaversion.viaversion.libs.gson;

import java.math.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.internal.*;

public abstract class JsonElement
{
    public abstract JsonElement deepCopy();
    
    public boolean isJsonArray() {
        return this instanceof JsonArray;
    }
    
    public boolean isJsonObject() {
        return this instanceof JsonObject;
    }
    
    public boolean isJsonPrimitive() {
        return this instanceof JsonPrimitive;
    }
    
    public boolean isJsonNull() {
        return this instanceof JsonNull;
    }
    
    public JsonObject getAsJsonObject() {
        if (this.isJsonObject()) {
            return (JsonObject)this;
        }
        throw new IllegalStateException("Not a JSON Object: " + this);
    }
    
    public JsonArray getAsJsonArray() {
        if (this.isJsonArray()) {
            return (JsonArray)this;
        }
        throw new IllegalStateException("Not a JSON Array: " + this);
    }
    
    public JsonPrimitive getAsJsonPrimitive() {
        if (this.isJsonPrimitive()) {
            return (JsonPrimitive)this;
        }
        throw new IllegalStateException("Not a JSON Primitive: " + this);
    }
    
    public JsonNull getAsJsonNull() {
        if (this.isJsonNull()) {
            return (JsonNull)this;
        }
        throw new IllegalStateException("Not a JSON Null: " + this);
    }
    
    public boolean getAsBoolean() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }
    
    public Number getAsNumber() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }
    
    public String getAsString() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }
    
    public double getAsDouble() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }
    
    public float getAsFloat() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }
    
    public long getAsLong() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }
    
    public int getAsInt() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }
    
    public byte getAsByte() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }
    
    @Deprecated
    public char getAsCharacter() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }
    
    public BigDecimal getAsBigDecimal() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }
    
    public BigInteger getAsBigInteger() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }
    
    public short getAsShort() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName());
    }
    
    @Override
    public String toString() {
        final StringWriter stringWriter = new StringWriter();
        final JsonWriter jsonWriter = new JsonWriter(stringWriter);
        jsonWriter.setLenient(true);
        Streams.write(this, jsonWriter);
        return stringWriter.toString();
    }
}
