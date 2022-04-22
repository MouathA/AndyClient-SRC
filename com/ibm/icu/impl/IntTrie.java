package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import java.util.*;
import java.io.*;

public class IntTrie extends Trie
{
    private int m_initialValue_;
    private int[] m_data_;
    static final boolean $assertionsDisabled;
    
    public IntTrie(final InputStream inputStream, final DataManipulate dataManipulate) throws IOException {
        super(inputStream, dataManipulate);
        if (!this.isIntTrie()) {
            throw new IllegalArgumentException("Data given does not belong to a int trie.");
        }
    }
    
    public IntTrie(final int p0, final int p1, final DataManipulate p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: sipush          2080
        //     4: newarray        C
        //     6: sipush          512
        //     9: aload_3        
        //    10: invokespecial   com/ibm/icu/impl/Trie.<init>:([CILcom/ibm/icu/impl/Trie$DataManipulate;)V
        //    13: sipush          256
        //    16: sipush          256
        //    19: istore          5
        //    21: istore          4
        //    23: iload_2        
        //    24: iload_1        
        //    25: if_icmpeq       31
        //    28: iinc            4, 32
        //    31: aload_0        
        //    32: iload           4
        //    34: newarray        I
        //    36: putfield        com/ibm/icu/impl/IntTrie.m_data_:[I
        //    39: aload_0        
        //    40: iload           4
        //    42: putfield        com/ibm/icu/impl/IntTrie.m_dataLength_:I
        //    45: aload_0        
        //    46: iload_1        
        //    47: putfield        com/ibm/icu/impl/IntTrie.m_initialValue_:I
        //    50: sipush          1728
        //    53: iload           5
        //    55: if_icmpge       73
        //    58: aload_0        
        //    59: getfield        com/ibm/icu/impl/IntTrie.m_data_:[I
        //    62: sipush          1728
        //    65: iload_1        
        //    66: iastore        
        //    67: iinc            6, 1
        //    70: goto            50
        //    73: iload_2        
        //    74: iload_1        
        //    75: if_icmpeq       145
        //    78: iload           5
        //    80: iconst_2       
        //    81: ishr           
        //    82: i2c            
        //    83: istore          8
        //    85: sipush          1728
        //    88: sipush          1760
        //    91: if_icmpge       110
        //    94: aload_0        
        //    95: getfield        com/ibm/icu/impl/IntTrie.m_index_:[C
        //    98: sipush          1728
        //   101: iload           8
        //   103: castore        
        //   104: iinc            6, 1
        //   107: goto            85
        //   110: iload           5
        //   112: bipush          32
        //   114: iadd           
        //   115: istore          7
        //   117: iload           5
        //   119: istore          6
        //   121: sipush          1728
        //   124: sipush          1760
        //   127: if_icmpge       145
        //   130: aload_0        
        //   131: getfield        com/ibm/icu/impl/IntTrie.m_data_:[I
        //   134: sipush          1728
        //   137: iload_2        
        //   138: iastore        
        //   139: iinc            6, 1
        //   142: goto            121
        //   145: return         
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
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
    
    public final int getCodePointValue(final int n) {
        if (0 <= n && n < 55296) {
            return this.m_data_[(this.m_index_[n >> 5] << 2) + (n & 0x1F)];
        }
        final int codePointOffset = this.getCodePointOffset(n);
        return (codePointOffset >= 0) ? this.m_data_[codePointOffset] : this.m_initialValue_;
    }
    
    public final int getLeadValue(final char c) {
        return this.m_data_[this.getLeadOffset(c)];
    }
    
    public final int getBMPValue(final char c) {
        return this.m_data_[this.getBMPOffset(c)];
    }
    
    public final int getSurrogateValue(final char c, final char c2) {
        if (!UTF16.isLeadSurrogate(c) || !UTF16.isTrailSurrogate(c2)) {
            throw new IllegalArgumentException("Argument characters do not form a supplementary character");
        }
        final int surrogateOffset = this.getSurrogateOffset(c, c2);
        if (surrogateOffset > 0) {
            return this.m_data_[surrogateOffset];
        }
        return this.m_initialValue_;
    }
    
    public final int getTrailValue(final int n, final char c) {
        if (this.m_dataManipulate_ == null) {
            throw new NullPointerException("The field DataManipulate in this Trie is null");
        }
        final int foldingOffset = this.m_dataManipulate_.getFoldingOffset(n);
        if (foldingOffset > 0) {
            return this.m_data_[this.getRawOffset(foldingOffset, (char)(c & '\u03ff'))];
        }
        return this.m_initialValue_;
    }
    
    public final int getLatin1LinearValue(final char c) {
        return this.m_data_[' ' + c];
    }
    
    @Override
    public boolean equals(final Object o) {
        if (super.equals(o) && o instanceof IntTrie) {
            final IntTrie intTrie = (IntTrie)o;
            return this.m_initialValue_ == intTrie.m_initialValue_ && Arrays.equals(this.m_data_, intTrie.m_data_);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Override
    protected final void unserialize(final InputStream inputStream) throws IOException {
        super.unserialize(inputStream);
        this.m_data_ = new int[this.m_dataLength_];
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        while (0 < this.m_dataLength_) {
            this.m_data_[0] = dataInputStream.readInt();
            int n = 0;
            ++n;
        }
        this.m_initialValue_ = this.m_data_[0];
    }
    
    @Override
    protected final int getSurrogateOffset(final char c, final char c2) {
        if (this.m_dataManipulate_ == null) {
            throw new NullPointerException("The field DataManipulate in this Trie is null");
        }
        final int foldingOffset = this.m_dataManipulate_.getFoldingOffset(this.getLeadValue(c));
        if (foldingOffset > 0) {
            return this.getRawOffset(foldingOffset, (char)(c2 & '\u03ff'));
        }
        return -1;
    }
    
    @Override
    protected final int getValue(final int n) {
        return this.m_data_[n];
    }
    
    @Override
    protected final int getInitialValue() {
        return this.m_initialValue_;
    }
    
    IntTrie(final char[] array, final int[] data_, final int initialValue_, final int n, final DataManipulate dataManipulate) {
        super(array, n, dataManipulate);
        this.m_data_ = data_;
        this.m_dataLength_ = this.m_data_.length;
        this.m_initialValue_ = initialValue_;
    }
    
    static {
        $assertionsDisabled = !IntTrie.class.desiredAssertionStatus();
    }
}
