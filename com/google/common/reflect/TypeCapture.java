package com.google.common.reflect;

import java.lang.reflect.*;
import com.google.common.base.*;

abstract class TypeCapture
{
    final Type capture() {
        final Type genericSuperclass = this.getClass().getGenericSuperclass();
        Preconditions.checkArgument(genericSuperclass instanceof ParameterizedType, "%s isn't parameterized", genericSuperclass);
        return ((ParameterizedType)genericSuperclass).getActualTypeArguments()[0];
    }
}
