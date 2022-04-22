package com.ibm.icu.text;

import java.io.*;
import com.ibm.icu.util.*;
import java.util.*;
import com.ibm.icu.impl.*;

public class DateIntervalInfo implements Cloneable, Freezable<DateIntervalInfo>, Serializable
{
    static final int currentSerialVersion = 1;
    static final String[] CALENDAR_FIELD_TO_PATTERN_LETTER;
    private static final long serialVersionUID = 1L;
    private static final int MINIMUM_SUPPORTED_CALENDAR_FIELD = 12;
    private static String FALLBACK_STRING;
    private static String LATEST_FIRST_PREFIX;
    private static String EARLIEST_FIRST_PREFIX;
    private static final ICUCache DIICACHE;
    private String fFallbackIntervalPattern;
    private boolean fFirstDateInPtnIsLaterDate;
    private Map fIntervalPatterns;
    private transient boolean frozen;
    private transient boolean fIntervalPatternsReadOnly;
    
    @Deprecated
    public DateIntervalInfo() {
        this.fFirstDateInPtnIsLaterDate = false;
        this.fIntervalPatterns = null;
        this.frozen = false;
        this.fIntervalPatternsReadOnly = false;
        this.fIntervalPatterns = new HashMap();
        this.fFallbackIntervalPattern = "{0} \u2013 {1}";
    }
    
    public DateIntervalInfo(final ULocale uLocale) {
        this.fFirstDateInPtnIsLaterDate = false;
        this.fIntervalPatterns = null;
        this.frozen = false;
        this.fIntervalPatternsReadOnly = false;
        this.initializeData(uLocale);
    }
    
    private void initializeData(final ULocale uLocale) {
        final String string = uLocale.toString();
        final DateIntervalInfo dateIntervalInfo = (DateIntervalInfo)DateIntervalInfo.DIICACHE.get(string);
        if (dateIntervalInfo == null) {
            this.setup(uLocale);
            this.fIntervalPatternsReadOnly = true;
            DateIntervalInfo.DIICACHE.put(string, ((DateIntervalInfo)this.clone()).freeze());
        }
        else {
            this.initializeFromReadOnlyPatterns(dateIntervalInfo);
        }
    }
    
    private void initializeFromReadOnlyPatterns(final DateIntervalInfo dateIntervalInfo) {
        this.fFallbackIntervalPattern = dateIntervalInfo.fFallbackIntervalPattern;
        this.fFirstDateInPtnIsLaterDate = dateIntervalInfo.fFirstDateInPtnIsLaterDate;
        this.fIntervalPatterns = dateIntervalInfo.fIntervalPatterns;
        this.fIntervalPatternsReadOnly = true;
    }
    
