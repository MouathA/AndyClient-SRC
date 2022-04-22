package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;

public class LineNumberAttribute extends AttributeInfo
{
    public static final String tag = "LineNumberTable";
    
    LineNumberAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    private LineNumberAttribute(final ConstPool constPool, final byte[] array) {
        super(constPool, "LineNumberTable", array);
    }
    
    public int tableLength() {
        return ByteArray.readU16bit(this.info, 0);
    }
    
    public int startPc(final int n) {
        return ByteArray.readU16bit(this.info, n * 4 + 2);
    }
    
    public int lineNumber(final int n) {
        return ByteArray.readU16bit(this.info, n * 4 + 4);
    }
    
    public int toLineNumber(final int n) {
        while (0 < this.tableLength()) {
            if (n < this.startPc(0)) {
                if (!false) {
                    return this.lineNumber(0);
                }
                break;
            }
            else {
                int n2 = 0;
                ++n2;
            }
        }
        return this.lineNumber(-1);
    }
    
    public int toStartPc(final int n) {
        while (0 < this.tableLength()) {
            if (n == this.lineNumber(0)) {
                return this.startPc(0);
            }
            int n2 = 0;
            ++n2;
        }
        return -1;
    }
    
    public Pc toNearPc(final int n) {
        final int tableLength = this.tableLength();
        if (tableLength > 0) {
            final int n2 = this.lineNumber(0) - n;
            this.startPc(0);
        }
        while (1 < tableLength) {
            final int n3 = this.lineNumber(1) - n;
            if ((n3 < 0 && n3 > 0) || (n3 >= 0 && (n3 < 0 || 0 < 0))) {
                this.startPc(1);
            }
            int n4 = 0;
            ++n4;
        }
        final Pc pc = new Pc();
        pc.index = 0;
        pc.line = n + 0;
        return pc;
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        final byte[] info = this.info;
        final int length = info.length;
        final byte[] array = new byte[length];
        while (0 < length) {
            array[0] = info[0];
            int n = 0;
            ++n;
        }
        return new LineNumberAttribute(constPool, array);
    }
    
    void shiftPc(final int n, final int n2, final boolean b) {
        while (0 < this.tableLength()) {
            final int u16bit = ByteArray.readU16bit(this.info, 2);
            if (u16bit > n || (b && u16bit == n)) {
                ByteArray.write16bit(u16bit + n2, this.info, 2);
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    public static class Pc
    {
        public int index;
        public int line;
    }
}
