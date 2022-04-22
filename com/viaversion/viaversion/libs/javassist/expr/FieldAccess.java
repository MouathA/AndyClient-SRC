package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.compiler.ast.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.compiler.*;

public class FieldAccess extends Expr
{
    int opcode;
    
    protected FieldAccess(final int n, final CodeIterator codeIterator, final CtClass ctClass, final MethodInfo methodInfo, final int opcode) {
        super(n, codeIterator, ctClass, methodInfo);
        this.opcode = opcode;
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
    
    public boolean isStatic() {
        return isStatic(this.opcode);
    }
    
    static boolean isStatic(final int n) {
        return n == 178 || n == 179;
    }
    
    public boolean isReader() {
        return this.opcode == 180 || this.opcode == 178;
    }
    
    public boolean isWriter() {
        return this.opcode == 181 || this.opcode == 179;
    }
    
    private CtClass getCtClass() throws NotFoundException {
        return this.thisClass.getClassPool().get(this.getClassName());
    }
    
    public String getClassName() {
        return this.getConstPool().getFieldrefClassName(this.iterator.u16bitAt(this.currentPos + 1));
    }
    
    public String getFieldName() {
        return this.getConstPool().getFieldrefName(this.iterator.u16bitAt(this.currentPos + 1));
    }
    
    public CtField getField() throws NotFoundException {
        final CtClass ctClass = this.getCtClass();
        final int u16bit = this.iterator.u16bitAt(this.currentPos + 1);
        final ConstPool constPool = this.getConstPool();
        return ctClass.getField(constPool.getFieldrefName(u16bit), constPool.getFieldrefType(u16bit));
    }
    
    @Override
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }
    
    public String getSignature() {
        return this.getConstPool().getFieldrefType(this.iterator.u16bitAt(this.currentPos + 1));
    }
    
    @Override
    public void replace(final String s) throws CannotCompileException {
        this.thisClass.getClassFile();
        final ConstPool constPool = this.getConstPool();
        final int currentPos = this.currentPos;
        final int u16bit = this.iterator.u16bitAt(currentPos + 1);
        final Javac javac = new Javac(this.thisClass);
        final CodeAttribute value = this.iterator.get();
        final CtClass ctClass = Descriptor.toCtClass(constPool.getFieldrefType(u16bit), this.thisClass.getClassPool());
        final boolean reader = this.isReader();
        CtClass[] array;
        CtClass voidType;
        if (reader) {
            array = new CtClass[0];
            voidType = ctClass;
        }
        else {
            array = new CtClass[] { ctClass };
            voidType = CtClass.voidType;
        }
        final int maxLocals = value.getMaxLocals();
        javac.recordParams(constPool.getFieldrefClassName(u16bit), array, true, maxLocals, this.withinStatic());
        Expr.checkResultValue(voidType, s);
        if (reader) {}
        final int recordReturnType = javac.recordReturnType(voidType, true);
        if (reader) {
            javac.recordProceed(new ProceedForRead(voidType, this.opcode, u16bit, maxLocals));
        }
        else {
            javac.recordType(ctClass);
            javac.recordProceed(new ProceedForWrite(array[0], this.opcode, u16bit, maxLocals));
        }
        final Bytecode bytecode = javac.getBytecode();
        Expr.storeStack(array, this.isStatic(), maxLocals, bytecode);
        javac.recordLocalVariables(value, currentPos);
        if (true) {
            if (voidType == CtClass.voidType) {
                bytecode.addOpcode(1);
                bytecode.addAstore(recordReturnType);
            }
            else {
                bytecode.addConstZero(voidType);
                bytecode.addStore(recordReturnType, voidType);
            }
        }
        javac.compileStmnt(s);
        if (reader) {
            bytecode.addLoad(recordReturnType, voidType);
        }
        this.replace0(currentPos, bytecode, 3);
    }
    
    static class ProceedForWrite implements ProceedHandler
    {
        CtClass fieldType;
        int opcode;
        int targetVar;
        int index;
        
        ProceedForWrite(final CtClass fieldType, final int opcode, final int index, final int targetVar) {
            this.fieldType = fieldType;
            this.targetVar = targetVar;
            this.opcode = opcode;
            this.index = index;
        }
        
        @Override
        public void doit(final JvstCodeGen jvstCodeGen, final Bytecode bytecode, final ASTList list) throws CompileError {
            if (jvstCodeGen.getMethodArgsLength(list) != 1) {
                throw new CompileError("$proceed() cannot take more than one parameter for field writing");
            }
            if (!FieldAccess.isStatic(this.opcode)) {
                bytecode.addAload(this.targetVar);
            }
            jvstCodeGen.atMethodArgs(list, new int[1], new int[1], new String[1]);
            jvstCodeGen.doNumCast(this.fieldType);
            if (this.fieldType instanceof CtPrimitiveType) {
                final int n = -1 - ((CtPrimitiveType)this.fieldType).getDataSize();
            }
            else {
                int n = 0;
                --n;
            }
            bytecode.add(this.opcode);
            bytecode.addIndex(this.index);
            bytecode.growStack(-1);
            jvstCodeGen.setType(CtClass.voidType);
            jvstCodeGen.addNullIfVoid();
        }
        
        @Override
        public void setReturnType(final JvstTypeChecker jvstTypeChecker, final ASTList list) throws CompileError {
            jvstTypeChecker.atMethodArgs(list, new int[1], new int[1], new String[1]);
            jvstTypeChecker.setType(CtClass.voidType);
            jvstTypeChecker.addNullIfVoid();
        }
    }
    
    static class ProceedForRead implements ProceedHandler
    {
        CtClass fieldType;
        int opcode;
        int targetVar;
        int index;
        
        ProceedForRead(final CtClass fieldType, final int opcode, final int index, final int targetVar) {
            this.fieldType = fieldType;
            this.targetVar = targetVar;
            this.opcode = opcode;
            this.index = index;
        }
        
        @Override
        public void doit(final JvstCodeGen jvstCodeGen, final Bytecode bytecode, final ASTList list) throws CompileError {
            if (list != null && !jvstCodeGen.isParamListName(list)) {
                throw new CompileError("$proceed() cannot take a parameter for field reading");
            }
            if (!FieldAccess.isStatic(this.opcode)) {
                bytecode.addAload(this.targetVar);
            }
            if (this.fieldType instanceof CtPrimitiveType) {
                final int n = -1 + ((CtPrimitiveType)this.fieldType).getDataSize();
            }
            else {
                int n = 0;
                ++n;
            }
            bytecode.add(this.opcode);
            bytecode.addIndex(this.index);
            bytecode.growStack(-1);
            jvstCodeGen.setType(this.fieldType);
        }
        
        @Override
        public void setReturnType(final JvstTypeChecker jvstTypeChecker, final ASTList list) throws CompileError {
            jvstTypeChecker.setType(this.fieldType);
        }
    }
}
