package com.ibm.icu.util;

import com.ibm.icu.impl.*;
import com.ibm.icu.text.*;

public final class LocaleData
{
    private static final String MEASUREMENT_SYSTEM = "MeasurementSystem";
    private static final String PAPER_SIZE = "PaperSize";
    private static final String LOCALE_DISPLAY_PATTERN = "localeDisplayPattern";
    private static final String PATTERN = "pattern";
    private static final String SEPARATOR = "separator";
    private boolean noSubstitute;
    private ICUResourceBundle bundle;
    private ICUResourceBundle langBundle;
    public static final int ES_STANDARD = 0;
    public static final int ES_AUXILIARY = 1;
    public static final int ES_INDEX = 2;
    @Deprecated
    public static final int ES_CURRENCY = 3;
    public static final int ES_PUNCTUATION = 4;
    public static final int ES_COUNT = 5;
    public static final int QUOTATION_START = 0;
    public static final int QUOTATION_END = 1;
    public static final int ALT_QUOTATION_START = 2;
    public static final int ALT_QUOTATION_END = 3;
    public static final int DELIMITER_COUNT = 4;
    private static VersionInfo gCLDRVersion;
    
    private LocaleData() {
    }
    
    public static UnicodeSet getExemplarSet(final ULocale uLocale, final int n) {
        return getInstance(uLocale).getExemplarSet(n, 0);
    }
    
    public static UnicodeSet getExemplarSet(final ULocale uLocale, final int n, final int n2) {
        return getInstance(uLocale).getExemplarSet(n, n2);
    }
    
    public UnicodeSet getExemplarSet(final int n, final int n2) {
        final String[] array = { "ExemplarCharacters", "AuxExemplarCharacters", "ExemplarCharactersIndex", "ExemplarCharactersCurrency", "ExemplarCharactersPunctuation" };
        if (n2 == 3) {
            return new UnicodeSet();
        }
        final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)this.bundle.get(array[n2]);
        if (this.noSubstitute && icuResourceBundle.getLoadingStatus() == 2) {
            return null;
        }
        final String string = icuResourceBundle.getString();
        if (n2 == 4) {
            return new UnicodeSet(string, 0x1 | n);
        }
        return new UnicodeSet(string, 0x1 | n);
    }
    
    public static final LocaleData getInstance(final ULocale uLocale) {
        final LocaleData localeData = new LocaleData();
        localeData.bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", uLocale);
        localeData.langBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b/lang", uLocale);
        localeData.noSubstitute = false;
        return localeData;
    }
    
    public static final LocaleData getInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public void setNoSubstitute(final boolean noSubstitute) {
        this.noSubstitute = noSubstitute;
    }
    
    public boolean getNoSubstitute() {
        return this.noSubstitute;
    }
    
    public String getDelimiter(final int n) {
        final ICUResourceBundle withFallback = ((ICUResourceBundle)this.bundle.get("delimiters")).getWithFallback(LocaleData.DELIMITER_TYPES[n]);
        if (this.noSubstitute && withFallback.getLoadingStatus() == 2) {
            return null;
        }
        return withFallback.getString();
    }
    
    public static final MeasurementSystem getMeasurementSystem(final ULocale uLocale) {
        final int int1 = ((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", uLocale)).get("MeasurementSystem").getInt();
        if (MeasurementSystem.access$000(MeasurementSystem.US, int1)) {
            return MeasurementSystem.US;
        }
        if (MeasurementSystem.access$000(MeasurementSystem.SI, int1)) {
            return MeasurementSystem.SI;
        }
        return null;
    }
    
    public static final PaperSize getPaperSize(final ULocale uLocale) {
        final int[] intVector = ((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", uLocale)).get("PaperSize").getIntVector();
        return new PaperSize(intVector[0], intVector[1], null);
    }
    
    public String getLocaleDisplayPattern() {
        return ((ICUResourceBundle)this.langBundle.get("localeDisplayPattern")).getStringWithFallback("pattern");
    }
    
    public String getLocaleSeparator() {
        return ((ICUResourceBundle)this.langBundle.get("localeDisplayPattern")).getStringWithFallback("separator");
    }
    
    public static VersionInfo getCLDRVersion() {
        if (LocaleData.gCLDRVersion == null) {
            LocaleData.gCLDRVersion = VersionInfo.getInstance(UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("cldrVersion").getString());
        }
        return LocaleData.gCLDRVersion;
    }
    
    static {
        LocaleData.DELIMITER_TYPES = new String[] { "quotationStart", "quotationEnd", "alternateQuotationStart", "alternateQuotationEnd" };
        LocaleData.gCLDRVersion = null;
    }
    
    public static final class PaperSize
    {
        private int height;
        private int width;
        
        private PaperSize(final int height, final int width) {
            this.height = height;
            this.width = width;
        }
        
        public int getHeight() {
            return this.height;
        }
        
        public int getWidth() {
            return this.width;
        }
        
        PaperSize(final int n, final int n2, final LocaleData$1 object) {
            this(n, n2);
        }
    }
    
    public static final class MeasurementSystem
    {
        public static final MeasurementSystem SI;
        public static final MeasurementSystem US;
        private int systemID;
        
        private MeasurementSystem(final int systemID) {
            this.systemID = systemID;
        }
        
        private boolean equals(final int n) {
            return this.systemID == n;
        }
        
        static boolean access$000(final MeasurementSystem measurementSystem, final int n) {
            return measurementSystem.equals(n);
        }
        
        static {
            SI = new MeasurementSystem(0);
            US = new MeasurementSystem(1);
        }
    }
}
