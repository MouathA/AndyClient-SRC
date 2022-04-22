package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.util.*;
import java.text.*;
import java.io.*;
import com.ibm.icu.impl.*;

public class DecimalFormatSymbols implements Cloneable, Serializable
{
    public static final int CURRENCY_SPC_CURRENCY_MATCH = 0;
    public static final int CURRENCY_SPC_SURROUNDING_MATCH = 1;
    public static final int CURRENCY_SPC_INSERT = 2;
    private String[] currencySpcBeforeSym;
    private String[] currencySpcAfterSym;
    private char zeroDigit;
    private char[] digits;
    private char groupingSeparator;
    private char decimalSeparator;
    private char perMill;
    private char percent;
    private char digit;
    private char sigDigit;
    private char patternSeparator;
    private String infinity;
    private String NaN;
    private char minusSign;
    private String currencySymbol;
    private String intlCurrencySymbol;
    private char monetarySeparator;
    private char monetaryGroupingSeparator;
    private char exponential;
    private String exponentSeparator;
    private char padEscape;
    private char plusSign;
    private Locale requestedLocale;
    private ULocale ulocale;
    private static final long serialVersionUID = 5772796243397350300L;
    private static final int currentSerialVersion = 6;
    private int serialVersionOnStream;
    private static final ICUCache cachedLocaleData;
    private String currencyPattern;
    private ULocale validLocale;
    private ULocale actualLocale;
    private transient Currency currency;
    
    public DecimalFormatSymbols() {
        this.serialVersionOnStream = 6;
        this.currencyPattern = null;
        this.initialize(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public DecimalFormatSymbols(final Locale locale) {
        this.serialVersionOnStream = 6;
        this.currencyPattern = null;
        this.initialize(ULocale.forLocale(locale));
    }
    
    public DecimalFormatSymbols(final ULocale uLocale) {
        this.serialVersionOnStream = 6;
        this.currencyPattern = null;
        this.initialize(uLocale);
    }
    
    public static DecimalFormatSymbols getInstance() {
        return new DecimalFormatSymbols();
    }
    
    public static DecimalFormatSymbols getInstance(final Locale locale) {
        return new DecimalFormatSymbols(locale);
    }
    
    public static DecimalFormatSymbols getInstance(final ULocale uLocale) {
        return new DecimalFormatSymbols(uLocale);
    }
    
    public static Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales();
    }
    
    public static ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales();
    }
    
    public char getZeroDigit() {
        if (this.digits != null) {
            return this.digits[0];
        }
        return this.zeroDigit;
    }
    
    public char[] getDigits() {
        if (this.digits != null) {
            return this.digits.clone();
        }
        final char[] array = new char[10];
        while (0 < 10) {
            array[0] = (char)(this.zeroDigit + '\0');
            int n = 0;
            ++n;
        }
        return array;
    }
    
