package com.ibm.icu.impl;

import java.io.*;
import java.util.*;
import com.ibm.icu.text.*;
import com.ibm.icu.util.*;

public final class UCaseProps
{
    private static final byte[] flagsOffset;
    public static final int MAX_STRING_LENGTH = 31;
    private static final int LOC_UNKNOWN = 0;
    private static final int LOC_ROOT = 1;
    private static final int LOC_TURKISH = 2;
    private static final int LOC_LITHUANIAN = 3;
    private static final String iDot = "i\u0307";
    private static final String jDot = "j\u0307";
    private static final String iOgonekDot = "\u012f\u0307";
    private static final String iDotGrave = "i\u0307\u0300";
    private static final String iDotAcute = "i\u0307\u0301";
    private static final String iDotTilde = "i\u0307\u0303";
    private static final int FOLD_CASE_OPTIONS_MASK = 255;
    private static final int[] rootLocCache;
    public static final StringBuilder dummyStringBuilder;
    private int[] indexes;
    private char[] exceptions;
    private char[] unfold;
    private Trie2_16 trie;
    private static final String DATA_NAME = "ucase";
    private static final String DATA_TYPE = "icu";
    private static final String DATA_FILE_NAME = "ucase.icu";
    private static final byte[] FMT;
    private static final int IX_TRIE_SIZE = 2;
    private static final int IX_EXC_LENGTH = 3;
    private static final int IX_UNFOLD_LENGTH = 4;
    private static final int IX_TOP = 16;
    public static final int TYPE_MASK = 3;
    public static final int NONE = 0;
    public static final int LOWER = 1;
    public static final int UPPER = 2;
    public static final int TITLE = 3;
    private static final int SENSITIVE = 8;
    private static final int EXCEPTION = 16;
    private static final int DOT_MASK = 96;
    private static final int SOFT_DOTTED = 32;
    private static final int ABOVE = 64;
    private static final int OTHER_ACCENT = 96;
    private static final int DELTA_SHIFT = 7;
    private static final int EXC_SHIFT = 5;
    private static final int EXC_LOWER = 0;
    private static final int EXC_FOLD = 1;
    private static final int EXC_UPPER = 2;
    private static final int EXC_TITLE = 3;
    private static final int EXC_CLOSURE = 6;
    private static final int EXC_FULL_MAPPINGS = 7;
    private static final int EXC_DOUBLE_SLOTS = 256;
    private static final int EXC_DOT_SHIFT = 7;
    private static final int EXC_CONDITIONAL_SPECIAL = 16384;
    private static final int EXC_CONDITIONAL_FOLD = 32768;
    private static final int FULL_LOWER = 15;
    private static final int CLOSURE_MAX_LENGTH = 15;
    private static final int UNFOLD_ROWS = 0;
    private static final int UNFOLD_ROW_WIDTH = 1;
    private static final int UNFOLD_STRING_WIDTH = 2;
    public static final UCaseProps INSTANCE;
    
    private UCaseProps() throws IOException {
        final InputStream requiredStream = ICUData.getRequiredStream("data/icudt51b/ucase.icu");
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(requiredStream, 4096);
        this.readData(bufferedInputStream);
        bufferedInputStream.close();
        requiredStream.close();
    }
    
