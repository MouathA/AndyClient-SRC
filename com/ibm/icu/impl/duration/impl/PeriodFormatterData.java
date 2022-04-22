package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.impl.duration.*;

public class PeriodFormatterData
{
    final DataRecord dr;
    String localeName;
    public static boolean trace;
    private static final int FORM_PLURAL = 0;
    private static final int FORM_SINGULAR = 1;
    private static final int FORM_DUAL = 2;
    private static final int FORM_PAUCAL = 3;
    private static final int FORM_SINGULAR_SPELLED = 4;
    private static final int FORM_SINGULAR_NO_OMIT = 5;
    private static final int FORM_HALF_SPELLED = 6;
    
    public PeriodFormatterData(final String localeName, final DataRecord dr) {
        this.dr = dr;
        this.localeName = localeName;
        if (localeName == null) {
            throw new NullPointerException("localename is null");
        }
        if (dr == null) {
            throw new NullPointerException("data record is null");
        }
    }
    
    public int pluralization() {
        return this.dr.pl;
    }
    
    public boolean allowZero() {
        return this.dr.allowZero;
    }
    
    public boolean weeksAloneOnly() {
        return this.dr.weeksAloneOnly;
    }
    
    public int useMilliseconds() {
        return this.dr.useMilliseconds;
    }
    
    public boolean appendPrefix(final int n, final int n2, final StringBuffer sb) {
        if (this.dr.scopeData != null) {
            final DataRecord.ScopeData scopeData = this.dr.scopeData[n * 3 + n2];
            if (scopeData != null) {
                final String prefix = scopeData.prefix;
                if (prefix != null) {
                    sb.append(prefix);
                    return scopeData.requiresDigitPrefix;
                }
            }
        }
        return false;
    }
    
    public void appendSuffix(final int n, final int n2, final StringBuffer sb) {
        if (this.dr.scopeData != null) {
            final DataRecord.ScopeData scopeData = this.dr.scopeData[n * 3 + n2];
            if (scopeData != null) {
                final String suffix = scopeData.suffix;
                if (suffix != null) {
                    if (PeriodFormatterData.trace) {
                        System.out.println("appendSuffix '" + suffix + "'");
                    }
                    sb.append(suffix);
                }
            }
        }
    }
    
    public boolean appendUnit(final TimeUnit timeUnit, int n, final int n2, final int n3, final boolean b, final boolean b2, final boolean b3, final boolean b4, final boolean b5, final StringBuffer sb) {
        final int ordinal = timeUnit.ordinal();
        if (this.dr.requiresSkipMarker != null && this.dr.requiresSkipMarker[ordinal] && this.dr.skippedUnitMarker != null && !b5 && b4) {
            sb.append(this.dr.skippedUnitMarker);
        }
        if (n3 != 0) {
            final boolean b6 = n3 == 1;
            String[] array = b6 ? this.dr.mediumNames : this.dr.shortNames;
            if (array == null || array[ordinal] == null) {
                array = (b6 ? this.dr.shortNames : this.dr.mediumNames);
            }
            if (array != null && array[ordinal] != null) {
                this.appendCount(timeUnit, false, false, n, 3, b, array[ordinal], b4, sb);
                return false;
            }
        }
        if (3 == 2 && this.dr.halfSupport != null) {
            switch (this.dr.halfSupport[ordinal]) {
                case 2: {
                    if (n > 1000) {
                        break;
                    }
                }
                case 1: {
                    n = n / 500 * 500;
                    break;
                }
            }
        }
        this.computeForm(timeUnit, n, 3, b3 && b4);
        String s;
        if (0 == 4) {
            if (this.dr.singularNames == null) {
                s = this.dr.pluralNames[ordinal][0];
            }
            else {
                s = this.dr.singularNames[ordinal];
            }
        }
        else if (0 == 5) {
            s = this.dr.pluralNames[ordinal][1];
        }
        else if (0 == 6) {
            s = this.dr.halfNames[ordinal];
        }
        else {
            s = this.dr.pluralNames[ordinal][0];
        }
        if (s == null) {
            s = this.dr.pluralNames[ordinal][0];
        }
        final int appendCount = this.appendCount(timeUnit, 0 == 4 || 0 == 6 || (this.dr.omitSingularCount && false == true) || (this.dr.omitDualCount && 0 == 2), b2, n, 3, b, s, b4, sb);
        if (b4 && appendCount >= 0) {
            String s2 = null;
            if (this.dr.rqdSuffixes != null && appendCount < this.dr.rqdSuffixes.length) {
                s2 = this.dr.rqdSuffixes[appendCount];
            }
            if (s2 == null && this.dr.optSuffixes != null && appendCount < this.dr.optSuffixes.length) {
                s2 = this.dr.optSuffixes[appendCount];
            }
            if (s2 != null) {
                sb.append(s2);
            }
        }
        return true;
    }
    
