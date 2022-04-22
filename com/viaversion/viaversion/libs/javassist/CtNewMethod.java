package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.compiler.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.util.*;

public class CtNewMethod
{
    public static CtMethod make(final String s, final CtClass ctClass) throws CannotCompileException {
        return make(s, ctClass, null, null);
    }
    
    public static CtMethod make(final String s, final CtClass ctClass, final String s2, final String s3) throws CannotCompileException {
        final Javac javac = new Javac(ctClass);
        if (s3 != null) {
            javac.recordProceed(s2, s3);
        }
        final CtMember compile = javac.compile(s);
        if (compile instanceof CtMethod) {
            return (CtMethod)compile;
        }
        throw new CannotCompileException("not a method");
    }
    
    public static CtMethod make(final CtClass ctClass, final String s, final CtClass[] array, final CtClass[] array2, final String s2, final CtClass ctClass2) throws CannotCompileException {
        return make(1, ctClass, s, array, array2, s2, ctClass2);
    }
    
    public static CtMethod make(final int modifiers, final CtClass ctClass, final String s, final CtClass[] array, final CtClass[] exceptionTypes, final String body, final CtClass ctClass2) throws CannotCompileException {
        final CtMethod ctMethod = new CtMethod(ctClass, s, array, ctClass2);
        ctMethod.setModifiers(modifiers);
        ctMethod.setExceptionTypes(exceptionTypes);
        ctMethod.setBody(body);
        return ctMethod;
    }
    
    public static CtMethod copy(final CtMethod ctMethod, final CtClass ctClass, final ClassMap classMap) throws CannotCompileException {
        return new CtMethod(ctMethod, ctClass, classMap);
    }
    
    public static CtMethod copy(final CtMethod ctMethod, final String name, final CtClass ctClass, final ClassMap classMap) throws CannotCompileException {
        final CtMethod ctMethod2 = new CtMethod(ctMethod, ctClass, classMap);
        ctMethod2.setName(name);
        return ctMethod2;
    }
    
    public static CtMethod abstractMethod(final CtClass ctClass, final String s, final CtClass[] array, final CtClass[] exceptionTypes, final CtClass ctClass2) throws NotFoundException {
        final CtMethod ctMethod = new CtMethod(ctClass, s, array, ctClass2);
        ctMethod.setExceptionTypes(exceptionTypes);
        return ctMethod;
    }
    
    public static CtMethod getter(final String s, final CtField ctField) throws CannotCompileException {
        final FieldInfo fieldInfo2 = ctField.getFieldInfo2();
        final String descriptor = fieldInfo2.getDescriptor();
        final String string = "()" + descriptor;
        final ConstPool constPool = fieldInfo2.getConstPool();
        final MethodInfo methodInfo = new MethodInfo(constPool, s, string);
        methodInfo.setAccessFlags(1);
        final Bytecode bytecode = new Bytecode(constPool, 2, 1);
        final String name = fieldInfo2.getName();
        if ((fieldInfo2.getAccessFlags() & 0x8) == 0x0) {
            bytecode.addAload(0);
            bytecode.addGetfield(Bytecode.THIS, name, descriptor);
        }
        else {
            bytecode.addGetstatic(Bytecode.THIS, name, descriptor);
        }
        bytecode.addReturn(ctField.getType());
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        return new CtMethod(methodInfo, ctField.getDeclaringClass());
    }
    
    public static CtMethod setter(final String s, final CtField ctField) throws CannotCompileException {
        final FieldInfo fieldInfo2 = ctField.getFieldInfo2();
        final String descriptor = fieldInfo2.getDescriptor();
        final String string = "(" + descriptor + ")V";
        final ConstPool constPool = fieldInfo2.getConstPool();
        final MethodInfo methodInfo = new MethodInfo(constPool, s, string);
        methodInfo.setAccessFlags(1);
        final Bytecode bytecode = new Bytecode(constPool, 3, 3);
        final String name = fieldInfo2.getName();
        if ((fieldInfo2.getAccessFlags() & 0x8) == 0x0) {
            bytecode.addAload(0);
            bytecode.addLoad(1, ctField.getType());
            bytecode.addPutfield(Bytecode.THIS, name, descriptor);
        }
        else {
            bytecode.addLoad(1, ctField.getType());
            bytecode.addPutstatic(Bytecode.THIS, name, descriptor);
        }
        bytecode.addReturn(null);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        return new CtMethod(methodInfo, ctField.getDeclaringClass());
    }
    
    public static CtMethod delegator(final CtMethod ctMethod, final CtClass ctClass) throws CannotCompileException {
        return delegator0(ctMethod, ctClass);
    }
    
    private static CtMethod delegator0(final CtMethod ctMethod, final CtClass ctClass) throws CannotCompileException, NotFoundException {
        final MethodInfo methodInfo2 = ctMethod.getMethodInfo2();
        final String name = methodInfo2.getName();
        final String descriptor = methodInfo2.getDescriptor();
        final ConstPool constPool = ctClass.getClassFile2().getConstPool();
        final MethodInfo methodInfo3 = new MethodInfo(constPool, name, descriptor);
        methodInfo3.setAccessFlags(methodInfo2.getAccessFlags());
        final ExceptionsAttribute exceptionsAttribute = methodInfo2.getExceptionsAttribute();
        if (exceptionsAttribute != null) {
            methodInfo3.setExceptionsAttribute((ExceptionsAttribute)exceptionsAttribute.copy(constPool, null));
        }
        final Bytecode bytecode = new Bytecode(constPool, 0, 0);
        final boolean static1 = Modifier.isStatic(ctMethod.getModifiers());
        final CtClass declaringClass = ctMethod.getDeclaringClass();
        final CtClass[] parameterTypes = ctMethod.getParameterTypes();
        int n;
        if (static1) {
            n = bytecode.addLoadParameters(parameterTypes, 0);
            bytecode.addInvokestatic(declaringClass, name, descriptor);
        }
        else {
            bytecode.addLoad(0, declaringClass);
            n = bytecode.addLoadParameters(parameterTypes, 1);
            bytecode.addInvokespecial(declaringClass, name, descriptor);
        }
        bytecode.addReturn(ctMethod.getReturnType());
        bytecode.setMaxLocals(++n);
        bytecode.setMaxStack((n < 2) ? 2 : n);
        methodInfo3.setCodeAttribute(bytecode.toCodeAttribute());
        return new CtMethod(methodInfo3, ctClass);
    }
    
    public static CtMethod wrapped(final CtClass ctClass, final String s, final CtClass[] array, final CtClass[] array2, final CtMethod ctMethod, final CtMethod.ConstParameter constParameter, final CtClass ctClass2) throws CannotCompileException {
        return CtNewWrappedMethod.wrapped(ctClass, s, array, array2, ctMethod, constParameter, ctClass2);
    }
}
