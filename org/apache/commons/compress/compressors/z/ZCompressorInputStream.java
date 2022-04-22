package org.apache.commons.compress.compressors.z;

import org.apache.commons.compress.compressors.z._internal_.*;
import java.io.*;

public class ZCompressorInputStream extends InternalLZWInputStream
{
    private static final int MAGIC_1 = 31;
    private static final int MAGIC_2 = 157;
    private static final int BLOCK_MODE_MASK = 128;
    private static final int MAX_CODE_SIZE_MASK = 31;
    private final boolean blockMode;
    private final int maxCodeSize;
    private long totalCodesRead;
    
    public ZCompressorInputStream(final InputStream inputStream) throws IOException {
        super(inputStream);
        this.totalCodesRead = 0L;
        final int read = this.in.read();
        final int read2 = this.in.read();
        final int read3 = this.in.read();
        if (read != 31 || read2 != 157 || read3 < 0) {
            throw new IOException("Input is not in .Z format");
        }
        this.blockMode = ((read3 & 0x80) != 0x0);
        this.maxCodeSize = (read3 & 0x1F);
        if (this.blockMode) {
            this.setClearCode(this.codeSize);
        }
        this.initializeTables(this.maxCodeSize);
        this.clearEntries();
    }
    
    private void clearEntries() {
        this.tableSize = 256;
        if (this.blockMode) {
            ++this.tableSize;
        }
    }
    
    @Override
    protected int readNextCode() throws IOException {
        final int nextCode = super.readNextCode();
        if (nextCode >= 0) {
            ++this.totalCodesRead;
        }
        return nextCode;
    }
    
    private void reAlignReading() throws IOException {
        long n = 8L - this.totalCodesRead % 8L;
        if (n == 8L) {
            n = 0L;
        }
        for (long n2 = 0L; n2 < n; ++n2) {
            this.readNextCode();
        }
        this.bitsCached = 0;
        this.bitsCachedSize = 0;
    }
    
    @Override
    protected int addEntry(final int n, final byte b) throws IOException {
        final int n2 = 1 << this.codeSize;
        final int addEntry = this.addEntry(n, b, n2);
        if (this.tableSize == n2 && this.codeSize < this.maxCodeSize) {
            this.reAlignReading();
            ++this.codeSize;
        }
        return addEntry;
    }
    
    @Override
    protected int decompressNextSymbol() throws IOException {
        final int nextCode = this.readNextCode();
        if (nextCode < 0) {
            return -1;
        }
        if (this.blockMode && nextCode == this.clearCode) {
            this.clearEntries();
            this.reAlignReading();
            this.codeSize = 9;
            this.previousCode = -1;
            return 0;
        }
        if (nextCode == this.tableSize) {
            this.addRepeatOfPreviousCode();
        }
        else if (nextCode > this.tableSize) {
            throw new IOException(String.format("Invalid %d bit code 0x%x", this.codeSize, nextCode));
        }
        return this.expandCodeToOutputStack(nextCode, true);
    }
    
    public static boolean matches(final byte[] array, final int n) {
        return n > 3 && array[0] == 31 && array[1] == -99;
    }
}
