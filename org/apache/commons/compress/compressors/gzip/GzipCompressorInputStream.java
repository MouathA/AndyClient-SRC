package org.apache.commons.compress.compressors.gzip;

import org.apache.commons.compress.compressors.*;
import java.util.zip.*;
import java.io.*;

public class GzipCompressorInputStream extends CompressorInputStream
{
    private static final int FHCRC = 2;
    private static final int FEXTRA = 4;
    private static final int FNAME = 8;
    private static final int FCOMMENT = 16;
    private static final int FRESERVED = 224;
    private final InputStream in;
    private final boolean decompressConcatenated;
    private final byte[] buf;
    private int bufUsed;
    private Inflater inf;
    private final CRC32 crc;
    private int memberSize;
    private boolean endReached;
    private final byte[] oneByte;
    private final GzipParameters parameters;
    static final boolean $assertionsDisabled;
    
    public GzipCompressorInputStream(final InputStream inputStream) throws IOException {
        this(inputStream, false);
    }
    
    public GzipCompressorInputStream(final InputStream in, final boolean decompressConcatenated) throws IOException {
        this.buf = new byte[8192];
        this.bufUsed = 0;
        this.inf = new Inflater(true);
        this.crc = new CRC32();
        this.endReached = false;
        this.oneByte = new byte[1];
        this.parameters = new GzipParameters();
        if (in.markSupported()) {
            this.in = in;
        }
        else {
            this.in = new BufferedInputStream(in);
        }
        this.decompressConcatenated = decompressConcatenated;
        this.init(true);
    }
    
    public GzipParameters getMetaData() {
        return this.parameters;
    }
    
    private boolean init(final boolean b) throws IOException {
        assert b || this.decompressConcatenated;
        final int read = this.in.read();
        final int read2 = this.in.read();
        if (read == -1 && !b) {
            return false;
        }
        if (read != 31 || read2 != 139) {
            throw new IOException(b ? "Input is not in the .gz format" : "Garbage after a valid .gz stream");
        }
        final DataInputStream dataInputStream = new DataInputStream(this.in);
        final int unsignedByte = dataInputStream.readUnsignedByte();
        if (unsignedByte != 8) {
            throw new IOException("Unsupported compression method " + unsignedByte + " in the .gz header");
        }
        final int unsignedByte2 = dataInputStream.readUnsignedByte();
        if ((unsignedByte2 & 0xE0) != 0x0) {
            throw new IOException("Reserved flags are set in the .gz header");
        }
        this.parameters.setModificationTime(this.readLittleEndianInt(dataInputStream) * 1000);
        switch (dataInputStream.readUnsignedByte()) {
            case 2: {
                this.parameters.setCompressionLevel(9);
                break;
            }
            case 4: {
                this.parameters.setCompressionLevel(1);
                break;
            }
        }
        this.parameters.setOperatingSystem(dataInputStream.readUnsignedByte());
        if ((unsignedByte2 & 0x4) != 0x0) {
            int n = dataInputStream.readUnsignedByte() | dataInputStream.readUnsignedByte() << 8;
            while (n-- > 0) {
                dataInputStream.readUnsignedByte();
            }
        }
        if ((unsignedByte2 & 0x8) != 0x0) {
            this.parameters.setFilename(new String(this.readToNull(dataInputStream), "ISO-8859-1"));
        }
        if ((unsignedByte2 & 0x10) != 0x0) {
            this.parameters.setComment(new String(this.readToNull(dataInputStream), "ISO-8859-1"));
        }
        if ((unsignedByte2 & 0x2) != 0x0) {
            dataInputStream.readShort();
        }
        this.inf.reset();
        this.crc.reset();
        this.memberSize = 0;
        return true;
    }
    
    private byte[] readToNull(final DataInputStream dataInputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (dataInputStream.readUnsignedByte() != 0) {
            byteArrayOutputStream.write(0);
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    private int readLittleEndianInt(final DataInputStream dataInputStream) throws IOException {
        return dataInputStream.readUnsignedByte() | dataInputStream.readUnsignedByte() << 8 | dataInputStream.readUnsignedByte() << 16 | dataInputStream.readUnsignedByte() << 24;
    }
    
    @Override
    public int read() throws IOException {
        return (this.read(this.oneByte, 0, 1) == -1) ? -1 : (this.oneByte[0] & 0xFF);
    }
    
    @Override
    public int read(final byte[] array, int n, int i) throws IOException {
        if (this.endReached) {
            return -1;
        }
        while (i > 0) {
            if (this.inf.needsInput()) {
                this.in.mark(this.buf.length);
                this.bufUsed = this.in.read(this.buf);
                if (this.bufUsed == -1) {
                    throw new EOFException();
                }
                this.inf.setInput(this.buf, 0, this.bufUsed);
            }
            final int inflate = this.inf.inflate(array, n, i);
            this.crc.update(array, n, inflate);
            this.memberSize += inflate;
            n += inflate;
            i -= inflate;
            this.count(inflate);
            if (this.inf.finished()) {
                this.in.reset();
                final int n2 = this.bufUsed - this.inf.getRemaining();
                if (this.in.skip(n2) != n2) {
                    throw new IOException();
                }
                this.bufUsed = 0;
                final DataInputStream dataInputStream = new DataInputStream(this.in);
                long n3 = 0L;
                while (0 < 4) {
                    n3 |= (long)dataInputStream.readUnsignedByte() << 0;
                    int n4 = 0;
                    ++n4;
                }
                if (n3 != this.crc.getValue()) {
                    throw new IOException("Gzip-compressed data is corrupt (CRC32 error)");
                }
                while (0 < 4) {
                    final int n4 = 0x0 | dataInputStream.readUnsignedByte() << 0;
                    int n5 = 0;
                    ++n5;
                }
                if (0 != this.memberSize) {
                    throw new IOException("Gzip-compressed data is corrupt(uncompressed size mismatch)");
                }
                if (!this.decompressConcatenated || !this.init(false)) {
                    this.inf.end();
                    this.inf = null;
                    this.endReached = true;
                    return false ? 0 : -1;
                }
                continue;
            }
        }
        return 0;
    }
    
    public static boolean matches(final byte[] array, final int n) {
        return n >= 2 && array[0] == 31 && array[1] == -117;
    }
    
    @Override
    public void close() throws IOException {
        if (this.inf != null) {
            this.inf.end();
            this.inf = null;
        }
        if (this.in != System.in) {
            this.in.close();
        }
    }
    
    static {
        $assertionsDisabled = !GzipCompressorInputStream.class.desiredAssertionStatus();
    }
}
