package org.apache.commons.compress.archivers.sevenz;

import java.util.zip.*;
import org.apache.commons.compress.utils.*;
import org.apache.commons.compress.archivers.*;
import java.io.*;
import java.util.*;

public class SevenZOutputFile implements Closeable
{
    private final RandomAccessFile file;
    private final List files;
    private int numNonEmptyStreams;
    private final CRC32 crc32;
    private final CRC32 compressedCrc32;
    private long fileBytesWritten;
    private boolean finished;
    private CountingOutputStream currentOutputStream;
    private CountingOutputStream[] additionalCountingStreams;
    private Iterable contentMethods;
    private final Map additionalSizes;
    
    public SevenZOutputFile(final File file) throws IOException {
        this.files = new ArrayList();
        this.numNonEmptyStreams = 0;
        this.crc32 = new CRC32();
        this.compressedCrc32 = new CRC32();
        this.fileBytesWritten = 0L;
        this.finished = false;
        this.contentMethods = Collections.singletonList(new SevenZMethodConfiguration(SevenZMethod.LZMA2));
        this.additionalSizes = new HashMap();
        (this.file = new RandomAccessFile(file, "rw")).seek(32L);
    }
    
    public void setContentCompression(final SevenZMethod sevenZMethod) {
        this.setContentMethods(Collections.singletonList(new SevenZMethodConfiguration(sevenZMethod)));
    }
    
    public void setContentMethods(final Iterable iterable) {
        this.contentMethods = reverse(iterable);
    }
    
    public void close() throws IOException {
        if (!this.finished) {
            this.finish();
        }
        this.file.close();
    }
    
    public SevenZArchiveEntry createArchiveEntry(final File file, final String name) throws IOException {
        final SevenZArchiveEntry sevenZArchiveEntry = new SevenZArchiveEntry();
        sevenZArchiveEntry.setDirectory(file.isDirectory());
        sevenZArchiveEntry.setName(name);
        sevenZArchiveEntry.setLastModifiedDate(new Date(file.lastModified()));
        return sevenZArchiveEntry;
    }
    
    public void putArchiveEntry(final ArchiveEntry archiveEntry) throws IOException {
        this.files.add(archiveEntry);
    }
    
    public void closeArchiveEntry() throws IOException {
        if (this.currentOutputStream != null) {
            this.currentOutputStream.flush();
            this.currentOutputStream.close();
        }
        final SevenZArchiveEntry sevenZArchiveEntry = this.files.get(this.files.size() - 1);
        if (this.fileBytesWritten > 0L) {
            sevenZArchiveEntry.setHasStream(true);
            ++this.numNonEmptyStreams;
            sevenZArchiveEntry.setSize(this.currentOutputStream.getBytesWritten());
            sevenZArchiveEntry.setCompressedSize(this.fileBytesWritten);
            sevenZArchiveEntry.setCrcValue(this.crc32.getValue());
            sevenZArchiveEntry.setCompressedCrcValue(this.compressedCrc32.getValue());
            sevenZArchiveEntry.setHasCrc(true);
            if (this.additionalCountingStreams != null) {
                final long[] array = new long[this.additionalCountingStreams.length];
                while (0 < this.additionalCountingStreams.length) {
                    array[0] = this.additionalCountingStreams[0].getBytesWritten();
                    int n = 0;
                    ++n;
                }
                this.additionalSizes.put(sevenZArchiveEntry, array);
            }
        }
        else {
            sevenZArchiveEntry.setHasStream(false);
            sevenZArchiveEntry.setSize(0L);
            sevenZArchiveEntry.setCompressedSize(0L);
            sevenZArchiveEntry.setHasCrc(false);
        }
        this.currentOutputStream = null;
        this.additionalCountingStreams = null;
        this.crc32.reset();
        this.compressedCrc32.reset();
        this.fileBytesWritten = 0L;
    }
    
    public void write(final int n) throws IOException {
        this.getCurrentOutputStream().write(n);
    }
    
