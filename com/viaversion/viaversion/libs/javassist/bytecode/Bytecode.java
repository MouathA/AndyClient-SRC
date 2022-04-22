package com.viaversion.viaversion.libs.javassist.bytecode;

import com.viaversion.viaversion.libs.javassist.*;

public class Bytecode extends ByteVector implements Cloneable, Opcode
{
    public static final CtClass THIS;
    ConstPool constPool;
    int maxStack;
    int maxLocals;
    ExceptionTable tryblocks;
    private int stackDepth;
    
    public Bytecode(final ConstPool constPool, final int maxStack, final int maxLocals) {
        this.constPool = constPool;
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.tryblocks = new ExceptionTable(constPool);
        this.stackDepth = 0;
    }
    
    public Bytecode(final ConstPool constPool) {
        this(constPool, 0, 0);
    }
    
    @Override
    public Object clone() {
        final Bytecode bytecode = (Bytecode)super.clone();
        bytecode.tryblocks = (ExceptionTable)this.tryblocks.clone();
        return bytecode;
    }
    
    public ConstPool getConstPool() {
        return this.constPool;
    }
    
    public ExceptionTable getExceptionTable() {
        return this.tryblocks;
    }
    
    public CodeAttribute toCodeAttribute() {
        return new CodeAttribute(this.constPool, this.maxStack, this.maxLocals, this.get(), this.tryblocks);
    }
    
    public int length() {
        return this.getSize();
    }
    
    public byte[] get() {
        return this.copy();
    }
    
    public int getMaxStack() {
        return this.maxStack;
    }
    
    public void setMaxStack(final int maxStack) {
        this.maxStack = maxStack;
    }
    
    public int getMaxLocals() {
        return this.maxLocals;
    }
    
    public void setMaxLocals(final int maxLocals) {
        this.maxLocals = maxLocals;
    }
    
    public void setMaxLocals(final boolean b, final CtClass[] array, int maxLocals) {
        if (!b) {
            ++maxLocals;
        }
        if (array != null) {
            final CtClass doubleType = CtClass.doubleType;
            final CtClass longType = CtClass.longType;
            while (0 < array.length) {
                final CtClass ctClass = array[0];
                if (ctClass == doubleType || ctClass == longType) {
                    maxLocals += 2;
                }
                else {
                    ++maxLocals;
                }
                int n = 0;
                ++n;
            }
        }
        this.maxLocals = maxLocals;
    }
    
    public void incMaxLocals(final int n) {
        this.maxLocals += n;
    }
    
    public void addExceptionHandler(final int n, final int n2, final int n3, final CtClass ctClass) {
        this.addExceptionHandler(n, n2, n3, this.constPool.addClassInfo(ctClass));
    }
    
    public void addExceptionHandler(final int n, final int n2, final int n3, final String s) {
        this.addExceptionHandler(n, n2, n3, this.constPool.addClassInfo(s));
    }
    
    public void addExceptionHandler(final int n, final int n2, final int n3, final int n4) {
        this.tryblocks.add(n, n2, n3, n4);
    }
    
    public int currentPc() {
        return this.getSize();
    }
    
    @Override
    public int read(final int n) {
        return super.read(n);
    }
    
    public int read16bit(final int n) {
        return (this.read(n) << 8) + (this.read(n + 1) & 0xFF);
    }
    
    public int read32bit(final int n) {
        return (this.read16bit(n) << 16) + (this.read16bit(n + 2) & 0xFFFF);
    }
    
    @Override
    public void write(final int n, final int n2) {
        super.write(n, n2);
    }
    
    public void write16bit(final int n, final int n2) {
        this.write(n, n2 >> 8);
        this.write(n + 1, n2);
    }
    
    public void write32bit(final int n, final int n2) {
        this.write16bit(n, n2 >> 16);
        this.write16bit(n + 2, n2);
    }
    
    @Override
    public void add(final int n) {
        super.add(n);
    }
    
    public void add32bit(final int n) {
        this.add(n >> 24, n >> 16, n >> 8, n);
    }
    
    @Override
    public void addGap(final int n) {
        super.addGap(n);
    }
    
    public void addOpcode(final int n) {
        this.add(n);
        this.growStack(Bytecode.STACK_GROW[n]);
    }
    
