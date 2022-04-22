package io.netty.handler.codec.compression;

import io.netty.buffer.*;

class Snappy
{
    private static final int MAX_HT_SIZE = 16384;
    private static final int MIN_COMPRESSIBLE_BYTES = 15;
    private static final int PREAMBLE_NOT_FULL = -1;
    private static final int NOT_ENOUGH_INPUT = -1;
    private static final int LITERAL = 0;
    private static final int COPY_1_BYTE_OFFSET = 1;
    private static final int COPY_2_BYTE_OFFSET = 2;
    private static final int COPY_4_BYTE_OFFSET = 3;
    private State state;
    private byte tag;
    private int written;
    
    Snappy() {
        this.state = State.READY;
    }
    
    public void reset() {
        this.state = State.READY;
        this.tag = 0;
        this.written = 0;
    }
    
    public void encode(final ByteBuf p0, final ByteBuf p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iconst_0       
        //     2: iushr          
        //     3: istore          5
        //     5: iconst_0       
        //     6: ifeq            20
        //     9: aload_2        
        //    10: sipush          128
        //    13: invokevirtual   io/netty/buffer/ByteBuf.writeByte:(I)Lio/netty/buffer/ByteBuf;
        //    16: pop            
        //    17: goto            29
        //    20: aload_2        
        //    21: iconst_0       
        //    22: invokevirtual   io/netty/buffer/ByteBuf.writeByte:(I)Lio/netty/buffer/ByteBuf;
        //    25: pop            
        //    26: goto            35
        //    29: iinc            4, 1
        //    32: goto            0
        //    35: aload_1        
        //    36: invokevirtual   io/netty/buffer/ByteBuf.readerIndex:()I
        //    39: istore          4
        //    41: iload_3        
        //    42: invokestatic    io/netty/handler/codec/compression/Snappy.getHashTable:(I)[S
        //    45: astore          6
        //    47: bipush          32
        //    49: aload           6
        //    51: arraylength    
        //    52: i2d            
        //    53: invokestatic    java/lang/Math.log:(D)D
        //    56: ldc2_w          2.0
        //    59: invokestatic    java/lang/Math.log:(D)D
        //    62: ddiv           
        //    63: invokestatic    java/lang/Math.floor:(D)D
        //    66: d2i            
        //    67: isub           
        //    68: istore          7
        //    70: iload_3        
        //    71: iconst_0       
        //    72: isub           
        //    73: bipush          15
        //    75: if_icmplt       282
        //    78: aload_1        
        //    79: iinc            4, 1
        //    82: iconst_0       
        //    83: iload           7
        //    85: invokestatic    io/netty/handler/codec/compression/Snappy.hash:(Lio/netty/buffer/ByteBuf;II)I
        //    88: istore          9
        //    90: iload           9
        //    92: istore          13
        //    94: bipush          32
        //    96: iinc            10, 1
        //    99: iconst_5       
        //   100: ishr           
        //   101: istore          14
        //   103: iconst_0       
        //   104: iload_3        
        //   105: iconst_4       
        //   106: isub           
        //   107: if_icmple       113
        //   110: goto            282
        //   113: aload_1        
        //   114: iconst_0       
        //   115: iload           7
        //   117: invokestatic    io/netty/handler/codec/compression/Snappy.hash:(Lio/netty/buffer/ByteBuf;II)I
        //   120: istore          9
        //   122: iconst_0       
        //   123: aload           6
        //   125: iconst_m1      
        //   126: saload         
        //   127: iadd           
        //   128: istore          11
        //   130: aload           6
        //   132: iconst_m1      
        //   133: iconst_0       
        //   134: i2s            
        //   135: sastore        
        //   136: aload_1        
        //   137: iconst_0       
        //   138: invokevirtual   io/netty/buffer/ByteBuf.getInt:(I)I
        //   141: aload_1        
        //   142: iload           11
        //   144: invokevirtual   io/netty/buffer/ByteBuf.getInt:(I)I
        //   147: if_icmpne       90
        //   150: aload_1        
        //   151: aload_2        
        //   152: iconst_0       
        //   153: invokestatic    io/netty/handler/codec/compression/Snappy.encodeLiteral:(Lio/netty/buffer/ByteBuf;Lio/netty/buffer/ByteBuf;I)V
        //   156: iconst_4       
        //   157: aload_1        
        //   158: iload           11
        //   160: iconst_4       
        //   161: iadd           
        //   162: iconst_4       
        //   163: iload_3        
        //   164: invokestatic    io/netty/handler/codec/compression/Snappy.findMatchingLength:(Lio/netty/buffer/ByteBuf;III)I
        //   167: iadd           
        //   168: istore          15
        //   170: iconst_0       
        //   171: iload           15
        //   173: iadd           
        //   174: istore          4
        //   176: iconst_0       
        //   177: iload           11
        //   179: isub           
        //   180: istore          16
        //   182: aload_2        
        //   183: iload           16
        //   185: iload           15
        //   187: invokestatic    io/netty/handler/codec/compression/Snappy.encodeCopy:(Lio/netty/buffer/ByteBuf;II)V
        //   190: aload_1        
        //   191: aload_1        
        //   192: invokevirtual   io/netty/buffer/ByteBuf.readerIndex:()I
        //   195: iload           15
        //   197: iadd           
        //   198: invokevirtual   io/netty/buffer/ByteBuf.readerIndex:(I)Lio/netty/buffer/ByteBuf;
        //   201: pop            
        //   202: iconst_0       
        //   203: iload_3        
        //   204: iconst_4       
        //   205: isub           
        //   206: if_icmplt       212
        //   209: goto            282
        //   212: aload_1        
        //   213: iconst_m1      
        //   214: iload           7
        //   216: invokestatic    io/netty/handler/codec/compression/Snappy.hash:(Lio/netty/buffer/ByteBuf;II)I
        //   219: istore          17
        //   221: aload           6
        //   223: iload           17
        //   225: iconst_m1      
        //   226: i2s            
        //   227: sastore        
        //   228: aload_1        
        //   229: iconst_0       
        //   230: iload           7
        //   232: invokestatic    io/netty/handler/codec/compression/Snappy.hash:(Lio/netty/buffer/ByteBuf;II)I
        //   235: istore          18
        //   237: iconst_0       
        //   238: aload           6
        //   240: iload           18
        //   242: saload         
        //   243: iadd           
        //   244: istore          11
        //   246: aload           6
        //   248: iload           18
        //   250: iconst_0       
        //   251: i2s            
        //   252: sastore        
        //   253: aload_1        
        //   254: iconst_0       
        //   255: invokevirtual   io/netty/buffer/ByteBuf.getInt:(I)I
        //   258: aload_1        
        //   259: iload           11
        //   261: invokevirtual   io/netty/buffer/ByteBuf.getInt:(I)I
        //   264: if_icmpeq       156
        //   267: aload_1        
        //   268: iconst_1       
        //   269: iload           7
        //   271: invokestatic    io/netty/handler/codec/compression/Snappy.hash:(Lio/netty/buffer/ByteBuf;II)I
        //   274: istore          9
        //   276: iinc            4, 1
        //   279: goto            90
        //   282: iconst_0       
        //   283: iload_3        
        //   284: if_icmpge       295
        //   287: aload_1        
        //   288: aload_2        
        //   289: iload_3        
        //   290: iconst_0       
        //   291: isub           
        //   292: invokestatic    io/netty/handler/codec/compression/Snappy.encodeLiteral:(Lio/netty/buffer/ByteBuf;Lio/netty/buffer/ByteBuf;I)V
        //   295: return         
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
    
    private static int hash(final ByteBuf byteBuf, final int n, final int n2) {
        return byteBuf.getInt(n) + 506832829 >>> n2;
    }
    
    private static short[] getHashTable(final int n) {
        while (256 < 16384 && 256 < n) {}
        short[] array;
        if (256 <= 256) {
            array = new short[256];
        }
        else {
            array = new short[16384];
        }
        return array;
    }
    
    private static int findMatchingLength(final ByteBuf byteBuf, final int n, int n2, final int n3) {
        int n4 = 0;
        while (n2 <= n3 - 4 && byteBuf.getInt(n2) == byteBuf.getInt(n + 0)) {
            n2 += 4;
            n4 += 4;
        }
        while (n2 < n3 && byteBuf.getByte(n + 0) == byteBuf.getByte(n2)) {
            ++n2;
            ++n4;
        }
        return 0;
    }
    
    private static int bitsToEncode(final int n) {
        int highestOneBit = Integer.highestOneBit(n);
        while ((highestOneBit >>= 1) != 0) {
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    private static void encodeLiteral(final ByteBuf byteBuf, final ByteBuf byteBuf2, final int n) {
        if (n < 61) {
            byteBuf2.writeByte(n - 1 << 2);
        }
        else {
            final int n2 = 1 + bitsToEncode(n - 1) / 8;
            byteBuf2.writeByte(59 + n2 << 2);
            while (0 < n2) {
                byteBuf2.writeByte(n - 1 >> 0 & 0xFF);
                int n3 = 0;
                ++n3;
            }
        }
        byteBuf2.writeBytes(byteBuf, n);
    }
    
    private static void encodeCopyWithOffset(final ByteBuf byteBuf, final int n, final int n2) {
        if (n2 < 12 && n < 2048) {
            byteBuf.writeByte(0x1 | n2 - 4 << 2 | n >> 8 << 5);
            byteBuf.writeByte(n & 0xFF);
        }
        else {
            byteBuf.writeByte(0x2 | n2 - 1 << 2);
            byteBuf.writeByte(n & 0xFF);
            byteBuf.writeByte(n >> 8 & 0xFF);
        }
    }
    
    private static void encodeCopy(final ByteBuf byteBuf, final int n, int i) {
        while (i >= 68) {
            encodeCopyWithOffset(byteBuf, n, 64);
            i -= 64;
        }
        if (i > 64) {
            encodeCopyWithOffset(byteBuf, n, 60);
            i -= 60;
        }
        encodeCopyWithOffset(byteBuf, n, i);
    }
    
    public void decode(final ByteBuf byteBuf, final ByteBuf byteBuf2) {
        while (byteBuf.isReadable()) {
            switch (this.state) {
                case READY: {
                    this.state = State.READING_PREAMBLE;
                }
                case READING_PREAMBLE: {
                    final int preamble = readPreamble(byteBuf);
                    if (preamble == -1) {
                        return;
                    }
                    if (preamble == 0) {
                        this.state = State.READY;
                        return;
                    }
                    byteBuf2.ensureWritable(preamble);
                    this.state = State.READING_TAG;
                }
                case READING_TAG: {
                    if (!byteBuf.isReadable()) {
                        return;
                    }
                    this.tag = byteBuf.readByte();
                    switch (this.tag & 0x3) {
                        case 0: {
                            this.state = State.READING_LITERAL;
                            continue;
                        }
                        case 1:
                        case 2:
                        case 3: {
                            this.state = State.READING_COPY;
                            continue;
                        }
                    }
                    continue;
                }
                case READING_LITERAL: {
                    final int decodeLiteral = decodeLiteral(this.tag, byteBuf, byteBuf2);
                    if (decodeLiteral != -1) {
                        this.state = State.READING_TAG;
                        this.written += decodeLiteral;
                        continue;
                    }
                }
                case READING_COPY: {
                    switch (this.tag & 0x3) {
                        case 1: {
                            final int decodeCopyWith1ByteOffset = decodeCopyWith1ByteOffset(this.tag, byteBuf, byteBuf2, this.written);
                            if (decodeCopyWith1ByteOffset != -1) {
                                this.state = State.READING_TAG;
                                this.written += decodeCopyWith1ByteOffset;
                                continue;
                            }
                            return;
                        }
                        case 2: {
                            final int decodeCopyWith2ByteOffset = decodeCopyWith2ByteOffset(this.tag, byteBuf, byteBuf2, this.written);
                            if (decodeCopyWith2ByteOffset != -1) {
                                this.state = State.READING_TAG;
                                this.written += decodeCopyWith2ByteOffset;
                                continue;
                            }
                            return;
                        }
                        case 3: {
                            final int decodeCopyWith4ByteOffset = decodeCopyWith4ByteOffset(this.tag, byteBuf, byteBuf2, this.written);
                            if (decodeCopyWith4ByteOffset != -1) {
                                this.state = State.READING_TAG;
                                this.written += decodeCopyWith4ByteOffset;
                                continue;
                            }
                            return;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    private static int readPreamble(final ByteBuf byteBuf) {
        while (byteBuf.isReadable()) {
            final short unsignedByte = byteBuf.readUnsignedByte();
            int n = 0;
            ++n;
            if ((unsignedByte & 0x80) == 0x0) {
                return 0;
            }
            if (0 >= 4) {
                throw new DecompressionException("Preamble is greater than 4 bytes");
            }
        }
        return 0;
    }
    
    private static int decodeLiteral(final byte b, final ByteBuf byteBuf, final ByteBuf byteBuf2) {
        byteBuf.markReaderIndex();
        int n = 0;
        switch (b >> 2 & 0x3F) {
            case 60: {
                if (!byteBuf.isReadable()) {
                    return -1;
                }
                n = byteBuf.readUnsignedByte();
                break;
            }
            case 61: {
                if (byteBuf.readableBytes() < 2) {
                    return -1;
                }
                n = ByteBufUtil.swapShort(byteBuf.readShort());
                break;
            }
            case 62: {
                if (byteBuf.readableBytes() < 3) {
                    return -1;
                }
                n = ByteBufUtil.swapMedium(byteBuf.readUnsignedMedium());
                break;
            }
            case 64: {
                if (byteBuf.readableBytes() < 4) {
                    return -1;
                }
                n = ByteBufUtil.swapInt(byteBuf.readInt());
                break;
            }
            default: {
                n = (b >> 2 & 0x3F);
                break;
            }
        }
        ++n;
        if (byteBuf.readableBytes() < n) {
            byteBuf.resetReaderIndex();
            return -1;
        }
        byteBuf2.writeBytes(byteBuf, n);
        return n;
    }
    
    private static int decodeCopyWith1ByteOffset(final byte b, final ByteBuf byteBuf, final ByteBuf byteBuf2, final int n) {
        if (!byteBuf.isReadable()) {
            return -1;
        }
        final int writerIndex = byteBuf2.writerIndex();
        final int n2 = 4 + ((b & 0x1C) >> 2);
        final int n3 = (b & 0xE0) << 8 >> 5 | byteBuf.readUnsignedByte();
        validateOffset(n3, n);
        byteBuf2.markReaderIndex();
        if (n3 < n2) {
            for (int i = n2 / n3; i > 0; --i) {
                byteBuf2.readerIndex(writerIndex - n3);
                byteBuf2.readBytes(byteBuf2, n3);
            }
            if (n2 % n3 != 0) {
                byteBuf2.readerIndex(writerIndex - n3);
                byteBuf2.readBytes(byteBuf2, n2 % n3);
            }
        }
        else {
            byteBuf2.readerIndex(writerIndex - n3);
            byteBuf2.readBytes(byteBuf2, n2);
        }
        byteBuf2.resetReaderIndex();
        return n2;
    }
    
    private static int decodeCopyWith2ByteOffset(final byte b, final ByteBuf byteBuf, final ByteBuf byteBuf2, final int n) {
        if (byteBuf.readableBytes() < 2) {
            return -1;
        }
        final int writerIndex = byteBuf2.writerIndex();
        final int n2 = 1 + (b >> 2 & 0x3F);
        final short swapShort = ByteBufUtil.swapShort(byteBuf.readShort());
        validateOffset(swapShort, n);
        byteBuf2.markReaderIndex();
        if (swapShort < n2) {
            for (int i = n2 / swapShort; i > 0; --i) {
                byteBuf2.readerIndex(writerIndex - swapShort);
                byteBuf2.readBytes(byteBuf2, swapShort);
            }
            if (n2 % swapShort != 0) {
                byteBuf2.readerIndex(writerIndex - swapShort);
                byteBuf2.readBytes(byteBuf2, n2 % swapShort);
            }
        }
        else {
            byteBuf2.readerIndex(writerIndex - swapShort);
            byteBuf2.readBytes(byteBuf2, n2);
        }
        byteBuf2.resetReaderIndex();
        return n2;
    }
    
    private static int decodeCopyWith4ByteOffset(final byte b, final ByteBuf byteBuf, final ByteBuf byteBuf2, final int n) {
        if (byteBuf.readableBytes() < 4) {
            return -1;
        }
        final int writerIndex = byteBuf2.writerIndex();
        final int n2 = 1 + (b >> 2 & 0x3F);
        final int swapInt = ByteBufUtil.swapInt(byteBuf.readInt());
        validateOffset(swapInt, n);
        byteBuf2.markReaderIndex();
        if (swapInt < n2) {
            for (int i = n2 / swapInt; i > 0; --i) {
                byteBuf2.readerIndex(writerIndex - swapInt);
                byteBuf2.readBytes(byteBuf2, swapInt);
            }
            if (n2 % swapInt != 0) {
                byteBuf2.readerIndex(writerIndex - swapInt);
                byteBuf2.readBytes(byteBuf2, n2 % swapInt);
            }
        }
        else {
            byteBuf2.readerIndex(writerIndex - swapInt);
            byteBuf2.readBytes(byteBuf2, n2);
        }
        byteBuf2.resetReaderIndex();
        return n2;
    }
    
    private static void validateOffset(final int n, final int n2) {
        if (n > 32767) {
            throw new DecompressionException("Offset exceeds maximum permissible value");
        }
        if (n <= 0) {
            throw new DecompressionException("Offset is less than minimum permissible value");
        }
        if (n > n2) {
            throw new DecompressionException("Offset exceeds size of chunk");
        }
    }
    
    public static int calculateChecksum(final ByteBuf byteBuf) {
        return calculateChecksum(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }
    
    public static int calculateChecksum(final ByteBuf byteBuf, final int n, final int n2) {
        final Crc32c crc32c = new Crc32c();
        if (byteBuf.hasArray()) {
            crc32c.update(byteBuf.array(), byteBuf.arrayOffset() + n, n2);
        }
        else {
            final byte[] array = new byte[n2];
            byteBuf.getBytes(n, array);
            crc32c.update(array, 0, n2);
        }
        final int maskChecksum = maskChecksum((int)crc32c.getValue());
        crc32c.reset();
        return maskChecksum;
    }
    
    static void validateChecksum(final int n, final ByteBuf byteBuf) {
        validateChecksum(n, byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }
    
    static void validateChecksum(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        final int calculateChecksum = calculateChecksum(byteBuf, n2, n3);
        if (calculateChecksum != n) {
            throw new DecompressionException("mismatching checksum: " + Integer.toHexString(calculateChecksum) + " (expected: " + Integer.toHexString(n) + ')');
        }
    }
    
    static int maskChecksum(final int n) {
        return (n >> 15 | n << 17) - 1568478504;
    }
    
    private enum State
    {
        READY("READY", 0), 
        READING_PREAMBLE("READING_PREAMBLE", 1), 
        READING_TAG("READING_TAG", 2), 
        READING_LITERAL("READING_LITERAL", 3), 
        READING_COPY("READING_COPY", 4);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.READY, State.READING_PREAMBLE, State.READING_TAG, State.READING_LITERAL, State.READING_COPY };
        }
    }
}
