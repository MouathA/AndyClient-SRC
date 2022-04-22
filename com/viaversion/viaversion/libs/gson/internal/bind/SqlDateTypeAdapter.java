package com.viaversion.viaversion.libs.gson.internal.bind;

import java.text.*;
import java.sql.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.gson.reflect.*;

public final class SqlDateTypeAdapter extends TypeAdapter
{
    public static final TypeAdapterFactory FACTORY;
    private final DateFormat format;
    
    public SqlDateTypeAdapter() {
        this.format = new SimpleDateFormat("MMM d, yyyy");
    }
    
    @Override
    public synchronized Date read(final JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return new Date(this.format.parse(jsonReader.nextString()).getTime());
    }
    
    public synchronized void write(final JsonWriter jsonWriter, final Date date) throws IOException {
        jsonWriter.value((date == null) ? null : this.format.format(date));
    }
    
    @Override
    public Object read(final JsonReader jsonReader) throws IOException {
        return this.read(jsonReader);
    }
    
    @Override
    public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
        this.write(jsonWriter, (Date)o);
    }
    
    static {
        FACTORY = new TypeAdapterFactory() {
            @Override
            public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
                return (typeToken.getRawType() == Date.class) ? new SqlDateTypeAdapter() : null;
            }
        };
    }
}
