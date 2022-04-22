package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;

public final class TransformNewClass extends Transformer
{
    private int nested;
    private String classname;
    private String newClassName;
    private int newClassIndex;
    private int newMethodNTIndex;
    private int newMethodIndex;
    
    public TransformNewClass(final Transformer transformer, final String classname, final String newClassName) {
        super(transformer);
        this.classname = classname;
        this.newClassName = newClassName;
    }
    
    @Override
    public void initialize(final ConstPool constPool, final CodeAttribute codeAttribute) {
        this.nested = 0;
        final int newClassIndex = 0;
        this.newMethodIndex = newClassIndex;
        this.newMethodNTIndex = newClassIndex;
        this.newClassIndex = newClassIndex;
    }
    
    @Override
    public int transform(final CtClass ctClass, final int n, final CodeIterator codeIterator, final ConstPool constPool) throws CannotCompileException {
        final int byte1 = codeIterator.byteAt(n);
        if (byte1 == 187) {
            if (constPool.getClassInfo(codeIterator.u16bitAt(n + 1)).equals(this.classname)) {
                if (codeIterator.byteAt(n + 3) != 89) {
                    throw new CannotCompileException("NEW followed by no DUP was found");
                }
                if (this.newClassIndex == 0) {
                    this.newClassIndex = constPool.addClassInfo(this.newClassName);
                }
                codeIterator.write16bit(this.newClassIndex, n + 1);
                ++this.nested;
            }
        }
        else if (byte1 == 183) {
            final int u16bit = codeIterator.u16bitAt(n + 1);
            if (constPool.isConstructor(this.classname, u16bit) != 0 && this.nested > 0) {
                final int methodrefNameAndType = constPool.getMethodrefNameAndType(u16bit);
                if (this.newMethodNTIndex != methodrefNameAndType) {
                    this.newMethodNTIndex = methodrefNameAndType;
                    this.newMethodIndex = constPool.addMethodrefInfo(this.newClassIndex, methodrefNameAndType);
                }
                codeIterator.write16bit(this.newMethodIndex, n + 1);
                --this.nested;
            }
        }
        return n;
    }
}
