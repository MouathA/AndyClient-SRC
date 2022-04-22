package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

class PackageInfo extends ConstInfo
{
    static final int tag = 20;
    int name;
    
    public PackageInfo(final int name, final int n) {
        super(n);
        this.name = name;
    }
    
    public PackageInfo(final DataInputStream dataInputStream, final int n) throws IOException {
        super(n);
        this.name = dataInputStream.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.name;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof PackageInfo && ((PackageInfo)o).name == this.name;
    }
    
    @Override
    public int getTag() {
        return 20;
    }
    
    public String getPackageName(final ConstPool constPool) {
        return constPool.getUtf8Info(this.name);
    }
    
    @Override
    public int copy(final ConstPool constPool, final ConstPool constPool2, final Map map) {
        return constPool2.addModuleInfo(constPool2.addUtf8Info(constPool.getUtf8Info(this.name)));
    }
    
    @Override
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(20);
        dataOutputStream.writeShort(this.name);
    }
    
    @Override
    public void print(final PrintWriter printWriter) {
        printWriter.print("Package #");
        printWriter.println(this.name);
    }
}
