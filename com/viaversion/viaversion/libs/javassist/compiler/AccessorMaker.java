package com.viaversion.viaversion.libs.javassist.compiler;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class AccessorMaker
{
    private CtClass clazz;
    private int uniqueNumber;
    private Map accessors;
    static final String lastParamType = "com.viaversion.viaversion.libs.javassist.runtime.Inner";
    
    public AccessorMaker(final CtClass clazz) {
        this.clazz = clazz;
        this.uniqueNumber = 1;
        this.accessors = new HashMap();
    }
    
    public String getConstructor(final CtClass ctClass, final String s, final MethodInfo methodInfo) throws CompileError {
        final String string = "<init>:" + s;
        final String s2 = this.accessors.get(string);
        if (s2 != null) {
            return s2;
        }
        final String appendParameter = Descriptor.appendParameter("com.viaversion.viaversion.libs.javassist.runtime.Inner", s);
        final ClassFile classFile = this.clazz.getClassFile();
        final ConstPool constPool = classFile.getConstPool();
        final ClassPool classPool = this.clazz.getClassPool();
        final MethodInfo methodInfo2 = new MethodInfo(constPool, "<init>", appendParameter);
        methodInfo2.setAccessFlags(0);
        methodInfo2.addAttribute(new SyntheticAttribute(constPool));
        final ExceptionsAttribute exceptionsAttribute = methodInfo.getExceptionsAttribute();
        if (exceptionsAttribute != null) {
            methodInfo2.addAttribute(exceptionsAttribute.copy(constPool, null));
        }
        final CtClass[] parameterTypes = Descriptor.getParameterTypes(s, classPool);
        final Bytecode bytecode = new Bytecode(constPool);
        bytecode.addAload(0);
        while (0 < parameterTypes.length) {
            final int n = 1 + bytecode.addLoad(1, parameterTypes[0]);
            int n2 = 0;
            ++n2;
        }
        bytecode.setMaxLocals(2);
        bytecode.addInvokespecial(this.clazz, "<init>", s);
        bytecode.addReturn(null);
        methodInfo2.setCodeAttribute(bytecode.toCodeAttribute());
        classFile.addMethod(methodInfo2);
        this.accessors.put(string, appendParameter);
        return appendParameter;
    }
    
    public String getMethodAccessor(final String s, final String s2, final String s3, final MethodInfo methodInfo) throws CompileError {
        final String string = s + ":" + s2;
        final String s4 = this.accessors.get(string);
        if (s4 != null) {
            return s4;
        }
        final ClassFile classFile = this.clazz.getClassFile();
        final String accessorName = this.findAccessorName(classFile);
        final ConstPool constPool = classFile.getConstPool();
        final ClassPool classPool = this.clazz.getClassPool();
        final MethodInfo methodInfo2 = new MethodInfo(constPool, accessorName, s3);
        methodInfo2.setAccessFlags(8);
        methodInfo2.addAttribute(new SyntheticAttribute(constPool));
        final ExceptionsAttribute exceptionsAttribute = methodInfo.getExceptionsAttribute();
        if (exceptionsAttribute != null) {
            methodInfo2.addAttribute(exceptionsAttribute.copy(constPool, null));
        }
        final CtClass[] parameterTypes = Descriptor.getParameterTypes(s3, classPool);
        final Bytecode bytecode = new Bytecode(constPool);
        while (0 < parameterTypes.length) {
            final int n = 0 + bytecode.addLoad(0, parameterTypes[0]);
            int n2 = 0;
            ++n2;
        }
        bytecode.setMaxLocals(0);
        if (s2 == s3) {
            bytecode.addInvokestatic(this.clazz, s, s2);
        }
        else {
            bytecode.addInvokevirtual(this.clazz, s, s2);
        }
        bytecode.addReturn(Descriptor.getReturnType(s2, classPool));
        methodInfo2.setCodeAttribute(bytecode.toCodeAttribute());
        classFile.addMethod(methodInfo2);
        this.accessors.put(string, accessorName);
        return accessorName;
    }
    
    public MethodInfo getFieldGetter(final FieldInfo fieldInfo, final boolean b) throws CompileError {
        final String name = fieldInfo.getName();
        final String string = name + ":getter";
        final Object value = this.accessors.get(string);
        if (value != null) {
            return (MethodInfo)value;
        }
        final ClassFile classFile = this.clazz.getClassFile();
        final String accessorName = this.findAccessorName(classFile);
        final ConstPool constPool = classFile.getConstPool();
        final ClassPool classPool = this.clazz.getClassPool();
        final String descriptor = fieldInfo.getDescriptor();
        String s;
        if (b) {
            s = "()" + descriptor;
        }
        else {
            s = "(" + Descriptor.of(this.clazz) + ")" + descriptor;
        }
        final MethodInfo methodInfo = new MethodInfo(constPool, accessorName, s);
        methodInfo.setAccessFlags(8);
        methodInfo.addAttribute(new SyntheticAttribute(constPool));
        final Bytecode bytecode = new Bytecode(constPool);
        if (b) {
            bytecode.addGetstatic(Bytecode.THIS, name, descriptor);
        }
        else {
            bytecode.addAload(0);
            bytecode.addGetfield(Bytecode.THIS, name, descriptor);
            bytecode.setMaxLocals(1);
        }
        bytecode.addReturn(Descriptor.toCtClass(descriptor, classPool));
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        classFile.addMethod(methodInfo);
        this.accessors.put(string, methodInfo);
        return methodInfo;
    }
    
    public MethodInfo getFieldSetter(final FieldInfo fieldInfo, final boolean b) throws CompileError {
        final String name = fieldInfo.getName();
        final String string = name + ":setter";
        final Object value = this.accessors.get(string);
        if (value != null) {
            return (MethodInfo)value;
        }
        final ClassFile classFile = this.clazz.getClassFile();
        final String accessorName = this.findAccessorName(classFile);
        final ConstPool constPool = classFile.getConstPool();
        final ClassPool classPool = this.clazz.getClassPool();
        final String descriptor = fieldInfo.getDescriptor();
        String s;
        if (b) {
            s = "(" + descriptor + ")V";
        }
        else {
            s = "(" + Descriptor.of(this.clazz) + descriptor + ")V";
        }
        final MethodInfo methodInfo = new MethodInfo(constPool, accessorName, s);
        methodInfo.setAccessFlags(8);
        methodInfo.addAttribute(new SyntheticAttribute(constPool));
        final Bytecode bytecode = new Bytecode(constPool);
        int addLoad;
        if (b) {
            addLoad = bytecode.addLoad(0, Descriptor.toCtClass(descriptor, classPool));
            bytecode.addPutstatic(Bytecode.THIS, name, descriptor);
        }
        else {
            bytecode.addAload(0);
            addLoad = bytecode.addLoad(1, Descriptor.toCtClass(descriptor, classPool)) + 1;
            bytecode.addPutfield(Bytecode.THIS, name, descriptor);
        }
        bytecode.addReturn(null);
        bytecode.setMaxLocals(addLoad);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        classFile.addMethod(methodInfo);
        this.accessors.put(string, methodInfo);
        return methodInfo;
    }
    
    private String findAccessorName(final ClassFile classFile) {
        String string;
        do {
            string = "access$" + this.uniqueNumber++;
        } while (classFile.getMethod(string) != null);
        return string;
    }
}
