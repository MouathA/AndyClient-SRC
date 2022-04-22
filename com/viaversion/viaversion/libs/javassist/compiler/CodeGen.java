package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.util.*;
import com.viaversion.viaversion.libs.javassist.compiler.ast.*;

public abstract class CodeGen extends Visitor implements Opcode, TokenId
{
    static final String javaLangObject = "java.lang.Object";
    static final String jvmJavaLangObject = "java/lang/Object";
    static final String javaLangString = "java.lang.String";
    static final String jvmJavaLangString = "java/lang/String";
    protected Bytecode bytecode;
    private int tempVar;
    TypeChecker typeChecker;
    protected boolean hasReturned;
    public boolean inStaticMethod;
    protected List breakList;
    protected List continueList;
    protected ReturnHook returnHooks;
    protected int exprType;
    protected int arrayDim;
    protected String className;
    static final int[] binOp;
    private static final int[] ifOp;
    private static final int[] ifOp2;
    private static final int P_DOUBLE = 0;
    private static final int P_FLOAT = 1;
    private static final int P_LONG = 2;
    private static final int P_INT = 3;
    private static final int P_OTHER = -1;
    private static final int[] castOp;
    
    public CodeGen(final Bytecode bytecode) {
        this.bytecode = bytecode;
        this.tempVar = -1;
        this.typeChecker = null;
        this.hasReturned = false;
        this.inStaticMethod = false;
        this.breakList = null;
        this.continueList = null;
        this.returnHooks = null;
    }
    
    public void setTypeChecker(final TypeChecker typeChecker) {
        this.typeChecker = typeChecker;
    }
    
    protected static void fatal() throws CompileError {
        throw new CompileError("fatal");
    }
    
    public static boolean is2word(final int n, final int n2) {
        return n2 == 0 && (n == 312 || n == 326);
    }
    
    public int getMaxLocals() {
        return this.bytecode.getMaxLocals();
    }
    
    public void setMaxLocals(final int maxLocals) {
        this.bytecode.setMaxLocals(maxLocals);
    }
    
    protected void incMaxLocals(final int n) {
        this.bytecode.incMaxLocals(n);
    }
    
    protected int getTempVar() {
        if (this.tempVar < 0) {
            this.tempVar = this.getMaxLocals();
            this.incMaxLocals(2);
        }
        return this.tempVar;
    }
    
    protected int getLocalVar(final Declarator declarator) {
        int localVar = declarator.getLocalVar();
        if (localVar < 0) {
            localVar = this.getMaxLocals();
            declarator.setLocalVar(localVar);
            this.incMaxLocals(1);
        }
        return localVar;
    }
    
    protected abstract String getThisName();
    
    protected abstract String getSuperName() throws CompileError;
    
    protected abstract String resolveClassName(final ASTList p0) throws CompileError;
    
    protected abstract String resolveClassName(final String p0) throws CompileError;
    
    protected static String toJvmArrayName(final String s, final int n) {
        if (s == null) {
            return null;
        }
        if (n == 0) {
            return s;
        }
        final StringBuffer sb = new StringBuffer();
        int n2 = n;
        while (n2-- > 0) {
            sb.append('[');
        }
        sb.append('L');
        sb.append(s);
        sb.append(';');
        return sb.toString();
    }
    
    protected static String toJvmTypeName(final int n, int n2) {
        char c = 'I';
        switch (n) {
            case 301: {
                c = 'Z';
                break;
            }
            case 303: {
                c = 'B';
                break;
            }
            case 306: {
                c = 'C';
                break;
            }
            case 334: {
                c = 'S';
                break;
            }
            case 324: {
                c = 'I';
                break;
            }
            case 326: {
                c = 'J';
                break;
            }
            case 317: {
                c = 'F';
                break;
            }
            case 312: {
                c = 'D';
                break;
            }
            case 344: {
                c = 'V';
                break;
            }
        }
        final StringBuffer sb = new StringBuffer();
        while (n2-- > 0) {
            sb.append('[');
        }
        sb.append(c);
        return sb.toString();
    }
    
    public void compileExpr(final ASTree asTree) throws CompileError {
        this.doTypeCheck(asTree);
        asTree.accept(this);
    }
    
    public boolean compileBooleanExpr(final boolean b, final ASTree asTree) throws CompileError {
        this.doTypeCheck(asTree);
        return this.booleanExpr(b, asTree);
    }
    
    public void doTypeCheck(final ASTree asTree) throws CompileError {
        if (this.typeChecker != null) {
            asTree.accept(this.typeChecker);
        }
    }
    
    @Override
    public void atASTList(final ASTList list) throws CompileError {
        fatal();
    }
    
    @Override
    public void atPair(final Pair pair) throws CompileError {
        fatal();
    }
    
    @Override
    public void atSymbol(final Symbol symbol) throws CompileError {
        fatal();
    }
    
    @Override
    public void atFieldDecl(final FieldDecl fieldDecl) throws CompileError {
        fieldDecl.getInit().accept(this);
    }
    
    @Override
    public void atMethodDecl(final MethodDecl methodDecl) throws CompileError {
        ASTList list = methodDecl.getModifiers();
        this.setMaxLocals(1);
        while (list != null) {
            final Keyword keyword = (Keyword)list.head();
            list = list.tail();
            if (keyword.get() == 335) {
                this.setMaxLocals(0);
                this.inStaticMethod = true;
            }
        }
        for (ASTList list2 = methodDecl.getParams(); list2 != null; list2 = list2.tail()) {
            this.atDeclarator((Declarator)list2.head());
        }
        this.atMethodBody(methodDecl.getBody(), methodDecl.isConstructor(), methodDecl.getReturn().getType() == 344);
    }
    
    public void atMethodBody(final Stmnt stmnt, final boolean b, final boolean b2) throws CompileError {
        if (stmnt == null) {
            return;
        }
        if (b && this.needsSuperCall(stmnt)) {
            this.insertDefaultSuperCall();
        }
        this.hasReturned = false;
        stmnt.accept(this);
        if (!this.hasReturned) {
            if (!b2) {
                throw new CompileError("no return statement");
            }
            this.bytecode.addOpcode(177);
            this.hasReturned = true;
        }
    }
    
    private boolean needsSuperCall(Stmnt stmnt) throws CompileError {
        if (stmnt.getOperator() == 66) {
            stmnt = (Stmnt)stmnt.head();
        }
        if (stmnt != null && stmnt.getOperator() == 69) {
            final ASTree head = stmnt.head();
            if (head != null && head instanceof Expr && ((Expr)head).getOperator() == 67) {
                final ASTree head2 = ((Expr)head).head();
                if (head2 instanceof Keyword) {
                    final int value = ((Keyword)head2).get();
                    return value != 339 && value != 336;
                }
            }
        }
        return true;
    }
    
    protected abstract void insertDefaultSuperCall() throws CompileError;
    
