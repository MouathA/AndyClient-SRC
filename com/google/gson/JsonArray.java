package com.google.gson;

import java.util.*;
import java.math.*;

public final class JsonArray extends JsonElement implements Iterable
{
    private final List elements;
    
    public JsonArray() {
        this.elements = new ArrayList();
    }
    
    @Override
    JsonArray deepCopy() {
        final JsonArray jsonArray = new JsonArray();
        final Iterator<JsonElement> iterator = this.elements.iterator();
        while (iterator.hasNext()) {
            jsonArray.add(iterator.next().deepCopy());
        }
        return jsonArray;
    }
    
    public void add(JsonElement instance) {
        if (instance == null) {
            instance = JsonNull.INSTANCE;
        }
        this.elements.add(instance);
    }
    
    public void addAll(final JsonArray jsonArray) {
        this.elements.addAll(jsonArray.elements);
    }
    
    public int size() {
        return this.elements.size();
    }
    
    public Iterator iterator() {
        return this.elements.iterator();
    }
    
    public JsonElement get(final int n) {
        return this.elements.get(n);
    }
    
    @Override
    public Number getAsNumber() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsNumber();
        }
        throw new IllegalStateException();
    }
    
    @Override
    public String getAsString() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsString();
        }
        throw new IllegalStateException();
    }
    
    @Override
    public double getAsDouble() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsDouble();
        }
        throw new IllegalStateException();
    }
    
    @Override
    public BigDecimal getAsBigDecimal() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsBigDecimal();
        }
        throw new IllegalStateException();
    }
    
    @Override
    public BigInteger getAsBigInteger() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsBigInteger();
        }
        throw new IllegalStateException();
    }
    
    @Override
    public float getAsFloat() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsFloat();
        }
        throw new IllegalStateException();
    }
    
    @Override
    public long getAsLong() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsLong();
        }
        throw new IllegalStateException();
    }
    
    @Override
    public int getAsInt() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsInt();
        }
        throw new IllegalStateException();
    }
    
    @Override
    public byte getAsByte() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsByte();
        }
        throw new IllegalStateException();
    }
    
    @Override
    public char getAsCharacter() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsCharacter();
        }
        throw new IllegalStateException();
    }
    
    @Override
    public short getAsShort() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsShort();
        }
        throw new IllegalStateException();
    }
    
    @Override
    public boolean getAsBoolean() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsBoolean();
        }
        throw new IllegalStateException();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof JsonArray && ((JsonArray)o).elements.equals(this.elements));
    }
    
    @Override
    public int hashCode() {
        return this.elements.hashCode();
    }
    
    @Override
    JsonElement deepCopy() {
        return this.deepCopy();
    }
}
