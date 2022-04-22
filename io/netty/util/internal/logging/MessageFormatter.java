package io.netty.util.internal.logging;

import java.util.*;

final class MessageFormatter
{
    static final char DELIM_START = '{';
    static final char DELIM_STOP = '}';
    static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';
    
    static FormattingTuple format(final String s, final Object o) {
        return arrayFormat(s, new Object[] { o });
    }
    
    static FormattingTuple format(final String s, final Object o, final Object o2) {
        return arrayFormat(s, new Object[] { o, o2 });
    }
    
    static Throwable getThrowableCandidate(final Object[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        final Object o = array[array.length - 1];
        if (o instanceof Throwable) {
            return (Throwable)o;
        }
        return null;
    }
    
    static FormattingTuple arrayFormat(final String p0, final Object[] p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    io/netty/util/internal/logging/MessageFormatter.getThrowableCandidate:([Ljava/lang/Object;)Ljava/lang/Throwable;
        //     4: astore_2       
        //     5: aload_0        
        //     6: ifnonnull       20
        //     9: new             Lio/netty/util/internal/logging/FormattingTuple;
        //    12: dup            
        //    13: aconst_null    
        //    14: aload_1        
        //    15: aload_2        
        //    16: invokespecial   io/netty/util/internal/logging/FormattingTuple.<init>:(Ljava/lang/String;[Ljava/lang/Object;Ljava/lang/Throwable;)V
        //    19: areturn        
        //    20: aload_1        
        //    21: ifnonnull       33
        //    24: new             Lio/netty/util/internal/logging/FormattingTuple;
        //    27: dup            
        //    28: aload_0        
        //    29: invokespecial   io/netty/util/internal/logging/FormattingTuple.<init>:(Ljava/lang/String;)V
        //    32: areturn        
        //    33: new             Ljava/lang/StringBuffer;
        //    36: dup            
        //    37: aload_0        
        //    38: invokevirtual   java/lang/String.length:()I
        //    41: bipush          50
        //    43: iadd           
        //    44: invokespecial   java/lang/StringBuffer.<init>:(I)V
        //    47: astore          5
        //    49: iconst_0       
        //    50: aload_1        
        //    51: arraylength    
        //    52: if_icmpge       234
        //    55: aload_0        
        //    56: ldc             "{}"
        //    58: iconst_0       
        //    59: invokevirtual   java/lang/String.indexOf:(Ljava/lang/String;I)I
        //    62: istore          4
        //    64: iload           4
        //    66: iconst_m1      
        //    67: if_icmpne       111
        //    70: new             Lio/netty/util/internal/logging/FormattingTuple;
        //    73: dup            
        //    74: aload_0        
        //    75: aload_1        
        //    76: aload_2        
        //    77: invokespecial   io/netty/util/internal/logging/FormattingTuple.<init>:(Ljava/lang/String;[Ljava/lang/Object;Ljava/lang/Throwable;)V
        //    80: areturn        
        //    81: aload           5
        //    83: aload_0        
        //    84: iconst_0       
        //    85: aload_0        
        //    86: invokevirtual   java/lang/String.length:()I
        //    89: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //    92: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    95: pop            
        //    96: new             Lio/netty/util/internal/logging/FormattingTuple;
        //    99: dup            
        //   100: aload           5
        //   102: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   105: aload_1        
        //   106: aload_2        
        //   107: invokespecial   io/netty/util/internal/logging/FormattingTuple.<init>:(Ljava/lang/String;[Ljava/lang/Object;Ljava/lang/Throwable;)V
        //   110: areturn        
        //   111: aload_0        
        //   112: iload           4
        //   114: ifne            195
        //   117: aload_0        
        //   118: iload           4
        //   120: if_icmplt       157
        //   123: iinc            6, -1
        //   126: aload           5
        //   128: aload_0        
        //   129: iconst_0       
        //   130: iload           4
        //   132: iconst_1       
        //   133: isub           
        //   134: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   137: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   140: pop            
        //   141: aload           5
        //   143: bipush          123
        //   145: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   148: pop            
        //   149: iload           4
        //   151: iconst_1       
        //   152: iadd           
        //   153: istore_3       
        //   154: goto            228
        //   157: aload           5
        //   159: aload_0        
        //   160: iconst_0       
        //   161: iload           4
        //   163: iconst_1       
        //   164: isub           
        //   165: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   168: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   171: pop            
        //   172: aload           5
        //   174: aload_1        
        //   175: iconst_0       
        //   176: aaload         
        //   177: new             Ljava/util/HashMap;
        //   180: dup            
        //   181: invokespecial   java/util/HashMap.<init>:()V
        //   184: invokestatic    io/netty/util/internal/logging/MessageFormatter.deeplyAppendParameter:(Ljava/lang/StringBuffer;Ljava/lang/Object;Ljava/util/Map;)V
        //   187: iload           4
        //   189: iconst_2       
        //   190: iadd           
        //   191: istore_3       
        //   192: goto            228
        //   195: aload           5
        //   197: aload_0        
        //   198: iconst_0       
        //   199: iload           4
        //   201: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   204: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   207: pop            
        //   208: aload           5
        //   210: aload_1        
        //   211: iconst_0       
        //   212: aaload         
        //   213: new             Ljava/util/HashMap;
        //   216: dup            
        //   217: invokespecial   java/util/HashMap.<init>:()V
        //   220: invokestatic    io/netty/util/internal/logging/MessageFormatter.deeplyAppendParameter:(Ljava/lang/StringBuffer;Ljava/lang/Object;Ljava/util/Map;)V
        //   223: iload           4
        //   225: iconst_2       
        //   226: iadd           
        //   227: istore_3       
        //   228: iinc            6, 1
        //   231: goto            49
        //   234: aload           5
        //   236: aload_0        
        //   237: iconst_0       
        //   238: aload_0        
        //   239: invokevirtual   java/lang/String.length:()I
        //   242: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   245: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   248: pop            
        //   249: iconst_0       
        //   250: aload_1        
        //   251: arraylength    
        //   252: iconst_1       
        //   253: isub           
        //   254: if_icmpge       272
        //   257: new             Lio/netty/util/internal/logging/FormattingTuple;
        //   260: dup            
        //   261: aload           5
        //   263: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   266: aload_1        
        //   267: aload_2        
        //   268: invokespecial   io/netty/util/internal/logging/FormattingTuple.<init>:(Ljava/lang/String;[Ljava/lang/Object;Ljava/lang/Throwable;)V
        //   271: areturn        
        //   272: new             Lio/netty/util/internal/logging/FormattingTuple;
        //   275: dup            
        //   276: aload           5
        //   278: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   281: aload_1        
        //   282: aconst_null    
        //   283: invokespecial   io/netty/util/internal/logging/FormattingTuple.<init>:(Ljava/lang/String;[Ljava/lang/Object;Ljava/lang/Throwable;)V
        //   286: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0049 (coming from #0231).
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
    
    private static void deeplyAppendParameter(final StringBuffer sb, final Object o, final Map map) {
        if (o == null) {
            sb.append("null");
            return;
        }
        if (!o.getClass().isArray()) {
            safeObjectAppend(sb, o);
        }
        else if (o instanceof boolean[]) {
            booleanArrayAppend(sb, (boolean[])o);
        }
        else if (o instanceof byte[]) {
            byteArrayAppend(sb, (byte[])o);
        }
        else if (o instanceof char[]) {
            charArrayAppend(sb, (char[])o);
        }
        else if (o instanceof short[]) {
            shortArrayAppend(sb, (short[])o);
        }
        else if (o instanceof int[]) {
            intArrayAppend(sb, (int[])o);
        }
        else if (o instanceof long[]) {
            longArrayAppend(sb, (long[])o);
        }
        else if (o instanceof float[]) {
            floatArrayAppend(sb, (float[])o);
        }
        else if (o instanceof double[]) {
            doubleArrayAppend(sb, (double[])o);
        }
        else {
            objectArrayAppend(sb, (Object[])o, map);
        }
    }
    
    private static void safeObjectAppend(final StringBuffer sb, final Object o) {
        sb.append(o.toString());
    }
    
    private static void objectArrayAppend(final StringBuffer sb, final Object[] array, final Map map) {
        sb.append('[');
        if (!map.containsKey(array)) {
            map.put(array, null);
            final int length = array.length;
            while (0 < length) {
                deeplyAppendParameter(sb, array[0], map);
                if (0 != length - 1) {
                    sb.append(", ");
                }
                int n = 0;
                ++n;
            }
            map.remove(array);
        }
        else {
            sb.append("...");
        }
        sb.append(']');
    }
    
    private static void booleanArrayAppend(final StringBuffer sb, final boolean[] array) {
        sb.append('[');
        final int length = array.length;
        while (0 < length) {
            sb.append(array[0]);
            if (0 != length - 1) {
                sb.append(", ");
            }
            int n = 0;
            ++n;
        }
        sb.append(']');
    }
    
    private static void byteArrayAppend(final StringBuffer sb, final byte[] array) {
        sb.append('[');
        final int length = array.length;
        while (0 < length) {
            sb.append(array[0]);
            if (0 != length - 1) {
                sb.append(", ");
            }
            int n = 0;
            ++n;
        }
        sb.append(']');
    }
    
    private static void charArrayAppend(final StringBuffer sb, final char[] array) {
        sb.append('[');
        final int length = array.length;
        while (0 < length) {
            sb.append(array[0]);
            if (0 != length - 1) {
                sb.append(", ");
            }
            int n = 0;
            ++n;
        }
        sb.append(']');
    }
    
    private static void shortArrayAppend(final StringBuffer sb, final short[] array) {
        sb.append('[');
        final int length = array.length;
        while (0 < length) {
            sb.append(array[0]);
            if (0 != length - 1) {
                sb.append(", ");
            }
            int n = 0;
            ++n;
        }
        sb.append(']');
    }
    
    private static void intArrayAppend(final StringBuffer sb, final int[] array) {
        sb.append('[');
        final int length = array.length;
        while (0 < length) {
            sb.append(array[0]);
            if (0 != length - 1) {
                sb.append(", ");
            }
            int n = 0;
            ++n;
        }
        sb.append(']');
    }
    
    private static void longArrayAppend(final StringBuffer sb, final long[] array) {
        sb.append('[');
        final int length = array.length;
        while (0 < length) {
            sb.append(array[0]);
            if (0 != length - 1) {
                sb.append(", ");
            }
            int n = 0;
            ++n;
        }
        sb.append(']');
    }
    
    private static void floatArrayAppend(final StringBuffer sb, final float[] array) {
        sb.append('[');
        final int length = array.length;
        while (0 < length) {
            sb.append(array[0]);
            if (0 != length - 1) {
                sb.append(", ");
            }
            int n = 0;
            ++n;
        }
        sb.append(']');
    }
    
    private static void doubleArrayAppend(final StringBuffer sb, final double[] array) {
        sb.append('[');
        final int length = array.length;
        while (0 < length) {
            sb.append(array[0]);
            if (0 != length - 1) {
                sb.append(", ");
            }
            int n = 0;
            ++n;
        }
        sb.append(']');
    }
    
    private MessageFormatter() {
    }
}
