package org.apache.commons.compress.archivers.zip;

import java.io.*;

class ExplodingInputStream extends InputStream
{
    private final InputStream in;
    private BitStream bits;
    private final int dictionarySize;
    private final int numberOfTrees;
    private final int minimumMatchLength;
    private BinaryTree literalTree;
    private BinaryTree lengthTree;
    private BinaryTree distanceTree;
    private final CircularBuffer buffer;
    
    public ExplodingInputStream(final int dictionarySize, final int n, final InputStream in) {
        this.buffer = new CircularBuffer(32768);
        if (dictionarySize != 4096 && dictionarySize != 8192) {
            throw new IllegalArgumentException("The dictionary size must be 4096 or 8192");
        }
        if (n != 2 && n != 3) {
            throw new IllegalArgumentException("The number of trees must be 2 or 3");
        }
        this.dictionarySize = dictionarySize;
        this.numberOfTrees = n;
        this.minimumMatchLength = n;
        this.in = in;
    }
    
    private void init() throws IOException {
        if (this.bits == null) {
            if (this.numberOfTrees == 3) {
                this.literalTree = BinaryTree.decode(this.in, 256);
            }
            this.lengthTree = BinaryTree.decode(this.in, 64);
            this.distanceTree = BinaryTree.decode(this.in, 64);
            this.bits = new BitStream(this.in);
        }
    }
    
    @Override
    public int read() throws IOException {
        if (!this.buffer.available()) {
            this.fillBuffer();
        }
        return this.buffer.get();
    }
    
    private void fillBuffer() throws IOException {
        this.init();
        final int nextBit = this.bits.nextBit();
        if (nextBit == 1) {
            int n;
            if (this.literalTree != null) {
                n = this.literalTree.read(this.bits);
            }
            else {
                n = this.bits.nextBits(8);
            }
            if (n == -1) {
                return;
            }
            this.buffer.put(n);
        }
        else if (nextBit == 0) {
            final int n2 = (this.dictionarySize == 4096) ? 6 : 7;
            final int nextBits = this.bits.nextBits(n2);
            final int read = this.distanceTree.read(this.bits);
            if (read == -1 && nextBits <= 0) {
                return;
            }
            final int n3 = read << n2 | nextBits;
            int read2 = this.lengthTree.read(this.bits);
            if (read2 == 63) {
                read2 += this.bits.nextBits(8);
            }
            this.buffer.copy(n3 + 1, read2 + this.minimumMatchLength);
        }
    }
}
