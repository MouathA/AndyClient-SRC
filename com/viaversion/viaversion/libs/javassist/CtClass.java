package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.compiler.*;
import java.net.*;
import java.util.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.expr.*;
import java.lang.invoke.*;
import java.security.*;
import java.io.*;

public abstract class CtClass
{
    protected String qualifiedName;
    public static String debugDump;
    public static final String version = "3.28.0-GA";
    static final String javaLangObject = "java.lang.Object";
    public static CtClass booleanType;
    public static CtClass charType;
    public static CtClass byteType;
    public static CtClass shortType;
    public static CtClass intType;
    public static CtClass longType;
    public static CtClass floatType;
    public static CtClass doubleType;
    public static CtClass voidType;
    static CtClass[] primitiveTypes;
    
    public static void main(final String[] array) {
        System.out.println("Javassist version 3.28.0-GA");
        System.out.println("Copyright (C) 1999-2021 Shigeru Chiba. All Rights Reserved.");
    }
    
    protected CtClass(final String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(this.getClass().getName());
        sb.append("@");
        sb.append(Integer.toHexString(this.hashCode()));
        sb.append("[");
        this.extendToString(sb);
        sb.append("]");
        return sb.toString();
    }
    
    protected void extendToString(final StringBuffer sb) {
        sb.append(this.getName());
    }
    
    public ClassPool getClassPool() {
        return null;
    }
    
    public ClassFile getClassFile() {
        this.checkModify();
        return this.getClassFile2();
    }
    
    public ClassFile getClassFile2() {
        return null;
    }
    
    public AccessorMaker getAccessorMaker() {
        return null;
    }
    
    public URL getURL() throws NotFoundException {
        throw new NotFoundException(this.getName());
    }
    
    public boolean isModified() {
        return false;
    }
    
    public boolean isFrozen() {
        return true;
    }
    
    public void freeze() {
    }
    
    void checkModify() throws RuntimeException {
        if (this.isFrozen()) {
            throw new RuntimeException(this.getName() + " class is frozen");
        }
    }
    
    public void defrost() {
        throw new RuntimeException("cannot defrost " + this.getName());
    }
    
    public boolean isPrimitive() {
        return false;
    }
    
    public boolean isArray() {
        return false;
    }
    
    public boolean isKotlin() {
        return this.hasAnnotation("kotlin.Metadata");
    }
    
    public CtClass getComponentType() throws NotFoundException {
        return null;
    }
    
    public boolean subtypeOf(final CtClass ctClass) throws NotFoundException {
        return this == ctClass || this.getName().equals(ctClass.getName());
    }
    
    public String getName() {
        return this.qualifiedName;
    }
    
    public final String getSimpleName() {
        final String qualifiedName = this.qualifiedName;
        final int lastIndex = qualifiedName.lastIndexOf(46);
        if (lastIndex < 0) {
            return qualifiedName;
        }
        return qualifiedName.substring(lastIndex + 1);
    }
    
    public final String getPackageName() {
        final String qualifiedName = this.qualifiedName;
        final int lastIndex = qualifiedName.lastIndexOf(46);
        if (lastIndex < 0) {
            return null;
        }
        return qualifiedName.substring(0, lastIndex);
    }
    
    public void setName(final String qualifiedName) {
        this.checkModify();
        if (qualifiedName != null) {
            this.qualifiedName = qualifiedName;
        }
    }
    
    public String getGenericSignature() {
        return null;
    }
    
    public void setGenericSignature(final String s) {
        this.checkModify();
    }
    
    public void replaceClassName(final String s, final String s2) {
        this.checkModify();
    }
    
    public void replaceClassName(final ClassMap classMap) {
        this.checkModify();
    }
    
