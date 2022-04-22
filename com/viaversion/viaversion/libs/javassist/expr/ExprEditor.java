package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;

public class ExprEditor
{
    public boolean doit(final CtClass ctClass, final MethodInfo methodInfo) throws CannotCompileException {
        final CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            return false;
        }
        final CodeIterator iterator = codeAttribute.iterator();
        final LoopContext loopContext = new LoopContext(codeAttribute.getMaxLocals());
        while (iterator.hasNext()) {
            if (this.loopBody(iterator, ctClass, methodInfo, loopContext)) {
                continue;
            }
        }
        final ExceptionTable exceptionTable = codeAttribute.getExceptionTable();
        while (0 < exceptionTable.size()) {
            final Handler handler = new Handler(exceptionTable, 0, iterator, ctClass, methodInfo);
            this.edit(handler);
            if (handler.edited()) {
                loopContext.updateMax(handler.locals(), handler.stack());
            }
            int n = 0;
            ++n;
        }
        if (codeAttribute.getMaxLocals() < loopContext.maxLocals) {
            codeAttribute.setMaxLocals(loopContext.maxLocals);
        }
        codeAttribute.setMaxStack(codeAttribute.getMaxStack() + loopContext.maxStack);
        if (true) {
            methodInfo.rebuildStackMapIf6(ctClass.getClassPool(), ctClass.getClassFile2());
        }
        return true;
    }
    
    boolean doit(final CtClass ctClass, final MethodInfo methodInfo, final LoopContext loopContext, final CodeIterator codeIterator, int n) throws CannotCompileException {
        while (codeIterator.hasNext() && codeIterator.lookAhead() < n) {
            final int codeLength = codeIterator.getCodeLength();
            if (this.loopBody(codeIterator, ctClass, methodInfo, loopContext)) {
                final int codeLength2 = codeIterator.getCodeLength();
                if (codeLength == codeLength2) {
                    continue;
                }
                n += codeLength2 - codeLength;
            }
        }
        return true;
    }
    
    final boolean loopBody(final CodeIterator codeIterator, final CtClass ctClass, final MethodInfo methodInfo, final LoopContext loopContext) throws CannotCompileException {
        Expr expr = null;
        final int next = codeIterator.next();
        final int byte1 = codeIterator.byteAt(next);
        if (byte1 >= 178) {
            if (byte1 < 188) {
                if (byte1 == 184 || byte1 == 185 || byte1 == 182) {
                    expr = new MethodCall(next, codeIterator, ctClass, methodInfo);
                    this.edit((MethodCall)expr);
                }
                else if (byte1 == 180 || byte1 == 178 || byte1 == 181 || byte1 == 179) {
                    expr = new FieldAccess(next, codeIterator, ctClass, methodInfo, byte1);
                    this.edit((FieldAccess)expr);
                }
                else if (byte1 == 187) {
                    loopContext.newList = new NewOp(loopContext.newList, next, methodInfo.getConstPool().getClassInfo(codeIterator.u16bitAt(next + 1)));
                }
                else if (byte1 == 183) {
                    final NewOp newList = loopContext.newList;
                    if (newList != null && methodInfo.getConstPool().isConstructor(newList.type, codeIterator.u16bitAt(next + 1)) > 0) {
                        expr = new NewExpr(next, codeIterator, ctClass, methodInfo, newList.type, newList.pos);
                        this.edit((NewExpr)expr);
                        loopContext.newList = newList.next;
                    }
                    else {
                        final MethodCall methodCall = new MethodCall(next, codeIterator, ctClass, methodInfo);
                        if (methodCall.getMethodName().equals("<init>")) {
                            this.edit((ConstructorCall)(expr = new ConstructorCall(next, codeIterator, ctClass, methodInfo)));
                        }
                        else {
                            expr = methodCall;
                            this.edit(methodCall);
                        }
                    }
                }
            }
            else if (byte1 == 188 || byte1 == 189 || byte1 == 197) {
                expr = new NewArray(next, codeIterator, ctClass, methodInfo, byte1);
                this.edit((NewArray)expr);
            }
            else if (byte1 == 193) {
                expr = new Instanceof(next, codeIterator, ctClass, methodInfo);
                this.edit((Instanceof)expr);
            }
            else if (byte1 == 192) {
                expr = new Cast(next, codeIterator, ctClass, methodInfo);
                this.edit((Cast)expr);
            }
        }
        if (expr != null && expr.edited()) {
            loopContext.updateMax(expr.locals(), expr.stack());
            return true;
        }
        return false;
    }
    
    public void edit(final NewExpr newExpr) throws CannotCompileException {
    }
    
    public void edit(final NewArray newArray) throws CannotCompileException {
    }
    
    public void edit(final MethodCall methodCall) throws CannotCompileException {
    }
    
    public void edit(final ConstructorCall constructorCall) throws CannotCompileException {
    }
    
    public void edit(final FieldAccess fieldAccess) throws CannotCompileException {
    }
    
    public void edit(final Instanceof instanceof1) throws CannotCompileException {
    }
    
    public void edit(final Cast cast) throws CannotCompileException {
    }
    
    public void edit(final Handler handler) throws CannotCompileException {
    }
    
    static final class LoopContext
    {
        NewOp newList;
        int maxLocals;
        int maxStack;
        
        LoopContext(final int maxLocals) {
            this.maxLocals = maxLocals;
            this.maxStack = 0;
            this.newList = null;
        }
        
        void updateMax(final int maxLocals, final int maxStack) {
            if (this.maxLocals < maxLocals) {
                this.maxLocals = maxLocals;
            }
            if (this.maxStack < maxStack) {
                this.maxStack = maxStack;
            }
        }
    }
    
    static final class NewOp
    {
        NewOp next;
        int pos;
        String type;
        
        NewOp(final NewOp next, final int pos, final String type) {
            this.next = next;
            this.pos = pos;
            this.type = type;
        }
    }
}
