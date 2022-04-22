package com.google.gson;

import java.lang.reflect.*;

public interface JsonDeserializationContext
{
    Object deserialize(final JsonElement p0, final Type p1) throws JsonParseException;
}
