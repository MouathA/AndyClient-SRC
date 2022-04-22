package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

class DoubleInfo extends ConstInfo
{
    static final int tag = 6;
    double value;
    
    public DoubleInfo(final double value, final int n) {
        super(n);
        this.value = value;
    }
    
    public DoubleInfo(final DataInputStream dataInputStream, final int n) throws IOException {
        super(n);
        this.value = dataInputStream.readDouble();
    }
    
    @Override
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.value);
        return (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof DoubleInfo && ((DoubleInfo)o).value == this.value;
    }
    
    @Override
    public int getTag() {
        return 6;
    }
    
    @Override
    public int copy(final ConstPool constPool, final ConstPool constPool2, final Map map) {
        return constPool2.addDoubleInfo(this.value);
    }
    
    @Override
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(6);
        dataOutputStream.writeDouble(this.value);
    }
    
    @Override
    public void print(final PrintWriter printWriter) {
        printWriter.print("Double ");
        printWriter.println(this.value);
    }
}
