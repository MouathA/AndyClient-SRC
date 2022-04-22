package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import com.ibm.icu.util.*;
import java.util.*;
import com.ibm.icu.lang.*;

public class LocaleDisplayNamesImpl extends LocaleDisplayNames
{
    private final ULocale locale;
    private final DialectHandling dialectHandling;
    private final DisplayContext capitalization;
    private final DataTable langData;
    private final DataTable regionData;
    private final Appender appender;
    private final MessageFormat format;
    private final MessageFormat keyTypeFormat;
    private static final Cache cache;
    private Map capitalizationUsage;
    private static final Map contextUsageTypeMap;
    
    public static LocaleDisplayNames getInstance(final ULocale uLocale, final DialectHandling dialectHandling) {
        // monitorenter(cache = LocaleDisplayNamesImpl.cache)
        // monitorexit(cache)
        return LocaleDisplayNamesImpl.cache.get(uLocale, dialectHandling);
    }
    
    public static LocaleDisplayNames getInstance(final ULocale uLocale, final DisplayContext... array) {
        // monitorenter(cache = LocaleDisplayNamesImpl.cache)
        // monitorexit(cache)
        return LocaleDisplayNamesImpl.cache.get(uLocale, array);
    }
    
    public LocaleDisplayNamesImpl(final ULocale uLocale, final DialectHandling dialectHandling) {
        this(uLocale, new DisplayContext[] { (dialectHandling == DialectHandling.STANDARD_NAMES) ? DisplayContext.STANDARD_NAMES : DisplayContext.DIALECT_NAMES, DisplayContext.CAPITALIZATION_NONE });
    }
    
