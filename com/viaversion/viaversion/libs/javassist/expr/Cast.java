package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.compiler.ast.*;
import com.viaversion.viaversion.libs.javassist.compiler.*;

public class Cast extends Expr
{
    protected Cast(final int n, final CodeIterator codeIterator, final CtClass ctClass, final MethodInfo methodInfo) {
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
        final CtClass type = this.getType();
        final int maxLocals = value.getMaxLocals();
        javac.recordParams("java.lang.Object", array, true, maxLocals, this.withinStatic());
        final int recordReturnType = javac.recordReturnType(type, true);
        javac.recordProceed(new ProceedForCast(u16bit, type));
        Expr.checkResultValue(type, s);
        final Bytecode bytecode = javac.getBytecode();
        Expr.storeStack(array, true, maxLocals, bytecode);
        javac.recordLocalVariables(value, currentPos);
        bytecode.addConstZero(type);
        bytecode.addStore(recordReturnType, type);
        javac.compileStmnt(s);
        bytecode.addLoad(recordReturnType, type);
        this.replace0(currentPos, bytecode, 3);
    }
    
    static class ProceedForCast implements ProceedHandler
    {
        int index;
        CtClass retType;
        
        ProceedForCast(final int index, final CtClass retType) {
            this.index = index;
            this.retType = retType;
        }
        
        @Override
        public void doit(final JvstCodeGen jvstCodeGen, final Bytecode bytecode, final ASTList list) throws CompileError {
            if (jvstCodeGen.getMethodArgsLength(list) != 1) {
                throw new CompileError("$proceed() cannot take more than one parameter for cast");
            }
            jvstCodeGen.atMethodArgs(list, new int[1], new int[1], new String[1]);
            bytecode.addOpcode(192);
            bytecode.addIndex(this.index);
            jvstCodeGen.setType(this.retType);
        }
        
        @Override
        public void setReturnType(final JvstTypeChecker jvstTypeChecker, final ASTList list) throws CompileError {
            jvstTypeChecker.atMethodArgs(list, new int[1], new int[1], new String[1]);
            jvstTypeChecker.setType(this.retType);
        }
    }
}