    public void growStack(final int n) {
        this.setStackDepth(this.stackDepth + n);
    }
    
    public int getStackDepth() {
        return this.stackDepth;
    }
    
    public void setStackDepth(final int stackDepth) {
        this.stackDepth = stackDepth;
        if (this.stackDepth > this.maxStack) {
            this.maxStack = this.stackDepth;
        }
    }
    
    public void addIndex(final int n) {
        this.add(n >> 8, n);
    }
    
    public void addAload(final int n) {
        if (n < 4) {
            this.addOpcode(42 + n);
        }
        else if (n < 256) {
            this.addOpcode(25);
            this.add(n);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(25);
            this.addIndex(n);
        }
    }
    
    public void addAstore(final int n) {
        if (n < 4) {
            this.addOpcode(75 + n);
        }
        else if (n < 256) {
            this.addOpcode(58);
            this.add(n);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(58);
            this.addIndex(n);
        }
    }
    
    public void addIconst(final int n) {
        if (n < 6 && -2 < n) {
            this.addOpcode(3 + n);
        }
        else if (n <= 127 && -128 <= n) {
            this.addOpcode(16);
            this.add(n);
        }
        else if (n <= 32767 && -32768 <= n) {
            this.addOpcode(17);
            this.add(n >> 8);
            this.add(n);
        }
        else {
            this.addLdc(this.constPool.addIntegerInfo(n));
        }
    }
    
    public void addConstZero(final CtClass ctClass) {
        if (ctClass.isPrimitive()) {
            if (ctClass == CtClass.longType) {
                this.addOpcode(9);
            }
            else if (ctClass == CtClass.floatType) {
                this.addOpcode(11);
            }
            else if (ctClass == CtClass.doubleType) {
                this.addOpcode(14);
            }
            else {
                if (ctClass == CtClass.voidType) {
                    throw new RuntimeException("void type?");
                }
                this.addOpcode(3);
            }
        }
        else {
            this.addOpcode(1);
        }
    }
    
    public void addIload(final int n) {
        if (n < 4) {
            this.addOpcode(26 + n);
        }
        else if (n < 256) {
            this.addOpcode(21);
            this.add(n);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(21);
            this.addIndex(n);
        }
    }
    
    public void addIstore(final int n) {
        if (n < 4) {
            this.addOpcode(59 + n);
        }
        else if (n < 256) {
            this.addOpcode(54);
            this.add(n);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(54);
            this.addIndex(n);
        }
    }
    
    public void addLconst(final long n) {
        if (n == 0L || n == 1L) {
            this.addOpcode(9 + (int)n);
        }
        else {
            this.addLdc2w(n);
        }
    }
    
    public void addLload(final int n) {
        if (n < 4) {
            this.addOpcode(30 + n);
        }
        else if (n < 256) {
            this.addOpcode(22);
            this.add(n);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(22);
            this.addIndex(n);
        }
    }
    
    public void addLstore(final int n) {
        if (n < 4) {
            this.addOpcode(63 + n);
        }
        else if (n < 256) {
            this.addOpcode(55);
            this.add(n);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(55);
            this.addIndex(n);
        }
    }
    
    public void addDconst(final double n) {
        if (n == 0.0 || n == 1.0) {
            this.addOpcode(14 + (int)n);
        }
        else {
            this.addLdc2w(n);
        }
    }
    
    public void addDload(final int n) {
        if (n < 4) {
            this.addOpcode(38 + n);
        }
        else if (n < 256) {
            this.addOpcode(24);
            this.add(n);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(24);
            this.addIndex(n);
        }
    }
    
    public void addDstore(final int n) {
        if (n < 4) {
            this.addOpcode(71 + n);
        }
        else if (n < 256) {
            this.addOpcode(57);
            this.add(n);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(57);
            this.addIndex(n);
        }
    }
    
    public void addFconst(final float n) {
        if (n == 0.0f || n == 1.0f || n == 2.0f) {
            this.addOpcode(11 + (int)n);
        }
        else {
            this.addLdc(this.constPool.addFloatInfo(n));
        }
    }
    
    public void addFload(final int n) {
        if (n < 4) {
            this.addOpcode(34 + n);
        }
        else if (n < 256) {
            this.addOpcode(23);
            this.add(n);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(23);
            this.addIndex(n);
        }
    }
    
