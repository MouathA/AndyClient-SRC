package com.viaversion.viaversion.libs.javassist.compiler;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.compiler.ast.*;

public class MemberCodeGen extends CodeGen
{
    protected MemberResolver resolver;
    protected CtClass thisClass;
    protected MethodInfo thisMethod;
    protected boolean resultStatic;
    
    public MemberCodeGen(final Bytecode bytecode, final CtClass thisClass, final ClassPool classPool) {
        super(bytecode);
        this.resolver = new MemberResolver(classPool);
        this.thisClass = thisClass;
        this.thisMethod = null;
    }
    
    public int getMajorVersion() {
        final ClassFile classFile2 = this.thisClass.getClassFile2();
        if (classFile2 == null) {
            return ClassFile.MAJOR_VERSION;
        }
        return classFile2.getMajorVersion();
    }
    
    public void setThisMethod(final CtMethod ctMethod) {
        this.thisMethod = ctMethod.getMethodInfo2();
        if (this.typeChecker != null) {
            this.typeChecker.setThisMethod(this.thisMethod);
        }
    }
    
    public CtClass getThisClass() {
        return this.thisClass;
    }
    
    @Override
    protected String getThisName() {
        return MemberResolver.javaToJvmName(this.thisClass.getName());
    }
    
    @Override
    protected String getSuperName() throws CompileError {
        return MemberResolver.javaToJvmName(MemberResolver.getSuperclass(this.thisClass).getName());
    }
    
    @Override
    protected void insertDefaultSuperCall() throws CompileError {
        this.bytecode.addAload(0);
        this.bytecode.addInvokespecial(MemberResolver.getSuperclass(this.thisClass), "<init>", "()V");
    }
    
    @Override
    protected void atTryStmnt(final Stmnt stmnt) throws CompileError {
        final Bytecode bytecode = this.bytecode;
        final Stmnt stmnt2 = (Stmnt)stmnt.getLeft();
        if (stmnt2 == null) {
            return;
        }
        ASTList tail = (ASTList)stmnt.getRight().getLeft();
        final Stmnt stmnt3 = (Stmnt)stmnt.getRight().getRight().getLeft();
        final ArrayList<Integer> list = new ArrayList<Integer>();
        JsrHook jsrHook = null;
        if (stmnt3 != null) {
            jsrHook = new JsrHook(this);
        }
        final int currentPc = bytecode.currentPc();
        stmnt2.accept(this);
        final int currentPc2 = bytecode.currentPc();
        if (currentPc == currentPc2) {
            throw new CompileError("empty try block");
        }
        final boolean b = !this.hasReturned;
        if (true) {
            bytecode.addOpcode(167);
            list.add(bytecode.currentPc());
            bytecode.addIndex(0);
        }
        final int maxLocals = this.getMaxLocals();
        this.incMaxLocals(1);
        while (tail != null) {
            final Pair pair = (Pair)tail.head();
            tail = tail.tail();
            final Declarator declarator = (Declarator)pair.getLeft();
            final Stmnt stmnt4 = (Stmnt)pair.getRight();
            declarator.setLocalVar(maxLocals);
            final CtClass lookupClassByJvmName = this.resolver.lookupClassByJvmName(declarator.getClassName());
            declarator.setClassName(MemberResolver.javaToJvmName(lookupClassByJvmName.getName()));
            bytecode.addExceptionHandler(currentPc, currentPc2, bytecode.currentPc(), lookupClassByJvmName);
            bytecode.growStack(1);
            bytecode.addAstore(maxLocals);
            this.hasReturned = false;
            if (stmnt4 != null) {
                stmnt4.accept(this);
            }
            if (!this.hasReturned) {
                bytecode.addOpcode(167);
                list.add(bytecode.currentPc());
                bytecode.addIndex(0);
            }
        }
        if (stmnt3 != null) {
            jsrHook.remove(this);
            final int currentPc3 = bytecode.currentPc();
            bytecode.addExceptionHandler(currentPc, currentPc3, currentPc3, 0);
            bytecode.growStack(1);
            bytecode.addAstore(maxLocals);
            this.hasReturned = false;
            stmnt3.accept(this);
            if (!this.hasReturned) {
                bytecode.addAload(maxLocals);
                bytecode.addOpcode(191);
            }
            this.addFinally(jsrHook.jsrList, stmnt3);
        }
        this.patchGoto(list, bytecode.currentPc());
        this.hasReturned = !true;
        if (stmnt3 != null && true) {
            stmnt3.accept(this);
        }
    }
    
