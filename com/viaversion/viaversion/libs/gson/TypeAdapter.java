package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.stream.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.internal.bind.*;

public abstract class TypeAdapter
{
    public abstract void write(final JsonWriter p0, final Object p1) throws IOException;
    
    public final void toJson(final Writer writer, final Object o) throws IOException {
        this.write(new JsonWriter(writer), o);
    }
    
    public final TypeAdapter nullSafe() {
        return new TypeAdapter() {
            final TypeAdapter this$0;
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                if (o == null) {
                    jsonWriter.nullValue();
                }
                else {
                    this.this$0.write(jsonWriter, o);
                }
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return this.this$0.read(jsonReader);
            }
        };
    }
    
    public final String toJson(final Object o) {
        final StringWriter stringWriter = new StringWriter();
        this.toJson(stringWriter, o);
        return stringWriter.toString();
    }
    
    public final JsonElement toJsonTree(final Object o) {
        final JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
        this.write(jsonTreeWriter, o);
        return jsonTreeWriter.get();
    }
    
    public abstract Object read(final JsonReader p0) throws IOException;
    
    public final Object fromJson(final Reader reader) throws IOException {
        return this.read(new JsonReader(reader));
    }
    
    public final Object fromJson(final String s) throws IOException {
        return this.fromJson(new StringReader(s));
    }
    
    public final Object fromJsonTree(final JsonElement jsonElement) {
        return this.read(new JsonTreeReader(jsonElement));
    }
}
