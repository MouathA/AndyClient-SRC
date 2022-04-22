package com.ibm.icu.util;

import java.util.*;
import java.nio.*;

public final class BytesTrie implements Cloneable, Iterable
{
    private static Result[] valueResults_;
    static final int kMaxBranchLinearSubNodeLength = 5;
    static final int kMinLinearMatch = 16;
    static final int kMaxLinearMatchLength = 16;
    static final int kMinValueLead = 32;
    private static final int kValueIsFinal = 1;
    static final int kMinOneByteValueLead = 16;
    static final int kMaxOneByteValue = 64;
    static final int kMinTwoByteValueLead = 81;
    static final int kMaxTwoByteValue = 6911;
    static final int kMinThreeByteValueLead = 108;
    static final int kFourByteValueLead = 126;
    static final int kMaxThreeByteValue = 1179647;
    static final int kFiveByteValueLead = 127;
    static final int kMaxOneByteDelta = 191;
    static final int kMinTwoByteDeltaLead = 192;
    static final int kMinThreeByteDeltaLead = 240;
    static final int kFourByteDeltaLead = 254;
    static final int kFiveByteDeltaLead = 255;
    static final int kMaxTwoByteDelta = 12287;
    static final int kMaxThreeByteDelta = 917503;
    private byte[] bytes_;
    private int root_;
    private int pos_;
    private int remainingMatchLength_;
    static final boolean $assertionsDisabled;
    
