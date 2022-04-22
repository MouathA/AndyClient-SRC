package com.google.common.reflect;

import javax.annotation.concurrent.*;
import java.util.*;
import com.google.common.collect.*;
import java.lang.reflect.*;

@NotThreadSafe
abstract class TypeVisitor
{
    private final Set visited;
    
    TypeVisitor() {
        this.visited = Sets.newHashSet();
    }
    
    public final void visit(final Type... array) {
        while (0 < array.length) {
            final Type type = array[0];
            if (type != null) {
                if (this.visited.add(type)) {
                    if (type instanceof TypeVariable) {
                        this.visitTypeVariable((TypeVariable)type);
                    }
                    else if (type instanceof WildcardType) {
                        this.visitWildcardType((WildcardType)type);
                    }
                    else if (type instanceof ParameterizedType) {
                        this.visitParameterizedType((ParameterizedType)type);
                    }
                    else if (type instanceof Class) {
                        this.visitClass((Class)type);
                    }
                    else {
                        if (!(type instanceof GenericArrayType)) {
                            throw new AssertionError((Object)("Unknown type: " + type));
                        }
                        this.visitGenericArrayType((GenericArrayType)type);
                    }
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    void visitClass(final Class clazz) {
    }
    
    void visitGenericArrayType(final GenericArrayType genericArrayType) {
    }
    
    void visitParameterizedType(final ParameterizedType parameterizedType) {
    }
    
    void visitTypeVariable(final TypeVariable typeVariable) {
    }
    
    void visitWildcardType(final WildcardType wildcardType) {
    }
}
