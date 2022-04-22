package com.ibm.icu.text;

import java.text.*;
import java.nio.*;
import com.ibm.icu.lang.*;
import com.ibm.icu.impl.*;

public final class Normalizer implements Cloneable
{
    private UCharacterIterator text;
    private Normalizer2 norm2;
    private Mode mode;
    private int options;
    private int currentIndex;
    private int nextIndex;
    private StringBuilder buffer;
    private int bufferPos;
    public static final int UNICODE_3_2 = 32;
    public static final int DONE = -1;
    public static final Mode NONE;
    public static final Mode NFD;
    public static final Mode NFKD;
    public static final Mode NFC;
    public static final Mode DEFAULT;
    public static final Mode NFKC;
    public static final Mode FCD;
    @Deprecated
    public static final Mode NO_OP;
    @Deprecated
    public static final Mode COMPOSE;
    @Deprecated
    public static final Mode COMPOSE_COMPAT;
    @Deprecated
    public static final Mode DECOMP;
    @Deprecated
    public static final Mode DECOMP_COMPAT;
    @Deprecated
    public static final int IGNORE_HANGUL = 1;
    public static final QuickCheckResult NO;
    public static final QuickCheckResult YES;
    public static final QuickCheckResult MAYBE;
    public static final int FOLD_CASE_DEFAULT = 0;
    public static final int INPUT_IS_FCD = 131072;
    public static final int COMPARE_IGNORE_CASE = 65536;
    public static final int COMPARE_CODE_POINT_ORDER = 32768;
    public static final int FOLD_CASE_EXCLUDE_SPECIAL_I = 1;
    public static final int COMPARE_NORM_OPTIONS_SHIFT = 20;
    private static final int COMPARE_EQUIV = 524288;
    
    public Normalizer(final String s, final Mode mode, final int options) {
        this.text = UCharacterIterator.getInstance(s);
        this.mode = mode;
        this.options = options;
        this.norm2 = mode.getNormalizer2(options);
        this.buffer = new StringBuilder();
    }
    
    public Normalizer(final CharacterIterator characterIterator, final Mode mode, final int options) {
        this.text = UCharacterIterator.getInstance((CharacterIterator)characterIterator.clone());
        this.mode = mode;
        this.options = options;
        this.norm2 = mode.getNormalizer2(options);
        this.buffer = new StringBuilder();
    }
    
    public Normalizer(final UCharacterIterator uCharacterIterator, final Mode mode, final int options) {
        this.text = (UCharacterIterator)uCharacterIterator.clone();
        this.mode = mode;
        this.options = options;
        this.norm2 = mode.getNormalizer2(options);
        this.buffer = new StringBuilder();
    }
    
    public Object clone() {
        final Normalizer normalizer = (Normalizer)super.clone();
        normalizer.text = (UCharacterIterator)this.text.clone();
        normalizer.mode = this.mode;
        normalizer.options = this.options;
        normalizer.norm2 = this.norm2;
        normalizer.buffer = new StringBuilder(this.buffer);
        normalizer.bufferPos = this.bufferPos;
        normalizer.currentIndex = this.currentIndex;
        normalizer.nextIndex = this.nextIndex;
        return normalizer;
    }
    
    private static final Normalizer2 getComposeNormalizer2(final boolean b, final int n) {
        return (b ? Normalizer.NFKC : Normalizer.NFC).getNormalizer2(n);
    }
    
    private static final Normalizer2 getDecomposeNormalizer2(final boolean b, final int n) {
        return (b ? Normalizer.NFKD : Normalizer.NFD).getNormalizer2(n);
    }
    
    public static String compose(final String s, final boolean b) {
        return compose(s, b, 0);
    }
    
    public static String compose(final String s, final boolean b, final int n) {
        return getComposeNormalizer2(b, n).normalize(s);
    }
    
    public static int compose(final char[] array, final char[] array2, final boolean b, final int n) {
        return compose(array, 0, array.length, array2, 0, array2.length, b, n);
    }
    
    public static int compose(final char[] array, final int n, final int n2, final char[] array2, final int n3, final int n4, final boolean b, final int n5) {
        final CharBuffer wrap = CharBuffer.wrap(array, n, n2 - n);
        final CharsAppendable charsAppendable = new CharsAppendable(array2, n3, n4);
        getComposeNormalizer2(b, n5).normalize(wrap, charsAppendable);
        return charsAppendable.length();
    }
    
    public static String decompose(final String s, final boolean b) {
        return decompose(s, b, 0);
    }
    