    public synchronized Collection getRefClasses() {
        final ClassFile classFile2 = this.getClassFile2();
        if (classFile2 != null) {
            final ClassMap classMap = new ClassMap() {
                private static final long serialVersionUID = 1L;
                final CtClass this$0;
                
                @Override
                public String put(final String s, final String s2) {
                    return this.put0(s, s2);
                }
                
                @Override
                public String get(final Object o) {
                    final String javaName = ClassMap.toJavaName((String)o);
                    this.put0(javaName, javaName);
                    return null;
                }
                
                @Override
                public void fix(final String s) {
                }
                
                @Override
                public Object put(final Object o, final Object o2) {
                    return this.put((String)o, (String)o2);
                }
                
                @Override
                public Object get(final Object o) {
                    return this.get(o);
                }
            };
            classFile2.getRefClasses(classMap);
            return classMap.values();
        }
        return null;
    }
    
    public boolean isInterface() {
        return false;
    }
    
    public boolean isAnnotation() {
        return false;
    }
    
    public boolean isEnum() {
        return false;
    }
    
    public int getModifiers() {
        return 0;
    }
    
    public boolean hasAnnotation(final Class clazz) {
        return this.hasAnnotation(clazz.getName());
    }
    
    public boolean hasAnnotation(final String s) {
        return false;
    }
    
    public Object getAnnotation(final Class clazz) throws ClassNotFoundException {
        return null;
    }
    
    public Object[] getAnnotations() throws ClassNotFoundException {
        return new Object[0];
    }
    
    public Object[] getAvailableAnnotations() {
        return new Object[0];
    }
    
    public CtClass[] getDeclaredClasses() throws NotFoundException {
        return this.getNestedClasses();
    }
    
    public CtClass[] getNestedClasses() throws NotFoundException {
        return new CtClass[0];
    }
    
    public void setModifiers(final int n) {
        this.checkModify();
    }
    
    public boolean subclassOf(final CtClass ctClass) {
        return false;
    }
    
    public CtClass getSuperclass() throws NotFoundException {
        return null;
    }
    
    public void setSuperclass(final CtClass ctClass) throws CannotCompileException {
        this.checkModify();
    }
    
    public CtClass[] getInterfaces() throws NotFoundException {
        return new CtClass[0];
    }
    
    public void setInterfaces(final CtClass[] array) {
        this.checkModify();
    }
    
    public void addInterface(final CtClass ctClass) {
        this.checkModify();
    }
    
    public CtClass getDeclaringClass() throws NotFoundException {
        return null;
    }
    
    @Deprecated
    public final CtMethod getEnclosingMethod() throws NotFoundException {
        final CtBehavior enclosingBehavior = this.getEnclosingBehavior();
        if (enclosingBehavior == null) {
            return null;
        }
        if (enclosingBehavior instanceof CtMethod) {
            return (CtMethod)enclosingBehavior;
        }
        throw new NotFoundException(enclosingBehavior.getLongName() + " is enclosing " + this.getName());
    }
    
    public CtBehavior getEnclosingBehavior() throws NotFoundException {
        return null;
    }
    
    public CtClass makeNestedClass(final String s, final boolean b) {
        throw new RuntimeException(this.getName() + " is not a class");
    }
    
    public CtField[] getFields() {
        return new CtField[0];
    }
    
    public CtField getField(final String s) throws NotFoundException {
        return this.getField(s, null);
    }
    
    public CtField getField(final String s, final String s2) throws NotFoundException {
        throw new NotFoundException(s);
    }
    
    CtField getField2(final String s, final String s2) {
        return null;
    }
    
    public CtField[] getDeclaredFields() {
        return new CtField[0];
    }
    
    public CtField getDeclaredField(final String s) throws NotFoundException {
        throw new NotFoundException(s);
    }
    
    public CtField getDeclaredField(final String s, final String s2) throws NotFoundException {
        throw new NotFoundException(s);
    }
    
    public CtBehavior[] getDeclaredBehaviors() {
        return new CtBehavior[0];
    }
    
    public CtConstructor[] getConstructors() {
        return new CtConstructor[0];
    }
    
    public CtConstructor getConstructor(final String s) throws NotFoundException {
        throw new NotFoundException("no such constructor");
    }
    
    public CtConstructor[] getDeclaredConstructors() {
        return new CtConstructor[0];
    }
    
