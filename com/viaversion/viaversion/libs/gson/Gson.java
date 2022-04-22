package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.reflect.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.math.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import java.util.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.gson.internal.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.internal.bind.*;

public final class Gson
{
    static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
    static final boolean DEFAULT_LENIENT = false;
    static final boolean DEFAULT_PRETTY_PRINT = false;
    static final boolean DEFAULT_ESCAPE_HTML = true;
    static final boolean DEFAULT_SERIALIZE_NULLS = false;
    static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
    static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
    private static final TypeToken NULL_KEY_SURROGATE;
    private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
    private final ThreadLocal calls;
    private final Map typeTokenCache;
    private final ConstructorConstructor constructorConstructor;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    final List factories;
    final Excluder excluder;
    final FieldNamingStrategy fieldNamingStrategy;
    final Map instanceCreators;
    final boolean serializeNulls;
    final boolean complexMapKeySerialization;
    final boolean generateNonExecutableJson;
    final boolean htmlSafe;
    final boolean prettyPrinting;
    final boolean lenient;
    final boolean serializeSpecialFloatingPointValues;
    final String datePattern;
    final int dateStyle;
    final int timeStyle;
    final LongSerializationPolicy longSerializationPolicy;
    final List builderFactories;
    final List builderHierarchyFactories;
    
    public Gson() {
        this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, false, LongSerializationPolicy.DEFAULT, null, 2, 2, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }
    
