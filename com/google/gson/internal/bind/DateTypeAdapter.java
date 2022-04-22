package com.google.gson.internal.bind;

import java.text.*;
import java.util.*;
import java.io.*;
import com.google.gson.stream.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public final class DateTypeAdapter extends TypeAdapter
{
    public static final TypeAdapterFactory FACTORY;
    private final DateFormat enUsFormat;
    private final DateFormat localFormat;
    private final DateFormat iso8601Format;
    
    public DateTypeAdapter() {
        this.enUsFormat = DateFormat.getDateTimeInstance(2, 2, Locale.US);
        this.localFormat = DateFormat.getDateTimeInstance(2, 2);
        this.iso8601Format = buildIso8601Format();
    }
    
    private static DateFormat buildIso8601Format() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }
    
    @Override
    public Date read(final JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return this.deserializeToDate(jsonReader.nextString());
    }
    
    private synchronized Date deserializeToDate(final String s) {
        return this.localFormat.parse(s);
    }
    
    public synchronized void write(final JsonWriter jsonWriter, final Date date) throws IOException {
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.value(this.enUsFormat.format(date));
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
            public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
                return (typeToken.getRawType() == Date.class) ? new DateTypeAdapter() : null;
            }
        };
    }
}
