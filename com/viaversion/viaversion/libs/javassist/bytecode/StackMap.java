package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.io.*;

public class StackMap extends AttributeInfo
{
    public static final String tag = "StackMap";
    public static final int TOP = 0;
    public static final int INTEGER = 1;
    public static final int FLOAT = 2;
    public static final int DOUBLE = 3;
    public static final int LONG = 4;
    public static final int NULL = 5;
    public static final int THIS = 6;
    public static final int OBJECT = 7;
    public static final int UNINIT = 8;
    
    StackMap(final ConstPool constPool, final byte[] array) {
        super(constPool, "StackMap", array);
    }
    
    StackMap(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    public int numOfEntries() {
        return ByteArray.readU16bit(this.info, 0);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        final Copier copier = new Copier(this, constPool, map);
        copier.visit();
        return copier.getStackMap();
    }
    
    public void insertLocal(final int n, final int n2, final int n3) throws BadBytecode {
        this.set(new InsertLocal(this, n, n2, n3).doit());
    }
    
    void shiftPc(final int n, final int n2, final boolean b) throws BadBytecode {
        new Shifter(this, n, n2, b).visit();
    }
    
    void shiftForSwitch(final int n, final int n2) throws BadBytecode {
        new SwitchShifter(this, n, n2).visit();
    }
    
    public void removeNew(final int n) throws CannotCompileException {
        this.set(new NewRemover(this, n).doit());
    }
    
    public void print(final PrintWriter printWriter) {
        new Printer(this, printWriter).print();
    }
    
    public static class Writer
    {
        private ByteArrayOutputStream output;
        
        public Writer() {
            this.output = new ByteArrayOutputStream();
        }
        
        public byte[] toByteArray() {
            return this.output.toByteArray();
        }
        
        public StackMap toStackMap(final ConstPool constPool) {
            return new StackMap(constPool, this.output.toByteArray());
        }
        
        public void writeVerifyTypeInfo(final int n, final int n2) {
            this.output.write(n);
            if (n == 7 || n == 8) {
                this.write16bit(n2);
            }
        }
        
        public void write16bit(final int n) {
            this.output.write(n >>> 8 & 0xFF);
            this.output.write(n & 0xFF);
        }
    }
    
    static class Printer extends Walker
    {
        private PrintWriter writer;
        
        public Printer(final StackMap stackMap, final PrintWriter writer) {
            super(stackMap);
            this.writer = writer;
        }
        
        public void print() {
            this.writer.println(ByteArray.readU16bit(this.info, 0) + " entries");
            this.visit();
        }
        
        @Override
        public int locals(final int n, final int n2, final int n3) {
            this.writer.println("  * offset " + n2);
            return super.locals(n, n2, n3);
        }
    }
    
    public static class Walker
    {
        byte[] info;
        
        public Walker(final StackMap stackMap) {
            this.info = stackMap.get();
        }
        
        public void visit() {
            while (0 < ByteArray.readU16bit(this.info, 0)) {
                final int u16bit = ByteArray.readU16bit(this.info, 2);
                this.locals(6, u16bit, ByteArray.readU16bit(this.info, 4));
                this.stack(4, u16bit, ByteArray.readU16bit(this.info, 2));
                int n = 0;
                ++n;
            }
        }
        
        public int locals(final int n, final int n2, final int n3) {
            return this.typeInfoArray(n, n2, n3, true);
        }
        
        public int stack(final int n, final int n2, final int n3) {
            return this.typeInfoArray(n, n2, n3, false);
        }
        
        public int typeInfoArray(int typeInfoArray2, final int n, final int n2, final boolean b) {
            while (0 < n2) {
                typeInfoArray2 = this.typeInfoArray2(0, typeInfoArray2);
                int n3 = 0;
                ++n3;
            }
            return typeInfoArray2;
        }
        
        int typeInfoArray2(final int n, int n2) {
            final byte b = this.info[n2];
            if (b == 7) {
                this.objectVariable(n2, ByteArray.readU16bit(this.info, n2 + 1));
                n2 += 3;
            }
            else if (b == 8) {
                this.uninitialized(n2, ByteArray.readU16bit(this.info, n2 + 1));
                n2 += 3;
            }
            else {
                this.typeInfo(n2, b);
                ++n2;
            }
            return n2;
        }
        
        public void typeInfo(final int n, final byte b) {
        }
        
        public void objectVariable(final int n, final int n2) {
        }
        
        public void uninitialized(final int n, final int n2) {
        }
    }
    
    static class NewRemover extends SimpleCopy
    {
        int posOfNew;
        
        NewRemover(final StackMap stackMap, final int posOfNew) {
            super(stackMap);
            this.posOfNew = posOfNew;
        }
        
        @Override
        public int stack(final int n, final int n2, final int n3) {
            return this.stackTypeInfoArray(n, n2, n3);
        }
        
        private int stackTypeInfoArray(int n, final int n2, final int n3) {
            int n4 = n;
            int n6 = 0;
            while (0 < n3) {
                final byte b = this.info[n4];
                if (b == 7) {
                    n4 += 3;
                }
                else if (b == 8) {
                    if (ByteArray.readU16bit(this.info, n4 + 1) == this.posOfNew) {
                        int n5 = 0;
                        ++n5;
                    }
                    n4 += 3;
                }
                else {
                    ++n4;
                }
                ++n6;
            }
            this.writer.write16bit(n3 - 0);
            while (0 < n3) {
                final byte b2 = this.info[n];
                if (b2 == 7) {
                    this.objectVariable(n, ByteArray.readU16bit(this.info, n + 1));
                    n += 3;
                }
                else if (b2 == 8) {
                    final int u16bit = ByteArray.readU16bit(this.info, n + 1);
                    if (u16bit != this.posOfNew) {
                        this.uninitialized(n, u16bit);
                    }
                    n += 3;
                }
                else {
                    this.typeInfo(n, b2);
                    ++n;
                }
                ++n6;
            }
            return n;
        }
    }
    
    static class SimpleCopy extends Walker
    {
        Writer writer;
        
        SimpleCopy(final StackMap stackMap) {
            super(stackMap);
            this.writer = new Writer();
        }
        
        byte[] doit() {
            this.visit();
            return this.writer.toByteArray();
        }
        
        @Override
        public void visit() {
            this.writer.write16bit(ByteArray.readU16bit(this.info, 0));
            super.visit();
        }
        
        @Override
        public int locals(final int n, final int n2, final int n3) {
            this.writer.write16bit(n2);
            return super.locals(n, n2, n3);
        }
        
        @Override
        public int typeInfoArray(final int n, final int n2, final int n3, final boolean b) {
            this.writer.write16bit(n3);
            return super.typeInfoArray(n, n2, n3, b);
        }
        
        @Override
        public void typeInfo(final int n, final byte b) {
            this.writer.writeVerifyTypeInfo(b, 0);
        }
        
        @Override
        public void objectVariable(final int n, final int n2) {
            this.writer.writeVerifyTypeInfo(7, n2);
        }
        
        @Override
        public void uninitialized(final int n, final int n2) {
            this.writer.writeVerifyTypeInfo(8, n2);
        }
    }
    
    static class SwitchShifter extends Walker
    {
        private int where;
        private int gap;
        
        public SwitchShifter(final StackMap stackMap, final int where, final int gap) {
            super(stackMap);
            this.where = where;
            this.gap = gap;
        }
        
        @Override
        public int locals(final int n, final int n2, final int n3) {
            if (this.where == n + n2) {
                ByteArray.write16bit(n2 - this.gap, this.info, n - 4);
            }
            else if (this.where == n) {
                ByteArray.write16bit(n2 + this.gap, this.info, n - 4);
            }
            return super.locals(n, n2, n3);
        }
    }
    
    static class Shifter extends Walker
    {
        private int where;
        private int gap;
        private boolean exclusive;
        
        public Shifter(final StackMap stackMap, final int where, final int gap, final boolean exclusive) {
            super(stackMap);
            this.where = where;
            this.gap = gap;
            this.exclusive = exclusive;
        }
        
        @Override
        public int locals(final int n, final int n2, final int n3) {
            if (this.exclusive) {
                if (this.where > n2) {
                    return super.locals(n, n2, n3);
                }
            }
            else if (this.where >= n2) {
                return super.locals(n, n2, n3);
            }
            ByteArray.write16bit(n2 + this.gap, this.info, n - 4);
            return super.locals(n, n2, n3);
        }
        
        @Override
        public void uninitialized(final int n, final int n2) {
            if (this.where <= n2) {
                ByteArray.write16bit(n2 + this.gap, this.info, n + 1);
            }
        }
    }
    
    static class InsertLocal extends SimpleCopy
    {
        private int varIndex;
        private int varTag;
        private int varData;
        
        InsertLocal(final StackMap stackMap, final int varIndex, final int varTag, final int varData) {
            super(stackMap);
            this.varIndex = varIndex;
            this.varTag = varTag;
            this.varData = varData;
        }
        
        @Override
        public int typeInfoArray(int typeInfoArray2, final int n, final int n2, final boolean b) {
            if (!b || n2 < this.varIndex) {
                return super.typeInfoArray(typeInfoArray2, n, n2, b);
            }
            this.writer.write16bit(n2 + 1);
            while (0 < n2) {
                if (0 == this.varIndex) {
                    this.writeVarTypeInfo();
                }
                typeInfoArray2 = this.typeInfoArray2(0, typeInfoArray2);
                int n3 = 0;
                ++n3;
            }
            if (n2 == this.varIndex) {
                this.writeVarTypeInfo();
            }
            return typeInfoArray2;
        }
        
        private void writeVarTypeInfo() {
            if (this.varTag == 7) {
                this.writer.writeVerifyTypeInfo(7, this.varData);
            }
            else if (this.varTag == 8) {
                this.writer.writeVerifyTypeInfo(8, this.varData);
            }
            else {
                this.writer.writeVerifyTypeInfo(this.varTag, 0);
            }
        }
    }
    
    static class Copier extends Walker
    {
        byte[] dest;
        ConstPool srcCp;
        ConstPool destCp;
        Map classnames;
        
        Copier(final StackMap stackMap, final ConstPool destCp, final Map classnames) {
            super(stackMap);
            this.srcCp = stackMap.getConstPool();
            this.dest = new byte[this.info.length];
            this.destCp = destCp;
            this.classnames = classnames;
        }
        
        @Override
        public void visit() {
            ByteArray.write16bit(ByteArray.readU16bit(this.info, 0), this.dest, 0);
            super.visit();
        }
        
        @Override
        public int locals(final int n, final int n2, final int n3) {
            ByteArray.write16bit(n2, this.dest, n - 4);
            return super.locals(n, n2, n3);
        }
        
        @Override
        public int typeInfoArray(final int n, final int n2, final int n3, final boolean b) {
            ByteArray.write16bit(n3, this.dest, n - 2);
            return super.typeInfoArray(n, n2, n3, b);
        }
        
        @Override
        public void typeInfo(final int n, final byte b) {
            this.dest[n] = b;
        }
        
        @Override
        public void objectVariable(final int n, final int n2) {
            this.dest[n] = 7;
            ByteArray.write16bit(this.srcCp.copy(n2, this.destCp, this.classnames), this.dest, n + 1);
        }
        
        @Override
        public void uninitialized(final int n, final int n2) {
            this.dest[n] = 8;
            ByteArray.write16bit(n2, this.dest, n + 1);
        }
        
        public StackMap getStackMap() {
            return new StackMap(this.destCp, this.dest);
        }
    }
}
