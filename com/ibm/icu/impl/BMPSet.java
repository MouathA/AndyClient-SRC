package com.ibm.icu.impl;

import com.ibm.icu.text.*;

public final class BMPSet
{
    private boolean[] latin1Contains;
    private int[] table7FF;
    private int[] bmpBlockBits;
    private int[] list4kStarts;
    private final int[] list;
    private final int listLength;
    static final boolean $assertionsDisabled;
    
    public BMPSet(final int[] list, final int listLength) {
        this.list = list;
        this.listLength = listLength;
        this.latin1Contains = new boolean[256];
        this.table7FF = new int[64];
        this.bmpBlockBits = new int[64];
        (this.list4kStarts = new int[18])[0] = this.findCodePoint(2048, 0, this.listLength - 1);
        while (true) {
            this.list4kStarts[1] = this.findCodePoint(4096, this.list4kStarts[0], this.listLength - 1);
            int n = 0;
            ++n;
        }
    }
    
    public BMPSet(final BMPSet set, final int[] list, final int listLength) {
        this.list = list;
        this.listLength = listLength;
        this.latin1Contains = set.latin1Contains.clone();
        this.table7FF = set.table7FF.clone();
        this.bmpBlockBits = set.bmpBlockBits.clone();
        this.list4kStarts = set.list4kStarts.clone();
    }
    
    public boolean contains(final int n) {
        if (n <= 255) {
            return this.latin1Contains[n];
        }
        if (n <= 2047) {
            return (this.table7FF[n & 0x3F] & 1 << (n >> 6)) != 0x0;
        }
        if (n >= 55296 && (n < 57344 || n > 65535)) {
            return n <= 1114111 && this.containsSlow(n, this.list4kStarts[13], this.list4kStarts[17]);
        }
        final int n2 = n >> 12;
        final int n3 = this.bmpBlockBits[n >> 6 & 0x3F] >> n2 & 0x10001;
        if (n3 <= 1) {
            return n3 != 0;
        }
        return this.containsSlow(n, this.list4kStarts[n2], this.list4kStarts[n2 + 1]);
    }
    
