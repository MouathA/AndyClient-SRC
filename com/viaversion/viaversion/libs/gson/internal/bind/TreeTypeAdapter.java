package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.reflect.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.gson.internal.*;

public final class TreeTypeAdapter extends TypeAdapter
{
    private final JsonSerializer serializer;
    private final JsonDeserializer deserializer;
    final Gson gson;
    private final TypeToken typeToken;
    private final TypeAdapterFactory skipPast;
    private final GsonContextImpl context;
    private TypeAdapter delegate;
    
    public TreeTypeAdapter(final JsonSerializer serializer, final JsonDeserializer deserializer, final Gson gson, final TypeToken typeToken, final TypeAdapterFactory skipPast) {
        this.context = new GsonContextImpl(null);
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
        return this.deserializer.deserialize(parse, this.typeToken.getType(), this.context);
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
        Streams.write(this.serializer.serialize(o, this.typeToken.getType(), this.context), jsonWriter);
    }
    
    private TypeAdapter delegate() {
        final TypeAdapter delegate = this.delegate;
        return (delegate != null) ? delegate : (this.delegate = this.gson.getDelegateAdapter(this.skipPast, this.typeToken));
    }
    
    public static TypeAdapterFactory newFactory(final TypeToken typeToken, final Object o) {
        return new SingleTypeFactory(o, typeToken, false, null);
    }
    
    public static TypeAdapterFactory newFactoryWithMatchRawType(final TypeToken typeToken, final Object o) {
        return new SingleTypeFactory(o, typeToken, typeToken.getType() == typeToken.getRawType(), null);
    }
    
    public static TypeAdapterFactory newTypeHierarchyFactory(final Class clazz, final Object o) {
        return new SingleTypeFactory(o, null, false, clazz);
    }
    
    private final class GsonContextImpl implements JsonSerializationContext, JsonDeserializationContext
    {
        final TreeTypeAdapter this$0;
        
        private GsonContextImpl(final TreeTypeAdapter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public JsonElement serialize(final Object o) {
            return this.this$0.gson.toJsonTree(o);
        }
        
        @Override
        public JsonElement serialize(final Object o, final Type type) {
            return this.this$0.gson.toJsonTree(o, type);
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type) throws JsonParseException {
            return this.this$0.gson.fromJson(jsonElement, type);
        }
        
        GsonContextImpl(final TreeTypeAdapter treeTypeAdapter, final TreeTypeAdapter$1 object) {
            this(treeTypeAdapter);
        }
    }
    
    private static final class SingleTypeFactory implements TypeAdapterFactory
    {
        private final TypeToken exactType;
        private final boolean matchRawType;
        private final Class hierarchyType;
        private final JsonSerializer serializer;
        private final JsonDeserializer deserializer;
        
        SingleTypeFactory(final Object o, final TypeToken exactType, final boolean matchRawType, final Class hierarchyType) {
            this.serializer = ((o instanceof JsonSerializer) ? ((JsonSerializer)o) : null);
            this.deserializer = ((o instanceof JsonDeserializer) ? ((JsonDeserializer)o) : null);
            $Gson$Preconditions.checkArgument(this.serializer != null || this.deserializer != null);
            this.exactType = exactType;
            this.matchRawType = matchRawType;
            this.hierarchyType = hierarchyType;
        }
        
        @Override
        public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
            return ((this.exactType != null) ? (this.exactType.equals(typeToken) || (this.matchRawType && this.exactType.getType() == typeToken.getRawType())) : this.hierarchyType.isAssignableFrom(typeToken.getRawType())) ? new TreeTypeAdapter(this.serializer, this.deserializer, gson, typeToken, this) : null;
        }
    }
}
