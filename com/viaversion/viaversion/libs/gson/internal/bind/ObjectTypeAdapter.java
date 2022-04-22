package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.internal.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.gson.reflect.*;

public final class ObjectTypeAdapter extends TypeAdapter
{
    public static final TypeAdapterFactory FACTORY;
    private final Gson gson;
    
    ObjectTypeAdapter(final Gson gson) {
        this.gson = gson;
    }
    
    @Override
    public Object read(final JsonReader jsonReader) throws IOException {
        switch (jsonReader.peek()) {
            case BEGIN_ARRAY: {
                final ArrayList<Object> list = new ArrayList<Object>();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    list.add(this.read(jsonReader));
                }
                jsonReader.endArray();
                return list;
            }
            case BEGIN_OBJECT: {
                final LinkedTreeMap linkedTreeMap = new LinkedTreeMap();
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    linkedTreeMap.put(jsonReader.nextName(), this.read(jsonReader));
                }
                jsonReader.endObject();
                return linkedTreeMap;
            }
            case STRING: {
                return jsonReader.nextString();
            }
            case NUMBER: {
                return jsonReader.nextDouble();
            }
            case BOOLEAN: {
                return jsonReader.nextBoolean();
            }
            case NULL: {
                jsonReader.nextNull();
                return null;
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }
    
    @Override
    public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
        if (o == null) {
            jsonWriter.nullValue();
            return;
        }
        final TypeAdapter adapter = this.gson.getAdapter(o.getClass());
        if (adapter instanceof ObjectTypeAdapter) {
            jsonWriter.beginObject();
            jsonWriter.endObject();
            return;
        }
        adapter.write(jsonWriter, o);
    }
    
    static {
        FACTORY = new TypeAdapterFactory() {
            @Override
            public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
                if (typeToken.getRawType() == Object.class) {
                    return new ObjectTypeAdapter(gson);
                }
                return null;
            }
        };
    }
}
