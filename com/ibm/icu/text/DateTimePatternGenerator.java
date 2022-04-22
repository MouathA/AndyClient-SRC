package com.ibm.icu.text;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.util.*;

public class DateTimePatternGenerator implements Freezable, Cloneable
{
    private static final boolean DEBUG = false;
    public static final int ERA = 0;
    public static final int YEAR = 1;
    public static final int QUARTER = 2;
    public static final int MONTH = 3;
    public static final int WEEK_OF_YEAR = 4;
    public static final int WEEK_OF_MONTH = 5;
    public static final int WEEKDAY = 6;
    public static final int DAY = 7;
    public static final int DAY_OF_YEAR = 8;
    public static final int DAY_OF_WEEK_IN_MONTH = 9;
    public static final int DAYPERIOD = 10;
    public static final int HOUR = 11;
    public static final int MINUTE = 12;
    public static final int SECOND = 13;
    public static final int FRACTIONAL_SECOND = 14;
    public static final int ZONE = 15;
    public static final int TYPE_LIMIT = 16;
    public static final int MATCH_NO_OPTIONS = 0;
    public static final int MATCH_HOUR_FIELD_LENGTH = 2048;
    @Deprecated
    public static final int MATCH_MINUTE_FIELD_LENGTH = 4096;
    @Deprecated
    public static final int MATCH_SECOND_FIELD_LENGTH = 8192;
    public static final int MATCH_ALL_FIELDS_LENGTH = 65535;
    private TreeMap skeleton2pattern;
    private TreeMap basePattern_pattern;
    private String decimal;
    private String dateTimeFormat;
    private String[] appendItemFormats;
    private String[] appendItemNames;
    private char defaultHourFormatChar;
    private boolean frozen;
    private transient DateTimeMatcher current;
    private transient FormatParser fp;
    private transient DistanceInfo _distanceInfo;
    private static final int FRACTIONAL_MASK = 16384;
    private static final int SECOND_AND_FRACTIONAL_MASK = 24576;
    private static ICUCache DTPNG_CACHE;
    private static final String[] CLDR_FIELD_NAME;
    private static final String[] FIELD_NAME;
    private static final String[] CANONICAL_ITEMS;
    private static final Set CANONICAL_SET;
    private Set cldrAvailableFormatKeys;
    private static final int DATE_MASK = 1023;
    private static final int TIME_MASK = 64512;
    private static final int DELTA = 16;
    private static final int NUMERIC = 256;
    private static final int NONE = 0;
    private static final int NARROW = -257;
    private static final int SHORT = -258;
    private static final int LONG = -259;
    private static final int EXTRA_FIELD = 65536;
    private static final int MISSING_FIELD = 4096;
    private static final int[][] types;
    
    public static DateTimePatternGenerator getEmptyInstance() {
        return new DateTimePatternGenerator();
    }
    
    protected DateTimePatternGenerator() {
        this.skeleton2pattern = new TreeMap();
        this.basePattern_pattern = new TreeMap();
        this.decimal = "?";
        this.dateTimeFormat = "{1} {0}";
        this.appendItemFormats = new String[16];
        this.appendItemNames = new String[16];
        while (0 < 16) {
            this.appendItemFormats[0] = "{0} \u251c{2}: {1}\u2524";
            this.appendItemNames[0] = "F" + 0;
            int n = 0;
            ++n;
        }
        this.defaultHourFormatChar = 'H';
        this.frozen = false;
        this.current = new DateTimeMatcher(null);
        this.fp = new FormatParser();
        this._distanceInfo = new DistanceInfo(null);
        this.complete();
        this.cldrAvailableFormatKeys = new HashSet(20);
    }
    
    public static DateTimePatternGenerator getInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static DateTimePatternGenerator getInstance(final ULocale uLocale) {
        return getFrozenInstance(uLocale).cloneAsThawed();
    }
    