    public BytesTrie(final byte[] bytes_, final int n) {
        this.bytes_ = bytes_;
        this.root_ = n;
        this.pos_ = n;
        this.remainingMatchLength_ = -1;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public BytesTrie reset() {
        this.pos_ = this.root_;
        this.remainingMatchLength_ = -1;
        return this;
    }
    
    public BytesTrie saveState(final State state) {
        State.access$002(state, this.bytes_);
        State.access$102(state, this.root_);
        State.access$202(state, this.pos_);
        State.access$302(state, this.remainingMatchLength_);
        return this;
    }
    
    public BytesTrie resetToState(final State state) {
        if (this.bytes_ == State.access$000(state) && this.bytes_ != null && this.root_ == State.access$100(state)) {
            this.pos_ = State.access$200(state);
            this.remainingMatchLength_ = State.access$300(state);
            return this;
        }
        throw new IllegalArgumentException("incompatible trie state");
    }
    
    public Result current() {
        final int pos_ = this.pos_;
        if (pos_ < 0) {
            return Result.NO_MATCH;
        }
        final int n;
        return (this.remainingMatchLength_ < 0 && (n = (this.bytes_[pos_] & 0xFF)) >= 32) ? BytesTrie.valueResults_[n & 0x1] : Result.NO_VALUE;
    }
    
    public Result first(int n) {
        this.remainingMatchLength_ = -1;
        if (n < 0) {
            n += 256;
        }
        return this.nextImpl(this.root_, n);
    }
    
    public Result next(int n) {
        int pos_ = this.pos_;
        if (pos_ < 0) {
            return Result.NO_MATCH;
        }
        if (n < 0) {
            n += 256;
        }
        int remainingMatchLength_ = this.remainingMatchLength_;
        if (remainingMatchLength_ < 0) {
            return this.nextImpl(pos_, n);
        }
        if (n == (this.bytes_[pos_++] & 0xFF)) {
            this.remainingMatchLength_ = --remainingMatchLength_;
            this.pos_ = pos_;
            final int n2;
            return (remainingMatchLength_ < 0 && (n2 = (this.bytes_[pos_] & 0xFF)) >= 32) ? BytesTrie.valueResults_[n2 & 0x1] : Result.NO_VALUE;
        }
        this.stop();
        return Result.NO_MATCH;
    }
    
    public Result next(final byte[] array, int i, final int n) {
        if (i >= n) {
            return this.current();
        }
        int pos_ = this.pos_;
        if (pos_ < 0) {
            return Result.NO_MATCH;
        }
        int remainingMatchLength_ = this.remainingMatchLength_;
        while (i != n) {
            byte b = array[i++];
            if (remainingMatchLength_ < 0) {
                this.remainingMatchLength_ = remainingMatchLength_;
                while (true) {
                    final int n2 = this.bytes_[pos_++] & 0xFF;
                    if (n2 < 16) {
                        final Result branchNext = this.branchNext(pos_, n2, b & 0xFF);
                        if (branchNext == Result.NO_MATCH) {
                            return Result.NO_MATCH;
                        }
                        if (i == n) {
                            return branchNext;
                        }
                        if (branchNext == Result.FINAL_VALUE) {
                            this.stop();
                            return Result.NO_MATCH;
                        }
                        b = array[i++];
                        pos_ = this.pos_;
                    }
                    else if (n2 < 32) {
                        remainingMatchLength_ = n2 - 16;
                        if (b != this.bytes_[pos_]) {
                            this.stop();
                            return Result.NO_MATCH;
                        }
                        ++pos_;
                        --remainingMatchLength_;
                        break;
                    }
                    else {
                        if ((n2 & 0x1) != 0x0) {
                            this.stop();
                            return Result.NO_MATCH;
                        }
                        pos_ = skipValue(pos_, n2);
                        assert (this.bytes_[pos_] & 0xFF) < 32;
                        continue;
                    }
                }
            }
            else {
                if (b != this.bytes_[pos_]) {
                    this.stop();
                    return Result.NO_MATCH;
                }
                ++pos_;
                --remainingMatchLength_;
            }
        }
        this.remainingMatchLength_ = remainingMatchLength_;
        this.pos_ = pos_;
        final int n3;
        return (remainingMatchLength_ < 0 && (n3 = (this.bytes_[pos_] & 0xFF)) >= 32) ? BytesTrie.valueResults_[n3 & 0x1] : Result.NO_VALUE;
    }
    
    public int getValue() {
        int pos_ = this.pos_;
        final int n = this.bytes_[pos_++] & 0xFF;
        assert n >= 32;
        return readValue(this.bytes_, pos_, n >> 1);
    }
    
    public long getUniqueValue() {
        final int pos_ = this.pos_;
        if (pos_ < 0) {
            return 0L;
        }
        return findUniqueValue(this.bytes_, pos_ + this.remainingMatchLength_ + 1, 0L) << 31 >> 31;
    }
    
    public int getNextBytes(final Appendable appendable) {
        int n = this.pos_;
        if (n < 0) {
            return 0;
        }
        if (this.remainingMatchLength_ >= 0) {
            append(appendable, this.bytes_[n] & 0xFF);
            return 1;
        }
        int n2 = this.bytes_[n++] & 0xFF;
        if (n2 >= 32) {
            if ((n2 & 0x1) != 0x0) {
                return 0;
            }
            n = skipValue(n, n2);
            n2 = (this.bytes_[n++] & 0xFF);
            assert n2 < 32;
        }
        if (n2 < 16) {
            if (n2 == 0) {
                n2 = (this.bytes_[n++] & 0xFF);
            }
            getNextBranchBytes(this.bytes_, n, ++n2, appendable);
            return n2;
        }
        append(appendable, this.bytes_[n] & 0xFF);
        return 1;
    }
    
    public Iterator iterator() {
        return new Iterator(this.bytes_, this.pos_, this.remainingMatchLength_, 0, null);
    }
    
    public Iterator iterator(final int n) {
        return new Iterator(this.bytes_, this.pos_, this.remainingMatchLength_, n, null);
    }
    
    public static Iterator iterator(final byte[] array, final int n, final int n2) {
        return new Iterator(array, n, -1, n2, null);
    }
    
    private void stop() {
        this.pos_ = -1;
    }
    
    private static int readValue(final byte[] array, final int n, final int n2) {
        int n3;
        if (n2 < 81) {
            n3 = n2 - 16;
        }
        else if (n2 < 108) {
            n3 = (n2 - 81 << 8 | (array[n] & 0xFF));
        }
        else if (n2 < 126) {
            n3 = (n2 - 108 << 16 | (array[n] & 0xFF) << 8 | (array[n + 1] & 0xFF));
        }
        else if (n2 == 126) {
            n3 = ((array[n] & 0xFF) << 16 | (array[n + 1] & 0xFF) << 8 | (array[n + 2] & 0xFF));
        }
        else {
            n3 = (array[n] << 24 | (array[n + 1] & 0xFF) << 16 | (array[n + 2] & 0xFF) << 8 | (array[n + 3] & 0xFF));
        }
        return n3;
    }
    
    private static int skipValue(int n, final int n2) {
        assert n2 >= 32;
        if (n2 >= 162) {
            if (n2 < 216) {
                ++n;
            }
            else if (n2 < 252) {
                n += 2;
            }
            else {
                n += 3 + (n2 >> 1 & 0x1);
            }
        }
        return n;
    }
    
    private static int skipValue(final byte[] array, int n) {
        return skipValue(n, array[n++] & 0xFF);
    }
    
    private static int jumpByDelta(final byte[] array, int n) {
        int n2 = array[n++] & 0xFF;
        if (n2 >= 192) {
            if (n2 < 240) {
                n2 = (n2 - 192 << 8 | (array[n++] & 0xFF));
            }
            else if (n2 < 254) {
                n2 = (n2 - 240 << 16 | (array[n] & 0xFF) << 8 | (array[n + 1] & 0xFF));
                n += 2;
            }
            else if (n2 == 254) {
                n2 = ((array[n] & 0xFF) << 16 | (array[n + 1] & 0xFF) << 8 | (array[n + 2] & 0xFF));
                n += 3;
            }
            else {
                n2 = (array[n] << 24 | (array[n + 1] & 0xFF) << 16 | (array[n + 2] & 0xFF) << 8 | (array[n + 3] & 0xFF));
                n += 4;
            }
        }
        return n + n2;
    }
    
    private static int skipDelta(final byte[] array, int n) {
        final int n2 = array[n++] & 0xFF;
        if (n2 >= 192) {
            if (n2 < 240) {
                ++n;
            }
            else if (n2 < 254) {
                n += 2;
            }
            else {
                n += 3 + (n2 & 0x1);
            }
        }
        return n;
    }
    
    private Result branchNext(int n, int i, final int j) {
        if (i == 0) {
            i = (this.bytes_[n++] & 0xFF);
        }
        ++i;
        while (i > 5) {
            if (j < (this.bytes_[n++] & 0xFF)) {
                i >>= 1;
                n = jumpByDelta(this.bytes_, n);
            }
            else {
                i -= i >> 1;
                n = skipDelta(this.bytes_, n);
            }
        }
        while (j != (this.bytes_[n++] & 0xFF)) {
            --i;
            n = skipValue(this.bytes_, n);
            if (i <= 1) {
                if (j == (this.bytes_[n++] & 0xFF)) {
                    this.pos_ = n;
                    final int n2 = this.bytes_[n] & 0xFF;
                    return (n2 >= 32) ? BytesTrie.valueResults_[n2 & 0x1] : Result.NO_VALUE;
                }
                this.stop();
                return Result.NO_MATCH;
            }
        }
        final int n3 = this.bytes_[n] & 0xFF;
        assert n3 >= 32;
        Result final_VALUE;
        if ((n3 & 0x1) != 0x0) {
            final_VALUE = Result.FINAL_VALUE;
        }
        else {
            ++n;
            final int n4 = n3 >> 1;
            int n5;
            if (n4 < 81) {
                n5 = n4 - 16;
            }
            else if (n4 < 108) {
                n5 = (n4 - 81 << 8 | (this.bytes_[n++] & 0xFF));
            }
            else if (n4 < 126) {
                n5 = (n4 - 108 << 16 | (this.bytes_[n] & 0xFF) << 8 | (this.bytes_[n + 1] & 0xFF));
                n += 2;
            }
            else if (n4 == 126) {
                n5 = ((this.bytes_[n] & 0xFF) << 16 | (this.bytes_[n + 1] & 0xFF) << 8 | (this.bytes_[n + 2] & 0xFF));
                n += 3;
            }
            else {
                n5 = (this.bytes_[n] << 24 | (this.bytes_[n + 1] & 0xFF) << 16 | (this.bytes_[n + 2] & 0xFF) << 8 | (this.bytes_[n + 3] & 0xFF));
                n += 4;
            }
            n += n5;
            final int n6 = this.bytes_[n] & 0xFF;
            final_VALUE = ((n6 >= 32) ? BytesTrie.valueResults_[n6 & 0x1] : Result.NO_VALUE);
        }
        this.pos_ = n;
        return final_VALUE;
    }
    
    private Result nextImpl(int skipValue, final int n) {
        while (true) {
            final int n2 = this.bytes_[skipValue++] & 0xFF;
            if (n2 < 16) {
                return this.branchNext(skipValue, n2, n);
            }
            if (n2 < 32) {
                int n3 = n2 - 16;
                if (n == (this.bytes_[skipValue++] & 0xFF)) {
                    this.remainingMatchLength_ = --n3;
                    this.pos_ = skipValue;
                    final int n4;
                    return (n3 < 0 && (n4 = (this.bytes_[skipValue] & 0xFF)) >= 32) ? BytesTrie.valueResults_[n4 & 0x1] : Result.NO_VALUE;
                }
                break;
            }
            else {
                if ((n2 & 0x1) != 0x0) {
                    break;
                }
                skipValue = skipValue(skipValue, n2);
                assert (this.bytes_[skipValue] & 0xFF) < 32;
                continue;
            }
        }
        this.stop();
        return Result.NO_MATCH;
    }
    
    private static long findUniqueValueFromBranch(final byte[] array, int n, int i, long n2) {
        while (i > 5) {
            ++n;
            n2 = findUniqueValueFromBranch(array, jumpByDelta(array, n), i >> 1, n2);
            if (n2 == 0L) {
                return 0L;
            }
            i -= i >> 1;
            n = skipDelta(array, n);
        }
        do {
            ++n;
            final int n3 = array[n++] & 0xFF;
            final boolean b = (n3 & 0x1) != 0x0;
            final int value = readValue(array, n, n3 >> 1);
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
                n2 = findUniqueValue(array, n + value, n2);
                if (n2 == 0L) {
                    return 0L;
                }
                continue;
            }
        } while (--i > 1);
        return (long)(n + 1) << 33 | (n2 & 0x1FFFFFFFFL);
    }
    
