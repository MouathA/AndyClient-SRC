package org.apache.commons.compress.compressors.gzip;

import org.apache.commons.compress.compressors.*;
import java.util.zip.*;
import java.io.*;
import java.nio.*;

public class GzipCompressorOutputStream extends CompressorOutputStream
{
    private static final int FNAME = 8;
    private static final int FCOMMENT = 16;
    private final OutputStream out;
    private final Deflater deflater;
    private final byte[] deflateBuffer;
    private boolean closed;
    private final CRC32 crc;
    
    public GzipCompressorOutputStream(final OutputStream outputStream) throws IOException {
        this(outputStream, new GzipParameters());
    }
    
    public GzipCompressorOutputStream(final OutputStream out, final GzipParameters gzipParameters) throws IOException {
        this.deflateBuffer = new byte[512];
        this.crc = new CRC32();
        this.out = out;
        this.deflater = new Deflater(gzipParameters.getCompressionLevel(), true);
        this.writeHeader(gzipParameters);
    }
    
    private void writeHeader(final GzipParameters gzipParameters) throws IOException {
        final String filename = gzipParameters.getFilename();
        final String comment = gzipParameters.getComment();
        final ByteBuffer allocate = ByteBuffer.allocate(10);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.putShort((short)(-29921));
        allocate.put((byte)8);
        allocate.put((byte)(((filename != null) ? 8 : 0) | ((comment != null) ? 16 : 0)));
        allocate.putInt((int)(gzipParameters.getModificationTime() / 1000L));
        final int compressionLevel = gzipParameters.getCompressionLevel();
        if (compressionLevel == 9) {
            allocate.put((byte)2);
        }
        else if (compressionLevel == 1) {
            allocate.put((byte)4);
        }
        else {
            allocate.put((byte)0);
        }
        allocate.put((byte)gzipParameters.getOperatingSystem());
        this.out.write(allocate.array());
        if (filename != null) {
            this.out.write(filename.getBytes("ISO-8859-1"));
            this.out.write(0);
        }
        if (comment != null) {
            this.out.write(comment.getBytes("ISO-8859-1"));
            this.out.write(0);
        }
    }
    
    private void writeTrailer() throws IOException {
        final ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.putInt((int)this.crc.getValue());
        allocate.putInt(this.deflater.getTotalIn());
        this.out.write(allocate.array());
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.write(new byte[] { (byte)(n & 0xFF) }, 0, 1);
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (this.deflater.finished()) {
            throw new IOException("Cannot write more data, the end of the compressed data stream has been reached");
        }
        if (n2 > 0) {
            this.deflater.setInput(array, n, n2);
            while (!this.deflater.needsInput()) {
                this.deflate();
            }
            this.crc.update(array, n, n2);
        }
    }
    
    private void deflate() throws IOException {
        final int deflate = this.deflater.deflate(this.deflateBuffer, 0, this.deflateBuffer.length);
        if (deflate > 0) {
            this.out.write(this.deflateBuffer, 0, deflate);
        }
    }
    
    public void finish() throws IOException {
        if (!this.deflater.finished()) {
            this.deflater.finish();
            while (!this.deflater.finished()) {
                this.deflate();
            }
            this.writeTrailer();
        }
    }
    
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            this.finish();
            this.deflater.end();
            this.out.close();
            this.closed = true;
        }
    }
}