    public static String decompose(final String s, final boolean b, final int n) {
        return getDecomposeNormalizer2(b, n).normalize(s);
    }
    
    public static int decompose(final char[] array, final char[] array2, final boolean b, final int n) {
        return decompose(array, 0, array.length, array2, 0, array2.length, b, n);
    }
    
    public static int decompose(final char[] array, final int n, final int n2, final char[] array2, final int n3, final int n4, final boolean b, final int n5) {
        final CharBuffer wrap = CharBuffer.wrap(array, n, n2 - n);
        final CharsAppendable charsAppendable = new CharsAppendable(array2, n3, n4);
        getDecomposeNormalizer2(b, n5).normalize(wrap, charsAppendable);
        return charsAppendable.length();
    }
    
    public static String normalize(final String s, final Mode mode, final int n) {
        return mode.getNormalizer2(n).normalize(s);
    }
    
    public static String normalize(final String s, final Mode mode) {
        return normalize(s, mode, 0);
    }
    
    public static int normalize(final char[] array, final char[] array2, final Mode mode, final int n) {
        return normalize(array, 0, array.length, array2, 0, array2.length, mode, n);
    }
    
    public static int normalize(final char[] array, final int n, final int n2, final char[] array2, final int n3, final int n4, final Mode mode, final int n5) {
        final CharBuffer wrap = CharBuffer.wrap(array, n, n2 - n);
        final CharsAppendable charsAppendable = new CharsAppendable(array2, n3, n4);
        mode.getNormalizer2(n5).normalize(wrap, charsAppendable);
        return charsAppendable.length();
    }
    
    public static String normalize(final int n, final Mode mode, final int n2) {
        if (mode == Normalizer.NFD && n2 == 0) {
            String s = Norm2AllModes.getNFCInstance().impl.getDecomposition(n);
            if (s == null) {
                s = UTF16.valueOf(n);
            }
            return s;
        }
        return normalize(UTF16.valueOf(n), mode, n2);
    }
    
    public static String normalize(final int n, final Mode mode) {
        return normalize(n, mode, 0);
    }
    
    public static QuickCheckResult quickCheck(final String s, final Mode mode) {
        return quickCheck(s, mode, 0);
    }
    
    public static QuickCheckResult quickCheck(final String s, final Mode mode, final int n) {
        return mode.getNormalizer2(n).quickCheck(s);
    }
    
    public static QuickCheckResult quickCheck(final char[] array, final Mode mode, final int n) {
        return quickCheck(array, 0, array.length, mode, n);
    }
    
    public static QuickCheckResult quickCheck(final char[] array, final int n, final int n2, final Mode mode, final int n3) {
        return mode.getNormalizer2(n3).quickCheck(CharBuffer.wrap(array, n, n2 - n));
    }
    
    public static boolean isNormalized(final char[] array, final int n, final int n2, final Mode mode, final int n3) {
        return mode.getNormalizer2(n3).isNormalized(CharBuffer.wrap(array, n, n2 - n));
    }
    
    public static boolean isNormalized(final String s, final Mode mode, final int n) {
        return mode.getNormalizer2(n).isNormalized(s);
    }
    
    public static boolean isNormalized(final int n, final Mode mode, final int n2) {
        return isNormalized(UTF16.valueOf(n), mode, n2);
    }
    
    public static int compare(final char[] array, final int n, final int n2, final char[] array2, final int n3, final int n4, final int n5) {
        if (array == null || n < 0 || n2 < 0 || array2 == null || n3 < 0 || n4 < 0 || n2 < n || n4 < n3) {
            throw new IllegalArgumentException();
        }
        return internalCompare(CharBuffer.wrap(array, n, n2 - n), CharBuffer.wrap(array2, n3, n4 - n3), n5);
    }
    
    public static int compare(final String s, final String s2, final int n) {
        return internalCompare(s, s2, n);
    }
    
    public static int compare(final char[] array, final char[] array2, final int n) {
        return internalCompare(CharBuffer.wrap(array), CharBuffer.wrap(array2), n);
    }
    
    public static int compare(final int n, final int n2, final int n3) {
        return internalCompare(UTF16.valueOf(n), UTF16.valueOf(n2), n3 | 0x20000);
    }
    
    public static int compare(final int n, final String s, final int n2) {
        return internalCompare(UTF16.valueOf(n), s, n2);
    }
    