    @Override
    public void atStmnt(final Stmnt stmnt) throws CompileError {
        if (stmnt == null) {
            return;
        }
        final int operator = stmnt.getOperator();
        if (operator == 69) {
            final ASTree left = stmnt.getLeft();
            this.doTypeCheck(left);
            if (left instanceof AssignExpr) {
                this.atAssignExpr((AssignExpr)left, false);
            }
            else if (isPlusPlusExpr(left)) {
                final AssignExpr assignExpr = (AssignExpr)left;
                this.atPlusPlus(assignExpr.getOperator(), assignExpr.oprand1(), assignExpr, false);
            }
            else {
                left.accept(this);
                if (is2word(this.exprType, this.arrayDim)) {
                    this.bytecode.addOpcode(88);
                }
                else if (this.exprType != 344) {
                    this.bytecode.addOpcode(87);
                }
            }
        }
        else if (operator == 68 || operator == 66) {
            ASTList tail = stmnt;
            while (tail != null) {
                final ASTree head = tail.head();
                tail = tail.tail();
                if (head != null) {
                    head.accept(this);
                }
            }
        }
        else if (operator == 320) {
            this.atIfStmnt(stmnt);
        }
        else if (operator == 346 || operator == 311) {
            this.atWhileStmnt(stmnt, operator == 346);
        }
        else if (operator == 318) {
            this.atForStmnt(stmnt);
        }
        else if (operator == 302 || operator == 309) {
            this.atBreakStmnt(stmnt, operator == 302);
        }
        else if (operator == 333) {
            this.atReturnStmnt(stmnt);
        }
        else if (operator == 340) {
            this.atThrowStmnt(stmnt);
        }
        else if (operator == 343) {
            this.atTryStmnt(stmnt);
        }
        else if (operator == 337) {
            this.atSwitchStmnt(stmnt);
        }
        else {
            if (operator != 338) {
                this.hasReturned = false;
                throw new CompileError("sorry, not supported statement: TokenId " + operator);
            }
            this.atSyncStmnt(stmnt);
        }
    }
    
    private void atIfStmnt(final Stmnt stmnt) throws CompileError {
        final ASTree head = stmnt.head();
        final Stmnt stmnt2 = (Stmnt)stmnt.tail().head();
        final Stmnt stmnt3 = (Stmnt)stmnt.tail().tail().head();
        if (this.compileBooleanExpr(false, head)) {
            this.hasReturned = false;
            if (stmnt3 != null) {
                stmnt3.accept(this);
            }
            return;
        }
        final int currentPc = this.bytecode.currentPc();
        int currentPc2 = 0;
        this.bytecode.addIndex(0);
        this.hasReturned = false;
        if (stmnt2 != null) {
            stmnt2.accept(this);
        }
        final boolean hasReturned = this.hasReturned;
        this.hasReturned = false;
        if (stmnt3 != null && !hasReturned) {
            this.bytecode.addOpcode(167);
            currentPc2 = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
        }
        this.bytecode.write16bit(currentPc, this.bytecode.currentPc() - currentPc + 1);
        if (stmnt3 != null) {
            stmnt3.accept(this);
            if (!hasReturned) {
                this.bytecode.write16bit(currentPc2, this.bytecode.currentPc() - currentPc2 + 1);
            }
            this.hasReturned = (hasReturned && this.hasReturned);
        }
    }
    
    private void atWhileStmnt(final Stmnt stmnt, final boolean b) throws CompileError {
        final List breakList = this.breakList;
        final List continueList = this.continueList;
        this.breakList = new ArrayList();
        this.continueList = new ArrayList();
        final ASTree head = stmnt.head();
        final Stmnt stmnt2 = (Stmnt)stmnt.tail();
        int currentPc = 0;
        if (b) {
            this.bytecode.addOpcode(167);
            currentPc = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
        }
        final int currentPc2 = this.bytecode.currentPc();
        if (stmnt2 != null) {
            stmnt2.accept(this);
        }
        final int currentPc3 = this.bytecode.currentPc();
        if (b) {
            this.bytecode.write16bit(currentPc, currentPc3 - currentPc + 1);
        }
        boolean compileBooleanExpr = this.compileBooleanExpr(true, head);
        if (compileBooleanExpr) {
            this.bytecode.addOpcode(167);
            compileBooleanExpr = (this.breakList.size() == 0);
        }
        this.bytecode.addIndex(currentPc2 - this.bytecode.currentPc() + 1);
        this.patchGoto(this.breakList, this.bytecode.currentPc());
        this.patchGoto(this.continueList, currentPc3);
        this.continueList = continueList;
        this.breakList = breakList;
        this.hasReturned = compileBooleanExpr;
    }
    
    protected void patchGoto(final List list, final int n) {
        for (final int intValue : list) {
            this.bytecode.write16bit(intValue, n - intValue + 1);
        }
    }
    
    private void atForStmnt(final Stmnt stmnt) throws CompileError {
        final List breakList = this.breakList;
        final List continueList = this.continueList;
        this.breakList = new ArrayList();
        this.continueList = new ArrayList();
        final Stmnt stmnt2 = (Stmnt)stmnt.head();
        final ASTList tail = stmnt.tail();
        final ASTree head = tail.head();
        final ASTList tail2 = tail.tail();
        final Stmnt stmnt3 = (Stmnt)tail2.head();
        final Stmnt stmnt4 = (Stmnt)tail2.tail();
        if (stmnt2 != null) {
            stmnt2.accept(this);
        }
        final int currentPc = this.bytecode.currentPc();
        int currentPc2 = 0;
        if (head != null) {
            if (this.compileBooleanExpr(false, head)) {
                this.continueList = continueList;
                this.breakList = breakList;
                this.hasReturned = false;
                return;
            }
            currentPc2 = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
        }
        if (stmnt4 != null) {
            stmnt4.accept(this);
        }
        final int currentPc3 = this.bytecode.currentPc();
        if (stmnt3 != null) {
            stmnt3.accept(this);
        }
        this.bytecode.addOpcode(167);
        this.bytecode.addIndex(currentPc - this.bytecode.currentPc() + 1);
        final int currentPc4 = this.bytecode.currentPc();
        if (head != null) {
            this.bytecode.write16bit(currentPc2, currentPc4 - currentPc2 + 1);
        }
        this.patchGoto(this.breakList, currentPc4);
        this.patchGoto(this.continueList, currentPc3);
        this.continueList = continueList;
        this.breakList = breakList;
        this.hasReturned = false;
    }
    
