package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.compiler.ast.*;

public final class Parser implements TokenId
{
    private Lex lex;
    private static final int[] binaryOpPrecedence;
    
    public Parser(final Lex lex) {
        this.lex = lex;
    }
    
    public boolean hasMore() {
        return this.lex.lookAhead() >= 0;
    }
    
    public ASTList parseMember(final SymbolTable symbolTable) throws CompileError {
        final ASTList member1 = this.parseMember1(symbolTable);
        if (member1 instanceof MethodDecl) {
            return this.parseMethod2(symbolTable, (MethodDecl)member1);
        }
        return member1;
    }
    
    public ASTList parseMember1(final SymbolTable symbolTable) throws CompileError {
        final ASTList memberMods = this.parseMemberMods();
        boolean b = false;
        Declarator formalType;
        if (this.lex.lookAhead() == 400 && this.lex.lookAhead(1) == 40) {
            formalType = new Declarator(344, 0);
            b = true;
        }
        else {
            formalType = this.parseFormalType(symbolTable);
        }
        if (this.lex.get() != 400) {
            throw new SyntaxError(this.lex);
        }
        String string;
        if (b) {
            string = "<init>";
        }
        else {
            string = this.lex.getString();
        }
        formalType.setVariable(new Symbol(string));
        if (b || this.lex.lookAhead() == 40) {
            return this.parseMethod1(symbolTable, b, memberMods, formalType);
        }
        return this.parseField(symbolTable, memberMods, formalType);
    }
    
    private FieldDecl parseField(final SymbolTable symbolTable, final ASTList list, final Declarator declarator) throws CompileError {
        ASTree expression = null;
        if (this.lex.lookAhead() == 61) {
            this.lex.get();
            expression = this.parseExpression(symbolTable);
        }
        final int value = this.lex.get();
        if (value == 59) {
            return new FieldDecl(list, new ASTList(declarator, new ASTList(expression)));
        }
        if (value == 44) {
            throw new CompileError("only one field can be declared in one declaration", this.lex);
        }
        throw new SyntaxError(this.lex);
    }
    
    private MethodDecl parseMethod1(final SymbolTable symbolTable, final boolean b, final ASTList list, final Declarator declarator) throws CompileError {
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        ASTList append = null;
        if (this.lex.lookAhead() != 41) {
            while (true) {
                append = ASTList.append(append, this.parseFormalParam(symbolTable));
                final int lookAhead = this.lex.lookAhead();
                if (lookAhead == 44) {
                    this.lex.get();
                }
                else {
                    if (lookAhead == 41) {
                        break;
                    }
                    continue;
                }
            }
        }
        this.lex.get();
        declarator.addArrayDim(this.parseArrayDimension());
        if (b && declarator.getArrayDim() > 0) {
            throw new SyntaxError(this.lex);
        }
        ASTList append2 = null;
        if (this.lex.lookAhead() == 341) {
            this.lex.get();
            while (true) {
                append2 = ASTList.append(append2, this.parseClassType(symbolTable));
                if (this.lex.lookAhead() != 44) {
                    break;
                }
                this.lex.get();
            }
        }
        return new MethodDecl(list, new ASTList(declarator, ASTList.make(append, append2, null)));
    }
    
    public MethodDecl parseMethod2(final SymbolTable symbolTable, final MethodDecl methodDecl) throws CompileError {
        ASTree block = null;
        if (this.lex.lookAhead() == 59) {
            this.lex.get();
        }
        else {
            block = this.parseBlock(symbolTable);
            if (block == null) {
                block = new Stmnt(66);
            }
        }
        methodDecl.sublist(4).setHead(block);
        return methodDecl;
    }
    
    private ASTList parseMemberMods() {
        ASTList list = null;
        while (true) {
            final int lookAhead = this.lex.lookAhead();
            if (lookAhead != 300 && lookAhead != 315 && lookAhead != 332 && lookAhead != 331 && lookAhead != 330 && lookAhead != 338 && lookAhead != 335 && lookAhead != 345 && lookAhead != 342 && lookAhead != 347) {
                break;
            }
            list = new ASTList(new Keyword(this.lex.get()), list);
        }
        return list;
    }
    
