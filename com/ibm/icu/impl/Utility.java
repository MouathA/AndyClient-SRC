package com.ibm.icu.impl;

import com.ibm.icu.lang.*;
import java.util.*;
import com.ibm.icu.text.*;
import java.util.regex.*;

public final class Utility
{
    private static final char APOSTROPHE = '\'';
    private static final char BACKSLASH = '\\';
    private static final int MAGIC_UNSIGNED = Integer.MIN_VALUE;
    private static final char ESCAPE = '\ua5a5';
    static final byte ESCAPE_BYTE = -91;
    public static String LINE_SEPARATOR;
    static final char[] HEX_DIGIT;
    private static final char[] UNESCAPE_MAP;
    static final char[] DIGITS;
    
    public static final boolean arrayEquals(final Object[] p0, final Object p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       14
        //     4: aload_1        
        //     5: ifnonnull       12
        //     8: iconst_1       
        //     9: goto            13
        //    12: iconst_0       
        //    13: ireturn        
        //    14: aload_1        
        //    15: instanceof      [Ljava/lang/Object;
        //    18: ifne            23
        //    21: iconst_0       
        //    22: ireturn        
        //    23: aload_1        
        //    24: checkcast       [Ljava/lang/Object;
        //    27: checkcast       [Ljava/lang/Object;
        //    30: astore_2       
        //    31: aload_0        
        //    32: arraylength    
        //    33: aload_2        
        //    34: arraylength    
        //    35: if_icmpne       51
        //    38: aload_0        
        //    39: iconst_0       
        //    40: aload_2        
        //    41: iconst_0       
        //    42: aload_0        
        //    43: arraylength    
        //    44: if_icmpge       51
        //    47: iconst_1       
        //    48: goto            52
        //    51: iconst_0       
        //    52: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0051 (coming from #0044).
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
    
    public static final boolean arrayEquals(final int[] p0, final Object p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       14
        //     4: aload_1        
        //     5: ifnonnull       12
        //     8: iconst_1       
        //     9: goto            13
        //    12: iconst_0       
        //    13: ireturn        
        //    14: aload_1        
        //    15: instanceof      [I
        //    18: ifne            23
        //    21: iconst_0       
        //    22: ireturn        
        //    23: aload_1        
        //    24: checkcast       [I
        //    27: checkcast       [I
        //    30: astore_2       
        //    31: aload_0        
        //    32: arraylength    
        //    33: aload_2        
        //    34: arraylength    
        //    35: if_icmpne       51
        //    38: aload_0        
        //    39: iconst_0       
        //    40: aload_2        
        //    41: iconst_0       
        //    42: aload_0        
        //    43: arraylength    
        //    44: if_icmpge       51
        //    47: iconst_1       
        //    48: goto            52
        //    51: iconst_0       
        //    52: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0051 (coming from #0044).
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
    
    public static final boolean arrayEquals(final double[] p0, final Object p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       14
        //     4: aload_1        
        //     5: ifnonnull       12
        //     8: iconst_1       
        //     9: goto            13
        //    12: iconst_0       
        //    13: ireturn        
        //    14: aload_1        
        //    15: instanceof      [D
        //    18: ifne            23
        //    21: iconst_0       
        //    22: ireturn        
        //    23: aload_1        
        //    24: checkcast       [D
        //    27: checkcast       [D
        //    30: astore_2       
        //    31: aload_0        
        //    32: arraylength    
        //    33: aload_2        
        //    34: arraylength    
        //    35: if_icmpne       51
        //    38: aload_0        
        //    39: iconst_0       
        //    40: aload_2        
        //    41: iconst_0       
        //    42: aload_0        
        //    43: arraylength    
        //    44: if_icmpge       51
        //    47: iconst_1       
        //    48: goto            52
        //    51: iconst_0       
        //    52: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0051 (coming from #0044).
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
    
    public static final boolean arrayEquals(final byte[] p0, final Object p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       14
        //     4: aload_1        
        //     5: ifnonnull       12
        //     8: iconst_1       
        //     9: goto            13
        //    12: iconst_0       
        //    13: ireturn        
        //    14: aload_1        
        //    15: instanceof      [B
        //    18: ifne            23
        //    21: iconst_0       
        //    22: ireturn        
        //    23: aload_1        
        //    24: checkcast       [B
        //    27: checkcast       [B
        //    30: astore_2       
        //    31: aload_0        
        //    32: arraylength    
        //    33: aload_2        
        //    34: arraylength    
        //    35: if_icmpne       51
        //    38: aload_0        
        //    39: iconst_0       
        //    40: aload_2        
        //    41: iconst_0       
        //    42: aload_0        
        //    43: arraylength    
        //    44: if_icmpge       51
        //    47: iconst_1       
        //    48: goto            52
        //    51: iconst_0       
        //    52: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0051 (coming from #0044).
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
    
    public static final boolean arrayRegionMatches(final char[] array, final int n, final char[] array2, final int n2, final int n3) {
        final int n4 = n + n3;
        final int n5 = n2 - n;
        for (int i = n; i < n4; ++i) {
            if (array[i] != array2[i + n5]) {
                return false;
            }
        }
        return true;
    }
    
    public static final boolean objectEquals(final Object o, final Object o2) {
        return (o == null) ? (o2 == null) : (o2 != null && o.equals(o2));
    }
    
    public static int checkCompare(final Comparable comparable, final Comparable comparable2) {
        return (comparable == null) ? ((comparable2 == null) ? 0 : -1) : ((comparable2 == null) ? 1 : comparable.compareTo(comparable2));
    }
    
    public static int checkHash(final Object o) {
        return (o == null) ? 0 : o.hashCode();
    }
    
    public static final String arrayToRLEString(final int[] array) {
        final StringBuilder sb = new StringBuilder();
        appendInt(sb, array.length);
        int n = array[0];
        while (1 < array.length) {
            final int n2 = array[1];
            if (n2 == n) {
                int n3 = 0;
                ++n3;
            }
            else {
                encodeRun(sb, n, 1);
                n = n2;
            }
            int n4 = 0;
            ++n4;
        }
        encodeRun(sb, n, 1);
        return sb.toString();
    }
    
    public static final String arrayToRLEString(final short[] array) {
        final StringBuilder sb = new StringBuilder();
        sb.append((char)(array.length >> 16));
        sb.append((char)array.length);
        short n = array[0];
        while (1 < array.length) {
            final short n2 = array[1];
            if (n2 == n) {
                int n3 = 0;
                ++n3;
            }
            else {
                encodeRun(sb, n, 1);
                n = n2;
            }
            int n4 = 0;
            ++n4;
        }
        encodeRun(sb, n, 1);
        return sb.toString();
    }
    
    public static final String arrayToRLEString(final char[] array) {
        final StringBuilder sb = new StringBuilder();
        sb.append((char)(array.length >> 16));
        sb.append((char)array.length);
        int n = array[0];
        while (1 < array.length) {
            final char c = array[1];
            if (c == n) {
                int n2 = 0;
                ++n2;
            }
            else {
                encodeRun(sb, (short)n, 1);
                n = c;
            }
            int n3 = 0;
            ++n3;
        }
        encodeRun(sb, (short)n, 1);
        return sb.toString();
    }
    
    public static final String arrayToRLEString(final byte[] array) {
        final StringBuilder sb = new StringBuilder();
        sb.append((char)(array.length >> 16));
        sb.append((char)array.length);
        byte b = array[0];
        final byte[] array2 = new byte[2];
        while (1 < array.length) {
            final byte b2 = array[1];
            if (b2 == b) {
                int n = 0;
                ++n;
            }
            else {
                encodeRun(sb, b, 1, array2);
                b = b2;
            }
            int n2 = 0;
            ++n2;
        }
        encodeRun(sb, b, 1, array2);
        if (array2[0] != 0) {
            appendEncodedByte(sb, (byte)0, array2);
        }
        return sb.toString();
    }
    
    private static final void encodeRun(final Appendable appendable, final int n, int n2) {
        if (n2 < 4) {
            while (0 < n2) {
                if (n == 42405) {
                    appendInt(appendable, n);
                }
                appendInt(appendable, n);
                int n3 = 0;
                ++n3;
            }
        }
        else {
            if (n2 == 42405) {
                if (n == 42405) {
                    appendInt(appendable, 42405);
                }
                appendInt(appendable, n);
                --n2;
            }
            appendInt(appendable, 42405);
            appendInt(appendable, n2);
            appendInt(appendable, n);
        }
    }
    
    private static final void appendInt(final Appendable appendable, final int n) {
        appendable.append((char)(n >>> 16));
        appendable.append((char)(n & 0xFFFF));
    }
    
    private static final void encodeRun(final Appendable appendable, final short n, int n2) {
        if (n2 < 4) {
            while (0 < n2) {
                if (n == 42405) {
                    appendable.append('\ua5a5');
                }
                appendable.append((char)n);
                int n3 = 0;
                ++n3;
            }
        }
        else {
            if (n2 == 42405) {
                if (n == 42405) {
                    appendable.append('\ua5a5');
                }
                appendable.append((char)n);
                --n2;
            }
            appendable.append('\ua5a5');
            appendable.append((char)n2);
            appendable.append((char)n);
        }
    }
    
    private static final void encodeRun(final Appendable appendable, final byte b, int n, final byte[] array) {
        if (n < 4) {
            while (0 < n) {
                if (b == -91) {
                    appendEncodedByte(appendable, (byte)(-91), array);
                }
                appendEncodedByte(appendable, b, array);
                int n2 = 0;
                ++n2;
            }
        }
        else {
            if (n == -91) {
                if (b == -91) {
                    appendEncodedByte(appendable, (byte)(-91), array);
                }
                appendEncodedByte(appendable, b, array);
                --n;
            }
            appendEncodedByte(appendable, (byte)(-91), array);
            appendEncodedByte(appendable, (byte)n, array);
            appendEncodedByte(appendable, b, array);
        }
    }
    
    private static final void appendEncodedByte(final Appendable appendable, final byte b, final byte[] array) {
        if (array[0] != 0) {
            appendable.append((char)(array[1] << 8 | (b & 0xFF)));
            array[0] = 0;
        }
        else {
            array[0] = 1;
            array[1] = b;
        }
    }
    
    public static final int[] RLEStringToIntArray(final String s) {
        final int int1 = getInt(s, 0);
        final int[] array = new int[int1];
        final int n = s.length() / 2;
        while (0 < int1 && 1 < n) {
            final int n2 = 1;
            int n3 = 0;
            ++n3;
            final int int2 = getInt(s, n2);
            if (int2 == 42405) {
                final int n4 = 1;
                ++n3;
                final int int3 = getInt(s, n4);
                if (int3 == 42405) {
                    final int[] array2 = array;
                    final int n5 = 0;
                    int n6 = 0;
                    ++n6;
                    array2[n5] = int3;
                }
                else {
                    final int n7 = int3;
                    final int n8 = 1;
                    ++n3;
                    final int int4 = getInt(s, n8);
                    while (0 < n7) {
                        final int[] array3 = array;
                        final int n9 = 0;
                        int n6 = 0;
                        ++n6;
                        array3[n9] = int4;
                        int n10 = 0;
                        ++n10;
                    }
                }
            }
            else {
                final int[] array4 = array;
                final int n11 = 0;
                int n6 = 0;
                ++n6;
                array4[n11] = int2;
            }
        }
        if (int1 || n == 0) {
            throw new IllegalStateException("Bad run-length encoded int array");
        }
        return array;
    }
    
    static final int getInt(final String s, final int n) {
        return s.charAt(2 * n) << 16 | s.charAt(2 * n + 1);
    }
    
    public static final short[] RLEStringToShortArray(final String s) {
        final int n = s.charAt(0) << 16 | s.charAt(1);
        final short[] array = new short[n];
        while (2 < s.length()) {
            final char char1 = s.charAt(2);
            int n2 = 0;
            if (char1 == '\ua5a5') {
                ++n2;
                final char char2 = s.charAt(2);
                if (char2 == '\ua5a5') {
                    final short[] array2 = array;
                    final int n3 = 0;
                    int n4 = 0;
                    ++n4;
                    array2[n3] = (short)char2;
                }
                else {
                    final char c = char2;
                    ++n2;
                    final short n5 = (short)s.charAt(2);
                    while ('\0' < c) {
                        final short[] array3 = array;
                        final int n6 = 0;
                        int n4 = 0;
                        ++n4;
                        array3[n6] = n5;
                        int n7 = 0;
                        ++n7;
                    }
                }
            }
            else {
                final short[] array4 = array;
                final int n8 = 0;
                int n4 = 0;
                ++n4;
                array4[n8] = (short)char1;
            }
            ++n2;
        }
        if (n != 0) {
            throw new IllegalStateException("Bad run-length encoded short array");
        }
        return array;
    }
    
    public static final char[] RLEStringToCharArray(final String s) {
        final int n = s.charAt(0) << 16 | s.charAt(1);
        final char[] array = new char[n];
        while (2 < s.length()) {
            final char char1 = s.charAt(2);
            int n2 = 0;
            if (char1 == '\ua5a5') {
                ++n2;
                final char char2 = s.charAt(2);
                if (char2 == '\ua5a5') {
                    final char[] array2 = array;
                    final int n3 = 0;
                    int n4 = 0;
                    ++n4;
                    array2[n3] = char2;
                }
                else {
                    final char c = char2;
                    ++n2;
                    final char char3 = s.charAt(2);
                    while ('\0' < c) {
                        final char[] array3 = array;
                        final int n5 = 0;
                        int n4 = 0;
                        ++n4;
                        array3[n5] = char3;
                        int n6 = 0;
                        ++n6;
                    }
                }
            }
            else {
                final char[] array4 = array;
                final int n7 = 0;
                int n4 = 0;
                ++n4;
                array4[n7] = char1;
            }
            ++n2;
        }
        if (n != 0) {
            throw new IllegalStateException("Bad run-length encoded short array");
        }
        return array;
    }
    
    public static final byte[] RLEStringToByteArray(final String s) {
        final int n = s.charAt(0) << 16 | s.charAt(1);
        final byte[] array = new byte[n];
        while (0 < n) {
            final int n2 = 2;
            int n3 = 0;
            ++n3;
            s.charAt(n2);
            final byte b = 0;
            switch (false) {
                case 0: {
                    if (b == -91) {
                        continue;
                    }
                    final byte[] array2 = array;
                    final int n4 = 0;
                    int n5 = 0;
                    ++n5;
                    array2[n4] = b;
                    continue;
                }
                case 1: {
                    if (b == -91) {
                        final byte[] array3 = array;
                        final int n6 = 0;
                        int n5 = 0;
                        ++n5;
                        array3[n6] = -91;
                        continue;
                    }
                    continue;
                }
                case 2: {
                    continue;
                }
            }
        }
        if (2 != s.length()) {
            throw new IllegalStateException("Excess data in RLE byte array string");
        }
        return array;
    }
    
    public static final String formatForSource(final String s) {
        final StringBuilder sb = new StringBuilder();
        while (0 < s.length()) {
            sb.append("        \"");
            while (0 < s.length()) {
                final int n = 0;
                int n2 = 0;
                ++n2;
                final char char1 = s.charAt(n);
                if (char1 < ' ' || char1 == '\"' || char1 == '\\') {
                    if (char1 == '\n') {
                        sb.append("\\n");
                        final int n3;
                        n3 += 2;
                    }
                    else if (char1 == '\t') {
                        sb.append("\\t");
                        final int n3;
                        n3 += 2;
                    }
                    else if (char1 == '\r') {
                        sb.append("\\r");
                        final int n3;
                        n3 += 2;
                    }
                    else {
                        sb.append('\\');
                        sb.append(Utility.HEX_DIGIT[(char1 & '\u01c0') >> 6]);
                        sb.append(Utility.HEX_DIGIT[(char1 & '8') >> 3]);
                        sb.append(Utility.HEX_DIGIT[char1 & '\u0007']);
                        final int n3;
                        n3 += 4;
                    }
                }
                else if (char1 <= '~') {
                    sb.append(char1);
                    int n3 = 0;
                    ++n3;
                }
                else {
                    sb.append("\\u");
                    sb.append(Utility.HEX_DIGIT[(char1 & '\uf000') >> 12]);
                    sb.append(Utility.HEX_DIGIT[(char1 & '\u0f00') >> 8]);
                    sb.append(Utility.HEX_DIGIT[(char1 & '\u00f0') >> 4]);
                    sb.append(Utility.HEX_DIGIT[char1 & '\u000f']);
                    final int n3;
                    n3 += 6;
                }
            }
            sb.append('\"');
        }
        return sb.toString();
    }
    
    public static final String format1ForSource(final String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append("\"");
        while (0 < s.length()) {
            final int n = 0;
            int n2 = 0;
            ++n2;
            final char char1 = s.charAt(n);
            if (char1 < ' ' || char1 == '\"' || char1 == '\\') {
                if (char1 == '\n') {
                    sb.append("\\n");
                }
                else if (char1 == '\t') {
                    sb.append("\\t");
                }
                else if (char1 == '\r') {
                    sb.append("\\r");
                }
                else {
                    sb.append('\\');
                    sb.append(Utility.HEX_DIGIT[(char1 & '\u01c0') >> 6]);
                    sb.append(Utility.HEX_DIGIT[(char1 & '8') >> 3]);
                    sb.append(Utility.HEX_DIGIT[char1 & '\u0007']);
                }
            }
            else if (char1 <= '~') {
                sb.append(char1);
            }
            else {
                sb.append("\\u");
                sb.append(Utility.HEX_DIGIT[(char1 & '\uf000') >> 12]);
                sb.append(Utility.HEX_DIGIT[(char1 & '\u0f00') >> 8]);
                sb.append(Utility.HEX_DIGIT[(char1 & '\u00f0') >> 4]);
                sb.append(Utility.HEX_DIGIT[char1 & '\u000f']);
            }
        }
        sb.append('\"');
        return sb.toString();
    }
    
    public static final String escape(final String s) {
        final StringBuilder sb = new StringBuilder();
        while (0 < s.length()) {
            final int codePoint = Character.codePointAt(s, 0);
            final int n = 0 + UTF16.getCharCount(codePoint);
            if (codePoint >= 32 && codePoint <= 127) {
                if (codePoint == 92) {
                    sb.append("\\\\");
                }
                else {
                    sb.append((char)codePoint);
                }
            }
            else {
                final boolean b = codePoint <= 65535;
                sb.append(b ? "\\u" : "\\U");
                sb.append(hex(codePoint, b ? 4 : 8));
            }
        }
        return sb.toString();
    }
    
    public static int unescapeAt(final String s, final int[] array) {
        final int n = array[0];
        final int length = s.length();
        if (n < 0 || n >= length) {
            return -1;
        }
        int n2 = Character.codePointAt(s, n);
        int i = n + UTF16.getCharCount(n2);
        switch (n2) {
            case 117: {
                break;
            }
            case 85: {
                break;
            }
            case 120: {
                if (i < length && UTF16.charAt(s, i) == 123) {
                    ++i;
                    break;
                }
                break;
            }
            default: {
                if (UCharacter.digit(n2, 8) >= 0) {
                    break;
                }
                break;
            }
        }
        while (i < length) {
            n2 = UTF16.charAt(s, i);
            if (UCharacter.digit(n2, 8) < 0) {
                break;
            }
            i += UTF16.getCharCount(n2);
            int n3 = 0;
            ++n3;
        }
        if (n2 != 125) {
            return -1;
        }
        if (++i < length && UTF16.isLeadSurrogate((char)0)) {
            int n4 = i + 1;
            int n5 = s.charAt(i);
            if (n5 == 92 && n4 < length) {
                final int[] array2 = { n4 };
                n5 = unescapeAt(s, array2);
                n4 = array2[0];
            }
            if (UTF16.isTrailSurrogate((char)n5)) {
                i = n4;
                UCharacterProperty.getRawSupplementary((char)0, (char)n5);
            }
        }
        array[0] = i;
        return 0;
    }
    
    public static String unescape(final String s) {
        final StringBuilder sb = new StringBuilder();
        final int[] array = { 0 };
        while (0 < s.length()) {
            final int n = 0;
            int n2 = 0;
            ++n2;
            final char char1 = s.charAt(n);
            if (char1 == '\\') {
                array[0] = 0;
                final int unescape = unescapeAt(s, array);
                if (unescape < 0) {
                    throw new IllegalArgumentException("Invalid escape sequence " + s.substring(-1, Math.min(8, s.length())));
                }
                sb.appendCodePoint(unescape);
                n2 = array[0];
            }
            else {
                sb.append(char1);
            }
        }
        return sb.toString();
    }
    
    public static String unescapeLeniently(final String s) {
        final StringBuilder sb = new StringBuilder();
        final int[] array = { 0 };
        while (0 < s.length()) {
            final int n = 0;
            int n2 = 0;
            ++n2;
            final char char1 = s.charAt(n);
            if (char1 == '\\') {
                array[0] = 0;
                final int unescape = unescapeAt(s, array);
                if (unescape < 0) {
                    sb.append(char1);
                }
                else {
                    sb.appendCodePoint(unescape);
                    n2 = array[0];
                }
            }
            else {
                sb.append(char1);
            }
        }
        return sb.toString();
    }
    
    public static String hex(final long n) {
        return hex(n, 4);
    }
    
    public static String hex(long n, final int n2) {
        if (n == Long.MIN_VALUE) {
            return "-8000000000000000";
        }
        final boolean b = n < 0L;
        if (b) {
            n = -n;
        }
        String s = Long.toString(n, 16).toUpperCase(Locale.ENGLISH);
        if (s.length() < n2) {
            s = "0000000000000000".substring(s.length(), n2) + s;
        }
        if (b) {
            return '-' + s;
        }
        return s;
    }
    
    public static String hex(final CharSequence charSequence) {
        return ((StringBuilder)hex(charSequence, 4, ",", true, new StringBuilder())).toString();
    }
    
    public static Appendable hex(final CharSequence charSequence, final int n, final CharSequence charSequence2, final boolean b, final Appendable appendable) {
        if (b) {
            while (0 < charSequence.length()) {
                final int codePoint = Character.codePointAt(charSequence, 0);
                appendable.append(hex(0, n));
                final int n2 = 0 + UTF16.getCharCount(0);
            }
        }
        else {
            while (0 < charSequence.length()) {
                appendable.append(hex(charSequence.charAt(0), n));
                int codePoint = 0;
                ++codePoint;
            }
        }
        return appendable;
    }
    
    public static String hex(final byte[] array, final int n, final int n2, final String s) {
        final StringBuilder sb = new StringBuilder();
        for (int i = n; i < n2; ++i) {
            if (i != 0) {
                sb.append(s);
            }
            sb.append(hex(array[i]));
        }
        return sb.toString();
    }
    
    public static String hex(final CharSequence charSequence, final int n, final CharSequence charSequence2) {
        return ((StringBuilder)hex(charSequence, n, charSequence2, true, new StringBuilder())).toString();
    }
    
    public static void split(final String s, final char c, final String[] array) {
        int n2 = 0;
        while (0 < s.length()) {
            if (s.charAt(0) == c) {
                final int n = 0;
                ++n2;
                array[n] = s.substring(0, 0);
            }
            int n3 = 0;
            ++n3;
        }
        final int n4 = 0;
        ++n2;
        array[n4] = s.substring(0, 0);
        while (0 < array.length) {
            final int n5 = 0;
            ++n2;
            array[n5] = "";
        }
    }
    
    public static String[] split(final String s, final char c) {
        final ArrayList<String> list = new ArrayList<String>();
        while (0 < s.length()) {
            if (s.charAt(0) == c) {
                list.add(s.substring(0, 0));
            }
            int n = 0;
            ++n;
        }
        list.add(s.substring(0, 0));
        return list.toArray(new String[list.size()]);
    }
    
    public static int lookup(final String s, final String[] array) {
        while (0 < array.length) {
            if (s.equals(array[0])) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public static boolean parseChar(final String s, final int[] array, final char c) {
        final int n = array[0];
        array[0] = PatternProps.skipWhiteSpace(s, array[0]);
        if (array[0] == s.length() || s.charAt(array[0]) != c) {
            array[0] = n;
            return false;
        }
        final int n2 = 0;
        ++array[n2];
        return true;
    }
    
    public static int parsePattern(final String s, int skipWhiteSpace, final int n, final String s2, final int[] array) {
        final int[] array2 = { 0 };
        while (0 < s2.length()) {
            final char char1 = s2.charAt(0);
            switch (char1) {
                case 32: {
                    if (skipWhiteSpace >= n) {
                        return -1;
                    }
                    if (!PatternProps.isWhiteSpace(s.charAt(skipWhiteSpace++))) {
                        return -1;
                    }
                }
                case 126: {
                    skipWhiteSpace = PatternProps.skipWhiteSpace(s, skipWhiteSpace);
                    break;
                }
                case 35: {
                    array2[0] = skipWhiteSpace;
                    final int n2 = 0;
                    int n3 = 0;
                    ++n3;
                    array[n2] = parseInteger(s, array2, n);
                    if (array2[0] == skipWhiteSpace) {
                        return -1;
                    }
                    skipWhiteSpace = array2[0];
                    break;
                }
                default: {
                    if (skipWhiteSpace >= n) {
                        return -1;
                    }
                    if ((char)UCharacter.toLowerCase(s.charAt(skipWhiteSpace++)) != char1) {
                        return -1;
                    }
                    break;
                }
            }
            int n4 = 0;
            ++n4;
        }
        return skipWhiteSpace;
    }
    
    public static int parsePattern(final String s, final Replaceable replaceable, int i, final int n) {
        if (0 == s.length()) {
            return i;
        }
        int n2 = Character.codePointAt(s, 0);
        while (i < n) {
            final int char32At = replaceable.char32At(i);
            if (n2 == 126) {
                if (PatternProps.isWhiteSpace(char32At)) {
                    i += UTF16.getCharCount(char32At);
                    continue;
                }
                int n3 = 0;
                ++n3;
                if (0 == s.length()) {
                    return i;
                }
            }
            else {
                if (char32At != n2) {
                    return -1;
                }
                final int charCount = UTF16.getCharCount(char32At);
                i += charCount;
                final int n3 = 0 + charCount;
                if (0 == s.length()) {
                    return i;
                }
            }
            n2 = UTF16.charAt(s, 0);
        }
        return -1;
    }
    
    public static int parseInteger(final String s, final int[] array, final int n) {
        int i = array[0];
        if (s.regionMatches(true, i, "0x", 0, 2)) {
            i += 2;
        }
        else if (i < n && s.charAt(i) == '0') {
            ++i;
        }
        while (i < n) {
            final int digit = UCharacter.digit(s.charAt(i++), 8);
            if (digit < 0) {
                --i;
                break;
            }
            int n2 = 0;
            ++n2;
            if (0 + digit <= 0) {
                return 0;
            }
        }
        array[0] = i;
        return 0;
    }
    
    public static String parseUnicodeIdentifier(final String s, final int[] array) {
        final StringBuilder sb = new StringBuilder();
        int i;
        int codePoint;
        for (i = array[0]; i < s.length(); i += UTF16.getCharCount(codePoint)) {
            codePoint = Character.codePointAt(s, i);
            if (sb.length() == 0) {
                if (!UCharacter.isUnicodeIdentifierStart(codePoint)) {
                    return null;
                }
                sb.appendCodePoint(codePoint);
            }
            else {
                if (!UCharacter.isUnicodeIdentifierPart(codePoint)) {
                    break;
                }
                sb.appendCodePoint(codePoint);
            }
        }
        array[0] = i;
        return sb.toString();
    }
    
    private static void recursiveAppendNumber(final Appendable appendable, final int n, final int n2, final int n3) {
        final int n4 = n % n2;
        if (n >= n2 || n3 > 1) {
            recursiveAppendNumber(appendable, n / n2, n2, n3 - 1);
        }
        appendable.append(Utility.DIGITS[n4]);
    }
    
    public static Appendable appendNumber(final Appendable appendable, final int n, final int n2, final int n3) {
        if (n2 < 2 || n2 > 36) {
            throw new IllegalArgumentException("Illegal radix " + n2);
        }
        int n4;
        if ((n4 = n) < 0) {
            n4 = -n;
            appendable.append("-");
        }
        recursiveAppendNumber(appendable, n4, n2, n3);
        return appendable;
    }
    
    public static int parseNumber(final String s, final int[] array, final int n) {
        int n2;
        for (n2 = array[0]; n2 < s.length() && UCharacter.digit(Character.codePointAt(s, n2), n) >= 0; ++n2) {}
        if (n2 == array[0]) {
            return -1;
        }
        array[0] = n2;
        return 0;
    }
    
    public static int quotedIndexOf(final String s, final int n, final int n2, final String s2) {
        for (int i = n; i < n2; ++i) {
            final char char1 = s.charAt(i);
            if (char1 == '\\') {
                ++i;
            }
            else if (char1 == '\'') {
                while (++i < n2 && s.charAt(i) != '\'') {}
            }
            else if (s2.indexOf(char1) >= 0) {
                return i;
            }
        }
        return -1;
    }
    
    public static void appendToRule(final StringBuffer p0, final int p1, final boolean p2, final boolean p3, final StringBuffer p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifne            12
        //     4: iload_3        
        //     5: ifeq            250
        //     8: iload_1        
        //     9: if_icmplt       250
        //    12: aload           4
        //    14: invokevirtual   java/lang/StringBuffer.length:()I
        //    17: ifle            192
        //    20: aload           4
        //    22: invokevirtual   java/lang/StringBuffer.length:()I
        //    25: iconst_2       
        //    26: if_icmplt       74
        //    29: aload           4
        //    31: iconst_0       
        //    32: invokevirtual   java/lang/StringBuffer.charAt:(I)C
        //    35: bipush          39
        //    37: if_icmpne       74
        //    40: aload           4
        //    42: iconst_1       
        //    43: invokevirtual   java/lang/StringBuffer.charAt:(I)C
        //    46: bipush          39
        //    48: if_icmpne       74
        //    51: aload_0        
        //    52: bipush          92
        //    54: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //    57: bipush          39
        //    59: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //    62: pop            
        //    63: aload           4
        //    65: iconst_0       
        //    66: iconst_2       
        //    67: invokevirtual   java/lang/StringBuffer.delete:(II)Ljava/lang/StringBuffer;
        //    70: pop            
        //    71: goto            20
        //    74: aload           4
        //    76: invokevirtual   java/lang/StringBuffer.length:()I
        //    79: iconst_2       
        //    80: if_icmplt       135
        //    83: aload           4
        //    85: aload           4
        //    87: invokevirtual   java/lang/StringBuffer.length:()I
        //    90: iconst_2       
        //    91: isub           
        //    92: invokevirtual   java/lang/StringBuffer.charAt:(I)C
        //    95: bipush          39
        //    97: if_icmpne       135
        //   100: aload           4
        //   102: aload           4
        //   104: invokevirtual   java/lang/StringBuffer.length:()I
        //   107: iconst_1       
        //   108: isub           
        //   109: invokevirtual   java/lang/StringBuffer.charAt:(I)C
        //   112: bipush          39
        //   114: if_icmpne       135
        //   117: aload           4
        //   119: aload           4
        //   121: invokevirtual   java/lang/StringBuffer.length:()I
        //   124: iconst_2       
        //   125: isub           
        //   126: invokevirtual   java/lang/StringBuffer.setLength:(I)V
        //   129: iinc            5, 1
        //   132: goto            74
        //   135: aload           4
        //   137: invokevirtual   java/lang/StringBuffer.length:()I
        //   140: ifle            170
        //   143: aload_0        
        //   144: bipush          39
        //   146: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   149: pop            
        //   150: aload_0        
        //   151: aload           4
        //   153: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/StringBuffer;)Ljava/lang/StringBuffer;
        //   156: pop            
        //   157: aload_0        
        //   158: bipush          39
        //   160: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   163: pop            
        //   164: aload           4
        //   166: iconst_0       
        //   167: invokevirtual   java/lang/StringBuffer.setLength:(I)V
        //   170: iconst_0       
        //   171: iinc            5, -1
        //   174: ifle            192
        //   177: aload_0        
        //   178: bipush          92
        //   180: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   183: bipush          39
        //   185: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   188: pop            
        //   189: goto            170
        //   192: iload_1        
        //   193: iconst_m1      
        //   194: if_icmpeq       378
        //   197: iload_1        
        //   198: bipush          32
        //   200: if_icmpne       232
        //   203: aload_0        
        //   204: invokevirtual   java/lang/StringBuffer.length:()I
        //   207: istore          5
        //   209: goto            229
        //   212: aload_0        
        //   213: iconst_m1      
        //   214: invokevirtual   java/lang/StringBuffer.charAt:(I)C
        //   217: bipush          32
        //   219: if_icmpeq       229
        //   222: aload_0        
        //   223: bipush          32
        //   225: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   228: pop            
        //   229: goto            378
        //   232: iload_3        
        //   233: ifeq            241
        //   236: aload_0        
        //   237: iload_1        
        //   238: if_icmplt       378
        //   241: aload_0        
        //   242: iload_1        
        //   243: invokevirtual   java/lang/StringBuffer.appendCodePoint:(I)Ljava/lang/StringBuffer;
        //   246: pop            
        //   247: goto            378
        //   250: aload           4
        //   252: invokevirtual   java/lang/StringBuffer.length:()I
        //   255: ifne            285
        //   258: iload_1        
        //   259: bipush          39
        //   261: if_icmpeq       270
        //   264: iload_1        
        //   265: bipush          92
        //   267: if_icmpne       285
        //   270: aload_0        
        //   271: bipush          92
        //   273: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   276: iload_1        
        //   277: i2c            
        //   278: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   281: pop            
        //   282: goto            378
        //   285: aload           4
        //   287: invokevirtual   java/lang/StringBuffer.length:()I
        //   290: ifgt            348
        //   293: iload_1        
        //   294: bipush          33
        //   296: if_icmplt       341
        //   299: iload_1        
        //   300: bipush          126
        //   302: if_icmpgt       341
        //   305: iload_1        
        //   306: bipush          48
        //   308: if_icmplt       317
        //   311: iload_1        
        //   312: bipush          57
        //   314: if_icmple       341
        //   317: iload_1        
        //   318: bipush          65
        //   320: if_icmplt       329
        //   323: iload_1        
        //   324: bipush          90
        //   326: if_icmple       341
        //   329: iload_1        
        //   330: bipush          97
        //   332: if_icmplt       348
        //   335: iload_1        
        //   336: bipush          122
        //   338: if_icmpgt       348
        //   341: iload_1        
        //   342: invokestatic    com/ibm/icu/impl/PatternProps.isWhiteSpace:(I)Z
        //   345: ifeq            372
        //   348: aload           4
        //   350: iload_1        
        //   351: invokevirtual   java/lang/StringBuffer.appendCodePoint:(I)Ljava/lang/StringBuffer;
        //   354: pop            
        //   355: iload_1        
        //   356: bipush          39
        //   358: if_icmpne       378
        //   361: aload           4
        //   363: iload_1        
        //   364: i2c            
        //   365: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   368: pop            
        //   369: goto            378
        //   372: aload_0        
        //   373: iload_1        
        //   374: invokevirtual   java/lang/StringBuffer.appendCodePoint:(I)Ljava/lang/StringBuffer;
        //   377: pop            
        //   378: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static void appendToRule(final StringBuffer sb, final String s, final boolean b, final boolean b2, final StringBuffer sb2) {
        while (0 < s.length()) {
            appendToRule(sb, s.charAt(0), b, b2, sb2);
            int n = 0;
            ++n;
        }
    }
    
    public static void appendToRule(final StringBuffer sb, final UnicodeMatcher unicodeMatcher, final boolean b, final StringBuffer sb2) {
        if (unicodeMatcher != null) {
            appendToRule(sb, unicodeMatcher.toPattern(b), true, b, sb2);
        }
    }
    
    public static final int compareUnsigned(int n, int n2) {
        n -= Integer.MIN_VALUE;
        n2 -= Integer.MIN_VALUE;
        if (n < n2) {
            return -1;
        }
        if (n > n2) {
            return 1;
        }
        return 0;
    }
    
    public static final byte highBit(int n) {
        if (n <= 0) {
            return -1;
        }
        if (n >= 65536) {
            n >>= 16;
            final byte b = 16;
        }
        if (n >= 256) {
            n >>= 8;
            final byte b2 = 8;
        }
        if (n >= 16) {
            n >>= 4;
            final byte b3 = 4;
        }
        if (n >= 4) {
            n >>= 2;
            final byte b4 = 2;
        }
        if (n >= 2) {
            n >>= 1;
            final byte b5 = 1;
        }
        return 0;
    }
    
    public static String valueOf(final int[] array) {
        final StringBuilder sb = new StringBuilder(array.length);
        while (0 < array.length) {
            sb.appendCodePoint(array[0]);
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static String repeat(final String s, final int n) {
        if (n <= 0) {
            return "";
        }
        if (n == 1) {
            return s;
        }
        final StringBuilder sb = new StringBuilder();
        while (0 < n) {
            sb.append(s);
            int n2 = 0;
            ++n2;
        }
        return sb.toString();
    }
    
    public static String[] splitString(final String s, final String s2) {
        return s.split("\\Q" + s2 + "\\E");
    }
    
    public static String[] splitWhitespace(final String s) {
        return s.split("\\s+");
    }
    
    public static String fromHex(final String s, final int n, final String s2) {
        return fromHex(s, n, Pattern.compile((s2 != null) ? s2 : "\\s+"));
    }
    
    public static String fromHex(final String s, final int n, final Pattern pattern) {
        final StringBuilder sb = new StringBuilder();
        final String[] split = pattern.split(s);
        while (0 < split.length) {
            final String s2 = split[0];
            if (s2.length() < n) {
                throw new IllegalArgumentException("code point too short: " + s2);
            }
            sb.appendCodePoint(Integer.parseInt(s2, 16));
            int n2 = 0;
            ++n2;
        }
        return sb.toString();
    }
    
    public static ClassLoader getFallbackClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
            if (classLoader == null) {
                throw new RuntimeException("No accessible class loader is available.");
            }
        }
        return classLoader;
    }
    
    static {
        Utility.LINE_SEPARATOR = System.getProperty("line.separator");
        HEX_DIGIT = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        UNESCAPE_MAP = new char[] { 'a', '\u0007', 'b', '\b', 'e', '\u001b', 'f', '\f', 'n', '\n', 'r', '\r', 't', '\t', 'v', '\u000b' };
        DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    }
}
