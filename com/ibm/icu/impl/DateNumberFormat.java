package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import com.ibm.icu.util.*;
import java.math.*;
import java.text.*;
import com.ibm.icu.lang.*;
import java.util.*;
import java.io.*;

public final class DateNumberFormat extends NumberFormat
{
    private static final long serialVersionUID = -6315692826916346953L;
    private char[] digits;
    private char zeroDigit;
    private char minusSign;
    private boolean positiveOnly;
    private transient char[] decimalBuf;
    private static SimpleCache CACHE;
    private int maxIntDigits;
    private int minIntDigits;
    private static final long PARSE_THRESHOLD = 922337203685477579L;
    
    public DateNumberFormat(final ULocale uLocale, final String s, final String s2) {
        this.positiveOnly = false;
        this.decimalBuf = new char[20];
        this.initialize(uLocale, s, s2);
    }
    
    public DateNumberFormat(final ULocale uLocale, final char c, final String s) {
        this.positiveOnly = false;
        this.decimalBuf = new char[20];
        final StringBuffer sb = new StringBuffer();
        while (0 < 10) {
            sb.append((char)(c + '\0'));
            int n = 0;
            ++n;
        }
        this.initialize(uLocale, sb.toString(), s);
    }
    
    private void initialize(final ULocale uLocale, final String s, final String s2) {
        char[] array = (char[])DateNumberFormat.CACHE.get(uLocale);
        if (array == null) {
            final String stringWithFallback = ((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", uLocale)).getStringWithFallback("NumberElements/" + s2 + "/symbols/minusSign");
            array = new char[11];
            while (0 < 10) {
                array[0] = s.charAt(0);
                int n = 0;
                ++n;
            }
            array[10] = stringWithFallback.charAt(0);
            DateNumberFormat.CACHE.put(uLocale, array);
        }
        System.arraycopy(array, 0, this.digits = new char[10], 0, 10);
        this.zeroDigit = this.digits[0];
        this.minusSign = array[10];
    }
    
    @Override
    public void setMaximumIntegerDigits(final int maxIntDigits) {
        this.maxIntDigits = maxIntDigits;
    }
    
    @Override
    public int getMaximumIntegerDigits() {
        return this.maxIntDigits;
    }
    
    @Override
    public void setMinimumIntegerDigits(final int minIntDigits) {
        this.minIntDigits = minIntDigits;
    }
    
    @Override
    public int getMinimumIntegerDigits() {
        return this.minIntDigits;
    }
    
    public void setParsePositiveOnly(final boolean positiveOnly) {
        this.positiveOnly = positiveOnly;
    }
    
    public char getZeroDigit() {
        return this.zeroDigit;
    }
    
    public void setZeroDigit(final char zeroDigit) {
        this.zeroDigit = zeroDigit;
        if (this.digits == null) {
            this.digits = new char[10];
        }
        this.digits[0] = zeroDigit;
        while (1 < 10) {
            this.digits[1] = (char)(zeroDigit + '\u0001');
            int n = 0;
            ++n;
        }
    }
    
    public char[] getDigits() {
        return this.digits;
    }
    
    @Override
    public StringBuffer format(final double n, final StringBuffer sb, final FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("StringBuffer format(double, StringBuffer, FieldPostion) is not implemented");
    }
    
    @Override
    public StringBuffer format(long n, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (n < 0L) {
            sb.append(this.minusSign);
            n = -n;
        }
        int n2 = (int)n;
        final int n3 = (this.decimalBuf.length < this.maxIntDigits) ? this.decimalBuf.length : this.maxIntDigits;
        int n4 = n3 - 1;
        while (true) {
            this.decimalBuf[n4] = this.digits[n2 % 10];
            n2 /= 10;
            if (n4 == 0 || n2 == 0) {
                break;
            }
            --n4;
        }
        for (int i = this.minIntDigits - (n3 - n4); i > 0; --i) {
            this.decimalBuf[--n4] = this.digits[0];
        }
        final int endIndex = n3 - n4;
        sb.append(this.decimalBuf, n4, endIndex);
        fieldPosition.setBeginIndex(0);
        if (fieldPosition.getField() == 0) {
            fieldPosition.setEndIndex(endIndex);
        }
        else {
            fieldPosition.setEndIndex(0);
        }
        return sb;
    }
    
    @Override
    public StringBuffer format(final BigInteger bigInteger, final StringBuffer sb, final FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("StringBuffer format(BigInteger, StringBuffer, FieldPostion) is not implemented");
    }
    
    @Override
    public StringBuffer format(final BigDecimal bigDecimal, final StringBuffer sb, final FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("StringBuffer format(BigDecimal, StringBuffer, FieldPostion) is not implemented");
    }
    
    @Override
    public StringBuffer format(final com.ibm.icu.math.BigDecimal bigDecimal, final StringBuffer sb, final FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("StringBuffer format(BigDecimal, StringBuffer, FieldPostion) is not implemented");
    }
    
    @Override
    public Number parse(final String s, final ParsePosition parsePosition) {
        long n = 0L;
        final int index = parsePosition.getIndex();
        while (index + 0 < s.length()) {
            final char char1 = s.charAt(index + 0);
            if (!false && char1 == this.minusSign) {
                if (this.positiveOnly) {
                    break;
                }
            }
            else {
                int digit = char1 - this.digits[0];
                if (0 < 0 || 9 < 0) {
                    digit = UCharacter.digit(char1);
                }
                if (0 < 0 || 9 < 0) {
                    while (0 < 10) {
                        if (char1 == this.digits[0]) {
                            break;
                        }
                        ++digit;
                    }
                }
                if (0 > 0 || 0 > 9 || n >= 922337203685477579L) {
                    break;
                }
                n = n * 10L + 0;
            }
            int n2 = 0;
            ++n2;
        }
        Number value = null;
        if (true) {
            value = (true ? (n * -1L) : n);
            parsePosition.setIndex(index + 0);
        }
        return value;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null || !super.equals(o) || !(o instanceof DateNumberFormat)) {
            return false;
        }
        final DateNumberFormat dateNumberFormat = (DateNumberFormat)o;
        return this.maxIntDigits == dateNumberFormat.maxIntDigits && this.minIntDigits == dateNumberFormat.minIntDigits && this.minusSign == dateNumberFormat.minusSign && this.positiveOnly == dateNumberFormat.positiveOnly && Arrays.equals(this.digits, dateNumberFormat.digits);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.digits == null) {
            this.setZeroDigit(this.zeroDigit);
        }
        this.decimalBuf = new char[20];
    }
    
    static {
        DateNumberFormat.CACHE = new SimpleCache();
    }
}
