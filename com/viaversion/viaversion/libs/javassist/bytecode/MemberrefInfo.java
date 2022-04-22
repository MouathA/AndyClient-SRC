package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

abstract class MemberrefInfo extends ConstInfo
{
    int classIndex;
    int nameAndTypeIndex;
    
    public MemberrefInfo(final int classIndex, final int nameAndTypeIndex, final int n) {
        super(n);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }
    
    public MemberrefInfo(final DataInputStream dataInputStream, final int n) throws IOException {
        super(n);
        this.classIndex = dataInputStream.readUnsignedShort();
        this.nameAndTypeIndex = dataInputStream.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.classIndex << 16 ^ this.nameAndTypeIndex;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof MemberrefInfo) {
            final MemberrefInfo memberrefInfo = (MemberrefInfo)o;
            return memberrefInfo.classIndex == this.classIndex && memberrefInfo.nameAndTypeIndex == this.nameAndTypeIndex && memberrefInfo.getClass() == this.getClass();
        }
        return false;
    }
    
    @Override
    public int copy(final ConstPool constPool, final ConstPool constPool2, final Map map) {
        return this.copy2(constPool2, constPool.getItem(this.classIndex).copy(constPool, constPool2, map), constPool.getItem(this.nameAndTypeIndex).copy(constPool, constPool2, map));
    }
    
    protected abstract int copy2(final ConstPool p0, final int p1, final int p2);
    
    @Override
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(this.getTag());
        dataOutputStream.writeShort(this.classIndex);
        dataOutputStream.writeShort(this.nameAndTypeIndex);
    }
    
    @Override
    public void print(final PrintWriter printWriter) {
        printWriter.print(this.getTagName() + " #");
        printWriter.print(this.classIndex);
        printWriter.print(", name&type #");
        printWriter.println(this.nameAndTypeIndex);
    }
    
    public abstract String getTagName();
}
