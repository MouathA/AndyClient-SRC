package com.ibm.icu.text;

import com.ibm.icu.lang.*;

final class BidiWriter
{
    static final char LRM_CHAR = '\u200e';
    static final char RLM_CHAR = '\u200f';
    static final int MASK_R_AL = 8194;
    
    private static String doWriteForward(final String s, final int n) {
        switch (n & 0xA) {
            case 0: {
                return s;
            }
            case 2: {
                final StringBuffer sb = new StringBuffer(s.length());
                do {
                    final int char1 = UTF16.charAt(s, 0);
                    final int n2 = 0 + UTF16.getCharCount(char1);
                    UTF16.append(sb, UCharacter.getMirror(char1));
                } while (0 < s.length());
                return sb.toString();
            }
            case 8: {
                final StringBuilder sb2 = new StringBuilder(s.length());
                do {
                    final int n3 = 0;
                    int n2 = 0;
                    ++n2;
                    final char char2 = s.charAt(n3);
                    if (!Bidi.IsBidiControlChar(char2)) {
                        sb2.append(char2);
                    }
                } while (0 < s.length());
                return sb2.toString();
            }
            default: {
                final StringBuffer sb3 = new StringBuffer(s.length());
                do {
                    final int char3 = UTF16.charAt(s, 0);
                    final int n2 = 0 + UTF16.getCharCount(char3);
                    if (!Bidi.IsBidiControlChar(char3)) {
                        UTF16.append(sb3, UCharacter.getMirror(char3));
                    }
                } while (0 < s.length());
                return sb3.toString();
            }
        }
    }
    
    private static String doWriteForward(final char[] array, final int n, final int n2, final int n3) {
        return doWriteForward(new String(array, n, n2 - n), n3);
    }
    
    static String writeReverse(final String s, final int n) {
        final StringBuffer sb = new StringBuffer(s.length());
        switch (n & 0xB) {
            case 0: {
                int i = s.length();
                do {
                    final int n2 = i;
                    i -= UTF16.getCharCount(UTF16.charAt(s, i - 1));
                    sb.append(s.substring(i, n2));
                } while (i > 0);
                break;
            }
            case 1: {
                int j = s.length();
                do {
                    final int n3 = j;
                    int char1;
                    do {
                        char1 = UTF16.charAt(s, j - 1);
                        j -= UTF16.getCharCount(char1);
                    } while (j > 0 && UCharacter.getType(char1) == 0);
                    sb.append(s.substring(j, n3));
                } while (j > 0);
                break;
            }
            default: {
                int k = s.length();
                do {
                    final int n4 = k;
                    int n5 = UTF16.charAt(s, k - 1);
                    k -= UTF16.getCharCount(n5);
                    if ((n & 0x1) != 0x0) {
                        while (k > 0 && UCharacter.getType(n5) != 0) {
                            n5 = UTF16.charAt(s, k - 1);
                            k -= UTF16.getCharCount(n5);
                        }
                    }
                    if ((n & 0x8) != 0x0 && Bidi.IsBidiControlChar(n5)) {
                        continue;
                    }
                    int n6 = k;
                    if ((n & 0x2) != 0x0) {
                        final int mirror = UCharacter.getMirror(n5);
                        UTF16.append(sb, mirror);
                        n6 += UTF16.getCharCount(mirror);
                    }
                    sb.append(s.substring(n6, n4));
                } while (k > 0);
                break;
            }
        }
        return sb.toString();
    }
    
    static String doWriteReverse(final char[] array, final int n, final int n2, final int n3) {
        return writeReverse(new String(array, n, n2 - n), n3);
    }
    
