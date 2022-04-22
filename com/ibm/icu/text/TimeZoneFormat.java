package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.text.*;
import java.util.*;
import com.ibm.icu.lang.*;
import java.io.*;
import com.ibm.icu.impl.*;

public class TimeZoneFormat extends UFormat implements Freezable, Serializable
{
    private static final long serialVersionUID = 2281246852693575022L;
    private static final int ISO_Z_STYLE_FLAG = 128;
    private static final int ISO_LOCAL_STYLE_FLAG = 256;
    private ULocale _locale;
    private TimeZoneNames _tznames;
    private String _gmtPattern;
    private String[] _gmtOffsetPatterns;
    private String[] _gmtOffsetDigits;
    private String _gmtZeroFormat;
    private boolean _parseAllStyles;
    private transient TimeZoneGenericNames _gnames;
    private transient String _gmtPatternPrefix;
    private transient String _gmtPatternSuffix;
    private transient Object[][] _gmtOffsetPatternItems;
    private transient boolean _abuttingOffsetHoursAndMinutes;
    private transient String _region;
    private transient boolean _frozen;
    private static final String TZID_GMT = "Etc/GMT";
    private static final String DEFAULT_GMT_PATTERN = "GMT{0}";
    private static final String DEFAULT_GMT_ZERO = "GMT";
    private static final String[] DEFAULT_GMT_DIGITS;
    private static final char DEFAULT_GMT_OFFSET_SEP = ':';
    private static final String ASCII_DIGITS = "0123456789";
    private static final String ISO8601_UTC = "Z";
    private static final String UNKNOWN_ZONE_ID = "Etc/Unknown";
    private static final String UNKNOWN_SHORT_ZONE_ID = "unk";
    private static final String UNKNOWN_LOCATION = "Unknown";
    private static final GMTOffsetPatternType[] PARSE_GMT_OFFSET_TYPES;
    private static final int MILLIS_PER_HOUR = 3600000;
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final int MILLIS_PER_SECOND = 1000;
    private static final int MAX_OFFSET = 86400000;
    private static final int MAX_OFFSET_HOUR = 23;
    private static final int MAX_OFFSET_MINUTE = 59;
    private static final int MAX_OFFSET_SECOND = 59;
    private static final int UNKNOWN_OFFSET = Integer.MAX_VALUE;
    private static TimeZoneFormatCache _tzfCache;
    private static final EnumSet ALL_SIMPLE_NAME_TYPES;
    private static final EnumSet ALL_GENERIC_NAME_TYPES;
    private static TextTrieMap ZONE_ID_TRIE;
    private static TextTrieMap SHORT_ZONE_ID_TRIE;
    private static final ObjectStreamField[] serialPersistentFields;
    static final boolean $assertionsDisabled;
    
    protected TimeZoneFormat(final ULocale locale) {
        this._locale = locale;
        this._tznames = TimeZoneNames.getInstance(locale);
        this._gmtZeroFormat = "GMT";
        final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b/zone", locale);
        String stringWithFallback = icuResourceBundle.getStringWithFallback("zoneStrings/gmtFormat");
        final String stringWithFallback2 = icuResourceBundle.getStringWithFallback("zoneStrings/hourFormat");
        this._gmtZeroFormat = icuResourceBundle.getStringWithFallback("zoneStrings/gmtZeroFormat");
        if (stringWithFallback == null) {
            stringWithFallback = "GMT{0}";
        }
        this.initGMTPattern(stringWithFallback);
        final String[] array = new String[GMTOffsetPatternType.values().length];
        if (stringWithFallback2 != null) {
            final String[] split = stringWithFallback2.split(";", 2);
            array[GMTOffsetPatternType.POSITIVE_H.ordinal()] = truncateOffsetPattern(split[0]);
            array[GMTOffsetPatternType.POSITIVE_HM.ordinal()] = split[0];
            array[GMTOffsetPatternType.POSITIVE_HMS.ordinal()] = expandOffsetPattern(split[0]);
            array[GMTOffsetPatternType.NEGATIVE_H.ordinal()] = truncateOffsetPattern(split[1]);
            array[GMTOffsetPatternType.NEGATIVE_HM.ordinal()] = split[1];
            array[GMTOffsetPatternType.NEGATIVE_HMS.ordinal()] = expandOffsetPattern(split[1]);
        }
        else {
            final GMTOffsetPatternType[] values = GMTOffsetPatternType.values();
            while (0 < values.length) {
                final GMTOffsetPatternType gmtOffsetPatternType = values[0];
                array[gmtOffsetPatternType.ordinal()] = GMTOffsetPatternType.access$100(gmtOffsetPatternType);
                int n = 0;
                ++n;
            }
        }
        this.initGMTOffsetPatterns(array);
        this._gmtOffsetDigits = TimeZoneFormat.DEFAULT_GMT_DIGITS;
        final NumberingSystem instance = NumberingSystem.getInstance(locale);
        if (!instance.isAlgorithmic()) {
            this._gmtOffsetDigits = toCodePoints(instance.getDescription());
        }
    }
    
    public static TimeZoneFormat getInstance(final ULocale uLocale) {
        if (uLocale == null) {
            throw new NullPointerException("locale is null");
        }
        return (TimeZoneFormat)TimeZoneFormat._tzfCache.getInstance(uLocale, uLocale);
    }
    
    public TimeZoneNames getTimeZoneNames() {
        return this._tznames;
    }
    
    private TimeZoneGenericNames getTimeZoneGenericNames() {
        if (this._gnames == null) {
            // monitorenter(this)
            if (this._gnames == null) {
                this._gnames = TimeZoneGenericNames.getInstance(this._locale);
            }
        }
        // monitorexit(this)
        return this._gnames;
    }
    