    private final void readData(final InputStream inputStream) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        ICUBinary.readHeader(dataInputStream, UCaseProps.FMT, new IsAcceptable(null));
        final int int1 = dataInputStream.readInt();
        if (int1 < 16) {
            throw new IOException("indexes[0] too small in ucase.icu");
        }
        (this.indexes = new int[int1])[0] = int1;
        for (int i = 1; i < int1; ++i) {
            this.indexes[i] = dataInputStream.readInt();
        }
        this.trie = Trie2_16.createFromSerialized(dataInputStream);
        final int n = this.indexes[2];
        final int serializedLength = this.trie.getSerializedLength();
        if (serializedLength > n) {
            throw new IOException("ucase.icu: not enough bytes for the trie");
        }
        dataInputStream.skipBytes(n - serializedLength);
        final int n2 = this.indexes[3];
        if (n2 > 0) {
            this.exceptions = new char[n2];
            for (int j = 0; j < n2; ++j) {
                this.exceptions[j] = dataInputStream.readChar();
            }
        }
        final int n3 = this.indexes[4];
        if (n3 > 0) {
            this.unfold = new char[n3];
            for (int k = 0; k < n3; ++k) {
                this.unfold[k] = dataInputStream.readChar();
            }
        }
    }
    
    public final void addPropertyStarts(final UnicodeSet set) {
        final Iterator iterator = this.trie.iterator();
        Trie2.Range range;
        while (iterator.hasNext() && !(range = iterator.next()).leadSurrogate) {
            set.add(range.startCodePoint);
        }
    }
    
    private static final int getExceptionsOffset(final int n) {
        return n >> 5;
    }
    
    private static final boolean propsHasException(final int n) {
        return (n & 0x10) != 0x0;
    }
    
    private static final boolean hasSlot(final int n, final int n2) {
        return (n & 1 << n2) != 0x0;
    }
    
    private static final byte slotOffset(final int n, final int n2) {
        return UCaseProps.flagsOffset[n & (1 << n2) - 1];
    }
    
    private final long getSlotValueAndOffset(final int n, final int n2, int n3) {
        long n4;
        if ((n & 0x100) == 0x0) {
            n3 += slotOffset(n, n2);
            n4 = this.exceptions[n3];
        }
        else {
            n3 += 2 * slotOffset(n, n2);
            n4 = ((long)this.exceptions[n3++] << 16 | (long)this.exceptions[n3]);
        }
        return n4 | (long)n3 << 32;
    }
    
    private final int getSlotValue(final int n, final int n2, int n3) {
        int n4;
        if ((n & 0x100) == 0x0) {
            n3 += slotOffset(n, n2);
            n4 = this.exceptions[n3];
        }
        else {
            n3 += 2 * slotOffset(n, n2);
            n4 = (this.exceptions[n3++] << 16 | this.exceptions[n3]);
        }
        return n4;
    }
    
    public final int tolower(int slotValue) {
        final int value = this.trie.get(slotValue);
        if (!propsHasException(value)) {
            if (getTypeFromProps(value) >= 2) {
                slotValue += getDelta(value);
            }
        }
        else {
            int exceptionsOffset = getExceptionsOffset(value);
            final char c = this.exceptions[exceptionsOffset++];
            if (hasSlot(c, 0)) {
                slotValue = this.getSlotValue(c, 0, exceptionsOffset);
            }
        }
        return slotValue;
    }
    
    public final int toupper(int slotValue) {
        final int value = this.trie.get(slotValue);
        if (!propsHasException(value)) {
            if (getTypeFromProps(value) == 1) {
                slotValue += getDelta(value);
            }
        }
        else {
            int exceptionsOffset = getExceptionsOffset(value);
            final char c = this.exceptions[exceptionsOffset++];
            if (hasSlot(c, 2)) {
                slotValue = this.getSlotValue(c, 2, exceptionsOffset);
            }
        }
        return slotValue;
    }
    
    public final int totitle(int slotValue) {
        final int value = this.trie.get(slotValue);
        if (!propsHasException(value)) {
            if (getTypeFromProps(value) == 1) {
                slotValue += getDelta(value);
            }
        }
        else {
            int exceptionsOffset = getExceptionsOffset(value);
            final char c = this.exceptions[exceptionsOffset++];
            int n;
            if (hasSlot(c, 3)) {
                n = 3;
            }
            else {
                if (!hasSlot(c, 2)) {
                    return slotValue;
                }
                n = 2;
            }
            slotValue = this.getSlotValue(c, n, exceptionsOffset);
        }
        return slotValue;
    }
    
    public final void addCaseClosure(int n, final UnicodeSet set) {
        switch (n) {
            case 73: {
                set.add(105);
            }
            case 105: {
                set.add(73);
            }
            case 304: {
                set.add("i\u0307");
            }
            case 305: {}
            default: {
                final int value = this.trie.get(n);
                if (!propsHasException(value)) {
                    if (getTypeFromProps(value) != 0) {
                        final int delta = getDelta(value);
                        if (delta != 0) {
                            set.add(n + delta);
                        }
                    }
                }
                else {
                    int exceptionsOffset = getExceptionsOffset(value);
                    final char c = this.exceptions[exceptionsOffset++];
                    final int n2 = exceptionsOffset;
                    for (int i = 0; i <= 3; ++i) {
                        if (hasSlot(c, i)) {
                            n = this.getSlotValue(c, i, n2);
                            set.add(n);
                        }
                    }
                    int n3;
                    int n4;
                    if (hasSlot(c, 6)) {
                        final long slotValueAndOffset = this.getSlotValueAndOffset(c, 6, n2);
                        n3 = ((int)slotValueAndOffset & 0xF);
                        n4 = (int)(slotValueAndOffset >> 32) + 1;
                    }
                    else {
                        n3 = 0;
                        n4 = 0;
                    }
                    if (hasSlot(c, 7)) {
                        final long slotValueAndOffset2 = this.getSlotValueAndOffset(c, 7, n2);
                        final int n5 = (int)slotValueAndOffset2;
                        final int n6 = (int)(slotValueAndOffset2 >> 32) + 1;
                        final int n7 = n5 & 0xFFFF;
                        int n8 = n6 + (n7 & 0xF);
                        final int n9 = n7 >> 4;
                        final int n10 = n9 & 0xF;
                        if (n10 != 0) {
                            set.add(new String(this.exceptions, n8, n10));
                            n8 += n10;
                        }
                        final int n11 = n9 >> 4;
                        n4 = n8 + (n11 & 0xF) + (n11 >> 4);
                    }
                    for (int j = 0; j < n3; j += UTF16.getCharCount(n)) {
                        n = UTF16.charAt(this.exceptions, n4, this.exceptions.length, j);
                        set.add(n);
                    }
                }
            }
        }
    }
    
    private final int strcmpMax(final String s, int n, int n2) {
        int length = s.length();
        n2 -= length;
        int n3 = 0;
        do {
            final char char1 = s.charAt(n3++);
            final char c = this.unfold[n++];
            if (c == '\0') {
                return 1;
            }
            final int n4 = char1 - c;
            if (n4 != 0) {
                return n4;
            }
        } while (--length > 0);
        if (n2 == 0 || this.unfold[n] == '\0') {
            return 0;
        }
        return -n2;
    }
    
    public final boolean addStringCaseClosure(final String s, final UnicodeSet set) {
        if (this.unfold == null || s == null) {
            return false;
        }
        final int length = s.length();
        if (length <= 1) {
            return false;
        }
        final char c = this.unfold[0];
        final char c2 = this.unfold[1];
        final char c3 = this.unfold[2];
        if (length > c3) {
            return false;
        }
        int i = 0;
        int n = c;
        while (i < n) {
            final int n2 = (i + n) / 2;
            final int n3 = (n2 + 1) * c2;
            final int strcmpMax = this.strcmpMax(s, n3, c3);
            if (strcmpMax == 0) {
                int char1;
                for (int n4 = c3; n4 < c2 && this.unfold[n3 + n4] != '\0'; n4 += UTF16.getCharCount(char1)) {
                    char1 = UTF16.charAt(this.unfold, n3, this.unfold.length, n4);
                    set.add(char1);
                    this.addCaseClosure(char1, set);
                }
                return true;
            }
            if (strcmpMax < 0) {
                n = n2;
            }
            else {
                i = n2 + 1;
            }
        }
        return false;
    }
    
    public final int getType(final int n) {
        return getTypeFromProps(this.trie.get(n));
    }
    
    public final int getTypeOrIgnorable(final int n) {
        return getTypeAndIgnorableFromProps(this.trie.get(n));
    }
    
    public final int getDotType(final int n) {
        final int value = this.trie.get(n);
        if (!propsHasException(value)) {
            return value & 0x60;
        }
        return this.exceptions[getExceptionsOffset(value)] >> 7 & 0x60;
    }
    
    public final boolean isSoftDotted(final int n) {
        return this.getDotType(n) == 32;
    }
    
    public final boolean isCaseSensitive(final int n) {
        return (this.trie.get(n) & 0x8) != 0x0;
    }
    
    private static final int getCaseLocale(final ULocale uLocale, final int[] array) {
        final int n;
        if (array != null && (n = array[0]) != 0) {
            return n;
        }
        int n2 = 1;
        final String language = uLocale.getLanguage();
        if (language.equals("tr") || language.equals("tur") || language.equals("az") || language.equals("aze")) {
            n2 = 2;
        }
        else if (language.equals("lt") || language.equals("lit")) {
            n2 = 3;
        }
        if (array != null) {
            array[0] = n2;
        }
        return n2;
    }
    
    private final boolean isFollowedByCasedLetter(final ContextIterator contextIterator, final int n) {
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(n);
        int next;
        while ((next = contextIterator.next()) >= 0) {
            final int typeOrIgnorable = this.getTypeOrIgnorable(next);
            if ((typeOrIgnorable & 0x4) != 0x0) {
                continue;
            }
            return typeOrIgnorable != 0;
        }
        return false;
    }
    
    private final boolean isPrecededBySoftDotted(final ContextIterator contextIterator) {
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(-1);
        int next;
        while ((next = contextIterator.next()) >= 0) {
            final int dotType = this.getDotType(next);
            if (dotType == 32) {
                return true;
            }
            if (dotType != 96) {
                return false;
            }
        }
        return false;
    }
    
    private final boolean isPrecededBy_I(final ContextIterator contextIterator) {
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(-1);
        int next;
        while ((next = contextIterator.next()) >= 0) {
            if (next == 73) {
                return true;
            }
            if (this.getDotType(next) != 96) {
                return false;
            }
        }
        return false;
    }
    
    private final boolean isFollowedByMoreAbove(final ContextIterator contextIterator) {
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(1);
        int next;
        while ((next = contextIterator.next()) >= 0) {
            final int dotType = this.getDotType(next);
            if (dotType == 64) {
                return true;
            }
            if (dotType != 96) {
                return false;
            }
        }
        return false;
    }
    
    private final boolean isFollowedByDotAbove(final ContextIterator contextIterator) {
        if (contextIterator == null) {
            return false;
        }
        contextIterator.reset(1);
        int next;
        while ((next = contextIterator.next()) >= 0) {
            if (next == 775) {
                return true;
            }
            if (this.getDotType(next) != 96) {
                return false;
            }
        }
        return false;
    }
    
    public final int toFullLower(final int n, final ContextIterator contextIterator, final StringBuilder sb, final ULocale uLocale, final int[] array) {
        int slotValue = n;
        final int value = this.trie.get(n);
        if (!propsHasException(value)) {
            if (getTypeFromProps(value) >= 2) {
                slotValue = n + getDelta(value);
            }
        }
        else {
            int exceptionsOffset = getExceptionsOffset(value);
            final char c = this.exceptions[exceptionsOffset++];
            final int n2 = exceptionsOffset;
            if ((c & '\u4000') != 0x0) {
                final int caseLocale = getCaseLocale(uLocale, array);
                if (caseLocale == 3 && (((n == 73 || n == 74 || n == 302) && this.isFollowedByMoreAbove(contextIterator)) || n == 204 || n == 205 || n == 296)) {
                    switch (n) {
                        case 73: {
                            sb.append("i\u0307");
                            return 2;
                        }
                        case 74: {
                            sb.append("j\u0307");
                            return 2;
                        }
                        case 302: {
                            sb.append("\u012f\u0307");
                            return 2;
                        }
                        case 204: {
                            sb.append("i\u0307\u0300");
                            return 3;
                        }
                        case 205: {
                            sb.append("i\u0307\u0301");
                            return 3;
                        }
                        case 296: {
                            sb.append("i\u0307\u0303");
                            return 3;
                        }
                        default: {
                            return 0;
                        }
                    }
                }
                else {
                    if (caseLocale == 2 && n == 304) {
                        return 105;
                    }
                    if (caseLocale == 2 && n == 775 && this.isPrecededBy_I(contextIterator)) {
                        return 0;
                    }
                    if (caseLocale == 2 && n == 73 && !this.isFollowedByDotAbove(contextIterator)) {
                        return 305;
                    }
                    if (n == 304) {
                        sb.append("i\u0307");
                        return 2;
                    }
                    if (n == 931 && !this.isFollowedByCasedLetter(contextIterator, 1) && this.isFollowedByCasedLetter(contextIterator, -1)) {
                        return 962;
                    }
                }
            }
            else if (hasSlot(c, 7)) {
                final long slotValueAndOffset = this.getSlotValueAndOffset(c, 7, exceptionsOffset);
                final int n3 = (int)slotValueAndOffset & 0xF;
                if (n3 != 0) {
                    sb.append(this.exceptions, (int)(slotValueAndOffset >> 32) + 1, n3);
                    return n3;
                }
            }
            if (hasSlot(c, 0)) {
                slotValue = this.getSlotValue(c, 0, n2);
            }
        }
        return (slotValue == n) ? (~slotValue) : slotValue;
    }
    
    private final int toUpperOrTitle(final int n, final ContextIterator contextIterator, final StringBuilder sb, final ULocale uLocale, final int[] array, final boolean b) {
        int slotValue = n;
        final int value = this.trie.get(n);
        if (!propsHasException(value)) {
            if (getTypeFromProps(value) == 1) {
                slotValue = n + getDelta(value);
            }
        }
        else {
            int exceptionsOffset = getExceptionsOffset(value);
            final char c = this.exceptions[exceptionsOffset++];
            final int n2 = exceptionsOffset;
            if ((c & '\u4000') != 0x0) {
                final int caseLocale = getCaseLocale(uLocale, array);
                if (caseLocale == 2 && n == 105) {
                    return 304;
                }
                if (caseLocale == 3 && n == 775 && this.isPrecededBySoftDotted(contextIterator)) {
                    return 0;
                }
            }
            else if (hasSlot(c, 7)) {
                final long slotValueAndOffset = this.getSlotValueAndOffset(c, 7, exceptionsOffset);
                final int n3 = (int)slotValueAndOffset & 0xFFFF;
                final int n4 = (int)(slotValueAndOffset >> 32) + 1 + (n3 & 0xF);
                final int n5 = n3 >> 4;
                int n6 = n4 + (n5 & 0xF);
                final int n7 = n5 >> 4;
                int n8;
                if (b) {
                    n8 = (n7 & 0xF);
                }
                else {
                    n6 += (n7 & 0xF);
                    n8 = (n7 >> 4 & 0xF);
                }
                if (n8 != 0) {
                    sb.append(this.exceptions, n6, n8);
                    return n8;
                }
            }
            int n9;
            if (!b && hasSlot(c, 3)) {
                n9 = 3;
            }
            else {
                if (!hasSlot(c, 2)) {
                    return ~n;
                }
                n9 = 2;
            }
            slotValue = this.getSlotValue(c, n9, n2);
        }
        return (slotValue == n) ? (~slotValue) : slotValue;
    }
    
    public final int toFullUpper(final int n, final ContextIterator contextIterator, final StringBuilder sb, final ULocale uLocale, final int[] array) {
        return this.toUpperOrTitle(n, contextIterator, sb, uLocale, array, true);
    }
    
    public final int toFullTitle(final int n, final ContextIterator contextIterator, final StringBuilder sb, final ULocale uLocale, final int[] array) {
        return this.toUpperOrTitle(n, contextIterator, sb, uLocale, array, false);
    }
    
    public final int fold(int slotValue, final int n) {
        final int value = this.trie.get(slotValue);
        if (!propsHasException(value)) {
            if (getTypeFromProps(value) >= 2) {
                slotValue += getDelta(value);
            }
        }
        else {
            int exceptionsOffset = getExceptionsOffset(value);
            final char c = this.exceptions[exceptionsOffset++];
            if ((c & '\u8000') != 0x0) {
                if ((n & 0xFF) == 0x0) {
                    if (slotValue == 73) {
                        return 105;
                    }
                    if (slotValue == 304) {
                        return slotValue;
                    }
                }
                else {
                    if (slotValue == 73) {
                        return 305;
                    }
                    if (slotValue == 304) {
                        return 105;
                    }
                }
            }
            int n2;
            if (hasSlot(c, 1)) {
                n2 = 1;
            }
            else {
                if (!hasSlot(c, 0)) {
                    return slotValue;
                }
                n2 = 0;
            }
            slotValue = this.getSlotValue(c, n2, exceptionsOffset);
        }
        return slotValue;
    }
    
    public final int toFullFolding(final int n, final StringBuilder sb, final int n2) {
        int slotValue = n;
        final int value = this.trie.get(n);
        if (!propsHasException(value)) {
            if (getTypeFromProps(value) >= 2) {
                slotValue = n + getDelta(value);
            }
        }
        else {
            int exceptionsOffset = getExceptionsOffset(value);
            final char c = this.exceptions[exceptionsOffset++];
            final int n3 = exceptionsOffset;
            if ((c & '\u8000') != 0x0) {
                if ((n2 & 0xFF) == 0x0) {
                    if (n == 73) {
                        return 105;
                    }
                    if (n == 304) {
                        sb.append("i\u0307");
                        return 2;
                    }
                }
                else {
                    if (n == 73) {
                        return 305;
                    }
                    if (n == 304) {
                        return 105;
                    }
                }
            }
            else if (hasSlot(c, 7)) {
                final long slotValueAndOffset = this.getSlotValueAndOffset(c, 7, exceptionsOffset);
                final int n4 = (int)slotValueAndOffset & 0xFFFF;
                final int n5 = (int)(slotValueAndOffset >> 32) + 1 + (n4 & 0xF);
                final int n6 = n4 >> 4 & 0xF;
                if (n6 != 0) {
                    sb.append(this.exceptions, n5, n6);
                    return n6;
                }
            }
            int n7;
            if (hasSlot(c, 1)) {
                n7 = 1;
            }
            else {
                if (!hasSlot(c, 0)) {
                    return ~n;
                }
                n7 = 0;
            }
            slotValue = this.getSlotValue(c, n7, n3);
        }
        return (slotValue == n) ? (~slotValue) : slotValue;
    }
    
    public final boolean hasBinaryProperty(final int n, final int n2) {
        switch (n2) {
            case 22: {
                return 1 == this.getType(n);
            }
            case 30: {
                return 2 == this.getType(n);
            }
            case 27: {
                return this.isSoftDotted(n);
            }
            case 34: {
                return this.isCaseSensitive(n);
            }
            case 49: {
                return 0 != this.getType(n);
            }
            case 50: {
                return this.getTypeOrIgnorable(n) >> 2 != 0;
            }
            case 51: {
                UCaseProps.dummyStringBuilder.setLength(0);
                return this.toFullLower(n, null, UCaseProps.dummyStringBuilder, ULocale.ROOT, UCaseProps.rootLocCache) >= 0;
            }
            case 52: {
                UCaseProps.dummyStringBuilder.setLength(0);
                return this.toFullUpper(n, null, UCaseProps.dummyStringBuilder, ULocale.ROOT, UCaseProps.rootLocCache) >= 0;
            }
            case 53: {
                UCaseProps.dummyStringBuilder.setLength(0);
                return this.toFullTitle(n, null, UCaseProps.dummyStringBuilder, ULocale.ROOT, UCaseProps.rootLocCache) >= 0;
            }
            case 55: {
                UCaseProps.dummyStringBuilder.setLength(0);
                return this.toFullLower(n, null, UCaseProps.dummyStringBuilder, ULocale.ROOT, UCaseProps.rootLocCache) >= 0 || this.toFullUpper(n, null, UCaseProps.dummyStringBuilder, ULocale.ROOT, UCaseProps.rootLocCache) >= 0 || this.toFullTitle(n, null, UCaseProps.dummyStringBuilder, ULocale.ROOT, UCaseProps.rootLocCache) >= 0;
            }
            default: {
                return false;
            }
        }
    }
    
    private static final int getTypeFromProps(final int n) {
        return n & 0x3;
    }
    
    private static final int getTypeAndIgnorableFromProps(final int n) {
        return n & 0x7;
    }
    
    private static final int getDelta(final int n) {
        return (short)n >> 7;
    }
    
    static {
        flagsOffset = new byte[] { 0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8 };
        rootLocCache = new int[] { 1 };
        dummyStringBuilder = new StringBuilder();
        FMT = new byte[] { 99, 65, 83, 69 };
        try {
            INSTANCE = new UCaseProps();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public interface ContextIterator
    {
        void reset(final int p0);
        
        int next();
    }
    
    private static final class IsAcceptable implements ICUBinary.Authenticate
    {
        private IsAcceptable() {
        }
        
        public boolean isDataVersionAcceptable(final byte[] array) {
            return array[0] == 3;
        }
        
        IsAcceptable(final UCaseProps$1 object) {
            this();
        }
    }
}
