package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

class FloatInfo extends ConstInfo
{
    static final int tag = 4;
    float value;
    
    public FloatInfo(final float value, final int n) {
        super(n);
        this.value = value;
    }
    
    public FloatInfo(final DataInputStream dataInputStream, final int n) throws IOException {
        super(n);
        this.value = dataInputStream.readFloat();
    }
    
    @Override
    public int hashCode() {
        return Float.floatToIntBits(this.value);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof FloatInfo && ((FloatInfo)o).value == this.value;
    }
    
    @Override
    public int getTag() {
        return 4;
    }
    
    @Override
    public int copy(final ConstPool constPool, final ConstPool constPool2, final Map map) {
        return constPool2.addFloatInfo(this.value);
    }
    
    @Override
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(4);
        dataOutputStream.writeFloat(this.value);
    }
    
    @Override
    public void print(final PrintWriter printWriter) {
        printWriter.print("Float ");
        printWriter.println(this.value);
    }
}
