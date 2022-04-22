package com.viaversion.viaversion.libs.javassist.compiler;

import java.util.*;

public final class KeywordTable extends HashMap
{
    private static final long serialVersionUID = 1L;
    
    public int lookup(final String s) {
        return this.containsKey(s) ? this.get(s) : -1;
    }
    
    public void append(final String s, final int n) {
        this.put(s, n);
    }
}