    char[] getDigitsLocal() {
        if (this.digits != null) {
            return this.digits;
        }
        final char[] array = new char[10];
        while (0 < 10) {
            array[0] = (char)(this.zeroDigit + '\0');
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public void setZeroDigit(final char zeroDigit) {
        if (this.digits != null) {
            this.digits[0] = zeroDigit;
            if (Character.digit(zeroDigit, 10) == 0) {
                while (1 < 10) {
                    this.digits[1] = (char)(zeroDigit + '\u0001');
                    int n = 0;
                    ++n;
                }
            }
        }
        else {
            this.zeroDigit = zeroDigit;
        }
    }
    
    public char getSignificantDigit() {
        return this.sigDigit;
    }
    
    public void setSignificantDigit(final char sigDigit) {
        this.sigDigit = sigDigit;
    }
    
    public char getGroupingSeparator() {
        return this.groupingSeparator;
    }
    
    public void setGroupingSeparator(final char groupingSeparator) {
        this.groupingSeparator = groupingSeparator;
    }
    
    public char getDecimalSeparator() {
        return this.decimalSeparator;
    }
    
    public void setDecimalSeparator(final char decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }
    
    public char getPerMill() {
        return this.perMill;
    }
    
    public void setPerMill(final char perMill) {
        this.perMill = perMill;
    }
    
    public char getPercent() {
        return this.percent;
    }
    
    public void setPercent(final char percent) {
        this.percent = percent;
    }
    
    public char getDigit() {
        return this.digit;
    }
    
    public void setDigit(final char digit) {
        this.digit = digit;
    }
    
    public char getPatternSeparator() {
        return this.patternSeparator;
    }
    
    public void setPatternSeparator(final char patternSeparator) {
        this.patternSeparator = patternSeparator;
    }
    
    public String getInfinity() {
        return this.infinity;
    }
    
    public void setInfinity(final String infinity) {
        this.infinity = infinity;
    }
    
    public String getNaN() {
        return this.NaN;
    }
    
    public void setNaN(final String naN) {
        this.NaN = naN;
    }
    
    public char getMinusSign() {
        return this.minusSign;
    }
    
    public void setMinusSign(final char minusSign) {
        this.minusSign = minusSign;
    }
    
    public String getCurrencySymbol() {
        return this.currencySymbol;
    }
    
    public void setCurrencySymbol(final String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
    
    public String getInternationalCurrencySymbol() {
        return this.intlCurrencySymbol;
    }
    
    public void setInternationalCurrencySymbol(final String intlCurrencySymbol) {
        this.intlCurrencySymbol = intlCurrencySymbol;
    }
    
    public Currency getCurrency() {
        return this.currency;
    }
    
    public void setCurrency(final Currency currency) {
        if (currency == null) {
            throw new NullPointerException();
        }
        this.currency = currency;
        this.intlCurrencySymbol = currency.getCurrencyCode();
        this.currencySymbol = currency.getSymbol(this.requestedLocale);
    }
    
    public char getMonetaryDecimalSeparator() {
        return this.monetarySeparator;
    }
    
    public char getMonetaryGroupingSeparator() {
        return this.monetaryGroupingSeparator;
    }
    
    String getCurrencyPattern() {
        return this.currencyPattern;
    }
    
    public void setMonetaryDecimalSeparator(final char monetarySeparator) {
        this.monetarySeparator = monetarySeparator;
    }
    
    public void setMonetaryGroupingSeparator(final char monetaryGroupingSeparator) {
        this.monetaryGroupingSeparator = monetaryGroupingSeparator;
    }
    
    public String getExponentSeparator() {
        return this.exponentSeparator;
    }
    
    public void setExponentSeparator(final String exponentSeparator) {
        this.exponentSeparator = exponentSeparator;
    }
    
    public char getPlusSign() {
        return this.plusSign;
    }
    
    public void setPlusSign(final char plusSign) {
        this.plusSign = plusSign;
    }
    
    public char getPadEscape() {
        return this.padEscape;
    }
    
    public void setPadEscape(final char padEscape) {
        this.padEscape = padEscape;
    }
    
    public String getPatternForCurrencySpacing(final int n, final boolean b) {
        if (n < 0 || n > 2) {
            throw new IllegalArgumentException("unknown currency spacing: " + n);
        }
        if (b) {
            return this.currencySpcBeforeSym[n];
        }
        return this.currencySpcAfterSym[n];
    }
    
    public void setPatternForCurrencySpacing(final int n, final boolean b, final String s) {
        if (n < 0 || n > 2) {
            throw new IllegalArgumentException("unknown currency spacing: " + n);
        }
        if (b) {
            this.currencySpcBeforeSym[n] = s;
        }
        else {
            this.currencySpcAfterSym[n] = s;
        }
    }
    
    public Locale getLocale() {
        return this.requestedLocale;
    }
    
    public ULocale getULocale() {
        return this.ulocale;
    }
    
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof DecimalFormatSymbols)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        final DecimalFormatSymbols decimalFormatSymbols = (DecimalFormatSymbols)o;
        int n = 0;
        while (0 <= 2) {
            if (!this.currencySpcBeforeSym[0].equals(decimalFormatSymbols.currencySpcBeforeSym[0])) {
                return false;
            }
            if (!this.currencySpcAfterSym[0].equals(decimalFormatSymbols.currencySpcAfterSym[0])) {
                return false;
            }
            ++n;
        }
        if (decimalFormatSymbols.digits == null) {
            while (0 < 10) {
                if (this.digits[0] != decimalFormatSymbols.zeroDigit + '\0') {
                    return false;
                }
                ++n;
            }
        }
        else if (!Arrays.equals(this.digits, decimalFormatSymbols.digits)) {
            return false;
        }
        return this.groupingSeparator == decimalFormatSymbols.groupingSeparator && this.decimalSeparator == decimalFormatSymbols.decimalSeparator && this.percent == decimalFormatSymbols.percent && this.perMill == decimalFormatSymbols.perMill && this.digit == decimalFormatSymbols.digit && this.minusSign == decimalFormatSymbols.minusSign && this.patternSeparator == decimalFormatSymbols.patternSeparator && this.infinity.equals(decimalFormatSymbols.infinity) && this.NaN.equals(decimalFormatSymbols.NaN) && this.currencySymbol.equals(decimalFormatSymbols.currencySymbol) && this.intlCurrencySymbol.equals(decimalFormatSymbols.intlCurrencySymbol) && this.padEscape == decimalFormatSymbols.padEscape && this.plusSign == decimalFormatSymbols.plusSign && this.exponentSeparator.equals(decimalFormatSymbols.exponentSeparator) && this.monetarySeparator == decimalFormatSymbols.monetarySeparator && this.monetaryGroupingSeparator == decimalFormatSymbols.monetaryGroupingSeparator;
    }
    
    @Override
    public int hashCode() {
        return (this.digits[0] * '%' + this.groupingSeparator) * 37 + this.decimalSeparator;
    }
    
    private void initialize(final ULocale ulocale) {
        this.requestedLocale = ulocale.toLocale();
        this.ulocale = ulocale;
        final NumberingSystem instance = NumberingSystem.getInstance(ulocale);
        this.digits = new char[10];
        String name;
        if (instance != null && instance.getRadix() == 10 && !instance.isAlgorithmic() && NumberingSystem.isValidDigitString(instance.getDescription())) {
            final String description = instance.getDescription();
            this.digits[0] = description.charAt(0);
            this.digits[1] = description.charAt(1);
            this.digits[2] = description.charAt(2);
            this.digits[3] = description.charAt(3);
            this.digits[4] = description.charAt(4);
            this.digits[5] = description.charAt(5);
            this.digits[6] = description.charAt(6);
            this.digits[7] = description.charAt(7);
            this.digits[8] = description.charAt(8);
            this.digits[9] = description.charAt(9);
            name = instance.getName();
        }
        else {
            this.digits[0] = '0';
            this.digits[1] = '1';
            this.digits[2] = '2';
            this.digits[3] = '3';
            this.digits[4] = '4';
            this.digits[5] = '5';
            this.digits[6] = '6';
            this.digits[7] = '7';
            this.digits[8] = '8';
            this.digits[9] = '9';
            name = "latn";
        }
        String[][] array = (String[][])DecimalFormatSymbols.cachedLocaleData.get(ulocale);
        if (array == null) {
            array = new String[][] { null };
            final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", ulocale);
            name.equals("latn");
            final String string = "NumberElements/" + name + "/symbols/";
            final String[] array2 = { "decimal", "group", "list", "percentSign", "minusSign", "plusSign", "exponential", "perMille", "infinity", "nan", "currencyDecimal", "currencyGroup" };
            final String[] array3 = { ".", ",", ";", "%", "-", "+", "E", "\u2030", "\u221e", "NaN", null, null };
            final String[] array4 = new String[array2.length];
            while (0 < array2.length) {
                array4[0] = icuResourceBundle.getStringWithFallback(string + array2[0]);
                int n = 0;
                ++n;
            }
            array[0] = array4;
            DecimalFormatSymbols.cachedLocaleData.put(ulocale, array);
        }
        final String[] array5 = array[0];
        final ULocale uLocale = ((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", ulocale)).getULocale();
        this.setLocale(uLocale, uLocale);
        this.decimalSeparator = array5[0].charAt(0);
        this.groupingSeparator = array5[1].charAt(0);
        this.patternSeparator = array5[2].charAt(0);
        this.percent = array5[3].charAt(0);
        this.minusSign = array5[4].charAt(0);
        this.plusSign = array5[5].charAt(0);
        this.exponentSeparator = array5[6];
        this.perMill = array5[7].charAt(0);
        this.infinity = array5[8];
        this.NaN = array5[9];
        if (array5[10] != null) {
            this.monetarySeparator = array5[10].charAt(0);
        }
        else {
            this.monetarySeparator = this.decimalSeparator;
        }
        if (array5[11] != null) {
            this.monetaryGroupingSeparator = array5[11].charAt(0);
        }
        else {
            this.monetaryGroupingSeparator = this.groupingSeparator;
        }
        this.digit = '#';
        this.padEscape = '*';
        this.sigDigit = '@';
        final CurrencyData.CurrencyDisplayInfo instance2 = CurrencyData.provider.getInstance(ulocale, true);
        this.currency = Currency.getInstance(ulocale);
        if (this.currency != null) {
            this.intlCurrencySymbol = this.currency.getCurrencyCode();
            final boolean[] array6 = { false };
            final String name2 = this.currency.getName(ulocale, 0, array6);
            this.currencySymbol = (array6[0] ? new ChoiceFormat(name2).format(2.0) : name2);
            final CurrencyData.CurrencyFormatInfo formatInfo = instance2.getFormatInfo(this.intlCurrencySymbol);
            if (formatInfo != null) {
                this.currencyPattern = formatInfo.currencyPattern;
                this.monetarySeparator = formatInfo.monetarySeparator;
                this.monetaryGroupingSeparator = formatInfo.monetaryGroupingSeparator;
            }
        }
        else {
            this.intlCurrencySymbol = "XXX";
            this.currencySymbol = "¤";
        }
        this.currencySpcBeforeSym = new String[3];
        this.currencySpcAfterSym = new String[3];
        this.initSpacingInfo(instance2.getSpacingInfo());
    }
    
    private void initSpacingInfo(final CurrencyData.CurrencySpacingInfo currencySpacingInfo) {
        this.currencySpcBeforeSym[0] = currencySpacingInfo.beforeCurrencyMatch;
        this.currencySpcBeforeSym[1] = currencySpacingInfo.beforeContextMatch;
        this.currencySpcBeforeSym[2] = currencySpacingInfo.beforeInsert;
        this.currencySpcAfterSym[0] = currencySpacingInfo.afterCurrencyMatch;
        this.currencySpcAfterSym[1] = currencySpacingInfo.afterContextMatch;
        this.currencySpcAfterSym[2] = currencySpacingInfo.afterInsert;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            this.monetarySeparator = this.decimalSeparator;
            this.exponential = 'E';
        }
        if (this.serialVersionOnStream < 2) {
            this.padEscape = '*';
            this.plusSign = '+';
            this.exponentSeparator = String.valueOf(this.exponential);
        }
        if (this.serialVersionOnStream < 3) {
            this.requestedLocale = Locale.getDefault();
        }
        if (this.serialVersionOnStream < 4) {
            this.ulocale = ULocale.forLocale(this.requestedLocale);
        }
        if (this.serialVersionOnStream < 5) {
            this.monetaryGroupingSeparator = this.groupingSeparator;
        }
        if (this.serialVersionOnStream < 6) {
            if (this.currencySpcBeforeSym == null) {
                this.currencySpcBeforeSym = new String[3];
            }
            if (this.currencySpcAfterSym == null) {
                this.currencySpcAfterSym = new String[3];
            }
            this.initSpacingInfo(CurrencyData.CurrencySpacingInfo.DEFAULT);
        }
        this.serialVersionOnStream = 6;
        this.currency = Currency.getInstance(this.intlCurrencySymbol);
    }
    
    public final ULocale getLocale(final ULocale.Type type) {
        return (type == ULocale.ACTUAL_LOCALE) ? this.actualLocale : this.validLocale;
    }
    
    final void setLocale(final ULocale validLocale, final ULocale actualLocale) {
        if (validLocale == null != (actualLocale == null)) {
            throw new IllegalArgumentException();
        }
        this.validLocale = validLocale;
        this.actualLocale = actualLocale;
    }
    
    static {
        cachedLocaleData = new SimpleCache();
    }
}
