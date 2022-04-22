package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class TransformBefore extends TransformCall
{
    protected CtClass[] parameterTypes;
    protected int locals;
    protected int maxLocals;
    protected byte[] saveCode;
    protected byte[] loadCode;
    
    public TransformBefore(final Transformer transformer, final CtMethod ctMethod, final CtMethod ctMethod2) throws NotFoundException {
        super(transformer, ctMethod, ctMethod2);
        this.methodDescriptor = ctMethod.getMethodInfo2().getDescriptor();
        this.parameterTypes = ctMethod.getParameterTypes();
        this.locals = 0;
        this.maxLocals = 0;
        final byte[] array = null;
        this.loadCode = array;
        this.saveCode = array;
    }
    
    @Override
    public void initialize(final ConstPool constPool, final CodeAttribute codeAttribute) {
        super.initialize(constPool, codeAttribute);
        this.locals = 0;
        this.maxLocals = codeAttribute.getMaxLocals();
        final byte[] array = null;
        this.loadCode = array;
        this.saveCode = array;
    }
    
    @Override
    protected int match(final int n, final int n2, final CodeIterator codeIterator, final int n3, final ConstPool constPool) throws BadBytecode {
        if (this.newIndex == 0) {
            this.newIndex = constPool.addMethodrefInfo(constPool.addClassInfo(this.newClassname), constPool.addNameAndTypeInfo(this.newMethodname, Descriptor.insertParameter(this.classname, Descriptor.ofParameters(this.parameterTypes) + 'V')));
            this.constPool = constPool;
        }
        if (this.saveCode == null) {
            this.makeCode(this.parameterTypes, constPool);
        }
        return this.match2(n2, codeIterator);
    }
    
    protected int match2(final int n, final CodeIterator codeIterator) throws BadBytecode {
        codeIterator.move(n);
        codeIterator.insert(this.saveCode);
        codeIterator.insert(this.loadCode);
        final int insertGap = codeIterator.insertGap(3);
        codeIterator.writeByte(184, insertGap);
        codeIterator.write16bit(this.newIndex, insertGap + 1);
        codeIterator.insert(this.loadCode);
        return codeIterator.next();
    }
    
    @Override
    public int extraLocals() {
        return this.locals;
    }
    
    protected void makeCode(final CtClass[] array, final ConstPool constPool) {
        final Bytecode bytecode = new Bytecode(constPool, 0, 0);
        final Bytecode bytecode2 = new Bytecode(constPool, 0, 0);
        final int maxLocals = this.maxLocals;
        final int n = (array == null) ? 0 : array.length;
        bytecode2.addAload(maxLocals);
        this.makeCode2(bytecode, bytecode2, 0, n, array, maxLocals + 1);
        bytecode.addAstore(maxLocals);
        this.saveCode = bytecode.get();
        this.loadCode = bytecode2.get();
    }
    
    private void makeCode2(final Bytecode bytecode, final Bytecode bytecode2, final int n, final int n2, final CtClass[] array, final int n3) {
        if (n < n2) {
            this.makeCode2(bytecode, bytecode2, n + 1, n2, array, n3 + bytecode2.addLoad(n3, array[n]));
            bytecode.addStore(n3, array[n]);
        }
        else {
            this.locals = n3 - this.maxLocals;
        }
    }
}