    public int appendCount(final TimeUnit timeUnit, final boolean b, final boolean b2, final int n, final int n2, final boolean b3, String s, final boolean b4, final StringBuffer sb) {
        if (0 != 2 || this.dr.halves == null) {}
        if (!b && b2 && this.dr.digitPrefix != null) {
            sb.append(this.dr.digitPrefix);
        }
        timeUnit.ordinal();
        switch (false) {
            case 0: {
                if (!b) {
                    this.appendInteger(n / 1000, 1, 10, sb);
                    break;
                }
                break;
            }
            case 1: {
                if (timeUnit == TimeUnit.MINUTE && (this.dr.fiveMinutes != null || this.dr.fifteenMinutes != null) && 3 != 0 && 3 == 0) {
                    if (this.dr.fifteenMinutes != null && (3 == 15 || 3 == 45)) {
                        final int n3 = (3 == 15) ? 1 : 3;
                        if (!b) {
                            this.appendInteger(3, 1, 10, sb);
                        }
                        s = this.dr.fifteenMinutes;
                        break;
                    }
                    if (this.dr.fiveMinutes != null) {
                        if (!b) {
                            this.appendInteger(3, 1, 10, sb);
                        }
                        s = this.dr.fiveMinutes;
                        break;
                    }
                }
                if (!b) {
                    this.appendInteger(3, 1, 10, sb);
                }
                break;
            }
            case 2: {
                if (3 != 1 && !b) {
                    this.appendCountValue(n, 1, 0, sb);
                }
                if (true == true) {
                    if (3 == 1 && this.dr.halfNames != null && this.dr.halfNames[9] != null) {
                        sb.append(s);
                        return b4 ? 9 : -1;
                    }
                    int n4 = (3 != 1) ? 1 : 0;
                    if (this.dr.genders != null && this.dr.halves.length > 2 && this.dr.genders[9] == 1) {
                        n4 += 2;
                    }
                    final byte b5 = (byte)((this.dr.halfPlacements == null) ? 0 : this.dr.halfPlacements[n4 & 0x1]);
                    final String s2 = this.dr.halves[n4];
                    final String s3 = (this.dr.measures == null) ? null : this.dr.measures[9];
                    switch (b5) {
                        case 0: {
                            sb.append(s2);
                            break;
                        }
                        case 1: {
                            if (s3 != null) {
                                sb.append(s3);
                                sb.append(s2);
                                if (b3 && !b) {
                                    sb.append(this.dr.countSep);
                                }
                                sb.append(s);
                                return -1;
                            }
                            sb.append(s);
                            sb.append(s2);
                            return b4 ? 9 : -1;
                        }
                        case 2: {
                            if (s3 != null) {
                                sb.append(s3);
                            }
                            if (b3 && !b) {
                                sb.append(this.dr.countSep);
                            }
                            sb.append(s);
                            sb.append(s2);
                            return b4 ? 9 : -1;
                        }
                    }
                }
                break;
            }
            default: {
                switch (false) {
                }
                if (!b) {
                    this.appendCountValue(n, 1, 3, sb);
                    break;
                }
                break;
            }
        }
        if (!b && b3) {
            sb.append(this.dr.countSep);
        }
        if (!b && this.dr.measures != null && 9 < this.dr.measures.length) {
            final String s4 = this.dr.measures[9];
            if (s4 != null) {
                sb.append(s4);
            }
        }
        sb.append(s);
        return b4 ? 9 : -1;
    }
    
    public void appendCountValue(final int n, final int n2, final int n3, final StringBuffer sb) {
        final int n4 = n / 1000;
        if (n3 == 0) {
            this.appendInteger(n4, n2, 10, sb);
            return;
        }
        if (this.dr.requiresDigitSeparator && sb.length() > 0) {
            sb.append(' ');
        }
        this.appendDigits(n4, n2, 10, sb);
        int n5 = n % 1000;
        if (n3 == 1) {
            n5 /= 100;
        }
        else if (n3 == 2) {
            n5 /= 10;
        }
        sb.append(this.dr.decimalSep);
        this.appendDigits(n5, n3, n3, sb);
        if (this.dr.requiresDigitSeparator) {
            sb.append(' ');
        }
    }
    
    public void appendInteger(final int n, final int n2, final int n3, final StringBuffer sb) {
        if (this.dr.numberNames != null && n < this.dr.numberNames.length) {
            final String s = this.dr.numberNames[n];
            if (s != null) {
                sb.append(s);
                return;
            }
        }
        if (this.dr.requiresDigitSeparator && sb.length() > 0) {
            sb.append(' ');
        }
        switch (this.dr.numberSystem) {
            case 0: {
                this.appendDigits(n, n2, n3, sb);
                break;
            }
            case 1: {
                sb.append(Utils.chineseNumber(n, Utils.ChineseDigits.TRADITIONAL));
                break;
            }
            case 2: {
                sb.append(Utils.chineseNumber(n, Utils.ChineseDigits.SIMPLIFIED));
                break;
            }
            case 3: {
                sb.append(Utils.chineseNumber(n, Utils.ChineseDigits.KOREAN));
                break;
            }
        }
        if (this.dr.requiresDigitSeparator) {
            sb.append(' ');
        }
    }
    
