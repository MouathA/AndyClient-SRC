package org.apache.commons.compress.archivers.sevenz;

import java.util.zip.*;
import java.io.*;
import org.apache.commons.compress.utils.*;
import java.util.*;

public class SevenZFile implements Closeable
{
    static final int SIGNATURE_HEADER_SIZE = 32;
    private RandomAccessFile file;
    private final Archive archive;
    private int currentEntryIndex;
    private int currentFolderIndex;
    private InputStream currentFolderInputStream;
    private InputStream currentEntryInputStream;
    private byte[] password;
    static final byte[] sevenZSignature;
    
    public SevenZFile(final File file, final byte[] array) throws IOException {
        this.currentEntryIndex = -1;
        this.currentFolderIndex = -1;
        this.currentFolderInputStream = null;
        this.currentEntryInputStream = null;
        this.file = new RandomAccessFile(file, "r");
        this.archive = this.readHeaders(array);
        if (array != null) {
            System.arraycopy(array, 0, this.password = new byte[array.length], 0, array.length);
        }
        else {
            this.password = null;
        }
        if (!true) {
            this.file.close();
        }
    }
    
    public SevenZFile(final File file) throws IOException {
        this(file, null);
    }
    
    public void close() throws IOException {
        if (this.file != null) {
            this.file.close();
            this.file = null;
            if (this.password != null) {
                Arrays.fill(this.password, (byte)0);
            }
            this.password = null;
        }
    }
    
    public SevenZArchiveEntry getNextEntry() throws IOException {
        if (this.currentEntryIndex >= this.archive.files.length - 1) {
            return null;
        }
        ++this.currentEntryIndex;
        final SevenZArchiveEntry sevenZArchiveEntry = this.archive.files[this.currentEntryIndex];
        this.buildDecodingStream();
        return sevenZArchiveEntry;
    }
    
    private Archive readHeaders(final byte[] array) throws IOException {
        final byte[] array2 = new byte[6];
        this.file.readFully(array2);
        if (!Arrays.equals(array2, SevenZFile.sevenZSignature)) {
            throw new IOException("Bad 7z signature");
        }
        final byte byte1 = this.file.readByte();
        final byte byte2 = this.file.readByte();
        if (byte1 != 0) {
            throw new IOException(String.format("Unsupported 7z version (%d,%d)", byte1, byte2));
        }
        final StartHeader startHeader = this.readStartHeader(0xFFFFFFFFL & (long)Integer.reverseBytes(this.file.readInt()));
        final int n = (int)startHeader.nextHeaderSize;
        if (n != startHeader.nextHeaderSize) {
            throw new IOException("cannot handle nextHeaderSize " + startHeader.nextHeaderSize);
        }
        this.file.seek(32L + startHeader.nextHeaderOffset);
        final byte[] array3 = new byte[n];
        this.file.readFully(array3);
        final CRC32 crc32 = new CRC32();
        crc32.update(array3);
        if (startHeader.nextHeaderCrc != crc32.getValue()) {
            throw new IOException("NextHeader CRC mismatch");
        }
        DataInputStream encodedHeader = new DataInputStream(new ByteArrayInputStream(array3));
        Archive archive = new Archive();
        int n2 = encodedHeader.readUnsignedByte();
        if (n2 == 23) {
            encodedHeader = this.readEncodedHeader(encodedHeader, archive, array);
            archive = new Archive();
            n2 = encodedHeader.readUnsignedByte();
        }
        if (n2 == 1) {
            this.readHeader(encodedHeader, archive);
            encodedHeader.close();
            return archive;
        }
        throw new IOException("Broken or unsupported archive: no Header");
    }
    
    private StartHeader readStartHeader(final long n) throws IOException {
        final StartHeader startHeader = new StartHeader();
        final DataInputStream dataInputStream = new DataInputStream(new CRC32VerifyingInputStream(new BoundedRandomAccessFileInputStream(this.file, 20L), 20L, n));
        startHeader.nextHeaderOffset = Long.reverseBytes(dataInputStream.readLong());
        startHeader.nextHeaderSize = Long.reverseBytes(dataInputStream.readLong());
        startHeader.nextHeaderCrc = (0xFFFFFFFFL & (long)Integer.reverseBytes(dataInputStream.readInt()));
        final StartHeader startHeader2 = startHeader;
        if (dataInputStream != null) {
            dataInputStream.close();
        }
        return startHeader2;
    }
    
    private void readHeader(final DataInput dataInput, final Archive archive) throws IOException {
        int n = dataInput.readUnsignedByte();
        if (n == 2) {
            this.readArchiveProperties(dataInput);
            n = dataInput.readUnsignedByte();
        }
        if (n == 3) {
            throw new IOException("Additional streams unsupported");
        }
        if (n == 4) {
            this.readStreamsInfo(dataInput, archive);
            n = dataInput.readUnsignedByte();
        }
        if (n == 5) {
            this.readFilesInfo(dataInput, archive);
            n = dataInput.readUnsignedByte();
        }
        if (n != 0) {
            throw new IOException("Badly terminated header");
        }
    }
    
    private void readArchiveProperties(final DataInput dataInput) throws IOException {
        for (int i = dataInput.readUnsignedByte(); i != 0; i = dataInput.readUnsignedByte()) {
            dataInput.readFully(new byte[(int)readUint64(dataInput)]);
        }
    }
    
    private DataInputStream readEncodedHeader(final DataInputStream dataInputStream, final Archive archive, final byte[] array) throws IOException {
        this.readStreamsInfo(dataInputStream, archive);
        final Folder folder = archive.folders[0];
        this.file.seek(32L + archive.packPos + 0L);
        InputStream addDecoder = new BoundedRandomAccessFileInputStream(this.file, archive.packSizes[0]);
        for (final Coder coder : folder.getOrderedCoders()) {
            if (coder.numInStreams != 1L || coder.numOutStreams != 1L) {
                throw new IOException("Multi input/output stream coders are not yet supported");
            }
            addDecoder = Coders.addDecoder(addDecoder, coder, array);
        }
        if (folder.hasCrc) {
            addDecoder = new CRC32VerifyingInputStream(addDecoder, folder.getUnpackSize(), folder.crc);
        }
        final byte[] array2 = new byte[(int)folder.getUnpackSize()];
        final DataInputStream dataInputStream2 = new DataInputStream(addDecoder);
        dataInputStream2.readFully(array2);
        dataInputStream2.close();
        return new DataInputStream(new ByteArrayInputStream(array2));
    }
    
