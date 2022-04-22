package org.apache.commons.compress.compressors.bzip2;

import org.apache.commons.compress.compressors.*;
import java.io.*;

public class BZip2CompressorInputStream extends CompressorInputStream implements BZip2Constants
{
    private int last;
    private int origPtr;
    private int blockSize100k;
    private boolean blockRandomised;
    private int bsBuff;
    private int bsLive;
    private final CRC crc;
    private int nInUse;
    private InputStream in;
    private final boolean decompressConcatenated;
    private static final int EOF = 0;
    private static final int START_BLOCK_STATE = 1;
    private static final int RAND_PART_A_STATE = 2;
    private static final int RAND_PART_B_STATE = 3;
    private static final int RAND_PART_C_STATE = 4;
    private static final int NO_RAND_PART_A_STATE = 5;
    private static final int NO_RAND_PART_B_STATE = 6;
    private static final int NO_RAND_PART_C_STATE = 7;
    private int currentState;
    private int storedBlockCRC;
    private int storedCombinedCRC;
    private int computedBlockCRC;
    private int computedCombinedCRC;
    private int su_count;
    private int su_ch2;
    private int su_chPrev;
    private int su_i2;
    private int su_j2;
    private int su_rNToGo;
    private int su_rTPos;
    private int su_tPos;
    private char su_z;
    private Data data;
    
    public BZip2CompressorInputStream(final InputStream inputStream) throws IOException {
        this(inputStream, false);
    }
    
    public BZip2CompressorInputStream(final InputStream in, final boolean decompressConcatenated) throws IOException {
        this.crc = new CRC();
        this.currentState = 1;
        this.in = in;
        this.decompressConcatenated = decompressConcatenated;
        this.init(true);
        this.initBlock();
    }
    