    private void atSwitchStmnt(final Stmnt stmnt) throws CompileError {
        boolean b = false;
        if (this.typeChecker != null) {
            this.doTypeCheck(stmnt.head());
            b = (this.typeChecker.exprType == 307 && this.typeChecker.arrayDim == 0 && "java/lang/String".equals(this.typeChecker.className));
        }
        this.compileExpr(stmnt.head());
        int maxLocals = -1;
        if (b) {
            maxLocals = this.getMaxLocals();
            this.incMaxLocals(1);
            this.bytecode.addAstore(maxLocals);
            this.bytecode.addAload(maxLocals);
            this.bytecode.addInvokevirtual("java/lang/String", "hashCode", "()I");
        }
        final List breakList = this.breakList;
        this.breakList = new ArrayList();
        final int currentPc = this.bytecode.currentPc();
        this.bytecode.addOpcode(171);
        int n = 3 - (currentPc & 0x3);
        while (n-- > 0) {
            this.bytecode.add(0);
        }
        final Stmnt stmnt2 = (Stmnt)stmnt.tail();
        int n2 = 0;
        for (ASTList tail = stmnt2; tail != null; tail = tail.tail()) {
            if (((Stmnt)tail.head()).getOperator() == 304) {
                ++n2;
            }
        }
        final int currentPc2 = this.bytecode.currentPc();
        this.bytecode.addGap(4);
        this.bytecode.add32bit(n2);
        this.bytecode.addGap(n2 * 8);
        final long[] array = new long[n2];
        final ArrayList<Integer> list = new ArrayList<Integer>();
        int n3 = 0;
        int currentPc3 = -1;
        for (ASTList tail2 = stmnt2; tail2 != null; tail2 = tail2.tail()) {
            final Stmnt stmnt3 = (Stmnt)tail2.head();
            final int operator = stmnt3.getOperator();
            if (operator == 310) {
                currentPc3 = this.bytecode.currentPc();
            }
            else if (operator != 304) {
                fatal();
            }
            else {
                final int currentPc4 = this.bytecode.currentPc();
                long n4;
                if (b) {
                    n4 = this.computeStringLabel(stmnt3.head(), maxLocals, list);
                }
                else {
                    n4 = this.computeLabel(stmnt3.head());
                }
                array[n3++] = (n4 << 32) + ((long)(currentPc4 - currentPc) & -1L);
            }
            this.hasReturned = false;
            ((Stmnt)stmnt3.tail()).accept(this);
        }
        Arrays.sort(array);
        int n5 = currentPc2 + 8;
        for (int i = 0; i < n2; ++i) {
            this.bytecode.write32bit(n5, (int)(array[i] >>> 32));
            this.bytecode.write32bit(n5 + 4, (int)array[i]);
            n5 += 8;
        }
        if (currentPc3 < 0 || this.breakList.size() > 0) {
            this.hasReturned = false;
        }
        final int currentPc5 = this.bytecode.currentPc();
        if (currentPc3 < 0) {
            currentPc3 = currentPc5;
        }
        this.bytecode.write32bit(currentPc2, currentPc3 - currentPc);
        for (final int intValue : list) {
            this.bytecode.write16bit(intValue, currentPc3 - intValue + 1);
        }
        this.patchGoto(this.breakList, currentPc5);
        this.breakList = breakList;
    }
    
    private int computeLabel(ASTree stripPlusExpr) throws CompileError {
        this.doTypeCheck(stripPlusExpr);
        stripPlusExpr = TypeChecker.stripPlusExpr(stripPlusExpr);
        if (stripPlusExpr instanceof IntConst) {
            return (int)((IntConst)stripPlusExpr).get();
        }
        throw new CompileError("bad case label");
    }
    
    private int computeStringLabel(ASTree stripPlusExpr, final int n, final List list) throws CompileError {
        this.doTypeCheck(stripPlusExpr);
        stripPlusExpr = TypeChecker.stripPlusExpr(stripPlusExpr);
        if (stripPlusExpr instanceof StringL) {
            final String value = ((StringL)stripPlusExpr).get();
            this.bytecode.addAload(n);
            this.bytecode.addLdc(value);
            this.bytecode.addInvokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z");
            this.bytecode.addOpcode(153);
            final Integer value2 = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
            list.add(value2);
            return value.hashCode();
        }
        throw new CompileError("bad case label");
    }
    
    private void atBreakStmnt(final Stmnt stmnt, final boolean b) throws CompileError {
        if (stmnt.head() != null) {
            throw new CompileError("sorry, not support labeled break or continue");
        }
        this.bytecode.addOpcode(167);
        final Integer value = this.bytecode.currentPc();
        this.bytecode.addIndex(0);
        if (b) {
            this.breakList.add(value);
        }
        else {
            this.continueList.add(value);
        }
    }
    
    protected void atReturnStmnt(final Stmnt stmnt) throws CompileError {
        this.atReturnStmnt2(stmnt.getLeft());
    }
    
    protected final void atReturnStmnt2(final ASTree asTree) throws CompileError {
        int n;
        if (asTree == null) {
            n = 177;
        }
        else {
            this.compileExpr(asTree);
            if (this.arrayDim > 0) {
                n = 176;
            }
            else {
                final int exprType = this.exprType;
                if (exprType == 312) {
                    n = 175;
                }
                else if (exprType == 317) {
                    n = 174;
                }
                else if (exprType == 326) {
                    n = 173;
                }
                else if (isRefType(exprType)) {
                    n = 176;
                }
                else {
                    n = 172;
                }
            }
        }
        for (ReturnHook returnHook = this.returnHooks; returnHook != null; returnHook = returnHook.next) {
            if (returnHook.doit(this.bytecode, n)) {
                this.hasReturned = true;
                return;
            }
        }
        this.bytecode.addOpcode(n);
        this.hasReturned = true;
    }
    
    private void atThrowStmnt(final Stmnt stmnt) throws CompileError {
        this.compileExpr(stmnt.getLeft());
        if (this.exprType != 307 || this.arrayDim > 0) {
            throw new CompileError("bad throw statement");
        }
        this.bytecode.addOpcode(191);
        this.hasReturned = true;
    }
    
    protected void atTryStmnt(final Stmnt stmnt) throws CompileError {
        this.hasReturned = false;
    }
    
    private void atSyncStmnt(final Stmnt stmnt) throws CompileError {
        final int listSize = getListSize(this.breakList);
        final int listSize2 = getListSize(this.continueList);
        this.compileExpr(stmnt.head());
        if (this.exprType != 307 && this.arrayDim == 0) {
            throw new CompileError("bad type expr for synchronized block");
        }
        final Bytecode bytecode = this.bytecode;
        final int maxLocals = bytecode.getMaxLocals();
        bytecode.incMaxLocals(1);
        bytecode.addOpcode(89);
        bytecode.addAstore(maxLocals);
        bytecode.addOpcode(194);
        final ReturnHook returnHook = new ReturnHook(this, maxLocals) {
            final int val$var;
            final CodeGen this$0;
            
            @Override
            protected boolean doit(final Bytecode bytecode, final int n) {
                bytecode.addAload(this.val$var);
                bytecode.addOpcode(195);
                return false;
            }
        };
        final int currentPc = bytecode.currentPc();
        final Stmnt stmnt2 = (Stmnt)stmnt.tail();
        if (stmnt2 != null) {
            stmnt2.accept(this);
        }
        final int currentPc2 = bytecode.currentPc();
        int currentPc3 = 0;
        if (!this.hasReturned) {
            returnHook.doit(bytecode, 0);
            bytecode.addOpcode(167);
            currentPc3 = bytecode.currentPc();
            bytecode.addIndex(0);
        }
        if (currentPc < currentPc2) {
            final int currentPc4 = bytecode.currentPc();
            returnHook.doit(bytecode, 0);
            bytecode.addOpcode(191);
            bytecode.addExceptionHandler(currentPc, currentPc2, currentPc4, 0);
        }
        if (!this.hasReturned) {
            bytecode.write16bit(currentPc3, bytecode.currentPc() - currentPc3 + 1);
        }
        returnHook.remove(this);
        if (getListSize(this.breakList) != listSize || getListSize(this.continueList) != listSize2) {
            throw new CompileError("sorry, cannot break/continue in synchronized block");
        }
    }
    
    private static int getListSize(final List list) {
        return (list == null) ? 0 : list.size();
    }
    
    private static boolean isPlusPlusExpr(final ASTree asTree) {
        if (asTree instanceof Expr) {
            final int operator = ((Expr)asTree).getOperator();
            return operator == 362 || operator == 363;
        }
        return false;
    }
    
