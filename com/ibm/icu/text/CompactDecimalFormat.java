package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;

public class CompactDecimalFormat extends DecimalFormat
{
    private static final long serialVersionUID = 4716293295276629682L;
    private static final int POSITIVE_PREFIX = 0;
    private static final int POSITIVE_SUFFIX = 1;
    private static final int AFFIX_SIZE = 2;
    private static final CompactDecimalDataCache cache;
    private final Map units;
    private final long[] divisor;
    private final String[] currencyAffixes;
    private final PluralRules pluralRules;
    
    public static CompactDecimalFormat getInstance(final ULocale uLocale, final CompactStyle compactStyle) {
        return new CompactDecimalFormat(uLocale, compactStyle);
    }
    
    public static CompactDecimalFormat getInstance(final Locale locale, final CompactStyle compactStyle) {
        return new CompactDecimalFormat(ULocale.forLocale(locale), compactStyle);
    }
    
    CompactDecimalFormat(final ULocale uLocale, final CompactStyle compactStyle) {
        final DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getInstance(uLocale);
        final CompactDecimalDataCache.Data data = this.getData(uLocale, compactStyle);
        this.units = data.units;
        this.divisor = data.divisors;
        this.applyPattern(decimalFormat.toPattern());
        this.setDecimalFormatSymbols(decimalFormat.getDecimalFormatSymbols());
        this.setMaximumSignificantDigits(3);
        this.setSignificantDigitsUsed(true);
        if (compactStyle == CompactStyle.SHORT) {
            this.setGroupingUsed(false);
        }
        this.pluralRules = PluralRules.forLocale(uLocale);
        final DecimalFormat decimalFormat2 = (DecimalFormat)NumberFormat.getCurrencyInstance(uLocale);
        (this.currencyAffixes = new String[2])[0] = decimalFormat2.getPositivePrefix();
        this.currencyAffixes[1] = decimalFormat2.getPositiveSuffix();
        this.setCurrency(null);
    }
    
    @Deprecated
    public CompactDecimalFormat(final String s, final DecimalFormatSymbols decimalFormatSymbols, final String[] array, final String[] array2, final long[] array3, final Collection collection, final CompactStyle compactStyle, final String[] array4) {
        if (array.length < 15) {
            this.recordError(collection, "Must have at least 15 prefix items.");
        }
        if (array.length != array2.length || array.length != array3.length) {
            this.recordError(collection, "Prefix, suffix, and divisor arrays must have the same length.");
        }
        long n = 0L;
        final HashMap<String, Integer> hashMap = (HashMap<String, Integer>)new HashMap<Object, Integer>();
        while (0 < array.length) {
            if (array[0] == null || array2[0] == null) {
                this.recordError(collection, "Prefix or suffix is null for " + 0);
            }
            final int n2 = (int)Math.log10((double)array3[0]);
            if (n2 > 0) {
                this.recordError(collection, "Divisor[" + 0 + "] must be less than or equal to 10^" + 0 + ", but is: " + array3[0]);
            }
            if ((long)Math.pow(10.0, n2) != array3[0]) {
                this.recordError(collection, "Divisor[" + 0 + "] must be a power of 10, but is: " + array3[0]);
            }
            final String string = array[0] + "\uffff" + array2[0] + "\uffff" + (0 - n2);
            final Integer n3 = hashMap.get(string);
            if (n3 != null) {
                this.recordError(collection, "Collision between values for " + 0 + " and " + n3 + " for [prefix/suffix/index-log(divisor)" + string.replace('\uffff', ';'));
            }
            else {
                hashMap.put(string, 0);
            }
            if (array3[0] < n) {
                this.recordError(collection, "Bad divisor, the divisor for 10E" + 0 + "(" + array3[0] + ") is less than the divisor for the divisor for 10E" + -1 + "(" + n + ")");
            }
            n = array3[0];
            int n4 = 0;
            ++n4;
        }
        this.units = this.otherPluralVariant(array, array2);
        this.divisor = array3.clone();
        this.applyPattern(s);
        this.setDecimalFormatSymbols(decimalFormatSymbols);
        this.setMaximumSignificantDigits(2);
        this.setSignificantDigitsUsed(true);
        this.setGroupingUsed(false);
        this.currencyAffixes = array4.clone();
        this.pluralRules = null;
        this.setCurrency(null);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final CompactDecimalFormat compactDecimalFormat = (CompactDecimalFormat)o;
        return this.units != compactDecimalFormat.units && Arrays.equals(this.divisor, compactDecimalFormat.divisor) && Arrays.equals(this.currencyAffixes, compactDecimalFormat.currencyAffixes) && this.pluralRules.equals(compactDecimalFormat.pluralRules);
    }
    
