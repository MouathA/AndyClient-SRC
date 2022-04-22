package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import com.viaversion.viaversion.libs.javassist.*;

public class InstructionPrinter implements Opcode
{
    private final PrintStream stream;
    
    public InstructionPrinter(final PrintStream stream) {
        this.stream = stream;
    }
    
    public static void print(final CtMethod ctMethod, final PrintStream printStream) {
        new InstructionPrinter(printStream).print(ctMethod);
    }
    
    public void print(final CtMethod ctMethod) {
        final MethodInfo methodInfo2 = ctMethod.getMethodInfo2();
        final ConstPool constPool = methodInfo2.getConstPool();
        final CodeAttribute codeAttribute = methodInfo2.getCodeAttribute();
        if (codeAttribute == null) {
            return;
        }
        final CodeIterator iterator = codeAttribute.iterator();
        while (iterator.hasNext()) {
            final int next = iterator.next();
            this.stream.println(next + ": " + instructionString(iterator, next, constPool));
        }
    }
    
    public static String instructionString(final CodeIterator codeIterator, final int n, final ConstPool constPool) {
        final int byte1 = codeIterator.byteAt(n);
        if (byte1 > InstructionPrinter.opcodes.length || byte1 < 0) {
            throw new IllegalArgumentException("Invalid opcode, opcode: " + byte1 + " pos: " + n);
        }
        final String s = InstructionPrinter.opcodes[byte1];
        switch (byte1) {
            case 16: {
                return s + " " + codeIterator.byteAt(n + 1);
            }
            case 17: {
                return s + " " + codeIterator.s16bitAt(n + 1);
            }
            case 18: {
                return s + " " + ldc(constPool, codeIterator.byteAt(n + 1));
            }
            case 19:
            case 20: {
                return s + " " + ldc(constPool, codeIterator.u16bitAt(n + 1));
            }
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58: {
                return s + " " + codeIterator.byteAt(n + 1);
            }
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 198:
            case 199: {
                return s + " " + (codeIterator.s16bitAt(n + 1) + n);
            }
            case 132: {
                return s + " " + codeIterator.byteAt(n + 1) + ", " + codeIterator.signedByteAt(n + 2);
            }
            case 167:
            case 168: {
                return s + " " + (codeIterator.s16bitAt(n + 1) + n);
            }
            case 169: {
                return s + " " + codeIterator.byteAt(n + 1);
            }
            case 170: {
                return tableSwitch(codeIterator, n);
            }
            case 171: {
                return lookupSwitch(codeIterator, n);
            }
            case 178:
            case 179:
            case 180:
            case 181: {
                return s + " " + fieldInfo(constPool, codeIterator.u16bitAt(n + 1));
            }
            case 182:
            case 183:
            case 184: {
                return s + " " + methodInfo(constPool, codeIterator.u16bitAt(n + 1));
            }
            case 185: {
                return s + " " + interfaceMethodInfo(constPool, codeIterator.u16bitAt(n + 1));
            }
            case 186: {
                return s + " " + codeIterator.u16bitAt(n + 1);
            }
            case 187: {
                return s + " " + classInfo(constPool, codeIterator.u16bitAt(n + 1));
            }
            case 188: {
                return s + " " + arrayInfo(codeIterator.byteAt(n + 1));
            }
            case 189:
            case 192: {
                return s + " " + classInfo(constPool, codeIterator.u16bitAt(n + 1));
            }
            case 196: {
                return wide(codeIterator, n);
            }
            case 197: {
                return s + " " + classInfo(constPool, codeIterator.u16bitAt(n + 1));
            }
            case 200:
            case 201: {
                return s + " " + (codeIterator.s32bitAt(n + 1) + n);
            }
            default: {
                return s;
            }
        }
    }
    
    private static String wide(final CodeIterator codeIterator, final int n) {
        final int byte1 = codeIterator.byteAt(n + 1);
        final int u16bit = codeIterator.u16bitAt(n + 2);
        switch (byte1) {
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 132:
            case 169: {
                return InstructionPrinter.opcodes[byte1] + " " + u16bit;
            }
            default: {
                throw new RuntimeException("Invalid WIDE operand");
            }
        }
    }
    
