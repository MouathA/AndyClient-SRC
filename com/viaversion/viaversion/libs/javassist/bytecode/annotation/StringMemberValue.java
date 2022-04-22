package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class StringMemberValue extends MemberValue
{
    int valueIndex;
    
    public StringMemberValue(final int valueIndex, final ConstPool constPool) {
        super('s', constPool);
        this.valueIndex = valueIndex;
    }
    
    public StringMemberValue(final String value, final ConstPool constPool) {
        super('s', constPool);
        this.setValue(value);
    }
    
    public StringMemberValue(final ConstPool constPool) {
        super('s', constPool);
        this.setValue("");
    }
    
    @Override
    Object getValue(final ClassLoader classLoader, final ClassPool classPool, final Method method) {
        return this.getValue();
    }
    
    @Override
    Class getType(final ClassLoader classLoader) {
        return String.class;
    }
    
    public String getValue() {
        return this.cp.getUtf8Info(this.valueIndex);
    }
    
    public void setValue(final String s) {
        this.valueIndex = this.cp.addUtf8Info(s);
    }
    
    @Override
    public String toString() {
        return "\"" + this.getValue() + "\"";
    }
    
    @Override
    public void write(final AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitStringMemberValue(this);
    }
}
