package com.google.gson;

import com.google.gson.reflect.*;

public interface TypeAdapterFactory
{
    TypeAdapter create(final Gson p0, final TypeToken p1);
}
