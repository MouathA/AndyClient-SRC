package com.sun.jna;

import java.lang.reflect.*;

public class MethodResultContext extends FunctionResultContext
{
    private final Method method;
    
    MethodResultContext(final Class clazz, final Function function, final Object[] array, final Method method) {
        super(clazz, function, array);
        this.method = method;
    }
    
    public Method getMethod() {
        return this.method;
    }
}
