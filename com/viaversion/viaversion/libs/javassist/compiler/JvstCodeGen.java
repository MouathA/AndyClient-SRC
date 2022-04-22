package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.compiler.ast.*;

public class JvstCodeGen extends MemberCodeGen
{
    String paramArrayName;
    String paramListName;
    CtClass[] paramTypeList;
    private int paramVarBase;
    private boolean useParam0;
    private String param0Type;
    public static final String sigName = "$sig";
    public static final String dollarTypeName = "$type";
    public static final String clazzName = "$class";
    private CtClass dollarType;
    CtClass returnType;
    String returnCastName;
    private String returnVarName;
    public static final String wrapperCastName = "$w";
    String proceedName;
    public static final String cflowName = "$cflow";
    ProceedHandler procHandler;
    
    public JvstCodeGen(final Bytecode bytecode, final CtClass ctClass, final ClassPool classPool) {
        super(bytecode, ctClass, classPool);
        this.paramArrayName = null;
        this.paramListName = null;
        this.paramTypeList = null;
        this.paramVarBase = 0;
        this.useParam0 = false;
        this.param0Type = null;
        this.dollarType = null;
        this.returnType = null;
        this.returnCastName = null;
        this.returnVarName = null;
        this.proceedName = null;
        this.procHandler = null;
        this.setTypeChecker(new JvstTypeChecker(ctClass, classPool, this));
    }
    
    private int indexOfParam1() {
        return this.paramVarBase + (this.useParam0 ? 1 : 0);
    }
    
    public void setProceedHandler(final ProceedHandler procHandler, final String proceedName) {
        this.proceedName = proceedName;
        this.procHandler = procHandler;
    }
    
    public void addNullIfVoid() {
        if (this.exprType == 344) {
            this.bytecode.addOpcode(1);
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Object";
        }
    }
    
    @Override
    public void atMember(final Member member) throws CompileError {
        final String value = member.get();
        if (value.equals(this.paramArrayName)) {
            compileParameterList(this.bytecode, this.paramTypeList, this.indexOfParam1());
            this.exprType = 307;
            this.arrayDim = 1;
            this.className = "java/lang/Object";
        }
        else if (value.equals("$sig")) {
            this.bytecode.addLdc(Descriptor.ofMethod(this.returnType, this.paramTypeList));
            this.bytecode.addInvokestatic("com/viaversion/viaversion/libs/javassist/runtime/Desc", "getParams", "(Ljava/lang/String;)[Ljava/lang/Class;");
            this.exprType = 307;
            this.arrayDim = 1;
            this.className = "java/lang/Class";
        }
        else if (value.equals("$type")) {
            if (this.dollarType == null) {
                throw new CompileError("$type is not available");
            }
            this.bytecode.addLdc(Descriptor.of(this.dollarType));
            this.callGetType("getType");
        }
        else if (value.equals("$class")) {
            if (this.param0Type == null) {
                throw new CompileError("$class is not available");
            }
            this.bytecode.addLdc(this.param0Type);
            this.callGetType("getClazz");
        }
        else {
            super.atMember(member);
        }
    }
    
    private void callGetType(final String s) {
        this.bytecode.addInvokestatic("com/viaversion/viaversion/libs/javassist/runtime/Desc", s, "(Ljava/lang/String;)Ljava/lang/Class;");
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/Class";
    }
    
    @Override
    protected void atFieldAssign(final Expr expr, final int n, final ASTree asTree, final ASTree asTree2, final boolean b) throws CompileError {
        if (asTree instanceof Member && ((Member)asTree).get().equals(this.paramArrayName)) {
            if (n != 61) {
                throw new CompileError("bad operator for " + this.paramArrayName);
            }
            asTree2.accept(this);
            if (this.arrayDim != 1 || this.exprType != 307) {
                throw new CompileError("invalid type for " + this.paramArrayName);
            }
            this.atAssignParamList(this.paramTypeList, this.bytecode);
            if (!b) {
                this.bytecode.addOpcode(87);
            }
        }
        else {
            super.atFieldAssign(expr, n, asTree, asTree2, b);
        }
    }
    
