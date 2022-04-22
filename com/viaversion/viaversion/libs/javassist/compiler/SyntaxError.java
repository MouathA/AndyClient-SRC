package com.viaversion.viaversion.libs.javassist.compiler;

public class SyntaxError extends CompileError
{
    private static final long serialVersionUID = 1L;
    
    public SyntaxError(final Lex lex) {
        super("syntax error near \"" + lex.getTextAround() + "\"", lex);
    }
}
