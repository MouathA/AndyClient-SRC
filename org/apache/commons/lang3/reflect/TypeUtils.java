package org.apache.commons.lang3.reflect;

import java.lang.reflect.*;
import java.util.*;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.builder.*;

public class TypeUtils
{
    public static final WildcardType WILDCARD_ALL;
    
    public static boolean isAssignable(final Type type, final Type type2) {
        return isAssignable(type, type2, (Map)null);
    }
    
    private static boolean isAssignable(final Type p0, final ParameterizedType p1, final Map p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: iconst_1       
        //     5: ireturn        
        //     6: aload_1        
        //     7: ifnonnull       12
        //    10: iconst_0       
        //    11: ireturn        
        //    12: aload_1        
        //    13: aload_0        
        //    14: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //    17: ifeq            22
        //    20: iconst_1       
        //    21: ireturn        
        //    22: aload_1        
        //    23: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getRawType:(Ljava/lang/reflect/ParameterizedType;)Ljava/lang/Class;
        //    26: astore_3       
        //    27: aload_0        
        //    28: aload_3        
        //    29: aconst_null    
        //    30: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getTypeArguments:(Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/util/Map;)Ljava/util/Map;
        //    33: astore          4
        //    35: aload           4
        //    37: ifnonnull       42
        //    40: iconst_0       
        //    41: ireturn        
        //    42: aload           4
        //    44: invokeinterface java/util/Map.isEmpty:()Z
        //    49: ifeq            54
        //    52: iconst_1       
        //    53: ireturn        
        //    54: aload_1        
        //    55: aload_3        
        //    56: aload_2        
        //    57: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getTypeArguments:(Ljava/lang/reflect/ParameterizedType;Ljava/lang/Class;Ljava/util/Map;)Ljava/util/Map;
        //    60: astore          5
        //    62: aload           5
        //    64: invokeinterface java/util/Map.keySet:()Ljava/util/Set;
        //    69: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    74: astore          6
        //    76: aload           6
        //    78: invokeinterface java/util/Iterator.hasNext:()Z
        //    83: ifeq            152
        //    86: aload           6
        //    88: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    93: checkcast       Ljava/lang/reflect/TypeVariable;
        //    96: astore          7
        //    98: aload           7
        //   100: aload           5
        //   102: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.unrollVariableAssignments:(Ljava/lang/reflect/TypeVariable;Ljava/util/Map;)Ljava/lang/reflect/Type;
        //   105: astore          8
        //   107: aload           7
        //   109: aload           4
        //   111: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.unrollVariableAssignments:(Ljava/lang/reflect/TypeVariable;Ljava/util/Map;)Ljava/lang/reflect/Type;
        //   114: astore          9
        //   116: aload           9
        //   118: ifnull          149
        //   121: aload           8
        //   123: aload           9
        //   125: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   128: ifne            149
        //   131: aload           8
        //   133: instanceof      Ljava/lang/reflect/WildcardType;
        //   136: ifeq            147
        //   139: aload           9
        //   141: aload           8
        //   143: aload_2        
        //   144: ifnull          149
        //   147: iconst_0       
        //   148: ireturn        
        //   149: goto            76
        //   152: iconst_1       
        //   153: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0147 (coming from #0144).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static Type unrollVariableAssignments(TypeVariable typeVariable, final Map map) {
        Type type;
        while (true) {
            type = map.get(typeVariable);
            if (!(type instanceof TypeVariable) || type.equals(typeVariable)) {
                break;
            }
            typeVariable = (TypeVariable)type;
        }
        return type;
    }
    
