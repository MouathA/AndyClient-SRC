package com.jcraft.jorbis;

import com.jcraft.jogg.*;

class Mapping0 extends FuncMapping
{
    float[][] pcmbundle;
    int[] zerobundle;
    int[] nonzero;
    Object[] floormemo;
    
    Mapping0() {
        this.pcmbundle = null;
        this.zerobundle = null;
        this.nonzero = null;
        this.floormemo = null;
    }
    
    @Override
    void free_info(final Object o) {
    }
    
    @Override
    void free_look(final Object o) {
    }
    
    @Override
    Object look(final DspState p0, final InfoMode p1, final Object p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: astore          4
        //     6: new             Lcom/jcraft/jorbis/Mapping0$LookMapping0;
        //     9: dup            
        //    10: aload_0        
        //    11: invokespecial   com/jcraft/jorbis/Mapping0$LookMapping0.<init>:(Lcom/jcraft/jorbis/Mapping0;)V
        //    14: astore          5
        //    16: aload           5
        //    18: aload_3        
        //    19: checkcast       Lcom/jcraft/jorbis/Mapping0$InfoMapping0;
        //    22: dup_x1         
        //    23: putfield        com/jcraft/jorbis/Mapping0$LookMapping0.map:Lcom/jcraft/jorbis/Mapping0$InfoMapping0;
        //    26: astore          6
        //    28: aload           5
        //    30: aload_2        
        //    31: putfield        com/jcraft/jorbis/Mapping0$LookMapping0.mode:Lcom/jcraft/jorbis/InfoMode;
        //    34: aload           5
        //    36: aload           6
        //    38: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.submaps:I
        //    41: anewarray       Ljava/lang/Object;
        //    44: putfield        com/jcraft/jorbis/Mapping0$LookMapping0.time_look:[Ljava/lang/Object;
        //    47: aload           5
        //    49: aload           6
        //    51: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.submaps:I
        //    54: anewarray       Ljava/lang/Object;
        //    57: putfield        com/jcraft/jorbis/Mapping0$LookMapping0.floor_look:[Ljava/lang/Object;
        //    60: aload           5
        //    62: aload           6
        //    64: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.submaps:I
        //    67: anewarray       Ljava/lang/Object;
        //    70: putfield        com/jcraft/jorbis/Mapping0$LookMapping0.residue_look:[Ljava/lang/Object;
        //    73: aload           5
        //    75: aload           6
        //    77: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.submaps:I
        //    80: anewarray       Lcom/jcraft/jorbis/FuncTime;
        //    83: putfield        com/jcraft/jorbis/Mapping0$LookMapping0.time_func:[Lcom/jcraft/jorbis/FuncTime;
        //    86: aload           5
        //    88: aload           6
        //    90: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.submaps:I
        //    93: anewarray       Lcom/jcraft/jorbis/FuncFloor;
        //    96: putfield        com/jcraft/jorbis/Mapping0$LookMapping0.floor_func:[Lcom/jcraft/jorbis/FuncFloor;
        //    99: aload           5
        //   101: aload           6
        //   103: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.submaps:I
        //   106: anewarray       Lcom/jcraft/jorbis/FuncResidue;
        //   109: putfield        com/jcraft/jorbis/Mapping0$LookMapping0.residue_func:[Lcom/jcraft/jorbis/FuncResidue;
        //   112: iconst_0       
        //   113: aload           6
        //   115: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.submaps:I
        //   118: if_icmpge       292
        //   121: aload           6
        //   123: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.timesubmap:[I
        //   126: iconst_0       
        //   127: iaload         
        //   128: istore          8
        //   130: aload           6
        //   132: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.floorsubmap:[I
        //   135: iconst_0       
        //   136: iaload         
        //   137: istore          9
        //   139: aload           6
        //   141: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.residuesubmap:[I
        //   144: iconst_0       
        //   145: iaload         
        //   146: istore          10
        //   148: aload           5
        //   150: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.time_func:[Lcom/jcraft/jorbis/FuncTime;
        //   153: iconst_0       
        //   154: getstatic       com/jcraft/jorbis/FuncTime.time_P:[Lcom/jcraft/jorbis/FuncTime;
        //   157: aload           4
        //   159: getfield        com/jcraft/jorbis/Info.time_type:[I
        //   162: iload           8
        //   164: iaload         
        //   165: aaload         
        //   166: aastore        
        //   167: aload           5
        //   169: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.time_look:[Ljava/lang/Object;
        //   172: iconst_0       
        //   173: aload           5
        //   175: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.time_func:[Lcom/jcraft/jorbis/FuncTime;
        //   178: iconst_0       
        //   179: aaload         
        //   180: aload_1        
        //   181: aload_2        
        //   182: aload           4
        //   184: getfield        com/jcraft/jorbis/Info.time_param:[Ljava/lang/Object;
        //   187: iload           8
        //   189: aaload         
        //   190: invokevirtual   com/jcraft/jorbis/FuncTime.look:(Lcom/jcraft/jorbis/DspState;Lcom/jcraft/jorbis/InfoMode;Ljava/lang/Object;)Ljava/lang/Object;
        //   193: aastore        
        //   194: aload           5
        //   196: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.floor_func:[Lcom/jcraft/jorbis/FuncFloor;
        //   199: iconst_0       
        //   200: getstatic       com/jcraft/jorbis/FuncFloor.floor_P:[Lcom/jcraft/jorbis/FuncFloor;
        //   203: aload           4
        //   205: getfield        com/jcraft/jorbis/Info.floor_type:[I
        //   208: iload           9
        //   210: iaload         
        //   211: aaload         
        //   212: aastore        
        //   213: aload           5
        //   215: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.floor_look:[Ljava/lang/Object;
        //   218: iconst_0       
        //   219: aload           5
        //   221: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.floor_func:[Lcom/jcraft/jorbis/FuncFloor;
        //   224: iconst_0       
        //   225: aaload         
        //   226: aload_1        
        //   227: aload_2        
        //   228: aload           4
        //   230: getfield        com/jcraft/jorbis/Info.floor_param:[Ljava/lang/Object;
        //   233: iload           9
        //   235: aaload         
        //   236: invokevirtual   com/jcraft/jorbis/FuncFloor.look:(Lcom/jcraft/jorbis/DspState;Lcom/jcraft/jorbis/InfoMode;Ljava/lang/Object;)Ljava/lang/Object;
        //   239: aastore        
        //   240: aload           5
        //   242: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.residue_func:[Lcom/jcraft/jorbis/FuncResidue;
        //   245: iconst_0       
        //   246: getstatic       com/jcraft/jorbis/FuncResidue.residue_P:[Lcom/jcraft/jorbis/FuncResidue;
        //   249: aload           4
        //   251: getfield        com/jcraft/jorbis/Info.residue_type:[I
        //   254: iload           10
        //   256: iaload         
        //   257: aaload         
        //   258: aastore        
        //   259: aload           5
        //   261: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.residue_look:[Ljava/lang/Object;
        //   264: iconst_0       
        //   265: aload           5
        //   267: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.residue_func:[Lcom/jcraft/jorbis/FuncResidue;
        //   270: iconst_0       
        //   271: aaload         
        //   272: aload_1        
        //   273: aload_2        
        //   274: aload           4
        //   276: getfield        com/jcraft/jorbis/Info.residue_param:[Ljava/lang/Object;
        //   279: iload           10
        //   281: aaload         
        //   282: invokevirtual   com/jcraft/jorbis/FuncResidue.look:(Lcom/jcraft/jorbis/DspState;Lcom/jcraft/jorbis/InfoMode;Ljava/lang/Object;)Ljava/lang/Object;
        //   285: aastore        
        //   286: iinc            7, 1
        //   289: goto            112
        //   292: aload           4
        //   294: getfield        com/jcraft/jorbis/Info.psys:I
        //   297: ifeq            304
        //   300: aload_1        
        //   301: getfield        com/jcraft/jorbis/DspState.analysisp:I
        //   304: aload           5
        //   306: aload           4
        //   308: getfield        com/jcraft/jorbis/Info.channels:I
        //   311: putfield        com/jcraft/jorbis/Mapping0$LookMapping0.ch:I
        //   314: aload           5
        //   316: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0304 (coming from #0301).
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
    
    @Override
    void pack(final Info info, final Object o, final Buffer buffer) {
        final InfoMapping0 infoMapping0 = (InfoMapping0)o;
        if (infoMapping0.submaps > 1) {
            buffer.write(1, 1);
            buffer.write(infoMapping0.submaps - 1, 4);
        }
        else {
            buffer.write(0, 1);
        }
        int n = 0;
        if (infoMapping0.coupling_steps > 0) {
            buffer.write(1, 1);
            buffer.write(infoMapping0.coupling_steps - 1, 8);
            while (0 < infoMapping0.coupling_steps) {
                buffer.write(infoMapping0.coupling_mag[0], Util.ilog2(info.channels));
                buffer.write(infoMapping0.coupling_ang[0], Util.ilog2(info.channels));
                ++n;
            }
        }
        else {
            buffer.write(0, 1);
        }
        buffer.write(0, 2);
        if (infoMapping0.submaps > 1) {
            while (0 < info.channels) {
                buffer.write(infoMapping0.chmuxlist[0], 4);
                ++n;
            }
        }
        while (0 < infoMapping0.submaps) {
            buffer.write(infoMapping0.timesubmap[0], 8);
            buffer.write(infoMapping0.floorsubmap[0], 8);
            buffer.write(infoMapping0.residuesubmap[0], 8);
            ++n;
        }
    }
    
    @Override
    Object unpack(final Info info, final Buffer buffer) {
        final InfoMapping0 infoMapping0 = new InfoMapping0();
        if (buffer.read(1) != 0) {
            infoMapping0.submaps = buffer.read(4) + 1;
        }
        else {
            infoMapping0.submaps = 1;
        }
        int n5 = 0;
        if (buffer.read(1) != 0) {
            infoMapping0.coupling_steps = buffer.read(8) + 1;
            while (0 < infoMapping0.coupling_steps) {
                final int[] coupling_mag = infoMapping0.coupling_mag;
                final int n = 0;
                final int read = buffer.read(Util.ilog2(info.channels));
                coupling_mag[n] = read;
                final int n2 = read;
                final int[] coupling_ang = infoMapping0.coupling_ang;
                final int n3 = 0;
                final int read2 = buffer.read(Util.ilog2(info.channels));
                coupling_ang[n3] = read2;
                final int n4 = read2;
                if (n2 < 0 || n4 < 0 || n2 == n4 || n2 >= info.channels || n4 >= info.channels) {
                    infoMapping0.free();
                    return null;
                }
                ++n5;
            }
        }
        if (buffer.read(2) > 0) {
            infoMapping0.free();
            return null;
        }
        if (infoMapping0.submaps > 1) {
            while (0 < info.channels) {
                infoMapping0.chmuxlist[0] = buffer.read(4);
                if (infoMapping0.chmuxlist[0] >= infoMapping0.submaps) {
                    infoMapping0.free();
                    return null;
                }
                ++n5;
            }
        }
        while (0 < infoMapping0.submaps) {
            infoMapping0.timesubmap[0] = buffer.read(8);
            if (infoMapping0.timesubmap[0] >= info.times) {
                infoMapping0.free();
                return null;
            }
            infoMapping0.floorsubmap[0] = buffer.read(8);
            if (infoMapping0.floorsubmap[0] >= info.floors) {
                infoMapping0.free();
                return null;
            }
            infoMapping0.residuesubmap[0] = buffer.read(8);
            if (infoMapping0.residuesubmap[0] >= info.residues) {
                infoMapping0.free();
                return null;
            }
            ++n5;
        }
        return infoMapping0;
    }
    
    @Override
    synchronized int inverse(final Block p0, final Object p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/jcraft/jorbis/Block.vd:Lcom/jcraft/jorbis/DspState;
        //     4: astore_3       
        //     5: aload_3        
        //     6: getfield        com/jcraft/jorbis/DspState.vi:Lcom/jcraft/jorbis/Info;
        //     9: astore          4
        //    11: aload_2        
        //    12: checkcast       Lcom/jcraft/jorbis/Mapping0$LookMapping0;
        //    15: astore          5
        //    17: aload           5
        //    19: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.map:Lcom/jcraft/jorbis/Mapping0$InfoMapping0;
        //    22: astore          6
        //    24: aload           5
        //    26: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.mode:Lcom/jcraft/jorbis/InfoMode;
        //    29: astore          7
        //    31: aload_1        
        //    32: aload           4
        //    34: getfield        com/jcraft/jorbis/Info.blocksizes:[I
        //    37: aload_1        
        //    38: getfield        com/jcraft/jorbis/Block.W:I
        //    41: iaload         
        //    42: dup_x1         
        //    43: putfield        com/jcraft/jorbis/Block.pcmend:I
        //    46: istore          8
        //    48: aload_3        
        //    49: getfield        com/jcraft/jorbis/DspState.window:[[[[[F
        //    52: aload_1        
        //    53: getfield        com/jcraft/jorbis/Block.W:I
        //    56: aaload         
        //    57: aload_1        
        //    58: getfield        com/jcraft/jorbis/Block.lW:I
        //    61: aaload         
        //    62: aload_1        
        //    63: getfield        com/jcraft/jorbis/Block.nW:I
        //    66: aaload         
        //    67: aload           7
        //    69: getfield        com/jcraft/jorbis/InfoMode.windowtype:I
        //    72: aaload         
        //    73: astore          9
        //    75: aload_0        
        //    76: getfield        com/jcraft/jorbis/Mapping0.pcmbundle:[[F
        //    79: ifnull          95
        //    82: aload_0        
        //    83: getfield        com/jcraft/jorbis/Mapping0.pcmbundle:[[F
        //    86: arraylength    
        //    87: aload           4
        //    89: getfield        com/jcraft/jorbis/Info.channels:I
        //    92: if_icmpge       141
        //    95: aload_0        
        //    96: aload           4
        //    98: getfield        com/jcraft/jorbis/Info.channels:I
        //   101: anewarray       [F
        //   104: putfield        com/jcraft/jorbis/Mapping0.pcmbundle:[[F
        //   107: aload_0        
        //   108: aload           4
        //   110: getfield        com/jcraft/jorbis/Info.channels:I
        //   113: newarray        I
        //   115: putfield        com/jcraft/jorbis/Mapping0.nonzero:[I
        //   118: aload_0        
        //   119: aload           4
        //   121: getfield        com/jcraft/jorbis/Info.channels:I
        //   124: newarray        I
        //   126: putfield        com/jcraft/jorbis/Mapping0.zerobundle:[I
        //   129: aload_0        
        //   130: aload           4
        //   132: getfield        com/jcraft/jorbis/Info.channels:I
        //   135: anewarray       Ljava/lang/Object;
        //   138: putfield        com/jcraft/jorbis/Mapping0.floormemo:[Ljava/lang/Object;
        //   141: iconst_0       
        //   142: aload           4
        //   144: getfield        com/jcraft/jorbis/Info.channels:I
        //   147: if_icmpge       248
        //   150: aload_1        
        //   151: getfield        com/jcraft/jorbis/Block.pcm:[[F
        //   154: iconst_0       
        //   155: aaload         
        //   156: astore          11
        //   158: aload           6
        //   160: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.chmuxlist:[I
        //   163: iconst_0       
        //   164: iaload         
        //   165: istore          12
        //   167: aload_0        
        //   168: getfield        com/jcraft/jorbis/Mapping0.floormemo:[Ljava/lang/Object;
        //   171: iconst_0       
        //   172: aload           5
        //   174: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.floor_func:[Lcom/jcraft/jorbis/FuncFloor;
        //   177: iconst_0       
        //   178: aaload         
        //   179: aload_1        
        //   180: aload           5
        //   182: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.floor_look:[Ljava/lang/Object;
        //   185: iconst_0       
        //   186: aaload         
        //   187: aload_0        
        //   188: getfield        com/jcraft/jorbis/Mapping0.floormemo:[Ljava/lang/Object;
        //   191: iconst_0       
        //   192: aaload         
        //   193: invokevirtual   com/jcraft/jorbis/FuncFloor.inverse1:(Lcom/jcraft/jorbis/Block;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   196: aastore        
        //   197: aload_0        
        //   198: getfield        com/jcraft/jorbis/Mapping0.floormemo:[Ljava/lang/Object;
        //   201: iconst_0       
        //   202: aaload         
        //   203: ifnull          216
        //   206: aload_0        
        //   207: getfield        com/jcraft/jorbis/Mapping0.nonzero:[I
        //   210: iconst_0       
        //   211: iconst_1       
        //   212: iastore        
        //   213: goto            223
        //   216: aload_0        
        //   217: getfield        com/jcraft/jorbis/Mapping0.nonzero:[I
        //   220: iconst_0       
        //   221: iconst_0       
        //   222: iastore        
        //   223: iconst_0       
        //   224: iload           8
        //   226: iconst_2       
        //   227: idiv           
        //   228: if_icmpge       242
        //   231: aload           11
        //   233: iconst_0       
        //   234: fconst_0       
        //   235: fastore        
        //   236: iinc            13, 1
        //   239: goto            223
        //   242: iinc            10, 1
        //   245: goto            141
        //   248: iconst_0       
        //   249: aload           6
        //   251: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.coupling_steps:I
        //   254: if_icmpge       319
        //   257: aload_0        
        //   258: getfield        com/jcraft/jorbis/Mapping0.nonzero:[I
        //   261: aload           6
        //   263: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.coupling_mag:[I
        //   266: iconst_0       
        //   267: iaload         
        //   268: iaload         
        //   269: ifne            287
        //   272: aload_0        
        //   273: getfield        com/jcraft/jorbis/Mapping0.nonzero:[I
        //   276: aload           6
        //   278: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.coupling_ang:[I
        //   281: iconst_0       
        //   282: iaload         
        //   283: iaload         
        //   284: ifeq            313
        //   287: aload_0        
        //   288: getfield        com/jcraft/jorbis/Mapping0.nonzero:[I
        //   291: aload           6
        //   293: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.coupling_mag:[I
        //   296: iconst_0       
        //   297: iaload         
        //   298: iconst_1       
        //   299: iastore        
        //   300: aload_0        
        //   301: getfield        com/jcraft/jorbis/Mapping0.nonzero:[I
        //   304: aload           6
        //   306: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.coupling_ang:[I
        //   309: iconst_0       
        //   310: iaload         
        //   311: iconst_1       
        //   312: iastore        
        //   313: iinc            10, 1
        //   316: goto            248
        //   319: iconst_0       
        //   320: aload           6
        //   322: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.submaps:I
        //   325: if_icmpge       429
        //   328: iconst_0       
        //   329: aload           4
        //   331: getfield        com/jcraft/jorbis/Info.channels:I
        //   334: if_icmpge       395
        //   337: aload           6
        //   339: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.chmuxlist:[I
        //   342: iconst_0       
        //   343: iaload         
        //   344: iconst_0       
        //   345: if_icmpne       389
        //   348: aload_0        
        //   349: getfield        com/jcraft/jorbis/Mapping0.nonzero:[I
        //   352: iconst_0       
        //   353: iaload         
        //   354: ifeq            367
        //   357: aload_0        
        //   358: getfield        com/jcraft/jorbis/Mapping0.zerobundle:[I
        //   361: iconst_0       
        //   362: iconst_1       
        //   363: iastore        
        //   364: goto            374
        //   367: aload_0        
        //   368: getfield        com/jcraft/jorbis/Mapping0.zerobundle:[I
        //   371: iconst_0       
        //   372: iconst_0       
        //   373: iastore        
        //   374: aload_0        
        //   375: getfield        com/jcraft/jorbis/Mapping0.pcmbundle:[[F
        //   378: iconst_0       
        //   379: iinc            11, 1
        //   382: aload_1        
        //   383: getfield        com/jcraft/jorbis/Block.pcm:[[F
        //   386: iconst_0       
        //   387: aaload         
        //   388: aastore        
        //   389: iinc            12, 1
        //   392: goto            328
        //   395: aload           5
        //   397: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.residue_func:[Lcom/jcraft/jorbis/FuncResidue;
        //   400: iconst_0       
        //   401: aaload         
        //   402: aload_1        
        //   403: aload           5
        //   405: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.residue_look:[Ljava/lang/Object;
        //   408: iconst_0       
        //   409: aaload         
        //   410: aload_0        
        //   411: getfield        com/jcraft/jorbis/Mapping0.pcmbundle:[[F
        //   414: aload_0        
        //   415: getfield        com/jcraft/jorbis/Mapping0.zerobundle:[I
        //   418: iconst_0       
        //   419: invokevirtual   com/jcraft/jorbis/FuncResidue.inverse:(Lcom/jcraft/jorbis/Block;Ljava/lang/Object;[[F[II)I
        //   422: pop            
        //   423: iinc            10, 1
        //   426: goto            319
        //   429: aload           6
        //   431: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.coupling_steps:I
        //   434: iconst_1       
        //   435: isub           
        //   436: istore          10
        //   438: iconst_0       
        //   439: iflt            592
        //   442: aload_1        
        //   443: getfield        com/jcraft/jorbis/Block.pcm:[[F
        //   446: aload           6
        //   448: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.coupling_mag:[I
        //   451: iconst_0       
        //   452: iaload         
        //   453: aaload         
        //   454: astore          11
        //   456: aload_1        
        //   457: getfield        com/jcraft/jorbis/Block.pcm:[[F
        //   460: aload           6
        //   462: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.coupling_ang:[I
        //   465: iconst_0       
        //   466: iaload         
        //   467: aaload         
        //   468: astore          12
        //   470: iconst_0       
        //   471: iload           8
        //   473: iconst_2       
        //   474: idiv           
        //   475: if_icmpge       586
        //   478: aload           11
        //   480: iconst_0       
        //   481: faload         
        //   482: fstore          14
        //   484: aload           12
        //   486: iconst_0       
        //   487: faload         
        //   488: fstore          15
        //   490: fload           14
        //   492: fconst_0       
        //   493: fcmpl          
        //   494: ifle            540
        //   497: fload           15
        //   499: fconst_0       
        //   500: fcmpl          
        //   501: ifle            522
        //   504: aload           11
        //   506: iconst_0       
        //   507: fload           14
        //   509: fastore        
        //   510: aload           12
        //   512: iconst_0       
        //   513: fload           14
        //   515: fload           15
        //   517: fsub           
        //   518: fastore        
        //   519: goto            580
        //   522: aload           12
        //   524: iconst_0       
        //   525: fload           14
        //   527: fastore        
        //   528: aload           11
        //   530: iconst_0       
        //   531: fload           14
        //   533: fload           15
        //   535: fadd           
        //   536: fastore        
        //   537: goto            580
        //   540: fload           15
        //   542: fconst_0       
        //   543: fcmpl          
        //   544: ifle            565
        //   547: aload           11
        //   549: iconst_0       
        //   550: fload           14
        //   552: fastore        
        //   553: aload           12
        //   555: iconst_0       
        //   556: fload           14
        //   558: fload           15
        //   560: fadd           
        //   561: fastore        
        //   562: goto            580
        //   565: aload           12
        //   567: iconst_0       
        //   568: fload           14
        //   570: fastore        
        //   571: aload           11
        //   573: iconst_0       
        //   574: fload           14
        //   576: fload           15
        //   578: fsub           
        //   579: fastore        
        //   580: iinc            13, 1
        //   583: goto            470
        //   586: iinc            10, -1
        //   589: goto            438
        //   592: iconst_0       
        //   593: aload           4
        //   595: getfield        com/jcraft/jorbis/Info.channels:I
        //   598: if_icmpge       651
        //   601: aload_1        
        //   602: getfield        com/jcraft/jorbis/Block.pcm:[[F
        //   605: iconst_0       
        //   606: aaload         
        //   607: astore          11
        //   609: aload           6
        //   611: getfield        com/jcraft/jorbis/Mapping0$InfoMapping0.chmuxlist:[I
        //   614: iconst_0       
        //   615: iaload         
        //   616: istore          12
        //   618: aload           5
        //   620: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.floor_func:[Lcom/jcraft/jorbis/FuncFloor;
        //   623: iconst_0       
        //   624: aaload         
        //   625: aload_1        
        //   626: aload           5
        //   628: getfield        com/jcraft/jorbis/Mapping0$LookMapping0.floor_look:[Ljava/lang/Object;
        //   631: iconst_0       
        //   632: aaload         
        //   633: aload_0        
        //   634: getfield        com/jcraft/jorbis/Mapping0.floormemo:[Ljava/lang/Object;
        //   637: iconst_0       
        //   638: aaload         
        //   639: aload           11
        //   641: invokevirtual   com/jcraft/jorbis/FuncFloor.inverse2:(Lcom/jcraft/jorbis/Block;Ljava/lang/Object;Ljava/lang/Object;[F)I
        //   644: pop            
        //   645: iinc            10, 1
        //   648: goto            592
        //   651: iconst_0       
        //   652: aload           4
        //   654: getfield        com/jcraft/jorbis/Info.channels:I
        //   657: if_icmpge       695
        //   660: aload_1        
        //   661: getfield        com/jcraft/jorbis/Block.pcm:[[F
        //   664: iconst_0       
        //   665: aaload         
        //   666: astore          11
        //   668: aload_3        
        //   669: getfield        com/jcraft/jorbis/DspState.transform:[[Ljava/lang/Object;
        //   672: aload_1        
        //   673: getfield        com/jcraft/jorbis/Block.W:I
        //   676: aaload         
        //   677: iconst_0       
        //   678: aaload         
        //   679: checkcast       Lcom/jcraft/jorbis/Mdct;
        //   682: aload           11
        //   684: aload           11
        //   686: invokevirtual   com/jcraft/jorbis/Mdct.backward:([F[F)V
        //   689: iinc            10, 1
        //   692: goto            651
        //   695: iconst_0       
        //   696: aload           4
        //   698: getfield        com/jcraft/jorbis/Info.channels:I
        //   701: if_icmpge       770
        //   704: aload_1        
        //   705: getfield        com/jcraft/jorbis/Block.pcm:[[F
        //   708: iconst_0       
        //   709: aaload         
        //   710: astore          11
        //   712: aload_0        
        //   713: getfield        com/jcraft/jorbis/Mapping0.nonzero:[I
        //   716: iconst_0       
        //   717: iaload         
        //   718: ifeq            747
        //   721: iconst_0       
        //   722: iload           8
        //   724: if_icmpge       744
        //   727: aload           11
        //   729: iconst_0       
        //   730: dup2           
        //   731: faload         
        //   732: aload           9
        //   734: iconst_0       
        //   735: faload         
        //   736: fmul           
        //   737: fastore        
        //   738: iinc            12, 1
        //   741: goto            721
        //   744: goto            764
        //   747: iconst_0       
        //   748: iload           8
        //   750: if_icmpge       764
        //   753: aload           11
        //   755: iconst_0       
        //   756: fconst_0       
        //   757: fastore        
        //   758: iinc            12, 1
        //   761: goto            747
        //   764: iinc            10, 1
        //   767: goto            695
        //   770: iconst_0       
        //   771: ireturn        
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
    
    class LookMapping0
    {
        InfoMode mode;
        InfoMapping0 map;
        Object[] time_look;
        Object[] floor_look;
        Object[] floor_state;
        Object[] residue_look;
        PsyLook[] psy_look;
        FuncTime[] time_func;
        FuncFloor[] floor_func;
        FuncResidue[] residue_func;
        int ch;
        float[][] decay;
        int lastframe;
        final Mapping0 this$0;
        
        LookMapping0(final Mapping0 this$0) {
            this.this$0 = this$0;
        }
    }
    
    class InfoMapping0
    {
        int submaps;
        int[] chmuxlist;
        int[] timesubmap;
        int[] floorsubmap;
        int[] residuesubmap;
        int[] psysubmap;
        int coupling_steps;
        int[] coupling_mag;
        int[] coupling_ang;
        final Mapping0 this$0;
        
        InfoMapping0(final Mapping0 this$0) {
            this.this$0 = this$0;
            this.chmuxlist = new int[256];
            this.timesubmap = new int[16];
            this.floorsubmap = new int[16];
            this.residuesubmap = new int[16];
            this.psysubmap = new int[16];
            this.coupling_mag = new int[256];
            this.coupling_ang = new int[256];
        }
        
        void free() {
            this.chmuxlist = null;
            this.timesubmap = null;
            this.floorsubmap = null;
            this.residuesubmap = null;
            this.psysubmap = null;
            this.coupling_mag = null;
            this.coupling_ang = null;
        }
    }
}
