package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.compiler.ast.*;
import com.viaversion.viaversion.libs.javassist.compiler.*;

public class NewArray extends Expr
{
    int opcode;
    
    protected NewArray(final int n, final CodeIterator codeIterator, final CtClass ctClass, final MethodInfo methodInfo, final int opcode) {
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
    
    @Override
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }
    
    public CtClass getComponentType() throws NotFoundException {
        if (this.opcode == 188) {
            return this.getPrimitiveType(this.iterator.byteAt(this.currentPos + 1));
        }
        if (this.opcode == 189 || this.opcode == 197) {
            final String classInfo = this.getConstPool().getClassInfo(this.iterator.u16bitAt(this.currentPos + 1));
            return Descriptor.toCtClass(Descriptor.toArrayComponent(classInfo, Descriptor.arrayDimension(classInfo)), this.thisClass.getClassPool());
        }
        throw new RuntimeException("bad opcode: " + this.opcode);
    }
    
    CtClass getPrimitiveType(final int n) {
        switch (n) {
            case 4: {
                return CtClass.booleanType;
            }
            case 5: {
                return CtClass.charType;
            }
            case 6: {
                return CtClass.floatType;
            }
            case 7: {
                return CtClass.doubleType;
            }
            case 8: {
                return CtClass.byteType;
            }
            case 9: {
                return CtClass.shortType;
            }
            case 10: {
                return CtClass.intType;
            }
            case 11: {
                return CtClass.longType;
            }
            default: {
                throw new RuntimeException("bad atype: " + n);
            }
        }
    }
    
    public int getDimension() {
        if (this.opcode == 188) {
            return 1;
        }
        if (this.opcode == 189 || this.opcode == 197) {
            return Descriptor.arrayDimension(this.getConstPool().getClassInfo(this.iterator.u16bitAt(this.currentPos + 1))) + ((this.opcode == 189) ? 1 : 0);
        }
        throw new RuntimeException("bad opcode: " + this.opcode);
    }
    
    public int getCreatedDimensions() {
        if (this.opcode == 197) {
            return this.iterator.byteAt(this.currentPos + 3);
        }
        return 1;
    }
    
    @Override
    public void replace(final String s) throws CannotCompileException {
        this.replace2(s);
    }
    
    private void replace2(final String s) throws CompileError, NotFoundException, BadBytecode, CannotCompileException {
        this.thisClass.getClassFile();
        final ConstPool constPool = this.getConstPool();
        final int currentPos = this.currentPos;
        String s2;
        if (this.opcode == 188) {
            this.iterator.byteAt(this.currentPos + 1);
            s2 = "[" + ((CtPrimitiveType)this.getPrimitiveType(0)).getDescriptor();
        }
        else if (this.opcode == 189) {
            this.iterator.u16bitAt(currentPos + 1);
            final String classInfo = constPool.getClassInfo(0);
            if (classInfo.startsWith("[")) {
                s2 = "[" + classInfo;
            }
            else {
                s2 = "[L" + classInfo + ";";
            }
        }
        else {
            if (this.opcode != 197) {
                throw new RuntimeException("bad opcode: " + this.opcode);
            }
            this.iterator.u16bitAt(this.currentPos + 1);
            s2 = constPool.getClassInfo(0);
            this.iterator.byteAt(this.currentPos + 3);
        }
        Descriptor.toCtClass(s2, this.thisClass.getClassPool());
        final Javac javac = new Javac(this.thisClass);
        this.iterator.get();
        final CtClass[] array = { null };
        while (true) {
            array[0] = CtClass.intType;
            int n = 0;
            ++n;
        }
    }
    
    static class ProceedForArray implements ProceedHandler
    {
        CtClass arrayType;
        int opcode;
        int index;
        int dimension;
        
        ProceedForArray(final CtClass arrayType, final int opcode, final int index, final int dimension) {
            this.arrayType = arrayType;
            this.opcode = opcode;
            this.index = index;
            this.dimension = dimension;
        }
        
        @Override
        public void doit(final JvstCodeGen jvstCodeGen, final Bytecode bytecode, final ASTList list) throws CompileError {
            final int methodArgsLength = jvstCodeGen.getMethodArgsLength(list);
            if (methodArgsLength != this.dimension) {
                throw new CompileError("$proceed() with a wrong number of parameters");
            }
            jvstCodeGen.atMethodArgs(list, new int[methodArgsLength], new int[methodArgsLength], new String[methodArgsLength]);
            bytecode.addOpcode(this.opcode);
            if (this.opcode == 189) {
                bytecode.addIndex(this.index);
            }
            else if (this.opcode == 188) {
                bytecode.add(this.index);
            }
            else {
                bytecode.addIndex(this.index);
                bytecode.add(this.dimension);
                bytecode.growStack(1 - this.dimension);
            }
            jvstCodeGen.setType(this.arrayType);
        }
        
        @Override
        public void setReturnType(final JvstTypeChecker jvstTypeChecker, final ASTList list) throws CompileError {
            jvstTypeChecker.setType(this.arrayType);
        }
    }
}
