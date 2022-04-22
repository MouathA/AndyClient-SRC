package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class ArrayMemberValue extends MemberValue
{
    MemberValue type;
    MemberValue[] values;
    
    public ArrayMemberValue(final ConstPool constPool) {
        super('[', constPool);
        this.type = null;
        this.values = null;
    }
    
    public ArrayMemberValue(final MemberValue type, final ConstPool constPool) {
        super('[', constPool);
        this.type = type;
        this.values = null;
    }
    
    @Override
    Object getValue(final ClassLoader classLoader, final ClassPool classPool, final Method method) throws ClassNotFoundException {
        if (this.values == null) {
            throw new ClassNotFoundException("no array elements found: " + method.getName());
        }
        final int length = this.values.length;
        Class<?> clazz;
        if (this.type == null) {
            clazz = method.getReturnType().getComponentType();
            if (clazz == null || length > 0) {
                throw new ClassNotFoundException("broken array type: " + method.getName());
            }
        }
        else {
            clazz = (Class<?>)this.type.getType(classLoader);
        }
        final Object instance = Array.newInstance(clazz, length);
        while (0 < length) {
            Array.set(instance, 0, this.values[0].getValue(classLoader, classPool, method));
            int n = 0;
            ++n;
        }
        return instance;
    }
    
    @Override
    Class getType(final ClassLoader classLoader) throws ClassNotFoundException {
        if (this.type == null) {
            throw new ClassNotFoundException("no array type specified");
        }
        return Array.newInstance(this.type.getType(classLoader), 0).getClass();
    }
    
    public MemberValue getType() {
        return this.type;
    }
    
    public MemberValue[] getValue() {
        return this.values;
    }
    
    public void setValue(final MemberValue[] values) {
        this.values = values;
        if (values != null && values.length > 0) {
            this.type = values[0];
        }
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        if (this.values != null) {
            while (0 < this.values.length) {
                sb.append(this.values[0].toString());
                if (1 < this.values.length) {
                    sb.append(", ");
                }
                int n = 0;
                ++n;
            }
        }
        sb.append("}");
        return sb.toString();
    }
    
    @Override
    public void write(final AnnotationsWriter annotationsWriter) throws IOException {
        final int n = (this.values == null) ? 0 : this.values.length;
        annotationsWriter.arrayValue(n);
        while (0 < n) {
            this.values[0].write(annotationsWriter);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void accept(final MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitArrayMemberValue(this);
    }
}
