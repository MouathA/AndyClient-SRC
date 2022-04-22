package com.google.gson.internal.bind;

import com.google.gson.reflect.*;
import java.util.*;
import java.lang.reflect.*;
import com.google.gson.internal.*;
import java.io.*;
import com.google.gson.stream.*;
import com.google.gson.*;

public final class MapTypeAdapterFactory implements TypeAdapterFactory
{
    private final ConstructorConstructor constructorConstructor;
    private final boolean complexMapKeySerialization;
    
    public MapTypeAdapterFactory(final ConstructorConstructor constructorConstructor, final boolean complexMapKeySerialization) {
        this.constructorConstructor = constructorConstructor;
        this.complexMapKeySerialization = complexMapKeySerialization;
    }
    
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
    
    static boolean access$000(final MapTypeAdapterFactory mapTypeAdapterFactory) {
        return mapTypeAdapterFactory.complexMapKeySerialization;
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
            //     5: invokevirtual   com/google/gson/stream/JsonWriter.nullValue:()Lcom/google/gson/stream/JsonWriter;
            //     8: pop            
            //     9: return         
            //    10: aload_0        
            //    11: getfield        com/google/gson/internal/bind/MapTypeAdapterFactory$Adapter.this$0:Lcom/google/gson/internal/bind/MapTypeAdapterFactory;
            //    14: invokestatic    com/google/gson/internal/bind/MapTypeAdapterFactory.access$000:(Lcom/google/gson/internal/bind/MapTypeAdapterFactory;)Z
            //    17: ifne            96
            //    20: aload_1        
            //    21: invokevirtual   com/google/gson/stream/JsonWriter.beginObject:()Lcom/google/gson/stream/JsonWriter;
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
            //    68: invokevirtual   com/google/gson/stream/JsonWriter.name:(Ljava/lang/String;)Lcom/google/gson/stream/JsonWriter;
            //    71: pop            
            //    72: aload_0        
            //    73: getfield        com/google/gson/internal/bind/MapTypeAdapterFactory$Adapter.valueTypeAdapter:Lcom/google/gson/TypeAdapter;
            //    76: aload_1        
            //    77: aload           4
            //    79: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
            //    84: invokevirtual   com/google/gson/TypeAdapter.write:(Lcom/google/gson/stream/JsonWriter;Ljava/lang/Object;)V
            //    87: goto            37
            //    90: aload_1        
            //    91: invokevirtual   com/google/gson/stream/JsonWriter.endObject:()Lcom/google/gson/stream/JsonWriter;
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
            //   162: getfield        com/google/gson/internal/bind/MapTypeAdapterFactory$Adapter.keyTypeAdapter:Lcom/google/gson/TypeAdapter;
            //   165: aload           7
            //   167: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
            //   172: invokevirtual   com/google/gson/TypeAdapter.toJsonTree:(Ljava/lang/Object;)Lcom/google/gson/JsonElement;
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
            //   205: invokevirtual   com/google/gson/JsonElement.isJsonArray:()Z
            //   208: ifne            219
            //   211: aload           8
            //   213: invokevirtual   com/google/gson/JsonElement.isJsonObject:()Z
            //   216: ifeq            223
            //   219: iconst_1       
            //   220: goto            224
            //   223: iconst_0       
            //   224: ior            
            //   225: istore_3       
            //   226: goto            139
            //   229: iconst_0       
            //   230: ifeq            304
            //   233: aload_1        
            //   234: invokevirtual   com/google/gson/stream/JsonWriter.beginArray:()Lcom/google/gson/stream/JsonWriter;
            //   237: pop            
            //   238: iconst_0       
            //   239: aload           4
            //   241: invokeinterface java/util/List.size:()I
            //   246: if_icmpge       296
            //   249: aload_1        
            //   250: invokevirtual   com/google/gson/stream/JsonWriter.beginArray:()Lcom/google/gson/stream/JsonWriter;
            //   253: pop            
            //   254: aload           4
            //   256: iconst_0       
            //   257: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
            //   262: checkcast       Lcom/google/gson/JsonElement;
            //   265: aload_1        
            //   266: invokestatic    com/google/gson/internal/Streams.write:(Lcom/google/gson/JsonElement;Lcom/google/gson/stream/JsonWriter;)V
            //   269: aload_0        
            //   270: getfield        com/google/gson/internal/bind/MapTypeAdapterFactory$Adapter.valueTypeAdapter:Lcom/google/gson/TypeAdapter;
            //   273: aload_1        
            //   274: aload           5
            //   276: iconst_0       
            //   277: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
            //   282: invokevirtual   com/google/gson/TypeAdapter.write:(Lcom/google/gson/stream/JsonWriter;Ljava/lang/Object;)V
            //   285: aload_1        
            //   286: invokevirtual   com/google/gson/stream/JsonWriter.endArray:()Lcom/google/gson/stream/JsonWriter;
            //   289: pop            
            //   290: iinc            6, 1
            //   293: goto            238
            //   296: aload_1        
            //   297: invokevirtual   com/google/gson/stream/JsonWriter.endArray:()Lcom/google/gson/stream/JsonWriter;
            //   300: pop            
            //   301: goto            371
            //   304: aload_1        
            //   305: invokevirtual   com/google/gson/stream/JsonWriter.beginObject:()Lcom/google/gson/stream/JsonWriter;
            //   308: pop            
            //   309: iconst_0       
            //   310: aload           4
            //   312: invokeinterface java/util/List.size:()I
            //   317: if_icmpge       366
            //   320: aload           4
            //   322: iconst_0       
            //   323: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
            //   328: checkcast       Lcom/google/gson/JsonElement;
            //   331: astore          7
            //   333: aload_1        
            //   334: aload_0        
            //   335: aload           7
            //   337: invokespecial   com/google/gson/internal/bind/MapTypeAdapterFactory$Adapter.keyToString:(Lcom/google/gson/JsonElement;)Ljava/lang/String;
            //   340: invokevirtual   com/google/gson/stream/JsonWriter.name:(Ljava/lang/String;)Lcom/google/gson/stream/JsonWriter;
            //   343: pop            
            //   344: aload_0        
            //   345: getfield        com/google/gson/internal/bind/MapTypeAdapterFactory$Adapter.valueTypeAdapter:Lcom/google/gson/TypeAdapter;
            //   348: aload_1        
            //   349: aload           5
            //   351: iconst_0       
            //   352: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
            //   357: invokevirtual   com/google/gson/TypeAdapter.write:(Lcom/google/gson/stream/JsonWriter;Ljava/lang/Object;)V
            //   360: iinc            6, 1
            //   363: goto            309
            //   366: aload_1        
            //   367: invokevirtual   com/google/gson/stream/JsonWriter.endObject:()Lcom/google/gson/stream/JsonWriter;
            //   370: pop            
            //   371: return         
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