    static String writeReordered(final Bidi p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/ibm/icu/text/Bidi.text:[C
        //     4: astore          5
        //     6: aload_0        
        //     7: invokevirtual   com/ibm/icu/text/Bidi.countRuns:()I
        //    10: istore_3       
        //    11: aload_0        
        //    12: getfield        com/ibm/icu/text/Bidi.reorderingOptions:I
        //    15: iconst_1       
        //    16: iand           
        //    17: ifeq            29
        //    20: iload_1        
        //    21: iconst_4       
        //    22: ior            
        //    23: istore_1       
        //    24: iload_1        
        //    25: bipush          -9
        //    27: iand           
        //    28: istore_1       
        //    29: aload_0        
        //    30: getfield        com/ibm/icu/text/Bidi.reorderingOptions:I
        //    33: iconst_2       
        //    34: iand           
        //    35: ifeq            48
        //    38: iload_1        
        //    39: bipush          8
        //    41: ior            
        //    42: istore_1       
        //    43: iload_1        
        //    44: bipush          -5
        //    46: iand           
        //    47: istore_1       
        //    48: aload_0        
        //    49: getfield        com/ibm/icu/text/Bidi.reorderingMode:I
        //    52: iconst_4       
        //    53: if_icmpeq       86
        //    56: aload_0        
        //    57: getfield        com/ibm/icu/text/Bidi.reorderingMode:I
        //    60: iconst_5       
        //    61: if_icmpeq       86
        //    64: aload_0        
        //    65: getfield        com/ibm/icu/text/Bidi.reorderingMode:I
        //    68: bipush          6
        //    70: if_icmpeq       86
        //    73: aload_0        
        //    74: getfield        com/ibm/icu/text/Bidi.reorderingMode:I
        //    77: iconst_3       
        //    78: if_icmpeq       86
        //    81: iload_1        
        //    82: bipush          -5
        //    84: iand           
        //    85: istore_1       
        //    86: new             Ljava/lang/StringBuilder;
        //    89: dup            
        //    90: iload_1        
        //    91: iconst_4       
        //    92: iand           
        //    93: ifeq            105
        //    96: aload_0        
        //    97: getfield        com/ibm/icu/text/Bidi.length:I
        //   100: iconst_2       
        //   101: imul           
        //   102: goto            109
        //   105: aload_0        
        //   106: getfield        com/ibm/icu/text/Bidi.length:I
        //   109: invokespecial   java/lang/StringBuilder.<init>:(I)V
        //   112: astore          4
        //   114: iload_1        
        //   115: bipush          16
        //   117: iand           
        //   118: ifne            477
        //   121: iload_1        
        //   122: iconst_4       
        //   123: iand           
        //   124: ifne            203
        //   127: iconst_0       
        //   128: iload_3        
        //   129: if_icmpge       733
        //   132: aload_0        
        //   133: iconst_0       
        //   134: invokevirtual   com/ibm/icu/text/Bidi.getVisualRun:(I)Lcom/ibm/icu/text/BidiRun;
        //   137: astore          6
        //   139: aload           6
        //   141: invokevirtual   com/ibm/icu/text/BidiRun.isEvenRun:()Z
        //   144: ifeq            175
        //   147: aload           4
        //   149: aload           5
        //   151: aload           6
        //   153: getfield        com/ibm/icu/text/BidiRun.start:I
        //   156: aload           6
        //   158: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   161: iload_1        
        //   162: bipush          -3
        //   164: iand           
        //   165: invokestatic    com/ibm/icu/text/BidiWriter.doWriteForward:([CIII)Ljava/lang/String;
        //   168: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   171: pop            
        //   172: goto            197
        //   175: aload           4
        //   177: aload           5
        //   179: aload           6
        //   181: getfield        com/ibm/icu/text/BidiRun.start:I
        //   184: aload           6
        //   186: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   189: iload_1        
        //   190: invokestatic    com/ibm/icu/text/BidiWriter.doWriteReverse:([CIII)Ljava/lang/String;
        //   193: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   196: pop            
        //   197: iinc            2, 1
        //   200: goto            127
        //   203: aload_0        
        //   204: getfield        com/ibm/icu/text/Bidi.dirProps:[B
        //   207: astore          6
        //   209: iconst_0       
        //   210: iload_3        
        //   211: if_icmpge       474
        //   214: aload_0        
        //   215: iconst_0       
        //   216: invokevirtual   com/ibm/icu/text/Bidi.getVisualRun:(I)Lcom/ibm/icu/text/BidiRun;
        //   219: astore          9
        //   221: aload_0        
        //   222: getfield        com/ibm/icu/text/Bidi.runs:[Lcom/ibm/icu/text/BidiRun;
        //   225: iconst_0       
        //   226: aaload         
        //   227: getfield        com/ibm/icu/text/BidiRun.insertRemove:I
        //   230: istore          8
        //   232: goto            235
        //   235: aload           9
        //   237: invokevirtual   com/ibm/icu/text/BidiRun.isEvenRun:()Z
        //   240: ifeq            353
        //   243: aload_0        
        //   244: invokevirtual   com/ibm/icu/text/Bidi.isInverse:()Z
        //   247: ifeq            261
        //   250: aload           6
        //   252: aload           9
        //   254: getfield        com/ibm/icu/text/BidiRun.start:I
        //   257: baload         
        //   258: ifeq            261
        //   261: goto            267
        //   264: goto            273
        //   267: goto            273
        //   270: goto            273
        //   273: goto            283
        //   276: aload           4
        //   278: iconst_0       
        //   279: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   282: pop            
        //   283: aload           4
        //   285: aload           5
        //   287: aload           9
        //   289: getfield        com/ibm/icu/text/BidiRun.start:I
        //   292: aload           9
        //   294: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   297: iload_1        
        //   298: bipush          -3
        //   300: iand           
        //   301: invokestatic    com/ibm/icu/text/BidiWriter.doWriteForward:([CIII)Ljava/lang/String;
        //   304: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   307: pop            
        //   308: aload_0        
        //   309: invokevirtual   com/ibm/icu/text/Bidi.isInverse:()Z
        //   312: ifeq            328
        //   315: aload           6
        //   317: aload           9
        //   319: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   322: iconst_1       
        //   323: isub           
        //   324: baload         
        //   325: ifeq            328
        //   328: goto            334
        //   331: goto            340
        //   334: goto            340
        //   337: goto            340
        //   340: goto            468
        //   343: aload           4
        //   345: iconst_0       
        //   346: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   349: pop            
        //   350: goto            468
        //   353: aload_0        
        //   354: invokevirtual   com/ibm/icu/text/Bidi.isInverse:()Z
        //   357: ifeq            377
        //   360: aload_0        
        //   361: sipush          8194
        //   364: aload           9
        //   366: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   369: iconst_1       
        //   370: isub           
        //   371: invokevirtual   com/ibm/icu/text/Bidi.testDirPropFlagAt:(II)Z
        //   374: ifne            377
        //   377: goto            383
        //   380: goto            389
        //   383: goto            389
        //   386: goto            389
        //   389: goto            399
        //   392: aload           4
        //   394: iconst_0       
        //   395: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   398: pop            
        //   399: aload           4
        //   401: aload           5
        //   403: aload           9
        //   405: getfield        com/ibm/icu/text/BidiRun.start:I
        //   408: aload           9
        //   410: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   413: iload_1        
        //   414: invokestatic    com/ibm/icu/text/BidiWriter.doWriteReverse:([CIII)Ljava/lang/String;
        //   417: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   420: pop            
        //   421: aload_0        
        //   422: invokevirtual   com/ibm/icu/text/Bidi.isInverse:()Z
        //   425: ifeq            446
        //   428: sipush          8194
        //   431: aload           6
        //   433: aload           9
        //   435: getfield        com/ibm/icu/text/BidiRun.start:I
        //   438: baload         
        //   439: invokestatic    com/ibm/icu/text/Bidi.DirPropFlag:(B)I
        //   442: iand           
        //   443: ifne            446
        //   446: goto            452
        //   449: goto            458
        //   452: goto            458
        //   455: goto            458
        //   458: goto            468
        //   461: aload           4
        //   463: iconst_0       
        //   464: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   467: pop            
        //   468: iinc            2, 1
        //   471: goto            209
        //   474: goto            733
        //   477: iload_1        
        //   478: iconst_4       
        //   479: iand           
        //   480: ifne            556
        //   483: iload_3        
        //   484: istore_2       
        //   485: iinc            2, -1
        //   488: aload_0        
        //   489: iconst_0       
        //   490: invokevirtual   com/ibm/icu/text/Bidi.getVisualRun:(I)Lcom/ibm/icu/text/BidiRun;
        //   493: astore          6
        //   495: aload           6
        //   497: invokevirtual   com/ibm/icu/text/BidiRun.isEvenRun:()Z
        //   500: ifeq            531
        //   503: aload           4
        //   505: aload           5
        //   507: aload           6
        //   509: getfield        com/ibm/icu/text/BidiRun.start:I
        //   512: aload           6
        //   514: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   517: iload_1        
        //   518: bipush          -3
        //   520: iand           
        //   521: invokestatic    com/ibm/icu/text/BidiWriter.doWriteReverse:([CIII)Ljava/lang/String;
        //   524: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   527: pop            
        //   528: goto            553
        //   531: aload           4
        //   533: aload           5
        //   535: aload           6
        //   537: getfield        com/ibm/icu/text/BidiRun.start:I
        //   540: aload           6
        //   542: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   545: iload_1        
        //   546: invokestatic    com/ibm/icu/text/BidiWriter.doWriteForward:([CIII)Ljava/lang/String;
        //   549: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   552: pop            
        //   553: goto            485
        //   556: aload_0        
        //   557: getfield        com/ibm/icu/text/Bidi.dirProps:[B
        //   560: astore          6
        //   562: iload_3        
        //   563: istore_2       
        //   564: iinc            2, -1
        //   567: aload_0        
        //   568: iconst_0       
        //   569: invokevirtual   com/ibm/icu/text/Bidi.getVisualRun:(I)Lcom/ibm/icu/text/BidiRun;
        //   572: astore          7
        //   574: aload           7
        //   576: invokevirtual   com/ibm/icu/text/BidiRun.isEvenRun:()Z
        //   579: ifeq            652
        //   582: aload           6
        //   584: aload           7
        //   586: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   589: iconst_1       
        //   590: isub           
        //   591: baload         
        //   592: ifeq            604
        //   595: aload           4
        //   597: sipush          8206
        //   600: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   603: pop            
        //   604: aload           4
        //   606: aload           5
        //   608: aload           7
        //   610: getfield        com/ibm/icu/text/BidiRun.start:I
        //   613: aload           7
        //   615: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   618: iload_1        
        //   619: bipush          -3
        //   621: iand           
        //   622: invokestatic    com/ibm/icu/text/BidiWriter.doWriteReverse:([CIII)Ljava/lang/String;
        //   625: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   628: pop            
        //   629: aload           6
        //   631: aload           7
        //   633: getfield        com/ibm/icu/text/BidiRun.start:I
        //   636: baload         
        //   637: ifeq            730
        //   640: aload           4
        //   642: sipush          8206
        //   645: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   648: pop            
        //   649: goto            730
        //   652: sipush          8194
        //   655: aload           6
        //   657: aload           7
        //   659: getfield        com/ibm/icu/text/BidiRun.start:I
        //   662: baload         
        //   663: invokestatic    com/ibm/icu/text/Bidi.DirPropFlag:(B)I
        //   666: iand           
        //   667: ifne            679
        //   670: aload           4
        //   672: sipush          8207
        //   675: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   678: pop            
        //   679: aload           4
        //   681: aload           5
        //   683: aload           7
        //   685: getfield        com/ibm/icu/text/BidiRun.start:I
        //   688: aload           7
        //   690: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   693: iload_1        
        //   694: invokestatic    com/ibm/icu/text/BidiWriter.doWriteForward:([CIII)Ljava/lang/String;
        //   697: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   700: pop            
        //   701: sipush          8194
        //   704: aload           6
        //   706: aload           7
        //   708: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   711: iconst_1       
        //   712: isub           
        //   713: baload         
        //   714: invokestatic    com/ibm/icu/text/Bidi.DirPropFlag:(B)I
        //   717: iand           
        //   718: ifne            730
        //   721: aload           4
        //   723: sipush          8207
        //   726: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   729: pop            
        //   730: goto            564
        //   733: aload           4
        //   735: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   738: areturn        
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
}
