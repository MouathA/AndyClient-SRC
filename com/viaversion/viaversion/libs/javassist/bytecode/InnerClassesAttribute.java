package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;

public class InnerClassesAttribute extends AttributeInfo
{
    public static final String tag = "InnerClasses";
    
    InnerClassesAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    private InnerClassesAttribute(final ConstPool constPool, final byte[] array) {
        super(constPool, "InnerClasses", array);
    }
    
    public InnerClassesAttribute(final ConstPool constPool) {
        super(constPool, "InnerClasses", new byte[2]);
        ByteArray.write16bit(0, this.get(), 0);
    }
    
    public int tableLength() {
        return ByteArray.readU16bit(this.get(), 0);
    }
    
    public int innerClassIndex(final int n) {
        return ByteArray.readU16bit(this.get(), n * 8 + 2);
    }
    
    public String innerClass(final int n) {
        final int innerClassIndex = this.innerClassIndex(n);
        if (innerClassIndex == 0) {
            return null;
        }
        return this.constPool.getClassInfo(innerClassIndex);
    }
    
    public void setInnerClassIndex(final int n, final int n2) {
        ByteArray.write16bit(n2, this.get(), n * 8 + 2);
    }
    
    public int outerClassIndex(final int n) {
        return ByteArray.readU16bit(this.get(), n * 8 + 4);
    }
    
    public String outerClass(final int n) {
        final int outerClassIndex = this.outerClassIndex(n);
        if (outerClassIndex == 0) {
            return null;
        }
        return this.constPool.getClassInfo(outerClassIndex);
    }
    
    public void setOuterClassIndex(final int n, final int n2) {
        ByteArray.write16bit(n2, this.get(), n * 8 + 4);
    }
    
    public int innerNameIndex(final int n) {
        return ByteArray.readU16bit(this.get(), n * 8 + 6);
    }
    
    public String innerName(final int n) {
        final int innerNameIndex = this.innerNameIndex(n);
        if (innerNameIndex == 0) {
            return null;
        }
        return this.constPool.getUtf8Info(innerNameIndex);
    }
    
    public void setInnerNameIndex(final int n, final int n2) {
        ByteArray.write16bit(n2, this.get(), n * 8 + 6);
    }
    
    public int accessFlags(final int n) {
        return ByteArray.readU16bit(this.get(), n * 8 + 8);
    }
    
    public void setAccessFlags(final int n, final int n2) {
        ByteArray.write16bit(n2, this.get(), n * 8 + 8);
    }
    
    public int find(final String s) {
        while (0 < this.tableLength()) {
            if (s.equals(this.innerClass(0))) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public void append(final String s, final String s2, final String s3, final int n) {
        this.append(this.constPool.addClassInfo(s), this.constPool.addClassInfo(s2), this.constPool.addUtf8Info(s3), n);
    }
    
    public void append(final int n, final int n2, final int n3, final int n4) {
        final byte[] value = this.get();
        final int length = value.length;
        final byte[] array = new byte[length + 8];
        while (2 < length) {
            array[2] = value[2];
            int u16bit = 0;
            ++u16bit;
        }
        int u16bit = ByteArray.readU16bit(value, 0);
        ByteArray.write16bit(3, array, 0);
        ByteArray.write16bit(n, array, length);
        ByteArray.write16bit(n2, array, length + 2);
        ByteArray.write16bit(n3, array, length + 4);
        ByteArray.write16bit(n4, array, length + 6);
        this.set(array);
    }
    
    public int remove(final int n) {
        final byte[] value = this.get();
        final int length = value.length;
        if (length < 10) {
            return 0;
        }
        final int u16bit = ByteArray.readU16bit(value, 0);
        final int n2 = 2 + n * 8;
        if (u16bit <= n) {
            return u16bit;
        }
        final byte[] array = new byte[length - 8];
        ByteArray.write16bit(u16bit - 1, array, 0);
        while (2 < length) {
            if (2 == n2) {
                final int n3;
                n3 += 8;
            }
            else {
                final byte[] array2 = array;
                final int n4 = 2;
                int n5 = 0;
                ++n5;
                final byte[] array3 = value;
                final int n6 = 2;
                int n3 = 0;
                ++n3;
                array2[n4] = array3[n6];
            }
        }
        this.set(array);
        return u16bit - 1;
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        final byte[] value = this.get();
        final byte[] array = new byte[value.length];
        final ConstPool constPool2 = this.getConstPool();
        final InnerClassesAttribute innerClassesAttribute = new InnerClassesAttribute(constPool, array);
        final int u16bit = ByteArray.readU16bit(value, 0);
        ByteArray.write16bit(u16bit, array, 0);
        while (0 < u16bit) {
            int n = ByteArray.readU16bit(value, 2);
            int n2 = ByteArray.readU16bit(value, 4);
            int n3 = ByteArray.readU16bit(value, 6);
            final int u16bit2 = ByteArray.readU16bit(value, 8);
            if (n != 0) {
                n = constPool2.copy(n, constPool, map);
            }
            ByteArray.write16bit(n, array, 2);
            if (n2 != 0) {
                n2 = constPool2.copy(n2, constPool, map);
            }
            ByteArray.write16bit(n2, array, 4);
            if (n3 != 0) {
                n3 = constPool2.copy(n3, constPool, map);
            }
            ByteArray.write16bit(n3, array, 6);
            ByteArray.write16bit(u16bit2, array, 8);
            final int n4;
            n4 += 8;
            int n5 = 0;
            ++n5;
        }
        return innerClassesAttribute;
    }
}
