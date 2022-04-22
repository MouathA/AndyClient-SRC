package com.ibm.icu.util;

import java.io.*;
import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;
import java.text.*;
import com.ibm.icu.impl.locale.*;
import java.lang.reflect.*;
import java.util.*;
import java.security.*;

public final class ULocale implements Serializable
{
    private static final long serialVersionUID = 3715177670352309217L;
    public static final ULocale ENGLISH;
    public static final ULocale FRENCH;
    public static final ULocale GERMAN;
    public static final ULocale ITALIAN;
    public static final ULocale JAPANESE;
    public static final ULocale KOREAN;
    public static final ULocale CHINESE;
    public static final ULocale SIMPLIFIED_CHINESE;
    public static final ULocale TRADITIONAL_CHINESE;
    public static final ULocale FRANCE;
    public static final ULocale GERMANY;
    public static final ULocale ITALY;
    public static final ULocale JAPAN;
    public static final ULocale KOREA;
    public static final ULocale CHINA;
    public static final ULocale PRC;
    public static final ULocale TAIWAN;
    public static final ULocale UK;
    public static final ULocale US;
    public static final ULocale CANADA;
    public static final ULocale CANADA_FRENCH;
    private static final String EMPTY_STRING = "";
    private static final char UNDERSCORE = '_';
    private static final Locale EMPTY_LOCALE;
    private static final String LOCALE_ATTRIBUTE_KEY = "attribute";
    public static final ULocale ROOT;
    private static final SimpleCache CACHE;
    private transient Locale locale;
    private String localeID;
    private transient BaseLocale baseLocale;
    private transient LocaleExtensions extensions;
    private static String[][] CANONICALIZE_MAP;
    private static String[][] variantsToKeywords;
    private static ICUCache nameCache;
    private static Locale defaultLocale;
    private static ULocale defaultULocale;
    private static Locale[] defaultCategoryLocales;
    private static ULocale[] defaultCategoryULocales;
    public static Type ACTUAL_LOCALE;
    public static Type VALID_LOCALE;
    private static final String UNDEFINED_LANGUAGE = "und";
    private static final String UNDEFINED_SCRIPT = "Zzzz";
    private static final String UNDEFINED_REGION = "ZZ";
    public static final char PRIVATE_USE_EXTENSION = 'x';
    public static final char UNICODE_LOCALE_EXTENSION = 'u';
    
    private static void initCANONICALIZE_MAP() {
        if (ULocale.CANONICALIZE_MAP == null) {
            final String[][] canonicalize_MAP = { { "C", "en_US_POSIX", null, null }, { "art_LOJBAN", "jbo", null, null }, { "az_AZ_CYRL", "az_Cyrl_AZ", null, null }, { "az_AZ_LATN", "az_Latn_AZ", null, null }, { "ca_ES_PREEURO", "ca_ES", "currency", "ESP" }, { "cel_GAULISH", "cel__GAULISH", null, null }, { "de_1901", "de__1901", null, null }, { "de_1906", "de__1906", null, null }, { "de__PHONEBOOK", "de", "collation", "phonebook" }, { "de_AT_PREEURO", "de_AT", "currency", "ATS" }, { "de_DE_PREEURO", "de_DE", "currency", "DEM" }, { "de_LU_PREEURO", "de_LU", "currency", "EUR" }, { "el_GR_PREEURO", "el_GR", "currency", "GRD" }, { "en_BOONT", "en__BOONT", null, null }, { "en_SCOUSE", "en__SCOUSE", null, null }, { "en_BE_PREEURO", "en_BE", "currency", "BEF" }, { "en_IE_PREEURO", "en_IE", "currency", "IEP" }, { "es__TRADITIONAL", "es", "collation", "traditional" }, { "es_ES_PREEURO", "es_ES", "currency", "ESP" }, { "eu_ES_PREEURO", "eu_ES", "currency", "ESP" }, { "fi_FI_PREEURO", "fi_FI", "currency", "FIM" }, { "fr_BE_PREEURO", "fr_BE", "currency", "BEF" }, { "fr_FR_PREEURO", "fr_FR", "currency", "FRF" }, { "fr_LU_PREEURO", "fr_LU", "currency", "LUF" }, { "ga_IE_PREEURO", "ga_IE", "currency", "IEP" }, { "gl_ES_PREEURO", "gl_ES", "currency", "ESP" }, { "hi__DIRECT", "hi", "collation", "direct" }, { "it_IT_PREEURO", "it_IT", "currency", "ITL" }, { "ja_JP_TRADITIONAL", "ja_JP", "calendar", "japanese" }, { "nl_BE_PREEURO", "nl_BE", "currency", "BEF" }, { "nl_NL_PREEURO", "nl_NL", "currency", "NLG" }, { "pt_PT_PREEURO", "pt_PT", "currency", "PTE" }, { "sl_ROZAJ", "sl__ROZAJ", null, null }, { "sr_SP_CYRL", "sr_Cyrl_RS", null, null }, { "sr_SP_LATN", "sr_Latn_RS", null, null }, { "sr_YU_CYRILLIC", "sr_Cyrl_RS", null, null }, { "th_TH_TRADITIONAL", "th_TH", "calendar", "buddhist" }, { "uz_UZ_CYRILLIC", "uz_Cyrl_UZ", null, null }, { "uz_UZ_CYRL", "uz_Cyrl_UZ", null, null }, { "uz_UZ_LATN", "uz_Latn_UZ", null, null }, { "zh_CHS", "zh_Hans", null, null }, { "zh_CHT", "zh_Hant", null, null }, { "zh_GAN", "zh__GAN", null, null }, { "zh_GUOYU", "zh", null, null }, { "zh_HAKKA", "zh__HAKKA", null, null }, { "zh_MIN", "zh__MIN", null, null }, { "zh_MIN_NAN", "zh__MINNAN", null, null }, { "zh_WUU", "zh__WUU", null, null }, { "zh_XIANG", "zh__XIANG", null, null }, { "zh_YUE", "zh__YUE", null, null } };
            final Class<ULocale> clazz = ULocale.class;
            final Class<ULocale> clazz2 = ULocale.class;
            // monitorenter(clazz)
            if (ULocale.CANONICALIZE_MAP == null) {
                ULocale.CANONICALIZE_MAP = canonicalize_MAP;
            }
        }
        // monitorexit(clazz2)
        if (ULocale.variantsToKeywords == null) {
            final String[][] variantsToKeywords = { { "EURO", "currency", "EUR" }, { "PINYIN", "collation", "pinyin" }, { "STROKE", "collation", "stroke" } };
            final Class<ULocale> clazz3 = ULocale.class;
            final Class<ULocale> clazz4 = ULocale.class;
            // monitorenter(clazz3)
            if (ULocale.variantsToKeywords == null) {
                ULocale.variantsToKeywords = variantsToKeywords;
            }
        }
        // monitorexit(clazz4)
    }
    
    private ULocale(final String localeID, final Locale locale) {
        this.localeID = localeID;
        this.locale = locale;
    }
    
    private ULocale(final Locale locale) {
        this.localeID = getName(forLocale(locale).toString());
        this.locale = locale;
    }
    
    public static ULocale forLocale(final Locale locale) {
        if (locale == null) {
            return null;
        }
        ULocale uLocale = (ULocale)ULocale.CACHE.get(locale);
        if (uLocale == null) {
            uLocale = JDKLocaleHelper.toULocale(locale);
            ULocale.CACHE.put(locale, uLocale);
        }
        return uLocale;
    }
    
    public ULocale(final String s) {
        this.localeID = getName(s);
    }
    
    public ULocale(final String s, final String s2) {
        this(s, s2, null);
    }
    
    public ULocale(final String s, final String s2, final String s3) {
        this.localeID = getName(lscvToID(s, s2, s3, ""));
    }
    
    public static ULocale createCanonical(final String s) {
        return new ULocale(canonicalize(s), (Locale)null);
    }
    
    private static String lscvToID(final String s, final String s2, final String s3, final String s4) {
        final StringBuilder sb = new StringBuilder();
        if (s != null && s.length() > 0) {
            sb.append(s);
        }
        if (s2 != null && s2.length() > 0) {
            sb.append('_');
            sb.append(s2);
        }
        if (s3 != null && s3.length() > 0) {
            sb.append('_');
            sb.append(s3);
        }
        if (s4 != null && s4.length() > 0) {
            if (s3 == null || s3.length() == 0) {
                sb.append('_');
            }
            sb.append('_');
            sb.append(s4);
        }
        return sb.toString();
    }
    
    public Locale toLocale() {
        if (this.locale == null) {
            this.locale = JDKLocaleHelper.toLocale(this);
        }
        return this.locale;
    }
    
    public static ULocale getDefault() {
        final Class<ULocale> clazz = ULocale.class;
        final Class<ULocale> clazz2 = ULocale.class;
        // monitorenter(clazz)
        if (ULocale.defaultULocale == null) {
            // monitorexit(clazz2)
            return ULocale.ROOT;
        }
        final Locale default1 = Locale.getDefault();
        if (!ULocale.defaultLocale.equals(default1)) {
            ULocale.defaultLocale = default1;
            ULocale.defaultULocale = forLocale(default1);
            if (!JDKLocaleHelper.isJava7orNewer()) {
                final Category[] values = Category.values();
                while (0 < values.length) {
                    final int ordinal = values[0].ordinal();
                    ULocale.defaultCategoryLocales[ordinal] = default1;
                    ULocale.defaultCategoryULocales[ordinal] = forLocale(default1);
                    int n = 0;
                    ++n;
                }
            }
        }
        // monitorexit(clazz2)
        return ULocale.defaultULocale;
    }
    
