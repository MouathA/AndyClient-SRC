package com.ibm.icu.util;

import java.io.*;
import java.lang.ref.*;
import com.ibm.icu.text.*;
import java.text.*;
import java.util.*;
import com.ibm.icu.impl.*;

public class Currency extends MeasureUnit implements Serializable
{
    private static final long serialVersionUID = -5839973855554750484L;
    private static final boolean DEBUG;
    private static ICUCache CURRENCY_NAME_CACHE;
    private String isoCode;
    public static final int SYMBOL_NAME = 0;
    public static final int LONG_NAME = 1;
    public static final int PLURAL_LONG_NAME = 2;
    private static ServiceShim shim;
    private static final String EUR_STR = "EUR";
    private static final ICUCache currencyCodeCache;
    private static final ULocale UND;
    private static final String[] EMPTY_STRING_ARRAY;
    private static final int[] POW10;
    private static SoftReference ALL_TENDER_CODES;
    private static SoftReference ALL_CODES_AS_SET;
    
    private static ServiceShim getShim() {
        if (Currency.shim == null) {
            try {
                Currency.shim = (ServiceShim)Class.forName("com.ibm.icu.util.CurrencyServiceShim").newInstance();
            }
            catch (Exception ex) {
                if (Currency.DEBUG) {
                    ex.printStackTrace();
                }
                throw new RuntimeException(ex.getMessage());
            }
        }
        return Currency.shim;
    }
    
