package org.apache.commons.io;

import java.io.*;
import java.util.*;

public class FilenameUtils
{
    public static final char EXTENSION_SEPARATOR = '.';
    public static final String EXTENSION_SEPARATOR_STR;
    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';
    private static final char SYSTEM_SEPARATOR;
    private static final char OTHER_SEPARATOR;
    
    public static String normalize(final String s) {
        return doNormalize(s, FilenameUtils.SYSTEM_SEPARATOR, true);
    }
    
    public static String normalize(final String s, final boolean b) {
        return doNormalize(s, b ? '/' : '\\', true);
    }
    
    public static String normalizeNoEndSeparator(final String s) {
        return doNormalize(s, FilenameUtils.SYSTEM_SEPARATOR, false);
    }
    
    public static String normalizeNoEndSeparator(final String s, final boolean b) {
        return doNormalize(s, b ? '/' : '\\', false);
    }
    
    private static String doNormalize(final String s, final char c, final boolean b) {
        if (s == null) {
            return null;
        }
        int length = s.length();
        if (length == 0) {
            return s;
        }
        final int prefixLength = getPrefixLength(s);
        if (prefixLength < 0) {
            return null;
        }
        final char[] array = new char[length + 2];
        s.getChars(0, s.length(), array, 0);
        final char c2 = (c == FilenameUtils.SYSTEM_SEPARATOR) ? FilenameUtils.OTHER_SEPARATOR : FilenameUtils.SYSTEM_SEPARATOR;
        while (1 < array.length) {
            if (array[1] == c2) {
                array[1] = c;
            }
            int n = 0;
            ++n;
        }
        if (array[length - 1] != c) {
            array[length++] = c;
        }
        for (int i = prefixLength + 1; i < length; ++i) {
            if (array[i] == c && array[i - 1] == c) {
                System.arraycopy(array, i, array, i - 1, length - i);
                --length;
                --i;
            }
        }
        for (int j = prefixLength + 1; j < length; ++j) {
            if (array[j] == c && array[j - 1] == '.' && (j == prefixLength + 1 || array[j - 2] == c)) {
                if (j == length - 1) {}
                System.arraycopy(array, j + 1, array, j - 1, length - j);
                length -= 2;
                --j;
            }
        }
    Label_0446:
        for (int k = prefixLength + 2; k < length; ++k) {
            if (array[k] == c && array[k - 1] == '.' && array[k - 2] == '.' && (k == prefixLength + 2 || array[k - 3] == c)) {
                if (k == prefixLength + 2) {
                    return null;
                }
                if (k == length - 1) {}
                for (int l = k - 4; l >= prefixLength; --l) {
                    if (array[l] == c) {
                        System.arraycopy(array, k + 1, array, l + 1, length - k);
                        length -= k - l;
                        k = l + 1;
                        continue Label_0446;
                    }
                }
                System.arraycopy(array, k + 1, array, prefixLength, length - k);
                length -= k + 1 - prefixLength;
                k = prefixLength + 1;
            }
        }
        if (length <= 0) {
            return "";
        }
        if (length <= prefixLength) {
            return new String(array, 0, length);
        }
        if (b) {
            return new String(array, 0, length);
        }
        return new String(array, 0, length - 1);
    }
    
