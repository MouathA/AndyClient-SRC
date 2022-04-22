package org.apache.commons.compress.compressors.z._internal_;

import org.apache.commons.compress.compressors.*;
import java.io.*;

public abstract class InternalLZWInputStream extends CompressorInputStream
{
    private final byte[] oneByte;
    protected final InputStream in;
    protected int clearCode;
    protected int codeSize;
    protected int bitsCached;
    protected int bitsCachedSize;
    protected int previousCode;
    protected int tableSize;
    protected int[] prefixes;
    protected byte[] characters;
    private byte[] outputStack;
    private int outputStackLocation;
    
    protected InternalLZWInputStream(final InputStream in) {
        this.oneByte = new byte[1];
        this.clearCode = -1;
        this.codeSize = 9;
        this.bitsCached = 0;
        this.bitsCachedSize = 0;
        this.previousCode = -1;
        this.tableSize = 0;
        this.in = in;
    }
    
    @Override
    public void close() throws IOException {
        this.in.close();
    }
    
    @Override
    public int read() throws IOException {
        final int read = this.read(this.oneByte);
        if (read < 0) {
            return read;
        }
        return 0xFF & this.oneByte[0];
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        int fromStack = this.readFromStack(array, n, n2);
        while (n2 - fromStack > 0) {
            final int decompressNextSymbol = this.decompressNextSymbol();
            if (decompressNextSymbol < 0) {
                if (fromStack > 0) {
                    this.count(fromStack);
                    return fromStack;
                }
                return decompressNextSymbol;
            }
            else {
                fromStack += this.readFromStack(array, n + fromStack, n2 - fromStack);
            }
        }
        this.count(fromStack);
        return fromStack;
    }
    
    protected abstract int decompressNextSymbol() throws IOException;
    
    protected abstract int addEntry(final int p0, final byte p1) throws IOException;
    
    protected void setClearCode(final int n) {
        this.clearCode = 1 << n - 1;
    }
    
    protected void initializeTables(final int n) {
        final int outputStackLocation = 1 << n;
        this.prefixes = new int[outputStackLocation];
        this.characters = new byte[outputStackLocation];
        this.outputStack = new byte[outputStackLocation];
        this.outputStackLocation = outputStackLocation;
        while (true) {
            this.prefixes[0] = -1;
            this.characters[0] = 0;
            int n2 = 0;
            ++n2;
        }
    }
    
    protected int readNextCode() throws IOException {
        while (this.bitsCachedSize < this.codeSize) {
            final int read = this.in.read();
            if (read < 0) {
                return read;
            }
            this.bitsCached |= read << this.bitsCachedSize;
            this.bitsCachedSize += 8;
        }
        final int n = this.bitsCached & (1 << this.codeSize) - 1;
        this.bitsCached >>>= this.codeSize;
        this.bitsCachedSize -= this.codeSize;
        return n;
    }
    
    protected int addEntry(final int n, final byte b, final int n2) {
        if (this.tableSize < n2) {
            final int tableSize = this.tableSize;
            this.prefixes[this.tableSize] = n;
            this.characters[this.tableSize] = b;
            ++this.tableSize;
            return tableSize;
        }
        return -1;
    }
    
    protected int addRepeatOfPreviousCode() throws IOException {
        if (this.previousCode == -1) {
            throw new IOException("The first code can't be a reference to its preceding code");
        }
        for (int i = this.previousCode; i >= 0; i = this.prefixes[i]) {
            final byte b = this.characters[i];
        }
        return this.addEntry(this.previousCode, (byte)0);
    }
    
    protected int expandCodeToOutputStack(final int previousCode, final boolean b) throws IOException {
        for (int i = previousCode; i >= 0; i = this.prefixes[i]) {
            this.outputStack[--this.outputStackLocation] = this.characters[i];
        }
        if (this.previousCode != -1 && !b) {
            this.addEntry(this.previousCode, this.outputStack[this.outputStackLocation]);
        }
        this.previousCode = previousCode;
        return this.outputStackLocation;
    }
    
    private int readFromStack(final byte[] array, final int n, final int n2) {
        final int n3 = this.outputStack.length - this.outputStackLocation;
        if (n3 > 0) {
            final int min = Math.min(n3, n2);
            System.arraycopy(this.outputStack, this.outputStackLocation, array, n, min);
            this.outputStackLocation += min;
            return min;
        }
        return 0;
    }
}
