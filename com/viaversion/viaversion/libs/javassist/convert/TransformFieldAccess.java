package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public final class TransformFieldAccess extends Transformer
{
    private String newClassname;
    private String newFieldname;
    private String fieldname;
    private CtClass fieldClass;
    private boolean isPrivate;
    private int newIndex;
    private ConstPool constPool;
    
    public TransformFieldAccess(final Transformer transformer, final CtField ctField, final String newClassname, final String newFieldname) {
        super(transformer);
        this.fieldClass = ctField.getDeclaringClass();
        this.fieldname = ctField.getName();
        this.isPrivate = Modifier.isPrivate(ctField.getModifiers());
        this.newClassname = newClassname;
        this.newFieldname = newFieldname;
        this.constPool = null;
    }
    
    @Override
    public void initialize(final ConstPool constPool, final CodeAttribute codeAttribute) {
        if (this.constPool != constPool) {
            this.newIndex = 0;
        }
    }
    
    @Override
    public int transform(final CtClass ctClass, final int n, final CodeIterator codeIterator, final ConstPool constPool) {
        final int byte1 = codeIterator.byteAt(n);
        if (byte1 == 180 || byte1 == 178 || byte1 == 181 || byte1 == 179) {
            final String field = TransformReadField.isField(ctClass.getClassPool(), constPool, this.fieldClass, this.fieldname, this.isPrivate, codeIterator.u16bitAt(n + 1));
            if (field != null) {
                if (this.newIndex == 0) {
                    this.newIndex = constPool.addFieldrefInfo(constPool.addClassInfo(this.newClassname), constPool.addNameAndTypeInfo(this.newFieldname, field));
                    this.constPool = constPool;
                }
                codeIterator.write16bit(this.newIndex, n + 1);
            }
        }
        return n;
    }
}