    public LocaleDisplayNamesImpl(final ULocale uLocale, final DisplayContext... array) {
        this.capitalizationUsage = null;
        DialectHandling standard_NAMES = DialectHandling.STANDARD_NAMES;
        DisplayContext capitalization_NONE = DisplayContext.CAPITALIZATION_NONE;
        while (0 < array.length) {
            final DisplayContext displayContext = array[0];
            switch (displayContext.type()) {
                case DIALECT_HANDLING: {
                    standard_NAMES = ((displayContext.value() == DisplayContext.STANDARD_NAMES.value()) ? DialectHandling.STANDARD_NAMES : DialectHandling.DIALECT_NAMES);
                    break;
                }
                case CAPITALIZATION: {
                    capitalization_NONE = displayContext;
                    break;
                }
            }
            int n = 0;
            ++n;
        }
        this.dialectHandling = standard_NAMES;
        this.capitalization = capitalization_NONE;
        this.langData = LangDataTables.impl.get(uLocale);
        this.regionData = RegionDataTables.impl.get(uLocale);
        this.locale = (ULocale.ROOT.equals(this.langData.getLocale()) ? this.regionData.getLocale() : this.langData.getLocale());
        String value = this.langData.get("localeDisplayPattern", "separator");
        if ("separator".equals(value)) {
            value = ", ";
        }
        this.appender = new Appender(value);
        String value2 = this.langData.get("localeDisplayPattern", "pattern");
        if ("pattern".equals(value2)) {
            value2 = "{0} ({1})";
        }
        this.format = new MessageFormat(value2);
        String value3 = this.langData.get("localeDisplayPattern", "keyTypePattern");
        if ("keyTypePattern".equals(value3)) {
            value3 = "{0}={1}";
        }
        this.keyTypeFormat = new MessageFormat(value3);
        if (capitalization_NONE == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || capitalization_NONE == DisplayContext.CAPITALIZATION_FOR_STANDALONE) {
            this.capitalizationUsage = new HashMap();
            final boolean[] array2 = { false, false };
            final CapitalizationContextUsage[] values = CapitalizationContextUsage.values();
            while (0 < values.length) {
                this.capitalizationUsage.put(values[0], array2);
                int n2 = 0;
                ++n2;
            }
            final ICUResourceBundle withFallback = ((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", uLocale)).getWithFallback("contextTransforms");
            if (withFallback != null) {
                final UResourceBundleIterator iterator = withFallback.getIterator();
                while (iterator.hasNext()) {
                    final UResourceBundle next = iterator.next();
                    final int[] intVector = next.getIntVector();
                    if (intVector.length >= 2) {
                        final CapitalizationContextUsage capitalizationContextUsage = LocaleDisplayNamesImpl.contextUsageTypeMap.get(next.getKey());
                        if (capitalizationContextUsage == null) {
                            continue;
                        }
                        this.capitalizationUsage.put(capitalizationContextUsage, new boolean[] { intVector[0] != 0, intVector[1] != 0 });
                    }
                }
            }
        }
    }
    
    @Override
    public ULocale getLocale() {
        return this.locale;
    }
    
    @Override
    public DialectHandling getDialectHandling() {
        return this.dialectHandling;
    }
    
    @Override
    public DisplayContext getContext(final DisplayContext.Type type) {
        DisplayContext displayContext = null;
        switch (type) {
            case DIALECT_HANDLING: {
                displayContext = ((this.dialectHandling == DialectHandling.STANDARD_NAMES) ? DisplayContext.STANDARD_NAMES : DisplayContext.DIALECT_NAMES);
                break;
            }
            case CAPITALIZATION: {
                displayContext = this.capitalization;
                break;
            }
            default: {
                displayContext = DisplayContext.STANDARD_NAMES;
                break;
            }
        }
        return displayContext;
    }
    
    private String adjustForUsageAndContext(final CapitalizationContextUsage p0, final String p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: astore_3       
        //     2: getstatic       com/ibm/icu/impl/LocaleDisplayNamesImpl$1.$SwitchMap$com$ibm$icu$text$DisplayContext:[I
        //     5: aload_0        
        //     6: getfield        com/ibm/icu/impl/LocaleDisplayNamesImpl.capitalization:Lcom/ibm/icu/text/DisplayContext;
        //     9: invokevirtual   com/ibm/icu/text/DisplayContext.ordinal:()I
        //    12: iaload         
        //    13: tableswitch {
        //                2: 40
        //                3: 43
        //                4: 43
        //          default: 88
        //        }
        //    40: goto            88
        //    43: aload_0        
        //    44: getfield        com/ibm/icu/impl/LocaleDisplayNamesImpl.capitalizationUsage:Ljava/util/Map;
        //    47: ifnull          88
        //    50: aload_0        
        //    51: getfield        com/ibm/icu/impl/LocaleDisplayNamesImpl.capitalizationUsage:Ljava/util/Map;
        //    54: aload_1        
        //    55: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    60: checkcast       [Z
        //    63: astore          5
        //    65: aload_0        
        //    66: getfield        com/ibm/icu/impl/LocaleDisplayNamesImpl.capitalization:Lcom/ibm/icu/text/DisplayContext;
        //    69: getstatic       com/ibm/icu/text/DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU:Lcom/ibm/icu/text/DisplayContext;
        //    72: if_acmpne       82
        //    75: aload           5
        //    77: iconst_0       
        //    78: baload         
        //    79: goto            86
        //    82: aload           5
        //    84: iconst_1       
        //    85: baload         
        //    86: istore          4
        //    88: iconst_1       
        //    89: ifeq            238
        //    92: aload_2        
        //    93: invokevirtual   java/lang/String.length:()I
        //    96: istore          7
        //    98: bipush          8
        //   100: iload           7
        //   102: if_icmple       109
        //   105: iload           7
        //   107: istore          6
        //   109: iconst_0       
        //   110: bipush          8
        //   112: if_icmpge       178
        //   115: aload_2        
        //   116: iconst_0       
        //   117: invokevirtual   java/lang/String.codePointAt:(I)I
        //   120: istore          8
        //   122: iload           8
        //   124: bipush          65
        //   126: if_icmplt       178
        //   129: iload           8
        //   131: bipush          90
        //   133: if_icmple       143
        //   136: iload           8
        //   138: bipush          97
        //   140: if_icmplt       178
        //   143: iload           8
        //   145: bipush          122
        //   147: if_icmple       161
        //   150: iload           8
        //   152: sipush          192
        //   155: if_icmpge       161
        //   158: goto            178
        //   161: iload           8
        //   163: ldc_w           65536
        //   166: if_icmplt       172
        //   169: iinc            5, 1
        //   172: iinc            5, 1
        //   175: goto            109
        //   178: iconst_0       
        //   179: ifle            225
        //   182: iconst_0       
        //   183: iload           7
        //   185: if_icmpge       225
        //   188: aload_2        
        //   189: iconst_0       
        //   190: iconst_0       
        //   191: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   194: astore          8
        //   196: aload_0        
        //   197: getfield        com/ibm/icu/impl/LocaleDisplayNamesImpl.locale:Lcom/ibm/icu/util/ULocale;
        //   200: aload           8
        //   202: aconst_null    
        //   203: sipush          768
        //   206: invokestatic    com/ibm/icu/lang/UCharacter.toTitleCase:(Lcom/ibm/icu/util/ULocale;Ljava/lang/String;Lcom/ibm/icu/text/BreakIterator;I)Ljava/lang/String;
        //   209: astore          9
        //   211: aload           9
        //   213: aload_2        
        //   214: iconst_0       
        //   215: invokevirtual   java/lang/String.substring:(I)Ljava/lang/String;
        //   218: invokevirtual   java/lang/String.concat:(Ljava/lang/String;)Ljava/lang/String;
        //   221: astore_3       
        //   222: goto            238
        //   225: aload_0        
        //   226: getfield        com/ibm/icu/impl/LocaleDisplayNamesImpl.locale:Lcom/ibm/icu/util/ULocale;
        //   229: aload_2        
        //   230: aconst_null    
        //   231: sipush          768
        //   234: invokestatic    com/ibm/icu/lang/UCharacter.toTitleCase:(Lcom/ibm/icu/util/ULocale;Ljava/lang/String;Lcom/ibm/icu/text/BreakIterator;I)Ljava/lang/String;
        //   237: astore_3       
        //   238: aload_3        
        //   239: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public String localeDisplayName(final ULocale uLocale) {
        return this.localeDisplayNameInternal(uLocale);
    }
    
    @Override
    public String localeDisplayName(final Locale locale) {
        return this.localeDisplayNameInternal(ULocale.forLocale(locale));
    }
    
    @Override
    public String localeDisplayName(final String s) {
        return this.localeDisplayNameInternal(new ULocale(s));
    }
    
    private String localeDisplayNameInternal(final ULocale uLocale) {
        String s = null;
        String language = uLocale.getLanguage();
        if (uLocale.getBaseName().length() == 0) {
            language = "root";
        }
        final String script = uLocale.getScript();
        final String country = uLocale.getCountry();
        final String variant = uLocale.getVariant();
        final boolean b = script.length() > 0;
        final boolean b2 = country.length() > 0;
        final boolean b3 = variant.length() > 0;
        Label_0267: {
            if (this.dialectHandling == DialectHandling.DIALECT_NAMES) {
                if (false && false) {
                    final String string = language + '_' + script + '_' + country;
                    final String localeIdName = this.localeIdName(string);
                    if (!localeIdName.equals(string)) {
                        s = localeIdName;
                        break Label_0267;
                    }
                }
                if (false) {
                    final String string2 = language + '_' + script;
                    final String localeIdName2 = this.localeIdName(string2);
                    if (!localeIdName2.equals(string2)) {
                        s = localeIdName2;
                        break Label_0267;
                    }
                }
                if (false) {
                    final String string3 = language + '_' + country;
                    final String localeIdName3 = this.localeIdName(string3);
                    if (!localeIdName3.equals(string3)) {
                        s = localeIdName3;
                    }
                }
            }
        }
        if (s == null) {
            s = this.localeIdName(language);
        }
        final StringBuilder sb = new StringBuilder();
        if (false) {
            sb.append(this.scriptDisplayNameInContext(script));
        }
        if (false) {
            this.appender.append(this.regionDisplayName(country), sb);
        }
        if (b3) {
            this.appender.append(this.variantDisplayName(variant), sb);
        }
        final Iterator keywords = uLocale.getKeywords();
        if (keywords != null) {
            while (keywords.hasNext()) {
                final String s2 = keywords.next();
                final String keywordValue = uLocale.getKeywordValue(s2);
                final String keyDisplayName = this.keyDisplayName(s2);
                final String keyValueDisplayName = this.keyValueDisplayName(s2, keywordValue);
                if (!keyValueDisplayName.equals(keywordValue)) {
                    this.appender.append(keyValueDisplayName, sb);
                }
                else if (!s2.equals(keyDisplayName)) {
                    this.appender.append(this.keyTypeFormat.format(new String[] { keyDisplayName, keyValueDisplayName }), sb);
                }
                else {
                    this.appender.append(keyDisplayName, sb).append("=").append(keyValueDisplayName);
                }
            }
        }
        Object string4 = null;
        if (sb.length() > 0) {
            string4 = sb.toString();
        }
        if (string4 != null) {
            s = this.format.format(new Object[] { s, string4 });
        }
        return this.adjustForUsageAndContext(CapitalizationContextUsage.LANGUAGE, s);
    }
    
    private String localeIdName(final String s) {
        return this.langData.get("Languages", s);
    }
    
    @Override
    public String languageDisplayName(final String s) {
        if (s.equals("root") || s.indexOf(95) != -1) {
            return s;
        }
        return this.adjustForUsageAndContext(CapitalizationContextUsage.LANGUAGE, this.langData.get("Languages", s));
    }
    
    @Override
    public String scriptDisplayName(final String s) {
        String s2 = this.langData.get("Scripts%stand-alone", s);
        if (s2.equals(s)) {
            s2 = this.langData.get("Scripts", s);
        }
        return this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, s2);
    }
    
    @Override
    public String scriptDisplayNameInContext(final String s) {
        return this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, this.langData.get("Scripts", s));
    }
    
