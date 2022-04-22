package com.jcraft.jorbis;

class Mdct
{
    int n;
    int log2n;
    float[] trig;
    int[] bitrev;
    float scale;
    float[] _x;
    float[] _w;
    
    Mdct() {
        this._x = new float[1024];
        this._w = new float[1024];
    }
    
    void init(final int n) {
        this.bitrev = new int[n / 4];
        this.trig = new float[n + n / 4];
        this.log2n = (int)Math.rint(Math.log(n) / Math.log(2.0));
        this.n = n;
        final int n2 = 0 + n / 2;
        final int n3 = n2 + 1;
        final int n4 = n2 + n / 2;
        final int n5 = n4 + 1;
        int n6 = 0;
        while (0 < n / 4) {
            this.trig[0] = (float)Math.cos(3.141592653589793 / n * 0);
            this.trig[1] = (float)(-Math.sin(3.141592653589793 / n * 0));
            this.trig[n2 + 0] = (float)Math.cos(3.141592653589793 / (2 * n) * 1);
            this.trig[n3 + 0] = (float)Math.sin(3.141592653589793 / (2 * n) * 1);
            ++n6;
        }
        while (0 < n / 8) {
            this.trig[n4 + 0] = (float)Math.cos(3.141592653589793 / n * 2);
            this.trig[n5 + 0] = (float)(-Math.sin(3.141592653589793 / n * 2));
            ++n6;
        }
        n6 = (1 << this.log2n - 1) - 1;
        final int n7 = 1 << this.log2n - 2;
        while (0 < n / 8) {
            while (n7 >>> 0 != 0) {
                if ((n7 >>> 0 & 0x0) != 0x0) {}
                int n8 = 0;
                ++n8;
            }
            this.bitrev[0] = 0;
            this.bitrev[1] = 0;
            int n9 = 0;
            ++n9;
        }
        this.scale = 4.0f / n;
    }
    
    void clear() {
    }
    
    void forward(final float[] array, final float[] array2) {
    }
    
