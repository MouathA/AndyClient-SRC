package com.viaversion.viaversion.compatibility;

import java.lang.reflect.*;

public interface ForcefulFieldModifier
{
    void setField(final Field p0, final Object p1, final Object p2) throws ReflectiveOperationException;
}
