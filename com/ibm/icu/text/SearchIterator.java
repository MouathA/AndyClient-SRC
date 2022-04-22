package com.ibm.icu.text;

import java.text.*;

public abstract class SearchIterator
{
    public static final int DONE = -1;
    protected BreakIterator breakIterator;
    protected CharacterIterator targetText;
    protected int matchLength;
    private boolean m_isForwardSearching_;
    private boolean m_isOverlap_;
    private boolean m_reset_;
    private int m_setOffset_;
    private int m_lastMatchStart_;
    
    public void setIndex(final int setOffset_) {
        if (setOffset_ < this.targetText.getBeginIndex() || setOffset_ > this.targetText.getEndIndex()) {
            throw new IndexOutOfBoundsException("setIndex(int) expected position to be between " + this.targetText.getBeginIndex() + " and " + this.targetText.getEndIndex());
        }
        this.m_setOffset_ = setOffset_;
        this.m_reset_ = false;
        this.matchLength = 0;
    }
    
    public void setOverlapping(final boolean isOverlap_) {
        this.m_isOverlap_ = isOverlap_;
    }
    
    public void setBreakIterator(final BreakIterator breakIterator) {
        this.breakIterator = breakIterator;
        if (this.breakIterator != null) {
            this.breakIterator.setText(this.targetText);
        }
    }
    
    public void setTarget(final CharacterIterator targetText) {
        if (targetText == null || targetText.getEndIndex() == targetText.getIndex()) {
            throw new IllegalArgumentException("Illegal null or empty text");
        }
        (this.targetText = targetText).setIndex(this.targetText.getBeginIndex());
        this.matchLength = 0;
        this.m_reset_ = true;
        this.m_isForwardSearching_ = true;
        if (this.breakIterator != null) {
            this.breakIterator.setText(this.targetText);
        }
    }
    
    public int getMatchStart() {
        return this.m_lastMatchStart_;
    }
    
    public abstract int getIndex();
    
    public int getMatchLength() {
        return this.matchLength;
    }
    
    public BreakIterator getBreakIterator() {
        return this.breakIterator;
    }
    
    public CharacterIterator getTarget() {
        return this.targetText;
    }
    
    public String getMatchedText() {
        if (this.matchLength > 0) {
            final int n = this.m_lastMatchStart_ + this.matchLength;
            final StringBuilder sb = new StringBuilder(this.matchLength);
            sb.append(this.targetText.current());
            this.targetText.next();
            while (this.targetText.getIndex() < n) {
                sb.append(this.targetText.current());
                this.targetText.next();
            }
            this.targetText.setIndex(this.m_lastMatchStart_);
            return sb.toString();
        }
        return null;
    }
    
    public int next() {
        int n = this.targetText.getIndex();
        if (this.m_setOffset_ != -1) {
            n = this.m_setOffset_;
            this.m_setOffset_ = -1;
        }
        if (this.m_isForwardSearching_) {
            if (!this.m_reset_ && n + this.matchLength >= this.targetText.getEndIndex()) {
                this.matchLength = 0;
                this.targetText.setIndex(this.targetText.getEndIndex());
                return this.m_lastMatchStart_ = -1;
            }
            this.m_reset_ = false;
        }
        else {
            this.m_isForwardSearching_ = true;
            if (n != -1) {
                return n;
            }
        }
        if (n == -1) {
            n = this.targetText.getBeginIndex();
        }
        if (this.matchLength > 0) {
            if (this.m_isOverlap_) {
                ++n;
            }
            else {
                n += this.matchLength;
            }
        }
        return this.m_lastMatchStart_ = this.handleNext(n);
    }
    
    public int previous() {
        int n = this.targetText.getIndex();
        if (this.m_setOffset_ != -1) {
            n = this.m_setOffset_;
            this.m_setOffset_ = -1;
        }
        if (this.m_reset_) {
            this.m_isForwardSearching_ = false;
            this.m_reset_ = false;
            n = this.targetText.getEndIndex();
        }
        if (this.m_isForwardSearching_) {
            this.m_isForwardSearching_ = false;
            if (n != this.targetText.getEndIndex()) {
                return n;
            }
        }
        else if (n == this.targetText.getBeginIndex()) {
            this.matchLength = 0;
            this.targetText.setIndex(this.targetText.getBeginIndex());
            return this.m_lastMatchStart_ = -1;
        }
        return this.m_lastMatchStart_ = this.handlePrevious(n);
    }
    
    public boolean isOverlapping() {
        return this.m_isOverlap_;
    }
    
    public void reset() {
        this.matchLength = 0;
        this.setIndex(this.targetText.getBeginIndex());
        this.m_isOverlap_ = false;
        this.m_isForwardSearching_ = true;
        this.m_reset_ = true;
        this.m_setOffset_ = -1;
    }
    
    public final int first() {
        this.m_isForwardSearching_ = true;
        this.setIndex(this.targetText.getBeginIndex());
        return this.next();
    }
    
    public final int following(final int index) {
        this.m_isForwardSearching_ = true;
        this.setIndex(index);
        return this.next();
    }
    
    public final int last() {
        this.m_isForwardSearching_ = false;
        this.setIndex(this.targetText.getEndIndex());
        return this.previous();
    }
    
    public final int preceding(final int index) {
        this.m_isForwardSearching_ = false;
        this.setIndex(index);
        return this.previous();
    }
    
    protected SearchIterator(final CharacterIterator characterIterator, final BreakIterator breakIterator) {
        if (characterIterator == null || characterIterator.getEndIndex() - characterIterator.getBeginIndex() == 0) {
            throw new IllegalArgumentException("Illegal argument target.  Argument can not be null or of length 0");
        }
        this.targetText = characterIterator;
        this.breakIterator = breakIterator;
        if (this.breakIterator != null) {
            this.breakIterator.setText(characterIterator);
        }
        this.matchLength = 0;
        this.m_lastMatchStart_ = -1;
        this.m_isOverlap_ = false;
        this.m_isForwardSearching_ = true;
        this.m_reset_ = true;
        this.m_setOffset_ = -1;
    }
    
    protected void setMatchLength(final int matchLength) {
        this.matchLength = matchLength;
    }
    
    protected abstract int handleNext(final int p0);
    
    protected abstract int handlePrevious(final int p0);
}
