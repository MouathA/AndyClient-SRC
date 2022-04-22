package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;

public abstract class AbstractInt2IntFunction implements Int2IntFunction, Serializable
{
    private static final long serialVersionUID = -4940583368468432370L;
    protected int defRetValue;
    
    protected AbstractInt2IntFunction() {
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