    synchronized void backward(final float[] p0, final float[] p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/jcraft/jorbis/Mdct._x:[F
        //     4: arraylength    
        //     5: aload_0        
        //     6: getfield        com/jcraft/jorbis/Mdct.n:I
        //     9: iconst_2       
        //    10: idiv           
        //    11: if_icmpge       26
        //    14: aload_0        
        //    15: aload_0        
        //    16: getfield        com/jcraft/jorbis/Mdct.n:I
        //    19: iconst_2       
        //    20: idiv           
        //    21: newarray        F
        //    23: putfield        com/jcraft/jorbis/Mdct._x:[F
        //    26: aload_0        
        //    27: getfield        com/jcraft/jorbis/Mdct._w:[F
        //    30: arraylength    
        //    31: aload_0        
        //    32: getfield        com/jcraft/jorbis/Mdct.n:I
        //    35: iconst_2       
        //    36: idiv           
        //    37: if_icmpge       52
        //    40: aload_0        
        //    41: aload_0        
        //    42: getfield        com/jcraft/jorbis/Mdct.n:I
        //    45: iconst_2       
        //    46: idiv           
        //    47: newarray        F
        //    49: putfield        com/jcraft/jorbis/Mdct._w:[F
        //    52: aload_0        
        //    53: getfield        com/jcraft/jorbis/Mdct._x:[F
        //    56: astore_3       
        //    57: aload_0        
        //    58: getfield        com/jcraft/jorbis/Mdct._w:[F
        //    61: astore          4
        //    63: aload_0        
        //    64: getfield        com/jcraft/jorbis/Mdct.n:I
        //    67: iconst_1       
        //    68: iushr          
        //    69: istore          5
        //    71: aload_0        
        //    72: getfield        com/jcraft/jorbis/Mdct.n:I
        //    75: iconst_2       
        //    76: iushr          
        //    77: istore          6
        //    79: aload_0        
        //    80: getfield        com/jcraft/jorbis/Mdct.n:I
        //    83: iconst_3       
        //    84: iushr          
        //    85: istore          7
        //    87: iload           5
        //    89: istore          10
        //    91: iconst_0       
        //    92: iload           7
        //    94: if_icmpge       172
        //    97: iinc            10, -2
        //   100: aload_3        
        //   101: iconst_0       
        //   102: iinc            9, 1
        //   105: aload_1        
        //   106: iconst_3       
        //   107: faload         
        //   108: fneg           
        //   109: aload_0        
        //   110: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   113: iload           10
        //   115: iconst_1       
        //   116: iadd           
        //   117: faload         
        //   118: fmul           
        //   119: aload_1        
        //   120: iconst_1       
        //   121: faload         
        //   122: aload_0        
        //   123: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   126: iload           10
        //   128: faload         
        //   129: fmul           
        //   130: fsub           
        //   131: fastore        
        //   132: aload_3        
        //   133: iconst_0       
        //   134: iinc            9, 1
        //   137: aload_1        
        //   138: iconst_1       
        //   139: faload         
        //   140: aload_0        
        //   141: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   144: iload           10
        //   146: iconst_1       
        //   147: iadd           
        //   148: faload         
        //   149: fmul           
        //   150: aload_1        
        //   151: iconst_3       
        //   152: faload         
        //   153: aload_0        
        //   154: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   157: iload           10
        //   159: faload         
        //   160: fmul           
        //   161: fsub           
        //   162: fastore        
        //   163: iinc            8, 4
        //   166: iinc            11, 1
        //   169: goto            91
        //   172: iload           5
        //   174: iconst_4       
        //   175: isub           
        //   176: istore          8
        //   178: iconst_0       
        //   179: iload           7
        //   181: if_icmpge       258
        //   184: iinc            10, -2
        //   187: aload_3        
        //   188: iconst_0       
        //   189: iinc            9, 1
        //   192: aload_1        
        //   193: iconst_1       
        //   194: faload         
        //   195: aload_0        
        //   196: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   199: iload           10
        //   201: iconst_1       
        //   202: iadd           
        //   203: faload         
        //   204: fmul           
        //   205: aload_1        
        //   206: iconst_3       
        //   207: faload         
        //   208: aload_0        
        //   209: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   212: iload           10
        //   214: faload         
        //   215: fmul           
        //   216: fadd           
        //   217: fastore        
        //   218: aload_3        
        //   219: iconst_0       
        //   220: iinc            9, 1
        //   223: aload_1        
        //   224: iconst_1       
        //   225: faload         
        //   226: aload_0        
        //   227: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   230: iload           10
        //   232: faload         
        //   233: fmul           
        //   234: aload_1        
        //   235: iconst_3       
        //   236: faload         
        //   237: aload_0        
        //   238: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   241: iload           10
        //   243: iconst_1       
        //   244: iadd           
        //   245: faload         
        //   246: fmul           
        //   247: fsub           
        //   248: fastore        
        //   249: iinc            8, -4
        //   252: iinc            11, 1
        //   255: goto            178
        //   258: aload_0        
        //   259: aload_3        
        //   260: aload           4
        //   262: aload_0        
        //   263: getfield        com/jcraft/jorbis/Mdct.n:I
        //   266: iload           5
        //   268: iload           6
        //   270: iload           7
        //   272: invokespecial   com/jcraft/jorbis/Mdct.mdct_kernel:([F[FIIII)[F
        //   275: astore          8
        //   277: iload           5
        //   279: istore          10
        //   281: iload           6
        //   283: istore          11
        //   285: iload           6
        //   287: iload           5
        //   289: iadd           
        //   290: istore          13
        //   292: iload           13
        //   294: iconst_1       
        //   295: isub           
        //   296: istore          14
        //   298: iconst_0       
        //   299: iload           6
        //   301: if_icmpge       410
        //   304: aload           8
        //   306: iconst_0       
        //   307: faload         
        //   308: aload_0        
        //   309: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   312: iload           10
        //   314: iconst_1       
        //   315: iadd           
        //   316: faload         
        //   317: fmul           
        //   318: aload           8
        //   320: iconst_1       
        //   321: faload         
        //   322: aload_0        
        //   323: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   326: iload           10
        //   328: faload         
        //   329: fmul           
        //   330: fsub           
        //   331: fstore          16
        //   333: aload           8
        //   335: iconst_0       
        //   336: faload         
        //   337: aload_0        
        //   338: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   341: iload           10
        //   343: faload         
        //   344: fmul           
        //   345: aload           8
        //   347: iconst_1       
        //   348: faload         
        //   349: aload_0        
        //   350: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   353: iload           10
        //   355: iconst_1       
        //   356: iadd           
        //   357: faload         
        //   358: fmul           
        //   359: fadd           
        //   360: fneg           
        //   361: fstore          17
        //   363: aload_2        
        //   364: iconst_0       
        //   365: fload           16
        //   367: fneg           
        //   368: fastore        
        //   369: aload_2        
        //   370: iconst_m1      
        //   371: fload           16
        //   373: fastore        
        //   374: aload_2        
        //   375: iload           13
        //   377: fload           17
        //   379: fastore        
        //   380: aload_2        
        //   381: iload           14
        //   383: fload           17
        //   385: fastore        
        //   386: iinc            11, 1
        //   389: iinc            12, -1
        //   392: iinc            13, 1
        //   395: iinc            14, -1
        //   398: iinc            9, 2
        //   401: iinc            10, 2
        //   404: iinc            15, 1
        //   407: goto            298
        //   410: return         
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
    
    private float[] mdct_kernel(final float[] p0, final float[] p1, final int p2, final int p3, final int p4, final int p5) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: istore          7
        //     4: iload           5
        //     6: istore          9
        //     8: iload           4
        //    10: istore          10
        //    12: iconst_0       
        //    13: iload           5
        //    15: if_icmpge       134
        //    18: aload_1        
        //    19: iload           7
        //    21: faload         
        //    22: aload_1        
        //    23: iconst_0       
        //    24: faload         
        //    25: fsub           
        //    26: fstore          12
        //    28: aload_2        
        //    29: iload           9
        //    31: iconst_0       
        //    32: iadd           
        //    33: aload_1        
        //    34: iload           7
        //    36: iinc            7, 1
        //    39: faload         
        //    40: aload_1        
        //    41: iconst_0       
        //    42: iinc            8, 1
        //    45: faload         
        //    46: fadd           
        //    47: fastore        
        //    48: aload_1        
        //    49: iload           7
        //    51: faload         
        //    52: aload_1        
        //    53: iconst_0       
        //    54: faload         
        //    55: fsub           
        //    56: fstore          13
        //    58: iinc            10, -4
        //    61: aload_2        
        //    62: iconst_0       
        //    63: iinc            11, 1
        //    66: fload           12
        //    68: aload_0        
        //    69: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //    72: iconst_0       
        //    73: faload         
        //    74: fmul           
        //    75: fload           13
        //    77: aload_0        
        //    78: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //    81: iconst_1       
        //    82: faload         
        //    83: fmul           
        //    84: fadd           
        //    85: fastore        
        //    86: aload_2        
        //    87: iconst_0       
        //    88: fload           13
        //    90: aload_0        
        //    91: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //    94: iconst_0       
        //    95: faload         
        //    96: fmul           
        //    97: fload           12
        //    99: aload_0        
        //   100: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   103: iconst_1       
        //   104: faload         
        //   105: fmul           
        //   106: fsub           
        //   107: fastore        
        //   108: aload_2        
        //   109: iload           9
        //   111: iconst_0       
        //   112: iadd           
        //   113: aload_1        
        //   114: iload           7
        //   116: iinc            7, 1
        //   119: faload         
        //   120: aload_1        
        //   121: iconst_0       
        //   122: iinc            8, 1
        //   125: faload         
        //   126: fadd           
        //   127: fastore        
        //   128: iinc            11, 1
        //   131: goto            12
        //   134: iconst_0       
        //   135: aload_0        
        //   136: getfield        com/jcraft/jorbis/Mdct.log2n:I
        //   139: iconst_3       
        //   140: isub           
        //   141: if_icmpge       324
        //   144: iload_3        
        //   145: iconst_2       
        //   146: iushr          
        //   147: istore          12
        //   149: iload           4
        //   151: iconst_2       
        //   152: isub           
        //   153: istore          14
        //   155: iconst_0       
        //   156: iconst_0       
        //   157: if_icmpge       310
        //   160: iload           14
        //   162: istore          17
        //   164: iload           17
        //   166: iconst_0       
        //   167: isub           
        //   168: istore          9
        //   170: aload_0        
        //   171: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   174: iconst_0       
        //   175: faload         
        //   176: fstore          18
        //   178: aload_0        
        //   179: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   182: iconst_1       
        //   183: faload         
        //   184: fstore          20
        //   186: iinc            14, -2
        //   189: iinc            12, 1
        //   192: iconst_0       
        //   193: iconst_2       
        //   194: if_icmpge       301
        //   197: aload_2        
        //   198: iload           17
        //   200: faload         
        //   201: aload_2        
        //   202: iload           9
        //   204: faload         
        //   205: fsub           
        //   206: fstore          21
        //   208: aload_1        
        //   209: iload           17
        //   211: aload_2        
        //   212: iload           17
        //   214: faload         
        //   215: aload_2        
        //   216: iload           9
        //   218: faload         
        //   219: fadd           
        //   220: fastore        
        //   221: aload_2        
        //   222: iinc            17, 1
        //   225: iload           17
        //   227: faload         
        //   228: aload_2        
        //   229: iinc            9, 1
        //   232: iload           9
        //   234: faload         
        //   235: fsub           
        //   236: fstore          19
        //   238: aload_1        
        //   239: iload           17
        //   241: aload_2        
        //   242: iload           17
        //   244: faload         
        //   245: aload_2        
        //   246: iload           9
        //   248: faload         
        //   249: fadd           
        //   250: fastore        
        //   251: aload_1        
        //   252: iload           9
        //   254: fload           19
        //   256: fload           18
        //   258: fmul           
        //   259: fload           21
        //   261: fload           20
        //   263: fmul           
        //   264: fsub           
        //   265: fastore        
        //   266: aload_1        
        //   267: iload           9
        //   269: iconst_1       
        //   270: isub           
        //   271: fload           21
        //   273: fload           18
        //   275: fmul           
        //   276: fload           19
        //   278: fload           20
        //   280: fmul           
        //   281: fadd           
        //   282: fastore        
        //   283: iload           17
        //   285: iconst_0       
        //   286: isub           
        //   287: istore          17
        //   289: iload           9
        //   291: iconst_0       
        //   292: isub           
        //   293: istore          9
        //   295: iinc            22, 1
        //   298: goto            192
        //   301: iinc            12, -1
        //   304: iinc            16, 1
        //   307: goto            155
        //   310: aload_2        
        //   311: astore          15
        //   313: aload_1        
        //   314: astore_2       
        //   315: aload           15
        //   317: astore_1       
        //   318: iinc            11, 1
        //   321: goto            134
        //   324: iload_3        
        //   325: istore          11
        //   327: iload           4
        //   329: iconst_1       
        //   330: isub           
        //   331: istore          14
        //   333: iconst_0       
        //   334: iload           6
        //   336: if_icmpge       532
        //   339: aload_0        
        //   340: getfield        com/jcraft/jorbis/Mdct.bitrev:[I
        //   343: iconst_0       
        //   344: iinc            12, 1
        //   347: iaload         
        //   348: istore          16
        //   350: aload_0        
        //   351: getfield        com/jcraft/jorbis/Mdct.bitrev:[I
        //   354: iconst_0       
        //   355: iinc            12, 1
        //   358: iaload         
        //   359: istore          17
        //   361: aload_2        
        //   362: iconst_0       
        //   363: faload         
        //   364: aload_2        
        //   365: iload           17
        //   367: iconst_1       
        //   368: iadd           
        //   369: faload         
        //   370: fsub           
        //   371: fstore          18
        //   373: aload_2        
        //   374: iconst_m1      
        //   375: faload         
        //   376: aload_2        
        //   377: iload           17
        //   379: faload         
        //   380: fadd           
        //   381: fstore          19
        //   383: aload_2        
        //   384: iconst_0       
        //   385: faload         
        //   386: aload_2        
        //   387: iload           17
        //   389: iconst_1       
        //   390: iadd           
        //   391: faload         
        //   392: fadd           
        //   393: fstore          20
        //   395: aload_2        
        //   396: iconst_m1      
        //   397: faload         
        //   398: aload_2        
        //   399: iload           17
        //   401: faload         
        //   402: fsub           
        //   403: fstore          21
        //   405: fload           18
        //   407: aload_0        
        //   408: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   411: iconst_0       
        //   412: faload         
        //   413: fmul           
        //   414: fstore          22
        //   416: fload           19
        //   418: aload_0        
        //   419: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   422: iconst_0       
        //   423: iinc            11, 1
        //   426: faload         
        //   427: fmul           
        //   428: fstore          23
        //   430: fload           18
        //   432: aload_0        
        //   433: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   436: iconst_0       
        //   437: faload         
        //   438: fmul           
        //   439: fstore          24
        //   441: fload           19
        //   443: aload_0        
        //   444: getfield        com/jcraft/jorbis/Mdct.trig:[F
        //   447: iconst_0       
        //   448: iinc            11, 1
        //   451: faload         
        //   452: fmul           
        //   453: fstore          25
        //   455: aload_1        
        //   456: iconst_0       
        //   457: iinc            13, 1
        //   460: fload           20
        //   462: fload           24
        //   464: fadd           
        //   465: fload           23
        //   467: fadd           
        //   468: ldc             0.5
        //   470: fmul           
        //   471: fastore        
        //   472: aload_1        
        //   473: iload           14
        //   475: iinc            14, -1
        //   478: fload           21
        //   480: fneg           
        //   481: fload           25
        //   483: fadd           
        //   484: fload           22
        //   486: fsub           
        //   487: ldc             0.5
        //   489: fmul           
        //   490: fastore        
        //   491: aload_1        
        //   492: iconst_0       
        //   493: iinc            13, 1
        //   496: fload           21
        //   498: fload           25
        //   500: fadd           
        //   501: fload           22
        //   503: fsub           
        //   504: ldc             0.5
        //   506: fmul           
        //   507: fastore        
        //   508: aload_1        
        //   509: iload           14
        //   511: iinc            14, -1
        //   514: fload           20
        //   516: fload           24
        //   518: fsub           
        //   519: fload           23
        //   521: fsub           
        //   522: ldc             0.5
        //   524: fmul           
        //   525: fastore        
        //   526: iinc            15, 1
        //   529: goto            333
        //   532: aload_1        
        //   533: areturn        
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
