package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.io.*;

public class ClassMemberValue extends MemberValue
{
    int valueIndex;
    
    public ClassMemberValue(final int valueIndex, final ConstPool constPool) {
        super('c', constPool);
        this.valueIndex = valueIndex;
    }
    
    public ClassMemberValue(final String value, final ConstPool constPool) {
        super('c', constPool);
        this.setValue(value);
    }
    
    public ClassMemberValue(final ConstPool constPool) {
        super('c', constPool);
        this.setValue("java.lang.Class");
    }
    
    @Override
    Object getValue(final ClassLoader classLoader, final ClassPool classPool, final Method method) throws ClassNotFoundException {
        final String value = this.getValue();
        if (value.equals("void")) {
            return Void.TYPE;
        }
        if (value.equals("int")) {
            return Integer.TYPE;
        }
        if (value.equals("byte")) {
            return Byte.TYPE;
        }
        if (value.equals("long")) {
            return Long.TYPE;
        }
        if (value.equals("double")) {
            return Double.TYPE;
        }
        if (value.equals("float")) {
            return Float.TYPE;
        }
        if (value.equals("char")) {
            return Character.TYPE;
        }
        if (value.equals("short")) {
            return Short.TYPE;
        }
        if (value.equals("boolean")) {
            return Boolean.TYPE;
        }
        return MemberValue.loadClass(classLoader, value);
    }
    
    @Override
    Class getType(final ClassLoader classLoader) throws ClassNotFoundException {
        return MemberValue.loadClass(classLoader, "java.lang.Class");
    }
    
    public String getValue() {
        return SignatureAttribute.toTypeSignature(this.cp.getUtf8Info(this.valueIndex)).jvmTypeName();
    }
    
    public void setValue(final String s) {
        this.valueIndex = this.cp.addUtf8Info(Descriptor.of(s));
    }
    
    @Override
    public String toString() {
        return this.getValue().replace('$', '.') + ".class";
    }
    
    @Override
    public void write(final AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.classInfoIndex(this.cp.getUtf8Info(this.valueIndex));
    }
    
    @Override
    public void accept(final MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitClassMemberValue(this);
    }
}
