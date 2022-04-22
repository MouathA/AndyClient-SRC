package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;

final class KeySerializer extends TypeAdapter
{
    static final TypeAdapter INSTANCE;
    
    private KeySerializer() {
    }
    
    public void write(final JsonWriter out, final Key value) throws IOException {
        out.value(value.asString());
    }
    
    @Override
    public Key read(final JsonReader in) throws IOException {
        return Key.key(in.nextString());
    }
    
    @Override
    public Object read(final JsonReader in) throws IOException {
        return this.read(in);
    }
    
    @Override
    public void write(final JsonWriter out, final Object value) throws IOException {
        this.write(out, (Key)value);
    }
    
    static {
        INSTANCE = new KeySerializer().nullSafe();
    }
}