    public static int concatenate(final char[] array, final int n, final int n2, final char[] array2, final int n3, final int n4, final char[] array3, final int n5, final int n6, final Mode mode, final int n7) {
        if (array3 == null) {
            throw new IllegalArgumentException();
        }
        if (array2 == array3 && n3 < n6 && n5 < n4) {
            throw new IllegalArgumentException("overlapping right and dst ranges");
        }
        final StringBuilder sb = new StringBuilder(n2 - n + n4 - n3 + 16);
        sb.append(array, n, n2 - n);
        mode.getNormalizer2(n7).append(sb, CharBuffer.wrap(array2, n3, n4 - n3));
        final int length = sb.length();
        if (length <= n6 - n5) {
            sb.getChars(0, length, array3, n5);
            return length;
        }
        throw new IndexOutOfBoundsException(Integer.toString(length));
    }
    
    public static String concatenate(final char[] array, final char[] array2, final Mode mode, final int n) {
        return mode.getNormalizer2(n).append(new StringBuilder(array.length + array2.length + 16).append(array), CharBuffer.wrap(array2)).toString();
    }
    
    public static String concatenate(final String s, final String s2, final Mode mode, final int n) {
        return mode.getNormalizer2(n).append(new StringBuilder(s.length() + s2.length() + 16).append(s), s2).toString();
    }
    
    public static int getFC_NFKC_Closure(final int n, final char[] array) {
        final String fc_NFKC_Closure = getFC_NFKC_Closure(n);
        final int length = fc_NFKC_Closure.length();
        if (length != 0 && array != null && length <= array.length) {
            fc_NFKC_Closure.getChars(0, length, array, 0);
        }
        return length;
    }
    
    public static String getFC_NFKC_Closure(final int n) {
        final Normalizer2 access$300 = ModeImpl.access$300(NFKCModeImpl.access$1000());
        final UCaseProps instance = UCaseProps.INSTANCE;
        final StringBuilder sb = new StringBuilder();
        final int fullFolding = instance.toFullFolding(n, sb, 0);
        if (fullFolding < 0) {
            final Normalizer2Impl impl = ((Norm2AllModes.Normalizer2WithImpl)access$300).impl;
            if (impl.getCompQuickCheck(impl.getNorm16(n)) != 0) {
                return "";
            }
            sb.appendCodePoint(n);
        }
        else if (fullFolding > 31) {
            sb.appendCodePoint(fullFolding);
        }
        final String normalize = access$300.normalize(sb);
        final String normalize2 = access$300.normalize(UCharacter.foldCase(normalize, 0));
        if (normalize.equals(normalize2)) {
            return "";
        }
        return normalize2;
    }
    
    public int current() {
        if (this.bufferPos < this.buffer.length() || this.nextNormalize()) {
            return this.buffer.codePointAt(this.bufferPos);
        }
        return -1;
    }
    
    public int next() {
        if (this.bufferPos < this.buffer.length() || this.nextNormalize()) {
            final int codePoint = this.buffer.codePointAt(this.bufferPos);
            this.bufferPos += Character.charCount(codePoint);
            return codePoint;
        }
        return -1;
    }
    
    public int previous() {
        if (this.bufferPos > 0 || this.previousNormalize()) {
            final int codePointBefore = this.buffer.codePointBefore(this.bufferPos);
            this.bufferPos -= Character.charCount(codePointBefore);
            return codePointBefore;
        }
        return -1;
    }
    
    public void reset() {
        this.text.setToStart();
        final int n = 0;
        this.nextIndex = n;
        this.currentIndex = n;
        this.clearBuffer();
    }
    
    public void setIndexOnly(final int currentIndex) {
        this.text.setIndex(currentIndex);
        this.nextIndex = currentIndex;
        this.currentIndex = currentIndex;
        this.clearBuffer();
    }
    
    @Deprecated
    public int setIndex(final int indexOnly) {
        this.setIndexOnly(indexOnly);
        return this.current();
    }
    
    @Deprecated
    public int getBeginIndex() {
        return 0;
    }
    
    @Deprecated
    public int getEndIndex() {
        return this.endIndex();
    }
    
    public int first() {
        this.reset();
        return this.next();
    }
    
    public int last() {
        this.text.setToLimit();
        final int index = this.text.getIndex();
        this.nextIndex = index;
        this.currentIndex = index;
        this.clearBuffer();
        return this.previous();
    }
    
    public int getIndex() {
        if (this.bufferPos < this.buffer.length()) {
            return this.currentIndex;
        }
        return this.nextIndex;
    }
    
    public int startIndex() {
        return 0;
    }
    
    public int endIndex() {
        return this.text.getLength();
    }
    
