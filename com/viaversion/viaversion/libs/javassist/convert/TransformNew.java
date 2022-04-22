package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public final class TransformNew extends Transformer
{
    private int nested;
    private String classname;
    private String trapClass;
    private String trapMethod;
    
    public TransformNew(final Transformer transformer, final String classname, final String trapClass, final String trapMethod) {
        super(transformer);
        this.classname = classname;
        this.trapClass = trapClass;
        this.trapMethod = trapMethod;
    }
    
    @Override
    public void initialize(final ConstPool constPool, final CodeAttribute codeAttribute) {
        this.nested = 0;
    }
    
    @Override
    public int transform(final CtClass ctClass, final int n, final CodeIterator codeIterator, final ConstPool constPool) throws CannotCompileException {
        final int byte1 = codeIterator.byteAt(n);
        if (byte1 == 187) {
            if (constPool.getClassInfo(codeIterator.u16bitAt(n + 1)).equals(this.classname)) {
                if (codeIterator.byteAt(n + 3) != 89) {
                    throw new CannotCompileException("NEW followed by no DUP was found");
                }
                codeIterator.writeByte(0, n);
                codeIterator.writeByte(0, n + 1);
                codeIterator.writeByte(0, n + 2);
                codeIterator.writeByte(0, n + 3);
                ++this.nested;
                final StackMapTable stackMapTable = (StackMapTable)codeIterator.get().getAttribute("StackMapTable");
                if (stackMapTable != null) {
                    stackMapTable.removeNew(n);
                }
                final StackMap stackMap = (StackMap)codeIterator.get().getAttribute("StackMap");
                if (stackMap != null) {
                    stackMap.removeNew(n);
                }
            }
        }
        else if (byte1 == 183) {
            final int constructor = constPool.isConstructor(this.classname, codeIterator.u16bitAt(n + 1));
            if (constructor != 0 && this.nested > 0) {
                final int computeMethodref = this.computeMethodref(constructor, constPool);
                codeIterator.writeByte(184, n);
                codeIterator.write16bit(computeMethodref, n + 1);
                --this.nested;
            }
        }
        return n;
    }
    
    private int computeMethodref(int addUtf8Info, final ConstPool constPool) {
        final int addClassInfo = constPool.addClassInfo(this.trapClass);
        final int addUtf8Info2 = constPool.addUtf8Info(this.trapMethod);
        addUtf8Info = constPool.addUtf8Info(Descriptor.changeReturnType(this.classname, constPool.getUtf8Info(addUtf8Info)));
        return constPool.addMethodrefInfo(addClassInfo, constPool.addNameAndTypeInfo(addUtf8Info2, addUtf8Info));
    }
}
