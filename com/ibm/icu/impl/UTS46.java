package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import java.util.*;
import com.ibm.icu.lang.*;
import java.io.*;

public final class UTS46 extends IDNA
{
    private static final Normalizer2 uts46Norm2;
    final int options;
    private static final EnumSet severeErrors;
    private static final byte[] asciiData;
    private static final int L_MASK;
    private static final int R_AL_MASK;
    private static final int L_R_AL_MASK;
    private static final int R_AL_AN_MASK;
    private static final int EN_AN_MASK;
    private static final int R_AL_EN_AN_MASK;
    private static final int L_EN_MASK;
    private static final int ES_CS_ET_ON_BN_NSM_MASK;
    private static final int L_EN_ES_CS_ET_ON_BN_NSM_MASK;
    private static final int R_AL_AN_EN_ES_CS_ET_ON_BN_NSM_MASK;
    private static int U_GC_M_MASK;
    
    public UTS46(final int options) {
        this.options = options;
    }
    
    @Override
    public StringBuilder labelToASCII(final CharSequence charSequence, final StringBuilder sb, final Info info) {
        return this.process(charSequence, true, true, sb, info);
    }
    
    @Override
    public StringBuilder labelToUnicode(final CharSequence charSequence, final StringBuilder sb, final Info info) {
        return this.process(charSequence, true, false, sb, info);
    }
    
    @Override
    public StringBuilder nameToASCII(final CharSequence charSequence, final StringBuilder sb, final Info info) {
        this.process(charSequence, false, true, sb, info);
        if (sb.length() >= 254 && !info.getErrors().contains(Error.DOMAIN_NAME_TOO_LONG) && isASCIIString(sb) && (sb.length() > 254 || sb.charAt(253) != '.')) {
            IDNA.addError(info, Error.DOMAIN_NAME_TOO_LONG);
        }
        return sb;
    }
    
    @Override
    public StringBuilder nameToUnicode(final CharSequence charSequence, final StringBuilder sb, final Info info) {
        return this.process(charSequence, false, false, sb, info);
    }
    
