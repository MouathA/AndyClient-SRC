package com.viaversion.viaversion.libs.gson;

import java.util.*;
import java.math.*;

public final class JsonArray extends JsonElement implements Iterable
{
    private final List elements;
    
    public JsonArray() {
        this.elements = new ArrayList();
    }
    
    public JsonArray(final int n) {
        this.elements = new ArrayList(n);
    }
    
    @Override
    public JsonArray deepCopy() {
        if (!this.elements.isEmpty()) {
            final JsonArray jsonArray = new JsonArray(this.elements.size());
            final Iterator<JsonElement> iterator = this.elements.iterator();
            while (iterator.hasNext()) {
                jsonArray.add(iterator.next().deepCopy());
            }
            return jsonArray;
        }
        return new JsonArray();
    }
    
    public void add(final Boolean b) {
        this.elements.add((b == null) ? JsonNull.INSTANCE : new JsonPrimitive(b));
    }
    
    public void add(final Character c) {
        this.elements.add((c == null) ? JsonNull.INSTANCE : new JsonPrimitive(c));
    }
    
    public void add(final Number n) {
        this.elements.add((n == null) ? JsonNull.INSTANCE : new JsonPrimitive(n));
    }
    
    public void add(final String s) {
        this.elements.add((s == null) ? JsonNull.INSTANCE : new JsonPrimitive(s));
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
    
    public JsonElement set(final int n, final JsonElement jsonElement) {
        return this.elements.set(n, jsonElement);
    }
    
    public boolean remove(final JsonElement jsonElement) {
        return this.elements.remove(jsonElement);
    }
    
    public JsonElement remove(final int n) {
        return this.elements.remove(n);
    }
    
    public boolean contains(final JsonElement jsonElement) {
        return this.elements.contains(jsonElement);
    }
    
    public int size() {
        return this.elements.size();
    }
    
    public boolean isEmpty() {
        return this.elements.isEmpty();
    }
    
    @Override
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
    public JsonElement deepCopy() {
        return this.deepCopy();
    }
}