    @Override
    public void atDeclarator(final Declarator declarator) throws CompileError {
        declarator.setLocalVar(this.getMaxLocals());
        declarator.setClassName(this.resolveClassName(declarator.getClassName()));
        int n;
        if (is2word(declarator.getType(), declarator.getArrayDim())) {
            n = 2;
        }
        else {
            n = 1;
        }
        this.incMaxLocals(n);
        final ASTree initializer = declarator.getInitializer();
        if (initializer != null) {
            this.doTypeCheck(initializer);
            this.atVariableAssign(null, 61, null, declarator, initializer, false);
        }
    }
    
    @Override
    public abstract void atNewExpr(final NewExpr p0) throws CompileError;
    
    @Override
    public abstract void atArrayInit(final ArrayInit p0) throws CompileError;
    
    @Override
    public void atAssignExpr(final AssignExpr assignExpr) throws CompileError {
        this.atAssignExpr(assignExpr, true);
    }
    
    protected void atAssignExpr(final AssignExpr assignExpr, final boolean b) throws CompileError {
        final int operator = assignExpr.getOperator();
        final ASTree oprand1 = assignExpr.oprand1();
        final ASTree oprand2 = assignExpr.oprand2();
        if (oprand1 instanceof Variable) {
            this.atVariableAssign(assignExpr, operator, (Variable)oprand1, ((Variable)oprand1).getDeclarator(), oprand2, b);
        }
        else {
            if (oprand1 instanceof Expr && ((Expr)oprand1).getOperator() == 65) {
                this.atArrayAssign(assignExpr, operator, (Expr)oprand1, oprand2, b);
                return;
            }
            this.atFieldAssign(assignExpr, operator, oprand1, oprand2, b);
        }
    }
    
    protected static void badAssign(final Expr expr) throws CompileError {
        String string;
        if (expr == null) {
            string = "incompatible type for assignment";
        }
        else {
            string = "incompatible type for " + expr.getName();
        }
        throw new CompileError(string);
    }
    
    private void atVariableAssign(final Expr expr, final int n, final Variable variable, final Declarator declarator, final ASTree asTree, final boolean b) throws CompileError {
        final int type = declarator.getType();
        final int arrayDim = declarator.getArrayDim();
        final String className = declarator.getClassName();
        final int localVar = this.getLocalVar(declarator);
        if (n != 61) {
            this.atVariable(variable);
        }
        if (expr == null && asTree instanceof ArrayInit) {
            this.atArrayVariableAssign((ArrayInit)asTree, type, arrayDim, className);
        }
        else {
            this.atAssignCore(expr, n, asTree, type, arrayDim, className);
        }
        if (b) {
            if (is2word(type, arrayDim)) {
                this.bytecode.addOpcode(92);
            }
            else {
                this.bytecode.addOpcode(89);
            }
        }
        if (arrayDim > 0) {
            this.bytecode.addAstore(localVar);
        }
        else if (type == 312) {
            this.bytecode.addDstore(localVar);
        }
        else if (type == 317) {
            this.bytecode.addFstore(localVar);
        }
        else if (type == 326) {
            this.bytecode.addLstore(localVar);
        }
        else if (isRefType(type)) {
            this.bytecode.addAstore(localVar);
        }
        else {
            this.bytecode.addIstore(localVar);
        }
        this.exprType = type;
        this.arrayDim = arrayDim;
        this.className = className;
    }
    
    protected abstract void atArrayVariableAssign(final ArrayInit p0, final int p1, final int p2, final String p3) throws CompileError;
    
    private void atArrayAssign(final Expr expr, final int n, final Expr expr2, final ASTree asTree, final boolean b) throws CompileError {
        this.arrayAccess(expr2.oprand1(), expr2.oprand2());
        if (n != 61) {
            this.bytecode.addOpcode(92);
            this.bytecode.addOpcode(getArrayReadOp(this.exprType, this.arrayDim));
        }
        final int exprType = this.exprType;
        final int arrayDim = this.arrayDim;
        final String className = this.className;
        this.atAssignCore(expr, n, asTree, exprType, arrayDim, className);
        if (b) {
            if (is2word(exprType, arrayDim)) {
                this.bytecode.addOpcode(94);
            }
            else {
                this.bytecode.addOpcode(91);
            }
        }
        this.bytecode.addOpcode(getArrayWriteOp(exprType, arrayDim));
        this.exprType = exprType;
        this.arrayDim = arrayDim;
        this.className = className;
    }
    
    protected abstract void atFieldAssign(final Expr p0, final int p1, final ASTree p2, final ASTree p3, final boolean p4) throws CompileError;
    
    protected void atAssignCore(final Expr expr, final int n, final ASTree asTree, final int n2, final int n3, final String s) throws CompileError {
        if (n == 354 && n3 == 0 && n2 == 307) {
            this.atStringPlusEq(expr, n2, n3, s, asTree);
        }
        else {
            asTree.accept(this);
            if (this.invalidDim(this.exprType, this.arrayDim, this.className, n2, n3, s, false) || (n != 61 && n3 > 0)) {
                badAssign(expr);
            }
            if (n != 61) {
                final int n4 = CodeGen.assignOps[n - 351];
                final int lookupBinOp = lookupBinOp(n4);
                if (lookupBinOp < 0) {
                    fatal();
                }
                this.atArithBinExpr(expr, n4, lookupBinOp, n2);
            }
        }
        if (n != 61 || (n3 == 0 && !isRefType(n2))) {
            this.atNumCastExpr(this.exprType, n2);
        }
    }
    
    private void atStringPlusEq(final Expr expr, final int n, final int n2, final String s, final ASTree asTree) throws CompileError {
        if (!"java/lang/String".equals(s)) {
            badAssign(expr);
        }
        this.convToString(n, n2);
        asTree.accept(this);
        this.convToString(this.exprType, this.arrayDim);
        this.bytecode.addInvokevirtual("java.lang.String", "concat", "(Ljava/lang/String;)Ljava/lang/String;");
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/String";
    }
    
    private boolean invalidDim(final int n, final int n2, final String s, final int n3, final int n4, final String s2, final boolean b) {
        return n2 != n4 && n != 412 && (n4 != 0 || n3 != 307 || !"java/lang/Object".equals(s2)) && (!b || n2 != 0 || n != 307 || !"java/lang/Object".equals(s));
    }
    
    @Override
    public void atCondExpr(final CondExpr condExpr) throws CompileError {
        if (this.booleanExpr(false, condExpr.condExpr())) {
            condExpr.elseExpr().accept(this);
        }
        else {
            final int currentPc = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
            condExpr.thenExpr().accept(this);
            final int arrayDim = this.arrayDim;
            this.bytecode.addOpcode(167);
            final int currentPc2 = this.bytecode.currentPc();
            this.bytecode.addIndex(0);
            this.bytecode.write16bit(currentPc, this.bytecode.currentPc() - currentPc + 1);
            condExpr.elseExpr().accept(this);
            if (arrayDim != this.arrayDim) {
                throw new CompileError("type mismatch in ?:");
            }
            this.bytecode.write16bit(currentPc2, this.bytecode.currentPc() - currentPc2 + 1);
        }
    }
    
