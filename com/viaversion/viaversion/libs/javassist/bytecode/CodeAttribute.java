package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

public class CodeAttribute extends AttributeInfo implements Opcode
{
    public static final String tag = "Code";
    private int maxStack;
    private int maxLocals;
    private ExceptionTable exceptions;
    private List attributes;
    
    public CodeAttribute(final ConstPool constPool, final int maxStack, final int maxLocals, final byte[] info, final ExceptionTable exceptions) {
        super(constPool, "Code");
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.info = info;
        this.exceptions = exceptions;
        this.attributes = new ArrayList();
    }
    
    private CodeAttribute(final ConstPool constPool, final CodeAttribute codeAttribute, final Map map) throws BadBytecode {
        super(constPool, "Code");
        this.maxStack = codeAttribute.getMaxStack();
        this.maxLocals = codeAttribute.getMaxLocals();
        this.exceptions = codeAttribute.getExceptionTable().copy(constPool, map);
        this.attributes = new ArrayList();
        final List attributes = codeAttribute.getAttributes();
        while (0 < attributes.size()) {
            this.attributes.add(attributes.get(0).copy(constPool, map));
            int n = 0;
            ++n;
        }
        this.info = codeAttribute.copyCode(constPool, map, this.exceptions, this);
    }
    
    CodeAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, (byte[])null);
        dataInputStream.readInt();
        this.maxStack = dataInputStream.readUnsignedShort();
        this.maxLocals = dataInputStream.readUnsignedShort();
        dataInputStream.readFully(this.info = new byte[dataInputStream.readInt()]);
        this.exceptions = new ExceptionTable(constPool, dataInputStream);
        this.attributes = new ArrayList();
        while (0 < dataInputStream.readUnsignedShort()) {
            this.attributes.add(AttributeInfo.read(constPool, dataInputStream));
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) throws RuntimeCopyException {
        return new CodeAttribute(constPool, this, map);
    }
    
    @Override
    public int length() {
        return 18 + this.info.length + this.exceptions.size() * 8 + AttributeInfo.getLength(this.attributes);
    }
    
    @Override
    void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.name);
        dataOutputStream.writeInt(this.length() - 6);
        dataOutputStream.writeShort(this.maxStack);
        dataOutputStream.writeShort(this.maxLocals);
        dataOutputStream.writeInt(this.info.length);
        dataOutputStream.write(this.info);
        this.exceptions.write(dataOutputStream);
        dataOutputStream.writeShort(this.attributes.size());
        AttributeInfo.writeAll(this.attributes, dataOutputStream);
    }
    
    @Override
    public byte[] get() {
        throw new UnsupportedOperationException("CodeAttribute.get()");
    }
    
    @Override
    public void set(final byte[] array) {
        throw new UnsupportedOperationException("CodeAttribute.set()");
    }
    
    @Override
    void renameClass(final String s, final String s2) {
        AttributeInfo.renameClass(this.attributes, s, s2);
    }
    
    @Override
    void renameClass(final Map map) {
        AttributeInfo.renameClass(this.attributes, map);
    }
    
    @Override
    void getRefClasses(final Map map) {
        AttributeInfo.getRefClasses(this.attributes, map);
    }
    
    public String getDeclaringClass() {
        return this.getConstPool().getClassName();
    }
    
    public int getMaxStack() {
        return this.maxStack;
    }
    
    public void setMaxStack(final int maxStack) {
        this.maxStack = maxStack;
    }
    
    public int computeMaxStack() throws BadBytecode {
        return this.maxStack = new CodeAnalyzer(this).computeMaxStack();
    }
    
    public int getMaxLocals() {
        return this.maxLocals;
    }
    
    public void setMaxLocals(final int maxLocals) {
        this.maxLocals = maxLocals;
    }
    
    public int getCodeLength() {
        return this.info.length;
    }
    
    public byte[] getCode() {
        return this.info;
    }
    
    void setCode(final byte[] array) {
        super.set(array);
    }
    
    public CodeIterator iterator() {
        return new CodeIterator(this);
    }
    
    public ExceptionTable getExceptionTable() {
        return this.exceptions;
    }
    
    public List getAttributes() {
        return this.attributes;
    }
    
    public AttributeInfo getAttribute(final String s) {
        return AttributeInfo.lookup(this.attributes, s);
    }
    
    public void setAttribute(final StackMapTable stackMapTable) {
        AttributeInfo.remove(this.attributes, "StackMapTable");
        if (stackMapTable != null) {
            this.attributes.add(stackMapTable);
        }
    }
    
    public void setAttribute(final StackMap stackMap) {
        AttributeInfo.remove(this.attributes, "StackMap");
        if (stackMap != null) {
            this.attributes.add(stackMap);
        }
    }
    
    private byte[] copyCode(final ConstPool constPool, final Map map, final ExceptionTable exceptionTable, final CodeAttribute codeAttribute) throws BadBytecode {
        final int codeLength = this.getCodeLength();
        final byte[] info = new byte[codeLength];
        codeAttribute.info = info;
        return LdcEntry.doit(info, copyCode(this.info, 0, codeLength, this.getConstPool(), info, constPool, map), exceptionTable, codeAttribute);
    }
    
    private static LdcEntry copyCode(final byte[] array, final int n, final int n2, final ConstPool constPool, final byte[] array2, final ConstPool constPool2, final Map map) throws BadBytecode {
        LdcEntry next = null;
        int nextOpcode;
        for (int i = n; i < n2; i = nextOpcode) {
            nextOpcode = CodeIterator.nextOpcode(array, i);
            switch ((array2[i] = array[i]) & 0xFF) {
                case 19:
                case 20:
                case 178:
                case 179:
                case 180:
                case 181:
                case 182:
                case 183:
                case 184:
                case 187:
                case 189:
                case 192:
                case 193: {
                    copyConstPoolInfo(i + 1, array, constPool, array2, constPool2, map);
                    break;
                }
                case 18: {
                    final int copy = constPool.copy(array[i + 1] & 0xFF, constPool2, map);
                    if (copy < 256) {
                        array2[i + 1] = (byte)copy;
                        break;
                    }
                    array2[i + 1] = (array2[i] = 0);
                    final LdcEntry ldcEntry = new LdcEntry();
                    ldcEntry.where = i;
                    ldcEntry.index = copy;
                    ldcEntry.next = next;
                    next = ldcEntry;
                    break;
                }
                case 185: {
                    copyConstPoolInfo(i + 1, array, constPool, array2, constPool2, map);
                    array2[i + 3] = array[i + 3];
                    array2[i + 4] = array[i + 4];
                    break;
                }
                case 186: {
                    copyConstPoolInfo(i + 1, array, constPool, array2, constPool2, map);
                    array2[i + 4] = (array2[i + 3] = 0);
                    break;
                }
                case 197: {
                    copyConstPoolInfo(i + 1, array, constPool, array2, constPool2, map);
                    array2[i + 3] = array[i + 3];
                    break;
                }
                default: {
                    while (++i < nextOpcode) {
                        array2[i] = array[i];
                    }
                    break;
                }
            }
        }
        return next;
    }
    
    private static void copyConstPoolInfo(final int n, final byte[] array, final ConstPool constPool, final byte[] array2, final ConstPool constPool2, final Map map) {
        final int copy = constPool.copy((array[n] & 0xFF) << 8 | (array[n + 1] & 0xFF), constPool2, map);
        array2[n] = (byte)(copy >> 8);
        array2[n + 1] = (byte)copy;
    }
    
    public void insertLocalVar(final int n, final int n2) throws BadBytecode {
        final CodeIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            shiftIndex(iterator, n, n2);
        }
        this.setMaxLocals(this.getMaxLocals() + n2);
    }
    
    private static void shiftIndex(final CodeIterator codeIterator, final int n, final int n2) throws BadBytecode {
        final int next = codeIterator.next();
        final int byte1 = codeIterator.byteAt(next);
        if (byte1 < 21) {
            return;
        }
        if (byte1 < 79) {
            if (byte1 < 26) {
                shiftIndex8(codeIterator, next, byte1, n, n2);
            }
            else if (byte1 < 46) {
                shiftIndex0(codeIterator, next, byte1, n, n2, 26, 21);
            }
            else {
                if (byte1 < 54) {
                    return;
                }
                if (byte1 < 59) {
                    shiftIndex8(codeIterator, next, byte1, n, n2);
                }
                else {
                    shiftIndex0(codeIterator, next, byte1, n, n2, 59, 54);
                }
            }
        }
        else if (byte1 == 132) {
            final int byte2 = codeIterator.byteAt(next + 1);
            if (byte2 < n) {
                return;
            }
            final int n3 = byte2 + n2;
            if (n3 < 256) {
                codeIterator.writeByte(n3, next + 1);
            }
            else {
                final byte b = (byte)codeIterator.byteAt(next + 2);
                final int insertExGap = codeIterator.insertExGap(3);
                codeIterator.writeByte(196, insertExGap - 3);
                codeIterator.writeByte(132, insertExGap - 2);
                codeIterator.write16bit(n3, insertExGap - 1);
                codeIterator.write16bit(b, insertExGap + 1);
            }
        }
        else if (byte1 == 169) {
            shiftIndex8(codeIterator, next, byte1, n, n2);
        }
        else if (byte1 == 196) {
            final int u16bit = codeIterator.u16bitAt(next + 2);
            if (u16bit < n) {
                return;
            }
            codeIterator.write16bit(u16bit + n2, next + 2);
        }
    }
    
    private static void shiftIndex8(final CodeIterator codeIterator, final int n, final int n2, final int n3, final int n4) throws BadBytecode {
        final int byte1 = codeIterator.byteAt(n + 1);
        if (byte1 < n3) {
            return;
        }
        final int n5 = byte1 + n4;
        if (n5 < 256) {
            codeIterator.writeByte(n5, n + 1);
        }
        else {
            final int insertExGap = codeIterator.insertExGap(2);
            codeIterator.writeByte(196, insertExGap - 2);
            codeIterator.writeByte(n2, insertExGap - 1);
            codeIterator.write16bit(n5, insertExGap);
        }
    }
    
    private static void shiftIndex0(final CodeIterator codeIterator, final int n, int n2, final int n3, final int n4, final int n5, final int n6) throws BadBytecode {
        final int n7 = (n2 - n5) % 4;
        if (n7 < n3) {
            return;
        }
        final int n8 = n7 + n4;
        if (n8 < 4) {
            codeIterator.writeByte(n2 + n4, n);
        }
        else {
            n2 = (n2 - n5) / 4 + n6;
            if (n8 < 256) {
                final int insertExGap = codeIterator.insertExGap(1);
                codeIterator.writeByte(n2, insertExGap - 1);
                codeIterator.writeByte(n8, insertExGap);
            }
            else {
                final int insertExGap2 = codeIterator.insertExGap(3);
                codeIterator.writeByte(196, insertExGap2 - 1);
                codeIterator.writeByte(n2, insertExGap2);
                codeIterator.write16bit(n8, insertExGap2 + 1);
            }
        }
    }
    
    static class LdcEntry
    {
        LdcEntry next;
        int where;
        int index;
        
        static byte[] doit(byte[] changeLdcToLdcW, final LdcEntry ldcEntry, final ExceptionTable exceptionTable, final CodeAttribute codeAttribute) throws BadBytecode {
            if (ldcEntry != null) {
                changeLdcToLdcW = CodeIterator.changeLdcToLdcW(changeLdcToLdcW, exceptionTable, codeAttribute, ldcEntry);
            }
            return changeLdcToLdcW;
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
