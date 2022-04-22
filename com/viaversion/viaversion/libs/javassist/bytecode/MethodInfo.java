package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.stackmap.*;
import java.io.*;

public class MethodInfo
{
    ConstPool constPool;
    int accessFlags;
    int name;
    String cachedName;
    int descriptor;
    List attribute;
    public static boolean doPreverify;
    public static final String nameInit = "<init>";
    public static final String nameClinit = "<clinit>";
    
    private MethodInfo(final ConstPool constPool) {
        this.constPool = constPool;
        this.attribute = null;
    }
    
    public MethodInfo(final ConstPool constPool, final String cachedName, final String s) {
        this(constPool);
        this.accessFlags = 0;
        this.name = constPool.addUtf8Info(cachedName);
        this.cachedName = cachedName;
        this.descriptor = this.constPool.addUtf8Info(s);
    }
    
    MethodInfo(final ConstPool constPool, final DataInputStream dataInputStream) throws IOException {
        this(constPool);
        this.read(dataInputStream);
    }
    
    public MethodInfo(final ConstPool constPool, final String s, final MethodInfo methodInfo, final Map map) throws BadBytecode {
        this(constPool);
        this.read(methodInfo, s, map);
    }
    
    @Override
    public String toString() {
        return this.getName() + " " + this.getDescriptor();
    }
    
    void compact(final ConstPool constPool) {
        this.name = constPool.addUtf8Info(this.getName());
        this.descriptor = constPool.addUtf8Info(this.getDescriptor());
        this.attribute = AttributeInfo.copyAll(this.attribute, constPool);
        this.constPool = constPool;
    }
    
    void prune(final ConstPool constPool) {
        final ArrayList<AttributeInfo> attribute = new ArrayList<AttributeInfo>();
        final AttributeInfo attribute2 = this.getAttribute("RuntimeInvisibleAnnotations");
        if (attribute2 != null) {
            attribute.add(attribute2.copy(constPool, null));
        }
        final AttributeInfo attribute3 = this.getAttribute("RuntimeVisibleAnnotations");
        if (attribute3 != null) {
            attribute.add(attribute3.copy(constPool, null));
        }
        final AttributeInfo attribute4 = this.getAttribute("RuntimeInvisibleParameterAnnotations");
        if (attribute4 != null) {
            attribute.add(attribute4.copy(constPool, null));
        }
        final AttributeInfo attribute5 = this.getAttribute("RuntimeVisibleParameterAnnotations");
        if (attribute5 != null) {
            attribute.add(attribute5.copy(constPool, null));
        }
        final AnnotationDefaultAttribute annotationDefaultAttribute = (AnnotationDefaultAttribute)this.getAttribute("AnnotationDefault");
        if (annotationDefaultAttribute != null) {
            attribute.add(annotationDefaultAttribute);
        }
        final ExceptionsAttribute exceptionsAttribute = this.getExceptionsAttribute();
        if (exceptionsAttribute != null) {
            attribute.add(exceptionsAttribute);
        }
        final AttributeInfo attribute6 = this.getAttribute("Signature");
        if (attribute6 != null) {
            attribute.add(attribute6.copy(constPool, null));
        }
        this.attribute = attribute;
        this.name = constPool.addUtf8Info(this.getName());
        this.descriptor = constPool.addUtf8Info(this.getDescriptor());
        this.constPool = constPool;
    }
    
    public String getName() {
        if (this.cachedName == null) {
            this.cachedName = this.constPool.getUtf8Info(this.name);
        }
        return this.cachedName;
    }
    
    public void setName(final String cachedName) {
        this.name = this.constPool.addUtf8Info(cachedName);
        this.cachedName = cachedName;
    }
    
    public boolean isMethod() {
        final String name = this.getName();
        return !name.equals("<init>") && !name.equals("<clinit>");
    }
    
    public ConstPool getConstPool() {
        return this.constPool;
    }
    
    public boolean isConstructor() {
        return this.getName().equals("<init>");
    }
    
    public boolean isStaticInitializer() {
        return this.getName().equals("<clinit>");
    }
    
    public int getAccessFlags() {
        return this.accessFlags;
    }
    
    public void setAccessFlags(final int accessFlags) {
        this.accessFlags = accessFlags;
    }
    
    public String getDescriptor() {
        return this.constPool.getUtf8Info(this.descriptor);
    }
    
    public void setDescriptor(final String s) {
        if (!s.equals(this.getDescriptor())) {
            this.descriptor = this.constPool.addUtf8Info(s);
        }
    }
    
    public List getAttributes() {
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        return this.attribute;
    }
    
    public AttributeInfo getAttribute(final String s) {
        return AttributeInfo.lookup(this.attribute, s);
    }
    
    public AttributeInfo removeAttribute(final String s) {
        return AttributeInfo.remove(this.attribute, s);
    }
    