    @Override
    public String scriptDisplayName(final int n) {
        return this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, this.scriptDisplayName(UScript.getShortName(n)));
    }
    
    @Override
    public String regionDisplayName(final String s) {
        return this.adjustForUsageAndContext(CapitalizationContextUsage.TERRITORY, this.regionData.get("Countries", s));
    }
    
    @Override
    public String variantDisplayName(final String s) {
        return this.adjustForUsageAndContext(CapitalizationContextUsage.VARIANT, this.langData.get("Variants", s));
    }
    
    @Override
    public String keyDisplayName(final String s) {
        return this.adjustForUsageAndContext(CapitalizationContextUsage.KEY, this.langData.get("Keys", s));
    }
    
    @Override
    public String keyValueDisplayName(final String s, final String s2) {
        return this.adjustForUsageAndContext(CapitalizationContextUsage.TYPE, this.langData.get("Types", s, s2));
    }
    
    public static boolean haveData(final DataTableType dataTableType) {
        switch (dataTableType) {
            case LANG: {
                return LangDataTables.impl instanceof ICUDataTables;
            }
            case REGION: {
                return RegionDataTables.impl instanceof ICUDataTables;
            }
            default: {
                throw new IllegalArgumentException("unknown type: " + dataTableType);
            }
        }
    }
    
    static {
        cache = new Cache(null);
        (contextUsageTypeMap = new HashMap()).put("languages", CapitalizationContextUsage.LANGUAGE);
        LocaleDisplayNamesImpl.contextUsageTypeMap.put("script", CapitalizationContextUsage.SCRIPT);
        LocaleDisplayNamesImpl.contextUsageTypeMap.put("territory", CapitalizationContextUsage.TERRITORY);
        LocaleDisplayNamesImpl.contextUsageTypeMap.put("variant", CapitalizationContextUsage.VARIANT);
        LocaleDisplayNamesImpl.contextUsageTypeMap.put("key", CapitalizationContextUsage.KEY);
        LocaleDisplayNamesImpl.contextUsageTypeMap.put("type", CapitalizationContextUsage.TYPE);
    }
    
    public enum DataTableType
    {
        LANG("LANG", 0), 
        REGION("REGION", 1);
        
        private static final DataTableType[] $VALUES;
        
        private DataTableType(final String s, final int n) {
        }
        
        static {
            $VALUES = new DataTableType[] { DataTableType.LANG, DataTableType.REGION };
        }
    }
    
    private static class Cache
    {
        private ULocale locale;
        private DialectHandling dialectHandling;
        private DisplayContext capitalization;
        private LocaleDisplayNames cache;
        
        private Cache() {
        }
        
        public LocaleDisplayNames get(final ULocale locale, final DialectHandling dialectHandling) {
            if (dialectHandling != this.dialectHandling || DisplayContext.CAPITALIZATION_NONE != this.capitalization || !locale.equals(this.locale)) {
                this.locale = locale;
                this.dialectHandling = dialectHandling;
                this.capitalization = DisplayContext.CAPITALIZATION_NONE;
                this.cache = new LocaleDisplayNamesImpl(locale, dialectHandling);
            }
            return this.cache;
        }
        
        public LocaleDisplayNames get(final ULocale locale, final DisplayContext... array) {
            DialectHandling standard_NAMES = DialectHandling.STANDARD_NAMES;
            DisplayContext capitalization_NONE = DisplayContext.CAPITALIZATION_NONE;
            while (0 < array.length) {
                final DisplayContext displayContext = array[0];
                switch (displayContext.type()) {
                    case DIALECT_HANDLING: {
                        standard_NAMES = ((displayContext.value() == DisplayContext.STANDARD_NAMES.value()) ? DialectHandling.STANDARD_NAMES : DialectHandling.DIALECT_NAMES);
                        break;
                    }
                    case CAPITALIZATION: {
                        capitalization_NONE = displayContext;
                        break;
                    }
                }
                int n = 0;
                ++n;
            }
            if (standard_NAMES != this.dialectHandling || capitalization_NONE != this.capitalization || !locale.equals(this.locale)) {
                this.locale = locale;
                this.dialectHandling = standard_NAMES;
                this.capitalization = capitalization_NONE;
                this.cache = new LocaleDisplayNamesImpl(locale, array);
            }
            return this.cache;
        }
        
        Cache(final LocaleDisplayNamesImpl$1 object) {
            this();
        }
    }
    
    static class Appender
    {
        private final String sep;
        
        Appender(final String sep) {
            this.sep = sep;
        }
        
        StringBuilder append(final String s, final StringBuilder sb) {
            if (sb.length() > 0) {
                sb.append(this.sep);
            }
            sb.append(s);
            return sb;
        }
    }
    
    static class RegionDataTables
    {
        static final DataTables impl;
        
        static {
            impl = DataTables.load("com.ibm.icu.impl.ICURegionDataTables");
        }
    }
    
    abstract static class DataTables
    {
        public abstract DataTable get(final ULocale p0);
        
        public static DataTables load(final String s) {
            return (DataTables)Class.forName(s).newInstance();
        }
    }
    
    public static class DataTable
    {
        ULocale getLocale() {
            return ULocale.ROOT;
        }
        
        String get(final String s, final String s2) {
            return this.get(s, null, s2);
        }
        
        String get(final String s, final String s2, final String s3) {
            return s3;
        }
    }
    
    static class LangDataTables
    {
        static final DataTables impl;
        
        static {
            impl = DataTables.load("com.ibm.icu.impl.ICULangDataTables");
        }
    }
    
    abstract static class ICUDataTables extends DataTables
    {
        private final String path;
        
        protected ICUDataTables(final String path) {
            this.path = path;
        }
        
        @Override
        public DataTable get(final ULocale uLocale) {
            return new ICUDataTable(this.path, uLocale);
        }
    }
    
    static class ICUDataTable extends DataTable
    {
        private final ICUResourceBundle bundle;
        
        public ICUDataTable(final String s, final ULocale uLocale) {
            this.bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(s, uLocale.getBaseName());
        }
        
        public ULocale getLocale() {
            return this.bundle.getULocale();
        }
        
        public String get(final String s, final String s2, final String s3) {
            return ICUResourceTableAccess.getTableString(this.bundle, s, s2, s3);
        }
    }
    
    private enum CapitalizationContextUsage
    {
        LANGUAGE("LANGUAGE", 0), 
        SCRIPT("SCRIPT", 1), 
        TERRITORY("TERRITORY", 2), 
        VARIANT("VARIANT", 3), 
        KEY("KEY", 4), 
        TYPE("TYPE", 5);
        
        private static final CapitalizationContextUsage[] $VALUES;
        
        private CapitalizationContextUsage(final String s, final int n) {
        }
        
        static {
            $VALUES = new CapitalizationContextUsage[] { CapitalizationContextUsage.LANGUAGE, CapitalizationContextUsage.SCRIPT, CapitalizationContextUsage.TERRITORY, CapitalizationContextUsage.VARIANT, CapitalizationContextUsage.KEY, CapitalizationContextUsage.TYPE };
        }
    }
}
