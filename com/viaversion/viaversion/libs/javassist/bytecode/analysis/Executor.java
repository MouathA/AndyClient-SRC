package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;

public class Executor implements Opcode
{
    private final ConstPool constPool;
    private final ClassPool classPool;
    private final Type STRING_TYPE;
    private final Type CLASS_TYPE;
    private final Type THROWABLE_TYPE;
    private int lastPos;
    
    public Executor(final ClassPool classPool, final ConstPool constPool) {
        this.constPool = constPool;
        this.classPool = classPool;
        this.STRING_TYPE = this.getType("java.lang.String");
        this.CLASS_TYPE = this.getType("java.lang.Class");
        this.THROWABLE_TYPE = this.getType("java.lang.Throwable");
    }
    
    public void execute(final MethodInfo methodInfo, final int lastPos, final CodeIterator codeIterator, final Frame frame, final Subroutine subroutine) throws BadBytecode {
        this.lastPos = lastPos;
        final int byte1 = codeIterator.byteAt(lastPos);
        switch (byte1) {
            case 1: {
                frame.push(Type.UNINIT);
                break;
            }
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8: {
                frame.push(Type.INTEGER);
                break;
            }
            case 9:
            case 10: {
                frame.push(Type.LONG);
                frame.push(Type.TOP);
                break;
            }
            case 11:
            case 12:
            case 13: {
                frame.push(Type.FLOAT);
                break;
            }
            case 14:
            case 15: {
                frame.push(Type.DOUBLE);
                frame.push(Type.TOP);
                break;
            }
            case 16:
            case 17: {
                frame.push(Type.INTEGER);
                break;
            }
            case 18: {
                this.evalLDC(codeIterator.byteAt(lastPos + 1), frame);
                break;
            }
            case 19:
            case 20: {
                this.evalLDC(codeIterator.u16bitAt(lastPos + 1), frame);
                break;
            }
            case 21: {
                this.evalLoad(Type.INTEGER, codeIterator.byteAt(lastPos + 1), frame, subroutine);
                break;
            }
            case 22: {
                this.evalLoad(Type.LONG, codeIterator.byteAt(lastPos + 1), frame, subroutine);
                break;
            }
            case 23: {
                this.evalLoad(Type.FLOAT, codeIterator.byteAt(lastPos + 1), frame, subroutine);
                break;
            }
            case 24: {
                this.evalLoad(Type.DOUBLE, codeIterator.byteAt(lastPos + 1), frame, subroutine);
                break;
            }
            case 25: {
                this.evalLoad(Type.OBJECT, codeIterator.byteAt(lastPos + 1), frame, subroutine);
                break;
            }
            case 26:
            case 27:
            case 28:
            case 29: {
                this.evalLoad(Type.INTEGER, byte1 - 26, frame, subroutine);
                break;
            }
            case 30:
            case 31:
            case 32:
            case 33: {
                this.evalLoad(Type.LONG, byte1 - 30, frame, subroutine);
                break;
            }
            case 34:
            case 35:
            case 36:
            case 37: {
                this.evalLoad(Type.FLOAT, byte1 - 34, frame, subroutine);
                break;
            }
            case 38:
            case 39:
            case 40:
            case 41: {
                this.evalLoad(Type.DOUBLE, byte1 - 38, frame, subroutine);
                break;
            }
            case 42:
            case 43:
            case 44:
            case 45: {
                this.evalLoad(Type.OBJECT, byte1 - 42, frame, subroutine);
                break;
            }
            case 46: {
                this.evalArrayLoad(Type.INTEGER, frame);
                break;
            }
            case 47: {
                this.evalArrayLoad(Type.LONG, frame);
                break;
            }
            case 48: {
                this.evalArrayLoad(Type.FLOAT, frame);
                break;
            }
            case 49: {
                this.evalArrayLoad(Type.DOUBLE, frame);
                break;
            }
            case 50: {
                this.evalArrayLoad(Type.OBJECT, frame);
                break;
            }
            case 51:
            case 52:
            case 53: {
                this.evalArrayLoad(Type.INTEGER, frame);
                break;
            }
            case 54: {
                this.evalStore(Type.INTEGER, codeIterator.byteAt(lastPos + 1), frame, subroutine);
                break;
            }
            case 55: {
                this.evalStore(Type.LONG, codeIterator.byteAt(lastPos + 1), frame, subroutine);
                break;
            }
            case 56: {
                this.evalStore(Type.FLOAT, codeIterator.byteAt(lastPos + 1), frame, subroutine);
                break;
            }
            case 57: {
                this.evalStore(Type.DOUBLE, codeIterator.byteAt(lastPos + 1), frame, subroutine);
                break;
            }
            case 58: {
                this.evalStore(Type.OBJECT, codeIterator.byteAt(lastPos + 1), frame, subroutine);
                break;
            }
            case 59:
            case 60:
            case 61:
            case 62: {
                this.evalStore(Type.INTEGER, byte1 - 59, frame, subroutine);
                break;
            }
            case 63:
            case 64:
            case 65:
            case 66: {
                this.evalStore(Type.LONG, byte1 - 63, frame, subroutine);
                break;
            }
            case 67:
            case 68:
            case 69:
            case 70: {
                this.evalStore(Type.FLOAT, byte1 - 67, frame, subroutine);
                break;
            }
            case 71:
            case 72:
            case 73:
            case 74: {
                this.evalStore(Type.DOUBLE, byte1 - 71, frame, subroutine);
                break;
            }
            case 75:
            case 76:
            case 77:
            case 78: {
                this.evalStore(Type.OBJECT, byte1 - 75, frame, subroutine);
                break;
            }
            case 79: {
                this.evalArrayStore(Type.INTEGER, frame);
                break;
            }
            case 80: {
                this.evalArrayStore(Type.LONG, frame);
                break;
            }
            case 81: {
                this.evalArrayStore(Type.FLOAT, frame);
                break;
            }
            case 82: {
                this.evalArrayStore(Type.DOUBLE, frame);
                break;
            }
            case 83: {
                this.evalArrayStore(Type.OBJECT, frame);
                break;
            }
            case 84:
            case 85:
            case 86: {
                this.evalArrayStore(Type.INTEGER, frame);
                break;
            }
            case 87: {
                if (frame.pop() == Type.TOP) {
                    throw new BadBytecode("POP can not be used with a category 2 value, pos = " + lastPos);
                }
                break;
            }
            case 88: {
                frame.pop();
                frame.pop();
                break;
            }
            case 89: {
                if (frame.peek() == Type.TOP) {
                    throw new BadBytecode("DUP can not be used with a category 2 value, pos = " + lastPos);
                }
                frame.push(frame.peek());
                break;
            }
            case 90:
            case 91: {
                final Type peek = frame.peek();
                if (peek == Type.TOP) {
                    throw new BadBytecode("DUP can not be used with a category 2 value, pos = " + lastPos);
                }
                int i = frame.getTopIndex();
                final int n = i - (byte1 - 90) - 1;
                frame.push(peek);
                while (i > n) {
                    frame.setStack(i, frame.getStack(i - 1));
                    --i;
                }
                frame.setStack(n, peek);
                break;
            }
            case 92: {
                frame.push(frame.getStack(frame.getTopIndex() - 1));
                frame.push(frame.getStack(frame.getTopIndex() - 1));
                break;
            }
            case 93:
            case 94: {
                int j = frame.getTopIndex();
                final int n2 = j - (byte1 - 93) - 1;
                final Type stack = frame.getStack(frame.getTopIndex() - 1);
                final Type peek2 = frame.peek();
                frame.push(stack);
                frame.push(peek2);
                while (j > n2) {
                    frame.setStack(j, frame.getStack(j - 2));
                    --j;
                }
                frame.setStack(n2, peek2);
                frame.setStack(n2 - 1, stack);
                break;
            }
            case 95: {
                final Type pop = frame.pop();
                final Type pop2 = frame.pop();
                if (pop.getSize() == 2 || pop2.getSize() == 2) {
                    throw new BadBytecode("Swap can not be used with category 2 values, pos = " + lastPos);
                }
                frame.push(pop);
                frame.push(pop2);
                break;
            }
            case 96: {
                this.evalBinaryMath(Type.INTEGER, frame);
                break;
            }
            case 97: {
                this.evalBinaryMath(Type.LONG, frame);
                break;
            }
            case 98: {
                this.evalBinaryMath(Type.FLOAT, frame);
                break;
            }
            case 99: {
                this.evalBinaryMath(Type.DOUBLE, frame);
                break;
            }
            case 100: {
                this.evalBinaryMath(Type.INTEGER, frame);
                break;
            }
            case 101: {
                this.evalBinaryMath(Type.LONG, frame);
                break;
            }
            case 102: {
                this.evalBinaryMath(Type.FLOAT, frame);
                break;
            }
            case 103: {
                this.evalBinaryMath(Type.DOUBLE, frame);
                break;
            }
            case 104: {
                this.evalBinaryMath(Type.INTEGER, frame);
                break;
            }
            case 105: {
                this.evalBinaryMath(Type.LONG, frame);
                break;
            }
            case 106: {
                this.evalBinaryMath(Type.FLOAT, frame);
                break;
            }
            case 107: {
                this.evalBinaryMath(Type.DOUBLE, frame);
                break;
            }
            case 108: {
                this.evalBinaryMath(Type.INTEGER, frame);
                break;
            }
            case 109: {
                this.evalBinaryMath(Type.LONG, frame);
                break;
            }
            case 110: {
                this.evalBinaryMath(Type.FLOAT, frame);
                break;
            }
            case 111: {
                this.evalBinaryMath(Type.DOUBLE, frame);
                break;
            }
            case 112: {
                this.evalBinaryMath(Type.INTEGER, frame);
                break;
            }
            case 113: {
                this.evalBinaryMath(Type.LONG, frame);
                break;
            }
            case 114: {
                this.evalBinaryMath(Type.FLOAT, frame);
                break;
            }
            case 115: {
                this.evalBinaryMath(Type.DOUBLE, frame);
                break;
            }
            case 116: {
                this.verifyAssignable(Type.INTEGER, this.simplePeek(frame));
                break;
            }
            case 117: {
                this.verifyAssignable(Type.LONG, this.simplePeek(frame));
                break;
            }
            case 118: {
                this.verifyAssignable(Type.FLOAT, this.simplePeek(frame));
                break;
            }
            case 119: {
                this.verifyAssignable(Type.DOUBLE, this.simplePeek(frame));
                break;
            }
            case 120: {
                this.evalShift(Type.INTEGER, frame);
                break;
            }
            case 121: {
                this.evalShift(Type.LONG, frame);
                break;
            }
            case 122: {
                this.evalShift(Type.INTEGER, frame);
                break;
            }
            case 123: {
                this.evalShift(Type.LONG, frame);
                break;
            }
            case 124: {
                this.evalShift(Type.INTEGER, frame);
                break;
            }
            case 125: {
                this.evalShift(Type.LONG, frame);
                break;
            }
            case 126: {
                this.evalBinaryMath(Type.INTEGER, frame);
                break;
            }
            case 127: {
                this.evalBinaryMath(Type.LONG, frame);
                break;
            }
            case 128: {
                this.evalBinaryMath(Type.INTEGER, frame);
                break;
            }
            case 129: {
                this.evalBinaryMath(Type.LONG, frame);
                break;
            }
            case 130: {
                this.evalBinaryMath(Type.INTEGER, frame);
                break;
            }
            case 131: {
                this.evalBinaryMath(Type.LONG, frame);
                break;
            }
            case 132: {
                final int byte2 = codeIterator.byteAt(lastPos + 1);
                this.verifyAssignable(Type.INTEGER, frame.getLocal(byte2));
                this.access(byte2, Type.INTEGER, subroutine);
                break;
            }
            case 133: {
                this.verifyAssignable(Type.INTEGER, this.simplePop(frame));
                this.simplePush(Type.LONG, frame);
                break;
            }
            case 134: {
                this.verifyAssignable(Type.INTEGER, this.simplePop(frame));
                this.simplePush(Type.FLOAT, frame);
                break;
            }
            case 135: {
                this.verifyAssignable(Type.INTEGER, this.simplePop(frame));
                this.simplePush(Type.DOUBLE, frame);
                break;
            }
            case 136: {
                this.verifyAssignable(Type.LONG, this.simplePop(frame));
                this.simplePush(Type.INTEGER, frame);
                break;
            }
            case 137: {
                this.verifyAssignable(Type.LONG, this.simplePop(frame));
                this.simplePush(Type.FLOAT, frame);
                break;
            }
            case 138: {
                this.verifyAssignable(Type.LONG, this.simplePop(frame));
                this.simplePush(Type.DOUBLE, frame);
                break;
            }
            case 139: {
                this.verifyAssignable(Type.FLOAT, this.simplePop(frame));
                this.simplePush(Type.INTEGER, frame);
                break;
            }
            case 140: {
                this.verifyAssignable(Type.FLOAT, this.simplePop(frame));
                this.simplePush(Type.LONG, frame);
                break;
            }
            case 141: {
                this.verifyAssignable(Type.FLOAT, this.simplePop(frame));
                this.simplePush(Type.DOUBLE, frame);
                break;
            }
            case 142: {
                this.verifyAssignable(Type.DOUBLE, this.simplePop(frame));
                this.simplePush(Type.INTEGER, frame);
                break;
            }
            case 143: {
                this.verifyAssignable(Type.DOUBLE, this.simplePop(frame));
                this.simplePush(Type.LONG, frame);
                break;
            }
            case 144: {
                this.verifyAssignable(Type.DOUBLE, this.simplePop(frame));
                this.simplePush(Type.FLOAT, frame);
                break;
            }
            case 145:
            case 146:
            case 147: {
                this.verifyAssignable(Type.INTEGER, frame.peek());
                break;
            }
            case 148: {
                this.verifyAssignable(Type.LONG, this.simplePop(frame));
                this.verifyAssignable(Type.LONG, this.simplePop(frame));
                frame.push(Type.INTEGER);
                break;
            }
            case 149:
            case 150: {
                this.verifyAssignable(Type.FLOAT, this.simplePop(frame));
                this.verifyAssignable(Type.FLOAT, this.simplePop(frame));
                frame.push(Type.INTEGER);
                break;
            }
            case 151:
            case 152: {
                this.verifyAssignable(Type.DOUBLE, this.simplePop(frame));
                this.verifyAssignable(Type.DOUBLE, this.simplePop(frame));
                frame.push(Type.INTEGER);
                break;
            }
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158: {
                this.verifyAssignable(Type.INTEGER, this.simplePop(frame));
                break;
            }
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164: {
                this.verifyAssignable(Type.INTEGER, this.simplePop(frame));
                this.verifyAssignable(Type.INTEGER, this.simplePop(frame));
                break;
            }
            case 165:
            case 166: {
                this.verifyAssignable(Type.OBJECT, this.simplePop(frame));
                this.verifyAssignable(Type.OBJECT, this.simplePop(frame));
            }
            case 168: {
                frame.push(Type.RETURN_ADDRESS);
                break;
            }
            case 169: {
                this.verifyAssignable(Type.RETURN_ADDRESS, frame.getLocal(codeIterator.byteAt(lastPos + 1)));
                break;
            }
            case 170:
            case 171:
            case 172: {
                this.verifyAssignable(Type.INTEGER, this.simplePop(frame));
                break;
            }
            case 173: {
                this.verifyAssignable(Type.LONG, this.simplePop(frame));
                break;
            }
            case 174: {
                this.verifyAssignable(Type.FLOAT, this.simplePop(frame));
                break;
            }
            case 175: {
                this.verifyAssignable(Type.DOUBLE, this.simplePop(frame));
                break;
            }
            case 176: {
                this.verifyAssignable(Type.get(Descriptor.getReturnType(methodInfo.getDescriptor(), this.classPool)), this.simplePop(frame));
            }
            case 178: {
                this.evalGetField(byte1, codeIterator.u16bitAt(lastPos + 1), frame);
                break;
            }
            case 179: {
                this.evalPutField(byte1, codeIterator.u16bitAt(lastPos + 1), frame);
                break;
            }
            case 180: {
                this.evalGetField(byte1, codeIterator.u16bitAt(lastPos + 1), frame);
                break;
            }
            case 181: {
                this.evalPutField(byte1, codeIterator.u16bitAt(lastPos + 1), frame);
                break;
            }
            case 182:
            case 183:
            case 184: {
                this.evalInvokeMethod(byte1, codeIterator.u16bitAt(lastPos + 1), frame);
                break;
            }
            case 185: {
                this.evalInvokeIntfMethod(byte1, codeIterator.u16bitAt(lastPos + 1), frame);
                break;
            }
            case 186: {
                this.evalInvokeDynamic(byte1, codeIterator.u16bitAt(lastPos + 1), frame);
                break;
            }
            case 187: {
                frame.push(this.resolveClassInfo(this.constPool.getClassInfo(codeIterator.u16bitAt(lastPos + 1))));
                break;
            }
            case 188: {
                this.evalNewArray(lastPos, codeIterator, frame);
                break;
            }
            case 189: {
                this.evalNewObjectArray(lastPos, codeIterator, frame);
                break;
            }
            case 190: {
                final Type simplePop = this.simplePop(frame);
                if (!simplePop.isArray() && simplePop != Type.UNINIT) {
                    throw new BadBytecode("Array length passed a non-array [pos = " + lastPos + "]: " + simplePop);
                }
                frame.push(Type.INTEGER);
                break;
            }
            case 191: {
                this.verifyAssignable(this.THROWABLE_TYPE, this.simplePop(frame));
                break;
            }
            case 192: {
                this.verifyAssignable(Type.OBJECT, this.simplePop(frame));
                frame.push(this.typeFromDesc(this.constPool.getClassInfoByDescriptor(codeIterator.u16bitAt(lastPos + 1))));
                break;
            }
            case 193: {
                this.verifyAssignable(Type.OBJECT, this.simplePop(frame));
                frame.push(Type.INTEGER);
                break;
            }
            case 194:
            case 195: {
                this.verifyAssignable(Type.OBJECT, this.simplePop(frame));
                break;
            }
            case 196: {
                this.evalWide(lastPos, codeIterator, frame, subroutine);
                break;
            }
            case 197: {
                this.evalNewObjectArray(lastPos, codeIterator, frame);
                break;
            }
            case 198:
            case 199: {
                this.verifyAssignable(Type.OBJECT, this.simplePop(frame));
            }
            case 201: {
                frame.push(Type.RETURN_ADDRESS);
                break;
            }
        }
    }
    
