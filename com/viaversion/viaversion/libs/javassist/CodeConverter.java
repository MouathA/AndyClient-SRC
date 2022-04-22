package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.convert.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class CodeConverter
{
    protected Transformer transformers;
    
    public CodeConverter() {
        this.transformers = null;
    }
    
    public void replaceNew(final CtClass ctClass, final CtClass ctClass2, final String s) {
        this.transformers = new TransformNew(this.transformers, ctClass.getName(), ctClass2.getName(), s);
    }
    
    public void replaceNew(final CtClass ctClass, final CtClass ctClass2) {
        this.transformers = new TransformNewClass(this.transformers, ctClass.getName(), ctClass2.getName());
    }
    
    public void redirectFieldAccess(final CtField ctField, final CtClass ctClass, final String s) {
        this.transformers = new TransformFieldAccess(this.transformers, ctField, ctClass.getName(), s);
    }
    
    public void replaceFieldRead(final CtField ctField, final CtClass ctClass, final String s) {
        this.transformers = new TransformReadField(this.transformers, ctField, ctClass.getName(), s);
    }
    
    public void replaceFieldWrite(final CtField ctField, final CtClass ctClass, final String s) {
        this.transformers = new TransformWriteField(this.transformers, ctField, ctClass.getName(), s);
    }
    
    public void replaceArrayAccess(final CtClass ctClass, final ArrayAccessReplacementMethodNames arrayAccessReplacementMethodNames) throws NotFoundException {
        this.transformers = new TransformAccessArrayField(this.transformers, ctClass.getName(), arrayAccessReplacementMethodNames);
    }
    
    public void redirectMethodCall(final CtMethod ctMethod, final CtMethod ctMethod2) throws CannotCompileException {
        if (!ctMethod.getMethodInfo2().getDescriptor().equals(ctMethod2.getMethodInfo2().getDescriptor())) {
            throw new CannotCompileException("signature mismatch: " + ctMethod2.getLongName());
        }
        final int modifiers = ctMethod.getModifiers();
        final int modifiers2 = ctMethod2.getModifiers();
        if (Modifier.isStatic(modifiers) != Modifier.isStatic(modifiers2) || (Modifier.isPrivate(modifiers) && !Modifier.isPrivate(modifiers2)) || ctMethod.getDeclaringClass().isInterface() != ctMethod2.getDeclaringClass().isInterface()) {
            throw new CannotCompileException("invoke-type mismatch " + ctMethod2.getLongName());
        }
        this.transformers = new TransformCall(this.transformers, ctMethod, ctMethod2);
    }
    
    public void redirectMethodCall(final String s, final CtMethod ctMethod) throws CannotCompileException {
        this.transformers = new TransformCall(this.transformers, s, ctMethod);
    }
    
    public void redirectMethodCallToStatic(final CtMethod ctMethod, final CtMethod ctMethod2) {
        this.transformers = new TransformCallToStatic(this.transformers, ctMethod, ctMethod2);
    }
    
    public void insertBeforeMethod(final CtMethod ctMethod, final CtMethod ctMethod2) throws CannotCompileException {
        this.transformers = new TransformBefore(this.transformers, ctMethod, ctMethod2);
    }
    
    public void insertAfterMethod(final CtMethod ctMethod, final CtMethod ctMethod2) throws CannotCompileException {
        this.transformers = new TransformAfter(this.transformers, ctMethod, ctMethod2);
    }
    
    protected void doit(final CtClass ctClass, final MethodInfo methodInfo, final ConstPool constPool) throws CannotCompileException {
        final CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute == null || this.transformers == null) {
            return;
        }
        for (Transformer transformer = this.transformers; transformer != null; transformer = transformer.getNext()) {
            transformer.initialize(constPool, ctClass, methodInfo);
        }
        final CodeIterator iterator = codeAttribute.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            for (Transformer transformer2 = this.transformers; transformer2 != null; transformer2 = transformer2.getNext()) {
                transformer2.transform(ctClass, 0, iterator, constPool);
            }
        }
        for (Transformer transformer3 = this.transformers; transformer3 != null; transformer3 = transformer3.getNext()) {
            if (transformer3.extraLocals() > 0) {}
            if (transformer3.extraStack() > 0) {}
        }
        for (Transformer transformer4 = this.transformers; transformer4 != null; transformer4 = transformer4.getNext()) {
            transformer4.clean();
        }
        if (0 > 0) {
            codeAttribute.setMaxLocals(codeAttribute.getMaxLocals() + 0);
        }
        if (0 > 0) {
            codeAttribute.setMaxStack(codeAttribute.getMaxStack() + 0);
        }
        methodInfo.rebuildStackMapIf6(ctClass.getClassPool(), ctClass.getClassFile2());
    }
    
    public static class DefaultArrayAccessReplacementMethodNames implements ArrayAccessReplacementMethodNames
    {
        @Override
        public String byteOrBooleanRead() {
            return "arrayReadByteOrBoolean";
        }
        
        @Override
        public String byteOrBooleanWrite() {
            return "arrayWriteByteOrBoolean";
        }
        
        @Override
        public String charRead() {
            return "arrayReadChar";
        }
        
        @Override
        public String charWrite() {
            return "arrayWriteChar";
        }
        
        @Override
        public String doubleRead() {
            return "arrayReadDouble";
        }
        
        @Override
        public String doubleWrite() {
            return "arrayWriteDouble";
        }
        
        @Override
        public String floatRead() {
            return "arrayReadFloat";
        }
        
        @Override
        public String floatWrite() {
            return "arrayWriteFloat";
        }
        
        @Override
        public String intRead() {
            return "arrayReadInt";
        }
        
        @Override
        public String intWrite() {
            return "arrayWriteInt";
        }
        
        @Override
        public String longRead() {
            return "arrayReadLong";
        }
        
        @Override
        public String longWrite() {
            return "arrayWriteLong";
        }
        
        @Override
        public String objectRead() {
            return "arrayReadObject";
        }
        
        @Override
        public String objectWrite() {
            return "arrayWriteObject";
        }
        
        @Override
        public String shortRead() {
            return "arrayReadShort";
        }
        
        @Override
        public String shortWrite() {
            return "arrayWriteShort";
        }
    }
    
    public interface ArrayAccessReplacementMethodNames
    {
        String byteOrBooleanRead();
        
        String byteOrBooleanWrite();
        
        String charRead();
        
        String charWrite();
        
        String doubleRead();
        
        String doubleWrite();
        
        String floatRead();
        
        String floatWrite();
        
        String intRead();
        
        String intWrite();
        
        String longRead();
        
        String longWrite();
        
        String objectRead();
        
        String objectWrite();
        
        String shortRead();
        
        String shortWrite();
    }
}
