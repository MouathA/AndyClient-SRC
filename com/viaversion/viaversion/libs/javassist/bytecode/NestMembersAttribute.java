package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;

public class NestMembersAttribute extends AttributeInfo
{
    public static final String tag = "NestMembers";
    
    NestMembersAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    private NestMembersAttribute(final ConstPool constPool, final byte[] array) {
        super(constPool, "NestMembers", array);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        final byte[] value = this.get();
        final byte[] array = new byte[value.length];
        final ConstPool constPool2 = this.getConstPool();
        final int u16bit = ByteArray.readU16bit(value, 0);
        ByteArray.write16bit(u16bit, array, 0);
        while (0 < u16bit) {
            ByteArray.write16bit(constPool2.copy(ByteArray.readU16bit(value, 2), constPool, map), array, 2);
            int n = 0;
            ++n;
            final int n2;
            n2 += 2;
        }
        return new NestMembersAttribute(constPool, array);
    }
    
    public int numberOfClasses() {
        return ByteArray.readU16bit(this.info, 0);
    }
    
    public int memberClass(final int n) {
        return ByteArray.readU16bit(this.info, n * 2 + 2);
    }
}
