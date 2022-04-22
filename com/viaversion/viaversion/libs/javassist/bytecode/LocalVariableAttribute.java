package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;

public class LocalVariableAttribute extends AttributeInfo
{
    public static final String tag = "LocalVariableTable";
    public static final String typeTag = "LocalVariableTypeTable";
    
    public LocalVariableAttribute(final ConstPool constPool) {
        super(constPool, "LocalVariableTable", new byte[2]);
        ByteArray.write16bit(0, this.info, 0);
    }
    
    @Deprecated
    public LocalVariableAttribute(final ConstPool constPool, final String s) {
        super(constPool, s, new byte[2]);
        ByteArray.write16bit(0, this.info, 0);
    }
    
    LocalVariableAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    LocalVariableAttribute(final ConstPool constPool, final String s, final byte[] array) {
        super(constPool, s, array);
    }
    
    public void addEntry(final int n, final int n2, final int n3, final int n4, final int n5) {
        final int length = this.info.length;
        final byte[] info = new byte[length + 10];
        ByteArray.write16bit(this.tableLength() + 1, info, 0);
        while (2 < length) {
            info[2] = this.info[2];
            int n6 = 0;
            ++n6;
        }
        ByteArray.write16bit(n, info, length);
        ByteArray.write16bit(n2, info, length + 2);
        ByteArray.write16bit(n3, info, length + 4);
        ByteArray.write16bit(n4, info, length + 6);
        ByteArray.write16bit(n5, info, length + 8);
        this.info = info;
    }
    
    @Override
    void renameClass(final String s, final String s2) {
        final ConstPool constPool = this.getConstPool();
        while (0 < this.tableLength()) {
            final int u16bit = ByteArray.readU16bit(this.info, 8);
            if (u16bit != 0) {
                ByteArray.write16bit(constPool.addUtf8Info(this.renameEntry(constPool.getUtf8Info(u16bit), s, s2)), this.info, 8);
            }
            int n = 0;
            ++n;
        }
    }
    
    String renameEntry(final String s, final String s2, final String s3) {
        return Descriptor.rename(s, s2, s3);
    }
    
    @Override
    void renameClass(final Map map) {
        final ConstPool constPool = this.getConstPool();
        while (0 < this.tableLength()) {
            final int u16bit = ByteArray.readU16bit(this.info, 8);
            if (u16bit != 0) {
                ByteArray.write16bit(constPool.addUtf8Info(this.renameEntry(constPool.getUtf8Info(u16bit), map)), this.info, 8);
            }
            int n = 0;
            ++n;
        }
    }
    
    String renameEntry(final String s, final Map map) {
        return Descriptor.rename(s, map);
    }
    
    public void shiftIndex(final int n, final int n2) {
        while (2 < this.info.length) {
            final int u16bit = ByteArray.readU16bit(this.info, 10);
            if (u16bit >= n) {
                ByteArray.write16bit(u16bit + n2, this.info, 10);
            }
            final int n3;
            n3 += 10;
        }
    }
    
    public int tableLength() {
        return ByteArray.readU16bit(this.info, 0);
    }
    
    public int startPc(final int n) {
        return ByteArray.readU16bit(this.info, n * 10 + 2);
    }
    
    public int codeLength(final int n) {
        return ByteArray.readU16bit(this.info, n * 10 + 4);
    }
    
    void shiftPc(final int n, final int n2, final boolean b) {
        while (0 < this.tableLength()) {
            final int u16bit = ByteArray.readU16bit(this.info, 2);
            final int u16bit2 = ByteArray.readU16bit(this.info, 4);
            if (u16bit > n || (b && u16bit == n && u16bit != 0)) {
                ByteArray.write16bit(u16bit + n2, this.info, 2);
            }
            else if (u16bit + u16bit2 > n || (b && u16bit + u16bit2 == n)) {
                ByteArray.write16bit(u16bit2 + n2, this.info, 4);
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    public int nameIndex(final int n) {
        return ByteArray.readU16bit(this.info, n * 10 + 6);
    }
    
    public String variableName(final int n) {
        return this.getConstPool().getUtf8Info(this.nameIndex(n));
    }
    
    public String variableNameByIndex(final int n) {
        while (0 < this.tableLength()) {
            if (this.index(0) == n) {
                return this.variableName(0);
            }
            int n2 = 0;
            ++n2;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
    
    public int descriptorIndex(final int n) {
        return ByteArray.readU16bit(this.info, n * 10 + 8);
    }
    
    public int signatureIndex(final int n) {
        return this.descriptorIndex(n);
    }
    
    public String descriptor(final int n) {
        return this.getConstPool().getUtf8Info(this.descriptorIndex(n));
    }
    
    public String signature(final int n) {
        return this.descriptor(n);
    }
    
    public int index(final int n) {
        return ByteArray.readU16bit(this.info, n * 10 + 10);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        final byte[] value = this.get();
        final byte[] array = new byte[value.length];
        final ConstPool constPool2 = this.getConstPool();
        final LocalVariableAttribute thisAttr = this.makeThisAttr(constPool, array);
        final int u16bit = ByteArray.readU16bit(value, 0);
        ByteArray.write16bit(u16bit, array, 0);
        while (0 < u16bit) {
            final int u16bit2 = ByteArray.readU16bit(value, 2);
            final int u16bit3 = ByteArray.readU16bit(value, 4);
            int n = ByteArray.readU16bit(value, 6);
            int n2 = ByteArray.readU16bit(value, 8);
            final int u16bit4 = ByteArray.readU16bit(value, 10);
            ByteArray.write16bit(u16bit2, array, 2);
            ByteArray.write16bit(u16bit3, array, 4);
            if (n != 0) {
                n = constPool2.copy(n, constPool, null);
            }
            ByteArray.write16bit(n, array, 6);
            if (n2 != 0) {
                n2 = constPool.addUtf8Info(Descriptor.rename(constPool2.getUtf8Info(n2), map));
            }
            ByteArray.write16bit(n2, array, 8);
            ByteArray.write16bit(u16bit4, array, 10);
            final int n3;
            n3 += 10;
            int n4 = 0;
            ++n4;
        }
        return thisAttr;
    }
    
    LocalVariableAttribute makeThisAttr(final ConstPool constPool, final byte[] array) {
        return new LocalVariableAttribute(constPool, "LocalVariableTable", array);
    }
}
