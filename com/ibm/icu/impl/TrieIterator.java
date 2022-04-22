package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import com.ibm.icu.text.*;

public class TrieIterator implements RangeValueIterator
{
    private static final int BMP_INDEX_LENGTH_ = 2048;
    private static final int LEAD_SURROGATE_MIN_VALUE_ = 55296;
    private static final int TRAIL_SURROGATE_MIN_VALUE_ = 56320;
    private static final int TRAIL_SURROGATE_COUNT_ = 1024;
    private static final int TRAIL_SURROGATE_INDEX_BLOCK_LENGTH_ = 32;
    private static final int DATA_BLOCK_LENGTH_ = 32;
    private Trie m_trie_;
    private int m_initialValue_;
    private int m_currentCodepoint_;
    private int m_nextCodepoint_;
    private int m_nextValue_;
    private int m_nextIndex_;
    private int m_nextBlock_;
    private int m_nextBlockIndex_;
    private int m_nextTrailIndexOffset_;
    
    public TrieIterator(final Trie trie_) {
        if (trie_ == null) {
            throw new IllegalArgumentException("Argument trie cannot be null");
        }
        this.m_trie_ = trie_;
        this.m_initialValue_ = this.extract(this.m_trie_.getInitialValue());
        this.reset();
    }
    
    public final boolean next(final Element p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/ibm/icu/impl/TrieIterator.m_nextCodepoint_:I
        //     4: ldc             1114111
        //     6: if_icmple       11
        //     9: iconst_0       
        //    10: ireturn        
        //    11: aload_0        
        //    12: getfield        com/ibm/icu/impl/TrieIterator.m_nextCodepoint_:I
        //    15: ldc             65536
        //    17: if_icmpge       27
        //    20: aload_0        
        //    21: aload_1        
        //    22: ifne            27
        //    25: iconst_1       
        //    26: ireturn        
        //    27: aload_0        
        //    28: aload_1        
        //    29: invokespecial   com/ibm/icu/impl/TrieIterator.calculateNextSupplementaryElement:(Lcom/ibm/icu/util/RangeValueIterator$Element;)V
        //    32: iconst_1       
        //    33: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0027 (coming from #0022).
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
    
    public final void reset() {
        this.m_currentCodepoint_ = 0;
        this.m_nextCodepoint_ = 0;
        this.m_nextIndex_ = 0;
        this.m_nextBlock_ = this.m_trie_.m_index_[0] << 2;
        if (this.m_nextBlock_ == this.m_trie_.m_dataOffset_) {
            this.m_nextValue_ = this.m_initialValue_;
        }
        else {
            this.m_nextValue_ = this.extract(this.m_trie_.getValue(this.m_nextBlock_));
        }
        this.m_nextBlockIndex_ = 0;
        this.m_nextTrailIndexOffset_ = 32;
    }
    
    protected int extract(final int n) {
        return n;
    }
    
    private final void setResult(final Element element, final int start, final int limit, final int value) {
        element.start = start;
        element.limit = limit;
        element.value = value;
    }
    
    private final void calculateNextSupplementaryElement(final Element element) {
        final int nextValue_ = this.m_nextValue_;
        ++this.m_nextCodepoint_;
        ++this.m_nextBlockIndex_;
        if (UTF16.getTrailSurrogate(this.m_nextCodepoint_) != '\udc00') {
            if (this <= 0 && this < nextValue_) {
                this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, nextValue_);
                this.m_currentCodepoint_ = this.m_nextCodepoint_;
                return;
            }
            ++this.m_nextIndex_;
            ++this.m_nextTrailIndexOffset_;
            if (this < nextValue_) {
                this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, nextValue_);
                this.m_currentCodepoint_ = this.m_nextCodepoint_;
                return;
            }
        }
        int i = UTF16.getLeadSurrogate(this.m_nextCodepoint_);
        while (i < 56320) {
            final int nextBlock_ = this.m_trie_.m_index_[i >> 5] << 2;
            if (nextBlock_ == this.m_trie_.m_dataOffset_) {
                if (nextValue_ != this.m_initialValue_) {
                    this.m_nextValue_ = this.m_initialValue_;
                    this.m_nextBlock_ = nextBlock_;
                    this.m_nextBlockIndex_ = 0;
                    this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, nextValue_);
                    this.m_currentCodepoint_ = this.m_nextCodepoint_;
                    return;
                }
                i += 32;
                this.m_nextCodepoint_ = UCharacterProperty.getRawSupplementary((char)i, '\udc00');
            }
            else {
                if (this.m_trie_.m_dataManipulate_ == null) {
                    throw new NullPointerException("The field DataManipulate in this Trie is null");
                }
                this.m_nextIndex_ = this.m_trie_.m_dataManipulate_.getFoldingOffset(this.m_trie_.getValue(nextBlock_ + (i & 0x1F)));
                if (this.m_nextIndex_ <= 0) {
                    if (nextValue_ != this.m_initialValue_) {
                        this.m_nextValue_ = this.m_initialValue_;
                        this.m_nextBlock_ = this.m_trie_.m_dataOffset_;
                        this.m_nextBlockIndex_ = 0;
                        this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, nextValue_);
                        this.m_currentCodepoint_ = this.m_nextCodepoint_;
                        return;
                    }
                    this.m_nextCodepoint_ += 1024;
                }
                else {
                    this.m_nextTrailIndexOffset_ = 0;
                    if (this < nextValue_) {
                        this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, nextValue_);
                        this.m_currentCodepoint_ = this.m_nextCodepoint_;
                        return;
                    }
                }
                ++i;
            }
        }
        this.setResult(element, this.m_currentCodepoint_, 1114112, nextValue_);
    }
}
