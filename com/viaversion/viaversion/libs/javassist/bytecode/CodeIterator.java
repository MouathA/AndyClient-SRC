package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;

public class CodeIterator implements Opcode
{
    protected CodeAttribute codeAttr;
    protected byte[] bytecode;
    protected int endPos;
    protected int currentPos;
    protected int mark;
    protected int mark2;
    private static final int[] opcodeLength;
    
    protected CodeIterator(final CodeAttribute codeAttr) {
        this.codeAttr = codeAttr;
        this.bytecode = codeAttr.getCode();
        this.begin();
    }
    
    public void begin() {
        final int currentPos = 0;
        this.mark2 = currentPos;
        this.mark = currentPos;
        this.currentPos = currentPos;
        this.endPos = this.getCodeLength();
    }
    
    public void move(final int currentPos) {
        this.currentPos = currentPos;
    }
    
    public void setMark(final int mark) {
        this.mark = mark;
    }
    
    public void setMark2(final int mark2) {
        this.mark2 = mark2;
    }
    
    public int getMark() {
        return this.mark;
    }
    
    public int getMark2() {
        return this.mark2;
    }
    
    public CodeAttribute get() {
        return this.codeAttr;
    }
    
    public int getCodeLength() {
        return this.bytecode.length;
    }
    
    public int byteAt(final int n) {
        return this.bytecode[n] & 0xFF;
    }
    
    public int signedByteAt(final int n) {
        return this.bytecode[n];
    }
    
    public void writeByte(final int n, final int n2) {
        this.bytecode[n2] = (byte)n;
    }
    
    public int u16bitAt(final int n) {
        return ByteArray.readU16bit(this.bytecode, n);
    }
    
    public int s16bitAt(final int n) {
        return ByteArray.readS16bit(this.bytecode, n);
    }
    
    public void write16bit(final int n, final int n2) {
        ByteArray.write16bit(n, this.bytecode, n2);
    }
    
    public int s32bitAt(final int n) {
        return ByteArray.read32bit(this.bytecode, n);
    }
    
    public void write32bit(final int n, final int n2) {
        ByteArray.write32bit(n, this.bytecode, n2);
    }
    
    public void write(final byte[] array, int n) {
        for (int length = array.length, i = 0; i < length; ++i) {
            this.bytecode[n++] = array[i];
        }
    }
    
    public boolean hasNext() {
        return this.currentPos < this.endPos;
    }
    
    public int next() throws BadBytecode {
        final int currentPos = this.currentPos;
        this.currentPos = nextOpcode(this.bytecode, currentPos);
        return currentPos;
    }
    
    public int lookAhead() {
        return this.currentPos;
    }
    
    public int skipConstructor() throws BadBytecode {
        return this.skipSuperConstructor0(-1);
    }
    
    public int skipSuperConstructor() throws BadBytecode {
        return this.skipSuperConstructor0(0);
    }
    
    public int skipThisConstructor() throws BadBytecode {
        return this.skipSuperConstructor0(1);
    }
    
    private int skipSuperConstructor0(final int n) throws BadBytecode {
        this.begin();
        final ConstPool constPool = this.codeAttr.getConstPool();
        final String declaringClass = this.codeAttr.getDeclaringClass();
        int n2 = 0;
        while (this.hasNext()) {
            final int next = this.next();
            final int byte1 = this.byteAt(next);
            if (byte1 == 187) {
                ++n2;
            }
            else {
                if (byte1 != 183) {
                    continue;
                }
                final int u16bit = ByteArray.readU16bit(this.bytecode, next + 1);
                if (!constPool.getMethodrefName(u16bit).equals("<init>") || --n2 >= 0) {
                    continue;
                }
                if (n < 0) {
                    return next;
                }
                if (constPool.getMethodrefClassName(u16bit).equals(declaringClass) == n > 0) {
                    return next;
                }
                break;
            }
        }
        this.begin();
        return -1;
    }
    
    public int insert(final byte[] array) throws BadBytecode {
        return this.insert0(this.currentPos, array, false);
    }
    
    public void insert(final int n, final byte[] array) throws BadBytecode {
        this.insert0(n, array, false);
    }
    
