package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.io.*;

class CtNewClass extends CtClassType
{
    protected boolean hasConstructor;
    
    CtNewClass(final String s, final ClassPool classPool, final boolean hasConstructor, final CtClass ctClass) {
        super(s, classPool);
        this.wasChanged = true;
        String name;
        if (hasConstructor || ctClass == null) {
            name = null;
        }
        else {
            name = ctClass.getName();
        }
        this.classfile = new ClassFile(hasConstructor, s, name);
        if (hasConstructor && ctClass != null) {
            this.classfile.setInterfaces(new String[] { ctClass.getName() });
        }
        this.setModifiers(Modifier.setPublic(this.getModifiers()));
        this.hasConstructor = hasConstructor;
    }
    
    @Override
    protected void extendToString(final StringBuffer sb) {
        if (this.hasConstructor) {
            sb.append("hasConstructor ");
        }
        super.extendToString(sb);
    }
    
    @Override
    public void addConstructor(final CtConstructor ctConstructor) throws CannotCompileException {
        this.hasConstructor = true;
        super.addConstructor(ctConstructor);
    }
    
    @Override
    public void toBytecode(final DataOutputStream dataOutputStream) throws CannotCompileException, IOException {
        if (!this.hasConstructor) {
            this.inheritAllConstructors();
            this.hasConstructor = true;
        }
        super.toBytecode(dataOutputStream);
    }
    
    public void inheritAllConstructors() throws CannotCompileException, NotFoundException {
        final CtClass superclass = this.getSuperclass();
        final CtConstructor[] declaredConstructors = superclass.getDeclaredConstructors();
        while (0 < declaredConstructors.length) {
            final CtConstructor ctConstructor = declaredConstructors[0];
            final int modifiers = ctConstructor.getModifiers();
            if (this.isInheritable(modifiers, superclass)) {
                final CtConstructor make = CtNewConstructor.make(ctConstructor.getParameterTypes(), ctConstructor.getExceptionTypes(), this);
                make.setModifiers(modifiers & 0x7);
                this.addConstructor(make);
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        if (0 < 1) {
            throw new CannotCompileException("no inheritable constructor in " + superclass.getName());
        }
    }
    
    private boolean isInheritable(final int n, final CtClass ctClass) {
        if (Modifier.isPrivate(n)) {
            return false;
        }
        if (!Modifier.isPackage(n)) {
            return true;
        }
        final String packageName = this.getPackageName();
        final String packageName2 = ctClass.getPackageName();
        if (packageName == null) {
            return packageName2 == null;
        }
        return packageName.equals(packageName2);
    }
}
