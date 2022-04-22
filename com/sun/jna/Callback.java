package com.sun.jna;

import java.util.*;

public interface Callback
{
    public static final String METHOD_NAME = "callback";
    public static final Collection FORBIDDEN_NAMES = Arrays.asList("hashCode", "equals", "toString");
    
    public interface UncaughtExceptionHandler
    {
        void uncaughtException(final Callback p0, final Throwable p1);
    }
}