    public static Currency getInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }
    
    public static Currency getInstance(final ULocale uLocale) {
        final String keywordValue = uLocale.getKeywordValue("currency");
        if (keywordValue != null) {
            return getInstance(keywordValue);
        }
        if (Currency.shim == null) {
            return createCurrency(uLocale);
        }
        return Currency.shim.createInstance(uLocale);
    }
    
    public static String[] getAvailableCurrencyCodes(final ULocale uLocale, final Date date) {
        final List tenderCurrencies = getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter.onDate(date).withRegion(uLocale.getCountry()));
        if (tenderCurrencies.isEmpty()) {
            return null;
        }
        return tenderCurrencies.toArray(new String[tenderCurrencies.size()]);
    }
    
    public static Set getAvailableCurrencies() {
        final List currencies = CurrencyMetaInfo.getInstance().currencies(CurrencyMetaInfo.CurrencyFilter.all());
        final HashSet set = new HashSet<Currency>(currencies.size());
        final Iterator<String> iterator = currencies.iterator();
        while (iterator.hasNext()) {
            set.add(new Currency(iterator.next()));
        }
        return set;
    }
    
    static Currency createCurrency(final ULocale uLocale) {
        final String variant = uLocale.getVariant();
        if ("EURO".equals(variant)) {
            return new Currency("EUR");
        }
        String s = (String)Currency.currencyCodeCache.get(uLocale);
        if (s == null) {
            final List currencies = CurrencyMetaInfo.getInstance().currencies(CurrencyMetaInfo.CurrencyFilter.onRegion(uLocale.getCountry()));
            if (currencies.size() <= 0) {
                return null;
            }
            s = currencies.get(0);
            if ("PREEURO".equals(variant) && "EUR".equals(s)) {
                if (currencies.size() < 2) {
                    return null;
                }
                s = currencies.get(1);
            }
            Currency.currencyCodeCache.put(uLocale, s);
        }
        return new Currency(s);
    }
    
    public static Currency getInstance(final String s) {
        if (s == null) {
            throw new NullPointerException("The input currency code is null.");
        }
        if (!isAlpha3Code(s)) {
            throw new IllegalArgumentException("The input currency code is not 3-letter alphabetic code.");
        }
        return new Currency(s.toUpperCase(Locale.ENGLISH));
    }
    
    private static boolean isAlpha3Code(final String s) {
        if (s.length() != 3) {
            return false;
        }
        for (int i = 0; i < 3; ++i) {
            final char char1 = s.charAt(i);
            if (char1 < 'A' || (char1 > 'Z' && char1 < 'a') || char1 > 'z') {
                return false;
            }
        }
        return true;
    }
    
    public static Object registerInstance(final Currency currency, final ULocale uLocale) {
        return getShim().registerInstance(currency, uLocale);
    }
    
    public static boolean unregister(final Object o) {
        if (o == null) {
            throw new IllegalArgumentException("registryKey must not be null");
        }
        return Currency.shim != null && Currency.shim.unregister(o);
    }
    
    public static Locale[] getAvailableLocales() {
        if (Currency.shim == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return Currency.shim.getAvailableLocales();
    }
    
    public static ULocale[] getAvailableULocales() {
        if (Currency.shim == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return Currency.shim.getAvailableULocales();
    }
    
    public static final String[] getKeywordValuesForLocale(final String s, final ULocale uLocale, final boolean b) {
        if (!"currency".equals(s)) {
            return Currency.EMPTY_STRING_ARRAY;
        }
        if (!b) {
            return getAllTenderCurrencies().toArray(new String[0]);
        }
        String s2 = uLocale.getCountry();
        if (s2.length() == 0) {
            if (Currency.UND.equals(uLocale)) {
                return Currency.EMPTY_STRING_ARRAY;
            }
            s2 = ULocale.addLikelySubtags(uLocale).getCountry();
        }
        final List tenderCurrencies = getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter.now().withRegion(s2));
        if (tenderCurrencies.size() == 0) {
            return Currency.EMPTY_STRING_ARRAY;
        }
        return tenderCurrencies.toArray(new String[tenderCurrencies.size()]);
    }
    
    @Override
    public int hashCode() {
        return this.isoCode.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        try {
            return this.isoCode.equals(((Currency)o).isoCode);
        }
        catch (ClassCastException ex) {
            return false;
        }
    }
    
    public String getCurrencyCode() {
        return this.isoCode;
    }
    
    public int getNumericCode() {
        int int1 = 0;
        try {
            int1 = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "currencyNumericCodes", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("codeMap").get(this.isoCode).getInt();
        }
        catch (MissingResourceException ex) {}
        return int1;
    }
    
    public String getSymbol() {
        return this.getSymbol(ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public String getSymbol(final Locale locale) {
        return this.getSymbol(ULocale.forLocale(locale));
    }
    
    public String getSymbol(final ULocale uLocale) {
        return this.getName(uLocale, 0, new boolean[1]);
    }
    
    public String getName(final Locale locale, final int n, final boolean[] array) {
        return this.getName(ULocale.forLocale(locale), n, array);
    }
    
    public String getName(final ULocale uLocale, final int n, final boolean[] array) {
        if (n != 0 && n != 1) {
            throw new IllegalArgumentException("bad name style: " + n);
        }
        if (array != null) {
            array[0] = false;
        }
        final CurrencyDisplayNames instance = CurrencyDisplayNames.getInstance(uLocale);
        return (n == 0) ? instance.getSymbol(this.isoCode) : instance.getName(this.isoCode);
    }
    
    public String getName(final Locale locale, final int n, final String s, final boolean[] array) {
        return this.getName(ULocale.forLocale(locale), n, s, array);
    }
    
    public String getName(final ULocale uLocale, final int n, final String s, final boolean[] array) {
        if (n != 2) {
            return this.getName(uLocale, n, array);
        }
        if (array != null) {
            array[0] = false;
        }
        return CurrencyDisplayNames.getInstance(uLocale).getPluralName(this.isoCode, s);
    }
    
    public String getDisplayName() {
        return this.getName(Locale.getDefault(), 1, null);
    }
    
    public String getDisplayName(final Locale locale) {
        return this.getName(locale, 1, null);
    }
    
    @Deprecated
    public static String parse(final ULocale uLocale, final String s, final int n, final ParsePosition parsePosition) {
        List<TextTrieMap> list = (List<TextTrieMap>)Currency.CURRENCY_NAME_CACHE.get(uLocale);
        if (list == null) {
            final TextTrieMap textTrieMap = new TextTrieMap(true);
            final TextTrieMap textTrieMap2 = new TextTrieMap(false);
            list = new ArrayList<TextTrieMap>();
            list.add(textTrieMap2);
            list.add(textTrieMap);
            setupCurrencyTrieVec(uLocale, list);
            Currency.CURRENCY_NAME_CACHE.put(uLocale, list);
        }
        int n2 = 0;
        String s2 = null;
        final TextTrieMap textTrieMap3 = list.get(1);
        final CurrencyNameResultHandler currencyNameResultHandler = new CurrencyNameResultHandler(null);
        textTrieMap3.find(s, parsePosition.getIndex(), currencyNameResultHandler);
        final List matchedCurrencyNames = currencyNameResultHandler.getMatchedCurrencyNames();
        if (matchedCurrencyNames != null && matchedCurrencyNames.size() != 0) {
            for (final CurrencyStringInfo currencyStringInfo : matchedCurrencyNames) {
                final String access$100 = CurrencyStringInfo.access$100(currencyStringInfo);
                final String access$101 = CurrencyStringInfo.access$200(currencyStringInfo);
                if (access$101.length() > n2) {
                    n2 = access$101.length();
                    s2 = access$100;
                }
            }
        }
        if (n != 1) {
            final TextTrieMap textTrieMap4 = list.get(0);
            final CurrencyNameResultHandler currencyNameResultHandler2 = new CurrencyNameResultHandler(null);
            textTrieMap4.find(s, parsePosition.getIndex(), currencyNameResultHandler2);
            final List matchedCurrencyNames2 = currencyNameResultHandler2.getMatchedCurrencyNames();
            if (matchedCurrencyNames2 != null && matchedCurrencyNames2.size() != 0) {
                for (final CurrencyStringInfo currencyStringInfo2 : matchedCurrencyNames2) {
                    final String access$102 = CurrencyStringInfo.access$100(currencyStringInfo2);
                    final String access$103 = CurrencyStringInfo.access$200(currencyStringInfo2);
                    if (access$103.length() > n2) {
                        n2 = access$103.length();
                        s2 = access$102;
                    }
                }
            }
        }
        parsePosition.setIndex(parsePosition.getIndex() + n2);
        return s2;
    }
    
    private static void setupCurrencyTrieVec(final ULocale uLocale, final List list) {
        final TextTrieMap textTrieMap = list.get(0);
        final TextTrieMap textTrieMap2 = list.get(1);
        final CurrencyDisplayNames instance = CurrencyDisplayNames.getInstance(uLocale);
        for (final Map.Entry<String, V> entry : instance.symbolMap().entrySet()) {
            final String s = entry.getKey();
            textTrieMap.put(s, new CurrencyStringInfo((String)entry.getValue(), s));
        }
        for (final Map.Entry<String, V> entry2 : instance.nameMap().entrySet()) {
            final String s2 = entry2.getKey();
            textTrieMap2.put(s2, new CurrencyStringInfo((String)entry2.getValue(), s2));
        }
    }
    
    public int getDefaultFractionDigits() {
        return CurrencyMetaInfo.getInstance().currencyDigits(this.isoCode).fractionDigits;
    }
    
    public double getRoundingIncrement() {
        final CurrencyMetaInfo.CurrencyDigits currencyDigits = CurrencyMetaInfo.getInstance().currencyDigits(this.isoCode);
        final int roundingIncrement = currencyDigits.roundingIncrement;
        if (roundingIncrement == 0) {
            return 0.0;
        }
        final int fractionDigits = currencyDigits.fractionDigits;
        if (fractionDigits < 0 || fractionDigits >= Currency.POW10.length) {
            return 0.0;
        }
        return roundingIncrement / (double)Currency.POW10[fractionDigits];
    }
    
    @Override
    public String toString() {
        return this.isoCode;
    }
    
    protected Currency(final String isoCode) {
        this.isoCode = isoCode;
    }
    
    private static synchronized List getAllTenderCurrencies() {
        List<Object> unmodifiableList = (Currency.ALL_TENDER_CODES == null) ? null : Currency.ALL_TENDER_CODES.get();
        if (unmodifiableList == null) {
            unmodifiableList = Collections.unmodifiableList((List<?>)getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter.all()));
            Currency.ALL_TENDER_CODES = new SoftReference(unmodifiableList);
        }
        return unmodifiableList;
    }
    
    private static synchronized Set getAllCurrenciesAsSet() {
        Set<Object> unmodifiableSet = (Currency.ALL_CODES_AS_SET == null) ? null : Currency.ALL_CODES_AS_SET.get();
        if (unmodifiableSet == null) {
            unmodifiableSet = Collections.unmodifiableSet((Set<?>)new HashSet<Object>(CurrencyMetaInfo.getInstance().currencies(CurrencyMetaInfo.CurrencyFilter.all())));
            Currency.ALL_CODES_AS_SET = new SoftReference(unmodifiableSet);
        }
        return unmodifiableSet;
    }
    
    public static boolean isAvailable(String upperCase, final Date date, final Date date2) {
        if (!isAlpha3Code(upperCase)) {
            return false;
        }
        if (date != null && date2 != null && date.after(date2)) {
            throw new IllegalArgumentException("To is before from");
        }
        upperCase = upperCase.toUpperCase(Locale.ENGLISH);
        return getAllCurrenciesAsSet().contains(upperCase) && ((date == null && date2 == null) || CurrencyMetaInfo.getInstance().currencies(CurrencyMetaInfo.CurrencyFilter.onDateRange(date, date2).withCurrency(upperCase)).contains(upperCase));
    }
    
    private static List getTenderCurrencies(final CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        return CurrencyMetaInfo.getInstance().currencies(currencyFilter.withTender());
    }
    
    static {
        DEBUG = ICUDebug.enabled("currency");
        Currency.CURRENCY_NAME_CACHE = new SimpleCache();
        currencyCodeCache = new SimpleCache();
        UND = new ULocale("und");
        EMPTY_STRING_ARRAY = new String[0];
        POW10 = new int[] { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000 };
    }
    
    private static class CurrencyNameResultHandler implements TextTrieMap.ResultHandler
    {
        private ArrayList resultList;
        
        private CurrencyNameResultHandler() {
        }
        
        public boolean handlePrefixMatch(final int n, final Iterator iterator) {
            if (this.resultList == null) {
                this.resultList = new ArrayList();
            }
            while (iterator.hasNext()) {
                final CurrencyStringInfo currencyStringInfo = iterator.next();
                if (currencyStringInfo == null) {
                    break;
                }
                while (0 < this.resultList.size()) {
                    final CurrencyStringInfo currencyStringInfo2 = this.resultList.get(0);
                    if (CurrencyStringInfo.access$100(currencyStringInfo).equals(CurrencyStringInfo.access$100(currencyStringInfo2))) {
                        if (n > CurrencyStringInfo.access$200(currencyStringInfo2).length()) {
                            this.resultList.set(0, currencyStringInfo);
                            break;
                        }
                        break;
                    }
                    else {
                        int n2 = 0;
                        ++n2;
                    }
                }
                if (0 != this.resultList.size()) {
                    continue;
                }
                this.resultList.add(currencyStringInfo);
            }
            return true;
        }
        
        List getMatchedCurrencyNames() {
            if (this.resultList == null || this.resultList.size() == 0) {
                return null;
            }
            return this.resultList;
        }
        
        CurrencyNameResultHandler(final Currency$1 object) {
            this();
        }
    }
    
    private static final class CurrencyStringInfo
    {
        private String isoCode;
        private String currencyString;
        
        public CurrencyStringInfo(final String isoCode, final String currencyString) {
            this.isoCode = isoCode;
            this.currencyString = currencyString;
        }
        
        private String getISOCode() {
            return this.isoCode;
        }
        
        private String getCurrencyString() {
            return this.currencyString;
        }
        
        static String access$100(final CurrencyStringInfo currencyStringInfo) {
            return currencyStringInfo.getISOCode();
        }
        
        static String access$200(final CurrencyStringInfo currencyStringInfo) {
            return currencyStringInfo.getCurrencyString();
        }
    }
    
    abstract static class ServiceShim
    {
        abstract ULocale[] getAvailableULocales();
        
        abstract Locale[] getAvailableLocales();
        
        abstract Currency createInstance(final ULocale p0);
        
        abstract Object registerInstance(final Currency p0, final ULocale p1);
        
        abstract boolean unregister(final Object p0);
    }
}