    private static long findUniqueValue(final byte[] array, int skipValue, long uniqueValueFromBranch) {
        while (true) {
            int n = array[skipValue++] & 0xFF;
            if (n < 16) {
                if (n == 0) {
                    n = (array[skipValue++] & 0xFF);
                }
                uniqueValueFromBranch = findUniqueValueFromBranch(array, skipValue, n + 1, uniqueValueFromBranch);
                if (uniqueValueFromBranch == 0L) {
                    return 0L;
                }
                skipValue = (int)(uniqueValueFromBranch >>> 33);
            }
            else if (n < 32) {
                skipValue += n - 16 + 1;
            }
            else {
                final boolean b = (n & 0x1) != 0x0;
                final int value = readValue(array, skipValue, n >> 1);
                if (uniqueValueFromBranch != 0L) {
                    if (value != (int)(uniqueValueFromBranch >> 1)) {
                        return 0L;
                    }
                }
                else {
                    uniqueValueFromBranch = ((long)value << 1 | 0x1L);
                }
                if (b) {
                    return uniqueValueFromBranch;
                }
                skipValue = skipValue(skipValue, n);
            }
        }
    }
    
    private static void getNextBranchBytes(final byte[] array, int n, int i, final Appendable appendable) {
        while (i > 5) {
            ++n;
            getNextBranchBytes(array, jumpByDelta(array, n), i >> 1, appendable);
            i -= i >> 1;
            n = skipDelta(array, n);
        }
        do {
            append(appendable, array[n++] & 0xFF);
            n = skipValue(array, n);
        } while (--i > 1);
        append(appendable, array[n] & 0xFF);
    }
    
