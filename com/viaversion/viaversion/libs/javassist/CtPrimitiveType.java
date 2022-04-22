package com.viaversion.viaversion.libs.javassist;

public final class CtPrimitiveType extends CtClass
{
    private char descriptor;
    private String wrapperName;
    private String getMethodName;
    private String mDescriptor;
    private int returnOp;
    private int arrayType;
    private int dataSize;
    
    CtPrimitiveType(final String s, final char descriptor, final String wrapperName, final String getMethodName, final String mDescriptor, final int returnOp, final int arrayType, final int dataSize) {
        super(s);
        this.descriptor = descriptor;
        this.wrapperName = wrapperName;
        this.getMethodName = getMethodName;
        this.mDescriptor = mDescriptor;
        this.returnOp = returnOp;
        this.arrayType = arrayType;
        this.dataSize = dataSize;
    }
    
    @Override
    public boolean isPrimitive() {
        return true;
    }
    
    @Override
    public int getModifiers() {
        return 17;
    }
    
    public char getDescriptor() {
        return this.descriptor;
    }
    
    public String getWrapperName() {
        return this.wrapperName;
    }
    
    public String getGetMethodName() {
        return this.getMethodName;
    }
    
    public String getGetMethodDescriptor() {
        return this.mDescriptor;
    }
    
    public int getReturnOp() {
        return this.returnOp;
    }
    
    public int getArrayType() {
        return this.arrayType;
    }
    
    public int getDataSize() {
        return this.dataSize;
    }
}
