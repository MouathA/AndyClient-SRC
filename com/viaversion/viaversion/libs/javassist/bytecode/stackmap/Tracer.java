package com.viaversion.viaversion.libs.javassist.bytecode.stackmap;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public abstract class Tracer implements TypeTag
{
    protected ClassPool classPool;
    protected ConstPool cpool;
    protected String returnType;
    protected int stackTop;
    protected TypeData[] stackTypes;
    protected TypeData[] localsTypes;
    
    public Tracer(final ClassPool classPool, final ConstPool cpool, final int n, final int n2, final String returnType) {
        this.classPool = classPool;
        this.cpool = cpool;
        this.returnType = returnType;
        this.stackTop = 0;
        this.stackTypes = TypeData.make(n);
        this.localsTypes = TypeData.make(n2);
    }
    
    public Tracer(final Tracer tracer) {
        this.classPool = tracer.classPool;
        this.cpool = tracer.cpool;
        this.returnType = tracer.returnType;
        this.stackTop = tracer.stackTop;
        this.stackTypes = TypeData.make(tracer.stackTypes.length);
        this.localsTypes = TypeData.make(tracer.localsTypes.length);
    }
    
    protected int doOpcode(final int n, final byte[] array) throws BadBytecode {
        final int n2 = array[n] & 0xFF;
        if (n2 < 54) {
            return this.doOpcode0_53(n, array, n2);
        }
        if (n2 < 96) {
            return this.doOpcode54_95(n, array, n2);
        }
        if (n2 < 148) {
            return this.doOpcode96_147(n, array, n2);
        }
        return this.doOpcode148_201(n, array, n2);
    }
    
    protected void visitBranch(final int n, final byte[] array, final int n2) throws BadBytecode {
    }
    
    protected void visitGoto(final int n, final byte[] array, final int n2) throws BadBytecode {
    }
    
    protected void visitReturn(final int n, final byte[] array) throws BadBytecode {
    }
    
    protected void visitThrow(final int n, final byte[] array) throws BadBytecode {
    }
    
    protected void visitTableSwitch(final int n, final byte[] array, final int n2, final int n3, final int n4) throws BadBytecode {
    }
    
    protected void visitLookupSwitch(final int n, final byte[] array, final int n2, final int n3, final int n4) throws BadBytecode {
    }
    
    protected void visitJSR(final int n, final byte[] array) throws BadBytecode {
    }
    
    protected void visitRET(final int n, final byte[] array) throws BadBytecode {
    }
    
    private int doOpcode0_53(final int n, final byte[] array, final int n2) throws BadBytecode {
        final TypeData[] stackTypes = this.stackTypes;
        switch (n2) {
            case 0: {
                break;
            }
            case 1: {
                stackTypes[this.stackTop++] = new TypeData.NullType();
                break;
            }
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8: {
                stackTypes[this.stackTop++] = Tracer.INTEGER;
                break;
            }
            case 9:
            case 10: {
                stackTypes[this.stackTop++] = Tracer.LONG;
                stackTypes[this.stackTop++] = Tracer.TOP;
                break;
            }
            case 11:
            case 12:
            case 13: {
                stackTypes[this.stackTop++] = Tracer.FLOAT;
                break;
            }
            case 14:
            case 15: {
                stackTypes[this.stackTop++] = Tracer.DOUBLE;
                stackTypes[this.stackTop++] = Tracer.TOP;
                break;
            }
            case 16:
            case 17: {
                stackTypes[this.stackTop++] = Tracer.INTEGER;
                return (n2 == 17) ? 3 : 2;
            }
            case 18: {
                this.doLDC(array[n + 1] & 0xFF);
                return 2;
            }
            case 19:
            case 20: {
                this.doLDC(ByteArray.readU16bit(array, n + 1));
                return 3;
            }
            case 21: {
                return this.doXLOAD(Tracer.INTEGER, array, n);
            }
            case 22: {
                return this.doXLOAD(Tracer.LONG, array, n);
            }
            case 23: {
                return this.doXLOAD(Tracer.FLOAT, array, n);
            }
            case 24: {
                return this.doXLOAD(Tracer.DOUBLE, array, n);
            }
            case 25: {
                return this.doALOAD(array[n + 1] & 0xFF);
            }
            case 26:
            case 27:
            case 28:
            case 29: {
                stackTypes[this.stackTop++] = Tracer.INTEGER;
                break;
            }
            case 30:
            case 31:
            case 32:
            case 33: {
                stackTypes[this.stackTop++] = Tracer.LONG;
                stackTypes[this.stackTop++] = Tracer.TOP;
                break;
            }
            case 34:
            case 35:
            case 36:
            case 37: {
                stackTypes[this.stackTop++] = Tracer.FLOAT;
                break;
            }
            case 38:
            case 39:
            case 40:
            case 41: {
                stackTypes[this.stackTop++] = Tracer.DOUBLE;
                stackTypes[this.stackTop++] = Tracer.TOP;
                break;
            }
            case 42:
            case 43:
            case 44:
            case 45: {
                stackTypes[this.stackTop++] = this.localsTypes[n2 - 42];
                break;
            }
            case 46: {
                final TypeData[] array2 = stackTypes;
                final int stackTop = this.stackTop - 1;
                this.stackTop = stackTop;
                array2[stackTop - 1] = Tracer.INTEGER;
                break;
            }
            case 47: {
                stackTypes[this.stackTop - 2] = Tracer.LONG;
                stackTypes[this.stackTop - 1] = Tracer.TOP;
                break;
            }
            case 48: {
                final TypeData[] array3 = stackTypes;
                final int stackTop2 = this.stackTop - 1;
                this.stackTop = stackTop2;
                array3[stackTop2 - 1] = Tracer.FLOAT;
                break;
            }
            case 49: {
                stackTypes[this.stackTop - 2] = Tracer.DOUBLE;
                stackTypes[this.stackTop - 1] = Tracer.TOP;
                break;
            }
            case 50: {
                final int stackTop3 = this.stackTop - 1;
                this.stackTop = stackTop3;
                final int n3 = stackTop3 - 1;
                stackTypes[n3] = TypeData.ArrayElement.make(stackTypes[n3]);
                break;
            }
            case 51:
            case 52:
            case 53: {
                final TypeData[] array4 = stackTypes;
                final int stackTop4 = this.stackTop - 1;
                this.stackTop = stackTop4;
                array4[stackTop4 - 1] = Tracer.INTEGER;
                break;
            }
            default: {
                throw new RuntimeException("fatal");
            }
        }
        return 1;
    }
    
    private void doLDC(final int n) {
        final TypeData[] stackTypes = this.stackTypes;
        final int tag = this.cpool.getTag(n);
        if (tag == 8) {
            stackTypes[this.stackTop++] = new TypeData.ClassName("java.lang.String");
        }
        else if (tag == 3) {
            stackTypes[this.stackTop++] = Tracer.INTEGER;
        }
        else if (tag == 4) {
            stackTypes[this.stackTop++] = Tracer.FLOAT;
        }
        else if (tag == 5) {
            stackTypes[this.stackTop++] = Tracer.LONG;
            stackTypes[this.stackTop++] = Tracer.TOP;
        }
        else if (tag == 6) {
            stackTypes[this.stackTop++] = Tracer.DOUBLE;
            stackTypes[this.stackTop++] = Tracer.TOP;
        }
        else if (tag == 7) {
            stackTypes[this.stackTop++] = new TypeData.ClassName("java.lang.Class");
        }
        else {
            if (tag != 17) {
                throw new RuntimeException("bad LDC: " + tag);
            }
            this.pushMemberType(this.cpool.getDynamicType(n));
        }
    }
    
    private int doXLOAD(final TypeData typeData, final byte[] array, final int n) {
        return this.doXLOAD(array[n + 1] & 0xFF, typeData);
    }
    
    private int doXLOAD(final int n, final TypeData typeData) {
        this.stackTypes[this.stackTop++] = typeData;
        if (typeData.is2WordType()) {
            this.stackTypes[this.stackTop++] = Tracer.TOP;
        }
        return 2;
    }
    
    private int doALOAD(final int n) {
        this.stackTypes[this.stackTop++] = this.localsTypes[n];
        return 2;
    }
    
    private int doOpcode54_95(final int n, final byte[] array, final int n2) throws BadBytecode {
        switch (n2) {
            case 54: {
                return this.doXSTORE(n, array, Tracer.INTEGER);
            }
            case 55: {
                return this.doXSTORE(n, array, Tracer.LONG);
            }
            case 56: {
                return this.doXSTORE(n, array, Tracer.FLOAT);
            }
            case 57: {
                return this.doXSTORE(n, array, Tracer.DOUBLE);
            }
            case 58: {
                return this.doASTORE(array[n + 1] & 0xFF);
            }
            case 59:
            case 60:
            case 61:
            case 62: {
                this.localsTypes[n2 - 59] = Tracer.INTEGER;
                --this.stackTop;
                break;
            }
            case 63:
            case 64:
            case 65:
            case 66: {
                final int n3 = n2 - 63;
                this.localsTypes[n3] = Tracer.LONG;
                this.localsTypes[n3 + 1] = Tracer.TOP;
                this.stackTop -= 2;
                break;
            }
            case 67:
            case 68:
            case 69:
            case 70: {
                this.localsTypes[n2 - 67] = Tracer.FLOAT;
                --this.stackTop;
                break;
            }
            case 71:
            case 72:
            case 73:
            case 74: {
                final int n4 = n2 - 71;
                this.localsTypes[n4] = Tracer.DOUBLE;
                this.localsTypes[n4 + 1] = Tracer.TOP;
                this.stackTop -= 2;
                break;
            }
            case 75:
            case 76:
            case 77:
            case 78: {
                this.doASTORE(n2 - 75);
                break;
            }
            case 79:
            case 80:
            case 81:
            case 82: {
                this.stackTop -= ((n2 == 80 || n2 == 82) ? 4 : 3);
                break;
            }
            case 83: {
                TypeData.aastore(this.stackTypes[this.stackTop - 3], this.stackTypes[this.stackTop - 1], this.classPool);
                this.stackTop -= 3;
                break;
            }
            case 84:
            case 85:
            case 86: {
                this.stackTop -= 3;
                break;
            }
            case 87: {
                --this.stackTop;
                break;
            }
            case 88: {
                this.stackTop -= 2;
                break;
            }
            case 89: {
                final int stackTop = this.stackTop;
                this.stackTypes[stackTop] = this.stackTypes[stackTop - 1];
                this.stackTop = stackTop + 1;
                break;
            }
            case 90:
            case 91: {
                final int n5 = n2 - 90 + 2;
                this.doDUP_XX(1, n5);
                final int stackTop2 = this.stackTop;
                this.stackTypes[stackTop2 - n5] = this.stackTypes[stackTop2];
                this.stackTop = stackTop2 + 1;
                break;
            }
            case 92: {
                this.doDUP_XX(2, 2);
                this.stackTop += 2;
                break;
            }
            case 93:
            case 94: {
                final int n6 = n2 - 93 + 3;
                this.doDUP_XX(2, n6);
                final int stackTop3 = this.stackTop;
                this.stackTypes[stackTop3 - n6] = this.stackTypes[stackTop3];
                this.stackTypes[stackTop3 - n6 + 1] = this.stackTypes[stackTop3 + 1];
                this.stackTop = stackTop3 + 2;
                break;
            }
            case 95: {
                final int n7 = this.stackTop - 1;
                final TypeData typeData = this.stackTypes[n7];
                this.stackTypes[n7] = this.stackTypes[n7 - 1];
                this.stackTypes[n7 - 1] = typeData;
                break;
            }
            default: {
                throw new RuntimeException("fatal");
            }
        }
        return 1;
    }
    
    private int doXSTORE(final int n, final byte[] array, final TypeData typeData) {
        return this.doXSTORE(array[n + 1] & 0xFF, typeData);
    }
    
    private int doXSTORE(final int n, final TypeData typeData) {
        --this.stackTop;
        this.localsTypes[n] = typeData;
        if (typeData.is2WordType()) {
            --this.stackTop;
            this.localsTypes[n + 1] = Tracer.TOP;
        }
        return 2;
    }
    
    private int doASTORE(final int n) {
        --this.stackTop;
        this.localsTypes[n] = this.stackTypes[this.stackTop];
        return 2;
    }
    
    private void doDUP_XX(final int n, final int n2) {
        final TypeData[] stackTypes = this.stackTypes;
        for (int i = this.stackTop - 1; i > i - n2; --i) {
            stackTypes[i + n] = stackTypes[i];
        }
    }
    
    private int doOpcode96_147(final int n, final byte[] array, final int n2) {
        if (n2 <= 131) {
            this.stackTop += Opcode.STACK_GROW[n2];
            return 1;
        }
        switch (n2) {
            case 132: {
                return 3;
            }
            case 133: {
                this.stackTypes[this.stackTop - 1] = Tracer.LONG;
                this.stackTypes[this.stackTop] = Tracer.TOP;
                ++this.stackTop;
                break;
            }
            case 134: {
                this.stackTypes[this.stackTop - 1] = Tracer.FLOAT;
                break;
            }
            case 135: {
                this.stackTypes[this.stackTop - 1] = Tracer.DOUBLE;
                this.stackTypes[this.stackTop] = Tracer.TOP;
                ++this.stackTop;
                break;
            }
            case 136: {
                final TypeData[] stackTypes = this.stackTypes;
                final int stackTop = this.stackTop - 1;
                this.stackTop = stackTop;
                stackTypes[stackTop - 1] = Tracer.INTEGER;
                break;
            }
            case 137: {
                final TypeData[] stackTypes2 = this.stackTypes;
                final int stackTop2 = this.stackTop - 1;
                this.stackTop = stackTop2;
                stackTypes2[stackTop2 - 1] = Tracer.FLOAT;
                break;
            }
            case 138: {
                this.stackTypes[this.stackTop - 2] = Tracer.DOUBLE;
                break;
            }
            case 139: {
                this.stackTypes[this.stackTop - 1] = Tracer.INTEGER;
                break;
            }
            case 140: {
                this.stackTypes[this.stackTop - 1] = Tracer.LONG;
                this.stackTypes[this.stackTop] = Tracer.TOP;
                ++this.stackTop;
                break;
            }
            case 141: {
                this.stackTypes[this.stackTop - 1] = Tracer.DOUBLE;
                this.stackTypes[this.stackTop] = Tracer.TOP;
                ++this.stackTop;
                break;
            }
            case 142: {
                final TypeData[] stackTypes3 = this.stackTypes;
                final int stackTop3 = this.stackTop - 1;
                this.stackTop = stackTop3;
                stackTypes3[stackTop3 - 1] = Tracer.INTEGER;
                break;
            }
            case 143: {
                this.stackTypes[this.stackTop - 2] = Tracer.LONG;
                break;
            }
            case 144: {
                final TypeData[] stackTypes4 = this.stackTypes;
                final int stackTop4 = this.stackTop - 1;
                this.stackTop = stackTop4;
                stackTypes4[stackTop4 - 1] = Tracer.FLOAT;
                break;
            }
            case 145:
            case 146:
            case 147: {
                break;
            }
            default: {
                throw new RuntimeException("fatal");
            }
        }
        return 1;
    }
    
    private int doOpcode148_201(final int n, final byte[] array, final int n2) throws BadBytecode {
        switch (n2) {
            case 148: {
                this.stackTypes[this.stackTop - 4] = Tracer.INTEGER;
                this.stackTop -= 3;
                break;
            }
            case 149:
            case 150: {
                final TypeData[] stackTypes = this.stackTypes;
                final int stackTop = this.stackTop - 1;
                this.stackTop = stackTop;
                stackTypes[stackTop - 1] = Tracer.INTEGER;
                break;
            }
            case 151:
            case 152: {
                this.stackTypes[this.stackTop - 4] = Tracer.INTEGER;
                this.stackTop -= 3;
                break;
            }
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158: {
                --this.stackTop;
                this.visitBranch(n, array, ByteArray.readS16bit(array, n + 1));
                return 3;
            }
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166: {
                this.stackTop -= 2;
                this.visitBranch(n, array, ByteArray.readS16bit(array, n + 1));
                return 3;
            }
            case 167: {
                this.visitGoto(n, array, ByteArray.readS16bit(array, n + 1));
                return 3;
            }
            case 168: {
                this.visitJSR(n, array);
                return 3;
            }
            case 169: {
                this.visitRET(n, array);
                return 2;
            }
            case 170: {
                --this.stackTop;
                final int n3 = (n & 0xFFFFFFFC) + 8;
                final int n4 = ByteArray.read32bit(array, n3 + 4) - ByteArray.read32bit(array, n3) + 1;
                this.visitTableSwitch(n, array, n4, n3 + 8, ByteArray.read32bit(array, n3 - 4));
                return n4 * 4 + 16 - (n & 0x3);
            }
            case 171: {
                --this.stackTop;
                final int n5 = (n & 0xFFFFFFFC) + 8;
                final int read32bit = ByteArray.read32bit(array, n5);
                this.visitLookupSwitch(n, array, read32bit, n5 + 4, ByteArray.read32bit(array, n5 - 4));
                return read32bit * 8 + 12 - (n & 0x3);
            }
            case 172: {
                --this.stackTop;
                this.visitReturn(n, array);
                break;
            }
            case 173: {
                this.stackTop -= 2;
                this.visitReturn(n, array);
                break;
            }
            case 174: {
                --this.stackTop;
                this.visitReturn(n, array);
                break;
            }
            case 175: {
                this.stackTop -= 2;
                this.visitReturn(n, array);
                break;
            }
            case 176: {
                final TypeData[] stackTypes2 = this.stackTypes;
                final int stackTop2 = this.stackTop - 1;
                this.stackTop = stackTop2;
                stackTypes2[stackTop2].setType(this.returnType, this.classPool);
                this.visitReturn(n, array);
                break;
            }
            case 177: {
                this.visitReturn(n, array);
                break;
            }
            case 178: {
                return this.doGetField(n, array, false);
            }
            case 179: {
                return this.doPutField(n, array, false);
            }
            case 180: {
                return this.doGetField(n, array, true);
            }
            case 181: {
                return this.doPutField(n, array, true);
            }
            case 182:
            case 183: {
                return this.doInvokeMethod(n, array, true);
            }
            case 184: {
                return this.doInvokeMethod(n, array, false);
            }
            case 185: {
                return this.doInvokeIntfMethod(n, array);
            }
            case 186: {
                return this.doInvokeDynamic(n, array);
            }
            case 187: {
                this.stackTypes[this.stackTop++] = new TypeData.UninitData(n, this.cpool.getClassInfo(ByteArray.readU16bit(array, n + 1)));
                return 3;
            }
            case 188: {
                return this.doNEWARRAY(n, array);
            }
            case 189: {
                final String replace = this.cpool.getClassInfo(ByteArray.readU16bit(array, n + 1)).replace('.', '/');
                String s;
                if (replace.charAt(0) == '[') {
                    s = "[" + replace;
                }
                else {
                    s = "[L" + replace + ";";
                }
                this.stackTypes[this.stackTop - 1] = new TypeData.ClassName(s);
                return 3;
            }
            case 190: {
                this.stackTypes[this.stackTop - 1].setType("[Ljava.lang.Object;", this.classPool);
                this.stackTypes[this.stackTop - 1] = Tracer.INTEGER;
                break;
            }
            case 191: {
                final TypeData[] stackTypes3 = this.stackTypes;
                final int stackTop3 = this.stackTop - 1;
                this.stackTop = stackTop3;
                stackTypes3[stackTop3].setType("java.lang.Throwable", this.classPool);
                this.visitThrow(n, array);
                break;
            }
            case 192: {
                String s2 = this.cpool.getClassInfo(ByteArray.readU16bit(array, n + 1));
                if (s2.charAt(0) == '[') {
                    s2 = s2.replace('.', '/');
                }
                this.stackTypes[this.stackTop - 1] = new TypeData.ClassName(s2);
                return 3;
            }
            case 193: {
                this.stackTypes[this.stackTop - 1] = Tracer.INTEGER;
                return 3;
            }
            case 194:
            case 195: {
                --this.stackTop;
                break;
            }
            case 196: {
                return this.doWIDE(n, array);
            }
            case 197: {
                return this.doMultiANewArray(n, array);
            }
            case 198:
            case 199: {
                --this.stackTop;
                this.visitBranch(n, array, ByteArray.readS16bit(array, n + 1));
                return 3;
            }
            case 200: {
                this.visitGoto(n, array, ByteArray.read32bit(array, n + 1));
                return 5;
            }
            case 201: {
                this.visitJSR(n, array);
                return 5;
            }
        }
        return 1;
    }
    
    private int doWIDE(final int n, final byte[] array) throws BadBytecode {
        final int n2 = array[n + 1] & 0xFF;
        switch (n2) {
            case 21: {
                this.doWIDE_XLOAD(n, array, Tracer.INTEGER);
                break;
            }
            case 22: {
                this.doWIDE_XLOAD(n, array, Tracer.LONG);
                break;
            }
            case 23: {
                this.doWIDE_XLOAD(n, array, Tracer.FLOAT);
                break;
            }
            case 24: {
                this.doWIDE_XLOAD(n, array, Tracer.DOUBLE);
                break;
            }
            case 25: {
                this.doALOAD(ByteArray.readU16bit(array, n + 2));
                break;
            }
            case 54: {
                this.doWIDE_STORE(n, array, Tracer.INTEGER);
                break;
            }
            case 55: {
                this.doWIDE_STORE(n, array, Tracer.LONG);
                break;
            }
            case 56: {
                this.doWIDE_STORE(n, array, Tracer.FLOAT);
                break;
            }
            case 57: {
                this.doWIDE_STORE(n, array, Tracer.DOUBLE);
                break;
            }
            case 58: {
                this.doASTORE(ByteArray.readU16bit(array, n + 2));
                break;
            }
            case 132: {
                return 6;
            }
            case 169: {
                this.visitRET(n, array);
                break;
            }
            default: {
                throw new RuntimeException("bad WIDE instruction: " + n2);
            }
        }
        return 4;
    }
    
    private void doWIDE_XLOAD(final int n, final byte[] array, final TypeData typeData) {
        this.doXLOAD(ByteArray.readU16bit(array, n + 2), typeData);
    }
    
    private void doWIDE_STORE(final int n, final byte[] array, final TypeData typeData) {
        this.doXSTORE(ByteArray.readU16bit(array, n + 2), typeData);
    }
    
    private int doPutField(final int n, final byte[] array, final boolean b) throws BadBytecode {
        final int u16bit = ByteArray.readU16bit(array, n + 1);
        final String fieldrefType = this.cpool.getFieldrefType(u16bit);
        this.stackTop -= Descriptor.dataSize(fieldrefType);
        final char char1 = fieldrefType.charAt(0);
        if (char1 == 'L') {
            this.stackTypes[this.stackTop].setType(getFieldClassName(fieldrefType, 0), this.classPool);
        }
        else if (char1 == '[') {
            this.stackTypes[this.stackTop].setType(fieldrefType, this.classPool);
        }
        this.setFieldTarget(b, u16bit);
        return 3;
    }
    
    private int doGetField(final int n, final byte[] array, final boolean b) throws BadBytecode {
        final int u16bit = ByteArray.readU16bit(array, n + 1);
        this.setFieldTarget(b, u16bit);
        this.pushMemberType(this.cpool.getFieldrefType(u16bit));
        return 3;
    }
    
    private void setFieldTarget(final boolean b, final int n) throws BadBytecode {
        if (b) {
            final String fieldrefClassName = this.cpool.getFieldrefClassName(n);
            final TypeData[] stackTypes = this.stackTypes;
            final int stackTop = this.stackTop - 1;
            this.stackTop = stackTop;
            stackTypes[stackTop].setType(fieldrefClassName, this.classPool);
        }
    }
    
    private int doNEWARRAY(final int n, final byte[] array) {
        final int n2 = this.stackTop - 1;
        String s = null;
        switch (array[n + 1] & 0xFF) {
            case 4: {
                s = "[Z";
                break;
            }
            case 5: {
                s = "[C";
                break;
            }
            case 6: {
                s = "[F";
                break;
            }
            case 7: {
                s = "[D";
                break;
            }
            case 8: {
                s = "[B";
                break;
            }
            case 9: {
                s = "[S";
                break;
            }
            case 10: {
                s = "[I";
                break;
            }
            case 11: {
                s = "[J";
                break;
            }
            default: {
                throw new RuntimeException("bad newarray");
            }
        }
        this.stackTypes[n2] = new TypeData.ClassName(s);
        return 2;
    }
    
    private int doMultiANewArray(final int n, final byte[] array) {
        final int u16bit = ByteArray.readU16bit(array, n + 1);
        this.stackTop -= (array[n + 3] & 0xFF) - 1;
        this.stackTypes[this.stackTop - 1] = new TypeData.ClassName(this.cpool.getClassInfo(u16bit).replace('.', '/'));
        return 4;
    }
    
    private int doInvokeMethod(final int n, final byte[] array, final boolean b) throws BadBytecode {
        final int u16bit = ByteArray.readU16bit(array, n + 1);
        final String methodrefType = this.cpool.getMethodrefType(u16bit);
        this.checkParamTypes(methodrefType, 1);
        if (b) {
            final String methodrefClassName = this.cpool.getMethodrefClassName(u16bit);
            final TypeData[] stackTypes = this.stackTypes;
            final int stackTop = this.stackTop - 1;
            this.stackTop = stackTop;
            final TypeData typeData = stackTypes[stackTop];
            if (typeData instanceof TypeData.UninitTypeVar && typeData.isUninit()) {
                this.constructorCalled(typeData, ((TypeData.UninitTypeVar)typeData).offset());
            }
            else if (typeData instanceof TypeData.UninitData) {
                this.constructorCalled(typeData, ((TypeData.UninitData)typeData).offset());
            }
            typeData.setType(methodrefClassName, this.classPool);
        }
        this.pushMemberType(methodrefType);
        return 3;
    }
    
    private void constructorCalled(final TypeData typeData, final int n) {
        typeData.constructorCalled(n);
        int n2 = 0;
        while (0 < this.stackTop) {
            this.stackTypes[0].constructorCalled(n);
            ++n2;
        }
        while (0 < this.localsTypes.length) {
            this.localsTypes[0].constructorCalled(n);
            ++n2;
        }
    }
    
    private int doInvokeIntfMethod(final int n, final byte[] array) throws BadBytecode {
        final int u16bit = ByteArray.readU16bit(array, n + 1);
        final String interfaceMethodrefType = this.cpool.getInterfaceMethodrefType(u16bit);
        this.checkParamTypes(interfaceMethodrefType, 1);
        final String interfaceMethodrefClassName = this.cpool.getInterfaceMethodrefClassName(u16bit);
        final TypeData[] stackTypes = this.stackTypes;
        final int stackTop = this.stackTop - 1;
        this.stackTop = stackTop;
        stackTypes[stackTop].setType(interfaceMethodrefClassName, this.classPool);
        this.pushMemberType(interfaceMethodrefType);
        return 5;
    }
    
    private int doInvokeDynamic(final int n, final byte[] array) throws BadBytecode {
        final String invokeDynamicType = this.cpool.getInvokeDynamicType(ByteArray.readU16bit(array, n + 1));
        this.checkParamTypes(invokeDynamicType, 1);
        this.pushMemberType(invokeDynamicType);
        return 5;
    }
    
    private void pushMemberType(final String s) {
        if (s.charAt(0) == '(') {
            final int n = s.indexOf(41) + 1;
            if (0 < 1) {
                throw new IndexOutOfBoundsException("bad descriptor: " + s);
            }
        }
        final TypeData[] stackTypes = this.stackTypes;
        final int stackTop = this.stackTop;
        switch (s.charAt(0)) {
            case '[': {
                stackTypes[stackTop] = new TypeData.ClassName(s.substring(0));
                break;
            }
            case 'L': {
                stackTypes[stackTop] = new TypeData.ClassName(getFieldClassName(s, 0));
                break;
            }
            case 'J': {
                stackTypes[stackTop] = Tracer.LONG;
                stackTypes[stackTop + 1] = Tracer.TOP;
                this.stackTop += 2;
                return;
            }
            case 'F': {
                stackTypes[stackTop] = Tracer.FLOAT;
                break;
            }
            case 'D': {
                stackTypes[stackTop] = Tracer.DOUBLE;
                stackTypes[stackTop + 1] = Tracer.TOP;
                this.stackTop += 2;
                return;
            }
            case 'V': {
                return;
            }
            default: {
                stackTypes[stackTop] = Tracer.INTEGER;
                break;
            }
        }
        ++this.stackTop;
    }
    
    private static String getFieldClassName(final String s, final int n) {
        return s.substring(n + 1, s.length() - 1).replace('/', '.');
    }
    
    private void checkParamTypes(final String s, final int n) throws BadBytecode {
        char c = s.charAt(n);
        if (c == ')') {
            return;
        }
        int n2;
        for (n2 = n; c == '['; c = s.charAt(++n2)) {}
        if (c == 'L') {
            n2 = s.indexOf(59, n2) + 1;
            if (n2 <= 0) {
                throw new IndexOutOfBoundsException("bad descriptor");
            }
        }
        else {
            ++n2;
        }
        this.checkParamTypes(s, n2);
        if (!true && (c == 'J' || c == 'D')) {
            this.stackTop -= 2;
        }
        else {
            --this.stackTop;
        }
        if (true) {
            this.stackTypes[this.stackTop].setType(s.substring(n, n2), this.classPool);
        }
        else if (c == 'L') {
            this.stackTypes[this.stackTop].setType(s.substring(n + 1, n2 - 1).replace('/', '.'), this.classPool);
        }
    }
}
