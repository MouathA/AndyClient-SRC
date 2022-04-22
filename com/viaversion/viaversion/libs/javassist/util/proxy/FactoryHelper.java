package com.viaversion.viaversion.libs.javassist.util.proxy;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.security.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.lang.invoke.*;
import java.io.*;

public class FactoryHelper
{
    public static final Class[] primitiveTypes;
    public static final String[] wrapperTypes;
    public static final String[] wrapperDesc;
    public static final String[] unwarpMethods;
    public static final String[] unwrapDesc;
    public static final int[] dataSize;
    
    public static final int typeIndex(final Class clazz) {
        for (int i = 0; i < FactoryHelper.primitiveTypes.length; ++i) {
            if (FactoryHelper.primitiveTypes[i] == clazz) {
                return i;
            }
        }
        throw new RuntimeException("bad type:" + clazz.getName());
    }
    
    @Deprecated
    public static Class toClass(final ClassFile classFile, final ClassLoader classLoader) throws CannotCompileException {
        return toClass(classFile, null, classLoader, null);
    }
    
    @Deprecated
    public static Class toClass(final ClassFile classFile, final ClassLoader classLoader, final ProtectionDomain protectionDomain) throws CannotCompileException {
        return toClass(classFile, null, classLoader, protectionDomain);
    }
    
    public static Class toClass(final ClassFile classFile, final Class clazz, final ClassLoader classLoader, final ProtectionDomain protectionDomain) throws CannotCompileException {
        try {
            final byte[] bytecode = toBytecode(classFile);
            if (ProxyFactory.onlyPublicMethods) {
                return DefineClassHelper.toPublicClass(classFile.getName(), bytecode);
            }
            return DefineClassHelper.toClass(classFile.getName(), clazz, classLoader, protectionDomain, bytecode);
        }
        catch (IOException ex) {
            throw new CannotCompileException(ex);
        }
    }
    
    public static Class toClass(final ClassFile classFile, final MethodHandles.Lookup lookup) throws CannotCompileException {
        try {
            return DefineClassHelper.toClass(lookup, toBytecode(classFile));
        }
        catch (IOException ex) {
            throw new CannotCompileException(ex);
        }
    }
    
    private static byte[] toBytecode(final ClassFile classFile) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            classFile.write(dataOutputStream);
        }
        finally {
            dataOutputStream.close();
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    public static void writeFile(final ClassFile classFile, final String s) throws CannotCompileException {
        try {
            writeFile0(classFile, s);
        }
        catch (IOException ex) {
            throw new CannotCompileException(ex);
        }
    }
    
    private static void writeFile0(final ClassFile classFile, final String s) throws CannotCompileException, IOException {
        final String string = s + File.separatorChar + classFile.getName().replace('.', File.separatorChar) + ".class";
        final int lastIndex = string.lastIndexOf(File.separatorChar);
        if (lastIndex > 0) {
            final String substring = string.substring(0, lastIndex);
            if (!substring.equals(".")) {
                new File(substring).mkdirs();
            }
        }
        final DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(string)));
        try {
            classFile.write(dataOutputStream);
        }
        catch (IOException ex) {
            throw ex;
        }
        finally {
            dataOutputStream.close();
        }
    }
    
    static {
        primitiveTypes = new Class[] { Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE };
        wrapperTypes = new String[] { "java.lang.Boolean", "java.lang.Byte", "java.lang.Character", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Void" };
        wrapperDesc = new String[] { "(Z)V", "(B)V", "(C)V", "(S)V", "(I)V", "(J)V", "(F)V", "(D)V" };
        unwarpMethods = new String[] { "booleanValue", "byteValue", "charValue", "shortValue", "intValue", "longValue", "floatValue", "doubleValue" };
        unwrapDesc = new String[] { "()Z", "()B", "()C", "()S", "()I", "()J", "()F", "()D" };
        dataSize = new int[] { 1, 1, 1, 1, 1, 2, 1, 2 };
    }
}