    private void addFinally(final List list, final Stmnt stmnt) throws CompileError {
        final Bytecode bytecode = this.bytecode;
        for (final int[] array : list) {
            final int n = array[0];
            bytecode.write16bit(n, bytecode.currentPc() - n + 1);
            final JsrHook2 jsrHook2 = new JsrHook2(this, array);
            stmnt.accept(this);
            jsrHook2.remove(this);
            if (!this.hasReturned) {
                bytecode.addOpcode(167);
                bytecode.addIndex(n + 3 - bytecode.currentPc());
            }
        }
    }
    
    @Override
    public void atNewExpr(final NewExpr newExpr) throws CompileError {
        if (newExpr.isArray()) {
            this.atNewArrayExpr(newExpr);
        }
        else {
            final CtClass lookupClassByName = this.resolver.lookupClassByName(newExpr.getClassName());
            final String name = lookupClassByName.getName();
            final ASTList arguments = newExpr.getArguments();
            this.bytecode.addNew(name);
            this.bytecode.addOpcode(89);
            this.atMethodCallCore(lookupClassByName, "<init>", arguments, false, true, -1, null);
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = MemberResolver.javaToJvmName(name);
        }
    }
    
    public void atNewArrayExpr(final NewExpr newExpr) throws CompileError {
        final int arrayType = newExpr.getArrayType();
        final ASTList arraySize = newExpr.getArraySize();
        final ASTList className = newExpr.getClassName();
        final ArrayInit initializer = newExpr.getInitializer();
        if (arraySize.length() <= 1) {
            this.atNewArrayExpr2(arrayType, arraySize.head(), Declarator.astToClassName(className, '/'), initializer);
            return;
        }
        if (initializer != null) {
            throw new CompileError("sorry, multi-dimensional array initializer for new is not supported");
        }
        this.atMultiNewArray(arrayType, className, arraySize);
    }
    
    private void atNewArrayExpr2(final int exprType, final ASTree asTree, final String s, final ArrayInit arrayInit) throws CompileError {
        if (arrayInit == null) {
            if (asTree == null) {
                throw new CompileError("no array size");
            }
            asTree.accept(this);
        }
        else {
            if (asTree != null) {
                throw new CompileError("unnecessary array size specified for new");
            }
            this.bytecode.addIconst(arrayInit.size());
        }
        String resolveClassName;
        if (exprType == 307) {
            resolveClassName = this.resolveClassName(s);
            this.bytecode.addAnewarray(MemberResolver.jvmToJavaName(resolveClassName));
        }
        else {
            resolveClassName = null;
            switch (exprType) {
                case 301: {}
                case 306: {}
                case 317: {}
                case 312: {}
                case 303: {}
                case 334: {}
                case 324: {}
            }
            this.bytecode.addOpcode(188);
            this.bytecode.add(11);
        }
        if (arrayInit != null) {
            arrayInit.size();
            ASTList tail = arrayInit;
            while (0 < 11) {
                this.bytecode.addOpcode(89);
                this.bytecode.addIconst(0);
                tail.head().accept(this);
                if (!CodeGen.isRefType(exprType)) {
                    this.atNumCastExpr(this.exprType, exprType);
                }
                this.bytecode.addOpcode(CodeGen.getArrayWriteOp(exprType, 0));
                tail = tail.tail();
                int n = 0;
                ++n;
            }
        }
        this.exprType = exprType;
        this.arrayDim = 1;
        this.className = resolveClassName;
    }
    
    private static void badNewExpr() throws CompileError {
        throw new CompileError("bad new expression");
    }
    
    @Override
    protected void atArrayVariableAssign(final ArrayInit arrayInit, final int n, final int n2, final String s) throws CompileError {
        this.atNewArrayExpr2(n, null, s, arrayInit);
    }
    
    @Override
    public void atArrayInit(final ArrayInit arrayInit) throws CompileError {
        throw new CompileError("array initializer is not supported");
    }
    
