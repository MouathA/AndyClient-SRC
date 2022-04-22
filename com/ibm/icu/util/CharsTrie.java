package com.ibm.icu.util;

import com.ibm.icu.text.*;
import java.util.*;

public final class CharsTrie implements Cloneable, Iterable
{
    private static BytesTrie.Result[] valueResults_;
    static final int kMaxBranchLinearSubNodeLength = 5;
    static final int kMinLinearMatch = 48;
    static final int kMaxLinearMatchLength = 16;
    static final int kMinValueLead = 64;
    static final int kNodeTypeMask = 63;
    static final int kValueIsFinal = 32768;
    static final int kMaxOneUnitValue = 16383;
    static final int kMinTwoUnitValueLead = 16384;
    static final int kThreeUnitValueLead = 32767;
    static final int kMaxTwoUnitValue = 1073676287;
    static final int kMaxOneUnitNodeValue = 255;
    static final int kMinTwoUnitNodeValueLead = 16448;
    static final int kThreeUnitNodeValueLead = 32704;
    static final int kMaxTwoUnitNodeValue = 16646143;
    static final int kMaxOneUnitDelta = 64511;
    static final int kMinTwoUnitDeltaLead = 64512;
    static final int kThreeUnitDeltaLead = 65535;
    static final int kMaxTwoUnitDelta = 67043327;
    private CharSequence chars_;
    private int root_;
    private int pos_;
    private int remainingMatchLength_;
    static final boolean $assertionsDisabled;
    
