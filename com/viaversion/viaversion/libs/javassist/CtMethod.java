package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.bytecode.*;

public final class CtMethod extends CtBehavior
{
    protected String cachedStringRep;
    
    CtMethod(final MethodInfo methodInfo, final CtClass ctClass) {
        super(ctClass, methodInfo);
        this.cachedStringRep = null;
    }
    
    public CtMethod(final CtClass ctClass, final String s, final CtClass[] array, final CtClass ctClass2) {
        this(null, ctClass2);
        this.methodInfo = new MethodInfo(ctClass2.getClassFile2().getConstPool(), s, Descriptor.ofMethod(ctClass, array));
        this.setModifiers(1025);
    }
    
    public CtMethod(final CtMethod ctMethod, final CtClass ctClass, final ClassMap classMap) throws CannotCompileException {
        this(null, ctClass);
        this.copy(ctMethod, false, classMap);
    }
    
    public static CtMethod make(final String s, final CtClass ctClass) throws CannotCompileException {
        return CtNewMethod.make(s, ctClass);
    }
    
    public static CtMethod make(final MethodInfo methodInfo, final CtClass ctClass) throws CannotCompileException {
        if (ctClass.getClassFile2().getConstPool() != methodInfo.getConstPool()) {
            throw new CannotCompileException("bad declaring class");
        }
        return new CtMethod(methodInfo, ctClass);
    }
    
    @Override
    public int hashCode() {
        return this.getStringRep().hashCode();
    }
    
    @Override
    void nameReplaced() {
        this.cachedStringRep = null;
    }
    
    final String getStringRep() {
        if (this.cachedStringRep == null) {
            this.cachedStringRep = this.methodInfo.getName() + Descriptor.getParamDescriptor(this.methodInfo.getDescriptor());
        }
        return this.cachedStringRep;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof CtMethod && ((CtMethod)o).getStringRep().equals(this.getStringRep());
    }
    
    @Override
    public String getLongName() {
        return this.getDeclaringClass().getName() + "." + this.getName() + Descriptor.toString(this.getSignature());
    }
    
    @Override
    public String getName() {
        return this.methodInfo.getName();
    }
    
    public void setName(final String name) {
        this.declaringClass.checkModify();
        this.methodInfo.setName(name);
    }
    
    public CtClass getReturnType() throws NotFoundException {
        return this.getReturnType0();
    }
    
    @Override
    public boolean isEmpty() {
        final CodeAttribute codeAttribute = this.getMethodInfo2().getCodeAttribute();
        if (codeAttribute == null) {
            return (this.getModifiers() & 0x400) != 0x0;
        }
        final CodeIterator iterator = codeAttribute.iterator();
        return iterator.hasNext() && iterator.byteAt(iterator.next()) == 177 && !iterator.hasNext();
    }
    
    public void setBody(final CtMethod ctMethod, final ClassMap classMap) throws CannotCompileException {
        CtBehavior.setBody0(ctMethod.declaringClass, ctMethod.methodInfo, this.declaringClass, this.methodInfo, classMap);
    }
    
    public void setWrappedBody(final CtMethod ctMethod, final ConstParameter constParameter) throws CannotCompileException {
        this.declaringClass.checkModify();
        final CtClass declaringClass = this.getDeclaringClass();
        this.methodInfo.setCodeAttribute(CtNewWrappedMethod.makeBody(declaringClass, declaringClass.getClassFile2(), ctMethod, this.getParameterTypes(), this.getReturnType(), constParameter).toCodeAttribute());
        this.methodInfo.setAccessFlags(this.methodInfo.getAccessFlags() & 0xFFFFFBFF);
    }
    
    static class StringConstParameter extends ConstParameter
    {
        String param;
        
        StringConstParameter(final String param) {
            this.param = param;
        }
        
        @Override
        int compile(final Bytecode bytecode) throws CannotCompileException {
            bytecode.addLdc(this.param);
            return 1;
        }
        
        @Override
        String descriptor() {
            return "([Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;";
        }
        
        @Override
        String constDescriptor() {
            return "([Ljava/lang/Object;Ljava/lang/String;)V";
        }
    }
    
    public static class ConstParameter
    {
        public static ConstParameter integer(final int n) {
            return new IntConstParameter(n);
        }
        
        public static ConstParameter integer(final long n) {
            return new LongConstParameter(n);
        }
        
        public static ConstParameter string(final String s) {
            return new StringConstParameter(s);
        }
        
        ConstParameter() {
        }
        
        int compile(final Bytecode bytecode) throws CannotCompileException {
            return 0;
        }
        
        String descriptor() {
            return defaultDescriptor();
        }
        
        static String defaultDescriptor() {
            return "([Ljava/lang/Object;)Ljava/lang/Object;";
        }
        
        String constDescriptor() {
            return defaultConstDescriptor();
        }
        
        static String defaultConstDescriptor() {
            return "([Ljava/lang/Object;)V";
        }
    }
    
    static class IntConstParameter extends ConstParameter
    {
        int param;
        
        IntConstParameter(final int param) {
            this.param = param;
        }
        
        @Override
        int compile(final Bytecode bytecode) throws CannotCompileException {
            bytecode.addIconst(this.param);
            return 1;
        }
        
        @Override
        String descriptor() {
            return "([Ljava/lang/Object;I)Ljava/lang/Object;";
        }
        
        @Override
        String constDescriptor() {
            return "([Ljava/lang/Object;I)V";
        }
    }
    
    static class LongConstParameter extends ConstParameter
    {
        long param;
        
        LongConstParameter(final long param) {
            this.param = param;
        }
        
        @Override
        int compile(final Bytecode bytecode) throws CannotCompileException {
            bytecode.addLconst(this.param);
            return 2;
        }
        
        @Override
        String descriptor() {
            return "([Ljava/lang/Object;J)Ljava/lang/Object;";
        }
        
        @Override
        String constDescriptor() {
            return "([Ljava/lang/Object;J)V";
        }
    }
}
