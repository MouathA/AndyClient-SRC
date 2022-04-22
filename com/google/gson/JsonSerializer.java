package com.google.gson;

import java.lang.reflect.*;

public interface JsonSerializer
{
    JsonElement serialize(final Object p0, final Type p1, final JsonSerializationContext p2);
}
