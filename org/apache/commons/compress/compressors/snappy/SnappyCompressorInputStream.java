package org.apache.commons.compress.compressors.snappy;

import org.apache.commons.compress.compressors.*;
import java.io.*;

public class SnappyCompressorInputStream extends CompressorInputStream
{
    private static final int TAG_MASK = 3;
    public static final int DEFAULT_BLOCK_SIZE = 32768;
    private final byte[] decompressBuf;
    private int writeIndex;
    private int readIndex;
    private final int blockSize;
    private final InputStream in;
    private final int size;
    private int uncompressedBytesRemaining;
    private final byte[] oneByte;
    private boolean endReached;
    
    public SnappyCompressorInputStream(final InputStream inputStream) throws IOException {
        this(inputStream, 32768);
    }
    
    public SnappyCompressorInputStream(final InputStream in, final int blockSize) throws IOException {
        this.oneByte = new byte[1];
        this.endReached = false;
        this.in = in;
        this.blockSize = blockSize;
        this.decompressBuf = new byte[blockSize * 3];
        final int n = 0;
        this.readIndex = n;
        this.writeIndex = n;
        final int n2 = (int)this.readSize();
        this.size = n2;
        this.uncompressedBytesRemaining = n2;
    }
    
    @Override
    public int read() throws IOException {
        return (this.read(this.oneByte, 0, 1) == -1) ? -1 : (this.oneByte[0] & 0xFF);
    }
    
    @Override
    public void close() throws IOException {
        this.in.close();
    }
    
    @Override
    public int available() {
        return this.writeIndex - this.readIndex;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.endReached) {
            return -1;
        }
        final int available = this.available();
        if (n2 > available) {
            this.fill(n2 - available);
        }
        final int min = Math.min(n2, this.available());
        System.arraycopy(this.decompressBuf, this.readIndex, array, n, min);
        this.readIndex += min;
        if (this.readIndex > this.blockSize) {
            this.slideBuffer();
        }
        return min;
    }
    
    private void fill(final int p0) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.uncompressedBytesRemaining:I
        //     4: ifne            12
        //     7: aload_0        
        //     8: iconst_1       
        //     9: putfield        org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.endReached:Z
        //    12: iload_1        
        //    13: aload_0        
        //    14: getfield        org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.uncompressedBytesRemaining:I
        //    17: invokestatic    java/lang/Math.min:(II)I
        //    20: istore_2       
        //    21: iload_2        
        //    22: ifle            226
        //    25: aload_0        
        //    26: invokespecial   org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.readOneByte:()I
        //    29: istore_3       
        //    30: lconst_0       
        //    31: lstore          5
        //    33: iload_3        
        //    34: iconst_3       
        //    35: iand           
        //    36: tableswitch {
        //                0: 68
        //                1: 81
        //                2: 117
        //                3: 150
        //          default: 209
        //        }
        //    68: aload_0        
        //    69: iload_3        
        //    70: invokespecial   org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.readLiteralLength:(I)I
        //    73: istore          4
        //    75: aload_0        
        //    76: iconst_0       
        //    77: if_icmpeq       209
        //    80: return         
        //    81: iconst_4       
        //    82: iload_3        
        //    83: iconst_2       
        //    84: ishr           
        //    85: bipush          7
        //    87: iand           
        //    88: iadd           
        //    89: istore          4
        //    91: iload_3        
        //    92: sipush          224
        //    95: iand           
        //    96: iconst_3       
        //    97: ishl           
        //    98: i2l            
        //    99: lstore          5
        //   101: lload           5
        //   103: aload_0        
        //   104: invokespecial   org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.readOneByte:()I
        //   107: i2l            
        //   108: lor            
        //   109: lstore          5
        //   111: aload_0        
        //   112: lload           5
        //   114: goto            209
        //   117: iload_3        
        //   118: iconst_2       
        //   119: ishr           
        //   120: iconst_1       
        //   121: iadd           
        //   122: istore          4
        //   124: aload_0        
        //   125: invokespecial   org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.readOneByte:()I
        //   128: i2l            
        //   129: lstore          5
        //   131: lload           5
        //   133: aload_0        
        //   134: invokespecial   org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.readOneByte:()I
        //   137: bipush          8
        //   139: ishl           
        //   140: i2l            
        //   141: lor            
        //   142: lstore          5
        //   144: aload_0        
        //   145: lload           5
        //   147: goto            209
        //   150: iload_3        
        //   151: iconst_2       
        //   152: ishr           
        //   153: iconst_1       
        //   154: iadd           
        //   155: istore          4
        //   157: aload_0        
        //   158: invokespecial   org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.readOneByte:()I
        //   161: i2l            
        //   162: lstore          5
        //   164: lload           5
        //   166: aload_0        
        //   167: invokespecial   org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.readOneByte:()I
        //   170: bipush          8
        //   172: ishl           
        //   173: i2l            
        //   174: lor            
        //   175: lstore          5
        //   177: lload           5
        //   179: aload_0        
        //   180: invokespecial   org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.readOneByte:()I
        //   183: bipush          16
        //   185: ishl           
        //   186: i2l            
        //   187: lor            
        //   188: lstore          5
        //   190: lload           5
        //   192: aload_0        
        //   193: invokespecial   org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.readOneByte:()I
        //   196: i2l            
        //   197: bipush          24
        //   199: lshl           
        //   200: lor            
        //   201: lstore          5
        //   203: aload_0        
        //   204: lload           5
        //   206: goto            209
        //   209: iload_2        
        //   210: iconst_0       
        //   211: isub           
        //   212: istore_2       
        //   213: aload_0        
        //   214: dup            
        //   215: getfield        org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.uncompressedBytesRemaining:I
        //   218: iconst_0       
        //   219: isub           
        //   220: putfield        org/apache/commons/compress/compressors/snappy/SnappyCompressorInputStream.uncompressedBytesRemaining:I
        //   223: goto            21
        //   226: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0209 (coming from #0206).
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
    
    private void slideBuffer() {
        System.arraycopy(this.decompressBuf, this.blockSize, this.decompressBuf, 0, this.blockSize * 2);
        this.writeIndex -= this.blockSize;
        this.readIndex -= this.blockSize;
    }
    
    private int readLiteralLength(final int n) throws IOException {
        int oneByte = 0;
        switch (n >> 2) {
            case 60: {
                oneByte = this.readOneByte();
                break;
            }
            case 61: {
                oneByte = (this.readOneByte() | this.readOneByte() << 8);
                break;
            }
            case 62: {
                oneByte = (this.readOneByte() | this.readOneByte() << 8 | this.readOneByte() << 16);
                break;
            }
            case 63: {
                oneByte = (int)((long)(this.readOneByte() | this.readOneByte() << 8 | this.readOneByte() << 16) | (long)this.readOneByte() << 24);
                break;
            }
            default: {
                oneByte = n >> 2;
                break;
            }
        }
        return oneByte + 1;
    }
    
    private int readOneByte() throws IOException {
        final int read = this.in.read();
        if (read == -1) {
            throw new IOException("Premature end of stream");
        }
        this.count(1);
        return read & 0xFF;
    }
    
    private long readSize() throws IOException {
        final long n = 0L;
        this.readOneByte();
        final long n2 = n;
        final int n3 = 0;
        final int n4 = 0;
        int n5 = 0;
        ++n5;
        return n2 | (long)(n3 << n4 * 7);
    }
    
    public int getSize() {
        return this.size;
    }
}