    Gson(final Excluder excluder, final FieldNamingStrategy fieldNamingStrategy, final Map instanceCreators, final boolean serializeNulls, final boolean complexMapKeySerialization, final boolean generateNonExecutableJson, final boolean htmlSafe, final boolean prettyPrinting, final boolean lenient, final boolean serializeSpecialFloatingPointValues, final LongSerializationPolicy longSerializationPolicy, final String datePattern, final int dateStyle, final int timeStyle, final List builderFactories, final List builderHierarchyFactories, final List list) {
        this.calls = new ThreadLocal();
        this.typeTokenCache = new ConcurrentHashMap();
        this.excluder = excluder;
        this.fieldNamingStrategy = fieldNamingStrategy;
        this.instanceCreators = instanceCreators;
        this.constructorConstructor = new ConstructorConstructor(instanceCreators);
        this.serializeNulls = serializeNulls;
        this.complexMapKeySerialization = complexMapKeySerialization;
        this.generateNonExecutableJson = generateNonExecutableJson;
        this.htmlSafe = htmlSafe;
        this.prettyPrinting = prettyPrinting;
        this.lenient = lenient;
        this.serializeSpecialFloatingPointValues = serializeSpecialFloatingPointValues;
        this.longSerializationPolicy = longSerializationPolicy;
        this.datePattern = datePattern;
        this.dateStyle = dateStyle;
        this.timeStyle = timeStyle;
        this.builderFactories = builderFactories;
        this.builderHierarchyFactories = builderHierarchyFactories;
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
        final TypeAdapter longAdapter = longAdapter(longSerializationPolicy);
        list2.add(TypeAdapters.newFactory(Long.TYPE, Long.class, longAdapter));
        list2.add(TypeAdapters.newFactory(Double.TYPE, Double.class, this.doubleAdapter(serializeSpecialFloatingPointValues)));
        list2.add(TypeAdapters.newFactory(Float.TYPE, Float.class, this.floatAdapter(serializeSpecialFloatingPointValues)));
        list2.add(TypeAdapters.NUMBER_FACTORY);
        list2.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
        list2.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
        list2.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(longAdapter)));
        list2.add(TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter(longAdapter)));
        list2.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
        list2.add(TypeAdapters.CHARACTER_FACTORY);
        list2.add(TypeAdapters.STRING_BUILDER_FACTORY);
        list2.add(TypeAdapters.STRING_BUFFER_FACTORY);
        list2.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        list2.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        list2.add(TypeAdapters.URL_FACTORY);
        list2.add(TypeAdapters.URI_FACTORY);
        list2.add(TypeAdapters.UUID_FACTORY);
        list2.add(TypeAdapters.CURRENCY_FACTORY);
        list2.add(TypeAdapters.LOCALE_FACTORY);
        list2.add(TypeAdapters.INET_ADDRESS_FACTORY);
        list2.add(TypeAdapters.BIT_SET_FACTORY);
        list2.add(DateTypeAdapter.FACTORY);
        list2.add(TypeAdapters.CALENDAR_FACTORY);
        list2.add(TimeTypeAdapter.FACTORY);
        list2.add(SqlDateTypeAdapter.FACTORY);
        list2.add(TypeAdapters.TIMESTAMP_FACTORY);
        list2.add(ArrayTypeAdapter.FACTORY);
        list2.add(TypeAdapters.CLASS_FACTORY);
        list2.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
        list2.add(new MapTypeAdapterFactory(this.constructorConstructor, complexMapKeySerialization));
        list2.add(this.jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor));
        list2.add(TypeAdapters.ENUM_FACTORY);
        list2.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy, excluder, this.jsonAdapterFactory));
        this.factories = Collections.unmodifiableList((List<?>)list2);
    }
    
    public GsonBuilder newBuilder() {
        return new GsonBuilder(this);
    }
    
    public Excluder excluder() {
        return this.excluder;
    }
    
    public FieldNamingStrategy fieldNamingStrategy() {
        return this.fieldNamingStrategy;
    }
    
    public boolean serializeNulls() {
        return this.serializeNulls;
    }
    
    public boolean htmlSafe() {
        return this.htmlSafe;
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
                Gson.checkValidFloatingPoint(n.doubleValue());
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
                Gson.checkValidFloatingPoint(n.floatValue());
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
    
    static void checkValidFloatingPoint(final double n) {
        if (Double.isNaN(n) || Double.isInfinite(n)) {
            throw new IllegalArgumentException(n + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }
    
    private static TypeAdapter longAdapter(final LongSerializationPolicy longSerializationPolicy) {
        if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
            return TypeAdapters.LONG;
        }
        return new TypeAdapter() {
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
    
    private static TypeAdapter atomicLongAdapter(final TypeAdapter typeAdapter) {
        return new TypeAdapter() {
            final TypeAdapter val$longAdapter;
            
            public void write(final JsonWriter jsonWriter, final AtomicLong atomicLong) throws IOException {
                this.val$longAdapter.write(jsonWriter, atomicLong.get());
            }
            
            @Override
            public AtomicLong read(final JsonReader jsonReader) throws IOException {
                return new AtomicLong(((Number)this.val$longAdapter.read(jsonReader)).longValue());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (AtomicLong)o);
            }
        }.nullSafe();
    }
    
    private static TypeAdapter atomicLongArrayAdapter(final TypeAdapter typeAdapter) {
        return new TypeAdapter() {
            final TypeAdapter val$longAdapter;
            
            public void write(final JsonWriter jsonWriter, final AtomicLongArray atomicLongArray) throws IOException {
                jsonWriter.beginArray();
                while (0 < atomicLongArray.length()) {
                    this.val$longAdapter.write(jsonWriter, atomicLongArray.get(0));
                    int n = 0;
                    ++n;
                }
                jsonWriter.endArray();
            }
            
            @Override
            public AtomicLongArray read(final JsonReader jsonReader) throws IOException {
                final ArrayList<Long> list = new ArrayList<Long>();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    list.add(((Number)this.val$longAdapter.read(jsonReader)).longValue());
                }
                jsonReader.endArray();
                final int size = list.size();
                final AtomicLongArray atomicLongArray = new AtomicLongArray(size);
                while (0 < size) {
                    atomicLongArray.set(0, list.get(0));
                    int n = 0;
                    ++n;
                }
                return atomicLongArray;
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (AtomicLongArray)o);
            }
        }.nullSafe();
    }
    
    public TypeAdapter getAdapter(final TypeToken typeToken) {
        final TypeAdapter typeAdapter = this.typeTokenCache.get((typeToken == null) ? Gson.NULL_KEY_SURROGATE : typeToken);
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
                if (true) {
                    this.calls.remove();
                }
                return typeAdapter2;
            }
        }
        throw new IllegalArgumentException("GSON (2.8.8) cannot handle " + typeToken);
    }
    
    public TypeAdapter getDelegateAdapter(TypeAdapterFactory jsonAdapterFactory, final TypeToken typeToken) {
        if (!this.factories.contains(jsonAdapterFactory)) {
            jsonAdapterFactory = this.jsonAdapterFactory;
        }
        for (final TypeAdapterFactory typeAdapterFactory : this.factories) {
            if (!true) {
                if (typeAdapterFactory == jsonAdapterFactory) {
                    continue;
                }
                continue;
            }
            else {
                final TypeAdapter create = typeAdapterFactory.create(this, typeToken);
                if (create != null) {
                    return create;
                }
                continue;
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
    
    public JsonWriter newJsonWriter(final Writer writer) throws IOException {
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
    
    public JsonReader newJsonReader(final Reader reader) {
        final JsonReader jsonReader = new JsonReader(reader);
        jsonReader.setLenient(this.lenient);
        return jsonReader;
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
        final JsonReader jsonReader = this.newJsonReader(reader);
        final Object fromJson = this.fromJson(jsonReader, clazz);
        assertFullConsumption(fromJson, jsonReader);
        return Primitives.wrap(clazz).cast(fromJson);
    }
    
    public Object fromJson(final Reader reader, final Type type) throws JsonIOException, JsonSyntaxException {
        final JsonReader jsonReader = this.newJsonReader(reader);
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
        return "{serializeNulls:" + this.serializeNulls + ",factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
    }
    
    static {
        NULL_KEY_SURROGATE = TypeToken.get(Object.class);
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
