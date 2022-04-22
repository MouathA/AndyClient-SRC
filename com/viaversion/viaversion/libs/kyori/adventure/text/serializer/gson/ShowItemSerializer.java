package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.*;
import com.viaversion.viaversion.libs.gson.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;

final class ShowItemSerializer extends TypeAdapter
{
    static final String ID = "id";
    static final String COUNT = "count";
    static final String TAG = "tag";
    private final Gson gson;
    
    static TypeAdapter create(final Gson gson) {
        return new ShowItemSerializer(gson).nullSafe();
    }
    
    private ShowItemSerializer(final Gson gson) {
        this.gson = gson;
    }
    
    @Override
    public HoverEvent.ShowItem read(final JsonReader in) throws IOException {
        in.beginObject();
        Key item = null;
        BinaryTagHolder nbt = null;
        while (in.hasNext()) {
            final String nextName = in.nextName();
            if (nextName.equals("id")) {
                item = (Key)this.gson.fromJson(in, SerializerFactory.KEY_TYPE);
            }
            else if (nextName.equals("count")) {
                in.nextInt();
            }
            else if (nextName.equals("tag")) {
                final JsonToken peek = in.peek();
                if (peek == JsonToken.STRING || peek == JsonToken.NUMBER) {
                    nbt = BinaryTagHolder.of(in.nextString());
                }
                else if (peek == JsonToken.BOOLEAN) {
                    nbt = BinaryTagHolder.of(String.valueOf(in.nextBoolean()));
                }
                else {
                    if (peek != JsonToken.NULL) {
                        throw new JsonParseException("Expected tag to be a string");
                    }
                    in.nextNull();
                }
            }
            else {
                in.skipValue();
            }
        }
        if (item == null) {
            throw new JsonParseException("Not sure how to deserialize show_item hover event");
        }
        in.endObject();
        return HoverEvent.ShowItem.of(item, 1, nbt);
    }
    
    public void write(final JsonWriter out, final HoverEvent.ShowItem value) throws IOException {
        out.beginObject();
        out.name("id");
        this.gson.toJson(value.item(), SerializerFactory.KEY_TYPE, out);
        final int count = value.count();
        if (count != 1) {
            out.name("count");
            out.value(count);
        }
        final BinaryTagHolder nbt = value.nbt();
        if (nbt != null) {
            out.name("tag");
            out.value(nbt.string());
        }
        out.endObject();
    }
    
    @Override
    public Object read(final JsonReader in) throws IOException {
        return this.read(in);
    }
    
    @Override
    public void write(final JsonWriter out, final Object value) throws IOException {
        this.write(out, (HoverEvent.ShowItem)value);
    }
}