    public void addFstore(final int n) {
        if (n < 4) {
            this.addOpcode(67 + n);
        }
        else if (n < 256) {
            this.addOpcode(56);
            this.add(n);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(56);
            this.addIndex(n);
        }
    }
    
    public int addLoad(final int n, final CtClass ctClass) {
        if (ctClass.isPrimitive()) {
            if (ctClass == CtClass.booleanType || ctClass == CtClass.charType || ctClass == CtClass.byteType || ctClass == CtClass.shortType || ctClass == CtClass.intType) {
                this.addIload(n);
            }
            else {
                if (ctClass == CtClass.longType) {
                    this.addLload(n);
                    return 2;
                }
                if (ctClass == CtClass.floatType) {
                    this.addFload(n);
                }
                else {
                    if (ctClass == CtClass.doubleType) {
                        this.addDload(n);
                        return 2;
                    }
                    throw new RuntimeException("void type?");
                }
            }
        }
        else {
            this.addAload(n);
        }
        return 1;
    }
    
    public int addStore(final int n, final CtClass ctClass) {
        if (ctClass.isPrimitive()) {
            if (ctClass == CtClass.booleanType || ctClass == CtClass.charType || ctClass == CtClass.byteType || ctClass == CtClass.shortType || ctClass == CtClass.intType) {
                this.addIstore(n);
            }
            else {
                if (ctClass == CtClass.longType) {
                    this.addLstore(n);
                    return 2;
                }
                if (ctClass == CtClass.floatType) {
                    this.addFstore(n);
                }
                else {
                    if (ctClass == CtClass.doubleType) {
                        this.addDstore(n);
                        return 2;
                    }
                    throw new RuntimeException("void type?");
                }
            }
        }
        else {
            this.addAstore(n);
        }
        return 1;
    }
    
    public int addLoadParameters(final CtClass[] array, final int n) {
        if (array != null) {
            while (0 < array.length) {
                final int n2 = 0 + this.addLoad(0 + n, array[0]);
                int n3 = 0;
                ++n3;
            }
        }
        return 0;
    }
    
    public void addCheckcast(final CtClass ctClass) {
        this.addOpcode(192);
        this.addIndex(this.constPool.addClassInfo(ctClass));
    }
    
    public void addCheckcast(final String s) {
        this.addOpcode(192);
        this.addIndex(this.constPool.addClassInfo(s));
    }
    
    public void addInstanceof(final String s) {
        this.addOpcode(193);
        this.addIndex(this.constPool.addClassInfo(s));
    }
    
    public void addGetfield(final CtClass ctClass, final String s, final String s2) {
        this.add(180);
        this.addIndex(this.constPool.addFieldrefInfo(this.constPool.addClassInfo(ctClass), s, s2));
        this.growStack(Descriptor.dataSize(s2) - 1);
    }
    
    public void addGetfield(final String s, final String s2, final String s3) {
        this.add(180);
        this.addIndex(this.constPool.addFieldrefInfo(this.constPool.addClassInfo(s), s2, s3));
        this.growStack(Descriptor.dataSize(s3) - 1);
    }
    
    public void addGetstatic(final CtClass ctClass, final String s, final String s2) {
        this.add(178);
        this.addIndex(this.constPool.addFieldrefInfo(this.constPool.addClassInfo(ctClass), s, s2));
        this.growStack(Descriptor.dataSize(s2));
    }
    
    public void addGetstatic(final String s, final String s2, final String s3) {
        this.add(178);
        this.addIndex(this.constPool.addFieldrefInfo(this.constPool.addClassInfo(s), s2, s3));
        this.growStack(Descriptor.dataSize(s3));
    }
    
    public void addInvokespecial(final CtClass ctClass, final String s, final CtClass ctClass2, final CtClass[] array) {
        this.addInvokespecial(ctClass, s, Descriptor.ofMethod(ctClass2, array));
    }
    
    public void addInvokespecial(final CtClass ctClass, final String s, final String s2) {
        this.addInvokespecial(ctClass != null && ctClass.isInterface(), this.constPool.addClassInfo(ctClass), s, s2);
    }
    
    public void addInvokespecial(final String s, final String s2, final String s3) {
        this.addInvokespecial(false, this.constPool.addClassInfo(s), s2, s3);
    }
    