    public CharsTrie(final CharSequence chars_, final int n) {
        this.chars_ = chars_;
        this.root_ = n;
        this.pos_ = n;
        this.remainingMatchLength_ = -1;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public CharsTrie reset() {
        this.pos_ = this.root_;
        this.remainingMatchLength_ = -1;
        return this;
    }
    
    public CharsTrie saveState(final State state) {
        State.access$002(state, this.chars_);
        State.access$102(state, this.root_);
        State.access$202(state, this.pos_);
        State.access$302(state, this.remainingMatchLength_);
        return this;
    }
    
    public CharsTrie resetToState(final State state) {
        if (this.chars_ == State.access$000(state) && this.chars_ != null && this.root_ == State.access$100(state)) {
            this.pos_ = State.access$200(state);
            this.remainingMatchLength_ = State.access$300(state);
            return this;
        }
        throw new IllegalArgumentException("incompatible trie state");
    }
    
    public BytesTrie.Result current() {
        final int pos_ = this.pos_;
        if (pos_ < 0) {
            return BytesTrie.Result.NO_MATCH;
        }
        final char char1;
        return (this.remainingMatchLength_ < 0 && (char1 = this.chars_.charAt(pos_)) >= '@') ? CharsTrie.valueResults_[char1 >> 15] : BytesTrie.Result.NO_VALUE;
    }
    
    public BytesTrie.Result first(final int n) {
        this.remainingMatchLength_ = -1;
        return this.nextImpl(this.root_, n);
    }
    
    public BytesTrie.Result firstForCodePoint(final int n) {
        return (n <= 65535) ? this.first(n) : (this.first(UTF16.getLeadSurrogate(n)).hasNext() ? this.next(UTF16.getTrailSurrogate(n)) : BytesTrie.Result.NO_MATCH);
    }
    
    public BytesTrie.Result next(final int n) {
        int pos_ = this.pos_;
        if (pos_ < 0) {
            return BytesTrie.Result.NO_MATCH;
        }
        int remainingMatchLength_ = this.remainingMatchLength_;
        if (remainingMatchLength_ < 0) {
            return this.nextImpl(pos_, n);
        }
        if (n == this.chars_.charAt(pos_++)) {
            this.remainingMatchLength_ = --remainingMatchLength_;
            this.pos_ = pos_;
            final char char1;
            return (remainingMatchLength_ < 0 && (char1 = this.chars_.charAt(pos_)) >= '@') ? CharsTrie.valueResults_[char1 >> 15] : BytesTrie.Result.NO_VALUE;
        }
        this.stop();
        return BytesTrie.Result.NO_MATCH;
    }
    
    public BytesTrie.Result nextForCodePoint(final int n) {
        return (n <= 65535) ? this.next(n) : (this.next(UTF16.getLeadSurrogate(n)).hasNext() ? this.next(UTF16.getTrailSurrogate(n)) : BytesTrie.Result.NO_MATCH);
    }
    
    public BytesTrie.Result next(final CharSequence charSequence, int i, final int n) {
        if (i >= n) {
            return this.current();
        }
        int pos_ = this.pos_;
        if (pos_ < 0) {
            return BytesTrie.Result.NO_MATCH;
        }
        int remainingMatchLength_ = this.remainingMatchLength_;
        while (i != n) {
            char c = charSequence.charAt(i++);
            if (remainingMatchLength_ < 0) {
                this.remainingMatchLength_ = remainingMatchLength_;
                int n2 = this.chars_.charAt(pos_++);
                while (true) {
                    if (n2 < 48) {
                        final BytesTrie.Result branchNext = this.branchNext(pos_, n2, c);
                        if (branchNext == BytesTrie.Result.NO_MATCH) {
                            return BytesTrie.Result.NO_MATCH;
                        }
                        if (i == n) {
                            return branchNext;
                        }
                        if (branchNext == BytesTrie.Result.FINAL_VALUE) {
                            this.stop();
                            return BytesTrie.Result.NO_MATCH;
                        }
                        c = charSequence.charAt(i++);
                        pos_ = this.pos_;
                        n2 = this.chars_.charAt(pos_++);
                    }
                    else if (n2 < 64) {
                        remainingMatchLength_ = n2 - 48;
                        if (c != this.chars_.charAt(pos_)) {
                            this.stop();
                            return BytesTrie.Result.NO_MATCH;
                        }
                        ++pos_;
                        --remainingMatchLength_;
                        break;
                    }
                    else {
                        if ((n2 & 0x8000) != 0x0) {
                            this.stop();
                            return BytesTrie.Result.NO_MATCH;
                        }
                        pos_ = skipNodeValue(pos_, n2);
                        n2 &= 0x3F;
                    }
                }
            }
            else {
                if (c != this.chars_.charAt(pos_)) {
                    this.stop();
                    return BytesTrie.Result.NO_MATCH;
                }
                ++pos_;
                --remainingMatchLength_;
            }
        }
        this.remainingMatchLength_ = remainingMatchLength_;
        this.pos_ = pos_;
        final char char1;
        return (remainingMatchLength_ < 0 && (char1 = this.chars_.charAt(pos_)) >= '@') ? CharsTrie.valueResults_[char1 >> 15] : BytesTrie.Result.NO_VALUE;
    }
    
    public int getValue() {
        int pos_ = this.pos_;
        final char char1 = this.chars_.charAt(pos_++);
        assert char1 >= '@';
        return ((char1 & '\u8000') != 0x0) ? readValue(this.chars_, pos_, char1 & '\u7fff') : readNodeValue(this.chars_, pos_, char1);
    }
    
    public long getUniqueValue() {
        final int pos_ = this.pos_;
        if (pos_ < 0) {
            return 0L;
        }
        return findUniqueValue(this.chars_, pos_ + this.remainingMatchLength_ + 1, 0L) << 31 >> 31;
    }
    
    public int getNextChars(final Appendable appendable) {
        int n = this.pos_;
        if (n < 0) {
            return 0;
        }
        if (this.remainingMatchLength_ >= 0) {
            append(appendable, this.chars_.charAt(n));
            return 1;
        }
        int n2 = this.chars_.charAt(n++);
        if (n2 >= 64) {
            if ((n2 & 0x8000) != 0x0) {
                return 0;
            }
            n = skipNodeValue(n, n2);
            n2 &= 0x3F;
        }
        if (n2 < 48) {
            if (n2 == 0) {
                n2 = this.chars_.charAt(n++);
            }
            getNextBranchChars(this.chars_, n, ++n2, appendable);
            return n2;
        }
        append(appendable, this.chars_.charAt(n));
        return 1;
    }
    
    public Iterator iterator() {
        return new Iterator(this.chars_, this.pos_, this.remainingMatchLength_, 0, null);
    }
    
    public Iterator iterator(final int n) {
        return new Iterator(this.chars_, this.pos_, this.remainingMatchLength_, n, null);
    }
    
    public static Iterator iterator(final CharSequence charSequence, final int n, final int n2) {
        return new Iterator(charSequence, n, -1, n2, null);
    }
    
    private void stop() {
        this.pos_ = -1;
    }
    
    private static int readValue(final CharSequence charSequence, final int n, final int n2) {
        int n3;
        if (n2 < 16384) {
            n3 = n2;
        }
        else if (n2 < 32767) {
            n3 = (n2 - 16384 << 16 | charSequence.charAt(n));
        }
        else {
            n3 = (charSequence.charAt(n) << 16 | charSequence.charAt(n + 1));
        }
        return n3;
    }
    
    private static int skipValue(int n, final int n2) {
        if (n2 >= 16384) {
            if (n2 < 32767) {
                ++n;
            }
            else {
                n += 2;
            }
        }
        return n;
    }
    
    private static int skipValue(final CharSequence charSequence, int n) {
        return skipValue(n, charSequence.charAt(n++) & '\u7fff');
    }
    
    private static int readNodeValue(final CharSequence charSequence, final int n, final int n2) {
        assert 64 <= n2 && n2 < 32768;
        int n3;
        if (n2 < 16448) {
            n3 = (n2 >> 6) - 1;
        }
        else if (n2 < 32704) {
            n3 = ((n2 & 0x7FC0) - 16448 << 10 | charSequence.charAt(n));
        }
        else {
            n3 = (charSequence.charAt(n) << 16 | charSequence.charAt(n + 1));
        }
        return n3;
    }
    
    private static int skipNodeValue(int n, final int n2) {
        assert 64 <= n2 && n2 < 32768;
        if (n2 >= 16448) {
            if (n2 < 32704) {
                ++n;
            }
            else {
                n += 2;
            }
        }
        return n;
    }
    
    private static int jumpByDelta(final CharSequence charSequence, int n) {
        int char1 = charSequence.charAt(n++);
        if (char1 >= 64512) {
            if (char1 == 65535) {
                char1 = (charSequence.charAt(n) << 16 | charSequence.charAt(n + 1));
                n += 2;
            }
            else {
                char1 = (char1 - 64512 << 16 | charSequence.charAt(n++));
            }
        }
        return n + char1;
    }
    
    private static int skipDelta(final CharSequence charSequence, int n) {
        final char char1 = charSequence.charAt(n++);
        if (char1 >= '\ufc00') {
            if (char1 == '\uffff') {
                n += 2;
            }
            else {
                ++n;
            }
        }
        return n;
    }
    
    private BytesTrie.Result branchNext(int n, int i, final int j) {
        if (i == 0) {
            i = this.chars_.charAt(n++);
        }
        ++i;
        while (i > 5) {
            if (j < this.chars_.charAt(n++)) {
                i >>= 1;
                n = jumpByDelta(this.chars_, n);
            }
            else {
                i -= i >> 1;
                n = skipDelta(this.chars_, n);
            }
        }
        while (j != this.chars_.charAt(n++)) {
            --i;
            n = skipValue(this.chars_, n);
            if (i <= 1) {
                if (j == this.chars_.charAt(n++)) {
                    this.pos_ = n;
                    final char char1 = this.chars_.charAt(n);
                    return (char1 >= '@') ? CharsTrie.valueResults_[char1 >> 15] : BytesTrie.Result.NO_VALUE;
                }
                this.stop();
                return BytesTrie.Result.NO_MATCH;
            }
        }
        final char char2 = this.chars_.charAt(n);
        BytesTrie.Result final_VALUE;
        if ((char2 & '\u8000') != 0x0) {
            final_VALUE = BytesTrie.Result.FINAL_VALUE;
        }
        else {
            ++n;
            int n2;
            if (char2 < '\u4000') {
                n2 = char2;
            }
            else if (char2 < '\u7fff') {
                n2 = (char2 - '\u4000' << 16 | this.chars_.charAt(n++));
            }
            else {
                n2 = (this.chars_.charAt(n) << 16 | this.chars_.charAt(n + 1));
                n += 2;
            }
            n += n2;
            final char char3 = this.chars_.charAt(n);
            final_VALUE = ((char3 >= '@') ? CharsTrie.valueResults_[char3 >> 15] : BytesTrie.Result.NO_VALUE);
        }
        this.pos_ = n;
        return final_VALUE;
    }
    
    private BytesTrie.Result nextImpl(int skipNodeValue, final int n) {
        int i = this.chars_.charAt(skipNodeValue++);
        while (i >= 48) {
            if (i < 64) {
                int n2 = i - 48;
                if (n == this.chars_.charAt(skipNodeValue++)) {
                    this.remainingMatchLength_ = --n2;
                    this.pos_ = skipNodeValue;
                    final char char1;
                    return (n2 < 0 && (char1 = this.chars_.charAt(skipNodeValue)) >= '@') ? CharsTrie.valueResults_[char1 >> 15] : BytesTrie.Result.NO_VALUE;
                }
            }
            else if ((i & 0x8000) == 0x0) {
                skipNodeValue = skipNodeValue(skipNodeValue, i);
                i &= 0x3F;
                continue;
            }
            this.stop();
            return BytesTrie.Result.NO_MATCH;
        }
        return this.branchNext(skipNodeValue, i, n);
    }
    
    private static long findUniqueValueFromBranch(final CharSequence charSequence, int n, int i, long n2) {
        while (i > 5) {
            ++n;
            n2 = findUniqueValueFromBranch(charSequence, jumpByDelta(charSequence, n), i >> 1, n2);
            if (n2 == 0L) {
                return 0L;
            }
            i -= i >> 1;
            n = skipDelta(charSequence, n);
        }
        do {
            ++n;
            final char char1 = charSequence.charAt(n++);
            final boolean b = (char1 & '\u8000') != 0x0;
            final int n3 = char1 & '\u7fff';
            final int value = readValue(charSequence, n, n3);
            n = skipValue(n, n3);
            if (b) {
                if (n2 != 0L) {
                    if (value != (int)(n2 >> 1)) {
                        return 0L;
                    }
                    continue;
                }
                else {
                    n2 = ((long)value << 1 | 0x1L);
                }
            }
            else {
                n2 = findUniqueValue(charSequence, n + value, n2);
                if (n2 == 0L) {
                    return 0L;
                }
                continue;
            }
        } while (--i > 1);
        return (long)(n + 1) << 33 | (n2 & 0x1FFFFFFFFL);
    }
    
    private static long findUniqueValue(final CharSequence charSequence, int skipNodeValue, long uniqueValueFromBranch) {
        int n = charSequence.charAt(skipNodeValue++);
        while (true) {
            if (n < 48) {
                if (n == 0) {
                    n = charSequence.charAt(skipNodeValue++);
                }
                uniqueValueFromBranch = findUniqueValueFromBranch(charSequence, skipNodeValue, n + 1, uniqueValueFromBranch);
                if (uniqueValueFromBranch == 0L) {
                    return 0L;
                }
                skipNodeValue = (int)(uniqueValueFromBranch >>> 33);
                n = charSequence.charAt(skipNodeValue++);
            }
            else if (n < 64) {
                skipNodeValue += n - 48 + 1;
                n = charSequence.charAt(skipNodeValue++);
            }
            else {
                final boolean b = (n & 0x8000) != 0x0;
                int n2;
                if (b) {
                    n2 = readValue(charSequence, skipNodeValue, n & 0x7FFF);
                }
                else {
                    n2 = readNodeValue(charSequence, skipNodeValue, n);
                }
                if (uniqueValueFromBranch != 0L) {
                    if (n2 != (int)(uniqueValueFromBranch >> 1)) {
                        return 0L;
                    }
                }
                else {
                    uniqueValueFromBranch = ((long)n2 << 1 | 0x1L);
                }
                if (b) {
                    return uniqueValueFromBranch;
                }
                skipNodeValue = skipNodeValue(skipNodeValue, n);
                n &= 0x3F;
            }
        }
    }
    
    private static void getNextBranchChars(final CharSequence charSequence, int n, int i, final Appendable appendable) {
        while (i > 5) {
            ++n;
            getNextBranchChars(charSequence, jumpByDelta(charSequence, n), i >> 1, appendable);
            i -= i >> 1;
            n = skipDelta(charSequence, n);
        }
        do {
            append(appendable, charSequence.charAt(n++));
            n = skipValue(charSequence, n);
        } while (--i > 1);
        append(appendable, charSequence.charAt(n));
    }
    
    private static void append(final Appendable appendable, final int n) {
        appendable.append((char)n);
    }
    
    public java.util.Iterator iterator() {
        return this.iterator();
    }
    
    static int access$500(final int n, final int n2) {
        return skipNodeValue(n, n2);
    }
    
    static int access$600(final CharSequence charSequence, final int n, final int n2) {
        return readValue(charSequence, n, n2);
    }
    
    static int access$700(final CharSequence charSequence, final int n, final int n2) {
        return readNodeValue(charSequence, n, n2);
    }
    
    static int access$800(final CharSequence charSequence, final int n) {
        return skipDelta(charSequence, n);
    }
    
    static int access$900(final CharSequence charSequence, final int n) {
        return jumpByDelta(charSequence, n);
    }
    
    static int access$1000(final int n, final int n2) {
        return skipValue(n, n2);
    }
    
    static {
        $assertionsDisabled = !CharsTrie.class.desiredAssertionStatus();
        CharsTrie.valueResults_ = new BytesTrie.Result[] { BytesTrie.Result.INTERMEDIATE_VALUE, BytesTrie.Result.FINAL_VALUE };
    }
    
    public static final class Iterator implements java.util.Iterator
    {
        private CharSequence chars_;
        private int pos_;
        private int initialPos_;
        private int remainingMatchLength_;
        private int initialRemainingMatchLength_;
        private boolean skipValue_;
        private StringBuilder str_;
        private int maxLength_;
        private Entry entry_;
        private ArrayList stack_;
        
        private Iterator(final CharSequence chars_, final int n, final int n2, final int maxLength_) {
            this.str_ = new StringBuilder();
            this.entry_ = new Entry(null);
            this.stack_ = new ArrayList();
            this.chars_ = chars_;
            this.initialPos_ = n;
            this.pos_ = n;
            this.initialRemainingMatchLength_ = n2;
            this.remainingMatchLength_ = n2;
            this.maxLength_ = maxLength_;
            int n3 = this.remainingMatchLength_;
            if (n3 >= 0) {
                ++n3;
                if (this.maxLength_ > 0 && n3 > this.maxLength_) {
                    n3 = this.maxLength_;
                }
                this.str_.append(this.chars_, this.pos_, this.pos_ + n3);
                this.pos_ += n3;
                this.remainingMatchLength_ -= n3;
            }
        }
        
        public Iterator reset() {
            this.pos_ = this.initialPos_;
            this.remainingMatchLength_ = this.initialRemainingMatchLength_;
            this.skipValue_ = false;
            int maxLength_ = this.remainingMatchLength_ + 1;
            if (this.maxLength_ > 0 && maxLength_ > this.maxLength_) {
                maxLength_ = this.maxLength_;
            }
            this.str_.setLength(maxLength_);
            this.pos_ += maxLength_;
            this.remainingMatchLength_ -= maxLength_;
            this.stack_.clear();
            return this;
        }
        
        public boolean hasNext() {
            return this.pos_ >= 0 || !this.stack_.isEmpty();
        }
        
        public Entry next() {
            int n = this.pos_;
            if (n < 0) {
                if (this.stack_.isEmpty()) {
                    throw new NoSuchElementException();
                }
                final long longValue = this.stack_.remove(this.stack_.size() - 1);
                final int n2 = (int)longValue;
                n = (int)(longValue >> 32);
                this.str_.setLength(n2 & 0xFFFF);
                final int n3 = n2 >>> 16;
                if (n3 > 1) {
                    n = this.branchNext(n, n3);
                    if (n < 0) {
                        return this.entry_;
                    }
                }
                else {
                    this.str_.append(this.chars_.charAt(n++));
                }
            }
            if (this.remainingMatchLength_ >= 0) {
                return this.truncateAndStop();
            }
            while (true) {
                int n4 = this.chars_.charAt(n++);
                if (n4 >= 64) {
                    if (!this.skipValue_) {
                        final boolean b = (n4 & 0x8000) != 0x0;
                        if (b) {
                            this.entry_.value = CharsTrie.access$600(this.chars_, n, n4 & 0x7FFF);
                        }
                        else {
                            this.entry_.value = CharsTrie.access$700(this.chars_, n, n4);
                        }
                        if (b || (this.maxLength_ > 0 && this.str_.length() == this.maxLength_)) {
                            this.pos_ = -1;
                        }
                        else {
                            this.pos_ = n - 1;
                            this.skipValue_ = true;
                        }
                        this.entry_.chars = this.str_;
                        return this.entry_;
                    }
                    n = CharsTrie.access$500(n, n4);
                    n4 &= 0x3F;
                    this.skipValue_ = false;
                }
                if (this.maxLength_ > 0 && this.str_.length() == this.maxLength_) {
                    return this.truncateAndStop();
                }
                if (n4 < 48) {
                    if (n4 == 0) {
                        n4 = this.chars_.charAt(n++);
                    }
                    n = this.branchNext(n, n4 + 1);
                    if (n < 0) {
                        return this.entry_;
                    }
                    continue;
                }
                else {
                    final int n5 = n4 - 48 + 1;
                    if (this.maxLength_ > 0 && this.str_.length() + n5 > this.maxLength_) {
                        this.str_.append(this.chars_, n, n + this.maxLength_ - this.str_.length());
                        return this.truncateAndStop();
                    }
                    this.str_.append(this.chars_, n, n + n5);
                    n += n5;
                }
            }
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        private Entry truncateAndStop() {
            this.pos_ = -1;
            this.entry_.chars = this.str_;
            this.entry_.value = -1;
            return this.entry_;
        }
        
        private int branchNext(int n, int i) {
            while (i > 5) {
                ++n;
                this.stack_.add((long)CharsTrie.access$800(this.chars_, n) << 32 | (long)(i - (i >> 1) << 16) | (long)this.str_.length());
                i >>= 1;
                n = CharsTrie.access$900(this.chars_, n);
            }
            final char char1 = this.chars_.charAt(n++);
            final char char2 = this.chars_.charAt(n++);
            final boolean b = (char2 & '\u8000') != 0x0;
            final int n2;
            final int access$600 = CharsTrie.access$600(this.chars_, n, n2 = (char2 & '\u7fff'));
            n = CharsTrie.access$1000(n, n2);
            this.stack_.add((long)n << 32 | (long)(i - 1 << 16) | (long)this.str_.length());
            this.str_.append(char1);
            if (b) {
                this.pos_ = -1;
                this.entry_.chars = this.str_;
                this.entry_.value = access$600;
                return -1;
            }
            return n + access$600;
        }
        
        public Object next() {
            return this.next();
        }
        
        Iterator(final CharSequence charSequence, final int n, final int n2, final int n3, final CharsTrie$1 object) {
            this(charSequence, n, n2, n3);
        }
    }
    
    public static final class Entry
    {
        public CharSequence chars;
        public int value;
        
        private Entry() {
        }
        
        Entry(final CharsTrie$1 object) {
            this();
        }
    }
    
    public static final class State
    {
        private CharSequence chars;
        private int root;
        private int pos;
        private int remainingMatchLength;
        
        static CharSequence access$002(final State state, final CharSequence chars) {
            return state.chars = chars;
        }
        
        static int access$102(final State state, final int root) {
            return state.root = root;
        }
        
        static int access$202(final State state, final int pos) {
            return state.pos = pos;
        }
        
        static int access$302(final State state, final int remainingMatchLength) {
            return state.remainingMatchLength = remainingMatchLength;
        }
        
        static CharSequence access$000(final State state) {
            return state.chars;
        }
        
        static int access$100(final State state) {
            return state.root;
        }
        
        static int access$200(final State state) {
            return state.pos;
        }
        
        static int access$300(final State state) {
            return state.remainingMatchLength;
        }
    }
}
