package com.google.gson.internal;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable
{
    private final Type ownerType;
    private final Type rawType;
    private final Type[] typeArguments;
    private static final long serialVersionUID = 0L;
    
    public ParameterizedTypeImpl(final Type p0, final Type p1, final Type... p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   java/lang/Object.<init>:()V
        //     4: aload_2        
        //     5: instanceof      Ljava/lang/Class;
        //     8: ifeq            57
        //    11: aload_2        
        //    12: checkcast       Ljava/lang/Class;
        //    15: astore          4
        //    17: aload_1        
        //    18: ifnonnull       29
        //    21: aload           4
        //    23: invokevirtual   java/lang/Class.getEnclosingClass:()Ljava/lang/Class;
        //    26: ifnonnull       33
        //    29: iconst_1       
        //    30: goto            34
        //    33: iconst_0       
        //    34: invokestatic    com/google/gson/internal/$Gson$Preconditions.checkArgument:(Z)V
        //    37: aload_1        
        //    38: ifnull          49
        //    41: aload           4
        //    43: invokevirtual   java/lang/Class.getEnclosingClass:()Ljava/lang/Class;
        //    46: ifnull          53
        //    49: iconst_1       
        //    50: goto            54
        //    53: iconst_0       
        //    54: invokestatic    com/google/gson/internal/$Gson$Preconditions.checkArgument:(Z)V
        //    57: aload_0        
        //    58: aload_1        
        //    59: ifnonnull       66
        //    62: aconst_null    
        //    63: goto            70
        //    66: aload_1        
        //    67: invokestatic    com/google/gson/internal/$Gson$Types.canonicalize:(Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;
        //    70: putfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.ownerType:Ljava/lang/reflect/Type;
        //    73: aload_0        
        //    74: aload_2        
        //    75: invokestatic    com/google/gson/internal/$Gson$Types.canonicalize:(Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;
        //    78: putfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.rawType:Ljava/lang/reflect/Type;
        //    81: aload_0        
        //    82: aload_3        
        //    83: invokevirtual   [java/lang/reflect/Type.clone:()Ljava/lang/Object;
        //    86: checkcast       [Ljava/lang/reflect/Type;
        //    89: putfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
        //    92: iconst_0       
        //    93: aload_0        
        //    94: getfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
        //    97: arraylength    
        //    98: if_icmpge       141
        //   101: aload_0        
        //   102: getfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
        //   105: iconst_0       
        //   106: aaload         
        //   107: invokestatic    com/google/gson/internal/$Gson$Preconditions.checkNotNull:(Ljava/lang/Object;)Ljava/lang/Object;
        //   110: pop            
        //   111: aload_0        
        //   112: getfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
        //   115: iconst_0       
        //   116: aaload         
        //   117: invokestatic    com/google/gson/internal/$Gson$Types.access$000:(Ljava/lang/reflect/Type;)V
        //   120: aload_0        
        //   121: getfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
        //   124: iconst_0       
        //   125: aload_0        
        //   126: getfield        com/google/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
        //   129: iconst_0       
        //   130: aaload         
        //   131: invokestatic    com/google/gson/internal/$Gson$Types.canonicalize:(Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;
        //   134: aastore        
        //   135: iinc            4, 1
        //   138: goto            92
        //   141: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public Type[] getActualTypeArguments() {
        return this.typeArguments.clone();
    }
    
    public Type getRawType() {
        return this.rawType;
    }
    
    public Type getOwnerType() {
        return this.ownerType;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof ParameterizedType && $Gson$Types.equals(this, (Type)o);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode() ^ $Gson$Types.access$100(this.ownerType);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(30 * (this.typeArguments.length + 1));
        sb.append($Gson$Types.typeToString(this.rawType));
        if (this.typeArguments.length == 0) {
            return sb.toString();
        }
        sb.append("<").append($Gson$Types.typeToString(this.typeArguments[0]));
        while (1 < this.typeArguments.length) {
            sb.append(", ").append($Gson$Types.typeToString(this.typeArguments[1]));
            int n = 0;
            ++n;
        }
        return sb.append(">").toString();
    }
}
