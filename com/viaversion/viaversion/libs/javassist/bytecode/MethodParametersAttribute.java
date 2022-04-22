package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;

public class MethodParametersAttribute extends AttributeInfo
{
    public static final String tag = "MethodParameters";
    
    MethodParametersAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    public MethodParametersAttribute(final ConstPool constPool, final String[] array, final int[] array2) {
        super(constPool, "MethodParameters");
        final byte[] array3 = new byte[array.length * 4 + 1];
        array3[0] = (byte)array.length;
        while (0 < array.length) {
            ByteArray.write16bit(constPool.addUtf8Info(array[0]), array3, 1);
            ByteArray.write16bit(array2[0], array3, 3);
            int n = 0;
            ++n;
        }
        this.set(array3);
    }
    
    public int size() {
        return this.info[0] & 0xFF;
    }
    
    public int name(final int n) {
        return ByteArray.readU16bit(this.info, n * 4 + 1);
    }
    
    public String parameterName(final int n) {
        return this.getConstPool().getUtf8Info(this.name(n));
    }
    
    public int accessFlags(final int n) {
        return ByteArray.readU16bit(this.info, n * 4 + 3);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        final int size = this.size();
        final ConstPool constPool2 = this.getConstPool();
        final String[] array = new String[size];
        final int[] array2 = new int[size];
        while (0 < size) {
            array[0] = constPool2.getUtf8Info(this.name(0));
            array2[0] = this.accessFlags(0);
            int n = 0;
            ++n;
        }
        return new MethodParametersAttribute(constPool, array, array2);
    }
}
