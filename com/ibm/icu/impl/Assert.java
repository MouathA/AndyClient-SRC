package com.ibm.icu.impl;

public class Assert
{
    public static void fail(final Exception ex) {
        fail(ex.toString());
    }
    
    public static void fail(final String s) {
        throw new IllegalStateException("failure '" + s + "'");
    }
    
    public static void assrt(final boolean b) {
        if (!b) {
            throw new IllegalStateException("assert failed");
        }
    }
    
    public static void assrt(final String s, final boolean b) {
        if (!b) {
            throw new IllegalStateException("assert '" + s + "' failed");
        }
    }
}
