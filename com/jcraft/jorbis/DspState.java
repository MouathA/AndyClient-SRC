package com.jcraft.jorbis;

public class DspState
{
    static final float M_PI = 3.1415927f;
    static final int VI_TRANSFORMB = 1;
    static final int VI_WINDOWB = 1;
    int analysisp;
    Info vi;
    int modebits;
    float[][] pcm;
    int pcm_storage;
    int pcm_current;
    int pcm_returned;
    float[] multipliers;
    int envelope_storage;
    int envelope_current;
    int eofflag;
    int lW;
    int W;
    int nW;
    int centerW;
    long granulepos;
    long sequence;
    long glue_bits;
    long time_bits;
    long floor_bits;
    long res_bits;
    float[][][][][] window;
    Object[][] transform;
    CodeBook[] fullbooks;
    Object[] mode;
    byte[] header;
    byte[] header1;
    byte[] header2;
    
    public DspState() {
        this.transform = new Object[2][];
        this.window = new float[2][][][][];
        (this.window[0] = new float[2][][][])[0] = new float[2][][];
        this.window[0][1] = new float[2][][];
        this.window[0][0][0] = new float[2][];
        this.window[0][0][1] = new float[2][];
        this.window[0][1][0] = new float[2][];
        this.window[0][1][1] = new float[2][];
        (this.window[1] = new float[2][][][])[0] = new float[2][][];
        this.window[1][1] = new float[2][][];
        this.window[1][0][0] = new float[2][];
        this.window[1][0][1] = new float[2][];
        this.window[1][1][0] = new float[2][];
        this.window[1][1][1] = new float[2][];
    }
    
