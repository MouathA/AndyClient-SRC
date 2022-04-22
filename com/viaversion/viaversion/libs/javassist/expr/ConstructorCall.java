package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;

public class ConstructorCall extends MethodCall
{
    protected ConstructorCall(final int n, final CodeIterator codeIterator, final CtClass ctClass, final MethodInfo methodInfo) {
        super(n, codeIterator, ctClass, methodInfo);
    }
    
    @Override
    public String getMethodName() {
        return this.isSuper() ? "super" : "this";
    }
    
    @Override
    public CtMethod getMethod() throws NotFoundException {
        throw new NotFoundException("this is a constructor call.  Call getConstructor().");
    }
    
    public CtConstructor getConstructor() throws NotFoundException {
        return this.getCtClass().getConstructor(this.getSignature());
    }
    
    @Override
    public boolean isSuper() {
        return super.isSuper();
    }
}
