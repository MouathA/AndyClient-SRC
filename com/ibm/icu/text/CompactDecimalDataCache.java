package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;
import java.util.*;

class CompactDecimalDataCache
{
    private static final String SHORT_STYLE = "short";
    private static final String LONG_STYLE = "long";
    private static final String NUMBER_ELEMENTS = "NumberElements";
    private static final String PATTERN_LONG_PATH = "patternsLong/decimalFormat";
    private static final String PATTERNS_SHORT_PATH = "patternsShort/decimalFormat";
    static final String OTHER = "other";
    static final int MAX_DIGITS = 15;
    private static final String LATIN_NUMBERING_SYSTEM = "latn";
    private final ICUCache cache;
    
    CompactDecimalDataCache() {
        this.cache = new SimpleCache();
    }
    
    DataBundle get(final ULocale uLocale) {
        DataBundle load = (DataBundle)this.cache.get(uLocale);
        if (load == null) {
            load = load(uLocale);
            this.cache.put(uLocale, load);
        }
        return load;
    }
    
    private static DataBundle load(final ULocale uLocale) {
        final NumberingSystem instance = NumberingSystem.getInstance(uLocale);
        final ICUResourceBundle withFallback = ((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", uLocale)).getWithFallback("NumberElements");
        final String name = instance.getName();
        ICUResourceBundle icuResourceBundle = null;
        ICUResourceBundle icuResourceBundle2 = null;
        if (!"latn".equals(name)) {
            final ICUResourceBundle withFallback2 = findWithFallback(withFallback, name, UResFlags.NOT_ROOT);
            icuResourceBundle = findWithFallback(withFallback2, "patternsShort/decimalFormat", UResFlags.NOT_ROOT);
            icuResourceBundle2 = findWithFallback(withFallback2, "patternsLong/decimalFormat", UResFlags.NOT_ROOT);
        }
        if (icuResourceBundle == null) {
            final ICUResourceBundle withFallback3 = getWithFallback(withFallback, "latn", UResFlags.ANY);
            icuResourceBundle = getWithFallback(withFallback3, "patternsShort/decimalFormat", UResFlags.ANY);
            if (icuResourceBundle2 == null) {
                icuResourceBundle2 = findWithFallback(withFallback3, "patternsLong/decimalFormat", UResFlags.ANY);
                if (icuResourceBundle2 != null && isRoot(icuResourceBundle2) && !isRoot(icuResourceBundle)) {
                    icuResourceBundle2 = null;
                }
            }
        }
        final Data loadStyle = loadStyle(icuResourceBundle, uLocale, "short");
        Data loadStyle2;
        if (icuResourceBundle2 == null) {
            loadStyle2 = loadStyle;
        }
        else {
            loadStyle2 = loadStyle(icuResourceBundle2, uLocale, "long");
        }
        return new DataBundle(loadStyle, loadStyle2);
    }
    
    private static ICUResourceBundle findWithFallback(final ICUResourceBundle icuResourceBundle, final String s, final UResFlags uResFlags) {
        if (icuResourceBundle == null) {
            return null;
        }
        final ICUResourceBundle withFallback = icuResourceBundle.findWithFallback(s);
        if (withFallback == null) {
            return null;
        }
        switch (uResFlags) {
            case NOT_ROOT: {
                return isRoot(withFallback) ? null : withFallback;
            }
            case ANY: {
                return withFallback;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }
    
    private static ICUResourceBundle getWithFallback(final ICUResourceBundle icuResourceBundle, final String s, final UResFlags uResFlags) {
        final ICUResourceBundle withFallback = findWithFallback(icuResourceBundle, s, uResFlags);
        if (withFallback == null) {
            throw new MissingResourceException("Cannot find " + s, ICUResourceBundle.class.getName(), s);
        }
        return withFallback;
    }
    
    private static boolean isRoot(final ICUResourceBundle icuResourceBundle) {
        final ULocale uLocale = icuResourceBundle.getULocale();
        return uLocale.equals(ULocale.ROOT) || uLocale.toString().equals("root");
    }
    
    private static Data loadStyle(final ICUResourceBundle icuResourceBundle, final ULocale uLocale, final String s) {
        final int size = icuResourceBundle.getSize();
        final Data data = new Data(new long[15], new HashMap());
        while (0 < size) {
            populateData(icuResourceBundle.get(0), uLocale, s, data);
            int n = 0;
            ++n;
        }
        fillInMissing(data);
        return data;
    }
    
    private static void populateData(final UResourceBundle p0, final ULocale p1, final String p2, final Data p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/ibm/icu/util/UResourceBundle.getKey:()Ljava/lang/String;
        //     4: invokestatic    java/lang/Long.parseLong:(Ljava/lang/String;)J
        //     7: lstore          4
        //     9: lload           4
        //    11: l2d            
        //    12: invokestatic    java/lang/Math.log10:(D)D
        //    15: d2i            
        //    16: istore          6
        //    18: iload           6
        //    20: bipush          15
        //    22: if_icmplt       26
        //    25: return         
        //    26: aload_0        
        //    27: invokevirtual   com/ibm/icu/util/UResourceBundle.getSize:()I
        //    30: istore          7
        //    32: iconst_0       
        //    33: iload           7
        //    35: if_icmpge       164
        //    38: aload_0        
        //    39: iconst_0       
        //    40: invokevirtual   com/ibm/icu/util/UResourceBundle.get:(I)Lcom/ibm/icu/util/UResourceBundle;
        //    43: astore          11
        //    45: aload           11
        //    47: invokevirtual   com/ibm/icu/util/UResourceBundle.getKey:()Ljava/lang/String;
        //    50: astore          12
        //    52: aload           11
        //    54: invokevirtual   com/ibm/icu/util/UResourceBundle.getString:()Ljava/lang/String;
        //    57: astore          13
        //    59: aload           12
        //    61: ldc             "other"
        //    63: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    66: ifeq            69
        //    69: aload           12
        //    71: iload           6
        //    73: aload           13
        //    75: aload_1        
        //    76: aload_2        
        //    77: aload_3        
        //    78: invokestatic    com/ibm/icu/text/CompactDecimalDataCache.populatePrefixSuffix:(Ljava/lang/String;ILjava/lang/String;Lcom/ibm/icu/util/ULocale;Ljava/lang/String;Lcom/ibm/icu/text/CompactDecimalDataCache$Data;)I
        //    81: istore          14
        //    83: iload           14
        //    85: iconst_0       
        //    86: if_icmpeq       158
        //    89: iconst_0       
        //    90: ifeq            154
        //    93: new             Ljava/lang/IllegalArgumentException;
        //    96: dup            
        //    97: new             Ljava/lang/StringBuilder;
        //   100: dup            
        //   101: invokespecial   java/lang/StringBuilder.<init>:()V
        //   104: ldc             "Plural variant '"
        //   106: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   109: aload           12
        //   111: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   114: ldc             "' template '"
        //   116: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   119: aload           13
        //   121: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   124: ldc             "' for 10^"
        //   126: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   129: iload           6
        //   131: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   134: ldc             " has wrong number of zeros in "
        //   136: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   139: aload_1        
        //   140: aload_2        
        //   141: invokestatic    com/ibm/icu/text/CompactDecimalDataCache.localeAndStyle:(Lcom/ibm/icu/util/ULocale;Ljava/lang/String;)Ljava/lang/String;
        //   144: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   147: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   150: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //   153: athrow         
        //   154: iload           14
        //   156: istore          8
        //   158: iinc            10, 1
        //   161: goto            32
        //   164: iconst_1       
        //   165: ifne            209
        //   168: new             Ljava/lang/IllegalArgumentException;
        //   171: dup            
        //   172: new             Ljava/lang/StringBuilder;
        //   175: dup            
        //   176: invokespecial   java/lang/StringBuilder.<init>:()V
        //   179: ldc             "No 'other' plural variant defined for 10^"
        //   181: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   184: iload           6
        //   186: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   189: ldc             "in "
        //   191: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   194: aload_1        
        //   195: aload_2        
        //   196: invokestatic    com/ibm/icu/text/CompactDecimalDataCache.localeAndStyle:(Lcom/ibm/icu/util/ULocale;Ljava/lang/String;)Ljava/lang/String;
        //   199: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   202: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   205: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //   208: athrow         
        //   209: lload           4
        //   211: lstore          10
        //   213: iconst_1       
        //   214: iconst_0       
        //   215: if_icmpge       232
        //   218: lload           10
        //   220: ldc2_w          10
        //   223: ldiv           
        //   224: lstore          10
        //   226: iinc            12, 1
        //   229: goto            213
        //   232: aload_3        
        //   233: getfield        com/ibm/icu/text/CompactDecimalDataCache$Data.divisors:[J
        //   236: iload           6
        //   238: lload           10
        //   240: lastore        
        //   241: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static int populatePrefixSuffix(final String s, final int n, final String s2, final ULocale uLocale, final String s3, final Data data) {
        final int index = s2.indexOf("0");
        final int lastIndex = s2.lastIndexOf("0");
        if (index == -1) {
            throw new IllegalArgumentException("Expect at least one zero in template '" + s2 + "' for variant '" + s + "' for 10^" + n + " in " + localeAndStyle(uLocale, s3));
        }
        final String fixQuotes = fixQuotes(s2.substring(0, index));
        final String fixQuotes2 = fixQuotes(s2.substring(lastIndex + 1));
        saveUnit(new DecimalFormat.Unit(fixQuotes, fixQuotes2), s, n, data.units);
        if (fixQuotes.trim().length() == 0 && fixQuotes2.trim().length() == 0) {
            return n + 1;
        }
        int n2;
        for (n2 = index + 1; n2 <= lastIndex && s2.charAt(n2) == '0'; ++n2) {}
        return n2 - index;
    }
    
    private static String fixQuotes(final String s) {
        final StringBuilder sb = new StringBuilder();
        final int length = s.length();
        QuoteState outside = QuoteState.OUTSIDE;
        while (0 < length) {
            final char char1 = s.charAt(0);
            if (char1 == '\'') {
                if (outside == QuoteState.INSIDE_EMPTY) {
                    sb.append('\'');
                }
            }
            else {
                sb.append(char1);
            }
            switch (outside) {
                case OUTSIDE: {
                    outside = ((char1 == '\'') ? QuoteState.INSIDE_EMPTY : QuoteState.OUTSIDE);
                    break;
                }
                case INSIDE_EMPTY:
                case INSIDE_FULL: {
                    outside = ((char1 == '\'') ? QuoteState.OUTSIDE : QuoteState.INSIDE_FULL);
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    private static String localeAndStyle(final ULocale uLocale, final String s) {
        return "locale '" + uLocale + "' style '" + s + "'";
    }
    
    private static void fillInMissing(final Data data) {
        long n = 1L;
        while (0 < data.divisors.length) {
            if (((DecimalFormat.Unit[])data.units.get("other"))[0] == null) {
                data.divisors[0] = n;
                copyFromPreviousIndex(0, data.units);
            }
            else {
                n = data.divisors[0];
                propagateOtherToMissing(0, data.units);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    private static void propagateOtherToMissing(final int n, final Map map) {
        final DecimalFormat.Unit unit = map.get("other")[n];
        for (final DecimalFormat.Unit[] array : map.values()) {
            if (array[n] == null) {
                array[n] = unit;
            }
        }
    }
    
    private static void copyFromPreviousIndex(final int n, final Map map) {
        for (final DecimalFormat.Unit[] array : map.values()) {
            if (n == 0) {
                array[n] = DecimalFormat.NULL_UNIT;
            }
            else {
                array[n] = array[n - 1];
            }
        }
    }
    
    private static void saveUnit(final DecimalFormat.Unit unit, final String s, final int n, final Map map) {
        DecimalFormat.Unit[] array = map.get(s);
        if (array == null) {
            array = new DecimalFormat.Unit[15];
            map.put(s, array);
        }
        array[n] = unit;
    }
    
    static DecimalFormat.Unit getUnit(final Map map, final String s, final int n) {
        DecimalFormat.Unit[] array = map.get(s);
        if (array == null) {
            array = map.get("other");
        }
        return array[n];
    }
    
    private enum QuoteState
    {
        OUTSIDE("OUTSIDE", 0), 
        INSIDE_EMPTY("INSIDE_EMPTY", 1), 
        INSIDE_FULL("INSIDE_FULL", 2);
        
        private static final QuoteState[] $VALUES;
        
        private QuoteState(final String s, final int n) {
        }
        
        static {
            $VALUES = new QuoteState[] { QuoteState.OUTSIDE, QuoteState.INSIDE_EMPTY, QuoteState.INSIDE_FULL };
        }
    }
    
    private enum UResFlags
    {
        ANY("ANY", 0), 
        NOT_ROOT("NOT_ROOT", 1);
        
        private static final UResFlags[] $VALUES;
        
        private UResFlags(final String s, final int n) {
        }
        
        static {
            $VALUES = new UResFlags[] { UResFlags.ANY, UResFlags.NOT_ROOT };
        }
    }
    
    static class DataBundle
    {
        Data shortData;
        Data longData;
        
        DataBundle(final Data shortData, final Data longData) {
            this.shortData = shortData;
            this.longData = longData;
        }
    }
    
    static class Data
    {
        long[] divisors;
        Map units;
        
        Data(final long[] divisors, final Map units) {
            this.divisors = divisors;
            this.units = units;
        }
    }
}