    public final int span(final CharSequence p0, final int p1, final int p2, final UnicodeSet.SpanCondition p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: istore          7
        //     3: aload_1        
        //     4: invokeinterface java/lang/CharSequence.length:()I
        //     9: iload_3        
        //    10: invokestatic    java/lang/Math.min:(II)I
        //    13: istore          8
        //    15: getstatic       com/ibm/icu/text/UnicodeSet$SpanCondition.NOT_CONTAINED:Lcom/ibm/icu/text/UnicodeSet$SpanCondition;
        //    18: aload           4
        //    20: if_acmpeq       252
        //    23: iload           7
        //    25: iload           8
        //    27: if_icmpge       481
        //    30: aload_1        
        //    31: iload           7
        //    33: invokeinterface java/lang/CharSequence.charAt:(I)C
        //    38: istore          5
        //    40: iload           5
        //    42: sipush          255
        //    45: if_icmpgt       61
        //    48: aload_0        
        //    49: getfield        com/ibm/icu/impl/BMPSet.latin1Contains:[Z
        //    52: iload           5
        //    54: baload         
        //    55: ifne            246
        //    58: goto            481
        //    61: iload           5
        //    63: sipush          2047
        //    66: if_icmpgt       93
        //    69: aload_0        
        //    70: getfield        com/ibm/icu/impl/BMPSet.table7FF:[I
        //    73: iload           5
        //    75: bipush          63
        //    77: iand           
        //    78: iaload         
        //    79: iconst_1       
        //    80: iload           5
        //    82: bipush          6
        //    84: ishr           
        //    85: ishl           
        //    86: iand           
        //    87: ifne            246
        //    90: goto            481
        //    93: iload           5
        //    95: ldc             55296
        //    97: if_icmplt       141
        //   100: iload           5
        //   102: ldc             56320
        //   104: if_icmpge       141
        //   107: iload           7
        //   109: iconst_1       
        //   110: iadd           
        //   111: iload           8
        //   113: if_icmpeq       141
        //   116: aload_1        
        //   117: iload           7
        //   119: iconst_1       
        //   120: iadd           
        //   121: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   126: dup            
        //   127: istore          6
        //   129: ldc             56320
        //   131: if_icmplt       141
        //   134: iload           6
        //   136: ldc             57344
        //   138: if_icmplt       211
        //   141: iload           5
        //   143: bipush          12
        //   145: ishr           
        //   146: istore          9
        //   148: aload_0        
        //   149: getfield        com/ibm/icu/impl/BMPSet.bmpBlockBits:[I
        //   152: iload           5
        //   154: bipush          6
        //   156: ishr           
        //   157: bipush          63
        //   159: iand           
        //   160: iaload         
        //   161: iload           9
        //   163: ishr           
        //   164: ldc             65537
        //   166: iand           
        //   167: istore          10
        //   169: iload           10
        //   171: iconst_1       
        //   172: if_icmpgt       183
        //   175: iload           10
        //   177: ifne            208
        //   180: goto            481
        //   183: aload_0        
        //   184: iload           5
        //   186: aload_0        
        //   187: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   190: iload           9
        //   192: iaload         
        //   193: aload_0        
        //   194: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   197: iload           9
        //   199: iconst_1       
        //   200: iadd           
        //   201: iaload         
        //   202: if_icmpeq       208
        //   205: goto            481
        //   208: goto            246
        //   211: iload           5
        //   213: iload           6
        //   215: invokestatic    com/ibm/icu/impl/UCharacterProperty.getRawSupplementary:(CC)I
        //   218: istore          9
        //   220: aload_0        
        //   221: iload           9
        //   223: aload_0        
        //   224: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   227: bipush          16
        //   229: iaload         
        //   230: aload_0        
        //   231: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   234: bipush          17
        //   236: iaload         
        //   237: if_icmpeq       243
        //   240: goto            481
        //   243: iinc            7, 1
        //   246: iinc            7, 1
        //   249: goto            23
        //   252: iload           7
        //   254: iload           8
        //   256: if_icmpge       481
        //   259: aload_1        
        //   260: iload           7
        //   262: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   267: istore          5
        //   269: iload           5
        //   271: sipush          255
        //   274: if_icmpgt       290
        //   277: aload_0        
        //   278: getfield        com/ibm/icu/impl/BMPSet.latin1Contains:[Z
        //   281: iload           5
        //   283: baload         
        //   284: ifeq            475
        //   287: goto            481
        //   290: iload           5
        //   292: sipush          2047
        //   295: if_icmpgt       322
        //   298: aload_0        
        //   299: getfield        com/ibm/icu/impl/BMPSet.table7FF:[I
        //   302: iload           5
        //   304: bipush          63
        //   306: iand           
        //   307: iaload         
        //   308: iconst_1       
        //   309: iload           5
        //   311: bipush          6
        //   313: ishr           
        //   314: ishl           
        //   315: iand           
        //   316: ifeq            475
        //   319: goto            481
        //   322: iload           5
        //   324: ldc             55296
        //   326: if_icmplt       370
        //   329: iload           5
        //   331: ldc             56320
        //   333: if_icmpge       370
        //   336: iload           7
        //   338: iconst_1       
        //   339: iadd           
        //   340: iload           8
        //   342: if_icmpeq       370
        //   345: aload_1        
        //   346: iload           7
        //   348: iconst_1       
        //   349: iadd           
        //   350: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   355: dup            
        //   356: istore          6
        //   358: ldc             56320
        //   360: if_icmplt       370
        //   363: iload           6
        //   365: ldc             57344
        //   367: if_icmplt       440
        //   370: iload           5
        //   372: bipush          12
        //   374: ishr           
        //   375: istore          9
        //   377: aload_0        
        //   378: getfield        com/ibm/icu/impl/BMPSet.bmpBlockBits:[I
        //   381: iload           5
        //   383: bipush          6
        //   385: ishr           
        //   386: bipush          63
        //   388: iand           
        //   389: iaload         
        //   390: iload           9
        //   392: ishr           
        //   393: ldc             65537
        //   395: iand           
        //   396: istore          10
        //   398: iload           10
        //   400: iconst_1       
        //   401: if_icmpgt       412
        //   404: iload           10
        //   406: ifeq            437
        //   409: goto            481
        //   412: aload_0        
        //   413: iload           5
        //   415: aload_0        
        //   416: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   419: iload           9
        //   421: iaload         
        //   422: aload_0        
        //   423: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   426: iload           9
        //   428: iconst_1       
        //   429: iadd           
        //   430: iaload         
        //   431: if_icmpeq       437
        //   434: goto            481
        //   437: goto            475
        //   440: iload           5
        //   442: iload           6
        //   444: invokestatic    com/ibm/icu/impl/UCharacterProperty.getRawSupplementary:(CC)I
        //   447: istore          9
        //   449: aload_0        
        //   450: iload           9
        //   452: aload_0        
        //   453: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   456: bipush          16
        //   458: iaload         
        //   459: aload_0        
        //   460: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   463: bipush          17
        //   465: iaload         
        //   466: if_icmpeq       472
        //   469: goto            481
        //   472: iinc            7, 1
        //   475: iinc            7, 1
        //   478: goto            252
        //   481: iload           7
        //   483: iload_2        
        //   484: isub           
        //   485: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0252 (coming from #0478).
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
    
    public final int spanBack(final CharSequence p0, final int p1, final UnicodeSet.SpanCondition p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokeinterface java/lang/CharSequence.length:()I
        //     6: iload_2        
        //     7: invokestatic    java/lang/Math.min:(II)I
        //    10: istore_2       
        //    11: getstatic       com/ibm/icu/text/UnicodeSet$SpanCondition.NOT_CONTAINED:Lcom/ibm/icu/text/UnicodeSet$SpanCondition;
        //    14: aload_3        
        //    15: if_acmpeq       238
        //    18: aload_1        
        //    19: iinc            2, -1
        //    22: iload_2        
        //    23: invokeinterface java/lang/CharSequence.charAt:(I)C
        //    28: istore          4
        //    30: iload           4
        //    32: sipush          255
        //    35: if_icmpgt       51
        //    38: aload_0        
        //    39: getfield        com/ibm/icu/impl/BMPSet.latin1Contains:[Z
        //    42: iload           4
        //    44: baload         
        //    45: ifne            231
        //    48: goto            458
        //    51: iload           4
        //    53: sipush          2047
        //    56: if_icmpgt       83
        //    59: aload_0        
        //    60: getfield        com/ibm/icu/impl/BMPSet.table7FF:[I
        //    63: iload           4
        //    65: bipush          63
        //    67: iand           
        //    68: iaload         
        //    69: iconst_1       
        //    70: iload           4
        //    72: bipush          6
        //    74: ishr           
        //    75: ishl           
        //    76: iand           
        //    77: ifne            231
        //    80: goto            458
        //    83: iload           4
        //    85: ldc             55296
        //    87: if_icmplt       126
        //    90: iload           4
        //    92: ldc             56320
        //    94: if_icmplt       126
        //    97: iconst_0       
        //    98: iload_2        
        //    99: if_icmpeq       126
        //   102: aload_1        
        //   103: iload_2        
        //   104: iconst_1       
        //   105: isub           
        //   106: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   111: dup            
        //   112: istore          5
        //   114: ldc             55296
        //   116: if_icmplt       126
        //   119: iload           5
        //   121: ldc             56320
        //   123: if_icmplt       196
        //   126: iload           4
        //   128: bipush          12
        //   130: ishr           
        //   131: istore          6
        //   133: aload_0        
        //   134: getfield        com/ibm/icu/impl/BMPSet.bmpBlockBits:[I
        //   137: iload           4
        //   139: bipush          6
        //   141: ishr           
        //   142: bipush          63
        //   144: iand           
        //   145: iaload         
        //   146: iload           6
        //   148: ishr           
        //   149: ldc             65537
        //   151: iand           
        //   152: istore          7
        //   154: iload           7
        //   156: iconst_1       
        //   157: if_icmpgt       168
        //   160: iload           7
        //   162: ifne            193
        //   165: goto            458
        //   168: aload_0        
        //   169: iload           4
        //   171: aload_0        
        //   172: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   175: iload           6
        //   177: iaload         
        //   178: aload_0        
        //   179: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   182: iload           6
        //   184: iconst_1       
        //   185: iadd           
        //   186: iaload         
        //   187: if_icmpeq       193
        //   190: goto            458
        //   193: goto            231
        //   196: iload           5
        //   198: iload           4
        //   200: invokestatic    com/ibm/icu/impl/UCharacterProperty.getRawSupplementary:(CC)I
        //   203: istore          6
        //   205: aload_0        
        //   206: iload           6
        //   208: aload_0        
        //   209: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   212: bipush          16
        //   214: iaload         
        //   215: aload_0        
        //   216: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   219: bipush          17
        //   221: iaload         
        //   222: if_icmpeq       228
        //   225: goto            458
        //   228: iinc            2, -1
        //   231: iconst_0       
        //   232: iload_2        
        //   233: if_icmpne       18
        //   236: iconst_0       
        //   237: ireturn        
        //   238: aload_1        
        //   239: iinc            2, -1
        //   242: iload_2        
        //   243: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   248: istore          4
        //   250: iload           4
        //   252: sipush          255
        //   255: if_icmpgt       271
        //   258: aload_0        
        //   259: getfield        com/ibm/icu/impl/BMPSet.latin1Contains:[Z
        //   262: iload           4
        //   264: baload         
        //   265: ifeq            451
        //   268: goto            458
        //   271: iload           4
        //   273: sipush          2047
        //   276: if_icmpgt       303
        //   279: aload_0        
        //   280: getfield        com/ibm/icu/impl/BMPSet.table7FF:[I
        //   283: iload           4
        //   285: bipush          63
        //   287: iand           
        //   288: iaload         
        //   289: iconst_1       
        //   290: iload           4
        //   292: bipush          6
        //   294: ishr           
        //   295: ishl           
        //   296: iand           
        //   297: ifeq            451
        //   300: goto            458
        //   303: iload           4
        //   305: ldc             55296
        //   307: if_icmplt       346
        //   310: iload           4
        //   312: ldc             56320
        //   314: if_icmplt       346
        //   317: iconst_0       
        //   318: iload_2        
        //   319: if_icmpeq       346
        //   322: aload_1        
        //   323: iload_2        
        //   324: iconst_1       
        //   325: isub           
        //   326: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   331: dup            
        //   332: istore          5
        //   334: ldc             55296
        //   336: if_icmplt       346
        //   339: iload           5
        //   341: ldc             56320
        //   343: if_icmplt       416
        //   346: iload           4
        //   348: bipush          12
        //   350: ishr           
        //   351: istore          6
        //   353: aload_0        
        //   354: getfield        com/ibm/icu/impl/BMPSet.bmpBlockBits:[I
        //   357: iload           4
        //   359: bipush          6
        //   361: ishr           
        //   362: bipush          63
        //   364: iand           
        //   365: iaload         
        //   366: iload           6
        //   368: ishr           
        //   369: ldc             65537
        //   371: iand           
        //   372: istore          7
        //   374: iload           7
        //   376: iconst_1       
        //   377: if_icmpgt       388
        //   380: iload           7
        //   382: ifeq            413
        //   385: goto            458
        //   388: aload_0        
        //   389: iload           4
        //   391: aload_0        
        //   392: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   395: iload           6
        //   397: iaload         
        //   398: aload_0        
        //   399: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   402: iload           6
        //   404: iconst_1       
        //   405: iadd           
        //   406: iaload         
        //   407: if_icmpeq       413
        //   410: goto            458
        //   413: goto            451
        //   416: iload           5
        //   418: iload           4
        //   420: invokestatic    com/ibm/icu/impl/UCharacterProperty.getRawSupplementary:(CC)I
        //   423: istore          6
        //   425: aload_0        
        //   426: iload           6
        //   428: aload_0        
        //   429: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   432: bipush          16
        //   434: iaload         
        //   435: aload_0        
        //   436: getfield        com/ibm/icu/impl/BMPSet.list4kStarts:[I
        //   439: bipush          17
        //   441: iaload         
        //   442: if_icmpeq       448
        //   445: goto            458
        //   448: iinc            2, -1
        //   451: iconst_0       
        //   452: iload_2        
        //   453: if_icmpne       238
        //   456: iconst_0       
        //   457: ireturn        
        //   458: iload_2        
        //   459: iconst_1       
        //   460: iadd           
        //   461: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0238 (coming from #0453).
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
    
    private static void set32x64Bits(final int[] array, final int n, final int n2) {
        assert 64 == array.length;
        final int n3 = n >> 6;
        int n4 = n & 0x3F;
        final int n5 = 1 << n3;
        if (n + 1 == n2) {
            final int n6 = 0;
            array[n6] |= n5;
            return;
        }
        final int n7 = n2 >> 6;
        final int n8 = n2 & 0x3F;
        if (n3 == n7) {
            while (0 < n8) {
                final int n9 = 0;
                ++n4;
                array[n9] |= n5;
            }
        }
        else if (n3 < n7) {
            int n10 = -(1 << n3);
            if (n7 < 32) {
                n10 &= (1 << n7) - 1;
            }
            while (true) {
                final int n11 = 0;
                array[n11] |= n10;
                ++n4;
            }
        }
        else {
            final int n12 = 1 << n7;
            while (0 < n8) {
                final int n13 = 0;
                array[n13] |= n12;
                ++n4;
            }
        }
    }
    
    private void initBits() {
        final int[] list = this.list;
        final int n = 0;
        int n2 = 0;
        ++n2;
        final int n3 = list[n];
        if (0 < this.listLength) {
            final int[] list2 = this.list;
            final int n4 = 0;
            ++n2;
            final int n5 = list2[n4];
        }
        while (true) {
            set32x64Bits(this.bmpBlockBits, 32, 17408);
            final int[] list3 = this.list;
            final int n6 = 0;
            ++n2;
            final int n7 = list3[n6];
            if (0 < this.listLength) {
                final int[] list4 = this.list;
                final int n8 = 0;
                ++n2;
                final int n9 = list4[n8];
            }
        }
    }
    
    private int findCodePoint(final int n, int n2, int n3) {
        if (n < this.list[n2]) {
            return n2;
        }
        if (n2 >= n3 || n >= this.list[n3 - 1]) {
            return n3;
        }
        while (true) {
            final int n4 = n2 + n3 >>> 1;
            if (n4 == n2) {
                break;
            }
            if (n < this.list[n4]) {
                n3 = n4;
            }
            else {
                n2 = n4;
            }
        }
        return n3;
    }
    
    static {
        $assertionsDisabled = !BMPSet.class.desiredAssertionStatus();
    }
}
