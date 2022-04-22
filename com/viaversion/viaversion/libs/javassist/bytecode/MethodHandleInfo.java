package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

class MethodHandleInfo extends ConstInfo
{
    static final int tag = 15;
    int refKind;
    int refIndex;
    
    public MethodHandleInfo(final int refKind, final int refIndex, final int n) {
        super(n);
        this.refKind = refKind;
        this.refIndex = refIndex;
    }
    
    public MethodHandleInfo(final DataInputStream dataInputStream, final int n) throws IOException {
        super(n);
        this.refKind = dataInputStream.readUnsignedByte();
        this.refIndex = dataInputStream.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.refKind << 16 ^ this.refIndex;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof MethodHandleInfo) {
            final MethodHandleInfo methodHandleInfo = (MethodHandleInfo)o;
            return methodHandleInfo.refKind == this.refKind && methodHandleInfo.refIndex == this.refIndex;
        }
        return false;
    }
    
    @Override
    public int getTag() {
        return 15;
    }
    
    @Override
    public int copy(final ConstPool constPool, final ConstPool constPool2, final Map map) {
        return constPool2.addMethodHandleInfo(this.refKind, constPool.getItem(this.refIndex).copy(constPool, constPool2, map));
    }
    
    @Override
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(15);
        dataOutputStream.writeByte(this.refKind);
        dataOutputStream.writeShort(this.refIndex);
    }
    
    @Override
    public void print(final PrintWriter printWriter) {
        printWriter.print("MethodHandle #");
        printWriter.print(this.refKind);
        printWriter.print(", index #");
        printWriter.println(this.refIndex);
    }
}