    private static boolean isAssignable(final Type p0, final GenericArrayType p1, final Map p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: iconst_1       
        //     5: ireturn        
        //     6: aload_1        
        //     7: ifnonnull       12
        //    10: iconst_0       
        //    11: ireturn        
        //    12: aload_1        
        //    13: aload_0        
        //    14: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //    17: ifeq            22
        //    20: iconst_1       
        //    21: ireturn        
        //    22: aload_1        
        //    23: invokeinterface java/lang/reflect/GenericArrayType.getGenericComponentType:()Ljava/lang/reflect/Type;
        //    28: astore_3       
        //    29: aload_0        
        //    30: instanceof      Ljava/lang/Class;
        //    33: ifeq            66
        //    36: aload_0        
        //    37: checkcast       Ljava/lang/Class;
        //    40: astore          4
        //    42: aload           4
        //    44: invokevirtual   java/lang/Class.isArray:()Z
        //    47: ifeq            64
        //    50: aload           4
        //    52: invokevirtual   java/lang/Class.getComponentType:()Ljava/lang/Class;
        //    55: aload_3        
        //    56: aload_2        
        //    57: ifnull          64
        //    60: iconst_1       
        //    61: goto            65
        //    64: iconst_0       
        //    65: ireturn        
        //    66: aload_0        
        //    67: instanceof      Ljava/lang/reflect/GenericArrayType;
        //    70: ifeq            88
        //    73: aload_0        
        //    74: checkcast       Ljava/lang/reflect/GenericArrayType;
        //    77: invokeinterface java/lang/reflect/GenericArrayType.getGenericComponentType:()Ljava/lang/reflect/Type;
        //    82: aload_3        
        //    83: aload_2        
        //    84: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.isAssignable:(Ljava/lang/reflect/Type;Ljava/lang/reflect/Type;Ljava/util/Map;)Z
        //    87: ireturn        
        //    88: aload_0        
        //    89: instanceof      Ljava/lang/reflect/WildcardType;
        //    92: ifeq            140
        //    95: aload_0        
        //    96: checkcast       Ljava/lang/reflect/WildcardType;
        //    99: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getImplicitUpperBounds:(Ljava/lang/reflect/WildcardType;)[Ljava/lang/reflect/Type;
        //   102: astore          4
        //   104: aload           4
        //   106: arraylength    
        //   107: istore          5
        //   109: iconst_0       
        //   110: iload           5
        //   112: if_icmpge       138
        //   115: aload           4
        //   117: iconst_0       
        //   118: aaload         
        //   119: astore          7
        //   121: aload           7
        //   123: aload_1        
        //   124: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.isAssignable:(Ljava/lang/reflect/Type;Ljava/lang/reflect/Type;)Z
        //   127: ifeq            132
        //   130: iconst_1       
        //   131: ireturn        
        //   132: iinc            6, 1
        //   135: goto            109
        //   138: iconst_0       
        //   139: ireturn        
        //   140: aload_0        
        //   141: instanceof      Ljava/lang/reflect/TypeVariable;
        //   144: ifeq            192
        //   147: aload_0        
        //   148: checkcast       Ljava/lang/reflect/TypeVariable;
        //   151: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getImplicitBounds:(Ljava/lang/reflect/TypeVariable;)[Ljava/lang/reflect/Type;
        //   154: astore          4
        //   156: aload           4
        //   158: arraylength    
        //   159: istore          5
        //   161: iconst_0       
        //   162: iload           5
        //   164: if_icmpge       190
        //   167: aload           4
        //   169: iconst_0       
        //   170: aaload         
        //   171: astore          7
        //   173: aload           7
        //   175: aload_1        
        //   176: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.isAssignable:(Ljava/lang/reflect/Type;Ljava/lang/reflect/Type;)Z
        //   179: ifeq            184
        //   182: iconst_1       
        //   183: ireturn        
        //   184: iinc            6, 1
        //   187: goto            161
        //   190: iconst_0       
        //   191: ireturn        
        //   192: aload_0        
        //   193: instanceof      Ljava/lang/reflect/ParameterizedType;
        //   196: ifeq            201
        //   199: iconst_0       
        //   200: ireturn        
        //   201: new             Ljava/lang/IllegalStateException;
        //   204: dup            
        //   205: new             Ljava/lang/StringBuilder;
        //   208: dup            
        //   209: invokespecial   java/lang/StringBuilder.<init>:()V
        //   212: ldc             "found an unhandled type: "
        //   214: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   217: aload_0        
        //   218: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   221: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   224: invokespecial   java/lang/IllegalStateException.<init>:(Ljava/lang/String;)V
        //   227: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0064 (coming from #0057).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static boolean isAssignable(final Type p0, final WildcardType p1, final Map p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: iconst_1       
        //     5: ireturn        
        //     6: aload_1        
        //     7: ifnonnull       12
        //    10: iconst_0       
        //    11: ireturn        
        //    12: aload_1        
        //    13: aload_0        
        //    14: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //    17: ifeq            22
        //    20: iconst_1       
        //    21: ireturn        
        //    22: aload_1        
        //    23: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getImplicitUpperBounds:(Ljava/lang/reflect/WildcardType;)[Ljava/lang/reflect/Type;
        //    26: astore_3       
        //    27: aload_1        
        //    28: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getImplicitLowerBounds:(Ljava/lang/reflect/WildcardType;)[Ljava/lang/reflect/Type;
        //    31: astore          4
        //    33: aload_0        
        //    34: instanceof      Ljava/lang/reflect/WildcardType;
        //    37: ifeq            205
        //    40: aload_0        
        //    41: checkcast       Ljava/lang/reflect/WildcardType;
        //    44: astore          5
        //    46: aload           5
        //    48: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getImplicitUpperBounds:(Ljava/lang/reflect/WildcardType;)[Ljava/lang/reflect/Type;
        //    51: astore          6
        //    53: aload           5
        //    55: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getImplicitLowerBounds:(Ljava/lang/reflect/WildcardType;)[Ljava/lang/reflect/Type;
        //    58: astore          7
        //    60: aload_3        
        //    61: astore          8
        //    63: aload           8
        //    65: arraylength    
        //    66: istore          9
        //    68: iconst_0       
        //    69: iload           9
        //    71: if_icmpge       131
        //    74: aload           8
        //    76: iconst_0       
        //    77: aaload         
        //    78: astore          11
        //    80: aload           11
        //    82: aload_2        
        //    83: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.substituteTypeVariables:(Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
        //    86: astore          11
        //    88: aload           6
        //    90: astore          12
        //    92: aload           12
        //    94: arraylength    
        //    95: istore          13
        //    97: iconst_0       
        //    98: iload           13
        //   100: if_icmpge       125
        //   103: aload           12
        //   105: iconst_0       
        //   106: aaload         
        //   107: astore          15
        //   109: aload           15
        //   111: aload           11
        //   113: aload_2        
        //   114: ifnull          119
        //   117: iconst_0       
        //   118: ireturn        
        //   119: iinc            14, 1
        //   122: goto            97
        //   125: iinc            10, 1
        //   128: goto            68
        //   131: aload           4
        //   133: astore          8
        //   135: aload           8
        //   137: arraylength    
        //   138: istore          9
        //   140: iconst_0       
        //   141: iload           9
        //   143: if_icmpge       203
        //   146: aload           8
        //   148: iconst_0       
        //   149: aaload         
        //   150: astore          11
        //   152: aload           11
        //   154: aload_2        
        //   155: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.substituteTypeVariables:(Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
        //   158: astore          11
        //   160: aload           7
        //   162: astore          12
        //   164: aload           12
        //   166: arraylength    
        //   167: istore          13
        //   169: iconst_0       
        //   170: iload           13
        //   172: if_icmpge       197
        //   175: aload           12
        //   177: iconst_0       
        //   178: aaload         
        //   179: astore          15
        //   181: aload           11
        //   183: aload           15
        //   185: aload_2        
        //   186: ifnull          191
        //   189: iconst_0       
        //   190: ireturn        
        //   191: iinc            14, 1
        //   194: goto            169
        //   197: iinc            10, 1
        //   200: goto            140
        //   203: iconst_1       
        //   204: ireturn        
        //   205: aload_3        
        //   206: astore          5
        //   208: aload           5
        //   210: arraylength    
        //   211: istore          6
        //   213: iconst_0       
        //   214: iload           6
        //   216: if_icmpge       244
        //   219: aload           5
        //   221: iconst_0       
        //   222: aaload         
        //   223: astore          8
        //   225: aload_0        
        //   226: aload           8
        //   228: aload_2        
        //   229: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.substituteTypeVariables:(Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
        //   232: aload_2        
        //   233: ifnull          238
        //   236: iconst_0       
        //   237: ireturn        
        //   238: iinc            7, 1
        //   241: goto            213
        //   244: aload           4
        //   246: astore          5
        //   248: aload           5
        //   250: arraylength    
        //   251: istore          6
        //   253: iconst_0       
        //   254: iload           6
        //   256: if_icmpge       284
        //   259: aload           5
        //   261: iconst_0       
        //   262: aaload         
        //   263: astore          8
        //   265: aload           8
        //   267: aload_2        
        //   268: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.substituteTypeVariables:(Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
        //   271: aload_0        
        //   272: aload_2        
        //   273: ifnull          278
        //   276: iconst_0       
        //   277: ireturn        
        //   278: iinc            7, 1
        //   281: goto            253
        //   284: iconst_1       
        //   285: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0253 (coming from #0281).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static Type substituteTypeVariables(final Type type, final Map map) {
        if (!(type instanceof TypeVariable) || map == null) {
            return type;
        }
        final Type type2 = map.get(type);
        if (type2 == null) {
            throw new IllegalArgumentException("missing assignment type for type variable " + type);
        }
        return type2;
    }
    
