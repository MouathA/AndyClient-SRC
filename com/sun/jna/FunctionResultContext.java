package com.sun.jna;

public class FunctionResultContext extends FromNativeContext
{
    private Function function;
    private Object[] args;
    
    FunctionResultContext(final Class clazz, final Function function, final Object[] args) {
        super(clazz);
        this.function = function;
        this.args = args;
    }
    
    public Function getFunction() {
        return this.function;
    }
    
    public Object[] getArguments() {
        return this.args;
    }
}
