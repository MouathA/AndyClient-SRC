package com.viaversion.viaversion.libs.javassist.expr;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public abstract class Expr implements Opcode
{
    int currentPos;
    CodeIterator iterator;
    CtClass thisClass;
    MethodInfo thisMethod;
    boolean edited;
    int maxLocals;
    int maxStack;
    static final String javaLangObject = "java.lang.Object";
    
    protected Expr(final int currentPos, final CodeIterator iterator, final CtClass thisClass, final MethodInfo thisMethod) {
        this.currentPos = currentPos;
        this.iterator = iterator;
        this.thisClass = thisClass;
        this.thisMethod = thisMethod;
    }
    
    public CtClass getEnclosingClass() {
        return this.thisClass;
    }
    
    protected final ConstPool getConstPool() {
        return this.thisMethod.getConstPool();
    }
    
    protected final boolean edited() {
        return this.edited;
    }
    
    protected final int locals() {
        return this.maxLocals;
    }
    
    protected final int stack() {
        return this.maxStack;
    }
    
    protected final boolean withinStatic() {
        return (this.thisMethod.getAccessFlags() & 0x8) != 0x0;
    }
    
    public CtBehavior where() {
        final MethodInfo thisMethod = this.thisMethod;
        final CtBehavior[] declaredBehaviors = this.thisClass.getDeclaredBehaviors();
        for (int i = declaredBehaviors.length - 1; i >= 0; --i) {
            if (declaredBehaviors[i].getMethodInfo2() == thisMethod) {
                return declaredBehaviors[i];
            }
        }
        final CtConstructor classInitializer = this.thisClass.getClassInitializer();
        if (classInitializer != null && classInitializer.getMethodInfo2() == thisMethod) {
            return classInitializer;
        }
        for (int j = declaredBehaviors.length - 1; j >= 0; --j) {
            if (this.thisMethod.getName().equals(declaredBehaviors[j].getMethodInfo2().getName()) && this.thisMethod.getDescriptor().equals(declaredBehaviors[j].getMethodInfo2().getDescriptor())) {
                return declaredBehaviors[j];
            }
        }
        throw new RuntimeException("fatal: not found");
    }
    
    public CtClass[] mayThrow() {
        final ClassPool classPool = this.thisClass.getClassPool();
        final ConstPool constPool = this.thisMethod.getConstPool();
        final LinkedList list = new LinkedList();
        final ExceptionTable exceptionTable = this.thisMethod.getCodeAttribute().getExceptionTable();
        final int currentPos = this.currentPos;
        int size = exceptionTable.size();
        while (0 < 0) {
            if (exceptionTable.startPc(0) <= currentPos && currentPos < exceptionTable.endPc(0)) {
                final int catchType = exceptionTable.catchType(0);
                if (catchType > 0) {
                    addClass(list, classPool.get(constPool.getClassInfo(catchType)));
                }
            }
            int n = 0;
            ++n;
        }
        final ExceptionsAttribute exceptionsAttribute = this.thisMethod.getExceptionsAttribute();
        if (exceptionsAttribute != null) {
            final String[] exceptions = exceptionsAttribute.getExceptions();
            if (exceptions != null) {
                while (0 < exceptions.length) {
                    addClass(list, classPool.get(exceptions[0]));
                    ++size;
                }
            }
        }
        return (CtClass[])list.toArray(new CtClass[list.size()]);
    }
    
    private static void addClass(final List list, final CtClass ctClass) {
        if (list.contains(ctClass)) {
            return;
        }
        list.add(ctClass);
    }
    
    public int indexOfBytecode() {
        return this.currentPos;
    }
    
    public int getLineNumber() {
        return this.thisMethod.getLineNumber(this.currentPos);
    }
    
    public String getFileName() {
        final ClassFile classFile2 = this.thisClass.getClassFile2();
        if (classFile2 == null) {
            return null;
        }
        return classFile2.getSourceFile();
    }
    
    static final boolean checkResultValue(final CtClass ctClass, final String s) throws CannotCompileException {
        final boolean b = s.indexOf("$_") >= 0;
        if (!b && ctClass != CtClass.voidType) {
            throw new CannotCompileException("the resulting value is not stored in $_");
        }
        return b;
    }
    
    static final void storeStack(final CtClass[] array, final boolean b, final int n, final Bytecode bytecode) {
        storeStack0(0, array.length, array, n + 1, bytecode);
        if (b) {
            bytecode.addOpcode(1);
        }
        bytecode.addAstore(n);
    }
    
    private static void storeStack0(final int n, final int n2, final CtClass[] array, final int n3, final Bytecode bytecode) {
        if (n >= n2) {
            return;
        }
        final CtClass ctClass = array[n];
        if (ctClass instanceof CtPrimitiveType) {
            ((CtPrimitiveType)ctClass).getDataSize();
        }
        storeStack0(n + 1, n2, array, n3 + 1, bytecode);
        bytecode.addStore(n3, ctClass);
    }
    
    public abstract void replace(final String p0) throws CannotCompileException;
    
    public void replace(final String s, final ExprEditor exprEditor) throws CannotCompileException {
        this.replace(s);
        if (exprEditor != null) {
            this.runEditor(exprEditor, this.iterator);
        }
    }
    
    protected void replace0(int position, final Bytecode bytecode, final int n) throws BadBytecode {
        final byte[] value = bytecode.get();
        this.edited = true;
        final int n2 = value.length - n;
        while (0 < n) {
            this.iterator.writeByte(0, position + 0);
            int n3 = 0;
            ++n3;
        }
        if (n2 > 0) {
            position = this.iterator.insertGapAt(position, n2, false).position;
        }
        this.iterator.write(value, position);
        this.iterator.insert(bytecode.getExceptionTable(), position);
        this.maxLocals = bytecode.getMaxLocals();
        this.maxStack = bytecode.getMaxStack();
    }
    
    protected void runEditor(final ExprEditor exprEditor, final CodeIterator codeIterator) throws CannotCompileException {
        final CodeAttribute value = codeIterator.get();
        final int maxLocals = value.getMaxLocals();
        final int maxStack = value.getMaxStack();
        final int locals = this.locals();
        value.setMaxStack(this.stack());
        value.setMaxLocals(locals);
        final ExprEditor.LoopContext loopContext = new ExprEditor.LoopContext(locals);
        final int codeLength = codeIterator.getCodeLength();
        final int lookAhead = codeIterator.lookAhead();
        codeIterator.move(this.currentPos);
        if (exprEditor.doit(this.thisClass, this.thisMethod, loopContext, codeIterator, lookAhead)) {
            this.edited = true;
        }
        codeIterator.move(lookAhead + codeIterator.getCodeLength() - codeLength);
        value.setMaxLocals(maxLocals);
        value.setMaxStack(maxStack);
        this.maxLocals = loopContext.maxLocals;
        this.maxStack += loopContext.maxStack;
    }
}
