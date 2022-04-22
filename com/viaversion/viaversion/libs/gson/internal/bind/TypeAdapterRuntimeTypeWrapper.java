package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.gson.reflect.*;
import java.lang.reflect.*;

final class TypeAdapterRuntimeTypeWrapper extends TypeAdapter
{
    private final Gson context;
    private final TypeAdapter delegate;
    private final Type type;
    
    TypeAdapterRuntimeTypeWrapper(final Gson context, final TypeAdapter delegate, final Type type) {
        this.context = context;
        this.delegate = delegate;
        this.type = type;
    }
    
    @Override
    public Object read(final JsonReader jsonReader) throws IOException {
        return this.delegate.read(jsonReader);
    }
    
    @Override
    public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
        TypeAdapter typeAdapter = this.delegate;
        final Type runtimeTypeIfMoreSpecific = this.getRuntimeTypeIfMoreSpecific(this.type, o);
        if (runtimeTypeIfMoreSpecific != this.type) {
            final TypeAdapter adapter = this.context.getAdapter(TypeToken.get(runtimeTypeIfMoreSpecific));
            if (!(adapter instanceof ReflectiveTypeAdapterFactory.Adapter)) {
                typeAdapter = adapter;
            }
            else if (!(this.delegate instanceof ReflectiveTypeAdapterFactory.Adapter)) {
                typeAdapter = this.delegate;
            }
            else {
                typeAdapter = adapter;
            }
        }
        typeAdapter.write(jsonWriter, o);
    }
    
    private Type getRuntimeTypeIfMoreSpecific(Type class1, final Object o) {
        if (o != null && (class1 == Object.class || class1 instanceof TypeVariable || class1 instanceof Class)) {
            class1 = o.getClass();
        }
        return class1;
    }
}
