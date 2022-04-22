package com.viaversion.viaversion.libs.gson.internal.reflect;

import java.lang.reflect.*;

final class PreJava9ReflectionAccessor extends ReflectionAccessor
{
    @Override
    public void makeAccessible(final AccessibleObject accessibleObject) {
        accessibleObject.setAccessible(true);
    }
}