    private Type zeroExtend(final Type type) {
        if (type == Type.SHORT || type == Type.BYTE || type == Type.CHAR || type == Type.BOOLEAN) {
            return Type.INTEGER;
        }
        return type;
    }
    
    private void evalArrayLoad(final Type type, final Frame frame) throws BadBytecode {
        final Type pop = frame.pop();
        final Type pop2 = frame.pop();
        if (pop2 == Type.UNINIT) {
            this.verifyAssignable(Type.INTEGER, pop);
            if (type == Type.OBJECT) {
                this.simplePush(Type.UNINIT, frame);
            }
            else {
                this.simplePush(type, frame);
            }
            return;
        }
        final Type component = pop2.getComponent();
        if (component == null) {
            throw new BadBytecode("Not an array! [pos = " + this.lastPos + "]: " + component);
        }
        final Type zeroExtend = this.zeroExtend(component);
        this.verifyAssignable(type, zeroExtend);
        this.verifyAssignable(Type.INTEGER, pop);
        this.simplePush(zeroExtend, frame);
    }
    
    private void evalArrayStore(final Type type, final Frame frame) throws BadBytecode {
        final Type simplePop = this.simplePop(frame);
        final Type pop = frame.pop();
        final Type pop2 = frame.pop();
        if (pop2 == Type.UNINIT) {
            this.verifyAssignable(Type.INTEGER, pop);
            return;
        }
        final Type component = pop2.getComponent();
        if (component == null) {
            throw new BadBytecode("Not an array! [pos = " + this.lastPos + "]: " + component);
        }
        final Type zeroExtend = this.zeroExtend(component);
        this.verifyAssignable(type, zeroExtend);
        this.verifyAssignable(Type.INTEGER, pop);
        if (type == Type.OBJECT) {
            this.verifyAssignable(type, simplePop);
        }
        else {
            this.verifyAssignable(zeroExtend, simplePop);
        }
    }
    