    protected void atAssignParamList(final CtClass[] array, final Bytecode bytecode) throws CompileError {
        if (array == null) {
            return;
        }
        int indexOfParam1 = this.indexOfParam1();
        while (0 < array.length) {
            bytecode.addOpcode(89);
            bytecode.addIconst(0);
            bytecode.addOpcode(50);
            this.compileUnwrapValue(array[0], bytecode);
            bytecode.addStore(indexOfParam1, array[0]);
            indexOfParam1 += (CodeGen.is2word(this.exprType, this.arrayDim) ? 2 : 1);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void atCastExpr(final CastExpr castExpr) throws CompileError {
        final ASTList className = castExpr.getClassName();
        if (className != null && castExpr.getArrayDim() == 0) {
            final ASTree head = className.head();
            if (head instanceof Symbol && className.tail() == null) {
                final String value = ((Symbol)head).get();
                if (value.equals(this.returnCastName)) {
                    this.atCastToRtype(castExpr);
                    return;
                }
                if (value.equals("$w")) {
                    this.atCastToWrapper(castExpr);
                    return;
                }
            }
        }
        super.atCastExpr(castExpr);
    }
    
    protected void atCastToRtype(final CastExpr castExpr) throws CompileError {
        castExpr.getOprand().accept(this);
        if (this.exprType == 344 || CodeGen.isRefType(this.exprType) || this.arrayDim > 0) {
            this.compileUnwrapValue(this.returnType, this.bytecode);
        }
        else {
            if (!(this.returnType instanceof CtPrimitiveType)) {
                throw new CompileError("invalid cast");
            }
            final int descToType = MemberResolver.descToType(((CtPrimitiveType)this.returnType).getDescriptor());
            this.atNumCastExpr(this.exprType, descToType);
            this.exprType = descToType;
            this.arrayDim = 0;
            this.className = null;
        }
    }
    
    protected void atCastToWrapper(final CastExpr castExpr) throws CompileError {
        castExpr.getOprand().accept(this);
        if (CodeGen.isRefType(this.exprType) || this.arrayDim > 0) {
            return;
        }
        final CtClass lookupClass = this.resolver.lookupClass(this.exprType, this.arrayDim, this.className);
        if (lookupClass instanceof CtPrimitiveType) {
            final CtPrimitiveType ctPrimitiveType = (CtPrimitiveType)lookupClass;
            final String wrapperName = ctPrimitiveType.getWrapperName();
            this.bytecode.addNew(wrapperName);
            this.bytecode.addOpcode(89);
            if (ctPrimitiveType.getDataSize() > 1) {
                this.bytecode.addOpcode(94);
            }
            else {
                this.bytecode.addOpcode(93);
            }
            this.bytecode.addOpcode(88);
            this.bytecode.addInvokespecial(wrapperName, "<init>", "(" + ctPrimitiveType.getDescriptor() + ")V");
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Object";
        }
    }
    
    @Override
    public void atCallExpr(final CallExpr callExpr) throws CompileError {
        final ASTree oprand1 = callExpr.oprand1();
        if (oprand1 instanceof Member) {
            final String value = ((Member)oprand1).get();
            if (this.procHandler != null && value.equals(this.proceedName)) {
                this.procHandler.doit(this, this.bytecode, (ASTList)callExpr.oprand2());
                return;
            }
            if (value.equals("$cflow")) {
                this.atCflow((ASTList)callExpr.oprand2());
                return;
            }
        }
        super.atCallExpr(callExpr);
    }
    
    protected void atCflow(final ASTList list) throws CompileError {
        final StringBuffer sb = new StringBuffer();
        if (list == null || list.tail() != null) {
            throw new CompileError("bad $cflow");
        }
        makeCflowName(sb, list.head());
        final String string = sb.toString();
        final Object[] lookupCflow = this.resolver.getClassPool().lookupCflow(string);
        if (lookupCflow == null) {
            throw new CompileError("no such $cflow: " + string);
        }
        this.bytecode.addGetstatic((String)lookupCflow[0], (String)lookupCflow[1], "Lcom/viaversion/viaversion/libs/javassist/runtime/Cflow;");
        this.bytecode.addInvokevirtual("com.viaversion.viaversion.libs.javassist.runtime.Cflow", "value", "()I");
        this.exprType = 324;
        this.arrayDim = 0;
        this.className = null;
    }
    
    private static void makeCflowName(final StringBuffer sb, final ASTree asTree) throws CompileError {
        if (asTree instanceof Symbol) {
            sb.append(((Symbol)asTree).get());
            return;
        }
        if (asTree instanceof Expr) {
            final Expr expr = (Expr)asTree;
            if (expr.getOperator() == 46) {
                makeCflowName(sb, expr.oprand1());
                sb.append('.');
                makeCflowName(sb, expr.oprand2());
                return;
            }
        }
        throw new CompileError("bad $cflow");
    }
    
    public boolean isParamListName(final ASTList list) {
        if (this.paramTypeList != null && list != null && list.tail() == null) {
            final ASTree head = list.head();
            return head instanceof Member && ((Member)head).get().equals(this.paramListName);
        }
        return false;
    }
    
    @Override
    public int getMethodArgsLength(ASTList tail) {
        final String paramListName = this.paramListName;
        while (tail != null) {
            final ASTree head = tail.head();
            if (head instanceof Member && ((Member)head).get().equals(paramListName)) {
                if (this.paramTypeList != null) {
                    final int n = 0 + this.paramTypeList.length;
                }
            }
            else {
                int n = 0;
                ++n;
            }
            tail = tail.tail();
        }
        return 0;
    }
    
    @Override
    public void atMethodArgs(ASTList tail, final int[] array, final int[] array2, final String[] array3) throws CompileError {
        final CtClass[] paramTypeList = this.paramTypeList;
        final String paramListName = this.paramListName;
        while (tail != null) {
            final ASTree head = tail.head();
            if (head instanceof Member && ((Member)head).get().equals(paramListName)) {
                if (paramTypeList != null) {
                    final int length = paramTypeList.length;
                    int indexOfParam1 = this.indexOfParam1();
                    while (0 < length) {
                        final CtClass type = paramTypeList[0];
                        indexOfParam1 += this.bytecode.addLoad(indexOfParam1, type);
                        this.setType(type);
                        array[0] = this.exprType;
                        array2[0] = this.arrayDim;
                        array3[0] = this.className;
                        int n = 0;
                        ++n;
                        int n2 = 0;
                        ++n2;
                    }
                }
            }
            else {
                head.accept(this);
                array[0] = this.exprType;
                array2[0] = this.arrayDim;
                array3[0] = this.className;
                int n = 0;
                ++n;
            }
            tail = tail.tail();
        }
    }
    
    void compileInvokeSpecial(final ASTree asTree, final int n, final String s, final ASTList list) throws CompileError {
        asTree.accept(this);
        final int methodArgsLength = this.getMethodArgsLength(list);
        this.atMethodArgs(list, new int[methodArgsLength], new int[methodArgsLength], new String[methodArgsLength]);
        this.bytecode.addInvokespecial(n, s);
        this.setReturnType(s, false, false);
        this.addNullIfVoid();
    }
    
    @Override
    protected void atReturnStmnt(final Stmnt stmnt) throws CompileError {
        ASTree left = stmnt.getLeft();
        if (left != null && this.returnType == CtClass.voidType) {
            this.compileExpr(left);
            if (CodeGen.is2word(this.exprType, this.arrayDim)) {
                this.bytecode.addOpcode(88);
            }
            else if (this.exprType != 344) {
                this.bytecode.addOpcode(87);
            }
            left = null;
        }
        this.atReturnStmnt2(left);
    }
    
    public int recordReturnType(final CtClass returnType, final String returnCastName, final String returnVarName, final SymbolTable symbolTable) throws CompileError {
        this.returnType = returnType;
        this.returnCastName = returnCastName;
        this.returnVarName = returnVarName;
        if (returnVarName == null) {
            return -1;
        }
        final int maxLocals = this.getMaxLocals();
        this.setMaxLocals(maxLocals + this.recordVar(returnType, returnVarName, maxLocals, symbolTable));
        return maxLocals;
    }
    
    public void recordType(final CtClass dollarType) {
        this.dollarType = dollarType;
    }
    
    public int recordParams(final CtClass[] array, final boolean b, final String s, final String s2, final String s3, final SymbolTable symbolTable) throws CompileError {
        return this.recordParams(array, b, s, s2, s3, !b, 0, this.getThisName(), symbolTable);
    }
    
    public int recordParams(final CtClass[] p0, final boolean p1, final String p2, final String p3, final String p4, final boolean p5, final int p6, final String p7, final SymbolTable p8) throws CompileError {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: putfield        com/viaversion/viaversion/libs/javassist/compiler/JvstCodeGen.paramTypeList:[Lcom/viaversion/viaversion/libs/javassist/CtClass;
        //     5: aload_0        
        //     6: aload           4
        //     8: putfield        com/viaversion/viaversion/libs/javassist/compiler/JvstCodeGen.paramArrayName:Ljava/lang/String;
        //    11: aload_0        
        //    12: aload           5
        //    14: putfield        com/viaversion/viaversion/libs/javassist/compiler/JvstCodeGen.paramListName:Ljava/lang/String;
        //    17: aload_0        
        //    18: iload           7
        //    20: putfield        com/viaversion/viaversion/libs/javassist/compiler/JvstCodeGen.paramVarBase:I
        //    23: aload_0        
        //    24: iload           6
        //    26: putfield        com/viaversion/viaversion/libs/javassist/compiler/JvstCodeGen.useParam0:Z
        //    29: aload           8
        //    31: ifnull          43
        //    34: aload_0        
        //    35: aload           8
        //    37: invokestatic    com/viaversion/viaversion/libs/javassist/compiler/MemberResolver.jvmToJavaName:(Ljava/lang/String;)Ljava/lang/String;
        //    40: putfield        com/viaversion/viaversion/libs/javassist/compiler/JvstCodeGen.param0Type:Ljava/lang/String;
        //    43: aload_0        
        //    44: iload_2        
        //    45: putfield        com/viaversion/viaversion/libs/javassist/compiler/JvstCodeGen.inStaticMethod:Z
        //    48: iload           7
        //    50: istore          10
        //    52: iload           6
        //    54: ifeq            120
        //    57: new             Ljava/lang/StringBuilder;
        //    60: dup            
        //    61: invokespecial   java/lang/StringBuilder.<init>:()V
        //    64: aload_3        
        //    65: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    68: ldc_w           "0"
        //    71: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    74: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    77: astore          11
        //    79: new             Lcom/viaversion/viaversion/libs/javassist/compiler/ast/Declarator;
        //    82: dup            
        //    83: sipush          307
        //    86: aload           8
        //    88: invokestatic    com/viaversion/viaversion/libs/javassist/compiler/MemberResolver.javaToJvmName:(Ljava/lang/String;)Ljava/lang/String;
        //    91: iconst_0       
        //    92: iload           10
        //    94: iinc            10, 1
        //    97: new             Lcom/viaversion/viaversion/libs/javassist/compiler/ast/Symbol;
        //   100: dup            
        //   101: aload           11
        //   103: invokespecial   com/viaversion/viaversion/libs/javassist/compiler/ast/Symbol.<init>:(Ljava/lang/String;)V
        //   106: invokespecial   com/viaversion/viaversion/libs/javassist/compiler/ast/Declarator.<init>:(ILjava/lang/String;IILcom/viaversion/viaversion/libs/javassist/compiler/ast/Symbol;)V
        //   109: astore          12
        //   111: aload           9
        //   113: aload           11
        //   115: aload           12
        //   117: invokevirtual   com/viaversion/viaversion/libs/javassist/compiler/SymbolTable.append:(Ljava/lang/String;Lcom/viaversion/viaversion/libs/javassist/compiler/ast/Declarator;)V
        //   120: iconst_0       
        //   121: aload_1        
        //   122: arraylength    
        //   123: if_icmpge       166
        //   126: iload           10
        //   128: aload_0        
        //   129: aload_1        
        //   130: iconst_0       
        //   131: aaload         
        //   132: new             Ljava/lang/StringBuilder;
        //   135: dup            
        //   136: invokespecial   java/lang/StringBuilder.<init>:()V
        //   139: aload_3        
        //   140: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   143: iconst_1       
        //   144: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   147: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   150: iload           10
        //   152: aload           9
        //   154: invokespecial   com/viaversion/viaversion/libs/javassist/compiler/JvstCodeGen.recordVar:(Lcom/viaversion/viaversion/libs/javassist/CtClass;Ljava/lang/String;ILcom/viaversion/viaversion/libs/javassist/compiler/SymbolTable;)I
        //   157: iadd           
        //   158: istore          10
        //   160: iinc            11, 1
        //   163: goto            120
        //   166: aload_0        
        //   167: invokevirtual   com/viaversion/viaversion/libs/javassist/compiler/JvstCodeGen.getMaxLocals:()I
        //   170: iload           10
        //   172: if_icmpge       181
        //   175: aload_0        
        //   176: iload           10
        //   178: invokevirtual   com/viaversion/viaversion/libs/javassist/compiler/JvstCodeGen.setMaxLocals:(I)V
        //   181: iload           10
        //   183: ireturn        
        //    Exceptions:
        //  throws com.viaversion.viaversion.libs.javassist.compiler.CompileError
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public int recordVariable(final CtClass ctClass, final String s, final SymbolTable symbolTable) throws CompileError {
        if (s == null) {
            return -1;
        }
        final int maxLocals = this.getMaxLocals();
        this.setMaxLocals(maxLocals + this.recordVar(ctClass, s, maxLocals, symbolTable));
        return maxLocals;
    }
    
    private int recordVar(final CtClass type, final String s, final int n, final SymbolTable symbolTable) throws CompileError {
        if (type == CtClass.voidType) {
            this.exprType = 307;
            this.arrayDim = 0;
            this.className = "java/lang/Object";
        }
        else {
            this.setType(type);
        }
        symbolTable.append(s, new Declarator(this.exprType, this.className, this.arrayDim, n, new Symbol(s)));
        return CodeGen.is2word(this.exprType, this.arrayDim) ? 2 : 1;
    }
    
    public void recordVariable(final String s, final String s2, final int n, final SymbolTable symbolTable) throws CompileError {
        char char1;
        while ((char1 = s.charAt(0)) == '[') {
            int n2 = 0;
            ++n2;
        }
        final int descToType = MemberResolver.descToType(char1);
        String s3 = null;
        if (descToType == 307) {
            if (!false) {
                s3 = s.substring(1, s.length() - 1);
            }
            else {
                s3 = s.substring(1, s.length() - 1);
            }
        }
        symbolTable.append(s2, new Declarator(descToType, s3, 0, n, new Symbol(s2)));
    }
    
    public static int compileParameterList(final Bytecode bytecode, final CtClass[] array, int n) {
        if (array == null) {
            bytecode.addIconst(0);
            bytecode.addAnewarray("java.lang.Object");
            return 1;
        }
        final CtClass[] array2 = { null };
        final int length = array.length;
        bytecode.addIconst(length);
        bytecode.addAnewarray("java.lang.Object");
        while (0 < length) {
            bytecode.addOpcode(89);
            bytecode.addIconst(0);
            if (array[0].isPrimitive()) {
                final CtPrimitiveType ctPrimitiveType = (CtPrimitiveType)array[0];
                final String wrapperName = ctPrimitiveType.getWrapperName();
                bytecode.addNew(wrapperName);
                bytecode.addOpcode(89);
                n += bytecode.addLoad(n, ctPrimitiveType);
                array2[0] = ctPrimitiveType;
                bytecode.addInvokespecial(wrapperName, "<init>", Descriptor.ofMethod(CtClass.voidType, array2));
            }
            else {
                bytecode.addAload(n);
                ++n;
            }
            bytecode.addOpcode(83);
            int n2 = 0;
            ++n2;
        }
        return 8;
    }
    
    protected void compileUnwrapValue(final CtClass ctClass, final Bytecode bytecode) throws CompileError {
        if (ctClass == CtClass.voidType) {
            this.addNullIfVoid();
            return;
        }
        if (this.exprType == 344) {
            throw new CompileError("invalid type for " + this.returnCastName);
        }
        if (ctClass instanceof CtPrimitiveType) {
            final CtPrimitiveType ctPrimitiveType = (CtPrimitiveType)ctClass;
            final String wrapperName = ctPrimitiveType.getWrapperName();
            bytecode.addCheckcast(wrapperName);
            bytecode.addInvokevirtual(wrapperName, ctPrimitiveType.getGetMethodName(), ctPrimitiveType.getGetMethodDescriptor());
            this.setType(ctClass);
        }
        else {
            bytecode.addCheckcast(ctClass);
            this.setType(ctClass);
        }
    }
    
    public void setType(final CtClass ctClass) throws CompileError {
        this.setType(ctClass, 0);
    }
    
    private void setType(final CtClass ctClass, final int n) throws CompileError {
        if (ctClass.isPrimitive()) {
            this.exprType = MemberResolver.descToType(((CtPrimitiveType)ctClass).getDescriptor());
            this.arrayDim = n;
            this.className = null;
        }
        else if (ctClass.isArray()) {
            this.setType(ctClass.getComponentType(), n + 1);
        }
        else {
            this.exprType = 307;
            this.arrayDim = n;
            this.className = MemberResolver.javaToJvmName(ctClass.getName());
        }
    }
    
    public void doNumCast(final CtClass ctClass) throws CompileError {
        if (this.arrayDim == 0 && !CodeGen.isRefType(this.exprType)) {
            if (!(ctClass instanceof CtPrimitiveType)) {
                throw new CompileError("type mismatch");
            }
            this.atNumCastExpr(this.exprType, MemberResolver.descToType(((CtPrimitiveType)ctClass).getDescriptor()));
        }
    }
}
