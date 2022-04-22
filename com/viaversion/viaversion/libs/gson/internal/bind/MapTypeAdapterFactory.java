package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.reflect.*;
import java.util.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.gson.internal.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.gson.*;

public final class MapTypeAdapterFactory implements TypeAdapterFactory
{
    private final ConstructorConstructor constructorConstructor;
    final boolean complexMapKeySerialization;
    
    public MapTypeAdapterFactory(final ConstructorConstructor constructorConstructor, final boolean complexMapKeySerialization) {
        this.constructorConstructor = constructorConstructor;
        this.complexMapKeySerialization = complexMapKeySerialization;
    }
    
    @Override
    public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
        final Type type = typeToken.getType();
        if (!Map.class.isAssignableFrom(typeToken.getRawType())) {
            return null;
        }
        final Type[] mapKeyAndValueTypes = $Gson$Types.getMapKeyAndValueTypes(type, $Gson$Types.getRawType(type));
        return new Adapter(gson, mapKeyAndValueTypes[0], this.getKeyAdapter(gson, mapKeyAndValueTypes[0]), mapKeyAndValueTypes[1], gson.getAdapter(TypeToken.get(mapKeyAndValueTypes[1])), this.constructorConstructor.get(typeToken));
    }
    
    private TypeAdapter getKeyAdapter(final Gson gson, final Type type) {
        return (type == Boolean.TYPE || type == Boolean.class) ? TypeAdapters.BOOLEAN_AS_STRING : gson.getAdapter(TypeToken.get(type));
    }
    
    private final class Adapter extends TypeAdapter
    {
        private final TypeAdapter keyTypeAdapter;
        private final TypeAdapter valueTypeAdapter;
        private final ObjectConstructor constructor;
        final MapTypeAdapterFactory this$0;
        
        public Adapter(final MapTypeAdapterFactory this$0, final Gson gson, final Type type, final TypeAdapter typeAdapter, final Type type2, final TypeAdapter typeAdapter2, final ObjectConstructor constructor) {
            this.this$0 = this$0;
            this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter, type);
            this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter2, type2);
            this.constructor = constructor;
        }
        
        @Override
        public Map read(final JsonReader jsonReader) throws IOException {
            final JsonToken peek = jsonReader.peek();
            if (peek == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            final Map map = (Map)this.constructor.construct();
            if (peek == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginArray();
                    final Object read = this.keyTypeAdapter.read(jsonReader);
                    if (map.put(read, this.valueTypeAdapter.read(jsonReader)) != null) {
                        throw new JsonSyntaxException("duplicate key: " + read);
                    }
                    jsonReader.endArray();
                }
                jsonReader.endArray();
            }
            else {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    JsonReaderInternalAccess.INSTANCE.promoteNameToValue(jsonReader);
                    final Object read2 = this.keyTypeAdapter.read(jsonReader);
                    if (map.put(read2, this.valueTypeAdapter.read(jsonReader)) != null) {
                        throw new JsonSyntaxException("duplicate key: " + read2);
                    }
                }
                jsonReader.endObject();
            }
            return map;
        }
        
        public void write(final JsonWriter p0, final Map p1) throws IOException {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: ifnonnull       10
            //     4: aload_1        
            //     5: invokevirtual   com/viaversion/viaversion/libs/gson/stream/JsonWriter.nullValue:()Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;
            //     8: pop            
            //     9: return         
            //    10: aload_0        
            //    11: getfield        com/viaversion/viaversion/libs/gson/internal/bind/MapTypeAdapterFactory$Adapter.this$0:Lcom/viaversion/viaversion/libs/gson/internal/bind/MapTypeAdapterFactory;
            //    14: getfield        com/viaversion/viaversion/libs/gson/internal/bind/MapTypeAdapterFactory.complexMapKeySerialization:Z
            //    17: ifne            96
            //    20: aload_1        
            //    21: invokevirtual   com/viaversion/viaversion/libs/gson/stream/JsonWriter.beginObject:()Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;
            //    24: pop            
            //    25: aload_2        
            //    26: invokeinterface java/util/Map.entrySet:()Ljava/util/Set;
            //    31: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
            //    36: astore_3       
            //    37: aload_3        
            //    38: invokeinterface java/util/Iterator.hasNext:()Z
            //    43: ifeq            90
            //    46: aload_3        
            //    47: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
            //    52: checkcast       Ljava/util/Map$Entry;
            //    55: astore          4
            //    57: aload_1        
            //    58: aload           4
            //    60: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
            //    65: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
            //    68: invokevirtual   com/viaversion/viaversion/libs/gson/stream/JsonWriter.name:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;
            //    71: pop            
            //    72: aload_0        
            //    73: getfield        com/viaversion/viaversion/libs/gson/internal/bind/MapTypeAdapterFactory$Adapter.valueTypeAdapter:Lcom/viaversion/viaversion/libs/gson/TypeAdapter;
            //    76: aload_1        
            //    77: aload           4
            //    79: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
            //    84: invokevirtual   com/viaversion/viaversion/libs/gson/TypeAdapter.write:(Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;Ljava/lang/Object;)V
            //    87: goto            37
            //    90: aload_1        
            //    91: invokevirtual   com/viaversion/viaversion/libs/gson/stream/JsonWriter.endObject:()Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;
            //    94: pop            
            //    95: return         
            //    96: new             Ljava/util/ArrayList;
            //    99: dup            
            //   100: aload_2        
            //   101: invokeinterface java/util/Map.size:()I
            //   106: invokespecial   java/util/ArrayList.<init>:(I)V
            //   109: astore          4
            //   111: new             Ljava/util/ArrayList;
            //   114: dup            
            //   115: aload_2        
            //   116: invokeinterface java/util/Map.size:()I
            //   121: invokespecial   java/util/ArrayList.<init>:(I)V
            //   124: astore          5
            //   126: aload_2        
            //   127: invokeinterface java/util/Map.entrySet:()Ljava/util/Set;
            //   132: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
            //   137: astore          6
            //   139: aload           6
            //   141: invokeinterface java/util/Iterator.hasNext:()Z
            //   146: ifeq            229
            //   149: aload           6
            //   151: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
            //   156: checkcast       Ljava/util/Map$Entry;
            //   159: astore          7
            //   161: aload_0        
            //   162: getfield        com/viaversion/viaversion/libs/gson/internal/bind/MapTypeAdapterFactory$Adapter.keyTypeAdapter:Lcom/viaversion/viaversion/libs/gson/TypeAdapter;
            //   165: aload           7
            //   167: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
            //   172: invokevirtual   com/viaversion/viaversion/libs/gson/TypeAdapter.toJsonTree:(Ljava/lang/Object;)Lcom/viaversion/viaversion/libs/gson/JsonElement;
            //   175: astore          8
            //   177: aload           4
            //   179: aload           8
            //   181: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
            //   186: pop            
            //   187: aload           5
            //   189: aload           7
            //   191: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
            //   196: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
            //   201: pop            
            //   202: iconst_0       
            //   203: aload           8
            //   205: invokevirtual   com/viaversion/viaversion/libs/gson/JsonElement.isJsonArray:()Z
            //   208: ifne            219
            //   211: aload           8
            //   213: invokevirtual   com/viaversion/viaversion/libs/gson/JsonElement.isJsonObject:()Z
            //   216: ifeq            223
            //   219: iconst_1       
            //   220: goto            224
            //   223: iconst_0       
            //   224: ior            
            //   225: istore_3       
            //   226: goto            139
            //   229: goto            307
            //   232: aload_1        
            //   233: invokevirtual   com/viaversion/viaversion/libs/gson/stream/JsonWriter.beginArray:()Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;
            //   236: pop            
            //   237: aload           4
            //   239: invokeinterface java/util/List.size:()I
            //   244: istore          7
            //   246: iconst_0       
            //   247: iload           7
            //   249: if_icmpge       299
            //   252: aload_1        
            //   253: invokevirtual   com/viaversion/viaversion/libs/gson/stream/JsonWriter.beginArray:()Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;
            //   256: pop            
            //   257: aload           4
            //   259: iconst_0       
            //   260: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
            //   265: checkcast       Lcom/viaversion/viaversion/libs/gson/JsonElement;
            //   268: aload_1        
            //   269: invokestatic    com/viaversion/viaversion/libs/gson/internal/Streams.write:(Lcom/viaversion/viaversion/libs/gson/JsonElement;Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;)V
            //   272: aload_0        
            //   273: getfield        com/viaversion/viaversion/libs/gson/internal/bind/MapTypeAdapterFactory$Adapter.valueTypeAdapter:Lcom/viaversion/viaversion/libs/gson/TypeAdapter;
            //   276: aload_1        
            //   277: aload           5
            //   279: iconst_0       
            //   280: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
            //   285: invokevirtual   com/viaversion/viaversion/libs/gson/TypeAdapter.write:(Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;Ljava/lang/Object;)V
            //   288: aload_1        
            //   289: invokevirtual   com/viaversion/viaversion/libs/gson/stream/JsonWriter.endArray:()Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;
            //   292: pop            
            //   293: iinc            6, 1
            //   296: goto            246
            //   299: aload_1        
            //   300: invokevirtual   com/viaversion/viaversion/libs/gson/stream/JsonWriter.endArray:()Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;
            //   303: pop            
            //   304: goto            378
            //   307: aload_1        
            //   308: invokevirtual   com/viaversion/viaversion/libs/gson/stream/JsonWriter.beginObject:()Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;
            //   311: pop            
            //   312: aload           4
            //   314: invokeinterface java/util/List.size:()I
            //   319: istore          7
            //   321: iconst_0       
            //   322: iload           7
            //   324: if_icmpge       373
            //   327: aload           4
            //   329: iconst_0       
            //   330: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
            //   335: checkcast       Lcom/viaversion/viaversion/libs/gson/JsonElement;
            //   338: astore          8
            //   340: aload_1        
            //   341: aload_0        
            //   342: aload           8
            //   344: invokespecial   com/viaversion/viaversion/libs/gson/internal/bind/MapTypeAdapterFactory$Adapter.keyToString:(Lcom/viaversion/viaversion/libs/gson/JsonElement;)Ljava/lang/String;
            //   347: invokevirtual   com/viaversion/viaversion/libs/gson/stream/JsonWriter.name:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;
            //   350: pop            
            //   351: aload_0        
            //   352: getfield        com/viaversion/viaversion/libs/gson/internal/bind/MapTypeAdapterFactory$Adapter.valueTypeAdapter:Lcom/viaversion/viaversion/libs/gson/TypeAdapter;
            //   355: aload_1        
            //   356: aload           5
            //   358: iconst_0       
            //   359: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
            //   364: invokevirtual   com/viaversion/viaversion/libs/gson/TypeAdapter.write:(Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;Ljava/lang/Object;)V
            //   367: iinc            6, 1
            //   370: goto            321
            //   373: aload_1        
            //   374: invokevirtual   com/viaversion/viaversion/libs/gson/stream/JsonWriter.endObject:()Lcom/viaversion/viaversion/libs/gson/stream/JsonWriter;
            //   377: pop            
            //   378: return         
            //    Exceptions:
            //  throws java.io.IOException
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        private String keyToString(final JsonElement jsonElement) {
            if (jsonElement.isJsonPrimitive()) {
                final JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
                if (asJsonPrimitive.isNumber()) {
                    return String.valueOf(asJsonPrimitive.getAsNumber());
                }
                if (asJsonPrimitive.isBoolean()) {
                    return Boolean.toString(asJsonPrimitive.getAsBoolean());
                }
                if (asJsonPrimitive.isString()) {
                    return asJsonPrimitive.getAsString();
                }
                throw new AssertionError();
            }
            else {
                if (jsonElement.isJsonNull()) {
                    return "null";
                }
                throw new AssertionError();
            }
        }
        
        @Override
        public Object read(final JsonReader jsonReader) throws IOException {
            return this.read(jsonReader);
        }
        
        @Override
        public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
            this.write(jsonWriter, (Map)o);
        }
    }
}
