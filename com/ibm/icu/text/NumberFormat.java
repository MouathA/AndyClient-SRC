package com.ibm.icu.text;

import java.math.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;
import java.io.*;
import java.text.*;
import java.util.*;

public abstract class NumberFormat extends UFormat
{
    public static final int NUMBERSTYLE = 0;
    public static final int CURRENCYSTYLE = 1;
    public static final int PERCENTSTYLE = 2;
    public static final int SCIENTIFICSTYLE = 3;
    public static final int INTEGERSTYLE = 4;
    public static final int ISOCURRENCYSTYLE = 5;
    public static final int PLURALCURRENCYSTYLE = 6;
    public static final int INTEGER_FIELD = 0;
    public static final int FRACTION_FIELD = 1;
    private static NumberFormatShim shim;
    private static final char[] doubleCurrencySign;
    private static final String doubleCurrencyStr;
    private boolean groupingUsed;
    private byte maxIntegerDigits;
    private byte minIntegerDigits;
    private byte maxFractionDigits;
    private byte minFractionDigits;
    private boolean parseIntegerOnly;
    private int maximumIntegerDigits;
    private int minimumIntegerDigits;
    private int maximumFractionDigits;
    private int minimumFractionDigits;
    private Currency currency;
    static final int currentSerialVersion = 1;
    private int serialVersionOnStream;
    private static final long serialVersionUID = -2308460125733713944L;
    private boolean parseStrict;
    
