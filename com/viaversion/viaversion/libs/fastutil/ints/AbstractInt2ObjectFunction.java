package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;

public abstract class AbstractInt2ObjectFunction implements Int2ObjectFunction, Serializable
{
    private static final long serialVersionUID = -4940583368468432370L;
    protected Object defRetValue;
    
    protected AbstractInt2ObjectFunction() {
    }
    
    @Override
    public void defaultReturnValue(final Object defRetValue) {
        this.defRetValue = defRetValue;
    }
    
    @Override
    public Object defaultReturnValue() {
        return this.defRetValue;
    }
}
