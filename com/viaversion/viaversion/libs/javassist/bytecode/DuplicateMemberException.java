package com.viaversion.viaversion.libs.javassist.bytecode;

import com.viaversion.viaversion.libs.javassist.*;

public class DuplicateMemberException extends CannotCompileException
{
    private static final long serialVersionUID = 1L;
    
    public DuplicateMemberException(final String s) {
        super(s);
    }
}