    private static String arrayInfo(final int n) {
        switch (n) {
            case 4: {
                return "boolean";
            }
            case 5: {
                return "char";
            }
            case 8: {
                return "byte";
            }
            case 9: {
                return "short";
            }
            case 10: {
                return "int";
            }
            case 11: {
                return "long";
            }
            case 6: {
                return "float";
            }
            case 7: {
                return "double";
            }
            default: {
                throw new RuntimeException("Invalid array type");
            }
        }
    }
    
    private static String classInfo(final ConstPool constPool, final int n) {
        return "#" + n + " = Class " + constPool.getClassInfo(n);
    }
    
    private static String interfaceMethodInfo(final ConstPool constPool, final int n) {
        return "#" + n + " = Method " + constPool.getInterfaceMethodrefClassName(n) + "." + constPool.getInterfaceMethodrefName(n) + "(" + constPool.getInterfaceMethodrefType(n) + ")";
    }
    
    private static String methodInfo(final ConstPool constPool, final int n) {
        return "#" + n + " = Method " + constPool.getMethodrefClassName(n) + "." + constPool.getMethodrefName(n) + "(" + constPool.getMethodrefType(n) + ")";
    }
    
    private static String fieldInfo(final ConstPool constPool, final int n) {
        return "#" + n + " = Field " + constPool.getFieldrefClassName(n) + "." + constPool.getFieldrefName(n) + "(" + constPool.getFieldrefType(n) + ")";
    }
    
    private static String lookupSwitch(final CodeIterator codeIterator, final int n) {
        final StringBuffer sb = new StringBuffer("lookupswitch {\n");
        int i = (n & 0xFFFFFFFC) + 4;
        sb.append("\t\tdefault: ").append(n + codeIterator.s32bitAt(i)).append("\n");
        i += 4;
        final int n2 = codeIterator.s32bitAt(i) * 8;
        for (i += 4; i < n2 + i; i += 8) {
            sb.append("\t\t").append(codeIterator.s32bitAt(i)).append(": ").append(codeIterator.s32bitAt(i + 4) + n).append("\n");
        }
        sb.setCharAt(sb.length() - 1, '}');
        return sb.toString();
    }
    
    private static String tableSwitch(final CodeIterator codeIterator, final int n) {
        final StringBuffer sb = new StringBuffer("tableswitch {\n");
        int i = (n & 0xFFFFFFFC) + 4;
        sb.append("\t\tdefault: ").append(n + codeIterator.s32bitAt(i)).append("\n");
        i += 4;
        final int s32bit = codeIterator.s32bitAt(i);
        i += 4;
        final int n2 = (codeIterator.s32bitAt(i) - s32bit + 1) * 4;
        i += 4;
        for (int n3 = n2 + i, n4 = s32bit; i < n3; i += 4, ++n4) {
            sb.append("\t\t").append(n4).append(": ").append(codeIterator.s32bitAt(i) + n).append("\n");
        }
        sb.setCharAt(sb.length() - 1, '}');
        return sb.toString();
    }
    
    private static String ldc(final ConstPool constPool, final int n) {
        final int tag = constPool.getTag(n);
        switch (tag) {
            case 8: {
                return "#" + n + " = \"" + constPool.getStringInfo(n) + "\"";
            }
            case 3: {
                return "#" + n + " = int " + constPool.getIntegerInfo(n);
            }
            case 4: {
                return "#" + n + " = float " + constPool.getFloatInfo(n);
            }
            case 5: {
                return "#" + n + " = long " + constPool.getLongInfo(n);
            }
            case 6: {
                return "#" + n + " = double " + constPool.getDoubleInfo(n);
            }
            case 7: {
                return classInfo(constPool, n);
            }
            default: {
                throw new RuntimeException("bad LDC: " + tag);
            }
        }
    }
    
    static {
        InstructionPrinter.opcodes = Mnemonic.OPCODE;
    }
}