    public static String concat(final String p0, final String p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    org/apache/commons/io/FilenameUtils.getPrefixLength:(Ljava/lang/String;)I
        //     4: istore_2       
        //     5: iload_2        
        //     6: ifge            11
        //     9: aconst_null    
        //    10: areturn        
        //    11: iload_2        
        //    12: ifle            20
        //    15: aload_1        
        //    16: invokestatic    org/apache/commons/io/FilenameUtils.normalize:(Ljava/lang/String;)Ljava/lang/String;
        //    19: areturn        
        //    20: aload_0        
        //    21: ifnonnull       26
        //    24: aconst_null    
        //    25: areturn        
        //    26: aload_0        
        //    27: invokevirtual   java/lang/String.length:()I
        //    30: istore_3       
        //    31: iload_3        
        //    32: ifne            40
        //    35: aload_1        
        //    36: invokestatic    org/apache/commons/io/FilenameUtils.normalize:(Ljava/lang/String;)Ljava/lang/String;
        //    39: areturn        
        //    40: aload_0        
        //    41: iload_3        
        //    42: iconst_1       
        //    43: isub           
        //    44: invokevirtual   java/lang/String.charAt:(I)C
        //    47: istore          4
        //    49: iload           4
        //    51: if_icmpeq       76
        //    54: new             Ljava/lang/StringBuilder;
        //    57: dup            
        //    58: invokespecial   java/lang/StringBuilder.<init>:()V
        //    61: aload_0        
        //    62: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    65: aload_1        
        //    66: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    69: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    72: invokestatic    org/apache/commons/io/FilenameUtils.normalize:(Ljava/lang/String;)Ljava/lang/String;
        //    75: areturn        
        //    76: new             Ljava/lang/StringBuilder;
        //    79: dup            
        //    80: invokespecial   java/lang/StringBuilder.<init>:()V
        //    83: aload_0        
        //    84: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    87: bipush          47
        //    89: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //    92: aload_1        
        //    93: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    96: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    99: invokestatic    org/apache/commons/io/FilenameUtils.normalize:(Ljava/lang/String;)Ljava/lang/String;
        //   102: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: -1
        //     at java.util.ArrayList.elementData(Unknown Source)
        //     at java.util.ArrayList.remove(Unknown Source)
        //     at com.strobel.assembler.ir.StackMappingVisitor.pop(StackMappingVisitor.java:267)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:600)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
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
    
    public static boolean directoryContains(final String s, final String s2) throws IOException {
        if (s == null) {
            throw new IllegalArgumentException("Directory must not be null");
        }
        return s2 != null && !IOCase.SYSTEM.checkEquals(s, s2) && IOCase.SYSTEM.checkStartsWith(s2, s);
    }
    
    public static String separatorsToUnix(final String s) {
        if (s == null || s.indexOf(92) == -1) {
            return s;
        }
        return s.replace('\\', '/');
    }
    
    public static String separatorsToWindows(final String s) {
        if (s == null || s.indexOf(47) == -1) {
            return s;
        }
        return s.replace('/', '\\');
    }
    
    public static String separatorsToSystem(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: aconst_null    
        //     5: areturn        
        //     6: if_icmpne       14
        //     9: aload_0        
        //    10: invokestatic    org/apache/commons/io/FilenameUtils.separatorsToWindows:(Ljava/lang/String;)Ljava/lang/String;
        //    13: areturn        
        //    14: aload_0        
        //    15: invokestatic    org/apache/commons/io/FilenameUtils.separatorsToUnix:(Ljava/lang/String;)Ljava/lang/String;
        //    18: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: -1
        //     at java.util.ArrayList.elementData(Unknown Source)
        //     at java.util.ArrayList.remove(Unknown Source)
        //     at com.strobel.assembler.ir.StackMappingVisitor.pop(StackMappingVisitor.java:267)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:599)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
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
    
    public static int getPrefixLength(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: iconst_m1      
        //     5: ireturn        
        //     6: aload_0        
        //     7: invokevirtual   java/lang/String.length:()I
        //    10: istore_1       
        //    11: iload_1        
        //    12: ifne            17
        //    15: iconst_0       
        //    16: ireturn        
        //    17: aload_0        
        //    18: iconst_0       
        //    19: invokevirtual   java/lang/String.charAt:(I)C
        //    22: istore_2       
        //    23: iload_2        
        //    24: bipush          58
        //    26: if_icmpne       31
        //    29: iconst_m1      
        //    30: ireturn        
        //    31: iload_1        
        //    32: iconst_1       
        //    33: if_icmpne       54
        //    36: iload_2        
        //    37: bipush          126
        //    39: if_icmpne       44
        //    42: iconst_2       
        //    43: ireturn        
        //    44: iload_2        
        //    45: if_icmpeq       52
        //    48: iconst_1       
        //    49: goto            53
        //    52: iconst_0       
        //    53: ireturn        
        //    54: iload_2        
        //    55: bipush          126
        //    57: if_icmpne       127
        //    60: aload_0        
        //    61: bipush          47
        //    63: iconst_1       
        //    64: invokevirtual   java/lang/String.indexOf:(II)I
        //    67: istore_3       
        //    68: aload_0        
        //    69: bipush          92
        //    71: iconst_1       
        //    72: invokevirtual   java/lang/String.indexOf:(II)I
        //    75: istore          4
        //    77: iload_3        
        //    78: iconst_m1      
        //    79: if_icmpne       92
        //    82: iload           4
        //    84: iconst_m1      
        //    85: if_icmpne       92
        //    88: iload_1        
        //    89: iconst_1       
        //    90: iadd           
        //    91: ireturn        
        //    92: iload_3        
        //    93: iconst_m1      
        //    94: if_icmpne       102
        //    97: iload           4
        //    99: goto            103
        //   102: iload_3        
        //   103: istore_3       
        //   104: iload           4
        //   106: iconst_m1      
        //   107: if_icmpne       114
        //   110: iload_3        
        //   111: goto            116
        //   114: iload           4
        //   116: istore          4
        //   118: iload_3        
        //   119: iload           4
        //   121: invokestatic    java/lang/Math.min:(II)I
        //   124: iconst_1       
        //   125: iadd           
        //   126: ireturn        
        //   127: aload_0        
        //   128: iconst_1       
        //   129: invokevirtual   java/lang/String.charAt:(I)C
        //   132: istore_3       
        //   133: iload_3        
        //   134: bipush          58
        //   136: if_icmpne       175
        //   139: iload_2        
        //   140: invokestatic    java/lang/Character.toUpperCase:(C)C
        //   143: istore_2       
        //   144: iload_2        
        //   145: bipush          65
        //   147: if_icmplt       173
        //   150: iload_2        
        //   151: bipush          90
        //   153: if_icmpgt       173
        //   156: iload_1        
        //   157: iconst_2       
        //   158: if_icmpeq       169
        //   161: aload_0        
        //   162: iconst_2       
        //   163: invokevirtual   java/lang/String.charAt:(I)C
        //   166: if_icmpeq       171
        //   169: iconst_2       
        //   170: ireturn        
        //   171: iconst_3       
        //   172: ireturn        
        //   173: iconst_m1      
        //   174: ireturn        
        //   175: iload_2        
        //   176: if_icmpeq       267
        //   179: iload_3        
        //   180: if_icmpeq       267
        //   183: aload_0        
        //   184: bipush          47
        //   186: iconst_2       
        //   187: invokevirtual   java/lang/String.indexOf:(II)I
        //   190: istore          4
        //   192: aload_0        
        //   193: bipush          92
        //   195: iconst_2       
        //   196: invokevirtual   java/lang/String.indexOf:(II)I
        //   199: istore          5
        //   201: iload           4
        //   203: iconst_m1      
        //   204: if_icmpne       213
        //   207: iload           5
        //   209: iconst_m1      
        //   210: if_icmpeq       225
        //   213: iload           4
        //   215: iconst_2       
        //   216: if_icmpeq       225
        //   219: iload           5
        //   221: iconst_2       
        //   222: if_icmpne       227
        //   225: iconst_m1      
        //   226: ireturn        
        //   227: iload           4
        //   229: iconst_m1      
        //   230: if_icmpne       238
        //   233: iload           5
        //   235: goto            240
        //   238: iload           4
        //   240: istore          4
        //   242: iload           5
        //   244: iconst_m1      
        //   245: if_icmpne       253
        //   248: iload           4
        //   250: goto            255
        //   253: iload           5
        //   255: istore          5
        //   257: iload           4
        //   259: iload           5
        //   261: invokestatic    java/lang/Math.min:(II)I
        //   264: iconst_1       
        //   265: iadd           
        //   266: ireturn        
        //   267: iload_2        
        //   268: if_icmpeq       275
        //   271: iconst_1       
        //   272: goto            276
        //   275: iconst_0       
        //   276: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: -1
        //     at java.util.ArrayList.elementData(Unknown Source)
        //     at java.util.ArrayList.remove(Unknown Source)
        //     at com.strobel.assembler.ir.StackMappingVisitor.pop(StackMappingVisitor.java:267)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:600)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
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
    
    public static int indexOfLastSeparator(final String s) {
        if (s == null) {
            return -1;
        }
        return Math.max(s.lastIndexOf(47), s.lastIndexOf(92));
    }
    
    public static int indexOfExtension(final String s) {
        if (s == null) {
            return -1;
        }
        final int lastIndex = s.lastIndexOf(46);
        return (indexOfLastSeparator(s) > lastIndex) ? -1 : lastIndex;
    }
    
    public static String getPrefix(final String s) {
        if (s == null) {
            return null;
        }
        final int prefixLength = getPrefixLength(s);
        if (prefixLength < 0) {
            return null;
        }
        if (prefixLength > s.length()) {
            return s + '/';
        }
        return s.substring(0, prefixLength);
    }
    
    public static String getPath(final String s) {
        return doGetPath(s, 1);
    }
    
    public static String getPathNoEndSeparator(final String s) {
        return doGetPath(s, 0);
    }
    
    private static String doGetPath(final String s, final int n) {
        if (s == null) {
            return null;
        }
        final int prefixLength = getPrefixLength(s);
        if (prefixLength < 0) {
            return null;
        }
        final int indexOfLastSeparator = indexOfLastSeparator(s);
        final int n2 = indexOfLastSeparator + n;
        if (prefixLength >= s.length() || indexOfLastSeparator < 0 || prefixLength >= n2) {
            return "";
        }
        return s.substring(prefixLength, n2);
    }
    
    public static String getFullPath(final String s) {
        return doGetFullPath(s, true);
    }
    
    public static String getFullPathNoEndSeparator(final String s) {
        return doGetFullPath(s, false);
    }
    
    private static String doGetFullPath(final String s, final boolean b) {
        if (s == null) {
            return null;
        }
        final int prefixLength = getPrefixLength(s);
        if (prefixLength < 0) {
            return null;
        }
        if (prefixLength >= s.length()) {
            if (b) {
                return getPrefix(s);
            }
            return s;
        }
        else {
            final int indexOfLastSeparator = indexOfLastSeparator(s);
            if (indexOfLastSeparator < 0) {
                return s.substring(0, prefixLength);
            }
            int n = indexOfLastSeparator + (b ? 1 : 0);
            if (n == 0) {
                ++n;
            }
            return s.substring(0, n);
        }
    }
    
    public static String getName(final String s) {
        if (s == null) {
            return null;
        }
        return s.substring(indexOfLastSeparator(s) + 1);
    }
    
    public static String getBaseName(final String s) {
        return removeExtension(getName(s));
    }
    
    public static String getExtension(final String s) {
        if (s == null) {
            return null;
        }
        final int indexOfExtension = indexOfExtension(s);
        if (indexOfExtension == -1) {
            return "";
        }
        return s.substring(indexOfExtension + 1);
    }
    
    public static String removeExtension(final String s) {
        if (s == null) {
            return null;
        }
        final int indexOfExtension = indexOfExtension(s);
        if (indexOfExtension == -1) {
            return s;
        }
        return s.substring(0, indexOfExtension);
    }
    
    public static boolean equals(final String s, final String s2) {
        return equals(s, s2, false, IOCase.SENSITIVE);
    }
    
    public static boolean equalsOnSystem(final String s, final String s2) {
        return equals(s, s2, false, IOCase.SYSTEM);
    }
    
    public static boolean equalsNormalized(final String s, final String s2) {
        return equals(s, s2, true, IOCase.SENSITIVE);
    }
    
    public static boolean equalsNormalizedOnSystem(final String s, final String s2) {
        return equals(s, s2, true, IOCase.SYSTEM);
    }
    
    public static boolean equals(String normalize, String normalize2, final boolean b, IOCase sensitive) {
        if (normalize == null || normalize2 == null) {
            return normalize == null && normalize2 == null;
        }
        if (b) {
            normalize = normalize(normalize);
            normalize2 = normalize(normalize2);
            if (normalize == null || normalize2 == null) {
                throw new NullPointerException("Error normalizing one or both of the file names");
            }
        }
        if (sensitive == null) {
            sensitive = IOCase.SENSITIVE;
        }
        return sensitive.checkEquals(normalize, normalize2);
    }
    
    public static boolean isExtension(final String s, final String s2) {
        if (s == null) {
            return false;
        }
        if (s2 == null || s2.length() == 0) {
            return indexOfExtension(s) == -1;
        }
        return getExtension(s).equals(s2);
    }
    
    public static boolean isExtension(final String s, final String[] array) {
        if (s == null) {
            return false;
        }
        if (array == null || array.length == 0) {
            return indexOfExtension(s) == -1;
        }
        final String extension = getExtension(s);
        while (0 < array.length) {
            if (extension.equals(array[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean isExtension(final String s, final Collection collection) {
        if (s == null) {
            return false;
        }
        if (collection == null || collection.isEmpty()) {
            return indexOfExtension(s) == -1;
        }
        final String extension = getExtension(s);
        final Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (extension.equals(iterator.next())) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean wildcardMatch(final String s, final String s2) {
        return wildcardMatch(s, s2, IOCase.SENSITIVE);
    }
    
    public static boolean wildcardMatchOnSystem(final String s, final String s2) {
        return wildcardMatch(s, s2, IOCase.SYSTEM);
    }
    
    public static boolean wildcardMatch(final String s, final String s2, IOCase sensitive) {
        if (s == null && s2 == null) {
            return true;
        }
        if (s == null || s2 == null) {
            return false;
        }
        if (sensitive == null) {
            sensitive = IOCase.SENSITIVE;
        }
        final String[] splitOnTokens = splitOnTokens(s2);
        final Stack<int[]> stack = new Stack<int[]>();
        do {
            int n = 0;
            int length = 0;
            if (stack.size() > 0) {
                final int[] array = stack.pop();
                n = array[0];
                length = array[1];
            }
            while (0 < splitOnTokens.length) {
                if (splitOnTokens[0].equals("?")) {
                    ++length;
                    if (0 > s.length()) {
                        break;
                    }
                }
                else if (splitOnTokens[0].equals("*")) {
                    if (0 == splitOnTokens.length - 1) {
                        length = s.length();
                    }
                }
                else {
                    if (!sensitive.checkRegionMatches(s, 0, splitOnTokens[0])) {
                        break;
                    }
                    length = 0 + splitOnTokens[0].length();
                }
                ++n;
            }
            if (0 == splitOnTokens.length && 0 == s.length()) {
                return true;
            }
        } while (stack.size() > 0);
        return false;
    }
    
    static String[] splitOnTokens(final String s) {
        if (s.indexOf(63) == -1 && s.indexOf(42) == -1) {
            return new String[] { s };
        }
        final char[] charArray = s.toCharArray();
        final ArrayList<String> list = new ArrayList<String>();
        final StringBuilder sb = new StringBuilder();
        while (0 < charArray.length) {
            if (charArray[0] == '?' || charArray[0] == '*') {
                if (sb.length() != 0) {
                    list.add(sb.toString());
                    sb.setLength(0);
                }
                if (charArray[0] == '?') {
                    list.add("?");
                }
                else if (list.isEmpty()) {
                    list.add("*");
                }
            }
            else {
                sb.append(charArray[0]);
            }
            int n = 0;
            ++n;
        }
        if (sb.length() != 0) {
            list.add(sb.toString());
        }
        return list.toArray(new String[list.size()]);
    }
    
    static {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: invokestatic    java/lang/Character.toString:(C)Ljava/lang/String;
        //     5: putstatic       org/apache/commons/io/FilenameUtils.EXTENSION_SEPARATOR_STR:Ljava/lang/String;
        //     8: getstatic       java/io/File.separatorChar:C
        //    11: putstatic       org/apache/commons/io/FilenameUtils.SYSTEM_SEPARATOR:C
        //    14: if_icmpne       25
        //    17: bipush          47
        //    19: putstatic       org/apache/commons/io/FilenameUtils.OTHER_SEPARATOR:C
        //    22: goto            30
        //    25: bipush          92
        //    27: putstatic       org/apache/commons/io/FilenameUtils.OTHER_SEPARATOR:C
        //    30: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: -1
        //     at java.util.ArrayList.elementData(Unknown Source)
        //     at java.util.ArrayList.remove(Unknown Source)
        //     at com.strobel.assembler.ir.StackMappingVisitor.pop(StackMappingVisitor.java:267)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:599)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
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
}
