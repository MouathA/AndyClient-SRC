package com.sun.jna;

import java.lang.reflect.*;

public class CallbackParameterContext extends FromNativeContext
{
    private Method method;
    private Object[] args;
    private int index;
    
    CallbackParameterContext(final Class clazz, final Method method, final Object[] args, final int index) {
        super(clazz);
        this.method = method;
        this.args = args;
        this.index = index;
    }
    
    public Method getMethod() {
        return this.method;
    }
    
    public Object[] getArguments() {
        return this.args;
    }
    
    public int getIndex() {
        return this.index;
    }
}
