package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class ByteMemberValue extends MemberValue
{
    int valueIndex;
    
    public ByteMemberValue(final int valueIndex, final ConstPool constPool) {
        super('B', constPool);
        this.valueIndex = valueIndex;
    }
    
    public ByteMemberValue(final byte value, final ConstPool constPool) {
        super('B', constPool);
        this.setValue(value);
    }
    
    public ByteMemberValue(final ConstPool constPool) {
        super('B', constPool);
        this.setValue((byte)0);
    }
    
    @Override
    Object getValue(final ClassLoader classLoader, final ClassPool classPool, final Method method) {
        return this.getValue();
    }
    
    @Override
    Class getType(final ClassLoader classLoader) {
        return Byte.TYPE;
    }
    
    public byte getValue() {
        return (byte)this.cp.getIntegerInfo(this.valueIndex);
    }
    
    public void setValue(final byte b) {
        this.valueIndex = this.cp.addIntegerInfo(b);
    }
    
    @Override
    public String toString() {
        return Byte.toString(this.getValue());
    }
    
    @Override
    public void write(final AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitByteMemberValue(this);
    }
}
