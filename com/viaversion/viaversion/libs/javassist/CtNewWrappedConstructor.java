package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.bytecode.*;

class CtNewWrappedConstructor extends CtNewWrappedMethod
{
    private static final int PASS_NONE = 0;
    private static final int PASS_PARAMS = 2;
    
    public static CtConstructor wrapped(final CtClass[] array, final CtClass[] exceptionTypes, final int n, final CtMethod ctMethod, final CtMethod.ConstParameter constParameter, final CtClass ctClass) throws CannotCompileException {
        final CtConstructor ctConstructor = new CtConstructor(array, ctClass);
        ctConstructor.setExceptionTypes(exceptionTypes);
        ctConstructor.getMethodInfo2().setCodeAttribute(makeBody(ctClass, ctClass.getClassFile2(), n, ctMethod, array, constParameter).toCodeAttribute());
        return ctConstructor;
    }
    
    protected static Bytecode makeBody(final CtClass ctClass, final ClassFile classFile, final int n, final CtMethod ctMethod, final CtClass[] array, final CtMethod.ConstParameter constParameter) throws CannotCompileException {
        final int superclassId = classFile.getSuperclassId();
        final Bytecode bytecode = new Bytecode(classFile.getConstPool(), 0, 0);
        bytecode.setMaxLocals(false, array, 0);
        bytecode.addAload(0);
        if (n == 0) {
            bytecode.addInvokespecial(superclassId, "<init>", "()V");
        }
        else if (n == 2) {
            final int n2 = bytecode.addLoadParameters(array, 1) + 1;
            bytecode.addInvokespecial(superclassId, "<init>", Descriptor.ofConstructor(array));
        }
        else {
            CtNewWrappedMethod.compileParameterList(bytecode, array, 1);
            String s;
            if (constParameter == null) {
                s = CtMethod.ConstParameter.defaultConstDescriptor();
            }
            else {
                final int n3 = constParameter.compile(bytecode) + 2;
                s = constParameter.constDescriptor();
            }
            if (1 < 2) {}
            bytecode.addInvokespecial(superclassId, "<init>", s);
        }
        if (ctMethod == null) {
            bytecode.add(177);
        }
        else {
            CtNewWrappedMethod.makeBody0(ctClass, classFile, ctMethod, false, array, CtClass.voidType, constParameter, bytecode);
            if (1 < 2) {}
        }
        bytecode.setMaxStack(1);
        return bytecode;
    }
}
