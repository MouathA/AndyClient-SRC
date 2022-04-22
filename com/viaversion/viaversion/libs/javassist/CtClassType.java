package com.viaversion.viaversion.libs.javassist;

import java.io.*;
import java.net.*;
import com.viaversion.viaversion.libs.javassist.bytecode.annotation.*;
import java.lang.ref.*;
import com.viaversion.viaversion.libs.javassist.expr.*;
import com.viaversion.viaversion.libs.javassist.compiler.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.util.*;

class CtClassType extends CtClass
{
    ClassPool classPool;
    boolean wasChanged;
    private boolean wasFrozen;
    boolean wasPruned;
    boolean gcConstPool;
    ClassFile classfile;
    byte[] rawClassfile;
    private Reference memberCache;
    private AccessorMaker accessors;
    private FieldInitLink fieldInitializers;
    private Map hiddenMethods;
    private int uniqueNumberSeed;
    private boolean doPruning;
    private int getCount;
    private static final int GET_THRESHOLD = 2;
    
    CtClassType(final String s, final ClassPool classPool) {
        super(s);
        this.doPruning = ClassPool.doPruning;
        this.classPool = classPool;
        final boolean b = false;
        this.gcConstPool = b;
        this.wasPruned = b;
        this.wasFrozen = b;
        this.wasChanged = b;
        this.classfile = null;
        this.rawClassfile = null;
        this.memberCache = null;
        this.accessors = null;
        this.fieldInitializers = null;
        this.hiddenMethods = null;
        this.uniqueNumberSeed = 0;
        this.getCount = 0;
    }
    
    CtClassType(final InputStream inputStream, final ClassPool classPool) throws IOException {
        this((String)null, classPool);
        this.classfile = new ClassFile(new DataInputStream(inputStream));
        this.qualifiedName = this.classfile.getName();
    }
    
    CtClassType(final ClassFile classfile, final ClassPool classPool) {
        this((String)null, classPool);
        this.classfile = classfile;
        this.qualifiedName = this.classfile.getName();
    }
    
    @Override
    protected void extendToString(final StringBuffer p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/viaversion/viaversion/libs/javassist/CtClassType.wasChanged:Z
        //     4: ifeq            14
        //     7: aload_1        
        //     8: ldc             "changed "
        //    10: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    13: pop            
        //    14: aload_0        
        //    15: getfield        com/viaversion/viaversion/libs/javassist/CtClassType.wasFrozen:Z
        //    18: ifeq            28
        //    21: aload_1        
        //    22: ldc             "frozen "
        //    24: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    27: pop            
        //    28: aload_0        
        //    29: getfield        com/viaversion/viaversion/libs/javassist/CtClassType.wasPruned:Z
        //    32: ifeq            42
        //    35: aload_1        
        //    36: ldc             "pruned "
        //    38: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    41: pop            
        //    42: aload_1        
        //    43: aload_0        
        //    44: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClassType.getModifiers:()I
        //    47: invokestatic    com/viaversion/viaversion/libs/javassist/Modifier.toString:(I)Ljava/lang/String;
        //    50: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    53: pop            
        //    54: aload_1        
        //    55: ldc             " class "
        //    57: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    60: pop            
        //    61: aload_1        
        //    62: aload_0        
        //    63: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClassType.getName:()Ljava/lang/String;
        //    66: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    69: pop            
        //    70: aload_0        
        //    71: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClassType.getSuperclass:()Lcom/viaversion/viaversion/libs/javassist/CtClass;
        //    74: astore_2       
        //    75: aload_2        
        //    76: ifnull          120
        //    79: aload_2        
        //    80: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClass.getName:()Ljava/lang/String;
        //    83: astore_3       
        //    84: aload_3        
        //    85: ldc             "java.lang.Object"
        //    87: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    90: ifne            120
        //    93: aload_1        
        //    94: new             Ljava/lang/StringBuilder;
        //    97: dup            
        //    98: invokespecial   java/lang/StringBuilder.<init>:()V
        //   101: ldc             " extends "
        //   103: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   106: aload_2        
        //   107: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClass.getName:()Ljava/lang/String;
        //   110: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   113: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   116: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   119: pop            
        //   120: goto            131
        //   123: astore_2       
        //   124: aload_1        
        //   125: ldc             " extends ??"
        //   127: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   130: pop            
        //   131: aload_0        
        //   132: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClassType.getInterfaces:()[Lcom/viaversion/viaversion/libs/javassist/CtClass;
        //   135: astore_2       
        //   136: aload_2        
        //   137: arraylength    
        //   138: ifle            148
        //   141: aload_1        
        //   142: ldc             " implements "
        //   144: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   147: pop            
        //   148: iconst_0       
        //   149: aload_2        
        //   150: arraylength    
        //   151: if_icmpge       178
        //   154: aload_1        
        //   155: aload_2        
        //   156: iconst_0       
        //   157: aaload         
        //   158: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClass.getName:()Ljava/lang/String;
        //   161: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   164: pop            
        //   165: aload_1        
        //   166: ldc             ", "
        //   168: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   171: pop            
        //   172: iinc            3, 1
        //   175: goto            148
        //   178: goto            189
        //   181: astore_2       
        //   182: aload_1        
        //   183: ldc             " extends ??"
        //   185: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   188: pop            
        //   189: aload_0        
        //   190: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClassType.getMembers:()Lcom/viaversion/viaversion/libs/javassist/CtMember$Cache;
        //   193: astore_2       
        //   194: aload_0        
        //   195: aload_1        
        //   196: ldc             " fields="
        //   198: aload_2        
        //   199: invokevirtual   com/viaversion/viaversion/libs/javassist/CtMember$Cache.fieldHead:()Lcom/viaversion/viaversion/libs/javassist/CtMember;
        //   202: aload_2        
        //   203: invokevirtual   com/viaversion/viaversion/libs/javassist/CtMember$Cache.lastField:()Lcom/viaversion/viaversion/libs/javassist/CtMember;
        //   206: invokespecial   com/viaversion/viaversion/libs/javassist/CtClassType.exToString:(Ljava/lang/StringBuffer;Ljava/lang/String;Lcom/viaversion/viaversion/libs/javassist/CtMember;Lcom/viaversion/viaversion/libs/javassist/CtMember;)V
        //   209: aload_0        
        //   210: aload_1        
        //   211: ldc             " constructors="
        //   213: aload_2        
        //   214: invokevirtual   com/viaversion/viaversion/libs/javassist/CtMember$Cache.consHead:()Lcom/viaversion/viaversion/libs/javassist/CtMember;
        //   217: aload_2        
        //   218: invokevirtual   com/viaversion/viaversion/libs/javassist/CtMember$Cache.lastCons:()Lcom/viaversion/viaversion/libs/javassist/CtMember;
        //   221: invokespecial   com/viaversion/viaversion/libs/javassist/CtClassType.exToString:(Ljava/lang/StringBuffer;Ljava/lang/String;Lcom/viaversion/viaversion/libs/javassist/CtMember;Lcom/viaversion/viaversion/libs/javassist/CtMember;)V
        //   224: aload_0        
        //   225: aload_1        
        //   226: ldc             " methods="
        //   228: aload_2        
        //   229: invokevirtual   com/viaversion/viaversion/libs/javassist/CtMember$Cache.methodHead:()Lcom/viaversion/viaversion/libs/javassist/CtMember;
        //   232: aload_2        
        //   233: invokevirtual   com/viaversion/viaversion/libs/javassist/CtMember$Cache.lastMethod:()Lcom/viaversion/viaversion/libs/javassist/CtMember;
        //   236: invokespecial   com/viaversion/viaversion/libs/javassist/CtClassType.exToString:(Ljava/lang/StringBuffer;Ljava/lang/String;Lcom/viaversion/viaversion/libs/javassist/CtMember;Lcom/viaversion/viaversion/libs/javassist/CtMember;)V
        //   239: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void exToString(final StringBuffer sb, final String s, CtMember next, final CtMember ctMember) {
        sb.append(s);
        while (next != ctMember) {
            next = next.next();
            sb.append(next);
            sb.append(", ");
        }
    }
    