    private void evalBinaryMath(final Type type, final Frame frame) throws BadBytecode {
        final Type simplePop = this.simplePop(frame);
        final Type simplePop2 = this.simplePop(frame);
        this.verifyAssignable(type, simplePop);
        this.verifyAssignable(type, simplePop2);
        this.simplePush(simplePop2, frame);
    }
    
    private void evalGetField(final int n, final int n2, final Frame frame) throws BadBytecode {
        final Type zeroExtend = this.zeroExtend(this.typeFromDesc(this.constPool.getFieldrefType(n2)));
        if (n == 180) {
            this.verifyAssignable(this.resolveClassInfo(this.constPool.getFieldrefClassName(n2)), this.simplePop(frame));
        }
        this.simplePush(zeroExtend, frame);
    }
    
    private void evalInvokeIntfMethod(final int n, final int n2, final Frame frame) throws BadBytecode {
        final String interfaceMethodrefType = this.constPool.getInterfaceMethodrefType(n2);
        final Type[] paramTypesFromDesc = this.paramTypesFromDesc(interfaceMethodrefType);
        int i = paramTypesFromDesc.length;
        while (i > 0) {
            this.verifyAssignable(this.zeroExtend(paramTypesFromDesc[--i]), this.simplePop(frame));
        }
        this.verifyAssignable(this.resolveClassInfo(this.constPool.getInterfaceMethodrefClassName(n2)), this.simplePop(frame));
        final Type returnTypeFromDesc = this.returnTypeFromDesc(interfaceMethodrefType);
        if (returnTypeFromDesc != Type.VOID) {
            this.simplePush(this.zeroExtend(returnTypeFromDesc), frame);
        }
    }
    
