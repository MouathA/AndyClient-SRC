package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.io.*;

public class StackMapTable extends AttributeInfo
{
    public static final String tag = "StackMapTable";
    public static final int TOP = 0;
    public static final int INTEGER = 1;
    public static final int FLOAT = 2;
    public static final int DOUBLE = 3;
    public static final int LONG = 4;
    public static final int NULL = 5;
    public static final int THIS = 6;
    public static final int OBJECT = 7;
    public static final int UNINIT = 8;
    
    StackMapTable(final ConstPool constPool, final byte[] array) {
        super(constPool, "StackMapTable", array);
    }
    
    StackMapTable(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) throws RuntimeCopyException {
        return new StackMapTable(constPool, new Copier(this.constPool, this.info, constPool, map).doit());
    }
    
    @Override
    void write(final DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
    }
    
    public void insertLocal(final int n, final int n2, final int n3) throws BadBytecode {
        this.set(new InsertLocal(this.get(), n, n2, n3).doit());
    }
    
    public static int typeTagOf(final char c) {
        switch (c) {
            case 'D': {
                return 3;
            }
            case 'F': {
                return 2;
            }
            case 'J': {
                return 4;
            }
            case 'L':
            case '[': {
                return 7;
            }
            default: {
                return 1;
            }
        }
    }
    
    public void println(final PrintWriter printWriter) {
        Printer.print(this, printWriter);
    }
    
    public void println(final PrintStream printStream) {
        Printer.print(this, new PrintWriter(printStream, true));
    }
    
    void shiftPc(final int n, final int n2, final boolean b) throws BadBytecode {
        new OffsetShifter(this, n, n2).parse();
        new Shifter(this, n, n2, b).doit();
    }
    
    void shiftForSwitch(final int n, final int n2) throws BadBytecode {
        new SwitchShifter(this, n, n2).doit();
    }
    
    public void removeNew(final int n) throws CannotCompileException {
        this.set(new NewRemover(this.get(), n).doit());
    }
    
    static class NewRemover extends SimpleCopy
    {
        int posOfNew;
        
        public NewRemover(final byte[] array, final int posOfNew) {
            super(array);
            this.posOfNew = posOfNew;
        }
        
        @Override
        public void sameLocals(final int n, final int n2, final int n3, final int n4) {
            if (n3 == 8 && n4 == this.posOfNew) {
                super.sameFrame(n, n2);
            }
            else {
                super.sameLocals(n, n2, n3, n4);
            }
        }
        
        @Override
        public void fullFrame(final int n, final int n2, final int[] array, final int[] array2, int[] array3, int[] array4) {
            int n3 = array3.length - 1;
            while (0 < n3) {
                if (array3[0] == 8 && array4[0] == this.posOfNew && array3[1] == 8 && array4[1] == this.posOfNew) {
                    final int[] array5 = new int[++n3 - 2];
                    final int[] array6 = new int[n3 - 2];
                    while (0 < n3) {
                        int n4 = 0;
                        ++n4;
                        ++n4;
                    }
                    array3 = array5;
                    array4 = array6;
                    break;
                }
                int n5 = 0;
                ++n5;
            }
            super.fullFrame(n, n2, array, array2, array3, array4);
        }
    }
    
    static class SimpleCopy extends Walker
    {
        private Writer writer;
        
        public SimpleCopy(final byte[] array) {
            super(array);
            this.writer = new Writer(array.length);
        }
        
        public byte[] doit() throws BadBytecode {
            this.parse();
            return this.writer.toByteArray();
        }
        
        @Override
        public void sameFrame(final int n, final int n2) {
            this.writer.sameFrame(n2);
        }
        
        @Override
        public void sameLocals(final int n, final int n2, final int n3, final int n4) {
            this.writer.sameLocals(n2, n3, this.copyData(n3, n4));
        }
        
        @Override
        public void chopFrame(final int n, final int n2, final int n3) {
            this.writer.chopFrame(n2, n3);
        }
        
        @Override
        public void appendFrame(final int n, final int n2, final int[] array, final int[] array2) {
            this.writer.appendFrame(n2, array, this.copyData(array, array2));
        }
        
        @Override
        public void fullFrame(final int n, final int n2, final int[] array, final int[] array2, final int[] array3, final int[] array4) {
            this.writer.fullFrame(n2, array, this.copyData(array, array2), array3, this.copyData(array3, array4));
        }
        
        protected int copyData(final int n, final int n2) {
            return n2;
        }
        
        protected int[] copyData(final int[] array, final int[] array2) {
            return array2;
        }
    }
    
