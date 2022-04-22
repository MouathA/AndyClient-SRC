package com.ibm.icu.impl;

import java.util.*;
import java.io.*;

public class IntTrieBuilder extends TrieBuilder
{
    protected int[] m_data_;
    protected int m_initialValue_;
    private int m_leadUnitValue_;
    
    public IntTrieBuilder(final IntTrieBuilder intTrieBuilder) {
        super(intTrieBuilder);
        this.m_data_ = new int[this.m_dataCapacity_];
        System.arraycopy(intTrieBuilder.m_data_, 0, this.m_data_, 0, this.m_dataLength_);
        this.m_initialValue_ = intTrieBuilder.m_initialValue_;
        this.m_leadUnitValue_ = intTrieBuilder.m_leadUnitValue_;
    }
    
    public IntTrieBuilder(final int[] data_, final int dataCapacity_, final int initialValue_, final int leadUnitValue_, final boolean isLatin1Linear_) {
        if (dataCapacity_ < 32 || (isLatin1Linear_ && dataCapacity_ < 1024)) {
            throw new IllegalArgumentException("Argument maxdatalength is too small");
        }
        if (data_ != null) {
            this.m_data_ = data_;
        }
        else {
            this.m_data_ = new int[dataCapacity_];
        }
        if (isLatin1Linear_) {
            final int[] index_ = this.m_index_;
            final int n = 0;
            int n2 = 0;
            ++n2;
            index_[n] = 32;
            final int n3;
            n3 += 32;
        }
        this.m_dataLength_ = 32;
        Arrays.fill(this.m_data_, 0, this.m_dataLength_, initialValue_);
        this.m_initialValue_ = initialValue_;
        this.m_leadUnitValue_ = leadUnitValue_;
        this.m_dataCapacity_ = dataCapacity_;
        this.m_isLatin1Linear_ = isLatin1Linear_;
        this.m_isCompacted_ = false;
    }
    
    public int getValue(final int n) {
        if (this.m_isCompacted_ || n > 1114111 || n < 0) {
            return 0;
        }
        return this.m_data_[Math.abs(this.m_index_[n >> 5]) + (n & 0x1F)];
    }
    
    public int getValue(final int n, final boolean[] array) {
        if (this.m_isCompacted_ || n > 1114111 || n < 0) {
            if (array != null) {
                array[0] = true;
            }
            return 0;
        }
        final int n2 = this.m_index_[n >> 5];
        if (array != null) {
            array[0] = (n2 == 0);
        }
        return this.m_data_[Math.abs(n2) + (n & 0x1F)];
    }
    
    public IntTrie serialize(final DataManipulate dataManipulate, final Trie.DataManipulate dataManipulate2) {
        if (dataManipulate == null) {
            throw new IllegalArgumentException("Parameters can not be null");
        }
        if (!this.m_isCompacted_) {
            this.compact(false);
            this.fold(dataManipulate);
            this.compact(true);
            this.m_isCompacted_ = true;
        }
        if (this.m_dataLength_ >= 262144) {
            throw new ArrayIndexOutOfBoundsException("Data length too small");
        }
        final char[] array = new char[this.m_indexLength_];
        final int[] array2 = new int[this.m_dataLength_];
        while (37 < this.m_indexLength_) {
            array[37] = (char)(this.m_index_[37] >>> 2);
            int n = 0;
            ++n;
        }
        System.arraycopy(this.m_data_, 0, array2, 0, this.m_dataLength_);
        if (this.m_isLatin1Linear_) {}
        return new IntTrie(array, array2, this.m_initialValue_, 37, dataManipulate2);
    }
    
