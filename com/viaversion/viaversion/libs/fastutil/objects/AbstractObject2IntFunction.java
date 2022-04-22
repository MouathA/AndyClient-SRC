package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;

public abstract class AbstractObject2IntFunction implements Object2IntFunction, Serializable
{
    private static final long serialVersionUID = -4940583368468432370L;
    protected int defRetValue;
    
    protected AbstractObject2IntFunction() {
    }
    
    @Override
    public void defaultReturnValue(final int defRetValue) {
        this.defRetValue = defRetValue;
    }
    
    @Override
    public int defaultReturnValue() {
        return this.defRetValue;
    }
}