    private void evalInvokeMethod(final int n, final int n2, final Frame frame) throws BadBytecode {
        final String methodrefType = this.constPool.getMethodrefType(n2);
        final Type[] paramTypesFromDesc = this.paramTypesFromDesc(methodrefType);
        int i = paramTypesFromDesc.length;
        while (i > 0) {
            this.verifyAssignable(this.zeroExtend(paramTypesFromDesc[--i]), this.simplePop(frame));
        }
        if (n != 184) {
            this.verifyAssignable(this.resolveClassInfo(this.constPool.getMethodrefClassName(n2)), this.simplePop(frame));
        }
        final Type returnTypeFromDesc = this.returnTypeFromDesc(methodrefType);
        if (returnTypeFromDesc != Type.VOID) {
            this.simplePush(this.zeroExtend(returnTypeFromDesc), frame);
        }
    }
    
    private void evalInvokeDynamic(final int n, final int n2, final Frame frame) throws BadBytecode {
        final String invokeDynamicType = this.constPool.getInvokeDynamicType(n2);
        final Type[] paramTypesFromDesc = this.paramTypesFromDesc(invokeDynamicType);
        int i = paramTypesFromDesc.length;
        while (i > 0) {
            this.verifyAssignable(this.zeroExtend(paramTypesFromDesc[--i]), this.simplePop(frame));
        }
        final Type returnTypeFromDesc = this.returnTypeFromDesc(invokeDynamicType);
        if (returnTypeFromDesc != Type.VOID) {
            this.simplePush(this.zeroExtend(returnTypeFromDesc), frame);
        }
    }
    
