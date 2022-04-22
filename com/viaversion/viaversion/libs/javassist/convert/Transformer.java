package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public abstract class Transformer implements Opcode
{
    private Transformer next;
    
    public Transformer(final Transformer next) {
        this.next = next;
    }
    
    public Transformer getNext() {
        return this.next;
    }
    
    public void initialize(final ConstPool constPool, final CodeAttribute codeAttribute) {
    }
    
    public void initialize(final ConstPool constPool, final CtClass ctClass, final MethodInfo methodInfo) throws CannotCompileException {
        this.initialize(constPool, methodInfo.getCodeAttribute());
    }
    
    public void clean() {
    }
    
    public abstract int transform(final CtClass p0, final int p1, final CodeIterator p2, final ConstPool p3) throws CannotCompileException, BadBytecode;
    
    public int extraLocals() {
        return 0;
    }
    
    public int extraStack() {
        return 0;
    }
}