    private void readStreamsInfo(final DataInput dataInput, final Archive archive) throws IOException {
        int n = dataInput.readUnsignedByte();
        if (n == 6) {
            this.readPackInfo(dataInput, archive);
            n = dataInput.readUnsignedByte();
        }
        if (n == 7) {
            this.readUnpackInfo(dataInput, archive);
            n = dataInput.readUnsignedByte();
        }
        else {
            archive.folders = new Folder[0];
        }
        if (n == 8) {
            this.readSubStreamsInfo(dataInput, archive);
            n = dataInput.readUnsignedByte();
        }
        if (n != 0) {
            throw new IOException("Badly terminated StreamsInfo");
        }
    }
    
    private void readPackInfo(final DataInput dataInput, final Archive archive) throws IOException {
        archive.packPos = readUint64(dataInput);
        final long uint64 = readUint64(dataInput);
        int n = dataInput.readUnsignedByte();
        int n2 = 0;
        if (n == 9) {
            archive.packSizes = new long[(int)uint64];
            while (0 < archive.packSizes.length) {
                archive.packSizes[0] = readUint64(dataInput);
                ++n2;
            }
            n = dataInput.readUnsignedByte();
        }
        if (n == 10) {
            archive.packCrcsDefined = this.readAllOrBits(dataInput, (int)uint64);
            archive.packCrcs = new long[(int)uint64];
            while (0 < (int)uint64) {
                if (archive.packCrcsDefined.get(0)) {
                    archive.packCrcs[0] = (0xFFFFFFFFL & (long)Integer.reverseBytes(dataInput.readInt()));
                }
                ++n2;
            }
            n = dataInput.readUnsignedByte();
        }
        if (n != 0) {
            throw new IOException("Badly terminated PackInfo (" + n + ")");
        }
    }
    