    protected void atMultiNewArray(final int exprType, final ASTList list, ASTList tail) throws CompileError {
        final int length = tail.length();
        while (tail != null) {
            final ASTree head = tail.head();
            if (head == null) {
                break;
            }
            int n = 0;
            ++n;
            head.accept(this);
            if (this.exprType != 324) {
                throw new CompileError("bad type for array size");
            }
            tail = tail.tail();
        }
        this.exprType = exprType;
        this.arrayDim = length;
        String s;
        if (exprType == 307) {
            this.className = this.resolveClassName(list);
            s = CodeGen.toJvmArrayName(this.className, length);
        }
        else {
            s = CodeGen.toJvmTypeName(exprType, length);
        }
        this.bytecode.addMultiNewarray(s, 0);
    }
    
    @Override
    public void atCallExpr(final CallExpr callExpr) throws CompileError {
        String s = null;
        CtClass ctClass = null;
        final ASTree oprand1 = callExpr.oprand1();
        final ASTList list = (ASTList)callExpr.oprand2();
        final MemberResolver.Method method = callExpr.getMethod();
        if (oprand1 instanceof Member) {
            s = ((Member)oprand1).get();
            ctClass = this.thisClass;
            if (!this.inStaticMethod && (method == null || !method.isStatic())) {
                this.bytecode.currentPc();
                this.bytecode.addAload(0);
            }
        }
        else if (oprand1 instanceof Keyword) {
            s = "<init>";
            ctClass = this.thisClass;
            if (this.inStaticMethod) {
                throw new CompileError("a constructor cannot be static");
            }
            this.bytecode.addAload(0);
            if (((Keyword)oprand1).get() == 336) {
                ctClass = MemberResolver.getSuperclass(ctClass);
            }
        }
        else if (oprand1 instanceof Expr) {
            final Expr expr = (Expr)oprand1;
            s = ((Symbol)expr.oprand2()).get();
            final int operator = expr.getOperator();
            if (operator == 35) {
                ctClass = this.resolver.lookupClass(((Symbol)expr.oprand1()).get(), false);
            }
            else if (operator == 46) {
                final ASTree oprand2 = expr.oprand1();
                final String dotSuper = TypeChecker.isDotSuper(oprand2);
                if (dotSuper != null) {
                    ctClass = MemberResolver.getSuperInterface(this.thisClass, dotSuper);
                    if (!this.inStaticMethod && (method == null || !method.isStatic())) {
                        this.bytecode.currentPc();
                        this.bytecode.addAload(0);
                    }
                }
                else {
                    if (!(oprand2 instanceof Keyword) || ((Keyword)oprand2).get() == 336) {}
                    oprand2.accept(this);
                    if (this.arrayDim > 0) {
                        ctClass = this.resolver.lookupClass("java.lang.Object", true);
                    }
                    else if (this.exprType == 307) {
                        ctClass = this.resolver.lookupClassByJvmName(this.className);
                    }
                }
            }
        }
        else {
            fatal();
        }
        this.atMethodCallCore(ctClass, s, list, true, true, -1, method);
    }
    
    private static void badMethod() throws CompileError {
        throw new CompileError("bad method");
    }
    
    public void atMethodCallCore(final CtClass ctClass, final String s, final ASTList list, final boolean b, final boolean b2, final int n, MemberResolver.Method lookupMethod) throws CompileError {
        final int methodArgsLength = this.getMethodArgsLength(list);
        final int[] array = new int[methodArgsLength];
        final int[] array2 = new int[methodArgsLength];
        final String[] array3 = new String[methodArgsLength];
        if (!true && lookupMethod != null && lookupMethod.isStatic()) {
            this.bytecode.addOpcode(87);
        }
        this.bytecode.getStackDepth();
        this.atMethodArgs(list, array, array2, array3);
        if (lookupMethod == null) {
            lookupMethod = this.resolver.lookupMethod(ctClass, this.thisClass, this.thisMethod, s, array, array2, array3);
        }
        if (lookupMethod == null) {
            String string;
            if (s.equals("<init>")) {
                string = "constructor not found";
            }
            else {
                string = "Method " + s + " not found in " + ctClass.getName();
            }
            throw new CompileError(string);
        }
        this.atMethodCallCore2(ctClass, s, true, b2, n, lookupMethod);
    }
    
