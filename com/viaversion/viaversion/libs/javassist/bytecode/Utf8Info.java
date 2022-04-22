package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

class Utf8Info extends ConstInfo
{
    static final int tag = 1;
    String string;
    
    public Utf8Info(final String string, final int n) {
        super(n);
        this.string = string;
    }
    
    public Utf8Info(final DataInputStream dataInputStream, final int n) throws IOException {
        super(n);
        this.string = dataInputStream.readUTF();
    }
    
    @Override
    public int hashCode() {
        return this.string.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Utf8Info && ((Utf8Info)o).string.equals(this.string);
    }
    
    @Override
    public int getTag() {
        return 1;
    }
    
    @Override
    public int copy(final ConstPool constPool, final ConstPool constPool2, final Map map) {
        return constPool2.addUtf8Info(this.string);
    }
    
    @Override
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(1);
        dataOutputStream.writeUTF(this.string);
    }
    
    @Override
    public void print(final PrintWriter printWriter) {
        printWriter.print("UTF8 \"");
        printWriter.print(this.string);
        printWriter.println("\"");
    }
}
