package com.google.gson.internal.bind;

import com.google.gson.reflect.*;
import com.google.gson.*;
import java.lang.reflect.*;
import com.google.gson.internal.*;
import java.io.*;
import com.google.gson.stream.*;
import java.util.*;

public final class CollectionTypeAdapterFactory implements TypeAdapterFactory
{
    private final ConstructorConstructor constructorConstructor;
    
    public CollectionTypeAdapterFactory(final ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }
    
    public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
        final Type type = typeToken.getType();
        final Class rawType = typeToken.getRawType();
        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }
        final Type collectionElementType = $Gson$Types.getCollectionElementType(type, rawType);
        return new Adapter(gson, collectionElementType, gson.getAdapter(TypeToken.get(collectionElementType)), this.constructorConstructor.get(typeToken));
    }
    
    private static final class Adapter extends TypeAdapter
    {
        private final TypeAdapter elementTypeAdapter;
        private final ObjectConstructor constructor;
        
        public Adapter(final Gson gson, final Type type, final TypeAdapter typeAdapter, final ObjectConstructor constructor) {
            this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter, type);
            this.constructor = constructor;
        }
        
        @Override
        public Collection read(final JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            final Collection collection = (Collection)this.constructor.construct();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                collection.add(this.elementTypeAdapter.read(jsonReader));
            }
            jsonReader.endArray();
            return collection;
        }
        
        public void write(final JsonWriter jsonWriter, final Collection collection) throws IOException {
            if (collection == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginArray();
            final Iterator<Object> iterator = collection.iterator();
            while (iterator.hasNext()) {
                this.elementTypeAdapter.write(jsonWriter, iterator.next());
            }
            jsonWriter.endArray();
        }
        
        @Override
        public Object read(final JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }
        
        @Override
        public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
            this.write(jsonWriter, (Collection)o);
        }
    }
}
