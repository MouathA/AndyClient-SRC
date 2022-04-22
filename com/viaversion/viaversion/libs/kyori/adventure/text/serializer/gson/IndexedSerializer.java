package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.gson.*;

final class IndexedSerializer extends TypeAdapter
{
    private final String name;
    private final Index map;
    
    public static TypeAdapter of(final String name, final Index map) {
        return new IndexedSerializer(name, map).nullSafe();
    }
    
    private IndexedSerializer(final String name, final Index map) {
        this.name = name;
        this.map = map;
    }
    
    @Override
    public void write(final JsonWriter out, final Object value) throws IOException {
        out.value((String)this.map.key(value));
    }
    
    @Override
    public Object read(final JsonReader in) throws IOException {
        final String nextString = in.nextString();
        final Object value = this.map.value(nextString);
        if (value != null) {
            return value;
        }
        throw new JsonParseException("invalid " + this.name + ":  " + nextString);
    }
}
