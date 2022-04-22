package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

class DynamicInfo extends ConstInfo
{
    static final int tag = 17;
    int bootstrap;
    int nameAndType;
    
    public DynamicInfo(final int bootstrap, final int nameAndType, final int n) {
        super(n);
        this.bootstrap = bootstrap;
        this.nameAndType = nameAndType;
    }
    
    public DynamicInfo(final DataInputStream dataInputStream, final int n) throws IOException {
        super(n);
        this.bootstrap = dataInputStream.readUnsignedShort();
        this.nameAndType = dataInputStream.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.bootstrap << 16 ^ this.nameAndType;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof DynamicInfo) {
            final DynamicInfo dynamicInfo = (DynamicInfo)o;
            return dynamicInfo.bootstrap == this.bootstrap && dynamicInfo.nameAndType == this.nameAndType;
        }
        return false;
    }
    
    @Override
    public int getTag() {
        return 17;
    }
    
    @Override
    public int copy(final ConstPool constPool, final ConstPool constPool2, final Map map) {
        return constPool2.addDynamicInfo(this.bootstrap, constPool.getItem(this.nameAndType).copy(constPool, constPool2, map));
    }
    
    @Override
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(17);
        dataOutputStream.writeShort(this.bootstrap);
        dataOutputStream.writeShort(this.nameAndType);
    }
    
    @Override
    public void print(final PrintWriter printWriter) {
        printWriter.print("Dynamic #");
        printWriter.print(this.bootstrap);
        printWriter.print(", name&type #");
        printWriter.println(this.nameAndType);
    }
}
