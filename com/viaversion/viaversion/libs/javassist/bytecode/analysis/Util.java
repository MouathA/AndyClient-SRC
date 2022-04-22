package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class Util implements Opcode
{
    public static int getJumpTarget(int n, final CodeIterator codeIterator) {
        final int byte1 = codeIterator.byteAt(n);
        n += ((byte1 == 201 || byte1 == 200) ? codeIterator.s32bitAt(n + 1) : codeIterator.s16bitAt(n + 1));
        return n;
    }
    
    public static boolean isJumpInstruction(final int n) {
        return (n >= 153 && n <= 168) || n == 198 || n == 199 || n == 201 || n == 200;
    }
    
    public static boolean isGoto(final int n) {
        return n == 167 || n == 200;
    }
    
    public static boolean isJsr(final int n) {
        return n == 168 || n == 201;
    }
    
    public static boolean isReturn(final int n) {
        return n >= 172 && n <= 177;
    }
}
