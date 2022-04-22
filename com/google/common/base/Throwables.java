package com.google.common.base;

import javax.annotation.*;
import java.util.*;
import com.google.common.annotations.*;
import java.io.*;

public final class Throwables
{
    private Throwables() {
    }
    
    public static void propagateIfInstanceOf(@Nullable final Throwable t, final Class clazz) throws Throwable {
        if (t != null && clazz.isInstance(t)) {
            throw (Throwable)clazz.cast(t);
        }
    }
    
    public static void propagateIfPossible(@Nullable final Throwable t) {
        propagateIfInstanceOf(t, Error.class);
        propagateIfInstanceOf(t, RuntimeException.class);
    }
    
    public static void propagateIfPossible(@Nullable final Throwable t, final Class clazz) throws Throwable {
        propagateIfInstanceOf(t, clazz);
        propagateIfPossible(t);
    }
    
    public static void propagateIfPossible(@Nullable final Throwable t, final Class clazz, final Class clazz2) throws Throwable {
        Preconditions.checkNotNull(clazz2);
        propagateIfInstanceOf(t, clazz);
        propagateIfPossible(t, clazz2);
    }
    
    public static RuntimeException propagate(final Throwable t) {
        propagateIfPossible((Throwable)Preconditions.checkNotNull(t));
        throw new RuntimeException(t);
    }
    
    public static Throwable getRootCause(Throwable t) {
        Throwable cause;
        while ((cause = t.getCause()) != null) {
            t = cause;
        }
        return t;
    }
    
    @Beta
    public static List getCausalChain(Throwable cause) {
        Preconditions.checkNotNull(cause);
        final ArrayList<Throwable> list = new ArrayList<Throwable>(4);
        while (cause != null) {
            list.add(cause);
            cause = cause.getCause();
        }
        return Collections.unmodifiableList((List<?>)list);
    }
    
    public static String getStackTraceAsString(final Throwable t) {
        final StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