    private static void append(final Appendable appendable, final int n) {
        appendable.append((char)n);
    }
    
    public java.util.Iterator iterator() {
        return this.iterator();
    }
    
    static int access$900(final byte[] array, final int n, final int n2) {
        return readValue(array, n, n2);
    }
    
    static int access$1100(final int n, final int n2) {
        return skipValue(n, n2);
    }
    
    static int access$1200(final byte[] array, final int n) {
        return skipDelta(array, n);
    }
    
    static int access$1300(final byte[] array, final int n) {
        return jumpByDelta(array, n);
    }
    
    static {
        $assertionsDisabled = !BytesTrie.class.desiredAssertionStatus();
        BytesTrie.valueResults_ = new Result[] { Result.INTERMEDIATE_VALUE, Result.FINAL_VALUE };
    }
    
    public static final class Iterator implements java.util.Iterator
    {
        private byte[] bytes_;
        private int pos_;
        private int initialPos_;
        private int remainingMatchLength_;
        private int initialRemainingMatchLength_;
        private int maxLength_;
        private Entry entry_;
        private ArrayList stack_;
        
        private Iterator(final byte[] bytes_, final int n, final int n2, final int maxLength_) {
            this.stack_ = new ArrayList();
            this.bytes_ = bytes_;
            this.initialPos_ = n;
            this.pos_ = n;
            this.initialRemainingMatchLength_ = n2;
            this.remainingMatchLength_ = n2;
            this.maxLength_ = maxLength_;
            this.entry_ = new Entry((this.maxLength_ != 0) ? this.maxLength_ : 32, null);
            int n3 = this.remainingMatchLength_;
            if (n3 >= 0) {
                ++n3;
                if (this.maxLength_ > 0 && n3 > this.maxLength_) {
                    n3 = this.maxLength_;
                }
                Entry.access$600(this.entry_, this.bytes_, this.pos_, n3);
                this.pos_ += n3;
                this.remainingMatchLength_ -= n3;
            }
        }
        
