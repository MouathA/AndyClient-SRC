package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.compiler.ast.*;

public class Javac
{
    JvstCodeGen gen;
    SymbolTable stable;
    private Bytecode bytecode;
    public static final String param0Name = "$0";
    public static final String resultVarName = "$_";
    public static final String proceedName = "$proceed";
    
    public Javac(final CtClass ctClass) {
        this(new Bytecode(ctClass.getClassFile2().getConstPool(), 0, 0), ctClass);
    }
    
    public Javac(final Bytecode bytecode, final CtClass ctClass) {
        this.gen = new JvstCodeGen(bytecode, ctClass, ctClass.getClassPool());
        this.stable = new SymbolTable();
        this.bytecode = bytecode;
    }
    
    public Bytecode getBytecode() {
        return this.bytecode;
    }
    
    public CtMember compile(final String s) throws CompileError {
        final Parser parser = new Parser(new Lex(s));
        final ASTList member1 = parser.parseMember1(this.stable);
        if (member1 instanceof FieldDecl) {
            return this.compileField((FieldDecl)member1);
        }
        final CtBehavior compileMethod = this.compileMethod(parser, (MethodDecl)member1);
        final CtClass declaringClass = compileMethod.getDeclaringClass();
        compileMethod.getMethodInfo2().rebuildStackMapIf6(declaringClass.getClassPool(), declaringClass.getClassFile2());
        return compileMethod;
    }
    
    private CtField compileField(final FieldDecl fieldDecl) throws CompileError, CannotCompileException {
        final Declarator declarator = fieldDecl.getDeclarator();
        final CtFieldWithInit ctFieldWithInit = new CtFieldWithInit(this.gen.resolver.lookupClass(declarator), declarator.getVariable().get(), this.gen.getThisClass());
        ctFieldWithInit.setModifiers(MemberResolver.getModifiers(fieldDecl.getModifiers()));
        if (fieldDecl.getInit() != null) {
            ctFieldWithInit.setInit(fieldDecl.getInit());
        }
        return ctFieldWithInit;
    }
    
    private CtBehavior compileMethod(final Parser parser, MethodDecl method2) throws CompileError {
        final int modifiers = MemberResolver.getModifiers(method2.getModifiers());
        final CtClass[] paramList = this.gen.makeParamList(method2);
        final CtClass[] throwsList = this.gen.makeThrowsList(method2);
        this.recordParams(paramList, Modifier.isStatic(modifiers));
        method2 = parser.parseMethod2(this.stable, method2);
        if (method2.isConstructor()) {
            final CtConstructor ctConstructor = new CtConstructor(paramList, this.gen.getThisClass());
            ctConstructor.setModifiers(modifiers);
            method2.accept(this.gen);
            ctConstructor.getMethodInfo().setCodeAttribute(this.bytecode.toCodeAttribute());
            ctConstructor.setExceptionTypes(throwsList);
            return ctConstructor;
        }
        final Declarator return1 = method2.getReturn();
        final CtClass lookupClass = this.gen.resolver.lookupClass(return1);
        this.recordReturnType(lookupClass, false);
        final CtMethod thisMethod = new CtMethod(lookupClass, return1.getVariable().get(), paramList, this.gen.getThisClass());
        thisMethod.setModifiers(modifiers);
        this.gen.setThisMethod(thisMethod);
        method2.accept(this.gen);
        if (method2.getBody() != null) {
            thisMethod.getMethodInfo().setCodeAttribute(this.bytecode.toCodeAttribute());
        }
        else {
            thisMethod.setModifiers(modifiers | 0x400);
        }
        thisMethod.setExceptionTypes(throwsList);
        return thisMethod;
    }
    
