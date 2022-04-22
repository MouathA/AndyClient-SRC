package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.reflect.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import java.util.concurrent.atomic.*;
import com.viaversion.viaversion.libs.gson.internal.*;
import java.math.*;
import java.net.*;
import java.sql.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;
import java.lang.reflect.*;
import java.security.*;
import com.viaversion.viaversion.libs.gson.annotations.*;

public final class TypeAdapters
{
    public static final TypeAdapter CLASS;
    public static final TypeAdapterFactory CLASS_FACTORY;
    public static final TypeAdapter BIT_SET;
    public static final TypeAdapterFactory BIT_SET_FACTORY;
    public static final TypeAdapter BOOLEAN;
    public static final TypeAdapter BOOLEAN_AS_STRING;
    public static final TypeAdapterFactory BOOLEAN_FACTORY;
    public static final TypeAdapter BYTE;
    public static final TypeAdapterFactory BYTE_FACTORY;
    public static final TypeAdapter SHORT;
    public static final TypeAdapterFactory SHORT_FACTORY;
    public static final TypeAdapter INTEGER;
    public static final TypeAdapterFactory INTEGER_FACTORY;
    public static final TypeAdapter ATOMIC_INTEGER;
    public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY;
    public static final TypeAdapter ATOMIC_BOOLEAN;
    public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY;
    public static final TypeAdapter ATOMIC_INTEGER_ARRAY;
    public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY;
    public static final TypeAdapter LONG;
    public static final TypeAdapter FLOAT;
    public static final TypeAdapter DOUBLE;
    public static final TypeAdapter NUMBER;
    public static final TypeAdapterFactory NUMBER_FACTORY;
    public static final TypeAdapter CHARACTER;
    public static final TypeAdapterFactory CHARACTER_FACTORY;
    public static final TypeAdapter STRING;
    public static final TypeAdapter BIG_DECIMAL;
    public static final TypeAdapter BIG_INTEGER;
    public static final TypeAdapterFactory STRING_FACTORY;
    public static final TypeAdapter STRING_BUILDER;
    public static final TypeAdapterFactory STRING_BUILDER_FACTORY;
    public static final TypeAdapter STRING_BUFFER;
    public static final TypeAdapterFactory STRING_BUFFER_FACTORY;
    public static final TypeAdapter URL;
    public static final TypeAdapterFactory URL_FACTORY;
    public static final TypeAdapter URI;
    public static final TypeAdapterFactory URI_FACTORY;
    public static final TypeAdapter INET_ADDRESS;
    public static final TypeAdapterFactory INET_ADDRESS_FACTORY;
    public static final TypeAdapter UUID;
    public static final TypeAdapterFactory UUID_FACTORY;
    public static final TypeAdapter CURRENCY;
    public static final TypeAdapterFactory CURRENCY_FACTORY;
    public static final TypeAdapterFactory TIMESTAMP_FACTORY;
    public static final TypeAdapter CALENDAR;
    public static final TypeAdapterFactory CALENDAR_FACTORY;
    public static final TypeAdapter LOCALE;
    public static final TypeAdapterFactory LOCALE_FACTORY;
    public static final TypeAdapter JSON_ELEMENT;
    public static final TypeAdapterFactory JSON_ELEMENT_FACTORY;
    public static final TypeAdapterFactory ENUM_FACTORY;
    
    private TypeAdapters() {
        throw new UnsupportedOperationException();
    }
    