    public CtConstructor getDeclaredConstructor(final CtClass[] array) throws NotFoundException {
        return this.getConstructor(Descriptor.ofConstructor(array));
    }
    
    public CtConstructor getClassInitializer() {
        return null;
    }
    
    public CtMethod[] getMethods() {
        return new CtMethod[0];
    }
    
    public CtMethod getMethod(final String s, final String s2) throws NotFoundException {
        throw new NotFoundException(s);
    }
    
    public CtMethod[] getDeclaredMethods() {
        return new CtMethod[0];
    }
    
    public CtMethod getDeclaredMethod(final String s, final CtClass[] array) throws NotFoundException {
        throw new NotFoundException(s);
    }
    
    public CtMethod[] getDeclaredMethods(final String s) throws NotFoundException {
        throw new NotFoundException(s);
    }
    
    public CtMethod getDeclaredMethod(final String s) throws NotFoundException {
        throw new NotFoundException(s);
    }
    
    public CtConstructor makeClassInitializer() throws CannotCompileException {
        throw new CannotCompileException("not a class");
    }
    
    public void addConstructor(final CtConstructor ctConstructor) throws CannotCompileException {
        this.checkModify();
    }
    
    public void removeConstructor(final CtConstructor ctConstructor) throws NotFoundException {
        this.checkModify();
    }
    
    public void addMethod(final CtMethod ctMethod) throws CannotCompileException {
        this.checkModify();
    }
    
    public void removeMethod(final CtMethod ctMethod) throws NotFoundException {
        this.checkModify();
    }
    
    public void addField(final CtField ctField) throws CannotCompileException {
        this.addField(ctField, (CtField.Initializer)null);
    }
    
    public void addField(final CtField ctField, final String s) throws CannotCompileException {
        this.checkModify();
    }
    
    public void addField(final CtField ctField, final CtField.Initializer initializer) throws CannotCompileException {
        this.checkModify();
    }
    
    public void removeField(final CtField ctField) throws NotFoundException {
        this.checkModify();
    }
    
    public byte[] getAttribute(final String s) {
        return null;
    }
    
    public void setAttribute(final String s, final byte[] array) {
        this.checkModify();
    }
    
    public void instrument(final CodeConverter codeConverter) throws CannotCompileException {
        this.checkModify();
    }
    
    public void instrument(final ExprEditor exprEditor) throws CannotCompileException {
        this.checkModify();
    }
    
    public Class toClass() throws CannotCompileException {
        return this.getClassPool().toClass(this);
    }
    
    public Class toClass(final Class clazz) throws CannotCompileException {
        return this.getClassPool().toClass(this, clazz);
    }
    
    public Class toClass(final MethodHandles.Lookup lookup) throws CannotCompileException {
        return this.getClassPool().toClass(this, lookup);
    }
    
    public Class toClass(ClassLoader classLoader, final ProtectionDomain protectionDomain) throws CannotCompileException {
        final ClassPool classPool = this.getClassPool();
        if (classLoader == null) {
            classLoader = classPool.getClassLoader();
        }
        return classPool.toClass(this, null, classLoader, protectionDomain);
    }
    
    @Deprecated
    public final Class toClass(final ClassLoader classLoader) throws CannotCompileException {
        return this.getClassPool().toClass(this, null, classLoader, null);
    }
    
    public void detach() {
        final ClassPool classPool = this.getClassPool();
        final CtClass removeCached = classPool.removeCached(this.getName());
        if (removeCached != this) {
            classPool.cacheCtClass(this.getName(), removeCached, false);
        }
    }
    
    public boolean stopPruning(final boolean b) {
        return true;
    }
    
    public void prune() {
    }
    
    void incGetCounter() {
    }
    
    public void rebuildClassFile() {
    }
    
    public byte[] toBytecode() throws IOException, CannotCompileException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        this.toBytecode(dataOutputStream);
        dataOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    
    public void writeFile() throws NotFoundException, IOException, CannotCompileException {
        this.writeFile(".");
    }
    
    public void writeFile(final String s) throws CannotCompileException, IOException {
        final DataOutputStream fileOutput = this.makeFileOutput(s);
        this.toBytecode(fileOutput);
        fileOutput.close();
    }
    
