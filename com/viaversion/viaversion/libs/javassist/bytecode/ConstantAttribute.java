package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;

public class ConstantAttribute extends AttributeInfo
{
    public static final String tag = "ConstantValue";
    
    ConstantAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    public ConstantAttribute(final ConstPool constPool, final int n) {
        super(constPool, "ConstantValue");
        this.set(new byte[] { (byte)(n >>> 8), (byte)n });
    }
    
    public int getConstantValue() {
        return ByteArray.readU16bit(this.get(), 0);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        return new ConstantAttribute(constPool, this.getConstPool().copy(this.getConstantValue(), constPool, map));
    }
}
