package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.*;
import java.util.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.gson.reflect.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.gson.internal.*;

public final class ArrayTypeAdapter extends TypeAdapter
{
    public static final TypeAdapterFactory FACTORY;
    private final Class componentType;
    private final TypeAdapter componentTypeAdapter;
    
    public ArrayTypeAdapter(final Gson gson, final TypeAdapter typeAdapter, final Class componentType) {
        this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter, componentType);
        this.componentType = componentType;
    }
    
    @Override
    public Object read(final JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        final ArrayList<Object> list = new ArrayList<Object>();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            list.add(this.componentTypeAdapter.read(jsonReader));
        }
        jsonReader.endArray();
        final int size = list.size();
        final Object instance = Array.newInstance(this.componentType, size);
        while (0 < size) {
            Array.set(instance, 0, list.get(0));
            int n = 0;
            ++n;
        }
        return instance;
    }
    
    @Override
    public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
        if (o == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.beginArray();
        while (0 < Array.getLength(o)) {
            this.componentTypeAdapter.write(jsonWriter, Array.get(o, 0));
            int n = 0;
            ++n;
        }
        jsonWriter.endArray();
    }
    
    static {
        FACTORY = new TypeAdapterFactory() {
            @Override
            public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
                final Type type = typeToken.getType();
                if (!(type instanceof GenericArrayType) && (!(type instanceof Class) || !((Class)type).isArray())) {
                    return null;
                }
                final Type arrayComponentType = $Gson$Types.getArrayComponentType(type);
                return new ArrayTypeAdapter(gson, gson.getAdapter(TypeToken.get(arrayComponentType)), $Gson$Types.getRawType(arrayComponentType));
            }
        };
    }
}