    public void addInvokespecial(final int n, final String s, final String s2) {
        this.addInvokespecial(false, n, s, s2);
    }
    
    public void addInvokespecial(final boolean b, final int n, final String s, final String s2) {
        int n2;
        if (b) {
            n2 = this.constPool.addInterfaceMethodrefInfo(n, s, s2);
        }
        else {
            n2 = this.constPool.addMethodrefInfo(n, s, s2);
        }
        this.addInvokespecial(n2, s2);
    }
    
    public void addInvokespecial(final int n, final String s) {
        this.add(183);
        this.addIndex(n);
        this.growStack(Descriptor.dataSize(s) - 1);
    }
    
    public void addInvokestatic(final CtClass ctClass, final String s, final CtClass ctClass2, final CtClass[] array) {
        this.addInvokestatic(ctClass, s, Descriptor.ofMethod(ctClass2, array));
    }
    
    public void addInvokestatic(final CtClass ctClass, final String s, final String s2) {
        if (ctClass != Bytecode.THIS) {
            ctClass.isInterface();
        }
        this.addInvokestatic(this.constPool.addClassInfo(ctClass), s, s2, false);
    }
    
    public void addInvokestatic(final String s, final String s2, final String s3) {
        this.addInvokestatic(this.constPool.addClassInfo(s), s2, s3);
    }
    
    public void addInvokestatic(final int n, final String s, final String s2) {
        this.addInvokestatic(n, s, s2, false);
    }
    
    private void addInvokestatic(final int n, final String s, final String s2, final boolean b) {
        this.add(184);
        int n2;
        if (b) {
            n2 = this.constPool.addInterfaceMethodrefInfo(n, s, s2);
        }
        else {
            n2 = this.constPool.addMethodrefInfo(n, s, s2);
        }
        this.addIndex(n2);
        this.growStack(Descriptor.dataSize(s2));
    }
    
    public void addInvokevirtual(final CtClass ctClass, final String s, final CtClass ctClass2, final CtClass[] array) {
        this.addInvokevirtual(ctClass, s, Descriptor.ofMethod(ctClass2, array));
    }
    
    public void addInvokevirtual(final CtClass ctClass, final String s, final String s2) {
        this.addInvokevirtual(this.constPool.addClassInfo(ctClass), s, s2);
    }
    
    public void addInvokevirtual(final String s, final String s2, final String s3) {
        this.addInvokevirtual(this.constPool.addClassInfo(s), s2, s3);
    }
    
    public void addInvokevirtual(final int n, final String s, final String s2) {
        this.add(182);
        this.addIndex(this.constPool.addMethodrefInfo(n, s, s2));
        this.growStack(Descriptor.dataSize(s2) - 1);
    }
    
    public void addInvokeinterface(final CtClass ctClass, final String s, final CtClass ctClass2, final CtClass[] array, final int n) {
        this.addInvokeinterface(ctClass, s, Descriptor.ofMethod(ctClass2, array), n);
    }
    
    public void addInvokeinterface(final CtClass ctClass, final String s, final String s2, final int n) {
        this.addInvokeinterface(this.constPool.addClassInfo(ctClass), s, s2, n);
    }
    
    public void addInvokeinterface(final String s, final String s2, final String s3, final int n) {
        this.addInvokeinterface(this.constPool.addClassInfo(s), s2, s3, n);
    }
    
    public void addInvokeinterface(final int n, final String s, final String s2, final int n2) {
        this.add(185);
        this.addIndex(this.constPool.addInterfaceMethodrefInfo(n, s, s2));
        this.add(n2);
        this.add(0);
        this.growStack(Descriptor.dataSize(s2) - 1);
    }
    
    public void addInvokedynamic(final int n, final String s, final String s2) {
        final int addInvokeDynamicInfo = this.constPool.addInvokeDynamicInfo(n, this.constPool.addNameAndTypeInfo(s, s2));
        this.add(186);
        this.addIndex(addInvokeDynamicInfo);
        this.add(0, 0);
        this.growStack(Descriptor.dataSize(s2));
    }
    
    public void addLdc(final String s) {
        this.addLdc(this.constPool.addStringInfo(s));
    }
    
