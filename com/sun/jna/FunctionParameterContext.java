package com.sun.jna;

public class FunctionParameterContext extends ToNativeContext
{
    private Function function;
    private Object[] args;
    private int index;
    
    FunctionParameterContext(final Function function, final Object[] args, final int index) {
        this.function = function;
        this.args = args;
        this.index = index;
    }
    
    public Function getFunction() {
        return this.function;
    }
    
    public Object[] getParameters() {
        return this.args;
    }
    
    public int getParameterIndex() {
        return this.index;
    }
}
