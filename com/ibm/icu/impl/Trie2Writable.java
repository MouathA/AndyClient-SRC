package com.ibm.icu.impl;

import java.util.*;

public class Trie2Writable extends Trie2
{
    private static final int UTRIE2_MAX_INDEX_LENGTH = 65535;
    private static final int UTRIE2_MAX_DATA_LENGTH = 262140;
    private static final int UNEWTRIE2_INITIAL_DATA_LENGTH = 16384;
    private static final int UNEWTRIE2_MEDIUM_DATA_LENGTH = 131072;
    private static final int UNEWTRIE2_INDEX_2_NULL_OFFSET = 2656;
    private static final int UNEWTRIE2_INDEX_2_START_OFFSET = 2720;
    private static final int UNEWTRIE2_DATA_NULL_OFFSET = 192;
    private static final int UNEWTRIE2_DATA_START_OFFSET = 256;
    private static final int UNEWTRIE2_DATA_0800_OFFSET = 2176;
    private int[] index1;
    private int[] index2;
    private int[] data;
    private int index2Length;
    private int dataCapacity;
    private int firstFreeBlock;
    private int index2NullOffset;
    private boolean isCompacted;
    private int[] map;
    private boolean UTRIE2_DEBUG;
    static final boolean $assertionsDisabled;
    
    public Trie2Writable(final int n, final int n2) {
        this.index1 = new int[544];
        this.index2 = new int[35488];
        this.map = new int[34852];
        this.UTRIE2_DEBUG = false;
        this.init(n, n2);
    }
    
    private void init(final int n, final int n2) {
        this.initialValue = n;
        this.errorValue = n2;
        this.highStart = 1114112;
        this.data = new int[16384];
        this.dataCapacity = 16384;
        this.initialValue = n;
        this.errorValue = n2;
        this.highStart = 1114112;
        this.firstFreeBlock = 0;
        this.isCompacted = false;
        while (true) {
            this.data[128] = this.errorValue;
            int n3 = 0;
            ++n3;
        }
    }
    
    public Trie2Writable(final Trie2 trie2) {
        this.index1 = new int[544];
        this.index2 = new int[35488];
        this.map = new int[34852];
        this.UTRIE2_DEBUG = false;
        this.init(trie2.initialValue, trie2.errorValue);
        final Iterator iterator = trie2.iterator();
        while (iterator.hasNext()) {
            this.setRange(iterator.next(), true);
        }
    }
    
    private int allocIndex2Block() {
        final int index2Length = this.index2Length;
        final int index2Length2 = index2Length + 64;
        if (index2Length2 > this.index2.length) {
            throw new IllegalStateException("Internal error in Trie2 creation.");
        }
        this.index2Length = index2Length2;
        System.arraycopy(this.index2, this.index2NullOffset, this.index2, index2Length, 64);
        return index2Length;
    }
    
    private int getIndex2Block(final int n, final boolean b) {
        if (n >= 55296 && n < 56320 && b) {
            return 2048;
        }
        final int n2 = n >> 11;
        int allocIndex2Block = this.index1[n2];
        if (allocIndex2Block == this.index2NullOffset) {
            allocIndex2Block = this.allocIndex2Block();
            this.index1[n2] = allocIndex2Block;
        }
        return allocIndex2Block;
    }
    
    private int allocDataBlock(final int n) {
        int n2;
        if (this.firstFreeBlock != 0) {
            n2 = this.firstFreeBlock;
            this.firstFreeBlock = -this.map[n2 >> 5];
        }
        else {
            n2 = this.dataLength;
            final int dataLength = n2 + 32;
            if (dataLength > this.dataCapacity) {
                if (this.dataCapacity >= 131072) {
                    if (this.dataCapacity >= 1115264) {
                        throw new IllegalStateException("Internal error in Trie2 creation.");
                    }
                }
                final int[] data = new int[1115264];
                System.arraycopy(this.data, 0, data, 0, this.dataLength);
                this.data = data;
                this.dataCapacity = 1115264;
            }
            this.dataLength = dataLength;
        }
        System.arraycopy(this.data, n, this.data, n2, 32);
        this.map[n2 >> 5] = 0;
        return n2;
    }
    
