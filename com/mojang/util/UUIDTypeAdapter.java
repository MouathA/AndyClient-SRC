package com.mojang.util;

import com.google.gson.*;
import java.util.*;
import java.io.*;
import com.google.gson.stream.*;

public class UUIDTypeAdapter extends TypeAdapter
{
    public void write(final JsonWriter jsonWriter, final UUID uuid) throws IOException {
        jsonWriter.value(fromUUID(uuid));
    }
    
    @Override
    public UUID read(final JsonReader jsonReader) throws IOException {
        return fromString(jsonReader.nextString());
    }
    
    public static String fromUUID(final UUID uuid) {
        return uuid.toString().replace("-", "");
    }
    
    public static UUID fromString(final String s) {
        return UUID.fromString(s.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
    }
    
    @Override
    public Object read(final JsonReader jsonReader) throws IOException {
        return this.read(jsonReader);
    }
    
    @Override
    public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
        this.write(jsonWriter, (UUID)o);
    }
}