    public int insertAt(final int n, final byte[] array) throws BadBytecode {
        return this.insert0(n, array, false);
    }
    
    public int insertEx(final byte[] array) throws BadBytecode {
        return this.insert0(this.currentPos, array, true);
    }
    
    public void insertEx(final int n, final byte[] array) throws BadBytecode {
        this.insert0(n, array, true);
    }
    
    public int insertExAt(final int n, final byte[] array) throws BadBytecode {
        return this.insert0(n, array, true);
    }
    
    private int insert0(int n, final byte[] array, final boolean b) throws BadBytecode {
        final int length = array.length;
        if (length <= 0) {
            return n;
        }
        int position;
        n = (position = this.insertGapAt(n, length, b).position);
        for (int i = 0; i < length; ++i) {
            this.bytecode[position++] = array[i];
        }
        return n;
    }
    
    public int insertGap(final int n) throws BadBytecode {
        return this.insertGapAt(this.currentPos, n, false).position;
    }
    
    public int insertGap(final int n, final int n2) throws BadBytecode {
        return this.insertGapAt(n, n2, false).length;
    }
    
    public int insertExGap(final int n) throws BadBytecode {
        return this.insertGapAt(this.currentPos, n, true).position;
    }
    
    public int insertExGap(final int n, final int n2) throws BadBytecode {
        return this.insertGapAt(n, n2, true).length;
    }
    
    public Gap insertGapAt(int position, final int n, final boolean b) throws BadBytecode {
        final Gap gap = new Gap();
        if (n <= 0) {
            gap.position = position;
            gap.length = 0;
            return gap;
        }
        byte[] array;
        int length;
        if (this.bytecode.length + n > 32767) {
            array = this.insertGapCore0w(this.bytecode, position, n, b, this.get().getExceptionTable(), this.codeAttr, gap);
            position = gap.position;
            length = n;
        }
        else {
            final int currentPos = this.currentPos;
            array = insertGapCore0(this.bytecode, position, n, b, this.get().getExceptionTable(), this.codeAttr);
            length = array.length - this.bytecode.length;
            gap.position = position;
            gap.length = length;
            if (currentPos >= position) {
                this.currentPos = currentPos + length;
            }
            if (this.mark > position || (this.mark == position && b)) {
                this.mark += length;
            }
            if (this.mark2 > position || (this.mark2 == position && b)) {
                this.mark2 += length;
            }
        }
        this.codeAttr.setCode(array);
        this.bytecode = array;
        this.endPos = this.getCodeLength();
        this.updateCursors(position, length);
        return gap;
    }
    
    protected void updateCursors(final int n, final int n2) {
    }
    
    public void insert(final ExceptionTable exceptionTable, final int n) {
        this.codeAttr.getExceptionTable().add(0, exceptionTable, n);
    }
    
    public int append(final byte[] array) {
        final int codeLength = this.getCodeLength();
        final int length = array.length;
        if (length <= 0) {
            return codeLength;
        }
        this.appendGap(length);
        final byte[] bytecode = this.bytecode;
        for (int i = 0; i < length; ++i) {
            bytecode[i + codeLength] = array[i];
        }
        return codeLength;
    }
    
    public void appendGap(final int n) {
        final byte[] bytecode = this.bytecode;
        final int length = bytecode.length;
        final byte[] array = new byte[length + n];
        for (int i = 0; i < length; ++i) {
            array[i] = bytecode[i];
        }
        for (int j = length; j < length + n; ++j) {
            array[j] = 0;
        }
        this.codeAttr.setCode(array);
        this.bytecode = array;
        this.endPos = this.getCodeLength();
    }
    
    public void append(final ExceptionTable exceptionTable, final int n) {
        final ExceptionTable exceptionTable2 = this.codeAttr.getExceptionTable();
        exceptionTable2.add(exceptionTable2.size(), exceptionTable, n);
    }
    