    private void evalLDC(final int n, final Frame frame) throws BadBytecode {
        final int tag = this.constPool.getTag(n);
        Type type = null;
        switch (tag) {
            case 8: {
                type = this.STRING_TYPE;
                break;
            }
            case 3: {
                type = Type.INTEGER;
                break;
            }
            case 4: {
                type = Type.FLOAT;
                break;
            }
            case 5: {
                type = Type.LONG;
                break;
            }
            case 6: {
                type = Type.DOUBLE;
                break;
            }
            case 7: {
                type = this.CLASS_TYPE;
                break;
            }
            default: {
                throw new BadBytecode("bad LDC [pos = " + this.lastPos + "]: " + tag);
            }
        }
        this.simplePush(type, frame);
    }
    
    private void evalLoad(final Type type, final int n, final Frame frame, final Subroutine subroutine) throws BadBytecode {
        final Type local = frame.getLocal(n);
        this.verifyAssignable(type, local);
        this.simplePush(local, frame);
        this.access(n, local, subroutine);
    }
    
    private void evalNewArray(final int n, final CodeIterator codeIterator, final Frame frame) throws BadBytecode {
        this.verifyAssignable(Type.INTEGER, this.simplePop(frame));
        final int byte1 = codeIterator.byteAt(n + 1);
        Type type = null;
        switch (byte1) {
            case 4: {
                type = this.getType("boolean[]");
                break;
            }
            case 5: {
                type = this.getType("char[]");
                break;
            }
            case 8: {
                type = this.getType("byte[]");
                break;
            }
            case 9: {
                type = this.getType("short[]");
                break;
            }
            case 10: {
                type = this.getType("int[]");
                break;
            }
            case 11: {
                type = this.getType("long[]");
                break;
            }
            case 6: {
                type = this.getType("float[]");
                break;
            }
            case 7: {
                type = this.getType("double[]");
                break;
            }
            default: {
                throw new BadBytecode("Invalid array type [pos = " + n + "]: " + byte1);
            }
        }
        frame.push(type);
    }
    
