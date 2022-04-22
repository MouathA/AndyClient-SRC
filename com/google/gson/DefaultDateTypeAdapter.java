package com.google.gson;

import java.text.*;
import java.util.*;
import java.lang.reflect.*;
import java.sql.*;

final class DefaultDateTypeAdapter implements JsonSerializer, JsonDeserializer
{
    private final DateFormat enUsFormat;
    private final DateFormat localFormat;
    private final DateFormat iso8601Format;
    
    DefaultDateTypeAdapter() {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }
    
    DefaultDateTypeAdapter(final String s) {
        this(new SimpleDateFormat(s, Locale.US), new SimpleDateFormat(s));
    }
    
    DefaultDateTypeAdapter(final int n) {
        this(DateFormat.getDateInstance(n, Locale.US), DateFormat.getDateInstance(n));
    }
    
    public DefaultDateTypeAdapter(final int n, final int n2) {
        this(DateFormat.getDateTimeInstance(n, n2, Locale.US), DateFormat.getDateTimeInstance(n, n2));
    }
    
    DefaultDateTypeAdapter(final DateFormat enUsFormat, final DateFormat localFormat) {
        this.enUsFormat = enUsFormat;
        this.localFormat = localFormat;
        (this.iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)).setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    
    public JsonElement serialize(final Date date, final Type type, final JsonSerializationContext jsonSerializationContext) {
        // monitorenter(localFormat = this.localFormat)
        // monitorexit(localFormat)
        return new JsonPrimitive(this.enUsFormat.format(date));
    }
    
    public Date deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!(jsonElement instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }
        final Date deserializeToDate = this.deserializeToDate(jsonElement);
        if (type == Date.class) {
            return deserializeToDate;
        }
        if (type == Timestamp.class) {
            return new Timestamp(deserializeToDate.getTime());
        }
        if (type == java.sql.Date.class) {
            return new java.sql.Date(deserializeToDate.getTime());
        }
        throw new IllegalArgumentException(this.getClass() + " cannot deserialize to " + type);
    }
    
    private Date deserializeToDate(final JsonElement jsonElement) {
        // monitorenter(localFormat = this.localFormat)
        // monitorexit(localFormat)
        return this.localFormat.parse(jsonElement.getAsString());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(DefaultDateTypeAdapter.class.getSimpleName());
        sb.append('(').append(this.localFormat.getClass().getSimpleName()).append(')');
        return sb.toString();
    }
    
    public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
        return this.serialize((Date)o, type, jsonSerializationContext);
    }
    
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
}