    @Override
    public int read() throws IOException {
        if (this.in != null) {
            final int read0 = this.read0();
            this.count((read0 < 0) ? -1 : 1);
            return read0;
        }
        throw new IOException("stream closed");
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (n < 0) {
            throw new IndexOutOfBoundsException("offs(" + n + ") < 0.");
        }
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("len(" + n2 + ") < 0.");
        }
        if (n + n2 > array.length) {
            throw new IndexOutOfBoundsException("offs(" + n + ") + len(" + n2 + ") > dest.length(" + array.length + ").");
        }
        if (this.in == null) {
            throw new IOException("stream closed");
        }
        final int n3 = n + n2;
        int n4 = n;
        int read0;
        while (n4 < n3 && (read0 = this.read0()) >= 0) {
            array[n4++] = (byte)read0;
            this.count(1);
        }
        return (n4 == n) ? -1 : (n4 - n);
    }
    
    private void makeMaps() {
        final boolean[] inUse = this.data.inUse;
        final byte[] seqToUnseq = this.data.seqToUnseq;
        while (0 < 256) {
            if (inUse[0]) {
                final byte[] array = seqToUnseq;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array[n] = 0;
            }
            int n3 = 0;
            ++n3;
        }
        this.nInUse = 0;
    }
    
    private int read0() throws IOException {
        switch (this.currentState) {
            case 0: {
                return -1;
            }
            case 1: {
                return this.setupBlock();
            }
            case 2: {
                throw new IllegalStateException();
            }
            case 3: {
                return this.setupRandPartB();
            }
            case 4: {
                return this.setupRandPartC();
            }
            case 5: {
                throw new IllegalStateException();
            }
            case 6: {
                return this.setupNoRandPartB();
            }
            case 7: {
                return this.setupNoRandPartC();
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }
    
    private boolean init(final boolean b) throws IOException {
        if (null == this.in) {
            throw new IOException("No InputStream");
        }
        final int read = this.in.read();
        if (read == -1 && !b) {
            return false;
        }
        final int read2 = this.in.read();
        final int read3 = this.in.read();
        if (read != 66 || read2 != 90 || read3 != 104) {
            throw new IOException(b ? "Stream is not in the BZip2 format" : "Garbage after a valid BZip2 stream");
        }
        final int read4 = this.in.read();
        if (read4 < 49 || read4 > 57) {
            throw new IOException("BZip2 block size is invalid");
        }
        this.blockSize100k = read4 - 48;
        this.bsLive = 0;
        this.computedCombinedCRC = 0;
        return true;
    }
    
    private void initBlock() throws IOException {
        do {
            final char bsGetUByte = this.bsGetUByte();
            final char bsGetUByte2 = this.bsGetUByte();
            final char bsGetUByte3 = this.bsGetUByte();
            final char bsGetUByte4 = this.bsGetUByte();
            final char bsGetUByte5 = this.bsGetUByte();
            final char bsGetUByte6 = this.bsGetUByte();
            if (bsGetUByte == '\u0017' && bsGetUByte2 == 'r' && bsGetUByte3 == 'E' && bsGetUByte4 == '8' && bsGetUByte5 == 'P' && bsGetUByte6 == '\u0090') {
                continue;
            }
            if (bsGetUByte != '1' || bsGetUByte2 != 'A' || bsGetUByte3 != 'Y' || bsGetUByte4 != '&' || bsGetUByte5 != 'S' || bsGetUByte6 != 'Y') {
                this.currentState = 0;
                throw new IOException("bad block header");
            }
            this.storedBlockCRC = this.bsGetInt();
            this.blockRandomised = (this.bsR(1) == 1);
            if (this.data == null) {
                this.data = new Data(this.blockSize100k);
            }
            this.getAndMoveToFrontDecode();
            this.crc.initialiseCRC();
            this.currentState = 1;
        } while (!this.complete());
    }
    
    private void endBlock() throws IOException {
        this.computedBlockCRC = this.crc.getFinalCRC();
        if (this.storedBlockCRC != this.computedBlockCRC) {
            this.computedCombinedCRC = (this.storedCombinedCRC << 1 | this.storedCombinedCRC >>> 31);
            this.computedCombinedCRC ^= this.storedBlockCRC;
            throw new IOException("BZip2 CRC error");
        }
        this.computedCombinedCRC = (this.computedCombinedCRC << 1 | this.computedCombinedCRC >>> 31);
        this.computedCombinedCRC ^= this.computedBlockCRC;
    }
    
    private boolean complete() throws IOException {
        this.storedCombinedCRC = this.bsGetInt();
        this.currentState = 0;
        this.data = null;
        if (this.storedCombinedCRC != this.computedCombinedCRC) {
            throw new IOException("BZip2 CRC error");
        }
        return !this.decompressConcatenated || !this.init(false);
    }
    
    @Override
    public void close() throws IOException {
        final InputStream in = this.in;
        if (in != null) {
            if (in != System.in) {
                in.close();
            }
            this.data = null;
            this.in = null;
        }
    }
    
    private int bsR(final int n) throws IOException {
        int i = this.bsLive;
        int bsBuff = this.bsBuff;
        if (i < n) {
            final InputStream in = this.in;
            do {
                final int read = in.read();
                if (read < 0) {
                    throw new IOException("unexpected end of stream");
                }
                bsBuff = (bsBuff << 8 | read);
                i += 8;
            } while (i < n);
            this.bsBuff = bsBuff;
        }
        return bsBuff >> (this.bsLive = i - n) & (1 << n) - 1;
    }
    
    private boolean bsGetBit() throws IOException {
        int bsLive = this.bsLive;
        int bsBuff = this.bsBuff;
        if (bsLive < 1) {
            final int read = this.in.read();
            if (read < 0) {
                throw new IOException("unexpected end of stream");
            }
            bsBuff = (bsBuff << 8 | read);
            bsLive += 8;
            this.bsBuff = bsBuff;
        }
        this.bsLive = bsLive - 1;
        return (bsBuff >> bsLive - 1 & 0x1) != 0x0;
    }
    
    private char bsGetUByte() throws IOException {
        return (char)this.bsR(8);
    }
    
    private int bsGetInt() throws IOException {
        return ((this.bsR(8) << 8 | this.bsR(8)) << 8 | this.bsR(8)) << 8 | this.bsR(8);
    }
    
    private static void hbCreateDecodeTables(final int[] array, final int[] array2, final int[] array3, final char[] array4, final int n, final int n2, final int n3) {
        int n4 = n;
        while (1 <= n2) {
            while (0 < n3) {
                if (array4[0] == '\u0001') {
                    final int n5 = 0;
                    int n6 = 0;
                    ++n6;
                    array3[n5] = 0;
                }
                int n7 = 0;
                ++n7;
            }
            ++n4;
        }
        while (true) {
            --n4;
            if (1 <= 0) {
                break;
            }
            array[1] = (array2[1] = 0);
        }
        while (1 < n3) {
            final int n8 = array4[1] + '\u0001';
            ++array2[n8];
            ++n4;
        }
        int n6 = array2[0];
        while (1 < 23) {
            n6 = 0 + array2[1];
            array2[1] = 0;
            ++n4;
        }
        int n9 = n;
        int n7 = array2[1];
        while (1 <= n2) {
            final int n10 = array2[2];
            n6 = 0 + (n10 - 0);
            n7 = n10;
            array[1] = -1;
            ++n9;
        }
        int n11 = n + 1;
        while (1 <= n2) {
            array2[1] = (array[0] + 1 << 1) - array2[1];
            ++n11;
        }
    }
    
    private void recvDecodingTables() throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.data:Lorg/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream$Data;
        //     4: astore_1       
        //     5: aload_1        
        //     6: getfield        org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream$Data.inUse:[Z
        //     9: astore_2       
        //    10: aload_1        
        //    11: getfield        org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream$Data.recvDecodingTables_pos:[B
        //    14: astore_3       
        //    15: aload_1        
        //    16: getfield        org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream$Data.selector:[B
        //    19: astore          4
        //    21: aload_1        
        //    22: getfield        org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream$Data.selectorMtf:[B
        //    25: astore          5
        //    27: iconst_0       
        //    28: bipush          16
        //    30: if_icmpge       46
        //    33: aload_0        
        //    34: invokespecial   org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.bsGetBit:()Z
        //    37: ifeq            40
        //    40: iinc            7, 1
        //    43: goto            27
        //    46: iinc            7, -1
        //    49: iconst_0       
        //    50: iflt            60
        //    53: aload_2        
        //    54: iconst_0       
        //    55: iconst_0       
        //    56: bastore        
        //    57: goto            46
        //    60: iconst_0       
        //    61: bipush          16
        //    63: if_icmpge       99
        //    66: iconst_0       
        //    67: ifeq            93
        //    70: iconst_0       
        //    71: bipush          16
        //    73: if_icmpge       93
        //    76: aload_0        
        //    77: invokespecial   org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.bsGetBit:()Z
        //    80: ifeq            87
        //    83: aload_2        
        //    84: iconst_0       
        //    85: iconst_1       
        //    86: bastore        
        //    87: iinc            9, 1
        //    90: goto            70
        //    93: iinc            7, 1
        //    96: goto            60
        //    99: aload_0        
        //   100: invokespecial   org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.makeMaps:()V
        //   103: aload_0        
        //   104: getfield        org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.nInUse:I
        //   107: iconst_2       
        //   108: iadd           
        //   109: istore          7
        //   111: aload_0        
        //   112: iconst_3       
        //   113: invokespecial   org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.bsR:(I)I
        //   116: istore          8
        //   118: aload_0        
        //   119: bipush          15
        //   121: invokespecial   org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.bsR:(I)I
        //   124: istore          9
        //   126: iconst_0       
        //   127: iconst_0       
        //   128: if_icmpge       156
        //   131: aload_0        
        //   132: invokespecial   org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.bsGetBit:()Z
        //   135: ifeq            144
        //   138: iinc            11, 1
        //   141: goto            131
        //   144: aload           5
        //   146: iconst_0       
        //   147: iconst_0       
        //   148: i2b            
        //   149: bastore        
        //   150: iinc            10, 1
        //   153: goto            126
        //   156: iinc            10, -1
        //   159: iconst_0       
        //   160: iflt            171
        //   163: aload_3        
        //   164: iconst_0       
        //   165: iconst_0       
        //   166: i2b            
        //   167: bastore        
        //   168: goto            156
        //   171: iconst_0       
        //   172: iconst_0       
        //   173: if_icmpge       224
        //   176: aload           5
        //   178: iconst_0       
        //   179: baload         
        //   180: sipush          255
        //   183: iand           
        //   184: istore          11
        //   186: aload_3        
        //   187: iconst_0       
        //   188: baload         
        //   189: istore          12
        //   191: iconst_0       
        //   192: ifle            207
        //   195: aload_3        
        //   196: iconst_0       
        //   197: aload_3        
        //   198: iconst_m1      
        //   199: baload         
        //   200: bastore        
        //   201: iinc            11, -1
        //   204: goto            191
        //   207: aload_3        
        //   208: iconst_0       
        //   209: iload           12
        //   211: bastore        
        //   212: aload           4
        //   214: iconst_0       
        //   215: iload           12
        //   217: bastore        
        //   218: iinc            10, 1
        //   221: goto            171
        //   224: aload_1        
        //   225: getfield        org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream$Data.temp_charArray2d:[[C
        //   228: astore          10
        //   230: iconst_0       
        //   231: iconst_0       
        //   232: if_icmpge       299
        //   235: aload_0        
        //   236: iconst_5       
        //   237: invokespecial   org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.bsR:(I)I
        //   240: istore          12
        //   242: aload           10
        //   244: iconst_0       
        //   245: aaload         
        //   246: astore          13
        //   248: iconst_0       
        //   249: iconst_0       
        //   250: if_icmpge       293
        //   253: aload_0        
        //   254: invokespecial   org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.bsGetBit:()Z
        //   257: ifeq            280
        //   260: iload           12
        //   262: aload_0        
        //   263: invokespecial   org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.bsGetBit:()Z
        //   266: ifeq            273
        //   269: iconst_m1      
        //   270: goto            274
        //   273: iconst_1       
        //   274: iadd           
        //   275: istore          12
        //   277: goto            253
        //   280: aload           13
        //   282: iconst_0       
        //   283: iload           12
        //   285: i2c            
        //   286: castore        
        //   287: iinc            14, 1
        //   290: goto            248
        //   293: iinc            11, 1
        //   296: goto            230
        //   299: aload_0        
        //   300: iconst_0       
        //   301: iconst_0       
        //   302: invokespecial   org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.createHuffmanDecodingTables:(II)V
        //   305: return         
        //    Exceptions:
        //  throws java.io.IOException
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
    
    private void createHuffmanDecodingTables(final int n, final int n2) {
        final Data data = this.data;
        final char[][] temp_charArray2d = data.temp_charArray2d;
        final int[] minLens = data.minLens;
        final int[][] limit = data.limit;
        final int[][] base = data.base;
        final int[][] perm = data.perm;
        while (0 < n2) {
            final char[] array = temp_charArray2d[0];
            int n3 = n;
            while (--n3 >= 0) {
                final char c = array[n3];
                if (c > '\0') {}
                if (c < ' ') {
                    continue;
                }
            }
            hbCreateDecodeTables(limit[0], base[0], perm[0], temp_charArray2d[0], 32, 0, n);
            minLens[0] = 32;
            int n4 = 0;
            ++n4;
        }
    }
    
    private void getAndMoveToFrontDecode() throws IOException {
        this.origPtr = this.bsR(24);
        this.recvDecodingTables();
        final InputStream in = this.in;
        final Data data = this.data;
        final byte[] ll8 = data.ll8;
        final int[] unzftab = data.unzftab;
        final byte[] selector = data.selector;
        final byte[] seqToUnseq = data.seqToUnseq;
        final char[] getAndMoveToFrontDecode_yy = data.getAndMoveToFrontDecode_yy;
        final int[] minLens = data.minLens;
        final int[][] limit = data.limit;
        final int[][] base = data.base;
        final int[][] perm = data.perm;
        final int n = this.blockSize100k * 100000;
        int n2 = 0;
        while (true) {
            --n2;
            if (0 < 0) {
                break;
            }
            getAndMoveToFrontDecode_yy[0] = 0;
            unzftab[0] = 0;
        }
        final int n3 = this.nInUse + 1;
        int i = this.getAndMoveToFrontDecode0(0);
        int bsBuff = this.bsBuff;
        int j = this.bsLive;
        final int n4 = selector[0] & 0xFF;
        int[] array = base[n4];
        int[] array2 = limit[n4];
        int[] array3 = perm[n4];
        int n5 = minLens[n4];
        while (i != n3) {
            if (i == 0 || i == 1) {
                while (true) {
                    if (i != 0) {
                        if (i != 1) {
                            final byte b = seqToUnseq[getAndMoveToFrontDecode_yy[0]];
                            final int[] array4 = unzftab;
                            final int n6 = 1;
                            array4[n6] += 0;
                            while (true) {
                                final int n7 = -1;
                                int n8 = 0;
                                --n8;
                                if (n7 < 0) {
                                    break;
                                }
                                final byte[] array5 = ll8;
                                int n9 = 0;
                                ++n9;
                                array5[-1] = 1;
                            }
                            if (-1 >= n) {
                                throw new IOException("block overrun");
                            }
                            break;
                        }
                    }
                    if (49 == 0) {
                        final byte[] array6 = selector;
                        ++n2;
                        final int n10 = array6[0] & 0xFF;
                        array = base[n10];
                        array2 = limit[n10];
                        array3 = perm[n10];
                        n5 = minLens[n10];
                    }
                    else {
                        int n11 = 0;
                        --n11;
                    }
                    int n12;
                    for (n12 = n5; j < n12; j += 8) {
                        final int read = in.read();
                        if (read < 0) {
                            throw new IOException("unexpected end of stream");
                        }
                        bsBuff = (bsBuff << 8 | read);
                    }
                    int k;
                    for (k = (bsBuff >> j - n12 & (1 << n12) - 1), j -= n12; k > array2[n12]; k = (k << 1 | (bsBuff >> j & 0x1))) {
                        ++n12;
                        while (j < 1) {
                            final int read2 = in.read();
                            if (read2 < 0) {
                                throw new IOException("unexpected end of stream");
                            }
                            bsBuff = (bsBuff << 8 | read2);
                            j += 8;
                        }
                        --j;
                    }
                    i = array3[k - array[n12]];
                }
            }
            else {
                int n9 = 0;
                ++n9;
                if (-1 >= n) {
                    throw new IOException("block overrun");
                }
                final int n8 = getAndMoveToFrontDecode_yy[i - 1];
                final int[] array7 = unzftab;
                final int n13 = seqToUnseq[-1] & 0xFF;
                ++array7[n13];
                ll8[-1] = seqToUnseq[-1];
                if (i <= 16) {
                    int n14 = i - 1;
                    while (1 > 0) {
                        final char[] array8 = getAndMoveToFrontDecode_yy;
                        final int n15 = 1;
                        final char[] array9 = getAndMoveToFrontDecode_yy;
                        --n14;
                        array8[n15] = array9[1];
                    }
                }
                else {
                    System.arraycopy(getAndMoveToFrontDecode_yy, 0, getAndMoveToFrontDecode_yy, 1, i - 1);
                }
                getAndMoveToFrontDecode_yy[0] = (char)(-1);
                if (49 == 0) {
                    final byte[] array10 = selector;
                    ++n2;
                    final int n16 = array10[0] & 0xFF;
                    array = base[n16];
                    array2 = limit[n16];
                    array3 = perm[n16];
                    n5 = minLens[n16];
                }
                else {
                    int n11 = 0;
                    --n11;
                }
                int n17 = n5;
                while (j < 1) {
                    final int read3 = in.read();
                    if (read3 < 0) {
                        throw new IOException("unexpected end of stream");
                    }
                    bsBuff = (bsBuff << 8 | read3);
                    j += 8;
                }
                int l;
                for (l = (bsBuff >> j - 1 & 0x1), --j; l > array2[1]; l = (l << 1 | (bsBuff >> j & 0x1))) {
                    ++n17;
                    while (j < 1) {
                        final int read4 = in.read();
                        if (read4 < 0) {
                            throw new IOException("unexpected end of stream");
                        }
                        bsBuff = (bsBuff << 8 | read4);
                        j += 8;
                    }
                    --j;
                }
                i = array3[l - array[1]];
            }
        }
        this.last = -1;
        this.bsLive = j;
        this.bsBuff = bsBuff;
    }
    
    private int getAndMoveToFrontDecode0(final int n) throws IOException {
        final InputStream in = this.in;
        final Data data = this.data;
        final int n2 = data.selector[n] & 0xFF;
        int[] array;
        int n3;
        int i;
        int j;
        int bsBuff;
        int read;
        for (array = data.limit[n2], n3 = data.minLens[n2], i = this.bsR(n3), j = this.bsLive, bsBuff = this.bsBuff; i > array[n3]; i = (i << 1 | (bsBuff >> j & 0x1))) {
            ++n3;
            while (j < 1) {
                read = in.read();
                if (read < 0) {
                    throw new IOException("unexpected end of stream");
                }
                bsBuff = (bsBuff << 8 | read);
                j += 8;
            }
            --j;
        }
        this.bsLive = j;
        this.bsBuff = bsBuff;
        return data.perm[n2][i - data.base[n2][n3]];
    }
    
    private int setupBlock() throws IOException {
        if (this.currentState == 0 || this.data == null) {
            return -1;
        }
        final int[] cftab = this.data.cftab;
        final int[] initTT = this.data.initTT(this.last + 1);
        final byte[] ll8 = this.data.ll8;
        cftab[0] = 0;
        System.arraycopy(this.data.unzftab, 0, cftab, 1, 256);
        int n = cftab[0];
        int n2 = 0;
        while (0 <= 256) {
            n += cftab[0];
            cftab[0] = n;
            ++n2;
        }
        while (0 <= this.last) {
            initTT[cftab[ll8[0] & 0xFF]++] = 0;
            ++n2;
        }
        if (this.origPtr < 0 || this.origPtr >= initTT.length) {
            throw new IOException("stream corrupted");
        }
        this.su_tPos = initTT[this.origPtr];
        this.su_count = 0;
        this.su_i2 = 0;
        this.su_ch2 = 256;
        if (this.blockRandomised) {
            this.su_rNToGo = 0;
            this.su_rTPos = 0;
            return this.setupRandPartA();
        }
        return this.setupNoRandPartA();
    }
    
    private int setupRandPartA() throws IOException {
        if (this.su_i2 <= this.last) {
            this.su_chPrev = this.su_ch2;
            final int n = this.data.ll8[this.su_tPos] & 0xFF;
            this.su_tPos = this.data.tt[this.su_tPos];
            if (this.su_rNToGo == 0) {
                this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
                if (++this.su_rTPos == 512) {
                    this.su_rTPos = 0;
                }
            }
            else {
                --this.su_rNToGo;
            }
            final int n2 = this.su_ch2 = (n ^ ((this.su_rNToGo == 1) ? 1 : 0));
            ++this.su_i2;
            this.currentState = 3;
            this.crc.updateCRC(n2);
            return n2;
        }
        this.endBlock();
        this.initBlock();
        return this.setupBlock();
    }
    
    private int setupNoRandPartA() throws IOException {
        if (this.su_i2 <= this.last) {
            this.su_chPrev = this.su_ch2;
            final int su_ch2 = this.data.ll8[this.su_tPos] & 0xFF;
            this.su_ch2 = su_ch2;
            this.su_tPos = this.data.tt[this.su_tPos];
            ++this.su_i2;
            this.currentState = 6;
            this.crc.updateCRC(su_ch2);
            return su_ch2;
        }
        this.currentState = 5;
        this.endBlock();
        this.initBlock();
        return this.setupBlock();
    }
    
    private int setupRandPartB() throws IOException {
        if (this.su_ch2 != this.su_chPrev) {
            this.currentState = 2;
            this.su_count = 1;
            return this.setupRandPartA();
        }
        if (++this.su_count >= 4) {
            this.su_z = (char)(this.data.ll8[this.su_tPos] & 0xFF);
            this.su_tPos = this.data.tt[this.su_tPos];
            if (this.su_rNToGo == 0) {
                this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
                if (++this.su_rTPos == 512) {
                    this.su_rTPos = 0;
                }
            }
            else {
                --this.su_rNToGo;
            }
            this.su_j2 = 0;
            this.currentState = 4;
            if (this.su_rNToGo == 1) {
                this.su_z ^= '\u0001';
            }
            return this.setupRandPartC();
        }
        this.currentState = 2;
        return this.setupRandPartA();
    }
    
    private int setupRandPartC() throws IOException {
        if (this.su_j2 < this.su_z) {
            this.crc.updateCRC(this.su_ch2);
            ++this.su_j2;
            return this.su_ch2;
        }
        this.currentState = 2;
        ++this.su_i2;
        this.su_count = 0;
        return this.setupRandPartA();
    }
    
    private int setupNoRandPartB() throws IOException {
        if (this.su_ch2 != this.su_chPrev) {
            this.su_count = 1;
            return this.setupNoRandPartA();
        }
        if (++this.su_count >= 4) {
            this.su_z = (char)(this.data.ll8[this.su_tPos] & 0xFF);
            this.su_tPos = this.data.tt[this.su_tPos];
            this.su_j2 = 0;
            return this.setupNoRandPartC();
        }
        return this.setupNoRandPartA();
    }
    
    private int setupNoRandPartC() throws IOException {
        if (this.su_j2 < this.su_z) {
            final int su_ch2 = this.su_ch2;
            this.crc.updateCRC(su_ch2);
            ++this.su_j2;
            this.currentState = 7;
            return su_ch2;
        }
        ++this.su_i2;
        this.su_count = 0;
        return this.setupNoRandPartA();
    }
    
    public static boolean matches(final byte[] array, final int n) {
        return n >= 3 && array[0] == 66 && array[1] == 90 && array[2] == 104;
    }
    
    private static final class Data
    {
        final boolean[] inUse;
        final byte[] seqToUnseq;
        final byte[] selector;
        final byte[] selectorMtf;
        final int[] unzftab;
        final int[][] limit;
        final int[][] base;
        final int[][] perm;
        final int[] minLens;
        final int[] cftab;
        final char[] getAndMoveToFrontDecode_yy;
        final char[][] temp_charArray2d;
        final byte[] recvDecodingTables_pos;
        int[] tt;
        byte[] ll8;
        
        Data(final int n) {
            this.inUse = new boolean[256];
            this.seqToUnseq = new byte[256];
            this.selector = new byte[18002];
            this.selectorMtf = new byte[18002];
            this.unzftab = new int[256];
            this.limit = new int[6][258];
            this.base = new int[6][258];
            this.perm = new int[6][258];
            this.minLens = new int[6];
            this.cftab = new int[257];
            this.getAndMoveToFrontDecode_yy = new char[256];
            this.temp_charArray2d = new char[6][258];
            this.recvDecodingTables_pos = new byte[6];
            this.ll8 = new byte[n * 100000];
        }
        
        int[] initTT(final int n) {
            int[] tt = this.tt;
            if (tt == null || tt.length < n) {
                tt = (this.tt = new int[n]);
            }
            return tt;
        }
    }
}
