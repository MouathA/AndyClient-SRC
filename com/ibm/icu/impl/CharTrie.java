package com.ibm.icu.impl;

import java.io.*;

public class CharTrie extends Trie
{
    private char m_initialValue_;
    private char[] m_data_;
    static final boolean $assertionsDisabled;
    
    public CharTrie(final InputStream inputStream, final DataManipulate dataManipulate) throws IOException {
        super(inputStream, dataManipulate);
        if (!this.isCharTrie()) {
            throw new IllegalArgumentException("Data given does not belong to a char trie.");
        }
    }
    
    public CharTrie(final int p0, final int p1, final DataManipulate p2) {
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
        //    34: newarray        C
        //    36: putfield        com/ibm/icu/impl/CharTrie.m_data_:[C
        //    39: aload_0        
        //    40: iload           4
        //    42: putfield        com/ibm/icu/impl/CharTrie.m_dataLength_:I
        //    45: aload_0        
        //    46: iload_1        
        //    47: i2c            
        //    48: putfield        com/ibm/icu/impl/CharTrie.m_initialValue_:C
        //    51: sipush          1728
        //    54: iload           5
        //    56: if_icmpge       75
        //    59: aload_0        
        //    60: getfield        com/ibm/icu/impl/CharTrie.m_data_:[C
        //    63: sipush          1728
        //    66: iload_1        
        //    67: i2c            
        //    68: castore        
        //    69: iinc            6, 1
        //    72: goto            51
        //    75: iload_2        
        //    76: iload_1        
        //    77: if_icmpeq       148
        //    80: iload           5
        //    82: iconst_2       
        //    83: ishr           
        //    84: i2c            
        //    85: istore          8
        //    87: sipush          1728
        //    90: sipush          1760
        //    93: if_icmpge       112
        //    96: aload_0        
        //    97: getfield        com/ibm/icu/impl/CharTrie.m_index_:[C
        //   100: sipush          1728
        //   103: iload           8
        //   105: castore        
        //   106: iinc            6, 1
        //   109: goto            87
        //   112: iload           5
        //   114: bipush          32
        //   116: iadd           
        //   117: istore          7
        //   119: iload           5
        //   121: istore          6
        //   123: sipush          1728
        //   126: sipush          1760
        //   129: if_icmpge       148
        //   132: aload_0        
        //   133: getfield        com/ibm/icu/impl/CharTrie.m_data_:[C
        //   136: sipush          1728
        //   139: iload_2        
        //   140: i2c            
        //   141: castore        
        //   142: iinc            6, 1
        //   145: goto            123
        //   148: return         
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
    
    public final char getCodePointValue(final int n) {
        if (0 <= n && n < 55296) {
            return this.m_data_[(this.m_index_[n >> 5] << 2) + (n & 0x1F)];
        }
        final int codePointOffset = this.getCodePointOffset(n);
        return (codePointOffset >= 0) ? this.m_data_[codePointOffset] : this.m_initialValue_;
    }
    
    public final char getLeadValue(final char c) {
        return this.m_data_[this.getLeadOffset(c)];
    }
    
    public final char getBMPValue(final char c) {
        return this.m_data_[this.getBMPOffset(c)];
    }
    
    public final char getSurrogateValue(final char c, final char c2) {
        final int surrogateOffset = this.getSurrogateOffset(c, c2);
        if (surrogateOffset > 0) {
            return this.m_data_[surrogateOffset];
        }
        return this.m_initialValue_;
    }
    
    public final char getTrailValue(final int n, final char c) {
        if (this.m_dataManipulate_ == null) {
            throw new NullPointerException("The field DataManipulate in this Trie is null");
        }
        final int foldingOffset = this.m_dataManipulate_.getFoldingOffset(n);
        if (foldingOffset > 0) {
            return this.m_data_[this.getRawOffset(foldingOffset, (char)(c & '\u03ff'))];
        }
        return this.m_initialValue_;
    }
    
    public final char getLatin1LinearValue(final char c) {
        return this.m_data_[32 + this.m_dataOffset_ + c];
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && o instanceof CharTrie && this.m_initialValue_ == ((CharTrie)o).m_initialValue_;
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Override
    protected final void unserialize(final InputStream inputStream) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        final int n = this.m_dataOffset_ + this.m_dataLength_;
        this.m_index_ = new char[n];
        while (0 < n) {
            this.m_index_[0] = dataInputStream.readChar();
            int n2 = 0;
            ++n2;
        }
        this.m_data_ = this.m_index_;
        this.m_initialValue_ = this.m_data_[this.m_dataOffset_];
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
    
    static {
        $assertionsDisabled = !CharTrie.class.desiredAssertionStatus();
    }
}