    public void setMode(final Mode mode) {
        this.mode = mode;
        this.norm2 = this.mode.getNormalizer2(this.options);
    }
    
    public Mode getMode() {
        return this.mode;
    }
    
    public void setOption(final int n, final boolean b) {
        if (b) {
            this.options |= n;
        }
        else {
            this.options &= ~n;
        }
        this.norm2 = this.mode.getNormalizer2(this.options);
    }
    
    public int getOption(final int n) {
        if ((this.options & n) != 0x0) {
            return 1;
        }
        return 0;
    }
    
    public int getText(final char[] array) {
        return this.text.getText(array);
    }
    
    public int getLength() {
        return this.text.getLength();
    }
    
    public String getText() {
        return this.text.getText();
    }
    
    public void setText(final StringBuffer sb) {
        final UCharacterIterator instance = UCharacterIterator.getInstance(sb);
        if (instance == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = instance;
        this.reset();
    }
    
    public void setText(final char[] array) {
        final UCharacterIterator instance = UCharacterIterator.getInstance(array);
        if (instance == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = instance;
        this.reset();
    }
    
    public void setText(final String s) {
        final UCharacterIterator instance = UCharacterIterator.getInstance(s);
        if (instance == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = instance;
        this.reset();
    }
    
    public void setText(final CharacterIterator characterIterator) {
        final UCharacterIterator instance = UCharacterIterator.getInstance(characterIterator);
        if (instance == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = instance;
        this.reset();
    }
    
    public void setText(final UCharacterIterator uCharacterIterator) {
        final UCharacterIterator text = (UCharacterIterator)uCharacterIterator.clone();
        if (text == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = text;
        this.reset();
    }
    
    private void clearBuffer() {
        this.buffer.setLength(0);
        this.bufferPos = 0;
    }
    
    private boolean nextNormalize() {
        this.clearBuffer();
        this.currentIndex = this.nextIndex;
        this.text.setIndex(this.nextIndex);
        final int nextCodePoint = this.text.nextCodePoint();
        if (nextCodePoint < 0) {
            return false;
        }
        final StringBuilder appendCodePoint = new StringBuilder().appendCodePoint(nextCodePoint);
        int nextCodePoint2;
        while ((nextCodePoint2 = this.text.nextCodePoint()) >= 0) {
            if (this.norm2.hasBoundaryBefore(nextCodePoint2)) {
                this.text.moveCodePointIndex(-1);
                break;
            }
            appendCodePoint.appendCodePoint(nextCodePoint2);
        }
        this.nextIndex = this.text.getIndex();
        this.norm2.normalize(appendCodePoint, this.buffer);
        return this.buffer.length() != 0;
    }
    
    private boolean previousNormalize() {
        this.clearBuffer();
        this.nextIndex = this.currentIndex;
        this.text.setIndex(this.currentIndex);
        final StringBuilder sb = new StringBuilder();
        int previousCodePoint;
        while ((previousCodePoint = this.text.previousCodePoint()) >= 0) {
            if (previousCodePoint <= 65535) {
                sb.insert(0, (char)previousCodePoint);
            }
            else {
                sb.insert(0, Character.toChars(previousCodePoint));
            }
            if (this.norm2.hasBoundaryBefore(previousCodePoint)) {
                break;
            }
        }
        this.currentIndex = this.text.getIndex();
        this.norm2.normalize(sb, this.buffer);
        this.bufferPos = this.buffer.length();
        return this.buffer.length() != 0;
    }
    
    private static int internalCompare(CharSequence normalizeSecondAndAppend, CharSequence normalizeSecondAndAppend2, int n) {
        final int n2 = n >>> 20;
        n |= 0x80000;
        if ((n & 0x20000) == 0x0 || (n & 0x1) != 0x0) {
            Normalizer2 normalizer2;
            if ((n & 0x1) != 0x0) {
                normalizer2 = Normalizer.NFD.getNormalizer2(n2);
            }
            else {
                normalizer2 = Normalizer.FCD.getNormalizer2(n2);
            }
            final int spanQuickCheckYes = normalizer2.spanQuickCheckYes(normalizeSecondAndAppend);
            final int spanQuickCheckYes2 = normalizer2.spanQuickCheckYes(normalizeSecondAndAppend2);
            if (spanQuickCheckYes < normalizeSecondAndAppend.length()) {
                normalizeSecondAndAppend = normalizer2.normalizeSecondAndAppend(new StringBuilder(normalizeSecondAndAppend.length() + 16).append(normalizeSecondAndAppend, 0, spanQuickCheckYes), normalizeSecondAndAppend.subSequence(spanQuickCheckYes, normalizeSecondAndAppend.length()));
            }
            if (spanQuickCheckYes2 < normalizeSecondAndAppend2.length()) {
                normalizeSecondAndAppend2 = normalizer2.normalizeSecondAndAppend(new StringBuilder(normalizeSecondAndAppend2.length() + 16).append(normalizeSecondAndAppend2, 0, spanQuickCheckYes2), normalizeSecondAndAppend2.subSequence(spanQuickCheckYes2, normalizeSecondAndAppend2.length()));
            }
        }
        return cmpEquivFold(normalizeSecondAndAppend, normalizeSecondAndAppend2, n);
    }
    
    private static final CmpEquivLevel[] createCmpEquivLevelStack() {
        return new CmpEquivLevel[] { new CmpEquivLevel(null), new CmpEquivLevel(null) };
    }
    
    static int cmpEquivFold(CharSequence cs, CharSequence cs2, final int n) {
        CmpEquivLevel[] array = null;
        CmpEquivLevel[] array2 = null;
        Normalizer2Impl impl;
        if ((n & 0x80000) != 0x0) {
            impl = Norm2AllModes.getNFCInstance().impl;
        }
        else {
            impl = null;
        }
        UCaseProps instance;
        StringBuilder sb;
        StringBuilder sb2;
        if ((n & 0x10000) != 0x0) {
            instance = UCaseProps.INSTANCE;
            sb = new StringBuilder();
            sb2 = new StringBuilder();
        }
        else {
            instance = null;
            sb2 = (sb = null);
        }
        int n2 = ((CharSequence)cs).length();
        int n3 = ((CharSequence)cs2).length();
        final int n4 = 0;
        int n5 = 0;
        int n6 = n4;
        final int n7 = -1;
        int n8 = -1;
        int n9 = n7;
        while (true) {
            int s = 0;
            Label_0165: {
                if (-1 < 0) {
                    while (n2 == 0) {
                        if (n6 == 0) {
                            break Label_0165;
                        }
                        do {
                            --n6;
                            cs = array[n6].cs;
                        } while (cs == null);
                        s = array[n6].s;
                        n2 = ((CharSequence)cs).length();
                    }
                    final Object o = cs;
                    final int n10 = 0;
                    ++s;
                    n9 = ((CharSequence)o).charAt(n10);
                }
            }
            int s2 = 0;
            Label_0235: {
                if (-1 < 0) {
                    while (n3 == 0) {
                        if (n5 == 0) {
                            break Label_0235;
                        }
                        do {
                            --n5;
                            cs2 = array2[n5].cs;
                        } while (cs2 == null);
                        s2 = array2[n5].s;
                        n3 = ((CharSequence)cs2).length();
                    }
                    final Object o2 = cs2;
                    final int n11 = 0;
                    ++s2;
                    n8 = ((CharSequence)o2).charAt(n11);
                }
            }
            if (-1 == -1) {
                if (-1 < 0) {
                    return 0;
                }
                final int n12 = -1;
                n8 = -1;
                n9 = n12;
            }
            else {
                if (-1 < 0) {
                    return -1;
                }
                if (-1 < 0) {
                    return 1;
                }
                if (UTF16.isSurrogate((char)(-1))) {
                    if (Normalizer2Impl.UTF16Plus.isSurrogateLead(-1)) {
                        final char char1;
                        if (n2 && Character.isLowSurrogate(char1 = ((CharSequence)cs).charAt(0))) {
                            Character.toCodePoint((char)(-1), char1);
                        }
                    }
                    else {
                        final char char2;
                        if (0 <= -2 && Character.isHighSurrogate(char2 = ((CharSequence)cs).charAt(-2))) {
                            Character.toCodePoint(char2, (char)(-1));
                        }
                    }
                }
                if (UTF16.isSurrogate((char)(-1))) {
                    if (Normalizer2Impl.UTF16Plus.isSurrogateLead(-1)) {
                        final char char3;
                        if (n3 && Character.isLowSurrogate(char3 = ((CharSequence)cs2).charAt(0))) {
                            Character.toCodePoint((char)(-1), char3);
                        }
                    }
                    else {
                        final char char4;
                        if (0 <= -2 && Character.isHighSurrogate(char4 = ((CharSequence)cs2).charAt(-2))) {
                            Character.toCodePoint(char4, (char)(-1));
                        }
                    }
                }
                final int fullFolding;
                if (n6 == 0 && (n & 0x10000) != 0x0 && (fullFolding = instance.toFullFolding(-1, sb, n)) >= 0) {
                    if (UTF16.isSurrogate((char)(-1))) {
                        if (Normalizer2Impl.UTF16Plus.isSurrogateLead(-1)) {
                            ++s;
                        }
                        else {
                            --s2;
                            n8 = ((CharSequence)cs2).charAt(-1);
                        }
                    }
                    if (array == null) {
                        array = createCmpEquivLevelStack();
                    }
                    array[0].cs = (CharSequence)cs;
                    array[0].s = 0;
                    ++n6;
                    if (fullFolding <= 31) {
                        sb.delete(0, sb.length() - fullFolding);
                    }
                    else {
                        sb.setLength(0);
                        sb.appendCodePoint(fullFolding);
                    }
                    cs = sb;
                    n2 = sb.length();
                }
                else {
                    final int fullFolding2;
                    if (n5 == 0 && (n & 0x10000) != 0x0 && (fullFolding2 = instance.toFullFolding(-1, sb2, n)) >= 0) {
                        if (UTF16.isSurrogate((char)(-1))) {
                            if (Normalizer2Impl.UTF16Plus.isSurrogateLead(-1)) {
                                ++s2;
                            }
                            else {
                                --s;
                                n9 = ((CharSequence)cs).charAt(-1);
                            }
                        }
                        if (array2 == null) {
                            array2 = createCmpEquivLevelStack();
                        }
                        array2[0].cs = (CharSequence)cs2;
                        array2[0].s = 0;
                        ++n5;
                        if (fullFolding2 <= 31) {
                            sb2.delete(0, sb2.length() - fullFolding2);
                        }
                        else {
                            sb2.setLength(0);
                            sb2.appendCodePoint(fullFolding2);
                        }
                        cs2 = sb2;
                        n3 = sb2.length();
                    }
                    else {
                        final String decomposition;
                        if (n6 < 2 && (n & 0x80000) != 0x0 && (decomposition = impl.getDecomposition(-1)) != null) {
                            if (UTF16.isSurrogate((char)(-1))) {
                                if (Normalizer2Impl.UTF16Plus.isSurrogateLead(-1)) {
                                    ++s;
                                }
                                else {
                                    --s2;
                                    n8 = ((CharSequence)cs2).charAt(-1);
                                }
                            }
                            if (array == null) {
                                array = createCmpEquivLevelStack();
                            }
                            array[n6].cs = (CharSequence)cs;
                            array[n6].s = 0;
                            if (++n6 < 2) {
                                array[n6++].cs = null;
                            }
                            cs = decomposition;
                            n2 = decomposition.length();
                        }
                        else {
                            final String decomposition2;
                            if (n5 >= 2 || (n & 0x80000) == 0x0 || (decomposition2 = impl.getDecomposition(-1)) == null) {
                                if (-1 >= 55296 && -1 >= 55296 && (n & 0x8000) != 0x0) {
                                    if (-1 > 56319 || n2 == 0 || !Character.isLowSurrogate(((CharSequence)cs).charAt(0))) {
                                        if (!Character.isLowSurrogate((char)(-1)) || 0 == -1 || !Character.isHighSurrogate(((CharSequence)cs).charAt(-2))) {
                                            n9 -= 10240;
                                        }
                                    }
                                    if (-1 > 56319 || n3 == 0 || !Character.isLowSurrogate(((CharSequence)cs2).charAt(0))) {
                                        if (!Character.isLowSurrogate((char)(-1)) || 0 == -1 || !Character.isHighSurrogate(((CharSequence)cs2).charAt(-2))) {
                                            n8 -= 10240;
                                        }
                                    }
                                }
                                return 0;
                            }
                            if (UTF16.isSurrogate((char)(-1))) {
                                if (Normalizer2Impl.UTF16Plus.isSurrogateLead(-1)) {
                                    ++s2;
                                }
                                else {
                                    --s;
                                    n9 = ((CharSequence)cs).charAt(-1);
                                }
                            }
                            if (array2 == null) {
                                array2 = createCmpEquivLevelStack();
                            }
                            array2[n5].cs = (CharSequence)cs2;
                            array2[n5].s = 0;
                            if (++n5 < 2) {
                                array2[n5++].cs = null;
                            }
                            cs2 = decomposition2;
                            n3 = decomposition2.length();
                        }
                    }
                }
            }
        }
    }
    
    static {
        NONE = new NONEMode(null);
        NFD = new NFDMode(null);
        NFKD = new NFKDMode(null);
        NFC = new NFCMode(null);
        DEFAULT = Normalizer.NFC;
        NFKC = new NFKCMode(null);
        FCD = new FCDMode(null);
        NO_OP = Normalizer.NONE;
        COMPOSE = Normalizer.NFC;
        COMPOSE_COMPAT = Normalizer.NFKC;
        DECOMP = Normalizer.NFD;
        DECOMP_COMPAT = Normalizer.NFKD;
        NO = new QuickCheckResult(0, null);
        YES = new QuickCheckResult(1, null);
        MAYBE = new QuickCheckResult(2, null);
    }
    
    private static final class CharsAppendable implements Appendable
    {
        private final char[] chars;
        private final int start;
        private final int limit;
        private int offset;
        
        public CharsAppendable(final char[] chars, final int n, final int limit) {
            this.chars = chars;
            this.offset = n;
            this.start = n;
            this.limit = limit;
        }
        
        public int length() {
            final int n = this.offset - this.start;
            if (this.offset <= this.limit) {
                return n;
            }
            throw new IndexOutOfBoundsException(Integer.toString(n));
        }
        
        public Appendable append(final char c) {
            if (this.offset < this.limit) {
                this.chars[this.offset] = c;
            }
            ++this.offset;
            return this;
        }
        
        public Appendable append(final CharSequence charSequence) {
            return this.append(charSequence, 0, charSequence.length());
        }
        
        public Appendable append(final CharSequence charSequence, int i, final int n) {
            final int n2 = n - i;
            if (n2 <= this.limit - this.offset) {
                while (i < n) {
                    this.chars[this.offset++] = charSequence.charAt(i++);
                }
            }
            else {
                this.offset += n2;
            }
            return this;
        }
    }
    
    private static final class CmpEquivLevel
    {
        CharSequence cs;
        int s;
        
        private CmpEquivLevel() {
        }
        
        CmpEquivLevel(final Normalizer$1 object) {
            this();
        }
    }
    
    public static final class QuickCheckResult
    {
        private QuickCheckResult(final int n) {
        }
        
        QuickCheckResult(final int n, final Normalizer$1 object) {
            this(n);
        }
    }
    
    private static final class FCDMode extends Mode
    {
        private FCDMode() {
        }
        
        @Override
        protected Normalizer2 getNormalizer2(final int n) {
            return ((n & 0x20) != 0x0) ? ModeImpl.access$300(FCD32ModeImpl.access$1100()) : ModeImpl.access$300(FCDModeImpl.access$1200());
        }
        
        FCDMode(final Normalizer$1 object) {
            this();
        }
    }
    
    public abstract static class Mode
    {
        @Deprecated
        protected abstract Normalizer2 getNormalizer2(final int p0);
    }
    
    private static final class FCD32ModeImpl
    {
        private static final ModeImpl INSTANCE;
        
        static ModeImpl access$1100() {
            return FCD32ModeImpl.INSTANCE;
        }
        
        static {
            INSTANCE = new ModeImpl(new FilteredNormalizer2(Norm2AllModes.getFCDNormalizer2(), Unicode32.access$100()), null);
        }
    }
    
    private static final class ModeImpl
    {
        private final Normalizer2 normalizer2;
        
        private ModeImpl(final Normalizer2 normalizer2) {
            this.normalizer2 = normalizer2;
        }
        
        ModeImpl(final Normalizer2 normalizer2, final Normalizer$1 object) {
            this(normalizer2);
        }
        
        static Normalizer2 access$300(final ModeImpl modeImpl) {
            return modeImpl.normalizer2;
        }
    }
    
    private static final class Unicode32
    {
        private static final UnicodeSet INSTANCE;
        
        static UnicodeSet access$100() {
            return Unicode32.INSTANCE;
        }
        
        static {
            INSTANCE = new UnicodeSet("[:age=3.2:]").freeze();
        }
    }
    
    private static final class FCDModeImpl
    {
        private static final ModeImpl INSTANCE;
        
        static ModeImpl access$1200() {
            return FCDModeImpl.INSTANCE;
        }
        
        static {
            INSTANCE = new ModeImpl(Norm2AllModes.getFCDNormalizer2(), null);
        }
    }
    
    private static final class NFKCMode extends Mode
    {
        private NFKCMode() {
        }
        
        @Override
        protected Normalizer2 getNormalizer2(final int n) {
            return ((n & 0x20) != 0x0) ? ModeImpl.access$300(NFKC32ModeImpl.access$900()) : ModeImpl.access$300(NFKCModeImpl.access$1000());
        }
        
        NFKCMode(final Normalizer$1 object) {
            this();
        }
    }
    
    private static final class NFKC32ModeImpl
    {
        private static final ModeImpl INSTANCE;
        
        static ModeImpl access$900() {
            return NFKC32ModeImpl.INSTANCE;
        }
        
        static {
            INSTANCE = new ModeImpl(new FilteredNormalizer2(Norm2AllModes.getNFKCInstance().comp, Unicode32.access$100()), null);
        }
    }
    
    private static final class NFKCModeImpl
    {
        private static final ModeImpl INSTANCE;
        
        static ModeImpl access$1000() {
            return NFKCModeImpl.INSTANCE;
        }
        
        static {
            INSTANCE = new ModeImpl(Norm2AllModes.getNFKCInstance().comp, null);
        }
    }
    
    private static final class NFCMode extends Mode
    {
        private NFCMode() {
        }
        
        @Override
        protected Normalizer2 getNormalizer2(final int n) {
            return ((n & 0x20) != 0x0) ? ModeImpl.access$300(NFC32ModeImpl.access$700()) : ModeImpl.access$300(NFCModeImpl.access$800());
        }
        
        NFCMode(final Normalizer$1 object) {
            this();
        }
    }
    
    private static final class NFC32ModeImpl
    {
        private static final ModeImpl INSTANCE;
        
        static ModeImpl access$700() {
            return NFC32ModeImpl.INSTANCE;
        }
        
        static {
            INSTANCE = new ModeImpl(new FilteredNormalizer2(Norm2AllModes.getNFCInstance().comp, Unicode32.access$100()), null);
        }
    }
    
    private static final class NFCModeImpl
    {
        private static final ModeImpl INSTANCE;
        
        static ModeImpl access$800() {
            return NFCModeImpl.INSTANCE;
        }
        
        static {
            INSTANCE = new ModeImpl(Norm2AllModes.getNFCInstance().comp, null);
        }
    }
    
    private static final class NFKDMode extends Mode
    {
        private NFKDMode() {
        }
        
        @Override
        protected Normalizer2 getNormalizer2(final int n) {
            return ((n & 0x20) != 0x0) ? ModeImpl.access$300(NFKD32ModeImpl.access$500()) : ModeImpl.access$300(NFKDModeImpl.access$600());
        }
        
        NFKDMode(final Normalizer$1 object) {
            this();
        }
    }
    
    private static final class NFKD32ModeImpl
    {
        private static final ModeImpl INSTANCE;
        
        static ModeImpl access$500() {
            return NFKD32ModeImpl.INSTANCE;
        }
        
        static {
            INSTANCE = new ModeImpl(new FilteredNormalizer2(Norm2AllModes.getNFKCInstance().decomp, Unicode32.access$100()), null);
        }
    }
    
    private static final class NFKDModeImpl
    {
        private static final ModeImpl INSTANCE;
        
        static ModeImpl access$600() {
            return NFKDModeImpl.INSTANCE;
        }
        
        static {
            INSTANCE = new ModeImpl(Norm2AllModes.getNFKCInstance().decomp, null);
        }
    }
    
    private static final class NFDMode extends Mode
    {
        private NFDMode() {
        }
        
        @Override
        protected Normalizer2 getNormalizer2(final int n) {
            return ((n & 0x20) != 0x0) ? ModeImpl.access$300(NFD32ModeImpl.access$200()) : ModeImpl.access$300(NFDModeImpl.access$400());
        }
        
        NFDMode(final Normalizer$1 object) {
            this();
        }
    }
    
    private static final class NFD32ModeImpl
    {
        private static final ModeImpl INSTANCE;
        
        static ModeImpl access$200() {
            return NFD32ModeImpl.INSTANCE;
        }
        
        static {
            INSTANCE = new ModeImpl(new FilteredNormalizer2(Norm2AllModes.getNFCInstance().decomp, Unicode32.access$100()), null);
        }
    }
    
    private static final class NFDModeImpl
    {
        private static final ModeImpl INSTANCE;
        
        static ModeImpl access$400() {
            return NFDModeImpl.INSTANCE;
        }
        
        static {
            INSTANCE = new ModeImpl(Norm2AllModes.getNFCInstance().decomp, null);
        }
    }
    
    private static final class NONEMode extends Mode
    {
        private NONEMode() {
        }
        
        @Override
        protected Normalizer2 getNormalizer2(final int n) {
            return Norm2AllModes.NOOP_NORMALIZER2;
        }
        
        NONEMode(final Normalizer$1 object) {
            this();
        }
    }
}
