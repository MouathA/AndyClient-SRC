package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.internal.*;
import com.viaversion.viaversion.libs.gson.reflect.*;
import com.viaversion.viaversion.libs.gson.annotations.*;
import com.viaversion.viaversion.libs.gson.*;

public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory
{
    private final ConstructorConstructor constructorConstructor;
    
    public JsonAdapterAnnotationTypeAdapterFactory(final ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }
    
    @Override
    public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
        final JsonAdapter jsonAdapter = typeToken.getRawType().getAnnotation(JsonAdapter.class);
        if (jsonAdapter == null) {
            return null;
        }
        return this.getTypeAdapter(this.constructorConstructor, gson, typeToken, jsonAdapter);
    }
    
    TypeAdapter getTypeAdapter(final ConstructorConstructor constructorConstructor, final Gson gson, final TypeToken typeToken, final JsonAdapter jsonAdapter) {
        final Object construct = constructorConstructor.get(TypeToken.get(jsonAdapter.value())).construct();
        TypeAdapter typeAdapter;
        if (construct instanceof TypeAdapter) {
            typeAdapter = (TypeAdapter)construct;
        }
        else if (construct instanceof TypeAdapterFactory) {
            typeAdapter = ((TypeAdapterFactory)construct).create(gson, typeToken);
        }
        else {
            if (!(construct instanceof JsonSerializer) && !(construct instanceof JsonDeserializer)) {
                throw new IllegalArgumentException("Invalid attempt to bind an instance of " + ((JsonDeserializer)construct).getClass().getName() + " as a @JsonAdapter for " + typeToken.toString() + ". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer.");
            }
            typeAdapter = new TreeTypeAdapter((construct instanceof JsonSerializer) ? ((JsonSerializer)construct) : null, (construct instanceof JsonDeserializer) ? ((JsonDeserializer)construct) : null, gson, typeToken, null);
        }
        if (typeAdapter != null && jsonAdapter.nullSafe()) {
            typeAdapter = typeAdapter.nullSafe();
        }
        return typeAdapter;
    }
}
