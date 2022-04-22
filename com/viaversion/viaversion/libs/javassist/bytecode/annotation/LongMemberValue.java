package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class LongMemberValue extends MemberValue
{
    int valueIndex;
    
    public LongMemberValue(final int valueIndex, final ConstPool constPool) {
        super('J', constPool);
        this.valueIndex = valueIndex;
    }
    
    public LongMemberValue(final long value, final ConstPool constPool) {
        super('J', constPool);
        this.setValue(value);
    }
    
    public LongMemberValue(final ConstPool constPool) {
        super('J', constPool);
        this.setValue(0L);
    }
    
    @Override
    Object getValue(final ClassLoader classLoader, final ClassPool classPool, final Method method) {
        return this.getValue();
    }
    
    @Override
    Class getType(final ClassLoader classLoader) {
        return Long.TYPE;
    }
    
    public long getValue() {
        return this.cp.getLongInfo(this.valueIndex);
    }
    
    public void setValue(final long n) {
        this.valueIndex = this.cp.addLongInfo(n);
    }
    
    @Override
    public String toString() {
        return Long.toString(this.getValue());
    }
    
    @Override
    public void write(final AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitLongMemberValue(this);
    }
}
