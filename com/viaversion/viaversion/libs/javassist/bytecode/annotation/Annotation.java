package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.util.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.io.*;

public class Annotation
{
    ConstPool pool;
    int typeIndex;
    Map members;
    
    public Annotation(final int typeIndex, final ConstPool pool) {
        this.pool = pool;
        this.typeIndex = typeIndex;
        this.members = null;
    }
    
    public Annotation(final String s, final ConstPool constPool) {
        this(constPool.addUtf8Info(Descriptor.of(s)), constPool);
    }
    
    public Annotation(final ConstPool constPool, final CtClass ctClass) throws NotFoundException {
        this(constPool.addUtf8Info(Descriptor.of(ctClass.getName())), constPool);
        if (!ctClass.isInterface()) {
            throw new RuntimeException("Only interfaces are allowed for Annotation creation.");
        }
        final CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
        if (declaredMethods.length > 0) {
            this.members = new LinkedHashMap();
        }
        final CtMethod[] array = declaredMethods;
        while (0 < array.length) {
            final CtMethod ctMethod = array[0];
            this.addMemberValue(ctMethod.getName(), createMemberValue(constPool, ctMethod.getReturnType()));
            int n = 0;
            ++n;
        }
    }
    
    public static MemberValue createMemberValue(final ConstPool constPool, final CtClass ctClass) throws NotFoundException {
        if (ctClass == CtClass.booleanType) {
            return new BooleanMemberValue(constPool);
        }
        if (ctClass == CtClass.byteType) {
            return new ByteMemberValue(constPool);
        }
        if (ctClass == CtClass.charType) {
            return new CharMemberValue(constPool);
        }
        if (ctClass == CtClass.shortType) {
            return new ShortMemberValue(constPool);
        }
        if (ctClass == CtClass.intType) {
            return new IntegerMemberValue(constPool);
        }
        if (ctClass == CtClass.longType) {
            return new LongMemberValue(constPool);
        }
        if (ctClass == CtClass.floatType) {
            return new FloatMemberValue(constPool);
        }
        if (ctClass == CtClass.doubleType) {
            return new DoubleMemberValue(constPool);
        }
        if (ctClass.getName().equals("java.lang.Class")) {
            return new ClassMemberValue(constPool);
        }
        if (ctClass.getName().equals("java.lang.String")) {
            return new StringMemberValue(constPool);
        }
        if (ctClass.isArray()) {
            return new ArrayMemberValue(createMemberValue(constPool, ctClass.getComponentType()), constPool);
        }
        if (ctClass.isInterface()) {
            return new AnnotationMemberValue(new Annotation(constPool, ctClass), constPool);
        }
        final EnumMemberValue enumMemberValue = new EnumMemberValue(constPool);
        enumMemberValue.setType(ctClass.getName());
        return enumMemberValue;
    }
    
    public void addMemberValue(final int name, final MemberValue value) {
        final Pair pair = new Pair();
        pair.name = name;
        pair.value = value;
        this.addMemberValue(pair);
    }
    
    public void addMemberValue(final String s, final MemberValue value) {
        final Pair pair = new Pair();
        pair.name = this.pool.addUtf8Info(s);
        pair.value = value;
        if (this.members == null) {
            this.members = new LinkedHashMap();
        }
        this.members.put(s, pair);
    }
    
    private void addMemberValue(final Pair pair) {
        final String utf8Info = this.pool.getUtf8Info(pair.name);
        if (this.members == null) {
            this.members = new LinkedHashMap();
        }
        this.members.put(utf8Info, pair);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("@");
        sb.append(this.getTypeName());
        if (this.members != null) {
            sb.append("(");
            for (final String s : this.members.keySet()) {
                sb.append(s).append("=").append(this.getMemberValue(s)).append(", ");
            }
            sb.setLength(sb.length() - 2);
            sb.append(")");
        }
        return sb.toString();
    }
    
    public String getTypeName() {
        return Descriptor.toClassName(this.pool.getUtf8Info(this.typeIndex));
    }
    
    public Set getMemberNames() {
        if (this.members == null) {
            return null;
        }
        return this.members.keySet();
    }
    
    public MemberValue getMemberValue(final String s) {
        if (this.members == null || this.members.get(s) == null) {
            return null;
        }
        return this.members.get(s).value;
    }
    
    public Object toAnnotationType(final ClassLoader classLoader, final ClassPool classPool) throws ClassNotFoundException, NoSuchClassError {
        return AnnotationImpl.make(classLoader, MemberValue.loadClass(classLoader, this.getTypeName()), classPool, this);
    }
    
    public void write(final AnnotationsWriter annotationsWriter) throws IOException {
        final String utf8Info = this.pool.getUtf8Info(this.typeIndex);
        if (this.members == null) {
            annotationsWriter.annotation(utf8Info, 0);
            return;
        }
        annotationsWriter.annotation(utf8Info, this.members.size());
        for (final Pair pair : this.members.values()) {
            annotationsWriter.memberValuePair(pair.name);
            pair.value.write(annotationsWriter);
        }
    }
    
    @Override
    public int hashCode() {
        return this.getTypeName().hashCode() + ((this.members == null) ? 0 : this.members.hashCode());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof Annotation)) {
            return false;
        }
        final Annotation annotation = (Annotation)o;
        if (!this.getTypeName().equals(annotation.getTypeName())) {
            return false;
        }
        final Map members = annotation.members;
        if (this.members == members) {
            return true;
        }
        if (this.members == null) {
            return members == null;
        }
        return members != null && this.members.equals(members);
    }
    
    static class Pair
    {
        int name;
        MemberValue value;
    }
}