    public static Map getTypeArguments(final ParameterizedType parameterizedType) {
        return getTypeArguments(parameterizedType, getRawType(parameterizedType), null);
    }
    
    public static Map getTypeArguments(final Type type, final Class clazz) {
        return getTypeArguments(type, clazz, null);
    }
    
    private static Map getTypeArguments(final Type p0, final Class p1, final Map p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: instanceof      Ljava/lang/Class;
        //     4: ifeq            17
        //     7: aload_0        
        //     8: checkcast       Ljava/lang/Class;
        //    11: aload_1        
        //    12: aload_2        
        //    13: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getTypeArguments:(Ljava/lang/Class;Ljava/lang/Class;Ljava/util/Map;)Ljava/util/Map;
        //    16: areturn        
        //    17: aload_0        
        //    18: instanceof      Ljava/lang/reflect/ParameterizedType;
        //    21: ifeq            34
        //    24: aload_0        
        //    25: checkcast       Ljava/lang/reflect/ParameterizedType;
        //    28: aload_1        
        //    29: aload_2        
        //    30: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getTypeArguments:(Ljava/lang/reflect/ParameterizedType;Ljava/lang/Class;Ljava/util/Map;)Ljava/util/Map;
        //    33: areturn        
        //    34: aload_0        
        //    35: instanceof      Ljava/lang/reflect/GenericArrayType;
        //    38: ifeq            70
        //    41: aload_0        
        //    42: checkcast       Ljava/lang/reflect/GenericArrayType;
        //    45: invokeinterface java/lang/reflect/GenericArrayType.getGenericComponentType:()Ljava/lang/reflect/Type;
        //    50: aload_1        
        //    51: invokevirtual   java/lang/Class.isArray:()Z
        //    54: ifeq            64
        //    57: aload_1        
        //    58: invokevirtual   java/lang/Class.getComponentType:()Ljava/lang/Class;
        //    61: goto            65
        //    64: aload_1        
        //    65: aload_2        
        //    66: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getTypeArguments:(Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/util/Map;)Ljava/util/Map;
        //    69: areturn        
        //    70: aload_0        
        //    71: instanceof      Ljava/lang/reflect/WildcardType;
        //    74: ifeq            122
        //    77: aload_0        
        //    78: checkcast       Ljava/lang/reflect/WildcardType;
        //    81: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getImplicitUpperBounds:(Ljava/lang/reflect/WildcardType;)[Ljava/lang/reflect/Type;
        //    84: astore_3       
        //    85: aload_3        
        //    86: arraylength    
        //    87: istore          4
        //    89: iconst_0       
        //    90: iload           4
        //    92: if_icmpge       120
        //    95: aload_3        
        //    96: iconst_0       
        //    97: aaload         
        //    98: astore          6
        //   100: aload           6
        //   102: aload_1        
        //   103: ifnonnull       114
        //   106: aload           6
        //   108: aload_1        
        //   109: aload_2        
        //   110: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getTypeArguments:(Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/util/Map;)Ljava/util/Map;
        //   113: areturn        
        //   114: iinc            5, 1
        //   117: goto            89
        //   120: aconst_null    
        //   121: areturn        
        //   122: aload_0        
        //   123: instanceof      Ljava/lang/reflect/TypeVariable;
        //   126: ifeq            174
        //   129: aload_0        
        //   130: checkcast       Ljava/lang/reflect/TypeVariable;
        //   133: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getImplicitBounds:(Ljava/lang/reflect/TypeVariable;)[Ljava/lang/reflect/Type;
        //   136: astore_3       
        //   137: aload_3        
        //   138: arraylength    
        //   139: istore          4
        //   141: iconst_0       
        //   142: iload           4
        //   144: if_icmpge       172
        //   147: aload_3        
        //   148: iconst_0       
        //   149: aaload         
        //   150: astore          6
        //   152: aload           6
        //   154: aload_1        
        //   155: ifnonnull       166
        //   158: aload           6
        //   160: aload_1        
        //   161: aload_2        
        //   162: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getTypeArguments:(Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/util/Map;)Ljava/util/Map;
        //   165: areturn        
        //   166: iinc            5, 1
        //   169: goto            141
        //   172: aconst_null    
        //   173: areturn        
        //   174: new             Ljava/lang/IllegalStateException;
        //   177: dup            
        //   178: new             Ljava/lang/StringBuilder;
        //   181: dup            
        //   182: invokespecial   java/lang/StringBuilder.<init>:()V
        //   185: ldc             "found an unhandled type: "
        //   187: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   190: aload_0        
        //   191: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   194: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   197: invokespecial   java/lang/IllegalStateException.<init>:(Ljava/lang/String;)V
        //   200: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0141 (coming from #0169).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static Map getTypeArguments(final ParameterizedType parameterizedType, final Class clazz, final Map map) {
        final Class rawType = getRawType(parameterizedType);
        if (clazz == null) {
            return null;
        }
        final Type ownerType = parameterizedType.getOwnerType();
        Map<TypeVariable, Type> typeArguments;
        if (ownerType instanceof ParameterizedType) {
            final ParameterizedType parameterizedType2 = (ParameterizedType)ownerType;
            typeArguments = (Map<TypeVariable, Type>)getTypeArguments(parameterizedType2, getRawType(parameterizedType2), map);
        }
        else {
            typeArguments = ((map == null) ? new HashMap<TypeVariable, Type>() : new HashMap<TypeVariable, Type>(map));
        }
        final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        final TypeVariable[] typeParameters = rawType.getTypeParameters();
        while (0 < typeParameters.length) {
            final Type type = actualTypeArguments[0];
            typeArguments.put(typeParameters[0], typeArguments.containsKey(type) ? typeArguments.get(type) : type);
            int n = 0;
            ++n;
        }
        if (clazz.equals(rawType)) {
            return typeArguments;
        }
        return getTypeArguments(getClosestParentType(rawType, clazz), clazz, typeArguments);
    }
    