    @Override
    public AccessorMaker getAccessorMaker() {
        if (this.accessors == null) {
            this.accessors = new AccessorMaker(this);
        }
        return this.accessors;
    }
    
    @Override
    public ClassFile getClassFile2() {
        return this.getClassFile3(true);
    }
    
    public ClassFile getClassFile3(final boolean b) {
        final ClassFile classfile = this.classfile;
        if (classfile != null) {
            return classfile;
        }
        if (b) {
            this.classPool.compress();
        }
        // monitorenter(this)
        final ClassFile classfile2 = this.classfile;
        if (classfile2 != null) {
            // monitorexit(this)
            return classfile2;
        }
        final byte[] rawClassfile = this.rawClassfile;
        // monitorexit(this)
        if (rawClassfile != null) {
            final ClassFile classFile2 = new ClassFile(new DataInputStream(new ByteArrayInputStream(rawClassfile)));
            this.getCount = 2;
            // monitorenter(this)
            this.rawClassfile = null;
            // monitorexit(this)
            return this.setClassFile(classFile2);
        }
        final InputStream openClassfile = this.classPool.openClassfile(this.getName());
        if (openClassfile == null) {
            throw new NotFoundException(this.getName());
        }
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(openClassfile);
        final ClassFile classFile3 = new ClassFile(new DataInputStream(bufferedInputStream));
        if (!classFile3.getName().equals(this.qualifiedName)) {
            throw new RuntimeException("cannot find " + this.qualifiedName + ": " + classFile3.getName() + " found in " + this.qualifiedName.replace('.', '/') + ".class");
        }
        final ClassFile setClassFile2 = this.setClassFile(classFile3);
        if (bufferedInputStream != null) {
            bufferedInputStream.close();
        }
        return setClassFile2;
    }
    
    @Override
    final void incGetCounter() {
        ++this.getCount;
    }
    
    @Override
    void compress() {
        if (this.getCount < 2) {
            if (!this.isModified() && ClassPool.releaseUnmodifiedClassFile) {
                this.removeClassFile();
            }
            else if (this.isFrozen() && !this.wasPruned) {
                this.saveClassFile();
            }
        }
        this.getCount = 0;
    }
    
    private synchronized void saveClassFile() {
        if (this.classfile == null || this.hasMemberCache() != null) {
            return;
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.classfile.write(new DataOutputStream(byteArrayOutputStream));
        byteArrayOutputStream.close();
        this.rawClassfile = byteArrayOutputStream.toByteArray();
        this.classfile = null;
    }
    
    private synchronized void removeClassFile() {
        if (this.classfile != null && !this.isModified() && this.hasMemberCache() == null) {
            this.classfile = null;
        }
    }
    
    private synchronized ClassFile setClassFile(final ClassFile classfile) {
        if (this.classfile == null) {
            this.classfile = classfile;
        }
        return this.classfile;
    }
    
    @Override
    public ClassPool getClassPool() {
        return this.classPool;
    }
    
    void setClassPool(final ClassPool classPool) {
        this.classPool = classPool;
    }
    
    @Override
    public URL getURL() throws NotFoundException {
        final URL find = this.classPool.find(this.getName());
        if (find == null) {
            throw new NotFoundException(this.getName());
        }
        return find;
    }
    
    @Override
    public boolean isModified() {
        return this.wasChanged;
    }
    
    @Override
    public boolean isFrozen() {
        return this.wasFrozen;
    }
    
    @Override
    public void freeze() {
        this.wasFrozen = true;
    }
    
    @Override
    void checkModify() throws RuntimeException {
        if (this.isFrozen()) {
            String s = this.getName() + " class is frozen";
            if (this.wasPruned) {
                s += " and pruned";
            }
            throw new RuntimeException(s);
        }
        this.wasChanged = true;
    }
    
    @Override
    public void defrost() {
        this.checkPruned("defrost");
        this.wasFrozen = false;
    }
    
    @Override
    public boolean subtypeOf(final CtClass ctClass) throws NotFoundException {
        final String name = ctClass.getName();
        if (this == ctClass || this.getName().equals(name)) {
            return true;
        }
        final ClassFile classFile2 = this.getClassFile2();
        final String superclass = classFile2.getSuperclass();
        if (superclass != null && superclass.equals(name)) {
            return true;
        }
        final String[] interfaces = classFile2.getInterfaces();
        final int length = interfaces.length;
        int n = 0;
        while (0 < length) {
            if (interfaces[0].equals(name)) {
                return true;
            }
            ++n;
        }
        if (superclass != null && this.classPool.get(superclass).subtypeOf(ctClass)) {
            return true;
        }
        while (0 < length) {
            if (this.classPool.get(interfaces[0]).subtypeOf(ctClass)) {
                return true;
            }
            ++n;
        }
        return false;
    }
    
    @Override
    public void setName(final String s) throws RuntimeException {
        final String name = this.getName();
        if (s.equals(name)) {
            return;
        }
        this.classPool.checkNotFrozen(s);
        final ClassFile classFile2 = this.getClassFile2();
        super.setName(s);
        classFile2.setName(s);
        this.nameReplaced();
        this.classPool.classNameChanged(name, this);
    }
    
    @Override
    public String getGenericSignature() {
        final SignatureAttribute signatureAttribute = (SignatureAttribute)this.getClassFile2().getAttribute("Signature");
        return (signatureAttribute == null) ? null : signatureAttribute.getSignature();
    }
    
    @Override
    public void setGenericSignature(final String s) {
        final ClassFile classFile = this.getClassFile();
        classFile.addAttribute(new SignatureAttribute(classFile.getConstPool(), s));
    }
    
    @Override
    public void replaceClassName(final ClassMap classMap) throws RuntimeException {
        final String name = this.getName();
        String name2 = classMap.get((Object)Descriptor.toJvmName(name));
        if (name2 != null) {
            name2 = Descriptor.toJavaName(name2);
            this.classPool.checkNotFrozen(name2);
        }
        super.replaceClassName(classMap);
        this.getClassFile2().renameClass(classMap);
        this.nameReplaced();
        if (name2 != null) {
            super.setName(name2);
            this.classPool.classNameChanged(name, this);
        }
    }
    
    @Override
    public void replaceClassName(final String s, final String name) throws RuntimeException {
        if (this.getName().equals(s)) {
            this.setName(name);
        }
        else {
            super.replaceClassName(s, name);
            this.getClassFile2().renameClass(s, name);
            this.nameReplaced();
        }
    }
    
    @Override
    public boolean isInterface() {
        return Modifier.isInterface(this.getModifiers());
    }
    
    @Override
    public boolean isAnnotation() {
        return Modifier.isAnnotation(this.getModifiers());
    }
    
    @Override
    public boolean isEnum() {
        return Modifier.isEnum(this.getModifiers());
    }
    
    @Override
    public int getModifiers() {
        final ClassFile classFile2 = this.getClassFile2();
        int clear = AccessFlag.clear(classFile2.getAccessFlags(), 32);
        final int innerAccessFlags = classFile2.getInnerAccessFlags();
        if (innerAccessFlags != -1) {
            if ((innerAccessFlags & 0x8) != 0x0) {
                clear |= 0x8;
            }
            if ((innerAccessFlags & 0x1) != 0x0) {
                clear |= 0x1;
            }
            else {
                clear &= 0xFFFFFFFE;
                if ((innerAccessFlags & 0x4) != 0x0) {
                    clear |= 0x4;
                }
                else if ((innerAccessFlags & 0x2) != 0x0) {
                    clear |= 0x2;
                }
            }
        }
        return AccessFlag.toModifier(clear);
    }
    
    @Override
    public CtClass[] getNestedClasses() throws NotFoundException {
        final ClassFile classFile2 = this.getClassFile2();
        final InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute)classFile2.getAttribute("InnerClasses");
        if (innerClassesAttribute == null) {
            return new CtClass[0];
        }
        final String string = classFile2.getName() + "$";
        final int tableLength = innerClassesAttribute.tableLength();
        final ArrayList list = new ArrayList<CtClass>(tableLength);
        while (0 < tableLength) {
            final String innerClass = innerClassesAttribute.innerClass(0);
            if (innerClass != null && innerClass.startsWith(string) && innerClass.lastIndexOf(36) < string.length()) {
                list.add(this.classPool.get(innerClass));
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new CtClass[list.size()]);
    }
    
    @Override
    public void setModifiers(final int n) {
        this.checkModify();
        updateInnerEntry(n, this.getName(), this, true);
        this.getClassFile2().setAccessFlags(AccessFlag.of(n & 0xFFFFFFF7));
    }
    
    private static void updateInnerEntry(final int n, final String s, final CtClass ctClass, final boolean b) {
        final InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute)ctClass.getClassFile2().getAttribute("InnerClasses");
        if (innerClassesAttribute != null) {
            final int n2 = n & 0xFFFFFFF7;
            final int find = innerClassesAttribute.find(s);
            if (find >= 0) {
                final int n3 = innerClassesAttribute.accessFlags(find) & 0x8;
                if (n3 != 0 || !Modifier.isStatic(n)) {
                    ctClass.checkModify();
                    innerClassesAttribute.setAccessFlags(find, AccessFlag.of(n2) | n3);
                    final String outerClass = innerClassesAttribute.outerClass(find);
                    if (outerClass != null && b) {
                        updateInnerEntry(n2, s, ctClass.getClassPool().get(outerClass), false);
                    }
                    return;
                }
            }
        }
        if (Modifier.isStatic(n)) {
            throw new RuntimeException("cannot change " + Descriptor.toJavaName(s) + " into a static class");
        }
    }
    
