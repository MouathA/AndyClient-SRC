package com.viaversion.viaversion.libs.javassist.compiler;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.compiler.ast.*;

public final class SymbolTable extends HashMap
{
    private static final long serialVersionUID = 1L;
    private SymbolTable parent;
    
    public SymbolTable() {
        this(null);
    }
    
    public SymbolTable(final SymbolTable parent) {
        this.parent = parent;
    }
    
    public SymbolTable getParent() {
        return this.parent;
    }
    
    public Declarator lookup(final String s) {
        final Declarator declarator = this.get(s);
        if (declarator == null && this.parent != null) {
            return this.parent.lookup(s);
        }
        return declarator;
    }
    
    public void append(final String s, final Declarator declarator) {
        this.put(s, declarator);
    }
}