    private static Map getTypeArguments(Class primitiveToWrapper, final Class clazz, final Map map) {
        if (clazz == null) {
            return null;
        }
        if (primitiveToWrapper.isPrimitive()) {
            if (clazz.isPrimitive()) {
                return new HashMap();
            }
            primitiveToWrapper = ClassUtils.primitiveToWrapper(primitiveToWrapper);
        }
        final HashMap hashMap = (map == null) ? new HashMap() : new HashMap(map);
        if (clazz.equals(primitiveToWrapper)) {
            return hashMap;
        }
        return getTypeArguments(getClosestParentType(primitiveToWrapper, clazz), clazz, hashMap);
    }
    
    public static Map determineTypeArguments(final Class clazz, final ParameterizedType parameterizedType) {
        Validate.notNull(clazz, "cls is null", new Object[0]);
        Validate.notNull(parameterizedType, "superType is null", new Object[0]);
        final Class rawType = getRawType(parameterizedType);
        if (rawType == null) {
            return null;
        }
        if (clazz.equals(rawType)) {
            return getTypeArguments(parameterizedType, rawType, null);
        }
        final Type closestParentType = getClosestParentType(clazz, rawType);
        if (closestParentType instanceof Class) {
            return determineTypeArguments((Class)closestParentType, parameterizedType);
        }
        final ParameterizedType parameterizedType2 = (ParameterizedType)closestParentType;
        final Map determineTypeArguments = determineTypeArguments(getRawType(parameterizedType2), parameterizedType);
        mapTypeVariablesToArguments(clazz, parameterizedType2, determineTypeArguments);
        return determineTypeArguments;
    }
    
    private static void mapTypeVariablesToArguments(final Class clazz, final ParameterizedType parameterizedType, final Map map) {
        final Type ownerType = parameterizedType.getOwnerType();
        if (ownerType instanceof ParameterizedType) {
            mapTypeVariablesToArguments(clazz, (ParameterizedType)ownerType, map);
        }
        final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        final TypeVariable[] typeParameters = getRawType(parameterizedType).getTypeParameters();
        final List<TypeVariable> list = Arrays.asList(clazz.getTypeParameters());
        while (0 < actualTypeArguments.length) {
            final TypeVariable typeVariable = typeParameters[0];
            final Type type = actualTypeArguments[0];
            if (list.contains(type) && map.containsKey(typeVariable)) {
                map.put(type, map.get(typeVariable));
            }
            int n = 0;
            ++n;
        }
    }
    
    private static Type getClosestParentType(final Class p0, final Class p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   java/lang/Class.isInterface:()Z
        //     4: ifeq            134
        //     7: aload_0        
        //     8: invokevirtual   java/lang/Class.getGenericInterfaces:()[Ljava/lang/reflect/Type;
        //    11: astore_2       
        //    12: aconst_null    
        //    13: astore_3       
        //    14: aload_2        
        //    15: astore          4
        //    17: aload           4
        //    19: arraylength    
        //    20: istore          5
        //    22: iconst_0       
        //    23: iload           5
        //    25: if_icmpge       128
        //    28: aload           4
        //    30: iconst_0       
        //    31: aaload         
        //    32: astore          7
        //    34: aconst_null    
        //    35: astore          8
        //    37: aload           7
        //    39: instanceof      Ljava/lang/reflect/ParameterizedType;
        //    42: ifeq            58
        //    45: aload           7
        //    47: checkcast       Ljava/lang/reflect/ParameterizedType;
        //    50: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getRawType:(Ljava/lang/reflect/ParameterizedType;)Ljava/lang/Class;
        //    53: astore          8
        //    55: goto            104
        //    58: aload           7
        //    60: instanceof      Ljava/lang/Class;
        //    63: ifeq            76
        //    66: aload           7
        //    68: checkcast       Ljava/lang/Class;
        //    71: astore          8
        //    73: goto            104
        //    76: new             Ljava/lang/IllegalStateException;
        //    79: dup            
        //    80: new             Ljava/lang/StringBuilder;
        //    83: dup            
        //    84: invokespecial   java/lang/StringBuilder.<init>:()V
        //    87: ldc             "Unexpected generic interface type found: "
        //    89: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    92: aload           7
        //    94: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    97: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   100: invokespecial   java/lang/IllegalStateException.<init>:(Ljava/lang/String;)V
        //   103: athrow         
        //   104: aload           8
        //   106: aload_1        
        //   107: ifnonnull       122
        //   110: aload_3        
        //   111: aload           8
        //   113: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.isAssignable:(Ljava/lang/reflect/Type;Ljava/lang/reflect/Type;)Z
        //   116: ifeq            122
        //   119: aload           7
        //   121: astore_3       
        //   122: iinc            6, 1
        //   125: goto            22
        //   128: aload_3        
        //   129: ifnull          134
        //   132: aload_3        
        //   133: areturn        
        //   134: aload_0        
        //   135: invokevirtual   java/lang/Class.getGenericSuperclass:()Ljava/lang/reflect/Type;
        //   138: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0022 (coming from #0125).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static boolean isInstance(final Object o, final Type type) {
        return type != null && ((o == null) ? (!(type instanceof Class) || !((Class)type).isPrimitive()) : isAssignable((Type)o.getClass(), type, (Map)null));
    }
    
