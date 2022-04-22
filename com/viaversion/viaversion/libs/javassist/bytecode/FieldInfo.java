package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

public final class FieldInfo
{
    ConstPool constPool;
    int accessFlags;
    int name;
    String cachedName;
    String cachedType;
    int descriptor;
    List attribute;
    
    private FieldInfo(final ConstPool constPool) {
        this.constPool = constPool;
        this.accessFlags = 0;
        this.attribute = null;
    }
    
    public FieldInfo(final ConstPool constPool, final String cachedName, final String s) {
        this(constPool);
        this.name = constPool.addUtf8Info(cachedName);
        this.cachedName = cachedName;
        this.descriptor = constPool.addUtf8Info(s);
    }
    
    FieldInfo(final ConstPool constPool, final DataInputStream dataInputStream) throws IOException {
        this(constPool);
        this.read(dataInputStream);
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
        final ArrayList<ConstantAttribute> attribute = new ArrayList<ConstantAttribute>();
        final AttributeInfo attribute2 = this.getAttribute("RuntimeInvisibleAnnotations");
        if (attribute2 != null) {
            attribute.add((ConstantAttribute)attribute2.copy(constPool, null));
        }
        final AttributeInfo attribute3 = this.getAttribute("RuntimeVisibleAnnotations");
        if (attribute3 != null) {
            attribute.add((ConstantAttribute)attribute3.copy(constPool, null));
        }
        final AttributeInfo attribute4 = this.getAttribute("Signature");
        if (attribute4 != null) {
            attribute.add((ConstantAttribute)attribute4.copy(constPool, null));
        }
        final int constantValue = this.getConstantValue();
        if (constantValue != 0) {
            attribute.add(new ConstantAttribute(constPool, this.constPool.copy(constantValue, constPool, null)));
        }
        this.attribute = attribute;
        this.name = constPool.addUtf8Info(this.getName());
        this.descriptor = constPool.addUtf8Info(this.getDescriptor());
        this.constPool = constPool;
    }
    
    public ConstPool getConstPool() {
        return this.constPool;
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
    
    public int getConstantValue() {
        if ((this.accessFlags & 0x8) == 0x0) {
            return 0;
        }
        final ConstantAttribute constantAttribute = (ConstantAttribute)this.getAttribute("ConstantValue");
        if (constantAttribute == null) {
            return 0;
        }
        return constantAttribute.getConstantValue();
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
}
