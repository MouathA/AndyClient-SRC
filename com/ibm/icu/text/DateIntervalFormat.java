package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.text.*;
import java.util.*;
import java.io.*;
import com.ibm.icu.impl.*;

public class DateIntervalFormat extends UFormat
{
    private static final long serialVersionUID = 1L;
    private static ICUCache LOCAL_PATTERN_CACHE;
    private DateIntervalInfo fInfo;
    private SimpleDateFormat fDateFormat;
    private Calendar fFromCalendar;
    private Calendar fToCalendar;
    private String fSkeleton;
    private boolean isDateIntervalInfoDefault;
    private transient Map fIntervalPatterns;
    
    private DateIntervalFormat() {
        this.fSkeleton = null;
        this.fIntervalPatterns = null;
    }
    
    @Deprecated
    public DateIntervalFormat(final String fSkeleton, final DateIntervalInfo fInfo, final SimpleDateFormat fDateFormat) {
        this.fSkeleton = null;
        this.fIntervalPatterns = null;
        this.fDateFormat = fDateFormat;
        fInfo.freeze();
        this.fSkeleton = fSkeleton;
        this.fInfo = fInfo;
        this.isDateIntervalInfoDefault = false;
        this.fFromCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
        this.fToCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
        this.initializePattern(null);
    }
    
    private DateIntervalFormat(final String fSkeleton, final ULocale uLocale, final SimpleDateFormat fDateFormat) {
        this.fSkeleton = null;
        this.fIntervalPatterns = null;
        this.fDateFormat = fDateFormat;
        this.fSkeleton = fSkeleton;
        this.fInfo = new DateIntervalInfo(uLocale).freeze();
        this.isDateIntervalInfoDefault = true;
        this.fFromCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
        this.fToCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
        this.initializePattern(DateIntervalFormat.LOCAL_PATTERN_CACHE);
    }
    
