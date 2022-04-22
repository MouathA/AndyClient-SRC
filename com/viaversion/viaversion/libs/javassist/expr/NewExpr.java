package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.compiler.ast.*;
import com.viaversion.viaversion.libs.javassist.compiler.*;

public class NewExpr extends Expr
{
    String newTypeName;
    int newPos;
    
    protected NewExpr(final int n, final CodeIterator codeIterator, final CtClass ctClass, final MethodInfo methodInfo, final String newTypeName, final int newPos) {
        super(n, codeIterator, ctClass, methodInfo);
        this.newTypeName = newTypeName;
        this.newPos = newPos;
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
    
    private CtClass getCtClass() throws NotFoundException {
        return this.thisClass.getClassPool().get(this.newTypeName);
    }
    
    public String getClassName() {
        return this.newTypeName;
    }
    
    public String getSignature() {
        return this.getConstPool().getMethodrefType(this.iterator.u16bitAt(this.currentPos + 1));
    }
    
    public CtConstructor getConstructor() throws NotFoundException {
        return this.getCtClass().getConstructor(this.getConstPool().getMethodrefType(this.iterator.u16bitAt(this.currentPos + 1)));
    }
    
    @Override
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }
    
    private int canReplace() throws CannotCompileException {
        final int byte1 = this.iterator.byteAt(this.newPos + 3);
        if (byte1 == 89) {
            return (this.iterator.byteAt(this.newPos + 4) == 94 && this.iterator.byteAt(this.newPos + 5) == 88) ? 6 : 4;
        }
        if (byte1 == 90 && this.iterator.byteAt(this.newPos + 4) == 95) {
            return 5;
        }
        return 3;
    }
    
    @Override
    public void replace(final String s) throws CannotCompileException {
        this.thisClass.getClassFile();
        final int newPos = this.newPos;
        final int u16bit = this.iterator.u16bitAt(newPos + 1);
        final int canReplace = this.canReplace();
        for (int n = newPos + canReplace, i = newPos; i < n; ++i) {
            this.iterator.writeByte(0, i);
        }
        final ConstPool constPool = this.getConstPool();
        final int currentPos = this.currentPos;
        final int u16bit2 = this.iterator.u16bitAt(currentPos + 1);
        final String methodrefType = constPool.getMethodrefType(u16bit2);
        final Javac javac = new Javac(this.thisClass);
        final ClassPool classPool = this.thisClass.getClassPool();
        final CodeAttribute value = this.iterator.get();
        final CtClass[] parameterTypes = Descriptor.getParameterTypes(methodrefType, classPool);
        final CtClass value2 = classPool.get(this.newTypeName);
        final int maxLocals = value.getMaxLocals();
        javac.recordParams(this.newTypeName, parameterTypes, true, maxLocals, this.withinStatic());
        final int recordReturnType = javac.recordReturnType(value2, true);
        javac.recordProceed(new ProceedForNew(value2, u16bit, u16bit2));
        Expr.checkResultValue(value2, s);
        final Bytecode bytecode = javac.getBytecode();
        Expr.storeStack(parameterTypes, true, maxLocals, bytecode);
        javac.recordLocalVariables(value, currentPos);
        bytecode.addConstZero(value2);
        bytecode.addStore(recordReturnType, value2);
        javac.compileStmnt(s);
        if (canReplace > 3) {
            bytecode.addAload(recordReturnType);
        }
        this.replace0(currentPos, bytecode, 3);
    }
    
    static class ProceedForNew implements ProceedHandler
    {
        CtClass newType;
        int newIndex;
        int methodIndex;
        
        ProceedForNew(final CtClass newType, final int newIndex, final int methodIndex) {
            this.newType = newType;
            this.newIndex = newIndex;
            this.methodIndex = methodIndex;
        }
        
        @Override
        public void doit(final JvstCodeGen jvstCodeGen, final Bytecode bytecode, final ASTList list) throws CompileError {
            bytecode.addOpcode(187);
            bytecode.addIndex(this.newIndex);
            bytecode.addOpcode(89);
            jvstCodeGen.atMethodCallCore(this.newType, "<init>", list, false, true, -1, null);
            jvstCodeGen.setType(this.newType);
        }
        
        @Override
        public void setReturnType(final JvstTypeChecker jvstTypeChecker, final ASTList list) throws CompileError {
            jvstTypeChecker.atMethodCallCore(this.newType, "<init>", list);
            jvstTypeChecker.setType(this.newType);
        }
    }
}