    @Override
    public boolean hasAnnotation(final String s) {
        final ClassFile classFile2 = this.getClassFile2();
        return hasAnnotationType(s, this.getClassPool(), (AnnotationsAttribute)classFile2.getAttribute("RuntimeInvisibleAnnotations"), (AnnotationsAttribute)classFile2.getAttribute("RuntimeVisibleAnnotations"));
    }
    
    @Deprecated
    static boolean hasAnnotationType(final Class clazz, final ClassPool classPool, final AnnotationsAttribute annotationsAttribute, final AnnotationsAttribute annotationsAttribute2) {
        return hasAnnotationType(clazz.getName(), classPool, annotationsAttribute, annotationsAttribute2);
    }
    
    static boolean hasAnnotationType(final String s, final ClassPool classPool, final AnnotationsAttribute annotationsAttribute, final AnnotationsAttribute annotationsAttribute2) {
        Annotation[] annotations;
        if (annotationsAttribute == null) {
            annotations = null;
        }
        else {
            annotations = annotationsAttribute.getAnnotations();
        }
        Annotation[] annotations2;
        if (annotationsAttribute2 == null) {
            annotations2 = null;
        }
        else {
            annotations2 = annotationsAttribute2.getAnnotations();
        }
        int n = 0;
        if (annotations != null) {
            while (0 < annotations.length) {
                if (annotations[0].getTypeName().equals(s)) {
                    return true;
                }
                ++n;
            }
        }
        if (annotations2 != null) {
            while (0 < annotations2.length) {
                if (annotations2[0].getTypeName().equals(s)) {
                    return true;
                }
                ++n;
            }
        }
        return false;
    }
    
    @Override
    public Object getAnnotation(final Class clazz) throws ClassNotFoundException {
        final ClassFile classFile2 = this.getClassFile2();
        return getAnnotationType(clazz, this.getClassPool(), (AnnotationsAttribute)classFile2.getAttribute("RuntimeInvisibleAnnotations"), (AnnotationsAttribute)classFile2.getAttribute("RuntimeVisibleAnnotations"));
    }
    
    static Object getAnnotationType(final Class clazz, final ClassPool classPool, final AnnotationsAttribute annotationsAttribute, final AnnotationsAttribute annotationsAttribute2) throws ClassNotFoundException {
        Annotation[] annotations;
        if (annotationsAttribute == null) {
            annotations = null;
        }
        else {
            annotations = annotationsAttribute.getAnnotations();
        }
        Annotation[] annotations2;
        if (annotationsAttribute2 == null) {
            annotations2 = null;
        }
        else {
            annotations2 = annotationsAttribute2.getAnnotations();
        }
        final String name = clazz.getName();
        int n = 0;
        if (annotations != null) {
            while (0 < annotations.length) {
                if (annotations[0].getTypeName().equals(name)) {
                    return toAnnoType(annotations[0], classPool);
                }
                ++n;
            }
        }
        if (annotations2 != null) {
            while (0 < annotations2.length) {
                if (annotations2[0].getTypeName().equals(name)) {
                    return toAnnoType(annotations2[0], classPool);
                }
                ++n;
            }
        }
        return null;
    }
    
    @Override
    public Object[] getAnnotations() throws ClassNotFoundException {
        return this.getAnnotations(false);
    }
    
    @Override
    public Object[] getAvailableAnnotations() {
        return this.getAnnotations(true);
    }
    
    private Object[] getAnnotations(final boolean b) throws ClassNotFoundException {
        final ClassFile classFile2 = this.getClassFile2();
        return toAnnotationType(b, this.getClassPool(), (AnnotationsAttribute)classFile2.getAttribute("RuntimeInvisibleAnnotations"), (AnnotationsAttribute)classFile2.getAttribute("RuntimeVisibleAnnotations"));
    }
    
    static Object[] toAnnotationType(final boolean b, final ClassPool classPool, final AnnotationsAttribute annotationsAttribute, final AnnotationsAttribute annotationsAttribute2) throws ClassNotFoundException {
        Annotation[] annotations;
        if (annotationsAttribute == null) {
            annotations = null;
        }
        else {
            annotations = annotationsAttribute.getAnnotations();
            final int length = annotations.length;
        }
        Annotation[] annotations2;
        if (annotationsAttribute2 == null) {
            annotations2 = null;
        }
        else {
            annotations2 = annotationsAttribute2.getAnnotations();
            final int length2 = annotations2.length;
        }
        int n = 0;
        if (!b) {
            final Object[] array = new Object[0];
            while (0 < 0) {
                array[0] = toAnnoType(annotations[0], classPool);
                ++n;
            }
            while (0 < 0) {
                array[0] = toAnnoType(annotations2[0], classPool);
                ++n;
            }
            return array;
        }
        final ArrayList<Object> list = new ArrayList<Object>();
        while (0 < 0) {
            list.add(toAnnoType(annotations[0], classPool));
            ++n;
        }
        while (0 < 0) {
            list.add(toAnnoType(annotations2[0], classPool));
            ++n;
        }
        return list.toArray();
    }
    