    private static boolean isASCIIString(final CharSequence charSequence) {
        while (0 < charSequence.length()) {
            if (charSequence.charAt(0) > '\u007f') {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private StringBuilder process(final CharSequence charSequence, final boolean b, final boolean b2, final StringBuilder sb, final Info info) {
        if (sb == charSequence) {
            throw new IllegalArgumentException();
        }
        sb.delete(0, Integer.MAX_VALUE);
        IDNA.resetInfo(info);
        final int length = charSequence.length();
        if (length == 0) {
            if (b2) {
                IDNA.addError(info, Error.EMPTY_LABEL);
            }
            return sb;
        }
        final boolean b3 = (this.options & 0x2) != 0x0;
        while (length) {
            final char char1 = charSequence.charAt(0);
            Label_0345: {
                if (char1 <= '\u007f') {
                    final byte b4 = UTS46.asciiData[char1];
                    int n = 0;
                    if (b4 > 0) {
                        sb.append((char)(char1 + ' '));
                    }
                    else {
                        if (b4 < 0 && b3) {
                            break Label_0345;
                        }
                        sb.append(char1);
                        if (char1 == '-') {
                            if (0 == 3 && charSequence.charAt(-1) == '-') {
                                ++n;
                                break Label_0345;
                            }
                            if (!false) {
                                IDNA.addLabelError(info, Error.LEADING_HYPHEN);
                            }
                            if (length || charSequence.charAt(1) == '.') {
                                IDNA.addLabelError(info, Error.TRAILING_HYPHEN);
                            }
                        }
                        else if (char1 == '.') {
                            if (b) {
                                ++n;
                                break Label_0345;
                            }
                            if (b2) {
                                if (!false && 0 < length - 1) {
                                    IDNA.addLabelError(info, Error.EMPTY_LABEL);
                                }
                                else if (0 > 63) {
                                    IDNA.addLabelError(info, Error.LABEL_TOO_LONG);
                                }
                            }
                            IDNA.promoteAndResetLabelErrors(info);
                        }
                    }
                    ++n;
                    continue;
                }
            }
            IDNA.promoteAndResetLabelErrors(info);
            this.processUnicode(charSequence, 0, 0, b, b2, sb, info);
            if (IDNA.isBiDi(info) && !IDNA.hasCertainErrors(info, UTS46.severeErrors) && (!IDNA.isOkBiDi(info) || (0 > 0 && !isASCIIOkBiDi(sb, 0)))) {
                IDNA.addError(info, Error.BIDI);
            }
            return sb;
        }
        if (b2) {
            if (0 > 63) {
                IDNA.addLabelError(info, Error.LABEL_TOO_LONG);
            }
            if (!b && 0 >= 254 && (0 > 254 || 0 < 0)) {
                IDNA.addError(info, Error.DOMAIN_NAME_TOO_LONG);
            }
        }
        IDNA.promoteAndResetLabelErrors(info);
        return sb;
    }
    
    private StringBuilder processUnicode(final CharSequence charSequence, int n, final int n2, final boolean b, final boolean b2, final StringBuilder sb, final Info transitionalDifferent) {
        if (n2 == 0) {
            UTS46.uts46Norm2.normalize(charSequence, sb);
        }
        else {
            UTS46.uts46Norm2.normalizeSecondAndAppend(sb, charSequence.subSequence(n2, charSequence.length()));
        }
        final boolean b3 = b2 ? ((this.options & 0x10) == 0x0) : ((this.options & 0x20) == 0x0);
        int n3 = sb.length();
        int i = n;
        while (i < n3) {
            final char char1 = sb.charAt(i);
            if (char1 == '.' && !b) {
                final int n4 = i - n;
                final int processLabel = this.processLabel(sb, n, n4, b2, transitionalDifferent);
                IDNA.promoteAndResetLabelErrors(transitionalDifferent);
                n3 += processLabel - n4;
                n = (i = n + (processLabel + 1));
            }
            else if ('\u00df' <= char1 && char1 <= '\u200d' && (char1 == '\u00df' || char1 == '\u03c2' || char1 >= '\u200c')) {
                IDNA.setTransitionalDifferent(transitionalDifferent);
                if (false) {
                    n3 = this.mapDevChars(sb, n, i);
                }
                else {
                    ++i;
                }
            }
            else {
                ++i;
            }
        }
        if (0 == n || n < i) {
            this.processLabel(sb, n, i - n, b2, transitionalDifferent);
            IDNA.promoteAndResetLabelErrors(transitionalDifferent);
        }
        return sb;
    }
    
    private int mapDevChars(final StringBuilder sb, final int n, final int n2) {
        int length = sb.length();
        int i = n2;
        while (i < length) {
            switch (sb.charAt(i)) {
                case '\u00df': {
                    sb.setCharAt(i++, 's');
                    sb.insert(i++, 's');
                    ++length;
                    continue;
                }
                case '\u03c2': {
                    sb.setCharAt(i++, '\u03c3');
                    continue;
                }
                case '\u200c':
                case '\u200d': {
                    sb.delete(i, i + 1);
                    --length;
                    continue;
                }
                default: {
                    ++i;
                    continue;
                }
            }
        }
        if (true) {
            sb.replace(n, Integer.MAX_VALUE, UTS46.uts46Norm2.normalize(sb.subSequence(n, sb.length())));
            return sb.length();
        }
        return length;
    }
    
    private static boolean isNonASCIIDisallowedSTD3Valid(final int n) {
        return n == 8800 || n == 8814 || n == 8815;
    }
    
    private static int replaceLabel(final StringBuilder sb, final int n, final int n2, final CharSequence charSequence, final int n3) {
        if (charSequence != sb) {
            sb.delete(n, n + n2).insert(n, charSequence);
        }
        return n3;
    }
    
    private int processLabel(final StringBuilder sb, final int n, int length, final boolean b, final Info info) {
        int n2 = length;
        int normalized = 0;
        StringBuilder sb2;
        if (length >= 4 && sb.charAt(0) == 'x' && sb.charAt(1) == 'n' && sb.charAt(2) == '-' && sb.charAt(3) == '-') {
            final StringBuilder decode = Punycode.decode(sb.subSequence(4, 0 + length), null);
            normalized = (UTS46.uts46Norm2.isNormalized(decode) ? 1 : 0);
            if (!false) {
                IDNA.addLabelError(info, Error.INVALID_ACE_LABEL);
                return this.markBadACELabel(sb, 0, length, b, info);
            }
            sb2 = decode;
            length = decode.length();
        }
        else {
            sb2 = sb;
        }
        if (length == 0) {
            if (b) {
                IDNA.addLabelError(info, Error.EMPTY_LABEL);
            }
            return replaceLabel(sb, 0, n2, sb2, length);
        }
        if (length >= 4 && sb2.charAt(2) == '-' && sb2.charAt(3) == '-') {
            IDNA.addLabelError(info, Error.HYPHEN_3_4);
        }
        if (sb2.charAt(0) == '-') {
            IDNA.addLabelError(info, Error.LEADING_HYPHEN);
        }
        if (sb2.charAt(0 + length - 1) == '-') {
            IDNA.addLabelError(info, Error.TRAILING_HYPHEN);
        }
        final int n3 = 0 + length;
        final boolean b2 = (this.options & 0x2) != 0x0;
        do {
            final char char1 = sb2.charAt(0);
            if (char1 <= '\u007f') {
                if (char1 == '.') {
                    IDNA.addLabelError(info, Error.LABEL_HAS_DOT);
                    sb2.setCharAt(0, '\ufffd');
                }
                else if (b2 && UTS46.asciiData[char1] < 0) {
                    IDNA.addLabelError(info, Error.DISALLOWED);
                    sb2.setCharAt(0, '\ufffd');
                }
            }
            else {
                final char c = (char)('\0' | char1);
                if (b2 && isNonASCIIDisallowedSTD3Valid(char1)) {
                    IDNA.addLabelError(info, Error.DISALLOWED);
                    sb2.setCharAt(0, '\ufffd');
                }
                else if (char1 == '\ufffd') {
                    IDNA.addLabelError(info, Error.DISALLOWED);
                }
            }
            ++normalized;
        } while (0 < n3);
        final int codePoint = sb2.codePointAt(0);
        if ((U_GET_GC_MASK(codePoint) & UTS46.U_GC_M_MASK) != 0x0) {
            IDNA.addLabelError(info, Error.LEADING_COMBINING_MARK);
            sb2.setCharAt(0, '\ufffd');
            if (codePoint > 65535) {
                sb2.deleteCharAt(1);
                --length;
                if (sb2 == sb) {
                    --n2;
                }
            }
        }
        if (!IDNA.hasCertainLabelErrors(info, UTS46.severeErrors)) {
            if ((this.options & 0x4) != 0x0 && (!IDNA.isBiDi(info) || IDNA.isOkBiDi(info))) {
                this.checkLabelBiDi(sb2, 0, length, info);
            }
            if ((this.options & 0x8) != 0x0 && 0 == 8204 && !this.isLabelOkContextJ(sb2, 0, length)) {
                IDNA.addLabelError(info, Error.CONTEXTJ);
            }
            if ((this.options & 0x40) != 0x0 && 0 >= 183) {
                this.checkLabelContextO(sb2, 0, length, info);
            }
            if (b) {
                if (false) {
                    if (n2 > 63) {
                        IDNA.addLabelError(info, Error.LABEL_TOO_LONG);
                    }
                    return n2;
                }
                if (0 >= 128) {
                    final StringBuilder encode = Punycode.encode(sb2.subSequence(0, 0 + length), null);
                    encode.insert(0, "xn--");
                    if (encode.length() > 63) {
                        IDNA.addLabelError(info, Error.LABEL_TOO_LONG);
                    }
                    return replaceLabel(sb, 0, n2, encode, encode.length());
                }
                if (length > 63) {
                    IDNA.addLabelError(info, Error.LABEL_TOO_LONG);
                }
            }
        }
        else if (false) {
            IDNA.addLabelError(info, Error.INVALID_ACE_LABEL);
            return this.markBadACELabel(sb, 0, n2, b, info);
        }
        return replaceLabel(sb, 0, n2, sb2, length);
    }
    
    private int markBadACELabel(final StringBuilder sb, final int n, int n2, final boolean b, final Info info) {
        final boolean b2 = (this.options & 0x2) != 0x0;
        int n3 = n + 4;
        do {
            final char char1 = sb.charAt(n3);
            if (char1 <= '\u007f') {
                if (char1 == '.') {
                    IDNA.addLabelError(info, Error.LABEL_HAS_DOT);
                    sb.setCharAt(n3, '\ufffd');
                }
                else {
                    if (UTS46.asciiData[char1] >= 0 || !b2) {
                        continue;
                    }
                    sb.setCharAt(n3, '\ufffd');
                }
            }
        } while (++n3 < n + n2);
        if (false) {
            sb.insert(n + n2, '\ufffd');
            ++n2;
        }
        else if (b && false && n2 > 63) {
            IDNA.addLabelError(info, Error.LABEL_TOO_LONG);
        }
        return n2;
    }
    
    private void checkLabelBiDi(final CharSequence charSequence, final int n, final int n2, final Info info) {
        final int codePoint = Character.codePointAt(charSequence, n);
        int i = n + Character.charCount(codePoint);
        final int u_MASK = U_MASK(UBiDiProps.INSTANCE.getClass(codePoint));
        if ((u_MASK & ~UTS46.L_R_AL_MASK) != 0x0) {
            IDNA.setNotOkBiDi(info);
        }
        int n3 = n + n2;
        while (true) {
            while (i < n3) {
                final int codePointBefore = Character.codePointBefore(charSequence, n3);
                n3 -= Character.charCount(codePointBefore);
                UBiDiProps.INSTANCE.getClass(codePointBefore);
                if (0 != 17) {
                    final int u_MASK2 = U_MASK(0);
                    while (true) {
                        Label_0149: {
                            if ((u_MASK & UTS46.L_MASK) != 0x0) {
                                if ((u_MASK2 & ~UTS46.L_EN_MASK) != 0x0) {
                                    break Label_0149;
                                }
                            }
                            else if ((u_MASK2 & ~UTS46.R_AL_EN_AN_MASK) != 0x0) {
                                break Label_0149;
                            }
                            while (i < n3) {
                                final int codePoint2 = Character.codePointAt(charSequence, i);
                                i += Character.charCount(codePoint2);
                                final int n4 = 0x0 | U_MASK(UBiDiProps.INSTANCE.getClass(codePoint2));
                            }
                            if ((u_MASK & UTS46.L_MASK) != 0x0) {
                                if ((0x0 & ~UTS46.L_EN_ES_CS_ET_ON_BN_NSM_MASK) != 0x0) {
                                    IDNA.setNotOkBiDi(info);
                                }
                            }
                            else {
                                if ((0x0 & ~UTS46.R_AL_AN_EN_ES_CS_ET_ON_BN_NSM_MASK) != 0x0) {
                                    IDNA.setNotOkBiDi(info);
                                }
                                if ((0x0 & UTS46.EN_AN_MASK) == UTS46.EN_AN_MASK) {
                                    IDNA.setNotOkBiDi(info);
                                }
                            }
                            if (((u_MASK | 0x0 | u_MASK2) & UTS46.R_AL_AN_MASK) != 0x0) {
                                IDNA.setBiDi(info);
                            }
                            return;
                        }
                        IDNA.setNotOkBiDi(info);
                        continue;
                    }
                }
            }
            final int u_MASK2 = u_MASK;
            continue;
        }
    }
    
    private static boolean isASCIIOkBiDi(final CharSequence charSequence, final int n) {
        while (0 < n) {
            final char char1 = charSequence.charAt(0);
            if (char1 == '.') {
                if (0 > 0) {
                    final char char2 = charSequence.charAt(-1);
                    if (('a' > char2 || char2 > 'z') && ('0' > char2 || char2 > '9')) {
                        return false;
                    }
                }
            }
            else if (!false) {
                if ('a' > char1 || char1 > 'z') {
                    return false;
                }
            }
            else if (char1 <= ' ' && (char1 >= '\u001c' || ('\t' <= char1 && char1 <= '\r'))) {
                return false;
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    private boolean isLabelOkContextJ(final CharSequence charSequence, final int n, final int n2) {
    Label_0259:
        for (int n3 = n + n2, i = n; i < n3; ++i) {
            if (charSequence.charAt(i) == '\u200c') {
                if (i == n) {
                    return false;
                }
                final int n4 = i;
                int n5 = Character.codePointBefore(charSequence, n4);
                int n6 = n4 - Character.charCount(n5);
                if (UTS46.uts46Norm2.getCombiningClass(n5) != 9) {
                    while (true) {
                        final int joiningType = UBiDiProps.INSTANCE.getJoiningType(n5);
                        if (joiningType == 5) {
                            if (n6 == 0) {
                                return false;
                            }
                            n5 = Character.codePointBefore(charSequence, n6);
                            n6 -= Character.charCount(n5);
                        }
                        else {
                            if (joiningType != 3 && joiningType != 2) {
                                return false;
                            }
                            int j = i + 1;
                            while (j != n3) {
                                final int codePoint = Character.codePointAt(charSequence, j);
                                j += Character.charCount(codePoint);
                                final int joiningType2 = UBiDiProps.INSTANCE.getJoiningType(codePoint);
                                if (joiningType2 == 5) {
                                    continue;
                                }
                                if (joiningType2 != 4 && joiningType2 != 2) {
                                    return false;
                                }
                                continue Label_0259;
                            }
                            return false;
                        }
                    }
                }
            }
            else if (charSequence.charAt(i) == '\u200d') {
                if (i == n) {
                    return false;
                }
                if (UTS46.uts46Norm2.getCombiningClass(Character.codePointBefore(charSequence, i)) != 9) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void checkLabelContextO(final CharSequence charSequence, final int n, final int n2, final Info info) {
    Label_0332:
        for (int n3 = n + n2 - 1, i = n; i <= n3; ++i) {
            final char char1 = charSequence.charAt(i);
            if (char1 >= '·') {
                if (char1 <= '\u06f9') {
                    if (char1 == '·') {
                        if (n >= i || charSequence.charAt(i - 1) != 'l' || i >= n3 || charSequence.charAt(i + 1) != 'l') {
                            IDNA.addLabelError(info, Error.CONTEXTO_PUNCTUATION);
                        }
                    }
                    else if (char1 == '\u0375') {
                        if (i >= n3 || 14 != UScript.getScript(Character.codePointAt(charSequence, i + 1))) {
                            IDNA.addLabelError(info, Error.CONTEXTO_PUNCTUATION);
                        }
                    }
                    else if (char1 == '\u05f3' || char1 == '\u05f4') {
                        if (n >= i || 19 != UScript.getScript(Character.codePointBefore(charSequence, i))) {
                            IDNA.addLabelError(info, Error.CONTEXTO_PUNCTUATION);
                        }
                    }
                    else if ('\u0660' <= char1) {
                        if (char1 <= '\u0669') {
                            if (1 > 0) {
                                IDNA.addLabelError(info, Error.CONTEXTO_DIGITS);
                            }
                        }
                        else if ('\u06f0' <= char1) {
                            if (1 < 0) {
                                IDNA.addLabelError(info, Error.CONTEXTO_DIGITS);
                            }
                        }
                    }
                }
                else if (char1 == '\u30fb') {
                    int codePoint;
                    for (int j = n; j <= n3; j += Character.charCount(codePoint)) {
                        codePoint = Character.codePointAt(charSequence, j);
                        final int script = UScript.getScript(codePoint);
                        if (script == 20 || script == 22) {
                            continue Label_0332;
                        }
                        if (script == 17) {
                            continue Label_0332;
                        }
                    }
                    IDNA.addLabelError(info, Error.CONTEXTO_PUNCTUATION);
                }
            }
        }
    }
    
    private static int U_MASK(final int n) {
        return 1 << n;
    }
    
    private static int U_GET_GC_MASK(final int n) {
        return 1 << UCharacter.getType(n);
    }
    
    static {
        uts46Norm2 = Normalizer2.getInstance(null, "uts46", Normalizer2.Mode.COMPOSE);
        severeErrors = EnumSet.of(Error.LEADING_COMBINING_MARK, Error.DISALLOWED, Error.PUNYCODE, Error.LABEL_HAS_DOT, Error.INVALID_ACE_LABEL);
        asciiData = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1 };
        L_MASK = U_MASK(0);
        R_AL_MASK = (U_MASK(1) | U_MASK(13));
        L_R_AL_MASK = (UTS46.L_MASK | UTS46.R_AL_MASK);
        R_AL_AN_MASK = (UTS46.R_AL_MASK | U_MASK(5));
        EN_AN_MASK = (U_MASK(2) | U_MASK(5));
        R_AL_EN_AN_MASK = (UTS46.R_AL_MASK | UTS46.EN_AN_MASK);
        L_EN_MASK = (UTS46.L_MASK | U_MASK(2));
        ES_CS_ET_ON_BN_NSM_MASK = (U_MASK(3) | U_MASK(6) | U_MASK(4) | U_MASK(10) | U_MASK(18) | U_MASK(17));
        L_EN_ES_CS_ET_ON_BN_NSM_MASK = (UTS46.L_EN_MASK | UTS46.ES_CS_ET_ON_BN_NSM_MASK);
        R_AL_AN_EN_ES_CS_ET_ON_BN_NSM_MASK = (UTS46.R_AL_MASK | UTS46.EN_AN_MASK | UTS46.ES_CS_ET_ON_BN_NSM_MASK);
        UTS46.U_GC_M_MASK = (U_MASK(6) | U_MASK(7) | U_MASK(8));
    }
}
