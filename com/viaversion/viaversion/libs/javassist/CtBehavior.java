package com.viaversion.viaversion.libs.javassist;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.expr.*;
import com.viaversion.viaversion.libs.javassist.compiler.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public abstract class CtBehavior extends CtMember
{
    protected MethodInfo methodInfo;
    
    protected CtBehavior(final CtClass ctClass, final MethodInfo methodInfo) {
        super(ctClass);
        this.methodInfo = methodInfo;
    }
    
    void copy(final CtBehavior ctBehavior, final boolean b, ClassMap classMap) throws CannotCompileException {
        final CtClass declaringClass = this.declaringClass;
        final MethodInfo methodInfo = ctBehavior.methodInfo;
        final CtClass declaringClass2 = ctBehavior.getDeclaringClass();
        final ConstPool constPool = declaringClass.getClassFile2().getConstPool();
        classMap = new ClassMap(classMap);
        classMap.put(declaringClass2.getName(), declaringClass.getName());
        final CtClass superclass = declaringClass2.getSuperclass();
        final CtClass superclass2 = declaringClass.getSuperclass();
        String name = null;
        if (superclass != null && superclass2 != null) {
            final String name2 = superclass.getName();
            name = superclass2.getName();
            if (!name2.equals(name)) {
                if (!name2.equals("java.lang.Object")) {
                    classMap.putIfNone(name2, name);
                }
            }
        }
        this.methodInfo = new MethodInfo(constPool, methodInfo.getName(), methodInfo, classMap);
        if (b && true) {
            this.methodInfo.setSuperclass(name);
        }
    }
    
    @Override
    protected void extendToString(final StringBuffer sb) {
        sb.append(' ');
        sb.append(this.getName());
        sb.append(' ');
        sb.append(this.methodInfo.getDescriptor());
    }
    
    public abstract String getLongName();
    
    public MethodInfo getMethodInfo() {
        this.declaringClass.checkModify();
        return this.methodInfo;
    }
    
    public MethodInfo getMethodInfo2() {
        return this.methodInfo;
    }
    
    @Override
    public int getModifiers() {
        return AccessFlag.toModifier(this.methodInfo.getAccessFlags());
    }
    
    @Override
    public void setModifiers(final int n) {
        this.declaringClass.checkModify();
        this.methodInfo.setAccessFlags(AccessFlag.of(n));
    }
    
    @Override
    public boolean hasAnnotation(final String s) {
        final MethodInfo methodInfo2 = this.getMethodInfo2();
        return CtClassType.hasAnnotationType(s, this.getDeclaringClass().getClassPool(), (AnnotationsAttribute)methodInfo2.getAttribute("RuntimeInvisibleAnnotations"), (AnnotationsAttribute)methodInfo2.getAttribute("RuntimeVisibleAnnotations"));
    }
    
    @Override
    public Object getAnnotation(final Class clazz) throws ClassNotFoundException {
        final MethodInfo methodInfo2 = this.getMethodInfo2();
        return CtClassType.getAnnotationType(clazz, this.getDeclaringClass().getClassPool(), (AnnotationsAttribute)methodInfo2.getAttribute("RuntimeInvisibleAnnotations"), (AnnotationsAttribute)methodInfo2.getAttribute("RuntimeVisibleAnnotations"));
    }
    
    @Override
    public Object[] getAnnotations() throws ClassNotFoundException {
        return this.getAnnotations(false);
    }
    
    @Override
    public Object[] getAvailableAnnotations() {
        return this.getAnnotations(true);
    }
    
    private Object[] getAnnotations(final boolean b) throws ClassNotFoundException {
        final MethodInfo methodInfo2 = this.getMethodInfo2();
        return CtClassType.toAnnotationType(b, this.getDeclaringClass().getClassPool(), (AnnotationsAttribute)methodInfo2.getAttribute("RuntimeInvisibleAnnotations"), (AnnotationsAttribute)methodInfo2.getAttribute("RuntimeVisibleAnnotations"));
    }
    
    public Object[][] getParameterAnnotations() throws ClassNotFoundException {
        return this.getParameterAnnotations(false);
    }
    
    public Object[][] getAvailableParameterAnnotations() {
        return this.getParameterAnnotations(true);
    }
    
    Object[][] getParameterAnnotations(final boolean b) throws ClassNotFoundException {
        final MethodInfo methodInfo2 = this.getMethodInfo2();
        return CtClassType.toAnnotationType(b, this.getDeclaringClass().getClassPool(), (ParameterAnnotationsAttribute)methodInfo2.getAttribute("RuntimeInvisibleParameterAnnotations"), (ParameterAnnotationsAttribute)methodInfo2.getAttribute("RuntimeVisibleParameterAnnotations"), methodInfo2);
    }
    
    public CtClass[] getParameterTypes() throws NotFoundException {
        return Descriptor.getParameterTypes(this.methodInfo.getDescriptor(), this.declaringClass.getClassPool());
    }
    
    CtClass getReturnType0() throws NotFoundException {
        return Descriptor.getReturnType(this.methodInfo.getDescriptor(), this.declaringClass.getClassPool());
    }
    
    @Override
    public String getSignature() {
        return this.methodInfo.getDescriptor();
    }
    
    @Override
    public String getGenericSignature() {
        final SignatureAttribute signatureAttribute = (SignatureAttribute)this.methodInfo.getAttribute("Signature");
        return (signatureAttribute == null) ? null : signatureAttribute.getSignature();
    }
    
    @Override
    public void setGenericSignature(final String s) {
        this.declaringClass.checkModify();
        this.methodInfo.addAttribute(new SignatureAttribute(this.methodInfo.getConstPool(), s));
    }
    
    public CtClass[] getExceptionTypes() throws NotFoundException {
        final ExceptionsAttribute exceptionsAttribute = this.methodInfo.getExceptionsAttribute();
        String[] exceptions;
        if (exceptionsAttribute == null) {
            exceptions = null;
        }
        else {
            exceptions = exceptionsAttribute.getExceptions();
        }
        return this.declaringClass.getClassPool().get(exceptions);
    }
    
    public void setExceptionTypes(final CtClass[] array) throws NotFoundException {
        this.declaringClass.checkModify();
        if (array == null || array.length == 0) {
            this.methodInfo.removeExceptionsAttribute();
            return;
        }
        final String[] exceptions = new String[array.length];
        while (0 < array.length) {
            exceptions[0] = array[0].getName();
            int n = 0;
            ++n;
        }
        ExceptionsAttribute exceptionsAttribute = this.methodInfo.getExceptionsAttribute();
        if (exceptionsAttribute == null) {
            exceptionsAttribute = new ExceptionsAttribute(this.methodInfo.getConstPool());
            this.methodInfo.setExceptionsAttribute(exceptionsAttribute);
        }
        exceptionsAttribute.setExceptions(exceptions);
    }
    
    public abstract boolean isEmpty();
    
    public void setBody(final String s) throws CannotCompileException {
        this.setBody(s, null, null);
    }
    
    public void setBody(final String s, final String s2, final String s3) throws CannotCompileException {
        final CtClass declaringClass = this.declaringClass;
        declaringClass.checkModify();
        final Javac javac = new Javac(declaringClass);
        if (s3 != null) {
            javac.recordProceed(s2, s3);
        }
        this.methodInfo.setCodeAttribute(javac.compileBody(this, s).toCodeAttribute());
        this.methodInfo.setAccessFlags(this.methodInfo.getAccessFlags() & 0xFFFFFBFF);
        this.methodInfo.rebuildStackMapIf6(declaringClass.getClassPool(), declaringClass.getClassFile2());
        this.declaringClass.rebuildClassFile();
    }
    
    static void setBody0(final CtClass ctClass, final MethodInfo methodInfo, final CtClass ctClass2, final MethodInfo methodInfo2, ClassMap classMap) throws CannotCompileException {
        ctClass2.checkModify();
        classMap = new ClassMap(classMap);
        classMap.put(ctClass.getName(), ctClass2.getName());
        final CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute != null) {
            methodInfo2.setCodeAttribute((CodeAttribute)codeAttribute.copy(methodInfo2.getConstPool(), classMap));
        }
        methodInfo2.setAccessFlags(methodInfo2.getAccessFlags() & 0xFFFFFBFF);
        ctClass2.rebuildClassFile();
    }
    
    @Override
    public byte[] getAttribute(final String s) {
        final AttributeInfo attribute = this.methodInfo.getAttribute(s);
        if (attribute == null) {
            return null;
        }
        return attribute.get();
    }
    
    @Override
    public void setAttribute(final String s, final byte[] array) {
        this.declaringClass.checkModify();
        this.methodInfo.addAttribute(new AttributeInfo(this.methodInfo.getConstPool(), s, array));
    }
    
    public void useCflow(final String s) throws CannotCompileException {
        final CtClass declaringClass = this.declaringClass;
        declaringClass.checkModify();
        declaringClass.getClassPool();
        while (true) {
            final StringBuilder append = new StringBuilder().append("_cflow$");
            final int n = 0;
            int n2 = 0;
            ++n2;
            declaringClass.getDeclaredField(append.append(n).toString());
        }
    }
    
    public void addLocalVariable(final String s, final CtClass ctClass) throws CannotCompileException {
        this.declaringClass.checkModify();
        final ConstPool constPool = this.methodInfo.getConstPool();
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            throw new CannotCompileException("no method body");
        }
        LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTable");
        if (localVariableAttribute == null) {
            localVariableAttribute = new LocalVariableAttribute(constPool);
            codeAttribute.getAttributes().add(localVariableAttribute);
        }
        final int maxLocals = codeAttribute.getMaxLocals();
        final String of = Descriptor.of(ctClass);
        localVariableAttribute.addEntry(0, codeAttribute.getCodeLength(), constPool.addUtf8Info(s), constPool.addUtf8Info(of), maxLocals);
        codeAttribute.setMaxLocals(maxLocals + Descriptor.dataSize(of));
    }
    
    public void insertParameter(final CtClass ctClass) throws CannotCompileException {
        this.declaringClass.checkModify();
        final String descriptor = this.methodInfo.getDescriptor();
        final String insertParameter = Descriptor.insertParameter(ctClass, descriptor);
        this.addParameter2(Modifier.isStatic(this.getModifiers()) ? 0 : 1, ctClass, descriptor);
        this.methodInfo.setDescriptor(insertParameter);
    }
    
    public void addParameter(final CtClass ctClass) throws CannotCompileException {
        this.declaringClass.checkModify();
        final String descriptor = this.methodInfo.getDescriptor();
        final String appendParameter = Descriptor.appendParameter(ctClass, descriptor);
        this.addParameter2((Modifier.isStatic(this.getModifiers()) ? 0 : 1) + Descriptor.paramSize(descriptor), ctClass, descriptor);
        this.methodInfo.setDescriptor(appendParameter);
    }
    
    private void addParameter2(final int n, final CtClass ctClass, final String s) throws BadBytecode {
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute != null) {
            if (ctClass.isPrimitive()) {
                final CtPrimitiveType ctPrimitiveType = (CtPrimitiveType)ctClass;
                ctPrimitiveType.getDataSize();
                ctPrimitiveType.getDescriptor();
            }
            else {
                this.methodInfo.getConstPool().addClassInfo(ctClass);
            }
            codeAttribute.insertLocalVar(n, 1);
            final LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTable");
            if (localVariableAttribute != null) {
                localVariableAttribute.shiftIndex(n, 1);
            }
            final LocalVariableTypeAttribute localVariableTypeAttribute = (LocalVariableTypeAttribute)codeAttribute.getAttribute("LocalVariableTypeTable");
            if (localVariableTypeAttribute != null) {
                localVariableTypeAttribute.shiftIndex(n, 1);
            }
            final StackMapTable stackMapTable = (StackMapTable)codeAttribute.getAttribute("StackMapTable");
            if (stackMapTable != null) {
                stackMapTable.insertLocal(n, StackMapTable.typeTagOf('L'), 0);
            }
            final StackMap stackMap = (StackMap)codeAttribute.getAttribute("StackMap");
            if (stackMap != null) {
                stackMap.insertLocal(n, StackMapTable.typeTagOf('L'), 0);
            }
        }
    }
    
    public void instrument(final CodeConverter codeConverter) throws CannotCompileException {
        this.declaringClass.checkModify();
        codeConverter.doit(this.getDeclaringClass(), this.methodInfo, this.methodInfo.getConstPool());
    }
    
    public void instrument(final ExprEditor exprEditor) throws CannotCompileException {
        if (this.declaringClass.isFrozen()) {
            this.declaringClass.checkModify();
        }
        if (exprEditor.doit(this.declaringClass, this.methodInfo)) {
            this.declaringClass.checkModify();
        }
    }
    
    public void insertBefore(final String s) throws CannotCompileException {
        this.insertBefore(s, true);
    }
    
    private void insertBefore(final String s, final boolean b) throws CannotCompileException {
        final CtClass declaringClass = this.declaringClass;
        declaringClass.checkModify();
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            throw new CannotCompileException("no method body");
        }
        final CodeIterator iterator = codeAttribute.iterator();
        final Javac javac = new Javac(declaringClass);
        javac.recordParamNames(codeAttribute, javac.recordParams(this.getParameterTypes(), Modifier.isStatic(this.getModifiers())));
        javac.recordLocalVariables(codeAttribute, 0);
        javac.recordReturnType(this.getReturnType0(), false);
        javac.compileStmnt(s);
        final Bytecode bytecode = javac.getBytecode();
        final int maxStack = bytecode.getMaxStack();
        final int maxLocals = bytecode.getMaxLocals();
        if (maxStack > codeAttribute.getMaxStack()) {
            codeAttribute.setMaxStack(maxStack);
        }
        if (maxLocals > codeAttribute.getMaxLocals()) {
            codeAttribute.setMaxLocals(maxLocals);
        }
        iterator.insert(bytecode.getExceptionTable(), iterator.insertEx(bytecode.get()));
        if (b) {
            this.methodInfo.rebuildStackMapIf6(declaringClass.getClassPool(), declaringClass.getClassFile2());
        }
    }
    
    public void insertAfter(final String s) throws CannotCompileException {
        this.insertAfter(s, false, false);
    }
    
    public void insertAfter(final String s, final boolean b) throws CannotCompileException {
        this.insertAfter(s, b, false);
    }
    
    public void insertAfter(final String s, final boolean b, final boolean b2) throws CannotCompileException {
        final CtClass declaringClass = this.declaringClass;
        declaringClass.checkModify();
        final ConstPool constPool = this.methodInfo.getConstPool();
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            throw new CannotCompileException("no method body");
        }
        final CodeIterator iterator = codeAttribute.iterator();
        final int maxLocals = codeAttribute.getMaxLocals();
        final Bytecode bytecode = new Bytecode(constPool, 0, maxLocals + 1);
        bytecode.setStackDepth(codeAttribute.getMaxStack() + 1);
        final Javac javac = new Javac(bytecode, declaringClass);
        javac.recordParamNames(codeAttribute, javac.recordParams(this.getParameterTypes(), Modifier.isStatic(this.getModifiers())));
        final CtClass returnType0 = this.getReturnType0();
        final int recordReturnType = javac.recordReturnType(returnType0, true);
        javac.recordLocalVariables(codeAttribute, 0);
        int insertAfterHandler = this.insertAfterHandler(b, bytecode, returnType0, recordReturnType, javac, s);
        int mark2 = iterator.getCodeLength();
        if (b) {
            codeAttribute.getExceptionTable().add(this.getStartPosOfBody(codeAttribute), mark2, mark2, 0);
        }
        while (iterator.hasNext()) {
            final int next = iterator.next();
            if (next >= mark2) {
                break;
            }
            final int byte1 = iterator.byteAt(next);
            if (byte1 != 176 && byte1 != 172 && byte1 != 174 && byte1 != 173 && byte1 != 175 && byte1 != 177) {
                continue;
            }
            if (b2) {
                iterator.setMark2(mark2);
                Bytecode bytecode2;
                Javac javac2;
                int recordReturnType2;
                if (false) {
                    bytecode2 = bytecode;
                    javac2 = javac;
                    recordReturnType2 = recordReturnType;
                }
                else {
                    bytecode2 = new Bytecode(constPool, 0, maxLocals + 1);
                    bytecode2.setStackDepth(codeAttribute.getMaxStack() + 1);
                    javac2 = new Javac(bytecode2, declaringClass);
                    javac2.recordParamNames(codeAttribute, javac2.recordParams(this.getParameterTypes(), Modifier.isStatic(this.getModifiers())));
                    recordReturnType2 = javac2.recordReturnType(returnType0, true);
                    javac2.recordLocalVariables(codeAttribute, 0);
                }
                final int insertAfterAdvice = this.insertAfterAdvice(bytecode2, javac2, s, constPool, returnType0, recordReturnType2);
                iterator.append(bytecode2.getExceptionTable(), iterator.append(bytecode2.get()));
                this.insertGoto(iterator, iterator.getCodeLength() - insertAfterAdvice, next);
                mark2 = iterator.getMark2();
            }
            else {
                if (false) {
                    this.insertAfterAdvice(bytecode, javac, s, constPool, returnType0, recordReturnType);
                    final int append = iterator.append(bytecode.get());
                    iterator.append(bytecode.getExceptionTable(), append);
                    final int n = iterator.getCodeLength() - 0;
                    insertAfterHandler = 0 - append;
                }
                this.insertGoto(iterator, 0, next);
                final int n2 = iterator.getCodeLength() - 0;
                mark2 = 0 - insertAfterHandler;
            }
        }
        if (false) {
            iterator.append(bytecode.getExceptionTable(), iterator.append(bytecode.get()));
        }
        codeAttribute.setMaxStack(bytecode.getMaxStack());
        codeAttribute.setMaxLocals(bytecode.getMaxLocals());
        this.methodInfo.rebuildStackMapIf6(declaringClass.getClassPool(), declaringClass.getClassFile2());
    }
    
    private int insertAfterAdvice(final Bytecode bytecode, final Javac javac, final String s, final ConstPool constPool, final CtClass ctClass, final int n) throws CompileError {
        final int currentPc = bytecode.currentPc();
        if (ctClass == CtClass.voidType) {
            bytecode.addOpcode(1);
            bytecode.addAstore(n);
            javac.compileStmnt(s);
            bytecode.addOpcode(177);
            if (bytecode.getMaxLocals() < 1) {
                bytecode.setMaxLocals(1);
            }
        }
        else {
            bytecode.addStore(n, ctClass);
            javac.compileStmnt(s);
            bytecode.addLoad(n, ctClass);
            if (ctClass.isPrimitive()) {
                bytecode.addOpcode(((CtPrimitiveType)ctClass).getReturnOp());
            }
            else {
                bytecode.addOpcode(176);
            }
        }
        return bytecode.currentPc() - currentPc;
    }
    
    private void insertGoto(final CodeIterator codeIterator, final int mark, int n) throws BadBytecode {
        codeIterator.setMark(mark);
        codeIterator.writeByte(0, n);
        final boolean b = mark + 2 - n > 32767;
        final int n2 = b ? 4 : 2;
        final CodeIterator.Gap insertGap = codeIterator.insertGapAt(n, n2, false);
        n = insertGap.position + insertGap.length - n2;
        final int n3 = codeIterator.getMark() - n;
        if (b) {
            codeIterator.writeByte(200, n);
            codeIterator.write32bit(n3, n + 1);
        }
        else if (n3 <= 32767) {
            codeIterator.writeByte(167, n);
            codeIterator.write16bit(n3, n + 1);
        }
        else {
            if (insertGap.length < 4) {
                final CodeIterator.Gap insertGap2 = codeIterator.insertGapAt(insertGap.position, 2, false);
                n = insertGap2.position + insertGap2.length + insertGap.length - 4;
            }
            codeIterator.writeByte(200, n);
            codeIterator.write32bit(codeIterator.getMark() - n, n + 1);
        }
    }
    
    private int insertAfterHandler(final boolean b, final Bytecode bytecode, final CtClass ctClass, final int n, final Javac javac, final String s) throws CompileError {
        if (!b) {
            return 0;
        }
        final int maxLocals = bytecode.getMaxLocals();
        bytecode.incMaxLocals(1);
        final int currentPc = bytecode.currentPc();
        bytecode.addAstore(maxLocals);
        if (ctClass.isPrimitive()) {
            final char descriptor = ((CtPrimitiveType)ctClass).getDescriptor();
            if (descriptor == 'D') {
                bytecode.addDconst(0.0);
                bytecode.addDstore(n);
            }
            else if (descriptor == 'F') {
                bytecode.addFconst(0.0f);
                bytecode.addFstore(n);
            }
            else if (descriptor == 'J') {
                bytecode.addLconst(0L);
                bytecode.addLstore(n);
            }
            else if (descriptor == 'V') {
                bytecode.addOpcode(1);
                bytecode.addAstore(n);
            }
            else {
                bytecode.addIconst(0);
                bytecode.addIstore(n);
            }
        }
        else {
            bytecode.addOpcode(1);
            bytecode.addAstore(n);
        }
        javac.compileStmnt(s);
        bytecode.addAload(maxLocals);
        bytecode.addOpcode(191);
        return bytecode.currentPc() - currentPc;
    }
    
    public void addCatch(final String s, final CtClass ctClass) throws CannotCompileException {
        this.addCatch(s, ctClass, "$e");
    }
    
    public void addCatch(final String s, final CtClass ctClass, final String s2) throws CannotCompileException {
        final CtClass declaringClass = this.declaringClass;
        declaringClass.checkModify();
        final ConstPool constPool = this.methodInfo.getConstPool();
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        final CodeIterator iterator = codeAttribute.iterator();
        final Bytecode bytecode = new Bytecode(constPool, codeAttribute.getMaxStack(), codeAttribute.getMaxLocals());
        bytecode.setStackDepth(1);
        final Javac javac = new Javac(bytecode, declaringClass);
        javac.recordParams(this.getParameterTypes(), Modifier.isStatic(this.getModifiers()));
        bytecode.addAstore(javac.recordVariable(ctClass, s2));
        javac.compileStmnt(s);
        final int maxStack = bytecode.getMaxStack();
        final int maxLocals = bytecode.getMaxLocals();
        if (maxStack > codeAttribute.getMaxStack()) {
            codeAttribute.setMaxStack(maxStack);
        }
        if (maxLocals > codeAttribute.getMaxLocals()) {
            codeAttribute.setMaxLocals(maxLocals);
        }
        final int codeLength = iterator.getCodeLength();
        final int append = iterator.append(bytecode.get());
        codeAttribute.getExceptionTable().add(this.getStartPosOfBody(codeAttribute), codeLength, codeLength, constPool.addClassInfo(ctClass));
        iterator.append(bytecode.getExceptionTable(), append);
        this.methodInfo.rebuildStackMapIf6(declaringClass.getClassPool(), declaringClass.getClassFile2());
    }
    
    int getStartPosOfBody(final CodeAttribute codeAttribute) throws CannotCompileException {
        return 0;
    }
    
    public int insertAt(final int n, final String s) throws CannotCompileException {
        return this.insertAt(n, true, s);
    }
    
    public int insertAt(int line, final boolean b, final String s) throws CannotCompileException {
        final CodeAttribute codeAttribute = this.methodInfo.getCodeAttribute();
        if (codeAttribute == null) {
            throw new CannotCompileException("no method body");
        }
        final LineNumberAttribute lineNumberAttribute = (LineNumberAttribute)codeAttribute.getAttribute("LineNumberTable");
        if (lineNumberAttribute == null) {
            throw new CannotCompileException("no line number info");
        }
        final LineNumberAttribute.Pc nearPc = lineNumberAttribute.toNearPc(line);
        line = nearPc.line;
        final int index = nearPc.index;
        if (!b) {
            return line;
        }
        final CtClass declaringClass = this.declaringClass;
        declaringClass.checkModify();
        final CodeIterator iterator = codeAttribute.iterator();
        final Javac javac = new Javac(declaringClass);
        javac.recordLocalVariables(codeAttribute, index);
        javac.recordParams(this.getParameterTypes(), Modifier.isStatic(this.getModifiers()));
        javac.setMaxLocals(codeAttribute.getMaxLocals());
        javac.compileStmnt(s);
        final Bytecode bytecode = javac.getBytecode();
        final int maxLocals = bytecode.getMaxLocals();
        final int maxStack = bytecode.getMaxStack();
        codeAttribute.setMaxLocals(maxLocals);
        if (maxStack > codeAttribute.getMaxStack()) {
            codeAttribute.setMaxStack(maxStack);
        }
        iterator.insert(bytecode.getExceptionTable(), iterator.insertAt(index, bytecode.get()));
        this.methodInfo.rebuildStackMapIf6(declaringClass.getClassPool(), declaringClass.getClassFile2());
        return line;
    }
}
