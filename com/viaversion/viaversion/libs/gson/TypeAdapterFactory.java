package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.reflect.*;

public interface TypeAdapterFactory
{
    TypeAdapter create(final Gson p0, final TypeToken p1);
}