    @Override
    public StringBuffer format(final double n, final StringBuffer sb, final FieldPosition fieldPosition) {
        final Amount amount = this.toAmount(n);
        final Unit unit = amount.getUnit();
        unit.writePrefix(sb);
        super.format(amount.getQty(), sb, fieldPosition);
        unit.writeSuffix(sb);
        return sb;
    }
    
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(final Object o) {
        if (!(o instanceof Number)) {
            throw new IllegalArgumentException();
        }
        final Amount amount = this.toAmount(((Number)o).doubleValue());
        return super.formatToCharacterIterator(amount.getQty(), amount.getUnit());
    }
    
    @Override
    public StringBuffer format(final long n, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.format((double)n, sb, fieldPosition);
    }
    
    @Override
    public StringBuffer format(final BigInteger bigInteger, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.format(bigInteger.doubleValue(), sb, fieldPosition);
    }
    
    @Override
    public StringBuffer format(final BigDecimal bigDecimal, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.format(bigDecimal.doubleValue(), sb, fieldPosition);
    }
    
    @Override
    public StringBuffer format(final com.ibm.icu.math.BigDecimal bigDecimal, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.format(bigDecimal.doubleValue(), sb, fieldPosition);
    }
    
    @Override
    public Number parse(final String s, final ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        throw new NotSerializableException();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        throw new NotSerializableException();
    }
    
    private Amount toAmount(double adjustNumberAsInFormatting) {
        final boolean numberNegative = this.isNumberNegative(adjustNumberAsInFormatting);
        adjustNumberAsInFormatting = this.adjustNumberAsInFormatting(adjustNumberAsInFormatting);
        final int n = (adjustNumberAsInFormatting <= 1.0) ? 0 : ((int)Math.log10(adjustNumberAsInFormatting));
        adjustNumberAsInFormatting /= this.divisor[14];
        final String pluralForm = this.getPluralForm(adjustNumberAsInFormatting);
        if (numberNegative) {
            adjustNumberAsInFormatting = -adjustNumberAsInFormatting;
        }
        return new Amount(adjustNumberAsInFormatting, CompactDecimalDataCache.getUnit(this.units, pluralForm, 14));
    }
    
    private void recordError(final Collection collection, final String s) {
        if (collection == null) {
            throw new IllegalArgumentException(s);
        }
        collection.add(s);
    }
    
    private Map otherPluralVariant(final String[] array, final String[] array2) {
        final HashMap<String, Unit[]> hashMap = new HashMap<String, Unit[]>();
        final Unit[] array3 = new Unit[array.length];
        while (0 < array3.length) {
            array3[0] = new Unit(array[0], array2[0]);
            int n = 0;
            ++n;
        }
        hashMap.put("other", array3);
        return hashMap;
    }
    
    private String getPluralForm(final double n) {
        if (this.pluralRules == null) {
            return "other";
        }
        return this.pluralRules.select(n);
    }
    
    private CompactDecimalDataCache.Data getData(final ULocale uLocale, final CompactStyle compactStyle) {
        final CompactDecimalDataCache.DataBundle value = CompactDecimalFormat.cache.get(uLocale);
        switch (compactStyle) {
            case SHORT: {
                return value.shortData;
            }
            case LONG: {
                return value.longData;
            }
            default: {
                return value.shortData;
            }
        }
    }
    
    static {
        cache = new CompactDecimalDataCache();
    }
    
    public enum CompactStyle
    {
        SHORT("SHORT", 0), 
        LONG("LONG", 1);
        
        private static final CompactStyle[] $VALUES;
        
        private CompactStyle(final String s, final int n) {
        }
        
        static {
            $VALUES = new CompactStyle[] { CompactStyle.SHORT, CompactStyle.LONG };
        }
    }
    
    private static class Amount
    {
        private final double qty;
        private final Unit unit;
        
        public Amount(final double qty, final Unit unit) {
            this.qty = qty;
            this.unit = unit;
        }
        
        public double getQty() {
            return this.qty;
        }
        
        public Unit getUnit() {
            return this.unit;
        }
    }
}