    static int nextOpcode(final byte[] array, final int n) throws BadBytecode {
        int n2;
        try {
            n2 = (array[n] & 0xFF);
        }
        catch (IndexOutOfBoundsException ex) {
            throw new BadBytecode("invalid opcode address");
        }
        try {
            final int n3 = CodeIterator.opcodeLength[n2];
            if (n3 > 0) {
                return n + n3;
            }
            if (n2 == 196) {
                if (array[n + 1] == -124) {
                    return n + 6;
                }
                return n + 4;
            }
            else {
                final int n4 = (n & 0xFFFFFFFC) + 8;
                if (n2 == 171) {
                    return n4 + ByteArray.read32bit(array, n4) * 8 + 4;
                }
                if (n2 == 170) {
                    return n4 + (ByteArray.read32bit(array, n4 + 4) - ByteArray.read32bit(array, n4) + 1) * 4 + 8;
                }
            }
        }
        catch (IndexOutOfBoundsException ex2) {}
        throw new BadBytecode(n2);
    }
    
    static byte[] insertGapCore0(final byte[] array, final int n, final int n2, final boolean b, final ExceptionTable exceptionTable, final CodeAttribute codeAttribute) throws BadBytecode {
        if (n2 <= 0) {
            return array;
        }
        try {
            return insertGapCore1(array, n, n2, b, exceptionTable, codeAttribute);
        }
        catch (AlignmentException ex) {
            try {
                return insertGapCore1(array, n, n2 + 3 & 0xFFFFFFFC, b, exceptionTable, codeAttribute);
            }
            catch (AlignmentException ex2) {
                throw new RuntimeException("fatal error?");
            }
        }
    }
    
