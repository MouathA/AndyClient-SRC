package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.lang.reflect.*;
import java.util.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.gson.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;

final class ShowEntitySerializer extends TypeAdapter
{
    static final String TYPE = "type";
    static final String ID = "id";
    static final String NAME = "name";
    private final Gson gson;
    
    static TypeAdapter create(final Gson gson) {
        return new ShowEntitySerializer(gson).nullSafe();
    }
    
    private ShowEntitySerializer(final Gson gson) {
        this.gson = gson;
    }
    
    @Override
    public HoverEvent.ShowEntity read(final JsonReader in) throws IOException {
        in.beginObject();
        Key type = null;
        UUID fromString = null;
        Component name = null;
        while (in.hasNext()) {
            final String nextName = in.nextName();
            if (nextName.equals("type")) {
                type = (Key)this.gson.fromJson(in, SerializerFactory.KEY_TYPE);
            }
            else if (nextName.equals("id")) {
                fromString = UUID.fromString(in.nextString());
            }
            else if (nextName.equals("name")) {
                name = (Component)this.gson.fromJson(in, SerializerFactory.COMPONENT_TYPE);
            }
            else {
                in.skipValue();
            }
        }
        if (type == null || fromString == null) {
            throw new JsonParseException("A show entity hover event needs type and id fields to be deserialized");
        }
        in.endObject();
        return HoverEvent.ShowEntity.of(type, fromString, name);
    }
    
    public void write(final JsonWriter out, final HoverEvent.ShowEntity value) throws IOException {
        out.beginObject();
        out.name("type");
        this.gson.toJson(value.type(), SerializerFactory.KEY_TYPE, out);
        out.name("id");
        out.value(value.id().toString());
        final Component name = value.name();
        if (name != null) {
            out.name("name");
            this.gson.toJson(name, SerializerFactory.COMPONENT_TYPE, out);
        }
        out.endObject();
    }
    
    @Override
    public Object read(final JsonReader in) throws IOException {
        return this.read(in);
    }
    
    @Override
    public void write(final JsonWriter out, final Object value) throws IOException {
        this.write(out, (HoverEvent.ShowEntity)value);
    }
}