    private void evalNewObjectArray(final int n, final CodeIterator codeIterator, final Frame frame) throws BadBytecode {
        String s = this.resolveClassInfo(this.constPool.getClassInfo(codeIterator.u16bitAt(n + 1))).getCtClass().getName();
        int byte1 = 0;
        if (codeIterator.byteAt(n) == 197) {
            byte1 = codeIterator.byteAt(n + 3);
        }
        else {
            s += "[]";
        }
        while (true) {
            final int n2 = 1;
            --byte1;
            if (n2 <= 0) {
                break;
            }
            this.verifyAssignable(Type.INTEGER, this.simplePop(frame));
        }
        this.simplePush(this.getType(s), frame);
    }
    
    private void evalPutField(final int n, final int n2, final Frame frame) throws BadBytecode {
        this.verifyAssignable(this.zeroExtend(this.typeFromDesc(this.constPool.getFieldrefType(n2))), this.simplePop(frame));
        if (n == 181) {
            this.verifyAssignable(this.resolveClassInfo(this.constPool.getFieldrefClassName(n2)), this.simplePop(frame));
        }
    }
    
    private void evalShift(final Type type, final Frame frame) throws BadBytecode {
        final Type simplePop = this.simplePop(frame);
        final Type simplePop2 = this.simplePop(frame);
        this.verifyAssignable(Type.INTEGER, simplePop);
        this.verifyAssignable(type, simplePop2);
        this.simplePush(simplePop2, frame);
    }
    