    private Declarator parseFormalType(final SymbolTable symbolTable) throws CompileError {
        final int lookAhead = this.lex.lookAhead();
        if (isBuiltinType(lookAhead) || lookAhead == 344) {
            this.lex.get();
            return new Declarator(lookAhead, this.parseArrayDimension());
        }
        return new Declarator(this.parseClassType(symbolTable), this.parseArrayDimension());
    }
    
    private static boolean isBuiltinType(final int n) {
        return n == 301 || n == 303 || n == 306 || n == 334 || n == 324 || n == 326 || n == 317 || n == 312;
    }
    
    private Declarator parseFormalParam(final SymbolTable symbolTable) throws CompileError {
        final Declarator formalType = this.parseFormalType(symbolTable);
        if (this.lex.get() != 400) {
            throw new SyntaxError(this.lex);
        }
        final String string = this.lex.getString();
        formalType.setVariable(new Symbol(string));
        formalType.addArrayDim(this.parseArrayDimension());
        symbolTable.append(string, formalType);
        return formalType;
    }
    
    public Stmnt parseStatement(final SymbolTable symbolTable) throws CompileError {
        final int lookAhead = this.lex.lookAhead();
        if (lookAhead == 123) {
            return this.parseBlock(symbolTable);
        }
        if (lookAhead == 59) {
            this.lex.get();
            return new Stmnt(66);
        }
        if (lookAhead == 400 && this.lex.lookAhead(1) == 58) {
            this.lex.get();
            final String string = this.lex.getString();
            this.lex.get();
            return Stmnt.make(76, new Symbol(string), this.parseStatement(symbolTable));
        }
        if (lookAhead == 320) {
            return this.parseIf(symbolTable);
        }
        if (lookAhead == 346) {
            return this.parseWhile(symbolTable);
        }
        if (lookAhead == 311) {
            return this.parseDo(symbolTable);
        }
        if (lookAhead == 318) {
            return this.parseFor(symbolTable);
        }
        if (lookAhead == 343) {
            return this.parseTry(symbolTable);
        }
        if (lookAhead == 337) {
            return this.parseSwitch(symbolTable);
        }
        if (lookAhead == 338) {
            return this.parseSynchronized(symbolTable);
        }
        if (lookAhead == 333) {
            return this.parseReturn(symbolTable);
        }
        if (lookAhead == 340) {
            return this.parseThrow(symbolTable);
        }
        if (lookAhead == 302) {
            return this.parseBreak(symbolTable);
        }
        if (lookAhead == 309) {
            return this.parseContinue(symbolTable);
        }
        return this.parseDeclarationOrExpression(symbolTable, false);
    }
    
    private Stmnt parseBlock(final SymbolTable symbolTable) throws CompileError {
        if (this.lex.get() != 123) {
            throw new SyntaxError(this.lex);
        }
        ASTList list = null;
        final SymbolTable symbolTable2 = new SymbolTable(symbolTable);
        while (this.lex.lookAhead() != 125) {
            final Stmnt statement = this.parseStatement(symbolTable2);
            if (statement != null) {
                list = ASTList.concat(list, new Stmnt(66, statement));
            }
        }
        this.lex.get();
        if (list == null) {
            return new Stmnt(66);
        }
        return (Stmnt)list;
    }
    
    private Stmnt parseIf(final SymbolTable symbolTable) throws CompileError {
        final int value = this.lex.get();
        final ASTree parExpression = this.parseParExpression(symbolTable);
        final Stmnt statement = this.parseStatement(symbolTable);
        Stmnt statement2;
        if (this.lex.lookAhead() == 313) {
            this.lex.get();
            statement2 = this.parseStatement(symbolTable);
        }
        else {
            statement2 = null;
        }
        return new Stmnt(value, parExpression, new ASTList(statement, new ASTList(statement2)));
    }
    
    private Stmnt parseWhile(final SymbolTable symbolTable) throws CompileError {
        return new Stmnt(this.lex.get(), this.parseParExpression(symbolTable), this.parseStatement(symbolTable));
    }
    