    private static byte[] insertGapCore1(final byte[] array, final int n, final int n2, final boolean b, final ExceptionTable exceptionTable, final CodeAttribute codeAttribute) throws BadBytecode, AlignmentException {
        final int length = array.length;
        final byte[] array2 = new byte[length + n2];
        insertGap2(array, n, n2, length, array2, b);
        exceptionTable.shiftPc(n, n2, b);
        final LineNumberAttribute lineNumberAttribute = (LineNumberAttribute)codeAttribute.getAttribute("LineNumberTable");
        if (lineNumberAttribute != null) {
            lineNumberAttribute.shiftPc(n, n2, b);
        }
        final LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTable");
        if (localVariableAttribute != null) {
            localVariableAttribute.shiftPc(n, n2, b);
        }
        final LocalVariableAttribute localVariableAttribute2 = (LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTypeTable");
        if (localVariableAttribute2 != null) {
            localVariableAttribute2.shiftPc(n, n2, b);
        }
        final StackMapTable stackMapTable = (StackMapTable)codeAttribute.getAttribute("StackMapTable");
        if (stackMapTable != null) {
            stackMapTable.shiftPc(n, n2, b);
        }
        final StackMap stackMap = (StackMap)codeAttribute.getAttribute("StackMap");
        if (stackMap != null) {
            stackMap.shiftPc(n, n2, b);
        }
        return array2;
    }
    
    private static void insertGap2(final byte[] array, final int n, final int n2, final int n3, final byte[] array2, final boolean b) throws BadBytecode, AlignmentException {
        int i = 0;
        int j = 0;
        while (i < n3) {
            if (i == n) {
                while (j < j + n2) {
                    array2[j++] = 0;
                }
            }
            final int nextOpcode = nextOpcode(array, i);
            final int n4 = array[i] & 0xFF;
            if ((153 <= n4 && n4 <= 168) || n4 == 198 || n4 == 199) {
                final int offset = newOffset(i, array[i + 1] << 8 | (array[i + 2] & 0xFF), n, n2, b);
                array2[j] = array[i];
                ByteArray.write16bit(offset, array2, j + 1);
                j += 3;
            }
            else if (n4 == 200 || n4 == 201) {
                final int offset2 = newOffset(i, ByteArray.read32bit(array, i + 1), n, n2, b);
                array2[j++] = array[i];
                ByteArray.write32bit(offset2, array2, j);
                j += 4;
            }
            else if (n4 == 170) {
                if (i != j && (n2 & 0x3) != 0x0) {
                    throw new AlignmentException();
                }
                final int n5 = (i & 0xFFFFFFFC) + 4;
                j = copyGapBytes(array2, j, array, i, n5);
                ByteArray.write32bit(newOffset(i, ByteArray.read32bit(array, n5), n, n2, b), array2, j);
                final int read32bit = ByteArray.read32bit(array, n5 + 4);
                ByteArray.write32bit(read32bit, array2, j + 4);
                final int read32bit2 = ByteArray.read32bit(array, n5 + 8);
                ByteArray.write32bit(read32bit2, array2, j + 8);
                j += 12;
                for (int k = n5 + 12; k < k + (read32bit2 - read32bit + 1) * 4; k += 4) {
                    ByteArray.write32bit(newOffset(i, ByteArray.read32bit(array, k), n, n2, b), array2, j);
                    j += 4;
                }
            }
            else if (n4 == 171) {
                if (i != j && (n2 & 0x3) != 0x0) {
                    throw new AlignmentException();
                }
                final int n6 = (i & 0xFFFFFFFC) + 4;
                j = copyGapBytes(array2, j, array, i, n6);
                ByteArray.write32bit(newOffset(i, ByteArray.read32bit(array, n6), n, n2, b), array2, j);
                final int read32bit3 = ByteArray.read32bit(array, n6 + 4);
                ByteArray.write32bit(read32bit3, array2, j + 4);
                j += 8;
                for (int l = n6 + 8; l < l + read32bit3 * 8; l += 8) {
                    ByteArray.copy32bit(array, l, array2, j);
                    ByteArray.write32bit(newOffset(i, ByteArray.read32bit(array, l + 4), n, n2, b), array2, j + 4);
                    j += 8;
                }
            }
            else {
                while (i < nextOpcode) {
                    array2[j++] = array[i++];
                }
            }
            i = nextOpcode;
        }
    }
    
    private static int copyGapBytes(final byte[] array, int n, final byte[] array2, int n2, final int n3) {
        switch (n3 - n2) {
            case 4: {
                array[n++] = array2[n2++];
            }
            case 3: {
                array[n++] = array2[n2++];
            }
            case 2: {
                array[n++] = array2[n2++];
            }
            case 1: {
                array[n++] = array2[n2++];
                break;
            }
        }
        return n;
    }
    
    private static int newOffset(final int n, int n2, final int n3, final int n4, final boolean b) {
        final int n5 = n + n2;
        if (n < n3) {
            if (n3 < n5 || (b && n3 == n5)) {
                n2 += n4;
            }
        }
        else if (n == n3) {
            if (n5 < n3) {
                n2 -= n4;
            }
        }
        else if (n5 < n3 || (!b && n3 == n5)) {
            n2 -= n4;
        }
        return n2;
    }
    
    static byte[] changeLdcToLdcW(final byte[] array, final ExceptionTable exceptionTable, final CodeAttribute codeAttribute, CodeAttribute.LdcEntry next) throws BadBytecode {
        final Pointers pointers = new Pointers(0, 0, 0, 0, exceptionTable, codeAttribute);
        final List jumpList = makeJumpList(array, array.length, pointers);
        while (next != null) {
            addLdcW(next, jumpList);
            next = next.next;
        }
        return insertGap2w(array, 0, 0, false, jumpList, pointers);
    }
    
    private static void addLdcW(final CodeAttribute.LdcEntry ldcEntry, final List list) {
        final int where = ldcEntry.where;
        final LdcW ldcW = new LdcW(where, ldcEntry.index);
        for (int size = list.size(), i = 0; i < size; ++i) {
            if (where < list.get(i).orgPos) {
                list.add(i, ldcW);
                return;
            }
        }
        list.add(ldcW);
    }
    
    private byte[] insertGapCore0w(final byte[] array, final int n, final int length, final boolean b, final ExceptionTable exceptionTable, final CodeAttribute codeAttribute, final Gap gap) throws BadBytecode {
        if (length <= 0) {
            return array;
        }
        final Pointers pointers = new Pointers(this.currentPos, this.mark, this.mark2, n, exceptionTable, codeAttribute);
        final byte[] insertGap2w = insertGap2w(array, n, length, b, makeJumpList(array, array.length, pointers), pointers);
        this.currentPos = pointers.cursor;
        this.mark = pointers.mark;
        this.mark2 = pointers.mark2;
        int mark0 = pointers.mark0;
        if (mark0 == this.currentPos && !b) {
            this.currentPos += length;
        }
        if (b) {
            mark0 -= length;
        }
        gap.position = mark0;
        gap.length = length;
        return insertGap2w;
    }
    
    private static byte[] insertGap2w(final byte[] array, final int n, final int n2, final boolean b, final List list, final Pointers pointers) throws BadBytecode {
        if (n2 > 0) {
            pointers.shiftPc(n, n2, b);
            final Iterator<Branch> iterator = list.iterator();
            while (iterator.hasNext()) {
                iterator.next().shift(n, n2, b);
            }
        }
        int n3 = 1;
        while (true) {
            if (n3 != 0) {
                n3 = 0;
                for (final Branch branch : list) {
                    if (branch.expanded()) {
                        n3 = 1;
                        final int pos = branch.pos;
                        final int deltaSize = branch.deltaSize();
                        pointers.shiftPc(pos, deltaSize, false);
                        final Iterator<Branch> iterator3 = list.iterator();
                        while (iterator3.hasNext()) {
                            iterator3.next().shift(pos, deltaSize, false);
                        }
                    }
                }
            }
            else {
                for (final Branch branch2 : list) {
                    final int gapChanged = branch2.gapChanged();
                    if (gapChanged > 0) {
                        n3 = 1;
                        final int pos2 = branch2.pos;
                        pointers.shiftPc(pos2, gapChanged, false);
                        final Iterator<Branch> iterator5 = list.iterator();
                        while (iterator5.hasNext()) {
                            iterator5.next().shift(pos2, gapChanged, false);
                        }
                    }
                }
                if (n3 == 0) {
                    break;
                }
                continue;
            }
        }
        return makeExapndedCode(array, list, n, n2);
    }
    
    private static List makeJumpList(final byte[] array, final int n, final Pointers pointers) throws BadBytecode {
        final ArrayList<Jump32> list = new ArrayList<Jump32>();
        int nextOpcode;
        for (int i = 0; i < n; i = nextOpcode) {
            nextOpcode = nextOpcode(array, i);
            final int n2 = array[i] & 0xFF;
            if ((153 <= n2 && n2 <= 168) || n2 == 198 || n2 == 199) {
                final int n3 = array[i + 1] << 8 | (array[i + 2] & 0xFF);
                Branch16 branch16;
                if (n2 == 167 || n2 == 168) {
                    branch16 = new Jump16(i, n3);
                }
                else {
                    branch16 = new If16(i, n3);
                }
                list.add((Jump32)branch16);
            }
            else if (n2 == 200 || n2 == 201) {
                list.add(new Jump32(i, ByteArray.read32bit(array, i + 1)));
            }
            else if (n2 == 170) {
                final int n4 = (i & 0xFFFFFFFC) + 4;
                final int read32bit = ByteArray.read32bit(array, n4);
                final int read32bit2 = ByteArray.read32bit(array, n4 + 4);
                final int read32bit3 = ByteArray.read32bit(array, n4 + 8);
                int n5 = n4 + 12;
                final int n6 = read32bit3 - read32bit2 + 1;
                final int[] array2 = new int[n6];
                for (int j = 0; j < n6; ++j) {
                    array2[j] = ByteArray.read32bit(array, n5);
                    n5 += 4;
                }
                list.add((Jump32)new Table(i, read32bit, read32bit2, read32bit3, array2, pointers));
            }
            else if (n2 == 171) {
                final int n7 = (i & 0xFFFFFFFC) + 4;
                final int read32bit4 = ByteArray.read32bit(array, n7);
                final int read32bit5 = ByteArray.read32bit(array, n7 + 4);
                int n8 = n7 + 8;
                final int[] array3 = new int[read32bit5];
                final int[] array4 = new int[read32bit5];
                for (int k = 0; k < read32bit5; ++k) {
                    array3[k] = ByteArray.read32bit(array, n8);
                    array4[k] = ByteArray.read32bit(array, n8 + 4);
                    n8 += 8;
                }
                list.add((Jump32)new Lookup(i, read32bit4, array3, array4, pointers));
            }
        }
        return list;
    }
    
    private static byte[] makeExapndedCode(final byte[] array, final List list, final int n, final int n2) throws BadBytecode {
        final int size = list.size();
        int n3 = array.length + n2;
        final Iterator<Branch> iterator = list.iterator();
        while (iterator.hasNext()) {
            n3 += iterator.next().deltaSize();
        }
        final byte[] array2 = new byte[n3];
        int i = 0;
        int j = 0;
        int n4 = 0;
        final int length = array.length;
        Branch branch;
        int n5;
        if (0 < size) {
            branch = list.get(0);
            n5 = branch.orgPos;
        }
        else {
            branch = null;
            n5 = length;
        }
        while (i < length) {
            if (i == n) {
                while (j < j + n2) {
                    array2[j++] = 0;
                }
            }
            if (i != n5) {
                array2[j++] = array[i++];
            }
            else {
                final int write = branch.write(i, array, j, array2);
                i += write;
                j += write + branch.deltaSize();
                if (++n4 < size) {
                    branch = list.get(n4);
                    n5 = branch.orgPos;
                }
                else {
                    branch = null;
                    n5 = length;
                }
            }
        }
        return array2;
    }
    
    static {
        opcodeLength = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 2, 3, 3, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 0, 0, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 5, 5, 3, 2, 3, 1, 1, 3, 3, 1, 1, 0, 4, 3, 3, 5, 5 };
    }
    
