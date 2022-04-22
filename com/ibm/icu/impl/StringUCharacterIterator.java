package com.ibm.icu.impl;

import com.ibm.icu.text.*;

public final class StringUCharacterIterator extends UCharacterIterator
{
    private String m_text_;
    private int m_currentIndex_;
    
    public StringUCharacterIterator(final String text_) {
        if (text_ == null) {
            throw new IllegalArgumentException();
        }
        this.m_text_ = text_;
        this.m_currentIndex_ = 0;
    }
    
    public StringUCharacterIterator() {
        this.m_text_ = "";
        this.m_currentIndex_ = 0;
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public int current() {
        if (this.m_currentIndex_ < this.m_text_.length()) {
            return this.m_text_.charAt(this.m_currentIndex_);
        }
        return -1;
    }
    
    @Override
    public int getLength() {
        return this.m_text_.length();
    }
    
    @Override
    public int getIndex() {
        return this.m_currentIndex_;
    }
    
    @Override
    public int next() {
        if (this.m_currentIndex_ < this.m_text_.length()) {
            return this.m_text_.charAt(this.m_currentIndex_++);
        }
        return -1;
    }
    
    @Override
    public int previous() {
        if (this.m_currentIndex_ > 0) {
            final String text_ = this.m_text_;
            final int currentIndex_ = this.m_currentIndex_ - 1;
            this.m_currentIndex_ = currentIndex_;
            return text_.charAt(currentIndex_);
        }
        return -1;
    }
    
    @Override
    public void setIndex(final int currentIndex_) throws IndexOutOfBoundsException {
        if (currentIndex_ < 0 || currentIndex_ > this.m_text_.length()) {
            throw new IndexOutOfBoundsException();
        }
        this.m_currentIndex_ = currentIndex_;
    }
    
    @Override
    public int getText(final char[] array, final int n) {
        final int length = this.m_text_.length();
        if (n < 0 || n + length > array.length) {
            throw new IndexOutOfBoundsException(Integer.toString(length));
        }
        this.m_text_.getChars(0, length, array, n);
        return length;
    }
    
    @Override
    public String getText() {
        return this.m_text_;
    }
    
    public void setText(final String text_) {
        if (text_ == null) {
            throw new NullPointerException();
        }
        this.m_text_ = text_;
        this.m_currentIndex_ = 0;
    }
}
