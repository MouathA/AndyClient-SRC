package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.io.*;
import java.text.*;

class BreakCTDictionary
{
    private CompactTrieHeader fData;
    private CompactTrieNodes[] nodes;
    private static final byte[] DATA_FORMAT_ID;
    
    private CompactTrieNodes getCompactTrieNode(final int n) {
        return this.nodes[n];
    }
    
    public BreakCTDictionary(final InputStream inputStream) throws IOException {
        ICUBinary.readHeader(inputStream, BreakCTDictionary.DATA_FORMAT_ID, null);
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        this.fData = new CompactTrieHeader();
        this.fData.size = dataInputStream.readInt();
        this.fData.magic = dataInputStream.readInt();
        this.fData.nodeCount = dataInputStream.readShort();
        this.fData.root = dataInputStream.readShort();
        this.loadBreakCTDictionary(dataInputStream);
    }
    
    private void loadBreakCTDictionary(final DataInputStream dataInputStream) throws IOException {
        int n = 0;
        while (1 < this.fData.nodeCount) {
            dataInputStream.readInt();
            ++n;
        }
        (this.nodes = new CompactTrieNodes[this.fData.nodeCount])[0] = new CompactTrieNodes();
        while (1 < this.fData.nodeCount) {
            this.nodes[1] = new CompactTrieNodes();
            this.nodes[1].flagscount = dataInputStream.readShort();
            final int n2 = this.nodes[1].flagscount & 0xFFF;
            if (n2 != 0) {
                if ((this.nodes[1].flagscount & 0x1000) != 0x0) {
                    this.nodes[1].vnode = new CompactTrieVerticalNode();
                    this.nodes[1].vnode.equal = dataInputStream.readShort();
                    this.nodes[1].vnode.chars = new char[n2];
                    while (0 < n2) {
                        this.nodes[1].vnode.chars[0] = dataInputStream.readChar();
                        int n3 = 0;
                        ++n3;
                    }
                }
                else {
                    this.nodes[1].hnode = new CompactTrieHorizontalNode[n2];
                    while (0 < n2) {
                        this.nodes[1].hnode[0] = new CompactTrieHorizontalNode(dataInputStream.readChar(), dataInputStream.readShort());
                        int n3 = 0;
                        ++n3;
                    }
                }
            }
            ++n;
        }
    }
    
    public int matches(final CharacterIterator characterIterator, final int n, final int[] array, final int[] array2, int n2) {
        CompactTrieNodes compactTrieNodes = this.getCompactTrieNode(this.fData.root);
        char c = characterIterator.current();
        while (compactTrieNodes != null) {
            if (n2 > 0 && (compactTrieNodes.flagscount & 0x2000) != 0x0) {
                final int n3 = 0;
                int n4 = 0;
                ++n4;
                array[n3] = 0;
                --n2;
            }
            if (0 >= n) {
                break;
            }
            final int n5 = compactTrieNodes.flagscount & 0xFFF;
            if (n5 == 0) {
                break;
            }
            if ((compactTrieNodes.flagscount & 0x1000) != 0x0) {
                int n6 = 0;
                int n7 = 0;
                for (CompactTrieVerticalNode vnode = compactTrieNodes.vnode; 0 < n5 && 0 < n && c == vnode.chars[0]; c = characterIterator.current(), ++n6, ++n7) {
                    characterIterator.next();
                }
                if (true) {
                    break;
                }
                final CompactTrieVerticalNode vnode;
                compactTrieNodes = this.getCompactTrieNode(vnode.equal);
            }
            else {
                final CompactTrieHorizontalNode[] hnode = compactTrieNodes.hnode;
                int i = n5 - 1;
                compactTrieNodes = null;
                while (i >= 0) {
                    final int n8 = i + 0 >>> 1;
                    if (c == hnode[n8].ch) {
                        compactTrieNodes = this.getCompactTrieNode(hnode[n8].equal);
                        characterIterator.next();
                        c = characterIterator.current();
                        int n6 = 0;
                        ++n6;
                        break;
                    }
                    if (c < hnode[n8].ch) {
                        i = n8 - 1;
                    }
                    else {
                        final int n7 = n8 + 1;
                    }
                }
            }
        }
        return array2[0] = 0;
    }
    
    static {
        DATA_FORMAT_ID = new byte[] { 84, 114, 68, 99 };
    }
    
    static class CompactTrieNodes
    {
        short flagscount;
        CompactTrieHorizontalNode[] hnode;
        CompactTrieVerticalNode vnode;
        
        CompactTrieNodes() {
            this.flagscount = 0;
            this.hnode = null;
            this.vnode = null;
        }
    }
    
    static class CompactTrieHorizontalNode
    {
        char ch;
        int equal;
        
        CompactTrieHorizontalNode(final char ch, final int equal) {
            this.ch = ch;
            this.equal = equal;
        }
    }
    
    static class CompactTrieVerticalNode
    {
        int equal;
        char[] chars;
        
        CompactTrieVerticalNode() {
            this.equal = 0;
            this.chars = null;
        }
    }
    
    static final class CompactTrieNodeFlags
    {
        static final int kVerticalNode = 4096;
        static final int kParentEndsWord = 8192;
        static final int kReservedFlag1 = 16384;
        static final int kReservedFlag2 = 32768;
        static final int kCountMask = 4095;
        static final int kFlagMask = 61440;
    }
    
    static class CompactTrieHeader
    {
        int size;
        int magic;
        int nodeCount;
        int root;
        int[] offset;
        
        CompactTrieHeader() {
            this.size = 0;
            this.magic = 0;
            this.nodeCount = 0;
            this.root = 0;
            this.offset = null;
        }
    }
}