    static Object[][] toAnnotationType(final boolean p0, final ClassPool p1, final ParameterAnnotationsAttribute p2, final ParameterAnnotationsAttribute p3, final MethodInfo p4) throws ClassNotFoundException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnull          13
        //     4: aload_2        
        //     5: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/ParameterAnnotationsAttribute.numParameters:()I
        //     8: istore          5
        //    10: goto            36
        //    13: aload_3        
        //    14: ifnull          26
        //    17: aload_3        
        //    18: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/ParameterAnnotationsAttribute.numParameters:()I
        //    21: istore          5
        //    23: goto            36
        //    26: aload           4
        //    28: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/MethodInfo.getDescriptor:()Ljava/lang/String;
        //    31: invokestatic    com/viaversion/viaversion/libs/javassist/bytecode/Descriptor.numOfParameters:(Ljava/lang/String;)I
        //    34: istore          5
        //    36: iconst_0       
        //    37: anewarray       [Ljava/lang/Object;
        //    40: astore          6
        //    42: iconst_0       
        //    43: iconst_0       
        //    44: if_icmpge       248
        //    47: aload_2        
        //    48: ifnonnull       57
        //    51: aconst_null    
        //    52: astore          8
        //    54: goto            70
        //    57: aload_2        
        //    58: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/ParameterAnnotationsAttribute.getAnnotations:()[[Lcom/viaversion/viaversion/libs/javassist/bytecode/annotation/Annotation;
        //    61: iconst_0       
        //    62: aaload         
        //    63: astore          8
        //    65: aload           8
        //    67: arraylength    
        //    68: istore          10
        //    70: aload_3        
        //    71: ifnonnull       80
        //    74: aconst_null    
        //    75: astore          9
        //    77: goto            93
        //    80: aload_3        
        //    81: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/ParameterAnnotationsAttribute.getAnnotations:()[[Lcom/viaversion/viaversion/libs/javassist/bytecode/annotation/Annotation;
        //    84: iconst_0       
        //    85: aaload         
        //    86: astore          9
        //    88: aload           9
        //    90: arraylength    
        //    91: istore          11
        //    93: iload_0        
        //    94: ifne            158
        //    97: aload           6
        //    99: iconst_0       
        //   100: iconst_0       
        //   101: anewarray       Ljava/lang/Object;
        //   104: aastore        
        //   105: iconst_0       
        //   106: iconst_0       
        //   107: if_icmpge       130
        //   110: aload           6
        //   112: iconst_0       
        //   113: aaload         
        //   114: iconst_0       
        //   115: aload           8
        //   117: iconst_0       
        //   118: aaload         
        //   119: aload_1        
        //   120: invokestatic    com/viaversion/viaversion/libs/javassist/CtClassType.toAnnoType:(Lcom/viaversion/viaversion/libs/javassist/bytecode/annotation/Annotation;Lcom/viaversion/viaversion/libs/javassist/ClassPool;)Ljava/lang/Object;
        //   123: aastore        
        //   124: iinc            12, 1
        //   127: goto            105
        //   130: iconst_0       
        //   131: iconst_0       
        //   132: if_icmpge       155
        //   135: aload           6
        //   137: iconst_0       
        //   138: aaload         
        //   139: iconst_0       
        //   140: aload           9
        //   142: iconst_0       
        //   143: aaload         
        //   144: aload_1        
        //   145: invokestatic    com/viaversion/viaversion/libs/javassist/CtClassType.toAnnoType:(Lcom/viaversion/viaversion/libs/javassist/bytecode/annotation/Annotation;Lcom/viaversion/viaversion/libs/javassist/ClassPool;)Ljava/lang/Object;
        //   148: aastore        
        //   149: iinc            12, 1
        //   152: goto            130
        //   155: goto            242
        //   158: new             Ljava/util/ArrayList;
        //   161: dup            
        //   162: invokespecial   java/util/ArrayList.<init>:()V
        //   165: astore          12
        //   167: iconst_0       
        //   168: iconst_0       
        //   169: if_icmpge       199
        //   172: aload           12
        //   174: aload           8
        //   176: iconst_0       
        //   177: aaload         
        //   178: aload_1        
        //   179: invokestatic    com/viaversion/viaversion/libs/javassist/CtClassType.toAnnoType:(Lcom/viaversion/viaversion/libs/javassist/bytecode/annotation/Annotation;Lcom/viaversion/viaversion/libs/javassist/ClassPool;)Ljava/lang/Object;
        //   182: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   187: pop            
        //   188: goto            193
        //   191: astore          14
        //   193: iinc            13, 1
        //   196: goto            167
        //   199: iconst_0       
        //   200: iconst_0       
        //   201: if_icmpge       231
        //   204: aload           12
        //   206: aload           9
        //   208: iconst_0       
        //   209: aaload         
        //   210: aload_1        
        //   211: invokestatic    com/viaversion/viaversion/libs/javassist/CtClassType.toAnnoType:(Lcom/viaversion/viaversion/libs/javassist/bytecode/annotation/Annotation;Lcom/viaversion/viaversion/libs/javassist/ClassPool;)Ljava/lang/Object;
        //   214: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   219: pop            
        //   220: goto            225
        //   223: astore          14
        //   225: iinc            13, 1
        //   228: goto            199
        //   231: aload           6
        //   233: iconst_0       
        //   234: aload           12
        //   236: invokeinterface java/util/List.toArray:()[Ljava/lang/Object;
        //   241: aastore        
        //   242: iinc            7, 1
        //   245: goto            42
        //   248: aload           6
        //   250: areturn        
        //    Exceptions:
        //  throws java.lang.ClassNotFoundException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static Object toAnnoType(final Annotation annotation, final ClassPool classPool) throws ClassNotFoundException {
        return annotation.toAnnotationType(classPool.getClassLoader(), classPool);
    }
    
    @Override
    public boolean subclassOf(final CtClass ctClass) {
        if (ctClass == null) {
            return false;
        }
        final String name = ctClass.getName();
        for (CtClass superclass = this; superclass != null; superclass = superclass.getSuperclass()) {
            if (superclass.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public CtClass getSuperclass() throws NotFoundException {
        final String superclass = this.getClassFile2().getSuperclass();
        if (superclass == null) {
            return null;
        }
        return this.classPool.get(superclass);
    }
    
    @Override
    public void setSuperclass(final CtClass ctClass) throws CannotCompileException {
        this.checkModify();
        if (this.isInterface()) {
            this.addInterface(ctClass);
        }
        else {
            this.getClassFile2().setSuperclass(ctClass.getName());
        }
    }
    
    @Override
    public CtClass[] getInterfaces() throws NotFoundException {
        final String[] interfaces = this.getClassFile2().getInterfaces();
        final int length = interfaces.length;
        final CtClass[] array = new CtClass[length];
        while (0 < length) {
            array[0] = this.classPool.get(interfaces[0]);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    @Override
    public void setInterfaces(final CtClass[] array) {
        this.checkModify();
        String[] interfaces;
        if (array == null) {
            interfaces = new String[0];
        }
        else {
            final int length = array.length;
            interfaces = new String[length];
            while (0 < length) {
                interfaces[0] = array[0].getName();
                int n = 0;
                ++n;
            }
        }
        this.getClassFile2().setInterfaces(interfaces);
    }
    
    @Override
    public void addInterface(final CtClass ctClass) {
        this.checkModify();
        if (ctClass != null) {
            this.getClassFile2().addInterface(ctClass.getName());
        }
    }
    
    @Override
    public CtClass getDeclaringClass() throws NotFoundException {
        final ClassFile classFile2 = this.getClassFile2();
        final InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute)classFile2.getAttribute("InnerClasses");
        if (innerClassesAttribute == null) {
            return null;
        }
        final String name = this.getName();
        while (0 < innerClassesAttribute.tableLength()) {
            if (name.equals(innerClassesAttribute.innerClass(0))) {
                final String outerClass = innerClassesAttribute.outerClass(0);
                if (outerClass != null) {
                    return this.classPool.get(outerClass);
                }
                final EnclosingMethodAttribute enclosingMethodAttribute = (EnclosingMethodAttribute)classFile2.getAttribute("EnclosingMethod");
                if (enclosingMethodAttribute != null) {
                    return this.classPool.get(enclosingMethodAttribute.className());
                }
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    @Override
    public CtBehavior getEnclosingBehavior() throws NotFoundException {
        final EnclosingMethodAttribute enclosingMethodAttribute = (EnclosingMethodAttribute)this.getClassFile2().getAttribute("EnclosingMethod");
        if (enclosingMethodAttribute == null) {
            return null;
        }
        final CtClass value = this.classPool.get(enclosingMethodAttribute.className());
        final String methodName = enclosingMethodAttribute.methodName();
        if ("<init>".equals(methodName)) {
            return value.getConstructor(enclosingMethodAttribute.methodDescriptor());
        }
        if ("<clinit>".equals(methodName)) {
            return value.getClassInitializer();
        }
        return value.getMethod(methodName, enclosingMethodAttribute.methodDescriptor());
    }
    
    @Override
    public CtClass makeNestedClass(final String s, final boolean b) {
        if (!b) {
            throw new RuntimeException("sorry, only nested static class is supported");
        }
        this.checkModify();
        final CtClass nestedClass = this.classPool.makeNestedClass(this.getName() + "$" + s);
        final ClassFile classFile2 = this.getClassFile2();
        final ClassFile classFile3 = nestedClass.getClassFile2();
        InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute)classFile2.getAttribute("InnerClasses");
        if (innerClassesAttribute == null) {
            innerClassesAttribute = new InnerClassesAttribute(classFile2.getConstPool());
            classFile2.addAttribute(innerClassesAttribute);
        }
        innerClassesAttribute.append(nestedClass.getName(), this.getName(), s, (classFile3.getAccessFlags() & 0xFFFFFFDF) | 0x8);
        classFile3.addAttribute(innerClassesAttribute.copy(classFile3.getConstPool(), null));
        return nestedClass;
    }
    
    private void nameReplaced() {
        final CtMember.Cache hasMemberCache = this.hasMemberCache();
        if (hasMemberCache != null) {
            CtMember ctMember = hasMemberCache.methodHead();
            while (ctMember != hasMemberCache.lastMethod()) {
                ctMember = ctMember.next();
                ctMember.nameReplaced();
            }
        }
    }
    
    protected CtMember.Cache hasMemberCache() {
        if (this.memberCache != null) {
            return this.memberCache.get();
        }
        return null;
    }
    
    protected synchronized CtMember.Cache getMembers() {
        CtMember.Cache cache;
        if (this.memberCache == null || (cache = this.memberCache.get()) == null) {
            cache = new CtMember.Cache(this);
            this.makeFieldCache(cache);
            this.makeBehaviorCache(cache);
            this.memberCache = new WeakReference(cache);
        }
        return cache;
    }
    
    private void makeFieldCache(final CtMember.Cache cache) {
        final Iterator<FieldInfo> iterator = this.getClassFile3(false).getFields().iterator();
        while (iterator.hasNext()) {
            cache.addField(new CtField(iterator.next(), this));
        }
    }
    
    private void makeBehaviorCache(final CtMember.Cache cache) {
        for (final MethodInfo methodInfo : this.getClassFile3(false).getMethods()) {
            if (methodInfo.isMethod()) {
                cache.addMethod(new CtMethod(methodInfo, this));
            }
            else {
                cache.addConstructor(new CtConstructor(methodInfo, this));
            }
        }
    }
    
    @Override
    public CtField[] getFields() {
        final ArrayList list = new ArrayList();
        getFields(list, this);
        return (CtField[])list.toArray(new CtField[list.size()]);
    }
    
    private static void getFields(final List list, final CtClass ctClass) {
        if (ctClass == null) {
            return;
        }
        getFields(list, ctClass.getSuperclass());
        final CtClass[] interfaces = ctClass.getInterfaces();
        while (0 < interfaces.length) {
            getFields(list, interfaces[0]);
            int n = 0;
            ++n;
        }
        final CtMember.Cache members = ((CtClassType)ctClass).getMembers();
        CtMember ctMember = members.fieldHead();
        while (ctMember != members.lastField()) {
            ctMember = ctMember.next();
            if (!Modifier.isPrivate(ctMember.getModifiers())) {
                list.add(ctMember);
            }
        }
    }
    
    @Override
    public CtField getField(final String s, final String s2) throws NotFoundException {
        return this.checkGetField(this.getField2(s, s2), s, s2);
    }
    
    private CtField checkGetField(final CtField ctField, final String s, final String s2) throws NotFoundException {
        if (ctField == null) {
            String s3 = "field: " + s;
            if (s2 != null) {
                s3 = s3 + " type " + s2;
            }
            throw new NotFoundException(s3 + " in " + this.getName());
        }
        return ctField;
    }
    
    @Override
    CtField getField2(final String s, final String s2) {
        final CtField declaredField2 = this.getDeclaredField2(s, s2);
        if (declaredField2 != null) {
            return declaredField2;
        }
        final CtClass[] interfaces = this.getInterfaces();
        while (0 < interfaces.length) {
            final CtField field2 = interfaces[0].getField2(s, s2);
            if (field2 != null) {
                return field2;
            }
            int n = 0;
            ++n;
        }
        final CtClass superclass = this.getSuperclass();
        if (superclass != null) {
            return superclass.getField2(s, s2);
        }
        return null;
    }
    
    @Override
    public CtField[] getDeclaredFields() {
        final CtMember.Cache members = this.getMembers();
        CtMember ctMember;
        CtMember lastField;
        CtField[] array;
        CtField[] array2;
        int n;
        int n2 = 0;
        for (ctMember = members.fieldHead(), lastField = members.lastField(), array = new CtField[CtMember.Cache.count(ctMember, lastField)]; ctMember != lastField; ctMember = ctMember.next(), array2 = array, n = 0, ++n2, array2[n] = (CtField)ctMember) {}
        return array;
    }
    
    @Override
    public CtField getDeclaredField(final String s) throws NotFoundException {
        return this.getDeclaredField(s, null);
    }
    
    @Override
    public CtField getDeclaredField(final String s, final String s2) throws NotFoundException {
        return this.checkGetField(this.getDeclaredField2(s, s2), s, s2);
    }
    
    private CtField getDeclaredField2(final String s, final String s2) {
        final CtMember.Cache members = this.getMembers();
        CtMember ctMember = members.fieldHead();
        while (ctMember != members.lastField()) {
            ctMember = ctMember.next();
            if (ctMember.getName().equals(s) && (s2 == null || s2.equals(ctMember.getSignature()))) {
                return (CtField)ctMember;
            }
        }
        return null;
    }
    
    @Override
    public CtBehavior[] getDeclaredBehaviors() {
        final CtMember.Cache members = this.getMembers();
        CtMember ctMember = members.consHead();
        final CtMember lastCons = members.lastCons();
        final int count = CtMember.Cache.count(ctMember, lastCons);
        CtMember ctMember2 = members.methodHead();
        final CtMember lastMethod = members.lastMethod();
        CtBehavior[] array;
        CtBehavior[] array2;
        int n;
        int n2 = 0;
        for (array = new CtBehavior[count + CtMember.Cache.count(ctMember2, lastMethod)]; ctMember != lastCons; ctMember = ctMember.next(), array2 = array, n = 0, ++n2, array2[n] = (CtBehavior)ctMember) {}
        while (ctMember2 != lastMethod) {
            ctMember2 = ctMember2.next();
            final CtBehavior[] array3 = array;
            final int n3 = 0;
            ++n2;
            array3[n3] = (CtBehavior)ctMember2;
        }
        return array;
    }
    
    @Override
    public CtConstructor[] getConstructors() {
        final CtMember.Cache members = this.getMembers();
        final CtMember consHead = members.consHead();
        final CtMember lastCons = members.lastCons();
        CtMember next = consHead;
        while (next != lastCons) {
            next = next.next();
            if (isPubCons((CtConstructor)next)) {
                int n = 0;
                ++n;
            }
        }
        final CtConstructor[] array = new CtConstructor[0];
        CtMember next2 = consHead;
        while (next2 != lastCons) {
            next2 = next2.next();
            final CtConstructor ctConstructor = (CtConstructor)next2;
            if (isPubCons(ctConstructor)) {
                final CtConstructor[] array2 = array;
                final int n2 = 0;
                int n3 = 0;
                ++n3;
                array2[n2] = ctConstructor;
            }
        }
        return array;
    }
    
    private static boolean isPubCons(final CtConstructor ctConstructor) {
        return !Modifier.isPrivate(ctConstructor.getModifiers()) && ctConstructor.isConstructor();
    }
    
    @Override
    public CtConstructor getConstructor(final String s) throws NotFoundException {
        final CtMember.Cache members = this.getMembers();
        CtMember ctMember = members.consHead();
        while (ctMember != members.lastCons()) {
            ctMember = ctMember.next();
            final CtConstructor ctConstructor = (CtConstructor)ctMember;
            if (ctConstructor.getMethodInfo2().getDescriptor().equals(s) && ctConstructor.isConstructor()) {
                return ctConstructor;
            }
        }
        return super.getConstructor(s);
    }
    
    @Override
    public CtConstructor[] getDeclaredConstructors() {
        final CtMember.Cache members = this.getMembers();
        final CtMember consHead = members.consHead();
        final CtMember lastCons = members.lastCons();
        CtMember next = consHead;
        while (next != lastCons) {
            next = next.next();
            if (((CtConstructor)next).isConstructor()) {
                int n = 0;
                ++n;
            }
        }
        final CtConstructor[] array = new CtConstructor[0];
        CtMember next2 = consHead;
        while (next2 != lastCons) {
            next2 = next2.next();
            final CtConstructor ctConstructor = (CtConstructor)next2;
            if (ctConstructor.isConstructor()) {
                final CtConstructor[] array2 = array;
                final int n2 = 0;
                int n3 = 0;
                ++n3;
                array2[n2] = ctConstructor;
            }
        }
        return array;
    }
    
    @Override
    public CtConstructor getClassInitializer() {
        final CtMember.Cache members = this.getMembers();
        CtMember ctMember = members.consHead();
        while (ctMember != members.lastCons()) {
            ctMember = ctMember.next();
            final CtConstructor ctConstructor = (CtConstructor)ctMember;
            if (ctConstructor.isClassInitializer()) {
                return ctConstructor;
            }
        }
        return null;
    }
    
    @Override
    public CtMethod[] getMethods() {
        final HashMap hashMap = new HashMap();
        getMethods0(hashMap, this);
        return (CtMethod[])hashMap.values().toArray(new CtMethod[hashMap.size()]);
    }
    
    private static void getMethods0(final Map map, final CtClass ctClass) {
        final CtClass[] interfaces = ctClass.getInterfaces();
        while (0 < interfaces.length) {
            getMethods0(map, interfaces[0]);
            int n = 0;
            ++n;
        }
        final CtClass superclass = ctClass.getSuperclass();
        if (superclass != null) {
            getMethods0(map, superclass);
        }
        if (ctClass instanceof CtClassType) {
            final CtMember.Cache members = ((CtClassType)ctClass).getMembers();
            CtMember ctMember = members.methodHead();
            while (ctMember != members.lastMethod()) {
                ctMember = ctMember.next();
                if (!Modifier.isPrivate(ctMember.getModifiers())) {
                    map.put(((CtMethod)ctMember).getStringRep(), ctMember);
                }
            }
        }
    }
    
    @Override
    public CtMethod getMethod(final String s, final String s2) throws NotFoundException {
        final CtMethod method0 = getMethod0(this, s, s2);
        if (method0 != null) {
            return method0;
        }
        throw new NotFoundException(s + "(..) is not found in " + this.getName());
    }
    
    private static CtMethod getMethod0(final CtClass ctClass, final String s, final String s2) {
        if (ctClass instanceof CtClassType) {
            final CtMember.Cache members = ((CtClassType)ctClass).getMembers();
            CtMember ctMember = members.methodHead();
            while (ctMember != members.lastMethod()) {
                ctMember = ctMember.next();
                if (ctMember.getName().equals(s) && ((CtMethod)ctMember).getMethodInfo2().getDescriptor().equals(s2)) {
                    return (CtMethod)ctMember;
                }
            }
        }
        final CtClass superclass = ctClass.getSuperclass();
        if (superclass != null) {
            final CtMethod method0 = getMethod0(superclass, s, s2);
            if (method0 != null) {
                return method0;
            }
        }
        final CtClass[] interfaces = ctClass.getInterfaces();
        while (0 < interfaces.length) {
            final CtMethod method2 = getMethod0(interfaces[0], s, s2);
            if (method2 != null) {
                return method2;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    @Override
    public CtMethod[] getDeclaredMethods() {
        final CtMember.Cache members = this.getMembers();
        CtMember ctMember = members.methodHead();
        final CtMember lastMethod = members.lastMethod();
        final ArrayList<CtMember> list = new ArrayList<CtMember>();
        while (ctMember != lastMethod) {
            ctMember = ctMember.next();
            list.add(ctMember);
        }
        return list.toArray(new CtMethod[list.size()]);
    }
    
    @Override
    public CtMethod[] getDeclaredMethods(final String s) throws NotFoundException {
        final CtMember.Cache members = this.getMembers();
        CtMember ctMember = members.methodHead();
        final CtMember lastMethod = members.lastMethod();
        final ArrayList<CtMember> list = new ArrayList<CtMember>();
        while (ctMember != lastMethod) {
            ctMember = ctMember.next();
            if (ctMember.getName().equals(s)) {
                list.add(ctMember);
            }
        }
        return list.toArray(new CtMethod[list.size()]);
    }
    
    @Override
    public CtMethod getDeclaredMethod(final String s) throws NotFoundException {
        final CtMember.Cache members = this.getMembers();
        CtMember ctMember = members.methodHead();
        while (ctMember != members.lastMethod()) {
            ctMember = ctMember.next();
            if (ctMember.getName().equals(s)) {
                return (CtMethod)ctMember;
            }
        }
        throw new NotFoundException(s + "(..) is not found in " + this.getName());
    }
    
    @Override
    public CtMethod getDeclaredMethod(final String s, final CtClass[] array) throws NotFoundException {
        final String ofParameters = Descriptor.ofParameters(array);
        final CtMember.Cache members = this.getMembers();
        CtMember ctMember = members.methodHead();
        while (ctMember != members.lastMethod()) {
            ctMember = ctMember.next();
            if (ctMember.getName().equals(s) && ((CtMethod)ctMember).getMethodInfo2().getDescriptor().startsWith(ofParameters)) {
                return (CtMethod)ctMember;
            }
        }
        throw new NotFoundException(s + "(..) is not found in " + this.getName());
    }
    
    @Override
    public void addField(final CtField ctField, final String s) throws CannotCompileException {
        this.addField(ctField, CtField.Initializer.byExpr(s));
    }
    
    @Override
    public void addField(final CtField ctField, CtField.Initializer init) throws CannotCompileException {
        this.checkModify();
        if (ctField.getDeclaringClass() != this) {
            throw new CannotCompileException("cannot add");
        }
        if (init == null) {
            init = ctField.getInit();
        }
        if (init != null) {
            init.check(ctField.getSignature());
            final int modifiers = ctField.getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                final ConstPool constPool = this.getClassFile2().getConstPool();
                final int constantValue = init.getConstantValue(constPool, ctField.getType());
                if (constantValue != 0) {
                    ctField.getFieldInfo2().addAttribute(new ConstantAttribute(constPool, constantValue));
                    init = null;
                }
            }
        }
        this.getMembers().addField(ctField);
        this.getClassFile2().addField(ctField.getFieldInfo2());
        if (init != null) {
            final FieldInitLink fieldInitLink = new FieldInitLink(ctField, init);
            FieldInitLink fieldInitLink2 = this.fieldInitializers;
            if (fieldInitLink2 == null) {
                this.fieldInitializers = fieldInitLink;
            }
            else {
                while (fieldInitLink2.next != null) {
                    fieldInitLink2 = fieldInitLink2.next;
                }
                fieldInitLink2.next = fieldInitLink;
            }
        }
    }
    
    @Override
    public void removeField(final CtField ctField) throws NotFoundException {
        this.checkModify();
        if (this.getClassFile2().getFields().remove(ctField.getFieldInfo2())) {
            this.getMembers().remove(ctField);
            this.gcConstPool = true;
            return;
        }
        throw new NotFoundException(ctField.toString());
    }
    
    @Override
    public CtConstructor makeClassInitializer() throws CannotCompileException {
        final CtConstructor classInitializer = this.getClassInitializer();
        if (classInitializer != null) {
            return classInitializer;
        }
        this.checkModify();
        final ClassFile classFile2 = this.getClassFile2();
        this.modifyClassConstructor(classFile2, new Bytecode(classFile2.getConstPool(), 0, 0), 0, 0);
        return this.getClassInitializer();
    }
    
    @Override
    public void addConstructor(final CtConstructor ctConstructor) throws CannotCompileException {
        this.checkModify();
        if (ctConstructor.getDeclaringClass() != this) {
            throw new CannotCompileException("cannot add");
        }
        this.getMembers().addConstructor(ctConstructor);
        this.getClassFile2().addMethod(ctConstructor.getMethodInfo2());
    }
    
    @Override
    public void removeConstructor(final CtConstructor ctConstructor) throws NotFoundException {
        this.checkModify();
        if (this.getClassFile2().getMethods().remove(ctConstructor.getMethodInfo2())) {
            this.getMembers().remove(ctConstructor);
            this.gcConstPool = true;
            return;
        }
        throw new NotFoundException(ctConstructor.toString());
    }
    
    @Override
    public void addMethod(final CtMethod ctMethod) throws CannotCompileException {
        this.checkModify();
        if (ctMethod.getDeclaringClass() != this) {
            throw new CannotCompileException("bad declaring class");
        }
        final int modifiers = ctMethod.getModifiers();
        if ((this.getModifiers() & 0x200) != 0x0) {
            if (Modifier.isProtected(modifiers) || Modifier.isPrivate(modifiers)) {
                throw new CannotCompileException("an interface method must be public: " + ctMethod.toString());
            }
            ctMethod.setModifiers(modifiers | 0x1);
        }
        this.getMembers().addMethod(ctMethod);
        this.getClassFile2().addMethod(ctMethod.getMethodInfo2());
        if ((modifiers & 0x400) != 0x0) {
            this.setModifiers(this.getModifiers() | 0x400);
        }
    }
    
    @Override
    public void removeMethod(final CtMethod ctMethod) throws NotFoundException {
        this.checkModify();
        if (this.getClassFile2().getMethods().remove(ctMethod.getMethodInfo2())) {
            this.getMembers().remove(ctMethod);
            this.gcConstPool = true;
            return;
        }
        throw new NotFoundException(ctMethod.toString());
    }
    
    @Override
    public byte[] getAttribute(final String s) {
        final AttributeInfo attribute = this.getClassFile2().getAttribute(s);
        if (attribute == null) {
            return null;
        }
        return attribute.get();
    }
    
    @Override
    public void setAttribute(final String s, final byte[] array) {
        this.checkModify();
        final ClassFile classFile2 = this.getClassFile2();
        classFile2.addAttribute(new AttributeInfo(classFile2.getConstPool(), s, array));
    }
    
    @Override
    public void instrument(final CodeConverter codeConverter) throws CannotCompileException {
        this.checkModify();
        final ClassFile classFile2 = this.getClassFile2();
        final ConstPool constPool = classFile2.getConstPool();
        final List methods = classFile2.getMethods();
        final MethodInfo[] array = methods.toArray(new MethodInfo[methods.size()]);
        while (0 < array.length) {
            codeConverter.doit(this, array[0], constPool);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void instrument(final ExprEditor exprEditor) throws CannotCompileException {
        this.checkModify();
        final List methods = this.getClassFile2().getMethods();
        final MethodInfo[] array = methods.toArray(new MethodInfo[methods.size()]);
        while (0 < array.length) {
            exprEditor.doit(this, array[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void prune() {
        if (this.wasPruned) {
            return;
        }
        final boolean b = true;
        this.wasFrozen = b;
        this.wasPruned = b;
        this.getClassFile2().prune();
    }
    
    @Override
    public void rebuildClassFile() {
        this.gcConstPool = true;
    }
    
    @Override
    public void toBytecode(final DataOutputStream dataOutputStream) throws CannotCompileException, IOException {
        if (this.isModified()) {
            this.checkPruned("toBytecode");
            final ClassFile classFile2 = this.getClassFile2();
            if (this.gcConstPool) {
                classFile2.compact();
                this.gcConstPool = false;
            }
            this.modifyClassConstructor(classFile2);
            this.modifyConstructors(classFile2);
            if (CtClassType.debugDump != null) {
                this.dumpClassFile(classFile2);
            }
            classFile2.write(dataOutputStream);
            dataOutputStream.flush();
            this.fieldInitializers = null;
            if (this.doPruning) {
                classFile2.prune();
                this.wasPruned = true;
            }
        }
        else {
            this.classPool.writeClassfile(this.getName(), dataOutputStream);
        }
        this.getCount = 0;
        this.wasFrozen = true;
    }
    
    private void dumpClassFile(final ClassFile classFile) throws IOException {
        final DataOutputStream fileOutput = this.makeFileOutput(CtClassType.debugDump);
        classFile.write(fileOutput);
        fileOutput.close();
    }
    
    private void checkPruned(final String s) {
        if (this.wasPruned) {
            throw new RuntimeException(s + "(): " + this.getName() + " was pruned.");
        }
    }
    
    @Override
    public boolean stopPruning(final boolean b) {
        final boolean b2 = !this.doPruning;
        this.doPruning = !b;
        return b2;
    }
    
    private void modifyClassConstructor(final ClassFile classFile) throws CannotCompileException, NotFoundException {
        if (this.fieldInitializers == null) {
            return;
        }
        final Bytecode bytecode = new Bytecode(classFile.getConstPool(), 0, 0);
        final Javac javac = new Javac(bytecode, this);
        for (FieldInitLink fieldInitLink = this.fieldInitializers; fieldInitLink != null; fieldInitLink = fieldInitLink.next) {
            final CtField field = fieldInitLink.field;
            if (Modifier.isStatic(field.getModifiers()) && 0 < fieldInitLink.init.compileIfStatic(field.getType(), field.getName(), bytecode, javac)) {}
        }
        if (true) {
            this.modifyClassConstructor(classFile, bytecode, 0, 0);
        }
    }
    
    private void modifyClassConstructor(final ClassFile classFile, final Bytecode bytecode, final int n, final int n2) throws CannotCompileException {
        MethodInfo staticInitializer = classFile.getStaticInitializer();
        if (staticInitializer == null) {
            bytecode.add(177);
            bytecode.setMaxStack(n);
            bytecode.setMaxLocals(n2);
            staticInitializer = new MethodInfo(classFile.getConstPool(), "<clinit>", "()V");
            staticInitializer.setAccessFlags(8);
            staticInitializer.setCodeAttribute(bytecode.toCodeAttribute());
            classFile.addMethod(staticInitializer);
            final CtMember.Cache hasMemberCache = this.hasMemberCache();
            if (hasMemberCache != null) {
                hasMemberCache.addConstructor(new CtConstructor(staticInitializer, this));
            }
        }
        else {
            final CodeAttribute codeAttribute = staticInitializer.getCodeAttribute();
            if (codeAttribute == null) {
                throw new CannotCompileException("empty <clinit>");
            }
            final CodeIterator iterator = codeAttribute.iterator();
            iterator.insert(bytecode.getExceptionTable(), iterator.insertEx(bytecode.get()));
            if (codeAttribute.getMaxStack() < n) {
                codeAttribute.setMaxStack(n);
            }
            if (codeAttribute.getMaxLocals() < n2) {
                codeAttribute.setMaxLocals(n2);
            }
        }
        staticInitializer.rebuildStackMapIf6(this.classPool, classFile);
    }
    
    private void modifyConstructors(final ClassFile classFile) throws CannotCompileException, NotFoundException {
        if (this.fieldInitializers == null) {
            return;
        }
        final ConstPool constPool = classFile.getConstPool();
        for (final MethodInfo methodInfo : classFile.getMethods()) {
            if (methodInfo.isConstructor()) {
                final CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
                if (codeAttribute == null) {
                    continue;
                }
                final Bytecode bytecode = new Bytecode(constPool, 0, codeAttribute.getMaxLocals());
                insertAuxInitializer(codeAttribute, bytecode, this.makeFieldInitializer(bytecode, Descriptor.getParameterTypes(methodInfo.getDescriptor(), this.classPool)));
                methodInfo.rebuildStackMapIf6(this.classPool, classFile);
            }
        }
    }
    
    private static void insertAuxInitializer(final CodeAttribute codeAttribute, final Bytecode bytecode, final int maxStack) throws BadBytecode {
        final CodeIterator iterator = codeAttribute.iterator();
        if (iterator.skipSuperConstructor() < 0 && iterator.skipThisConstructor() >= 0) {
            return;
        }
        iterator.insert(bytecode.getExceptionTable(), iterator.insertEx(bytecode.get()));
        if (codeAttribute.getMaxStack() < maxStack) {
            codeAttribute.setMaxStack(maxStack);
        }
    }
    
    private int makeFieldInitializer(final Bytecode bytecode, final CtClass[] array) throws CannotCompileException, NotFoundException {
        final Javac javac = new Javac(bytecode, this);
        javac.recordParams(array, false);
        for (FieldInitLink fieldInitLink = this.fieldInitializers; fieldInitLink != null; fieldInitLink = fieldInitLink.next) {
            final CtField field = fieldInitLink.field;
            if (!Modifier.isStatic(field.getModifiers()) && 0 < fieldInitLink.init.compile(field.getType(), field.getName(), bytecode, array, javac)) {}
        }
        return 0;
    }
    
    Map getHiddenMethods() {
        if (this.hiddenMethods == null) {
            this.hiddenMethods = new Hashtable();
        }
        return this.hiddenMethods;
    }
    
    int getUniqueNumber() {
        return this.uniqueNumberSeed++;
    }
    
    @Override
    public String makeUniqueName(final String s) {
        final HashMap hashMap = new HashMap();
        this.makeMemberList(hashMap);
        final Set keySet = hashMap.keySet();
        final String[] array = new String[keySet.size()];
        keySet.toArray(array);
        if (notFindInArray(s, array)) {
            return s;
        }
        while (100 <= 999) {
            final StringBuilder append = new StringBuilder().append(s);
            final int n = 100;
            int n2 = 0;
            ++n2;
            final String string = append.append(n).toString();
            if (notFindInArray(string, array)) {
                return string;
            }
        }
        throw new RuntimeException("too many unique name");
    }
    
    private static boolean notFindInArray(final String s, final String[] array) {
        while (0 < array.length) {
            if (array[0].startsWith(s)) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private void makeMemberList(final Map map) {
        final int modifiers = this.getModifiers();
        if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
            final CtClass[] interfaces = this.getInterfaces();
            while (0 < interfaces.length) {
                final CtClass ctClass = interfaces[0];
                if (ctClass != null && ctClass instanceof CtClassType) {
                    ((CtClassType)ctClass).makeMemberList(map);
                }
                int n = 0;
                ++n;
            }
        }
        final CtClass superclass = this.getSuperclass();
        if (superclass != null && superclass instanceof CtClassType) {
            ((CtClassType)superclass).makeMemberList(map);
        }
        final Iterator<MethodInfo> iterator = this.getClassFile2().getMethods().iterator();
        while (iterator.hasNext()) {
            map.put(iterator.next().getName(), this);
        }
        final Iterator<FieldInfo> iterator2 = (Iterator<FieldInfo>)this.getClassFile2().getFields().iterator();
        while (iterator2.hasNext()) {
            map.put(iterator2.next().getName(), this);
        }
    }
}
