package com.google.gson;

import com.google.gson.reflect.*;
import java.io.*;
import com.google.gson.stream.*;
import com.google.gson.internal.*;

final class TreeTypeAdapter extends TypeAdapter
{
    private final JsonSerializer serializer;
    private final JsonDeserializer deserializer;
    private final Gson gson;
    private final TypeToken typeToken;
    private final TypeAdapterFactory skipPast;
    private TypeAdapter delegate;
    
    private TreeTypeAdapter(final JsonSerializer serializer, final JsonDeserializer deserializer, final Gson gson, final TypeToken typeToken, final TypeAdapterFactory skipPast) {
        this.serializer = serializer;
        this.deserializer = deserializer;
        this.gson = gson;
        this.typeToken = typeToken;
        this.skipPast = skipPast;
    }
    
    @Override
    public Object read(final JsonReader jsonReader) throws IOException {
        if (this.deserializer == null) {
            return this.delegate().read(jsonReader);
        }
        final JsonElement parse = Streams.parse(jsonReader);
        if (parse.isJsonNull()) {
            return null;
        }
        return this.deserializer.deserialize(parse, this.typeToken.getType(), this.gson.deserializationContext);
    }
    
    @Override
    public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
        if (this.serializer == null) {
            this.delegate().write(jsonWriter, o);
            return;
        }
        if (o == null) {
            jsonWriter.nullValue();
            return;
        }
        Streams.write(this.serializer.serialize(o, this.typeToken.getType(), this.gson.serializationContext), jsonWriter);
    }
    
    private TypeAdapter delegate() {
        final TypeAdapter delegate = this.delegate;
        return (delegate != null) ? delegate : (this.delegate = this.gson.getDelegateAdapter(this.skipPast, this.typeToken));
    }
    
    public static TypeAdapterFactory newFactory(final TypeToken typeToken, final Object o) {
        return new SingleTypeFactory(o, typeToken, false, null, null);
    }
    
    public static TypeAdapterFactory newFactoryWithMatchRawType(final TypeToken typeToken, final Object o) {
        return new SingleTypeFactory(o, typeToken, typeToken.getType() == typeToken.getRawType(), null, null);
    }
    
    public static TypeAdapterFactory newTypeHierarchyFactory(final Class clazz, final Object o) {
        return new SingleTypeFactory(o, null, false, clazz, null);
    }
    
    TreeTypeAdapter(final JsonSerializer jsonSerializer, final JsonDeserializer jsonDeserializer, final Gson gson, final TypeToken typeToken, final TypeAdapterFactory typeAdapterFactory, final TreeTypeAdapter$1 object) {
        this(jsonSerializer, jsonDeserializer, gson, typeToken, typeAdapterFactory);
    }
    
    private static class SingleTypeFactory implements TypeAdapterFactory
    {
        private final TypeToken exactType;
        private final boolean matchRawType;
        private final Class hierarchyType;
        private final JsonSerializer serializer;
        private final JsonDeserializer deserializer;
        
        private SingleTypeFactory(final Object o, final TypeToken exactType, final boolean matchRawType, final Class hierarchyType) {
            this.serializer = ((o instanceof JsonSerializer) ? ((JsonSerializer)o) : null);
            this.deserializer = ((o instanceof JsonDeserializer) ? ((JsonDeserializer)o) : null);
            $Gson$Preconditions.checkArgument(this.serializer != null || this.deserializer != null);
            this.exactType = exactType;
            this.matchRawType = matchRawType;
            this.hierarchyType = hierarchyType;
        }
        
        public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
            return ((this.exactType != null) ? (this.exactType.equals(typeToken) || (this.matchRawType && this.exactType.getType() == typeToken.getRawType())) : this.hierarchyType.isAssignableFrom(typeToken.getRawType())) ? new TreeTypeAdapter(this.serializer, this.deserializer, gson, typeToken, this, null) : null;
        }
        
        SingleTypeFactory(final Object o, final TypeToken typeToken, final boolean b, final Class clazz, final TreeTypeAdapter$1 object) {
            this(o, typeToken, b, clazz);
        }
    }
}