    private void setup(final ULocale uLocale) {
        this.fIntervalPatterns = new HashMap(19);
        this.fFallbackIntervalPattern = "{0} \u2013 {1}";
        final HashSet<String> set = new HashSet<String>();
        try {
            ULocale fallback = uLocale;
            String keywordValue = uLocale.getKeywordValue("calendar");
            if (keywordValue == null) {
                keywordValue = Calendar.getKeywordValuesForLocale("calendar", uLocale, true)[0];
            }
            if (keywordValue == null) {
                keywordValue = "gregorian";
            }
            while (fallback.getName().length() != 0) {
                final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", fallback);
                final ICUResourceBundle withFallback = icuResourceBundle.getWithFallback("calendar/" + keywordValue + "/intervalFormats");
                this.setFallbackIntervalPattern(withFallback.getStringWithFallback(DateIntervalInfo.FALLBACK_STRING));
                for (int size = withFallback.getSize(), i = 0; i < size; ++i) {
                    final String key = withFallback.get(i).getKey();
                    if (!set.contains(key)) {
                        set.add(key);
                        if (key.compareTo(DateIntervalInfo.FALLBACK_STRING) != 0) {
                            final ICUResourceBundle icuResourceBundle2 = (ICUResourceBundle)withFallback.get(key);
                            for (int size2 = icuResourceBundle2.getSize(), j = 0; j < size2; ++j) {
                                final String key2 = icuResourceBundle2.get(j).getKey();
                                final String string = icuResourceBundle2.get(j).getString();
                                int n = -1;
                                if (key2.compareTo(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1]) == 0) {
                                    n = 1;
                                }
                                else if (key2.compareTo(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2]) == 0) {
                                    n = 2;
                                }
                                else if (key2.compareTo(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5]) == 0) {
                                    n = 5;
                                }
                                else if (key2.compareTo(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[9]) == 0) {
                                    n = 9;
                                }
                                else if (key2.compareTo(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[10]) == 0) {
                                    n = 10;
                                }
                                else if (key2.compareTo(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[12]) == 0) {
                                    n = 12;
                                }
                                if (n != -1) {
                                    this.setIntervalPatternInternally(key, key2, string);
                                }
                            }
                        }
                    }
                }
                try {
                    fallback = new ULocale(icuResourceBundle.get("%%Parent").getString());
                }
                catch (MissingResourceException ex) {
                    fallback = fallback.getFallback();
                }
                if (fallback == null || fallback.getBaseName().equals("root")) {
                    return;
                }
            }
        }
        catch (MissingResourceException ex2) {}
    }
    
    private static int splitPatternInto2Part(final String s) {
        boolean b = false;
        int n = '\0';
        int n2 = 0;
        final int[] array = new int[58];
        final int n3 = 'A';
        boolean b2 = false;
        int i;
        for (i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 != n && n2 > 0) {
                if (array[n - n3] != 0) {
                    b2 = true;
                    break;
                }
                array[n - n3] = 1;
                n2 = 0;
            }
            if (char1 == '\'') {
                if (i + 1 < s.length() && s.charAt(i + 1) == '\'') {
                    ++i;
                }
                else {
                    b = !b;
                }
            }
            else if (!b && ((char1 >= 'a' && char1 <= 'z') || (char1 >= 'A' && char1 <= 'Z'))) {
                n = char1;
                ++n2;
            }
        }
        if (n2 > 0 && !b2 && array[n - n3] == 0) {
            n2 = 0;
        }
        return i - n2;
    }
    
    public void setIntervalPattern(final String s, final int n, final String s2) {
        if (this.frozen) {
            throw new UnsupportedOperationException("no modification is allowed after DII is frozen");
        }
        if (n > 12) {
            throw new IllegalArgumentException("calendar field is larger than MINIMUM_SUPPORTED_CALENDAR_FIELD");
        }
        if (this.fIntervalPatternsReadOnly) {
            this.fIntervalPatterns = cloneIntervalPatterns(this.fIntervalPatterns);
            this.fIntervalPatternsReadOnly = false;
        }
        final PatternInfo setIntervalPatternInternally = this.setIntervalPatternInternally(s, DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], s2);
        if (n == 11) {
            this.setIntervalPattern(s, DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[9], setIntervalPatternInternally);
            this.setIntervalPattern(s, DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[10], setIntervalPatternInternally);
        }
        else if (n == 5 || n == 7) {
            this.setIntervalPattern(s, DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], setIntervalPatternInternally);
        }
    }
    
    private PatternInfo setIntervalPatternInternally(final String s, final String s2, String s3) {
        Map<String, PatternInfo> map = this.fIntervalPatterns.get(s);
        int n = 0;
        if (map == null) {
            map = new HashMap<String, PatternInfo>();
            n = 1;
        }
        boolean fFirstDateInPtnIsLaterDate = this.fFirstDateInPtnIsLaterDate;
        if (s3.startsWith(DateIntervalInfo.LATEST_FIRST_PREFIX)) {
            fFirstDateInPtnIsLaterDate = true;
            s3 = s3.substring(DateIntervalInfo.LATEST_FIRST_PREFIX.length(), s3.length());
        }
        else if (s3.startsWith(DateIntervalInfo.EARLIEST_FIRST_PREFIX)) {
            fFirstDateInPtnIsLaterDate = false;
            s3 = s3.substring(DateIntervalInfo.EARLIEST_FIRST_PREFIX.length(), s3.length());
        }
        final PatternInfo genPatternInfo = genPatternInfo(s3, fFirstDateInPtnIsLaterDate);
        map.put(s2, genPatternInfo);
        if (n == 1) {
            this.fIntervalPatterns.put(s, map);
        }
        return genPatternInfo;
    }
    
    private void setIntervalPattern(final String s, final String s2, final PatternInfo patternInfo) {
        this.fIntervalPatterns.get(s).put(s2, patternInfo);
    }
    
    static PatternInfo genPatternInfo(final String s, final boolean b) {
        final int splitPatternInto2Part = splitPatternInto2Part(s);
        final String substring = s.substring(0, splitPatternInto2Part);
        String substring2 = null;
        if (splitPatternInto2Part < s.length()) {
            substring2 = s.substring(splitPatternInto2Part, s.length());
        }
        return new PatternInfo(substring, substring2, b);
    }
    
    public PatternInfo getIntervalPattern(final String s, final int n) {
        if (n > 12) {
            throw new IllegalArgumentException("no support for field less than MINUTE");
        }
        final Map<Object, PatternInfo> map = this.fIntervalPatterns.get(s);
        if (map != null) {
            final PatternInfo patternInfo = map.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n]);
            if (patternInfo != null) {
                return patternInfo;
            }
        }
        return null;
    }
    
    public String getFallbackIntervalPattern() {
        return this.fFallbackIntervalPattern;
    }
    
    public void setFallbackIntervalPattern(final String fFallbackIntervalPattern) {
        if (this.frozen) {
            throw new UnsupportedOperationException("no modification is allowed after DII is frozen");
        }
        final int index = fFallbackIntervalPattern.indexOf("{0}");
        final int index2 = fFallbackIntervalPattern.indexOf("{1}");
        if (index == -1 || index2 == -1) {
            throw new IllegalArgumentException("no pattern {0} or pattern {1} in fallbackPattern");
        }
        if (index > index2) {
            this.fFirstDateInPtnIsLaterDate = true;
        }
        this.fFallbackIntervalPattern = fFallbackIntervalPattern;
    }
    
    public boolean getDefaultOrder() {
        return this.fFirstDateInPtnIsLaterDate;
    }
    
    public Object clone() {
        if (this.frozen) {
            return this;
        }
        return this.cloneUnfrozenDII();
    }
    
    private Object cloneUnfrozenDII() {
        try {
            final DateIntervalInfo dateIntervalInfo = (DateIntervalInfo)super.clone();
            dateIntervalInfo.fFallbackIntervalPattern = this.fFallbackIntervalPattern;
            dateIntervalInfo.fFirstDateInPtnIsLaterDate = this.fFirstDateInPtnIsLaterDate;
            if (this.fIntervalPatternsReadOnly) {
                dateIntervalInfo.fIntervalPatterns = this.fIntervalPatterns;
                dateIntervalInfo.fIntervalPatternsReadOnly = true;
            }
            else {
                dateIntervalInfo.fIntervalPatterns = cloneIntervalPatterns(this.fIntervalPatterns);
                dateIntervalInfo.fIntervalPatternsReadOnly = false;
            }
            dateIntervalInfo.frozen = false;
            return dateIntervalInfo;
        }
        catch (CloneNotSupportedException ex) {
            throw new IllegalStateException("clone is not supported");
        }
    }
    
    private static Map cloneIntervalPatterns(final Map map) {
        final HashMap<String, HashMap<String, PatternInfo>> hashMap = new HashMap<String, HashMap<String, PatternInfo>>();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            final String s = entry.getKey();
            final Map map2 = (Map)entry.getValue();
            final HashMap<String, PatternInfo> hashMap2 = new HashMap<String, PatternInfo>();
            for (final Map.Entry<String, V> entry2 : map2.entrySet()) {
                hashMap2.put(entry2.getKey(), (PatternInfo)entry2.getValue());
            }
            hashMap.put(s, hashMap2);
        }
        return hashMap;
    }
    
    public boolean isFrozen() {
        return this.frozen;
    }
    
    public DateIntervalInfo freeze() {
        this.frozen = true;
        this.fIntervalPatternsReadOnly = true;
        return this;
    }
    
    public DateIntervalInfo cloneAsThawed() {
        return (DateIntervalInfo)this.cloneUnfrozenDII();
    }
    
    static void parseSkeleton(final String s, final int[] array) {
        final char c = 'A';
        for (int i = 0; i < s.length(); ++i) {
            final int n = s.charAt(i) - c;
            ++array[n];
        }
    }
    
    private static boolean stringNumeric(final int n, final int n2, final char c) {
        return c == 'M' && ((n <= 2 && n2 > 2) || (n > 2 && n2 <= 2));
    }
    
    DateIntervalFormat.BestMatchInfo getBestSkeleton(String replace) {
        String s = replace;
        final int[] array = new int[58];
        final int[] array2 = new int[58];
        boolean b = false;
        if (replace.indexOf(122) != -1) {
            replace = replace.replace('z', 'v');
            b = true;
        }
        parseSkeleton(replace, array);
        int n = Integer.MAX_VALUE;
        int n2 = 0;
        for (final String s2 : this.fIntervalPatterns.keySet()) {
            for (int i = 0; i < array2.length; ++i) {
                array2[i] = 0;
            }
            parseSkeleton(s2, array2);
            int n3 = 0;
            int n4 = 1;
            for (int j = 0; j < array.length; ++j) {
                final int n5 = array[j];
                final int n6 = array2[j];
                if (n5 != n6) {
                    if (n5 == 0) {
                        n4 = -1;
                        n3 += 4096;
                    }
                    else if (n6 == 0) {
                        n4 = -1;
                        n3 += 4096;
                    }
                    else if (stringNumeric(n5, n6, (char)(j + 65))) {
                        n3 += 256;
                    }
                    else {
                        n3 += Math.abs(n5 - n6);
                    }
                }
            }
            if (n3 < n) {
                s = s2;
                n = n3;
                n2 = n4;
            }
            if (n3 == 0) {
                n2 = 0;
                break;
            }
        }
        if (b && n2 != -1) {
            n2 = 2;
        }
        return new DateIntervalFormat.BestMatchInfo(s, n2);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof DateIntervalInfo && this.fIntervalPatterns.equals(((DateIntervalInfo)o).fIntervalPatterns);
    }
    
    @Override
    public int hashCode() {
        return this.fIntervalPatterns.hashCode();
    }
    
    @Deprecated
    public Map getPatterns() {
        final LinkedHashMap<Object, LinkedHashSet> linkedHashMap = new LinkedHashMap<Object, LinkedHashSet>();
        for (final Map.Entry<Object, V> entry : this.fIntervalPatterns.entrySet()) {
            linkedHashMap.put(entry.getKey(), new LinkedHashSet(((Map)entry.getValue()).keySet()));
        }
        return linkedHashMap;
    }
    
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    public Object freeze() {
        return this.freeze();
    }
    
    static {
        CALENDAR_FIELD_TO_PATTERN_LETTER = new String[] { "G", "y", "M", "w", "W", "d", "D", "E", "F", "a", "h", "H", "m" };
        DateIntervalInfo.FALLBACK_STRING = "fallback";
        DateIntervalInfo.LATEST_FIRST_PREFIX = "latestFirst:";
        DateIntervalInfo.EARLIEST_FIRST_PREFIX = "earliestFirst:";
        DIICACHE = new SimpleCache();
    }
    
    public static final class PatternInfo implements Cloneable, Serializable
    {
        static final int currentSerialVersion = 1;
        private static final long serialVersionUID = 1L;
        private final String fIntervalPatternFirstPart;
        private final String fIntervalPatternSecondPart;
        private final boolean fFirstDateInPtnIsLaterDate;
        
        public PatternInfo(final String fIntervalPatternFirstPart, final String fIntervalPatternSecondPart, final boolean fFirstDateInPtnIsLaterDate) {
            this.fIntervalPatternFirstPart = fIntervalPatternFirstPart;
            this.fIntervalPatternSecondPart = fIntervalPatternSecondPart;
            this.fFirstDateInPtnIsLaterDate = fFirstDateInPtnIsLaterDate;
        }
        
        public String getFirstPart() {
            return this.fIntervalPatternFirstPart;
        }
        
        public String getSecondPart() {
            return this.fIntervalPatternSecondPart;
        }
        
        public boolean firstDateInPtnIsLaterDate() {
            return this.fFirstDateInPtnIsLaterDate;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o instanceof PatternInfo) {
                final PatternInfo patternInfo = (PatternInfo)o;
                return Utility.objectEquals(this.fIntervalPatternFirstPart, patternInfo.fIntervalPatternFirstPart) && Utility.objectEquals(this.fIntervalPatternSecondPart, this.fIntervalPatternSecondPart) && this.fFirstDateInPtnIsLaterDate == patternInfo.fFirstDateInPtnIsLaterDate;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            int n = (this.fIntervalPatternFirstPart != null) ? this.fIntervalPatternFirstPart.hashCode() : 0;
            if (this.fIntervalPatternSecondPart != null) {
                n ^= this.fIntervalPatternSecondPart.hashCode();
            }
            if (this.fFirstDateInPtnIsLaterDate) {
                n ^= -1;
            }
            return n;
        }
    }
}
