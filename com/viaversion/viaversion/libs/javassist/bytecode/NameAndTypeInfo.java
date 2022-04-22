package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

class NameAndTypeInfo extends ConstInfo
{
    static final int tag = 12;
    int memberName;
    int typeDescriptor;
    
    public NameAndTypeInfo(final int memberName, final int typeDescriptor, final int n) {
        super(n);
        this.memberName = memberName;
        this.typeDescriptor = typeDescriptor;
    }
    
    public NameAndTypeInfo(final DataInputStream dataInputStream, final int n) throws IOException {
        super(n);
        this.memberName = dataInputStream.readUnsignedShort();
        this.typeDescriptor = dataInputStream.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.memberName << 16 ^ this.typeDescriptor;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof NameAndTypeInfo) {
            final NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo)o;
            return nameAndTypeInfo.memberName == this.memberName && nameAndTypeInfo.typeDescriptor == this.typeDescriptor;
        }
        return false;
    }
    
    @Override
    public int getTag() {
        return 12;
    }
    
    @Override
    public void renameClass(final ConstPool constPool, final String s, final String s2, final Map map) {
        final String utf8Info = constPool.getUtf8Info(this.typeDescriptor);
        final String rename = Descriptor.rename(utf8Info, s, s2);
        if (utf8Info != rename) {
            if (map == null) {
                this.typeDescriptor = constPool.addUtf8Info(rename);
            }
            else {
                map.remove(this);
                this.typeDescriptor = constPool.addUtf8Info(rename);
                map.put(this, this);
            }
        }
    }
    
    @Override
    public void renameClass(final ConstPool constPool, final Map map, final Map map2) {
        final String utf8Info = constPool.getUtf8Info(this.typeDescriptor);
        final String rename = Descriptor.rename(utf8Info, map);
        if (utf8Info != rename) {
            if (map2 == null) {
                this.typeDescriptor = constPool.addUtf8Info(rename);
            }
            else {
                map2.remove(this);
                this.typeDescriptor = constPool.addUtf8Info(rename);
                map2.put(this, this);
            }
        }
    }
    
    @Override
    public int copy(final ConstPool constPool, final ConstPool constPool2, final Map map) {
        return constPool2.addNameAndTypeInfo(constPool2.addUtf8Info(constPool.getUtf8Info(this.memberName)), constPool2.addUtf8Info(Descriptor.rename(constPool.getUtf8Info(this.typeDescriptor), map)));
    }
    
    @Override
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(12);
        dataOutputStream.writeShort(this.memberName);
        dataOutputStream.writeShort(this.typeDescriptor);
    }
    
    @Override
    public void print(final PrintWriter printWriter) {
        printWriter.print("NameAndType #");
        printWriter.print(this.memberName);
        printWriter.print(", type #");
        printWriter.println(this.typeDescriptor);
    }
}