    public static synchronized void setDefault(final ULocale defaultULocale) {
        Locale.setDefault(ULocale.defaultLocale = defaultULocale.toLocale());
        ULocale.defaultULocale = defaultULocale;
        final Category[] values = Category.values();
        while (0 < values.length) {
            setDefault(values[0], defaultULocale);
            int n = 0;
            ++n;
        }
    }
    
    public static ULocale getDefault(final Category category) {
        final Class<ULocale> clazz = ULocale.class;
        final Class<ULocale> clazz2 = ULocale.class;
        // monitorenter(clazz)
        final int ordinal = category.ordinal();
        if (ULocale.defaultCategoryULocales[ordinal] == null) {
            // monitorexit(clazz2)
            return ULocale.ROOT;
        }
        if (JDKLocaleHelper.isJava7orNewer()) {
            final Locale default1 = JDKLocaleHelper.getDefault(category);
            if (!ULocale.defaultCategoryLocales[ordinal].equals(default1)) {
                ULocale.defaultCategoryLocales[ordinal] = default1;
                ULocale.defaultCategoryULocales[ordinal] = forLocale(default1);
            }
        }
        else {
            final Locale default2 = Locale.getDefault();
            if (!ULocale.defaultLocale.equals(default2)) {
                ULocale.defaultLocale = default2;
                ULocale.defaultULocale = forLocale(default2);
                final Category[] values = Category.values();
                while (0 < values.length) {
                    final int ordinal2 = values[0].ordinal();
                    ULocale.defaultCategoryLocales[ordinal2] = default2;
                    ULocale.defaultCategoryULocales[ordinal2] = forLocale(default2);
                    int n = 0;
                    ++n;
                }
            }
        }
        // monitorexit(clazz2)
        return ULocale.defaultCategoryULocales[ordinal];
    }
    
    public static synchronized void setDefault(final Category category, final ULocale uLocale) {
        final Locale locale = uLocale.toLocale();
        final int ordinal = category.ordinal();
        ULocale.defaultCategoryULocales[ordinal] = uLocale;
        JDKLocaleHelper.setDefault(category, ULocale.defaultCategoryLocales[ordinal] = locale);
    }
    
    public Object clone() {
        return this;
    }
    
    @Override
    public int hashCode() {
        return this.localeID.hashCode();
    }
    
