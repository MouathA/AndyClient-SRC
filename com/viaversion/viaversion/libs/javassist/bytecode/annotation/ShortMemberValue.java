package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class ShortMemberValue extends MemberValue
{
    int valueIndex;
    
    public ShortMemberValue(final int valueIndex, final ConstPool constPool) {
        super('S', constPool);
        this.valueIndex = valueIndex;
    }
    
    public ShortMemberValue(final short value, final ConstPool constPool) {
        super('S', constPool);
        this.setValue(value);
    }
    
    public ShortMemberValue(final ConstPool constPool) {
        super('S', constPool);
        this.setValue((short)0);
    }
    
    @Override
    Object getValue(final ClassLoader classLoader, final ClassPool classPool, final Method method) {
        return this.getValue();
    }
    
    @Override
    Class getType(final ClassLoader classLoader) {
        return Short.TYPE;
    }
    
    public short getValue() {
        return (short)this.cp.getIntegerInfo(this.valueIndex);
    }
    
    public void setValue(final short n) {
        this.valueIndex = this.cp.addIntegerInfo(n);
    }
    
    @Override
    public String toString() {
        return Short.toString(this.getValue());
    }
    
    @Override
    public void write(final AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitShortMemberValue(this);
    }
}
