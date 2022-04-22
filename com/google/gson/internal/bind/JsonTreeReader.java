package com.google.gson.internal.bind;

import com.google.gson.stream.*;
import java.io.*;
import com.google.gson.*;
import java.util.*;

public final class JsonTreeReader extends JsonReader
{
    private static final Reader UNREADABLE_READER;
    private static final Object SENTINEL_CLOSED;
    private final List stack;
    
    public JsonTreeReader(final JsonElement jsonElement) {
        super(JsonTreeReader.UNREADABLE_READER);
        (this.stack = new ArrayList()).add(jsonElement);
    }
    
    @Override
    public void beginArray() throws IOException {
        this.expect(JsonToken.BEGIN_ARRAY);
        this.stack.add(((JsonArray)this.peekStack()).iterator());
    }
    
    @Override
    public void endArray() throws IOException {
        this.expect(JsonToken.END_ARRAY);
        this.popStack();
        this.popStack();
    }
    
    @Override
    public void beginObject() throws IOException {
        this.expect(JsonToken.BEGIN_OBJECT);
        this.stack.add(((JsonObject)this.peekStack()).entrySet().iterator());
    }
    
    @Override
    public void endObject() throws IOException {
        this.expect(JsonToken.END_OBJECT);
        this.popStack();
        this.popStack();
    }
    
    @Override
    public boolean hasNext() throws IOException {
        final JsonToken peek = this.peek();
        return peek != JsonToken.END_OBJECT && peek != JsonToken.END_ARRAY;
    }
    
    @Override
    public JsonToken peek() throws IOException {
        if (this.stack.isEmpty()) {
            return JsonToken.END_DOCUMENT;
        }
        final Object peekStack = this.peekStack();
        if (peekStack instanceof Iterator) {
            final boolean b = this.stack.get(this.stack.size() - 2) instanceof JsonObject;
            final Iterator<Object> iterator = (Iterator<Object>)peekStack;
            if (!iterator.hasNext()) {
                return b ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
            }
            if (b) {
                return JsonToken.NAME;
            }
            this.stack.add(iterator.next());
            return this.peek();
        }
        else {
            if (peekStack instanceof JsonObject) {
                return JsonToken.BEGIN_OBJECT;
            }
            if (peekStack instanceof JsonArray) {
                return JsonToken.BEGIN_ARRAY;
            }
            if (peekStack instanceof JsonPrimitive) {
                final JsonPrimitive jsonPrimitive = (JsonPrimitive)peekStack;
                if (jsonPrimitive.isString()) {
                    return JsonToken.STRING;
                }
                if (jsonPrimitive.isBoolean()) {
                    return JsonToken.BOOLEAN;
                }
                if (jsonPrimitive.isNumber()) {
                    return JsonToken.NUMBER;
                }
                throw new AssertionError();
            }
            else {
                if (peekStack instanceof JsonNull) {
                    return JsonToken.NULL;
                }
                if (peekStack == JsonTreeReader.SENTINEL_CLOSED) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
        }
    }
    
    private Object peekStack() {
        return this.stack.get(this.stack.size() - 1);
    }
    
    private Object popStack() {
        return this.stack.remove(this.stack.size() - 1);
    }
    
    private void expect(final JsonToken jsonToken) throws IOException {
        if (this.peek() != jsonToken) {
            throw new IllegalStateException("Expected " + jsonToken + " but was " + this.peek());
        }
    }
    
    @Override
    public String nextName() throws IOException {
        this.expect(JsonToken.NAME);
        final Map.Entry<K, Object> entry = ((Iterator)this.peekStack()).next();
        this.stack.add(entry.getValue());
        return (String)entry.getKey();
    }
    
    @Override
    public String nextString() throws IOException {
        final JsonToken peek = this.peek();
        if (peek != JsonToken.STRING && peek != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected " + JsonToken.STRING + " but was " + peek);
        }
        return ((JsonPrimitive)this.popStack()).getAsString();
    }
    
    @Override
    public boolean nextBoolean() throws IOException {
        this.expect(JsonToken.BOOLEAN);
        return ((JsonPrimitive)this.popStack()).getAsBoolean();
    }
    
    @Override
    public void nextNull() throws IOException {
        this.expect(JsonToken.NULL);
        this.popStack();
    }
    
    @Override
    public double nextDouble() throws IOException {
        final JsonToken peek = this.peek();
        if (peek != JsonToken.NUMBER && peek != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + peek);
        }
        final double asDouble = ((JsonPrimitive)this.peekStack()).getAsDouble();
        if (!this.isLenient() && (Double.isNaN(asDouble) || Double.isInfinite(asDouble))) {
            throw new NumberFormatException("JSON forbids NaN and infinities: " + asDouble);
        }
        this.popStack();
        return asDouble;
    }
    
    @Override
    public long nextLong() throws IOException {
        final JsonToken peek = this.peek();
        if (peek != JsonToken.NUMBER && peek != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + peek);
        }
        final long asLong = ((JsonPrimitive)this.peekStack()).getAsLong();
        this.popStack();
        return asLong;
    }
    
    @Override
    public int nextInt() throws IOException {
        final JsonToken peek = this.peek();
        if (peek != JsonToken.NUMBER && peek != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + peek);
        }
        final int asInt = ((JsonPrimitive)this.peekStack()).getAsInt();
        this.popStack();
        return asInt;
    }
    
    @Override
    public void close() throws IOException {
        this.stack.clear();
        this.stack.add(JsonTreeReader.SENTINEL_CLOSED);
    }
    
    @Override
    public void skipValue() throws IOException {
        if (this.peek() == JsonToken.NAME) {
            this.nextName();
        }
        else {
            this.popStack();
        }
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public void promoteNameToValue() throws IOException {
        this.expect(JsonToken.NAME);
        final Map.Entry<K, Object> entry = ((Iterator)this.peekStack()).next();
        this.stack.add(entry.getValue());
        this.stack.add(new JsonPrimitive((String)entry.getKey()));
    }
    
    static {
        UNREADABLE_READER = new Reader() {
            @Override
            public int read(final char[] array, final int n, final int n2) throws IOException {
                throw new AssertionError();
            }
            
            @Override
            public void close() throws IOException {
                throw new AssertionError();
            }
        };
        SENTINEL_CLOSED = new Object();
    }
}