    public void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (n2 > 0) {
            this.getCurrentOutputStream().write(array, n, n2);
        }
    }
    
    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        this.finished = true;
        final long filePointer = this.file.getFilePointer();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        this.writeHeader(dataOutputStream);
        dataOutputStream.flush();
        final byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.file.write(byteArray);
        final CRC32 crc32 = new CRC32();
        this.file.seek(0L);
        this.file.write(SevenZFile.sevenZSignature);
        this.file.write(0);
        this.file.write(2);
        final ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream2);
        dataOutputStream2.writeLong(Long.reverseBytes(filePointer - 32L));
        dataOutputStream2.writeLong(Long.reverseBytes(0xFFFFFFFFL & (long)byteArray.length));
        crc32.reset();
        crc32.update(byteArray);
        dataOutputStream2.writeInt(Integer.reverseBytes((int)crc32.getValue()));
        dataOutputStream2.flush();
        final byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
        crc32.reset();
        crc32.update(byteArray2);
        this.file.writeInt(Integer.reverseBytes((int)crc32.getValue()));
        this.file.write(byteArray2);
    }
    
    private OutputStream getCurrentOutputStream() throws IOException {
        if (this.currentOutputStream == null) {
            this.currentOutputStream = this.setupFileOutputStream();
        }
        return this.currentOutputStream;
    }
    
    private CountingOutputStream setupFileOutputStream() throws IOException {
        if (this.files.isEmpty()) {
            throw new IllegalStateException("No current 7z entry");
        }
        OutputStream addEncoder = new OutputStreamWrapper(null);
        final ArrayList<CountingOutputStream> list = new ArrayList<CountingOutputStream>();
        for (final SevenZMethodConfiguration sevenZMethodConfiguration : this.getContentMethods(this.files.get(this.files.size() - 1))) {
            if (!false) {
                final CountingOutputStream countingOutputStream = new CountingOutputStream(addEncoder);
                list.add(countingOutputStream);
                addEncoder = countingOutputStream;
            }
            addEncoder = Coders.addEncoder(addEncoder, sevenZMethodConfiguration.getMethod(), sevenZMethodConfiguration.getOptions());
        }
        if (!list.isEmpty()) {
            this.additionalCountingStreams = list.toArray(new CountingOutputStream[list.size()]);
        }
        return new CountingOutputStream(addEncoder) {
            final SevenZOutputFile this$0;
            
            @Override
            public void write(final int n) throws IOException {
                super.write(n);
                SevenZOutputFile.access$100(this.this$0).update(n);
            }
            
            @Override
            public void write(final byte[] array) throws IOException {
                super.write(array);
                SevenZOutputFile.access$100(this.this$0).update(array);
            }
            
            @Override
            public void write(final byte[] array, final int n, final int n2) throws IOException {
                super.write(array, n, n2);
                SevenZOutputFile.access$100(this.this$0).update(array, n, n2);
            }
        };
    }
    
    private Iterable getContentMethods(final SevenZArchiveEntry sevenZArchiveEntry) {
        final Iterable contentMethods = sevenZArchiveEntry.getContentMethods();
        return (contentMethods == null) ? this.contentMethods : contentMethods;
    }
    
    private void writeHeader(final DataOutput dataOutput) throws IOException {
        dataOutput.write(1);
        dataOutput.write(4);
        this.writeStreamsInfo(dataOutput);
        this.writeFilesInfo(dataOutput);
        dataOutput.write(0);
    }
    
    private void writeStreamsInfo(final DataOutput dataOutput) throws IOException {
        if (this.numNonEmptyStreams > 0) {
            this.writePackInfo(dataOutput);
            this.writeUnpackInfo(dataOutput);
        }
        this.writeSubStreamsInfo(dataOutput);
        dataOutput.write(0);
    }
    
    private void writePackInfo(final DataOutput dataOutput) throws IOException {
        dataOutput.write(6);
        this.writeUint64(dataOutput, 0L);
        this.writeUint64(dataOutput, 0xFFFFFFFFL & (long)this.numNonEmptyStreams);
        dataOutput.write(9);
        for (final SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            if (sevenZArchiveEntry.hasStream()) {
                this.writeUint64(dataOutput, sevenZArchiveEntry.getCompressedSize());
            }
        }
        dataOutput.write(10);
        dataOutput.write(1);
        for (final SevenZArchiveEntry sevenZArchiveEntry2 : this.files) {
            if (sevenZArchiveEntry2.hasStream()) {
                dataOutput.writeInt(Integer.reverseBytes((int)sevenZArchiveEntry2.getCompressedCrcValue()));
            }
        }
        dataOutput.write(0);
    }
    
    private void writeUnpackInfo(final DataOutput dataOutput) throws IOException {
        dataOutput.write(7);
        dataOutput.write(11);
        this.writeUint64(dataOutput, this.numNonEmptyStreams);
        dataOutput.write(0);
        for (final SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            if (sevenZArchiveEntry.hasStream()) {
                this.writeFolder(dataOutput, sevenZArchiveEntry);
            }
        }
        dataOutput.write(12);
        for (final SevenZArchiveEntry sevenZArchiveEntry2 : this.files) {
            if (sevenZArchiveEntry2.hasStream()) {
                final long[] array = this.additionalSizes.get(sevenZArchiveEntry2);
                if (array != null) {
                    final long[] array2 = array;
                    while (0 < array2.length) {
                        this.writeUint64(dataOutput, array2[0]);
                        int n = 0;
                        ++n;
                    }
                }
                this.writeUint64(dataOutput, sevenZArchiveEntry2.getSize());
            }
        }
        dataOutput.write(10);
        dataOutput.write(1);
        for (final SevenZArchiveEntry sevenZArchiveEntry3 : this.files) {
            if (sevenZArchiveEntry3.hasStream()) {
                dataOutput.writeInt(Integer.reverseBytes((int)sevenZArchiveEntry3.getCrcValue()));
            }
        }
        dataOutput.write(0);
    }
    
    private void writeFolder(final DataOutput p0, final SevenZArchiveEntry p1) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/io/ByteArrayOutputStream.<init>:()V
        //     7: astore_3       
        //     8: aload_0        
        //     9: aload_2        
        //    10: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZOutputFile.getContentMethods:(Lorg/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry;)Ljava/lang/Iterable;
        //    13: invokeinterface java/lang/Iterable.iterator:()Ljava/util/Iterator;
        //    18: astore          5
        //    20: aload           5
        //    22: invokeinterface java/util/Iterator.hasNext:()Z
        //    27: ifeq            55
        //    30: aload           5
        //    32: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    37: checkcast       Lorg/apache/commons/compress/archivers/sevenz/SevenZMethodConfiguration;
        //    40: astore          6
        //    42: iinc            4, 1
        //    45: aload_0        
        //    46: aload           6
        //    48: aload_3        
        //    49: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZOutputFile.writeSingleCodec:(Lorg/apache/commons/compress/archivers/sevenz/SevenZMethodConfiguration;Ljava/io/OutputStream;)V
        //    52: goto            20
        //    55: aload_0        
        //    56: aload_1        
        //    57: iconst_0       
        //    58: i2l            
        //    59: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZOutputFile.writeUint64:(Ljava/io/DataOutput;J)V
        //    62: aload_1        
        //    63: aload_3        
        //    64: invokevirtual   java/io/ByteArrayOutputStream.toByteArray:()[B
        //    67: invokeinterface java/io/DataOutput.write:([B)V
        //    72: iconst_0       
        //    73: iconst_m1      
        //    74: if_icmpge       97
        //    77: aload_0        
        //    78: aload_1        
        //    79: iconst_1       
        //    80: i2l            
        //    81: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZOutputFile.writeUint64:(Ljava/io/DataOutput;J)V
        //    84: aload_0        
        //    85: aload_1        
        //    86: iconst_0       
        //    87: i2l            
        //    88: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZOutputFile.writeUint64:(Ljava/io/DataOutput;J)V
        //    91: iinc            5, 1
        //    94: goto            72
        //    97: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void writeSingleCodec(final SevenZMethodConfiguration sevenZMethodConfiguration, final OutputStream outputStream) throws IOException {
        final byte[] id = sevenZMethodConfiguration.getMethod().getId();
        final byte[] optionsAsProperties = Coders.findByMethod(sevenZMethodConfiguration.getMethod()).getOptionsAsProperties(sevenZMethodConfiguration.getOptions());
        int length = id.length;
        if (optionsAsProperties.length > 0) {
            length |= 0x20;
        }
        outputStream.write(length);
        outputStream.write(id);
        if (optionsAsProperties.length > 0) {
            outputStream.write(optionsAsProperties.length);
            outputStream.write(optionsAsProperties);
        }
    }
    
    private void writeSubStreamsInfo(final DataOutput dataOutput) throws IOException {
        dataOutput.write(8);
        dataOutput.write(0);
    }
    
    private void writeFilesInfo(final DataOutput dataOutput) throws IOException {
        dataOutput.write(5);
        this.writeUint64(dataOutput, this.files.size());
        this.writeFileEmptyStreams(dataOutput);
        this.writeFileEmptyFiles(dataOutput);
        this.writeFileAntiItems(dataOutput);
        this.writeFileNames(dataOutput);
        this.writeFileCTimes(dataOutput);
        this.writeFileATimes(dataOutput);
        this.writeFileMTimes(dataOutput);
        this.writeFileWindowsAttributes(dataOutput);
        dataOutput.write(0);
    }
    
    private void writeFileEmptyStreams(final DataOutput p0) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        org/apache/commons/compress/archivers/sevenz/SevenZOutputFile.files:Ljava/util/List;
        //     4: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //     9: astore_3       
        //    10: aload_3        
        //    11: invokeinterface java/util/Iterator.hasNext:()Z
        //    16: ifeq            44
        //    19: aload_3        
        //    20: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    25: checkcast       Lorg/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry;
        //    28: astore          4
        //    30: aload           4
        //    32: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.hasStream:()Z
        //    35: ifne            41
        //    38: goto            44
        //    41: goto            10
        //    44: iconst_1       
        //    45: ifeq            186
        //    48: aload_1        
        //    49: bipush          14
        //    51: invokeinterface java/io/DataOutput.write:(I)V
        //    56: new             Ljava/util/BitSet;
        //    59: dup            
        //    60: aload_0        
        //    61: getfield        org/apache/commons/compress/archivers/sevenz/SevenZOutputFile.files:Ljava/util/List;
        //    64: invokeinterface java/util/List.size:()I
        //    69: invokespecial   java/util/BitSet.<init>:(I)V
        //    72: astore_3       
        //    73: iconst_0       
        //    74: aload_0        
        //    75: getfield        org/apache/commons/compress/archivers/sevenz/SevenZOutputFile.files:Ljava/util/List;
        //    78: invokeinterface java/util/List.size:()I
        //    83: if_icmpge       121
        //    86: aload_3        
        //    87: iconst_0       
        //    88: aload_0        
        //    89: getfield        org/apache/commons/compress/archivers/sevenz/SevenZOutputFile.files:Ljava/util/List;
        //    92: iconst_0       
        //    93: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //    98: checkcast       Lorg/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry;
        //   101: invokevirtual   org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.hasStream:()Z
        //   104: ifne            111
        //   107: iconst_1       
        //   108: goto            112
        //   111: iconst_0       
        //   112: invokevirtual   java/util/BitSet.set:(IZ)V
        //   115: iinc            4, 1
        //   118: goto            73
        //   121: new             Ljava/io/ByteArrayOutputStream;
        //   124: dup            
        //   125: invokespecial   java/io/ByteArrayOutputStream.<init>:()V
        //   128: astore          4
        //   130: new             Ljava/io/DataOutputStream;
        //   133: dup            
        //   134: aload           4
        //   136: invokespecial   java/io/DataOutputStream.<init>:(Ljava/io/OutputStream;)V
        //   139: astore          5
        //   141: aload_0        
        //   142: aload           5
        //   144: aload_3        
        //   145: aload_0        
        //   146: getfield        org/apache/commons/compress/archivers/sevenz/SevenZOutputFile.files:Ljava/util/List;
        //   149: invokeinterface java/util/List.size:()I
        //   154: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZOutputFile.writeBits:(Ljava/io/DataOutput;Ljava/util/BitSet;I)V
        //   157: aload           5
        //   159: invokevirtual   java/io/DataOutputStream.flush:()V
        //   162: aload           4
        //   164: invokevirtual   java/io/ByteArrayOutputStream.toByteArray:()[B
        //   167: astore          6
        //   169: aload_0        
        //   170: aload_1        
        //   171: aload           6
        //   173: arraylength    
        //   174: i2l            
        //   175: invokespecial   org/apache/commons/compress/archivers/sevenz/SevenZOutputFile.writeUint64:(Ljava/io/DataOutput;J)V
        //   178: aload_1        
        //   179: aload           6
        //   181: invokeinterface java/io/DataOutput.write:([B)V
        //   186: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void writeFileEmptyFiles(final DataOutput dataOutput) throws IOException {
        final BitSet set = new BitSet(0);
        while (0 < this.files.size()) {
            if (!this.files.get(0).hasStream()) {
                final boolean directory = this.files.get(0).isDirectory();
                final BitSet set2 = set;
                final int n = 0;
                int n2 = 0;
                ++n2;
                set2.set(n, !directory);
                final boolean b = false | !directory;
            }
            int n3 = 0;
            ++n3;
        }
        if (false) {
            dataOutput.write(15);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            this.writeBits(dataOutputStream, set, 0);
            dataOutputStream.flush();
            final byte[] byteArray = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }
    
    private void writeFileAntiItems(final DataOutput dataOutput) throws IOException {
        final BitSet set = new BitSet(0);
        while (0 < this.files.size()) {
            if (!this.files.get(0).hasStream()) {
                final boolean antiItem = this.files.get(0).isAntiItem();
                final BitSet set2 = set;
                final int n = 0;
                int n2 = 0;
                ++n2;
                set2.set(n, antiItem);
            }
            int n3 = 0;
            ++n3;
        }
        if (false) {
            dataOutput.write(16);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            this.writeBits(dataOutputStream, set, 0);
            dataOutputStream.flush();
            final byte[] byteArray = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }
    
    private void writeFileNames(final DataOutput dataOutput) throws IOException {
        dataOutput.write(17);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.write(0);
        final Iterator<SevenZArchiveEntry> iterator = this.files.iterator();
        while (iterator.hasNext()) {
            dataOutputStream.write(iterator.next().getName().getBytes("UTF-16LE"));
            dataOutputStream.writeShort(0);
        }
        dataOutputStream.flush();
        final byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.writeUint64(dataOutput, byteArray.length);
        dataOutput.write(byteArray);
    }
    
    private void writeFileCTimes(final DataOutput dataOutput) throws IOException {
        final Iterator<SevenZArchiveEntry> iterator = this.files.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getHasCreationDate()) {
                int n = 0;
                ++n;
            }
        }
        if (0 > 0) {
            dataOutput.write(18);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (0 != this.files.size()) {
                dataOutputStream.write(0);
                final BitSet set = new BitSet(this.files.size());
                while (0 < this.files.size()) {
                    set.set(0, this.files.get(0).getHasCreationDate());
                    int n2 = 0;
                    ++n2;
                }
                this.writeBits(dataOutputStream, set, this.files.size());
            }
            else {
                dataOutputStream.write(1);
            }
            dataOutputStream.write(0);
            for (final SevenZArchiveEntry sevenZArchiveEntry : this.files) {
                if (sevenZArchiveEntry.getHasCreationDate()) {
                    dataOutputStream.writeLong(Long.reverseBytes(SevenZArchiveEntry.javaTimeToNtfsTime(sevenZArchiveEntry.getCreationDate())));
                }
            }
            dataOutputStream.flush();
            final byte[] byteArray = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }
    
    private void writeFileATimes(final DataOutput dataOutput) throws IOException {
        final Iterator<SevenZArchiveEntry> iterator = this.files.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getHasAccessDate()) {
                int n = 0;
                ++n;
            }
        }
        if (0 > 0) {
            dataOutput.write(19);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (0 != this.files.size()) {
                dataOutputStream.write(0);
                final BitSet set = new BitSet(this.files.size());
                while (0 < this.files.size()) {
                    set.set(0, this.files.get(0).getHasAccessDate());
                    int n2 = 0;
                    ++n2;
                }
                this.writeBits(dataOutputStream, set, this.files.size());
            }
            else {
                dataOutputStream.write(1);
            }
            dataOutputStream.write(0);
            for (final SevenZArchiveEntry sevenZArchiveEntry : this.files) {
                if (sevenZArchiveEntry.getHasAccessDate()) {
                    dataOutputStream.writeLong(Long.reverseBytes(SevenZArchiveEntry.javaTimeToNtfsTime(sevenZArchiveEntry.getAccessDate())));
                }
            }
            dataOutputStream.flush();
            final byte[] byteArray = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }
    
    private void writeFileMTimes(final DataOutput dataOutput) throws IOException {
        final Iterator<SevenZArchiveEntry> iterator = this.files.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getHasLastModifiedDate()) {
                int n = 0;
                ++n;
            }
        }
        if (0 > 0) {
            dataOutput.write(20);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (0 != this.files.size()) {
                dataOutputStream.write(0);
                final BitSet set = new BitSet(this.files.size());
                while (0 < this.files.size()) {
                    set.set(0, this.files.get(0).getHasLastModifiedDate());
                    int n2 = 0;
                    ++n2;
                }
                this.writeBits(dataOutputStream, set, this.files.size());
            }
            else {
                dataOutputStream.write(1);
            }
            dataOutputStream.write(0);
            for (final SevenZArchiveEntry sevenZArchiveEntry : this.files) {
                if (sevenZArchiveEntry.getHasLastModifiedDate()) {
                    dataOutputStream.writeLong(Long.reverseBytes(SevenZArchiveEntry.javaTimeToNtfsTime(sevenZArchiveEntry.getLastModifiedDate())));
                }
            }
            dataOutputStream.flush();
            final byte[] byteArray = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }
    
    private void writeFileWindowsAttributes(final DataOutput dataOutput) throws IOException {
        final Iterator<SevenZArchiveEntry> iterator = this.files.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getHasWindowsAttributes()) {
                int n = 0;
                ++n;
            }
        }
        if (0 > 0) {
            dataOutput.write(21);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (0 != this.files.size()) {
                dataOutputStream.write(0);
                final BitSet set = new BitSet(this.files.size());
                while (0 < this.files.size()) {
                    set.set(0, this.files.get(0).getHasWindowsAttributes());
                    int n2 = 0;
                    ++n2;
                }
                this.writeBits(dataOutputStream, set, this.files.size());
            }
            else {
                dataOutputStream.write(1);
            }
            dataOutputStream.write(0);
            for (final SevenZArchiveEntry sevenZArchiveEntry : this.files) {
                if (sevenZArchiveEntry.getHasWindowsAttributes()) {
                    dataOutputStream.writeInt(Integer.reverseBytes(sevenZArchiveEntry.getWindowsAttributes()));
                }
            }
            dataOutputStream.flush();
            final byte[] byteArray = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }
    
    private void writeUint64(final DataOutput dataOutput, long n) throws IOException {
        int n3 = 0;
        while (0 < 8) {
            if (n < 128L) {
                final int n2 = (int)((long)0 | n >>> 0);
                break;
            }
            ++n3;
        }
        dataOutput.write(0);
        while (0 > 0) {
            dataOutput.write((int)(0xFFL & n));
            n >>>= 8;
            --n3;
        }
    }
    
    private void writeBits(final DataOutput dataOutput, final BitSet set, final int n) throws IOException {
        while (0 < n) {
            final boolean b = (0x0 | (set.get(0) ? 1 : 0) << 7) != 0x0;
            int n2 = 0;
            --n2;
            if (7 < 0) {
                dataOutput.write(0);
            }
            int n3 = 0;
            ++n3;
        }
        if (7 != 7) {
            dataOutput.write(0);
        }
    }
    
    private static Iterable reverse(final Iterable iterable) {
        final LinkedList<Object> list = new LinkedList<Object>();
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            list.addFirst(iterator.next());
        }
        return list;
    }
    
    static CRC32 access$100(final SevenZOutputFile sevenZOutputFile) {
        return sevenZOutputFile.crc32;
    }
    
    static RandomAccessFile access$200(final SevenZOutputFile sevenZOutputFile) {
        return sevenZOutputFile.file;
    }
    
    static CRC32 access$300(final SevenZOutputFile sevenZOutputFile) {
        return sevenZOutputFile.compressedCrc32;
    }
    
    static long access$408(final SevenZOutputFile sevenZOutputFile) {
        return sevenZOutputFile.fileBytesWritten++;
    }
    
    static long access$414(final SevenZOutputFile sevenZOutputFile, final long n) {
        return sevenZOutputFile.fileBytesWritten += n;
    }
    
    private class OutputStreamWrapper extends OutputStream
    {
        final SevenZOutputFile this$0;
        
        private OutputStreamWrapper(final SevenZOutputFile this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void write(final int n) throws IOException {
            SevenZOutputFile.access$200(this.this$0).write(n);
            SevenZOutputFile.access$300(this.this$0).update(n);
            SevenZOutputFile.access$408(this.this$0);
        }
        
        @Override
        public void write(final byte[] array) throws IOException {
            this.write(array, 0, array.length);
        }
        
        @Override
        public void write(final byte[] array, final int n, final int n2) throws IOException {
            SevenZOutputFile.access$200(this.this$0).write(array, n, n2);
            SevenZOutputFile.access$300(this.this$0).update(array, n, n2);
            SevenZOutputFile.access$414(this.this$0, n2);
        }
        
        @Override
        public void flush() throws IOException {
        }
        
        @Override
        public void close() throws IOException {
        }
        
        OutputStreamWrapper(final SevenZOutputFile sevenZOutputFile, final SevenZOutputFile$1 countingOutputStream) {
            this(sevenZOutputFile);
        }
    }
}