    public void addLdc(final int n) {
        if (n > 255) {
            this.addOpcode(19);
            this.addIndex(n);
        }
        else {
            this.addOpcode(18);
            this.add(n);
        }
    }
    
    public void addLdc2w(final long n) {
        this.addOpcode(20);
        this.addIndex(this.constPool.addLongInfo(n));
    }
    
    public void addLdc2w(final double n) {
        this.addOpcode(20);
        this.addIndex(this.constPool.addDoubleInfo(n));
    }
    
    public void addNew(final CtClass ctClass) {
        this.addOpcode(187);
        this.addIndex(this.constPool.addClassInfo(ctClass));
    }
    
    public void addNew(final String s) {
        this.addOpcode(187);
        this.addIndex(this.constPool.addClassInfo(s));
    }
    
    public void addAnewarray(final String s) {
        this.addOpcode(189);
        this.addIndex(this.constPool.addClassInfo(s));
    }
    
    public void addAnewarray(final CtClass ctClass, final int n) {
        this.addIconst(n);
        this.addOpcode(189);
        this.addIndex(this.constPool.addClassInfo(ctClass));
    }
    
    public void addNewarray(final int n, final int n2) {
        this.addIconst(n2);
        this.addOpcode(188);
        this.add(n);
    }
    
    public int addMultiNewarray(final CtClass ctClass, final int[] array) {
        final int length = array.length;
        while (0 < length) {
            this.addIconst(array[0]);
            int n = 0;
            ++n;
        }
        this.growStack(length);
        return this.addMultiNewarray(ctClass, length);
    }
    
    public int addMultiNewarray(final CtClass ctClass, final int n) {
        this.add(197);
        this.addIndex(this.constPool.addClassInfo(ctClass));
        this.add(n);
        this.growStack(1 - n);
        return n;
    }
    
    public int addMultiNewarray(final String s, final int n) {
        this.add(197);
        this.addIndex(this.constPool.addClassInfo(s));
        this.add(n);
        this.growStack(1 - n);
        return n;
    }
    
    public void addPutfield(final CtClass ctClass, final String s, final String s2) {
        this.addPutfield0(ctClass, null, s, s2);
    }
    
    public void addPutfield(final String s, final String s2, final String s3) {
        this.addPutfield0(null, s, s2, s3);
    }
    
    private void addPutfield0(final CtClass ctClass, final String s, final String s2, final String s3) {
        this.add(181);
        this.addIndex(this.constPool.addFieldrefInfo((s == null) ? this.constPool.addClassInfo(ctClass) : this.constPool.addClassInfo(s), s2, s3));
        this.growStack(-1 - Descriptor.dataSize(s3));
    }
    
    public void addPutstatic(final CtClass ctClass, final String s, final String s2) {
        this.addPutstatic0(ctClass, null, s, s2);
    }
    
    public void addPutstatic(final String s, final String s2, final String s3) {
        this.addPutstatic0(null, s, s2, s3);
    }
    
    private void addPutstatic0(final CtClass ctClass, final String s, final String s2, final String s3) {
        this.add(179);
        this.addIndex(this.constPool.addFieldrefInfo((s == null) ? this.constPool.addClassInfo(ctClass) : this.constPool.addClassInfo(s), s2, s3));
        this.growStack(-Descriptor.dataSize(s3));
    }
    
    public void addReturn(final CtClass ctClass) {
        if (ctClass == null) {
            this.addOpcode(177);
        }
        else if (ctClass.isPrimitive()) {
            this.addOpcode(((CtPrimitiveType)ctClass).getReturnOp());
        }
        else {
            this.addOpcode(176);
        }
    }
    
    public void addRet(final int n) {
        if (n < 256) {
            this.addOpcode(169);
            this.add(n);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(169);
            this.addIndex(n);
        }
    }
    
    public void addPrintln(final String s) {
        this.addGetstatic("java.lang.System", "err", "Ljava/io/PrintStream;");
        this.addLdc(s);
        this.addInvokevirtual("java.io.PrintStream", "println", "(Ljava/lang/String;)V");
    }
    
    @Override
    public void add(final int n, final int n2, final int n3, final int n4) {
        super.add(n, n2, n3, n4);
    }
    
    @Override
    public void add(final int n, final int n2) {
        super.add(n, n2);
    }
    
    static {
        THIS = ConstPool.THIS;
    }
}