    public static TypeAdapterFactory newFactory(final TypeToken typeToken, final TypeAdapter typeAdapter) {
        return new TypeAdapterFactory(typeAdapter) {
            final TypeToken val$type;
            final TypeAdapter val$typeAdapter;
            
            @Override
            public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
                return typeToken.equals(this.val$type) ? this.val$typeAdapter : null;
            }
        };
    }
    
    public static TypeAdapterFactory newFactory(final Class clazz, final TypeAdapter typeAdapter) {
        return new TypeAdapterFactory(typeAdapter) {
            final Class val$type;
            final TypeAdapter val$typeAdapter;
            
            @Override
            public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
                return (typeToken.getRawType() == this.val$type) ? this.val$typeAdapter : null;
            }
            
            @Override
            public String toString() {
                return "Factory[type=" + this.val$type.getName() + ",adapter=" + this.val$typeAdapter + "]";
            }
        };
    }
    
    public static TypeAdapterFactory newFactory(final Class clazz, final Class clazz2, final TypeAdapter typeAdapter) {
        return new TypeAdapterFactory(clazz2, typeAdapter) {
            final Class val$unboxed;
            final Class val$boxed;
            final TypeAdapter val$typeAdapter;
            
            @Override
            public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
                final Class rawType = typeToken.getRawType();
                return (rawType == this.val$unboxed || rawType == this.val$boxed) ? this.val$typeAdapter : null;
            }
            
            @Override
            public String toString() {
                return "Factory[type=" + this.val$boxed.getName() + "+" + this.val$unboxed.getName() + ",adapter=" + this.val$typeAdapter + "]";
            }
        };
    }
    
    public static TypeAdapterFactory newFactoryForMultipleTypes(final Class clazz, final Class clazz2, final TypeAdapter typeAdapter) {
        return new TypeAdapterFactory(clazz2, typeAdapter) {
            final Class val$base;
            final Class val$sub;
            final TypeAdapter val$typeAdapter;
            
            @Override
            public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
                final Class rawType = typeToken.getRawType();
                return (rawType == this.val$base || rawType == this.val$sub) ? this.val$typeAdapter : null;
            }
            
            @Override
            public String toString() {
                return "Factory[type=" + this.val$base.getName() + "+" + this.val$sub.getName() + ",adapter=" + this.val$typeAdapter + "]";
            }
        };
    }
    
    public static TypeAdapterFactory newTypeHierarchyFactory(final Class clazz, final TypeAdapter typeAdapter) {
        return new TypeAdapterFactory(typeAdapter) {
            final Class val$clazz;
            final TypeAdapter val$typeAdapter;
            
            @Override
            public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
                final Class rawType = typeToken.getRawType();
                if (!this.val$clazz.isAssignableFrom(rawType)) {
                    return null;
                }
                return new TypeAdapter(rawType) {
                    final Class val$requestedType;
                    final TypeAdapters$35 this$0;
                    
                    @Override
                    public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                        this.this$0.val$typeAdapter.write(jsonWriter, o);
                    }
                    
                    @Override
                    public Object read(final JsonReader jsonReader) throws IOException {
                        final Object read = this.this$0.val$typeAdapter.read(jsonReader);
                        if (read != null && !this.val$requestedType.isInstance(read)) {
                            throw new JsonSyntaxException("Expected a " + this.val$requestedType.getName() + " but was " + read.getClass().getName());
                        }
                        return read;
                    }
                };
            }
            
            @Override
            public String toString() {
                return "Factory[typeHierarchy=" + this.val$clazz.getName() + ",adapter=" + this.val$typeAdapter + "]";
            }
        };
    }
    
    static {
        CLASS = new TypeAdapter() {
            public void write(final JsonWriter jsonWriter, final Class clazz) throws IOException {
                throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + clazz.getName() + ". Forgot to register a type adapter?");
            }
            
            @Override
            public Class read(final JsonReader jsonReader) throws IOException {
                throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (Class)o);
            }
        }.nullSafe();
        CLASS_FACTORY = newFactory(Class.class, TypeAdapters.CLASS);
        BIT_SET = new TypeAdapter() {
            @Override
            public BitSet read(final JsonReader jsonReader) throws IOException {
                final BitSet set = new BitSet();
                jsonReader.beginArray();
                for (JsonToken jsonToken = jsonReader.peek(); jsonToken != JsonToken.END_ARRAY; jsonToken = jsonReader.peek()) {
                    boolean nextBoolean = false;
                    switch (jsonToken) {
                        case NUMBER: {
                            nextBoolean = (jsonReader.nextInt() != 0);
                            break;
                        }
                        case BOOLEAN: {
                            nextBoolean = jsonReader.nextBoolean();
                            break;
                        }
                        case STRING: {
                            nextBoolean = (Integer.parseInt(jsonReader.nextString()) != 0);
                            break;
                        }
                        default: {
                            throw new JsonSyntaxException("Invalid bitset value type: " + jsonToken);
                        }
                    }
                    if (nextBoolean) {
                        set.set(0);
                    }
                    int n = 0;
                    ++n;
                }
                jsonReader.endArray();
                return set;
            }
            
            public void write(final JsonWriter jsonWriter, final BitSet set) throws IOException {
                jsonWriter.beginArray();
                while (0 < set.length()) {
                    jsonWriter.value(set.get(0) ? 1 : 0);
                    int n = 0;
                    ++n;
                }
                jsonWriter.endArray();
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (BitSet)o);
            }
        }.nullSafe();
        BIT_SET_FACTORY = newFactory(BitSet.class, TypeAdapters.BIT_SET);
        BOOLEAN = new TypeAdapter() {
            @Override
            public Boolean read(final JsonReader jsonReader) throws IOException {
                final JsonToken peek = jsonReader.peek();
                if (peek == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                if (peek == JsonToken.STRING) {
                    return Boolean.parseBoolean(jsonReader.nextString());
                }
                return jsonReader.nextBoolean();
            }
            
            public void write(final JsonWriter jsonWriter, final Boolean b) throws IOException {
                jsonWriter.value(b);
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (Boolean)o);
            }
        };
        BOOLEAN_AS_STRING = new TypeAdapter() {
            @Override
            public Boolean read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return Boolean.valueOf(jsonReader.nextString());
            }
            
            public void write(final JsonWriter jsonWriter, final Boolean b) throws IOException {
                jsonWriter.value((b == null) ? "null" : b.toString());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (Boolean)o);
            }
        };
        BOOLEAN_FACTORY = newFactory(Boolean.TYPE, Boolean.class, TypeAdapters.BOOLEAN);
        BYTE = new TypeAdapter() {
            @Override
            public Number read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return (byte)jsonReader.nextInt();
            }
            
            public void write(final JsonWriter jsonWriter, final Number n) throws IOException {
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
        BYTE_FACTORY = newFactory(Byte.TYPE, Byte.class, TypeAdapters.BYTE);
        SHORT = new TypeAdapter() {
            @Override
            public Number read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return (short)jsonReader.nextInt();
            }
            
            public void write(final JsonWriter jsonWriter, final Number n) throws IOException {
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
        SHORT_FACTORY = newFactory(Short.TYPE, Short.class, TypeAdapters.SHORT);
        INTEGER = new TypeAdapter() {
            @Override
            public Number read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextInt();
            }
            
            public void write(final JsonWriter jsonWriter, final Number n) throws IOException {
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
        INTEGER_FACTORY = newFactory(Integer.TYPE, Integer.class, TypeAdapters.INTEGER);
        ATOMIC_INTEGER = new TypeAdapter() {
            @Override
            public AtomicInteger read(final JsonReader jsonReader) throws IOException {
                return new AtomicInteger(jsonReader.nextInt());
            }
            
            public void write(final JsonWriter jsonWriter, final AtomicInteger atomicInteger) throws IOException {
                jsonWriter.value(atomicInteger.get());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (AtomicInteger)o);
            }
        }.nullSafe();
        ATOMIC_INTEGER_FACTORY = newFactory(AtomicInteger.class, TypeAdapters.ATOMIC_INTEGER);
        ATOMIC_BOOLEAN = new TypeAdapter() {
            @Override
            public AtomicBoolean read(final JsonReader jsonReader) throws IOException {
                return new AtomicBoolean(jsonReader.nextBoolean());
            }
            
            public void write(final JsonWriter jsonWriter, final AtomicBoolean atomicBoolean) throws IOException {
                jsonWriter.value(atomicBoolean.get());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (AtomicBoolean)o);
            }
        }.nullSafe();
        ATOMIC_BOOLEAN_FACTORY = newFactory(AtomicBoolean.class, TypeAdapters.ATOMIC_BOOLEAN);
        ATOMIC_INTEGER_ARRAY = new TypeAdapter() {
            @Override
            public AtomicIntegerArray read(final JsonReader jsonReader) throws IOException {
                final ArrayList<Integer> list = new ArrayList<Integer>();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    list.add(jsonReader.nextInt());
                }
                jsonReader.endArray();
                final int size = list.size();
                final AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(size);
                while (0 < size) {
                    atomicIntegerArray.set(0, list.get(0));
                    int n = 0;
                    ++n;
                }
                return atomicIntegerArray;
            }
            
            public void write(final JsonWriter jsonWriter, final AtomicIntegerArray atomicIntegerArray) throws IOException {
                jsonWriter.beginArray();
                while (0 < atomicIntegerArray.length()) {
                    jsonWriter.value(atomicIntegerArray.get(0));
                    int n = 0;
                    ++n;
                }
                jsonWriter.endArray();
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (AtomicIntegerArray)o);
            }
        }.nullSafe();
        ATOMIC_INTEGER_ARRAY_FACTORY = newFactory(AtomicIntegerArray.class, TypeAdapters.ATOMIC_INTEGER_ARRAY);
        LONG = new TypeAdapter() {
            @Override
            public Number read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextLong();
            }
            
            public void write(final JsonWriter jsonWriter, final Number n) throws IOException {
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
        FLOAT = new TypeAdapter() {
            @Override
            public Number read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return (float)jsonReader.nextDouble();
            }
            
            public void write(final JsonWriter jsonWriter, final Number n) throws IOException {
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
        DOUBLE = new TypeAdapter() {
            @Override
            public Number read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextDouble();
            }
            
            public void write(final JsonWriter jsonWriter, final Number n) throws IOException {
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
        NUMBER = new TypeAdapter() {
            @Override
            public Number read(final JsonReader jsonReader) throws IOException {
                final JsonToken peek = jsonReader.peek();
                switch (peek) {
                    case NULL: {
                        jsonReader.nextNull();
                        return null;
                    }
                    case NUMBER:
                    case STRING: {
                        return new LazilyParsedNumber(jsonReader.nextString());
                    }
                    default: {
                        throw new JsonSyntaxException("Expecting number, got: " + peek);
                    }
                }
            }
            
            public void write(final JsonWriter jsonWriter, final Number n) throws IOException {
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
        NUMBER_FACTORY = newFactory(Number.class, TypeAdapters.NUMBER);
        CHARACTER = new TypeAdapter() {
            @Override
            public Character read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                final String nextString = jsonReader.nextString();
                if (nextString.length() != 1) {
                    throw new JsonSyntaxException("Expecting character, got: " + nextString);
                }
                return nextString.charAt(0);
            }
            
            public void write(final JsonWriter jsonWriter, final Character c) throws IOException {
                jsonWriter.value((c == null) ? null : String.valueOf(c));
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (Character)o);
            }
        };
        CHARACTER_FACTORY = newFactory(Character.TYPE, Character.class, TypeAdapters.CHARACTER);
        STRING = new TypeAdapter() {
            @Override
            public String read(final JsonReader jsonReader) throws IOException {
                final JsonToken peek = jsonReader.peek();
                if (peek == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                if (peek == JsonToken.BOOLEAN) {
                    return Boolean.toString(jsonReader.nextBoolean());
                }
                return jsonReader.nextString();
            }
            
            public void write(final JsonWriter jsonWriter, final String s) throws IOException {
                jsonWriter.value(s);
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (String)o);
            }
        };
        BIG_DECIMAL = new TypeAdapter() {
            @Override
            public BigDecimal read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return new BigDecimal(jsonReader.nextString());
            }
            
            public void write(final JsonWriter jsonWriter, final BigDecimal bigDecimal) throws IOException {
                jsonWriter.value(bigDecimal);
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (BigDecimal)o);
            }
        };
        BIG_INTEGER = new TypeAdapter() {
            @Override
            public BigInteger read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return new BigInteger(jsonReader.nextString());
            }
            
            public void write(final JsonWriter jsonWriter, final BigInteger bigInteger) throws IOException {
                jsonWriter.value(bigInteger);
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (BigInteger)o);
            }
        };
        STRING_FACTORY = newFactory(String.class, TypeAdapters.STRING);
        STRING_BUILDER = new TypeAdapter() {
            @Override
            public StringBuilder read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return new StringBuilder(jsonReader.nextString());
            }
            
            public void write(final JsonWriter jsonWriter, final StringBuilder sb) throws IOException {
                jsonWriter.value((sb == null) ? null : sb.toString());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (StringBuilder)o);
            }
        };
        STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, TypeAdapters.STRING_BUILDER);
        STRING_BUFFER = new TypeAdapter() {
            @Override
            public StringBuffer read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return new StringBuffer(jsonReader.nextString());
            }
            
            public void write(final JsonWriter jsonWriter, final StringBuffer sb) throws IOException {
                jsonWriter.value((sb == null) ? null : sb.toString());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (StringBuffer)o);
            }
        };
        STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, TypeAdapters.STRING_BUFFER);
        URL = new TypeAdapter() {
            @Override
            public URL read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                final String nextString = jsonReader.nextString();
                return "null".equals(nextString) ? null : new URL(nextString);
            }
            
            public void write(final JsonWriter jsonWriter, final URL url) throws IOException {
                jsonWriter.value((url == null) ? null : url.toExternalForm());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (URL)o);
            }
        };
        URL_FACTORY = newFactory(URL.class, TypeAdapters.URL);
        URI = new TypeAdapter() {
            @Override
            public URI read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                final String nextString = jsonReader.nextString();
                return "null".equals(nextString) ? null : new URI(nextString);
            }
            
            public void write(final JsonWriter jsonWriter, final URI uri) throws IOException {
                jsonWriter.value((uri == null) ? null : uri.toASCIIString());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (URI)o);
            }
        };
        URI_FACTORY = newFactory(URI.class, TypeAdapters.URI);
        INET_ADDRESS = new TypeAdapter() {
            @Override
            public InetAddress read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return InetAddress.getByName(jsonReader.nextString());
            }
            
            public void write(final JsonWriter jsonWriter, final InetAddress inetAddress) throws IOException {
                jsonWriter.value((inetAddress == null) ? null : inetAddress.getHostAddress());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (InetAddress)o);
            }
        };
        INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, TypeAdapters.INET_ADDRESS);
        UUID = new TypeAdapter() {
            @Override
            public UUID read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return java.util.UUID.fromString(jsonReader.nextString());
            }
            
            public void write(final JsonWriter jsonWriter, final UUID uuid) throws IOException {
                jsonWriter.value((uuid == null) ? null : uuid.toString());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (UUID)o);
            }
        };
        UUID_FACTORY = newFactory(UUID.class, TypeAdapters.UUID);
        CURRENCY = new TypeAdapter() {
            @Override
            public Currency read(final JsonReader jsonReader) throws IOException {
                return Currency.getInstance(jsonReader.nextString());
            }
            
            public void write(final JsonWriter jsonWriter, final Currency currency) throws IOException {
                jsonWriter.value(currency.getCurrencyCode());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (Currency)o);
            }
        }.nullSafe();
        CURRENCY_FACTORY = newFactory(Currency.class, TypeAdapters.CURRENCY);
        TIMESTAMP_FACTORY = new TypeAdapterFactory() {
            @Override
            public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
                if (typeToken.getRawType() != Timestamp.class) {
                    return null;
                }
                return new TypeAdapter(gson.getAdapter(Date.class)) {
                    final TypeAdapter val$dateTypeAdapter;
                    final TypeAdapters$26 this$0;
                    
                    @Override
                    public Timestamp read(final JsonReader jsonReader) throws IOException {
                        final Date date = (Date)this.val$dateTypeAdapter.read(jsonReader);
                        return (date != null) ? new Timestamp(date.getTime()) : null;
                    }
                    
                    public void write(final JsonWriter jsonWriter, final Timestamp timestamp) throws IOException {
                        this.val$dateTypeAdapter.write(jsonWriter, timestamp);
                    }
                    
                    @Override
                    public Object read(final JsonReader jsonReader) throws IOException {
                        return this.read(jsonReader);
                    }
                    
                    @Override
                    public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                        this.write(jsonWriter, (Timestamp)o);
                    }
                };
            }
        };
        CALENDAR = new TypeAdapter() {
            private static final String YEAR = "year";
            private static final String MONTH = "month";
            private static final String DAY_OF_MONTH = "dayOfMonth";
            private static final String HOUR_OF_DAY = "hourOfDay";
            private static final String MINUTE = "minute";
            private static final String SECOND = "second";
            
            @Override
            public Calendar read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                jsonReader.beginObject();
                while (jsonReader.peek() != JsonToken.END_OBJECT) {
                    final String nextName = jsonReader.nextName();
                    jsonReader.nextInt();
                    if ("year".equals(nextName)) {
                        continue;
                    }
                    if ("month".equals(nextName)) {
                        continue;
                    }
                    if ("dayOfMonth".equals(nextName)) {
                        continue;
                    }
                    if ("hourOfDay".equals(nextName)) {
                        continue;
                    }
                    if ("minute".equals(nextName)) {
                        continue;
                    }
                    if ("second".equals(nextName)) {
                        continue;
                    }
                }
                jsonReader.endObject();
                return new GregorianCalendar(0, 0, 0, 0, 0, 0);
            }
            
            public void write(final JsonWriter jsonWriter, final Calendar calendar) throws IOException {
                if (calendar == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.beginObject();
                jsonWriter.name("year");
                jsonWriter.value(calendar.get(1));
                jsonWriter.name("month");
                jsonWriter.value(calendar.get(2));
                jsonWriter.name("dayOfMonth");
                jsonWriter.value(calendar.get(5));
                jsonWriter.name("hourOfDay");
                jsonWriter.value(calendar.get(11));
                jsonWriter.name("minute");
                jsonWriter.value(calendar.get(12));
                jsonWriter.name("second");
                jsonWriter.value(calendar.get(13));
                jsonWriter.endObject();
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (Calendar)o);
            }
        };
        CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, TypeAdapters.CALENDAR);
        LOCALE = new TypeAdapter() {
            @Override
            public Locale read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                final StringTokenizer stringTokenizer = new StringTokenizer(jsonReader.nextString(), "_");
                String nextToken = null;
                String nextToken2 = null;
                String nextToken3 = null;
                if (stringTokenizer.hasMoreElements()) {
                    nextToken = stringTokenizer.nextToken();
                }
                if (stringTokenizer.hasMoreElements()) {
                    nextToken2 = stringTokenizer.nextToken();
                }
                if (stringTokenizer.hasMoreElements()) {
                    nextToken3 = stringTokenizer.nextToken();
                }
                if (nextToken2 == null && nextToken3 == null) {
                    return new Locale(nextToken);
                }
                if (nextToken3 == null) {
                    return new Locale(nextToken, nextToken2);
                }
                return new Locale(nextToken, nextToken2, nextToken3);
            }
            
            public void write(final JsonWriter jsonWriter, final Locale locale) throws IOException {
                jsonWriter.value((locale == null) ? null : locale.toString());
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (Locale)o);
            }
        };
        LOCALE_FACTORY = newFactory(Locale.class, TypeAdapters.LOCALE);
        JSON_ELEMENT = new TypeAdapter() {
            @Override
            public JsonElement read(final JsonReader jsonReader) throws IOException {
                switch (jsonReader.peek()) {
                    case STRING: {
                        return new JsonPrimitive(jsonReader.nextString());
                    }
                    case NUMBER: {
                        return new JsonPrimitive(new LazilyParsedNumber(jsonReader.nextString()));
                    }
                    case BOOLEAN: {
                        return new JsonPrimitive(jsonReader.nextBoolean());
                    }
                    case NULL: {
                        jsonReader.nextNull();
                        return JsonNull.INSTANCE;
                    }
                    case BEGIN_ARRAY: {
                        final JsonArray jsonArray = new JsonArray();
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            jsonArray.add(this.read(jsonReader));
                        }
                        jsonReader.endArray();
                        return jsonArray;
                    }
                    case BEGIN_OBJECT: {
                        final JsonObject jsonObject = new JsonObject();
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            jsonObject.add(jsonReader.nextName(), this.read(jsonReader));
                        }
                        jsonReader.endObject();
                        return jsonObject;
                    }
                    default: {
                        throw new IllegalArgumentException();
                    }
                }
            }
            
            public void write(final JsonWriter jsonWriter, final JsonElement jsonElement) throws IOException {
                if (jsonElement == null || jsonElement.isJsonNull()) {
                    jsonWriter.nullValue();
                }
                else if (jsonElement.isJsonPrimitive()) {
                    final JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
                    if (asJsonPrimitive.isNumber()) {
                        jsonWriter.value(asJsonPrimitive.getAsNumber());
                    }
                    else if (asJsonPrimitive.isBoolean()) {
                        jsonWriter.value(asJsonPrimitive.getAsBoolean());
                    }
                    else {
                        jsonWriter.value(asJsonPrimitive.getAsString());
                    }
                }
                else if (jsonElement.isJsonArray()) {
                    jsonWriter.beginArray();
                    final Iterator iterator = jsonElement.getAsJsonArray().iterator();
                    while (iterator.hasNext()) {
                        this.write(jsonWriter, iterator.next());
                    }
                    jsonWriter.endArray();
                }
                else {
                    if (!jsonElement.isJsonObject()) {
                        throw new IllegalArgumentException("Couldn't write " + jsonElement.getClass());
                    }
                    jsonWriter.beginObject();
                    for (final Map.Entry<String, V> entry : jsonElement.getAsJsonObject().entrySet()) {
                        jsonWriter.name(entry.getKey());
                        this.write(jsonWriter, (JsonElement)entry.getValue());
                    }
                    jsonWriter.endObject();
                }
            }
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                this.write(jsonWriter, (JsonElement)o);
            }
        };
        JSON_ELEMENT_FACTORY = newTypeHierarchyFactory(JsonElement.class, TypeAdapters.JSON_ELEMENT);
        ENUM_FACTORY = new TypeAdapterFactory() {
            @Override
            public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
                Class<? super Enum> clazz = (Class<? super Enum>)typeToken.getRawType();
                if (!Enum.class.isAssignableFrom(clazz) || clazz == Enum.class) {
                    return null;
                }
                if (!clazz.isEnum()) {
                    clazz = clazz.getSuperclass();
                }
                return new EnumTypeAdapter(clazz);
            }
        };
    }
    
    private static final class EnumTypeAdapter extends TypeAdapter
    {
        private final Map nameToConstant;
        private final Map constantToName;
        
        public EnumTypeAdapter(final Class clazz) {
            this.nameToConstant = new HashMap();
            this.constantToName = new HashMap();
            final Field[] declaredFields = clazz.getDeclaredFields();
            while (0 < declaredFields.length) {
                final Field field = declaredFields[0];
                if (field.isEnumConstant()) {
                    AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction(field) {
                        final Field val$field;
                        final EnumTypeAdapter this$0;
                        
                        @Override
                        public Void run() {
                            this.val$field.setAccessible(true);
                            return null;
                        }
                        
                        @Override
                        public Object run() {
                            return this.run();
                        }
                    });
                    final Enum enum1 = (Enum)field.get(null);
                    String s = enum1.name();
                    final SerializedName serializedName = field.getAnnotation(SerializedName.class);
                    if (serializedName != null) {
                        s = serializedName.value();
                        final String[] alternate = serializedName.alternate();
                        while (0 < alternate.length) {
                            this.nameToConstant.put(alternate[0], enum1);
                            int n = 0;
                            ++n;
                        }
                    }
                    this.nameToConstant.put(s, enum1);
                    this.constantToName.put(enum1, s);
                }
                int n2 = 0;
                ++n2;
            }
        }
        
        @Override
        public Enum read(final JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return this.nameToConstant.get(jsonReader.nextString());
        }
        
        public void write(final JsonWriter jsonWriter, final Enum enum1) throws IOException {
            jsonWriter.value((enum1 == null) ? null : this.constantToName.get(enum1));
        }
        
        @Override
        public Object read(final JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }
        
        @Override
        public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
            this.write(jsonWriter, (Enum)o);
        }
    }
}
