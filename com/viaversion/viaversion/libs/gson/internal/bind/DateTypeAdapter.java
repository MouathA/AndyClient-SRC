package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.internal.*;
import java.io.*;
import java.text.*;
import com.viaversion.viaversion.libs.gson.internal.bind.util.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.gson.reflect.*;

public final class DateTypeAdapter extends TypeAdapter
{
    public static final TypeAdapterFactory FACTORY;
    private final List dateFormats;
    
    public DateTypeAdapter() {
        (this.dateFormats = new ArrayList()).add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
        }
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
        final Iterator<DateFormat> iterator = this.dateFormats.iterator();
        if (iterator.hasNext()) {
            return iterator.next().parse(s);
        }
        return ISO8601Utils.parse(s, new ParsePosition(0));
    }
    
    public synchronized void write(final JsonWriter jsonWriter, final Date date) throws IOException {
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.value(this.dateFormats.get(0).format(date));
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
                return (typeToken.getRawType() == Date.class) ? new DateTypeAdapter() : null;
            }
        };
    }
}