    public Bytecode compileBody(final CtBehavior ctBehavior, final String s) throws CompileError {
        this.recordParams(ctBehavior.getParameterTypes(), Modifier.isStatic(ctBehavior.getModifiers()));
        CtClass ctClass;
        if (ctBehavior instanceof CtMethod) {
            this.gen.setThisMethod((CtMethod)ctBehavior);
            ctClass = ((CtMethod)ctBehavior).getReturnType();
        }
        else {
            ctClass = CtClass.voidType;
        }
        this.recordReturnType(ctClass, false);
        final boolean b = ctClass == CtClass.voidType;
        if (s == null) {
            makeDefaultBody(this.bytecode, ctClass);
        }
        else {
            final Parser parser = new Parser(new Lex(s));
            final Stmnt statement = parser.parseStatement(new SymbolTable(this.stable));
            if (parser.hasMore()) {
                throw new CompileError("the method/constructor body must be surrounded by {}");
            }
            if (ctBehavior instanceof CtConstructor) {
                final boolean b2 = !((CtConstructor)ctBehavior).isClassInitializer();
            }
            this.gen.atMethodBody(statement, false, b);
        }
        return this.bytecode;
    }
    
    private static void makeDefaultBody(final Bytecode bytecode, final CtClass ctClass) {
        if (ctClass instanceof CtPrimitiveType) {
            ((CtPrimitiveType)ctClass).getReturnOp();
            if (176 != 175) {
                if (176 != 174) {
                    if (176 != 173) {
                        if (176 == 177) {}
                    }
                }
            }
        }
        if (true) {
            bytecode.addOpcode(1);
        }
        bytecode.addOpcode(176);
    }
    