    public static ULocale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableULocales();
    }
    
    public static String[] getISOCountries() {
        return LocaleIDs.getISOCountries();
    }
    
    public static String[] getISOLanguages() {
        return LocaleIDs.getISOLanguages();
    }
    
    public String getLanguage() {
        return getLanguage(this.localeID);
    }
    
    public static String getLanguage(final String s) {
        return new LocaleIDParser(s).getLanguage();
    }
    
    public String getScript() {
        return getScript(this.localeID);
    }
    
    public static String getScript(final String s) {
        return new LocaleIDParser(s).getScript();
    }
    
    public String getCountry() {
        return getCountry(this.localeID);
    }
    
    public static String getCountry(final String s) {
        return new LocaleIDParser(s).getCountry();
    }
    
    public String getVariant() {
        return getVariant(this.localeID);
    }
    
    public static String getVariant(final String s) {
        return new LocaleIDParser(s).getVariant();
    }
    
    public static String getFallback(final String s) {
        return getFallbackString(getName(s));
    }
    
    public ULocale getFallback() {
        if (this.localeID.length() == 0 || this.localeID.charAt(0) == '@') {
            return null;
        }
        return new ULocale(getFallbackString(this.localeID), (Locale)null);
    }
    
    private static String getFallbackString(final String s) {
        int n = s.indexOf(64);
        if (n == -1) {
            n = s.length();
        }
        s.lastIndexOf(95, n);
        return s.substring(0, 0) + s.substring(n);
    }
    
    public String getBaseName() {
        return getBaseName(this.localeID);
    }
    
    public static String getBaseName(final String s) {
        if (s.indexOf(64) == -1) {
            return s;
        }
        return new LocaleIDParser(s).getBaseName();
    }
    
    public String getName() {
        return this.localeID;
    }
    
    private static int getShortestSubtagLength(final String s) {
        while (0 < s.length()) {
            if (s.charAt(0) != '_' && s.charAt(0) != '-') {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    public static String getName(final String s) {
        String name;
        if (s != null && !s.contains("@") && getShortestSubtagLength(s) == 1) {
            name = forLanguageTag(s).getName();
            if (name.length() == 0) {
                name = s;
            }
        }
        else {
            name = s;
        }
        String name2 = (String)ULocale.nameCache.get(name);
        if (name2 == null) {
            name2 = new LocaleIDParser(name).getName();
            ULocale.nameCache.put(name, name2);
        }
        return name2;
    }
    
    @Override
    public String toString() {
        return this.localeID;
    }
    
    public Iterator getKeywords() {
        return getKeywords(this.localeID);
    }
    
    public static Iterator getKeywords(final String s) {
        return new LocaleIDParser(s).getKeywords();
    }
    
    public String getKeywordValue(final String s) {
        return getKeywordValue(this.localeID, s);
    }
    
    public static String getKeywordValue(final String s, final String s2) {
        return new LocaleIDParser(s).getKeywordValue(s2);
    }
    
    public static String canonicalize(final String s) {
        final LocaleIDParser localeIDParser = new LocaleIDParser(s, true);
        String baseName = localeIDParser.getBaseName();
        if (s.equals("")) {
            return "";
        }
        int n = 0;
        while (0 < ULocale.variantsToKeywords.length) {
            final String[] array = ULocale.variantsToKeywords[0];
            int lastIndex = baseName.lastIndexOf("_" + array[0]);
            if (lastIndex > -1) {
                baseName = baseName.substring(0, lastIndex);
                if (baseName.endsWith("_")) {
                    baseName = baseName.substring(0, --lastIndex);
                }
                localeIDParser.setBaseName(baseName);
                localeIDParser.defaultKeywordValue(array[1], array[2]);
                break;
            }
            ++n;
        }
        while (0 < ULocale.CANONICALIZE_MAP.length) {
            if (ULocale.CANONICALIZE_MAP[0][0].equals(baseName)) {
                final String[] array2 = ULocale.CANONICALIZE_MAP[0];
                localeIDParser.setBaseName(array2[1]);
                if (array2[2] != null) {
                    localeIDParser.defaultKeywordValue(array2[2], array2[3]);
                    break;
                }
                break;
            }
            else {
                ++n;
            }
        }
        return localeIDParser.getName();
    }
    
    public ULocale setKeywordValue(final String s, final String s2) {
        return new ULocale(setKeywordValue(this.localeID, s, s2), (Locale)null);
    }
    
    public static String setKeywordValue(final String s, final String s2, final String s3) {
        final LocaleIDParser localeIDParser = new LocaleIDParser(s);
        localeIDParser.setKeywordValue(s2, s3);
        return localeIDParser.getName();
    }
    
    public String getISO3Language() {
        return getISO3Language(this.localeID);
    }
    
    public static String getISO3Language(final String s) {
        return LocaleIDs.getISO3Language(getLanguage(s));
    }
    
    public String getISO3Country() {
        return getISO3Country(this.localeID);
    }
    
    public static String getISO3Country(final String s) {
        return LocaleIDs.getISO3Country(getCountry(s));
    }
    
    public String getDisplayLanguage() {
        return getDisplayLanguageInternal(this, getDefault(Category.DISPLAY), false);
    }
    
    public String getDisplayLanguage(final ULocale uLocale) {
        return getDisplayLanguageInternal(this, uLocale, false);
    }
    
    public static String getDisplayLanguage(final String s, final String s2) {
        return getDisplayLanguageInternal(new ULocale(s), new ULocale(s2), false);
    }
    
    public static String getDisplayLanguage(final String s, final ULocale uLocale) {
        return getDisplayLanguageInternal(new ULocale(s), uLocale, false);
    }
    
    public String getDisplayLanguageWithDialect() {
        return getDisplayLanguageInternal(this, getDefault(Category.DISPLAY), true);
    }
    
    public String getDisplayLanguageWithDialect(final ULocale uLocale) {
        return getDisplayLanguageInternal(this, uLocale, true);
    }
    
    public static String getDisplayLanguageWithDialect(final String s, final String s2) {
        return getDisplayLanguageInternal(new ULocale(s), new ULocale(s2), true);
    }
    
    public static String getDisplayLanguageWithDialect(final String s, final ULocale uLocale) {
        return getDisplayLanguageInternal(new ULocale(s), uLocale, true);
    }
    
    private static String getDisplayLanguageInternal(final ULocale uLocale, final ULocale uLocale2, final boolean b) {
        return LocaleDisplayNames.getInstance(uLocale2).languageDisplayName(b ? uLocale.getBaseName() : uLocale.getLanguage());
    }
    
    public String getDisplayScript() {
        return getDisplayScriptInternal(this, getDefault(Category.DISPLAY));
    }
    
    @Deprecated
    public String getDisplayScriptInContext() {
        return getDisplayScriptInContextInternal(this, getDefault(Category.DISPLAY));
    }
    
    public String getDisplayScript(final ULocale uLocale) {
        return getDisplayScriptInternal(this, uLocale);
    }
    
    @Deprecated
    public String getDisplayScriptInContext(final ULocale uLocale) {
        return getDisplayScriptInContextInternal(this, uLocale);
    }
    
    public static String getDisplayScript(final String s, final String s2) {
        return getDisplayScriptInternal(new ULocale(s), new ULocale(s2));
    }
    
    @Deprecated
    public static String getDisplayScriptInContext(final String s, final String s2) {
        return getDisplayScriptInContextInternal(new ULocale(s), new ULocale(s2));
    }
    
    public static String getDisplayScript(final String s, final ULocale uLocale) {
        return getDisplayScriptInternal(new ULocale(s), uLocale);
    }
    
    @Deprecated
    public static String getDisplayScriptInContext(final String s, final ULocale uLocale) {
        return getDisplayScriptInContextInternal(new ULocale(s), uLocale);
    }
    
    private static String getDisplayScriptInternal(final ULocale uLocale, final ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).scriptDisplayName(uLocale.getScript());
    }
    
    private static String getDisplayScriptInContextInternal(final ULocale uLocale, final ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).scriptDisplayNameInContext(uLocale.getScript());
    }
    
    public String getDisplayCountry() {
        return getDisplayCountryInternal(this, getDefault(Category.DISPLAY));
    }
    
    public String getDisplayCountry(final ULocale uLocale) {
        return getDisplayCountryInternal(this, uLocale);
    }
    
    public static String getDisplayCountry(final String s, final String s2) {
        return getDisplayCountryInternal(new ULocale(s), new ULocale(s2));
    }
    
    public static String getDisplayCountry(final String s, final ULocale uLocale) {
        return getDisplayCountryInternal(new ULocale(s), uLocale);
    }
    
    private static String getDisplayCountryInternal(final ULocale uLocale, final ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).regionDisplayName(uLocale.getCountry());
    }
    
    public String getDisplayVariant() {
        return getDisplayVariantInternal(this, getDefault(Category.DISPLAY));
    }
    
    public String getDisplayVariant(final ULocale uLocale) {
        return getDisplayVariantInternal(this, uLocale);
    }
    
    public static String getDisplayVariant(final String s, final String s2) {
        return getDisplayVariantInternal(new ULocale(s), new ULocale(s2));
    }
    
    public static String getDisplayVariant(final String s, final ULocale uLocale) {
        return getDisplayVariantInternal(new ULocale(s), uLocale);
    }
    
    private static String getDisplayVariantInternal(final ULocale uLocale, final ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).variantDisplayName(uLocale.getVariant());
    }
    
    public static String getDisplayKeyword(final String s) {
        return getDisplayKeywordInternal(s, getDefault(Category.DISPLAY));
    }
    
    public static String getDisplayKeyword(final String s, final String s2) {
        return getDisplayKeywordInternal(s, new ULocale(s2));
    }
    
    public static String getDisplayKeyword(final String s, final ULocale uLocale) {
        return getDisplayKeywordInternal(s, uLocale);
    }
    
    private static String getDisplayKeywordInternal(final String s, final ULocale uLocale) {
        return LocaleDisplayNames.getInstance(uLocale).keyDisplayName(s);
    }
    
    public String getDisplayKeywordValue(final String s) {
        return getDisplayKeywordValueInternal(this, s, getDefault(Category.DISPLAY));
    }
    
    public String getDisplayKeywordValue(final String s, final ULocale uLocale) {
        return getDisplayKeywordValueInternal(this, s, uLocale);
    }
    
    public static String getDisplayKeywordValue(final String s, final String s2, final String s3) {
        return getDisplayKeywordValueInternal(new ULocale(s), s2, new ULocale(s3));
    }
    
    public static String getDisplayKeywordValue(final String s, final String s2, final ULocale uLocale) {
        return getDisplayKeywordValueInternal(new ULocale(s), s2, uLocale);
    }
    
    private static String getDisplayKeywordValueInternal(final ULocale uLocale, String lowerString, final ULocale uLocale2) {
        lowerString = AsciiUtil.toLowerString(lowerString.trim());
        return LocaleDisplayNames.getInstance(uLocale2).keyValueDisplayName(lowerString, uLocale.getKeywordValue(lowerString));
    }
    
    public String getDisplayName() {
        return getDisplayNameInternal(this, getDefault(Category.DISPLAY));
    }
    
    public String getDisplayName(final ULocale uLocale) {
        return getDisplayNameInternal(this, uLocale);
    }
    
    public static String getDisplayName(final String s, final String s2) {
        return getDisplayNameInternal(new ULocale(s), new ULocale(s2));
    }
    
    public static String getDisplayName(final String s, final ULocale uLocale) {
        return getDisplayNameInternal(new ULocale(s), uLocale);
    }
    
    private static String getDisplayNameInternal(final ULocale uLocale, final ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).localeDisplayName(uLocale);
    }
    
    public String getDisplayNameWithDialect() {
        return getDisplayNameWithDialectInternal(this, getDefault(Category.DISPLAY));
    }
    
    public String getDisplayNameWithDialect(final ULocale uLocale) {
        return getDisplayNameWithDialectInternal(this, uLocale);
    }
    
    public static String getDisplayNameWithDialect(final String s, final String s2) {
        return getDisplayNameWithDialectInternal(new ULocale(s), new ULocale(s2));
    }
    
    public static String getDisplayNameWithDialect(final String s, final ULocale uLocale) {
        return getDisplayNameWithDialectInternal(new ULocale(s), uLocale);
    }
    
    private static String getDisplayNameWithDialectInternal(final ULocale uLocale, final ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2, LocaleDisplayNames.DialectHandling.DIALECT_NAMES).localeDisplayName(uLocale);
    }
    
    public String getCharacterOrientation() {
        return ICUResourceTableAccess.getTableString("com/ibm/icu/impl/data/icudt51b", this, "layout", "characters");
    }
    
    public String getLineOrientation() {
        return ICUResourceTableAccess.getTableString("com/ibm/icu/impl/data/icudt51b", this, "layout", "lines");
    }
    
    public static ULocale acceptLanguage(final String s, final ULocale[] array, final boolean[] array2) {
        if (s == null) {
            throw new NullPointerException();
        }
        final ULocale[] acceptLanguage = parseAcceptLanguage(s, true);
        if (acceptLanguage == null) {
            return null;
        }
        return acceptLanguage(acceptLanguage, array, array2);
    }
    
    public static ULocale acceptLanguage(final ULocale[] array, final ULocale[] array2, final boolean[] array3) {
        if (array3 != null) {
            array3[0] = true;
        }
        while (0 < array.length) {
            ULocale uLocale = array[0];
            boolean[] array4 = array3;
            while (true) {
                if (0 < array2.length) {
                    if (array2[0] == uLocale) {
                        if (array4 != null) {
                            array4[0] = false;
                        }
                        return array2[0];
                    }
                    if (uLocale.getScript().length() == 0 && array2[0].getScript().length() > 0 && array2[0].getLanguage().equals(uLocale.getLanguage()) && array2[0].getCountry().equals(uLocale.getCountry()) && array2[0].getVariant().equals(uLocale.getVariant()) && minimizeSubtags(array2[0]).getScript().length() == 0) {
                        if (array4 != null) {
                            array4[0] = false;
                        }
                        return uLocale;
                    }
                    int n = 0;
                    ++n;
                }
                else {
                    final Locale fallback = LocaleUtility.fallback(uLocale.toLocale());
                    if (fallback != null) {
                        uLocale = new ULocale(fallback);
                    }
                    else {
                        uLocale = null;
                    }
                    array4 = null;
                    if (uLocale == null) {
                        int n2 = 0;
                        ++n2;
                        break;
                    }
                    continue;
                }
            }
        }
        return null;
    }
    
    public static ULocale acceptLanguage(final String s, final boolean[] array) {
        return acceptLanguage(s, getAvailableLocales(), array);
    }
    
    public static ULocale acceptLanguage(final ULocale[] array, final boolean[] array2) {
        return acceptLanguage(array, getAvailableLocales(), array2);
    }
    
    static ULocale[] parseAcceptLanguage(String string, final boolean b) throws ParseException {
        final TreeMap<ULocaleAcceptLanguageQ, ULocale> treeMap = new TreeMap<ULocaleAcceptLanguageQ, ULocale>();
        final StringBuilder sb = new StringBuilder();
        final StringBuilder sb2 = new StringBuilder();
        string += ",";
        while (0 < string.length()) {
            final char char1 = string.charAt(0);
            switch (false) {
                case 0: {
                    if (('A' <= char1 && char1 <= 'Z') || ('a' <= char1 && char1 <= 'z')) {
                        sb.append(char1);
                        break;
                    }
                    if (char1 == '*') {
                        sb.append(char1);
                        break;
                    }
                    if (char1 != ' ' && char1 != '\t') {
                        break;
                    }
                    break;
                }
                case 1: {
                    if (('A' <= char1 && char1 <= 'Z') || ('a' <= char1 && char1 <= 'z')) {
                        sb.append(char1);
                        break;
                    }
                    if (char1 == '-') {
                        sb.append(char1);
                        break;
                    }
                    if (char1 == '_') {
                        if (b) {
                            sb.append(char1);
                            break;
                        }
                        break;
                    }
                    else {
                        if ('0' <= char1 && char1 <= '9') {
                            sb.append(char1);
                            break;
                        }
                        if (char1 == ',') {
                            break;
                        }
                        if (char1 == ' ' || char1 == '\t') {
                            break;
                        }
                        if (char1 == ';') {
                            break;
                        }
                        break;
                    }
                    break;
                }
                case 2: {
                    if (char1 == ',') {
                        break;
                    }
                    if (char1 == ' ' || char1 == '\t') {
                        break;
                    }
                    if (char1 == ';') {
                        break;
                    }
                    break;
                }
                case 3: {
                    if (char1 == ',') {
                        break;
                    }
                    if (char1 == ';') {
                        break;
                    }
                    if (char1 != ' ' && char1 != '\t') {
                        break;
                    }
                    break;
                }
                case 4: {
                    if (char1 == 'q') {
                        break;
                    }
                    if (char1 != ' ' && char1 != '\t') {
                        break;
                    }
                    break;
                }
                case 5: {
                    if (char1 == '=') {
                        break;
                    }
                    if (char1 != ' ' && char1 != '\t') {
                        break;
                    }
                    break;
                }
                case 6: {
                    if (char1 == '0') {
                        sb2.append(char1);
                        break;
                    }
                    if (char1 == '1') {
                        sb2.append(char1);
                        break;
                    }
                    if (char1 == '.') {
                        if (b) {
                            sb2.append(char1);
                            break;
                        }
                        break;
                    }
                    else {
                        if (char1 != ' ' && char1 != '\t') {
                            break;
                        }
                        break;
                    }
                    break;
                }
                case 7: {
                    if (char1 == '.') {
                        sb2.append(char1);
                        break;
                    }
                    if (char1 == ',') {
                        break;
                    }
                    if (char1 == ' ' || char1 == '\t') {
                        break;
                    }
                    break;
                }
                case 8: {
                    if ('0' <= char1 || char1 <= '9') {
                        sb2.append(char1);
                        break;
                    }
                    break;
                }
                case 9: {
                    if ('0' <= char1 && char1 <= '9') {
                        sb2.append(char1);
                        break;
                    }
                    if (char1 == ',') {
                        break;
                    }
                    if (char1 == ' ' || char1 == '\t') {
                        break;
                    }
                    break;
                }
                case 10: {
                    if (char1 == ',') {
                        break;
                    }
                    if (char1 == ' ' || char1 != '\t') {}
                    break;
                }
            }
            double double1 = 1.0;
            if (sb2.length() != 0) {
                double1 = Double.parseDouble(sb2.toString());
                if (double1 > 1.0) {
                    double1 = 1.0;
                }
            }
            if (sb.charAt(0) != '*') {
                treeMap.put(new ULocaleAcceptLanguageQ(double1, treeMap.size()), new ULocale(canonicalize(sb.toString())));
            }
            sb.setLength(0);
            sb2.setLength(0);
            int n = 0;
            ++n;
        }
        return treeMap.values().toArray(new ULocale[treeMap.size()]);
    }
    
    public static ULocale addLikelySubtags(final ULocale uLocale) {
        final String[] array = new String[3];
        String substring = null;
        final int tagString = parseTagString(uLocale.localeID, array);
        if (tagString < uLocale.localeID.length()) {
            substring = uLocale.localeID.substring(tagString);
        }
        final String likelySubtagsString = createLikelySubtagsString(array[0], array[1], array[2], substring);
        return (likelySubtagsString == null) ? uLocale : new ULocale(likelySubtagsString);
    }
    
    public static ULocale minimizeSubtags(final ULocale uLocale) {
        final String[] array = new String[3];
        final int tagString = parseTagString(uLocale.localeID, array);
        final String s = array[0];
        final String s2 = array[1];
        final String s3 = array[2];
        String substring = null;
        if (tagString < uLocale.localeID.length()) {
            substring = uLocale.localeID.substring(tagString);
        }
        final String likelySubtagsString = createLikelySubtagsString(s, s2, s3, null);
        if (likelySubtagsString != null) {
            return uLocale;
        }
        if (createLikelySubtagsString(s, null, null, null).equals(likelySubtagsString)) {
            return new ULocale(createTagString(s, null, null, substring));
        }
        if (s3.length() != 0 && createLikelySubtagsString(s, null, s3, null).equals(likelySubtagsString)) {
            return new ULocale(createTagString(s, null, s3, substring));
        }
        if (s3.length() != 0 && s2.length() != 0 && createLikelySubtagsString(s, s2, null, null).equals(likelySubtagsString)) {
            return new ULocale(createTagString(s, s2, null, substring));
        }
        return uLocale;
    }
    
    private static void appendTag(final String s, final StringBuilder sb) {
        if (sb.length() != 0) {
            sb.append('_');
        }
        sb.append(s);
    }
    
    private static String createTagString(final String s, final String s2, final String s3, final String s4, final String s5) {
        LocaleIDParser localeIDParser = null;
        final StringBuilder sb = new StringBuilder();
        if (s != null) {
            appendTag(s, sb);
        }
        else if (s5 != null) {
            appendTag("und", sb);
        }
        else {
            localeIDParser = new LocaleIDParser(s5);
            final String language = localeIDParser.getLanguage();
            appendTag((language != null) ? language : "und", sb);
        }
        if (s2 != null) {
            appendTag(s2, sb);
        }
        else if (s5 != null) {
            if (localeIDParser == null) {
                localeIDParser = new LocaleIDParser(s5);
            }
            final String script = localeIDParser.getScript();
            if (script != null) {
                appendTag(script, sb);
            }
        }
        if (s3 != null) {
            appendTag(s3, sb);
        }
        else if (s5 != null) {
            if (localeIDParser == null) {
                localeIDParser = new LocaleIDParser(s5);
            }
            final String country = localeIDParser.getCountry();
            if (country != null) {
                appendTag(country, sb);
            }
        }
        if (s4 != null && s4.length() > 1) {
            if (s4.charAt(0) != '_' || s4.charAt(1) == '_') {}
            sb.append(s4);
        }
        return sb.toString();
    }
    
    static String createTagString(final String s, final String s2, final String s3, final String s4) {
        return createTagString(s, s2, s3, s4, null);
    }
    
    private static int parseTagString(final String s, final String[] array) {
        final LocaleIDParser localeIDParser = new LocaleIDParser(s);
        final String language = localeIDParser.getLanguage();
        final String script = localeIDParser.getScript();
        final String country = localeIDParser.getCountry();
        if (language != null) {
            array[0] = "und";
        }
        else {
            array[0] = language;
        }
        if (script.equals("Zzzz")) {
            array[1] = "";
        }
        else {
            array[1] = script;
        }
        if (country.equals("ZZ")) {
            array[2] = "";
        }
        else {
            array[2] = country;
        }
        final String variant = localeIDParser.getVariant();
        if (variant != null) {
            final int index = s.indexOf(variant);
            return (index > 0) ? (index - 1) : index;
        }
        final int index2 = s.indexOf(64);
        return (index2 == -1) ? s.length() : index2;
    }
    
    private static String lookupLikelySubtags(final String s) {
        return UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "likelySubtags").getString(s);
    }
    
    private static String createLikelySubtagsString(final String s, final String s2, final String s3, final String s4) {
        if (s2 != null && s3 != null) {
            final String lookupLikelySubtags = lookupLikelySubtags(createTagString(s, s2, s3, null));
            if (lookupLikelySubtags != null) {
                return createTagString(null, null, null, s4, lookupLikelySubtags);
            }
        }
        if (s2 != null) {
            final String lookupLikelySubtags2 = lookupLikelySubtags(createTagString(s, s2, null, null));
            if (lookupLikelySubtags2 != null) {
                return createTagString(null, null, s3, s4, lookupLikelySubtags2);
            }
        }
        if (s3 != null) {
            final String lookupLikelySubtags3 = lookupLikelySubtags(createTagString(s, null, s3, null));
            if (lookupLikelySubtags3 != null) {
                return createTagString(null, s2, null, s4, lookupLikelySubtags3);
            }
        }
        final String lookupLikelySubtags4 = lookupLikelySubtags(createTagString(s, null, null, null));
        if (lookupLikelySubtags4 != null) {
            return createTagString(null, s2, s3, s4, lookupLikelySubtags4);
        }
        return null;
    }
    
    public String getExtension(final char c) {
        if (!LocaleExtensions.isValidKey(c)) {
            throw new IllegalArgumentException("Invalid extension key: " + c);
        }
        return this.extensions().getExtensionValue(c);
    }
    
    public Set getExtensionKeys() {
        return this.extensions().getKeys();
    }
    
    public Set getUnicodeLocaleAttributes() {
        return this.extensions().getUnicodeLocaleAttributes();
    }
    
    public String getUnicodeLocaleType(final String s) {
        if (!LocaleExtensions.isValidUnicodeLocaleKey(s)) {
            throw new IllegalArgumentException("Invalid Unicode locale key: " + s);
        }
        return this.extensions().getUnicodeLocaleType(s);
    }
    
    public Set getUnicodeLocaleKeys() {
        return this.extensions().getUnicodeLocaleKeys();
    }
    
    public String toLanguageTag() {
        BaseLocale baseLocale = this.base();
        LocaleExtensions localeExtensions = this.extensions();
        if (baseLocale.getVariant().equalsIgnoreCase("POSIX")) {
            baseLocale = BaseLocale.getInstance(baseLocale.getLanguage(), baseLocale.getScript(), baseLocale.getRegion(), "");
            if (localeExtensions.getUnicodeLocaleType("va") == null) {
                final InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
                internalLocaleBuilder.setLocale(BaseLocale.ROOT, localeExtensions);
                internalLocaleBuilder.setUnicodeLocaleKeyword("va", "posix");
                localeExtensions = internalLocaleBuilder.getLocaleExtensions();
            }
        }
        final LanguageTag locale = LanguageTag.parseLocale(baseLocale, localeExtensions);
        final StringBuilder sb = new StringBuilder();
        final String language = locale.getLanguage();
        if (language.length() > 0) {
            sb.append(LanguageTag.canonicalizeLanguage(language));
        }
        final String script = locale.getScript();
        if (script.length() > 0) {
            sb.append("-");
            sb.append(LanguageTag.canonicalizeScript(script));
        }
        final String region = locale.getRegion();
        if (region.length() > 0) {
            sb.append("-");
            sb.append(LanguageTag.canonicalizeRegion(region));
        }
        for (final String s : locale.getVariants()) {
            sb.append("-");
            sb.append(LanguageTag.canonicalizeVariant(s));
        }
        for (final String s2 : locale.getExtensions()) {
            sb.append("-");
            sb.append(LanguageTag.canonicalizeExtension(s2));
        }
        final String privateuse = locale.getPrivateuse();
        if (privateuse.length() > 0) {
            if (sb.length() > 0) {
                sb.append("-");
            }
            sb.append("x").append("-");
            sb.append(LanguageTag.canonicalizePrivateuse(privateuse));
        }
        return sb.toString();
    }
    
    public static ULocale forLanguageTag(final String s) {
        final LanguageTag parse = LanguageTag.parse(s, null);
        final InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
        internalLocaleBuilder.setLanguageTag(parse);
        return getInstance(internalLocaleBuilder.getBaseLocale(), internalLocaleBuilder.getLocaleExtensions());
    }
    
    private static ULocale getInstance(final BaseLocale baseLocale, final LocaleExtensions localeExtensions) {
        String s = lscvToID(baseLocale.getLanguage(), baseLocale.getScript(), baseLocale.getRegion(), baseLocale.getVariant());
        final Set keys = localeExtensions.getKeys();
        if (!keys.isEmpty()) {
            final TreeMap<String, String> treeMap = new TreeMap<String, String>();
            for (final Character c : keys) {
                final Extension extension = localeExtensions.getExtension(c);
                if (extension instanceof UnicodeLocaleExtension) {
                    final UnicodeLocaleExtension unicodeLocaleExtension = (UnicodeLocaleExtension)extension;
                    for (final String s2 : unicodeLocaleExtension.getUnicodeLocaleKeys()) {
                        final String unicodeLocaleType = unicodeLocaleExtension.getUnicodeLocaleType(s2);
                        final String bcp47ToLDMLKey = bcp47ToLDMLKey(s2);
                        final String bcp47ToLDMLType = bcp47ToLDMLType(bcp47ToLDMLKey, (unicodeLocaleType.length() == 0) ? "yes" : unicodeLocaleType);
                        if (bcp47ToLDMLKey.equals("va") && bcp47ToLDMLType.equals("posix") && baseLocale.getVariant().length() == 0) {
                            s += "_POSIX";
                        }
                        else {
                            treeMap.put(bcp47ToLDMLKey, bcp47ToLDMLType);
                        }
                    }
                    final Set unicodeLocaleAttributes = unicodeLocaleExtension.getUnicodeLocaleAttributes();
                    if (unicodeLocaleAttributes.size() <= 0) {
                        continue;
                    }
                    final StringBuilder sb = new StringBuilder();
                    for (final String s3 : unicodeLocaleAttributes) {
                        if (sb.length() > 0) {
                            sb.append('-');
                        }
                        sb.append(s3);
                    }
                    treeMap.put("attribute", sb.toString());
                }
                else {
                    treeMap.put(String.valueOf(c), extension.getValue());
                }
            }
            if (!treeMap.isEmpty()) {
                final StringBuilder sb2 = new StringBuilder(s);
                sb2.append("@");
                for (final Map.Entry<String, String> entry : treeMap.entrySet()) {
                    sb2.append(";");
                    sb2.append(entry.getKey());
                    sb2.append("=");
                    sb2.append(entry.getValue());
                }
                s = sb2.toString();
            }
        }
        return new ULocale(s);
    }
    
    private BaseLocale base() {
        if (this.baseLocale == null) {
            String language = this.getLanguage();
            if (this == ULocale.ROOT) {
                language = "";
            }
            this.baseLocale = BaseLocale.getInstance(language, this.getScript(), this.getCountry(), this.getVariant());
        }
        return this.baseLocale;
    }
    
    private LocaleExtensions extensions() {
        if (this.extensions == null) {
            final Iterator keywords = this.getKeywords();
            if (keywords == null) {
                this.extensions = LocaleExtensions.EMPTY_EXTENSIONS;
            }
            else {
                final InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
                while (keywords.hasNext()) {
                    final String s = keywords.next();
                    if (s.equals("attribute")) {
                        final String[] split = this.getKeywordValue(s).split("[-_]");
                        while (0 < split.length) {
                            internalLocaleBuilder.addUnicodeLocaleAttribute(split[0]);
                            int n = 0;
                            ++n;
                        }
                    }
                    else if (s.length() >= 2) {
                        final String ldmlKeyToBCP47 = ldmlKeyToBCP47(s);
                        final String ldmlTypeToBCP47 = ldmlTypeToBCP47(s, this.getKeywordValue(s));
                        if (ldmlKeyToBCP47 == null || ldmlTypeToBCP47 == null) {
                            continue;
                        }
                        internalLocaleBuilder.setUnicodeLocaleKeyword(ldmlKeyToBCP47, ldmlTypeToBCP47);
                    }
                    else {
                        if (s.length() != 1 || s.charAt(0) == 'u') {
                            continue;
                        }
                        internalLocaleBuilder.setExtension(s.charAt(0), this.getKeywordValue(s).replace("_", "-"));
                    }
                }
                this.extensions = internalLocaleBuilder.getLocaleExtensions();
            }
        }
        return this.extensions;
    }
    
    private static String ldmlKeyToBCP47(String lowerString) {
        final UResourceBundle value = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("keyMap");
        lowerString = AsciiUtil.toLowerString(lowerString);
        final String string = value.getString(lowerString);
        if (string != null) {
            return string;
        }
        if (lowerString.length() == 2 && LanguageTag.isExtensionSubtag(lowerString)) {
            return lowerString;
        }
        return null;
    }
    
    private static String bcp47ToLDMLKey(String lowerString) {
        final UResourceBundle value = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("keyMap");
        lowerString = AsciiUtil.toLowerString(lowerString);
        String key = null;
        while (0 < value.getSize()) {
            final UResourceBundle value2 = value.get(0);
            if (lowerString.equals(value2.getString())) {
                key = value2.getKey();
                break;
            }
            int n = 0;
            ++n;
        }
        if (key == null) {
            return lowerString;
        }
        return key;
    }
    
    private static String ldmlTypeToBCP47(String lowerString, final String s) {
        final UResourceBundle bundleInstance = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        final UResourceBundle value = bundleInstance.get("typeMap");
        lowerString = AsciiUtil.toLowerString(lowerString);
        final String s2 = lowerString.equals("timezone") ? s.replace('/', ':') : s;
        final UResourceBundle value2 = value.get(lowerString);
        String s3 = value2.getString(s2);
        if (s3 == null && value2 != null) {
            s3 = value2.getString(bundleInstance.get("typeAlias").get(lowerString).getString(s2).replace('/', ':'));
        }
        if (s3 != null) {
            return s3;
        }
        final int length = s.length();
        if (length >= 3 && length <= 8 && LanguageTag.isExtensionSubtag(s)) {
            return s;
        }
        return null;
    }
    
    private static String bcp47ToLDMLType(String lowerString, String lowerString2) {
        final UResourceBundle value = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("typeMap");
        lowerString = AsciiUtil.toLowerString(lowerString);
        lowerString2 = AsciiUtil.toLowerString(lowerString2);
        String s = null;
        final UResourceBundle value2 = value.get(lowerString);
        while (0 < value2.getSize()) {
            final UResourceBundle value3 = value2.get(0);
            if (lowerString2.equals(value3.getString())) {
                s = value3.getKey();
                if (lowerString.equals("timezone")) {
                    s = s.replace(':', '/');
                    break;
                }
                break;
            }
            else {
                int n = 0;
                ++n;
            }
        }
        if (s == null) {
            return lowerString2;
        }
        return s;
    }
    
    static BaseLocale access$100(final ULocale uLocale) {
        return uLocale.base();
    }
    
    static LocaleExtensions access$200(final ULocale uLocale) {
        return uLocale.extensions();
    }
    
    static ULocale access$300(final BaseLocale baseLocale, final LocaleExtensions localeExtensions) {
        return getInstance(baseLocale, localeExtensions);
    }
    
    static String access$400(final String s) {
        return bcp47ToLDMLKey(s);
    }
    
    static String access$500(final String s, final String s2) {
        return bcp47ToLDMLType(s, s2);
    }
    
    ULocale(final String s, final Locale locale, final ULocale$1 object) {
        this(s, locale);
    }
    
    static {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: ldc_w           "en"
        //     7: getstatic       java/util/Locale.ENGLISH:Ljava/util/Locale;
        //    10: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //    13: putstatic       com/ibm/icu/util/ULocale.ENGLISH:Lcom/ibm/icu/util/ULocale;
        //    16: new             Lcom/ibm/icu/util/ULocale;
        //    19: dup            
        //    20: ldc_w           "fr"
        //    23: getstatic       java/util/Locale.FRENCH:Ljava/util/Locale;
        //    26: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //    29: putstatic       com/ibm/icu/util/ULocale.FRENCH:Lcom/ibm/icu/util/ULocale;
        //    32: new             Lcom/ibm/icu/util/ULocale;
        //    35: dup            
        //    36: ldc             "de"
        //    38: getstatic       java/util/Locale.GERMAN:Ljava/util/Locale;
        //    41: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //    44: putstatic       com/ibm/icu/util/ULocale.GERMAN:Lcom/ibm/icu/util/ULocale;
        //    47: new             Lcom/ibm/icu/util/ULocale;
        //    50: dup            
        //    51: ldc_w           "it"
        //    54: getstatic       java/util/Locale.ITALIAN:Ljava/util/Locale;
        //    57: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //    60: putstatic       com/ibm/icu/util/ULocale.ITALIAN:Lcom/ibm/icu/util/ULocale;
        //    63: new             Lcom/ibm/icu/util/ULocale;
        //    66: dup            
        //    67: ldc_w           "ja"
        //    70: getstatic       java/util/Locale.JAPANESE:Ljava/util/Locale;
        //    73: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //    76: putstatic       com/ibm/icu/util/ULocale.JAPANESE:Lcom/ibm/icu/util/ULocale;
        //    79: new             Lcom/ibm/icu/util/ULocale;
        //    82: dup            
        //    83: ldc_w           "ko"
        //    86: getstatic       java/util/Locale.KOREAN:Ljava/util/Locale;
        //    89: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //    92: putstatic       com/ibm/icu/util/ULocale.KOREAN:Lcom/ibm/icu/util/ULocale;
        //    95: new             Lcom/ibm/icu/util/ULocale;
        //    98: dup            
        //    99: ldc_w           "zh"
        //   102: getstatic       java/util/Locale.CHINESE:Ljava/util/Locale;
        //   105: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   108: putstatic       com/ibm/icu/util/ULocale.CHINESE:Lcom/ibm/icu/util/ULocale;
        //   111: new             Lcom/ibm/icu/util/ULocale;
        //   114: dup            
        //   115: ldc_w           "zh_Hans"
        //   118: getstatic       java/util/Locale.CHINESE:Ljava/util/Locale;
        //   121: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   124: putstatic       com/ibm/icu/util/ULocale.SIMPLIFIED_CHINESE:Lcom/ibm/icu/util/ULocale;
        //   127: new             Lcom/ibm/icu/util/ULocale;
        //   130: dup            
        //   131: ldc_w           "zh_Hant"
        //   134: getstatic       java/util/Locale.CHINESE:Ljava/util/Locale;
        //   137: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   140: putstatic       com/ibm/icu/util/ULocale.TRADITIONAL_CHINESE:Lcom/ibm/icu/util/ULocale;
        //   143: new             Lcom/ibm/icu/util/ULocale;
        //   146: dup            
        //   147: ldc             "fr_FR"
        //   149: getstatic       java/util/Locale.FRANCE:Ljava/util/Locale;
        //   152: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   155: putstatic       com/ibm/icu/util/ULocale.FRANCE:Lcom/ibm/icu/util/ULocale;
        //   158: new             Lcom/ibm/icu/util/ULocale;
        //   161: dup            
        //   162: ldc             "de_DE"
        //   164: getstatic       java/util/Locale.GERMANY:Ljava/util/Locale;
        //   167: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   170: putstatic       com/ibm/icu/util/ULocale.GERMANY:Lcom/ibm/icu/util/ULocale;
        //   173: new             Lcom/ibm/icu/util/ULocale;
        //   176: dup            
        //   177: ldc_w           "it_IT"
        //   180: getstatic       java/util/Locale.ITALY:Ljava/util/Locale;
        //   183: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   186: putstatic       com/ibm/icu/util/ULocale.ITALY:Lcom/ibm/icu/util/ULocale;
        //   189: new             Lcom/ibm/icu/util/ULocale;
        //   192: dup            
        //   193: ldc_w           "ja_JP"
        //   196: getstatic       java/util/Locale.JAPAN:Ljava/util/Locale;
        //   199: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   202: putstatic       com/ibm/icu/util/ULocale.JAPAN:Lcom/ibm/icu/util/ULocale;
        //   205: new             Lcom/ibm/icu/util/ULocale;
        //   208: dup            
        //   209: ldc_w           "ko_KR"
        //   212: getstatic       java/util/Locale.KOREA:Ljava/util/Locale;
        //   215: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   218: putstatic       com/ibm/icu/util/ULocale.KOREA:Lcom/ibm/icu/util/ULocale;
        //   221: new             Lcom/ibm/icu/util/ULocale;
        //   224: dup            
        //   225: ldc_w           "zh_Hans_CN"
        //   228: getstatic       java/util/Locale.CHINA:Ljava/util/Locale;
        //   231: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   234: putstatic       com/ibm/icu/util/ULocale.CHINA:Lcom/ibm/icu/util/ULocale;
        //   237: getstatic       com/ibm/icu/util/ULocale.CHINA:Lcom/ibm/icu/util/ULocale;
        //   240: putstatic       com/ibm/icu/util/ULocale.PRC:Lcom/ibm/icu/util/ULocale;
        //   243: new             Lcom/ibm/icu/util/ULocale;
        //   246: dup            
        //   247: ldc_w           "zh_Hant_TW"
        //   250: getstatic       java/util/Locale.TAIWAN:Ljava/util/Locale;
        //   253: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   256: putstatic       com/ibm/icu/util/ULocale.TAIWAN:Lcom/ibm/icu/util/ULocale;
        //   259: new             Lcom/ibm/icu/util/ULocale;
        //   262: dup            
        //   263: ldc_w           "en_GB"
        //   266: getstatic       java/util/Locale.UK:Ljava/util/Locale;
        //   269: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   272: putstatic       com/ibm/icu/util/ULocale.UK:Lcom/ibm/icu/util/ULocale;
        //   275: new             Lcom/ibm/icu/util/ULocale;
        //   278: dup            
        //   279: ldc_w           "en_US"
        //   282: getstatic       java/util/Locale.US:Ljava/util/Locale;
        //   285: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   288: putstatic       com/ibm/icu/util/ULocale.US:Lcom/ibm/icu/util/ULocale;
        //   291: new             Lcom/ibm/icu/util/ULocale;
        //   294: dup            
        //   295: ldc_w           "en_CA"
        //   298: getstatic       java/util/Locale.CANADA:Ljava/util/Locale;
        //   301: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   304: putstatic       com/ibm/icu/util/ULocale.CANADA:Lcom/ibm/icu/util/ULocale;
        //   307: new             Lcom/ibm/icu/util/ULocale;
        //   310: dup            
        //   311: ldc_w           "fr_CA"
        //   314: getstatic       java/util/Locale.CANADA_FRENCH:Ljava/util/Locale;
        //   317: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   320: putstatic       com/ibm/icu/util/ULocale.CANADA_FRENCH:Lcom/ibm/icu/util/ULocale;
        //   323: new             Ljava/util/Locale;
        //   326: dup            
        //   327: ldc             ""
        //   329: ldc             ""
        //   331: invokespecial   java/util/Locale.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //   334: putstatic       com/ibm/icu/util/ULocale.EMPTY_LOCALE:Ljava/util/Locale;
        //   337: new             Lcom/ibm/icu/util/ULocale;
        //   340: dup            
        //   341: ldc             ""
        //   343: getstatic       com/ibm/icu/util/ULocale.EMPTY_LOCALE:Ljava/util/Locale;
        //   346: invokespecial   com/ibm/icu/util/ULocale.<init>:(Ljava/lang/String;Ljava/util/Locale;)V
        //   349: putstatic       com/ibm/icu/util/ULocale.ROOT:Lcom/ibm/icu/util/ULocale;
        //   352: new             Lcom/ibm/icu/impl/SimpleCache;
        //   355: dup            
        //   356: invokespecial   com/ibm/icu/impl/SimpleCache.<init>:()V
        //   359: putstatic       com/ibm/icu/util/ULocale.CACHE:Lcom/ibm/icu/impl/SimpleCache;
        //   362: new             Lcom/ibm/icu/impl/SimpleCache;
        //   365: dup            
        //   366: invokespecial   com/ibm/icu/impl/SimpleCache.<init>:()V
        //   369: putstatic       com/ibm/icu/util/ULocale.nameCache:Lcom/ibm/icu/impl/ICUCache;
        //   372: invokestatic    java/util/Locale.getDefault:()Ljava/util/Locale;
        //   375: putstatic       com/ibm/icu/util/ULocale.defaultLocale:Ljava/util/Locale;
        //   378: invokestatic    com/ibm/icu/util/ULocale$Category.values:()[Lcom/ibm/icu/util/ULocale$Category;
        //   381: arraylength    
        //   382: anewarray       Ljava/util/Locale;
        //   385: putstatic       com/ibm/icu/util/ULocale.defaultCategoryLocales:[Ljava/util/Locale;
        //   388: invokestatic    com/ibm/icu/util/ULocale$Category.values:()[Lcom/ibm/icu/util/ULocale$Category;
        //   391: arraylength    
        //   392: anewarray       Lcom/ibm/icu/util/ULocale;
        //   395: putstatic       com/ibm/icu/util/ULocale.defaultCategoryULocales:[Lcom/ibm/icu/util/ULocale;
        //   398: getstatic       com/ibm/icu/util/ULocale.defaultLocale:Ljava/util/Locale;
        //   401: invokestatic    com/ibm/icu/util/ULocale.forLocale:(Ljava/util/Locale;)Lcom/ibm/icu/util/ULocale;
        //   404: putstatic       com/ibm/icu/util/ULocale.defaultULocale:Lcom/ibm/icu/util/ULocale;
        //   407: invokestatic    com/ibm/icu/util/ULocale$JDKLocaleHelper.isJava7orNewer:()Z
        //   410: ifeq            469
        //   413: invokestatic    com/ibm/icu/util/ULocale$Category.values:()[Lcom/ibm/icu/util/ULocale$Category;
        //   416: astore_0       
        //   417: aload_0        
        //   418: arraylength    
        //   419: istore_1       
        //   420: iconst_0       
        //   421: iload_1        
        //   422: if_icmpge       466
        //   425: aload_0        
        //   426: iconst_0       
        //   427: aaload         
        //   428: astore_3       
        //   429: aload_3        
        //   430: invokevirtual   com/ibm/icu/util/ULocale$Category.ordinal:()I
        //   433: istore          4
        //   435: getstatic       com/ibm/icu/util/ULocale.defaultCategoryLocales:[Ljava/util/Locale;
        //   438: iload           4
        //   440: aload_3        
        //   441: invokestatic    com/ibm/icu/util/ULocale$JDKLocaleHelper.getDefault:(Lcom/ibm/icu/util/ULocale$Category;)Ljava/util/Locale;
        //   444: aastore        
        //   445: getstatic       com/ibm/icu/util/ULocale.defaultCategoryULocales:[Lcom/ibm/icu/util/ULocale;
        //   448: iload           4
        //   450: getstatic       com/ibm/icu/util/ULocale.defaultCategoryLocales:[Ljava/util/Locale;
        //   453: iload           4
        //   455: aaload         
        //   456: invokestatic    com/ibm/icu/util/ULocale.forLocale:(Ljava/util/Locale;)Lcom/ibm/icu/util/ULocale;
        //   459: aastore        
        //   460: iinc            2, 1
        //   463: goto            420
        //   466: goto            579
        //   469: getstatic       com/ibm/icu/util/ULocale.defaultLocale:Ljava/util/Locale;
        //   472: invokestatic    com/ibm/icu/util/ULocale$JDKLocaleHelper.isOriginalDefaultLocale:(Ljava/util/Locale;)Z
        //   475: ifeq            533
        //   478: ldc_w           "user.script"
        //   481: invokestatic    com/ibm/icu/util/ULocale$JDKLocaleHelper.getSystemProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   484: astore_0       
        //   485: aload_0        
        //   486: ifnull          533
        //   489: aload_0        
        //   490: invokestatic    com/ibm/icu/impl/locale/LanguageTag.isScript:(Ljava/lang/String;)Z
        //   493: ifeq            533
        //   496: getstatic       com/ibm/icu/util/ULocale.defaultULocale:Lcom/ibm/icu/util/ULocale;
        //   499: invokespecial   com/ibm/icu/util/ULocale.base:()Lcom/ibm/icu/impl/locale/BaseLocale;
        //   502: astore_1       
        //   503: aload_1        
        //   504: invokevirtual   com/ibm/icu/impl/locale/BaseLocale.getLanguage:()Ljava/lang/String;
        //   507: aload_0        
        //   508: aload_1        
        //   509: invokevirtual   com/ibm/icu/impl/locale/BaseLocale.getRegion:()Ljava/lang/String;
        //   512: aload_1        
        //   513: invokevirtual   com/ibm/icu/impl/locale/BaseLocale.getVariant:()Ljava/lang/String;
        //   516: invokestatic    com/ibm/icu/impl/locale/BaseLocale.getInstance:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/ibm/icu/impl/locale/BaseLocale;
        //   519: astore_2       
        //   520: aload_2        
        //   521: getstatic       com/ibm/icu/util/ULocale.defaultULocale:Lcom/ibm/icu/util/ULocale;
        //   524: invokespecial   com/ibm/icu/util/ULocale.extensions:()Lcom/ibm/icu/impl/locale/LocaleExtensions;
        //   527: invokestatic    com/ibm/icu/util/ULocale.getInstance:(Lcom/ibm/icu/impl/locale/BaseLocale;Lcom/ibm/icu/impl/locale/LocaleExtensions;)Lcom/ibm/icu/util/ULocale;
        //   530: putstatic       com/ibm/icu/util/ULocale.defaultULocale:Lcom/ibm/icu/util/ULocale;
        //   533: invokestatic    com/ibm/icu/util/ULocale$Category.values:()[Lcom/ibm/icu/util/ULocale$Category;
        //   536: astore_0       
        //   537: aload_0        
        //   538: arraylength    
        //   539: istore_1       
        //   540: iconst_0       
        //   541: iload_1        
        //   542: if_icmpge       579
        //   545: aload_0        
        //   546: iconst_0       
        //   547: aaload         
        //   548: astore_3       
        //   549: aload_3        
        //   550: invokevirtual   com/ibm/icu/util/ULocale$Category.ordinal:()I
        //   553: istore          4
        //   555: getstatic       com/ibm/icu/util/ULocale.defaultCategoryLocales:[Ljava/util/Locale;
        //   558: iload           4
        //   560: getstatic       com/ibm/icu/util/ULocale.defaultLocale:Ljava/util/Locale;
        //   563: aastore        
        //   564: getstatic       com/ibm/icu/util/ULocale.defaultCategoryULocales:[Lcom/ibm/icu/util/ULocale;
        //   567: iload           4
        //   569: getstatic       com/ibm/icu/util/ULocale.defaultULocale:Lcom/ibm/icu/util/ULocale;
        //   572: aastore        
        //   573: iinc            2, 1
        //   576: goto            540
        //   579: new             Lcom/ibm/icu/util/ULocale$Type;
        //   582: dup            
        //   583: aconst_null    
        //   584: invokespecial   com/ibm/icu/util/ULocale$Type.<init>:(Lcom/ibm/icu/util/ULocale$1;)V
        //   587: putstatic       com/ibm/icu/util/ULocale.ACTUAL_LOCALE:Lcom/ibm/icu/util/ULocale$Type;
        //   590: new             Lcom/ibm/icu/util/ULocale$Type;
        //   593: dup            
        //   594: aconst_null    
        //   595: invokespecial   com/ibm/icu/util/ULocale$Type.<init>:(Lcom/ibm/icu/util/ULocale$1;)V
        //   598: putstatic       com/ibm/icu/util/ULocale.VALID_LOCALE:Lcom/ibm/icu/util/ULocale$Type;
        //   601: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public enum Category
    {
        DISPLAY("DISPLAY", 0), 
        FORMAT("FORMAT", 1);
        
        private static final Category[] $VALUES;
        
        private Category(final String s, final int n) {
        }
        
        static {
            $VALUES = new Category[] { Category.DISPLAY, Category.FORMAT };
        }
    }
    
    private static final class JDKLocaleHelper
    {
        private static Method mGetScript;
        private static Method mGetExtensionKeys;
        private static Method mGetExtension;
        private static Method mGetUnicodeLocaleKeys;
        private static Method mGetUnicodeLocaleAttributes;
        private static Method mGetUnicodeLocaleType;
        private static Method mForLanguageTag;
        private static Method mGetDefault;
        private static Method mSetDefault;
        private static Object eDISPLAY;
        private static Object eFORMAT;
        private static final String[][] JAVA6_MAPDATA;
        
        public static boolean isJava7orNewer() {
            return JDKLocaleHelper.isJava7orNewer;
        }
        
        public static ULocale toULocale(final Locale locale) {
            return JDKLocaleHelper.isJava7orNewer ? toULocale7(locale) : toULocale6(locale);
        }
        
        public static Locale toLocale(final ULocale uLocale) {
            return JDKLocaleHelper.isJava7orNewer ? toLocale7(uLocale) : toLocale6(uLocale);
        }
        
        private static ULocale toULocale7(final Locale locale) {
            String language = locale.getLanguage();
            final String country = locale.getCountry();
            String variant = locale.getVariant();
            Set<String> set = null;
            Map<String, String> map = null;
            final String s = (String)JDKLocaleHelper.mGetScript.invoke(locale, (Object[])null);
            final Set set2 = (Set)JDKLocaleHelper.mGetExtensionKeys.invoke(locale, (Object[])null);
            if (!set2.isEmpty()) {
                for (final Character c : set2) {
                    if (c == 'u') {
                        final Set set3 = (Set)JDKLocaleHelper.mGetUnicodeLocaleAttributes.invoke(locale, (Object[])null);
                        if (!set3.isEmpty()) {
                            set = new TreeSet<String>();
                            final Iterator<String> iterator2 = set3.iterator();
                            while (iterator2.hasNext()) {
                                set.add(iterator2.next());
                            }
                        }
                        for (final String s2 : (Set)JDKLocaleHelper.mGetUnicodeLocaleKeys.invoke(locale, (Object[])null)) {
                            final String s3 = (String)JDKLocaleHelper.mGetUnicodeLocaleType.invoke(locale, s2);
                            if (s3 != null) {
                                if (s2.equals("va")) {
                                    variant = ((variant.length() == 0) ? s3 : (s3 + "_" + variant));
                                }
                                else {
                                    if (map == null) {
                                        map = new TreeMap<String, String>();
                                    }
                                    map.put(s2, s3);
                                }
                            }
                        }
                    }
                    else {
                        final String s4 = (String)JDKLocaleHelper.mGetExtension.invoke(locale, c);
                        if (s4 == null) {
                            continue;
                        }
                        if (map == null) {
                            map = new TreeMap<String, String>();
                        }
                        map.put(String.valueOf(c), s4);
                    }
                }
            }
            if (language.equals("no") && country.equals("NO") && variant.equals("NY")) {
                language = "nn";
                variant = "";
            }
            final StringBuilder sb = new StringBuilder(language);
            if (s.length() > 0) {
                sb.append('_');
                sb.append(s);
            }
            if (country.length() > 0) {
                sb.append('_');
                sb.append(country);
            }
            if (variant.length() > 0) {
                if (country.length() == 0) {
                    sb.append('_');
                }
                sb.append('_');
                sb.append(variant);
            }
            if (set != null) {
                final StringBuilder sb2 = new StringBuilder();
                for (final String s5 : set) {
                    if (sb2.length() != 0) {
                        sb2.append('-');
                    }
                    sb2.append(s5);
                }
                if (map == null) {
                    map = new TreeMap<String, String>();
                }
                map.put("attribute", sb2.toString());
            }
            if (map != null) {
                sb.append('@');
                for (final Map.Entry<String, String> entry : map.entrySet()) {
                    String access$400 = entry.getKey();
                    String access$401 = entry.getValue();
                    if (access$400.length() != 1) {
                        access$400 = ULocale.access$400(access$400);
                        access$401 = ULocale.access$500(access$400, (access$401.length() == 0) ? "yes" : access$401);
                    }
                    if (true) {
                        sb.append(';');
                    }
                    sb.append(access$400);
                    sb.append('=');
                    sb.append(access$401);
                }
            }
            return new ULocale(ULocale.getName(sb.toString()), locale, null);
        }
        
        private static ULocale toULocale6(final Locale locale) {
            String s = locale.toString();
            ULocale root;
            if (s.length() == 0) {
                root = ULocale.ROOT;
            }
            else {
                while (0 < JDKLocaleHelper.JAVA6_MAPDATA.length) {
                    if (JDKLocaleHelper.JAVA6_MAPDATA[0][0].equals(s)) {
                        final LocaleIDParser localeIDParser = new LocaleIDParser(JDKLocaleHelper.JAVA6_MAPDATA[0][1]);
                        localeIDParser.setKeywordValue(JDKLocaleHelper.JAVA6_MAPDATA[0][2], JDKLocaleHelper.JAVA6_MAPDATA[0][3]);
                        s = localeIDParser.getName();
                        break;
                    }
                    int n = 0;
                    ++n;
                }
                root = new ULocale(ULocale.getName(s), locale, null);
            }
            return root;
        }
        
        private static Locale toLocale7(final ULocale uLocale) {
            Locale locale = null;
            final String name = uLocale.getName();
            if (uLocale.getScript().length() > 0 || name.contains("@")) {
                locale = (Locale)JDKLocaleHelper.mForLanguageTag.invoke(null, AsciiUtil.toUpperString(uLocale.toLanguageTag()));
            }
            if (locale == null) {
                locale = new Locale(uLocale.getLanguage(), uLocale.getCountry(), uLocale.getVariant());
            }
            return locale;
        }
        
        private static Locale toLocale6(final ULocale uLocale) {
            String baseName = uLocale.getBaseName();
            while (0 < JDKLocaleHelper.JAVA6_MAPDATA.length) {
                if (baseName.equals(JDKLocaleHelper.JAVA6_MAPDATA[0][1]) || baseName.equals(JDKLocaleHelper.JAVA6_MAPDATA[0][4])) {
                    if (JDKLocaleHelper.JAVA6_MAPDATA[0][2] == null) {
                        baseName = JDKLocaleHelper.JAVA6_MAPDATA[0][0];
                        break;
                    }
                    final String keywordValue = uLocale.getKeywordValue(JDKLocaleHelper.JAVA6_MAPDATA[0][2]);
                    if (keywordValue != null && keywordValue.equals(JDKLocaleHelper.JAVA6_MAPDATA[0][3])) {
                        baseName = JDKLocaleHelper.JAVA6_MAPDATA[0][0];
                        break;
                    }
                }
                int n = 0;
                ++n;
            }
            final String[] languageScriptCountryVariant = new LocaleIDParser(baseName).getLanguageScriptCountryVariant();
            return new Locale(languageScriptCountryVariant[0], languageScriptCountryVariant[2], languageScriptCountryVariant[3]);
        }
        
        public static Locale getDefault(final Category category) {
            return Locale.getDefault();
        }
        
        public static void setDefault(final Category category, final Locale locale) {
        }
        
        public static boolean isOriginalDefaultLocale(final Locale locale) {
            if (JDKLocaleHelper.isJava7orNewer) {
                final String s = (String)JDKLocaleHelper.mGetScript.invoke(locale, (Object[])null);
                return locale.getLanguage().equals(getSystemProperty("user.language")) && locale.getCountry().equals(getSystemProperty("user.country")) && locale.getVariant().equals(getSystemProperty("user.variant")) && s.equals(getSystemProperty("user.script"));
            }
            return locale.getLanguage().equals(getSystemProperty("user.language")) && locale.getCountry().equals(getSystemProperty("user.country")) && locale.getVariant().equals(getSystemProperty("user.variant"));
        }
        
        public static String getSystemProperty(final String s) {
            String property;
            if (System.getSecurityManager() != null) {
                property = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction(s) {
                    final String val$fkey;
                    
                    public String run() {
                        return System.getProperty(this.val$fkey);
                    }
                    
                    public Object run() {
                        return this.run();
                    }
                });
            }
            else {
                property = System.getProperty(s);
            }
            return property;
        }
        
        static {
            JDKLocaleHelper.isJava7orNewer = false;
            JAVA6_MAPDATA = new String[][] { { "ja_JP_JP", "ja_JP", "calendar", "japanese", "ja" }, { "no_NO_NY", "nn_NO", null, null, "nn" }, { "th_TH_TH", "th_TH", "numbers", "thai", "th" } };
            JDKLocaleHelper.mGetScript = Locale.class.getMethod("getScript", (Class<?>[])null);
            JDKLocaleHelper.mGetExtensionKeys = Locale.class.getMethod("getExtensionKeys", (Class<?>[])null);
            JDKLocaleHelper.mGetExtension = Locale.class.getMethod("getExtension", Character.TYPE);
            JDKLocaleHelper.mGetUnicodeLocaleKeys = Locale.class.getMethod("getUnicodeLocaleKeys", (Class<?>[])null);
            JDKLocaleHelper.mGetUnicodeLocaleAttributes = Locale.class.getMethod("getUnicodeLocaleAttributes", (Class<?>[])null);
            JDKLocaleHelper.mGetUnicodeLocaleType = Locale.class.getMethod("getUnicodeLocaleType", String.class);
            JDKLocaleHelper.mForLanguageTag = Locale.class.getMethod("forLanguageTag", String.class);
            Class<Object> clazz = null;
            final Class<?>[] declaredClasses = Locale.class.getDeclaredClasses();
            while (0 < declaredClasses.length) {
                final Class<?> clazz2 = declaredClasses[0];
                if (clazz2.getName().equals("java.util.Locale$Category")) {
                    clazz = (Class<Object>)clazz2;
                    break;
                }
                int n = 0;
                ++n;
            }
            if (clazz != null) {
                JDKLocaleHelper.mGetDefault = Locale.class.getDeclaredMethod("getDefault", clazz);
                JDKLocaleHelper.mSetDefault = Locale.class.getDeclaredMethod("setDefault", clazz, Locale.class);
                final Method method = clazz.getMethod("name", (Class<?>[])null);
                final Object[] enumConstants = clazz.getEnumConstants();
                while (0 < enumConstants.length) {
                    final Object o = enumConstants[0];
                    final String s = (String)method.invoke(o, (Object[])null);
                    if (s.equals("DISPLAY")) {
                        JDKLocaleHelper.eDISPLAY = o;
                    }
                    else if (s.equals("FORMAT")) {
                        JDKLocaleHelper.eFORMAT = o;
                    }
                    int n2 = 0;
                    ++n2;
                }
                if (JDKLocaleHelper.eDISPLAY != null && JDKLocaleHelper.eFORMAT != null) {
                    JDKLocaleHelper.isJava7orNewer = true;
                }
            }
        }
    }
    
    public static final class Builder
    {
        private final InternalLocaleBuilder _locbld;
        
        public Builder() {
            this._locbld = new InternalLocaleBuilder();
        }
        
        public Builder setLocale(final ULocale uLocale) {
            this._locbld.setLocale(ULocale.access$100(uLocale), ULocale.access$200(uLocale));
            return this;
        }
        
        public Builder setLanguageTag(final String s) {
            final ParseStatus parseStatus = new ParseStatus();
            final LanguageTag parse = LanguageTag.parse(s, parseStatus);
            if (parseStatus.isError()) {
                throw new IllformedLocaleException(parseStatus.getErrorMessage(), parseStatus.getErrorIndex());
            }
            this._locbld.setLanguageTag(parse);
            return this;
        }
        
        public Builder setLanguage(final String language) {
            this._locbld.setLanguage(language);
            return this;
        }
        
        public Builder setScript(final String script) {
            this._locbld.setScript(script);
            return this;
        }
        
        public Builder setRegion(final String region) {
            this._locbld.setRegion(region);
            return this;
        }
        
        public Builder setVariant(final String variant) {
            this._locbld.setVariant(variant);
            return this;
        }
        
        public Builder setExtension(final char c, final String s) {
            this._locbld.setExtension(c, s);
            return this;
        }
        
        public Builder setUnicodeLocaleKeyword(final String s, final String s2) {
            this._locbld.setUnicodeLocaleKeyword(s, s2);
            return this;
        }
        
        public Builder addUnicodeLocaleAttribute(final String s) {
            this._locbld.addUnicodeLocaleAttribute(s);
            return this;
        }
        
        public Builder removeUnicodeLocaleAttribute(final String s) {
            this._locbld.removeUnicodeLocaleAttribute(s);
            return this;
        }
        
        public Builder clear() {
            this._locbld.clear();
            return this;
        }
        
        public Builder clearExtensions() {
            this._locbld.clearExtensions();
            return this;
        }
        
        public ULocale build() {
            return ULocale.access$300(this._locbld.getBaseLocale(), this._locbld.getLocaleExtensions());
        }
    }
    
    public static final class Type
    {
        private Type() {
        }
        
        Type(final ULocale$1 object) {
            this();
        }
    }
    
    class ULocaleAcceptLanguageQ implements Comparable
    {
        private double q;
        private double serial;
        
        public ULocaleAcceptLanguageQ(final double q, final int n) {
            this.q = q;
            this.serial = n;
        }
        
        public int compareTo(final ULocaleAcceptLanguageQ uLocaleAcceptLanguageQ) {
            if (this.q > uLocaleAcceptLanguageQ.q) {
                return -1;
            }
            if (this.q < uLocaleAcceptLanguageQ.q) {
                return 1;
            }
            if (this.serial < uLocaleAcceptLanguageQ.serial) {
                return -1;
            }
            if (this.serial > uLocaleAcceptLanguageQ.serial) {
                return 1;
            }
            return 0;
        }
        
        public int compareTo(final Object o) {
            return this.compareTo((ULocaleAcceptLanguageQ)o);
        }
    }
}
