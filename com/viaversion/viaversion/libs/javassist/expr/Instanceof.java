package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.compiler.ast.*;
import com.viaversion.viaversion.libs.javassist.compiler.*;

public class Instanceof extends Expr
{
    protected Instanceof(final int n, final CodeIterator codeIterator, final CtClass ctClass, final MethodInfo methodInfo) {
        super(n, codeIterator, ctClass, methodInfo);
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
    
    public CtClass getType() throws NotFoundException {
        return this.thisClass.getClassPool().getCtClass(this.getConstPool().getClassInfo(this.iterator.u16bitAt(this.currentPos + 1)));
    }
    
    @Override
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }
    
    @Override
    public void replace(final String s) throws CannotCompileException {
        this.thisClass.getClassFile();
        this.getConstPool();
        final int currentPos = this.currentPos;
        final int u16bit = this.iterator.u16bitAt(currentPos + 1);
        final Javac javac = new Javac(this.thisClass);
        final ClassPool classPool = this.thisClass.getClassPool();
        final CodeAttribute value = this.iterator.get();
        final CtClass[] array = { classPool.get("java.lang.Object") };
        final CtClass booleanType = CtClass.booleanType;
        final int maxLocals = value.getMaxLocals();
        javac.recordParams("java.lang.Object", array, true, maxLocals, this.withinStatic());
        final int recordReturnType = javac.recordReturnType(booleanType, true);
        javac.recordProceed(new ProceedForInstanceof(u16bit));
        javac.recordType(this.getType());
        Expr.checkResultValue(booleanType, s);
        final Bytecode bytecode = javac.getBytecode();
        Expr.storeStack(array, true, maxLocals, bytecode);
        javac.recordLocalVariables(value, currentPos);
        bytecode.addConstZero(booleanType);
        bytecode.addStore(recordReturnType, booleanType);
        javac.compileStmnt(s);
        bytecode.addLoad(recordReturnType, booleanType);
        this.replace0(currentPos, bytecode, 3);
    }
    
    static class ProceedForInstanceof implements ProceedHandler
    {
        int index;
        
        ProceedForInstanceof(final int index) {
            this.index = index;
        }
        
        @Override
        public void doit(final JvstCodeGen jvstCodeGen, final Bytecode bytecode, final ASTList list) throws CompileError {
            if (jvstCodeGen.getMethodArgsLength(list) != 1) {
                throw new CompileError("$proceed() cannot take more than one parameter for instanceof");
            }
            jvstCodeGen.atMethodArgs(list, new int[1], new int[1], new String[1]);
            bytecode.addOpcode(193);
            bytecode.addIndex(this.index);
            jvstCodeGen.setType(CtClass.booleanType);
        }
        
        @Override
        public void setReturnType(final JvstTypeChecker jvstTypeChecker, final ASTList list) throws CompileError {
            jvstTypeChecker.atMethodArgs(list, new int[1], new int[1], new String[1]);
            jvstTypeChecker.setType(CtClass.booleanType);
        }
    }
}
