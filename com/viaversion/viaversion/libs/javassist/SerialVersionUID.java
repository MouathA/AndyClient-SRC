package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class SerialVersionUID
{
    public static void setSerialVersionUID(final CtClass ctClass) throws CannotCompileException, NotFoundException {
        ctClass.getDeclaredField("serialVersionUID");
    }
    
    private static boolean isSerializable(final CtClass ctClass) throws NotFoundException {
        return ctClass.subtypeOf(ctClass.getClassPool().get("java.io.Serializable"));
    }
    
    public static long calculateDefault(final CtClass p0) throws CannotCompileException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/io/ByteArrayOutputStream.<init>:()V
        //     7: astore_1       
        //     8: new             Ljava/io/DataOutputStream;
        //    11: dup            
        //    12: aload_1        
        //    13: invokespecial   java/io/DataOutputStream.<init>:(Ljava/io/OutputStream;)V
        //    16: astore_2       
        //    17: aload_0        
        //    18: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClass.getClassFile:()Lcom/viaversion/viaversion/libs/javassist/bytecode/ClassFile;
        //    21: astore_3       
        //    22: aload_0        
        //    23: invokestatic    com/viaversion/viaversion/libs/javassist/SerialVersionUID.javaName:(Lcom/viaversion/viaversion/libs/javassist/CtClass;)Ljava/lang/String;
        //    26: astore          4
        //    28: aload_2        
        //    29: aload           4
        //    31: invokevirtual   java/io/DataOutputStream.writeUTF:(Ljava/lang/String;)V
        //    34: aload_0        
        //    35: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClass.getDeclaredMethods:()[Lcom/viaversion/viaversion/libs/javassist/CtMethod;
        //    38: astore          5
        //    40: aload_0        
        //    41: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClass.getModifiers:()I
        //    44: istore          6
        //    46: iload           6
        //    48: sipush          512
        //    51: iand           
        //    52: ifeq            80
        //    55: aload           5
        //    57: arraylength    
        //    58: ifle            72
        //    61: iload           6
        //    63: sipush          1024
        //    66: ior            
        //    67: istore          6
        //    69: goto            80
        //    72: iload           6
        //    74: sipush          -1025
        //    77: iand           
        //    78: istore          6
        //    80: aload_2        
        //    81: iload           6
        //    83: invokevirtual   java/io/DataOutputStream.writeInt:(I)V
        //    86: aload_3        
        //    87: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/ClassFile.getInterfaces:()[Ljava/lang/String;
        //    90: astore          7
        //    92: iconst_0       
        //    93: aload           7
        //    95: arraylength    
        //    96: if_icmpge       116
        //    99: aload           7
        //   101: iconst_0       
        //   102: aload           7
        //   104: iconst_0       
        //   105: aaload         
        //   106: invokestatic    com/viaversion/viaversion/libs/javassist/SerialVersionUID.javaName:(Ljava/lang/String;)Ljava/lang/String;
        //   109: aastore        
        //   110: iinc            8, 1
        //   113: goto            92
        //   116: aload           7
        //   118: invokestatic    java/util/Arrays.sort:([Ljava/lang/Object;)V
        //   121: iconst_0       
        //   122: aload           7
        //   124: arraylength    
        //   125: if_icmpge       142
        //   128: aload_2        
        //   129: aload           7
        //   131: iconst_0       
        //   132: aaload         
        //   133: invokevirtual   java/io/DataOutputStream.writeUTF:(Ljava/lang/String;)V
        //   136: iinc            8, 1
        //   139: goto            121
        //   142: aload_0        
        //   143: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClass.getDeclaredFields:()[Lcom/viaversion/viaversion/libs/javassist/CtField;
        //   146: astore          8
        //   148: aload           8
        //   150: new             Lcom/viaversion/viaversion/libs/javassist/SerialVersionUID$1;
        //   153: dup            
        //   154: invokespecial   com/viaversion/viaversion/libs/javassist/SerialVersionUID$1.<init>:()V
        //   157: invokestatic    java/util/Arrays.sort:([Ljava/lang/Object;Ljava/util/Comparator;)V
        //   160: iconst_0       
        //   161: aload           8
        //   163: arraylength    
        //   164: if_icmpge       229
        //   167: aload           8
        //   169: iconst_0       
        //   170: aaload         
        //   171: astore          10
        //   173: aload           10
        //   175: invokevirtual   com/viaversion/viaversion/libs/javassist/CtField.getModifiers:()I
        //   178: istore          11
        //   180: iload           11
        //   182: iconst_2       
        //   183: iand           
        //   184: ifeq            196
        //   187: iload           11
        //   189: sipush          136
        //   192: iand           
        //   193: ifne            223
        //   196: aload_2        
        //   197: aload           10
        //   199: invokevirtual   com/viaversion/viaversion/libs/javassist/CtField.getName:()Ljava/lang/String;
        //   202: invokevirtual   java/io/DataOutputStream.writeUTF:(Ljava/lang/String;)V
        //   205: aload_2        
        //   206: iload           11
        //   208: invokevirtual   java/io/DataOutputStream.writeInt:(I)V
        //   211: aload_2        
        //   212: aload           10
        //   214: invokevirtual   com/viaversion/viaversion/libs/javassist/CtField.getFieldInfo2:()Lcom/viaversion/viaversion/libs/javassist/bytecode/FieldInfo;
        //   217: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/FieldInfo.getDescriptor:()Ljava/lang/String;
        //   220: invokevirtual   java/io/DataOutputStream.writeUTF:(Ljava/lang/String;)V
        //   223: iinc            9, 1
        //   226: goto            160
        //   229: aload_3        
        //   230: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/ClassFile.getStaticInitializer:()Lcom/viaversion/viaversion/libs/javassist/bytecode/MethodInfo;
        //   233: ifnull          254
        //   236: aload_2        
        //   237: ldc             "<clinit>"
        //   239: invokevirtual   java/io/DataOutputStream.writeUTF:(Ljava/lang/String;)V
        //   242: aload_2        
        //   243: bipush          8
        //   245: invokevirtual   java/io/DataOutputStream.writeInt:(I)V
        //   248: aload_2        
        //   249: ldc             "()V"
        //   251: invokevirtual   java/io/DataOutputStream.writeUTF:(Ljava/lang/String;)V
        //   254: aload_0        
        //   255: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClass.getDeclaredConstructors:()[Lcom/viaversion/viaversion/libs/javassist/CtConstructor;
        //   258: astore          9
        //   260: aload           9
        //   262: new             Lcom/viaversion/viaversion/libs/javassist/SerialVersionUID$2;
        //   265: dup            
        //   266: invokespecial   com/viaversion/viaversion/libs/javassist/SerialVersionUID$2.<init>:()V
        //   269: invokestatic    java/util/Arrays.sort:([Ljava/lang/Object;Ljava/util/Comparator;)V
        //   272: iconst_0       
        //   273: aload           9
        //   275: arraylength    
        //   276: if_icmpge       336
        //   279: aload           9
        //   281: iconst_0       
        //   282: aaload         
        //   283: astore          11
        //   285: aload           11
        //   287: invokevirtual   com/viaversion/viaversion/libs/javassist/CtConstructor.getModifiers:()I
        //   290: istore          12
        //   292: iload           12
        //   294: iconst_2       
        //   295: iand           
        //   296: ifne            330
        //   299: aload_2        
        //   300: ldc             "<init>"
        //   302: invokevirtual   java/io/DataOutputStream.writeUTF:(Ljava/lang/String;)V
        //   305: aload_2        
        //   306: iload           12
        //   308: invokevirtual   java/io/DataOutputStream.writeInt:(I)V
        //   311: aload_2        
        //   312: aload           11
        //   314: invokevirtual   com/viaversion/viaversion/libs/javassist/CtConstructor.getMethodInfo2:()Lcom/viaversion/viaversion/libs/javassist/bytecode/MethodInfo;
        //   317: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/MethodInfo.getDescriptor:()Ljava/lang/String;
        //   320: bipush          47
        //   322: bipush          46
        //   324: invokevirtual   java/lang/String.replace:(CC)Ljava/lang/String;
        //   327: invokevirtual   java/io/DataOutputStream.writeUTF:(Ljava/lang/String;)V
        //   330: iinc            10, 1
        //   333: goto            272
        //   336: aload           5
        //   338: new             Lcom/viaversion/viaversion/libs/javassist/SerialVersionUID$3;
        //   341: dup            
        //   342: invokespecial   com/viaversion/viaversion/libs/javassist/SerialVersionUID$3.<init>:()V
        //   345: invokestatic    java/util/Arrays.sort:([Ljava/lang/Object;Ljava/util/Comparator;)V
        //   348: iconst_0       
        //   349: aload           5
        //   351: arraylength    
        //   352: if_icmpge       419
        //   355: aload           5
        //   357: iconst_0       
        //   358: aaload         
        //   359: astore          11
        //   361: aload           11
        //   363: invokevirtual   com/viaversion/viaversion/libs/javassist/CtMethod.getModifiers:()I
        //   366: sipush          3391
        //   369: iand           
        //   370: istore          12
        //   372: iload           12
        //   374: iconst_2       
        //   375: iand           
        //   376: ifne            413
        //   379: aload_2        
        //   380: aload           11
        //   382: invokevirtual   com/viaversion/viaversion/libs/javassist/CtMethod.getName:()Ljava/lang/String;
        //   385: invokevirtual   java/io/DataOutputStream.writeUTF:(Ljava/lang/String;)V
        //   388: aload_2        
        //   389: iload           12
        //   391: invokevirtual   java/io/DataOutputStream.writeInt:(I)V
        //   394: aload_2        
        //   395: aload           11
        //   397: invokevirtual   com/viaversion/viaversion/libs/javassist/CtMethod.getMethodInfo2:()Lcom/viaversion/viaversion/libs/javassist/bytecode/MethodInfo;
        //   400: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/MethodInfo.getDescriptor:()Ljava/lang/String;
        //   403: bipush          47
        //   405: bipush          46
        //   407: invokevirtual   java/lang/String.replace:(CC)Ljava/lang/String;
        //   410: invokevirtual   java/io/DataOutputStream.writeUTF:(Ljava/lang/String;)V
        //   413: iinc            10, 1
        //   416: goto            348
        //   419: aload_2        
        //   420: invokevirtual   java/io/DataOutputStream.flush:()V
        //   423: ldc             "SHA"
        //   425: invokestatic    java/security/MessageDigest.getInstance:(Ljava/lang/String;)Ljava/security/MessageDigest;
        //   428: astore          10
        //   430: aload           10
        //   432: aload_1        
        //   433: invokevirtual   java/io/ByteArrayOutputStream.toByteArray:()[B
        //   436: invokevirtual   java/security/MessageDigest.digest:([B)[B
        //   439: astore          11
        //   441: lconst_0       
        //   442: lstore          12
        //   444: aload           11
        //   446: arraylength    
        //   447: bipush          8
        //   449: invokestatic    java/lang/Math.min:(II)I
        //   452: iconst_1       
        //   453: isub           
        //   454: istore          14
        //   456: iload           14
        //   458: iflt            485
        //   461: lload           12
        //   463: bipush          8
        //   465: lshl           
        //   466: aload           11
        //   468: iload           14
        //   470: baload         
        //   471: sipush          255
        //   474: iand           
        //   475: i2l            
        //   476: lor            
        //   477: lstore          12
        //   479: iinc            14, -1
        //   482: goto            456
        //   485: lload           12
        //   487: lreturn        
        //   488: astore_1       
        //   489: new             Lcom/viaversion/viaversion/libs/javassist/CannotCompileException;
        //   492: dup            
        //   493: aload_1        
        //   494: invokespecial   com/viaversion/viaversion/libs/javassist/CannotCompileException.<init>:(Ljava/lang/Throwable;)V
        //   497: athrow         
        //   498: astore_1       
        //   499: new             Lcom/viaversion/viaversion/libs/javassist/CannotCompileException;
        //   502: dup            
        //   503: aload_1        
        //   504: invokespecial   com/viaversion/viaversion/libs/javassist/CannotCompileException.<init>:(Ljava/lang/Throwable;)V
        //   507: athrow         
        //    Exceptions:
        //  throws com.viaversion.viaversion.libs.javassist.CannotCompileException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static String javaName(final CtClass ctClass) {
        return Descriptor.toJavaName(Descriptor.toJvmName(ctClass));
    }
    
    private static String javaName(final String s) {
        return Descriptor.toJavaName(Descriptor.toJvmName(s));
    }
}