    public static Type[] normalizeUpperBounds(final Type[] p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "null value specified for bounds array"
        //     3: iconst_0       
        //     4: anewarray       Ljava/lang/Object;
        //     7: invokestatic    org/apache/commons/lang3/Validate.notNull:(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
        //    10: pop            
        //    11: aload_0        
        //    12: arraylength    
        //    13: iconst_2       
        //    14: if_icmpge       19
        //    17: aload_0        
        //    18: areturn        
        //    19: new             Ljava/util/HashSet;
        //    22: dup            
        //    23: aload_0        
        //    24: arraylength    
        //    25: invokespecial   java/util/HashSet.<init>:(I)V
        //    28: astore_1       
        //    29: aload_0        
        //    30: astore_2       
        //    31: aload_2        
        //    32: arraylength    
        //    33: istore_3       
        //    34: iconst_0       
        //    35: iload_3        
        //    36: if_icmpge       105
        //    39: aload_2        
        //    40: iconst_0       
        //    41: aaload         
        //    42: astore          5
        //    44: aload_0        
        //    45: astore          7
        //    47: aload           7
        //    49: arraylength    
        //    50: istore          8
        //    52: iconst_0       
        //    53: iload           8
        //    55: if_icmpge       87
        //    58: aload           7
        //    60: iconst_0       
        //    61: aaload         
        //    62: astore          10
        //    64: aload           5
        //    66: aload           10
        //    68: if_acmpeq       81
        //    71: aload           10
        //    73: aload           5
        //    75: goto            81
        //    78: goto            87
        //    81: iinc            9, 1
        //    84: goto            52
        //    87: goto            99
        //    90: aload_1        
        //    91: aload           5
        //    93: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //    98: pop            
        //    99: iinc            4, 1
        //   102: goto            34
        //   105: aload_1        
        //   106: aload_1        
        //   107: invokeinterface java/util/Set.size:()I
        //   112: anewarray       Ljava/lang/reflect/Type;
        //   115: invokeinterface java/util/Set.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   120: checkcast       [Ljava/lang/reflect/Type;
        //   123: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0081 (coming from #0075).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static Type[] getImplicitBounds(final TypeVariable typeVariable) {
        Validate.notNull(typeVariable, "typeVariable is null", new Object[0]);
        final Type[] bounds = typeVariable.getBounds();
        return (bounds.length == 0) ? new Type[] { Object.class } : normalizeUpperBounds(bounds);
    }
    
    public static Type[] getImplicitUpperBounds(final WildcardType wildcardType) {
        Validate.notNull(wildcardType, "wildcardType is null", new Object[0]);
        final Type[] upperBounds = wildcardType.getUpperBounds();
        return (upperBounds.length == 0) ? new Type[] { Object.class } : normalizeUpperBounds(upperBounds);
    }
    
    public static Type[] getImplicitLowerBounds(final WildcardType wildcardType) {
        Validate.notNull(wildcardType, "wildcardType is null", new Object[0]);
        final Type[] lowerBounds = wildcardType.getLowerBounds();
        return (lowerBounds.length == 0) ? new Type[] { null } : lowerBounds;
    }
    
