package com.ibm.icu.util;

import java.util.*;
import com.ibm.icu.text.*;

public final class StringTokenizer implements Enumeration
{
    private int m_tokenOffset_;
    private int m_tokenSize_;
    private int[] m_tokenStart_;
    private int[] m_tokenLimit_;
    private UnicodeSet m_delimiters_;
    private String m_source_;
    private int m_length_;
    private int m_nextOffset_;
    private boolean m_returnDelimiters_;
    private boolean m_coalesceDelimiters_;
    private static final UnicodeSet DEFAULT_DELIMITERS_;
    private static final int TOKEN_SIZE_ = 100;
    private static final UnicodeSet EMPTY_DELIMITER_;
    private boolean[] delims;
    
    public StringTokenizer(final String s, final UnicodeSet set, final boolean b) {
        this(s, set, b, false);
    }
    
    @Deprecated
    public StringTokenizer(final String source_, final UnicodeSet delimiters_, final boolean returnDelimiters_, final boolean coalesceDelimiters_) {
        this.m_source_ = source_;
        this.m_length_ = source_.length();
        if (delimiters_ == null) {
            this.m_delimiters_ = StringTokenizer.EMPTY_DELIMITER_;
        }
        else {
            this.m_delimiters_ = delimiters_;
        }
        this.m_returnDelimiters_ = returnDelimiters_;
        this.m_coalesceDelimiters_ = coalesceDelimiters_;
        this.m_tokenOffset_ = -1;
        this.m_tokenSize_ = -1;
        if (this.m_length_ == 0) {
            this.m_nextOffset_ = -1;
        }
        else {
            this.m_nextOffset_ = 0;
            if (!returnDelimiters_) {
                this.m_nextOffset_ = this.getNextNonDelimiter(0);
            }
        }
    }
    
    public StringTokenizer(final String s, final UnicodeSet set) {
        this(s, set, false, false);
    }
    
    public StringTokenizer(final String s, final String s2, final boolean b) {
        this(s, s2, b, false);
    }
    
    @Deprecated
    public StringTokenizer(final String source_, final String s, final boolean returnDelimiters_, final boolean coalesceDelimiters_) {
        this.m_delimiters_ = StringTokenizer.EMPTY_DELIMITER_;
        if (s != null && s.length() > 0) {
            (this.m_delimiters_ = new UnicodeSet()).addAll(s);
            this.checkDelimiters();
        }
        this.m_coalesceDelimiters_ = coalesceDelimiters_;
        this.m_source_ = source_;
        this.m_length_ = source_.length();
        this.m_returnDelimiters_ = returnDelimiters_;
        this.m_tokenOffset_ = -1;
        this.m_tokenSize_ = -1;
        if (this.m_length_ == 0) {
            this.m_nextOffset_ = -1;
        }
        else {
            this.m_nextOffset_ = 0;
            if (!returnDelimiters_) {
                this.m_nextOffset_ = this.getNextNonDelimiter(0);
            }
        }
    }
    
    public StringTokenizer(final String s, final String s2) {
        this(s, s2, false, false);
    }
    
    public StringTokenizer(final String s) {
        this(s, StringTokenizer.DEFAULT_DELIMITERS_, false, false);
    }
    
    public boolean hasMoreTokens() {
        return this.m_nextOffset_ >= 0;
    }
    
    public String nextToken() {
        if (this.m_tokenOffset_ < 0) {
            if (this.m_nextOffset_ < 0) {
                throw new NoSuchElementException("No more tokens in String");
            }
            if (this.m_returnDelimiters_) {
                final int char1 = UTF16.charAt(this.m_source_, this.m_nextOffset_);
                if ((this.delims == null) ? this.m_delimiters_.contains(char1) : (char1 < this.delims.length && this.delims[char1])) {
                    if (this.m_coalesceDelimiters_) {
                        this.getNextNonDelimiter(this.m_nextOffset_);
                    }
                    else {
                        final int n = this.m_nextOffset_ + UTF16.getCharCount(char1);
                        if (-1 == this.m_length_) {}
                    }
                }
                else {
                    this.getNextDelimiter(this.m_nextOffset_);
                }
                String s;
                if (-1 < 0) {
                    s = this.m_source_.substring(this.m_nextOffset_);
                }
                else {
                    s = this.m_source_.substring(this.m_nextOffset_, -1);
                }
                this.m_nextOffset_ = -1;
                return s;
            }
            this.getNextDelimiter(this.m_nextOffset_);
            String s2;
            if (-1 < 0) {
                s2 = this.m_source_.substring(this.m_nextOffset_);
                this.m_nextOffset_ = -1;
            }
            else {
                s2 = this.m_source_.substring(this.m_nextOffset_, -1);
                this.m_nextOffset_ = this.getNextNonDelimiter(-1);
            }
            return s2;
        }
        else {
            if (this.m_tokenOffset_ >= this.m_tokenSize_) {
                throw new NoSuchElementException("No more tokens in String");
            }
            String s3;
            if (this.m_tokenLimit_[this.m_tokenOffset_] >= 0) {
                s3 = this.m_source_.substring(this.m_tokenStart_[this.m_tokenOffset_], this.m_tokenLimit_[this.m_tokenOffset_]);
            }
            else {
                s3 = this.m_source_.substring(this.m_tokenStart_[this.m_tokenOffset_]);
            }
            ++this.m_tokenOffset_;
            this.m_nextOffset_ = -1;
            if (this.m_tokenOffset_ < this.m_tokenSize_) {
                this.m_nextOffset_ = this.m_tokenStart_[this.m_tokenOffset_];
            }
            return s3;
        }
    }
    
