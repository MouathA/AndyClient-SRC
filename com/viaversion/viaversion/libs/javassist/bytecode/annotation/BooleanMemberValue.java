package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class BooleanMemberValue extends MemberValue
{
    int valueIndex;
    
    public BooleanMemberValue(final int valueIndex, final ConstPool constPool) {
        super('Z', constPool);
        this.valueIndex = valueIndex;
    }
    
    public BooleanMemberValue(final boolean value, final ConstPool constPool) {
        super('Z', constPool);
        this.setValue(value);
    }
    
    public BooleanMemberValue(final ConstPool constPool) {
        super('Z', constPool);
        this.setValue(false);
    }
    
    @Override
    Object getValue(final ClassLoader classLoader, final ClassPool classPool, final Method method) {
        return this.getValue();
    }
    
    @Override
    Class getType(final ClassLoader classLoader) {
        return Boolean.TYPE;
    }
    
    public void setValue(final boolean b) {
        this.valueIndex = this.cp.addIntegerInfo(b ? 1 : 0);
    }
    
    @Override
    public String toString() {
        return (this != 0) ? "true" : "false";
    }
    
    @Override
    public void write(final AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitBooleanMemberValue(this);
    }
}