    public static class Writer
    {
        ByteArrayOutputStream output;
        int numOfEntries;
        
        public Writer(final int n) {
            this.output = new ByteArrayOutputStream(n);
            this.numOfEntries = 0;
            this.output.write(0);
            this.output.write(0);
        }
        
        public byte[] toByteArray() {
            final byte[] byteArray = this.output.toByteArray();
            ByteArray.write16bit(this.numOfEntries, byteArray, 0);
            return byteArray;
        }
        
        public StackMapTable toStackMapTable(final ConstPool constPool) {
            return new StackMapTable(constPool, this.toByteArray());
        }
        
        public void sameFrame(final int n) {
            ++this.numOfEntries;
            if (n < 64) {
                this.output.write(n);
            }
            else {
                this.output.write(251);
                this.write16(n);
            }
        }
        
        public void sameLocals(final int n, final int n2, final int n3) {
            ++this.numOfEntries;
            if (n < 64) {
                this.output.write(n + 64);
            }
            else {
                this.output.write(247);
                this.write16(n);
            }
            this.writeTypeInfo(n2, n3);
        }
        
        public void chopFrame(final int n, final int n2) {
            ++this.numOfEntries;
            this.output.write(251 - n2);
            this.write16(n);
        }
        
        public void appendFrame(final int n, final int[] array, final int[] array2) {
            ++this.numOfEntries;
            final int length = array.length;
            this.output.write(length + 251);
            this.write16(n);
            while (0 < length) {
                this.writeTypeInfo(array[0], array2[0]);
                int n2 = 0;
                ++n2;
            }
        }
        
        public void fullFrame(final int n, final int[] array, final int[] array2, final int[] array3, final int[] array4) {
            ++this.numOfEntries;
            this.output.write(255);
            this.write16(n);
            final int length = array.length;
            this.write16(length);
            int n2 = 0;
            while (0 < length) {
                this.writeTypeInfo(array[0], array2[0]);
                ++n2;
            }
            final int length2 = array3.length;
            this.write16(length2);
            while (0 < length2) {
                this.writeTypeInfo(array3[0], array4[0]);
                ++n2;
            }
        }
        
        private void writeTypeInfo(final int n, final int n2) {
            this.output.write(n);
            if (n == 7 || n == 8) {
                this.write16(n2);
            }
        }
        
        private void write16(final int n) {
            this.output.write(n >>> 8 & 0xFF);
            this.output.write(n & 0xFF);
        }
    }
    
    public static class Walker
    {
        byte[] info;
        int numOfEntries;
        
        public Walker(final StackMapTable stackMapTable) {
            this(stackMapTable.get());
        }
        
        public Walker(final byte[] info) {
            this.info = info;
            this.numOfEntries = ByteArray.readU16bit(info, 0);
        }
        
        public final int size() {
            return this.numOfEntries;
        }
        
        public void parse() throws BadBytecode {
            while (0 < this.numOfEntries) {
                this.stackMapFrames(2, 0);
                int n = 0;
                ++n;
            }
        }
        
        int stackMapFrames(int n, final int n2) throws BadBytecode {
            final int n3 = this.info[n] & 0xFF;
            if (n3 < 64) {
                this.sameFrame(n, n3);
                ++n;
            }
            else if (n3 < 128) {
                n = this.sameLocals(n, n3);
            }
            else {
                if (n3 < 247) {
                    throw new BadBytecode("bad frame_type " + n3 + " in StackMapTable (pos: " + n + ", frame no.:" + n2 + ")");
                }
                if (n3 == 247) {
                    n = this.sameLocals(n, n3);
                }
                else if (n3 < 251) {
                    this.chopFrame(n, ByteArray.readU16bit(this.info, n + 1), 251 - n3);
                    n += 3;
                }
                else if (n3 == 251) {
                    this.sameFrame(n, ByteArray.readU16bit(this.info, n + 1));
                    n += 3;
                }
                else if (n3 < 255) {
                    n = this.appendFrame(n, n3);
                }
                else {
                    n = this.fullFrame(n);
                }
            }
            return n;
        }
        
        public void sameFrame(final int n, final int n2) throws BadBytecode {
        }
        
        private int sameLocals(int n, final int n2) throws BadBytecode {
            final int n3 = n;
            int u16bit;
            if (n2 < 128) {
                u16bit = n2 - 64;
            }
            else {
                u16bit = ByteArray.readU16bit(this.info, n + 1);
                n += 2;
            }
            final int n4 = this.info[n + 1] & 0xFF;
            if (n4 == 7 || n4 == 8) {
                ByteArray.readU16bit(this.info, n + 2);
                this.objectOrUninitialized(n4, 0, n + 2);
                n += 2;
            }
            this.sameLocals(n3, u16bit, n4, 0);
            return n + 2;
        }
        
