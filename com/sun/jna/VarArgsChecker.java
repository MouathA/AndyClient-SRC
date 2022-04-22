package com.sun.jna;

import java.lang.reflect.*;

abstract class VarArgsChecker
{
    private VarArgsChecker() {
    }
    
    static VarArgsChecker create() {
        if (Method.class.getMethod("isVarArgs", (Class<?>[])new Class[0]) != null) {
            return new RealVarArgsChecker(null);
        }
        return new NoVarArgsChecker(null);
    }
    
    abstract boolean isVarArgs(final Method p0);
    
    abstract int fixedArgs(final Method p0);
    
    VarArgsChecker(final VarArgsChecker$1 object) {
        this();
    }
    
    private static final class NoVarArgsChecker extends VarArgsChecker
    {
        private NoVarArgsChecker() {
            super(null);
        }
        
        @Override
        boolean isVarArgs(final Method method) {
            return false;
        }
        
        @Override
        int fixedArgs(final Method method) {
            return 0;
        }
        
        NoVarArgsChecker(final VarArgsChecker$1 object) {
            this();
        }
    }
    
    private static final class RealVarArgsChecker extends VarArgsChecker
    {
        private RealVarArgsChecker() {
            super(null);
        }
        
        @Override
        boolean isVarArgs(final Method method) {
            return method.isVarArgs();
        }
        
        @Override
        int fixedArgs(final Method method) {
            return method.isVarArgs() ? (method.getParameterTypes().length - 1) : 0;
        }
        
        RealVarArgsChecker(final VarArgsChecker$1 object) {
            this();
        }
    }
}