    public void addAttribute(final AttributeInfo attributeInfo) {
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        AttributeInfo.remove(this.attribute, attributeInfo.getName());
        this.attribute.add(attributeInfo);
    }
    
    public ExceptionsAttribute getExceptionsAttribute() {
        return (ExceptionsAttribute)AttributeInfo.lookup(this.attribute, "Exceptions");
    }
    
    public CodeAttribute getCodeAttribute() {
        return (CodeAttribute)AttributeInfo.lookup(this.attribute, "Code");
    }
    
    public void removeExceptionsAttribute() {
        AttributeInfo.remove(this.attribute, "Exceptions");
    }
    
    public void setExceptionsAttribute(final ExceptionsAttribute exceptionsAttribute) {
        this.removeExceptionsAttribute();
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        this.attribute.add(exceptionsAttribute);
    }
    
    public void removeCodeAttribute() {
        AttributeInfo.remove(this.attribute, "Code");
    }
    
    public void setCodeAttribute(final CodeAttribute codeAttribute) {
        this.removeCodeAttribute();
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        this.attribute.add(codeAttribute);
    }
    
    public void rebuildStackMapIf6(final ClassPool classPool, final ClassFile classFile) throws BadBytecode {
        if (classFile.getMajorVersion() >= 50) {
            this.rebuildStackMap(classPool);
        }
        if (MethodInfo.doPreverify) {
            this.rebuildStackMapForME(classPool);
        }
    }
    
    public void rebuildStackMap(final ClassPool classPool) throws BadBytecode {
        final CodeAttribute codeAttribute = this.getCodeAttribute();
        if (codeAttribute != null) {
            codeAttribute.setAttribute(MapMaker.make(classPool, this));
        }
    }
    
    public void rebuildStackMapForME(final ClassPool classPool) throws BadBytecode {
        final CodeAttribute codeAttribute = this.getCodeAttribute();
        if (codeAttribute != null) {
            codeAttribute.setAttribute(MapMaker.make2(classPool, this));
        }
    }
    
    public int getLineNumber(final int n) {
        final CodeAttribute codeAttribute = this.getCodeAttribute();
        if (codeAttribute == null) {
            return -1;
        }
        final LineNumberAttribute lineNumberAttribute = (LineNumberAttribute)codeAttribute.getAttribute("LineNumberTable");
        if (lineNumberAttribute == null) {
            return -1;
        }
        return lineNumberAttribute.toLineNumber(n);
    }
    
    public void setSuperclass(final String s) throws BadBytecode {
        if (!this.isConstructor()) {
            return;
        }
        final CodeAttribute codeAttribute = this.getCodeAttribute();
        final byte[] code = codeAttribute.getCode();
        final int skipSuperConstructor = codeAttribute.iterator().skipSuperConstructor();
        if (skipSuperConstructor >= 0) {
            final ConstPool constPool = this.constPool;
            ByteArray.write16bit(constPool.addMethodrefInfo(constPool.addClassInfo(s), constPool.getMethodrefNameAndType(ByteArray.readU16bit(code, skipSuperConstructor + 1))), code, skipSuperConstructor + 1);
        }
    }
    
    private void read(final MethodInfo methodInfo, final String cachedName, final Map map) {
        final ConstPool constPool = this.constPool;
        this.accessFlags = methodInfo.accessFlags;
        this.name = constPool.addUtf8Info(cachedName);
        this.cachedName = cachedName;
        this.descriptor = constPool.addUtf8Info(Descriptor.rename(methodInfo.constPool.getUtf8Info(methodInfo.descriptor), map));
        this.attribute = new ArrayList();
        final ExceptionsAttribute exceptionsAttribute = methodInfo.getExceptionsAttribute();
        if (exceptionsAttribute != null) {
            this.attribute.add(exceptionsAttribute.copy(constPool, map));
        }
        final CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute != null) {
            this.attribute.add(codeAttribute.copy(constPool, map));
        }
    }
    
    private void read(final DataInputStream dataInputStream) throws IOException {
        this.accessFlags = dataInputStream.readUnsignedShort();
        this.name = dataInputStream.readUnsignedShort();
        this.descriptor = dataInputStream.readUnsignedShort();
        final int unsignedShort = dataInputStream.readUnsignedShort();
        this.attribute = new ArrayList();
        while (0 < unsignedShort) {
            this.attribute.add(AttributeInfo.read(this.constPool, dataInputStream));
            int n = 0;
            ++n;
        }
    }
    
    void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.accessFlags);
        dataOutputStream.writeShort(this.name);
        dataOutputStream.writeShort(this.descriptor);
        if (this.attribute == null) {
            dataOutputStream.writeShort(0);
        }
        else {
            dataOutputStream.writeShort(this.attribute.size());
            AttributeInfo.writeAll(this.attribute, dataOutputStream);
        }
    }
    
    static {
        MethodInfo.doPreverify = false;
    }
}
