package com.viaversion.viaversion.libs.javassist.runtime;

public class DotClass
{
    public static NoClassDefFoundError fail(final ClassNotFoundException ex) {
        return new NoClassDefFoundError(ex.getMessage());
    }
}