        public Iterator reset() {
            this.pos_ = this.initialPos_;
            this.remainingMatchLength_ = this.initialRemainingMatchLength_;
            int maxLength_ = this.remainingMatchLength_ + 1;
            if (this.maxLength_ > 0 && maxLength_ > this.maxLength_) {
                maxLength_ = this.maxLength_;
            }
            Entry.access$700(this.entry_, maxLength_);
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
                Entry.access$700(this.entry_, n2 & 0xFFFF);
                final int n3 = n2 >>> 16;
                if (n3 > 1) {
                    n = this.branchNext(n, n3);
                    if (n < 0) {
                        return this.entry_;
                    }
                }
                else {
                    Entry.access$800(this.entry_, this.bytes_[n++]);
                }
            }
            if (this.remainingMatchLength_ >= 0) {
                return this.truncateAndStop();
            }
            while (true) {
                int n4 = this.bytes_[n++] & 0xFF;
                if (n4 >= 32) {
                    final boolean b = (n4 & 0x1) != 0x0;
                    this.entry_.value = BytesTrie.access$900(this.bytes_, n, n4 >> 1);
                    if (b || (this.maxLength_ > 0 && Entry.access$1000(this.entry_) == this.maxLength_)) {
                        this.pos_ = -1;
                    }
                    else {
                        this.pos_ = BytesTrie.access$1100(n, n4);
                    }
                    return this.entry_;
                }
                if (this.maxLength_ > 0 && Entry.access$1000(this.entry_) == this.maxLength_) {
                    return this.truncateAndStop();
                }
                if (n4 < 16) {
                    if (n4 == 0) {
                        n4 = (this.bytes_[n++] & 0xFF);
                    }
                    n = this.branchNext(n, n4 + 1);
                    if (n < 0) {
                        return this.entry_;
                    }
                    continue;
                }
                else {
                    final int n5 = n4 - 16 + 1;
                    if (this.maxLength_ > 0 && Entry.access$1000(this.entry_) + n5 > this.maxLength_) {
                        Entry.access$600(this.entry_, this.bytes_, n, this.maxLength_ - Entry.access$1000(this.entry_));
                        return this.truncateAndStop();
                    }
                    Entry.access$600(this.entry_, this.bytes_, n, n5);
                    n += n5;
                }
            }
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        private Entry truncateAndStop() {
            this.pos_ = -1;
            this.entry_.value = -1;
            return this.entry_;
        }
        
