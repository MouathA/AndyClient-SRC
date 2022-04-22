package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.*;
import java.sql.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import java.text.*;
import com.viaversion.viaversion.libs.gson.internal.bind.util.*;
import java.util.*;

final class DefaultDateTypeAdapter extends TypeAdapter
{
    private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
    private final Class dateType;
    private final List dateFormats;
    
    DefaultDateTypeAdapter(final Class clazz) {
        this.dateFormats = new ArrayList();
        this.dateType = verifyDateType(clazz);
        this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
        }
    }
    
    DefaultDateTypeAdapter(final Class clazz, final String s) {
        this.dateFormats = new ArrayList();
        this.dateType = verifyDateType(clazz);
        this.dateFormats.add(new SimpleDateFormat(s, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(new SimpleDateFormat(s));
        }
    }
    
    DefaultDateTypeAdapter(final Class clazz, final int n) {
        this.dateFormats = new ArrayList();
        this.dateType = verifyDateType(clazz);
        this.dateFormats.add(DateFormat.getDateInstance(n, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(DateFormat.getDateInstance(n));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.dateFormats.add(PreJava9DateFormatProvider.getUSDateFormat(n));
        }
    }
    
    public DefaultDateTypeAdapter(final int n, final int n2) {
        this(Date.class, n, n2);
    }
    
    public DefaultDateTypeAdapter(final Class clazz, final int n, final int n2) {
        this.dateFormats = new ArrayList();
        this.dateType = verifyDateType(clazz);
        this.dateFormats.add(DateFormat.getDateTimeInstance(n, n2, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(DateFormat.getDateTimeInstance(n, n2));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(n, n2));
        }
    }
    
    private static Class verifyDateType(final Class clazz) {
        if (clazz != Date.class && clazz != java.sql.Date.class && clazz != Timestamp.class) {
            throw new IllegalArgumentException("Date type must be one of " + Date.class + ", " + Timestamp.class + ", or " + java.sql.Date.class + " but was " + clazz);
        }
        return clazz;
    }
    
    public void write(final JsonWriter jsonWriter, final Date date) throws IOException {
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        // monitorenter(dateFormats = this.dateFormats)
        jsonWriter.value(this.dateFormats.get(0).format(date));
    }
    // monitorexit(dateFormats)
    
    @Override
    public Date read(final JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        final Date deserializeToDate = this.deserializeToDate(jsonReader.nextString());
        if (this.dateType == Date.class) {
            return deserializeToDate;
        }
        if (this.dateType == Timestamp.class) {
            return new Timestamp(deserializeToDate.getTime());
        }
        if (this.dateType == java.sql.Date.class) {
            return new java.sql.Date(deserializeToDate.getTime());
        }
        throw new AssertionError();
    }
    
    private Date deserializeToDate(final String s) {
        // monitorenter(dateFormats = this.dateFormats)
        final Iterator<DateFormat> iterator = (Iterator<DateFormat>)this.dateFormats.iterator();
        if (iterator.hasNext()) {
            // monitorexit(dateFormats)
            return iterator.next().parse(s);
        }
        // monitorexit(dateFormats)
        return ISO8601Utils.parse(s, new ParsePosition(0));
    }
    
    @Override
    public String toString() {
        final DateFormat dateFormat = this.dateFormats.get(0);
        if (dateFormat instanceof SimpleDateFormat) {
            return "DefaultDateTypeAdapter(" + ((SimpleDateFormat)dateFormat).toPattern() + ')';
        }
        return "DefaultDateTypeAdapter(" + ((SimpleDateFormat)dateFormat).getClass().getSimpleName() + ')';
    }
    
    @Override
    public Object read(final JsonReader jsonReader) throws IOException {
        return this.read(jsonReader);
    }
    
    @Override
    public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
        this.write(jsonWriter, (Date)o);
    }
}
