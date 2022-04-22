package com.jcraft.jorbis;

import com.jcraft.jogg.*;

class Floor0 extends FuncFloor
{
    float[] lsp;
    
    Floor0() {
        this.lsp = null;
    }
    
    @Override
    void pack(final Object o, final Buffer buffer) {
        final InfoFloor0 infoFloor0 = (InfoFloor0)o;
        buffer.write(infoFloor0.order, 8);
        buffer.write(infoFloor0.rate, 16);
        buffer.write(infoFloor0.barkmap, 16);
        buffer.write(infoFloor0.ampbits, 6);
        buffer.write(infoFloor0.ampdB, 8);
        buffer.write(infoFloor0.numbooks - 1, 4);
        while (0 < infoFloor0.numbooks) {
            buffer.write(infoFloor0.books[0], 8);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    Object unpack(final Info info, final Buffer buffer) {
        final InfoFloor0 infoFloor0 = new InfoFloor0();
        infoFloor0.order = buffer.read(8);
        infoFloor0.rate = buffer.read(16);
        infoFloor0.barkmap = buffer.read(16);
        infoFloor0.ampbits = buffer.read(6);
        infoFloor0.ampdB = buffer.read(8);
        infoFloor0.numbooks = buffer.read(4) + 1;
        if (infoFloor0.order < 1 || infoFloor0.rate < 1 || infoFloor0.barkmap < 1 || infoFloor0.numbooks < 1) {
            return null;
        }
        while (0 < infoFloor0.numbooks) {
            infoFloor0.books[0] = buffer.read(8);
            if (infoFloor0.books[0] < 0 || infoFloor0.books[0] >= info.books) {
                return null;
            }
            int n = 0;
            ++n;
        }
        return infoFloor0;
    }
    
    @Override
    Object look(final DspState dspState, final InfoMode infoMode, final Object o) {
        final Info vi = dspState.vi;
        final InfoFloor0 vi2 = (InfoFloor0)o;
        final LookFloor0 lookFloor0 = new LookFloor0();
        lookFloor0.m = vi2.order;
        lookFloor0.n = vi.blocksizes[infoMode.blockflag] / 2;
        lookFloor0.ln = vi2.barkmap;
        lookFloor0.vi = vi2;
        lookFloor0.lpclook.init(lookFloor0.ln, lookFloor0.m);
        final float n = lookFloor0.ln / toBARK((float)(vi2.rate / 2.0));
        lookFloor0.linearmap = new int[lookFloor0.n];
        while (0 < lookFloor0.n) {
            int ln = (int)Math.floor(toBARK((float)(vi2.rate / 2.0 / lookFloor0.n * 0)) * n);
            if (ln >= lookFloor0.ln) {
                ln = lookFloor0.ln;
            }
            lookFloor0.linearmap[0] = ln;
            int n2 = 0;
            ++n2;
        }
        return lookFloor0;
    }
    
    static float toBARK(final float n) {
        return (float)(13.1 * Math.atan(7.4E-4 * n) + 2.24 * Math.atan(n * n * 1.85E-8) + 1.0E-4 * n);
    }
    
    Object state(final Object o) {
        final EchstateFloor0 echstateFloor0 = new EchstateFloor0();
        final InfoFloor0 infoFloor0 = (InfoFloor0)o;
        echstateFloor0.codewords = new int[infoFloor0.order];
        echstateFloor0.curve = new float[infoFloor0.barkmap];
        echstateFloor0.frameno = -1L;
        return echstateFloor0;
    }
    
    @Override
    void free_info(final Object o) {
    }
    
    @Override
    void free_look(final Object o) {
    }
    
    @Override
    void free_state(final Object o) {
    }
    
    @Override
    int forward(final Block block, final Object o, final float[] array, final float[] array2, final Object o2) {
        return 0;
    }
    
    int inverse(final Block p0, final Object p1, final float[] p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: astore          4
        //     6: aload           4
        //     8: getfield        com/jcraft/jorbis/Floor0$LookFloor0.vi:Lcom/jcraft/jorbis/Floor0$InfoFloor0;
        //    11: astore          5
        //    13: aload_1        
        //    14: getfield        com/jcraft/jorbis/Block.opb:Lcom/jcraft/jogg/Buffer;
        //    17: aload           5
        //    19: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.ampbits:I
        //    22: invokevirtual   com/jcraft/jogg/Buffer.read:(I)I
        //    25: istore          6
        //    27: iload           6
        //    29: ifle            356
        //    32: iconst_1       
        //    33: aload           5
        //    35: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.ampbits:I
        //    38: ishl           
        //    39: iconst_1       
        //    40: isub           
        //    41: istore          7
        //    43: iload           6
        //    45: i2f            
        //    46: iload           7
        //    48: i2f            
        //    49: fdiv           
        //    50: aload           5
        //    52: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.ampdB:I
        //    55: i2f            
        //    56: fmul           
        //    57: fstore          8
        //    59: aload_1        
        //    60: getfield        com/jcraft/jorbis/Block.opb:Lcom/jcraft/jogg/Buffer;
        //    63: aload           5
        //    65: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.numbooks:I
        //    68: invokestatic    com/jcraft/jorbis/Util.ilog:(I)I
        //    71: invokevirtual   com/jcraft/jogg/Buffer.read:(I)I
        //    74: istore          9
        //    76: iload           9
        //    78: iconst_m1      
        //    79: if_icmpeq       356
        //    82: iload           9
        //    84: aload           5
        //    86: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.numbooks:I
        //    89: if_icmpge       356
        //    92: aload_0        
        //    93: dup            
        //    94: astore          10
        //    96: monitorenter   
        //    97: aload_0        
        //    98: getfield        com/jcraft/jorbis/Floor0.lsp:[F
        //   101: ifnull          117
        //   104: aload_0        
        //   105: getfield        com/jcraft/jorbis/Floor0.lsp:[F
        //   108: arraylength    
        //   109: aload           4
        //   111: getfield        com/jcraft/jorbis/Floor0$LookFloor0.m:I
        //   114: if_icmpge       131
        //   117: aload_0        
        //   118: aload           4
        //   120: getfield        com/jcraft/jorbis/Floor0$LookFloor0.m:I
        //   123: newarray        F
        //   125: putfield        com/jcraft/jorbis/Floor0.lsp:[F
        //   128: goto            153
        //   131: iconst_0       
        //   132: aload           4
        //   134: getfield        com/jcraft/jorbis/Floor0$LookFloor0.m:I
        //   137: if_icmpge       153
        //   140: aload_0        
        //   141: getfield        com/jcraft/jorbis/Floor0.lsp:[F
        //   144: iconst_0       
        //   145: fconst_0       
        //   146: fastore        
        //   147: iinc            11, 1
        //   150: goto            131
        //   153: aload_1        
        //   154: getfield        com/jcraft/jorbis/Block.vd:Lcom/jcraft/jorbis/DspState;
        //   157: getfield        com/jcraft/jorbis/DspState.fullbooks:[Lcom/jcraft/jorbis/CodeBook;
        //   160: aload           5
        //   162: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.books:[I
        //   165: iload           9
        //   167: iaload         
        //   168: aaload         
        //   169: astore          11
        //   171: fconst_0       
        //   172: fstore          12
        //   174: iconst_0       
        //   175: aload           4
        //   177: getfield        com/jcraft/jorbis/Floor0$LookFloor0.m:I
        //   180: if_icmpge       193
        //   183: aload_3        
        //   184: iconst_0       
        //   185: fconst_0       
        //   186: fastore        
        //   187: iinc            13, 1
        //   190: goto            174
        //   193: iconst_0       
        //   194: aload           4
        //   196: getfield        com/jcraft/jorbis/Floor0$LookFloor0.m:I
        //   199: if_icmpge       258
        //   202: aload           11
        //   204: aload_0        
        //   205: getfield        com/jcraft/jorbis/Floor0.lsp:[F
        //   208: iconst_0       
        //   209: aload_1        
        //   210: getfield        com/jcraft/jorbis/Block.opb:Lcom/jcraft/jogg/Buffer;
        //   213: iconst_1       
        //   214: iconst_m1      
        //   215: invokevirtual   com/jcraft/jorbis/CodeBook.decodevs:([FILcom/jcraft/jogg/Buffer;II)I
        //   218: iconst_m1      
        //   219: if_icmpne       246
        //   222: iconst_0       
        //   223: aload           4
        //   225: getfield        com/jcraft/jorbis/Floor0$LookFloor0.n:I
        //   228: if_icmpge       241
        //   231: aload_3        
        //   232: iconst_0       
        //   233: fconst_0       
        //   234: fastore        
        //   235: iinc            14, 1
        //   238: goto            222
        //   241: iconst_0       
        //   242: aload           10
        //   244: monitorexit    
        //   245: ireturn        
        //   246: iconst_0       
        //   247: aload           11
        //   249: getfield        com/jcraft/jorbis/CodeBook.dim:I
        //   252: iadd           
        //   253: istore          13
        //   255: goto            193
        //   258: iconst_0       
        //   259: aload           4
        //   261: getfield        com/jcraft/jorbis/Floor0$LookFloor0.m:I
        //   264: if_icmpge       307
        //   267: iconst_0       
        //   268: aload           11
        //   270: getfield        com/jcraft/jorbis/CodeBook.dim:I
        //   273: if_icmpge       296
        //   276: aload_0        
        //   277: getfield        com/jcraft/jorbis/Floor0.lsp:[F
        //   280: iconst_0       
        //   281: dup2           
        //   282: faload         
        //   283: fload           12
        //   285: fadd           
        //   286: fastore        
        //   287: iinc            14, 1
        //   290: iinc            13, 1
        //   293: goto            267
        //   296: aload_0        
        //   297: getfield        com/jcraft/jorbis/Floor0.lsp:[F
        //   300: iconst_m1      
        //   301: faload         
        //   302: fstore          12
        //   304: goto            258
        //   307: aload_3        
        //   308: aload           4
        //   310: getfield        com/jcraft/jorbis/Floor0$LookFloor0.linearmap:[I
        //   313: aload           4
        //   315: getfield        com/jcraft/jorbis/Floor0$LookFloor0.n:I
        //   318: aload           4
        //   320: getfield        com/jcraft/jorbis/Floor0$LookFloor0.ln:I
        //   323: aload_0        
        //   324: getfield        com/jcraft/jorbis/Floor0.lsp:[F
        //   327: aload           4
        //   329: getfield        com/jcraft/jorbis/Floor0$LookFloor0.m:I
        //   332: fload           8
        //   334: aload           5
        //   336: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.ampdB:I
        //   339: i2f            
        //   340: invokestatic    com/jcraft/jorbis/Lsp.lsp_to_curve:([F[III[FIFF)V
        //   343: iconst_1       
        //   344: aload           10
        //   346: monitorexit    
        //   347: ireturn        
        //   348: astore          15
        //   350: aload           10
        //   352: monitorexit    
        //   353: aload           15
        //   355: athrow         
        //   356: iconst_0       
        //   357: ireturn        
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
    
    @Override
    Object inverse1(final Block p0, final Object p1, final Object p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: astore          4
        //     6: aload           4
        //     8: getfield        com/jcraft/jorbis/Floor0$LookFloor0.vi:Lcom/jcraft/jorbis/Floor0$InfoFloor0;
        //    11: astore          5
        //    13: aconst_null    
        //    14: astore          6
        //    16: aload_3        
        //    17: instanceof      [F
        //    20: ifeq            32
        //    23: aload_3        
        //    24: checkcast       [F
        //    27: checkcast       [F
        //    30: astore          6
        //    32: aload_1        
        //    33: getfield        com/jcraft/jorbis/Block.opb:Lcom/jcraft/jogg/Buffer;
        //    36: aload           5
        //    38: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.ampbits:I
        //    41: invokevirtual   com/jcraft/jogg/Buffer.read:(I)I
        //    44: istore          7
        //    46: iload           7
        //    48: ifle            284
        //    51: iconst_1       
        //    52: aload           5
        //    54: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.ampbits:I
        //    57: ishl           
        //    58: iconst_1       
        //    59: isub           
        //    60: istore          8
        //    62: iload           7
        //    64: i2f            
        //    65: iload           8
        //    67: i2f            
        //    68: fdiv           
        //    69: aload           5
        //    71: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.ampdB:I
        //    74: i2f            
        //    75: fmul           
        //    76: fstore          9
        //    78: aload_1        
        //    79: getfield        com/jcraft/jorbis/Block.opb:Lcom/jcraft/jogg/Buffer;
        //    82: aload           5
        //    84: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.numbooks:I
        //    87: invokestatic    com/jcraft/jorbis/Util.ilog:(I)I
        //    90: invokevirtual   com/jcraft/jogg/Buffer.read:(I)I
        //    93: istore          10
        //    95: iload           10
        //    97: iconst_m1      
        //    98: if_icmpeq       284
        //   101: iload           10
        //   103: aload           5
        //   105: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.numbooks:I
        //   108: if_icmpge       284
        //   111: aload_1        
        //   112: getfield        com/jcraft/jorbis/Block.vd:Lcom/jcraft/jorbis/DspState;
        //   115: getfield        com/jcraft/jorbis/DspState.fullbooks:[Lcom/jcraft/jorbis/CodeBook;
        //   118: aload           5
        //   120: getfield        com/jcraft/jorbis/Floor0$InfoFloor0.books:[I
        //   123: iload           10
        //   125: iaload         
        //   126: aaload         
        //   127: astore          11
        //   129: fconst_0       
        //   130: fstore          12
        //   132: aload           6
        //   134: ifnull          150
        //   137: aload           6
        //   139: arraylength    
        //   140: aload           4
        //   142: getfield        com/jcraft/jorbis/Floor0$LookFloor0.m:I
        //   145: iconst_1       
        //   146: iadd           
        //   147: if_icmpge       164
        //   150: aload           4
        //   152: getfield        com/jcraft/jorbis/Floor0$LookFloor0.m:I
        //   155: iconst_1       
        //   156: iadd           
        //   157: newarray        F
        //   159: astore          6
        //   161: goto            182
        //   164: iconst_0       
        //   165: aload           6
        //   167: arraylength    
        //   168: if_icmpge       182
        //   171: aload           6
        //   173: iconst_0       
        //   174: fconst_0       
        //   175: fastore        
        //   176: iinc            13, 1
        //   179: goto            164
        //   182: iconst_0       
        //   183: aload           4
        //   185: getfield        com/jcraft/jorbis/Floor0$LookFloor0.m:I
        //   188: if_icmpge       226
        //   191: aload           11
        //   193: aload           6
        //   195: iconst_0       
        //   196: aload_1        
        //   197: getfield        com/jcraft/jorbis/Block.opb:Lcom/jcraft/jogg/Buffer;
        //   200: aload           11
        //   202: getfield        com/jcraft/jorbis/CodeBook.dim:I
        //   205: invokevirtual   com/jcraft/jorbis/CodeBook.decodev_set:([FILcom/jcraft/jogg/Buffer;I)I
        //   208: iconst_m1      
        //   209: if_icmpne       214
        //   212: aconst_null    
        //   213: areturn        
        //   214: iconst_0       
        //   215: aload           11
        //   217: getfield        com/jcraft/jorbis/CodeBook.dim:I
        //   220: iadd           
        //   221: istore          13
        //   223: goto            182
        //   226: iconst_0       
        //   227: aload           4
        //   229: getfield        com/jcraft/jorbis/Floor0$LookFloor0.m:I
        //   232: if_icmpge       271
        //   235: iconst_0       
        //   236: aload           11
        //   238: getfield        com/jcraft/jorbis/CodeBook.dim:I
        //   241: if_icmpge       262
        //   244: aload           6
        //   246: iconst_0       
        //   247: dup2           
        //   248: faload         
        //   249: fload           12
        //   251: fadd           
        //   252: fastore        
        //   253: iinc            14, 1
        //   256: iinc            13, 1
        //   259: goto            235
        //   262: aload           6
        //   264: iconst_m1      
        //   265: faload         
        //   266: fstore          12
        //   268: goto            226
        //   271: aload           6
        //   273: aload           4
        //   275: getfield        com/jcraft/jorbis/Floor0$LookFloor0.m:I
        //   278: fload           9
        //   280: fastore        
        //   281: aload           6
        //   283: areturn        
        //   284: aconst_null    
        //   285: areturn        
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
    
    @Override
    int inverse2(final Block block, final Object o, final Object o2, final float[] array) {
        final LookFloor0 lookFloor0 = (LookFloor0)o;
        final InfoFloor0 vi = lookFloor0.vi;
        if (o2 != null) {
            final float[] array2 = (float[])o2;
            Lsp.lsp_to_curve(array, lookFloor0.linearmap, lookFloor0.n, lookFloor0.ln, array2, lookFloor0.m, array2[lookFloor0.m], (float)vi.ampdB);
            return 1;
        }
        while (0 < lookFloor0.n) {
            array[0] = 0.0f;
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    static float fromdB(final float n) {
        return (float)Math.exp(n * 0.11512925);
    }
    
    static void lsp_to_lpc(final float[] array, final float[] array2, final int n) {
        final int n2 = n / 2;
        final float[] array3 = new float[n2];
        final float[] array4 = new float[n2];
        final float[] array5 = new float[n2 + 1];
        final float[] array6 = new float[n2 + 1];
        final float[] array7 = new float[n2];
        final float[] array8 = new float[n2];
        int n3 = 0;
        while (1 < n2) {
            array3[1] = (float)(-2.0 * Math.cos(array[2]));
            array4[1] = (float)(-2.0 * Math.cos(array[3]));
            ++n3;
        }
        int n4 = 0;
        while (0 < n2) {
            array5[0] = 0.0f;
            array6[0] = 1.0f;
            array7[0] = 0.0f;
            array8[0] = 1.0f;
            ++n4;
        }
        array5[0] = (array6[0] = 1.0f);
        while (1 < n + 1) {
            final float n5 = 0.0f;
            float n6 = 0.0f;
            float n7 = n5;
            while (0 < n2) {
                final float n8 = array3[0] * array6[0] + array5[0];
                array5[0] = array6[0];
                array6[0] = n7;
                n7 += n8;
                final float n9 = array4[0] * array8[0] + array7[0];
                array7[0] = array8[0];
                array8[0] = n6;
                n6 += n9;
                ++n4;
            }
            array2[0] = (n7 + array6[0] + n6 - array5[0]) / 2.0f;
            array6[0] = n7;
            array5[0] = n6;
            ++n3;
        }
    }
    
    static void lpc_to_curve(final float[] array, final float[] array2, final float n, final LookFloor0 lookFloor0, final String s, final int n2) {
        final float[] array3 = new float[Math.max(lookFloor0.ln * 2, lookFloor0.m * 2 + 2)];
        int n3 = 0;
        if (n == 0.0f) {
            while (0 < lookFloor0.n) {
                array[0] = 0.0f;
                ++n3;
            }
            return;
        }
        lookFloor0.lpclook.lpc_to_curve(array3, array2, n);
        while (0 < lookFloor0.n) {
            array[0] = array3[lookFloor0.linearmap[0]];
            ++n3;
        }
    }
    
    class EchstateFloor0
    {
        int[] codewords;
        float[] curve;
        long frameno;
        long codes;
        final Floor0 this$0;
        
        EchstateFloor0(final Floor0 this$0) {
            this.this$0 = this$0;
        }
    }
    
    class LookFloor0
    {
        int n;
        int ln;
        int m;
        int[] linearmap;
        InfoFloor0 vi;
        Lpc lpclook;
        final Floor0 this$0;
        
        LookFloor0(final Floor0 this$0) {
            this.this$0 = this$0;
            this.lpclook = new Lpc();
        }
    }
    
    class InfoFloor0
    {
        int order;
        int rate;
        int barkmap;
        int ampbits;
        int ampdB;
        int numbooks;
        int[] books;
        final Floor0 this$0;
        
        InfoFloor0(final Floor0 this$0) {
            this.this$0 = this$0;
            this.books = new int[16];
        }
    }
}
