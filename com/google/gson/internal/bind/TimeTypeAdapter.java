package com.google.gson.internal.bind;

import java.text.*;
import java.sql.*;
import java.io.*;
import com.google.gson.stream.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public final class TimeTypeAdapter extends TypeAdapter
{
    public static final TypeAdapterFactory FACTORY;
    private final DateFormat format;
    
    public TimeTypeAdapter() {
        this.format = new SimpleDateFormat("hh:mm:ss a");
    }
    
    @Override
    public synchronized Time read(final JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return new Time(this.format.parse(jsonReader.nextString()).getTime());
    }
    
    public synchronized void write(final JsonWriter jsonWriter, final Time time) throws IOException {
        jsonWriter.value((time == null) ? null : this.format.format(time));
    }
    
    @Override
    public Object read(final JsonReader jsonReader) throws IOException {
        return this.read(jsonReader);
    }
    
    @Override
    public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
        this.write(jsonWriter, (Time)o);
    }
    
    static {
        FACTORY = new TypeAdapterFactory() {
            public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
                return (typeToken.getRawType() == Time.class) ? new TimeTypeAdapter() : null;
            }
        };
    }
}