        private int branchNext(int n, int i) {
            while (i > 5) {
                ++n;
                this.stack_.add((long)BytesTrie.access$1200(this.bytes_, n) << 32 | (long)(i - (i >> 1) << 16) | (long)Entry.access$1000(this.entry_));
                i >>= 1;
                n = BytesTrie.access$1300(this.bytes_, n);
            }
            final byte b = this.bytes_[n++];
            final int n2 = this.bytes_[n++] & 0xFF;
            final boolean b2 = (n2 & 0x1) != 0x0;
            final int access$900 = BytesTrie.access$900(this.bytes_, n, n2 >> 1);
            n = BytesTrie.access$1100(n, n2);
            this.stack_.add((long)n << 32 | (long)(i - 1 << 16) | (long)Entry.access$1000(this.entry_));
            Entry.access$800(this.entry_, b);
            if (b2) {
                this.pos_ = -1;
                this.entry_.value = access$900;
                return -1;
            }
            return n + access$900;
        }
        
        public Object next() {
            return this.next();
        }
        
        Iterator(final byte[] array, final int n, final int n2, final int n3, final BytesTrie$1 object) {
            this(array, n, n2, n3);
        }
    }
    
    public static final class Entry
    {
        public int value;
        private byte[] bytes;
        private int length;
        
        private Entry(final int n) {
            this.bytes = new byte[n];
        }
        
        public int bytesLength() {
            return this.length;
        }
        
        public byte byteAt(final int n) {
            return this.bytes[n];
        }
        
        public void copyBytesTo(final byte[] array, final int n) {
            System.arraycopy(this.bytes, 0, array, n, this.length);
        }
        
        public ByteBuffer bytesAsByteBuffer() {
            return ByteBuffer.wrap(this.bytes, 0, this.length).asReadOnlyBuffer();
        }
        
        private void ensureCapacity(final int n) {
            if (this.bytes.length < n) {
                final byte[] bytes = new byte[Math.min(2 * this.bytes.length, 2 * n)];
                System.arraycopy(this.bytes, 0, bytes, 0, this.length);
                this.bytes = bytes;
            }
        }
        
        private void append(final byte b) {
            this.ensureCapacity(this.length + 1);
            this.bytes[this.length++] = b;
        }
        
        private void append(final byte[] array, final int n, final int n2) {
            this.ensureCapacity(this.length + n2);
            System.arraycopy(array, n, this.bytes, this.length, n2);
            this.length += n2;
        }
        
        private void truncateString(final int length) {
            this.length = length;
        }
        
        Entry(final int n, final BytesTrie$1 object) {
            this(n);
        }
        
        static void access$600(final Entry entry, final byte[] array, final int n, final int n2) {
            entry.append(array, n, n2);
        }
        
        static void access$700(final Entry entry, final int n) {
            entry.truncateString(n);
        }
        
        static void access$800(final Entry entry, final byte b) {
            entry.append(b);
        }
        
        static int access$1000(final Entry entry) {
            return entry.length;
        }
    }
    
    public enum Result
    {
        NO_MATCH("NO_MATCH", 0), 
        NO_VALUE("NO_VALUE", 1), 
        FINAL_VALUE("FINAL_VALUE", 2), 
        INTERMEDIATE_VALUE("INTERMEDIATE_VALUE", 3);
        
        private static final Result[] $VALUES;
        
        private Result(final String s, final int n) {
        }
        
        public boolean matches() {
            return this != Result.NO_MATCH;
        }
        
        public boolean hasValue() {
            return this.ordinal() >= 2;
        }
        
        public boolean hasNext() {
            return (this.ordinal() & 0x1) != 0x0;
        }
        
        static {
            $VALUES = new Result[] { Result.NO_MATCH, Result.NO_VALUE, Result.FINAL_VALUE, Result.INTERMEDIATE_VALUE };
        }
    }
    
    public static final class State
    {
        private byte[] bytes;
        private int root;
        private int pos;
        private int remainingMatchLength;
        
        static byte[] access$002(final State state, final byte[] bytes) {
            return state.bytes = bytes;
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
        
        static byte[] access$000(final State state) {
            return state.bytes;
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