    private void evalStore(final Type type, final int n, final Frame frame, final Subroutine subroutine) throws BadBytecode {
        final Type simplePop = this.simplePop(frame);
        if (type != Type.OBJECT || simplePop != Type.RETURN_ADDRESS) {
            this.verifyAssignable(type, simplePop);
        }
        this.simpleSetLocal(n, simplePop, frame);
        this.access(n, simplePop, subroutine);
    }
    
    private void evalWide(final int n, final CodeIterator codeIterator, final Frame frame, final Subroutine subroutine) throws BadBytecode {
        final int byte1 = codeIterator.byteAt(n + 1);
        final int u16bit = codeIterator.u16bitAt(n + 2);
        switch (byte1) {
            case 21: {
                this.evalLoad(Type.INTEGER, u16bit, frame, subroutine);
                break;
            }
            case 22: {
                this.evalLoad(Type.LONG, u16bit, frame, subroutine);
                break;
            }
            case 23: {
                this.evalLoad(Type.FLOAT, u16bit, frame, subroutine);
                break;
            }
            case 24: {
                this.evalLoad(Type.DOUBLE, u16bit, frame, subroutine);
                break;
            }
            case 25: {
                this.evalLoad(Type.OBJECT, u16bit, frame, subroutine);
                break;
            }
            case 54: {
                this.evalStore(Type.INTEGER, u16bit, frame, subroutine);
                break;
            }
            case 55: {
                this.evalStore(Type.LONG, u16bit, frame, subroutine);
                break;
            }
            case 56: {
                this.evalStore(Type.FLOAT, u16bit, frame, subroutine);
                break;
            }
            case 57: {
                this.evalStore(Type.DOUBLE, u16bit, frame, subroutine);
                break;
            }
            case 58: {
                this.evalStore(Type.OBJECT, u16bit, frame, subroutine);
                break;
            }
            case 132: {
                this.verifyAssignable(Type.INTEGER, frame.getLocal(u16bit));
                break;
            }
            case 169: {
                this.verifyAssignable(Type.RETURN_ADDRESS, frame.getLocal(u16bit));
                break;
            }
            default: {
                throw new BadBytecode("Invalid WIDE operand [pos = " + n + "]: " + byte1);
            }
        }
    }
    