    public static boolean typesSatisfyVariables(final Map p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc_w           "typeVarAssigns is null"
        //     4: iconst_0       
        //     5: anewarray       Ljava/lang/Object;
        //     8: invokestatic    org/apache/commons/lang3/Validate.notNull:(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
        //    11: pop            
        //    12: aload_0        
        //    13: invokeinterface java/util/Map.entrySet:()Ljava/util/Set;
        //    18: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    23: astore_1       
        //    24: aload_1        
        //    25: invokeinterface java/util/Iterator.hasNext:()Z
        //    30: ifeq            110
        //    33: aload_1        
        //    34: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    39: checkcast       Ljava/util/Map$Entry;
        //    42: astore_2       
        //    43: aload_2        
        //    44: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
        //    49: checkcast       Ljava/lang/reflect/TypeVariable;
        //    52: astore_3       
        //    53: aload_2        
        //    54: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
        //    59: checkcast       Ljava/lang/reflect/Type;
        //    62: astore          4
        //    64: aload_3        
        //    65: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.getImplicitBounds:(Ljava/lang/reflect/TypeVariable;)[Ljava/lang/reflect/Type;
        //    68: astore          5
        //    70: aload           5
        //    72: arraylength    
        //    73: istore          6
        //    75: iconst_0       
        //    76: iload           6
        //    78: if_icmpge       107
        //    81: aload           5
        //    83: iconst_0       
        //    84: aaload         
        //    85: astore          8
        //    87: aload           4
        //    89: aload           8
        //    91: aload_0        
        //    92: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.substituteTypeVariables:(Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
        //    95: aload_0        
        //    96: ifnull          101
        //    99: iconst_0       
        //   100: ireturn        
        //   101: iinc            7, 1
        //   104: goto            75
        //   107: goto            24
        //   110: iconst_1       
        //   111: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0075 (coming from #0104).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static Class getRawType(final ParameterizedType parameterizedType) {
        final Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            throw new IllegalStateException("Wait... What!? Type of rawType: " + rawType);
        }
        return (Class)rawType;
    }
    
    public static Class getRawType(final Type type, final Type type2) {
        if (type instanceof Class) {
            return (Class)type;
        }
        if (type instanceof ParameterizedType) {
            return getRawType((ParameterizedType)type);
        }
        if (type instanceof TypeVariable) {
            if (type2 == null) {
                return null;
            }
            final Class genericDeclaration = ((TypeVariable)type).getGenericDeclaration();
            if (!(genericDeclaration instanceof Class)) {
                return null;
            }
            final Map typeArguments = getTypeArguments(type2, genericDeclaration);
            if (typeArguments == null) {
                return null;
            }
            final Type type3 = typeArguments.get(type);
            if (type3 == null) {
                return null;
            }
            return getRawType(type3, type2);
        }
        else {
            if (type instanceof GenericArrayType) {
                return Array.newInstance(getRawType(((GenericArrayType)type).getGenericComponentType(), type2), 0).getClass();
            }
            if (type instanceof WildcardType) {
                return null;
            }
            throw new IllegalArgumentException("unknown type: " + type);
        }
    }
    
    public static boolean isArrayType(final Type type) {
        return type instanceof GenericArrayType || (type instanceof Class && ((Class)type).isArray());
    }
    
    public static Type getArrayComponentType(final Type type) {
        if (type instanceof Class) {
            final Class clazz = (Class)type;
            return clazz.isArray() ? clazz.getComponentType() : null;
        }
        if (type instanceof GenericArrayType) {
            return ((GenericArrayType)type).getGenericComponentType();
        }
        return null;
    }
    
    public static Type unrollVariables(Map emptyMap, final Type type) {
        if (emptyMap == null) {
            emptyMap = Collections.emptyMap();
        }
        if (type != 0) {
            if (type instanceof TypeVariable) {
                return unrollVariables(emptyMap, emptyMap.get(type));
            }
            if (type instanceof ParameterizedType) {
                final ParameterizedType parameterizedType = (ParameterizedType)type;
                Map<? extends K, Type> map;
                if (parameterizedType.getOwnerType() == null) {
                    map = emptyMap;
                }
                else {
                    map = (Map<? extends K, Type>)new HashMap<Object, Type>(emptyMap);
                    map.putAll(getTypeArguments(parameterizedType));
                }
                final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                while (0 < actualTypeArguments.length) {
                    final Type unrollVariables = unrollVariables(map, actualTypeArguments[0]);
                    if (unrollVariables != null) {
                        actualTypeArguments[0] = unrollVariables;
                    }
                    int n = 0;
                    ++n;
                }
                return parameterizeWithOwner(parameterizedType.getOwnerType(), (Class)parameterizedType.getRawType(), actualTypeArguments);
            }
            if (type instanceof WildcardType) {
                final WildcardType wildcardType = (WildcardType)type;
                return wildcardType().withUpperBounds(unrollBounds(emptyMap, wildcardType.getUpperBounds())).withLowerBounds(unrollBounds(emptyMap, wildcardType.getLowerBounds())).build();
            }
        }
        return type;
    }
    
    private static Type[] unrollBounds(final Map map, final Type[] array) {
        Type[] array2 = array;
        while (0 < array2.length) {
            final Type unrollVariables = unrollVariables(map, array2[0]);
            int n2 = 0;
            if (unrollVariables == null) {
                final Type[] array3 = array2;
                final int n = 0;
                --n2;
                array2 = (Type[])ArrayUtils.remove(array3, n);
            }
            else {
                array2[0] = unrollVariables;
            }
            ++n2;
        }
        return array2;
    }
    
    public static final ParameterizedType parameterize(final Class clazz, final Type... array) {
        return parameterizeWithOwner(null, clazz, array);
    }
    
    public static final ParameterizedType parameterize(final Class clazz, final Map map) {
        Validate.notNull(clazz, "raw class is null", new Object[0]);
        Validate.notNull(map, "typeArgMappings is null", new Object[0]);
        return parameterizeWithOwner(null, clazz, extractTypeArgumentsFrom(map, clazz.getTypeParameters()));
    }
    
    public static final ParameterizedType parameterizeWithOwner(final Type type, final Class clazz, final Type... array) {
        Validate.notNull(clazz, "raw class is null", new Object[0]);
        Type enclosingClass;
        if (clazz.getEnclosingClass() == null) {
            Validate.isTrue(type == null, "no owner allowed for top-level %s", clazz);
            enclosingClass = null;
        }
        else if (type == null) {
            enclosingClass = clazz.getEnclosingClass();
        }
        else {
            Validate.isTrue(isAssignable(type, (Class)clazz.getEnclosingClass()), "%s is invalid owner type for parameterized %s", type, clazz);
            enclosingClass = type;
        }
        Validate.noNullElements(array, "null type argument at index %s", new Object[0]);
        Validate.isTrue(clazz.getTypeParameters().length == array.length, "invalid number of type parameters specified: expected %s, got %s", clazz.getTypeParameters().length, array.length);
        return new ParameterizedTypeImpl(clazz, enclosingClass, array, null);
    }
    
    public static final ParameterizedType parameterizeWithOwner(final Type type, final Class clazz, final Map map) {
        Validate.notNull(clazz, "raw class is null", new Object[0]);
        Validate.notNull(map, "typeArgMappings is null", new Object[0]);
        return parameterizeWithOwner(type, clazz, extractTypeArgumentsFrom(map, clazz.getTypeParameters()));
    }
    
    private static Type[] extractTypeArgumentsFrom(final Map map, final TypeVariable[] array) {
        final Type[] array2 = new Type[array.length];
        while (0 < array.length) {
            final TypeVariable typeVariable = array[0];
            Validate.isTrue(map.containsKey(typeVariable), "missing argument mapping for %s", toString(typeVariable));
            final Type[] array3 = array2;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array3[n] = map.get(typeVariable);
            int n3 = 0;
            ++n3;
        }
        return array2;
    }
    
    public static WildcardTypeBuilder wildcardType() {
        return new WildcardTypeBuilder(null);
    }
    
    public static GenericArrayType genericArrayType(final Type type) {
        return new GenericArrayTypeImpl((Type)Validate.notNull(type, "componentType is null", new Object[0]), null);
    }
    
    private static boolean equals(final ParameterizedType p0, final Type p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: instanceof      Ljava/lang/reflect/ParameterizedType;
        //     4: ifeq            58
        //     7: aload_1        
        //     8: checkcast       Ljava/lang/reflect/ParameterizedType;
        //    11: astore_2       
        //    12: aload_0        
        //    13: invokeinterface java/lang/reflect/ParameterizedType.getRawType:()Ljava/lang/reflect/Type;
        //    18: aload_2        
        //    19: invokeinterface java/lang/reflect/ParameterizedType.getRawType:()Ljava/lang/reflect/Type;
        //    24: ifeq            58
        //    27: aload_0        
        //    28: invokeinterface java/lang/reflect/ParameterizedType.getOwnerType:()Ljava/lang/reflect/Type;
        //    33: aload_2        
        //    34: invokeinterface java/lang/reflect/ParameterizedType.getOwnerType:()Ljava/lang/reflect/Type;
        //    39: ifeq            58
        //    42: aload_0        
        //    43: invokeinterface java/lang/reflect/ParameterizedType.getActualTypeArguments:()[Ljava/lang/reflect/Type;
        //    48: aload_2        
        //    49: invokeinterface java/lang/reflect/ParameterizedType.getActualTypeArguments:()[Ljava/lang/reflect/Type;
        //    54: invokestatic    org/apache/commons/lang3/reflect/TypeUtils.equals:([Ljava/lang/reflect/Type;[Ljava/lang/reflect/Type;)Z
        //    57: ireturn        
        //    58: iconst_0       
        //    59: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0058 (coming from #0024).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static boolean equals(final GenericArrayType p0, final Type p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: instanceof      Ljava/lang/reflect/GenericArrayType;
        //     4: ifeq            29
        //     7: aload_0        
        //     8: invokeinterface java/lang/reflect/GenericArrayType.getGenericComponentType:()Ljava/lang/reflect/Type;
        //    13: aload_1        
        //    14: checkcast       Ljava/lang/reflect/GenericArrayType;
        //    17: invokeinterface java/lang/reflect/GenericArrayType.getGenericComponentType:()Ljava/lang/reflect/Type;
        //    22: ifeq            29
        //    25: iconst_1       
        //    26: goto            30
        //    29: iconst_0       
        //    30: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0029 (coming from #0022).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static boolean equals(final WildcardType wildcardType, final Type type) {
        if (type instanceof WildcardType) {
            final WildcardType wildcardType2 = (WildcardType)type;
            return wildcardType.getLowerBounds() == wildcardType2.getLowerBounds() && getImplicitUpperBounds(wildcardType) == getImplicitUpperBounds(wildcardType2);
        }
        return true;
    }
    
    public static String toString(final Type type) {
        Validate.notNull(type);
        if (type instanceof Class) {
            return classToString((Class)type);
        }
        if (type instanceof ParameterizedType) {
            return parameterizedTypeToString((ParameterizedType)type);
        }
        if (type instanceof WildcardType) {
            return wildcardTypeToString((WildcardType)type);
        }
        if (type instanceof TypeVariable) {
            return typeVariableToString((TypeVariable)type);
        }
        if (type instanceof GenericArrayType) {
            return genericArrayTypeToString((GenericArrayType)type);
        }
        throw new IllegalArgumentException(ObjectUtils.identityToString(type));
    }
    
    public static String toLongString(final TypeVariable typeVariable) {
        Validate.notNull(typeVariable, "var is null", new Object[0]);
        final StringBuilder sb = new StringBuilder();
        final Class<?> genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            Class<?> enclosingClass;
            for (enclosingClass = genericDeclaration; enclosingClass.getEnclosingClass() != null; enclosingClass = enclosingClass.getEnclosingClass()) {
                sb.insert(0, enclosingClass.getSimpleName()).insert(0, '.');
            }
            sb.insert(0, enclosingClass.getName());
        }
        else if (genericDeclaration instanceof Type) {
            sb.append(toString(genericDeclaration));
        }
        else {
            sb.append(genericDeclaration);
        }
        return sb.append(':').append(typeVariableToString(typeVariable)).toString();
    }
    
    public static Typed wrap(final Type type) {
        return new Typed(type) {
            final Type val$type;
            
            @Override
            public Type getType() {
                return this.val$type;
            }
        };
    }
    
    public static Typed wrap(final Class clazz) {
        return wrap((Type)clazz);
    }
    
    private static String classToString(final Class clazz) {
        final StringBuilder sb = new StringBuilder();
        if (clazz.getEnclosingClass() != null) {
            sb.append(classToString(clazz.getEnclosingClass())).append('.').append(clazz.getSimpleName());
        }
        else {
            sb.append(clazz.getName());
        }
        if (clazz.getTypeParameters().length > 0) {
            sb.append('<');
            appendAllTo(sb, ", ", (Type[])clazz.getTypeParameters());
            sb.append('>');
        }
        return sb.toString();
    }
    
    private static String typeVariableToString(final TypeVariable typeVariable) {
        final StringBuilder sb = new StringBuilder(typeVariable.getName());
        final Type[] bounds = typeVariable.getBounds();
        if (bounds.length > 0 && (bounds.length != 1 || !Object.class.equals(bounds[0]))) {
            sb.append(" extends ");
            appendAllTo(sb, " & ", typeVariable.getBounds());
        }
        return sb.toString();
    }
    
    private static String parameterizedTypeToString(final ParameterizedType parameterizedType) {
        final StringBuilder sb = new StringBuilder();
        final Type ownerType = parameterizedType.getOwnerType();
        final Class clazz = (Class)parameterizedType.getRawType();
        final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (ownerType == null) {
            sb.append(clazz.getName());
        }
        else {
            if (ownerType instanceof Class) {
                sb.append(((Class)ownerType).getName());
            }
            else {
                sb.append(ownerType.toString());
            }
            sb.append('.').append(clazz.getSimpleName());
        }
        appendAllTo(sb.append('<'), ", ", actualTypeArguments).append('>');
        return sb.toString();
    }
    
    private static String wildcardTypeToString(final WildcardType wildcardType) {
        final StringBuilder append = new StringBuilder().append('?');
        final Type[] lowerBounds = wildcardType.getLowerBounds();
        final Type[] upperBounds = wildcardType.getUpperBounds();
        if (lowerBounds.length > 0) {
            appendAllTo(append.append(" super "), " & ", lowerBounds);
        }
        else if (upperBounds.length != 1 || !Object.class.equals(upperBounds[0])) {
            appendAllTo(append.append(" extends "), " & ", upperBounds);
        }
        return append.toString();
    }
    
    private static String genericArrayTypeToString(final GenericArrayType genericArrayType) {
        return String.format("%s[]", toString(genericArrayType.getGenericComponentType()));
    }
    
    private static StringBuilder appendAllTo(final StringBuilder sb, final String s, final Type... array) {
        Validate.notEmpty(Validate.noNullElements(array));
        if (array.length > 0) {
            sb.append(toString(array[0]));
            while (1 < array.length) {
                sb.append(s).append(toString(array[1]));
                int n = 0;
                ++n;
            }
        }
        return sb;
    }
    
    static boolean access$100(final GenericArrayType genericArrayType, final Type type) {
        return equals(genericArrayType, type);
    }
    
    static boolean access$200(final ParameterizedType parameterizedType, final Type type) {
        return equals(parameterizedType, type);
    }
    
    static boolean access$300(final WildcardType wildcardType, final Type type) {
        return equals(wildcardType, type);
    }
    
    static {
        WILDCARD_ALL = wildcardType().withUpperBounds(Object.class).build();
    }
    
    private static final class WildcardTypeImpl implements WildcardType
    {
        private static final Type[] EMPTY_BOUNDS;
        private final Type[] upperBounds;
        private final Type[] lowerBounds;
        
        private WildcardTypeImpl(final Type[] array, final Type[] array2) {
            this.upperBounds = (Type[])ObjectUtils.defaultIfNull(array, WildcardTypeImpl.EMPTY_BOUNDS);
            this.lowerBounds = (Type[])ObjectUtils.defaultIfNull(array2, WildcardTypeImpl.EMPTY_BOUNDS);
        }
        
        @Override
        public Type[] getUpperBounds() {
            return this.upperBounds.clone();
        }
        
        @Override
        public Type[] getLowerBounds() {
            return this.lowerBounds.clone();
        }
        
        @Override
        public String toString() {
            return TypeUtils.toString(this);
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || (o instanceof WildcardType && TypeUtils.access$300(this, (Type)o));
        }
        
        @Override
        public int hashCode() {
            final int n = 0x4900 | Arrays.hashCode(this.upperBounds);
            final int n2 = 0x4900 | Arrays.hashCode(this.lowerBounds);
            return 18688;
        }
        
        WildcardTypeImpl(final Type[] array, final Type[] array2, final TypeUtils$1 typed) {
            this(array, array2);
        }
        
        static {
            EMPTY_BOUNDS = new Type[0];
        }
    }
    
    private static final class ParameterizedTypeImpl implements ParameterizedType
    {
        private final Class raw;
        private final Type useOwner;
        private final Type[] typeArguments;
        
        private ParameterizedTypeImpl(final Class raw, final Type useOwner, final Type[] typeArguments) {
            this.raw = raw;
            this.useOwner = useOwner;
            this.typeArguments = typeArguments;
        }
        
        @Override
        public Type getRawType() {
            return this.raw;
        }
        
        @Override
        public Type getOwnerType() {
            return this.useOwner;
        }
        
        @Override
        public Type[] getActualTypeArguments() {
            return this.typeArguments.clone();
        }
        
        @Override
        public String toString() {
            return TypeUtils.toString(this);
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || (o instanceof ParameterizedType && TypeUtils.access$200(this, (Type)o));
        }
        
        @Override
        public int hashCode() {
            final int n = 0x470 | this.raw.hashCode();
            final int n2 = 0x470 | ObjectUtils.hashCode(this.useOwner);
            final int n3 = 0x470 | Arrays.hashCode(this.typeArguments);
            return 1136;
        }
        
        ParameterizedTypeImpl(final Class clazz, final Type type, final Type[] array, final TypeUtils$1 typed) {
            this(clazz, type, array);
        }
    }
    
    private static final class GenericArrayTypeImpl implements GenericArrayType
    {
        private final Type componentType;
        
        private GenericArrayTypeImpl(final Type componentType) {
            this.componentType = componentType;
        }
        
        @Override
        public Type getGenericComponentType() {
            return this.componentType;
        }
        
        @Override
        public String toString() {
            return TypeUtils.toString(this);
        }
        
        @Override
        public boolean equals(final Object o) {
            return o == this || (o instanceof GenericArrayType && TypeUtils.access$100(this, (Type)o));
        }
        
        @Override
        public int hashCode() {
            final int n = 0x430 | this.componentType.hashCode();
            return 1072;
        }
        
        GenericArrayTypeImpl(final Type type, final TypeUtils$1 typed) {
            this(type);
        }
    }
    
    public static class WildcardTypeBuilder implements Builder
    {
        private Type[] upperBounds;
        private Type[] lowerBounds;
        
        private WildcardTypeBuilder() {
        }
        
        public WildcardTypeBuilder withUpperBounds(final Type... upperBounds) {
            this.upperBounds = upperBounds;
            return this;
        }
        
        public WildcardTypeBuilder withLowerBounds(final Type... lowerBounds) {
            this.lowerBounds = lowerBounds;
            return this;
        }
        
        @Override
        public WildcardType build() {
            return new WildcardTypeImpl(this.upperBounds, this.lowerBounds, null);
        }
        
        @Override
        public Object build() {
            return this.build();
        }
        
        WildcardTypeBuilder(final TypeUtils$1 typed) {
            this();
        }
    }
}