    private void releaseDataBlock(final int firstFreeBlock) {
        this.map[firstFreeBlock >> 5] = -this.firstFreeBlock;
        this.firstFreeBlock = firstFreeBlock;
    }
    
    private void setIndex2Entry(final int n, final int n2) {
        final int[] map = this.map;
        final int n3 = n2 >> 5;
        ++map[n3];
        final int n4 = this.index2[n];
        final boolean b = false;
        final int[] map2 = this.map;
        final int n5 = n4 >> 5;
        final int n6 = map2[n5] - 1;
        map2[n5] = n6;
        if ((b ? 1 : 0) == n6) {
            this.releaseDataBlock(n4);
        }
        this.index2[n] = n2;
    }
    
    private int getDataBlock(final int n, final boolean b) {
        final int n2 = this.getIndex2Block(n, b) + (n >> 5 & 0x3F);
        final int n3 = this.index2[n2];
        if (this != n3) {
            return n3;
        }
        final int allocDataBlock = this.allocDataBlock(n3);
        this.setIndex2Entry(n2, allocDataBlock);
        return allocDataBlock;
    }
    
    public Trie2Writable set(final int n, final int n2) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Invalid code point.");
        }
        this.set(n, true, n2);
        this.fHash = 0;
        return this;
    }
    
    private Trie2Writable set(final int n, final boolean b, final int n2) {
        if (this.isCompacted) {
            this.uncompact();
        }
        this.data[this.getDataBlock(n, b) + (n & 0x1F)] = n2;
        return this;
    }
    
    private void uncompact() {
        final Trie2Writable trie2Writable = new Trie2Writable(this);
        this.index1 = trie2Writable.index1;
        this.index2 = trie2Writable.index2;
        this.data = trie2Writable.data;
        this.index2Length = trie2Writable.index2Length;
        this.dataCapacity = trie2Writable.dataCapacity;
        this.isCompacted = trie2Writable.isCompacted;
        this.header = trie2Writable.header;
        this.index = trie2Writable.index;
        this.data16 = trie2Writable.data16;
        this.data32 = trie2Writable.data32;
        this.indexLength = trie2Writable.indexLength;
        this.dataLength = trie2Writable.dataLength;
        this.index2NullOffset = trie2Writable.index2NullOffset;
        this.initialValue = trie2Writable.initialValue;
        this.errorValue = trie2Writable.errorValue;
        this.highStart = trie2Writable.highStart;
        this.highValueIndex = trie2Writable.highValueIndex;
        this.dataNullOffset = trie2Writable.dataNullOffset;
    }
    
    private void writeBlock(int i, final int n) {
        while (i < i + 32) {
            this.data[i++] = n;
        }
    }
    
    private void fillBlock(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        final int n6 = n + n3;
        if (b) {
            for (int i = n + n2; i < n6; ++i) {
                this.data[i] = n4;
            }
        }
        else {
            for (int j = n + n2; j < n6; ++j) {
                if (this.data[j] == n5) {
                    this.data[j] = n4;
                }
            }
        }
    }
    
    public Trie2Writable setRange(final int p0, final int p1, final int p2, final boolean p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             1114111
        //     3: if_icmpgt       25
        //     6: iload_1        
        //     7: iflt            25
        //    10: iload_2        
        //    11: ldc             1114111
        //    13: if_icmpgt       25
        //    16: iload_2        
        //    17: iflt            25
        //    20: iload_1        
        //    21: iload_2        
        //    22: if_icmple       35
        //    25: new             Ljava/lang/IllegalArgumentException;
        //    28: dup            
        //    29: ldc             "Invalid code point range."
        //    31: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //    34: athrow         
        //    35: iload           4
        //    37: ifne            50
        //    40: iload_3        
        //    41: aload_0        
        //    42: getfield        com/ibm/icu/impl/Trie2Writable.initialValue:I
        //    45: if_icmpne       50
        //    48: aload_0        
        //    49: areturn        
        //    50: aload_0        
        //    51: iconst_0       
        //    52: putfield        com/ibm/icu/impl/Trie2Writable.fHash:I
        //    55: aload_0        
        //    56: getfield        com/ibm/icu/impl/Trie2Writable.isCompacted:Z
        //    59: ifeq            66
        //    62: aload_0        
        //    63: invokespecial   com/ibm/icu/impl/Trie2Writable.uncompact:()V
        //    66: iload_2        
        //    67: iconst_1       
        //    68: iadd           
        //    69: istore          8
        //    71: iload_1        
        //    72: bipush          31
        //    74: iand           
        //    75: ifeq            151
        //    78: aload_0        
        //    79: iload_1        
        //    80: iconst_1       
        //    81: invokespecial   com/ibm/icu/impl/Trie2Writable.getDataBlock:(IZ)I
        //    84: istore          5
        //    86: iload_1        
        //    87: bipush          32
        //    89: iadd           
        //    90: bipush          -32
        //    92: iand           
        //    93: istore          9
        //    95: iload           9
        //    97: iload           8
        //    99: if_icmpgt       127
        //   102: aload_0        
        //   103: iload           5
        //   105: iload_1        
        //   106: bipush          31
        //   108: iand           
        //   109: bipush          32
        //   111: iload_3        
        //   112: aload_0        
        //   113: getfield        com/ibm/icu/impl/Trie2Writable.initialValue:I
        //   116: iload           4
        //   118: invokespecial   com/ibm/icu/impl/Trie2Writable.fillBlock:(IIIIIZ)V
        //   121: iload           9
        //   123: istore_1       
        //   124: goto            151
        //   127: aload_0        
        //   128: iload           5
        //   130: iload_1        
        //   131: bipush          31
        //   133: iand           
        //   134: iload           8
        //   136: bipush          31
        //   138: iand           
        //   139: iload_3        
        //   140: aload_0        
        //   141: getfield        com/ibm/icu/impl/Trie2Writable.initialValue:I
        //   144: iload           4
        //   146: invokespecial   com/ibm/icu/impl/Trie2Writable.fillBlock:(IIIIIZ)V
        //   149: aload_0        
        //   150: areturn        
        //   151: iload           8
        //   153: bipush          31
        //   155: iand           
        //   156: istore          6
        //   158: iload           8
        //   160: bipush          -32
        //   162: iand           
        //   163: istore          8
        //   165: iload_3        
        //   166: aload_0        
        //   167: getfield        com/ibm/icu/impl/Trie2Writable.initialValue:I
        //   170: if_icmpne       182
        //   173: aload_0        
        //   174: getfield        com/ibm/icu/impl/Trie2Writable.dataNullOffset:I
        //   177: istore          7
        //   179: goto            182
        //   182: iload_1        
        //   183: iload           8
        //   185: if_icmpge       331
        //   188: iload_3        
        //   189: aload_0        
        //   190: getfield        com/ibm/icu/impl/Trie2Writable.initialValue:I
        //   193: if_icmpne       204
        //   196: aload_0        
        //   197: iload_1        
        //   198: iinc            1, 32
        //   201: goto            182
        //   204: aload_0        
        //   205: iload_1        
        //   206: iconst_1       
        //   207: invokespecial   com/ibm/icu/impl/Trie2Writable.getIndex2Block:(IZ)I
        //   210: istore          9
        //   212: iload           9
        //   214: iload_1        
        //   215: iconst_5       
        //   216: ishr           
        //   217: bipush          63
        //   219: iand           
        //   220: iadd           
        //   221: istore          9
        //   223: aload_0        
        //   224: getfield        com/ibm/icu/impl/Trie2Writable.index2:[I
        //   227: iload           9
        //   229: iaload         
        //   230: istore          5
        //   232: aload_0        
        //   233: iload           5
        //   235: if_icmpeq       273
        //   238: iload           4
        //   240: ifeq            254
        //   243: iload           5
        //   245: sipush          2176
        //   248: if_icmplt       254
        //   251: goto            298
        //   254: aload_0        
        //   255: iload           5
        //   257: iconst_0       
        //   258: bipush          32
        //   260: iload_3        
        //   261: aload_0        
        //   262: getfield        com/ibm/icu/impl/Trie2Writable.initialValue:I
        //   265: iload           4
        //   267: invokespecial   com/ibm/icu/impl/Trie2Writable.fillBlock:(IIIIIZ)V
        //   270: goto            298
        //   273: aload_0        
        //   274: getfield        com/ibm/icu/impl/Trie2Writable.data:[I
        //   277: iload           5
        //   279: iaload         
        //   280: iload_3        
        //   281: if_icmpeq       298
        //   284: iload           4
        //   286: ifne            298
        //   289: iload           5
        //   291: aload_0        
        //   292: getfield        com/ibm/icu/impl/Trie2Writable.dataNullOffset:I
        //   295: if_icmpne       298
        //   298: goto            311
        //   301: aload_0        
        //   302: iload           9
        //   304: iconst_m1      
        //   305: invokespecial   com/ibm/icu/impl/Trie2Writable.setIndex2Entry:(II)V
        //   308: goto            325
        //   311: aload_0        
        //   312: iload_1        
        //   313: iconst_1       
        //   314: invokespecial   com/ibm/icu/impl/Trie2Writable.getDataBlock:(IZ)I
        //   317: istore          7
        //   319: aload_0        
        //   320: iconst_m1      
        //   321: iload_3        
        //   322: invokespecial   com/ibm/icu/impl/Trie2Writable.writeBlock:(II)V
        //   325: iinc            1, 32
        //   328: goto            182
        //   331: iload           6
        //   333: ifle            360
        //   336: aload_0        
        //   337: iload_1        
        //   338: iconst_1       
        //   339: invokespecial   com/ibm/icu/impl/Trie2Writable.getDataBlock:(IZ)I
        //   342: istore          5
        //   344: aload_0        
        //   345: iload           5
        //   347: iconst_0       
        //   348: iload           6
        //   350: iload_3        
        //   351: aload_0        
        //   352: getfield        com/ibm/icu/impl/Trie2Writable.initialValue:I
        //   355: iload           4
        //   357: invokespecial   com/ibm/icu/impl/Trie2Writable.fillBlock:(IIIIIZ)V
        //   360: aload_0        
        //   361: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0182 (coming from #0201).
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
    
    public Trie2Writable setRange(final Range range, final boolean b) {
        this.fHash = 0;
        if (range.leadSurrogate) {
            for (int i = range.startCodePoint; i <= range.endCodePoint; ++i) {
                if (b || this.getFromU16SingleLead((char)i) == this.initialValue) {
                    this.setForLeadSurrogateCodeUnit((char)i, range.value);
                }
            }
        }
        else {
            this.setRange(range.startCodePoint, range.endCodePoint, range.value, b);
        }
        return this;
    }
    
    public Trie2Writable setForLeadSurrogateCodeUnit(final char c, final int n) {
        this.fHash = 0;
        this.set(c, false, n);
        return this;
    }
    
    @Override
    public int get(final int n) {
        if (n < 0 || n > 1114111) {
            return this.errorValue;
        }
        return this.get(n, true);
    }
    
    private int get(final int n, final boolean b) {
        if (n >= this.highStart && (n < 55296 || n >= 56320 || b)) {
            return this.data[this.dataLength - 4];
        }
        int n2;
        if (n >= 55296 && n < 56320 && b) {
            n2 = 320 + (n >> 5);
        }
        else {
            n2 = this.index1[n >> 11] + (n >> 5 & 0x3F);
        }
        return this.data[this.index2[n2] + (n & 0x1F)];
    }
    
    @Override
    public int getFromU16SingleLead(final char c) {
        return this.get(c, false);
    }
    
    private int findSameIndex2Block(final int p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: iconst_0       
        //     4: iload_1        
        //     5: if_icmpgt       28
        //     8: aload_0        
        //     9: aload_0        
        //    10: getfield        com/ibm/icu/impl/Trie2Writable.index2:[I
        //    13: iconst_0       
        //    14: iload_2        
        //    15: bipush          64
        //    17: if_icmpge       22
        //    20: iconst_0       
        //    21: ireturn        
        //    22: iinc            3, 1
        //    25: goto            3
        //    28: iconst_m1      
        //    29: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0003 (coming from #0025).
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
    
    private int findSameDataBlock(final int p0, final int p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iload_3        
        //     2: isub           
        //     3: istore_1       
        //     4: iconst_0       
        //     5: iload_1        
        //     6: if_icmpgt       28
        //     9: aload_0        
        //    10: aload_0        
        //    11: getfield        com/ibm/icu/impl/Trie2Writable.data:[I
        //    14: iconst_0       
        //    15: iload_2        
        //    16: iload_3        
        //    17: if_icmpge       22
        //    20: iconst_0       
        //    21: ireturn        
        //    22: iinc            4, 4
        //    25: goto            4
        //    28: iconst_m1      
        //    29: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0004 (coming from #0025).
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
    
    private int findHighStart(final int n) {
        if (n == this.initialValue) {
            final int index2NullOffset = this.index2NullOffset;
            final int dataNullOffset = this.dataNullOffset;
        }
        int n3;
        int n4 = 0;
        while (true) {
            final int[] index1 = this.index1;
            int n2 = 0;
            --n2;
            n3 = index1[544];
            if (n3 == -1) {
                n4 -= 2048;
            }
            else {
                if (n3 != this.index2NullOffset) {
                    break;
                }
                if (n != this.initialValue) {
                    return 1114112;
                }
                n4 -= 2048;
            }
        }
        int n7;
        while (true) {
            final int[] index2 = this.index2;
            final int n5 = n3;
            int n6 = 0;
            --n6;
            n7 = index2[n5 + 64];
            if (n7 == -1) {
                n4 -= 32;
            }
            else {
                if (n7 != this.dataNullOffset) {
                    break;
                }
                if (n != this.initialValue) {
                    return 1114112;
                }
                n4 -= 32;
            }
        }
        while (true) {
            final int[] data = this.data;
            final int n8 = n7;
            int n9 = 0;
            --n9;
            if (data[n8 + 32] != n) {
                break;
            }
            --n4;
        }
        return 1114112;
    }
    
    private void compactData() {
        while (true) {
            this.map[0] = 0;
            final int n;
            n += 32;
            int n2 = 0;
            ++n2;
        }
    }
    
    private void compactIndex2() {
        int n = 2080 + (32 + (this.highStart - 65536 >> 11));
        int n5 = 0;
        while (2656 < this.index2Length) {
            final int sameIndex2Block;
            if ((sameIndex2Block = this.findSameIndex2Block(2080, 2656)) >= 0) {
                this.map[41] = sameIndex2Block;
                final int n2;
                n2 += 64;
            }
            else {
                this.index2;
                2017;
                this.map[41] = 2017;
                while (true) {
                    final int[] index2 = this.index2;
                    final int n3 = 2080;
                    ++n;
                    final int[] index3 = this.index2;
                    final int n4 = 2656;
                    int n2 = 0;
                    ++n2;
                    index2[n3] = index3[n4];
                    --n5;
                }
            }
        }
        while (true) {
            this.index1[0] = this.map[this.index1[0] >> 6];
            ++n5;
        }
    }
    
    private void compactTrie() {
        int n = this.get(1114111);
        final int highStart = this.findHighStart(n) + 2047 & 0xFFFFF800;
        if (highStart == 1114112) {
            n = this.errorValue;
        }
        this.highStart = highStart;
        if (this.UTRIE2_DEBUG) {
            System.out.printf("UTrie2: highStart U+%04x  highValue 0x%x  initialValue 0x%x\n", this.highStart, n, this.initialValue);
        }
        if (this.highStart < 1114112) {
            this.setRange((this.highStart <= 65536) ? 65536 : this.highStart, 1114111, this.initialValue, true);
        }
        this.compactData();
        if (this.highStart > 65536) {
            this.compactIndex2();
        }
        else if (this.UTRIE2_DEBUG) {
            System.out.printf("UTrie2: highStart U+%04x  count of 16-bit index-2 words %d->%d\n", this.highStart, this.index2Length, 2112);
        }
        this.data[this.dataLength++] = n;
        while ((this.dataLength & 0x3) != 0x0) {
            this.data[this.dataLength++] = this.initialValue;
        }
        this.isCompacted = true;
    }
    
    public Trie2_16 toTrie2_16() {
        final Trie2_16 trie2_16 = new Trie2_16();
        this.freeze(trie2_16, ValueWidth.BITS_16);
        return trie2_16;
    }
    
    public Trie2_32 toTrie2_32() {
        final Trie2_32 trie2_32 = new Trie2_32();
        this.freeze(trie2_32, ValueWidth.BITS_32);
        return trie2_32;
    }
    
    private void freeze(final Trie2 trie2, final ValueWidth valueWidth) {
        if (!this.isCompacted) {
            this.compactTrie();
        }
        if (this.highStart > 65536) {
            final int index2Length = this.index2Length;
        }
        if (valueWidth == ValueWidth.BITS_16) {}
        if (0 + this.dataNullOffset > 65535 || 0 + this.dataLength > 262140) {
            throw new UnsupportedOperationException("Trie2 data is too large.");
        }
        if (valueWidth == ValueWidth.BITS_16) {
            final int n = 2112 + this.dataLength;
        }
        else {
            trie2.data32 = new int[this.dataLength];
        }
        trie2.index = new char[2112];
        trie2.indexLength = 2112;
        trie2.dataLength = this.dataLength;
        if (this.highStart <= 65536) {
            trie2.index2NullOffset = 65535;
        }
        else {
            trie2.index2NullOffset = 0 + this.index2NullOffset;
        }
        trie2.initialValue = this.initialValue;
        trie2.errorValue = this.errorValue;
        trie2.highStart = this.highStart;
        trie2.highValueIndex = 0 + this.dataLength - 4;
        trie2.dataNullOffset = 0 + this.dataNullOffset;
        trie2.header = new UTrie2Header();
        trie2.header.signature = 1416784178;
        trie2.header.options = ((valueWidth != ValueWidth.BITS_16) ? 1 : 0);
        trie2.header.indexLength = trie2.indexLength;
        trie2.header.shiftedDataLength = trie2.dataLength >> 2;
        trie2.header.index2NullOffset = trie2.index2NullOffset;
        trie2.header.dataNullOffset = trie2.dataNullOffset;
        trie2.header.shiftedHighStart = trie2.highStart >> 11;
        while (true) {
            final char[] index = trie2.index;
            final int n2 = 0;
            int n3 = 0;
            ++n3;
            index[n2] = (char)(this.index2[0] + 0 >> 2);
            int n4 = 0;
            ++n4;
        }
    }
    
    static {
        $assertionsDisabled = !Trie2Writable.class.desiredAssertionStatus();
    }
}