        public void sameLocals(final int n, final int n2, final int n3, final int n4) throws BadBytecode {
        }
        
        public void chopFrame(final int n, final int n2, final int n3) throws BadBytecode {
        }
        
        private int appendFrame(final int n, final int n2) throws BadBytecode {
            final int n3 = n2 - 251;
            final int u16bit = ByteArray.readU16bit(this.info, n + 1);
            final int[] array = new int[n3];
            final int[] array2 = new int[n3];
            int n4 = n + 3;
            while (0 < n3) {
                final int n5 = this.info[n4] & 0xFF;
                array[0] = n5;
                if (n5 == 7 || n5 == 8) {
                    this.objectOrUninitialized(n5, array2[0] = ByteArray.readU16bit(this.info, n4 + 1), n4 + 1);
                    n4 += 3;
                }
                else {
                    array2[0] = 0;
                    ++n4;
                }
                int n6 = 0;
                ++n6;
            }
            this.appendFrame(n, u16bit, array, array2);
            return n4;
        }
        
        public void appendFrame(final int n, final int n2, final int[] array, final int[] array2) throws BadBytecode {
        }
        
        private int fullFrame(final int n) throws BadBytecode {
            final int u16bit = ByteArray.readU16bit(this.info, n + 1);
            final int u16bit2 = ByteArray.readU16bit(this.info, n + 3);
            final int[] array = new int[u16bit2];
            final int[] array2 = new int[u16bit2];
            final int verifyTypeInfo = this.verifyTypeInfo(n + 5, u16bit2, array, array2);
            final int u16bit3 = ByteArray.readU16bit(this.info, verifyTypeInfo);
            final int[] array3 = new int[u16bit3];
            final int[] array4 = new int[u16bit3];
            final int verifyTypeInfo2 = this.verifyTypeInfo(verifyTypeInfo + 2, u16bit3, array3, array4);
            this.fullFrame(n, u16bit, array, array2, array3, array4);
            return verifyTypeInfo2;
        }
        
        public void fullFrame(final int n, final int n2, final int[] array, final int[] array2, final int[] array3, final int[] array4) throws BadBytecode {
        }
        
        private int verifyTypeInfo(int n, final int n2, final int[] array, final int[] array2) {
            while (0 < n2) {
                final int n3 = this.info[n++] & 0xFF;
                array[0] = n3;
                if (n3 == 7 || n3 == 8) {
                    this.objectOrUninitialized(n3, array2[0] = ByteArray.readU16bit(this.info, n), n);
                    n += 2;
                }
                int n4 = 0;
                ++n4;
            }
            return n;
        }
        
        public void objectOrUninitialized(final int n, final int n2, final int n3) {
        }
    }
    
    static class SwitchShifter extends Shifter
    {
        SwitchShifter(final StackMapTable stackMapTable, final int n, final int n2) {
            super(stackMapTable, n, n2, false);
        }
        
        @Override
        void update(final int n, final int n2, final int n3, final int n4) {
            final int position = this.position;
            this.position = position + n2 + ((position != 0) ? 1 : 0);
            int n5;
            if (this.where == this.position) {
                n5 = n2 - this.gap;
            }
            else {
                if (this.where != position) {
                    return;
                }
                n5 = n2 + this.gap;
            }
            if (n2 < 64) {
                if (n5 < 64) {
                    this.info[n] = (byte)(n5 + n3);
                }
                else {
                    final byte[] insertGap = Shifter.insertGap(this.info, n, 2);
                    insertGap[n] = (byte)n4;
                    ByteArray.write16bit(n5, insertGap, n + 1);
                    this.updatedInfo = insertGap;
                }
            }
            else if (n5 < 64) {
                final byte[] deleteGap = deleteGap(this.info, n, 2);
                deleteGap[n] = (byte)(n5 + n3);
                this.updatedInfo = deleteGap;
            }
            else {
                ByteArray.write16bit(n5, this.info, n + 1);
            }
        }
        
        static byte[] deleteGap(final byte[] array, int n, final int n2) {
            n += n2;
            final int length = array.length;
            final byte[] array2 = new byte[length - n2];
            while (0 < length) {
                array2[0 - ((0 < n) ? 0 : n2)] = array[0];
                int n3 = 0;
                ++n3;
            }
            return array2;
        }
        