    public int serialize(final OutputStream outputStream, final boolean b, final DataManipulate dataManipulate) throws IOException {
        if (dataManipulate == null) {
            throw new IllegalArgumentException("Parameters can not be null");
        }
        if (!this.m_isCompacted_) {
            this.compact(false);
            this.fold(dataManipulate);
            this.compact(true);
            this.m_isCompacted_ = true;
        }
        int dataLength_;
        if (b) {
            dataLength_ = this.m_dataLength_ + this.m_indexLength_;
        }
        else {
            dataLength_ = this.m_dataLength_;
        }
        if (dataLength_ >= 262144) {
            throw new ArrayIndexOutOfBoundsException("Data length too small");
        }
        final int n = 16 + 2 * this.m_indexLength_;
        int n2;
        if (b) {
            n2 = n + 2 * this.m_dataLength_;
        }
        else {
            n2 = n + 4 * this.m_dataLength_;
        }
        if (outputStream == null) {
            return n2;
        }
        final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(1416784229);
        if (!b) {}
        if (this.m_isLatin1Linear_) {}
        dataOutputStream.writeInt(37);
        dataOutputStream.writeInt(this.m_indexLength_);
        dataOutputStream.writeInt(this.m_dataLength_);
        if (b) {
            int n3 = 0;
            while (0 < this.m_indexLength_) {
                dataOutputStream.writeChar(this.m_index_[0] + this.m_indexLength_ >>> 2);
                ++n3;
            }
            while (0 < this.m_dataLength_) {
                dataOutputStream.writeChar(this.m_data_[0] & 0xFFFF);
                ++n3;
            }
        }
        else {
            int n3 = 0;
            while (0 < this.m_indexLength_) {
                dataOutputStream.writeChar(this.m_index_[0] >>> 2);
                ++n3;
            }
            while (0 < this.m_dataLength_) {
                dataOutputStream.writeInt(this.m_data_[0]);
                ++n3;
            }
        }
        return n2;
    }
    
    public boolean setRange(final int n, final int n2, final int n3, final boolean b) {
        if (!this.m_isCompacted_) {}
        return false;
    }
    
    private int allocDataBlock() {
        final int dataLength_ = this.m_dataLength_;
        final int dataLength_2 = dataLength_ + 32;
        if (dataLength_2 > this.m_dataCapacity_) {
            return -1;
        }
        this.m_dataLength_ = dataLength_2;
        return dataLength_;
    }
    
    private int getDataBlock(int n) {
        n >>= 5;
        final int n2 = this.m_index_[n];
        if (n2 > 0) {
            return n2;
        }
        final int allocDataBlock = this.allocDataBlock();
        if (allocDataBlock < 0) {
            return -1;
        }
        this.m_index_[n] = allocDataBlock;
        System.arraycopy(this.m_data_, Math.abs(n2), this.m_data_, allocDataBlock, 128);
        return allocDataBlock;
    }
    
    private void compact(final boolean b) {
        if (this.m_isCompacted_) {
            return;
        }
        this.findUnusedBlocks();
        if (this.m_isLatin1Linear_) {
            final int n;
            n += 256;
        }
        int sameDataBlock = 0;
        while (32 < this.m_dataLength_) {
            if (this.m_map_[1] < 0) {
                final int n2;
                n2 += 32;
            }
            else {
                sameDataBlock = findSameDataBlock(this.m_data_, 32, 32, b ? 4 : 32);
                this.m_map_[1] = 0;
                final int n2;
                n2 += 32;
            }
        }
        while (0 < this.m_indexLength_) {
            this.m_index_[0] = this.m_map_[Math.abs(this.m_index_[0]) >>> 5];
            ++sameDataBlock;
        }
        this.m_dataLength_ = 32;
    }
    
    private static final int findSameDataBlock(final int[] array, int n, final int n2, final int n3) {
        n -= 32;
        while (0 <= n) {
            if (equal_int(array, 0, n2, 32)) {
                return 0;
            }
        }
        return -1;
    }
    