    private Type getType(final String s) throws BadBytecode {
        return Type.get(this.classPool.get(s));
    }
    
    private Type[] paramTypesFromDesc(final String s) throws BadBytecode {
        final CtClass[] parameterTypes = Descriptor.getParameterTypes(s, this.classPool);
        if (parameterTypes == null) {
            throw new BadBytecode("Could not obtain parameters for descriptor [pos = " + this.lastPos + "]: " + s);
        }
        final Type[] array = new Type[parameterTypes.length];
        while (0 < array.length) {
            array[0] = Type.get(parameterTypes[0]);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    private Type returnTypeFromDesc(final String s) throws BadBytecode {
        final CtClass returnType = Descriptor.getReturnType(s, this.classPool);
        if (returnType == null) {
            throw new BadBytecode("Could not obtain return type for descriptor [pos = " + this.lastPos + "]: " + s);
        }
        return Type.get(returnType);
    }
    
    private Type simplePeek(final Frame frame) {
        final Type peek = frame.peek();
        return (peek == Type.TOP) ? frame.getStack(frame.getTopIndex() - 1) : peek;
    }
    
    private Type simplePop(final Frame frame) {
        final Type pop = frame.pop();
        return (pop == Type.TOP) ? frame.pop() : pop;
    }
    
    private void simplePush(final Type type, final Frame frame) {
        frame.push(type);
        if (type.getSize() == 2) {
            frame.push(Type.TOP);
        }
    }
    
    private void access(final int n, final Type type, final Subroutine subroutine) {
        if (subroutine == null) {
            return;
        }
        subroutine.access(n);
        if (type.getSize() == 2) {
            subroutine.access(n + 1);
        }
    }
    
    private void simpleSetLocal(final int n, final Type type, final Frame frame) {
        frame.setLocal(n, type);
        if (type.getSize() == 2) {
            frame.setLocal(n + 1, Type.TOP);
        }
    }
    
    private Type resolveClassInfo(final String s) throws BadBytecode {
        CtClass ctClass;
        if (s.charAt(0) == '[') {
            ctClass = Descriptor.toCtClass(s, this.classPool);
        }
        else {
            ctClass = this.classPool.get(s);
        }
        if (ctClass == null) {
            throw new BadBytecode("Could not obtain type for descriptor [pos = " + this.lastPos + "]: " + s);
        }
        return Type.get(ctClass);
    }
    
    private Type typeFromDesc(final String s) throws BadBytecode {
        final CtClass ctClass = Descriptor.toCtClass(s, this.classPool);
        if (ctClass == null) {
            throw new BadBytecode("Could not obtain type for descriptor [pos = " + this.lastPos + "]: " + s);
        }
        return Type.get(ctClass);
    }
    
    private void verifyAssignable(final Type type, final Type type2) throws BadBytecode {
        if (!type.isAssignableFrom(type2)) {
            throw new BadBytecode("Expected type: " + type + " Got: " + type2 + " [pos = " + this.lastPos + "]");
        }
    }
}