    public static final DateIntervalFormat getInstance(final String s) {
        return getInstance(s, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static final DateIntervalFormat getInstance(final String s, final Locale locale) {
        return getInstance(s, ULocale.forLocale(locale));
    }
    
    public static final DateIntervalFormat getInstance(final String s, final ULocale uLocale) {
        return new DateIntervalFormat(s, uLocale, new SimpleDateFormat(DateTimePatternGenerator.getInstance(uLocale).getBestPattern(s), uLocale));
    }
    
    public static final DateIntervalFormat getInstance(final String s, final DateIntervalInfo dateIntervalInfo) {
        return getInstance(s, ULocale.getDefault(ULocale.Category.FORMAT), dateIntervalInfo);
    }
    
    public static final DateIntervalFormat getInstance(final String s, final Locale locale, final DateIntervalInfo dateIntervalInfo) {
        return getInstance(s, ULocale.forLocale(locale), dateIntervalInfo);
    }
    
    public static final DateIntervalFormat getInstance(final String s, final ULocale uLocale, DateIntervalInfo dateIntervalInfo) {
        dateIntervalInfo = (DateIntervalInfo)dateIntervalInfo.clone();
        return new DateIntervalFormat(s, dateIntervalInfo, new SimpleDateFormat(DateTimePatternGenerator.getInstance(uLocale).getBestPattern(s), uLocale));
    }
    
    @Override
    public Object clone() {
        final DateIntervalFormat dateIntervalFormat = (DateIntervalFormat)super.clone();
        dateIntervalFormat.fDateFormat = (SimpleDateFormat)this.fDateFormat.clone();
        dateIntervalFormat.fInfo = (DateIntervalInfo)this.fInfo.clone();
        dateIntervalFormat.fFromCalendar = (Calendar)this.fFromCalendar.clone();
        dateIntervalFormat.fToCalendar = (Calendar)this.fToCalendar.clone();
        return dateIntervalFormat;
    }
    
    @Override
    public final StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (o instanceof DateInterval) {
            return this.format((DateInterval)o, sb, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object (" + o.getClass().getName() + ") as a DateInterval");
    }
    
    public final StringBuffer format(final DateInterval dateInterval, final StringBuffer sb, final FieldPosition fieldPosition) {
        this.fFromCalendar.setTimeInMillis(dateInterval.getFromDate());
        this.fToCalendar.setTimeInMillis(dateInterval.getToDate());
        return this.format(this.fFromCalendar, this.fToCalendar, sb, fieldPosition);
    }
    
    public final StringBuffer format(final Calendar calendar, final Calendar calendar2, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (!calendar.isEquivalentTo(calendar2)) {
            throw new IllegalArgumentException("can not format on two different calendars");
        }
        if (calendar.get(0) == calendar2.get(0)) {
            if (calendar.get(1) == calendar2.get(1)) {
                if (calendar.get(2) == calendar2.get(2)) {
                    if (calendar.get(5) == calendar2.get(5)) {
                        if (calendar.get(9) == calendar2.get(9)) {
                            if (calendar.get(10) == calendar2.get(10)) {
                                if (calendar.get(12) == calendar2.get(12)) {
                                    return this.fDateFormat.format(calendar, sb, fieldPosition);
                                }
                            }
                        }
                    }
                }
            }
        }
        final DateIntervalInfo.PatternInfo patternInfo = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[12]);
        if (patternInfo == null) {
            if (this.fDateFormat.isFieldUnitIgnored(12)) {
                return this.fDateFormat.format(calendar, sb, fieldPosition);
            }
            return this.fallbackFormat(calendar, calendar2, sb, fieldPosition);
        }
        else {
            if (patternInfo.getFirstPart() == null) {
                return this.fallbackFormat(calendar, calendar2, sb, fieldPosition, patternInfo.getSecondPart());
            }
            Calendar calendar3;
            Calendar calendar4;
            if (patternInfo.firstDateInPtnIsLaterDate()) {
                calendar3 = calendar2;
                calendar4 = calendar;
            }
            else {
                calendar3 = calendar;
                calendar4 = calendar2;
            }
            final String pattern = this.fDateFormat.toPattern();
            this.fDateFormat.applyPattern(patternInfo.getFirstPart());
            this.fDateFormat.format(calendar3, sb, fieldPosition);
            if (patternInfo.getSecondPart() != null) {
                this.fDateFormat.applyPattern(patternInfo.getSecondPart());
                this.fDateFormat.format(calendar4, sb, fieldPosition);
            }
            this.fDateFormat.applyPattern(pattern);
            return sb;
        }
    }
    
    private final StringBuffer fallbackFormat(final Calendar calendar, final Calendar calendar2, final StringBuffer sb, final FieldPosition fieldPosition) {
        sb.append(MessageFormat.format(this.fInfo.getFallbackIntervalPattern(), this.fDateFormat.format(calendar, new StringBuffer(64), fieldPosition).toString(), this.fDateFormat.format(calendar2, new StringBuffer(64), fieldPosition).toString()));
        return sb;
    }
    
    private final StringBuffer fallbackFormat(final Calendar calendar, final Calendar calendar2, final StringBuffer sb, final FieldPosition fieldPosition, final String s) {
        final String pattern = this.fDateFormat.toPattern();
        this.fDateFormat.applyPattern(s);
        this.fallbackFormat(calendar, calendar2, sb, fieldPosition);
        this.fDateFormat.applyPattern(pattern);
        return sb;
    }
    
    @Override
    @Deprecated
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        throw new UnsupportedOperationException("parsing is not supported");
    }
    
    public DateIntervalInfo getDateIntervalInfo() {
        return (DateIntervalInfo)this.fInfo.clone();
    }
    
    public void setDateIntervalInfo(final DateIntervalInfo dateIntervalInfo) {
        this.fInfo = (DateIntervalInfo)dateIntervalInfo.clone();
        this.isDateIntervalInfoDefault = false;
        this.fInfo.freeze();
        if (this.fDateFormat != null) {
            this.initializePattern(null);
        }
    }
    
    public DateFormat getDateFormat() {
        return (DateFormat)this.fDateFormat.clone();
    }
    
    private void initializePattern(final ICUCache icuCache) {
        final String pattern = this.fDateFormat.toPattern();
        final ULocale locale = this.fDateFormat.getLocale();
        Object o = null;
        Map<Object, Object> unmodifiableMap = null;
        if (icuCache != null) {
            if (this.fSkeleton != null) {
                o = locale.toString() + "+" + pattern + "+" + this.fSkeleton;
            }
            else {
                o = locale.toString() + "+" + pattern;
            }
            unmodifiableMap = (Map<Object, Object>)icuCache.get(o);
        }
        if (unmodifiableMap == null) {
            unmodifiableMap = Collections.unmodifiableMap((Map<?, ?>)this.initializeIntervalPattern(pattern, locale));
            if (icuCache != null) {
                icuCache.put(o, unmodifiableMap);
            }
        }
        this.fIntervalPatterns = unmodifiableMap;
    }
    
    private Map initializeIntervalPattern(final String s, final ULocale uLocale) {
        final DateTimePatternGenerator instance = DateTimePatternGenerator.getInstance(uLocale);
        if (this.fSkeleton == null) {
            this.fSkeleton = instance.getSkeleton(s);
        }
        String s2 = this.fSkeleton;
        final HashMap<String, DateIntervalInfo.PatternInfo> hashMap = new HashMap<String, DateIntervalInfo.PatternInfo>();
        final StringBuilder sb = new StringBuilder(s2.length());
        final StringBuilder sb2 = new StringBuilder(s2.length());
        final StringBuilder sb3 = new StringBuilder(s2.length());
        final StringBuilder sb4 = new StringBuilder(s2.length());
        getDateTimeSkeleton(s2, sb, sb2, sb3, sb4);
        final String string = sb.toString();
        final String string2 = sb3.toString();
        if (!this.genSeparateDateTimePtn(sb2.toString(), sb4.toString(), hashMap)) {
            if (sb3.length() != 0 && sb.length() == 0) {
                final DateIntervalInfo.PatternInfo patternInfo = new DateIntervalInfo.PatternInfo(null, instance.getBestPattern("yMd" + string2), this.fInfo.getDefaultOrder());
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], patternInfo);
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], patternInfo);
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], patternInfo);
            }
            return hashMap;
        }
        if (sb3.length() != 0) {
            if (sb.length() == 0) {
                final DateIntervalInfo.PatternInfo patternInfo2 = new DateIntervalInfo.PatternInfo(null, instance.getBestPattern("yMd" + string2), this.fInfo.getDefaultOrder());
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], patternInfo2);
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], patternInfo2);
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], patternInfo2);
            }
            else {
                if (!fieldExistsInSkeleton(5, string)) {
                    s2 = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5] + s2;
                    this.genFallbackPattern(5, s2, hashMap, instance);
                }
                if (!fieldExistsInSkeleton(2, string)) {
                    s2 = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2] + s2;
                    this.genFallbackPattern(2, s2, hashMap, instance);
                }
                if (!fieldExistsInSkeleton(1, string)) {
                    this.genFallbackPattern(1, DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1] + s2, hashMap, instance);
                }
                final String[] dateTimePatterns = new CalendarData(uLocale, null).getDateTimePatterns();
                final String bestPattern = instance.getBestPattern(string);
                this.concatSingleDate2TimeInterval(dateTimePatterns[8], bestPattern, 9, hashMap);
                this.concatSingleDate2TimeInterval(dateTimePatterns[8], bestPattern, 10, hashMap);
                this.concatSingleDate2TimeInterval(dateTimePatterns[8], bestPattern, 12, hashMap);
            }
        }
        return hashMap;
    }
    
    private void genFallbackPattern(final int n, final String s, final Map map, final DateTimePatternGenerator dateTimePatternGenerator) {
        map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], new DateIntervalInfo.PatternInfo(null, dateTimePatternGenerator.getBestPattern(s), this.fInfo.getDefaultOrder()));
    }
    
    private static void getDateTimeSkeleton(final String s, final StringBuilder sb, final StringBuilder sb2, final StringBuilder sb3, final StringBuilder sb4) {
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            switch (char1) {
                case 69: {
                    sb.append(char1);
                    int n = 0;
                    ++n;
                    break;
                }
                case 100: {
                    sb.append(char1);
                    int n2 = 0;
                    ++n2;
                    break;
                }
                case 77: {
                    sb.append(char1);
                    int n3 = 0;
                    ++n3;
                    break;
                }
                case 121: {
                    sb.append(char1);
                    int n4 = 0;
                    ++n4;
                    break;
                }
                case 68:
                case 70:
                case 71:
                case 76:
                case 81:
                case 87:
                case 89:
                case 99:
                case 101:
                case 103:
                case 108:
                case 113:
                case 117:
                case 119: {
                    sb2.append(char1);
                    sb.append(char1);
                    break;
                }
                case 97: {
                    sb3.append(char1);
                    break;
                }
                case 104: {
                    sb3.append(char1);
                    int n5 = 0;
                    ++n5;
                    break;
                }
                case 72: {
                    sb3.append(char1);
                    int n6 = 0;
                    ++n6;
                    break;
                }
                case 109: {
                    sb3.append(char1);
                    int n7 = 0;
                    ++n7;
                    break;
                }
                case 122: {
                    int n8 = 0;
                    ++n8;
                    sb3.append(char1);
                    break;
                }
                case 118: {
                    int n9 = 0;
                    ++n9;
                    sb3.append(char1);
                    break;
                }
                case 65:
                case 75:
                case 83:
                case 86:
                case 90:
                case 106:
                case 107:
                case 115: {
                    sb3.append(char1);
                    sb4.append(char1);
                    break;
                }
            }
            int n10 = 0;
            ++n10;
        }
        if (false) {
            sb2.append('y');
        }
        if (false) {
            if (0 < 3) {
                sb2.append('M');
            }
            else {
                while (0 < 0 && 0 < 5) {
                    sb2.append('M');
                    int n10 = 0;
                    ++n10;
                }
            }
        }
        if (false) {
            if (0 <= 3) {
                sb2.append('E');
            }
            else {
                while (0 < 0 && 0 < 5) {
                    sb2.append('E');
                    int n10 = 0;
                    ++n10;
                }
            }
        }
        if (false) {
            sb2.append('d');
        }
        if (false) {
            sb4.append('H');
        }
        else if (false) {
            sb4.append('h');
        }
        if (false) {
            sb4.append('m');
        }
        if (false) {
            sb4.append('z');
        }
        if (false) {
            sb4.append('v');
        }
    }
    
    private boolean genSeparateDateTimePtn(final String s, final String s2, final Map map) {
        String bestMatchSkeleton;
        if (s2.length() != 0) {
            bestMatchSkeleton = s2;
        }
        else {
            bestMatchSkeleton = s;
        }
        final BestMatchInfo bestSkeleton = this.fInfo.getBestSkeleton(bestMatchSkeleton);
        String s3 = bestSkeleton.bestMatchSkeleton;
        final int bestMatchDistanceInfo = bestSkeleton.bestMatchDistanceInfo;
        if (bestMatchDistanceInfo == -1) {
            return false;
        }
        if (s2.length() == 0) {
            this.genIntervalPattern(5, bestMatchSkeleton, s3, bestMatchDistanceInfo, map);
            final SkeletonAndItsBestMatch genIntervalPattern = this.genIntervalPattern(2, bestMatchSkeleton, s3, bestMatchDistanceInfo, map);
            if (genIntervalPattern != null) {
                s3 = genIntervalPattern.skeleton;
                bestMatchSkeleton = genIntervalPattern.bestMatchSkeleton;
            }
            this.genIntervalPattern(1, bestMatchSkeleton, s3, bestMatchDistanceInfo, map);
        }
        else {
            this.genIntervalPattern(12, bestMatchSkeleton, s3, bestMatchDistanceInfo, map);
            this.genIntervalPattern(10, bestMatchSkeleton, s3, bestMatchDistanceInfo, map);
            this.genIntervalPattern(9, bestMatchSkeleton, s3, bestMatchDistanceInfo, map);
        }
        return true;
    }
    
    private SkeletonAndItsBestMatch genIntervalPattern(final int n, String string, String string2, int bestMatchDistanceInfo, final Map map) {
        SkeletonAndItsBestMatch skeletonAndItsBestMatch = null;
        DateIntervalInfo.PatternInfo patternInfo = this.fInfo.getIntervalPattern(string2, n);
        if (patternInfo == null) {
            if (SimpleDateFormat.isFieldUnitIgnored(string2, n)) {
                map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], new DateIntervalInfo.PatternInfo(this.fDateFormat.toPattern(), null, this.fInfo.getDefaultOrder()));
                return null;
            }
            if (n == 9) {
                final DateIntervalInfo.PatternInfo intervalPattern = this.fInfo.getIntervalPattern(string2, 10);
                if (intervalPattern != null) {
                    map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], intervalPattern);
                }
                return null;
            }
            final String s = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n];
            string2 = s + string2;
            string = s + string;
            patternInfo = this.fInfo.getIntervalPattern(string2, n);
            if (patternInfo == null && bestMatchDistanceInfo == 0) {
                final BestMatchInfo bestSkeleton = this.fInfo.getBestSkeleton(string);
                final String bestMatchSkeleton = bestSkeleton.bestMatchSkeleton;
                bestMatchDistanceInfo = bestSkeleton.bestMatchDistanceInfo;
                if (bestMatchSkeleton.length() != 0 && bestMatchDistanceInfo != -1) {
                    patternInfo = this.fInfo.getIntervalPattern(bestMatchSkeleton, n);
                    string2 = bestMatchSkeleton;
                }
            }
            if (patternInfo != null) {
                skeletonAndItsBestMatch = new SkeletonAndItsBestMatch(string, string2);
            }
        }
        if (patternInfo != null) {
            if (bestMatchDistanceInfo != 0) {
                patternInfo = new DateIntervalInfo.PatternInfo(adjustFieldWidth(string, string2, patternInfo.getFirstPart(), bestMatchDistanceInfo), adjustFieldWidth(string, string2, patternInfo.getSecondPart(), bestMatchDistanceInfo), patternInfo.firstDateInPtnIsLaterDate());
            }
            map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], patternInfo);
        }
        return skeletonAndItsBestMatch;
    }
    
    private static String adjustFieldWidth(final String s, final String s2, String replace, final int n) {
        if (replace == null) {
            return null;
        }
        final int[] array = new int[58];
        final int[] array2 = new int[58];
        DateIntervalInfo.parseSkeleton(s, array);
        DateIntervalInfo.parseSkeleton(s2, array2);
        if (n == 2) {
            replace = replace.replace('v', 'z');
        }
        final StringBuilder sb = new StringBuilder(replace);
        int length = sb.length();
        int n2 = 0;
        while (77 < length) {
            final char char1 = sb.charAt(77);
            int n4 = 0;
            if (char1 != '\0' && 0 > 0) {
                if (77 == 76) {}
                n2 = array2[12];
                final int n3 = array[12];
                if (!false && n3 > 0) {
                    n4 = n3 - 0;
                    while (0 < 0) {
                        sb.insert(77, '\0');
                        int n5 = 0;
                        ++n5;
                    }
                    length += 0;
                }
            }
            int n6 = 0;
            if (char1 == '\'') {
                if (78 < sb.length() && sb.charAt(78) == '\'') {
                    ++n6;
                }
                else {
                    final boolean b = !false;
                }
            }
            else if (!false && ((char1 >= 'a' && char1 <= 'z') || (char1 >= 'A' && char1 <= 'Z'))) {
                ++n4;
            }
            ++n6;
        }
        if (0 > 0) {
            if (77 == 76) {}
            final int n7 = array2[12];
            final int n8 = array[12];
            if (n7 == 0 && 77 > n7) {
                while (0 < 0) {
                    sb.append('\0');
                    ++n2;
                }
            }
        }
        return sb.toString();
    }
    
    private void concatSingleDate2TimeInterval(final String s, final String s2, final int n, final Map map) {
        final DateIntervalInfo.PatternInfo patternInfo = map.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n]);
        if (patternInfo != null) {
            map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], DateIntervalInfo.genPatternInfo(MessageFormat.format(s, patternInfo.getFirstPart() + patternInfo.getSecondPart(), s2), patternInfo.firstDateInPtnIsLaterDate()));
        }
    }
    
    private static boolean fieldExistsInSkeleton(final int n, final String s) {
        return s.indexOf(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n]) != -1;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.initializePattern(this.isDateIntervalInfoDefault ? DateIntervalFormat.LOCAL_PATTERN_CACHE : null);
    }
    
    static {
        DateIntervalFormat.LOCAL_PATTERN_CACHE = new SimpleCache();
    }
    
    private static final class SkeletonAndItsBestMatch
    {
        final String skeleton;
        final String bestMatchSkeleton;
        
        SkeletonAndItsBestMatch(final String skeleton, final String bestMatchSkeleton) {
            this.skeleton = skeleton;
            this.bestMatchSkeleton = bestMatchSkeleton;
        }
    }
    
    static final class BestMatchInfo
    {
        final String bestMatchSkeleton;
        final int bestMatchDistanceInfo;
        
        BestMatchInfo(final String bestMatchSkeleton, final int bestMatchDistanceInfo) {
            this.bestMatchSkeleton = bestMatchSkeleton;
            this.bestMatchDistanceInfo = bestMatchDistanceInfo;
        }
    }
}
