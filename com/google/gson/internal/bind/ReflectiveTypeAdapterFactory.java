package com.google.gson.internal.bind;

import com.google.gson.annotations.*;
import com.google.gson.reflect.*;
import com.google.gson.*;
import java.lang.reflect.*;
import java.io.*;
import com.google.gson.internal.*;
import com.google.gson.stream.*;
import java.util.*;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory
{
    private final ConstructorConstructor constructorConstructor;
    private final FieldNamingStrategy fieldNamingPolicy;
    private final Excluder excluder;
    
    public ReflectiveTypeAdapterFactory(final ConstructorConstructor constructorConstructor, final FieldNamingStrategy fieldNamingPolicy, final Excluder excluder) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingPolicy;
        this.excluder = excluder;
    }
    
    public boolean excludeField(final Field field, final boolean b) {
        return !this.excluder.excludeClass(field.getType(), b) && !this.excluder.excludeField(field, b);
    }
    
    private String getFieldName(final Field field) {
        final SerializedName serializedName = field.getAnnotation(SerializedName.class);
        return (serializedName == null) ? this.fieldNamingPolicy.translateName(field) : serializedName.value();
    }
    
    public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
        final Class rawType = typeToken.getRawType();
        if (!Object.class.isAssignableFrom(rawType)) {
            return null;
        }
        return new Adapter(this.constructorConstructor.get(typeToken), this.getBoundFields(gson, typeToken, rawType), null);
    }
    
    private BoundField createBoundField(final Gson gson, final Field field, final String s, final TypeToken typeToken, final boolean b, final boolean b2) {
        return new BoundField(s, b, b2, gson, typeToken, field, Primitives.isPrimitive(typeToken.getRawType())) {
            final TypeAdapter typeAdapter = this.val$context.getAdapter(this.val$fieldType);
            final Gson val$context;
            final TypeToken val$fieldType;
            final Field val$field;
            final boolean val$isPrimitive;
            final ReflectiveTypeAdapterFactory this$0;
            
            @Override
            void write(final JsonWriter jsonWriter, final Object o) throws IOException, IllegalAccessException {
                new TypeAdapterRuntimeTypeWrapper(this.val$context, this.typeAdapter, this.val$fieldType.getType()).write(jsonWriter, this.val$field.get(o));
            }
            
            @Override
            void read(final JsonReader jsonReader, final Object o) throws IOException, IllegalAccessException {
                final Object read = this.typeAdapter.read(jsonReader);
                if (read != null || !this.val$isPrimitive) {
                    this.val$field.set(o, read);
                }
            }
        };
    }
    
    private Map getBoundFields(final Gson gson, TypeToken value, Class rawType) {
        final LinkedHashMap<String, BoundField> linkedHashMap = new LinkedHashMap<String, BoundField>();
        if (rawType.isInterface()) {
            return linkedHashMap;
        }
        final Type type = value.getType();
        while (rawType != Object.class) {
            final Field[] declaredFields = rawType.getDeclaredFields();
            while (0 < declaredFields.length) {
                final Field field = declaredFields[0];
                final boolean excludeField = this.excludeField(field, true);
                final boolean excludeField2 = this.excludeField(field, false);
                if (excludeField || excludeField2) {
                    field.setAccessible(true);
                    final BoundField boundField = this.createBoundField(gson, field, this.getFieldName(field), TypeToken.get($Gson$Types.resolve(value.getType(), rawType, field.getGenericType())), excludeField, excludeField2);
                    final BoundField boundField2 = linkedHashMap.put(boundField.name, boundField);
                    if (boundField2 != null) {
                        throw new IllegalArgumentException(type + " declares multiple JSON fields named " + boundField2.name);
                    }
                }
                int n = 0;
                ++n;
            }
            value = TypeToken.get($Gson$Types.resolve(value.getType(), rawType, rawType.getGenericSuperclass()));
            rawType = value.getRawType();
        }
        return linkedHashMap;
    }
    
    public static final class Adapter extends TypeAdapter
    {
        private final ObjectConstructor constructor;
        private final Map boundFields;
        
        private Adapter(final ObjectConstructor constructor, final Map boundFields) {
            this.constructor = constructor;
            this.boundFields = boundFields;
        }
        
        @Override
        public Object read(final JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            final Object construct = this.constructor.construct();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                final BoundField boundField = this.boundFields.get(jsonReader.nextName());
                if (boundField == null || !boundField.deserialized) {
                    jsonReader.skipValue();
                }
                else {
                    boundField.read(jsonReader, construct);
                }
            }
            jsonReader.endObject();
            return construct;
        }
        
        @Override
        public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
            if (o == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            for (final BoundField boundField : this.boundFields.values()) {
                if (boundField.serialized) {
                    jsonWriter.name(boundField.name);
                    boundField.write(jsonWriter, o);
                }
            }
            jsonWriter.endObject();
        }
        
        Adapter(final ObjectConstructor objectConstructor, final Map map, final ReflectiveTypeAdapterFactory$1 boundField) {
            this(objectConstructor, map);
        }
    }
    
    abstract static class BoundField
    {
        final String name;
        final boolean serialized;
        final boolean deserialized;
        
        protected BoundField(final String name, final boolean serialized, final boolean deserialized) {
            this.name = name;
            this.serialized = serialized;
            this.deserialized = deserialized;
        }
        
        abstract void write(final JsonWriter p0, final Object p1) throws IOException, IllegalAccessException;
        
        abstract void read(final JsonReader p0, final Object p1) throws IOException, IllegalAccessException;
    }
}