    private final void fold(final DataManipulate p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: newarray        I
        //     4: astore_2       
        //     5: aload_0        
        //     6: getfield        com/ibm/icu/impl/IntTrieBuilder.m_index_:[I
        //     9: astore_3       
        //    10: aload_3        
        //    11: sipush          1728
        //    14: aload_2        
        //    15: iconst_0       
        //    16: bipush          32
        //    18: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //    21: aload_0        
        //    22: getfield        com/ibm/icu/impl/IntTrieBuilder.m_leadUnitValue_:I
        //    25: aload_0        
        //    26: getfield        com/ibm/icu/impl/IntTrieBuilder.m_initialValue_:I
        //    29: if_icmpne       35
        //    32: goto            67
        //    35: aload_0        
        //    36: invokespecial   com/ibm/icu/impl/IntTrieBuilder.allocDataBlock:()I
        //    39: istore          4
        //    41: goto            54
        //    44: new             Ljava/lang/IllegalStateException;
        //    47: dup            
        //    48: ldc             "Internal error: Out of memory space"
        //    50: invokespecial   java/lang/IllegalStateException.<init>:(Ljava/lang/String;)V
        //    53: athrow         
        //    54: aload_0        
        //    55: iconst_0       
        //    56: iconst_0       
        //    57: bipush          32
        //    59: aload_0        
        //    60: getfield        com/ibm/icu/impl/IntTrieBuilder.m_leadUnitValue_:I
        //    63: iconst_1       
        //    64: invokespecial   com/ibm/icu/impl/IntTrieBuilder.fillBlock:(IIIIZ)V
        //    67: goto            85
        //    70: aload_0        
        //    71: getfield        com/ibm/icu/impl/IntTrieBuilder.m_index_:[I
        //    74: sipush          2048
        //    77: iconst_0       
        //    78: iastore        
        //    79: iinc            5, 1
        //    82: goto            67
        //    85: aload_3        
        //    86: sipush          2048
        //    89: iaload         
        //    90: ifeq            180
        //    93: aload_3        
        //    94: sipush          2048
        //    97: sipush          2048
        //   100: invokestatic    com/ibm/icu/impl/IntTrieBuilder.findSameIndexBlock:([III)I
        //   103: istore          4
        //   105: aload_1        
        //   106: ldc             65536
        //   108: bipush          32
        //   110: invokeinterface com/ibm/icu/impl/TrieBuilder$DataManipulate.getFoldedValue:(II)I
        //   115: istore          7
        //   117: iload           7
        //   119: aload_0        
        //   120: ldc             65536
        //   122: invokestatic    com/ibm/icu/text/UTF16.getLeadSurrogate:(I)C
        //   125: invokevirtual   com/ibm/icu/impl/IntTrieBuilder.getValue:(I)I
        //   128: if_icmpeq       171
        //   131: aload_0        
        //   132: ldc             65536
        //   134: invokestatic    com/ibm/icu/text/UTF16.getLeadSurrogate:(I)C
        //   137: iload           7
        //   139: ifne            152
        //   142: new             Ljava/lang/ArrayIndexOutOfBoundsException;
        //   145: dup            
        //   146: ldc             "Data table overflow"
        //   148: invokespecial   java/lang/ArrayIndexOutOfBoundsException.<init>:(Ljava/lang/String;)V
        //   151: athrow         
        //   152: goto            171
        //   155: aload_3        
        //   156: sipush          2048
        //   159: aload_3        
        //   160: sipush          2048
        //   163: bipush          32
        //   165: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   168: iinc            5, 32
        //   171: iinc_w          6, 1024
        //   177: goto            85
        //   180: iinc            6, 32
        //   183: goto            85
        //   186: goto            199
        //   189: new             Ljava/lang/ArrayIndexOutOfBoundsException;
        //   192: dup            
        //   193: ldc             "Index table overflow"
        //   195: invokespecial   java/lang/ArrayIndexOutOfBoundsException.<init>:(Ljava/lang/String;)V
        //   198: athrow         
        //   199: aload_3        
        //   200: sipush          2048
        //   203: aload_3        
        //   204: sipush          2080
        //   207: iconst_0       
        //   208: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   211: aload_2        
        //   212: iconst_0       
        //   213: aload_3        
        //   214: sipush          2048
        //   217: bipush          32
        //   219: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   222: iinc            5, 32
        //   225: aload_0        
        //   226: sipush          2048
        //   229: putfield        com/ibm/icu/impl/IntTrieBuilder.m_indexLength_:I
        //   232: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0171 (coming from #0152).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void fillBlock(int i, final int n, int n2, final int n3, final boolean b) {
        n2 += i;
        i += n;
        if (b) {
            while (i < n2) {
                this.m_data_[i++] = n3;
            }
        }
        else {
            while (i < n2) {
                if (this.m_data_[i] == this.m_initialValue_) {
                    this.m_data_[i] = n3;
                }
                ++i;
            }
        }
    }
}
