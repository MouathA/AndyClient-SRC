package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.compiler.ast.*;

public interface ProceedHandler
{
    void doit(final JvstCodeGen p0, final Bytecode p1, final ASTList p2) throws CompileError;
    
    void setReturnType(final JvstTypeChecker p0, final ASTList p1) throws CompileError;
}