    private Stmnt parseDo(final SymbolTable symbolTable) throws CompileError {
        final int value = this.lex.get();
        final Stmnt statement = this.parseStatement(symbolTable);
        if (this.lex.get() != 346 || this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        final ASTree expression = this.parseExpression(symbolTable);
        if (this.lex.get() != 41 || this.lex.get() != 59) {
            throw new SyntaxError(this.lex);
        }
        return new Stmnt(value, expression, statement);
    }
    
    private Stmnt parseFor(final SymbolTable symbolTable) throws CompileError {
        final int value = this.lex.get();
        final SymbolTable symbolTable2 = new SymbolTable(symbolTable);
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        ASTree declarationOrExpression;
        if (this.lex.lookAhead() == 59) {
            this.lex.get();
            declarationOrExpression = null;
        }
        else {
            declarationOrExpression = this.parseDeclarationOrExpression(symbolTable2, true);
        }
        ASTree expression;
        if (this.lex.lookAhead() == 59) {
            expression = null;
        }
        else {
            expression = this.parseExpression(symbolTable2);
        }
        if (this.lex.get() != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        ASTree exprList;
        if (this.lex.lookAhead() == 41) {
            exprList = null;
        }
        else {
            exprList = this.parseExprList(symbolTable2);
        }
        if (this.lex.get() != 41) {
            throw new CompileError(") is missing", this.lex);
        }
        return new Stmnt(value, declarationOrExpression, new ASTList(expression, new ASTList(exprList, this.parseStatement(symbolTable2))));
    }
    
    private Stmnt parseSwitch(final SymbolTable symbolTable) throws CompileError {
        return new Stmnt(this.lex.get(), this.parseParExpression(symbolTable), this.parseSwitchBlock(symbolTable));
    }
    
    private Stmnt parseSwitchBlock(final SymbolTable symbolTable) throws CompileError {
        if (this.lex.get() != 123) {
            throw new SyntaxError(this.lex);
        }
        final SymbolTable symbolTable2 = new SymbolTable(symbolTable);
        Stmnt stmntOrCase = this.parseStmntOrCase(symbolTable2);
        if (stmntOrCase == null) {
            throw new CompileError("empty switch block", this.lex);
        }
        final int operator = stmntOrCase.getOperator();
        if (operator != 304 && operator != 310) {
            throw new CompileError("no case or default in a switch block", this.lex);
        }
        Stmnt stmnt = new Stmnt(66, stmntOrCase);
        while (this.lex.lookAhead() != 125) {
            final Stmnt stmntOrCase2 = this.parseStmntOrCase(symbolTable2);
            if (stmntOrCase2 != null) {
                final int operator2 = stmntOrCase2.getOperator();
                if (operator2 == 304 || operator2 == 310) {
                    stmnt = (Stmnt)ASTList.concat(stmnt, new Stmnt(66, stmntOrCase2));
                    stmntOrCase = stmntOrCase2;
                }
                else {
                    stmntOrCase = (Stmnt)ASTList.concat(stmntOrCase, new Stmnt(66, stmntOrCase2));
                }
            }
        }
        this.lex.get();
        return stmnt;
    }
    
    private Stmnt parseStmntOrCase(final SymbolTable symbolTable) throws CompileError {
        final int lookAhead = this.lex.lookAhead();
        if (lookAhead != 304 && lookAhead != 310) {
            return this.parseStatement(symbolTable);
        }
        this.lex.get();
        Stmnt stmnt;
        if (lookAhead == 304) {
            stmnt = new Stmnt(lookAhead, this.parseExpression(symbolTable));
        }
        else {
            stmnt = new Stmnt(310);
        }
        if (this.lex.get() != 58) {
            throw new CompileError(": is missing", this.lex);
        }
        return stmnt;
    }
    
    private Stmnt parseSynchronized(final SymbolTable symbolTable) throws CompileError {
        final int value = this.lex.get();
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        final ASTree expression = this.parseExpression(symbolTable);
        if (this.lex.get() != 41) {
            throw new SyntaxError(this.lex);
        }
        return new Stmnt(value, expression, this.parseBlock(symbolTable));
    }
    
    private Stmnt parseTry(final SymbolTable symbolTable) throws CompileError {
        this.lex.get();
        final Stmnt block = this.parseBlock(symbolTable);
        ASTList append = null;
        while (this.lex.lookAhead() == 305) {
            this.lex.get();
            if (this.lex.get() != 40) {
                throw new SyntaxError(this.lex);
            }
            final SymbolTable symbolTable2 = new SymbolTable(symbolTable);
            final Declarator formalParam = this.parseFormalParam(symbolTable2);
            if (formalParam.getArrayDim() > 0 || formalParam.getType() != 307) {
                throw new SyntaxError(this.lex);
            }
            if (this.lex.get() != 41) {
                throw new SyntaxError(this.lex);
            }
            append = ASTList.append(append, new Pair(formalParam, this.parseBlock(symbolTable2)));
        }
        ASTree block2 = null;
        if (this.lex.lookAhead() == 316) {
            this.lex.get();
            block2 = this.parseBlock(symbolTable);
        }
        return Stmnt.make(343, block, append, block2);
    }
    
    private Stmnt parseReturn(final SymbolTable symbolTable) throws CompileError {
        final Stmnt stmnt = new Stmnt(this.lex.get());
        if (this.lex.lookAhead() != 59) {
            stmnt.setLeft(this.parseExpression(symbolTable));
        }
        if (this.lex.get() != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        return stmnt;
    }
    
    private Stmnt parseThrow(final SymbolTable symbolTable) throws CompileError {
        final int value = this.lex.get();
        final ASTree expression = this.parseExpression(symbolTable);
        if (this.lex.get() != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        return new Stmnt(value, expression);
    }
    
    private Stmnt parseBreak(final SymbolTable symbolTable) throws CompileError {
        return this.parseContinue(symbolTable);
    }
    
    private Stmnt parseContinue(final SymbolTable symbolTable) throws CompileError {
        final Stmnt stmnt = new Stmnt(this.lex.get());
        int n = this.lex.get();
        if (n == 400) {
            stmnt.setLeft(new Symbol(this.lex.getString()));
            n = this.lex.get();
        }
        if (n != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        return stmnt;
    }
    
    private Stmnt parseDeclarationOrExpression(final SymbolTable symbolTable, final boolean b) throws CompileError {
        int i;
        for (i = this.lex.lookAhead(); i == 315; i = this.lex.lookAhead()) {
            this.lex.get();
        }
        if (isBuiltinType(i)) {
            return this.parseDeclarators(symbolTable, new Declarator(this.lex.get(), this.parseArrayDimension()));
        }
        if (i == 400) {
            final int nextIsClassType = this.nextIsClassType(0);
            if (nextIsClassType >= 0 && this.lex.lookAhead(nextIsClassType) == 400) {
                return this.parseDeclarators(symbolTable, new Declarator(this.parseClassType(symbolTable), this.parseArrayDimension()));
            }
        }
        Stmnt exprList;
        if (b) {
            exprList = this.parseExprList(symbolTable);
        }
        else {
            exprList = new Stmnt(69, this.parseExpression(symbolTable));
        }
        if (this.lex.get() != 59) {
            throw new CompileError("; is missing", this.lex);
        }
        return exprList;
    }
    
    private Stmnt parseExprList(final SymbolTable symbolTable) throws CompileError {
        ASTList list = null;
        while (true) {
            list = ASTList.concat(list, new Stmnt(66, new Stmnt(69, this.parseExpression(symbolTable))));
            if (this.lex.lookAhead() != 44) {
                break;
            }
            this.lex.get();
        }
        return (Stmnt)list;
    }
    
    private Stmnt parseDeclarators(final SymbolTable symbolTable, final Declarator declarator) throws CompileError {
        ASTList list = null;
        while (true) {
            list = ASTList.concat(list, new Stmnt(68, this.parseDeclarator(symbolTable, declarator)));
            final int value = this.lex.get();
            if (value == 59) {
                return (Stmnt)list;
            }
            if (value != 44) {
                throw new CompileError("; is missing", this.lex);
            }
        }
    }
    
    private Declarator parseDeclarator(final SymbolTable symbolTable, final Declarator declarator) throws CompileError {
        if (this.lex.get() != 400 || declarator.getType() == 344) {
            throw new SyntaxError(this.lex);
        }
        final String string = this.lex.getString();
        final Symbol symbol = new Symbol(string);
        final int arrayDimension = this.parseArrayDimension();
        ASTree initializer = null;
        if (this.lex.lookAhead() == 61) {
            this.lex.get();
            initializer = this.parseInitializer(symbolTable);
        }
        final Declarator make = declarator.make(symbol, arrayDimension, initializer);
        symbolTable.append(string, make);
        return make;
    }
    
    private ASTree parseInitializer(final SymbolTable symbolTable) throws CompileError {
        if (this.lex.lookAhead() == 123) {
            return this.parseArrayInitializer(symbolTable);
        }
        return this.parseExpression(symbolTable);
    }
    
    private ArrayInit parseArrayInitializer(final SymbolTable symbolTable) throws CompileError {
        this.lex.get();
        if (this.lex.lookAhead() == 125) {
            this.lex.get();
            return new ArrayInit(null);
        }
        final ArrayInit arrayInit = new ArrayInit(this.parseExpression(symbolTable));
        while (this.lex.lookAhead() == 44) {
            this.lex.get();
            ASTList.append(arrayInit, this.parseExpression(symbolTable));
        }
        if (this.lex.get() != 125) {
            throw new SyntaxError(this.lex);
        }
        return arrayInit;
    }
    
    private ASTree parseParExpression(final SymbolTable symbolTable) throws CompileError {
        if (this.lex.get() != 40) {
            throw new SyntaxError(this.lex);
        }
        final ASTree expression = this.parseExpression(symbolTable);
        if (this.lex.get() != 41) {
            throw new SyntaxError(this.lex);
        }
        return expression;
    }
    
    public ASTree parseExpression(final SymbolTable symbolTable) throws CompileError {
        final ASTree conditionalExpr = this.parseConditionalExpr(symbolTable);
        if (!isAssignOp(this.lex.lookAhead())) {
            return conditionalExpr;
        }
        return AssignExpr.makeAssign(this.lex.get(), conditionalExpr, this.parseExpression(symbolTable));
    }
    
    private static boolean isAssignOp(final int n) {
        return n == 61 || n == 351 || n == 352 || n == 353 || n == 354 || n == 355 || n == 356 || n == 360 || n == 361 || n == 365 || n == 367 || n == 371;
    }
    
    private ASTree parseConditionalExpr(final SymbolTable symbolTable) throws CompileError {
        final ASTree binaryExpr = this.parseBinaryExpr(symbolTable);
        if (this.lex.lookAhead() != 63) {
            return binaryExpr;
        }
        this.lex.get();
        final ASTree expression = this.parseExpression(symbolTable);
        if (this.lex.get() != 58) {
            throw new CompileError(": is missing", this.lex);
        }
        return new CondExpr(binaryExpr, expression, this.parseExpression(symbolTable));
    }
    
    private ASTree parseBinaryExpr(final SymbolTable symbolTable) throws CompileError {
        ASTree asTree = this.parseUnaryExpr(symbolTable);
        while (true) {
            final int opPrecedence = this.getOpPrecedence(this.lex.lookAhead());
            if (opPrecedence == 0) {
                break;
            }
            asTree = this.binaryExpr2(symbolTable, asTree, opPrecedence);
        }
        return asTree;
    }
    
    private ASTree parseInstanceOf(final SymbolTable symbolTable, final ASTree asTree) throws CompileError {
        final int lookAhead = this.lex.lookAhead();
        if (isBuiltinType(lookAhead)) {
            this.lex.get();
            return new InstanceOfExpr(lookAhead, this.parseArrayDimension(), asTree);
        }
        return new InstanceOfExpr(this.parseClassType(symbolTable), this.parseArrayDimension(), asTree);
    }
    
    private ASTree binaryExpr2(final SymbolTable symbolTable, final ASTree asTree, final int n) throws CompileError {
        final int value = this.lex.get();
        if (value == 323) {
            return this.parseInstanceOf(symbolTable, asTree);
        }
        ASTree asTree2 = this.parseUnaryExpr(symbolTable);
        while (true) {
            final int opPrecedence = this.getOpPrecedence(this.lex.lookAhead());
            if (opPrecedence == 0 || n <= opPrecedence) {
                break;
            }
            asTree2 = this.binaryExpr2(symbolTable, asTree2, opPrecedence);
        }
        return BinExpr.makeBin(value, asTree, asTree2);
    }
    
    private int getOpPrecedence(final int n) {
        if (33 <= n && n <= 63) {
            return Parser.binaryOpPrecedence[n - 33];
        }
        if (n == 94) {
            return 7;
        }
        if (n == 124) {
            return 8;
        }
        if (n == 369) {
            return 9;
        }
        if (n == 368) {
            return 10;
        }
        if (n == 358 || n == 350) {
            return 5;
        }
        if (n == 357 || n == 359 || n == 323) {
            return 4;
        }
        if (n == 364 || n == 366 || n == 370) {
            return 3;
        }
        return 0;
    }
    
    private ASTree parseUnaryExpr(final SymbolTable symbolTable) throws CompileError {
        switch (this.lex.lookAhead()) {
            case 33:
            case 43:
            case 45:
            case 126:
            case 362:
            case 363: {
                final int value = this.lex.get();
                if (value == 45) {
                    final int lookAhead = this.lex.lookAhead();
                    switch (lookAhead) {
                        case 401:
                        case 402:
                        case 403: {
                            this.lex.get();
                            return new IntConst(-this.lex.getLong(), lookAhead);
                        }
                        case 404:
                        case 405: {
                            this.lex.get();
                            return new DoubleConst(-this.lex.getDouble(), lookAhead);
                        }
                    }
                }
                return Expr.make(value, this.parseUnaryExpr(symbolTable));
            }
            case 40: {
                return this.parseCast(symbolTable);
            }
            default: {
                return this.parsePostfix(symbolTable);
            }
        }
    }
    
    private ASTree parseCast(final SymbolTable symbolTable) throws CompileError {
        final int lookAhead = this.lex.lookAhead(1);
        if (isBuiltinType(lookAhead) && this.nextIsBuiltinCast()) {
            this.lex.get();
            this.lex.get();
            final int arrayDimension = this.parseArrayDimension();
            if (this.lex.get() != 41) {
                throw new CompileError(") is missing", this.lex);
            }
            return new CastExpr(lookAhead, arrayDimension, this.parseUnaryExpr(symbolTable));
        }
        else {
            if (lookAhead != 400 || !this.nextIsClassCast()) {
                return this.parsePostfix(symbolTable);
            }
            this.lex.get();
            final ASTList classType = this.parseClassType(symbolTable);
            final int arrayDimension2 = this.parseArrayDimension();
            if (this.lex.get() != 41) {
                throw new CompileError(") is missing", this.lex);
            }
            return new CastExpr(classType, arrayDimension2, this.parseUnaryExpr(symbolTable));
        }
    }
    
    private boolean nextIsBuiltinCast() {
        int n = 2;
        while (this.lex.lookAhead(n++) == 91) {
            if (this.lex.lookAhead(n++) != 93) {
                return false;
            }
        }
        return this.lex.lookAhead(n - 1) == 41;
    }
    
    private boolean nextIsClassCast() {
        final int nextIsClassType = this.nextIsClassType(1);
        if (nextIsClassType < 0) {
            return false;
        }
        if (this.lex.lookAhead(nextIsClassType) != 41) {
            return false;
        }
        final int lookAhead = this.lex.lookAhead(nextIsClassType + 1);
        return lookAhead == 40 || lookAhead == 412 || lookAhead == 406 || lookAhead == 400 || lookAhead == 339 || lookAhead == 336 || lookAhead == 328 || lookAhead == 410 || lookAhead == 411 || lookAhead == 403 || lookAhead == 402 || lookAhead == 401 || lookAhead == 405 || lookAhead == 404;
    }
    
    private int nextIsClassType(int n) {
        while (this.lex.lookAhead(++n) == 46) {
            if (this.lex.lookAhead(++n) != 400) {
                return -1;
            }
        }
        while (this.lex.lookAhead(n++) == 91) {
            if (this.lex.lookAhead(n++) != 93) {
                return -1;
            }
        }
        return n - 1;
    }
    
    private int parseArrayDimension() throws CompileError {
        int n = 0;
        while (this.lex.lookAhead() == 91) {
            ++n;
            this.lex.get();
            if (this.lex.get() != 93) {
                throw new CompileError("] is missing", this.lex);
            }
        }
        return n;
    }
    
    private ASTList parseClassType(final SymbolTable symbolTable) throws CompileError {
        ASTList append = null;
        while (this.lex.get() == 400) {
            append = ASTList.append(append, new Symbol(this.lex.getString()));
            if (this.lex.lookAhead() != 46) {
                return append;
            }
            this.lex.get();
        }
        throw new SyntaxError(this.lex);
    }
    
    private ASTree parsePostfix(final SymbolTable symbolTable) throws CompileError {
        final int lookAhead = this.lex.lookAhead();
        switch (lookAhead) {
            case 401:
            case 402:
            case 403: {
                this.lex.get();
                return new IntConst(this.lex.getLong(), lookAhead);
            }
            case 404:
            case 405: {
                this.lex.get();
                return new DoubleConst(this.lex.getDouble(), lookAhead);
            }
            default: {
                ASTree asTree = this.parsePrimaryExpr(symbolTable);
                while (true) {
                    switch (this.lex.lookAhead()) {
                        case 40: {
                            asTree = this.parseMethodCall(symbolTable, asTree);
                            continue;
                        }
                        case 91: {
                            if (this.lex.lookAhead(1) == 93) {
                                final int arrayDimension = this.parseArrayDimension();
                                if (this.lex.get() != 46 || this.lex.get() != 307) {
                                    throw new SyntaxError(this.lex);
                                }
                                asTree = this.parseDotClass(asTree, arrayDimension);
                                continue;
                            }
                            else {
                                final ASTree arrayIndex = this.parseArrayIndex(symbolTable);
                                if (arrayIndex == null) {
                                    throw new SyntaxError(this.lex);
                                }
                                asTree = Expr.make(65, asTree, arrayIndex);
                                continue;
                            }
                            break;
                        }
                        case 362:
                        case 363: {
                            asTree = Expr.make(this.lex.get(), null, asTree);
                            continue;
                        }
                        case 46: {
                            this.lex.get();
                            final int value = this.lex.get();
                            if (value == 307) {
                                asTree = this.parseDotClass(asTree, 0);
                                continue;
                            }
                            if (value == 336) {
                                asTree = Expr.make(46, new Symbol(this.toClassName(asTree)), new Keyword(value));
                                continue;
                            }
                            if (value == 400) {
                                asTree = Expr.make(46, asTree, new Member(this.lex.getString()));
                                continue;
                            }
                            throw new CompileError("missing member name", this.lex);
                        }
                        case 35: {
                            this.lex.get();
                            if (this.lex.get() != 400) {
                                throw new CompileError("missing static member name", this.lex);
                            }
                            asTree = Expr.make(35, new Symbol(this.toClassName(asTree)), new Member(this.lex.getString()));
                            continue;
                        }
                        default: {
                            return asTree;
                        }
                    }
                }
                break;
            }
        }
    }
    
    private ASTree parseDotClass(final ASTree asTree, int n) throws CompileError {
        String s = this.toClassName(asTree);
        if (n > 0) {
            final StringBuffer sb = new StringBuffer();
            while (n-- > 0) {
                sb.append('[');
            }
            sb.append('L').append(s.replace('.', '/')).append(';');
            s = sb.toString();
        }
        return Expr.make(46, new Symbol(s), new Member("class"));
    }
    
    private ASTree parseDotClass(final int n, final int n2) throws CompileError {
        if (n2 > 0) {
            return Expr.make(46, new Symbol(CodeGen.toJvmTypeName(n, n2)), new Member("class"));
        }
        String s = null;
        switch (n) {
            case 301: {
                s = "java.lang.Boolean";
                break;
            }
            case 303: {
                s = "java.lang.Byte";
                break;
            }
            case 306: {
                s = "java.lang.Character";
                break;
            }
            case 334: {
                s = "java.lang.Short";
                break;
            }
            case 324: {
                s = "java.lang.Integer";
                break;
            }
            case 326: {
                s = "java.lang.Long";
                break;
            }
            case 317: {
                s = "java.lang.Float";
                break;
            }
            case 312: {
                s = "java.lang.Double";
                break;
            }
            case 344: {
                s = "java.lang.Void";
                break;
            }
            default: {
                throw new CompileError("invalid builtin type: " + n);
            }
        }
        return Expr.make(35, new Symbol(s), new Member("TYPE"));
    }
    
    private ASTree parseMethodCall(final SymbolTable symbolTable, final ASTree asTree) throws CompileError {
        if (asTree instanceof Keyword) {
            final int value = ((Keyword)asTree).get();
            if (value != 339 && value != 336) {
                throw new SyntaxError(this.lex);
            }
        }
        else if (!(asTree instanceof Symbol)) {
            if (asTree instanceof Expr) {
                final int operator = ((Expr)asTree).getOperator();
                if (operator != 46 && operator != 35) {
                    throw new SyntaxError(this.lex);
                }
            }
        }
        return CallExpr.makeCall(asTree, this.parseArgumentList(symbolTable));
    }
    
    private String toClassName(final ASTree asTree) throws CompileError {
        final StringBuffer sb = new StringBuffer();
        this.toClassName(asTree, sb);
        return sb.toString();
    }
    
    private void toClassName(final ASTree asTree, final StringBuffer sb) throws CompileError {
        if (asTree instanceof Symbol) {
            sb.append(((Symbol)asTree).get());
            return;
        }
        if (asTree instanceof Expr) {
            final Expr expr = (Expr)asTree;
            if (expr.getOperator() == 46) {
                this.toClassName(expr.oprand1(), sb);
                sb.append('.');
                this.toClassName(expr.oprand2(), sb);
                return;
            }
        }
        throw new CompileError("bad static member access", this.lex);
    }
    
    private ASTree parsePrimaryExpr(final SymbolTable symbolTable) throws CompileError {
        final int value;
        switch (value = this.lex.get()) {
            case 336:
            case 339:
            case 410:
            case 411:
            case 412: {
                return new Keyword(value);
            }
            case 400: {
                final String string = this.lex.getString();
                final Declarator lookup = symbolTable.lookup(string);
                if (lookup == null) {
                    return new Member(string);
                }
                return new Variable(string, lookup);
            }
            case 406: {
                return new StringL(this.lex.getString());
            }
            case 328: {
                return this.parseNew(symbolTable);
            }
            case 40: {
                final ASTree expression = this.parseExpression(symbolTable);
                if (this.lex.get() == 41) {
                    return expression;
                }
                throw new CompileError(") is missing", this.lex);
            }
            default: {
                if (isBuiltinType(value) || value == 344) {
                    final int arrayDimension = this.parseArrayDimension();
                    if (this.lex.get() == 46 && this.lex.get() == 307) {
                        return this.parseDotClass(value, arrayDimension);
                    }
                }
                throw new SyntaxError(this.lex);
            }
        }
    }
    
    private NewExpr parseNew(final SymbolTable symbolTable) throws CompileError {
        ArrayInit arrayInit = null;
        final int lookAhead = this.lex.lookAhead();
        if (isBuiltinType(lookAhead)) {
            this.lex.get();
            final ASTList arraySize = this.parseArraySize(symbolTable);
            if (this.lex.lookAhead() == 123) {
                arrayInit = this.parseArrayInitializer(symbolTable);
            }
            return new NewExpr(lookAhead, arraySize, arrayInit);
        }
        if (lookAhead == 400) {
            final ASTList classType = this.parseClassType(symbolTable);
            final int lookAhead2 = this.lex.lookAhead();
            if (lookAhead2 == 40) {
                return new NewExpr(classType, this.parseArgumentList(symbolTable));
            }
            if (lookAhead2 == 91) {
                final ASTList arraySize2 = this.parseArraySize(symbolTable);
                if (this.lex.lookAhead() == 123) {
                    arrayInit = this.parseArrayInitializer(symbolTable);
                }
                return NewExpr.makeObjectArray(classType, arraySize2, arrayInit);
            }
        }
        throw new SyntaxError(this.lex);
    }
    
    private ASTList parseArraySize(final SymbolTable symbolTable) throws CompileError {
        ASTList append = null;
        while (this.lex.lookAhead() == 91) {
            append = ASTList.append(append, this.parseArrayIndex(symbolTable));
        }
        return append;
    }
    
    private ASTree parseArrayIndex(final SymbolTable symbolTable) throws CompileError {
        this.lex.get();
        if (this.lex.lookAhead() == 93) {
            this.lex.get();
            return null;
        }
        final ASTree expression = this.parseExpression(symbolTable);
        if (this.lex.get() != 93) {
            throw new CompileError("] is missing", this.lex);
        }
        return expression;
    }
    
    private ASTList parseArgumentList(final SymbolTable symbolTable) throws CompileError {
        if (this.lex.get() != 40) {
            throw new CompileError("( is missing", this.lex);
        }
        ASTList append = null;
        if (this.lex.lookAhead() != 41) {
            while (true) {
                append = ASTList.append(append, this.parseExpression(symbolTable));
                if (this.lex.lookAhead() != 44) {
                    break;
                }
                this.lex.get();
            }
        }
        if (this.lex.get() != 41) {
            throw new CompileError(") is missing", this.lex);
        }
        return append;
    }
    
    static {
        binaryOpPrecedence = new int[] { 0, 0, 0, 0, 1, 6, 0, 0, 0, 1, 2, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 4, 0 };
    }
}
