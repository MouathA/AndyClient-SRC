package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

class StringInfo extends ConstInfo
{
    static final int tag = 8;
    int string;
    
    public StringInfo(final int string, final int n) {
        super(n);
        this.string = string;
    }
    
    public StringInfo(final DataInputStream dataInputStream, final int n) throws IOException {
        super(n);
        this.string = dataInputStream.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.string;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof StringInfo && ((StringInfo)o).string == this.string;
    }
    
    @Override
    public int getTag() {
        return 8;
    }
    
    @Override
    public int copy(final ConstPool constPool, final ConstPool constPool2, final Map map) {
        return constPool2.addStringInfo(constPool.getUtf8Info(this.string));
    }
    
    @Override
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(8);
        dataOutputStream.writeShort(this.string);
    }
    
    @Override
    public void print(final PrintWriter printWriter) {
        printWriter.print("String #");
        printWriter.println(this.string);
    }
}