    public boolean recordLocalVariables(final CodeAttribute codeAttribute, final int n) throws CompileError {
        final LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTable");
        if (localVariableAttribute == null) {
            return false;
        }
        while (0 < localVariableAttribute.tableLength()) {
            final int startPc = localVariableAttribute.startPc(0);
            final int codeLength = localVariableAttribute.codeLength(0);
            if (startPc <= n && n < startPc + codeLength) {
                this.gen.recordVariable(localVariableAttribute.descriptor(0), localVariableAttribute.variableName(0), localVariableAttribute.index(0), this.stable);
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    public boolean recordParamNames(final CodeAttribute codeAttribute, final int n) throws CompileError {
        final LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTable");
        if (localVariableAttribute == null) {
            return false;
        }
        while (0 < localVariableAttribute.tableLength()) {
            final int index = localVariableAttribute.index(0);
            if (index < n) {
                this.gen.recordVariable(localVariableAttribute.descriptor(0), localVariableAttribute.variableName(0), index, this.stable);
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    public int recordParams(final CtClass[] array, final boolean b) throws CompileError {
        return this.gen.recordParams(array, b, "$", "$args", "$$", this.stable);
    }
    
    public int recordParams(final String s, final CtClass[] array, final boolean b, final int n, final boolean b2) throws CompileError {
        return this.gen.recordParams(array, b2, "$", "$args", "$$", b, n, s, this.stable);
    }
    
    public void setMaxLocals(final int maxLocals) {
        this.gen.setMaxLocals(maxLocals);
    }
    
    public int recordReturnType(final CtClass ctClass, final boolean b) throws CompileError {
        this.gen.recordType(ctClass);
        return this.gen.recordReturnType(ctClass, "$r", b ? "$_" : null, this.stable);
    }
    
    public void recordType(final CtClass ctClass) {
        this.gen.recordType(ctClass);
    }
    
    public int recordVariable(final CtClass ctClass, final String s) throws CompileError {
        return this.gen.recordVariable(ctClass, s, this.stable);
    }
    
    public void recordProceed(final String s, final String s2) throws CompileError {
        this.gen.setProceedHandler(new ProceedHandler(s2, new Parser(new Lex(s)).parseExpression(this.stable)) {
            final String val$m;
            final ASTree val$texpr;
            final Javac this$0;
            
            @Override
            public void doit(final JvstCodeGen jvstCodeGen, final Bytecode bytecode, final ASTList list) throws CompileError {
                ASTree make = new Member(this.val$m);
                if (this.val$texpr != null) {
                    make = Expr.make(46, this.val$texpr, make);
                }
                jvstCodeGen.compileExpr(CallExpr.makeCall(make, list));
                jvstCodeGen.addNullIfVoid();
            }
            
            @Override
            public void setReturnType(final JvstTypeChecker jvstTypeChecker, final ASTList list) throws CompileError {
                ASTree make = new Member(this.val$m);
                if (this.val$texpr != null) {
                    make = Expr.make(46, this.val$texpr, make);
                }
                CallExpr.makeCall(make, list).accept(jvstTypeChecker);
                jvstTypeChecker.addNullIfVoid();
            }
        }, "$proceed");
    }
    
    public void recordStaticProceed(final String s, final String s2) throws CompileError {
        this.gen.setProceedHandler(new ProceedHandler(s, s2) {
            final String val$c;
            final String val$m;
            final Javac this$0;
            
            @Override
            public void doit(final JvstCodeGen jvstCodeGen, final Bytecode bytecode, final ASTList list) throws CompileError {
                jvstCodeGen.compileExpr(CallExpr.makeCall(Expr.make(35, new Symbol(this.val$c), new Member(this.val$m)), list));
                jvstCodeGen.addNullIfVoid();
            }
            
            @Override
            public void setReturnType(final JvstTypeChecker jvstTypeChecker, final ASTList list) throws CompileError {
                CallExpr.makeCall(Expr.make(35, new Symbol(this.val$c), new Member(this.val$m)), list).accept(jvstTypeChecker);
                jvstTypeChecker.addNullIfVoid();
            }
        }, "$proceed");
    }
    
    public void recordSpecialProceed(final String s, final String s2, final String s3, final String s4, final int n) throws CompileError {
        this.gen.setProceedHandler(new ProceedHandler(new Parser(new Lex(s)).parseExpression(this.stable), n, s4, s2, s3) {
            final ASTree val$texpr;
            final int val$methodIndex;
            final String val$descriptor;
            final String val$classname;
            final String val$methodname;
            final Javac this$0;
            
            @Override
            public void doit(final JvstCodeGen jvstCodeGen, final Bytecode bytecode, final ASTList list) throws CompileError {
                jvstCodeGen.compileInvokeSpecial(this.val$texpr, this.val$methodIndex, this.val$descriptor, list);
            }
            
            @Override
            public void setReturnType(final JvstTypeChecker jvstTypeChecker, final ASTList list) throws CompileError {
                jvstTypeChecker.compileInvokeSpecial(this.val$texpr, this.val$classname, this.val$methodname, this.val$descriptor, list);
            }
        }, "$proceed");
    }
    
    public void recordProceed(final ProceedHandler proceedHandler) {
        this.gen.setProceedHandler(proceedHandler, "$proceed");
    }
    
    public void compileStmnt(final String s) throws CompileError {
        final Parser parser = new Parser(new Lex(s));
        final SymbolTable symbolTable = new SymbolTable(this.stable);
        while (parser.hasMore()) {
            final Stmnt statement = parser.parseStatement(symbolTable);
            if (statement != null) {
                statement.accept(this.gen);
            }
        }
    }
    
    public void compileExpr(final String s) throws CompileError {
        this.compileExpr(parseExpr(s, this.stable));
    }
    
    public static ASTree parseExpr(final String s, final SymbolTable symbolTable) throws CompileError {
        return new Parser(new Lex(s)).parseExpression(symbolTable);
    }
    
    public void compileExpr(final ASTree asTree) throws CompileError {
        if (asTree != null) {
            this.gen.compileExpr(asTree);
        }
    }
    
    public static class CtFieldWithInit extends CtField
    {
        private ASTree init;
        
        CtFieldWithInit(final CtClass ctClass, final String s, final CtClass ctClass2) throws CannotCompileException {
            super(ctClass, s, ctClass2);
            this.init = null;
        }
        
        protected void setInit(final ASTree init) {
            this.init = init;
        }
        
        @Override
        protected ASTree getInitAST() {
            return this.init;
        }
    }
}
