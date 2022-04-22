package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class AnnotationMemberValue extends MemberValue
{
    Annotation value;
    
    public AnnotationMemberValue(final ConstPool constPool) {
        this(null, constPool);
    }
    
    public AnnotationMemberValue(final Annotation value, final ConstPool constPool) {
        super('@', constPool);
        this.value = value;
    }
    
    @Override
    Object getValue(final ClassLoader classLoader, final ClassPool classPool, final Method method) throws ClassNotFoundException {
        return AnnotationImpl.make(classLoader, this.getType(classLoader), classPool, this.value);
    }
    
    @Override
    Class getType(final ClassLoader classLoader) throws ClassNotFoundException {
        if (this.value == null) {
            throw new ClassNotFoundException("no type specified");
        }
        return MemberValue.loadClass(classLoader, this.value.getTypeName());
    }
    
    public Annotation getValue() {
        return this.value;
    }
    
    public void setValue(final Annotation value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return this.value.toString();
    }
    
    @Override
    public void write(final AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.annotationValue();
        this.value.write(annotationsWriter);
    }
    
    @Override
    public void accept(final MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitAnnotationMemberValue(this);
    }
}