    protected DataOutputStream makeFileOutput(final String s) {
        final String string = s + File.separatorChar + this.getName().replace('.', File.separatorChar) + ".class";
        final int lastIndex = string.lastIndexOf(File.separatorChar);
        if (lastIndex > 0) {
            final String substring = string.substring(0, lastIndex);
            if (!substring.equals(".")) {
                new File(substring).mkdirs();
            }
        }
        return new DataOutputStream(new BufferedOutputStream(new DelayedFileOutputStream(string)));
    }
    
    public void debugWriteFile() {
        this.debugWriteFile(".");
    }
    
    public void debugWriteFile(final String s) {
        final boolean stopPruning = this.stopPruning(true);
        this.writeFile(s);
        this.defrost();
        this.stopPruning(stopPruning);
    }
    
    public void toBytecode(final DataOutputStream dataOutputStream) throws CannotCompileException, IOException {
        throw new CannotCompileException("not a class");
    }
    
    public String makeUniqueName(final String s) {
        throw new RuntimeException("not available in " + this.getName());
    }
    
    void compress() {
    }
    
    static {
        CtClass.debugDump = null;
        CtClass.primitiveTypes = new CtClass[9];
        CtClass.booleanType = new CtPrimitiveType("boolean", 'Z', "java.lang.Boolean", "booleanValue", "()Z", 172, 4, 1);
        CtClass.primitiveTypes[0] = CtClass.booleanType;
        CtClass.charType = new CtPrimitiveType("char", 'C', "java.lang.Character", "charValue", "()C", 172, 5, 1);
        CtClass.primitiveTypes[1] = CtClass.charType;
        CtClass.byteType = new CtPrimitiveType("byte", 'B', "java.lang.Byte", "byteValue", "()B", 172, 8, 1);
        CtClass.primitiveTypes[2] = CtClass.byteType;
        CtClass.shortType = new CtPrimitiveType("short", 'S', "java.lang.Short", "shortValue", "()S", 172, 9, 1);
        CtClass.primitiveTypes[3] = CtClass.shortType;
        CtClass.intType = new CtPrimitiveType("int", 'I', "java.lang.Integer", "intValue", "()I", 172, 10, 1);
        CtClass.primitiveTypes[4] = CtClass.intType;
        CtClass.longType = new CtPrimitiveType("long", 'J', "java.lang.Long", "longValue", "()J", 173, 11, 2);
        CtClass.primitiveTypes[5] = CtClass.longType;
        CtClass.floatType = new CtPrimitiveType("float", 'F', "java.lang.Float", "floatValue", "()F", 174, 6, 1);
        CtClass.primitiveTypes[6] = CtClass.floatType;
        CtClass.doubleType = new CtPrimitiveType("double", 'D', "java.lang.Double", "doubleValue", "()D", 175, 7, 2);
        CtClass.primitiveTypes[7] = CtClass.doubleType;
        CtClass.voidType = new CtPrimitiveType("void", 'V', "java.lang.Void", null, null, 177, 0, 0);
        CtClass.primitiveTypes[8] = CtClass.voidType;
    }
    
    static class DelayedFileOutputStream extends OutputStream
    {
        private FileOutputStream file;
        private String filename;
        
        DelayedFileOutputStream(final String filename) {
            this.file = null;
            this.filename = filename;
        }
        
        private void init() throws IOException {
            if (this.file == null) {
                this.file = new FileOutputStream(this.filename);
            }
        }
        
        @Override
        public void write(final int n) throws IOException {
            this.init();
            this.file.write(n);
        }
        
        @Override
        public void write(final byte[] array) throws IOException {
            this.init();
            this.file.write(array);
        }
        
        @Override
        public void write(final byte[] array, final int n, final int n2) throws IOException {
            this.init();
            this.file.write(array, n, n2);
        }
        
        @Override
        public void flush() throws IOException {
            this.init();
            this.file.flush();
        }
        
        @Override
        public void close() throws IOException {
            this.init();
            this.file.close();
        }
    }
}
