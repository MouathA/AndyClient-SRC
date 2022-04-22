package org.apache.commons.compress.compressors;

import org.apache.commons.compress.utils.*;
import org.apache.commons.compress.compressors.z.*;
import org.apache.commons.compress.compressors.lzma.*;
import org.apache.commons.compress.compressors.snappy.*;
import java.io.*;
import org.apache.commons.compress.compressors.gzip.*;
import org.apache.commons.compress.compressors.bzip2.*;
import org.apache.commons.compress.compressors.xz.*;
import org.apache.commons.compress.compressors.pack200.*;

public class CompressorStreamFactory
{
    public static final String BZIP2 = "bzip2";
    public static final String GZIP = "gz";
    public static final String PACK200 = "pack200";
    public static final String XZ = "xz";
    public static final String LZMA = "lzma";
    public static final String SNAPPY_FRAMED = "snappy-framed";
    public static final String SNAPPY_RAW = "snappy-raw";
    public static final String Z = "z";
    private boolean decompressConcatenated;
    
    public CompressorStreamFactory() {
        this.decompressConcatenated = false;
    }
    
    public void setDecompressConcatenated(final boolean decompressConcatenated) {
        this.decompressConcatenated = decompressConcatenated;
    }
    
    public CompressorInputStream createCompressorInputStream(final InputStream inputStream) throws CompressorException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Stream must not be null.");
        }
        if (!inputStream.markSupported()) {
            throw new IllegalArgumentException("Mark is not supported.");
        }
        final byte[] array = new byte[12];
        inputStream.mark(array.length);
        final int fully = IOUtils.readFully(inputStream, array);
        inputStream.reset();
        if (BZip2CompressorInputStream.matches(array, fully)) {
            return new BZip2CompressorInputStream(inputStream, this.decompressConcatenated);
        }
        if (GzipCompressorInputStream.matches(array, fully)) {
            return new GzipCompressorInputStream(inputStream, this.decompressConcatenated);
        }
        if (XZUtils.isXZCompressionAvailable() && XZCompressorInputStream.matches(array, fully)) {
            return new XZCompressorInputStream(inputStream, this.decompressConcatenated);
        }
        if (Pack200CompressorInputStream.matches(array, fully)) {
            return new Pack200CompressorInputStream(inputStream);
        }
        if (FramedSnappyCompressorInputStream.matches(array, fully)) {
            return new FramedSnappyCompressorInputStream(inputStream);
        }
        if (ZCompressorInputStream.matches(array, fully)) {
            return new ZCompressorInputStream(inputStream);
        }
        throw new CompressorException("No Compressor found for the stream signature.");
    }
    
    public CompressorInputStream createCompressorInputStream(final String s, final InputStream inputStream) throws CompressorException {
        if (s == null || inputStream == null) {
            throw new IllegalArgumentException("Compressor name and stream must not be null.");
        }
        if ("gz".equalsIgnoreCase(s)) {
            return new GzipCompressorInputStream(inputStream, this.decompressConcatenated);
        }
        if ("bzip2".equalsIgnoreCase(s)) {
            return new BZip2CompressorInputStream(inputStream, this.decompressConcatenated);
        }
        if ("xz".equalsIgnoreCase(s)) {
            return new XZCompressorInputStream(inputStream, this.decompressConcatenated);
        }
        if ("lzma".equalsIgnoreCase(s)) {
            return new LZMACompressorInputStream(inputStream);
        }
        if ("pack200".equalsIgnoreCase(s)) {
            return new Pack200CompressorInputStream(inputStream);
        }
        if ("snappy-raw".equalsIgnoreCase(s)) {
            return new SnappyCompressorInputStream(inputStream);
        }
        if ("snappy-framed".equalsIgnoreCase(s)) {
            return new FramedSnappyCompressorInputStream(inputStream);
        }
        if ("z".equalsIgnoreCase(s)) {
            return new ZCompressorInputStream(inputStream);
        }
        throw new CompressorException("Compressor: " + s + " not found.");
    }
    
    public CompressorOutputStream createCompressorOutputStream(final String s, final OutputStream outputStream) throws CompressorException {
        if (s == null || outputStream == null) {
            throw new IllegalArgumentException("Compressor name and stream must not be null.");
        }
        if ("gz".equalsIgnoreCase(s)) {
            return new GzipCompressorOutputStream(outputStream);
        }
        if ("bzip2".equalsIgnoreCase(s)) {
            return new BZip2CompressorOutputStream(outputStream);
        }
        if ("xz".equalsIgnoreCase(s)) {
            return new XZCompressorOutputStream(outputStream);
        }
        if ("pack200".equalsIgnoreCase(s)) {
            return new Pack200CompressorOutputStream(outputStream);
        }
        throw new CompressorException("Compressor: " + s + " not found.");
    }
}
