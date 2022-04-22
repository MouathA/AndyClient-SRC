package org.apache.commons.compress.utils;

import org.apache.commons.compress.archivers.*;

public class ArchiveUtils
{
    private ArchiveUtils() {
    }
    
    public static String toString(final ArchiveEntry archiveEntry) {
        final StringBuilder sb = new StringBuilder();
        sb.append(archiveEntry.isDirectory() ? 'd' : '-');
        final String string = Long.toString(archiveEntry.getSize());
        sb.append(' ');
        while (7 > string.length()) {
            sb.append(' ');
            int n = 0;
            --n;
        }
        sb.append(string);
        sb.append(' ').append(archiveEntry.getName());
        return sb.toString();
    }
    
    public static boolean matchAsciiBuffer(final String s, final byte[] array, final int n, final int n2) {
        final byte[] bytes = s.getBytes("US-ASCII");
        return isEqual(bytes, 0, bytes.length, array, n, n2, false);
    }
    
    public static boolean matchAsciiBuffer(final String s, final byte[] array) {
        return matchAsciiBuffer(s, array, 0, array.length);
    }
    
    public static byte[] toAsciiBytes(final String s) {
        return s.getBytes("US-ASCII");
    }
    
    public static String toAsciiString(final byte[] array) {
        return new String(array, "US-ASCII");
    }
    
    public static String toAsciiString(final byte[] array, final int n, final int n2) {
        return new String(array, n, n2, "US-ASCII");
    }
    
    public static boolean isEqual(final byte[] p0, final int p1, final int p2, final byte[] p3, final int p4, final int p5, final boolean p6) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iload           5
        //     3: if_icmpge       10
        //     6: iload_2        
        //     7: goto            12
        //    10: iload           5
        //    12: istore          7
        //    14: iconst_0       
        //    15: iload           7
        //    17: if_icmpge       42
        //    20: aload_0        
        //    21: iload_1        
        //    22: iconst_0       
        //    23: iadd           
        //    24: baload         
        //    25: aload_3        
        //    26: iload           4
        //    28: iconst_0       
        //    29: iadd           
        //    30: baload         
        //    31: if_icmpeq       36
        //    34: iconst_0       
        //    35: ireturn        
        //    36: iinc            8, 1
        //    39: goto            14
        //    42: iload_2        
        //    43: iload           5
        //    45: if_icmpne       50
        //    48: iconst_1       
        //    49: ireturn        
        //    50: iload           6
        //    52: ifeq            117
        //    55: iload_2        
        //    56: iload           5
        //    58: if_icmple       89
        //    61: iload           5
        //    63: istore          8
        //    65: iconst_0       
        //    66: iload_2        
        //    67: if_icmpge       86
        //    70: aload_0        
        //    71: iload_1        
        //    72: iconst_0       
        //    73: iadd           
        //    74: baload         
        //    75: ifeq            80
        //    78: iconst_0       
        //    79: ireturn        
        //    80: iinc            8, 1
        //    83: goto            65
        //    86: goto            115
        //    89: iload_2        
        //    90: istore          8
        //    92: iconst_0       
        //    93: iload           5
        //    95: if_icmpge       115
        //    98: aload_3        
        //    99: iload           4
        //   101: iconst_0       
        //   102: iadd           
        //   103: baload         
        //   104: ifeq            109
        //   107: iconst_0       
        //   108: ireturn        
        //   109: iinc            8, 1
        //   112: goto            92
        //   115: iconst_1       
        //   116: ireturn        
        //   117: iconst_0       
        //   118: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    public static boolean isEqual(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) {
        return isEqual(array, n, n2, array2, n3, n4, false);
    }
    
    public static boolean isEqual(final byte[] array, final byte[] array2) {
        return isEqual(array, 0, array.length, array2, 0, array2.length, false);
    }
    
    public static boolean isEqual(final byte[] array, final byte[] array2, final boolean b) {
        return isEqual(array, 0, array.length, array2, 0, array2.length, b);
    }
    
    public static boolean isEqualWithNull(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) {
        return isEqual(array, n, n2, array2, n3, n4, true);
    }
    
    public static boolean isArrayZero(final byte[] array, final int n) {
        while (0 < n) {
            if (array[0] != 0) {
                return false;
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
}