    public TimeZoneFormat setTimeZoneNames(final TimeZoneNames tznames) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        this._tznames = tznames;
        this._gnames = new TimeZoneGenericNames(this._locale, this._tznames);
        return this;
    }
    
    public String getGMTPattern() {
        return this._gmtPattern;
    }
    
    public TimeZoneFormat setGMTPattern(final String s) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        this.initGMTPattern(s);
        return this;
    }
    
    public String getGMTOffsetPattern(final GMTOffsetPatternType gmtOffsetPatternType) {
        return this._gmtOffsetPatterns[gmtOffsetPatternType.ordinal()];
    }
    
    public TimeZoneFormat setGMTOffsetPattern(final GMTOffsetPatternType gmtOffsetPatternType, final String s) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        if (s == null) {
            throw new NullPointerException("Null GMT offset pattern");
        }
        final Object[] offsetPattern = parseOffsetPattern(s, GMTOffsetPatternType.access$200(gmtOffsetPatternType));
        this._gmtOffsetPatterns[gmtOffsetPatternType.ordinal()] = s;
        this._gmtOffsetPatternItems[gmtOffsetPatternType.ordinal()] = offsetPattern;
        this.checkAbuttingHoursAndMinutes();
        return this;
    }
    
    public String getGMTOffsetDigits() {
        final StringBuilder sb = new StringBuilder(this._gmtOffsetDigits.length);
        final String[] gmtOffsetDigits = this._gmtOffsetDigits;
        while (0 < gmtOffsetDigits.length) {
            sb.append(gmtOffsetDigits[0]);
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public TimeZoneFormat setGMTOffsetDigits(final String s) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        if (s == null) {
            throw new NullPointerException("Null GMT offset digits");
        }
        final String[] codePoints = toCodePoints(s);
        if (codePoints.length != 10) {
            throw new IllegalArgumentException("Length of digits must be 10");
        }
        this._gmtOffsetDigits = codePoints;
        return this;
    }
    
    public String getGMTZeroFormat() {
        return this._gmtZeroFormat;
    }
    
    public TimeZoneFormat setGMTZeroFormat(final String gmtZeroFormat) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        if (gmtZeroFormat == null) {
            throw new NullPointerException("Null GMT zero format");
        }
        if (gmtZeroFormat.length() == 0) {
            throw new IllegalArgumentException("Empty GMT zero format");
        }
        this._gmtZeroFormat = gmtZeroFormat;
        return this;
    }
    
    public TimeZoneFormat setDefaultParseOptions(final EnumSet set) {
        this._parseAllStyles = set.contains(ParseOption.ALL_STYLES);
        return this;
    }
    
    public EnumSet getDefaultParseOptions() {
        if (this._parseAllStyles) {
            return EnumSet.of(ParseOption.ALL_STYLES);
        }
        return EnumSet.noneOf(ParseOption.class);
    }
    
    public final String formatOffsetISO8601Basic(final int n, final boolean b, final boolean b2, final boolean b3) {
        return this.formatOffsetISO8601(n, true, b, b2, b3);
    }
    
    public final String formatOffsetISO8601Extended(final int n, final boolean b, final boolean b2, final boolean b3) {
        return this.formatOffsetISO8601(n, false, b, b2, b3);
    }
    
    public String formatOffsetLocalizedGMT(final int n) {
        return this.formatOffsetLocalizedGMT(n, false);
    }
    
    public String formatOffsetShortLocalizedGMT(final int n) {
        return this.formatOffsetLocalizedGMT(n, true);
    }
    
    public final String format(final Style style, final TimeZone timeZone, final long n) {
        return this.format(style, timeZone, n, null);
    }
    
    public String format(final Style style, final TimeZone timeZone, final long n, final Output output) {
        String s = null;
        if (output != null) {
            output.value = TimeType.UNKNOWN;
        }
        switch (style) {
            case GENERIC_LOCATION: {
                s = this.getTimeZoneGenericNames().getGenericLocationName(ZoneMeta.getCanonicalCLDRID(timeZone));
                break;
            }
            case GENERIC_LONG: {
                s = this.getTimeZoneGenericNames().getDisplayName(timeZone, TimeZoneGenericNames.GenericNameType.LONG, n);
                break;
            }
            case GENERIC_SHORT: {
                s = this.getTimeZoneGenericNames().getDisplayName(timeZone, TimeZoneGenericNames.GenericNameType.SHORT, n);
                break;
            }
            case SPECIFIC_LONG: {
                s = this.formatSpecific(timeZone, TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, n, output);
                break;
            }
            case SPECIFIC_SHORT: {
                s = this.formatSpecific(timeZone, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT, n, output);
                break;
            }
        }
        if (s == null) {
            final int[] array = { 0, 0 };
            timeZone.getOffset(n, false, array);
            final int n2 = array[0] + array[1];
            switch (style) {
                case GENERIC_LOCATION:
                case GENERIC_LONG:
                case SPECIFIC_LONG:
                case LOCALIZED_GMT: {
                    s = this.formatOffsetLocalizedGMT(n2);
                    break;
                }
                case GENERIC_SHORT:
                case SPECIFIC_SHORT:
                case LOCALIZED_GMT_SHORT: {
                    s = this.formatOffsetShortLocalizedGMT(n2);
                    break;
                }
                case ISO_BASIC_SHORT: {
                    s = this.formatOffsetISO8601Basic(n2, true, true, true);
                    break;
                }
                case ISO_BASIC_LOCAL_SHORT: {
                    s = this.formatOffsetISO8601Basic(n2, false, true, true);
                    break;
                }
                case ISO_BASIC_FIXED: {
                    s = this.formatOffsetISO8601Basic(n2, true, false, true);
                    break;
                }
                case ISO_BASIC_LOCAL_FIXED: {
                    s = this.formatOffsetISO8601Basic(n2, false, false, true);
                    break;
                }
                case ISO_BASIC_FULL: {
                    s = this.formatOffsetISO8601Basic(n2, true, false, false);
                    break;
                }
                case ISO_BASIC_LOCAL_FULL: {
                    s = this.formatOffsetISO8601Basic(n2, false, false, false);
                    break;
                }
                case ISO_EXTENDED_FIXED: {
                    s = this.formatOffsetISO8601Extended(n2, true, false, true);
                    break;
                }
                case ISO_EXTENDED_LOCAL_FIXED: {
                    s = this.formatOffsetISO8601Extended(n2, false, false, true);
                    break;
                }
                case ISO_EXTENDED_FULL: {
                    s = this.formatOffsetISO8601Extended(n2, true, false, false);
                    break;
                }
                case ISO_EXTENDED_LOCAL_FULL: {
                    s = this.formatOffsetISO8601Extended(n2, false, false, false);
                    break;
                }
                case ZONE_ID: {
                    s = timeZone.getID();
                    break;
                }
                case ZONE_ID_SHORT: {
                    s = ZoneMeta.getShortID(timeZone);
                    if (s == null) {
                        s = "unk";
                        break;
                    }
                    break;
                }
                case EXEMPLAR_LOCATION: {
                    s = this.formatExemplarLocation(timeZone);
                    break;
                }
            }
            if (output != null) {
                output.value = ((array[1] != 0) ? TimeType.DAYLIGHT : TimeType.STANDARD);
            }
        }
        assert s != null;
        return s;
    }
    
    public final int parseOffsetISO8601(final String s, final ParsePosition parsePosition) {
        return parseOffsetISO8601(s, parsePosition, false, null);
    }
    
    public int parseOffsetLocalizedGMT(final String s, final ParsePosition parsePosition) {
        return this.parseOffsetLocalizedGMT(s, parsePosition, false, null);
    }
    
    public int parseOffsetShortLocalizedGMT(final String s, final ParsePosition parsePosition) {
        return this.parseOffsetLocalizedGMT(s, parsePosition, true, null);
    }
    
    public TimeZone parse(final Style style, final String s, final ParsePosition parsePosition, final EnumSet set, Output output) {
        if (output == null) {
            output = new Output(TimeType.UNKNOWN);
        }
        else {
            output.value = TimeType.UNKNOWN;
        }
        final int index = parsePosition.getIndex();
        final int length = s.length();
        final boolean b = style == Style.SPECIFIC_LONG || style == Style.GENERIC_LONG || style == Style.GENERIC_LOCATION;
        final boolean b2 = style == Style.SPECIFIC_SHORT || style == Style.GENERIC_SHORT;
        final ParsePosition parsePosition2 = new ParsePosition(index);
        if (b || b2) {
            final Output output2 = new Output(false);
            final int offsetLocalizedGMT = this.parseOffsetLocalizedGMT(s, parsePosition2, b2, output2);
            if (parsePosition2.getErrorIndex() == -1) {
                if (parsePosition2.getIndex() == length || (boolean)output2.value) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return this.getTimeZoneForOffset(offsetLocalizedGMT);
                }
                parsePosition2.getIndex();
            }
            final int n = 0x0 | (Style.LOCALIZED_GMT.flag | Style.LOCALIZED_GMT_SHORT.flag);
        }
        switch (style) {
            case LOCALIZED_GMT: {
                parsePosition2.setIndex(index);
                parsePosition2.setErrorIndex(-1);
                final int offsetLocalizedGMT2 = this.parseOffsetLocalizedGMT(s, parsePosition2);
                if (parsePosition2.getErrorIndex() == -1) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return this.getTimeZoneForOffset(offsetLocalizedGMT2);
                }
                final int n2 = 0x0 | Style.LOCALIZED_GMT_SHORT.flag;
                break;
            }
            case LOCALIZED_GMT_SHORT: {
                parsePosition2.setIndex(index);
                parsePosition2.setErrorIndex(-1);
                final int offsetShortLocalizedGMT = this.parseOffsetShortLocalizedGMT(s, parsePosition2);
                if (parsePosition2.getErrorIndex() == -1) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return this.getTimeZoneForOffset(offsetShortLocalizedGMT);
                }
                final int n3 = 0x0 | Style.LOCALIZED_GMT.flag;
                break;
            }
            case ISO_BASIC_SHORT:
            case ISO_BASIC_FIXED:
            case ISO_BASIC_FULL:
            case ISO_EXTENDED_FIXED:
            case ISO_EXTENDED_FULL: {
                parsePosition2.setIndex(index);
                parsePosition2.setErrorIndex(-1);
                final int offsetISO8601 = this.parseOffsetISO8601(s, parsePosition2);
                if (parsePosition2.getErrorIndex() == -1) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return this.getTimeZoneForOffset(offsetISO8601);
                }
                break;
            }
            case ISO_BASIC_LOCAL_SHORT:
            case ISO_BASIC_LOCAL_FIXED:
            case ISO_BASIC_LOCAL_FULL:
            case ISO_EXTENDED_LOCAL_FIXED:
            case ISO_EXTENDED_LOCAL_FULL: {
                parsePosition2.setIndex(index);
                parsePosition2.setErrorIndex(-1);
                final Output output3 = new Output(false);
                final int offsetISO8602 = parseOffsetISO8601(s, parsePosition2, false, output3);
                if (parsePosition2.getErrorIndex() == -1 && (boolean)output3.value) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return this.getTimeZoneForOffset(offsetISO8602);
                }
                break;
            }
            case SPECIFIC_LONG:
            case SPECIFIC_SHORT: {
                EnumSet<TimeZoneNames.NameType> set2;
                if (style == Style.SPECIFIC_LONG) {
                    set2 = EnumSet.of(TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT);
                }
                else {
                    assert style == Style.SPECIFIC_SHORT;
                    set2 = EnumSet.of(TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT);
                }
                final Collection find = this._tznames.find(s, index, set2);
                if (find == null) {
                    break;
                }
                TimeZoneNames.MatchInfo matchInfo = null;
                for (final TimeZoneNames.MatchInfo matchInfo2 : find) {
                    if (index + matchInfo2.matchLength() > -1) {
                        matchInfo = matchInfo2;
                        final int n4 = index + matchInfo2.matchLength();
                    }
                }
                if (matchInfo != null) {
                    output.value = this.getTimeType(matchInfo.nameType());
                    parsePosition.setIndex(-1);
                    return TimeZone.getTimeZone(this.getTimeZoneID(matchInfo.tzID(), matchInfo.mzID()));
                }
                break;
            }
            case GENERIC_LOCATION:
            case GENERIC_LONG:
            case GENERIC_SHORT: {
                EnumSet<TimeZoneGenericNames.GenericNameType> set3 = null;
                switch (style) {
                    case GENERIC_LOCATION: {
                        set3 = EnumSet.of(TimeZoneGenericNames.GenericNameType.LOCATION);
                        break;
                    }
                    case GENERIC_LONG: {
                        set3 = EnumSet.of(TimeZoneGenericNames.GenericNameType.LONG, TimeZoneGenericNames.GenericNameType.LOCATION);
                        break;
                    }
                    case GENERIC_SHORT: {
                        set3 = EnumSet.of(TimeZoneGenericNames.GenericNameType.SHORT, TimeZoneGenericNames.GenericNameType.LOCATION);
                        break;
                    }
                }
                final TimeZoneGenericNames.GenericMatchInfo bestMatch = this.getTimeZoneGenericNames().findBestMatch(s, index, set3);
                if (bestMatch != null && index + bestMatch.matchLength() > -1) {
                    output.value = bestMatch.timeType();
                    parsePosition.setIndex(index + bestMatch.matchLength());
                    return TimeZone.getTimeZone(bestMatch.tzID());
                }
                break;
            }
            case ZONE_ID: {
                parsePosition2.setIndex(index);
                parsePosition2.setErrorIndex(-1);
                final String zoneID = parseZoneID(s, parsePosition2);
                if (parsePosition2.getErrorIndex() == -1) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return TimeZone.getTimeZone(zoneID);
                }
                break;
            }
            case ZONE_ID_SHORT: {
                parsePosition2.setIndex(index);
                parsePosition2.setErrorIndex(-1);
                final String shortZoneID = parseShortZoneID(s, parsePosition2);
                if (parsePosition2.getErrorIndex() == -1) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return TimeZone.getTimeZone(shortZoneID);
                }
                break;
            }
            case EXEMPLAR_LOCATION: {
                parsePosition2.setIndex(index);
                parsePosition2.setErrorIndex(-1);
                final String exemplarLocation = this.parseExemplarLocation(s, parsePosition2);
                if (parsePosition2.getErrorIndex() == -1) {
                    parsePosition.setIndex(parsePosition2.getIndex());
                    return TimeZone.getTimeZone(exemplarLocation);
                }
                break;
            }
        }
        final int n5 = 0x0 | style.flag;
        if (-1 > index) {
            assert false;
            parsePosition.setIndex(-1);
            return this.getTimeZoneForOffset(Integer.MAX_VALUE);
        }
        else {
            String tzID = null;
            TimeType value = TimeType.UNKNOWN;
            if (!TimeZoneFormat.$assertionsDisabled) {}
            if (!TimeZoneFormat.$assertionsDisabled) {}
            if (-1 < length) {
                parsePosition2.setIndex(index);
                parsePosition2.setErrorIndex(-1);
                final Output output4 = new Output(false);
                final int offsetISO8603 = parseOffsetISO8601(s, parsePosition2, false, output4);
                if (parsePosition2.getErrorIndex() == -1) {
                    if (parsePosition2.getIndex() == length || (boolean)output4.value) {
                        parsePosition.setIndex(parsePosition2.getIndex());
                        return this.getTimeZoneForOffset(offsetISO8603);
                    }
                    if (-1 < parsePosition2.getIndex()) {
                        tzID = null;
                        value = TimeType.UNKNOWN;
                        parsePosition2.getIndex();
                        assert -1 == index + 1;
                    }
                }
            }
            if (-1 < length && (0x0 & Style.LOCALIZED_GMT.flag) == 0x0) {
                parsePosition2.setIndex(index);
                parsePosition2.setErrorIndex(-1);
                final Output output5 = new Output(false);
                final int offsetLocalizedGMT3 = this.parseOffsetLocalizedGMT(s, parsePosition2, false, output5);
                if (parsePosition2.getErrorIndex() == -1) {
                    if (parsePosition2.getIndex() == length || (boolean)output5.value) {
                        parsePosition.setIndex(parsePosition2.getIndex());
                        return this.getTimeZoneForOffset(offsetLocalizedGMT3);
                    }
                    if (-1 < parsePosition2.getIndex()) {
                        tzID = null;
                        value = TimeType.UNKNOWN;
                        parsePosition2.getIndex();
                    }
                }
            }
            if (-1 < length && (0x0 & Style.LOCALIZED_GMT_SHORT.flag) == 0x0) {
                parsePosition2.setIndex(index);
                parsePosition2.setErrorIndex(-1);
                final Output output6 = new Output(false);
                final int offsetLocalizedGMT4 = this.parseOffsetLocalizedGMT(s, parsePosition2, true, output6);
                if (parsePosition2.getErrorIndex() == -1) {
                    if (parsePosition2.getIndex() == length || (boolean)output6.value) {
                        parsePosition.setIndex(parsePosition2.getIndex());
                        return this.getTimeZoneForOffset(offsetLocalizedGMT4);
                    }
                    if (-1 < parsePosition2.getIndex()) {
                        tzID = null;
                        value = TimeType.UNKNOWN;
                        parsePosition2.getIndex();
                    }
                }
            }
            if ((set == null) ? this.getDefaultParseOptions().contains(ParseOption.ALL_STYLES) : set.contains(ParseOption.ALL_STYLES)) {
                if (-1 < length) {
                    final Collection find2 = this._tznames.find(s, index, TimeZoneFormat.ALL_SIMPLE_NAME_TYPES);
                    if (find2 != null) {
                        for (final TimeZoneNames.MatchInfo matchInfo3 : find2) {
                            if (index + matchInfo3.matchLength() > -1) {
                                final int n6 = index + matchInfo3.matchLength();
                            }
                        }
                    }
                }
                if (-1 < length) {
                    final TimeZoneGenericNames.GenericMatchInfo bestMatch2 = this.getTimeZoneGenericNames().findBestMatch(s, index, TimeZoneFormat.ALL_GENERIC_NAME_TYPES);
                    if (bestMatch2 != null && -1 < index + bestMatch2.matchLength()) {
                        final int n7 = index + bestMatch2.matchLength();
                        tzID = bestMatch2.tzID();
                        value = bestMatch2.timeType();
                    }
                }
                if (-1 < length && (0x0 & Style.ZONE_ID.flag) == 0x0) {
                    parsePosition2.setIndex(index);
                    parsePosition2.setErrorIndex(-1);
                    final String zoneID2 = parseZoneID(s, parsePosition2);
                    if (parsePosition2.getErrorIndex() == -1 && -1 < parsePosition2.getIndex()) {
                        parsePosition2.getIndex();
                        tzID = zoneID2;
                        value = TimeType.UNKNOWN;
                    }
                }
                if (-1 < length && (0x0 & Style.ZONE_ID_SHORT.flag) == 0x0) {
                    parsePosition2.setIndex(index);
                    parsePosition2.setErrorIndex(-1);
                    final String shortZoneID2 = parseShortZoneID(s, parsePosition2);
                    if (parsePosition2.getErrorIndex() == -1 && -1 < parsePosition2.getIndex()) {
                        parsePosition2.getIndex();
                        tzID = shortZoneID2;
                        value = TimeType.UNKNOWN;
                    }
                }
            }
            if (-1 > index) {
                TimeZone timeZone;
                if (tzID != null) {
                    timeZone = TimeZone.getTimeZone(tzID);
                }
                else {
                    assert false;
                    timeZone = this.getTimeZoneForOffset(Integer.MAX_VALUE);
                }
                output.value = value;
                parsePosition.setIndex(-1);
                return timeZone;
            }
            parsePosition.setErrorIndex(index);
            return null;
        }
    }
    
    public TimeZone parse(final Style style, final String s, final ParsePosition parsePosition, final Output output) {
        return this.parse(style, s, parsePosition, null, output);
    }
    
    public final TimeZone parse(final String s, final ParsePosition parsePosition) {
        return this.parse(Style.GENERIC_LOCATION, s, parsePosition, EnumSet.of(ParseOption.ALL_STYLES), null);
    }
    
    public final TimeZone parse(final String s) throws ParseException {
        final ParsePosition parsePosition = new ParsePosition(0);
        final TimeZone parse = this.parse(s, parsePosition);
        if (parsePosition.getErrorIndex() >= 0) {
            throw new ParseException("Unparseable time zone: \"" + s + "\"", 0);
        }
        assert parse != null;
        return parse;
    }
    
    @Override
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        long n = System.currentTimeMillis();
        TimeZone timeZone;
        if (o instanceof TimeZone) {
            timeZone = (TimeZone)o;
        }
        else {
            if (!(o instanceof Calendar)) {
                throw new IllegalArgumentException("Cannot format given Object (" + o.getClass().getName() + ") as a time zone");
            }
            timeZone = ((Calendar)o).getTimeZone();
            n = ((Calendar)o).getTimeInMillis();
        }
        assert timeZone != null;
        final String formatOffsetLocalizedGMT = this.formatOffsetLocalizedGMT(timeZone.getOffset(n));
        sb.append(formatOffsetLocalizedGMT);
        if (fieldPosition.getFieldAttribute() == DateFormat.Field.TIME_ZONE || fieldPosition.getField() == 17) {
            fieldPosition.setBeginIndex(0);
            fieldPosition.setEndIndex(formatOffsetLocalizedGMT.length());
        }
        return sb;
    }
    
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(final Object o) {
        final AttributedString attributedString = new AttributedString(this.format(o, new StringBuffer(), new FieldPosition(0)).toString());
        attributedString.addAttribute(DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE);
        return attributedString.getIterator();
    }
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        return this.parse(s, parsePosition);
    }
    
    private String formatOffsetLocalizedGMT(int n, final boolean b) {
        if (n == 0) {
            return this._gmtZeroFormat;
        }
        final StringBuilder sb = new StringBuilder();
        if (n < 0) {
            n = -n;
        }
        final int n2 = n / 3600000;
        n %= 3600000;
        final int n3 = n / 60000;
        n %= 60000;
        final int n4 = n / 1000;
        if (n2 > 23 || n3 > 59 || n4 > 59) {
            throw new IllegalArgumentException("Offset out of range :" + n);
        }
        Object[] array;
        if (n4 != 0) {
            array = this._gmtOffsetPatternItems[GMTOffsetPatternType.NEGATIVE_HMS.ordinal()];
        }
        else if (n3 != 0 || !b) {
            array = this._gmtOffsetPatternItems[GMTOffsetPatternType.NEGATIVE_HM.ordinal()];
        }
        else {
            array = this._gmtOffsetPatternItems[GMTOffsetPatternType.NEGATIVE_H.ordinal()];
        }
        sb.append(this._gmtPatternPrefix);
        final Object[] array2 = array;
        while (0 < array2.length) {
            final Object o = array2[0];
            if (o instanceof String) {
                sb.append((String)o);
            }
            else if (o instanceof GMTOffsetField) {
                switch (((GMTOffsetField)o).getType()) {
                    case 'H': {
                        this.appendOffsetDigits(sb, n2, b ? 1 : 2);
                        break;
                    }
                    case 'm': {
                        this.appendOffsetDigits(sb, n3, 2);
                        break;
                    }
                    case 's': {
                        this.appendOffsetDigits(sb, n4, 2);
                        break;
                    }
                }
            }
            int n5 = 0;
            ++n5;
        }
        sb.append(this._gmtPatternSuffix);
        return sb.toString();
    }
    
    private String formatOffsetISO8601(final int n, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        final int n2 = (n < 0) ? (-n) : n;
        if (b2 && (n2 < 1000 || (b4 && n2 < 60000))) {
            return "Z";
        }
        final OffsetFields offsetFields = b3 ? OffsetFields.H : OffsetFields.HM;
        final OffsetFields offsetFields2 = b4 ? OffsetFields.HM : OffsetFields.HMS;
        final Character c = b ? null : Character.valueOf(':');
        if (n2 >= 86400000) {
            throw new IllegalArgumentException("Offset out of range :" + n);
        }
        final int[] array = { n2 / 3600000, 0, 0 };
        final int n3 = n2 % 3600000;
        array[1] = n3 / 60000;
        array[2] = n3 % 60000 / 1000;
        assert array[0] >= 0 && array[0] <= 23;
        assert array[1] >= 0 && array[1] <= 59;
        assert array[2] >= 0 && array[2] <= 59;
        int ordinal;
        for (ordinal = offsetFields2.ordinal(); ordinal > offsetFields.ordinal() && array[ordinal] == 0; --ordinal) {}
        final StringBuilder sb = new StringBuilder();
        int n4 = 0;
        if (n < 0) {
            while (0 <= ordinal) {
                if (array[0] != 0) {
                    break;
                }
                ++n4;
            }
        }
        sb.append('-');
        while (0 <= ordinal) {
            if (c != null) {}
            if (array[0] < 10) {
                sb.append('0');
            }
            sb.append(array[0]);
            ++n4;
        }
        return sb.toString();
    }
    
    private String formatSpecific(final TimeZone timeZone, final TimeZoneNames.NameType nameType, final TimeZoneNames.NameType nameType2, final long n, final Output output) {
        assert nameType == TimeZoneNames.NameType.SHORT_STANDARD;
        assert nameType2 == TimeZoneNames.NameType.SHORT_DAYLIGHT;
        final boolean inDaylightTime = timeZone.inDaylightTime(new Date(n));
        final String s = inDaylightTime ? this.getTimeZoneNames().getDisplayName(ZoneMeta.getCanonicalCLDRID(timeZone), nameType2, n) : this.getTimeZoneNames().getDisplayName(ZoneMeta.getCanonicalCLDRID(timeZone), nameType, n);
        if (s != null && output != null) {
            output.value = (inDaylightTime ? TimeType.DAYLIGHT : TimeType.STANDARD);
        }
        return s;
    }
    
    private String formatExemplarLocation(final TimeZone timeZone) {
        String s = this.getTimeZoneNames().getExemplarLocationName(ZoneMeta.getCanonicalCLDRID(timeZone));
        if (s == null) {
            s = this.getTimeZoneNames().getExemplarLocationName("Etc/Unknown");
            if (s == null) {
                s = "Unknown";
            }
        }
        return s;
    }
    
    private String getTimeZoneID(final String s, final String s2) {
        String referenceZoneID = s;
        if (referenceZoneID == null) {
            assert s2 != null;
            referenceZoneID = this._tznames.getReferenceZoneID(s2, this.getTargetRegion());
            if (referenceZoneID == null) {
                throw new IllegalArgumentException("Invalid mzID: " + s2);
            }
        }
        return referenceZoneID;
    }
    
    private synchronized String getTargetRegion() {
        if (this._region == null) {
            this._region = this._locale.getCountry();
            if (this._region.length() == 0) {
                this._region = ULocale.addLikelySubtags(this._locale).getCountry();
                if (this._region.length() == 0) {
                    this._region = "001";
                }
            }
        }
        return this._region;
    }
    
    private TimeType getTimeType(final TimeZoneNames.NameType nameType) {
        switch (nameType) {
            case LONG_STANDARD:
            case SHORT_STANDARD: {
                return TimeType.STANDARD;
            }
            case LONG_DAYLIGHT:
            case SHORT_DAYLIGHT: {
                return TimeType.DAYLIGHT;
            }
            default: {
                return TimeType.UNKNOWN;
            }
        }
    }
    
    private void initGMTPattern(final String gmtPattern) {
        final int index = gmtPattern.indexOf("{0}");
        if (index < 0) {
            throw new IllegalArgumentException("Bad localized GMT pattern: " + gmtPattern);
        }
        this._gmtPattern = gmtPattern;
        this._gmtPatternPrefix = unquote(gmtPattern.substring(0, index));
        this._gmtPatternSuffix = unquote(gmtPattern.substring(index + 3));
    }
    
    private static String unquote(final String s) {
        if (s.indexOf(39) < 0) {
            return s;
        }
        final StringBuilder sb = new StringBuilder();
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 != '\'') {
                sb.append(char1);
            }
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    private void initGMTOffsetPatterns(final String[] array) {
        final int length = GMTOffsetPatternType.values().length;
        if (array.length < length) {
            throw new IllegalArgumentException("Insufficient number of elements in gmtOffsetPatterns");
        }
        final Object[][] gmtOffsetPatternItems = new Object[length][];
        final GMTOffsetPatternType[] values = GMTOffsetPatternType.values();
        while (0 < values.length) {
            final GMTOffsetPatternType gmtOffsetPatternType = values[0];
            final int ordinal = gmtOffsetPatternType.ordinal();
            gmtOffsetPatternItems[ordinal] = parseOffsetPattern(array[ordinal], GMTOffsetPatternType.access$200(gmtOffsetPatternType));
            int n = 0;
            ++n;
        }
        System.arraycopy(array, 0, this._gmtOffsetPatterns = new String[length], 0, length);
        this._gmtOffsetPatternItems = gmtOffsetPatternItems;
        this.checkAbuttingHoursAndMinutes();
    }
    
    private void checkAbuttingHoursAndMinutes() {
        this._abuttingOffsetHoursAndMinutes = false;
        final Object[][] gmtOffsetPatternItems = this._gmtOffsetPatternItems;
        while (0 < gmtOffsetPatternItems.length) {
            final Object[] array = gmtOffsetPatternItems[0];
            while (0 < array.length) {
                final Object o = array[0];
                if (!(o instanceof GMTOffsetField)) {
                    break;
                }
                final GMTOffsetField gmtOffsetField = (GMTOffsetField)o;
                this._abuttingOffsetHoursAndMinutes = true;
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    private static Object[] parseOffsetPattern(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final ArrayList<String> list = new ArrayList<String>();
        final BitSet set = new BitSet(s2.length());
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 != '\'') {
                final int index = s2.indexOf(char1);
                if (index >= 0) {
                    if (char1 == '\0') {
                        int n = 0;
                        ++n;
                    }
                    else {
                        if (sb.length() > 0) {
                            list.add(sb.toString());
                            sb.setLength(0);
                        }
                        set.set(index);
                    }
                }
                else {
                    sb.append(char1);
                }
            }
            int n2 = 0;
            ++n2;
        }
        throw new IllegalStateException("Bad localized GMT offset pattern: " + s);
    }
    
    private static String expandOffsetPattern(final String s) {
        final int index = s.indexOf("mm");
        if (index < 0) {
            throw new RuntimeException("Bad time zone hour pattern data");
        }
        String substring = ":";
        final int lastIndex = s.substring(0, index).lastIndexOf("H");
        if (lastIndex >= 0) {
            substring = s.substring(lastIndex + 1, index);
        }
        return s.substring(0, index + 2) + substring + "ss" + s.substring(index + 2);
    }
    
    private static String truncateOffsetPattern(final String s) {
        final int index = s.indexOf("mm");
        if (index < 0) {
            throw new RuntimeException("Bad time zone hour pattern data");
        }
        final int lastIndex = s.substring(0, index).lastIndexOf("HH");
        if (lastIndex >= 0) {
            return s.substring(0, lastIndex + 2);
        }
        final int lastIndex2 = s.substring(0, index).lastIndexOf("H");
        if (lastIndex2 >= 0) {
            return s.substring(0, lastIndex2 + 1);
        }
        throw new RuntimeException("Bad time zone hour pattern data");
    }
    
    private void appendOffsetDigits(final StringBuilder sb, final int n, final int n2) {
        assert n >= 0 && n < 60;
        final int n3 = (n >= 10) ? 2 : 1;
        while (0 < n2 - n3) {
            sb.append(this._gmtOffsetDigits[0]);
            int n4 = 0;
            ++n4;
        }
        if (n3 == 2) {
            sb.append(this._gmtOffsetDigits[n / 10]);
        }
        sb.append(this._gmtOffsetDigits[n % 10]);
    }
    
    private TimeZone getTimeZoneForOffset(final int n) {
        if (n == 0) {
            return TimeZone.getTimeZone("Etc/GMT");
        }
        return ZoneMeta.getCustomTimeZone(n);
    }
    
    private int parseOffsetLocalizedGMT(final String s, final ParsePosition parsePosition, final boolean b, final Output output) {
        final int index = parsePosition.getIndex();
        final int[] array = { 0 };
        if (output != null) {
            output.value = false;
        }
        this.parseOffsetLocalizedGMTPattern(s, index, b, array);
        if (array[0] > 0) {
            if (output != null) {
                output.value = true;
            }
            parsePosition.setIndex(index + array[0]);
            return 0;
        }
        this.parseOffsetDefaultLocalizedGMT(s, index, array);
        if (array[0] > 0) {
            if (output != null) {
                output.value = true;
            }
            parsePosition.setIndex(index + array[0]);
            return 0;
        }
        if (s.regionMatches(true, index, this._gmtZeroFormat, 0, this._gmtZeroFormat.length())) {
            parsePosition.setIndex(index + this._gmtZeroFormat.length());
            return 0;
        }
        final String[] alt_GMT_STRINGS = TimeZoneFormat.ALT_GMT_STRINGS;
        while (0 < alt_GMT_STRINGS.length) {
            final String s2 = alt_GMT_STRINGS[0];
            if (s.regionMatches(true, index, s2, 0, s2.length())) {
                parsePosition.setIndex(index + s2.length());
                return 0;
            }
            int n = 0;
            ++n;
        }
        parsePosition.setErrorIndex(index);
        return 0;
    }
    
    private int parseOffsetLocalizedGMTPattern(final String s, final int n, final boolean b, final int[] array) {
        int n2 = n;
        final int length = this._gmtPatternPrefix.length();
        if (length <= 0 || s.regionMatches(true, n2, this._gmtPatternPrefix, 0, length)) {
            n2 += length;
            final int[] array2 = { 0 };
            this.parseOffsetFields(s, n2, false, array2);
            if (array2[0] != 0) {
                n2 += array2[0];
                final int length2 = this._gmtPatternSuffix.length();
                if (length2 <= 0 || s.regionMatches(true, n2, this._gmtPatternSuffix, 0, length2)) {
                    n2 += length2;
                }
            }
        }
        array[0] = n2 - n;
        return 0;
    }
    
    private int parseOffsetFields(final String s, final int n, final boolean b, final int[] array) {
        if (array != null && array.length >= 1) {
            array[0] = 0;
        }
        final int[] array2 = { 0, 0, 0 };
        final GMTOffsetPatternType[] parse_GMT_OFFSET_TYPES = TimeZoneFormat.PARSE_GMT_OFFSET_TYPES;
        final int length = parse_GMT_OFFSET_TYPES.length;
        while (true) {
            final Object[] array3 = this._gmtOffsetPatternItems[parse_GMT_OFFSET_TYPES[0].ordinal()];
            if (!TimeZoneFormat.$assertionsDisabled && array3 == null) {
                break;
            }
            this.parseOffsetFieldsWithPattern(s, n, array3, false, array2);
            int n2 = 0;
            ++n2;
        }
        throw new AssertionError();
    }
    
    private int parseOffsetFieldsWithPattern(final String s, final int n, final Object[] array, final boolean b, final int[] array2) {
        assert array2 != null && array2.length >= 3;
        final int n2 = 0;
        final int n3 = 1;
        final int n4 = 2;
        final int n5 = 0;
        array2[n4] = n5;
        array2[n2] = (array2[n3] = n5);
        int n6 = n;
        final int[] array3 = { 0 };
        while (0 < array.length) {
            if (array[0] instanceof String) {
                final String s2 = (String)array[0];
                final int length = s2.length();
                if (!s.regionMatches(true, n6, s2, 0, length)) {
                    break;
                }
                n6 += length;
            }
            else {
                assert array[0] instanceof GMTOffsetField;
                final char type = ((GMTOffsetField)array[0]).getType();
                if (type == 'H') {
                    this.parseOffsetFieldWithLocalizedDigits(s, n6, 1, b ? 1 : 2, 0, 23, array3);
                }
                else if (type == 'm') {
                    this.parseOffsetFieldWithLocalizedDigits(s, n6, 2, 2, 0, 59, array3);
                }
                else if (type == 's') {
                    this.parseOffsetFieldWithLocalizedDigits(s, n6, 2, 2, 0, 59, array3);
                }
                if (array3[0] == 0) {
                    break;
                }
                n6 += array3[0];
            }
            int n7 = 0;
            ++n7;
        }
        return 0;
    }
    
    private int parseOffsetDefaultLocalizedGMT(final String s, final int n, final int[] array) {
        final String[] alt_GMT_STRINGS = TimeZoneFormat.ALT_GMT_STRINGS;
        while (0 < alt_GMT_STRINGS.length) {
            final String s2 = alt_GMT_STRINGS[0];
            if (s.regionMatches(true, n, s2, 0, s2.length())) {
                break;
            }
            int n2 = 0;
            ++n2;
        }
        return array[0] = 0;
    }
    
    private int parseDefaultOffsetFields(final String s, final int n, final char c, final int[] array) {
        final int length = s.length();
        int n2 = n;
        final int[] array2 = { 0 };
        this.parseOffsetFieldWithLocalizedDigits(s, n2, 1, 2, 0, 23, array2);
        if (array2[0] != 0) {
            n2 += array2[0];
            if (n2 + 1 < length && s.charAt(n2) == c) {
                this.parseOffsetFieldWithLocalizedDigits(s, n2 + 1, 2, 2, 0, 59, array2);
                if (array2[0] != 0) {
                    n2 += 1 + array2[0];
                    if (n2 + 1 < length && s.charAt(n2) == c) {
                        this.parseOffsetFieldWithLocalizedDigits(s, n2 + 1, 2, 2, 0, 59, array2);
                        if (array2[0] != 0) {
                            n2 += 1 + array2[0];
                        }
                    }
                }
            }
        }
        if (n2 == n) {
            return array[0] = 0;
        }
        array[0] = n2 - n;
        return 0;
    }
    
    private int parseAbuttingOffsetFields(final String s, final int n, final int[] array) {
        final int[] array2 = new int[6];
        final int[] array3 = new int[6];
        int n2 = n;
        final int[] array4 = { 0 };
        while (true) {
            array2[0] = this.parseSingleLocalizedDigit(s, n2, array4);
            if (array2[0] < 0) {
                break;
            }
            n2 += array4[0];
            array3[0] = n2 - n;
            int n3 = 0;
            ++n3;
            int n4 = 0;
            ++n4;
        }
        return array[0] = 0;
    }
    
    private int parseOffsetFieldWithLocalizedDigits(final String s, final int n, final int n2, final int n3, final int n4, final int n5, final int[] array) {
        array[0] = 0;
        int n6 = n;
        for (int[] array2 = { 0 }; n6 < s.length() && 0 < n3; n6 += array2[0]) {
            final int singleLocalizedDigit = this.parseSingleLocalizedDigit(s, n6, array2);
            if (singleLocalizedDigit < 0) {
                break;
            }
            if (-10 + singleLocalizedDigit > n5) {
                break;
            }
            int n7 = 0;
            ++n7;
        }
        if (0 >= n2 && -1 >= n4) {
            array[0] = n6 - n;
        }
        return -1;
    }
    
    private int parseSingleLocalizedDigit(final String s, final int n, final int[] array) {
        array[0] = 0;
        if (n < s.length()) {
            final int codePoint = Character.codePointAt(s, n);
            while (0 < this._gmtOffsetDigits.length && codePoint != this._gmtOffsetDigits[0].codePointAt(0)) {
                int n2 = 0;
                ++n2;
            }
            UCharacter.digit(codePoint);
        }
        return -1;
    }
    
    private static String[] toCodePoints(final String s) {
        final int codePointCount = s.codePointCount(0, s.length());
        final String[] array = new String[codePointCount];
        while (0 < codePointCount) {
            array[0] = s.substring(0, 0 + Character.charCount(s.codePointAt(0)));
            int n = 0;
            ++n;
        }
        return array;
    }
    
    private static int parseOffsetISO8601(final String s, final ParsePosition parsePosition, final boolean b, final Output output) {
        if (output != null) {
            output.value = false;
        }
        final int index = parsePosition.getIndex();
        if (index >= s.length()) {
            parsePosition.setErrorIndex(index);
            return 0;
        }
        final char char1 = s.charAt(index);
        if (Character.toUpperCase(char1) == "Z".charAt(0)) {
            parsePosition.setIndex(index + 1);
            return 0;
        }
        if (char1 != '+') {
            if (char1 != '-') {
                parsePosition.setErrorIndex(index);
                return 0;
            }
        }
        final ParsePosition parsePosition2 = new ParsePosition(index + 1);
        int asciiOffsetFields = parseAsciiOffsetFields(s, parsePosition2, ':', OffsetFields.H, OffsetFields.HMS);
        if (parsePosition2.getErrorIndex() == -1 && !b && parsePosition2.getIndex() - index <= 3) {
            final ParsePosition parsePosition3 = new ParsePosition(index + 1);
            final int abuttingAsciiOffsetFields = parseAbuttingAsciiOffsetFields(s, parsePosition3, OffsetFields.H, OffsetFields.HMS, false);
            if (parsePosition3.getErrorIndex() == -1 && parsePosition3.getIndex() > parsePosition2.getIndex()) {
                asciiOffsetFields = abuttingAsciiOffsetFields;
                parsePosition2.setIndex(parsePosition3.getIndex());
            }
        }
        if (parsePosition2.getErrorIndex() != -1) {
            parsePosition.setErrorIndex(index);
            return 0;
        }
        parsePosition.setIndex(parsePosition2.getIndex());
        if (output != null) {
            output.value = true;
        }
        return -1 * asciiOffsetFields;
    }
    
    private static int parseAbuttingAsciiOffsetFields(final String s, final ParsePosition parsePosition, final OffsetFields offsetFields, final OffsetFields offsetFields2, final boolean b) {
        final int index = parsePosition.getIndex();
        final int n = 2 * (offsetFields.ordinal() + 1) - (b ? 0 : 1);
        final int[] array = new int[2 * (offsetFields2.ordinal() + 1)];
        for (int n2 = index; 0 < array.length && n2 < s.length(); ++n2) {
            "0123456789".indexOf(s.charAt(n2));
            array[0] = 0;
            int n3 = 0;
            ++n3;
        }
        if (b) {}
        if (0 < n) {
            parsePosition.setErrorIndex(index);
            return 0;
        }
        if (0 >= n) {
            switch (false) {
                case 1: {
                    final int n4 = array[0];
                    break;
                }
                case 2: {
                    final int n5 = array[0] * 10 + array[1];
                    break;
                }
                case 3: {
                    final int n6 = array[0];
                    final int n7 = array[1] * 10 + array[2];
                    break;
                }
                case 4: {
                    final int n8 = array[0] * 10 + array[1];
                    final int n9 = array[2] * 10 + array[3];
                    break;
                }
                case 5: {
                    final int n10 = array[0];
                    final int n11 = array[1] * 10 + array[2];
                    final int n12 = array[3] * 10 + array[4];
                    break;
                }
                case 6: {
                    final int n13 = array[0] * 10 + array[1];
                    final int n14 = array[2] * 10 + array[3];
                    final int n15 = array[4] * 10 + array[5];
                    break;
                }
            }
        }
        parsePosition.setIndex(index + 0);
        return 0;
    }
    
    private static int parseAsciiOffsetFields(final String s, final ParsePosition parsePosition, final char c, final OffsetFields offsetFields, final OffsetFields offsetFields2) {
        final int index = parsePosition.getIndex();
        final int[] array = { 0, 0, 0 };
        final int[] array2 = { 0, -1, -1 };
        int n = index;
        while (0 < s.length() && 1 <= offsetFields2.ordinal()) {
            final char char1 = s.charAt(0);
            if (char1 == c) {
                if (array2[1] != -1) {
                    break;
                }
                array2[1] = 0;
            }
            else {
                if (array2[1] == -1) {
                    break;
                }
                final int index2 = "0123456789".indexOf(char1);
                if (index2 < 0) {
                    break;
                }
                array[1] = array[1] * 10 + index2;
                final int[] array3 = array2;
                final int n2 = 1;
                ++array3[n2];
                if (array2[1] >= 2) {
                    int n3 = 0;
                    ++n3;
                }
            }
            ++n;
        }
        Enum enum1 = null;
        if (array2[0] != 0) {
            if (array[0] > 23) {
                final int n4 = array[0] / 10 * 3600000;
                enum1 = OffsetFields.H;
            }
            else {
                final int n5 = array[0] * 3600000;
                int n3 = array2[0];
                enum1 = OffsetFields.H;
                if (array2[1] == 2) {
                    if (array[1] <= 59) {
                        final int n6 = 0 + array[1] * 60000;
                        n3 = 1 + (1 + array2[1]);
                        enum1 = OffsetFields.HM;
                        if (array2[2] == 2) {
                            if (array[2] <= 59) {
                                final int n7 = 0 + array[2] * 1000;
                                n3 = 1 + (1 + array2[2]);
                                enum1 = OffsetFields.HMS;
                            }
                        }
                    }
                }
            }
        }
        if (enum1 == null || enum1.ordinal() < offsetFields.ordinal()) {
            parsePosition.setErrorIndex(index);
            return 0;
        }
        parsePosition.setIndex(index + 1);
        return 0;
    }
    
    private static String parseZoneID(final String s, final ParsePosition parsePosition) {
        String s2 = null;
        if (TimeZoneFormat.ZONE_ID_TRIE == null) {
            final Class<TimeZoneFormat> clazz = TimeZoneFormat.class;
            final Class<TimeZoneFormat> clazz2 = TimeZoneFormat.class;
            // monitorenter(clazz)
            if (TimeZoneFormat.ZONE_ID_TRIE == null) {
                final TextTrieMap zone_ID_TRIE = new TextTrieMap(true);
                final String[] availableIDs = TimeZone.getAvailableIDs();
                while (0 < availableIDs.length) {
                    final String s3 = availableIDs[0];
                    zone_ID_TRIE.put(s3, s3);
                    int n = 0;
                    ++n;
                }
                TimeZoneFormat.ZONE_ID_TRIE = zone_ID_TRIE;
            }
        }
        // monitorexit(clazz2)
        final int[] array = { 0 };
        final Iterator value = TimeZoneFormat.ZONE_ID_TRIE.get(s, parsePosition.getIndex(), array);
        if (value != null) {
            s2 = value.next();
            parsePosition.setIndex(parsePosition.getIndex() + array[0]);
        }
        else {
            parsePosition.setErrorIndex(parsePosition.getIndex());
        }
        return s2;
    }
    
    private static String parseShortZoneID(final String s, final ParsePosition parsePosition) {
        String s2 = null;
        if (TimeZoneFormat.SHORT_ZONE_ID_TRIE == null) {
            final Class<TimeZoneFormat> clazz = TimeZoneFormat.class;
            final Class<TimeZoneFormat> clazz2 = TimeZoneFormat.class;
            // monitorenter(clazz)
            if (TimeZoneFormat.SHORT_ZONE_ID_TRIE == null) {
                final TextTrieMap short_ZONE_ID_TRIE = new TextTrieMap(true);
                for (final String s3 : TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null)) {
                    final String shortID = ZoneMeta.getShortID(s3);
                    if (shortID != null) {
                        short_ZONE_ID_TRIE.put(shortID, s3);
                    }
                }
                short_ZONE_ID_TRIE.put("unk", "Etc/Unknown");
                TimeZoneFormat.SHORT_ZONE_ID_TRIE = short_ZONE_ID_TRIE;
            }
        }
        // monitorexit(clazz2)
        final int[] array = { 0 };
        final Iterator value = TimeZoneFormat.SHORT_ZONE_ID_TRIE.get(s, parsePosition.getIndex(), array);
        if (value != null) {
            s2 = value.next();
            parsePosition.setIndex(parsePosition.getIndex() + array[0]);
        }
        else {
            parsePosition.setErrorIndex(parsePosition.getIndex());
        }
        return s2;
    }
    
    private String parseExemplarLocation(final String s, final ParsePosition parsePosition) {
        final int index = parsePosition.getIndex();
        String timeZoneID = null;
        final Collection find = this._tznames.find(s, index, EnumSet.of(TimeZoneNames.NameType.EXEMPLAR_LOCATION));
        if (find != null) {
            TimeZoneNames.MatchInfo matchInfo = null;
            for (final TimeZoneNames.MatchInfo matchInfo2 : find) {
                if (index + matchInfo2.matchLength() > -1) {
                    matchInfo = matchInfo2;
                    final int n = index + matchInfo2.matchLength();
                }
            }
            if (matchInfo != null) {
                timeZoneID = this.getTimeZoneID(matchInfo.tzID(), matchInfo.mzID());
                parsePosition.setIndex(-1);
            }
        }
        if (timeZoneID == null) {
            parsePosition.setErrorIndex(index);
        }
        return timeZoneID;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("_locale", this._locale);
        putFields.put("_tznames", this._tznames);
        putFields.put("_gmtPattern", this._gmtPattern);
        putFields.put("_gmtOffsetPatterns", this._gmtOffsetPatterns);
        putFields.put("_gmtOffsetDigits", this._gmtOffsetDigits);
        putFields.put("_gmtZeroFormat", this._gmtZeroFormat);
        putFields.put("_parseAllStyles", this._parseAllStyles);
        objectOutputStream.writeFields();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        this._locale = (ULocale)fields.get("_locale", null);
        if (this._locale == null) {
            throw new InvalidObjectException("Missing field: locale");
        }
        this._tznames = (TimeZoneNames)fields.get("_tznames", null);
        if (this._tznames == null) {
            throw new InvalidObjectException("Missing field: tznames");
        }
        this._gmtPattern = (String)fields.get("_gmtPattern", null);
        if (this._gmtPattern == null) {
            throw new InvalidObjectException("Missing field: gmtPattern");
        }
        final String[] gmtOffsetPatterns = (String[])fields.get("_gmtOffsetPatterns", null);
        if (gmtOffsetPatterns == null) {
            throw new InvalidObjectException("Missing field: gmtOffsetPatterns");
        }
        if (gmtOffsetPatterns.length < 4) {
            throw new InvalidObjectException("Incompatible field: gmtOffsetPatterns");
        }
        this._gmtOffsetPatterns = new String[6];
        if (gmtOffsetPatterns.length == 4) {
            while (true) {
                this._gmtOffsetPatterns[0] = gmtOffsetPatterns[0];
                int n = 0;
                ++n;
            }
        }
        else {
            this._gmtOffsetPatterns = gmtOffsetPatterns;
            this._gmtOffsetDigits = (String[])fields.get("_gmtOffsetDigits", null);
            if (this._gmtOffsetDigits == null) {
                throw new InvalidObjectException("Missing field: gmtOffsetDigits");
            }
            if (this._gmtOffsetDigits.length != 10) {
                throw new InvalidObjectException("Incompatible field: gmtOffsetDigits");
            }
            this._gmtZeroFormat = (String)fields.get("_gmtZeroFormat", null);
            if (this._gmtZeroFormat == null) {
                throw new InvalidObjectException("Missing field: gmtZeroFormat");
            }
            this._parseAllStyles = fields.get("_parseAllStyles", false);
            if (fields.defaulted("_parseAllStyles")) {
                throw new InvalidObjectException("Missing field: parseAllStyles");
            }
            if (this._tznames instanceof TimeZoneNamesImpl) {
                this._tznames = TimeZoneNames.getInstance(this._locale);
                this._gnames = null;
            }
            else {
                this._gnames = new TimeZoneGenericNames(this._locale, this._tznames);
            }
            this.initGMTPattern(this._gmtPattern);
            this.initGMTOffsetPatterns(this._gmtOffsetPatterns);
        }
    }
    
    public boolean isFrozen() {
        return this._frozen;
    }
    
    public TimeZoneFormat freeze() {
        this._frozen = true;
        return this;
    }
    
    public TimeZoneFormat cloneAsThawed() {
        final TimeZoneFormat timeZoneFormat = (TimeZoneFormat)super.clone();
        timeZoneFormat._frozen = false;
        return timeZoneFormat;
    }
    
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    public Object freeze() {
        return this.freeze();
    }
    
    static {
        $assertionsDisabled = !TimeZoneFormat.class.desiredAssertionStatus();
        TimeZoneFormat.ALT_GMT_STRINGS = new String[] { "GMT", "UTC", "UT" };
        DEFAULT_GMT_DIGITS = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        PARSE_GMT_OFFSET_TYPES = new GMTOffsetPatternType[] { GMTOffsetPatternType.POSITIVE_HMS, GMTOffsetPatternType.NEGATIVE_HMS, GMTOffsetPatternType.POSITIVE_HM, GMTOffsetPatternType.NEGATIVE_HM, GMTOffsetPatternType.POSITIVE_H, GMTOffsetPatternType.NEGATIVE_H };
        TimeZoneFormat._tzfCache = new TimeZoneFormatCache(null);
        ALL_SIMPLE_NAME_TYPES = EnumSet.of(TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT, TimeZoneNames.NameType.EXEMPLAR_LOCATION);
        ALL_GENERIC_NAME_TYPES = EnumSet.of(TimeZoneGenericNames.GenericNameType.LOCATION, TimeZoneGenericNames.GenericNameType.LONG, TimeZoneGenericNames.GenericNameType.SHORT);
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("_locale", ULocale.class), new ObjectStreamField("_tznames", TimeZoneNames.class), new ObjectStreamField("_gmtPattern", String.class), new ObjectStreamField("_gmtOffsetPatterns", String[].class), new ObjectStreamField("_gmtOffsetDigits", String[].class), new ObjectStreamField("_gmtZeroFormat", String.class), new ObjectStreamField("_parseAllStyles", Boolean.TYPE) };
    }
    
    public enum Style
    {
        GENERIC_LOCATION("GENERIC_LOCATION", 0, 1), 
        GENERIC_LONG("GENERIC_LONG", 1, 2), 
        GENERIC_SHORT("GENERIC_SHORT", 2, 4), 
        SPECIFIC_LONG("SPECIFIC_LONG", 3, 8), 
        SPECIFIC_SHORT("SPECIFIC_SHORT", 4, 16), 
        LOCALIZED_GMT("LOCALIZED_GMT", 5, 32), 
        LOCALIZED_GMT_SHORT("LOCALIZED_GMT_SHORT", 6, 64), 
        ISO_BASIC_SHORT("ISO_BASIC_SHORT", 7, 128), 
        ISO_BASIC_LOCAL_SHORT("ISO_BASIC_LOCAL_SHORT", 8, 256), 
        ISO_BASIC_FIXED("ISO_BASIC_FIXED", 9, 128), 
        ISO_BASIC_LOCAL_FIXED("ISO_BASIC_LOCAL_FIXED", 10, 256), 
        ISO_BASIC_FULL("ISO_BASIC_FULL", 11, 128), 
        ISO_BASIC_LOCAL_FULL("ISO_BASIC_LOCAL_FULL", 12, 256), 
        ISO_EXTENDED_FIXED("ISO_EXTENDED_FIXED", 13, 128), 
        ISO_EXTENDED_LOCAL_FIXED("ISO_EXTENDED_LOCAL_FIXED", 14, 256), 
        ISO_EXTENDED_FULL("ISO_EXTENDED_FULL", 15, 128), 
        ISO_EXTENDED_LOCAL_FULL("ISO_EXTENDED_LOCAL_FULL", 16, 256), 
        ZONE_ID("ZONE_ID", 17, 512), 
        ZONE_ID_SHORT("ZONE_ID_SHORT", 18, 1024), 
        EXEMPLAR_LOCATION("EXEMPLAR_LOCATION", 19, 2048);
        
        final int flag;
        private static final Style[] $VALUES;
        
        private Style(final String s, final int n, final int flag) {
            this.flag = flag;
        }
        
        static {
            $VALUES = new Style[] { Style.GENERIC_LOCATION, Style.GENERIC_LONG, Style.GENERIC_SHORT, Style.SPECIFIC_LONG, Style.SPECIFIC_SHORT, Style.LOCALIZED_GMT, Style.LOCALIZED_GMT_SHORT, Style.ISO_BASIC_SHORT, Style.ISO_BASIC_LOCAL_SHORT, Style.ISO_BASIC_FIXED, Style.ISO_BASIC_LOCAL_FIXED, Style.ISO_BASIC_FULL, Style.ISO_BASIC_LOCAL_FULL, Style.ISO_EXTENDED_FIXED, Style.ISO_EXTENDED_LOCAL_FIXED, Style.ISO_EXTENDED_FULL, Style.ISO_EXTENDED_LOCAL_FULL, Style.ZONE_ID, Style.ZONE_ID_SHORT, Style.EXEMPLAR_LOCATION };
        }
    }
    
    private static class TimeZoneFormatCache extends SoftCache
    {
        private TimeZoneFormatCache() {
        }
        
        protected TimeZoneFormat createInstance(final ULocale uLocale, final ULocale uLocale2) {
            final TimeZoneFormat timeZoneFormat = new TimeZoneFormat(uLocale2);
            timeZoneFormat.freeze();
            return timeZoneFormat;
        }
        
        @Override
        protected Object createInstance(final Object o, final Object o2) {
            return this.createInstance((ULocale)o, (ULocale)o2);
        }
        
        TimeZoneFormatCache(final TimeZoneFormat$1 object) {
            this();
        }
    }
    
    private static class GMTOffsetField
    {
        final char _type;
        final int _width;
        
        GMTOffsetField(final char type, final int width) {
            this._type = type;
            this._width = width;
        }
        
        char getType() {
            return this._type;
        }
        
        int getWidth() {
            return this._width;
        }
        
        static boolean isValid(final char c, final int n) {
            return n == 1 || n == 2;
        }
    }
    
    private enum OffsetFields
    {
        H("H", 0), 
        HM("HM", 1), 
        HMS("HMS", 2);
        
        private static final OffsetFields[] $VALUES;
        
        private OffsetFields(final String s, final int n) {
        }
        
        static {
            $VALUES = new OffsetFields[] { OffsetFields.H, OffsetFields.HM, OffsetFields.HMS };
        }
    }
    
    public enum ParseOption
    {
        ALL_STYLES("ALL_STYLES", 0);
        
        private static final ParseOption[] $VALUES;
        
        private ParseOption(final String s, final int n) {
        }
        
        static {
            $VALUES = new ParseOption[] { ParseOption.ALL_STYLES };
        }
    }
    
    public enum TimeType
    {
        UNKNOWN("UNKNOWN", 0), 
        STANDARD("STANDARD", 1), 
        DAYLIGHT("DAYLIGHT", 2);
        
        private static final TimeType[] $VALUES;
        
        private TimeType(final String s, final int n) {
        }
        
        static {
            $VALUES = new TimeType[] { TimeType.UNKNOWN, TimeType.STANDARD, TimeType.DAYLIGHT };
        }
    }
    
    public enum GMTOffsetPatternType
    {
        POSITIVE_HM("POSITIVE_HM", 0, "+H:mm", "Hm", true), 
        POSITIVE_HMS("POSITIVE_HMS", 1, "+H:mm:ss", "Hms", true), 
        NEGATIVE_HM("NEGATIVE_HM", 2, "-H:mm", "Hm", false), 
        NEGATIVE_HMS("NEGATIVE_HMS", 3, "-H:mm:ss", "Hms", false), 
        POSITIVE_H("POSITIVE_H", 4, "+H", "H", true), 
        NEGATIVE_H("NEGATIVE_H", 5, "-H", "H", false);
        
        private String _defaultPattern;
        private String _required;
        private boolean _isPositive;
        private static final GMTOffsetPatternType[] $VALUES;
        
        private GMTOffsetPatternType(final String s, final int n, final String defaultPattern, final String required, final boolean isPositive) {
            this._defaultPattern = defaultPattern;
            this._required = required;
            this._isPositive = isPositive;
        }
        
        private String defaultPattern() {
            return this._defaultPattern;
        }
        
        private String required() {
            return this._required;
        }
        
        private boolean isPositive() {
            return this._isPositive;
        }
        
        static String access$100(final GMTOffsetPatternType gmtOffsetPatternType) {
            return gmtOffsetPatternType.defaultPattern();
        }
        
        static String access$200(final GMTOffsetPatternType gmtOffsetPatternType) {
            return gmtOffsetPatternType.required();
        }
        
        static boolean access$300(final GMTOffsetPatternType gmtOffsetPatternType) {
            return gmtOffsetPatternType.isPositive();
        }
        
        static {
            $VALUES = new GMTOffsetPatternType[] { GMTOffsetPatternType.POSITIVE_HM, GMTOffsetPatternType.POSITIVE_HMS, GMTOffsetPatternType.NEGATIVE_HM, GMTOffsetPatternType.NEGATIVE_HMS, GMTOffsetPatternType.POSITIVE_H, GMTOffsetPatternType.NEGATIVE_H };
        }
    }
}
