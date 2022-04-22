package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;

public abstract class AbstractObject2ObjectFunction implements Object2ObjectFunction, Serializable
{
    private static final long serialVersionUID = -4940583368468432370L;
    protected Object defRetValue;
    
    protected AbstractObject2ObjectFunction() {
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