        @Override
        void update(final int n, final int n2) {
            final int position = this.position;
            this.position = position + n2 + ((position != 0) ? 1 : 0);
            int n3;
            if (this.where == this.position) {
                n3 = n2 - this.gap;
            }
            else {
                if (this.where != position) {
                    return;
                }
                n3 = n2 + this.gap;
            }
            ByteArray.write16bit(n3, this.info, n + 1);
        }
    }
    
    static class Shifter extends Walker
    {
        private StackMapTable stackMap;
        int where;
        int gap;
        int position;
        byte[] updatedInfo;
        boolean exclusive;
        
        public Shifter(final StackMapTable stackMap, final int where, final int gap, final boolean exclusive) {
            super(stackMap);
            this.stackMap = stackMap;
            this.where = where;
            this.gap = gap;
            this.position = 0;
            this.updatedInfo = null;
            this.exclusive = exclusive;
        }
        
        public void doit() throws BadBytecode {
            this.parse();
            if (this.updatedInfo != null) {
                this.stackMap.set(this.updatedInfo);
            }
        }
        
        @Override
        public void sameFrame(final int n, final int n2) {
            this.update(n, n2, 0, 251);
        }
        
        @Override
        public void sameLocals(final int n, final int n2, final int n3, final int n4) {
            this.update(n, n2, 64, 247);
        }
        
        void update(final int n, final int n2, final int n3, final int n4) {
            final int position = this.position;
            this.position = position + n2 + ((position != 0) ? 1 : 0);
            boolean b;
            if (this.exclusive) {
                b = ((position == 0 && this.where == 0) || (position < this.where && this.where <= this.position));
            }
            else {
                b = (position <= this.where && this.where < this.position);
            }
            if (b) {
                final int n5 = this.info[n] & 0xFF;
                final int n6 = n2 + this.gap;
                this.position += this.gap;
                if (n6 < 64) {
                    this.info[n] = (byte)(n6 + n3);
                }
                else if (n2 < 64 && n5 != n4) {
                    final byte[] insertGap = insertGap(this.info, n, 2);
                    insertGap[n] = (byte)n4;
                    ByteArray.write16bit(n6, insertGap, n + 1);
                    this.updatedInfo = insertGap;
                }
                else {
                    ByteArray.write16bit(n6, this.info, n + 1);
                }
            }
        }
        
        static byte[] insertGap(final byte[] array, final int n, final int n2) {
            final int length = array.length;
            final byte[] array2 = new byte[length + n2];
            while (0 < length) {
                array2[0 + ((0 < n) ? 0 : n2)] = array[0];
                int n3 = 0;
                ++n3;
            }
            return array2;
        }
        
        @Override
        public void chopFrame(final int n, final int n2, final int n3) {
            this.update(n, n2);
        }
        
        @Override
        public void appendFrame(final int n, final int n2, final int[] array, final int[] array2) {
            this.update(n, n2);
        }
        
        @Override
        public void fullFrame(final int n, final int n2, final int[] array, final int[] array2, final int[] array3, final int[] array4) {
            this.update(n, n2);
        }
        
        void update(final int n, final int n2) {
            final int position = this.position;
            this.position = position + n2 + ((position != 0) ? 1 : 0);
            boolean b;
            if (this.exclusive) {
                b = ((position == 0 && this.where == 0) || (position < this.where && this.where <= this.position));
            }
            else {
                b = (position <= this.where && this.where < this.position);
            }
            if (b) {
                ByteArray.write16bit(n2 + this.gap, this.info, n + 1);
                this.position += this.gap;
            }
        }
    }
    
    static class OffsetShifter extends Walker
    {
        int where;
        int gap;
        
        public OffsetShifter(final StackMapTable stackMapTable, final int where, final int gap) {
            super(stackMapTable);
            this.where = where;
            this.gap = gap;
        }
        
        @Override
        public void objectOrUninitialized(final int n, final int n2, final int n3) {
            if (n == 8 && this.where <= n2) {
                ByteArray.write16bit(n2 + this.gap, this.info, n3);
            }
        }
    }
    
    static class Printer extends Walker
    {
        private PrintWriter writer;
        private int offset;
        
        public static void print(final StackMapTable stackMapTable, final PrintWriter printWriter) {
            new Printer(stackMapTable.get(), printWriter).parse();
        }
        
        Printer(final byte[] array, final PrintWriter writer) {
            super(array);
            this.writer = writer;
            this.offset = -1;
        }
        
        @Override
        public void sameFrame(final int n, final int n2) {
            this.offset += n2 + 1;
            this.writer.println(this.offset + " same frame: " + n2);
        }
        