    private boolean isFromSameDeclaringClass(CtClass declaringClass, final CtClass ctClass) {
        while (declaringClass != null) {
            if (this.isEnclosing(declaringClass, ctClass)) {
                return true;
            }
            declaringClass = declaringClass.getDeclaringClass();
        }
        return false;
    }
    
    private void atMethodCallCore2(final CtClass ctClass, String accessiblePrivate, final boolean b, final boolean b2, final int n, final MemberResolver.Method method) throws CompileError {
        CtClass declaring = method.declaring;
        final MethodInfo info = method.info;
        String s = info.getDescriptor();
        int accessFlags = info.getAccessFlags();
        if (accessiblePrivate.equals("<init>")) {
            if (declaring != ctClass) {
                throw new CompileError("no such constructor: " + ctClass.getName());
            }
            if (declaring != this.thisClass && AccessFlag.isPrivate(accessFlags) && (declaring.getClassFile().getMajorVersion() < 55 || !this.isFromSameDeclaringClass(declaring, this.thisClass))) {
                s = this.getAccessibleConstructor(s, declaring, info);
                this.bytecode.addOpcode(1);
            }
        }
        else if (AccessFlag.isPrivate(accessFlags)) {
            if (declaring != this.thisClass) {
                final String s2 = s;
                if ((accessFlags & 0x8) == 0x0) {
                    s = Descriptor.insertParameter(declaring.getName(), s2);
                }
                accessFlags = (AccessFlag.setPackage(accessFlags) | 0x8);
                accessiblePrivate = this.getAccessiblePrivate(accessiblePrivate, s2, s, info, declaring);
            }
        }
        if ((accessFlags & 0x8) != 0x0) {
            if (!true && n >= 0) {
                this.bytecode.write(n, 0);
            }
            this.bytecode.addInvokestatic(declaring, accessiblePrivate, s);
        }
        else if (false) {
            this.bytecode.addInvokespecial(ctClass, accessiblePrivate, s);
        }
        else {
            if (!Modifier.isPublic(declaring.getModifiers()) || declaring.isInterface() != ctClass.isInterface()) {
                declaring = ctClass;
            }
            if (declaring.isInterface()) {
                this.bytecode.addInvokeinterface(declaring, accessiblePrivate, s, Descriptor.paramSize(s) + 1);
            }
            else {
                if (true) {
                    throw new CompileError(accessiblePrivate + " is not static");
                }
                this.bytecode.addInvokevirtual(declaring, accessiblePrivate, s);
            }
        }
        this.setReturnType(s, true, true);
    }
    
    protected String getAccessiblePrivate(final String s, final String s2, final String s3, final MethodInfo methodInfo, final CtClass ctClass) throws CompileError {
        if (this.isEnclosing(ctClass, this.thisClass)) {
            final AccessorMaker accessorMaker = ctClass.getAccessorMaker();
            if (accessorMaker != null) {
                return accessorMaker.getMethodAccessor(s, s2, s3, methodInfo);
            }
        }
        throw new CompileError("Method " + s + " is private");
    }
    
    protected String getAccessibleConstructor(final String s, final CtClass ctClass, final MethodInfo methodInfo) throws CompileError {
        if (this.isEnclosing(ctClass, this.thisClass)) {
            final AccessorMaker accessorMaker = ctClass.getAccessorMaker();
            if (accessorMaker != null) {
                return accessorMaker.getConstructor(ctClass, s, methodInfo);
            }
        }
        throw new CompileError("the called constructor is private in " + ctClass.getName());
    }
    
    private boolean isEnclosing(final CtClass ctClass, CtClass declaringClass) {
        while (declaringClass != null) {
            declaringClass = declaringClass.getDeclaringClass();
            if (declaringClass == ctClass) {
                return true;
            }
        }
        return false;
    }
    
    public int getMethodArgsLength(final ASTList list) {
        return ASTList.length(list);
    }
    
    public void atMethodArgs(ASTList tail, final int[] array, final int[] array2, final String[] array3) throws CompileError {
        while (tail != null) {
            tail.head().accept(this);
            array[0] = this.exprType;
            array2[0] = this.arrayDim;
            array3[0] = this.className;
            int n = 0;
            ++n;
            tail = tail.tail();
        }
    }
    