    static int lookupBinOp(final int n) {
        final int[] binOp = CodeGen.binOp;
        for (int length = binOp.length, i = 0; i < length; i += 5) {
            if (binOp[i] == n) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public void atBinExpr(final BinExpr binExpr) throws CompileError {
        final int operator = binExpr.getOperator();
        final int lookupBinOp = lookupBinOp(operator);
        if (lookupBinOp >= 0) {
            binExpr.oprand1().accept(this);
            final ASTree oprand2 = binExpr.oprand2();
            if (oprand2 == null) {
                return;
            }
            final int exprType = this.exprType;
            final int arrayDim = this.arrayDim;
            final String className = this.className;
            oprand2.accept(this);
            if (arrayDim != this.arrayDim) {
                throw new CompileError("incompatible array types");
            }
            if (operator == 43 && arrayDim == 0 && (exprType == 307 || this.exprType == 307)) {
                this.atStringConcatExpr(binExpr, exprType, arrayDim, className);
            }
            else {
                this.atArithBinExpr(binExpr, operator, lookupBinOp, exprType);
            }
        }
        else {
            if (!this.booleanExpr(true, binExpr)) {
                this.bytecode.addIndex(7);
                this.bytecode.addIconst(0);
                this.bytecode.addOpcode(167);
                this.bytecode.addIndex(4);
            }
            this.bytecode.addIconst(1);
        }
    }
    
    private void atArithBinExpr(final Expr expr, final int n, final int n2, final int exprType) throws CompileError {
        if (this.arrayDim != 0) {
            badTypes(expr);
        }
        final int exprType2 = this.exprType;
        if (n == 364 || n == 366 || n == 370) {
            if (exprType2 == 324 || exprType2 == 334 || exprType2 == 306 || exprType2 == 303) {
                this.exprType = exprType;
            }
            else {
                badTypes(expr);
            }
        }
        else {
            this.convertOprandTypes(exprType, exprType2, expr);
        }
        final int typePrecedence = typePrecedence(this.exprType);
        if (typePrecedence >= 0) {
            final int n3 = CodeGen.binOp[n2 + typePrecedence + 1];
            if (n3 != 0) {
                if (typePrecedence == 3 && this.exprType != 301) {
                    this.exprType = 324;
                }
                this.bytecode.addOpcode(n3);
                return;
            }
        }
        badTypes(expr);
    }
    
    private void atStringConcatExpr(final Expr expr, final int n, final int n2, final String s) throws CompileError {
        final int exprType = this.exprType;
        final int arrayDim = this.arrayDim;
        final boolean is2word = is2word(exprType, arrayDim);
        final boolean b = exprType == 307 && "java/lang/String".equals(this.className);
        if (is2word) {
            this.convToString(exprType, arrayDim);
        }
        if (is2word(n, n2)) {
            this.bytecode.addOpcode(91);
            this.bytecode.addOpcode(87);
        }
        else {
            this.bytecode.addOpcode(95);
        }
        this.convToString(n, n2);
        this.bytecode.addOpcode(95);
        if (!is2word && !b) {
            this.convToString(exprType, arrayDim);
        }
        this.bytecode.addInvokevirtual("java.lang.String", "concat", "(Ljava/lang/String;)Ljava/lang/String;");
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/String";
    }
    
    private void convToString(final int n, final int n2) throws CompileError {
        if (isRefType(n) || n2 > 0) {
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(Ljava/lang/Object;)Ljava/lang/String;");
        }
        else if (n == 312) {
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(D)Ljava/lang/String;");
        }
        else if (n == 317) {
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(F)Ljava/lang/String;");
        }
        else if (n == 326) {
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(J)Ljava/lang/String;");
        }
        else if (n == 301) {
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(Z)Ljava/lang/String;");
        }
        else if (n == 306) {
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(C)Ljava/lang/String;");
        }
        else {
            if (n == 344) {
                throw new CompileError("void type expression");
            }
            this.bytecode.addInvokestatic("java.lang.String", "valueOf", "(I)Ljava/lang/String;");
        }
    }
    
    private boolean booleanExpr(final boolean b, final ASTree asTree) throws CompileError {
        final int compOperator = getCompOperator(asTree);
        if (compOperator == 358) {
            final BinExpr binExpr = (BinExpr)asTree;
            this.compareExpr(b, binExpr.getOperator(), this.compileOprands(binExpr), binExpr);
        }
        else {
            if (compOperator == 33) {
                return this.booleanExpr(!b, ((Expr)asTree).oprand1());
            }
            final boolean b2;
            if ((b2 = (compOperator == 369)) || compOperator == 368) {
                final BinExpr binExpr2 = (BinExpr)asTree;
                if (this.booleanExpr(!b2, binExpr2.oprand1())) {
                    this.exprType = 301;
                    this.arrayDim = 0;
                    return true;
                }
                final int currentPc = this.bytecode.currentPc();
                this.bytecode.addIndex(0);
                if (this.booleanExpr(b2, binExpr2.oprand2())) {
                    this.bytecode.addOpcode(167);
                }
                this.bytecode.write16bit(currentPc, this.bytecode.currentPc() - currentPc + 3);
                if (b != b2) {
                    this.bytecode.addIndex(6);
                    this.bytecode.addOpcode(167);
                }
            }
            else {
                if (isAlwaysBranch(asTree, b)) {
                    this.exprType = 301;
                    this.arrayDim = 0;
                    return true;
                }
                asTree.accept(this);
                if (this.exprType != 301 || this.arrayDim != 0) {
                    throw new CompileError("boolean expr is required");
                }
                this.bytecode.addOpcode(b ? 154 : 153);
            }
        }
        this.exprType = 301;
        this.arrayDim = 0;
        return false;
    }
    
    private static boolean isAlwaysBranch(final ASTree asTree, final boolean b) {
        if (asTree instanceof Keyword) {
            final int value = ((Keyword)asTree).get();
            return b ? (value == 410) : (value == 411);
        }
        return false;
    }
    
    static int getCompOperator(final ASTree asTree) throws CompileError {
        if (!(asTree instanceof Expr)) {
            return 32;
        }
        final Expr expr = (Expr)asTree;
        final int operator = expr.getOperator();
        if (operator == 33) {
            return 33;
        }
        if (expr instanceof BinExpr && operator != 368 && operator != 369 && operator != 38 && operator != 124) {
            return 358;
        }
        return operator;
    }
    
    private int compileOprands(final BinExpr binExpr) throws CompileError {
        binExpr.oprand1().accept(this);
        final int exprType = this.exprType;
        final int arrayDim = this.arrayDim;
        binExpr.oprand2().accept(this);
        if (arrayDim != this.arrayDim) {
            if (exprType != 412 && this.exprType != 412) {
                throw new CompileError("incompatible array types");
            }
            if (this.exprType == 412) {
                this.arrayDim = arrayDim;
            }
        }
        if (exprType == 412) {
            return this.exprType;
        }
        return exprType;
    }
    
    private void compareExpr(final boolean b, final int n, final int n2, final BinExpr binExpr) throws CompileError {
        if (this.arrayDim == 0) {
            this.convertOprandTypes(n2, this.exprType, binExpr);
        }
        final int typePrecedence = typePrecedence(this.exprType);
        if (typePrecedence == -1 || this.arrayDim > 0) {
            if (n == 358) {
                this.bytecode.addOpcode(b ? 165 : 166);
            }
            else if (n == 350) {
                this.bytecode.addOpcode(b ? 166 : 165);
            }
            else {
                badTypes(binExpr);
            }
        }
        else if (typePrecedence == 3) {
            final int[] ifOp = CodeGen.ifOp;
            for (int i = 0; i < ifOp.length; i += 3) {
                if (ifOp[i] == n) {
                    this.bytecode.addOpcode(ifOp[i + (b ? 1 : 2)]);
                    return;
                }
            }
            badTypes(binExpr);
        }
        else {
            if (typePrecedence == 0) {
                if (n == 60 || n == 357) {
                    this.bytecode.addOpcode(152);
                }
                else {
                    this.bytecode.addOpcode(151);
                }
            }
            else if (typePrecedence == 1) {
                if (n == 60 || n == 357) {
                    this.bytecode.addOpcode(150);
                }
                else {
                    this.bytecode.addOpcode(149);
                }
            }
            else if (typePrecedence == 2) {
                this.bytecode.addOpcode(148);
            }
            else {
                fatal();
            }
            final int[] ifOp2 = CodeGen.ifOp2;
            for (int j = 0; j < ifOp2.length; j += 3) {
                if (ifOp2[j] == n) {
                    this.bytecode.addOpcode(ifOp2[j + (b ? 1 : 2)]);
                    return;
                }
            }
            badTypes(binExpr);
        }
    }
    
    protected static void badTypes(final Expr expr) throws CompileError {
        throw new CompileError("invalid types for " + expr.getName());
    }
    
    protected static boolean isRefType(final int n) {
        return n == 307 || n == 412;
    }
    
    private static int typePrecedence(final int n) {
        if (n == 312) {
            return 0;
        }
        if (n == 317) {
            return 1;
        }
        if (n == 326) {
            return 2;
        }
        if (isRefType(n)) {
            return -1;
        }
        if (n == 344) {
            return -1;
        }
        return 3;
    }
    
    static boolean isP_INT(final int n) {
        return typePrecedence(n) == 3;
    }
    
    static boolean rightIsStrong(final int n, final int n2) {
        final int typePrecedence = typePrecedence(n);
        final int typePrecedence2 = typePrecedence(n2);
        return typePrecedence >= 0 && typePrecedence2 >= 0 && typePrecedence > typePrecedence2;
    }
    
    private void convertOprandTypes(final int exprType, final int n, final Expr expr) throws CompileError {
        final int typePrecedence = typePrecedence(exprType);
        final int typePrecedence2 = typePrecedence(n);
        if (typePrecedence2 < 0 && typePrecedence < 0) {
            return;
        }
        if (typePrecedence2 < 0 || typePrecedence < 0) {
            badTypes(expr);
        }
        boolean b;
        int n2;
        int n3;
        if (typePrecedence <= typePrecedence2) {
            b = false;
            this.exprType = exprType;
            n2 = CodeGen.castOp[typePrecedence2 * 4 + typePrecedence];
            n3 = typePrecedence;
        }
        else {
            b = true;
            n2 = CodeGen.castOp[typePrecedence * 4 + typePrecedence2];
            n3 = typePrecedence2;
        }
        if (b) {
            if (n3 == 0 || n3 == 2) {
                if (typePrecedence == 0 || typePrecedence == 2) {
                    this.bytecode.addOpcode(94);
                }
                else {
                    this.bytecode.addOpcode(93);
                }
                this.bytecode.addOpcode(88);
                this.bytecode.addOpcode(n2);
                this.bytecode.addOpcode(94);
                this.bytecode.addOpcode(88);
            }
            else if (n3 == 1) {
                if (typePrecedence == 2) {
                    this.bytecode.addOpcode(91);
                    this.bytecode.addOpcode(87);
                }
                else {
                    this.bytecode.addOpcode(95);
                }
                this.bytecode.addOpcode(n2);
                this.bytecode.addOpcode(95);
            }
            else {
                fatal();
            }
        }
        else if (n2 != 0) {
            this.bytecode.addOpcode(n2);
        }
    }
    
    @Override
    public void atCastExpr(final CastExpr castExpr) throws CompileError {
        final String resolveClassName = this.resolveClassName(castExpr.getClassName());
        final String checkCastExpr = this.checkCastExpr(castExpr, resolveClassName);
        final int exprType = this.exprType;
        this.exprType = castExpr.getType();
        this.arrayDim = castExpr.getArrayDim();
        this.className = resolveClassName;
        if (checkCastExpr == null) {
            this.atNumCastExpr(exprType, this.exprType);
        }
        else {
            this.bytecode.addCheckcast(checkCastExpr);
        }
    }
    
    @Override
    public void atInstanceOfExpr(final InstanceOfExpr instanceOfExpr) throws CompileError {
        this.bytecode.addInstanceof(this.checkCastExpr(instanceOfExpr, this.resolveClassName(instanceOfExpr.getClassName())));
        this.exprType = 301;
        this.arrayDim = 0;
    }
    
    private String checkCastExpr(final CastExpr castExpr, final String s) throws CompileError {
        final ASTree oprand = castExpr.getOprand();
        final int arrayDim = castExpr.getArrayDim();
        final int type = castExpr.getType();
        oprand.accept(this);
        final int exprType = this.exprType;
        final int arrayDim2 = this.arrayDim;
        if (this.invalidDim(exprType, this.arrayDim, this.className, type, arrayDim, s, true) || exprType == 344 || type == 344) {
            throw new CompileError("invalid cast");
        }
        if (type == 307) {
            if (!isRefType(exprType) && arrayDim2 == 0) {
                throw new CompileError("invalid cast");
            }
            return toJvmArrayName(s, arrayDim);
        }
        else {
            if (arrayDim > 0) {
                return toJvmTypeName(type, arrayDim);
            }
            return null;
        }
    }
    
    void atNumCastExpr(final int n, final int n2) throws CompileError {
        if (n == n2) {
            return;
        }
        final int typePrecedence = typePrecedence(n);
        final int typePrecedence2 = typePrecedence(n2);
        int n3;
        if (0 <= typePrecedence && typePrecedence < 3) {
            n3 = CodeGen.castOp[typePrecedence * 4 + typePrecedence2];
        }
        else {
            n3 = 0;
        }
        int n4;
        if (n2 == 312) {
            n4 = 135;
        }
        else if (n2 == 317) {
            n4 = 134;
        }
        else if (n2 == 326) {
            n4 = 133;
        }
        else if (n2 == 334) {
            n4 = 147;
        }
        else if (n2 == 306) {
            n4 = 146;
        }
        else if (n2 == 303) {
            n4 = 145;
        }
        else {
            n4 = 0;
        }
        if (n3 != 0) {
            this.bytecode.addOpcode(n3);
        }
        if ((n3 == 0 || n3 == 136 || n3 == 139 || n3 == 142) && n4 != 0) {
            this.bytecode.addOpcode(n4);
        }
    }
    
    @Override
    public void atExpr(final Expr expr) throws CompileError {
        final int operator = expr.getOperator();
        final ASTree oprand1 = expr.oprand1();
        if (operator == 46) {
            if (((Symbol)expr.oprand2()).get().equals("class")) {
                this.atClassObject(expr);
            }
            else {
                this.atFieldRead(expr);
            }
        }
        else if (operator == 35) {
            this.atFieldRead(expr);
        }
        else if (operator == 65) {
            this.atArrayRead(oprand1, expr.oprand2());
        }
        else if (operator == 362 || operator == 363) {
            this.atPlusPlus(operator, oprand1, expr, true);
        }
        else if (operator == 33) {
            if (!this.booleanExpr(false, expr)) {
                this.bytecode.addIndex(7);
                this.bytecode.addIconst(1);
                this.bytecode.addOpcode(167);
                this.bytecode.addIndex(4);
            }
            this.bytecode.addIconst(0);
        }
        else if (operator == 67) {
            fatal();
        }
        else {
            expr.oprand1().accept(this);
            final int typePrecedence = typePrecedence(this.exprType);
            if (this.arrayDim > 0) {
                badType(expr);
            }
            if (operator == 45) {
                if (typePrecedence == 0) {
                    this.bytecode.addOpcode(119);
                }
                else if (typePrecedence == 1) {
                    this.bytecode.addOpcode(118);
                }
                else if (typePrecedence == 2) {
                    this.bytecode.addOpcode(117);
                }
                else if (typePrecedence == 3) {
                    this.bytecode.addOpcode(116);
                    this.exprType = 324;
                }
                else {
                    badType(expr);
                }
            }
            else if (operator == 126) {
                if (typePrecedence == 3) {
                    this.bytecode.addIconst(-1);
                    this.bytecode.addOpcode(130);
                    this.exprType = 324;
                }
                else if (typePrecedence == 2) {
                    this.bytecode.addLconst(-1L);
                    this.bytecode.addOpcode(131);
                }
                else {
                    badType(expr);
                }
            }
            else if (operator == 43) {
                if (typePrecedence == -1) {
                    badType(expr);
                }
            }
            else {
                fatal();
            }
        }
    }
    
    protected static void badType(final Expr expr) throws CompileError {
        throw new CompileError("invalid type for " + expr.getName());
    }
    
    @Override
    public abstract void atCallExpr(final CallExpr p0) throws CompileError;
    
    protected abstract void atFieldRead(final ASTree p0) throws CompileError;
    
    public void atClassObject(final Expr expr) throws CompileError {
        final ASTree oprand1 = expr.oprand1();
        if (!(oprand1 instanceof Symbol)) {
            throw new CompileError("fatal error: badly parsed .class expr");
        }
        String s = ((Symbol)oprand1).get();
        if (s.startsWith("[")) {
            int index = s.indexOf("[L");
            if (index >= 0) {
                final String substring = s.substring(index + 2, s.length() - 1);
                final String resolveClassName = this.resolveClassName(substring);
                if (!substring.equals(resolveClassName)) {
                    final String jvmToJavaName = MemberResolver.jvmToJavaName(resolveClassName);
                    final StringBuffer sb = new StringBuffer();
                    while (index-- >= 0) {
                        sb.append('[');
                    }
                    sb.append('L').append(jvmToJavaName).append(';');
                    s = sb.toString();
                }
            }
        }
        else {
            s = MemberResolver.jvmToJavaName(this.resolveClassName(MemberResolver.javaToJvmName(s)));
        }
        this.atClassObject2(s);
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/Class";
    }
    
    protected void atClassObject2(final String s) throws CompileError {
        final int currentPc = this.bytecode.currentPc();
        this.bytecode.addLdc(s);
        this.bytecode.addInvokestatic("java.lang.Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
        final int currentPc2 = this.bytecode.currentPc();
        this.bytecode.addOpcode(167);
        final int currentPc3 = this.bytecode.currentPc();
        this.bytecode.addIndex(0);
        this.bytecode.addExceptionHandler(currentPc, currentPc2, this.bytecode.currentPc(), "java.lang.ClassNotFoundException");
        this.bytecode.growStack(1);
        this.bytecode.addInvokestatic("com.viaversion.viaversion.libs.javassist.runtime.DotClass", "fail", "(Ljava/lang/ClassNotFoundException;)Ljava/lang/NoClassDefFoundError;");
        this.bytecode.addOpcode(191);
        this.bytecode.write16bit(currentPc3, this.bytecode.currentPc() - currentPc3 + 1);
    }
    
    public void atArrayRead(final ASTree asTree, final ASTree asTree2) throws CompileError {
        this.arrayAccess(asTree, asTree2);
        this.bytecode.addOpcode(getArrayReadOp(this.exprType, this.arrayDim));
    }
    
    protected void arrayAccess(final ASTree asTree, final ASTree asTree2) throws CompileError {
        asTree.accept(this);
        final int exprType = this.exprType;
        final int arrayDim = this.arrayDim;
        if (arrayDim == 0) {
            throw new CompileError("bad array access");
        }
        final String className = this.className;
        asTree2.accept(this);
        if (typePrecedence(this.exprType) != 3 || this.arrayDim > 0) {
            throw new CompileError("bad array index");
        }
        this.exprType = exprType;
        this.arrayDim = arrayDim - 1;
        this.className = className;
    }
    
    protected static int getArrayReadOp(final int n, final int n2) {
        if (n2 > 0) {
            return 50;
        }
        switch (n) {
            case 312: {
                return 49;
            }
            case 317: {
                return 48;
            }
            case 326: {
                return 47;
            }
            case 324: {
                return 46;
            }
            case 334: {
                return 53;
            }
            case 306: {
                return 52;
            }
            case 301:
            case 303: {
                return 51;
            }
            default: {
                return 50;
            }
        }
    }
    
    protected static int getArrayWriteOp(final int n, final int n2) {
        if (n2 > 0) {
            return 83;
        }
        switch (n) {
            case 312: {
                return 82;
            }
            case 317: {
                return 81;
            }
            case 326: {
                return 80;
            }
            case 324: {
                return 79;
            }
            case 334: {
                return 86;
            }
            case 306: {
                return 85;
            }
            case 301:
            case 303: {
                return 84;
            }
            default: {
                return 83;
            }
        }
    }
    
    private void atPlusPlus(final int n, ASTree oprand2, final Expr expr, final boolean b) throws CompileError {
        final boolean b2 = oprand2 == null;
        if (b2) {
            oprand2 = expr.oprand2();
        }
        if (oprand2 instanceof Variable) {
            final Declarator declarator = ((Variable)oprand2).getDeclarator();
            final int type = declarator.getType();
            this.exprType = type;
            final int n2 = type;
            this.arrayDim = declarator.getArrayDim();
            final int localVar = this.getLocalVar(declarator);
            if (this.arrayDim > 0) {
                badType(expr);
            }
            if (n2 == 312) {
                this.bytecode.addDload(localVar);
                if (b && b2) {
                    this.bytecode.addOpcode(92);
                }
                this.bytecode.addDconst(1.0);
                this.bytecode.addOpcode((n == 362) ? 99 : 103);
                if (b && !b2) {
                    this.bytecode.addOpcode(92);
                }
                this.bytecode.addDstore(localVar);
            }
            else if (n2 == 326) {
                this.bytecode.addLload(localVar);
                if (b && b2) {
                    this.bytecode.addOpcode(92);
                }
                this.bytecode.addLconst(1L);
                this.bytecode.addOpcode((n == 362) ? 97 : 101);
                if (b && !b2) {
                    this.bytecode.addOpcode(92);
                }
                this.bytecode.addLstore(localVar);
            }
            else if (n2 == 317) {
                this.bytecode.addFload(localVar);
                if (b && b2) {
                    this.bytecode.addOpcode(89);
                }
                this.bytecode.addFconst(1.0f);
                this.bytecode.addOpcode((n == 362) ? 98 : 102);
                if (b && !b2) {
                    this.bytecode.addOpcode(89);
                }
                this.bytecode.addFstore(localVar);
            }
            else if (n2 == 303 || n2 == 306 || n2 == 334 || n2 == 324) {
                if (b && b2) {
                    this.bytecode.addIload(localVar);
                }
                final int n3 = (n == 362) ? 1 : -1;
                if (localVar > 255) {
                    this.bytecode.addOpcode(196);
                    this.bytecode.addOpcode(132);
                    this.bytecode.addIndex(localVar);
                    this.bytecode.addIndex(n3);
                }
                else {
                    this.bytecode.addOpcode(132);
                    this.bytecode.add(localVar);
                    this.bytecode.add(n3);
                }
                if (b && !b2) {
                    this.bytecode.addIload(localVar);
                }
            }
            else {
                badType(expr);
            }
        }
        else {
            if (oprand2 instanceof Expr) {
                final Expr expr2 = (Expr)oprand2;
                if (expr2.getOperator() == 65) {
                    this.atArrayPlusPlus(n, b2, expr2, b);
                    return;
                }
            }
            this.atFieldPlusPlus(n, b2, oprand2, expr, b);
        }
    }
    
    public void atArrayPlusPlus(final int n, final boolean b, final Expr expr, final boolean b2) throws CompileError {
        this.arrayAccess(expr.oprand1(), expr.oprand2());
        final int exprType = this.exprType;
        final int arrayDim = this.arrayDim;
        if (arrayDim > 0) {
            badType(expr);
        }
        this.bytecode.addOpcode(92);
        this.bytecode.addOpcode(getArrayReadOp(exprType, this.arrayDim));
        this.atPlusPlusCore(is2word(exprType, arrayDim) ? 94 : 91, b2, n, b, expr);
        this.bytecode.addOpcode(getArrayWriteOp(exprType, arrayDim));
    }
    
    protected void atPlusPlusCore(final int n, final boolean b, final int n2, final boolean b2, final Expr expr) throws CompileError {
        final int exprType = this.exprType;
        if (b && b2) {
            this.bytecode.addOpcode(n);
        }
        if (exprType == 324 || exprType == 303 || exprType == 306 || exprType == 334) {
            this.bytecode.addIconst(1);
            this.bytecode.addOpcode((n2 == 362) ? 96 : 100);
            this.exprType = 324;
        }
        else if (exprType == 326) {
            this.bytecode.addLconst(1L);
            this.bytecode.addOpcode((n2 == 362) ? 97 : 101);
        }
        else if (exprType == 317) {
            this.bytecode.addFconst(1.0f);
            this.bytecode.addOpcode((n2 == 362) ? 98 : 102);
        }
        else if (exprType == 312) {
            this.bytecode.addDconst(1.0);
            this.bytecode.addOpcode((n2 == 362) ? 99 : 103);
        }
        else {
            badType(expr);
        }
        if (b && !b2) {
            this.bytecode.addOpcode(n);
        }
    }
    
    protected abstract void atFieldPlusPlus(final int p0, final boolean p1, final ASTree p2, final Expr p3, final boolean p4) throws CompileError;
    
    @Override
    public abstract void atMember(final Member p0) throws CompileError;
    
    @Override
    public void atVariable(final Variable variable) throws CompileError {
        final Declarator declarator = variable.getDeclarator();
        this.exprType = declarator.getType();
        this.arrayDim = declarator.getArrayDim();
        this.className = declarator.getClassName();
        final int localVar = this.getLocalVar(declarator);
        if (this.arrayDim > 0) {
            this.bytecode.addAload(localVar);
        }
        else {
            switch (this.exprType) {
                case 307: {
                    this.bytecode.addAload(localVar);
                    break;
                }
                case 326: {
                    this.bytecode.addLload(localVar);
                    break;
                }
                case 317: {
                    this.bytecode.addFload(localVar);
                    break;
                }
                case 312: {
                    this.bytecode.addDload(localVar);
                    break;
                }
                default: {
                    this.bytecode.addIload(localVar);
                    break;
                }
            }
        }
    }
    
    @Override
    public void atKeyword(final Keyword keyword) throws CompileError {
        this.arrayDim = 0;
        final int value = keyword.get();
        switch (value) {
            case 410: {
                this.bytecode.addIconst(1);
                this.exprType = 301;
                break;
            }
            case 411: {
                this.bytecode.addIconst(0);
                this.exprType = 301;
                break;
            }
            case 412: {
                this.bytecode.addOpcode(1);
                this.exprType = 412;
                break;
            }
            case 336:
            case 339: {
                if (this.inStaticMethod) {
                    throw new CompileError("not-available: " + ((value == 339) ? "this" : "super"));
                }
                this.bytecode.addAload(0);
                this.exprType = 307;
                if (value == 339) {
                    this.className = this.getThisName();
                    break;
                }
                this.className = this.getSuperName();
                break;
            }
            default: {
                fatal();
                break;
            }
        }
    }
    
    @Override
    public void atStringL(final StringL stringL) throws CompileError {
        this.exprType = 307;
        this.arrayDim = 0;
        this.className = "java/lang/String";
        this.bytecode.addLdc(stringL.get());
    }
    
    @Override
    public void atIntConst(final IntConst intConst) throws CompileError {
        this.arrayDim = 0;
        final long value = intConst.get();
        final int type = intConst.getType();
        if (type == 402 || type == 401) {
            this.exprType = ((type == 402) ? 324 : 306);
            this.bytecode.addIconst((int)value);
        }
        else {
            this.exprType = 326;
            this.bytecode.addLconst(value);
        }
    }
    
    @Override
    public void atDoubleConst(final DoubleConst doubleConst) throws CompileError {
        this.arrayDim = 0;
        if (doubleConst.getType() == 405) {
            this.exprType = 312;
            this.bytecode.addDconst(doubleConst.get());
        }
        else {
            this.exprType = 317;
            this.bytecode.addFconst((float)doubleConst.get());
        }
    }
    
    static {
        binOp = new int[] { 43, 99, 98, 97, 96, 45, 103, 102, 101, 100, 42, 107, 106, 105, 104, 47, 111, 110, 109, 108, 37, 115, 114, 113, 112, 124, 0, 0, 129, 128, 94, 0, 0, 131, 130, 38, 0, 0, 127, 126, 364, 0, 0, 121, 120, 366, 0, 0, 123, 122, 370, 0, 0, 125, 124 };
        ifOp = new int[] { 358, 159, 160, 350, 160, 159, 357, 164, 163, 359, 162, 161, 60, 161, 162, 62, 163, 164 };
        ifOp2 = new int[] { 358, 153, 154, 350, 154, 153, 357, 158, 157, 359, 156, 155, 60, 155, 156, 62, 157, 158 };
        castOp = new int[] { 0, 144, 143, 142, 141, 0, 140, 139, 138, 137, 0, 136, 135, 134, 133, 0 };
    }
    
    protected abstract static class ReturnHook
    {
        ReturnHook next;
        
        protected abstract boolean doit(final Bytecode p0, final int p1);
        
        protected ReturnHook(final CodeGen codeGen) {
            this.next = codeGen.returnHooks;
            codeGen.returnHooks = this;
        }
        
        protected void remove(final CodeGen codeGen) {
            codeGen.returnHooks = this.next;
        }
    }
}