    public void appendDigits(long n, final int n2, final int n3, final StringBuffer sb) {
        char[] array;
        int i;
        for (array = new char[n3], i = n3; i > 0 && n > 0L; array[--i] = (char)(this.dr.zero + n % 10L), n /= 10L) {}
        while (i > n3 - n2) {
            array[--i] = this.dr.zero;
        }
        sb.append(array, i, n3 - i);
    }
    
    public void appendSkippedUnit(final StringBuffer sb) {
        if (this.dr.skippedUnitMarker != null) {
            sb.append(this.dr.skippedUnitMarker);
        }
    }
    
    public boolean appendUnitSeparator(final TimeUnit timeUnit, final boolean b, final boolean b2, final boolean b3, final StringBuffer sb) {
        if ((b && this.dr.unitSep != null) || this.dr.shortUnitSep != null) {
            if (b && this.dr.unitSep != null) {
                final int n = (b2 ? 2 : 0) + (b3 ? 1 : 0);
                sb.append(this.dr.unitSep[n]);
                return this.dr.unitSepRequiresDP != null && this.dr.unitSepRequiresDP[n];
            }
            sb.append(this.dr.shortUnitSep);
        }
        return false;
    }
    
    private int computeForm(final TimeUnit timeUnit, final int n, final int n2, final boolean b) {
        if (PeriodFormatterData.trace) {
            System.err.println("pfd.cf unit: " + timeUnit + " count: " + n + " cv: " + n2 + " dr.pl: " + this.dr.pl);
            Thread.dumpStack();
        }
        if (this.dr.pl == 0) {
            return 0;
        }
        final int n3 = n / 1000;
        Label_0353: {
            switch (n2) {
                case 0:
                case 1: {
                    break;
                }
                case 2: {
                    switch (this.dr.fractionHandling) {
                        case 0: {
                            return 0;
                        }
                        case 1:
                        case 2: {
                            if (5 != 1) {
                                if (true == true) {
                                    if (this.dr.pl == 5 && 5 > 21) {
                                        return 5;
                                    }
                                    if (5 == 3 && this.dr.pl == 1 && this.dr.fractionHandling != 2) {
                                        return 0;
                                    }
                                }
                                break Label_0353;
                            }
                            if (this.dr.halfNames != null && this.dr.halfNames[timeUnit.ordinal()] != null) {
                                return 6;
                            }
                            return 5;
                        }
                        case 3: {
                            if (5 == 1 || 5 == 3) {
                                return 3;
                            }
                            break Label_0353;
                        }
                        default: {
                            throw new IllegalStateException();
                        }
                    }
                    break;
                }
                default: {
                    switch (this.dr.decimalHandling) {
                        case 1: {
                            return 5;
                        }
                        case 2: {
                            if (n < 1000) {
                                return 5;
                            }
                            break;
                        }
                        case 3: {
                            if (this.dr.pl == 3) {
                                return 3;
                            }
                            break;
                        }
                    }
                    return 0;
                }
            }
        }
        if (PeriodFormatterData.trace && n == 0) {
            System.err.println("EZeroHandling = " + this.dr.zeroHandling);
        }
        if (n == 0 && this.dr.zeroHandling == 1) {
            return 4;
        }
        switch (this.dr.pl) {
            case 0: {
                break;
            }
            case 1: {
                if (n3 == 1) {
                    break;
                }
                break;
            }
            case 2: {
                if (n3 == 2) {
                    break;
                }
                if (n3 == 1) {
                    break;
                }
                break;
            }
            case 3: {
                int n4 = n3 % 100;
                if (n4 > 20) {
                    n4 %= 10;
                }
                if (n4 != 1) {
                    if (n4 <= 1 || n4 < 5) {}
                }
                break;
            }
            case 4: {
                if (n3 == 2) {
                    break;
                }
                if (n3 == 1) {
                    if (b) {
                        break;
                    }
                    break;
                }
                else {
                    if (timeUnit == TimeUnit.YEAR && n3 > 11) {
                        break;
                    }
                    break;
                }
                break;
            }
            case 5: {
                if (n3 == 2) {
                    break;
                }
                if (n3 == 1) {
                    break;
                }
                if (n3 > 10) {
                    break;
                }
                break;
            }
            default: {
                System.err.println("dr.pl is " + this.dr.pl);
                throw new IllegalStateException();
            }
        }
        return 5;
    }
    
    static {
        PeriodFormatterData.trace = false;
    }
}