    static class AlignmentException extends Exception
    {
        private static final long serialVersionUID = 1L;
    }
    
    static class Lookup extends Switcher
    {
        int[] matches;
        
        Lookup(final int n, final int n2, final int[] matches, final int[] array, final Pointers pointers) {
            super(n, n2, array, pointers);
            this.matches = matches;
        }
        
        @Override
        int write2(int n, final byte[] array) {
            final int length = this.matches.length;
            ByteArray.write32bit(length, array, n);
            n += 4;
            while (0 < length) {
                ByteArray.write32bit(this.matches[0], array, n);
                ByteArray.write32bit(this.offsets[0], array, n + 4);
                n += 8;
                int n2 = 0;
                ++n2;
            }
            return 4 + 8 * length;
        }
        
        @Override
        int tableSize() {
            return 4 + 8 * this.matches.length;
        }
    }
    
    static class Pointers
    {
        int cursor;
        int mark0;
        int mark;
        int mark2;
        ExceptionTable etable;
        LineNumberAttribute line;
        LocalVariableAttribute vars;
        LocalVariableAttribute types;
        StackMapTable stack;
        StackMap stack2;
        
        Pointers(final int cursor, final int mark, final int mark2, final int mark3, final ExceptionTable etable, final CodeAttribute codeAttribute) {
            this.cursor = cursor;
            this.mark = mark;
            this.mark2 = mark2;
            this.mark0 = mark3;
            this.etable = etable;
            this.line = (LineNumberAttribute)codeAttribute.getAttribute("LineNumberTable");
            this.vars = (LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTable");
            this.types = (LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTypeTable");
            this.stack = (StackMapTable)codeAttribute.getAttribute("StackMapTable");
            this.stack2 = (StackMap)codeAttribute.getAttribute("StackMap");
        }
        
        void shiftPc(final int n, final int n2, final boolean b) throws BadBytecode {
            if (n < this.cursor || (n == this.cursor && b)) {
                this.cursor += n2;
            }
            if (n < this.mark || (n == this.mark && b)) {
                this.mark += n2;
            }
            if (n < this.mark2 || (n == this.mark2 && b)) {
                this.mark2 += n2;
            }
            if (n < this.mark0 || (n == this.mark0 && b)) {
                this.mark0 += n2;
            }
            this.etable.shiftPc(n, n2, b);
            if (this.line != null) {
                this.line.shiftPc(n, n2, b);
            }
            if (this.vars != null) {
                this.vars.shiftPc(n, n2, b);
            }
            if (this.types != null) {
                this.types.shiftPc(n, n2, b);
            }
            if (this.stack != null) {
                this.stack.shiftPc(n, n2, b);
            }
            if (this.stack2 != null) {
                this.stack2.shiftPc(n, n2, b);
            }
        }
        
        void shiftForSwitch(final int n, final int n2) throws BadBytecode {
            if (this.stack != null) {
                this.stack.shiftForSwitch(n, n2);
            }
            if (this.stack2 != null) {
                this.stack2.shiftForSwitch(n, n2);
            }
        }
    }
    
    abstract static class Switcher extends Branch
    {
        int gap;
        int defaultByte;
        int[] offsets;
        Pointers pointers;
        
        Switcher(final int n, final int defaultByte, final int[] offsets, final Pointers pointers) {
            super(n);
            this.gap = 3 - (n & 0x3);
            this.defaultByte = defaultByte;
            this.offsets = offsets;
            this.pointers = pointers;
        }
        
        @Override
        void shift(final int n, final int n2, final boolean b) {
            final int pos = this.pos;
            this.defaultByte = Branch.shiftOffset(pos, this.defaultByte, n, n2, b);
            while (0 < this.offsets.length) {
                this.offsets[0] = Branch.shiftOffset(pos, this.offsets[0], n, n2, b);
                int n3 = 0;
                ++n3;
            }
            super.shift(n, n2, b);
        }
        
        @Override
        int gapChanged() {
            final int gap = 3 - (this.pos & 0x3);
            if (gap > this.gap) {
                final int n = gap - this.gap;
                this.gap = gap;
                return n;
            }
            return 0;
        }
        
        @Override
        int deltaSize() {
            return this.gap - (3 - (this.orgPos & 0x3));
        }
        
        @Override
        int write(final int n, final byte[] array, int n2, final byte[] array2) throws BadBytecode {
            int n3 = 3 - (this.pos & 0x3);
            int n4 = this.gap - n3;
            final int n5 = 5 + (3 - (this.orgPos & 0x3)) + this.tableSize();
            if (n4 > 0) {
                this.adjustOffsets(n5, n4);
            }
            array2[n2++] = array[n];
            while (n3-- > 0) {
                array2[n2++] = 0;
            }
            ByteArray.write32bit(this.defaultByte, array2, n2);
            final int write2 = this.write2(n2 + 4, array2);
            n2 += write2 + 4;
            while (n4-- > 0) {
                array2[n2++] = 0;
            }
            return 5 + (3 - (this.orgPos & 0x3)) + write2;
        }
        
        abstract int write2(final int p0, final byte[] p1);
        
        abstract int tableSize();
        
        void adjustOffsets(final int n, final int n2) throws BadBytecode {
            this.pointers.shiftForSwitch(this.pos + n, n2);
            if (this.defaultByte == n) {
                this.defaultByte -= n2;
            }
            while (0 < this.offsets.length) {
                if (this.offsets[0] == n) {
                    final int[] offsets = this.offsets;
                    final int n3 = 0;
                    offsets[n3] -= n2;
                }
                int n4 = 0;
                ++n4;
            }
        }
    }
    
    abstract static class Branch
    {
        int pos;
        int orgPos;
        
        Branch(final int n) {
            this.orgPos = n;
            this.pos = n;
        }
        
        void shift(final int n, final int n2, final boolean b) {
            if (n < this.pos || (n == this.pos && b)) {
                this.pos += n2;
            }
        }
        
        static int shiftOffset(final int n, int n2, final int n3, final int n4, final boolean b) {
            final int n5 = n + n2;
            if (n < n3) {
                if (n3 < n5 || (b && n3 == n5)) {
                    n2 += n4;
                }
            }
            else if (n == n3) {
                if (n5 < n3 && b) {
                    n2 -= n4;
                }
                else if (n3 < n5 && !b) {
                    n2 += n4;
                }
            }
            else if (n5 < n3 || (!b && n3 == n5)) {
                n2 -= n4;
            }
            return n2;
        }
        
        boolean expanded() {
            return false;
        }
        
        int gapChanged() {
            return 0;
        }
        
        int deltaSize() {
            return 0;
        }
        
        abstract int write(final int p0, final byte[] p1, final int p2, final byte[] p3) throws BadBytecode;
    }
    
    static class Table extends Switcher
    {
        int low;
        int high;
        
        Table(final int n, final int n2, final int low, final int high, final int[] array, final Pointers pointers) {
            super(n, n2, array, pointers);
            this.low = low;
            this.high = high;
        }
        
        @Override
        int write2(int n, final byte[] array) {
            ByteArray.write32bit(this.low, array, n);
            ByteArray.write32bit(this.high, array, n + 4);
            final int length = this.offsets.length;
            n += 8;
            while (0 < length) {
                ByteArray.write32bit(this.offsets[0], array, n);
                n += 4;
                int n2 = 0;
                ++n2;
            }
            return 8 + 4 * length;
        }
        
        @Override
        int tableSize() {
            return 8 + 4 * this.offsets.length;
        }
    }
    
    static class Jump32 extends Branch
    {
        int offset;
        
        Jump32(final int n, final int offset) {
            super(n);
            this.offset = offset;
        }
        
        @Override
        void shift(final int n, final int n2, final boolean b) {
            this.offset = Branch.shiftOffset(this.pos, this.offset, n, n2, b);
            super.shift(n, n2, b);
        }
        
        @Override
        int write(final int n, final byte[] array, final int n2, final byte[] array2) {
            array2[n2] = array[n];
            ByteArray.write32bit(this.offset, array2, n2 + 1);
            return 5;
        }
    }
    
    static class If16 extends Branch16
    {
        If16(final int n, final int n2) {
            super(n, n2);
        }
        
        @Override
        int deltaSize() {
            return (this.state == 2) ? 5 : 0;
        }
        
        @Override
        void write32(final int n, final byte[] array, final int n2, final byte[] array2) {
            array2[n2] = (byte)this.opcode(array[n] & 0xFF);
            array2[n2 + 1] = 0;
            array2[n2 + 2] = 8;
            array2[n2 + 3] = -56;
            ByteArray.write32bit(this.offset - 3, array2, n2 + 4);
        }
        
        int opcode(final int n) {
            if (n == 198) {
                return 199;
            }
            if (n == 199) {
                return 198;
            }
            if ((n - 153 & 0x1) == 0x0) {
                return n + 1;
            }
            return n - 1;
        }
    }
    
    abstract static class Branch16 extends Branch
    {
        int offset;
        int state;
        static final int BIT16 = 0;
        static final int EXPAND = 1;
        static final int BIT32 = 2;
        
        Branch16(final int n, final int offset) {
            super(n);
            this.offset = offset;
            this.state = 0;
        }
        
        @Override
        void shift(final int n, final int n2, final boolean b) {
            this.offset = Branch.shiftOffset(this.pos, this.offset, n, n2, b);
            super.shift(n, n2, b);
            if (this.state == 0 && (this.offset < -32768 || 32767 < this.offset)) {
                this.state = 1;
            }
        }
        
        @Override
        boolean expanded() {
            if (this.state == 1) {
                this.state = 2;
                return true;
            }
            return false;
        }
        
        @Override
        abstract int deltaSize();
        
        abstract void write32(final int p0, final byte[] p1, final int p2, final byte[] p3);
        
        @Override
        int write(final int n, final byte[] array, final int n2, final byte[] array2) {
            if (this.state == 2) {
                this.write32(n, array, n2, array2);
            }
            else {
                array2[n2] = array[n];
                ByteArray.write16bit(this.offset, array2, n2 + 1);
            }
            return 3;
        }
    }
    
    static class Jump16 extends Branch16
    {
        Jump16(final int n, final int n2) {
            super(n, n2);
        }
        
        @Override
        int deltaSize() {
            return (this.state == 2) ? 2 : 0;
        }
        
        @Override
        void write32(final int n, final byte[] array, final int n2, final byte[] array2) {
            array2[n2] = (byte)(((array[n] & 0xFF) == 0xA7) ? 200 : 201);
            ByteArray.write32bit(this.offset, array2, n2 + 1);
        }
    }
    
    static class LdcW extends Branch
    {
        int index;
        boolean state;
        
        LdcW(final int n, final int index) {
            super(n);
            this.index = index;
            this.state = true;
        }
        
        @Override
        boolean expanded() {
            if (this.state) {
                this.state = false;
                return true;
            }
            return false;
        }
        
        @Override
        int deltaSize() {
            return 1;
        }
        
        @Override
        int write(final int n, final byte[] array, final int n2, final byte[] array2) {
            array2[n2] = 19;
            ByteArray.write16bit(this.index, array2, n2 + 1);
            return 2;
        }
    }
    
    public static class Gap
    {
        public int position;
        public int length;
    }
}
