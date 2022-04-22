package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class DoubleMemberValue extends MemberValue
{
    int valueIndex;
    
    public DoubleMemberValue(final int valueIndex, final ConstPool constPool) {
        super('D', constPool);
        this.valueIndex = valueIndex;
    }
    
    public DoubleMemberValue(final double value, final ConstPool constPool) {
        super('D', constPool);
        this.setValue(value);
    }
    
    public DoubleMemberValue(final ConstPool constPool) {
        super('D', constPool);
        this.setValue(0.0);
    }
    
    @Override
    Object getValue(final ClassLoader classLoader, final ClassPool classPool, final Method method) {
        return this.getValue();
    }
    
    @Override
    Class getType(final ClassLoader classLoader) {
        return Double.TYPE;
    }
    
    public double getValue() {
        return this.cp.getDoubleInfo(this.valueIndex);
    }
    
    public void setValue(final double n) {
        this.valueIndex = this.cp.addDoubleInfo(n);
    }
    
    @Override
    public String toString() {
        return Double.toString(this.getValue());
    }
    
    @Override
    public void write(final AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitDoubleMemberValue(this);
    }
}