    static float[] window(final int p0, final int p1, final int p2, final int p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: newarray        F
        //     3: astore          4
        //     5: iload_0        
        //     6: lookupswitch {
        //                0: 24
        //          default: 222
        //        }
        //    24: iload_1        
        //    25: iconst_4       
        //    26: idiv           
        //    27: iload_2        
        //    28: iconst_2       
        //    29: idiv           
        //    30: isub           
        //    31: istore          5
        //    33: iload_1        
        //    34: iload_1        
        //    35: iconst_4       
        //    36: idiv           
        //    37: isub           
        //    38: iload_3        
        //    39: iconst_2       
        //    40: idiv           
        //    41: isub           
        //    42: istore          6
        //    44: iconst_0       
        //    45: iload_2        
        //    46: if_icmpge       119
        //    49: iconst_0       
        //    50: i2d            
        //    51: ldc2_w          0.5
        //    54: dadd           
        //    55: iload_2        
        //    56: i2d            
        //    57: ddiv           
        //    58: ldc2_w          3.1415927410125732
        //    61: dmul           
        //    62: ldc2_w          2.0
        //    65: ddiv           
        //    66: d2f            
        //    67: fstore          8
        //    69: fload           8
        //    71: f2d            
        //    72: invokestatic    java/lang/Math.sin:(D)D
        //    75: d2f            
        //    76: fstore          8
        //    78: fload           8
        //    80: fload           8
        //    82: fmul           
        //    83: fstore          8
        //    85: fload           8
        //    87: f2d            
        //    88: ldc2_w          1.5707963705062866
        //    91: dmul           
        //    92: d2f            
        //    93: fstore          8
        //    95: fload           8
        //    97: f2d            
        //    98: invokestatic    java/lang/Math.sin:(D)D
        //   101: d2f            
        //   102: fstore          8
        //   104: aload           4
        //   106: iconst_0       
        //   107: iload           5
        //   109: iadd           
        //   110: fload           8
        //   112: fastore        
        //   113: iinc            7, 1
        //   116: goto            44
        //   119: iload           5
        //   121: iload_2        
        //   122: iadd           
        //   123: istore          7
        //   125: iconst_0       
        //   126: iload           6
        //   128: if_icmpge       142
        //   131: aload           4
        //   133: iconst_0       
        //   134: fconst_1       
        //   135: fastore        
        //   136: iinc            7, 1
        //   139: goto            125
        //   142: iconst_0       
        //   143: iload_3        
        //   144: if_icmpge       219
        //   147: iload_3        
        //   148: iconst_0       
        //   149: isub           
        //   150: i2d            
        //   151: ldc2_w          0.5
        //   154: dsub           
        //   155: iload_3        
        //   156: i2d            
        //   157: ddiv           
        //   158: ldc2_w          3.1415927410125732
        //   161: dmul           
        //   162: ldc2_w          2.0
        //   165: ddiv           
        //   166: d2f            
        //   167: fstore          8
        //   169: fload           8
        //   171: f2d            
        //   172: invokestatic    java/lang/Math.sin:(D)D
        //   175: d2f            
        //   176: fstore          8
        //   178: fload           8
        //   180: fload           8
        //   182: fmul           
        //   183: fstore          8
        //   185: fload           8
        //   187: f2d            
        //   188: ldc2_w          1.5707963705062866
        //   191: dmul           
        //   192: d2f            
        //   193: fstore          8
        //   195: fload           8
        //   197: f2d            
        //   198: invokestatic    java/lang/Math.sin:(D)D
        //   201: d2f            
        //   202: fstore          8
        //   204: aload           4
        //   206: iconst_0       
        //   207: iload           6
        //   209: iadd           
        //   210: fload           8
        //   212: fastore        
        //   213: iinc            7, 1
        //   216: goto            142
        //   219: goto            224
        //   222: aconst_null    
        //   223: areturn        
        //   224: aload           4
        //   226: areturn        
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
    
    int init(final Info vi, final boolean b) {
        this.vi = vi;
        this.modebits = Util.ilog2(vi.modes);
        this.transform[0] = new Object[1];
        this.transform[1] = new Object[1];
        this.transform[0][0] = new Mdct();
        this.transform[1][0] = new Mdct();
        ((Mdct)this.transform[0][0]).init(vi.blocksizes[0]);
        ((Mdct)this.transform[1][0]).init(vi.blocksizes[1]);
        this.window[0][0][0] = new float[1][];
        this.window[0][0][1] = this.window[0][0][0];
        this.window[0][1][0] = this.window[0][0][0];
        this.window[0][1][1] = this.window[0][0][0];
        this.window[1][0][0] = new float[1][];
        this.window[1][0][1] = new float[1][];
        this.window[1][1][0] = new float[1][];
        this.window[1][1][1] = new float[1][];
        int n = 0;
        while (0 < 1) {
            this.window[0][0][0][0] = window(0, vi.blocksizes[0], vi.blocksizes[0] / 2, vi.blocksizes[0] / 2);
            this.window[1][0][0][0] = window(0, vi.blocksizes[1], vi.blocksizes[0] / 2, vi.blocksizes[0] / 2);
            this.window[1][0][1][0] = window(0, vi.blocksizes[1], vi.blocksizes[0] / 2, vi.blocksizes[1] / 2);
            this.window[1][1][0][0] = window(0, vi.blocksizes[1], vi.blocksizes[1] / 2, vi.blocksizes[0] / 2);
            this.window[1][1][1][0] = window(0, vi.blocksizes[1], vi.blocksizes[1] / 2, vi.blocksizes[1] / 2);
            ++n;
        }
        this.fullbooks = new CodeBook[vi.books];
        while (0 < vi.books) {
            (this.fullbooks[0] = new CodeBook()).init_decode(vi.book_param[0]);
            ++n;
        }
        this.pcm_storage = 8192;
        this.pcm = new float[vi.channels][];
        while (0 < vi.channels) {
            this.pcm[0] = new float[this.pcm_storage];
            ++n;
        }
        this.lW = 0;
        this.W = 0;
        this.centerW = vi.blocksizes[1] / 2;
        this.pcm_current = this.centerW;
        this.mode = new Object[vi.modes];
        while (0 < vi.modes) {
            final int mapping = vi.mode_param[0].mapping;
            this.mode[0] = FuncMapping.mapping_P[vi.map_type[mapping]].look(this, vi.mode_param[0], vi.map_param[mapping]);
            ++n;
        }
        return 0;
    }
    
    public int synthesis_init(final Info info) {
        this.init(info, false);
        this.pcm_returned = this.centerW;
        this.centerW -= info.blocksizes[this.W] / 4 + info.blocksizes[this.lW] / 4;
        this.granulepos = -1L;
        this.sequence = -1L;
        return 0;
    }
    
    DspState(final Info info) {
        this();
        this.init(info, false);
        this.pcm_returned = this.centerW;
        this.centerW -= info.blocksizes[this.W] / 4 + info.blocksizes[this.lW] / 4;
        this.granulepos = -1L;
        this.sequence = -1L;
    }
    
    public int synthesis_blockin(final Block block) {
        if (this.centerW > this.vi.blocksizes[1] / 2 && this.pcm_returned > 8192) {
            final int n = this.centerW - this.vi.blocksizes[1] / 2;
            final int n2 = (this.pcm_returned < n) ? this.pcm_returned : n;
            this.pcm_current -= n2;
            this.centerW -= n2;
            this.pcm_returned -= n2;
            if (n2 != 0) {
                while (0 < this.vi.channels) {
                    System.arraycopy(this.pcm[0], n2, this.pcm[0], 0, this.pcm_current);
                    int n3 = 0;
                    ++n3;
                }
            }
        }
        this.lW = this.W;
        this.W = block.W;
        this.nW = -1;
        this.glue_bits += block.glue_bits;
        this.time_bits += block.time_bits;
        this.floor_bits += block.floor_bits;
        this.res_bits += block.res_bits;
        if (this.sequence + 1L != block.sequence) {
            this.granulepos = -1L;
        }
        this.sequence = block.sequence;
        final int n4 = this.vi.blocksizes[this.W];
        int n3 = this.centerW + this.vi.blocksizes[this.lW] / 4 + n4 / 4;
        final int n5 = 0 - n4 / 2;
        final int pcm_current = n5 + n4;
        int n6 = 0;
        if (pcm_current > this.pcm_storage) {
            this.pcm_storage = pcm_current + this.vi.blocksizes[1];
            while (0 < this.vi.channels) {
                final float[] array = new float[this.pcm_storage];
                System.arraycopy(this.pcm[0], 0, array, 0, this.pcm[0].length);
                this.pcm[0] = array;
                ++n6;
            }
        }
        switch (this.W) {
            case 0: {
                final int n7 = this.vi.blocksizes[0] / 2;
                break;
            }
            case 1: {
                final int n8 = this.vi.blocksizes[1] / 4 - this.vi.blocksizes[this.lW] / 4;
                final int n9 = 0 + this.vi.blocksizes[this.lW] / 2;
                break;
            }
        }
        while (0 < this.vi.channels) {
            final int n10 = n5;
            int n12 = 0;
            while (0 < 0) {
                final float[] array2 = this.pcm[0];
                final int n11 = n10 + 0;
                array2[n11] += block.pcm[0][0];
                ++n12;
            }
            while (0 < n4) {
                this.pcm[0][n10 + 0] = block.pcm[0][0];
                ++n12;
            }
            ++n6;
        }
        if (this.granulepos == -1L) {
            this.granulepos = block.granulepos;
        }
        else {
            this.granulepos += 0 - this.centerW;
            if (block.granulepos != -1L && this.granulepos != block.granulepos) {
                if (this.granulepos > block.granulepos && block.eofflag != 0) {
                    n3 = (int)(0 - (this.granulepos - block.granulepos));
                }
                this.granulepos = block.granulepos;
            }
        }
        this.centerW = 0;
        this.pcm_current = pcm_current;
        if (block.eofflag != 0) {
            this.eofflag = 1;
        }
        return 0;
    }
    
    public int synthesis_pcmout(final float[][][] array, final int[] array2) {
        if (this.pcm_returned < this.centerW) {
            if (array != null) {
                while (0 < this.vi.channels) {
                    array2[0] = this.pcm_returned;
                    int n = 0;
                    ++n;
                }
                array[0] = this.pcm;
            }
            return this.centerW - this.pcm_returned;
        }
        return 0;
    }
    
    public int synthesis_read(final int n) {
        if (n != 0 && this.pcm_returned + n > this.centerW) {
            return -1;
        }
        this.pcm_returned += n;
        return 0;
    }
    
    public void clear() {
    }
}
