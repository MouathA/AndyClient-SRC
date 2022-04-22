package com.google.gson;

import java.lang.reflect.*;
import java.math.*;
import com.google.gson.stream.*;
import com.google.gson.reflect.*;
import java.util.*;
import com.google.gson.internal.*;
import java.io.*;
import com.google.gson.internal.bind.*;

public final class Gson
{
    static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
    private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
    private final ThreadLocal calls;
    private final Map typeTokenCache;
    private final List factories;
    private final ConstructorConstructor constructorConstructor;
    private final boolean serializeNulls;
    private final boolean htmlSafe;
    private final boolean generateNonExecutableJson;
    private final boolean prettyPrinting;
    final JsonDeserializationContext deserializationContext;
    final JsonSerializationContext serializationContext;
    
    public Gson() {
        this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, LongSerializationPolicy.DEFAULT, Collections.emptyList());
    }
    
    Gson(final Excluder excluder, final FieldNamingStrategy fieldNamingStrategy, final Map map, final boolean serializeNulls, final boolean b, final boolean generateNonExecutableJson, final boolean htmlSafe, final boolean prettyPrinting, final boolean b2, final LongSerializationPolicy longSerializationPolicy, final List list) {
        this.calls = new ThreadLocal();
        this.typeTokenCache = Collections.synchronizedMap(new HashMap<Object, Object>());
        this.deserializationContext = new JsonDeserializationContext() {
            final Gson this$0;
            
            public Object deserialize(final JsonElement jsonElement, final Type type) throws JsonParseException {
                return this.this$0.fromJson(jsonElement, type);
            }
        };
        this.serializationContext = new JsonSerializationContext() {
            final Gson this$0;
            
            public JsonElement serialize(final Object o) {
                return this.this$0.toJsonTree(o);
            }
            
            public JsonElement serialize(final Object o, final Type type) {
                return this.this$0.toJsonTree(o, type);
            }
        };
        this.constructorConstructor = new ConstructorConstructor(map);
        this.serializeNulls = serializeNulls;
        this.generateNonExecutableJson = generateNonExecutableJson;
        this.htmlSafe = htmlSafe;
        this.prettyPrinting = prettyPrinting;
        final ArrayList<Object> list2 = new ArrayList<Object>();
        list2.add(TypeAdapters.JSON_ELEMENT_FACTORY);
        list2.add(ObjectTypeAdapter.FACTORY);
        list2.add(excluder);
        list2.addAll(list);
        list2.add(TypeAdapters.STRING_FACTORY);
        list2.add(TypeAdapters.INTEGER_FACTORY);
        list2.add(TypeAdapters.BOOLEAN_FACTORY);
        list2.add(TypeAdapters.BYTE_FACTORY);
        list2.add(TypeAdapters.SHORT_FACTORY);
        list2.add(TypeAdapters.newFactory(Long.TYPE, Long.class, this.longAdapter(longSerializationPolicy)));
        list2.add(TypeAdapters.newFactory(Double.TYPE, Double.class, this.doubleAdapter(b2)));
        list2.add(TypeAdapters.newFactory(Float.TYPE, Float.class, this.floatAdapter(b2)));
        list2.add(TypeAdapters.NUMBER_FACTORY);
        list2.add(TypeAdapters.CHARACTER_FACTORY);
        list2.add(TypeAdapters.STRING_BUILDER_FACTORY);
        list2.add(TypeAdapters.STRING_BUFFER_FACTORY);
        list2.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        list2.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        list2.add(TypeAdapters.URL_FACTORY);
        list2.add(TypeAdapters.URI_FACTORY);
        list2.add(TypeAdapters.UUID_FACTORY);
        list2.add(TypeAdapters.LOCALE_FACTORY);
        list2.add(TypeAdapters.INET_ADDRESS_FACTORY);
        list2.add(TypeAdapters.BIT_SET_FACTORY);
        list2.add(DateTypeAdapter.FACTORY);
        list2.add(TypeAdapters.CALENDAR_FACTORY);
        list2.add(TimeTypeAdapter.FACTORY);
        list2.add(SqlDateTypeAdapter.FACTORY);
        list2.add(TypeAdapters.TIMESTAMP_FACTORY);
        list2.add(ArrayTypeAdapter.FACTORY);
        list2.add(TypeAdapters.ENUM_FACTORY);
        list2.add(TypeAdapters.CLASS_FACTORY);
        list2.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
        list2.add(new MapTypeAdapterFactory(this.constructorConstructor, b));
        list2.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy, excluder));
        this.factories = Collections.unmodifiableList((List<?>)list2);
    }
    
    private TypeAdapter doubleAdapter(final boolean b) {
        if (b) {
            return TypeAdapters.DOUBLE;
        }
        return new TypeAdapter() {
            final Gson this$0;
            
            @Override
            public Double read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextDouble();
            }
            
            public void write(final JsonWriter jsonWriter, final Number n) throws IOException {
                if (n == null) {
                    jsonWriter.nullValue();
                    return;
                }
                Gson.access$000(this.this$0, n.doubleValue());
                jsonWriter.value(n);
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (Number)o);
            }
        };
    }
    
    private TypeAdapter floatAdapter(final boolean b) {
        if (b) {
            return TypeAdapters.FLOAT;
        }
        return new TypeAdapter() {
            final Gson this$0;
            
            @Override
            public Float read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return (float)jsonReader.nextDouble();
            }
            
            public void write(final JsonWriter jsonWriter, final Number n) throws IOException {
                if (n == null) {
                    jsonWriter.nullValue();
                    return;
                }
                Gson.access$000(this.this$0, n.floatValue());
                jsonWriter.value(n);
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (Number)o);
            }
        };
    }
    
    private void checkValidFloatingPoint(final double n) {
        if (Double.isNaN(n) || Double.isInfinite(n)) {
            throw new IllegalArgumentException(n + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }
    
    private TypeAdapter longAdapter(final LongSerializationPolicy longSerializationPolicy) {
        if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
            return TypeAdapters.LONG;
        }
        return new TypeAdapter() {
            final Gson this$0;
            
            @Override
            public Number read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextLong();
            }
            
            public void write(final JsonWriter jsonWriter, final Number n) throws IOException {
                if (n == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.value(n.toString());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (Number)o);
            }
        };
    }
    
    public TypeAdapter getAdapter(final TypeToken typeToken) {
        final TypeAdapter typeAdapter = this.typeTokenCache.get(typeToken);
        if (typeAdapter != null) {
            return typeAdapter;
        }
        Map<?, FutureTypeAdapter> map = this.calls.get();
        if (map == null) {
            map = new HashMap<Object, FutureTypeAdapter>();
            this.calls.set(map);
        }
        final FutureTypeAdapter futureTypeAdapter = map.get(typeToken);
        if (futureTypeAdapter != null) {
            return futureTypeAdapter;
        }
        final FutureTypeAdapter futureTypeAdapter2 = new FutureTypeAdapter();
        map.put(typeToken, futureTypeAdapter2);
        final Iterator<TypeAdapterFactory> iterator = (Iterator<TypeAdapterFactory>)this.factories.iterator();
        while (iterator.hasNext()) {
            final TypeAdapter create = iterator.next().create(this, typeToken);
            if (create != null) {
                futureTypeAdapter2.setDelegate(create);
                this.typeTokenCache.put(typeToken, create);
                final TypeAdapter typeAdapter2 = create;
                map.remove(typeToken);
                this.calls.remove();
                return typeAdapter2;
            }
        }
        throw new IllegalArgumentException("GSON cannot handle " + typeToken);
    }
    
    public TypeAdapter getDelegateAdapter(final TypeAdapterFactory typeAdapterFactory, final TypeToken typeToken) {
        final Iterator<TypeAdapterFactory> iterator = this.factories.iterator();
        while (iterator.hasNext()) {
            final TypeAdapter create = iterator.next().create(this, typeToken);
            if (create != null) {
                return create;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + typeToken);
    }
    
    public TypeAdapter getAdapter(final Class clazz) {
        return this.getAdapter(TypeToken.get(clazz));
    }
    
    public JsonElement toJsonTree(final Object o) {
        if (o == null) {
            return JsonNull.INSTANCE;
        }
        return this.toJsonTree(o, o.getClass());
    }
    
    public JsonElement toJsonTree(final Object o, final Type type) {
        final JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
        this.toJson(o, type, jsonTreeWriter);
        return jsonTreeWriter.get();
    }
    
    public String toJson(final Object o) {
        if (o == null) {
            return this.toJson(JsonNull.INSTANCE);
        }
        return this.toJson(o, o.getClass());
    }
    
    public String toJson(final Object o, final Type type) {
        final StringWriter stringWriter = new StringWriter();
        this.toJson(o, type, stringWriter);
        return stringWriter.toString();
    }
    
    public void toJson(final Object o, final Appendable appendable) throws JsonIOException {
        if (o != null) {
            this.toJson(o, o.getClass(), appendable);
        }
        else {
            this.toJson(JsonNull.INSTANCE, appendable);
        }
    }
    
    public void toJson(final Object o, final Type type, final Appendable appendable) throws JsonIOException {
        this.toJson(o, type, this.newJsonWriter(Streams.writerForAppendable(appendable)));
    }
    
    public void toJson(final Object o, final Type type, final JsonWriter jsonWriter) throws JsonIOException {
        final TypeAdapter adapter = this.getAdapter(TypeToken.get(type));
        final boolean lenient = jsonWriter.isLenient();
        jsonWriter.setLenient(true);
        final boolean htmlSafe = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.htmlSafe);
        final boolean serializeNulls = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.serializeNulls);
        adapter.write(jsonWriter, o);
        jsonWriter.setLenient(lenient);
        jsonWriter.setHtmlSafe(htmlSafe);
        jsonWriter.setSerializeNulls(serializeNulls);
    }
    
    public String toJson(final JsonElement jsonElement) {
        final StringWriter stringWriter = new StringWriter();
        this.toJson(jsonElement, stringWriter);
        return stringWriter.toString();
    }
    
    public void toJson(final JsonElement jsonElement, final Appendable appendable) throws JsonIOException {
        this.toJson(jsonElement, this.newJsonWriter(Streams.writerForAppendable(appendable)));
    }
    
    private JsonWriter newJsonWriter(final Writer writer) throws IOException {
        if (this.generateNonExecutableJson) {
            writer.write(")]}'\n");
        }
        final JsonWriter jsonWriter = new JsonWriter(writer);
        if (this.prettyPrinting) {
            jsonWriter.setIndent("  ");
        }
        jsonWriter.setSerializeNulls(this.serializeNulls);
        return jsonWriter;
    }
    
    public void toJson(final JsonElement jsonElement, final JsonWriter jsonWriter) throws JsonIOException {
        final boolean lenient = jsonWriter.isLenient();
        jsonWriter.setLenient(true);
        final boolean htmlSafe = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.htmlSafe);
        final boolean serializeNulls = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.serializeNulls);
        Streams.write(jsonElement, jsonWriter);
        jsonWriter.setLenient(lenient);
        jsonWriter.setHtmlSafe(htmlSafe);
        jsonWriter.setSerializeNulls(serializeNulls);
    }
    
    public Object fromJson(final String s, final Class clazz) throws JsonSyntaxException {
        return Primitives.wrap(clazz).cast(this.fromJson(s, (Type)clazz));
    }
    
    public Object fromJson(final String s, final Type type) throws JsonSyntaxException {
        if (s == null) {
            return null;
        }
        return this.fromJson(new StringReader(s), type);
    }
    
    public Object fromJson(final Reader reader, final Class clazz) throws JsonSyntaxException, JsonIOException {
        final JsonReader jsonReader = new JsonReader(reader);
        final Object fromJson = this.fromJson(jsonReader, clazz);
        assertFullConsumption(fromJson, jsonReader);
        return Primitives.wrap(clazz).cast(fromJson);
    }
    
    public Object fromJson(final Reader reader, final Type type) throws JsonIOException, JsonSyntaxException {
        final JsonReader jsonReader = new JsonReader(reader);
        final Object fromJson = this.fromJson(jsonReader, type);
        assertFullConsumption(fromJson, jsonReader);
        return fromJson;
    }
    
    private static void assertFullConsumption(final Object o, final JsonReader jsonReader) {
        if (o != null && jsonReader.peek() != JsonToken.END_DOCUMENT) {
            throw new JsonIOException("JSON document was not fully consumed.");
        }
    }
    
    public Object fromJson(final JsonReader jsonReader, final Type type) throws JsonIOException, JsonSyntaxException {
        final boolean lenient = jsonReader.isLenient();
        jsonReader.setLenient(true);
        jsonReader.peek();
        final Object read = this.getAdapter(TypeToken.get(type)).read(jsonReader);
        jsonReader.setLenient(lenient);
        return read;
    }
    
    public Object fromJson(final JsonElement jsonElement, final Class clazz) throws JsonSyntaxException {
        return Primitives.wrap(clazz).cast(this.fromJson(jsonElement, (Type)clazz));
    }
    
    public Object fromJson(final JsonElement jsonElement, final Type type) throws JsonSyntaxException {
        if (jsonElement == null) {
            return null;
        }
        return this.fromJson(new JsonTreeReader(jsonElement), type);
    }
    
    @Override
    public String toString() {
        return "{serializeNulls:" + this.serializeNulls + "factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
    }
    
    static void access$000(final Gson gson, final double n) {
        gson.checkValidFloatingPoint(n);
    }
    
    static class FutureTypeAdapter extends TypeAdapter
    {
        private TypeAdapter delegate;
        
        public void setDelegate(final TypeAdapter delegate) {
            if (this.delegate != null) {
                throw new AssertionError();
            }
            this.delegate = delegate;
        }
        
        @Override
        public Object read(final JsonReader jsonReader) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            return this.delegate.read(jsonReader);
        }
        
        @Override
        public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            this.delegate.write(jsonWriter, o);
        }
    }
}
