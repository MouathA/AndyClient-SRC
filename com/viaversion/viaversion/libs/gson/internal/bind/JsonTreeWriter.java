package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.stream.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.*;
import java.io.*;

public final class JsonTreeWriter extends JsonWriter
{
    private static final Writer UNWRITABLE_WRITER;
    private static final JsonPrimitive SENTINEL_CLOSED;
    private final List stack;
    private String pendingName;
    private JsonElement product;
    
    public JsonTreeWriter() {
        super(JsonTreeWriter.UNWRITABLE_WRITER);
        this.stack = new ArrayList();
        this.product = JsonNull.INSTANCE;
    }
    
    public JsonElement get() {
        if (!this.stack.isEmpty()) {
            throw new IllegalStateException("Expected one JSON element but was " + this.stack);
        }
        return this.product;
    }
    
    private JsonElement peek() {
        return this.stack.get(this.stack.size() - 1);
    }
    
    private void put(final JsonElement product) {
        if (this.pendingName != null) {
            if (!product.isJsonNull() || this.getSerializeNulls()) {
                ((JsonObject)this.peek()).add(this.pendingName, product);
            }
            this.pendingName = null;
        }
        else if (this.stack.isEmpty()) {
            this.product = product;
        }
        else {
            final JsonElement peek = this.peek();
            if (!(peek instanceof JsonArray)) {
                throw new IllegalStateException();
            }
            ((JsonArray)peek).add(product);
        }
    }
    
    @Override
    public JsonWriter beginArray() throws IOException {
        final JsonArray jsonArray = new JsonArray();
        this.put(jsonArray);
        this.stack.add(jsonArray);
        return this;
    }
    
    @Override
    public JsonWriter endArray() throws IOException {
        if (this.stack.isEmpty() || this.pendingName != null) {
            throw new IllegalStateException();
        }
        if (this.peek() instanceof JsonArray) {
            this.stack.remove(this.stack.size() - 1);
            return this;
        }
        throw new IllegalStateException();
    }
    
    @Override
    public JsonWriter beginObject() throws IOException {
        final JsonObject jsonObject = new JsonObject();
        this.put(jsonObject);
        this.stack.add(jsonObject);
        return this;
    }
    
    @Override
    public JsonWriter endObject() throws IOException {
        if (this.stack.isEmpty() || this.pendingName != null) {
            throw new IllegalStateException();
        }
        if (this.peek() instanceof JsonObject) {
            this.stack.remove(this.stack.size() - 1);
            return this;
        }
        throw new IllegalStateException();
    }
    
    @Override
    public JsonWriter name(final String pendingName) throws IOException {
        if (pendingName == null) {
            throw new NullPointerException("name == null");
        }
        if (this.stack.isEmpty() || this.pendingName != null) {
            throw new IllegalStateException();
        }
        if (this.peek() instanceof JsonObject) {
            this.pendingName = pendingName;
            return this;
        }
        throw new IllegalStateException();
    }
    
    @Override
    public JsonWriter value(final String s) throws IOException {
        if (s == null) {
            return this.nullValue();
        }
        this.put(new JsonPrimitive(s));
        return this;
    }
    
    @Override
    public JsonWriter nullValue() throws IOException {
        this.put(JsonNull.INSTANCE);
        return this;
    }
    
    @Override
    public JsonWriter value(final boolean b) throws IOException {
        this.put(new JsonPrimitive(b));
        return this;
    }
    
    @Override
    public JsonWriter value(final Boolean b) throws IOException {
        if (b == null) {
            return this.nullValue();
        }
        this.put(new JsonPrimitive(b));
        return this;
    }
    
    @Override
    public JsonWriter value(final double n) throws IOException {
        if (!this.isLenient() && (Double.isNaN(n) || Double.isInfinite(n))) {
            throw new IllegalArgumentException("JSON forbids NaN and infinities: " + n);
        }
        this.put(new JsonPrimitive(n));
        return this;
    }
    
    @Override
    public JsonWriter value(final long n) throws IOException {
        this.put(new JsonPrimitive(n));
        return this;
    }
    
    @Override
    public JsonWriter value(final Number n) throws IOException {
        if (n == null) {
            return this.nullValue();
        }
        if (!this.isLenient()) {
            final double doubleValue = n.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                throw new IllegalArgumentException("JSON forbids NaN and infinities: " + n);
            }
        }
        this.put(new JsonPrimitive(n));
        return this;
    }
    
    @Override
    public void flush() throws IOException {
    }
    
    @Override
    public void close() throws IOException {
        if (!this.stack.isEmpty()) {
            throw new IOException("Incomplete document");
        }
        this.stack.add(JsonTreeWriter.SENTINEL_CLOSED);
    }
    
    static {
        UNWRITABLE_WRITER = new Writer() {
            @Override
            public void write(final char[] array, final int n, final int n2) {
                throw new AssertionError();
            }
            
            @Override
            public void flush() throws IOException {
                throw new AssertionError();
            }
            
            @Override
            public void close() throws IOException {
                throw new AssertionError();
            }
        };
        SENTINEL_CLOSED = new JsonPrimitive("closed");
    }
}
