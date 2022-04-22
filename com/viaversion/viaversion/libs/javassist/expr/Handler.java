package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.compiler.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class Handler extends Expr
{
    private static String EXCEPTION_NAME;
    private ExceptionTable etable;
    private int index;
    
    protected Handler(final ExceptionTable etable, final int index, final CodeIterator codeIterator, final CtClass ctClass, final MethodInfo methodInfo) {
        super(etable.handlerPc(index), codeIterator, ctClass, methodInfo);
        this.etable = etable;
        this.index = index;
    }
    
    @Override
    public CtBehavior where() {
        return super.where();
    }
    
    @Override
    public int getLineNumber() {
        return super.getLineNumber();
    }
    
    @Override
    public String getFileName() {
        return super.getFileName();
    }
    
    @Override
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }
    
    public CtClass getType() throws NotFoundException {
        final int catchType = this.etable.catchType(this.index);
        if (catchType == 0) {
            return null;
        }
        return this.thisClass.getClassPool().getCtClass(this.getConstPool().getClassInfo(catchType));
    }
    
    public boolean isFinally() {
        return this.etable.catchType(this.index) == 0;
    }
    
    @Override
    public void replace(final String s) throws CannotCompileException {
        throw new RuntimeException("not implemented yet");
    }
    
    public void insertBefore(final String s) throws CannotCompileException {
        this.edited = true;
        this.getConstPool();
        final CodeAttribute value = this.iterator.get();
        final Javac javac = new Javac(this.thisClass);
        final Bytecode bytecode = javac.getBytecode();
        bytecode.setStackDepth(1);
        bytecode.setMaxLocals(value.getMaxLocals());
        final CtClass type = this.getType();
        final int recordVariable = javac.recordVariable(type, Handler.EXCEPTION_NAME);
        javac.recordReturnType(type, false);
        bytecode.addAstore(recordVariable);
        javac.compileStmnt(s);
        bytecode.addAload(recordVariable);
        final int handlerPc = this.etable.handlerPc(this.index);
        bytecode.addOpcode(167);
        bytecode.addIndex(handlerPc - this.iterator.getCodeLength() - bytecode.currentPc() + 1);
        this.maxStack = bytecode.getMaxStack();
        this.maxLocals = bytecode.getMaxLocals();
        final int append = this.iterator.append(bytecode.get());
        this.iterator.append(bytecode.getExceptionTable(), append);
        this.etable.setHandlerPc(this.index, append);
    }
    
    static {
        Handler.EXCEPTION_NAME = "$1";
    }
}