    @Deprecated
    public static DateTimePatternGenerator getFrozenInstance(final ULocale p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/ibm/icu/util/ULocale.toString:()Ljava/lang/String;
        //     4: astore_1       
        //     5: getstatic       com/ibm/icu/text/DateTimePatternGenerator.DTPNG_CACHE:Lcom/ibm/icu/impl/ICUCache;
        //     8: aload_1        
        //     9: invokeinterface com/ibm/icu/impl/ICUCache.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    14: checkcast       Lcom/ibm/icu/text/DateTimePatternGenerator;
        //    17: astore_2       
        //    18: aload_2        
        //    19: ifnull          24
        //    22: aload_2        
        //    23: areturn        
        //    24: new             Lcom/ibm/icu/text/DateTimePatternGenerator;
        //    27: dup            
        //    28: invokespecial   com/ibm/icu/text/DateTimePatternGenerator.<init>:()V
        //    31: astore_2       
        //    32: new             Lcom/ibm/icu/text/DateTimePatternGenerator$PatternInfo;
        //    35: dup            
        //    36: invokespecial   com/ibm/icu/text/DateTimePatternGenerator$PatternInfo.<init>:()V
        //    39: astore_3       
        //    40: aconst_null    
        //    41: astore          4
        //    43: iconst_0       
        //    44: iconst_3       
        //    45: if_icmpgt       202
        //    48: iconst_0       
        //    49: aload_0        
        //    50: invokestatic    com/ibm/icu/text/DateFormat.getDateInstance:(ILcom/ibm/icu/util/ULocale;)Lcom/ibm/icu/text/DateFormat;
        //    53: checkcast       Lcom/ibm/icu/text/SimpleDateFormat;
        //    56: astore          6
        //    58: aload_2        
        //    59: aload           6
        //    61: invokevirtual   com/ibm/icu/text/SimpleDateFormat.toPattern:()Ljava/lang/String;
        //    64: iconst_0       
        //    65: aload_3        
        //    66: invokevirtual   com/ibm/icu/text/DateTimePatternGenerator.addPattern:(Ljava/lang/String;ZLcom/ibm/icu/text/DateTimePatternGenerator$PatternInfo;)Lcom/ibm/icu/text/DateTimePatternGenerator;
        //    69: pop            
        //    70: iconst_0       
        //    71: aload_0        
        //    72: invokestatic    com/ibm/icu/text/DateFormat.getTimeInstance:(ILcom/ibm/icu/util/ULocale;)Lcom/ibm/icu/text/DateFormat;
        //    75: checkcast       Lcom/ibm/icu/text/SimpleDateFormat;
        //    78: astore          6
        //    80: aload_2        
        //    81: aload           6
        //    83: invokevirtual   com/ibm/icu/text/SimpleDateFormat.toPattern:()Ljava/lang/String;
        //    86: iconst_0       
        //    87: aload_3        
        //    88: invokevirtual   com/ibm/icu/text/DateTimePatternGenerator.addPattern:(Ljava/lang/String;ZLcom/ibm/icu/text/DateTimePatternGenerator$PatternInfo;)Lcom/ibm/icu/text/DateTimePatternGenerator;
        //    91: pop            
        //    92: iconst_0       
        //    93: iconst_3       
        //    94: if_icmpne       196
        //    97: aload           6
        //    99: invokevirtual   com/ibm/icu/text/SimpleDateFormat.toPattern:()Ljava/lang/String;
        //   102: astore          4
        //   104: new             Lcom/ibm/icu/text/DateTimePatternGenerator$FormatParser;
        //   107: dup            
        //   108: invokespecial   com/ibm/icu/text/DateTimePatternGenerator$FormatParser.<init>:()V
        //   111: astore          7
        //   113: aload           7
        //   115: aload           4
        //   117: invokevirtual   com/ibm/icu/text/DateTimePatternGenerator$FormatParser.set:(Ljava/lang/String;)Lcom/ibm/icu/text/DateTimePatternGenerator$FormatParser;
        //   120: pop            
        //   121: aload           7
        //   123: invokevirtual   com/ibm/icu/text/DateTimePatternGenerator$FormatParser.getItems:()Ljava/util/List;
        //   126: astore          8
        //   128: iconst_0       
        //   129: aload           8
        //   131: invokeinterface java/util/List.size:()I
        //   136: if_icmpge       196
        //   139: aload           8
        //   141: iconst_0       
        //   142: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   147: astore          10
        //   149: aload           10
        //   151: instanceof      Lcom/ibm/icu/text/DateTimePatternGenerator$VariableField;
        //   154: ifeq            190
        //   157: aload           10
        //   159: checkcast       Lcom/ibm/icu/text/DateTimePatternGenerator$VariableField;
        //   162: astore          11
        //   164: aload           11
        //   166: invokevirtual   com/ibm/icu/text/DateTimePatternGenerator$VariableField.getType:()I
        //   169: bipush          11
        //   171: if_icmpne       190
        //   174: aload_2        
        //   175: aload           11
        //   177: invokevirtual   com/ibm/icu/text/DateTimePatternGenerator$VariableField.toString:()Ljava/lang/String;
        //   180: iconst_0       
        //   181: invokevirtual   java/lang/String.charAt:(I)C
        //   184: putfield        com/ibm/icu/text/DateTimePatternGenerator.defaultHourFormatChar:C
        //   187: goto            196
        //   190: iinc            9, 1
        //   193: goto            128
        //   196: iinc            5, 1
        //   199: goto            43
        //   202: ldc_w           "com/ibm/icu/impl/data/icudt51b"
        //   205: aload_0        
        //   206: invokestatic    com/ibm/icu/util/UResourceBundle.getBundleInstance:(Ljava/lang/String;Lcom/ibm/icu/util/ULocale;)Lcom/ibm/icu/util/UResourceBundle;
        //   209: checkcast       Lcom/ibm/icu/impl/ICUResourceBundle;
        //   212: astore          5
        //   214: aload_0        
        //   215: ldc_w           "calendar"
        //   218: invokevirtual   com/ibm/icu/util/ULocale.getKeywordValue:(Ljava/lang/String;)Ljava/lang/String;
        //   221: astore          6
        //   223: aload           6
        //   225: ifnonnull       244
        //   228: ldc_w           "calendar"
        //   231: aload_0        
        //   232: iconst_1       
        //   233: invokestatic    com/ibm/icu/util/Calendar.getKeywordValuesForLocale:(Ljava/lang/String;Lcom/ibm/icu/util/ULocale;Z)[Ljava/lang/String;
        //   236: astore          7
        //   238: aload           7
        //   240: iconst_0       
        //   241: aaload         
        //   242: astore          6
        //   244: aload           6
        //   246: ifnonnull       254
        //   249: ldc_w           "gregorian"
        //   252: astore          6
        //   254: aload           5
        //   256: new             Ljava/lang/StringBuilder;
        //   259: dup            
        //   260: invokespecial   java/lang/StringBuilder.<init>:()V
        //   263: ldc_w           "calendar/"
        //   266: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   269: aload           6
        //   271: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   274: ldc_w           "/appendItems"
        //   277: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   280: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   283: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.getWithFallback:(Ljava/lang/String;)Lcom/ibm/icu/impl/ICUResourceBundle;
        //   286: astore          7
        //   288: iconst_0       
        //   289: aload           7
        //   291: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.getSize:()I
        //   294: if_icmpge       343
        //   297: aload           7
        //   299: iconst_0       
        //   300: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.get:(I)Lcom/ibm/icu/util/UResourceBundle;
        //   303: checkcast       Lcom/ibm/icu/impl/ICUResourceBundle;
        //   306: astore          9
        //   308: aload           7
        //   310: iconst_0       
        //   311: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.get:(I)Lcom/ibm/icu/util/UResourceBundle;
        //   314: invokevirtual   com/ibm/icu/util/UResourceBundle.getKey:()Ljava/lang/String;
        //   317: astore          10
        //   319: aload           9
        //   321: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.getString:()Ljava/lang/String;
        //   324: astore          11
        //   326: aload_2        
        //   327: aload           10
        //   329: invokestatic    com/ibm/icu/text/DateTimePatternGenerator.getAppendFormatNumber:(Ljava/lang/String;)I
        //   332: aload           11
        //   334: invokevirtual   com/ibm/icu/text/DateTimePatternGenerator.setAppendItemFormat:(ILjava/lang/String;)V
        //   337: iinc            8, 1
        //   340: goto            288
        //   343: goto            348
        //   346: astore          7
        //   348: aload           5
        //   350: ldc_w           "fields"
        //   353: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.getWithFallback:(Ljava/lang/String;)Lcom/ibm/icu/impl/ICUResourceBundle;
        //   356: astore          7
        //   358: iconst_0       
        //   359: bipush          16
        //   361: if_icmpge       413
        //   364: iconst_0       
        //   365: invokestatic    com/ibm/icu/text/DateTimePatternGenerator.isCLDRFieldName:(I)Z
        //   368: ifeq            407
        //   371: aload           7
        //   373: getstatic       com/ibm/icu/text/DateTimePatternGenerator.CLDR_FIELD_NAME:[Ljava/lang/String;
        //   376: iconst_0       
        //   377: aaload         
        //   378: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.getWithFallback:(Ljava/lang/String;)Lcom/ibm/icu/impl/ICUResourceBundle;
        //   381: astore          8
        //   383: aload           8
        //   385: ldc_w           "dn"
        //   388: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.getWithFallback:(Ljava/lang/String;)Lcom/ibm/icu/impl/ICUResourceBundle;
        //   391: astore          9
        //   393: aload           9
        //   395: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.getString:()Ljava/lang/String;
        //   398: astore          11
        //   400: aload_2        
        //   401: iconst_0       
        //   402: aload           11
        //   404: invokevirtual   com/ibm/icu/text/DateTimePatternGenerator.setAppendItemName:(ILjava/lang/String;)V
        //   407: iinc            10, 1
        //   410: goto            358
        //   413: goto            418
        //   416: astore          7
        //   418: aconst_null    
        //   419: astore          7
        //   421: aload           5
        //   423: new             Ljava/lang/StringBuilder;
        //   426: dup            
        //   427: invokespecial   java/lang/StringBuilder.<init>:()V
        //   430: ldc_w           "calendar/"
        //   433: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   436: aload           6
        //   438: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   441: ldc_w           "/availableFormats"
        //   444: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   447: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   450: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.getWithFallback:(Ljava/lang/String;)Lcom/ibm/icu/impl/ICUResourceBundle;
        //   453: astore          7
        //   455: goto            460
        //   458: astore          8
        //   460: aload           7
        //   462: ifnull          613
        //   465: iconst_0       
        //   466: aload           7
        //   468: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.getSize:()I
        //   471: if_icmpge       528
        //   474: aload           7
        //   476: iconst_0       
        //   477: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.get:(I)Lcom/ibm/icu/util/UResourceBundle;
        //   480: invokevirtual   com/ibm/icu/util/UResourceBundle.getKey:()Ljava/lang/String;
        //   483: astore          10
        //   485: aload_2        
        //   486: aload           10
        //   488: invokespecial   com/ibm/icu/text/DateTimePatternGenerator.isAvailableFormatSet:(Ljava/lang/String;)Z
        //   491: ifne            522
        //   494: aload_2        
        //   495: aload           10
        //   497: invokespecial   com/ibm/icu/text/DateTimePatternGenerator.setAvailableFormat:(Ljava/lang/String;)V
        //   500: aload           7
        //   502: iconst_0       
        //   503: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.get:(I)Lcom/ibm/icu/util/UResourceBundle;
        //   506: invokevirtual   com/ibm/icu/util/UResourceBundle.getString:()Ljava/lang/String;
        //   509: astore          11
        //   511: aload_2        
        //   512: aload           11
        //   514: aload           10
        //   516: iconst_0       
        //   517: aload_3        
        //   518: invokevirtual   com/ibm/icu/text/DateTimePatternGenerator.addPatternWithSkeleton:(Ljava/lang/String;Ljava/lang/String;ZLcom/ibm/icu/text/DateTimePatternGenerator$PatternInfo;)Lcom/ibm/icu/text/DateTimePatternGenerator;
        //   521: pop            
        //   522: iinc            9, 1
        //   525: goto            465
        //   528: aload           7
        //   530: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.getParent:()Lcom/ibm/icu/util/UResourceBundle;
        //   533: checkcast       Lcom/ibm/icu/impl/ICUResourceBundle;
        //   536: astore          9
        //   538: aload           9
        //   540: ifnonnull       546
        //   543: goto            613
        //   546: aload           9
        //   548: new             Ljava/lang/StringBuilder;
        //   551: dup            
        //   552: invokespecial   java/lang/StringBuilder.<init>:()V
        //   555: ldc_w           "calendar/"
        //   558: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   561: aload           6
        //   563: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   566: ldc_w           "/availableFormats"
        //   569: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   572: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   575: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.getWithFallback:(Ljava/lang/String;)Lcom/ibm/icu/impl/ICUResourceBundle;
        //   578: astore          7
        //   580: goto            588
        //   583: astore          10
        //   585: aconst_null    
        //   586: astore          7
        //   588: aload           7
        //   590: ifnull          610
        //   593: aload           9
        //   595: invokevirtual   com/ibm/icu/impl/ICUResourceBundle.getULocale:()Lcom/ibm/icu/util/ULocale;
        //   598: invokevirtual   com/ibm/icu/util/ULocale.getBaseName:()Ljava/lang/String;
        //   601: ldc_w           "root"
        //   604: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   607: ifeq            610
        //   610: goto            460
        //   613: aload           4
        //   615: ifnull          625
        //   618: aload_2        
        //   619: aload_3        
        //   620: aload           4
        //   622: invokestatic    com/ibm/icu/text/DateTimePatternGenerator.hackTimes:(Lcom/ibm/icu/text/DateTimePatternGenerator;Lcom/ibm/icu/text/DateTimePatternGenerator$PatternInfo;Ljava/lang/String;)V
        //   625: aload_2        
        //   626: aload_0        
        //   627: invokestatic    com/ibm/icu/util/Calendar.getInstance:(Lcom/ibm/icu/util/ULocale;)Lcom/ibm/icu/util/Calendar;
        //   630: aload_0        
        //   631: iconst_2       
        //   632: invokestatic    com/ibm/icu/util/Calendar.getDateTimePattern:(Lcom/ibm/icu/util/Calendar;Lcom/ibm/icu/util/ULocale;I)Ljava/lang/String;
        //   635: invokevirtual   com/ibm/icu/text/DateTimePatternGenerator.setDateTimeFormat:(Ljava/lang/String;)V
        //   638: new             Lcom/ibm/icu/text/DecimalFormatSymbols;
        //   641: dup            
        //   642: aload_0        
        //   643: invokespecial   com/ibm/icu/text/DecimalFormatSymbols.<init>:(Lcom/ibm/icu/util/ULocale;)V
        //   646: astore          9
        //   648: aload_2        
        //   649: aload           9
        //   651: invokevirtual   com/ibm/icu/text/DecimalFormatSymbols.getDecimalSeparator:()C
        //   654: invokestatic    java/lang/String.valueOf:(C)Ljava/lang/String;
        //   657: invokevirtual   com/ibm/icu/text/DateTimePatternGenerator.setDecimal:(Ljava/lang/String;)V
        //   660: aload_2        
        //   661: invokevirtual   com/ibm/icu/text/DateTimePatternGenerator.freeze:()Lcom/ibm/icu/text/DateTimePatternGenerator;
        //   664: pop            
        //   665: getstatic       com/ibm/icu/text/DateTimePatternGenerator.DTPNG_CACHE:Lcom/ibm/icu/impl/ICUCache;
        //   668: aload_1        
        //   669: aload_2        
        //   670: invokeinterface com/ibm/icu/impl/ICUCache.put:(Ljava/lang/Object;Ljava/lang/Object;)V
        //   675: aload_2        
        //   676: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Deprecated
    public char getDefaultHourFormatChar() {
        return this.defaultHourFormatChar;
    }
    
    @Deprecated
    public void setDefaultHourFormatChar(final char defaultHourFormatChar) {
        this.defaultHourFormatChar = defaultHourFormatChar;
    }
    
    private static void hackTimes(final DateTimePatternGenerator dateTimePatternGenerator, final PatternInfo patternInfo, final String s) {
        dateTimePatternGenerator.fp.set(s);
        final StringBuilder sb = new StringBuilder();
        int char1 = 0;
        while (0 < FormatParser.access$000(dateTimePatternGenerator.fp).size()) {
            final Object value = FormatParser.access$000(dateTimePatternGenerator.fp).get(0);
            if (value instanceof String) {
                if (true) {
                    sb.append(dateTimePatternGenerator.fp.quoteLiteral(value.toString()));
                }
            }
            else {
                char1 = value.toString().charAt(0);
                if (0 == 109) {
                    sb.append(value);
                }
                else if (0 == 115) {
                    if (!true) {
                        break;
                    }
                    sb.append(value);
                    dateTimePatternGenerator.addPattern(sb.toString(), false, patternInfo);
                    break;
                }
                else {
                    if (true || 0 == 122 || 0 == 90 || 0 == 118) {
                        break;
                    }
                    if (0 == 86) {
                        break;
                    }
                }
            }
            int n = 0;
            ++n;
        }
        final BitSet set = new BitSet();
        final BitSet set2 = new BitSet();
        while (0 < FormatParser.access$000(dateTimePatternGenerator.fp).size()) {
            final Object value2 = FormatParser.access$000(dateTimePatternGenerator.fp).get(0);
            if (value2 instanceof VariableField) {
                set.set(0);
                final char char2 = value2.toString().charAt(0);
                if (char2 == 's' || char2 == 'S') {
                    set2.set(0);
                    while (-1 >= 0) {
                        if (set.get(-1)) {
                            break;
                        }
                        set2.set(0);
                        int n2 = 0;
                        ++n2;
                    }
                }
            }
            ++char1;
        }
        dateTimePatternGenerator.addPattern(getFilteredPattern(dateTimePatternGenerator.fp, set2), false, patternInfo);
    }
    
    private static String getFilteredPattern(final FormatParser formatParser, final BitSet set) {
        final StringBuilder sb = new StringBuilder();
        while (0 < FormatParser.access$000(formatParser).size()) {
            if (!set.get(0)) {
                final Object value = FormatParser.access$000(formatParser).get(0);
                if (value instanceof String) {
                    sb.append(formatParser.quoteLiteral(value.toString()));
                }
                else {
                    sb.append(value.toString());
                }
            }
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    @Deprecated
    public static int getAppendFormatNumber(final String s) {
        while (0 < DateTimePatternGenerator.CLDR_FIELD_APPEND.length) {
            if (DateTimePatternGenerator.CLDR_FIELD_APPEND[0].equals(s)) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    private static boolean isCLDRFieldName(final int n) {
        return (n >= 0 || n < 16) && DateTimePatternGenerator.CLDR_FIELD_NAME[n].charAt(0) != '*';
    }
    
    public String getBestPattern(final String s) {
        return this.getBestPattern(s, null, 0);
    }
    
    public String getBestPattern(final String s, final int n) {
        return this.getBestPattern(s, null, n);
    }
    
    private String getBestPattern(String replaceAll, final DateTimeMatcher dateTimeMatcher, final int n) {
        replaceAll = replaceAll.replaceAll("j", String.valueOf(this.defaultHourFormatChar));
        // monitorenter(this)
        this.current.set(replaceAll, this.fp, false);
        final PatternWithMatcher bestRaw = this.getBestRaw(this.current, -1, this._distanceInfo, dateTimeMatcher);
        if (this._distanceInfo.missingFieldMask == 0 && this._distanceInfo.extraFieldMask == 0) {
            // monitorexit(this)
            return this.adjustFieldTypes(bestRaw, this.current, false, n);
        }
        final int fieldMask = this.current.getFieldMask();
        final String bestAppending = this.getBestAppending(this.current, fieldMask & 0x3FF, this._distanceInfo, dateTimeMatcher, n);
        final String bestAppending2 = this.getBestAppending(this.current, fieldMask & 0xFC00, this._distanceInfo, dateTimeMatcher, n);
        // monitorexit(this)
        if (bestAppending == null) {
            return (bestAppending2 == null) ? "" : bestAppending2;
        }
        if (bestAppending2 == null) {
            return bestAppending;
        }
        return MessageFormat.format(this.getDateTimeFormat(), bestAppending2, bestAppending);
    }
    
    public DateTimePatternGenerator addPattern(final String s, final boolean b, final PatternInfo patternInfo) {
        return this.addPatternWithSkeleton(s, null, b, patternInfo);
    }
    
    @Deprecated
    public DateTimePatternGenerator addPatternWithSkeleton(final String s, final String s2, final boolean b, final PatternInfo patternInfo) {
        this.checkFrozen();
        DateTimeMatcher dateTimeMatcher;
        if (s2 == null) {
            dateTimeMatcher = new DateTimeMatcher(null).set(s, this.fp, false);
        }
        else {
            dateTimeMatcher = new DateTimeMatcher(null).set(s2, this.fp, false);
        }
        final String basePattern = dateTimeMatcher.getBasePattern();
        final PatternWithSkeletonFlag patternWithSkeletonFlag = this.basePattern_pattern.get(basePattern);
        if (patternWithSkeletonFlag != null && (!patternWithSkeletonFlag.skeletonWasSpecified || (s2 != null && !b))) {
            patternInfo.status = 1;
            patternInfo.conflictingPattern = patternWithSkeletonFlag.pattern;
            if (!b) {
                return this;
            }
        }
        final PatternWithSkeletonFlag patternWithSkeletonFlag2 = this.skeleton2pattern.get(dateTimeMatcher);
        if (patternWithSkeletonFlag2 != null) {
            patternInfo.status = 2;
            patternInfo.conflictingPattern = patternWithSkeletonFlag2.pattern;
            if (!b || (s2 != null && patternWithSkeletonFlag2.skeletonWasSpecified)) {
                return this;
            }
        }
        patternInfo.status = 0;
        patternInfo.conflictingPattern = "";
        final PatternWithSkeletonFlag patternWithSkeletonFlag3 = new PatternWithSkeletonFlag(s, s2 != null);
        this.skeleton2pattern.put(dateTimeMatcher, patternWithSkeletonFlag3);
        this.basePattern_pattern.put(basePattern, patternWithSkeletonFlag3);
        return this;
    }
    
    public String getSkeleton(final String s) {
        // monitorenter(this)
        this.current.set(s, this.fp, false);
        // monitorexit(this)
        return this.current.toString();
    }
    
    @Deprecated
    public String getSkeletonAllowingDuplicates(final String s) {
        // monitorenter(this)
        this.current.set(s, this.fp, true);
        // monitorexit(this)
        return this.current.toString();
    }
    
    @Deprecated
    public String getCanonicalSkeletonAllowingDuplicates(final String s) {
        // monitorenter(this)
        this.current.set(s, this.fp, true);
        // monitorexit(this)
        return this.current.toCanonicalString();
    }
    
    public String getBaseSkeleton(final String s) {
        // monitorenter(this)
        this.current.set(s, this.fp, false);
        // monitorexit(this)
        return this.current.getBasePattern();
    }
    
    public Map getSkeletons(Map linkedHashMap) {
        if (linkedHashMap == null) {
            linkedHashMap = new LinkedHashMap<String, String>();
        }
        for (final DateTimeMatcher dateTimeMatcher : this.skeleton2pattern.keySet()) {
            final String pattern = this.skeleton2pattern.get(dateTimeMatcher).pattern;
            if (DateTimePatternGenerator.CANONICAL_SET.contains(pattern)) {
                continue;
            }
            linkedHashMap.put(dateTimeMatcher.toString(), pattern);
        }
        return linkedHashMap;
    }
    
    public Set getBaseSkeletons(Set set) {
        if (set == null) {
            set = new HashSet();
        }
        set.addAll(this.basePattern_pattern.keySet());
        return set;
    }
    
    public String replaceFieldTypes(final String s, final String s2) {
        return this.replaceFieldTypes(s, s2, 0);
    }
    
    public String replaceFieldTypes(final String s, final String s2, final int n) {
        // monitorenter(this)
        // monitorexit(this)
        return this.adjustFieldTypes(new PatternWithMatcher(s, null), this.current.set(s2, this.fp, false), false, n);
    }
    
    public void setDateTimeFormat(final String dateTimeFormat) {
        this.checkFrozen();
        this.dateTimeFormat = dateTimeFormat;
    }
    
    public String getDateTimeFormat() {
        return this.dateTimeFormat;
    }
    
    public void setDecimal(final String decimal) {
        this.checkFrozen();
        this.decimal = decimal;
    }
    
    public String getDecimal() {
        return this.decimal;
    }
    
    @Deprecated
    public Collection getRedundants(Collection set) {
        // monitorenter(this)
        if (set == null) {
            set = new LinkedHashSet<String>();
        }
        for (final DateTimeMatcher dateTimeMatcher : this.skeleton2pattern.keySet()) {
            final String pattern = this.skeleton2pattern.get(dateTimeMatcher).pattern;
            if (DateTimePatternGenerator.CANONICAL_SET.contains(pattern)) {
                continue;
            }
            if (!this.getBestPattern(dateTimeMatcher.toString(), dateTimeMatcher, 0).equals(pattern)) {
                continue;
            }
            set.add(pattern);
        }
        // monitorexit(this)
        return set;
    }
    
    public void setAppendItemFormat(final int n, final String s) {
        this.checkFrozen();
        this.appendItemFormats[n] = s;
    }
    
    public String getAppendItemFormat(final int n) {
        return this.appendItemFormats[n];
    }
    
    public void setAppendItemName(final int n, final String s) {
        this.checkFrozen();
        this.appendItemNames[n] = s;
    }
    
    public String getAppendItemName(final int n) {
        return this.appendItemNames[n];
    }
    
    @Deprecated
    public static boolean isSingleField(final String s) {
        final char char1 = s.charAt(0);
        while (1 < s.length()) {
            if (s.charAt(1) != char1) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private void setAvailableFormat(final String s) {
        this.checkFrozen();
        this.cldrAvailableFormatKeys.add(s);
    }
    
    private boolean isAvailableFormatSet(final String s) {
        return this.cldrAvailableFormatKeys.contains(s);
    }
    
    public boolean isFrozen() {
        return this.frozen;
    }
    
    public DateTimePatternGenerator freeze() {
        this.frozen = true;
        return this;
    }
    
    public DateTimePatternGenerator cloneAsThawed() {
        final DateTimePatternGenerator dateTimePatternGenerator = (DateTimePatternGenerator)this.clone();
        this.frozen = false;
        return dateTimePatternGenerator;
    }
    
    public Object clone() {
        final DateTimePatternGenerator dateTimePatternGenerator = (DateTimePatternGenerator)super.clone();
        dateTimePatternGenerator.skeleton2pattern = (TreeMap)this.skeleton2pattern.clone();
        dateTimePatternGenerator.basePattern_pattern = (TreeMap)this.basePattern_pattern.clone();
        dateTimePatternGenerator.appendItemFormats = this.appendItemFormats.clone();
        dateTimePatternGenerator.appendItemNames = this.appendItemNames.clone();
        dateTimePatternGenerator.current = new DateTimeMatcher(null);
        dateTimePatternGenerator.fp = new FormatParser();
        dateTimePatternGenerator._distanceInfo = new DistanceInfo(null);
        dateTimePatternGenerator.frozen = false;
        return dateTimePatternGenerator;
    }
    
    @Deprecated
    public boolean skeletonsAreSimilar(final String s, final String s2) {
        if (s.equals(s2)) {
            return true;
        }
        final TreeSet set = this.getSet(s);
        final TreeSet set2 = this.getSet(s2);
        if (set.size() != set2.size()) {
            return false;
        }
        final Iterator<String> iterator = set2.iterator();
        final Iterator<String> iterator2 = set.iterator();
        while (iterator2.hasNext()) {
            if (DateTimePatternGenerator.types[getCanonicalIndex(iterator2.next(), false)][1] != DateTimePatternGenerator.types[getCanonicalIndex(iterator.next(), false)][1]) {
                return false;
            }
        }
        return true;
    }
    
    private TreeSet getSet(final String s) {
        final List items = this.fp.set(s).getItems();
        final TreeSet<String> set = new TreeSet<String>();
        final Iterator<Object> iterator = items.iterator();
        while (iterator.hasNext()) {
            final String string = iterator.next().toString();
            if (!string.startsWith("G")) {
                if (string.startsWith("a")) {
                    continue;
                }
                set.add(string);
            }
        }
        return set;
    }
    
    private void checkFrozen() {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
    }
    
    private String getBestAppending(final DateTimeMatcher dateTimeMatcher, final int n, final DistanceInfo distanceInfo, final DateTimeMatcher dateTimeMatcher2, final int n2) {
        String pattern = null;
        if (n != 0) {
            final PatternWithMatcher bestRaw = this.getBestRaw(dateTimeMatcher, n, distanceInfo, dateTimeMatcher2);
            pattern = this.adjustFieldTypes(bestRaw, dateTimeMatcher, false, n2);
            while (distanceInfo.missingFieldMask != 0) {
                if ((distanceInfo.missingFieldMask & 0x6000) == 0x4000 && (n & 0x6000) == 0x6000) {
                    bestRaw.pattern = pattern;
                    pattern = this.adjustFieldTypes(bestRaw, dateTimeMatcher, true, n2);
                    distanceInfo.missingFieldMask &= 0xFFFFBFFF;
                }
                else {
                    final int missingFieldMask = distanceInfo.missingFieldMask;
                    final String adjustFieldTypes = this.adjustFieldTypes(this.getBestRaw(dateTimeMatcher, distanceInfo.missingFieldMask, distanceInfo, dateTimeMatcher2), dateTimeMatcher, false, n2);
                    final int topBitNumber = this.getTopBitNumber(missingFieldMask & ~distanceInfo.missingFieldMask);
                    pattern = MessageFormat.format(this.getAppendFormat(topBitNumber), pattern, adjustFieldTypes, this.getAppendName(topBitNumber));
                }
            }
        }
        return pattern;
    }
    
    private String getAppendName(final int n) {
        return "'" + this.appendItemNames[n] + "'";
    }
    
    private String getAppendFormat(final int n) {
        return this.appendItemFormats[n];
    }
    
    private int getTopBitNumber(int i) {
        while (i != 0) {
            i >>>= 1;
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    private void complete() {
        final PatternInfo patternInfo = new PatternInfo();
        while (0 < DateTimePatternGenerator.CANONICAL_ITEMS.length) {
            this.addPattern(String.valueOf(DateTimePatternGenerator.CANONICAL_ITEMS[0]), false, patternInfo);
            int n = 0;
            ++n;
        }
    }
    
    private PatternWithMatcher getBestRaw(final DateTimeMatcher dateTimeMatcher, final int n, final DistanceInfo distanceInfo, final DateTimeMatcher dateTimeMatcher2) {
        final PatternWithMatcher patternWithMatcher = new PatternWithMatcher("", null);
        final DistanceInfo to = new DistanceInfo(null);
        for (final DateTimeMatcher matcherWithSkeleton : this.skeleton2pattern.keySet()) {
            if (matcherWithSkeleton.equals(dateTimeMatcher2)) {
                continue;
            }
            final int distance = dateTimeMatcher.getDistance(matcherWithSkeleton, n, to);
            if (distance >= Integer.MAX_VALUE) {
                continue;
            }
            final PatternWithSkeletonFlag patternWithSkeletonFlag = this.skeleton2pattern.get(matcherWithSkeleton);
            patternWithMatcher.pattern = patternWithSkeletonFlag.pattern;
            if (patternWithSkeletonFlag.skeletonWasSpecified) {
                patternWithMatcher.matcherWithSkeleton = matcherWithSkeleton;
            }
            else {
                patternWithMatcher.matcherWithSkeleton = null;
            }
            distanceInfo.setTo(to);
            if (distance == 0) {
                break;
            }
        }
        return patternWithMatcher;
    }
    
    private String adjustFieldTypes(final PatternWithMatcher patternWithMatcher, final DateTimeMatcher dateTimeMatcher, final boolean b, final int n) {
        this.fp.set(patternWithMatcher.pattern);
        final StringBuilder sb = new StringBuilder();
        for (final String next : this.fp.getItems()) {
            if (next instanceof String) {
                sb.append(this.fp.quoteLiteral(next));
            }
            else {
                final VariableField variableField = (VariableField)next;
                StringBuilder sb2 = new StringBuilder(variableField.toString());
                final int type = variableField.getType();
                if (b && type == 13) {
                    final String s = DateTimeMatcher.access$600(dateTimeMatcher)[14];
                    sb2.append(this.decimal);
                    sb2.append(s);
                }
                else if (DateTimeMatcher.access$700(dateTimeMatcher)[type] != 0) {
                    final String s2 = DateTimeMatcher.access$600(dateTimeMatcher)[type];
                    s2.length();
                    if (s2.charAt(0) != 'E' || 3 < 3) {}
                    final DateTimeMatcher matcherWithSkeleton = patternWithMatcher.matcherWithSkeleton;
                    int length = 0;
                    if ((type == 11 && (n & 0x800) == 0x0) || (type == 12 && (n & 0x1000) == 0x0) || (type == 13 && (n & 0x2000) == 0x0)) {
                        sb2.length();
                    }
                    else if (matcherWithSkeleton != null) {
                        length = matcherWithSkeleton.origStringForField(type).length();
                        final boolean numeric = variableField.isNumeric();
                        final boolean fieldIsNumeric = matcherWithSkeleton.fieldIsNumeric(type);
                        if (3 == 3 || (numeric && !fieldIsNumeric) || (fieldIsNumeric && !numeric)) {
                            sb2.length();
                        }
                    }
                    final char c = (type != 11 && type != 3 && type != 6 && type != 1) ? s2.charAt(0) : sb2.charAt(0);
                    sb2 = new StringBuilder();
                    while (3 > 0) {
                        sb2.append(c);
                        --length;
                    }
                }
                sb.append((CharSequence)sb2);
            }
        }
        return sb.toString();
    }
    
    @Deprecated
    public String getFields(final String s) {
        this.fp.set(s);
        final StringBuilder sb = new StringBuilder();
        for (final String next : this.fp.getItems()) {
            if (next instanceof String) {
                sb.append(this.fp.quoteLiteral(next));
            }
            else {
                sb.append("{" + getName(next.toString()) + "}");
            }
        }
        return sb.toString();
    }
    
    private static String showMask(final int n) {
        final StringBuilder sb = new StringBuilder();
        while (0 < 16) {
            if ((n & 0x1) != 0x0) {
                if (sb.length() != 0) {
                    sb.append(" | ");
                }
                sb.append(DateTimePatternGenerator.FIELD_NAME[0]);
                sb.append(" ");
            }
            int n2 = 0;
            ++n2;
        }
        return sb.toString();
    }
    
    private static String getName(final String s) {
        final int canonicalIndex = getCanonicalIndex(s, true);
        final String s2 = DateTimePatternGenerator.FIELD_NAME[DateTimePatternGenerator.types[canonicalIndex][1]];
        int n = DateTimePatternGenerator.types[canonicalIndex][2];
        if (n < 0) {
            n = -n;
        }
        String s3;
        if (n < 0) {
            s3 = s2 + ":S";
        }
        else {
            s3 = s2 + ":N";
        }
        return s3;
    }
    
    private static int getCanonicalIndex(final String s, final boolean b) {
        final int length = s.length();
        if (length == 0) {
            return -1;
        }
        final char char1 = s.charAt(0);
        while (-1 < length) {
            if (s.charAt(-1) != char1) {
                return -1;
            }
            int n = 0;
            ++n;
        }
        while (0 < DateTimePatternGenerator.types.length) {
            final int[] array = DateTimePatternGenerator.types[0];
            if (array[0] == char1) {
                if (array[3] <= length) {
                    if (array[array.length - 1] >= length) {
                        return 0;
                    }
                }
            }
            int n2 = 0;
            ++n2;
        }
        return b ? -1 : -1;
    }
    
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    public Object freeze() {
        return this.freeze();
    }
    
    static int access$300(final String s, final boolean b) {
        return getCanonicalIndex(s, b);
    }
    
    static int[][] access$400() {
        return DateTimePatternGenerator.types;
    }
    
    static String[] access$500() {
        return DateTimePatternGenerator.CANONICAL_ITEMS;
    }
    
    static String access$900(final int n) {
        return showMask(n);
    }
    
    static {
        DateTimePatternGenerator.DTPNG_CACHE = new SimpleCache();
        DateTimePatternGenerator.CLDR_FIELD_APPEND = new String[] { "Era", "Year", "Quarter", "Month", "Week", "*", "Day-Of-Week", "Day", "*", "*", "*", "Hour", "Minute", "Second", "*", "Timezone" };
        CLDR_FIELD_NAME = new String[] { "era", "year", "*", "month", "week", "*", "weekday", "day", "*", "*", "dayperiod", "hour", "minute", "second", "*", "zone" };
        FIELD_NAME = new String[] { "Era", "Year", "Quarter", "Month", "Week_in_Year", "Week_in_Month", "Weekday", "Day", "Day_Of_Year", "Day_of_Week_in_Month", "Dayperiod", "Hour", "Minute", "Second", "Fractional_Second", "Zone" };
        CANONICAL_ITEMS = new String[] { "G", "y", "Q", "M", "w", "W", "E", "d", "D", "F", "H", "m", "s", "S", "v" };
        CANONICAL_SET = new HashSet(Arrays.asList(DateTimePatternGenerator.CANONICAL_ITEMS));
        types = new int[][] { { 71, 0, -258, 1, 3 }, { 71, 0, -259, 4 }, { 121, 1, 256, 1, 20 }, { 89, 1, 272, 1, 20 }, { 117, 1, 288, 1, 20 }, { 85, 1, -258, 1, 3 }, { 85, 1, -259, 4 }, { 85, 1, -257, 5 }, { 81, 2, 256, 1, 2 }, { 81, 2, -258, 3 }, { 81, 2, -259, 4 }, { 113, 2, 272, 1, 2 }, { 113, 2, -242, 3 }, { 113, 2, -243, 4 }, { 77, 3, 256, 1, 2 }, { 77, 3, -258, 3 }, { 77, 3, -259, 4 }, { 77, 3, -257, 5 }, { 76, 3, 272, 1, 2 }, { 76, 3, -274, 3 }, { 76, 3, -275, 4 }, { 76, 3, -273, 5 }, { 108, 3, 272, 1, 1 }, { 119, 4, 256, 1, 2 }, { 87, 5, 272, 1 }, { 69, 6, -258, 1, 3 }, { 69, 6, -259, 4 }, { 69, 6, -257, 5 }, { 99, 6, 288, 1, 2 }, { 99, 6, -290, 3 }, { 99, 6, -291, 4 }, { 99, 6, -289, 5 }, { 101, 6, 272, 1, 2 }, { 101, 6, -274, 3 }, { 101, 6, -275, 4 }, { 101, 6, -273, 5 }, { 100, 7, 256, 1, 2 }, { 68, 8, 272, 1, 3 }, { 70, 9, 288, 1 }, { 103, 7, 304, 1, 20 }, { 97, 10, -258, 1 }, { 72, 11, 416, 1, 2 }, { 107, 11, 432, 1, 2 }, { 104, 11, 256, 1, 2 }, { 75, 11, 272, 1, 2 }, { 109, 12, 256, 1, 2 }, { 115, 13, 256, 1, 2 }, { 83, 14, 272, 1, 1000 }, { 65, 13, 288, 1, 1000 }, { 118, 15, -290, 1 }, { 118, 15, -291, 4 }, { 122, 15, -258, 1, 3 }, { 122, 15, -259, 4 }, { 90, 15, -274, 1, 3 }, { 90, 15, -275, 4 }, { 86, 15, -274, 1, 3 }, { 86, 15, -275, 4 } };
    }
    
    private static class DistanceInfo
    {
        int missingFieldMask;
        int extraFieldMask;
        
        private DistanceInfo() {
        }
        
        void clear() {
            final int n = 0;
            this.extraFieldMask = n;
            this.missingFieldMask = n;
        }
        
        void setTo(final DistanceInfo distanceInfo) {
            this.missingFieldMask = distanceInfo.missingFieldMask;
            this.extraFieldMask = distanceInfo.extraFieldMask;
        }
        
        void addMissing(final int n) {
            this.missingFieldMask |= 1 << n;
        }
        
        void addExtra(final int n) {
            this.extraFieldMask |= 1 << n;
        }
        
        @Override
        public String toString() {
            return "missingFieldMask: " + DateTimePatternGenerator.access$900(this.missingFieldMask) + ", extraFieldMask: " + DateTimePatternGenerator.access$900(this.extraFieldMask);
        }
        
        DistanceInfo(final DateTimePatternGenerator$1 object) {
            this();
        }
    }
    
    private static class DateTimeMatcher implements Comparable
    {
        private int[] type;
        private String[] original;
        private String[] baseOriginal;
        
        private DateTimeMatcher() {
            this.type = new int[16];
            this.original = new String[16];
            this.baseOriginal = new String[16];
        }
        
        public String origStringForField(final int n) {
            return this.original[n];
        }
        
        public boolean fieldIsNumeric(final int n) {
            return this.type[n] > 0;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            while (0 < 16) {
                if (this.original[0].length() != 0) {
                    sb.append(this.original[0]);
                }
                int n = 0;
                ++n;
            }
            return sb.toString();
        }
        
        public String toCanonicalString() {
            final StringBuilder sb = new StringBuilder();
            while (0 < 16) {
                if (this.original[0].length() != 0) {
                    while (0 < DateTimePatternGenerator.access$400().length) {
                        final int[] array = DateTimePatternGenerator.access$400()[0];
                        if (array[1] == 0) {
                            final char char1 = this.original[0].charAt(0);
                            sb.append(Utility.repeat(String.valueOf((char1 == 'h' || char1 == 'K') ? 'h' : ((char)array[0])), this.original[0].length()));
                            break;
                        }
                        int n = 0;
                        ++n;
                    }
                }
                int n2 = 0;
                ++n2;
            }
            return sb.toString();
        }
        
        String getBasePattern() {
            final StringBuilder sb = new StringBuilder();
            while (0 < 16) {
                if (this.baseOriginal[0].length() != 0) {
                    sb.append(this.baseOriginal[0]);
                }
                int n = 0;
                ++n;
            }
            return sb.toString();
        }
        
        DateTimeMatcher set(final String s, final FormatParser formatParser, final boolean b) {
            while (0 < 16) {
                this.type[0] = 0;
                this.original[0] = "";
                this.baseOriginal[0] = "";
                int n = 0;
                ++n;
            }
            formatParser.set(s);
            for (final VariableField next : formatParser.getItems()) {
                if (!(next instanceof VariableField)) {
                    continue;
                }
                final VariableField variableField = next;
                final String string = variableField.toString();
                if (string.charAt(0) == 'a') {
                    continue;
                }
                final int[] array = DateTimePatternGenerator.access$400()[VariableField.access$800(variableField)];
                final int n2 = array[1];
                if (this.original[n2].length() != 0) {
                    if (b) {
                        continue;
                    }
                    throw new IllegalArgumentException("Conflicting fields:\t" + this.original[n2] + ", " + string + "\t in " + s);
                }
                else {
                    this.original[n2] = string;
                    final char c = (char)array[0];
                    final int n3 = array[3];
                    if ("GEzvQ".indexOf(c) >= 0) {}
                    this.baseOriginal[n2] = Utility.repeat(String.valueOf(c), 1);
                    int n4 = array[2];
                    if (n4 > 0) {
                        n4 += string.length();
                    }
                    this.type[n2] = n4;
                }
            }
            return this;
        }
        
        int getFieldMask() {
            while (0 < this.type.length) {
                if (this.type[0] != 0) {}
                int n = 0;
                ++n;
            }
            return 0;
        }
        
        void extractFrom(final DateTimeMatcher dateTimeMatcher, final int n) {
            while (0 < this.type.length) {
                if ((n & 0x1) != 0x0) {
                    this.type[0] = dateTimeMatcher.type[0];
                    this.original[0] = dateTimeMatcher.original[0];
                }
                else {
                    this.type[0] = 0;
                    this.original[0] = "";
                }
                int n2 = 0;
                ++n2;
            }
        }
        
        int getDistance(final DateTimeMatcher dateTimeMatcher, final int n, final DistanceInfo distanceInfo) {
            distanceInfo.clear();
            while (0 < this.type.length) {
                final int n2 = ((n & 0x1) == 0x0) ? 0 : this.type[0];
                final int n3 = dateTimeMatcher.type[0];
                if (n2 != n3) {
                    if (n2 == 0) {
                        distanceInfo.addExtra(0);
                    }
                    else if (n3 == 0) {
                        final int n4;
                        n4 += 4096;
                        distanceInfo.addMissing(0);
                    }
                    else {
                        final int n4 = 0 + Math.abs(n2 - n3);
                    }
                }
                int n5 = 0;
                ++n5;
            }
            return 0;
        }
        
        public int compareTo(final DateTimeMatcher dateTimeMatcher) {
            while (0 < this.original.length) {
                final int compareTo = this.original[0].compareTo(dateTimeMatcher.original[0]);
                if (compareTo != 0) {
                    return -compareTo;
                }
                int n = 0;
                ++n;
            }
            return 0;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof DateTimeMatcher)) {
                return false;
            }
            final DateTimeMatcher dateTimeMatcher = (DateTimeMatcher)o;
            while (0 < this.original.length) {
                if (!this.original[0].equals(dateTimeMatcher.original[0])) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        
        @Override
        public int hashCode() {
            while (0 < this.original.length) {
                final int n = 0x0 ^ this.original[0].hashCode();
                int n2 = 0;
                ++n2;
            }
            return 0;
        }
        
        public int compareTo(final Object o) {
            return this.compareTo((DateTimeMatcher)o);
        }
        
        DateTimeMatcher(final DateTimePatternGenerator$1 object) {
            this();
        }
        
        static String[] access$600(final DateTimeMatcher dateTimeMatcher) {
            return dateTimeMatcher.original;
        }
        
        static int[] access$700(final DateTimeMatcher dateTimeMatcher) {
            return dateTimeMatcher.type;
        }
    }
    
    public static class FormatParser
    {
        private transient PatternTokenizer tokenizer;
        private List items;
        
        @Deprecated
        public FormatParser() {
            this.tokenizer = new PatternTokenizer().setSyntaxCharacters(new UnicodeSet("[a-zA-Z]")).setExtraQuotingCharacters(new UnicodeSet("[[[:script=Latn:][:script=Cyrl:]]&[[:L:][:M:]]]")).setUsingQuote(true);
            this.items = new ArrayList();
        }
        
        @Deprecated
        public final FormatParser set(final String s) {
            return this.set(s, false);
        }
        
        @Deprecated
        public FormatParser set(final String pattern, final boolean b) {
            this.items.clear();
            if (pattern.length() == 0) {
                return this;
            }
            this.tokenizer.setPattern(pattern);
            final StringBuffer sb = new StringBuffer();
            final StringBuffer sb2 = new StringBuffer();
            while (true) {
                sb.setLength(0);
                final int next = this.tokenizer.next(sb);
                if (next == 0) {
                    break;
                }
                if (next == 1) {
                    if (sb2.length() != 0 && sb.charAt(0) != sb2.charAt(0)) {
                        this.addVariable(sb2, false);
                    }
                    sb2.append(sb);
                }
                else {
                    this.addVariable(sb2, false);
                    this.items.add(sb.toString());
                }
            }
            this.addVariable(sb2, false);
            return this;
        }
        
        private void addVariable(final StringBuffer sb, final boolean b) {
            if (sb.length() != 0) {
                this.items.add(new VariableField(sb.toString(), b));
                sb.setLength(0);
            }
        }
        
        @Deprecated
        public List getItems() {
            return this.items;
        }
        
        @Override
        @Deprecated
        public String toString() {
            return this.toString(0, this.items.size());
        }
        
        @Deprecated
        public String toString(final int n, final int n2) {
            final StringBuilder sb = new StringBuilder();
            for (int i = n; i < n2; ++i) {
                final Object value = this.items.get(i);
                if (value instanceof String) {
                    sb.append(this.tokenizer.quoteLiteral((String)value));
                }
                else {
                    sb.append(this.items.get(i).toString());
                }
            }
            return sb.toString();
        }
        
        @Deprecated
        public boolean hasDateAndTimeFields() {
            for (final VariableField next : this.items) {
                if (next instanceof VariableField) {
                    final int n = 0x0 | 1 << next.getType();
                }
            }
            final boolean b = false;
            final boolean b2 = false;
            return b && b2;
        }
        
        @Deprecated
        public Object quoteLiteral(final String s) {
            return this.tokenizer.quoteLiteral(s);
        }
        
        static List access$000(final FormatParser formatParser) {
            return formatParser.items;
        }
    }
    
    public static class VariableField
    {
        private final String string;
        private final int canonicalIndex;
        
        @Deprecated
        public VariableField(final String s) {
            this(s, false);
        }
        
        @Deprecated
        public VariableField(final String string, final boolean b) {
            this.canonicalIndex = DateTimePatternGenerator.access$300(string, b);
            if (this.canonicalIndex < 0) {
                throw new IllegalArgumentException("Illegal datetime field:\t" + string);
            }
            this.string = string;
        }
        
        @Deprecated
        public int getType() {
            return DateTimePatternGenerator.access$400()[this.canonicalIndex][1];
        }
        
        @Deprecated
        public static String getCanonicalCode(final int n) {
            return DateTimePatternGenerator.access$500()[n];
        }
        
        @Deprecated
        public boolean isNumeric() {
            return DateTimePatternGenerator.access$400()[this.canonicalIndex][2] > 0;
        }
        
        private int getCanonicalIndex() {
            return this.canonicalIndex;
        }
        
        @Override
        @Deprecated
        public String toString() {
            return this.string;
        }
        
        static int access$800(final VariableField variableField) {
            return variableField.getCanonicalIndex();
        }
    }
    
    private static class PatternWithSkeletonFlag
    {
        public String pattern;
        public boolean skeletonWasSpecified;
        
        public PatternWithSkeletonFlag(final String pattern, final boolean skeletonWasSpecified) {
            this.pattern = pattern;
            this.skeletonWasSpecified = skeletonWasSpecified;
        }
        
        @Override
        public String toString() {
            return this.pattern + "," + this.skeletonWasSpecified;
        }
    }
    
    private static class PatternWithMatcher
    {
        public String pattern;
        public DateTimeMatcher matcherWithSkeleton;
        
        public PatternWithMatcher(final String pattern, final DateTimeMatcher matcherWithSkeleton) {
            this.pattern = pattern;
            this.matcherWithSkeleton = matcherWithSkeleton;
        }
    }
    
    public static final class PatternInfo
    {
        public static final int OK = 0;
        public static final int BASE_CONFLICT = 1;
        public static final int CONFLICT = 2;
        public int status;
        public String conflictingPattern;
    }
}