    private void readUnpackInfo(final DataInput dataInput, final Archive archive) throws IOException {
        final int unsignedByte = dataInput.readUnsignedByte();
        if (unsignedByte != 11) {
            throw new IOException("Expected kFolder, got " + unsignedByte);
        }
        final long uint64 = readUint64(dataInput);
        final Folder[] folders = new Folder[(int)uint64];
        archive.folders = folders;
        if (dataInput.readUnsignedByte() != 0) {
            throw new IOException("External unsupported");
        }
        while (0 < (int)uint64) {
            folders[0] = this.readFolder(dataInput);
            int n = 0;
            ++n;
        }
        final int unsignedByte2 = dataInput.readUnsignedByte();
        if (unsignedByte2 != 12) {
            throw new IOException("Expected kCodersUnpackSize, got " + unsignedByte2);
        }
        final Folder[] array = folders;
        int length = array.length;
        while (0 < 0) {
            final Folder folder = array[0];
            folder.unpackSizes = new long[(int)folder.totalOutputStreams];
            while (0 < folder.totalOutputStreams) {
                folder.unpackSizes[0] = readUint64(dataInput);
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        int n4 = dataInput.readUnsignedByte();
        if (n4 == 10) {
            final BitSet allOrBits = this.readAllOrBits(dataInput, (int)uint64);
            while (0 < (int)uint64) {
                if (allOrBits.get(0)) {
                    folders[0].hasCrc = true;
                    folders[0].crc = (0xFFFFFFFFL & (long)Integer.reverseBytes(dataInput.readInt()));
                }
                else {
                    folders[0].hasCrc = false;
                }
                ++length;
            }
            n4 = dataInput.readUnsignedByte();
        }
        if (n4 != 0) {
            throw new IOException("Badly terminated UnpackInfo");
        }
    }
    
    private void readSubStreamsInfo(final DataInput p0, final Archive p1) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        org/apache/commons/compress/archivers/sevenz/Archive.folders:[Lorg/apache/commons/compress/archivers/sevenz/Folder;
        //     4: astore_3       
        //     5: aload_3        
        //     6: arraylength    
        //     7: istore          4
        //     9: iconst_0       
        //    10: iload           4
        //    12: if_icmpge       32
        //    15: aload_3        
        //    16: iconst_0       
        //    17: aaload         
        //    18: astore          6
        //    20: aload           6
        //    22: iconst_1       
        //    23: putfield        org/apache/commons/compress/archivers/sevenz/Folder.numUnpackSubStreams:I
        //    26: iinc            5, 1
        //    29: goto            9
        //    32: aload_2        
        //    33: getfield        org/apache/commons/compress/archivers/sevenz/Archive.folders:[Lorg/apache/commons/compress/archivers/sevenz/Folder;
        //    36: arraylength    
        //    37: istore_3       
        //    38: aload_1        
        //    39: invokeinterface java/io/DataInput.readUnsignedByte:()I
        //    44: istore          4
        //    46: iload           4
        //    48: bipush          13
        //    50: if_icmpne       110
        //    53: aload_2        
        //    54: getfield        org/apache/commons/compress/archivers/sevenz/Archive.folders:[Lorg/apache/commons/compress/archivers/sevenz/Folder;
        //    57: astore          5
        //    59: aload           5
        //    61: arraylength    
        //    62: istore          6
        //    64: iconst_0       
        //    65: iconst_0       
        //    66: if_icmpge       102
        //    69: aload           5
        //    71: iconst_0       
        //    72: aaload         
        //    73: astore          8
        //    75: aload_1        
        //    76: invokestatic    org/apache/commons/compress/archivers/sevenz/SevenZFile.readUint64:(Ljava/io/DataInput;)J
        //    79: lstore          9
        //    81: aload           8
        //    83: lload           9
        //    85: l2i            
        //    86: putfield        org/apache/commons/compress/archivers/sevenz/Folder.numUnpackSubStreams:I
        //    89: iconst_0       
        //    90: i2l            
        //    91: lload           9
        //    93: ladd           
        //    94: l2i            
        //    95: istore_3       
        //    96: iinc            7, 1
        //    99: goto            64
        //   102: aload_1        
        //   103: invokeinterface java/io/DataInput.readUnsignedByte:()I
        //   108: istore          4
        //   110: new             Lorg/apache/commons/compress/archivers/sevenz/SubStreamsInfo;
        //   113: dup            
        //   114: invokespecial   org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.<init>:()V
        //   117: astore          5
        //   119: aload           5
        //   121: iconst_0       
        //   122: newarray        J
        //   124: putfield        org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.unpackSizes:[J
        //   127: aload           5
        //   129: new             Ljava/util/BitSet;
        //   132: dup            
        //   133: iconst_0       
        //   134: invokespecial   java/util/BitSet.<init>:(I)V
        //   137: putfield        org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.hasCrc:Ljava/util/BitSet;
        //   140: aload           5
        //   142: iconst_0       
        //   143: newarray        J
        //   145: putfield        org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.crcs:[J
        //   148: aload_2        
        //   149: getfield        org/apache/commons/compress/archivers/sevenz/Archive.folders:[Lorg/apache/commons/compress/archivers/sevenz/Folder;
        //   152: astore          7
        //   154: aload           7
        //   156: arraylength    
        //   157: istore          8
        //   159: iconst_0       
        //   160: iload           8
        //   162: if_icmpge       258
        //   165: aload           7
        //   167: iconst_0       
        //   168: aaload         
        //   169: astore          10
        //   171: aload           10
        //   173: getfield        org/apache/commons/compress/archivers/sevenz/Folder.numUnpackSubStreams:I
        //   176: ifne            182
        //   179: goto            252
        //   182: lconst_0       
        //   183: lstore          11
        //   185: iload           4
        //   187: bipush          9
        //   189: if_icmpne       234
        //   192: iconst_0       
        //   193: aload           10
        //   195: getfield        org/apache/commons/compress/archivers/sevenz/Folder.numUnpackSubStreams:I
        //   198: iconst_1       
        //   199: isub           
        //   200: if_icmpge       234
        //   203: aload_1        
        //   204: invokestatic    org/apache/commons/compress/archivers/sevenz/SevenZFile.readUint64:(Ljava/io/DataInput;)J
        //   207: lstore          14
        //   209: aload           5
        //   211: getfield        org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.unpackSizes:[J
        //   214: iconst_0       
        //   215: iinc            6, 1
        //   218: lload           14
        //   220: lastore        
        //   221: lload           11
        //   223: lload           14
        //   225: ladd           
        //   226: lstore          11
        //   228: iinc            13, 1
        //   231: goto            192
        //   234: aload           5
        //   236: getfield        org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.unpackSizes:[J
        //   239: iconst_0       
        //   240: iinc            6, 1
        //   243: aload           10
        //   245: invokevirtual   org/apache/commons/compress/archivers/sevenz/Folder.getUnpackSize:()J
        //   248: lload           11
        //   250: lsub           
        //   251: lastore        
        //   252: iinc            9, 1
        //   255: goto            159
        //   258: iload           4
        //   260: bipush          9
        //   262: if_icmpne       273
        //   265: aload_1        
        //   266: invokeinterface java/io/DataInput.readUnsignedByte:()I
        //   271: istore          4
        //   273: aload_2        
        //   274: getfield        org/apache/commons/compress/archivers/sevenz/Archive.folders:[Lorg/apache/commons/compress/archivers/sevenz/Folder;
        //   277: astore          8
        //   279: aload           8
        //   281: arraylength    
        //   282: istore          9
        //   284: iconst_0       
        //   285: iconst_0       
        //   286: if_icmpge       327
        //   289: aload           8
        //   291: iconst_0       
        //   292: aaload         
        //   293: astore          11
        //   295: aload           11
        //   297: getfield        org/apache/commons/compress/archivers/sevenz/Folder.numUnpackSubStreams:I
        //   300: iconst_1       
        //   301: if_icmpne       312
        //   304: aload           11
        //   306: getfield        org/apache/commons/compress/archivers/sevenz/Folder.hasCrc:Z
        //   309: ifne            321
        //   312: iconst_0       
        //   313: aload           11
        //   315: getfield        org/apache/commons/compress/archivers/sevenz/Folder.numUnpackSubStreams:I
        //   318: iadd           
        //   319: istore          7
        //   321: iinc            10, 1
        //   324: goto            284
        //   327: iload           4
        //   329: bipush          10
        //   331: if_icmpne       513
        //   334: aload_0        
        //   335: aload_1        
        //   336: iconst_0       
        //   337: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZFile.readAllOrBits:(Ljava/io/DataInput;I)Ljava/util/BitSet;
        //   340: astore          8
        //   342: iconst_0       
        //   343: newarray        J
        //   345: astore          9
        //   347: iconst_0       
        //   348: iconst_0       
        //   349: if_icmpge       385
        //   352: aload           8
        //   354: iconst_0       
        //   355: invokevirtual   java/util/BitSet.get:(I)Z
        //   358: ifeq            379
        //   361: aload           9
        //   363: iconst_0       
        //   364: ldc2_w          4294967295
        //   367: aload_1        
        //   368: invokeinterface java/io/DataInput.readInt:()I
        //   373: invokestatic    java/lang/Integer.reverseBytes:(I)I
        //   376: i2l            
        //   377: land           
        //   378: lastore        
        //   379: iinc            10, 1
        //   382: goto            347
        //   385: aload_2        
        //   386: getfield        org/apache/commons/compress/archivers/sevenz/Archive.folders:[Lorg/apache/commons/compress/archivers/sevenz/Folder;
        //   389: astore          12
        //   391: aload           12
        //   393: arraylength    
        //   394: istore          13
        //   396: iconst_0       
        //   397: iconst_0       
        //   398: if_icmpge       505
        //   401: aload           12
        //   403: iconst_0       
        //   404: aaload         
        //   405: astore          15
        //   407: aload           15
        //   409: getfield        org/apache/commons/compress/archivers/sevenz/Folder.numUnpackSubStreams:I
        //   412: iconst_1       
        //   413: if_icmpne       452
        //   416: aload           15
        //   418: getfield        org/apache/commons/compress/archivers/sevenz/Folder.hasCrc:Z
        //   421: ifeq            452
        //   424: aload           5
        //   426: getfield        org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.hasCrc:Ljava/util/BitSet;
        //   429: iconst_0       
        //   430: iconst_1       
        //   431: invokevirtual   java/util/BitSet.set:(IZ)V
        //   434: aload           5
        //   436: getfield        org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.crcs:[J
        //   439: iconst_0       
        //   440: aload           15
        //   442: getfield        org/apache/commons/compress/archivers/sevenz/Folder.crc:J
        //   445: lastore        
        //   446: iinc            10, 1
        //   449: goto            499
        //   452: iconst_0       
        //   453: aload           15
        //   455: getfield        org/apache/commons/compress/archivers/sevenz/Folder.numUnpackSubStreams:I
        //   458: if_icmpge       499
        //   461: aload           5
        //   463: getfield        org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.hasCrc:Ljava/util/BitSet;
        //   466: iconst_0       
        //   467: aload           8
        //   469: iconst_0       
        //   470: invokevirtual   java/util/BitSet.get:(I)Z
        //   473: invokevirtual   java/util/BitSet.set:(IZ)V
        //   476: aload           5
        //   478: getfield        org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.crcs:[J
        //   481: iconst_0       
        //   482: aload           9
        //   484: iconst_0       
        //   485: laload         
        //   486: lastore        
        //   487: iinc            10, 1
        //   490: iinc            11, 1
        //   493: iinc            16, 1
        //   496: goto            452
        //   499: iinc            14, 1
        //   502: goto            396
        //   505: aload_1        
        //   506: invokeinterface java/io/DataInput.readUnsignedByte:()I
        //   511: istore          4
        //   513: iload           4
        //   515: ifeq            529
        //   518: new             Ljava/io/IOException;
        //   521: dup            
        //   522: ldc_w           "Badly terminated SubStreamsInfo"
        //   525: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   528: athrow         
        //   529: aload_2        
        //   530: aload           5
        //   532: putfield        org/apache/commons/compress/archivers/sevenz/Archive.subStreamsInfo:Lorg/apache/commons/compress/archivers/sevenz/SubStreamsInfo;
        //   535: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private Folder readFolder(final DataInput p0) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   org/apache/commons/compress/archivers/sevenz/Folder.<init>:()V
        //     7: astore_2       
        //     8: aload_1        
        //     9: invokestatic    org/apache/commons/compress/archivers/sevenz/SevenZFile.readUint64:(Ljava/io/DataInput;)J
        //    12: lstore_3       
        //    13: lload_3        
        //    14: l2i            
        //    15: anewarray       Lorg/apache/commons/compress/archivers/sevenz/Coder;
        //    18: astore          5
        //    20: lconst_0       
        //    21: lstore          6
        //    23: lconst_0       
        //    24: lstore          8
        //    26: iconst_0       
        //    27: aload           5
        //    29: arraylength    
        //    30: if_icmpge       256
        //    33: aload           5
        //    35: iconst_0       
        //    36: new             Lorg/apache/commons/compress/archivers/sevenz/Coder;
        //    39: dup            
        //    40: invokespecial   org/apache/commons/compress/archivers/sevenz/Coder.<init>:()V
        //    43: aastore        
        //    44: aload_1        
        //    45: invokeinterface java/io/DataInput.readUnsignedByte:()I
        //    50: istore          11
        //    52: iload           11
        //    54: bipush          15
        //    56: iand           
        //    57: istore          12
        //    59: iload           11
        //    61: bipush          16
        //    63: iand           
        //    64: ifne            71
        //    67: iconst_1       
        //    68: goto            72
        //    71: iconst_0       
        //    72: istore          13
        //    74: iload           11
        //    76: bipush          32
        //    78: iand           
        //    79: ifeq            86
        //    82: iconst_1       
        //    83: goto            87
        //    86: iconst_0       
        //    87: istore          14
        //    89: iload           11
        //    91: sipush          128
        //    94: iand           
        //    95: ifeq            102
        //    98: iconst_1       
        //    99: goto            103
        //   102: iconst_0       
        //   103: istore          15
        //   105: aload           5
        //   107: iconst_0       
        //   108: aaload         
        //   109: iload           12
        //   111: newarray        B
        //   113: putfield        org/apache/commons/compress/archivers/sevenz/Coder.decompressionMethodId:[B
        //   116: aload_1        
        //   117: aload           5
        //   119: iconst_0       
        //   120: aaload         
        //   121: getfield        org/apache/commons/compress/archivers/sevenz/Coder.decompressionMethodId:[B
        //   124: invokeinterface java/io/DataInput.readFully:([B)V
        //   129: iconst_0       
        //   130: ifeq            152
        //   133: aload           5
        //   135: iconst_0       
        //   136: aaload         
        //   137: lconst_1       
        //   138: putfield        org/apache/commons/compress/archivers/sevenz/Coder.numInStreams:J
        //   141: aload           5
        //   143: iconst_0       
        //   144: aaload         
        //   145: lconst_1       
        //   146: putfield        org/apache/commons/compress/archivers/sevenz/Coder.numOutStreams:J
        //   149: goto            174
        //   152: aload           5
        //   154: iconst_0       
        //   155: aaload         
        //   156: aload_1        
        //   157: invokestatic    org/apache/commons/compress/archivers/sevenz/SevenZFile.readUint64:(Ljava/io/DataInput;)J
        //   160: putfield        org/apache/commons/compress/archivers/sevenz/Coder.numInStreams:J
        //   163: aload           5
        //   165: iconst_0       
        //   166: aaload         
        //   167: aload_1        
        //   168: invokestatic    org/apache/commons/compress/archivers/sevenz/SevenZFile.readUint64:(Ljava/io/DataInput;)J
        //   171: putfield        org/apache/commons/compress/archivers/sevenz/Coder.numOutStreams:J
        //   174: lload           6
        //   176: aload           5
        //   178: iconst_0       
        //   179: aaload         
        //   180: getfield        org/apache/commons/compress/archivers/sevenz/Coder.numInStreams:J
        //   183: ladd           
        //   184: lstore          6
        //   186: lload           8
        //   188: aload           5
        //   190: iconst_0       
        //   191: aaload         
        //   192: getfield        org/apache/commons/compress/archivers/sevenz/Coder.numOutStreams:J
        //   195: ladd           
        //   196: lstore          8
        //   198: iload           14
        //   200: ifeq            234
        //   203: aload_1        
        //   204: invokestatic    org/apache/commons/compress/archivers/sevenz/SevenZFile.readUint64:(Ljava/io/DataInput;)J
        //   207: lstore          16
        //   209: aload           5
        //   211: iconst_0       
        //   212: aaload         
        //   213: lload           16
        //   215: l2i            
        //   216: newarray        B
        //   218: putfield        org/apache/commons/compress/archivers/sevenz/Coder.properties:[B
        //   221: aload_1        
        //   222: aload           5
        //   224: iconst_0       
        //   225: aaload         
        //   226: getfield        org/apache/commons/compress/archivers/sevenz/Coder.properties:[B
        //   229: invokeinterface java/io/DataInput.readFully:([B)V
        //   234: iload           15
        //   236: ifeq            250
        //   239: new             Ljava/io/IOException;
        //   242: dup            
        //   243: ldc_w           "Alternative methods are unsupported, please report. The reference implementation doesn't support them either."
        //   246: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   249: athrow         
        //   250: iinc            10, 1
        //   253: goto            26
        //   256: aload_2        
        //   257: aload           5
        //   259: putfield        org/apache/commons/compress/archivers/sevenz/Folder.coders:[Lorg/apache/commons/compress/archivers/sevenz/Coder;
        //   262: aload_2        
        //   263: lload           6
        //   265: putfield        org/apache/commons/compress/archivers/sevenz/Folder.totalInputStreams:J
        //   268: aload_2        
        //   269: lload           8
        //   271: putfield        org/apache/commons/compress/archivers/sevenz/Folder.totalOutputStreams:J
        //   274: lload           8
        //   276: lconst_0       
        //   277: lcmp           
        //   278: ifne            292
        //   281: new             Ljava/io/IOException;
        //   284: dup            
        //   285: ldc_w           "Total output streams can't be 0"
        //   288: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   291: athrow         
        //   292: lload           8
        //   294: lconst_1       
        //   295: lsub           
        //   296: lstore          10
        //   298: lload           10
        //   300: l2i            
        //   301: anewarray       Lorg/apache/commons/compress/archivers/sevenz/BindPair;
        //   304: astore          12
        //   306: iconst_0       
        //   307: aload           12
        //   309: arraylength    
        //   310: if_icmpge       352
        //   313: aload           12
        //   315: iconst_0       
        //   316: new             Lorg/apache/commons/compress/archivers/sevenz/BindPair;
        //   319: dup            
        //   320: invokespecial   org/apache/commons/compress/archivers/sevenz/BindPair.<init>:()V
        //   323: aastore        
        //   324: aload           12
        //   326: iconst_0       
        //   327: aaload         
        //   328: aload_1        
        //   329: invokestatic    org/apache/commons/compress/archivers/sevenz/SevenZFile.readUint64:(Ljava/io/DataInput;)J
        //   332: putfield        org/apache/commons/compress/archivers/sevenz/BindPair.inIndex:J
        //   335: aload           12
        //   337: iconst_0       
        //   338: aaload         
        //   339: aload_1        
        //   340: invokestatic    org/apache/commons/compress/archivers/sevenz/SevenZFile.readUint64:(Ljava/io/DataInput;)J
        //   343: putfield        org/apache/commons/compress/archivers/sevenz/BindPair.outIndex:J
        //   346: iinc            13, 1
        //   349: goto            306
        //   352: aload_2        
        //   353: aload           12
        //   355: putfield        org/apache/commons/compress/archivers/sevenz/Folder.bindPairs:[Lorg/apache/commons/compress/archivers/sevenz/BindPair;
        //   358: lload           6
        //   360: lload           10
        //   362: lcmp           
        //   363: ifge            377
        //   366: new             Ljava/io/IOException;
        //   369: dup            
        //   370: ldc_w           "Total input streams can't be less than the number of bind pairs"
        //   373: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   376: athrow         
        //   377: lload           6
        //   379: lload           10
        //   381: lsub           
        //   382: lstore          13
        //   384: lload           13
        //   386: l2i            
        //   387: newarray        J
        //   389: astore          15
        //   391: lload           13
        //   393: lconst_1       
        //   394: lcmp           
        //   395: ifne            449
        //   398: iconst_0       
        //   399: lload           6
        //   401: l2i            
        //   402: if_icmpge       422
        //   405: aload_2        
        //   406: iconst_0       
        //   407: invokevirtual   org/apache/commons/compress/archivers/sevenz/Folder.findBindPairForInStream:(I)I
        //   410: ifge            416
        //   413: goto            422
        //   416: iinc            16, 1
        //   419: goto            398
        //   422: iconst_0       
        //   423: lload           6
        //   425: l2i            
        //   426: if_icmpne       440
        //   429: new             Ljava/io/IOException;
        //   432: dup            
        //   433: ldc_w           "Couldn't find stream's bind pair index"
        //   436: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   439: athrow         
        //   440: aload           15
        //   442: iconst_0       
        //   443: iconst_0       
        //   444: i2l            
        //   445: lastore        
        //   446: goto            470
        //   449: iconst_0       
        //   450: lload           13
        //   452: l2i            
        //   453: if_icmpge       470
        //   456: aload           15
        //   458: iconst_0       
        //   459: aload_1        
        //   460: invokestatic    org/apache/commons/compress/archivers/sevenz/SevenZFile.readUint64:(Ljava/io/DataInput;)J
        //   463: lastore        
        //   464: iinc            16, 1
        //   467: goto            449
        //   470: aload_2        
        //   471: aload           15
        //   473: putfield        org/apache/commons/compress/archivers/sevenz/Folder.packedStreams:[J
        //   476: aload_2        
        //   477: areturn        
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private BitSet readAllOrBits(final DataInput dataInput, final int n) throws IOException {
        BitSet bits;
        if (dataInput.readUnsignedByte() != 0) {
            bits = new BitSet(n);
            while (0 < n) {
                bits.set(0, true);
                int n2 = 0;
                ++n2;
            }
        }
        else {
            bits = this.readBits(dataInput, n);
        }
        return bits;
    }
    
    private BitSet readBits(final DataInput dataInput, final int n) throws IOException {
        final BitSet set = new BitSet(n);
        while (0 < n) {
            if (128 == 0) {
                dataInput.readUnsignedByte();
            }
            set.set(0, false);
            int n2 = 0;
            ++n2;
        }
        return set;
    }
    
    private void readFilesInfo(final DataInput p0, final Archive p1) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    org/apache/commons/compress/archivers/sevenz/SevenZFile.readUint64:(Ljava/io/DataInput;)J
        //     4: lstore_3       
        //     5: lload_3        
        //     6: l2i            
        //     7: anewarray       Lorg/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry;
        //    10: astore          5
        //    12: iconst_0       
        //    13: aload           5
        //    15: arraylength    
        //    16: if_icmpge       36
        //    19: aload           5
        //    21: iconst_0       
        //    22: new             Lorg/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry;
        //    25: dup            
        //    26: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.<init>:()V
        //    29: aastore        
        //    30: iinc            6, 1
        //    33: goto            12
        //    36: aconst_null    
        //    37: astore          6
        //    39: aconst_null    
        //    40: astore          7
        //    42: aconst_null    
        //    43: astore          8
        //    45: aload_1        
        //    46: invokeinterface java/io/DataInput.readUnsignedByte:()I
        //    51: istore          9
        //    53: iconst_0       
        //    54: ifne            60
        //    57: goto            754
        //    60: aload_1        
        //    61: invokestatic    org/apache/commons/compress/archivers/sevenz/SevenZFile.readUint64:(Ljava/io/DataInput;)J
        //    64: lstore          10
        //    66: iconst_0       
        //    67: tableswitch {
        //               28: 128
        //               29: 141
        //               30: 172
        //               31: 203
        //               32: 345
        //               33: 434
        //               34: 523
        //               35: 612
        //               36: 723
        //               37: 723
        //               38: 701
        //               39: 712
        //          default: 723
        //        }
        //   128: aload_0        
        //   129: aload_1        
        //   130: aload           5
        //   132: arraylength    
        //   133: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZFile.readBits:(Ljava/io/DataInput;I)Ljava/util/BitSet;
        //   136: astore          6
        //   138: goto            751
        //   141: aload           6
        //   143: ifnonnull       157
        //   146: new             Ljava/io/IOException;
        //   149: dup            
        //   150: ldc_w           "Header format error: kEmptyStream must appear before kEmptyFile"
        //   153: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   156: athrow         
        //   157: aload_0        
        //   158: aload_1        
        //   159: aload           6
        //   161: invokevirtual   java/util/BitSet.cardinality:()I
        //   164: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZFile.readBits:(Ljava/io/DataInput;I)Ljava/util/BitSet;
        //   167: astore          7
        //   169: goto            751
        //   172: aload           6
        //   174: ifnonnull       188
        //   177: new             Ljava/io/IOException;
        //   180: dup            
        //   181: ldc_w           "Header format error: kEmptyStream must appear before kAnti"
        //   184: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   187: athrow         
        //   188: aload_0        
        //   189: aload_1        
        //   190: aload           6
        //   192: invokevirtual   java/util/BitSet.cardinality:()I
        //   195: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZFile.readBits:(Ljava/io/DataInput;I)Ljava/util/BitSet;
        //   198: astore          8
        //   200: goto            751
        //   203: aload_1        
        //   204: invokeinterface java/io/DataInput.readUnsignedByte:()I
        //   209: istore          12
        //   211: iload           12
        //   213: ifeq            227
        //   216: new             Ljava/io/IOException;
        //   219: dup            
        //   220: ldc_w           "Not implemented"
        //   223: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   226: athrow         
        //   227: lload           10
        //   229: lconst_1       
        //   230: lsub           
        //   231: lconst_1       
        //   232: land           
        //   233: lconst_0       
        //   234: lcmp           
        //   235: ifeq            249
        //   238: new             Ljava/io/IOException;
        //   241: dup            
        //   242: ldc_w           "File names length invalid"
        //   245: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   248: athrow         
        //   249: lload           10
        //   251: lconst_1       
        //   252: lsub           
        //   253: l2i            
        //   254: newarray        B
        //   256: astore          13
        //   258: aload_1        
        //   259: aload           13
        //   261: invokeinterface java/io/DataInput.readFully:([B)V
        //   266: iconst_0       
        //   267: aload           13
        //   269: arraylength    
        //   270: if_icmpge       317
        //   273: aload           13
        //   275: iconst_0       
        //   276: baload         
        //   277: ifne            311
        //   280: aload           13
        //   282: iconst_1       
        //   283: baload         
        //   284: ifne            311
        //   287: aload           5
        //   289: iconst_0       
        //   290: iinc            14, 1
        //   293: aaload         
        //   294: new             Ljava/lang/String;
        //   297: dup            
        //   298: aload           13
        //   300: iconst_0       
        //   301: iconst_0       
        //   302: ldc_w           "UTF-16LE"
        //   305: invokespecial   java/lang/String.<init>:([BIILjava/lang/String;)V
        //   308: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setName:(Ljava/lang/String;)V
        //   311: iinc            16, 2
        //   314: goto            266
        //   317: iconst_0       
        //   318: aload           13
        //   320: arraylength    
        //   321: if_icmpne       331
        //   324: iconst_0       
        //   325: aload           5
        //   327: arraylength    
        //   328: if_icmpeq       342
        //   331: new             Ljava/io/IOException;
        //   334: dup            
        //   335: ldc_w           "Error parsing file names"
        //   338: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   341: athrow         
        //   342: goto            751
        //   345: aload_0        
        //   346: aload_1        
        //   347: aload           5
        //   349: arraylength    
        //   350: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZFile.readAllOrBits:(Ljava/io/DataInput;I)Ljava/util/BitSet;
        //   353: astore          12
        //   355: aload_1        
        //   356: invokeinterface java/io/DataInput.readUnsignedByte:()I
        //   361: istore          13
        //   363: iload           13
        //   365: ifeq            379
        //   368: new             Ljava/io/IOException;
        //   371: dup            
        //   372: ldc_w           "Unimplemented"
        //   375: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   378: athrow         
        //   379: iconst_0       
        //   380: aload           5
        //   382: arraylength    
        //   383: if_icmpge       431
        //   386: aload           5
        //   388: iconst_0       
        //   389: aaload         
        //   390: aload           12
        //   392: iconst_0       
        //   393: invokevirtual   java/util/BitSet.get:(I)Z
        //   396: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setHasCreationDate:(Z)V
        //   399: aload           5
        //   401: iconst_0       
        //   402: aaload         
        //   403: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.getHasCreationDate:()Z
        //   406: ifeq            425
        //   409: aload           5
        //   411: iconst_0       
        //   412: aaload         
        //   413: aload_1        
        //   414: invokeinterface java/io/DataInput.readLong:()J
        //   419: invokestatic    java/lang/Long.reverseBytes:(J)J
        //   422: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setCreationDate:(J)V
        //   425: iinc            14, 1
        //   428: goto            379
        //   431: goto            751
        //   434: aload_0        
        //   435: aload_1        
        //   436: aload           5
        //   438: arraylength    
        //   439: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZFile.readAllOrBits:(Ljava/io/DataInput;I)Ljava/util/BitSet;
        //   442: astore          12
        //   444: aload_1        
        //   445: invokeinterface java/io/DataInput.readUnsignedByte:()I
        //   450: istore          13
        //   452: iload           13
        //   454: ifeq            468
        //   457: new             Ljava/io/IOException;
        //   460: dup            
        //   461: ldc_w           "Unimplemented"
        //   464: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   467: athrow         
        //   468: iconst_0       
        //   469: aload           5
        //   471: arraylength    
        //   472: if_icmpge       520
        //   475: aload           5
        //   477: iconst_0       
        //   478: aaload         
        //   479: aload           12
        //   481: iconst_0       
        //   482: invokevirtual   java/util/BitSet.get:(I)Z
        //   485: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setHasAccessDate:(Z)V
        //   488: aload           5
        //   490: iconst_0       
        //   491: aaload         
        //   492: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.getHasAccessDate:()Z
        //   495: ifeq            514
        //   498: aload           5
        //   500: iconst_0       
        //   501: aaload         
        //   502: aload_1        
        //   503: invokeinterface java/io/DataInput.readLong:()J
        //   508: invokestatic    java/lang/Long.reverseBytes:(J)J
        //   511: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setAccessDate:(J)V
        //   514: iinc            14, 1
        //   517: goto            468
        //   520: goto            751
        //   523: aload_0        
        //   524: aload_1        
        //   525: aload           5
        //   527: arraylength    
        //   528: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZFile.readAllOrBits:(Ljava/io/DataInput;I)Ljava/util/BitSet;
        //   531: astore          12
        //   533: aload_1        
        //   534: invokeinterface java/io/DataInput.readUnsignedByte:()I
        //   539: istore          13
        //   541: iload           13
        //   543: ifeq            557
        //   546: new             Ljava/io/IOException;
        //   549: dup            
        //   550: ldc_w           "Unimplemented"
        //   553: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   556: athrow         
        //   557: iconst_0       
        //   558: aload           5
        //   560: arraylength    
        //   561: if_icmpge       609
        //   564: aload           5
        //   566: iconst_0       
        //   567: aaload         
        //   568: aload           12
        //   570: iconst_0       
        //   571: invokevirtual   java/util/BitSet.get:(I)Z
        //   574: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setHasLastModifiedDate:(Z)V
        //   577: aload           5
        //   579: iconst_0       
        //   580: aaload         
        //   581: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.getHasLastModifiedDate:()Z
        //   584: ifeq            603
        //   587: aload           5
        //   589: iconst_0       
        //   590: aaload         
        //   591: aload_1        
        //   592: invokeinterface java/io/DataInput.readLong:()J
        //   597: invokestatic    java/lang/Long.reverseBytes:(J)J
        //   600: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setLastModifiedDate:(J)V
        //   603: iinc            14, 1
        //   606: goto            557
        //   609: goto            751
        //   612: aload_0        
        //   613: aload_1        
        //   614: aload           5
        //   616: arraylength    
        //   617: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZFile.readAllOrBits:(Ljava/io/DataInput;I)Ljava/util/BitSet;
        //   620: astore          12
        //   622: aload_1        
        //   623: invokeinterface java/io/DataInput.readUnsignedByte:()I
        //   628: istore          13
        //   630: iload           13
        //   632: ifeq            646
        //   635: new             Ljava/io/IOException;
        //   638: dup            
        //   639: ldc_w           "Unimplemented"
        //   642: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   645: athrow         
        //   646: iconst_0       
        //   647: aload           5
        //   649: arraylength    
        //   650: if_icmpge       698
        //   653: aload           5
        //   655: iconst_0       
        //   656: aaload         
        //   657: aload           12
        //   659: iconst_0       
        //   660: invokevirtual   java/util/BitSet.get:(I)Z
        //   663: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setHasWindowsAttributes:(Z)V
        //   666: aload           5
        //   668: iconst_0       
        //   669: aaload         
        //   670: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.getHasWindowsAttributes:()Z
        //   673: ifeq            692
        //   676: aload           5
        //   678: iconst_0       
        //   679: aaload         
        //   680: aload_1        
        //   681: invokeinterface java/io/DataInput.readInt:()I
        //   686: invokestatic    java/lang/Integer.reverseBytes:(I)I
        //   689: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setWindowsAttributes:(I)V
        //   692: iinc            14, 1
        //   695: goto            646
        //   698: goto            751
        //   701: new             Ljava/io/IOException;
        //   704: dup            
        //   705: ldc_w           "kStartPos is unsupported, please report"
        //   708: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   711: athrow         
        //   712: new             Ljava/io/IOException;
        //   715: dup            
        //   716: ldc_w           "kDummy is unsupported, please report"
        //   719: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   722: athrow         
        //   723: new             Ljava/io/IOException;
        //   726: dup            
        //   727: new             Ljava/lang/StringBuilder;
        //   730: dup            
        //   731: invokespecial   java/lang/StringBuilder.<init>:()V
        //   734: ldc_w           "Unknown property "
        //   737: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   740: iconst_0       
        //   741: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   744: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   747: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   750: athrow         
        //   751: goto            45
        //   754: iconst_0       
        //   755: aload           5
        //   757: arraylength    
        //   758: if_icmpge       950
        //   761: aload           5
        //   763: iconst_0       
        //   764: aaload         
        //   765: aload           6
        //   767: ifnonnull       774
        //   770: iconst_1       
        //   771: goto            788
        //   774: aload           6
        //   776: iconst_0       
        //   777: invokevirtual   java/util/BitSet.get:(I)Z
        //   780: ifne            787
        //   783: iconst_1       
        //   784: goto            788
        //   787: iconst_0       
        //   788: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setHasStream:(Z)V
        //   791: aload           5
        //   793: iconst_0       
        //   794: aaload         
        //   795: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.hasStream:()Z
        //   798: ifeq            873
        //   801: aload           5
        //   803: iconst_0       
        //   804: aaload         
        //   805: iconst_0       
        //   806: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setDirectory:(Z)V
        //   809: aload           5
        //   811: iconst_0       
        //   812: aaload         
        //   813: iconst_0       
        //   814: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setAntiItem:(Z)V
        //   817: aload           5
        //   819: iconst_0       
        //   820: aaload         
        //   821: aload_2        
        //   822: getfield        org/apache/commons/compress/archivers/sevenz/Archive.subStreamsInfo:Lorg/apache/commons/compress/archivers/sevenz/SubStreamsInfo;
        //   825: getfield        org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.hasCrc:Ljava/util/BitSet;
        //   828: iconst_0       
        //   829: invokevirtual   java/util/BitSet.get:(I)Z
        //   832: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setHasCrc:(Z)V
        //   835: aload           5
        //   837: iconst_0       
        //   838: aaload         
        //   839: aload_2        
        //   840: getfield        org/apache/commons/compress/archivers/sevenz/Archive.subStreamsInfo:Lorg/apache/commons/compress/archivers/sevenz/SubStreamsInfo;
        //   843: getfield        org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.crcs:[J
        //   846: iconst_0       
        //   847: laload         
        //   848: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setCrcValue:(J)V
        //   851: aload           5
        //   853: iconst_0       
        //   854: aaload         
        //   855: aload_2        
        //   856: getfield        org/apache/commons/compress/archivers/sevenz/Archive.subStreamsInfo:Lorg/apache/commons/compress/archivers/sevenz/SubStreamsInfo;
        //   859: getfield        org/apache/commons/compress/archivers/sevenz/SubStreamsInfo.unpackSizes:[J
        //   862: iconst_0       
        //   863: laload         
        //   864: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setSize:(J)V
        //   867: iinc            9, 1
        //   870: goto            944
        //   873: aload           5
        //   875: iconst_0       
        //   876: aaload         
        //   877: aload           7
        //   879: ifnonnull       886
        //   882: iconst_1       
        //   883: goto            900
        //   886: aload           7
        //   888: iconst_0       
        //   889: invokevirtual   java/util/BitSet.get:(I)Z
        //   892: ifne            899
        //   895: iconst_1       
        //   896: goto            900
        //   899: iconst_0       
        //   900: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setDirectory:(Z)V
        //   903: aload           5
        //   905: iconst_0       
        //   906: aaload         
        //   907: aload           8
        //   909: ifnonnull       916
        //   912: iconst_0       
        //   913: goto            922
        //   916: aload           8
        //   918: iconst_0       
        //   919: invokevirtual   java/util/BitSet.get:(I)Z
        //   922: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setAntiItem:(Z)V
        //   925: aload           5
        //   927: iconst_0       
        //   928: aaload         
        //   929: iconst_0       
        //   930: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setHasCrc:(Z)V
        //   933: aload           5
        //   935: iconst_0       
        //   936: aaload         
        //   937: lconst_0       
        //   938: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.setSize:(J)V
        //   941: iinc            10, 1
        //   944: iinc            11, 1
        //   947: goto            754
        //   950: aload_2        
        //   951: aload           5
        //   953: putfield        org/apache/commons/compress/archivers/sevenz/Archive.files:[Lorg/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry;
        //   956: aload_0        
        //   957: aload_2        
        //   958: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZFile.calculateStreamMap:(Lorg/apache/commons/compress/archivers/sevenz/Archive;)V
        //   961: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void calculateStreamMap(final Archive archive) throws IOException {
        final StreamMap streamMap = new StreamMap();
        final int n = (archive.folders != null) ? archive.folders.length : 0;
        streamMap.folderFirstPackStreamIndex = new int[n];
        while (0 < n) {
            streamMap.folderFirstPackStreamIndex[0] = 0;
            final int n2 = 0 + archive.folders[0].packedStreams.length;
            int n3 = 0;
            ++n3;
        }
        long n4 = 0L;
        final int n5 = (archive.packSizes != null) ? archive.packSizes.length : 0;
        streamMap.packStreamOffsets = new long[n5];
        int n6 = 0;
        while (0 < n5) {
            streamMap.packStreamOffsets[0] = n4;
            n4 += archive.packSizes[0];
            ++n6;
        }
        streamMap.folderFirstFileIndex = new int[n];
        streamMap.fileFolderIndex = new int[archive.files.length];
        while (0 < archive.files.length) {
            if (!archive.files[0].hasStream() && !false) {
                streamMap.fileFolderIndex[0] = -1;
            }
            else {
                if (!false) {
                    while (0 < archive.folders.length) {
                        streamMap.folderFirstFileIndex[0] = 0;
                        if (archive.folders[0].numUnpackSubStreams > 0) {
                            break;
                        }
                        ++n6;
                    }
                    if (0 >= archive.folders.length) {
                        throw new IOException("Too few folders in archive");
                    }
                }
                streamMap.fileFolderIndex[0] = 0;
                if (archive.files[0].hasStream()) {
                    int n7 = 0;
                    ++n7;
                    if (0 >= archive.folders[0].numUnpackSubStreams) {
                        ++n6;
                    }
                }
            }
            int n8 = 0;
            ++n8;
        }
        archive.streamMap = streamMap;
    }
    
    private void buildDecodingStream() throws IOException {
        final int currentFolderIndex = this.archive.streamMap.fileFolderIndex[this.currentEntryIndex];
        if (currentFolderIndex < 0) {
            this.currentEntryInputStream = new BoundedInputStream(new ByteArrayInputStream(new byte[0]), 0L);
            return;
        }
        final SevenZArchiveEntry sevenZArchiveEntry = this.archive.files[this.currentEntryIndex];
        if (this.currentFolderIndex == currentFolderIndex) {
            this.drainPreviousEntry();
            sevenZArchiveEntry.setContentMethods(this.archive.files[this.currentEntryIndex - 1].getContentMethods());
        }
        else {
            this.currentFolderIndex = currentFolderIndex;
            if (this.currentFolderInputStream != null) {
                this.currentFolderInputStream.close();
                this.currentFolderInputStream = null;
            }
            final Folder folder = this.archive.folders[currentFolderIndex];
            final int n = this.archive.streamMap.folderFirstPackStreamIndex[currentFolderIndex];
            this.currentFolderInputStream = this.buildDecoderStack(folder, 32L + this.archive.packPos + this.archive.streamMap.packStreamOffsets[n], n, sevenZArchiveEntry);
        }
        final BoundedInputStream currentEntryInputStream = new BoundedInputStream(this.currentFolderInputStream, sevenZArchiveEntry.getSize());
        if (sevenZArchiveEntry.getHasCrc()) {
            this.currentEntryInputStream = new CRC32VerifyingInputStream(currentEntryInputStream, sevenZArchiveEntry.getSize(), sevenZArchiveEntry.getCrcValue());
        }
        else {
            this.currentEntryInputStream = currentEntryInputStream;
        }
    }
    
    private void drainPreviousEntry() throws IOException {
        if (this.currentEntryInputStream != null) {
            IOUtils.skip(this.currentEntryInputStream, Long.MAX_VALUE);
            this.currentEntryInputStream.close();
            this.currentEntryInputStream = null;
        }
    }
    
    private InputStream buildDecoderStack(final Folder folder, final long n, final int n2, final SevenZArchiveEntry sevenZArchiveEntry) throws IOException {
        this.file.seek(n);
        InputStream addDecoder = new BoundedRandomAccessFileInputStream(this.file, this.archive.packSizes[n2]);
        final LinkedList<SevenZMethodConfiguration> contentMethods = new LinkedList<SevenZMethodConfiguration>();
        for (final Coder coder : folder.getOrderedCoders()) {
            if (coder.numInStreams != 1L || coder.numOutStreams != 1L) {
                throw new IOException("Multi input/output stream coders are not yet supported");
            }
            final SevenZMethod byId = SevenZMethod.byId(coder.decompressionMethodId);
            addDecoder = Coders.addDecoder(addDecoder, coder, this.password);
            contentMethods.addFirst(new SevenZMethodConfiguration(byId, Coders.findByMethod(byId).getOptionsFromCoder(coder, addDecoder)));
        }
        sevenZArchiveEntry.setContentMethods(contentMethods);
        if (folder.hasCrc) {
            return new CRC32VerifyingInputStream(addDecoder, folder.getUnpackSize(), folder.crc);
        }
        return addDecoder;
    }
    
    public int read() throws IOException {
        if (this.currentEntryInputStream == null) {
            throw new IllegalStateException("No current 7z entry");
        }
        return this.currentEntryInputStream.read();
    }
    
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.currentEntryInputStream == null) {
            throw new IllegalStateException("No current 7z entry");
        }
        return this.currentEntryInputStream.read(array, n, n2);
    }
    
    private static long readUint64(final DataInput dataInput) throws IOException {
        final long n = dataInput.readUnsignedByte();
        long n2 = 0L;
        while (0 < 8) {
            if ((n & (long)128) == 0x0L) {
                return n2 | (n & (long)127) << 0;
            }
            n2 |= (long)dataInput.readUnsignedByte() << 0;
            int n3 = 0;
            ++n3;
        }
        return n2;
    }
    
    public static boolean matches(final byte[] array, final int n) {
        if (n < SevenZFile.sevenZSignature.length) {
            return false;
        }
        while (0 < SevenZFile.sevenZSignature.length) {
            if (array[0] != SevenZFile.sevenZSignature[0]) {
                return false;
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    static {
        sevenZSignature = new byte[] { 55, 122, -68, -81, 39, 28 };
    }
}
