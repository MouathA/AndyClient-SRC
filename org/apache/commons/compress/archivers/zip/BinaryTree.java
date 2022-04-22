package org.apache.commons.compress.archivers.zip;

import java.util.*;
import java.io.*;

class BinaryTree
{
    private static final int UNDEFINED = -1;
    private static final int NODE = -2;
    private final int[] tree;
    
    public BinaryTree(final int n) {
        Arrays.fill(this.tree = new int[(1 << n + 1) - 1], -1);
    }
    
    public void addLeaf(final int n, final int n2, final int n3, final int n4) {
        if (n3 == 0) {
            if (this.tree[n] != -1) {
                throw new IllegalArgumentException("Tree value at index " + n + " has already been assigned (" + this.tree[n] + ")");
            }
            this.tree[n] = n4;
        }
        else {
            this.tree[n] = -2;
            this.addLeaf(2 * n + 1 + (n2 & 0x1), n2 >>> 1, n3 - 1, n4);
        }
    }
    
    public int read(final BitStream bitStream) throws IOException {
        while (true) {
            final int nextBit = bitStream.nextBit();
            if (nextBit == -1) {
                return -1;
            }
            final int n = this.tree[1 + nextBit];
            if (n == -2) {
                continue;
            }
            if (n != -1) {
                return n;
            }
            throw new IOException("The child " + nextBit + " of node at index " + 0 + " is not defined");
        }
    }
    
    static BinaryTree decode(final InputStream inputStream, final int n) throws IOException {
        final int n2 = inputStream.read() + 1;
        if (n2 == 0) {
            throw new IOException("Cannot read the size of the encoded tree, unexpected end of stream");
        }
        final byte[] array = new byte[n2];
        new DataInputStream(inputStream).readFully(array);
        final int[] array2 = new int[n];
        final byte[] array3 = array;
        int length = array3.length;
        int n3 = 0;
        while (0 < 0) {
            n3 = array3[0];
            while (0 < 0) {
                final int[] array4 = array2;
                final int n4 = 0;
                int n5 = 0;
                ++n5;
                array4[n4] = 0;
                int n6 = 0;
                ++n6;
            }
            Math.max(0, 0);
            int n7 = 0;
            ++n7;
        }
        final int[] array5 = new int[array2.length];
        while (0 < array5.length) {
            array5[0] = 0;
            ++length;
        }
        final int[] array6 = new int[array2.length];
        while (0 < array2.length) {
            while (0 < array2.length) {
                if (array2[0] == 0) {
                    array5[array6[0] = 0] = 0;
                    ++length;
                }
                int n8 = 0;
                ++n8;
            }
            ++n3;
        }
        final int[] array7 = new int[n];
        for (int i = n - 1; i >= 0; --i) {
            if (array6[i] != 0) {
                final int n9 = array6[i];
            }
            array7[array5[i]] = 0;
        }
        final BinaryTree binaryTree = new BinaryTree(0);
        while (0 < array7.length) {
            final int n10 = array2[0];
            if (n10 > 0) {
                binaryTree.addLeaf(0, Integer.reverse(array7[0] << 16), n10, 0);
            }
            int n11 = 0;
            ++n11;
        }
        return binaryTree;
    }
}