    @Override
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (o instanceof Long) {
            return this.format((long)o, sb, fieldPosition);
        }
        if (o instanceof BigInteger) {
            return this.format((BigInteger)o, sb, fieldPosition);
        }
        if (o instanceof BigDecimal) {
            return this.format((BigDecimal)o, sb, fieldPosition);
        }
        if (o instanceof com.ibm.icu.math.BigDecimal) {
            return this.format((com.ibm.icu.math.BigDecimal)o, sb, fieldPosition);
        }
        if (o instanceof CurrencyAmount) {
            return this.format((CurrencyAmount)o, sb, fieldPosition);
        }
        if (o instanceof Number) {
            return this.format(((Number)o).doubleValue(), sb, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object as a Number");
    }
    
    @Override
    public final Object parseObject(final String s, final ParsePosition parsePosition) {
        return this.parse(s, parsePosition);
    }
    
    public final String format(final double n) {
        return this.format(n, new StringBuffer(), new FieldPosition(0)).toString();
    }
    
    public final String format(final long n) {
        final StringBuffer sb = new StringBuffer(19);
        this.format(n, sb, new FieldPosition(0));
        return sb.toString();
    }
    
    public final String format(final BigInteger bigInteger) {
        return this.format(bigInteger, new StringBuffer(), new FieldPosition(0)).toString();
    }
    
    public final String format(final BigDecimal bigDecimal) {
        return this.format(bigDecimal, new StringBuffer(), new FieldPosition(0)).toString();
    }
    
    public final String format(final com.ibm.icu.math.BigDecimal bigDecimal) {
        return this.format(bigDecimal, new StringBuffer(), new FieldPosition(0)).toString();
    }
    
    public final String format(final CurrencyAmount currencyAmount) {
        return this.format(currencyAmount, new StringBuffer(), new FieldPosition(0)).toString();
    }
    
    public abstract StringBuffer format(final double p0, final StringBuffer p1, final FieldPosition p2);
    
    public abstract StringBuffer format(final long p0, final StringBuffer p1, final FieldPosition p2);
    
    public abstract StringBuffer format(final BigInteger p0, final StringBuffer p1, final FieldPosition p2);
    
    public abstract StringBuffer format(final BigDecimal p0, final StringBuffer p1, final FieldPosition p2);
    
    public abstract StringBuffer format(final com.ibm.icu.math.BigDecimal p0, final StringBuffer p1, final FieldPosition p2);
    
    public StringBuffer format(final CurrencyAmount currencyAmount, final StringBuffer sb, final FieldPosition fieldPosition) {
        final Currency currency = this.getCurrency();
        final Currency currency2 = currencyAmount.getCurrency();
        final boolean equals = currency2.equals(currency);
        if (!equals) {
            this.setCurrency(currency2);
        }
        this.format(currencyAmount.getNumber(), sb, fieldPosition);
        if (!equals) {
            this.setCurrency(currency);
        }
        return sb;
    }
    
    public abstract Number parse(final String p0, final ParsePosition p1);
    
    public Number parse(final String s) throws ParseException {
        final ParsePosition parsePosition = new ParsePosition(0);
        final Number parse = this.parse(s, parsePosition);
        if (parsePosition.getIndex() == 0) {
            throw new ParseException("Unparseable number: \"" + s + '\"', parsePosition.getErrorIndex());
        }
        return parse;
    }
    
    public CurrencyAmount parseCurrency(final CharSequence charSequence, final ParsePosition parsePosition) {
        final Number parse = this.parse(charSequence.toString(), parsePosition);
        return (parse == null) ? null : new CurrencyAmount(parse, this.getEffectiveCurrency());
    }
    
    public boolean isParseIntegerOnly() {
        return this.parseIntegerOnly;
    }
    
    public void setParseIntegerOnly(final boolean parseIntegerOnly) {
        this.parseIntegerOnly = parseIntegerOnly;
    }
    
    public void setParseStrict(final boolean parseStrict) {
        this.parseStrict = parseStrict;
    }
    
    public boolean isParseStrict() {
        return this.parseStrict;
    }
    
    public static final NumberFormat getInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 0);
    }
    
    public static NumberFormat getInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale), 0);
    }
    
    public static NumberFormat getInstance(final ULocale uLocale) {
        return getInstance(uLocale, 0);
    }
    
    public static final NumberFormat getInstance(final int n) {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), n);
    }
    
    public static NumberFormat getInstance(final Locale locale, final int n) {
        return getInstance(ULocale.forLocale(locale), n);
    }
    
    public static final NumberFormat getNumberInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 0);
    }
    
    public static NumberFormat getNumberInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale), 0);
    }
    
    public static NumberFormat getNumberInstance(final ULocale uLocale) {
        return getInstance(uLocale, 0);
    }
    
    public static final NumberFormat getIntegerInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 4);
    }
    
    public static NumberFormat getIntegerInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale), 4);
    }
    
    public static NumberFormat getIntegerInstance(final ULocale uLocale) {
        return getInstance(uLocale, 4);
    }
    
    public static final NumberFormat getCurrencyInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 1);
    }
    
    public static NumberFormat getCurrencyInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale), 1);
    }
    
    public static NumberFormat getCurrencyInstance(final ULocale uLocale) {
        return getInstance(uLocale, 1);
    }
    
    public static final NumberFormat getPercentInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 2);
    }
    
    public static NumberFormat getPercentInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale), 2);
    }
    
    public static NumberFormat getPercentInstance(final ULocale uLocale) {
        return getInstance(uLocale, 2);
    }
    
    public static final NumberFormat getScientificInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 3);
    }
    
    public static NumberFormat getScientificInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale), 3);
    }
    
    public static NumberFormat getScientificInstance(final ULocale uLocale) {
        return getInstance(uLocale, 3);
    }
    
    private static NumberFormatShim getShim() {
        if (NumberFormat.shim == null) {
            NumberFormat.shim = (NumberFormatShim)Class.forName("com.ibm.icu.text.NumberFormatServiceShim").newInstance();
        }
        return NumberFormat.shim;
    }
    
    public static Locale[] getAvailableLocales() {
        if (NumberFormat.shim == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return getShim().getAvailableLocales();
    }
    
    public static ULocale[] getAvailableULocales() {
        if (NumberFormat.shim == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return getShim().getAvailableULocales();
    }
    
    public static Object registerFactory(final NumberFormatFactory numberFormatFactory) {
        if (numberFormatFactory == null) {
            throw new IllegalArgumentException("factory must not be null");
        }
        return getShim().registerFactory(numberFormatFactory);
    }
    
    public static boolean unregister(final Object o) {
        if (o == null) {
            throw new IllegalArgumentException("registryKey must not be null");
        }
        return NumberFormat.shim != null && NumberFormat.shim.unregister(o);
    }
    
    @Override
    public int hashCode() {
        return this.maximumIntegerDigits * 37 + this.maxFractionDigits;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final NumberFormat numberFormat = (NumberFormat)o;
        return this.maximumIntegerDigits == numberFormat.maximumIntegerDigits && this.minimumIntegerDigits == numberFormat.minimumIntegerDigits && this.maximumFractionDigits == numberFormat.maximumFractionDigits && this.minimumFractionDigits == numberFormat.minimumFractionDigits && this.groupingUsed == numberFormat.groupingUsed && this.parseIntegerOnly == numberFormat.parseIntegerOnly && this.parseStrict == numberFormat.parseStrict;
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    public boolean isGroupingUsed() {
        return this.groupingUsed;
    }
    
    public void setGroupingUsed(final boolean groupingUsed) {
        this.groupingUsed = groupingUsed;
    }
    
    public int getMaximumIntegerDigits() {
        return this.maximumIntegerDigits;
    }
    
    public void setMaximumIntegerDigits(final int n) {
        this.maximumIntegerDigits = Math.max(0, n);
        if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
            this.minimumIntegerDigits = this.maximumIntegerDigits;
        }
    }
    
    public int getMinimumIntegerDigits() {
        return this.minimumIntegerDigits;
    }
    
    public void setMinimumIntegerDigits(final int n) {
        this.minimumIntegerDigits = Math.max(0, n);
        if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
            this.maximumIntegerDigits = this.minimumIntegerDigits;
        }
    }
    
    public int getMaximumFractionDigits() {
        return this.maximumFractionDigits;
    }
    
    public void setMaximumFractionDigits(final int n) {
        this.maximumFractionDigits = Math.max(0, n);
        if (this.maximumFractionDigits < this.minimumFractionDigits) {
            this.minimumFractionDigits = this.maximumFractionDigits;
        }
    }
    
    public int getMinimumFractionDigits() {
        return this.minimumFractionDigits;
    }
    
    public void setMinimumFractionDigits(final int n) {
        this.minimumFractionDigits = Math.max(0, n);
        if (this.maximumFractionDigits < this.minimumFractionDigits) {
            this.maximumFractionDigits = this.minimumFractionDigits;
        }
    }
    
    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }
    
    public Currency getCurrency() {
        return this.currency;
    }
    
    @Deprecated
    protected Currency getEffectiveCurrency() {
        Currency currency = this.getCurrency();
        if (currency == null) {
            ULocale uLocale = this.getLocale(ULocale.VALID_LOCALE);
            if (uLocale == null) {
                uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
            }
            currency = Currency.getInstance(uLocale);
        }
        return currency;
    }
    
    public int getRoundingMode() {
        throw new UnsupportedOperationException("getRoundingMode must be implemented by the subclass implementation.");
    }
    
    public void setRoundingMode(final int n) {
        throw new UnsupportedOperationException("setRoundingMode must be implemented by the subclass implementation.");
    }
    
    public static NumberFormat getInstance(final ULocale uLocale, final int n) {
        if (n < 0 || n > 6) {
            throw new IllegalArgumentException("choice should be from NUMBERSTYLE to PLURALCURRENCYSTYLE");
        }
        return getShim().createInstance(uLocale, n);
    }
    
    static NumberFormat createInstance(final ULocale uLocale, final int n) {
        String s = getPattern(uLocale, n);
        final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(uLocale);
        if (n == 1 || n == 5) {
            final String currencyPattern = decimalFormatSymbols.getCurrencyPattern();
            if (currencyPattern != null) {
                s = currencyPattern;
            }
        }
        if (n == 5) {
            s = s.replace("¤", NumberFormat.doubleCurrencyStr);
        }
        final NumberingSystem instance = NumberingSystem.getInstance(uLocale);
        if (instance == null) {
            return null;
        }
        NumberFormat numberFormat;
        if (instance != null && instance.isAlgorithmic()) {
            final String description = instance.getDescription();
            final int index = description.indexOf("/");
            final int lastIndex = description.lastIndexOf("/");
            String substring3;
            ULocale uLocale2;
            if (lastIndex > index) {
                final String substring = description.substring(0, index);
                final String substring2 = description.substring(index + 1, lastIndex);
                substring3 = description.substring(lastIndex + 1);
                uLocale2 = new ULocale(substring);
                if (substring2.equals("SpelloutRules")) {}
            }
            else {
                uLocale2 = uLocale;
                substring3 = description;
            }
            final RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(uLocale2, 1);
            ruleBasedNumberFormat.setDefaultRuleSet(substring3);
            numberFormat = ruleBasedNumberFormat;
        }
        else {
            final DecimalFormat decimalFormat = new DecimalFormat(s, decimalFormatSymbols, n);
            if (n == 4) {
                decimalFormat.setMaximumFractionDigits(0);
                decimalFormat.setDecimalSeparatorAlwaysShown(false);
                decimalFormat.setParseIntegerOnly(true);
            }
            numberFormat = decimalFormat;
        }
        numberFormat.setLocale(decimalFormatSymbols.getLocale(ULocale.VALID_LOCALE), decimalFormatSymbols.getLocale(ULocale.ACTUAL_LOCALE));
        return numberFormat;
    }
    
    @Deprecated
    protected static String getPattern(final Locale locale, final int n) {
        return getPattern(ULocale.forLocale(locale), n);
    }
    
    protected static String getPattern(final ULocale uLocale, final int n) {
        return ((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", uLocale)).getStringWithFallback("NumberElements/" + NumberingSystem.getInstance(uLocale).getName() + "/patterns/" + (new String[] { "decimalFormat", "currencyFormat", "percentFormat", "scientificFormat" })[(n == 4) ? 0 : ((n == 5 || n == 6) ? 1 : n)]);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            this.maximumIntegerDigits = this.maxIntegerDigits;
            this.minimumIntegerDigits = this.minIntegerDigits;
            this.maximumFractionDigits = this.maxFractionDigits;
            this.minimumFractionDigits = this.minFractionDigits;
        }
        if (this.minimumIntegerDigits > this.maximumIntegerDigits || this.minimumFractionDigits > this.maximumFractionDigits || this.minimumIntegerDigits < 0 || this.minimumFractionDigits < 0) {
            throw new InvalidObjectException("Digit count range invalid");
        }
        this.serialVersionOnStream = 1;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.maxIntegerDigits = (byte)((this.maximumIntegerDigits > 127) ? 127 : ((byte)this.maximumIntegerDigits));
        this.minIntegerDigits = (byte)((this.minimumIntegerDigits > 127) ? 127 : ((byte)this.minimumIntegerDigits));
        this.maxFractionDigits = (byte)((this.maximumFractionDigits > 127) ? 127 : ((byte)this.maximumFractionDigits));
        this.minFractionDigits = (byte)((this.minimumFractionDigits > 127) ? 127 : ((byte)this.minimumFractionDigits));
        objectOutputStream.defaultWriteObject();
    }
    
    public NumberFormat() {
        this.groupingUsed = true;
        this.maxIntegerDigits = 40;
        this.minIntegerDigits = 1;
        this.maxFractionDigits = 3;
        this.minFractionDigits = 0;
        this.parseIntegerOnly = false;
        this.maximumIntegerDigits = 40;
        this.minimumIntegerDigits = 1;
        this.maximumFractionDigits = 3;
        this.minimumFractionDigits = 0;
        this.serialVersionOnStream = 1;
    }
    
    static {
        doubleCurrencySign = new char[] { '¤', '¤' };
        doubleCurrencyStr = new String(NumberFormat.doubleCurrencySign);
    }
    
    public static class Field extends Format.Field
    {
        static final long serialVersionUID = -4516273749929385842L;
        public static final Field SIGN;
        public static final Field INTEGER;
        public static final Field FRACTION;
        public static final Field EXPONENT;
        public static final Field EXPONENT_SIGN;
        public static final Field EXPONENT_SYMBOL;
        public static final Field DECIMAL_SEPARATOR;
        public static final Field GROUPING_SEPARATOR;
        public static final Field PERCENT;
        public static final Field PERMILLE;
        public static final Field CURRENCY;
        
        protected Field(final String s) {
            super(s);
        }
        
        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getName().equals(Field.INTEGER.getName())) {
                return Field.INTEGER;
            }
            if (this.getName().equals(Field.FRACTION.getName())) {
                return Field.FRACTION;
            }
            if (this.getName().equals(Field.EXPONENT.getName())) {
                return Field.EXPONENT;
            }
            if (this.getName().equals(Field.EXPONENT_SIGN.getName())) {
                return Field.EXPONENT_SIGN;
            }
            if (this.getName().equals(Field.EXPONENT_SYMBOL.getName())) {
                return Field.EXPONENT_SYMBOL;
            }
            if (this.getName().equals(Field.CURRENCY.getName())) {
                return Field.CURRENCY;
            }
            if (this.getName().equals(Field.DECIMAL_SEPARATOR.getName())) {
                return Field.DECIMAL_SEPARATOR;
            }
            if (this.getName().equals(Field.GROUPING_SEPARATOR.getName())) {
                return Field.GROUPING_SEPARATOR;
            }
            if (this.getName().equals(Field.PERCENT.getName())) {
                return Field.PERCENT;
            }
            if (this.getName().equals(Field.PERMILLE.getName())) {
                return Field.PERMILLE;
            }
            if (this.getName().equals(Field.SIGN.getName())) {
                return Field.SIGN;
            }
            throw new InvalidObjectException("An invalid object.");
        }
        
        static {
            SIGN = new Field("sign");
            INTEGER = new Field("integer");
            FRACTION = new Field("fraction");
            EXPONENT = new Field("exponent");
            EXPONENT_SIGN = new Field("exponent sign");
            EXPONENT_SYMBOL = new Field("exponent symbol");
            DECIMAL_SEPARATOR = new Field("decimal separator");
            GROUPING_SEPARATOR = new Field("grouping separator");
            PERCENT = new Field("percent");
            PERMILLE = new Field("per mille");
            CURRENCY = new Field("currency");
        }
    }
    
    abstract static class NumberFormatShim
    {
        abstract Locale[] getAvailableLocales();
        
        abstract ULocale[] getAvailableULocales();
        
        abstract Object registerFactory(final NumberFormatFactory p0);
        
        abstract boolean unregister(final Object p0);
        
        abstract NumberFormat createInstance(final ULocale p0, final int p1);
    }
    
    public abstract static class NumberFormatFactory
    {
        public static final int FORMAT_NUMBER = 0;
        public static final int FORMAT_CURRENCY = 1;
        public static final int FORMAT_PERCENT = 2;
        public static final int FORMAT_SCIENTIFIC = 3;
        public static final int FORMAT_INTEGER = 4;
        
        public boolean visible() {
            return true;
        }
        
        public abstract Set getSupportedLocaleNames();
        
        public NumberFormat createFormat(final ULocale uLocale, final int n) {
            return this.createFormat(uLocale.toLocale(), n);
        }
        
        public NumberFormat createFormat(final Locale locale, final int n) {
            return this.createFormat(ULocale.forLocale(locale), n);
        }
        
        protected NumberFormatFactory() {
        }
    }
    
    public abstract static class SimpleNumberFormatFactory extends NumberFormatFactory
    {
        final Set localeNames;
        final boolean visible;
        
        public SimpleNumberFormatFactory(final Locale locale) {
            this(locale, true);
        }
        
        public SimpleNumberFormatFactory(final Locale locale, final boolean visible) {
            this.localeNames = Collections.singleton(ULocale.forLocale(locale).getBaseName());
            this.visible = visible;
        }
        
        public SimpleNumberFormatFactory(final ULocale uLocale) {
            this(uLocale, true);
        }
        
        public SimpleNumberFormatFactory(final ULocale uLocale, final boolean visible) {
            this.localeNames = Collections.singleton(uLocale.getBaseName());
            this.visible = visible;
        }
        
        @Override
        public final boolean visible() {
            return this.visible;
        }
        
        @Override
        public final Set getSupportedLocaleNames() {
            return this.localeNames;
        }
    }
}
