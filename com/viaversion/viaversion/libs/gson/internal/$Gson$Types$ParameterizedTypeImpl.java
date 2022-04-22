package com.viaversion.viaversion.libs.gson.internal;

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
        //     8: ifeq            60
        //    11: aload_2        
        //    12: checkcast       Ljava/lang/Class;
        //    15: astore          4
        //    17: aload           4
        //    19: invokevirtual   java/lang/Class.getModifiers:()I
        //    22: invokestatic    java/lang/reflect/Modifier.isStatic:(I)Z
        //    25: ifne            36
        //    28: aload           4
        //    30: invokevirtual   java/lang/Class.getEnclosingClass:()Ljava/lang/Class;
        //    33: ifnonnull       40
        //    36: iconst_1       
        //    37: goto            41
        //    40: iconst_0       
        //    41: istore          5
        //    43: aload_1        
        //    44: ifnonnull       52
        //    47: iload           5
        //    49: ifeq            56
        //    52: iconst_1       
        //    53: goto            57
        //    56: iconst_0       
        //    57: invokestatic    com/viaversion/viaversion/libs/gson/internal/$Gson$Preconditions.checkArgument:(Z)V
        //    60: aload_0        
        //    61: aload_1        
        //    62: ifnonnull       69
        //    65: aconst_null    
        //    66: goto            73
        //    69: aload_1        
        //    70: invokestatic    com/viaversion/viaversion/libs/gson/internal/$Gson$Types.canonicalize:(Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;
        //    73: putfield        com/viaversion/viaversion/libs/gson/internal/$Gson$Types$ParameterizedTypeImpl.ownerType:Ljava/lang/reflect/Type;
        //    76: aload_0        
        //    77: aload_2        
        //    78: invokestatic    com/viaversion/viaversion/libs/gson/internal/$Gson$Types.canonicalize:(Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;
        //    81: putfield        com/viaversion/viaversion/libs/gson/internal/$Gson$Types$ParameterizedTypeImpl.rawType:Ljava/lang/reflect/Type;
        //    84: aload_0        
        //    85: aload_3        
        //    86: invokevirtual   [java/lang/reflect/Type.clone:()Ljava/lang/Object;
        //    89: checkcast       [Ljava/lang/reflect/Type;
        //    92: putfield        com/viaversion/viaversion/libs/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
        //    95: aload_0        
        //    96: getfield        com/viaversion/viaversion/libs/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
        //    99: arraylength    
        //   100: istore          5
        //   102: iconst_0       
        //   103: iload           5
        //   105: if_icmpge       148
        //   108: aload_0        
        //   109: getfield        com/viaversion/viaversion/libs/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
        //   112: iconst_0       
        //   113: aaload         
        //   114: invokestatic    com/viaversion/viaversion/libs/gson/internal/$Gson$Preconditions.checkNotNull:(Ljava/lang/Object;)Ljava/lang/Object;
        //   117: pop            
        //   118: aload_0        
        //   119: getfield        com/viaversion/viaversion/libs/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
        //   122: iconst_0       
        //   123: aaload         
        //   124: invokestatic    com/viaversion/viaversion/libs/gson/internal/$Gson$Types.checkNotPrimitive:(Ljava/lang/reflect/Type;)V
        //   127: aload_0        
        //   128: getfield        com/viaversion/viaversion/libs/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
        //   131: iconst_0       
        //   132: aload_0        
        //   133: getfield        com/viaversion/viaversion/libs/gson/internal/$Gson$Types$ParameterizedTypeImpl.typeArguments:[Ljava/lang/reflect/Type;
        //   136: iconst_0       
        //   137: aaload         
        //   138: invokestatic    com/viaversion/viaversion/libs/gson/internal/$Gson$Types.canonicalize:(Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;
        //   141: aastore        
        //   142: iinc            4, 1
        //   145: goto            102
        //   148: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public Type[] getActualTypeArguments() {
        return this.typeArguments.clone();
    }
    
    @Override
    public Type getRawType() {
        return this.rawType;
    }
    
    @Override
    public Type getOwnerType() {
        return this.ownerType;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof ParameterizedType && $Gson$Types.equals(this, (Type)o);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode() ^ $Gson$Types.hashCodeOrZero(this.ownerType);
    }
    
    @Override
    public String toString() {
        final int length = this.typeArguments.length;
        if (length == 0) {
            return $Gson$Types.typeToString(this.rawType);
        }
        final StringBuilder sb = new StringBuilder(30 * (length + 1));
        sb.append($Gson$Types.typeToString(this.rawType)).append("<").append($Gson$Types.typeToString(this.typeArguments[0]));
        while (1 < length) {
            sb.append(", ").append($Gson$Types.typeToString(this.typeArguments[1]));
            int n = 0;
            ++n;
        }
        return sb.append(">").toString();
    }
}
