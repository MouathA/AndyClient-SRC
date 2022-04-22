package com.viaversion.viaversion.libs.javassist.bytecode;

class CodeAnalyzer implements Opcode
{
    private ConstPool constPool;
    private CodeAttribute codeAttr;
    
    public CodeAnalyzer(final CodeAttribute codeAttr) {
        this.codeAttr = codeAttr;
        this.constPool = codeAttr.getConstPool();
    }
    
    public int computeMaxStack() throws BadBytecode {
        final CodeIterator iterator = this.codeAttr.iterator();
        final int codeLength = iterator.getCodeLength();
        final int[] array = new int[codeLength];
        this.constPool = this.codeAttr.getConstPool();
        this.initStack(array, this.codeAttr);
        while (true) {
            if (1 < codeLength) {
                if (array[1] < 0) {
                    this.visitBytecode(iterator, array, 1);
                }
                int n = 0;
                ++n;
            }
            else {
                if (!true) {
                    break;
                }
                continue;
            }
        }
        while (0 < codeLength) {
            if (array[0] > 1) {
                final int n = array[0];
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    private void initStack(final int[] array, final CodeAttribute codeAttribute) {
        array[0] = -1;
        final ExceptionTable exceptionTable = codeAttribute.getExceptionTable();
        if (exceptionTable != null) {
            while (0 < exceptionTable.size()) {
                array[exceptionTable.handlerPc(0)] = -2;
                int n = 0;
                ++n;
            }
        }
    }
    
    private void visitBytecode(final CodeIterator codeIterator, final int[] array, int next) throws BadBytecode {
        final int length = array.length;
        codeIterator.move(next);
        int visitInst = -array[next];
        final int[] array2 = { -1 };
        while (codeIterator.hasNext()) {
            next = codeIterator.next();
            array[next] = visitInst;
            final int byte1 = codeIterator.byteAt(next);
            visitInst = this.visitInst(byte1, codeIterator, next, visitInst);
            if (visitInst < 1) {
                throw new BadBytecode("stack underflow at " + next);
            }
            if (this.processBranch(byte1, codeIterator, next, length, array, visitInst, array2)) {
                break;
            }
            if (isEnd(byte1)) {
                break;
            }
            if (byte1 != 168 && byte1 != 201) {
                continue;
            }
            --visitInst;
        }
    }
    
    private boolean processBranch(final int n, final CodeIterator codeIterator, final int n2, final int n3, final int[] array, final int n4, final int[] array2) throws BadBytecode {
        if ((153 <= n && n <= 166) || n == 198 || n == 199) {
            this.checkTarget(n2, n2 + codeIterator.s16bitAt(n2 + 1), n3, array, n4);
        }
        else {
            switch (n) {
                case 167: {
                    this.checkTarget(n2, n2 + codeIterator.s16bitAt(n2 + 1), n3, array, n4);
                    return true;
                }
                case 200: {
                    this.checkTarget(n2, n2 + codeIterator.s32bitAt(n2 + 1), n3, array, n4);
                    return true;
                }
                case 168:
                case 201: {
                    int n5;
                    if (n == 168) {
                        n5 = n2 + codeIterator.s16bitAt(n2 + 1);
                    }
                    else {
                        n5 = n2 + codeIterator.s32bitAt(n2 + 1);
                    }
                    this.checkTarget(n2, n5, n3, array, n4);
                    if (array2[0] < 0) {
                        array2[0] = n4;
                        return false;
                    }
                    if (n4 == array2[0]) {
                        return false;
                    }
                    throw new BadBytecode("sorry, cannot compute this data flow due to JSR: " + n4 + "," + array2[0]);
                }
                case 169: {
                    if (array2[0] < 0) {
                        array2[0] = n4 + 1;
                        return false;
                    }
                    if (n4 + 1 == array2[0]) {
                        return true;
                    }
                    throw new BadBytecode("sorry, cannot compute this data flow due to RET: " + n4 + "," + array2[0]);
                }
                case 170:
                case 171: {
                    int n6 = (n2 & 0xFFFFFFFC) + 4;
                    this.checkTarget(n2, n2 + codeIterator.s32bitAt(n6), n3, array, n4);
                    if (n == 171) {
                        final int s32bit = codeIterator.s32bitAt(n6 + 4);
                        n6 += 12;
                        while (0 < s32bit) {
                            this.checkTarget(n2, n2 + codeIterator.s32bitAt(n6), n3, array, n4);
                            n6 += 8;
                            int s32bit2 = 0;
                            ++s32bit2;
                        }
                    }
                    else {
                        final int s32bit3 = codeIterator.s32bitAt(n6 + 4);
                        final int s32bit2 = codeIterator.s32bitAt(n6 + 8);
                        final int n7 = 0 - s32bit3 + 1;
                        n6 += 12;
                        while (0 < n7) {
                            this.checkTarget(n2, n2 + codeIterator.s32bitAt(n6), n3, array, n4);
                            n6 += 4;
                            int n8 = 0;
                            ++n8;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    private void checkTarget(final int n, final int n2, final int n3, final int[] array, final int n4) throws BadBytecode {
        if (n2 < 0 || n3 <= n2) {
            throw new BadBytecode("bad branch offset at " + n);
        }
        final int n5 = array[n2];
        if (n5 == 0) {
            array[n2] = -n4;
        }
        else if (n5 != n4 && n5 != -n4) {
            throw new BadBytecode("verification error (" + n4 + "," + n5 + ") at " + n);
        }
    }
    
    private static boolean isEnd(final int n) {
        return (172 <= n && n <= 177) || n == 191;
    }
    
    private int visitInst(int byte1, final CodeIterator codeIterator, final int n, int n2) throws BadBytecode {
        switch (byte1) {
            case 180: {
                n2 = 1 + (this.getFieldSize(codeIterator, n) - 1);
                return 1;
            }
            case 181: {
                n2 = 1 - (this.getFieldSize(codeIterator, n) + 1);
                return 1;
            }
            case 178: {
                n2 = 1 + this.getFieldSize(codeIterator, n);
                return 1;
            }
            case 179: {
                n2 = 1 - this.getFieldSize(codeIterator, n);
                return 1;
            }
            case 182:
            case 183: {
                n2 = 1 + (Descriptor.dataSize(this.constPool.getMethodrefType(codeIterator.u16bitAt(n + 1))) - 1);
                return 1;
            }
            case 184: {
                n2 = 1 + Descriptor.dataSize(this.constPool.getMethodrefType(codeIterator.u16bitAt(n + 1)));
                return 1;
            }
            case 185: {
                n2 = 1 + (Descriptor.dataSize(this.constPool.getInterfaceMethodrefType(codeIterator.u16bitAt(n + 1))) - 1);
                return 1;
            }
            case 186: {
                n2 = 1 + Descriptor.dataSize(this.constPool.getInvokeDynamicType(codeIterator.u16bitAt(n + 1)));
                return 1;
            }
            case 191: {
                return 1;
            }
            case 197: {
                n2 = 1 + (1 - codeIterator.byteAt(n + 3));
                return 1;
            }
            case 196: {
                byte1 = codeIterator.byteAt(n + 1);
                break;
            }
        }
        n2 = 1 + CodeAnalyzer.STACK_GROW[byte1];
        return 1;
    }
    
    private int getFieldSize(final CodeIterator codeIterator, final int n) {
        return Descriptor.dataSize(this.constPool.getFieldrefType(codeIterator.u16bitAt(n + 1)));
    }
}
