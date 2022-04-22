package com.viaversion.viaversion.libs.javassist.tools.reflect;

import com.viaversion.viaversion.libs.javassist.*;

public class CannotReflectException extends CannotCompileException
{
    private static final long serialVersionUID = 1L;
    
    public CannotReflectException(final String s) {
        super(s);
    }
}