    public String nextToken(final String s) {
        this.m_delimiters_ = StringTokenizer.EMPTY_DELIMITER_;
        if (s != null && s.length() > 0) {
            (this.m_delimiters_ = new UnicodeSet()).addAll(s);
        }
        return this.nextToken(this.m_delimiters_);
    }
    
    public String nextToken(final UnicodeSet delimiters_) {
        this.m_delimiters_ = delimiters_;
        this.checkDelimiters();
        this.m_tokenOffset_ = -1;
        this.m_tokenSize_ = -1;
        if (!this.m_returnDelimiters_) {
            this.m_nextOffset_ = this.getNextNonDelimiter(this.m_nextOffset_);
        }
        return this.nextToken();
    }
    
    public boolean hasMoreElements() {
        return this.hasMoreTokens();
    }
    
    public Object nextElement() {
        return this.nextToken();
    }
    
    public int countTokens() {
        if (this.hasMoreTokens()) {
            if (this.m_tokenOffset_ >= 0) {
                return this.m_tokenSize_ - this.m_tokenOffset_;
            }
            if (this.m_tokenStart_ == null) {
                this.m_tokenStart_ = new int[100];
                this.m_tokenLimit_ = new int[100];
            }
            do {
                if (this.m_tokenStart_.length == 0) {
                    final int[] tokenStart_ = this.m_tokenStart_;
                    final int[] tokenLimit_ = this.m_tokenLimit_;
                    final int length = tokenStart_.length;
                    this.m_tokenStart_ = new int[99];
                    this.m_tokenLimit_ = new int[99];
                    System.arraycopy(tokenStart_, 0, this.m_tokenStart_, 0, -1);
                    System.arraycopy(tokenLimit_, 0, this.m_tokenLimit_, 0, -1);
                }
                this.m_tokenStart_[0] = this.m_nextOffset_;
                if (this.m_returnDelimiters_) {
                    final int char1 = UTF16.charAt(this.m_source_, this.m_nextOffset_);
                    if ((this.delims == null) ? this.m_delimiters_.contains(char1) : (char1 < this.delims.length && this.delims[char1])) {
                        if (this.m_coalesceDelimiters_) {
                            this.m_tokenLimit_[0] = this.getNextNonDelimiter(this.m_nextOffset_);
                        }
                        else {
                            final int n = this.m_nextOffset_ + 1;
                            if (-1 == this.m_length_) {}
                            this.m_tokenLimit_[0] = -1;
                        }
                    }
                    else {
                        this.m_tokenLimit_[0] = this.getNextDelimiter(this.m_nextOffset_);
                    }
                    this.m_nextOffset_ = this.m_tokenLimit_[0];
                }
                else {
                    this.m_tokenLimit_[0] = this.getNextDelimiter(this.m_nextOffset_);
                    this.m_nextOffset_ = this.getNextNonDelimiter(this.m_tokenLimit_[0]);
                }
                int n2 = 0;
                ++n2;
            } while (this.m_nextOffset_ >= 0);
            this.m_tokenOffset_ = 0;
            this.m_tokenSize_ = 0;
            this.m_nextOffset_ = this.m_tokenStart_[0];
        }
        return 0;
    }
    
    private int getNextDelimiter(final int n) {
        if (n >= 0) {
            int n2 = n;
            if (this.delims == null) {
                do {
                    UTF16.charAt(this.m_source_, n2);
                    if (this.m_delimiters_.contains(0)) {
                        break;
                    }
                } while (++n2 < this.m_length_);
            }
            else {
                do {
                    UTF16.charAt(this.m_source_, n2);
                    if (0 < this.delims.length && this.delims[0]) {
                        break;
                    }
                } while (++n2 < this.m_length_);
            }
            if (n2 < this.m_length_) {
                return n2;
            }
        }
        return -1 - this.m_length_;
    }
    
    private int getNextNonDelimiter(final int n) {
        if (n >= 0) {
            int n2 = n;
            if (this.delims == null) {
                do {
                    UTF16.charAt(this.m_source_, n2);
                    if (!this.m_delimiters_.contains(0)) {
                        break;
                    }
                } while (++n2 < this.m_length_);
            }
            else {
                do {
                    UTF16.charAt(this.m_source_, n2);
                    if (0 >= this.delims.length) {
                        break;
                    }
                    if (!this.delims[0]) {
                        break;
                    }
                } while (++n2 < this.m_length_);
            }
            if (n2 < this.m_length_) {
                return n2;
            }
        }
        return -1 - this.m_length_;
    }
    
    void checkDelimiters() {
        if (this.m_delimiters_ == null || this.m_delimiters_.size() == 0) {
            this.delims = new boolean[0];
        }
        else {
            final int rangeEnd = this.m_delimiters_.getRangeEnd(this.m_delimiters_.getRangeCount() - 1);
            if (rangeEnd < 127) {
                this.delims = new boolean[rangeEnd + 1];
                int char1;
                while (-1 != (char1 = this.m_delimiters_.charAt(0))) {
                    this.delims[char1] = true;
                    int n = 0;
                    ++n;
                }
            }
            else {
                this.delims = null;
            }
        }
    }
    
    static {
        DEFAULT_DELIMITERS_ = new UnicodeSet(new int[] { 9, 10, 12, 13, 32, 32 });
        EMPTY_DELIMITER_ = UnicodeSet.EMPTY;
    }
}
