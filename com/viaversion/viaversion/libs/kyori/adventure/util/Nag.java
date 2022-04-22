package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.*;

public abstract class Nag extends RuntimeException
{
    public static void print(@NotNull final Nag nag) {
        nag.printStackTrace();
    }
    
    protected Nag(final String message) {
        super(message);
    }
}
