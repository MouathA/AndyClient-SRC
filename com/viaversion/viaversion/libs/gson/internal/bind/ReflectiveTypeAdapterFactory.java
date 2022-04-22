package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.internal.reflect.*;
import com.viaversion.viaversion.libs.gson.reflect.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.gson.annotations.*;
import java.io.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.gson.internal.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import java.util.*;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory
{
    private final ConstructorConstructor constructorConstructor;
    private final FieldNamingStrategy fieldNamingPolicy;
    private final Excluder excluder;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    private final ReflectionAccessor accessor;
    
    public ReflectiveTypeAdapterFactory(final ConstructorConstructor constructorConstructor, final FieldNamingStrategy fieldNamingPolicy, final Excluder excluder, final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory) {
        this.accessor = ReflectionAccessor.getInstance();
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingPolicy;
        this.excluder = excluder;
        this.jsonAdapterFactory = jsonAdapterFactory;
    }
    
    public boolean excludeField(final Field field, final boolean b) {
        return excludeField(field, b, this.excluder);
    }
    
    static boolean excludeField(final Field field, final boolean b, final Excluder excluder) {
        return !excluder.excludeClass(field.getType(), b) && !excluder.excludeField(field, b);
    }
    
    private List getFieldNames(final Field field) {
        final SerializedName serializedName = field.getAnnotation(SerializedName.class);
        if (serializedName == null) {
            return Collections.singletonList(this.fieldNamingPolicy.translateName(field));
        }
        final String value = serializedName.value();
        final String[] alternate = serializedName.alternate();
        if (alternate.length == 0) {
            return Collections.singletonList(value);
        }
        final ArrayList list = new ArrayList<String>(alternate.length + 1);
        list.add(value);
        final String[] array = alternate;
        while (0 < array.length) {
            list.add(array[0]);
            int n = 0;
            ++n;
        }
        return list;
    }
    
    @Override
    public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
        final Class rawType = typeToken.getRawType();
        if (!Object.class.isAssignableFrom(rawType)) {
            return null;
        }
        return new Adapter(this.constructorConstructor.get(typeToken), this.getBoundFields(gson, typeToken, rawType));
    }
    
    private BoundField createBoundField(final Gson gson, final Field field, final String s, final TypeToken typeToken, final boolean b, final boolean b2) {
        final boolean primitive = Primitives.isPrimitive(typeToken.getRawType());
        final JsonAdapter jsonAdapter = field.getAnnotation(JsonAdapter.class);
        TypeAdapter typeAdapter = null;
        if (jsonAdapter != null) {
            typeAdapter = this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, gson, typeToken, jsonAdapter);
        }
        final boolean b3 = typeAdapter != null;
        if (typeAdapter == null) {
            typeAdapter = gson.getAdapter(typeToken);
        }
        return new BoundField(s, b, b2, field, b3, typeAdapter, gson, typeToken, primitive) {
            final Field val$field;
            final boolean val$jsonAdapterPresent;
            final TypeAdapter val$typeAdapter;
            final Gson val$context;
            final TypeToken val$fieldType;
            final boolean val$isPrimitive;
            final ReflectiveTypeAdapterFactory this$0;
            
            @Override
            void write(final JsonWriter jsonWriter, final Object o) throws IOException, IllegalAccessException {
                (this.val$jsonAdapterPresent ? this.val$typeAdapter : new TypeAdapterRuntimeTypeWrapper(this.val$context, this.val$typeAdapter, this.val$fieldType.getType())).write(jsonWriter, this.val$field.get(o));
            }
            
            @Override
            void read(final JsonReader jsonReader, final Object o) throws IOException, IllegalAccessException {
                final Object read = this.val$typeAdapter.read(jsonReader);
                if (read != null || !this.val$isPrimitive) {
                    this.val$field.set(o, read);
                }
            }
            
            public boolean writeField(final Object o) throws IOException, IllegalAccessException {
                return this.serialized && this.val$field.get(o) != o;
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
                this.excludeField(field, true);
                final boolean excludeField = this.excludeField(field, false);
                if (false || excludeField) {
                    this.accessor.makeAccessible(field);
                    final Type resolve = $Gson$Types.resolve(value.getType(), rawType, field.getGenericType());
                    final List fieldNames = this.getFieldNames(field);
                    BoundField boundField = null;
                    while (0 < fieldNames.size()) {
                        final String s = fieldNames.get(0);
                        if (false) {}
                        final BoundField boundField2 = linkedHashMap.put(s, this.createBoundField(gson, field, s, TypeToken.get(resolve), false, excludeField));
                        if (boundField == null) {
                            boundField = boundField2;
                        }
                        int n = 0;
                        ++n;
                    }
                    if (boundField != null) {
                        throw new IllegalArgumentException(type + " declares multiple JSON fields named " + boundField.name);
                    }
                }
                int n2 = 0;
                ++n2;
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
        
        Adapter(final ObjectConstructor constructor, final Map boundFields) {
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
                if (boundField.writeField(o)) {
                    jsonWriter.name(boundField.name);
                    boundField.write(jsonWriter, o);
                }
            }
            jsonWriter.endObject();
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
        
        abstract boolean writeField(final Object p0) throws IOException, IllegalAccessException;
        
        abstract void write(final JsonWriter p0, final Object p1) throws IOException, IllegalAccessException;
        
        abstract void read(final JsonReader p0, final Object p1) throws IOException, IllegalAccessException;
    }
}
