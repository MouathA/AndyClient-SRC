package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import java.util.*;

public class Subroutine
{
    private List callers;
    private Set access;
    private int start;
    
    public Subroutine(final int start, final int n) {
        this.callers = new ArrayList();
        this.access = new HashSet();
        this.start = start;
        this.callers.add(n);
    }
    
    public void addCaller(final int n) {
        this.callers.add(n);
    }
    
    public int start() {
        return this.start;
    }
    
    public void access(final int n) {
        this.access.add(n);
    }
    
    public boolean isAccessed(final int n) {
        return this.access.contains(n);
    }
    
    public Collection accessed() {
        return this.access;
    }
    
    public Collection callers() {
        return this.callers;
    }
    
    @Override
    public String toString() {
        return "start = " + this.start + " callers = " + this.callers.toString();
    }
}