        @Override
        public void sameLocals(final int n, final int n2, final int n3, final int n4) {
            this.offset += n2 + 1;
            this.writer.println(this.offset + " same locals: " + n2);
            this.printTypeInfo(n3, n4);
        }
        
        @Override
        public void chopFrame(final int n, final int n2, final int n3) {
            this.offset += n2 + 1;
            this.writer.println(this.offset + " chop frame: " + n2 + ",    " + n3 + " last locals");
        }
        
        @Override
        public void appendFrame(final int n, final int n2, final int[] array, final int[] array2) {
            this.offset += n2 + 1;
            this.writer.println(this.offset + " append frame: " + n2);
            while (0 < array.length) {
                this.printTypeInfo(array[0], array2[0]);
                int n3 = 0;
                ++n3;
            }
        }
        
        @Override
        public void fullFrame(final int n, final int n2, final int[] array, final int[] array2, final int[] array3, final int[] array4) {
            this.offset += n2 + 1;
            this.writer.println(this.offset + " full frame: " + n2);
            this.writer.println("[locals]");
            int n3 = 0;
            while (0 < array.length) {
                this.printTypeInfo(array[0], array2[0]);
                ++n3;
            }
            this.writer.println("[stack]");
            while (0 < array3.length) {
                this.printTypeInfo(array3[0], array4[0]);
                ++n3;
            }
        }
        
        private void printTypeInfo(final int n, final int n2) {
            String s = null;
            switch (n) {
                case 0: {
                    s = "top";
                    break;
                }
                case 1: {
                    s = "integer";
                    break;
                }
                case 2: {
                    s = "float";
                    break;
                }
                case 3: {
                    s = "double";
                    break;
                }
                case 4: {
                    s = "long";
                    break;
                }
                case 5: {
                    s = "null";
                    break;
                }
                case 6: {
                    s = "this";
                    break;
                }
                case 7: {
                    s = "object (cpool_index " + n2 + ")";
                    break;
                }
                case 8: {
                    s = "uninitialized (offset " + n2 + ")";
                    break;
                }
            }
            this.writer.print("    ");
            this.writer.println(s);
        }
    }
    
    static class InsertLocal extends SimpleCopy
    {
        private int varIndex;
        private int varTag;
        private int varData;
        
        public InsertLocal(final byte[] array, final int varIndex, final int varTag, final int varData) {
            super(array);
            this.varIndex = varIndex;
            this.varTag = varTag;
            this.varData = varData;
        }
        
        @Override
        public void fullFrame(final int n, final int n2, final int[] array, final int[] array2, final int[] array3, final int[] array4) {
            final int length = array.length;
            if (length < this.varIndex) {
                super.fullFrame(n, n2, array, array2, array3, array4);
                return;
            }
            final int n3 = (this.varTag == 4 || this.varTag == 3) ? 2 : 1;
            final int[] array5 = new int[length + n3];
            final int[] array6 = new int[length + n3];
            final int varIndex = this.varIndex;
            while (0 < length) {
                int n4 = 0;
                if (varIndex == 0) {
                    n4 = 0 + n3;
                }
                array5[0] = array[0];
                final int[] array7 = array6;
                final int n5 = 0;
                ++n4;
                array7[n5] = array2[0];
                int n6 = 0;
                ++n6;
            }
            array5[varIndex] = this.varTag;
            array6[varIndex] = this.varData;
            if (n3 > 1) {
                array6[varIndex + 1] = (array5[varIndex + 1] = 0);
            }
            super.fullFrame(n, n2, array5, array6, array3, array4);
        }
    }
    
    static class Copier extends SimpleCopy
    {
        private ConstPool srcPool;
        private ConstPool destPool;
        private Map classnames;
        
        public Copier(final ConstPool srcPool, final byte[] array, final ConstPool destPool, final Map classnames) {
            super(array);
            this.srcPool = srcPool;
            this.destPool = destPool;
            this.classnames = classnames;
        }
        
        @Override
        protected int copyData(final int n, final int n2) {
            if (n == 7) {
                return this.srcPool.copy(n2, this.destPool, this.classnames);
            }
            return n2;
        }
        
        @Override
        protected int[] copyData(final int[] array, final int[] array2) {
            final int[] array3 = new int[array2.length];
            while (0 < array2.length) {
                if (array[0] == 7) {
                    array3[0] = this.srcPool.copy(array2[0], this.destPool, this.classnames);
                }
                else {
                    array3[0] = array2[0];
                }
                int n = 0;
                ++n;
            }
            return array3;
        }
    }
    
    public static class RuntimeCopyException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;
        
        public RuntimeCopyException(final String s) {
            super(s);
        }
    }
}