    void setReturnType(final String p0, final boolean p1, final boolean p2) throws CompileError {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: invokevirtual   java/lang/String.indexOf:(I)I
        //     6: istore          4
        //     8: iload           4
        //    10: aload_1        
        //    11: iinc            4, 1
        //    14: iload           4
        //    16: invokevirtual   java/lang/String.charAt:(I)C
        //    19: istore          5
        //    21: iload           5
        //    23: bipush          91
        //    25: if_icmpne       45
        //    28: iinc            6, 1
        //    31: aload_1        
        //    32: iinc            4, 1
        //    35: iload           4
        //    37: invokevirtual   java/lang/String.charAt:(I)C
        //    40: istore          5
        //    42: goto            21
        //    45: aload_0        
        //    46: iconst_0       
        //    47: putfield        com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.arrayDim:I
        //    50: iload           5
        //    52: bipush          76
        //    54: if_icmpne       95
        //    57: aload_1        
        //    58: bipush          59
        //    60: iload           4
        //    62: iconst_1       
        //    63: iadd           
        //    64: invokevirtual   java/lang/String.indexOf:(II)I
        //    67: istore          7
        //    69: iload           7
        //    71: aload_0        
        //    72: sipush          307
        //    75: putfield        com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.exprType:I
        //    78: aload_0        
        //    79: aload_1        
        //    80: iload           4
        //    82: iconst_1       
        //    83: iadd           
        //    84: iload           7
        //    86: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //    89: putfield        com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.className:Ljava/lang/String;
        //    92: goto            109
        //    95: aload_0        
        //    96: iload           5
        //    98: invokestatic    com/viaversion/viaversion/libs/javassist/compiler/MemberResolver.descToType:(C)I
        //   101: putfield        com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.exprType:I
        //   104: aload_0        
        //   105: aconst_null    
        //   106: putfield        com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.className:Ljava/lang/String;
        //   109: aload_0        
        //   110: getfield        com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.exprType:I
        //   113: istore          7
        //   115: iload_2        
        //   116: ifeq            200
        //   119: iload_3        
        //   120: ifeq            200
        //   123: iload           7
        //   125: iconst_0       
        //   126: invokestatic    com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.is2word:(II)Z
        //   129: ifeq            162
        //   132: aload_0        
        //   133: getfield        com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.bytecode:Lcom/viaversion/viaversion/libs/javassist/bytecode/Bytecode;
        //   136: bipush          93
        //   138: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/Bytecode.addOpcode:(I)V
        //   141: aload_0        
        //   142: getfield        com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.bytecode:Lcom/viaversion/viaversion/libs/javassist/bytecode/Bytecode;
        //   145: bipush          88
        //   147: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/Bytecode.addOpcode:(I)V
        //   150: aload_0        
        //   151: getfield        com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.bytecode:Lcom/viaversion/viaversion/libs/javassist/bytecode/Bytecode;
        //   154: bipush          87
        //   156: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/Bytecode.addOpcode:(I)V
        //   159: goto            200
        //   162: iload           7
        //   164: sipush          344
        //   167: if_icmpne       182
        //   170: aload_0        
        //   171: getfield        com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.bytecode:Lcom/viaversion/viaversion/libs/javassist/bytecode/Bytecode;
        //   174: bipush          87
        //   176: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/Bytecode.addOpcode:(I)V
        //   179: goto            200
        //   182: aload_0        
        //   183: getfield        com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.bytecode:Lcom/viaversion/viaversion/libs/javassist/bytecode/Bytecode;
        //   186: bipush          95
        //   188: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/Bytecode.addOpcode:(I)V
        //   191: aload_0        
        //   192: getfield        com/viaversion/viaversion/libs/javassist/compiler/MemberCodeGen.bytecode:Lcom/viaversion/viaversion/libs/javassist/bytecode/Bytecode;
        //   195: bipush          87
        //   197: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/Bytecode.addOpcode:(I)V
        //   200: return         
        //    Exceptions:
        //  throws com.viaversion.viaversion.libs.javassist.compiler.CompileError
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0109 (coming from #0092).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void atFieldAssign(final Expr expr, final int n, final ASTree asTree, final ASTree asTree2, final boolean b) throws CompileError {
        final CtField fieldAccess = this.fieldAccess(asTree, false);
        final boolean resultStatic = this.resultStatic;
        if (n != 61 && !resultStatic) {
            this.bytecode.addOpcode(89);
        }
        if (n == 61) {
            final FieldInfo fieldInfo2 = fieldAccess.getFieldInfo2();
            this.setFieldType(fieldInfo2);
            if (this.isAccessibleField(fieldAccess, fieldInfo2) == null) {
                this.addFieldrefInfo(fieldAccess, fieldInfo2);
            }
        }
        else {
            this.atFieldRead(fieldAccess, resultStatic);
        }
        final int exprType = this.exprType;
        final int arrayDim = this.arrayDim;
        final String className = this.className;
        this.atAssignCore(expr, n, asTree2, exprType, arrayDim, className);
        final boolean is2word = CodeGen.is2word(exprType, arrayDim);
        if (b) {
            int n2;
            if (resultStatic) {
                n2 = (is2word ? 92 : 89);
            }
            else {
                n2 = (is2word ? 93 : 90);
            }
            this.bytecode.addOpcode(n2);
        }
        this.atFieldAssignCore(fieldAccess, resultStatic, 0, is2word);
        this.exprType = exprType;
        this.arrayDim = arrayDim;
        this.className = className;
    }
    
    private void atFieldAssignCore(final CtField ctField, final boolean b, final int n, final boolean b2) throws CompileError {
        if (n != 0) {
            if (b) {
                this.bytecode.add(179);
                this.bytecode.growStack(b2 ? -2 : -1);
            }
            else {
                this.bytecode.add(181);
                this.bytecode.growStack(b2 ? -3 : -2);
            }
            this.bytecode.addIndex(n);
        }
        else {
            final CtClass declaringClass = ctField.getDeclaringClass();
            final MethodInfo fieldSetter = declaringClass.getAccessorMaker().getFieldSetter(ctField.getFieldInfo2(), b);
            this.bytecode.addInvokestatic(declaringClass, fieldSetter.getName(), fieldSetter.getDescriptor());
        }
    }
    
    @Override
    public void atMember(final Member member) throws CompileError {
        this.atFieldRead(member);
    }
    
    @Override
    protected void atFieldRead(final ASTree asTree) throws CompileError {
        final CtField fieldAccess = this.fieldAccess(asTree, true);
        if (fieldAccess == null) {
            this.atArrayLength(asTree);
            return;
        }
        final boolean resultStatic = this.resultStatic;
        final ASTree constantFieldValue = TypeChecker.getConstantFieldValue(fieldAccess);
        if (constantFieldValue == null) {
            this.atFieldRead(fieldAccess, resultStatic);
        }
        else {
            constantFieldValue.accept(this);
            this.setFieldType(fieldAccess.getFieldInfo2());
        }
    }
    
    private void atArrayLength(final ASTree asTree) throws CompileError {
        if (this.arrayDim == 0) {
            throw new CompileError(".length applied to a non array");
        }
        this.bytecode.addOpcode(190);
        this.exprType = 324;
        this.arrayDim = 0;
    }
    
    private int atFieldRead(final CtField ctField, final boolean b) throws CompileError {
        final FieldInfo fieldInfo2 = ctField.getFieldInfo2();
        final int setFieldType = this.setFieldType(fieldInfo2) ? 1 : 0;
        final AccessorMaker accessibleField = this.isAccessibleField(ctField, fieldInfo2);
        if (accessibleField != null) {
            final MethodInfo fieldGetter = accessibleField.getFieldGetter(fieldInfo2, b);
            this.bytecode.addInvokestatic(ctField.getDeclaringClass(), fieldGetter.getName(), fieldGetter.getDescriptor());
            return 0;
        }
        final int addFieldrefInfo = this.addFieldrefInfo(ctField, fieldInfo2);
        if (b) {
            this.bytecode.add(178);
            this.bytecode.growStack((setFieldType != 0) ? 2 : 1);
        }
        else {
            this.bytecode.add(180);
            this.bytecode.growStack(setFieldType);
        }
        this.bytecode.addIndex(addFieldrefInfo);
        return addFieldrefInfo;
    }
    
    private AccessorMaker isAccessibleField(final CtField ctField, final FieldInfo fieldInfo) throws CompileError {
        if (AccessFlag.isPrivate(fieldInfo.getAccessFlags()) && ctField.getDeclaringClass() != this.thisClass) {
            final CtClass declaringClass = ctField.getDeclaringClass();
            if (this.isEnclosing(declaringClass, this.thisClass)) {
                final AccessorMaker accessorMaker = declaringClass.getAccessorMaker();
                if (accessorMaker != null) {
                    return accessorMaker;
                }
            }
            throw new CompileError("Field " + ctField.getName() + " in " + declaringClass.getName() + " is private.");
        }
        return null;
    }
    
    private boolean setFieldType(final FieldInfo fieldInfo) throws CompileError {
        final String descriptor = fieldInfo.getDescriptor();
        char c;
        String s;
        for (c = descriptor.charAt(0); c == '['; c = s.charAt(0)) {
            int n = 0;
            ++n;
            s = descriptor;
            int n2 = 0;
            ++n2;
        }
        this.arrayDim = 0;
        this.exprType = MemberResolver.descToType(c);
        if (c == 'L') {
            this.className = descriptor.substring(1, descriptor.indexOf(59, 1));
        }
        else {
            this.className = null;
        }
        return !false && (c == 'J' || c == 'D');
    }
    
    private int addFieldrefInfo(final CtField ctField, final FieldInfo fieldInfo) {
        final ConstPool constPool = this.bytecode.getConstPool();
        return constPool.addFieldrefInfo(constPool.addClassInfo(ctField.getDeclaringClass().getName()), fieldInfo.getName(), fieldInfo.getDescriptor());
    }
    
    @Override
    protected void atClassObject2(final String s) throws CompileError {
        if (this.getMajorVersion() < 49) {
            super.atClassObject2(s);
        }
        else {
            this.bytecode.addLdc(this.bytecode.getConstPool().addClassInfo(s));
        }
    }
    
    @Override
    protected void atFieldPlusPlus(final int n, final boolean b, final ASTree asTree, final Expr expr, final boolean b2) throws CompileError {
        final CtField fieldAccess = this.fieldAccess(asTree, false);
        final boolean resultStatic = this.resultStatic;
        if (!resultStatic) {
            this.bytecode.addOpcode(89);
        }
        final int atFieldRead = this.atFieldRead(fieldAccess, resultStatic);
        final boolean is2word = CodeGen.is2word(this.exprType, this.arrayDim);
        int n2;
        if (resultStatic) {
            n2 = (is2word ? 92 : 89);
        }
        else {
            n2 = (is2word ? 93 : 90);
        }
        this.atPlusPlusCore(n2, b2, n, b, expr);
        this.atFieldAssignCore(fieldAccess, resultStatic, atFieldRead, is2word);
    }
    
    protected CtField fieldAccess(final ASTree asTree, final boolean b) throws CompileError {
        if (asTree instanceof Member) {
            final String value = ((Member)asTree).get();
            final CtField field = this.thisClass.getField(value);
            final boolean static1 = Modifier.isStatic(field.getModifiers());
            if (!static1) {
                if (this.inStaticMethod) {
                    throw new CompileError("not available in a static method: " + value);
                }
                this.bytecode.addAload(0);
            }
            this.resultStatic = static1;
            return field;
        }
        if (asTree instanceof Expr) {
            final Expr expr = (Expr)asTree;
            final int operator = expr.getOperator();
            if (operator == 35) {
                final CtField lookupField = this.resolver.lookupField(((Symbol)expr.oprand1()).get(), (Symbol)expr.oprand2());
                this.resultStatic = true;
                return lookupField;
            }
            if (operator == 46) {
                CtField lookupFieldByJvmName = null;
                expr.oprand1().accept(this);
                if (this.exprType == 307 && this.arrayDim == 0) {
                    lookupFieldByJvmName = this.resolver.lookupFieldByJvmName(this.className, (Symbol)expr.oprand2());
                }
                else if (b && this.arrayDim > 0 && ((Symbol)expr.oprand2()).get().equals("length")) {
                    return null;
                }
                final boolean static2 = Modifier.isStatic(lookupFieldByJvmName.getModifiers());
                if (static2) {
                    this.bytecode.addOpcode(87);
                }
                this.resultStatic = static2;
                return lookupFieldByJvmName;
            }
        }
        this.resultStatic = false;
        return null;
    }
    
    private static void badLvalue() throws CompileError {
        throw new CompileError("bad l-value");
    }
    
    public CtClass[] makeParamList(final MethodDecl methodDecl) throws CompileError {
        ASTList list = methodDecl.getParams();
        CtClass[] array;
        if (list == null) {
            array = new CtClass[0];
        }
        else {
            array = new CtClass[list.length()];
            while (list != null) {
                final CtClass[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = this.resolver.lookupClass((Declarator)list.head());
                list = list.tail();
            }
        }
        return array;
    }
    
    public CtClass[] makeThrowsList(final MethodDecl methodDecl) throws CompileError {
        ASTList list = methodDecl.getThrows();
        if (list == null) {
            return null;
        }
        final CtClass[] array = new CtClass[list.length()];
        while (list != null) {
            final CtClass[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = this.resolver.lookupClassByName((ASTList)list.head());
            list = list.tail();
        }
        return array;
    }
    
    @Override
    protected String resolveClassName(final ASTList list) throws CompileError {
        return this.resolver.resolveClassName(list);
    }
    
    @Override
    protected String resolveClassName(final String s) throws CompileError {
        return this.resolver.resolveJvmClassName(s);
    }
    
    static class JsrHook2 extends ReturnHook
    {
        int var;
        int target;
        
        JsrHook2(final CodeGen codeGen, final int[] array) {
            super(codeGen);
            this.target = array[0];
            this.var = array[1];
        }
        
        @Override
        protected boolean doit(final Bytecode bytecode, final int n) {
            switch (n) {
                case 177: {
                    break;
                }
                case 176: {
                    bytecode.addAstore(this.var);
                    break;
                }
                case 172: {
                    bytecode.addIstore(this.var);
                    break;
                }
                case 173: {
                    bytecode.addLstore(this.var);
                    break;
                }
                case 175: {
                    bytecode.addDstore(this.var);
                    break;
                }
                case 174: {
                    bytecode.addFstore(this.var);
                    break;
                }
                default: {
                    throw new RuntimeException("fatal");
                }
            }
            bytecode.addOpcode(167);
            bytecode.addIndex(this.target - bytecode.currentPc() + 3);
            return true;
        }
    }
    
    static class JsrHook extends ReturnHook
    {
        List jsrList;
        CodeGen cgen;
        int var;
        
        JsrHook(final CodeGen cgen) {
            super(cgen);
            this.jsrList = new ArrayList();
            this.cgen = cgen;
            this.var = -1;
        }
        
        private int getVar(final int n) {
            if (this.var < 0) {
                this.var = this.cgen.getMaxLocals();
                this.cgen.incMaxLocals(n);
            }
            return this.var;
        }
        
        private void jsrJmp(final Bytecode bytecode) {
            bytecode.addOpcode(167);
            this.jsrList.add(new int[] { bytecode.currentPc(), this.var });
            bytecode.addIndex(0);
        }
        
        @Override
        protected boolean doit(final Bytecode bytecode, final int n) {
            switch (n) {
                case 177: {
                    this.jsrJmp(bytecode);
                    break;
                }
                case 176: {
                    bytecode.addAstore(this.getVar(1));
                    this.jsrJmp(bytecode);
                    bytecode.addAload(this.var);
                    break;
                }
                case 172: {
                    bytecode.addIstore(this.getVar(1));
                    this.jsrJmp(bytecode);
                    bytecode.addIload(this.var);
                    break;
                }
                case 173: {
                    bytecode.addLstore(this.getVar(2));
                    this.jsrJmp(bytecode);
                    bytecode.addLload(this.var);
                    break;
                }
                case 175: {
                    bytecode.addDstore(this.getVar(2));
                    this.jsrJmp(bytecode);
                    bytecode.addDload(this.var);
                    break;
                }
                case 174: {
                    bytecode.addFstore(this.getVar(1));
                    this.jsrJmp(bytecode);
                    bytecode.addFload(this.var);
                    break;
                }
                default: {
                    throw new RuntimeException("fatal");
                }
            }
            return false;
        }
    }
}
