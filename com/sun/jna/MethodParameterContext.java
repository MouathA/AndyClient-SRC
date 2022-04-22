package com.sun.jna;

import java.lang.reflect.*;

public class MethodParameterContext extends FunctionParameterContext
{
    private Method method;
    
    MethodParameterContext(final Function function, final Object[] array, final int n, final Method method) {
        super(function, array, n);
        this.method = method;
    }
    
    public Method getMethod() {
        return this.method;
    }
}
