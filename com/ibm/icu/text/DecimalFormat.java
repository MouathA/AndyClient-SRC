package com.ibm.icu.text;

import com.ibm.icu.math.*;
import com.ibm.icu.util.*;
import java.util.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.lang.*;
import java.math.*;
import java.text.*;
import java.io.*;

public class DecimalFormat extends NumberFormat
{
    private static final int CURRENCY_SIGN_COUNT_IN_SYMBOL_FORMAT = 1;
    private static final int CURRENCY_SIGN_COUNT_IN_ISO_FORMAT = 2;
    private static final int CURRENCY_SIGN_COUNT_IN_PLURAL_FORMAT = 3;
    private static final int STATUS_INFINITE = 0;
    private static final int STATUS_POSITIVE = 1;
    private static final int STATUS_UNDERFLOW = 2;
    private static final int STATUS_LENGTH = 3;
    private static final UnicodeSet dotEquivalents;
    private static final UnicodeSet commaEquivalents;
    private static final UnicodeSet strictDotEquivalents;
    private static final UnicodeSet strictCommaEquivalents;
    private static final UnicodeSet defaultGroupingSeparators;
    private static final UnicodeSet strictDefaultGroupingSeparators;
    private int PARSE_MAX_EXPONENT;
    static final double roundingIncrementEpsilon = 1.0E-9;
    private transient DigitList digitList;
    private String positivePrefix;
    private String positiveSuffix;
    private String negativePrefix;
    private String negativeSuffix;
    private String posPrefixPattern;
    private String posSuffixPattern;
    private String negPrefixPattern;
    private String negSuffixPattern;
    private ChoiceFormat currencyChoice;
    private int multiplier;
    private byte groupingSize;
    private byte groupingSize2;
    private boolean decimalSeparatorAlwaysShown;
    private DecimalFormatSymbols symbols;
    private boolean useSignificantDigits;
    private int minSignificantDigits;
    private int maxSignificantDigits;
    private boolean useExponentialNotation;
    private byte minExponentDigits;
    private boolean exponentSignAlwaysShown;
    private BigDecimal roundingIncrement;
    private transient com.ibm.icu.math.BigDecimal roundingIncrementICU;
    private transient double roundingDouble;
    private transient double roundingDoubleReciprocal;
    private int roundingMode;
    private MathContext mathContext;
    private int formatWidth;
    private char pad;
    private int padPosition;
    private boolean parseBigDecimal;
    static final int currentSerialVersion = 3;
    private int serialVersionOnStream;
    public static final int PAD_BEFORE_PREFIX = 0;
    public static final int PAD_AFTER_PREFIX = 1;
    public static final int PAD_BEFORE_SUFFIX = 2;
    public static final int PAD_AFTER_SUFFIX = 3;
    static final char PATTERN_ZERO_DIGIT = '0';
    static final char PATTERN_ONE_DIGIT = '1';
    static final char PATTERN_TWO_DIGIT = '2';
    static final char PATTERN_THREE_DIGIT = '3';
    static final char PATTERN_FOUR_DIGIT = '4';
    static final char PATTERN_FIVE_DIGIT = '5';
    static final char PATTERN_SIX_DIGIT = '6';
    static final char PATTERN_SEVEN_DIGIT = '7';
    static final char PATTERN_EIGHT_DIGIT = '8';
    static final char PATTERN_NINE_DIGIT = '9';
    static final char PATTERN_GROUPING_SEPARATOR = ',';
    static final char PATTERN_DECIMAL_SEPARATOR = '.';
    static final char PATTERN_DIGIT = '#';
    static final char PATTERN_SIGNIFICANT_DIGIT = '@';
    static final char PATTERN_EXPONENT = 'E';
    static final char PATTERN_PLUS_SIGN = '+';
    private static final char PATTERN_PER_MILLE = '\u2030';
    private static final char PATTERN_PERCENT = '%';
    static final char PATTERN_PAD_ESCAPE = '*';
    private static final char PATTERN_MINUS = '-';
    private static final char PATTERN_SEPARATOR = ';';
    private static final char CURRENCY_SIGN = '¤';
    private static final char QUOTE = '\'';
    static final int DOUBLE_INTEGER_DIGITS = 309;
    static final int DOUBLE_FRACTION_DIGITS = 340;
    static final int MAX_SCIENTIFIC_INTEGER_DIGITS = 8;
    private static final long serialVersionUID = 864413376551465018L;
    private ArrayList attributes;
    private String formatPattern;
    private int style;
    private int currencySignCount;
    private transient Set affixPatternsForCurrency;
    private transient boolean isReadyForParsing;
    private CurrencyPluralInfo currencyPluralInfo;
    static final Unit NULL_UNIT;
    
    public DecimalFormat() {
        this.PARSE_MAX_EXPONENT = 1000;
        this.digitList = new DigitList();
        this.positivePrefix = "";
        this.positiveSuffix = "";
        this.negativePrefix = "-";
        this.negativeSuffix = "";
        this.multiplier = 1;
        this.groupingSize = 3;
        this.groupingSize2 = 0;
        this.decimalSeparatorAlwaysShown = false;
        this.symbols = null;
        this.useSignificantDigits = false;
        this.minSignificantDigits = 1;
        this.maxSignificantDigits = 6;
        this.exponentSignAlwaysShown = false;
        this.roundingIncrement = null;
        this.roundingIncrementICU = null;
        this.roundingDouble = 0.0;
        this.roundingDoubleReciprocal = 0.0;
        this.roundingMode = 6;
        this.mathContext = new MathContext(0, 0);
        this.formatWidth = 0;
        this.pad = ' ';
        this.padPosition = 0;
        this.parseBigDecimal = false;
        this.serialVersionOnStream = 3;
        this.attributes = new ArrayList();
        this.formatPattern = "";
        this.style = 0;
        this.currencySignCount = 0;
        this.affixPatternsForCurrency = null;
        this.isReadyForParsing = false;
        this.currencyPluralInfo = null;
        final ULocale default1 = ULocale.getDefault(ULocale.Category.FORMAT);
        final String pattern = NumberFormat.getPattern(default1, 0);
        this.symbols = new DecimalFormatSymbols(default1);
        this.setCurrency(Currency.getInstance(default1));
        this.applyPatternWithoutExpandAffix(pattern, false);
        if (this.currencySignCount == 3) {
            this.currencyPluralInfo = new CurrencyPluralInfo(default1);
        }
        else {
            this.expandAffixAdjustWidth(null);
        }
    }
    
    public DecimalFormat(final String s) {
        this.PARSE_MAX_EXPONENT = 1000;
        this.digitList = new DigitList();
        this.positivePrefix = "";
        this.positiveSuffix = "";
        this.negativePrefix = "-";
        this.negativeSuffix = "";
        this.multiplier = 1;
        this.groupingSize = 3;
        this.groupingSize2 = 0;
        this.decimalSeparatorAlwaysShown = false;
        this.symbols = null;
        this.useSignificantDigits = false;
        this.minSignificantDigits = 1;
        this.maxSignificantDigits = 6;
        this.exponentSignAlwaysShown = false;
        this.roundingIncrement = null;
        this.roundingIncrementICU = null;
        this.roundingDouble = 0.0;
        this.roundingDoubleReciprocal = 0.0;
        this.roundingMode = 6;
        this.mathContext = new MathContext(0, 0);
        this.formatWidth = 0;
        this.pad = ' ';
        this.padPosition = 0;
        this.parseBigDecimal = false;
        this.serialVersionOnStream = 3;
        this.attributes = new ArrayList();
        this.formatPattern = "";
        this.style = 0;
        this.currencySignCount = 0;
        this.affixPatternsForCurrency = null;
        this.isReadyForParsing = false;
        this.currencyPluralInfo = null;
        final ULocale default1 = ULocale.getDefault(ULocale.Category.FORMAT);
        this.symbols = new DecimalFormatSymbols(default1);
        this.setCurrency(Currency.getInstance(default1));
        this.applyPatternWithoutExpandAffix(s, false);
        if (this.currencySignCount == 3) {
            this.currencyPluralInfo = new CurrencyPluralInfo(default1);
        }
        else {
            this.expandAffixAdjustWidth(null);
        }
    }
    
    public DecimalFormat(final String s, final DecimalFormatSymbols decimalFormatSymbols) {
        this.PARSE_MAX_EXPONENT = 1000;
        this.digitList = new DigitList();
        this.positivePrefix = "";
        this.positiveSuffix = "";
        this.negativePrefix = "-";
        this.negativeSuffix = "";
        this.multiplier = 1;
        this.groupingSize = 3;
        this.groupingSize2 = 0;
        this.decimalSeparatorAlwaysShown = false;
        this.symbols = null;
        this.useSignificantDigits = false;
        this.minSignificantDigits = 1;
        this.maxSignificantDigits = 6;
        this.exponentSignAlwaysShown = false;
        this.roundingIncrement = null;
        this.roundingIncrementICU = null;
        this.roundingDouble = 0.0;
        this.roundingDoubleReciprocal = 0.0;
        this.roundingMode = 6;
        this.mathContext = new MathContext(0, 0);
        this.formatWidth = 0;
        this.pad = ' ';
        this.padPosition = 0;
        this.parseBigDecimal = false;
        this.serialVersionOnStream = 3;
        this.attributes = new ArrayList();
        this.formatPattern = "";
        this.style = 0;
        this.currencySignCount = 0;
        this.affixPatternsForCurrency = null;
        this.isReadyForParsing = false;
        this.currencyPluralInfo = null;
        this.createFromPatternAndSymbols(s, decimalFormatSymbols);
    }
    
    private void createFromPatternAndSymbols(final String s, final DecimalFormatSymbols decimalFormatSymbols) {
        this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
        this.setCurrencyForSymbols();
        this.applyPatternWithoutExpandAffix(s, false);
        if (this.currencySignCount == 3) {
            this.currencyPluralInfo = new CurrencyPluralInfo(this.symbols.getULocale());
        }
        else {
            this.expandAffixAdjustWidth(null);
        }
    }
    
    public DecimalFormat(final String s, final DecimalFormatSymbols decimalFormatSymbols, final CurrencyPluralInfo currencyPluralInfo, final int n) {
        this.PARSE_MAX_EXPONENT = 1000;
        this.digitList = new DigitList();
        this.positivePrefix = "";
        this.positiveSuffix = "";
        this.negativePrefix = "-";
        this.negativeSuffix = "";
        this.multiplier = 1;
        this.groupingSize = 3;
        this.groupingSize2 = 0;
        this.decimalSeparatorAlwaysShown = false;
        this.symbols = null;
        this.useSignificantDigits = false;
        this.minSignificantDigits = 1;
        this.maxSignificantDigits = 6;
        this.exponentSignAlwaysShown = false;
        this.roundingIncrement = null;
        this.roundingIncrementICU = null;
        this.roundingDouble = 0.0;
        this.roundingDoubleReciprocal = 0.0;
        this.roundingMode = 6;
        this.mathContext = new MathContext(0, 0);
        this.formatWidth = 0;
        this.pad = ' ';
        this.padPosition = 0;
        this.parseBigDecimal = false;
        this.serialVersionOnStream = 3;
        this.attributes = new ArrayList();
        this.formatPattern = "";
        this.style = 0;
        this.currencySignCount = 0;
        this.affixPatternsForCurrency = null;
        this.isReadyForParsing = false;
        this.currencyPluralInfo = null;
        CurrencyPluralInfo currencyPluralInfo2 = currencyPluralInfo;
        if (n == 6) {
            currencyPluralInfo2 = (CurrencyPluralInfo)currencyPluralInfo.clone();
        }
        this.create(s, decimalFormatSymbols, currencyPluralInfo2, n);
    }
    
    private void create(final String s, final DecimalFormatSymbols decimalFormatSymbols, final CurrencyPluralInfo currencyPluralInfo, final int style) {
        if (style != 6) {
            this.createFromPatternAndSymbols(s, decimalFormatSymbols);
        }
        else {
            this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
            this.currencyPluralInfo = currencyPluralInfo;
            this.applyPatternWithoutExpandAffix(this.currencyPluralInfo.getCurrencyPluralPattern("other"), false);
            this.setCurrencyForSymbols();
        }
        this.style = style;
    }
    
    DecimalFormat(final String s, final DecimalFormatSymbols decimalFormatSymbols, final int n) {
        this.PARSE_MAX_EXPONENT = 1000;
        this.digitList = new DigitList();
        this.positivePrefix = "";
        this.positiveSuffix = "";
        this.negativePrefix = "-";
        this.negativeSuffix = "";
        this.multiplier = 1;
        this.groupingSize = 3;
        this.groupingSize2 = 0;
        this.decimalSeparatorAlwaysShown = false;
        this.symbols = null;
        this.useSignificantDigits = false;
        this.minSignificantDigits = 1;
        this.maxSignificantDigits = 6;
        this.exponentSignAlwaysShown = false;
        this.roundingIncrement = null;
        this.roundingIncrementICU = null;
        this.roundingDouble = 0.0;
        this.roundingDoubleReciprocal = 0.0;
        this.roundingMode = 6;
        this.mathContext = new MathContext(0, 0);
        this.formatWidth = 0;
        this.pad = ' ';
        this.padPosition = 0;
        this.parseBigDecimal = false;
        this.serialVersionOnStream = 3;
        this.attributes = new ArrayList();
        this.formatPattern = "";
        this.style = 0;
        this.currencySignCount = 0;
        this.affixPatternsForCurrency = null;
        this.isReadyForParsing = false;
        this.currencyPluralInfo = null;
        CurrencyPluralInfo currencyPluralInfo = null;
        if (n == 6) {
            currencyPluralInfo = new CurrencyPluralInfo(decimalFormatSymbols.getULocale());
        }
        this.create(s, decimalFormatSymbols, currencyPluralInfo, n);
    }
    
    @Override
    public StringBuffer format(final double n, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.format(n, sb, fieldPosition, false);
    }
    
    private boolean isNegative(final double n) {
        return n < 0.0 || (n == 0.0 && 1.0 / n < 0.0);
    }
    
    private double round(double n) {
        final boolean negative = this.isNegative(n);
        if (negative) {
            n = -n;
        }
        if (this.roundingDouble > 0.0) {
            return round(n, this.roundingDouble, this.roundingDoubleReciprocal, this.roundingMode, negative);
        }
        return n;
    }
    
    private double multiply(final double n) {
        if (this.multiplier != 1) {
            return n * this.multiplier;
        }
        return n;
    }
    
    private StringBuffer format(double n, final StringBuffer sb, final FieldPosition fieldPosition, final boolean b) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        if (Double.isNaN(n)) {
            if (fieldPosition.getField() == 0) {
                fieldPosition.setBeginIndex(sb.length());
            }
            else if (fieldPosition.getFieldAttribute() == Field.INTEGER) {
                fieldPosition.setBeginIndex(sb.length());
            }
            sb.append(this.symbols.getNaN());
            if (b) {
                this.addAttribute(Field.INTEGER, sb.length() - this.symbols.getNaN().length(), sb.length());
            }
            if (fieldPosition.getField() == 0) {
                fieldPosition.setEndIndex(sb.length());
            }
            else if (fieldPosition.getFieldAttribute() == Field.INTEGER) {
                fieldPosition.setEndIndex(sb.length());
            }
            this.addPadding(sb, fieldPosition, 0, 0);
            return sb;
        }
        n = this.multiply(n);
        final boolean negative = this.isNegative(n);
        n = this.round(n);
        if (Double.isInfinite(n)) {
            final int appendAffix = this.appendAffix(sb, negative, true, b);
            if (fieldPosition.getField() == 0) {
                fieldPosition.setBeginIndex(sb.length());
            }
            else if (fieldPosition.getFieldAttribute() == Field.INTEGER) {
                fieldPosition.setBeginIndex(sb.length());
            }
            sb.append(this.symbols.getInfinity());
            if (b) {
                this.addAttribute(Field.INTEGER, sb.length() - this.symbols.getInfinity().length(), sb.length());
            }
            if (fieldPosition.getField() == 0) {
                fieldPosition.setEndIndex(sb.length());
            }
            else if (fieldPosition.getFieldAttribute() == Field.INTEGER) {
                fieldPosition.setEndIndex(sb.length());
            }
            this.addPadding(sb, fieldPosition, appendAffix, this.appendAffix(sb, negative, false, b));
            return sb;
        }
        // monitorenter(digitList = this.digitList)
        this.digitList.set(n, this.precision(false), !this.useExponentialNotation && !this.areSignificantDigitsUsed());
        // monitorexit(digitList)
        return this.subformat(n, sb, fieldPosition, negative, false, b);
    }
    
    @Deprecated
    double adjustNumberAsInFormatting(double round) {
        if (Double.isNaN(round)) {
            return round;
        }
        round = this.round(this.multiply(round));
        if (Double.isInfinite(round)) {
            return round;
        }
        final DigitList list = new DigitList();
        list.set(round, this.precision(false), false);
        return list.getDouble();
    }
    
    @Deprecated
    boolean isNumberNegative(final double n) {
        return !Double.isNaN(n) && this.isNegative(this.multiply(n));
    }
    
    private static double round(double n, final double n2, final double n3, final int n4, final boolean b) {
        final double n5 = (n3 == 0.0) ? (n / n2) : (n * n3);
        double n6 = 0.0;
        Label_0366: {
            switch (n4) {
                case 2: {
                    n6 = (b ? Math.floor(n5 + 0.0) : Math.ceil(n5 - 0.0));
                    break;
                }
                case 3: {
                    n6 = (b ? Math.ceil(n5 - 0.0) : Math.floor(n5 + 0.0));
                    break;
                }
                case 1: {
                    n6 = Math.floor(n5 + 0.0);
                    break;
                }
                case 0: {
                    n6 = Math.ceil(n5 - 0.0);
                    break;
                }
                case 7: {
                    if (n5 != Math.floor(n5)) {
                        throw new ArithmeticException("Rounding necessary");
                    }
                    return n;
                }
                default: {
                    final double ceil = Math.ceil(n5);
                    final double n7 = ceil - n5;
                    final double floor = Math.floor(n5);
                    final double n8 = n5 - floor;
                    switch (n4) {
                        case 6: {
                            if (n8 + 0.0 < n7) {
                                n6 = floor;
                                break Label_0366;
                            }
                            if (n7 + 0.0 < n8) {
                                n6 = ceil;
                                break Label_0366;
                            }
                            final double n9 = floor / 2.0;
                            n6 = ((n9 == Math.floor(n9)) ? floor : ceil);
                            break Label_0366;
                        }
                        case 5: {
                            n6 = ((n8 <= n7 + 0.0) ? floor : ceil);
                            break Label_0366;
                        }
                        case 4: {
                            n6 = ((n7 <= n8 + 0.0) ? ceil : floor);
                            break Label_0366;
                        }
                        default: {
                            throw new IllegalArgumentException("Invalid rounding mode: " + n4);
                        }
                    }
                    break;
                }
            }
        }
        n = ((n3 == 0.0) ? (n6 * n2) : (n6 / n3));
        return n;
    }
    
    @Override
    public StringBuffer format(final long n, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.format(n, sb, fieldPosition, false);
    }
    
    private StringBuffer format(long n, final StringBuffer sb, final FieldPosition fieldPosition, final boolean b) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        if (this.roundingIncrementICU != null) {
            return this.format(com.ibm.icu.math.BigDecimal.valueOf(n), sb, fieldPosition);
        }
        final boolean b2 = n < 0L;
        if (b2) {
            n = -n;
        }
        if (this.multiplier != 1) {
            if (n < 0L) {
                final boolean b3 = n <= Long.MIN_VALUE / this.multiplier;
            }
            else {
                final boolean b4 = n > Long.MAX_VALUE / this.multiplier;
            }
        }
        n *= this.multiplier;
        // monitorenter(digitList = this.digitList)
        this.digitList.set(n, this.precision(true));
        // monitorexit(digitList)
        return this.subformat((double)n, sb, fieldPosition, b2, true, b);
    }
    
    @Override
    public StringBuffer format(final BigInteger bigInteger, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.format(bigInteger, sb, fieldPosition, false);
    }
    
    private StringBuffer format(BigInteger multiply, final StringBuffer sb, final FieldPosition fieldPosition, final boolean b) {
        if (this.roundingIncrementICU != null) {
            return this.format(new com.ibm.icu.math.BigDecimal(multiply), sb, fieldPosition);
        }
        if (this.multiplier != 1) {
            multiply = multiply.multiply(BigInteger.valueOf(this.multiplier));
        }
        // monitorenter(digitList = this.digitList)
        this.digitList.set(multiply, this.precision(true));
        // monitorexit(digitList)
        return this.subformat(multiply.intValue(), sb, fieldPosition, multiply.signum() < 0, true, b);
    }
    
    @Override
    public StringBuffer format(final BigDecimal bigDecimal, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.format(bigDecimal, sb, fieldPosition, false);
    }
    
    private StringBuffer format(BigDecimal bigDecimal, final StringBuffer sb, final FieldPosition fieldPosition, final boolean b) {
        if (this.multiplier != 1) {
            bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(this.multiplier));
        }
        if (this.roundingIncrement != null) {
            bigDecimal = bigDecimal.divide(this.roundingIncrement, 0, this.roundingMode).multiply(this.roundingIncrement);
        }
        // monitorenter(digitList = this.digitList)
        this.digitList.set(bigDecimal, this.precision(false), !this.useExponentialNotation && !this.areSignificantDigitsUsed());
        // monitorexit(digitList)
        return this.subformat(bigDecimal.doubleValue(), sb, fieldPosition, bigDecimal.signum() < 0, false, b);
    }
    
    @Override
    public StringBuffer format(com.ibm.icu.math.BigDecimal bigDecimal, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (this.multiplier != 1) {
            bigDecimal = bigDecimal.multiply(com.ibm.icu.math.BigDecimal.valueOf(this.multiplier), this.mathContext);
        }
        if (this.roundingIncrementICU != null) {
            bigDecimal = bigDecimal.divide(this.roundingIncrementICU, 0, this.roundingMode).multiply(this.roundingIncrementICU, this.mathContext);
        }
        // monitorenter(digitList = this.digitList)
        this.digitList.set(bigDecimal, this.precision(false), !this.useExponentialNotation && !this.areSignificantDigitsUsed());
        // monitorexit(digitList)
        return this.subformat(bigDecimal.doubleValue(), sb, fieldPosition, bigDecimal.signum() < 0, false, false);
    }
    
    private int precision(final boolean b) {
        if (this.areSignificantDigitsUsed()) {
            return this.getMaximumSignificantDigits();
        }
        if (this.useExponentialNotation) {
            return this.getMinimumIntegerDigits() + this.getMaximumFractionDigits();
        }
        return b ? 0 : this.getMaximumFractionDigits();
    }
    
    private StringBuffer subformat(final int n, final StringBuffer sb, final FieldPosition fieldPosition, final boolean b, final boolean b2, final boolean b3) {
        if (this.currencySignCount == 3) {
            return this.subformat(this.currencyPluralInfo.select(n), sb, fieldPosition, b, b2, b3);
        }
        return this.subformat(sb, fieldPosition, b, b2, b3);
    }
    
    private StringBuffer subformat(final double n, final StringBuffer sb, final FieldPosition fieldPosition, final boolean b, final boolean b2, final boolean b3) {
        if (this.currencySignCount == 3) {
            return this.subformat(this.currencyPluralInfo.select(n), sb, fieldPosition, b, b2, b3);
        }
        return this.subformat(sb, fieldPosition, b, b2, b3);
    }
    
    private StringBuffer subformat(final String s, final StringBuffer sb, final FieldPosition fieldPosition, final boolean b, final boolean b2, final boolean b3) {
        if (this.style == 6) {
            final String currencyPluralPattern = this.currencyPluralInfo.getCurrencyPluralPattern(s);
            if (!this.formatPattern.equals(currencyPluralPattern)) {
                this.applyPatternWithoutExpandAffix(currencyPluralPattern, false);
            }
        }
        this.expandAffixAdjustWidth(s);
        return this.subformat(sb, fieldPosition, b, b2, b3);
    }
    
    private StringBuffer subformat(final StringBuffer sb, final FieldPosition fieldPosition, final boolean b, final boolean b2, final boolean b3) {
        if (this.digitList.isZero()) {
            this.digitList.decimalAt = 0;
        }
        final int appendAffix = this.appendAffix(sb, b, true, b3);
        if (this.useExponentialNotation) {
            this.subformatExponential(sb, fieldPosition, b3);
        }
        else {
            this.subformatFixed(sb, fieldPosition, b2, b3);
        }
        this.addPadding(sb, fieldPosition, appendAffix, this.appendAffix(sb, b, false, b3));
        return sb;
    }
    
    private void subformatFixed(final StringBuffer p0, final FieldPosition p1, final boolean p2, final boolean p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/ibm/icu/text/DecimalFormat.symbols:Lcom/ibm/icu/text/DecimalFormatSymbols;
        //     4: invokevirtual   com/ibm/icu/text/DecimalFormatSymbols.getDigitsLocal:()[C
        //     7: astore          5
        //     9: aload_0        
        //    10: getfield        com/ibm/icu/text/DecimalFormat.currencySignCount:I
        //    13: ifle            26
        //    16: aload_0        
        //    17: getfield        com/ibm/icu/text/DecimalFormat.symbols:Lcom/ibm/icu/text/DecimalFormatSymbols;
        //    20: invokevirtual   com/ibm/icu/text/DecimalFormatSymbols.getMonetaryGroupingSeparator:()C
        //    23: goto            33
        //    26: aload_0        
        //    27: getfield        com/ibm/icu/text/DecimalFormat.symbols:Lcom/ibm/icu/text/DecimalFormatSymbols;
        //    30: invokevirtual   com/ibm/icu/text/DecimalFormatSymbols.getGroupingSeparator:()C
        //    33: istore          6
        //    35: aload_0        
        //    36: getfield        com/ibm/icu/text/DecimalFormat.currencySignCount:I
        //    39: ifle            52
        //    42: aload_0        
        //    43: getfield        com/ibm/icu/text/DecimalFormat.symbols:Lcom/ibm/icu/text/DecimalFormatSymbols;
        //    46: invokevirtual   com/ibm/icu/text/DecimalFormatSymbols.getMonetaryDecimalSeparator:()C
        //    49: goto            59
        //    52: aload_0        
        //    53: getfield        com/ibm/icu/text/DecimalFormat.symbols:Lcom/ibm/icu/text/DecimalFormatSymbols;
        //    56: invokevirtual   com/ibm/icu/text/DecimalFormatSymbols.getDecimalSeparator:()C
        //    59: istore          7
        //    61: aload_0        
        //    62: invokevirtual   com/ibm/icu/text/DecimalFormat.areSignificantDigitsUsed:()Z
        //    65: istore          8
        //    67: aload_0        
        //    68: invokevirtual   com/ibm/icu/text/DecimalFormat.getMaximumIntegerDigits:()I
        //    71: istore          9
        //    73: aload_0        
        //    74: invokevirtual   com/ibm/icu/text/DecimalFormat.getMinimumIntegerDigits:()I
        //    77: istore          10
        //    79: aload_1        
        //    80: invokevirtual   java/lang/StringBuffer.length:()I
        //    83: istore          12
        //    85: aload_2        
        //    86: invokevirtual   java/text/FieldPosition.getField:()I
        //    89: ifne            103
        //    92: aload_2        
        //    93: aload_1        
        //    94: invokevirtual   java/lang/StringBuffer.length:()I
        //    97: invokevirtual   java/text/FieldPosition.setBeginIndex:(I)V
        //   100: goto            121
        //   103: aload_2        
        //   104: invokevirtual   java/text/FieldPosition.getFieldAttribute:()Ljava/text/Format$Field;
        //   107: getstatic       com/ibm/icu/text/NumberFormat$Field.INTEGER:Lcom/ibm/icu/text/NumberFormat$Field;
        //   110: if_acmpne       121
        //   113: aload_2        
        //   114: aload_1        
        //   115: invokevirtual   java/lang/StringBuffer.length:()I
        //   118: invokevirtual   java/text/FieldPosition.setBeginIndex:(I)V
        //   121: aload_0        
        //   122: invokevirtual   com/ibm/icu/text/DecimalFormat.getMinimumSignificantDigits:()I
        //   125: istore          14
        //   127: aload_0        
        //   128: invokevirtual   com/ibm/icu/text/DecimalFormat.getMaximumSignificantDigits:()I
        //   131: istore          15
        //   133: iload           8
        //   135: ifne            138
        //   138: iload           8
        //   140: ifeq            157
        //   143: iconst_1       
        //   144: aload_0        
        //   145: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   148: getfield        com/ibm/icu/text/DigitList.decimalAt:I
        //   151: invokestatic    java/lang/Math.max:(II)I
        //   154: goto            159
        //   157: iload           10
        //   159: istore          16
        //   161: aload_0        
        //   162: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   165: getfield        com/ibm/icu/text/DigitList.decimalAt:I
        //   168: ifle            191
        //   171: iconst_0       
        //   172: aload_0        
        //   173: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   176: getfield        com/ibm/icu/text/DigitList.decimalAt:I
        //   179: if_icmpge       191
        //   182: aload_0        
        //   183: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   186: getfield        com/ibm/icu/text/DigitList.decimalAt:I
        //   189: istore          16
        //   191: iconst_0       
        //   192: iload           9
        //   194: if_icmple       217
        //   197: iload           9
        //   199: iflt            217
        //   202: iload           9
        //   204: istore          16
        //   206: aload_0        
        //   207: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   210: getfield        com/ibm/icu/text/DigitList.decimalAt:I
        //   213: iconst_0       
        //   214: isub           
        //   215: istore          17
        //   217: aload_1        
        //   218: invokevirtual   java/lang/StringBuffer.length:()I
        //   221: istore          18
        //   223: iconst_0       
        //   224: aload_0        
        //   225: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   228: getfield        com/ibm/icu/text/DigitList.decimalAt:I
        //   231: if_icmpge       270
        //   234: iconst_0       
        //   235: aload_0        
        //   236: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   239: getfield        com/ibm/icu/text/DigitList.count:I
        //   242: if_icmpge       270
        //   245: aload_1        
        //   246: aload           5
        //   248: aload_0        
        //   249: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   252: iconst_0       
        //   253: iinc            17, 1
        //   256: invokevirtual   com/ibm/icu/text/DigitList.getDigitValue:(I)B
        //   259: caload         
        //   260: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   263: pop            
        //   264: iinc            13, 1
        //   267: goto            285
        //   270: aload_1        
        //   271: aload           5
        //   273: iconst_0       
        //   274: caload         
        //   275: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   278: pop            
        //   279: goto            285
        //   282: iinc            13, 1
        //   285: aload_0        
        //   286: goto            318
        //   289: aload_1        
        //   290: iload           6
        //   292: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   295: pop            
        //   296: iload           4
        //   298: ifeq            318
        //   301: aload_0        
        //   302: getstatic       com/ibm/icu/text/NumberFormat$Field.GROUPING_SEPARATOR:Lcom/ibm/icu/text/NumberFormat$Field;
        //   305: aload_1        
        //   306: invokevirtual   java/lang/StringBuffer.length:()I
        //   309: iconst_1       
        //   310: isub           
        //   311: aload_1        
        //   312: invokevirtual   java/lang/StringBuffer.length:()I
        //   315: invokespecial   com/ibm/icu/text/DecimalFormat.addAttribute:(Lcom/ibm/icu/text/NumberFormat$Field;II)V
        //   318: iinc            11, -1
        //   321: goto            223
        //   324: aload_2        
        //   325: invokevirtual   java/text/FieldPosition.getField:()I
        //   328: ifne            342
        //   331: aload_2        
        //   332: aload_1        
        //   333: invokevirtual   java/lang/StringBuffer.length:()I
        //   336: invokevirtual   java/text/FieldPosition.setEndIndex:(I)V
        //   339: goto            360
        //   342: aload_2        
        //   343: invokevirtual   java/text/FieldPosition.getFieldAttribute:()Ljava/text/Format$Field;
        //   346: getstatic       com/ibm/icu/text/NumberFormat$Field.INTEGER:Lcom/ibm/icu/text/NumberFormat$Field;
        //   349: if_acmpne       360
        //   352: aload_2        
        //   353: aload_1        
        //   354: invokevirtual   java/lang/StringBuffer.length:()I
        //   357: invokevirtual   java/text/FieldPosition.setEndIndex:(I)V
        //   360: iload_3        
        //   361: ifne            375
        //   364: iconst_0       
        //   365: aload_0        
        //   366: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   369: getfield        com/ibm/icu/text/DigitList.count:I
        //   372: if_icmplt       393
        //   375: iload           8
        //   377: ifeq            386
        //   380: goto            397
        //   383: goto            393
        //   386: aload_0        
        //   387: invokevirtual   com/ibm/icu/text/DecimalFormat.getMinimumFractionDigits:()I
        //   390: ifle            397
        //   393: iconst_1       
        //   394: goto            398
        //   397: iconst_0       
        //   398: istore          19
        //   400: iload           19
        //   402: ifne            423
        //   405: aload_1        
        //   406: invokevirtual   java/lang/StringBuffer.length:()I
        //   409: iload           18
        //   411: if_icmpne       423
        //   414: aload_1        
        //   415: aload           5
        //   417: iconst_0       
        //   418: caload         
        //   419: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   422: pop            
        //   423: iload           4
        //   425: ifeq            441
        //   428: aload_0        
        //   429: getstatic       com/ibm/icu/text/NumberFormat$Field.INTEGER:Lcom/ibm/icu/text/NumberFormat$Field;
        //   432: iload           12
        //   434: aload_1        
        //   435: invokevirtual   java/lang/StringBuffer.length:()I
        //   438: invokespecial   com/ibm/icu/text/DecimalFormat.addAttribute:(Lcom/ibm/icu/text/NumberFormat$Field;II)V
        //   441: aload_0        
        //   442: getfield        com/ibm/icu/text/DecimalFormat.decimalSeparatorAlwaysShown:Z
        //   445: ifne            453
        //   448: iload           19
        //   450: ifeq            518
        //   453: aload_2        
        //   454: invokevirtual   java/text/FieldPosition.getFieldAttribute:()Ljava/text/Format$Field;
        //   457: getstatic       com/ibm/icu/text/NumberFormat$Field.DECIMAL_SEPARATOR:Lcom/ibm/icu/text/NumberFormat$Field;
        //   460: if_acmpne       471
        //   463: aload_2        
        //   464: aload_1        
        //   465: invokevirtual   java/lang/StringBuffer.length:()I
        //   468: invokevirtual   java/text/FieldPosition.setBeginIndex:(I)V
        //   471: aload_1        
        //   472: iload           7
        //   474: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   477: pop            
        //   478: aload_2        
        //   479: invokevirtual   java/text/FieldPosition.getFieldAttribute:()Ljava/text/Format$Field;
        //   482: getstatic       com/ibm/icu/text/NumberFormat$Field.DECIMAL_SEPARATOR:Lcom/ibm/icu/text/NumberFormat$Field;
        //   485: if_acmpne       496
        //   488: aload_2        
        //   489: aload_1        
        //   490: invokevirtual   java/lang/StringBuffer.length:()I
        //   493: invokevirtual   java/text/FieldPosition.setEndIndex:(I)V
        //   496: iload           4
        //   498: ifeq            518
        //   501: aload_0        
        //   502: getstatic       com/ibm/icu/text/NumberFormat$Field.DECIMAL_SEPARATOR:Lcom/ibm/icu/text/NumberFormat$Field;
        //   505: aload_1        
        //   506: invokevirtual   java/lang/StringBuffer.length:()I
        //   509: iconst_1       
        //   510: isub           
        //   511: aload_1        
        //   512: invokevirtual   java/lang/StringBuffer.length:()I
        //   515: invokespecial   com/ibm/icu/text/DecimalFormat.addAttribute:(Lcom/ibm/icu/text/NumberFormat$Field;II)V
        //   518: aload_2        
        //   519: invokevirtual   java/text/FieldPosition.getField:()I
        //   522: iconst_1       
        //   523: if_icmpne       537
        //   526: aload_2        
        //   527: aload_1        
        //   528: invokevirtual   java/lang/StringBuffer.length:()I
        //   531: invokevirtual   java/text/FieldPosition.setBeginIndex:(I)V
        //   534: goto            555
        //   537: aload_2        
        //   538: invokevirtual   java/text/FieldPosition.getFieldAttribute:()Ljava/text/Format$Field;
        //   541: getstatic       com/ibm/icu/text/NumberFormat$Field.FRACTION:Lcom/ibm/icu/text/NumberFormat$Field;
        //   544: if_acmpne       555
        //   547: aload_2        
        //   548: aload_1        
        //   549: invokevirtual   java/lang/StringBuffer.length:()I
        //   552: invokevirtual   java/text/FieldPosition.setBeginIndex:(I)V
        //   555: aload_1        
        //   556: invokevirtual   java/lang/StringBuffer.length:()I
        //   559: istore          20
        //   561: iload           8
        //   563: ifeq            572
        //   566: ldc_w           2147483647
        //   569: goto            576
        //   572: aload_0        
        //   573: invokevirtual   com/ibm/icu/text/DecimalFormat.getMaximumFractionDigits:()I
        //   576: istore          16
        //   578: iload           8
        //   580: ifeq            594
        //   583: iconst_0       
        //   584: aload_0        
        //   585: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   588: getfield        com/ibm/icu/text/DigitList.count:I
        //   591: if_icmpne       594
        //   594: goto            727
        //   597: iload           8
        //   599: ifne            628
        //   602: iconst_0       
        //   603: aload_0        
        //   604: invokevirtual   com/ibm/icu/text/DecimalFormat.getMinimumFractionDigits:()I
        //   607: if_icmplt       628
        //   610: iload_3        
        //   611: ifne            727
        //   614: iconst_0       
        //   615: aload_0        
        //   616: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   619: getfield        com/ibm/icu/text/DigitList.count:I
        //   622: if_icmplt       628
        //   625: goto            727
        //   628: iconst_m1      
        //   629: aload_0        
        //   630: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   633: getfield        com/ibm/icu/text/DigitList.decimalAt:I
        //   636: iconst_1       
        //   637: isub           
        //   638: if_icmple       653
        //   641: aload_1        
        //   642: aload           5
        //   644: iconst_0       
        //   645: caload         
        //   646: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   649: pop            
        //   650: goto            721
        //   653: iload_3        
        //   654: ifne            690
        //   657: iconst_0       
        //   658: aload_0        
        //   659: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   662: getfield        com/ibm/icu/text/DigitList.count:I
        //   665: if_icmpge       690
        //   668: aload_1        
        //   669: aload           5
        //   671: aload_0        
        //   672: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   675: iconst_0       
        //   676: iinc            17, 1
        //   679: invokevirtual   com/ibm/icu/text/DigitList.getDigitValue:(I)B
        //   682: caload         
        //   683: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   686: pop            
        //   687: goto            699
        //   690: aload_1        
        //   691: aload           5
        //   693: iconst_0       
        //   694: caload         
        //   695: invokevirtual   java/lang/StringBuffer.append:(C)Ljava/lang/StringBuffer;
        //   698: pop            
        //   699: iinc            13, 1
        //   702: iload           8
        //   704: ifeq            721
        //   707: iconst_0       
        //   708: aload_0        
        //   709: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   712: getfield        com/ibm/icu/text/DigitList.count:I
        //   715: if_icmpne       721
        //   718: goto            727
        //   721: iinc            11, 1
        //   724: goto            594
        //   727: aload_2        
        //   728: invokevirtual   java/text/FieldPosition.getField:()I
        //   731: iconst_1       
        //   732: if_icmpne       746
        //   735: aload_2        
        //   736: aload_1        
        //   737: invokevirtual   java/lang/StringBuffer.length:()I
        //   740: invokevirtual   java/text/FieldPosition.setEndIndex:(I)V
        //   743: goto            764
        //   746: aload_2        
        //   747: invokevirtual   java/text/FieldPosition.getFieldAttribute:()Ljava/text/Format$Field;
        //   750: getstatic       com/ibm/icu/text/NumberFormat$Field.FRACTION:Lcom/ibm/icu/text/NumberFormat$Field;
        //   753: if_acmpne       764
        //   756: aload_2        
        //   757: aload_1        
        //   758: invokevirtual   java/lang/StringBuffer.length:()I
        //   761: invokevirtual   java/text/FieldPosition.setEndIndex:(I)V
        //   764: iload           4
        //   766: ifeq            794
        //   769: aload_0        
        //   770: getfield        com/ibm/icu/text/DecimalFormat.decimalSeparatorAlwaysShown:Z
        //   773: ifne            781
        //   776: iload           19
        //   778: ifeq            794
        //   781: aload_0        
        //   782: getstatic       com/ibm/icu/text/NumberFormat$Field.FRACTION:Lcom/ibm/icu/text/NumberFormat$Field;
        //   785: iload           20
        //   787: aload_1        
        //   788: invokevirtual   java/lang/StringBuffer.length:()I
        //   791: invokespecial   com/ibm/icu/text/DecimalFormat.addAttribute:(Lcom/ibm/icu/text/NumberFormat$Field;II)V
        //   794: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0223 (coming from #0321).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void subformatExponential(final StringBuffer sb, final FieldPosition fieldPosition, final boolean b) {
        final char[] digitsLocal = this.symbols.getDigitsLocal();
        final char c = (this.currencySignCount > 0) ? this.symbols.getMonetaryDecimalSeparator() : this.symbols.getDecimalSeparator();
        final boolean significantDigitsUsed = this.areSignificantDigitsUsed();
        this.getMaximumIntegerDigits();
        this.getMinimumIntegerDigits();
        if (fieldPosition.getField() == 0) {
            fieldPosition.setBeginIndex(sb.length());
            fieldPosition.setEndIndex(-1);
        }
        else if (fieldPosition.getField() == 1) {
            fieldPosition.setBeginIndex(-1);
        }
        else if (fieldPosition.getFieldAttribute() == Field.INTEGER) {
            fieldPosition.setBeginIndex(sb.length());
            fieldPosition.setEndIndex(-1);
        }
        else if (fieldPosition.getFieldAttribute() == Field.FRACTION) {
            fieldPosition.setBeginIndex(-1);
        }
        final int length = sb.length();
        if (significantDigitsUsed) {
            final int n = this.getMinimumSignificantDigits() - 1;
        }
        else {
            this.getMinimumFractionDigits();
        }
        final int decimalAt = this.digitList.decimalAt;
        final int n2 = this.digitList.isZero() ? 1 : (this.digitList.decimalAt - 0);
        final int count = this.digitList.count;
        if (n2 > 1) {}
        while (true) {
            if (n2 == 0) {
                if (fieldPosition.getField() == 0) {
                    fieldPosition.setEndIndex(sb.length());
                }
                else if (fieldPosition.getFieldAttribute() == Field.INTEGER) {
                    fieldPosition.setEndIndex(sb.length());
                }
                if (b) {
                    sb.length();
                    this.addAttribute(Field.INTEGER, length, sb.length());
                }
                sb.append(c);
                if (b) {
                    this.addAttribute(Field.DECIMAL_SEPARATOR, sb.length() - 1, sb.length());
                    sb.length();
                }
                if (fieldPosition.getField() == 1) {
                    fieldPosition.setBeginIndex(sb.length());
                }
                else if (fieldPosition.getFieldAttribute() == Field.FRACTION) {
                    fieldPosition.setBeginIndex(sb.length());
                }
            }
            sb.append((0 < this.digitList.count) ? digitsLocal[this.digitList.getDigitValue(0)] : digitsLocal[0]);
            int n3 = 0;
            ++n3;
        }
    }
    
    private final void addPadding(final StringBuffer sb, final FieldPosition fieldPosition, final int n, final int n2) {
        if (this.formatWidth > 0) {
            final int n3 = this.formatWidth - sb.length();
            if (n3 > 0) {
                final char[] array = new char[n3];
                while (0 < n3) {
                    array[0] = this.pad;
                    int n4 = 0;
                    ++n4;
                }
                switch (this.padPosition) {
                    case 1: {
                        sb.insert(n, array);
                        break;
                    }
                    case 0: {
                        sb.insert(0, array);
                        break;
                    }
                    case 2: {
                        sb.insert(sb.length() - n2, array);
                        break;
                    }
                    case 3: {
                        sb.append(array);
                        break;
                    }
                }
                if (this.padPosition == 0 || this.padPosition == 1) {
                    fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + n3);
                    fieldPosition.setEndIndex(fieldPosition.getEndIndex() + n3);
                }
            }
        }
    }
    
    @Override
    public Number parse(final String s, final ParsePosition parsePosition) {
        return (Number)this.parse(s, parsePosition, null);
    }
    
    @Override
    public CurrencyAmount parseCurrency(final CharSequence charSequence, final ParsePosition parsePosition) {
        return (CurrencyAmount)this.parse(charSequence.toString(), parsePosition, new Currency[1]);
    }
    
    private Object parse(final String p0, final ParsePosition p1, final Currency[] p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   java/text/ParsePosition.getIndex:()I
        //     4: dup            
        //     5: istore          4
        //     7: istore          5
        //     9: aload_0        
        //    10: getfield        com/ibm/icu/text/DecimalFormat.formatWidth:I
        //    13: ifle            40
        //    16: aload_0        
        //    17: getfield        com/ibm/icu/text/DecimalFormat.padPosition:I
        //    20: ifeq            31
        //    23: aload_0        
        //    24: getfield        com/ibm/icu/text/DecimalFormat.padPosition:I
        //    27: iconst_1       
        //    28: if_icmpne       40
        //    31: aload_0        
        //    32: aload_1        
        //    33: iload           5
        //    35: invokespecial   com/ibm/icu/text/DecimalFormat.skipPadding:(Ljava/lang/String;I)I
        //    38: istore          5
        //    40: aload_1        
        //    41: iload           5
        //    43: aload_0        
        //    44: getfield        com/ibm/icu/text/DecimalFormat.symbols:Lcom/ibm/icu/text/DecimalFormatSymbols;
        //    47: invokevirtual   com/ibm/icu/text/DecimalFormatSymbols.getNaN:()Ljava/lang/String;
        //    50: iconst_0       
        //    51: aload_0        
        //    52: getfield        com/ibm/icu/text/DecimalFormat.symbols:Lcom/ibm/icu/text/DecimalFormatSymbols;
        //    55: invokevirtual   com/ibm/icu/text/DecimalFormatSymbols.getNaN:()Ljava/lang/String;
        //    58: invokevirtual   java/lang/String.length:()I
        //    61: invokevirtual   java/lang/String.regionMatches:(ILjava/lang/String;II)Z
        //    64: ifeq            131
        //    67: iload           5
        //    69: aload_0        
        //    70: getfield        com/ibm/icu/text/DecimalFormat.symbols:Lcom/ibm/icu/text/DecimalFormatSymbols;
        //    73: invokevirtual   com/ibm/icu/text/DecimalFormatSymbols.getNaN:()Ljava/lang/String;
        //    76: invokevirtual   java/lang/String.length:()I
        //    79: iadd           
        //    80: istore          5
        //    82: aload_0        
        //    83: getfield        com/ibm/icu/text/DecimalFormat.formatWidth:I
        //    86: ifle            114
        //    89: aload_0        
        //    90: getfield        com/ibm/icu/text/DecimalFormat.padPosition:I
        //    93: iconst_2       
        //    94: if_icmpeq       105
        //    97: aload_0        
        //    98: getfield        com/ibm/icu/text/DecimalFormat.padPosition:I
        //   101: iconst_3       
        //   102: if_icmpne       114
        //   105: aload_0        
        //   106: aload_1        
        //   107: iload           5
        //   109: invokespecial   com/ibm/icu/text/DecimalFormat.skipPadding:(Ljava/lang/String;I)I
        //   112: istore          5
        //   114: aload_2        
        //   115: iload           5
        //   117: invokevirtual   java/text/ParsePosition.setIndex:(I)V
        //   120: new             Ljava/lang/Double;
        //   123: dup            
        //   124: ldc2_w          NaN
        //   127: invokespecial   java/lang/Double.<init>:(D)V
        //   130: areturn        
        //   131: iload           4
        //   133: istore          5
        //   135: iconst_3       
        //   136: newarray        Z
        //   138: astore          6
        //   140: aload_0        
        //   141: getfield        com/ibm/icu/text/DecimalFormat.currencySignCount:I
        //   144: ifle            158
        //   147: aload_0        
        //   148: aload_1        
        //   149: aload_2        
        //   150: aload_3        
        //   151: aload           6
        //   153: ifne            195
        //   156: aconst_null    
        //   157: areturn        
        //   158: aload_0        
        //   159: aload_1        
        //   160: aload_2        
        //   161: aload_0        
        //   162: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   165: aload           6
        //   167: aload_3        
        //   168: aload_0        
        //   169: getfield        com/ibm/icu/text/DecimalFormat.negPrefixPattern:Ljava/lang/String;
        //   172: aload_0        
        //   173: getfield        com/ibm/icu/text/DecimalFormat.negSuffixPattern:Ljava/lang/String;
        //   176: aload_0        
        //   177: getfield        com/ibm/icu/text/DecimalFormat.posPrefixPattern:Ljava/lang/String;
        //   180: aload_0        
        //   181: getfield        com/ibm/icu/text/DecimalFormat.posSuffixPattern:Ljava/lang/String;
        //   184: goto            195
        //   187: aload_2        
        //   188: iload           4
        //   190: invokevirtual   java/text/ParsePosition.setIndex:(I)V
        //   193: aconst_null    
        //   194: areturn        
        //   195: aconst_null    
        //   196: astore          7
        //   198: aload           6
        //   200: iconst_0       
        //   201: baload         
        //   202: ifeq            233
        //   205: new             Ljava/lang/Double;
        //   208: dup            
        //   209: aload           6
        //   211: iconst_1       
        //   212: baload         
        //   213: ifeq            222
        //   216: ldc2_w          Infinity
        //   219: goto            225
        //   222: ldc2_w          -Infinity
        //   225: invokespecial   java/lang/Double.<init>:(D)V
        //   228: astore          7
        //   230: goto            561
        //   233: aload           6
        //   235: iconst_2       
        //   236: baload         
        //   237: ifeq            275
        //   240: aload           6
        //   242: iconst_1       
        //   243: baload         
        //   244: ifeq            260
        //   247: new             Ljava/lang/Double;
        //   250: dup            
        //   251: ldc_w           "0.0"
        //   254: invokespecial   java/lang/Double.<init>:(Ljava/lang/String;)V
        //   257: goto            270
        //   260: new             Ljava/lang/Double;
        //   263: dup            
        //   264: ldc_w           "-0.0"
        //   267: invokespecial   java/lang/Double.<init>:(Ljava/lang/String;)V
        //   270: astore          7
        //   272: goto            561
        //   275: aload           6
        //   277: iconst_1       
        //   278: baload         
        //   279: ifne            307
        //   282: aload_0        
        //   283: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   286: invokevirtual   com/ibm/icu/text/DigitList.isZero:()Z
        //   289: ifeq            307
        //   292: new             Ljava/lang/Double;
        //   295: dup            
        //   296: ldc_w           "-0.0"
        //   299: invokespecial   java/lang/Double.<init>:(Ljava/lang/String;)V
        //   302: astore          7
        //   304: goto            561
        //   307: aload_0        
        //   308: getfield        com/ibm/icu/text/DecimalFormat.multiplier:I
        //   311: istore          8
        //   313: iload           8
        //   315: bipush          10
        //   317: irem           
        //   318: ifne            344
        //   321: aload_0        
        //   322: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   325: dup            
        //   326: getfield        com/ibm/icu/text/DigitList.decimalAt:I
        //   329: iconst_1       
        //   330: isub           
        //   331: putfield        com/ibm/icu/text/DigitList.decimalAt:I
        //   334: iload           8
        //   336: bipush          10
        //   338: idiv           
        //   339: istore          8
        //   341: goto            313
        //   344: aload_0        
        //   345: getfield        com/ibm/icu/text/DecimalFormat.parseBigDecimal:Z
        //   348: ifne            521
        //   351: iload           8
        //   353: iconst_1       
        //   354: if_icmpne       521
        //   357: aload_0        
        //   358: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   361: invokevirtual   com/ibm/icu/text/DigitList.isIntegral:()Z
        //   364: ifeq            521
        //   367: aload_0        
        //   368: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   371: getfield        com/ibm/icu/text/DigitList.decimalAt:I
        //   374: bipush          12
        //   376: if_icmpge       480
        //   379: lconst_0       
        //   380: lstore          9
        //   382: aload_0        
        //   383: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   386: getfield        com/ibm/icu/text/DigitList.count:I
        //   389: ifle            470
        //   392: iconst_0       
        //   393: aload_0        
        //   394: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   397: getfield        com/ibm/icu/text/DigitList.count:I
        //   400: if_icmpge       433
        //   403: lload           9
        //   405: ldc2_w          10
        //   408: lmul           
        //   409: aload_0        
        //   410: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   413: getfield        com/ibm/icu/text/DigitList.digits:[B
        //   416: iconst_0       
        //   417: iinc            11, 1
        //   420: baload         
        //   421: i2c            
        //   422: i2l            
        //   423: ladd           
        //   424: ldc2_w          48
        //   427: lsub           
        //   428: lstore          9
        //   430: goto            392
        //   433: iconst_0       
        //   434: iinc            11, 1
        //   437: aload_0        
        //   438: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   441: getfield        com/ibm/icu/text/DigitList.decimalAt:I
        //   444: if_icmpge       458
        //   447: lload           9
        //   449: ldc2_w          10
        //   452: lmul           
        //   453: lstore          9
        //   455: goto            433
        //   458: aload           6
        //   460: iconst_1       
        //   461: baload         
        //   462: ifne            470
        //   465: lload           9
        //   467: lneg           
        //   468: lstore          9
        //   470: lload           9
        //   472: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //   475: astore          7
        //   477: goto            561
        //   480: aload_0        
        //   481: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   484: aload           6
        //   486: iconst_1       
        //   487: baload         
        //   488: invokevirtual   com/ibm/icu/text/DigitList.getBigInteger:(Z)Ljava/math/BigInteger;
        //   491: astore          9
        //   493: aload           9
        //   495: invokevirtual   java/math/BigInteger.bitLength:()I
        //   498: bipush          64
        //   500: if_icmpge       514
        //   503: aload           9
        //   505: invokevirtual   java/math/BigInteger.longValue:()J
        //   508: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //   511: goto            516
        //   514: aload           9
        //   516: astore          7
        //   518: goto            561
        //   521: aload_0        
        //   522: getfield        com/ibm/icu/text/DecimalFormat.digitList:Lcom/ibm/icu/text/DigitList;
        //   525: aload           6
        //   527: iconst_1       
        //   528: baload         
        //   529: invokevirtual   com/ibm/icu/text/DigitList.getBigDecimalICU:(Z)Lcom/ibm/icu/math/BigDecimal;
        //   532: astore          9
        //   534: aload           9
        //   536: astore          7
        //   538: iload           8
        //   540: iconst_1       
        //   541: if_icmpeq       561
        //   544: aload           9
        //   546: iload           8
        //   548: i2l            
        //   549: invokestatic    com/ibm/icu/math/BigDecimal.valueOf:(J)Lcom/ibm/icu/math/BigDecimal;
        //   552: aload_0        
        //   553: getfield        com/ibm/icu/text/DecimalFormat.mathContext:Lcom/ibm/icu/math/MathContext;
        //   556: invokevirtual   com/ibm/icu/math/BigDecimal.divide:(Lcom/ibm/icu/math/BigDecimal;Lcom/ibm/icu/math/MathContext;)Lcom/ibm/icu/math/BigDecimal;
        //   559: astore          7
        //   561: aload_3        
        //   562: ifnull          580
        //   565: new             Lcom/ibm/icu/util/CurrencyAmount;
        //   568: dup            
        //   569: aload           7
        //   571: aload_3        
        //   572: iconst_0       
        //   573: aaload         
        //   574: invokespecial   com/ibm/icu/util/CurrencyAmount.<init>:(Ljava/lang/Number;Lcom/ibm/icu/util/Currency;)V
        //   577: goto            582
        //   580: aload           7
        //   582: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0195 (coming from #0153).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void setupCurrencyAffixForAllPatterns() {
        if (this.currencyPluralInfo == null) {
            this.currencyPluralInfo = new CurrencyPluralInfo(this.symbols.getULocale());
        }
        this.affixPatternsForCurrency = new HashSet();
        final String formatPattern = this.formatPattern;
        this.applyPatternWithoutExpandAffix(NumberFormat.getPattern(this.symbols.getULocale(), 1), false);
        this.affixPatternsForCurrency.add(new AffixForCurrency(this.negPrefixPattern, this.negSuffixPattern, this.posPrefixPattern, this.posSuffixPattern, 0));
        final Iterator pluralPatternIterator = this.currencyPluralInfo.pluralPatternIterator();
        final HashSet<String> set = new HashSet<String>();
        while (pluralPatternIterator.hasNext()) {
            final String currencyPluralPattern = this.currencyPluralInfo.getCurrencyPluralPattern(pluralPatternIterator.next());
            if (currencyPluralPattern != null && !set.contains(currencyPluralPattern)) {
                set.add(currencyPluralPattern);
                this.applyPatternWithoutExpandAffix(currencyPluralPattern, false);
                this.affixPatternsForCurrency.add(new AffixForCurrency(this.negPrefixPattern, this.negSuffixPattern, this.posPrefixPattern, this.posSuffixPattern, 1));
            }
        }
        this.formatPattern = formatPattern;
    }
    
    private int countCodePoints(final String s, final int n, final int n2) {
        for (int i = n; i < n2; i += UTF16.getCharCount(UTF16.charAt(s, i))) {
            int n3 = 0;
            ++n3;
        }
        return 0;
    }
    
    private UnicodeSet getEquivalentDecimals(final char c, final boolean b) {
        UnicodeSet set = UnicodeSet.EMPTY;
        if (b) {
            if (DecimalFormat.strictDotEquivalents.contains(c)) {
                set = DecimalFormat.strictDotEquivalents;
            }
            else if (DecimalFormat.strictCommaEquivalents.contains(c)) {
                set = DecimalFormat.strictCommaEquivalents;
            }
        }
        else if (DecimalFormat.dotEquivalents.contains(c)) {
            set = DecimalFormat.dotEquivalents;
        }
        else if (DecimalFormat.commaEquivalents.contains(c)) {
            set = DecimalFormat.commaEquivalents;
        }
        return set;
    }
    
    private final int skipPadding(final String s, int n) {
        while (n < s.length() && s.charAt(n) == this.pad) {
            ++n;
        }
        return n;
    }
    
    private int compareAffix(final String s, final int n, final boolean b, final boolean b2, final String s2, final int n2, final Currency[] array) {
        if (array != null || this.currencyChoice != null || this.currencySignCount > 0) {
            return this.compareComplexAffix(s2, s, n, n2, array);
        }
        if (b2) {
            return compareSimpleAffix(b ? this.negativePrefix : this.positivePrefix, s, n);
        }
        return compareSimpleAffix(b ? this.negativeSuffix : this.positiveSuffix, s, n);
    }
    
    private static int compareSimpleAffix(final String s, final String s2, int skipUWhiteSpace) {
        final int n = skipUWhiteSpace;
        while (0 < s.length()) {
            int n2 = UTF16.charAt(s, 0);
            int n3 = UTF16.getCharCount(n2);
            if (PatternProps.isWhiteSpace(n2)) {
                while (skipUWhiteSpace < s2.length() && UTF16.charAt(s2, skipUWhiteSpace) == n2) {
                    skipUWhiteSpace += n3;
                    if (0 == s.length()) {
                        break;
                    }
                    n2 = UTF16.charAt(s, 0);
                    n3 = UTF16.getCharCount(n2);
                    if (!PatternProps.isWhiteSpace(n2)) {
                        break;
                    }
                }
                skipPatternWhiteSpace(s, 0);
                final int n4 = skipUWhiteSpace;
                skipUWhiteSpace = skipUWhiteSpace(s2, skipUWhiteSpace);
                if (skipUWhiteSpace == n4) {}
                skipUWhiteSpace(s, 0);
            }
            else {
                if (skipUWhiteSpace >= s2.length() || UTF16.charAt(s2, skipUWhiteSpace) != n2) {
                    return -1;
                }
                skipUWhiteSpace += n3;
            }
        }
        return skipUWhiteSpace - n;
    }
    
    private static int skipPatternWhiteSpace(final String s, int i) {
        while (i < s.length()) {
            final int char1 = UTF16.charAt(s, i);
            if (!PatternProps.isWhiteSpace(char1)) {
                break;
            }
            i += UTF16.getCharCount(char1);
        }
        return i;
    }
    
    private static int skipUWhiteSpace(final String s, int i) {
        while (i < s.length()) {
            final int char1 = UTF16.charAt(s, i);
            if (!UCharacter.isUWhiteSpace(char1)) {
                break;
            }
            i += UTF16.getCharCount(char1);
        }
        return i;
    }
    
    private int compareComplexAffix(final String s, final String s2, final int n, final int n2, final Currency[] array) {
        if (0 < s.length()) {}
        return 0;
    }
    
    static final int match(final String s, int skipPatternWhiteSpace, final int n) {
        if (skipPatternWhiteSpace >= s.length()) {
            return -1;
        }
        if (!PatternProps.isWhiteSpace(n)) {
            return (skipPatternWhiteSpace >= 0 && UTF16.charAt(s, skipPatternWhiteSpace) == n) ? (skipPatternWhiteSpace + UTF16.getCharCount(n)) : -1;
        }
        final int n2 = skipPatternWhiteSpace;
        skipPatternWhiteSpace = skipPatternWhiteSpace(s, skipPatternWhiteSpace);
        if (skipPatternWhiteSpace == n2) {
            return -1;
        }
        return skipPatternWhiteSpace;
    }
    
    static final int match(final String s, int match, final String s2) {
        while (0 < s2.length() && match >= 0) {
            final int char1 = UTF16.charAt(s2, 0);
            final int n = 0 + UTF16.getCharCount(char1);
            match = match(s, match, char1);
            if (PatternProps.isWhiteSpace(char1)) {
                skipPatternWhiteSpace(s2, 0);
            }
        }
        return match;
    }
    
    public DecimalFormatSymbols getDecimalFormatSymbols() {
        return (DecimalFormatSymbols)this.symbols.clone();
    }
    
    public void setDecimalFormatSymbols(final DecimalFormatSymbols decimalFormatSymbols) {
        this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
        this.setCurrencyForSymbols();
        this.expandAffixes(null);
    }
    
    private void setCurrencyForSymbols() {
        final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(this.symbols.getULocale());
        if (this.symbols.getCurrencySymbol().equals(decimalFormatSymbols.getCurrencySymbol()) && this.symbols.getInternationalCurrencySymbol().equals(decimalFormatSymbols.getInternationalCurrencySymbol())) {
            this.setCurrency(Currency.getInstance(this.symbols.getULocale()));
        }
        else {
            this.setCurrency(null);
        }
    }
    
    public String getPositivePrefix() {
        return this.positivePrefix;
    }
    
    public void setPositivePrefix(final String positivePrefix) {
        this.positivePrefix = positivePrefix;
        this.posPrefixPattern = null;
    }
    
    public String getNegativePrefix() {
        return this.negativePrefix;
    }
    
    public void setNegativePrefix(final String negativePrefix) {
        this.negativePrefix = negativePrefix;
        this.negPrefixPattern = null;
    }
    
    public String getPositiveSuffix() {
        return this.positiveSuffix;
    }
    
    public void setPositiveSuffix(final String positiveSuffix) {
        this.positiveSuffix = positiveSuffix;
        this.posSuffixPattern = null;
    }
    
    public String getNegativeSuffix() {
        return this.negativeSuffix;
    }
    
    public void setNegativeSuffix(final String negativeSuffix) {
        this.negativeSuffix = negativeSuffix;
        this.negSuffixPattern = null;
    }
    
    public int getMultiplier() {
        return this.multiplier;
    }
    
    public void setMultiplier(final int multiplier) {
        if (multiplier == 0) {
            throw new IllegalArgumentException("Bad multiplier: " + multiplier);
        }
        this.multiplier = multiplier;
    }
    
    public BigDecimal getRoundingIncrement() {
        if (this.roundingIncrementICU == null) {
            return null;
        }
        return this.roundingIncrementICU.toBigDecimal();
    }
    
    public void setRoundingIncrement(final BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            this.setRoundingIncrement((com.ibm.icu.math.BigDecimal)null);
        }
        else {
            this.setRoundingIncrement(new com.ibm.icu.math.BigDecimal(bigDecimal));
        }
    }
    
    public void setRoundingIncrement(final com.ibm.icu.math.BigDecimal internalRoundingIncrement) {
        final int n = (internalRoundingIncrement == null) ? 0 : internalRoundingIncrement.compareTo(com.ibm.icu.math.BigDecimal.ZERO);
        if (n < 0) {
            throw new IllegalArgumentException("Illegal rounding increment");
        }
        if (n == 0) {
            this.setInternalRoundingIncrement(null);
        }
        else {
            this.setInternalRoundingIncrement(internalRoundingIncrement);
        }
        this.setRoundingDouble();
    }
    
    public void setRoundingIncrement(final double n) {
        if (n < 0.0) {
            throw new IllegalArgumentException("Illegal rounding increment");
        }
        this.roundingDouble = n;
        this.roundingDoubleReciprocal = 0.0;
        if (n == 0.0) {
            this.setRoundingIncrement((com.ibm.icu.math.BigDecimal)null);
        }
        else {
            this.roundingDouble = n;
            if (this.roundingDouble < 1.0) {
                this.setRoundingDoubleReciprocal(1.0 / this.roundingDouble);
            }
            this.setInternalRoundingIncrement(new com.ibm.icu.math.BigDecimal(n));
        }
    }
    
    private void setRoundingDoubleReciprocal(final double n) {
        this.roundingDoubleReciprocal = Math.rint(n);
        if (Math.abs(n - this.roundingDoubleReciprocal) > 1.0E-9) {
            this.roundingDoubleReciprocal = 0.0;
        }
    }
    
    @Override
    public int getRoundingMode() {
        return this.roundingMode;
    }
    
    @Override
    public void setRoundingMode(final int roundingMode) {
        if (roundingMode < 0 || roundingMode > 7) {
            throw new IllegalArgumentException("Invalid rounding mode: " + roundingMode);
        }
        this.roundingMode = roundingMode;
        if (this.getRoundingIncrement() == null) {
            this.setRoundingIncrement(Math.pow(10.0, -this.getMaximumFractionDigits()));
        }
    }
    
    public int getFormatWidth() {
        return this.formatWidth;
    }
    
    public void setFormatWidth(final int formatWidth) {
        if (formatWidth < 0) {
            throw new IllegalArgumentException("Illegal format width");
        }
        this.formatWidth = formatWidth;
    }
    
    public char getPadCharacter() {
        return this.pad;
    }
    
    public void setPadCharacter(final char pad) {
        this.pad = pad;
    }
    
    public int getPadPosition() {
        return this.padPosition;
    }
    
    public void setPadPosition(final int padPosition) {
        if (padPosition < 0 || padPosition > 3) {
            throw new IllegalArgumentException("Illegal pad position");
        }
        this.padPosition = padPosition;
    }
    
    public boolean isScientificNotation() {
        return this.useExponentialNotation;
    }
    
    public void setScientificNotation(final boolean useExponentialNotation) {
        this.useExponentialNotation = useExponentialNotation;
    }
    
    public byte getMinimumExponentDigits() {
        return this.minExponentDigits;
    }
    
    public void setMinimumExponentDigits(final byte minExponentDigits) {
        if (minExponentDigits < 1) {
            throw new IllegalArgumentException("Exponent digits must be >= 1");
        }
        this.minExponentDigits = minExponentDigits;
    }
    
    public boolean isExponentSignAlwaysShown() {
        return this.exponentSignAlwaysShown;
    }
    
    public void setExponentSignAlwaysShown(final boolean exponentSignAlwaysShown) {
        this.exponentSignAlwaysShown = exponentSignAlwaysShown;
    }
    
    public int getGroupingSize() {
        return this.groupingSize;
    }
    
    public void setGroupingSize(final int n) {
        this.groupingSize = (byte)n;
    }
    
    public int getSecondaryGroupingSize() {
        return this.groupingSize2;
    }
    
    public void setSecondaryGroupingSize(final int n) {
        this.groupingSize2 = (byte)n;
    }
    
    public MathContext getMathContextICU() {
        return this.mathContext;
    }
    
    public java.math.MathContext getMathContext() {
        return (this.mathContext == null) ? null : new java.math.MathContext(this.mathContext.getDigits(), RoundingMode.valueOf(this.mathContext.getRoundingMode()));
    }
    
    public void setMathContextICU(final MathContext mathContext) {
        this.mathContext = mathContext;
    }
    
    public void setMathContext(final java.math.MathContext mathContext) {
        this.mathContext = new MathContext(mathContext.getPrecision(), 1, false, mathContext.getRoundingMode().ordinal());
    }
    
    public boolean isDecimalSeparatorAlwaysShown() {
        return this.decimalSeparatorAlwaysShown;
    }
    
    public void setDecimalSeparatorAlwaysShown(final boolean decimalSeparatorAlwaysShown) {
        this.decimalSeparatorAlwaysShown = decimalSeparatorAlwaysShown;
    }
    
    public CurrencyPluralInfo getCurrencyPluralInfo() {
        return (this.currencyPluralInfo == null) ? null : ((CurrencyPluralInfo)this.currencyPluralInfo.clone());
    }
    
    public void setCurrencyPluralInfo(final CurrencyPluralInfo currencyPluralInfo) {
        this.currencyPluralInfo = (CurrencyPluralInfo)currencyPluralInfo.clone();
        this.isReadyForParsing = false;
    }
    
    @Override
    public Object clone() {
        final DecimalFormat decimalFormat = (DecimalFormat)super.clone();
        decimalFormat.symbols = (DecimalFormatSymbols)this.symbols.clone();
        decimalFormat.digitList = new DigitList();
        if (this.currencyPluralInfo != null) {
            decimalFormat.currencyPluralInfo = (CurrencyPluralInfo)this.currencyPluralInfo.clone();
        }
        decimalFormat.attributes = new ArrayList();
        return decimalFormat;
    }
    
    @Override
    public boolean equals(final Object p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_0        
        //     7: aload_1        
        //     8: invokespecial   com/ibm/icu/text/NumberFormat.equals:(Ljava/lang/Object;)Z
        //    11: ifne            16
        //    14: iconst_0       
        //    15: ireturn        
        //    16: aload_1        
        //    17: checkcast       Lcom/ibm/icu/text/DecimalFormat;
        //    20: astore_2       
        //    21: aload_0        
        //    22: getfield        com/ibm/icu/text/DecimalFormat.currencySignCount:I
        //    25: aload_2        
        //    26: getfield        com/ibm/icu/text/DecimalFormat.currencySignCount:I
        //    29: if_icmpne       234
        //    32: aload_0        
        //    33: getfield        com/ibm/icu/text/DecimalFormat.style:I
        //    36: bipush          6
        //    38: if_icmpne       89
        //    41: aload_0        
        //    42: aload_0        
        //    43: getfield        com/ibm/icu/text/DecimalFormat.posPrefixPattern:Ljava/lang/String;
        //    46: aload_2        
        //    47: getfield        com/ibm/icu/text/DecimalFormat.posPrefixPattern:Ljava/lang/String;
        //    50: ifnull          234
        //    53: aload_0        
        //    54: aload_0        
        //    55: getfield        com/ibm/icu/text/DecimalFormat.posSuffixPattern:Ljava/lang/String;
        //    58: aload_2        
        //    59: getfield        com/ibm/icu/text/DecimalFormat.posSuffixPattern:Ljava/lang/String;
        //    62: ifnull          234
        //    65: aload_0        
        //    66: aload_0        
        //    67: getfield        com/ibm/icu/text/DecimalFormat.negPrefixPattern:Ljava/lang/String;
        //    70: aload_2        
        //    71: getfield        com/ibm/icu/text/DecimalFormat.negPrefixPattern:Ljava/lang/String;
        //    74: ifnull          234
        //    77: aload_0        
        //    78: aload_0        
        //    79: getfield        com/ibm/icu/text/DecimalFormat.negSuffixPattern:Ljava/lang/String;
        //    82: aload_2        
        //    83: getfield        com/ibm/icu/text/DecimalFormat.negSuffixPattern:Ljava/lang/String;
        //    86: ifnull          234
        //    89: aload_0        
        //    90: getfield        com/ibm/icu/text/DecimalFormat.multiplier:I
        //    93: aload_2        
        //    94: getfield        com/ibm/icu/text/DecimalFormat.multiplier:I
        //    97: if_icmpne       234
        //   100: aload_0        
        //   101: getfield        com/ibm/icu/text/DecimalFormat.groupingSize:B
        //   104: aload_2        
        //   105: getfield        com/ibm/icu/text/DecimalFormat.groupingSize:B
        //   108: if_icmpne       234
        //   111: aload_0        
        //   112: getfield        com/ibm/icu/text/DecimalFormat.groupingSize2:B
        //   115: aload_2        
        //   116: getfield        com/ibm/icu/text/DecimalFormat.groupingSize2:B
        //   119: if_icmpne       234
        //   122: aload_0        
        //   123: getfield        com/ibm/icu/text/DecimalFormat.decimalSeparatorAlwaysShown:Z
        //   126: aload_2        
        //   127: getfield        com/ibm/icu/text/DecimalFormat.decimalSeparatorAlwaysShown:Z
        //   130: if_icmpne       234
        //   133: aload_0        
        //   134: getfield        com/ibm/icu/text/DecimalFormat.useExponentialNotation:Z
        //   137: aload_2        
        //   138: getfield        com/ibm/icu/text/DecimalFormat.useExponentialNotation:Z
        //   141: if_icmpne       234
        //   144: aload_0        
        //   145: getfield        com/ibm/icu/text/DecimalFormat.useExponentialNotation:Z
        //   148: ifeq            162
        //   151: aload_0        
        //   152: getfield        com/ibm/icu/text/DecimalFormat.minExponentDigits:B
        //   155: aload_2        
        //   156: getfield        com/ibm/icu/text/DecimalFormat.minExponentDigits:B
        //   159: if_icmpne       234
        //   162: aload_0        
        //   163: getfield        com/ibm/icu/text/DecimalFormat.useSignificantDigits:Z
        //   166: aload_2        
        //   167: getfield        com/ibm/icu/text/DecimalFormat.useSignificantDigits:Z
        //   170: if_icmpne       234
        //   173: aload_0        
        //   174: getfield        com/ibm/icu/text/DecimalFormat.useSignificantDigits:Z
        //   177: ifeq            202
        //   180: aload_0        
        //   181: getfield        com/ibm/icu/text/DecimalFormat.minSignificantDigits:I
        //   184: aload_2        
        //   185: getfield        com/ibm/icu/text/DecimalFormat.minSignificantDigits:I
        //   188: if_icmpne       234
        //   191: aload_0        
        //   192: getfield        com/ibm/icu/text/DecimalFormat.maxSignificantDigits:I
        //   195: aload_2        
        //   196: getfield        com/ibm/icu/text/DecimalFormat.maxSignificantDigits:I
        //   199: if_icmpne       234
        //   202: aload_0        
        //   203: getfield        com/ibm/icu/text/DecimalFormat.symbols:Lcom/ibm/icu/text/DecimalFormatSymbols;
        //   206: aload_2        
        //   207: getfield        com/ibm/icu/text/DecimalFormat.symbols:Lcom/ibm/icu/text/DecimalFormatSymbols;
        //   210: invokevirtual   com/ibm/icu/text/DecimalFormatSymbols.equals:(Ljava/lang/Object;)Z
        //   213: ifeq            234
        //   216: aload_0        
        //   217: getfield        com/ibm/icu/text/DecimalFormat.currencyPluralInfo:Lcom/ibm/icu/text/CurrencyPluralInfo;
        //   220: aload_2        
        //   221: getfield        com/ibm/icu/text/DecimalFormat.currencyPluralInfo:Lcom/ibm/icu/text/CurrencyPluralInfo;
        //   224: invokestatic    com/ibm/icu/impl/Utility.objectEquals:(Ljava/lang/Object;Ljava/lang/Object;)Z
        //   227: ifeq            234
        //   230: iconst_1       
        //   231: goto            235
        //   234: iconst_0       
        //   235: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0234 (coming from #0050).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private String unquote(final String s) {
        final StringBuilder sb = new StringBuilder(s.length());
        while (0 < s.length()) {
            final int n = 0;
            int n2 = 0;
            ++n2;
            final char char1 = s.charAt(n);
            if (char1 != '\'') {
                sb.append(char1);
            }
        }
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() * 37 + this.positivePrefix.hashCode();
    }
    
    public String toPattern() {
        if (this.style == 6) {
            return this.formatPattern;
        }
        return this.toPattern(false);
    }
    
    public String toLocalizedPattern() {
        if (this.style == 6) {
            return this.formatPattern;
        }
        return this.toPattern(true);
    }
    
    private void expandAffixes(final String s) {
        this.currencyChoice = null;
        final StringBuffer sb = new StringBuffer();
        if (this.posPrefixPattern != null) {
            this.expandAffix(this.posPrefixPattern, s, sb, false);
            this.positivePrefix = sb.toString();
        }
        if (this.posSuffixPattern != null) {
            this.expandAffix(this.posSuffixPattern, s, sb, false);
            this.positiveSuffix = sb.toString();
        }
        if (this.negPrefixPattern != null) {
            this.expandAffix(this.negPrefixPattern, s, sb, false);
            this.negativePrefix = sb.toString();
        }
        if (this.negSuffixPattern != null) {
            this.expandAffix(this.negSuffixPattern, s, sb, false);
            this.negativeSuffix = sb.toString();
        }
    }
    
    private void expandAffix(final String s, final String s2, final StringBuffer sb, final boolean b) {
        sb.setLength(0);
        while (0 < s.length()) {
            final int n = 0;
            int n2 = 0;
            ++n2;
            char c = s.charAt(n);
            if (c == '\'') {
                s.indexOf(39, 0);
                sb.append('\'');
            }
            else {
                switch (c) {
                    case 164: {
                        final boolean b2 = 0 < s.length() && s.charAt(0) == '¤';
                        final Currency currency = this.getCurrency();
                        String s3;
                        if (currency != null) {
                            if (s2 != null) {
                                s3 = currency.getName(this.symbols.getULocale(), 2, s2, new boolean[1]);
                            }
                            else {
                                final boolean[] array = { false };
                                s3 = currency.getName(this.symbols.getULocale(), 0, array);
                                if (array[0]) {
                                    if (b) {
                                        this.currencyChoice.format(this.digitList.getDouble(), sb, new FieldPosition(0));
                                        continue;
                                    }
                                    if (this.currencyChoice == null) {
                                        this.currencyChoice = new ChoiceFormat(s3);
                                    }
                                    s3 = String.valueOf('¤');
                                }
                            }
                        }
                        else {
                            s3 = this.symbols.getCurrencySymbol();
                        }
                        sb.append(s3);
                        continue;
                    }
                    case 37: {
                        c = this.symbols.getPercent();
                        break;
                    }
                    case 8240: {
                        c = this.symbols.getPerMill();
                        break;
                    }
                    case 45: {
                        c = this.symbols.getMinusSign();
                        break;
                    }
                }
                sb.append(c);
            }
        }
    }
    
    private int appendAffix(final StringBuffer sb, final boolean b, final boolean b2, final boolean b3) {
        if (this.currencyChoice != null) {
            String s;
            if (b2) {
                s = (b ? this.negPrefixPattern : this.posPrefixPattern);
            }
            else {
                s = (b ? this.negSuffixPattern : this.posSuffixPattern);
            }
            final StringBuffer sb2 = new StringBuffer();
            this.expandAffix(s, null, sb2, true);
            sb.append(sb2);
            return sb2.length();
        }
        String s2;
        if (b2) {
            s2 = (b ? this.negativePrefix : this.positivePrefix);
        }
        else {
            s2 = (b ? this.negativeSuffix : this.positiveSuffix);
        }
        if (b3) {
            s2.indexOf(this.symbols.getCurrencySymbol());
            this.formatAffix2Attribute(s2, sb.length() + 0, sb.length() + s2.length());
        }
        sb.append(s2);
        return s2.length();
    }
    
    private void formatAffix2Attribute(final String s, final int n, final int n2) {
        if (s.indexOf(this.symbols.getCurrencySymbol()) > -1) {
            this.addAttribute(Field.CURRENCY, n, n2);
        }
        else if (s.indexOf(this.symbols.getMinusSign()) > -1) {
            this.addAttribute(Field.SIGN, n, n2);
        }
        else if (s.indexOf(this.symbols.getPercent()) > -1) {
            this.addAttribute(Field.PERCENT, n, n2);
        }
        else if (s.indexOf(this.symbols.getPerMill()) > -1) {
            this.addAttribute(Field.PERMILLE, n, n2);
        }
    }
    
    private void addAttribute(final Field field, final int beginIndex, final int endIndex) {
        final FieldPosition fieldPosition = new FieldPosition(field);
        fieldPosition.setBeginIndex(beginIndex);
        fieldPosition.setEndIndex(endIndex);
        this.attributes.add(fieldPosition);
    }
    
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(final Object o) {
        return this.formatToCharacterIterator(o, DecimalFormat.NULL_UNIT);
    }
    
    AttributedCharacterIterator formatToCharacterIterator(final Object o, final Unit unit) {
        if (!(o instanceof Number)) {
            throw new IllegalArgumentException();
        }
        final Number n = (Number)o;
        final StringBuffer sb = new StringBuffer();
        unit.writePrefix(sb);
        this.attributes.clear();
        if (o instanceof BigInteger) {
            this.format((BigInteger)n, sb, new FieldPosition(0), true);
        }
        else if (o instanceof BigDecimal) {
            this.format((BigDecimal)n, sb, new FieldPosition(0), true);
        }
        else if (o instanceof Double) {
            this.format(n.doubleValue(), sb, new FieldPosition(0), true);
        }
        else {
            if (!(o instanceof Integer) && !(o instanceof Long)) {
                throw new IllegalArgumentException();
            }
            this.format(n.longValue(), sb, new FieldPosition(0), true);
        }
        unit.writeSuffix(sb);
        final AttributedString attributedString = new AttributedString(sb.toString());
        while (0 < this.attributes.size()) {
            final FieldPosition fieldPosition = this.attributes.get(0);
            final Format.Field fieldAttribute = fieldPosition.getFieldAttribute();
            attributedString.addAttribute(fieldAttribute, fieldAttribute, fieldPosition.getBeginIndex(), fieldPosition.getEndIndex());
            int n2 = 0;
            ++n2;
        }
        return attributedString.getIterator();
    }
    
    private void appendAffixPattern(final StringBuffer sb, final boolean b, final boolean b2, final boolean b3) {
        String s;
        if (b2) {
            s = (b ? this.negPrefixPattern : this.posPrefixPattern);
        }
        else {
            s = (b ? this.negSuffixPattern : this.posSuffixPattern);
        }
        if (s == null) {
            String s2;
            if (b2) {
                s2 = (b ? this.negativePrefix : this.positivePrefix);
            }
            else {
                s2 = (b ? this.negativeSuffix : this.positiveSuffix);
            }
            sb.append('\'');
            while (0 < s2.length()) {
                final char char1 = s2.charAt(0);
                if (char1 == '\'') {
                    sb.append(char1);
                }
                sb.append(char1);
                int index = 0;
                ++index;
            }
            sb.append('\'');
            return;
        }
        if (!b3) {
            sb.append(s);
        }
        else {
            while (0 < s.length()) {
                char c = s.charAt(0);
                Label_0373: {
                    switch (c) {
                        case 39: {
                            final int index = s.indexOf(39, 1);
                            sb.append(s.substring(0, 1));
                            break Label_0373;
                        }
                        case 8240: {
                            c = this.symbols.getPerMill();
                            break;
                        }
                        case 37: {
                            c = this.symbols.getPercent();
                            break;
                        }
                        case 45: {
                            c = this.symbols.getMinusSign();
                            break;
                        }
                    }
                    if (c == this.symbols.getDecimalSeparator() || c == this.symbols.getGroupingSeparator()) {
                        sb.append('\'');
                        sb.append(c);
                        sb.append('\'');
                    }
                    else {
                        sb.append(c);
                    }
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    private String toPattern(final boolean b) {
        final StringBuffer sb = new StringBuffer();
        final char c = b ? this.symbols.getZeroDigit() : '0';
        final char c2 = b ? this.symbols.getDigit() : '#';
        final boolean significantDigitsUsed = this.areSignificantDigitsUsed();
        if (significantDigitsUsed) {
            final char c3 = b ? this.symbols.getSignificantDigit() : '@';
        }
        final char c4 = b ? this.symbols.getGroupingSeparator() : ',';
        String string = null;
        final int n = (this.formatWidth > 0) ? this.padPosition : -1;
        final String s = (this.formatWidth > 0) ? new StringBuffer(2).append(b ? this.symbols.getPadEscape() : '*').append(this.pad).toString() : null;
        int scale = 0;
        if (this.roundingIncrementICU != null) {
            scale = this.roundingIncrementICU.scale();
            string = this.roundingIncrementICU.movePointRight(0).toString();
            final int n2 = string.length() - 0;
        }
        while (true) {
            if (n == 0) {
                sb.append(s);
            }
            this.appendAffixPattern(sb, false, true, b);
            if (n == 1) {
                sb.append(s);
            }
            final int length = sb.length();
            int n3 = this.isGroupingUsed() ? Math.max(0, this.groupingSize) : 0;
            if (n3 > 0 && this.groupingSize2 > 0 && this.groupingSize2 != this.groupingSize) {
                n3 += this.groupingSize2;
            }
            if (significantDigitsUsed) {
                this.getMinimumSignificantDigits();
                this.getMaximumSignificantDigits();
            }
            else {
                this.getMinimumIntegerDigits();
                this.getMaximumIntegerDigits();
            }
            if (!this.useExponentialNotation) {
                if (significantDigitsUsed) {
                    Math.max(1, n3 + 1);
                }
                else {
                    final int n4 = Math.max(Math.max(n3, this.getMinimumIntegerDigits()), 0) + 1;
                }
            }
            if (!significantDigitsUsed) {
                if (this.getMaximumFractionDigits() > 0 || this.decimalSeparatorAlwaysShown) {
                    sb.append(b ? this.symbols.getDecimalSeparator() : '.');
                }
                while (0 < this.getMaximumFractionDigits()) {
                    if (string != null && 0 < string.length()) {
                        sb.append((char)(string.charAt(0) - '0' + c));
                        int n5 = 0;
                        ++n5;
                    }
                    else {
                        sb.append((0 < this.getMinimumFractionDigits()) ? c : c2);
                    }
                    ++scale;
                }
            }
            if (this.useExponentialNotation) {
                if (b) {
                    sb.append(this.symbols.getExponentSeparator());
                }
                else {
                    sb.append('E');
                }
                if (this.exponentSignAlwaysShown) {
                    sb.append(b ? this.symbols.getPlusSign() : '+');
                }
                while (0 < this.minExponentDigits) {
                    sb.append(c);
                    ++scale;
                }
            }
            if (s != null && !this.useExponentialNotation) {
                final int n5 = this.formatWidth - sb.length() + length - (this.positivePrefix.length() + this.positiveSuffix.length());
            }
            if (n == 2) {
                sb.append(s);
            }
            this.appendAffixPattern(sb, false, false, b);
            if (n == 3) {
                sb.append(s);
            }
            if (this.negativeSuffix.equals(this.positiveSuffix) && this.negativePrefix.equals('-' + this.positivePrefix)) {
                break;
            }
            sb.append(b ? this.symbols.getPatternSeparator() : ';');
            int n6 = 0;
            ++n6;
        }
        return sb.toString();
    }
    
    public void applyPattern(final String s) {
        this.applyPattern(s, false);
    }
    
    public void applyLocalizedPattern(final String s) {
        this.applyPattern(s, true);
    }
    
    private void applyPattern(final String s, final boolean b) {
        this.applyPatternWithoutExpandAffix(s, b);
        this.expandAffixAdjustWidth(null);
    }
    
    private void expandAffixAdjustWidth(final String s) {
        this.expandAffixes(s);
        if (this.formatWidth > 0) {
            this.formatWidth += this.positivePrefix.length() + this.positiveSuffix.length();
        }
    }
    
    private void applyPatternWithoutExpandAffix(final String formatPattern, final boolean b) {
        String s = String.valueOf('E');
        if (b) {
            this.symbols.getZeroDigit();
            this.symbols.getSignificantDigit();
            this.symbols.getGroupingSeparator();
            this.symbols.getDecimalSeparator();
            this.symbols.getPercent();
            this.symbols.getPerMill();
            this.symbols.getDigit();
            this.symbols.getPatternSeparator();
            s = this.symbols.getExponentSeparator();
            this.symbols.getPlusSign();
            this.symbols.getPadEscape();
            this.symbols.getMinusSign();
        }
        final char c = 57;
        while (0 < formatPattern.length()) {
            final StringBuilder sb = new StringBuilder();
            final StringBuilder sb2 = new StringBuilder();
            final long n = 0L;
            StringBuilder sb3 = sb;
            while (0 < formatPattern.length()) {
                formatPattern.charAt(0);
                int n2 = 0;
                switch (false) {
                    case 0: {
                        if (formatPattern.regionMatches(0, s, 0, s.length())) {
                            this.patternError("Multiple exponential symbols", formatPattern);
                            this.patternError("Grouping separator in exponential", formatPattern);
                            n2 = 0 + s.length();
                            if (0 < formatPattern.length() && formatPattern.charAt(0) == '+') {
                                ++n2;
                            }
                            while (0 < formatPattern.length() && formatPattern.charAt(0) == '0') {
                                final byte b2 = 1;
                                ++n2;
                            }
                            this.patternError("Malformed exponential", formatPattern);
                        }
                        sb3 = sb2;
                        --n2;
                        break;
                    }
                    case 1:
                    case 2: {
                        sb3.append('-');
                        break;
                    }
                    case 3:
                    case 4: {
                        sb3.append('-');
                        break;
                    }
                }
                ++n2;
            }
            formatPattern.length();
            formatPattern.length();
            this.patternError("Malformed pattern", formatPattern);
            this.patternError("Illegal pad position", formatPattern);
            final String string = sb.toString();
            this.negPrefixPattern = string;
            this.posPrefixPattern = string;
            final String string2 = sb2.toString();
            this.negSuffixPattern = string2;
            this.posSuffixPattern = string2;
            this.useExponentialNotation = true;
            if (this.useExponentialNotation) {
                this.minExponentDigits = 0;
                this.exponentSignAlwaysShown = true;
            }
            final boolean significantDigitsUsed = false;
            this.setSignificantDigitsUsed(significantDigitsUsed);
            if (significantDigitsUsed) {
                this.setMinimumSignificantDigits(0);
                this.setMaximumSignificantDigits(0);
            }
            else {
                this.setMinimumIntegerDigits(-2);
                this.setMaximumIntegerDigits(this.useExponentialNotation ? -2 : 309);
                this.setMaximumFractionDigits(0);
                this.setMinimumFractionDigits(0);
            }
            this.setGroupingUsed(false);
            this.groupingSize = 0;
            this.groupingSize2 = 0;
            this.multiplier = 1;
            this.setDecimalSeparatorAlwaysShown(true);
            this.padPosition = 3;
            this.formatWidth = 0;
            this.pad = '\0';
            if (n != 0L) {
                this.roundingIncrementICU = com.ibm.icu.math.BigDecimal.valueOf(n, 0);
                this.roundingIncrementICU = this.roundingIncrementICU.movePointRight(2);
                this.setRoundingDouble();
                this.roundingMode = 6;
            }
            else {
                this.setRoundingIncrement((com.ibm.icu.math.BigDecimal)null);
            }
            this.currencySignCount = 1;
            int n3 = 0;
            ++n3;
        }
        if (formatPattern.length() == 0) {
            final String s2 = "";
            this.posSuffixPattern = s2;
            this.posPrefixPattern = s2;
            this.setMinimumIntegerDigits(0);
            this.setMaximumIntegerDigits(309);
            this.setMinimumFractionDigits(0);
            this.setMaximumFractionDigits(340);
        }
        if (this.negPrefixPattern.equals(this.posPrefixPattern) && this.negSuffixPattern.equals(this.posSuffixPattern)) {
            this.negSuffixPattern = this.posSuffixPattern;
            this.negPrefixPattern = '-' + this.posPrefixPattern;
        }
        this.setLocale(null, null);
        this.formatPattern = formatPattern;
        if (this.currencySignCount > 0) {
            final Currency currency = this.getCurrency();
            if (currency != null) {
                this.setRoundingIncrement(currency.getRoundingIncrement());
                currency.getDefaultFractionDigits();
                this.setMinimumFractionDigits(0);
                this.setMaximumFractionDigits(0);
            }
            if (this.currencySignCount == 3 && this.currencyPluralInfo == null) {
                this.currencyPluralInfo = new CurrencyPluralInfo(this.symbols.getULocale());
            }
        }
    }
    
    private void setRoundingDouble() {
        if (this.roundingIncrementICU == null) {
            this.roundingDouble = 0.0;
            this.roundingDoubleReciprocal = 0.0;
        }
        else {
            this.roundingDouble = this.roundingIncrementICU.doubleValue();
            this.setRoundingDoubleReciprocal(1.0 / this.roundingDouble);
        }
    }
    
    private void patternError(final String s, final String s2) {
        throw new IllegalArgumentException(s + " in pattern \"" + s2 + '\"');
    }
    
    @Override
    public void setMaximumIntegerDigits(final int n) {
        super.setMaximumIntegerDigits(Math.min(n, 309));
    }
    
    @Override
    public void setMinimumIntegerDigits(final int n) {
        super.setMinimumIntegerDigits(Math.min(n, 309));
    }
    
    public int getMinimumSignificantDigits() {
        return this.minSignificantDigits;
    }
    
    public int getMaximumSignificantDigits() {
        return this.maxSignificantDigits;
    }
    
    public void setMinimumSignificantDigits(final int n) {
        final int max = Math.max(this.maxSignificantDigits, 1);
        this.minSignificantDigits = 1;
        this.maxSignificantDigits = max;
    }
    
    public void setMaximumSignificantDigits(final int n) {
        this.minSignificantDigits = Math.min(this.minSignificantDigits, 1);
        this.maxSignificantDigits = 1;
    }
    
    public boolean areSignificantDigitsUsed() {
        return this.useSignificantDigits;
    }
    
    public void setSignificantDigitsUsed(final boolean useSignificantDigits) {
        this.useSignificantDigits = useSignificantDigits;
    }
    
    @Override
    public void setCurrency(final Currency currency) {
        super.setCurrency(currency);
        if (currency != null) {
            final String name = currency.getName(this.symbols.getULocale(), 0, new boolean[1]);
            this.symbols.setCurrency(currency);
            this.symbols.setCurrencySymbol(name);
        }
        if (this.currencySignCount > 0) {
            if (currency != null) {
                this.setRoundingIncrement(currency.getRoundingIncrement());
                final int defaultFractionDigits = currency.getDefaultFractionDigits();
                this.setMinimumFractionDigits(defaultFractionDigits);
                this.setMaximumFractionDigits(defaultFractionDigits);
            }
            if (this.currencySignCount != 3) {
                this.expandAffixes(null);
            }
        }
    }
    
    @Deprecated
    @Override
    protected Currency getEffectiveCurrency() {
        Currency currency = this.getCurrency();
        if (currency == null) {
            currency = Currency.getInstance(this.symbols.getInternationalCurrencySymbol());
        }
        return currency;
    }
    
    @Override
    public void setMaximumFractionDigits(final int n) {
        super.setMaximumFractionDigits(Math.min(n, 340));
    }
    
    @Override
    public void setMinimumFractionDigits(final int n) {
        super.setMinimumFractionDigits(Math.min(n, 340));
    }
    
    public void setParseBigDecimal(final boolean parseBigDecimal) {
        this.parseBigDecimal = parseBigDecimal;
    }
    
    public boolean isParseBigDecimal() {
        return this.parseBigDecimal;
    }
    
    public void setParseMaxDigits(final int parse_MAX_EXPONENT) {
        if (parse_MAX_EXPONENT > 0) {
            this.PARSE_MAX_EXPONENT = parse_MAX_EXPONENT;
        }
    }
    
    public int getParseMaxDigits() {
        return this.PARSE_MAX_EXPONENT;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.attributes.clear();
        objectOutputStream.defaultWriteObject();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.getMaximumIntegerDigits() > 309) {
            this.setMaximumIntegerDigits(309);
        }
        if (this.getMaximumFractionDigits() > 340) {
            this.setMaximumFractionDigits(340);
        }
        if (this.serialVersionOnStream < 2) {
            this.exponentSignAlwaysShown = false;
            this.setInternalRoundingIncrement(null);
            this.setRoundingDouble();
            this.roundingMode = 6;
            this.formatWidth = 0;
            this.pad = ' ';
            this.padPosition = 0;
            if (this.serialVersionOnStream < 1) {
                this.useExponentialNotation = false;
            }
        }
        if (this.serialVersionOnStream < 3) {
            this.setCurrencyForSymbols();
        }
        this.serialVersionOnStream = 3;
        this.digitList = new DigitList();
        if (this.roundingIncrement != null) {
            this.setInternalRoundingIncrement(new com.ibm.icu.math.BigDecimal(this.roundingIncrement));
            this.setRoundingDouble();
        }
    }
    
    private void setInternalRoundingIncrement(final com.ibm.icu.math.BigDecimal roundingIncrementICU) {
        this.roundingIncrementICU = roundingIncrementICU;
        this.roundingIncrement = ((roundingIncrementICU == null) ? null : roundingIncrementICU.toBigDecimal());
    }
    
    static {
        dotEquivalents = new UnicodeSet(new int[] { 46, 46, 8228, 8228, 12290, 12290, 65042, 65042, 65106, 65106, 65294, 65294, 65377, 65377 }).freeze();
        commaEquivalents = new UnicodeSet(new int[] { 44, 44, 1548, 1548, 1643, 1643, 12289, 12289, 65040, 65041, 65104, 65105, 65292, 65292, 65380, 65380 }).freeze();
        strictDotEquivalents = new UnicodeSet(new int[] { 46, 46, 8228, 8228, 65106, 65106, 65294, 65294, 65377, 65377 }).freeze();
        strictCommaEquivalents = new UnicodeSet(new int[] { 44, 44, 1643, 1643, 65040, 65040, 65104, 65104, 65292, 65292 }).freeze();
        defaultGroupingSeparators = new UnicodeSet(new int[] { 32, 32, 39, 39, 44, 44, 46, 46, 160, 160, 1548, 1548, 1643, 1644, 8192, 8202, 8216, 8217, 8228, 8228, 8239, 8239, 8287, 8287, 12288, 12290, 65040, 65042, 65104, 65106, 65287, 65287, 65292, 65292, 65294, 65294, 65377, 65377, 65380, 65380 }).freeze();
        strictDefaultGroupingSeparators = new UnicodeSet(new int[] { 32, 32, 39, 39, 44, 44, 46, 46, 160, 160, 1643, 1644, 8192, 8202, 8216, 8217, 8228, 8228, 8239, 8239, 8287, 8287, 12288, 12288, 65040, 65040, 65104, 65104, 65106, 65106, 65287, 65287, 65292, 65292, 65294, 65294, 65377, 65377 }).freeze();
        NULL_UNIT = new Unit("", "");
    }
    
    static class Unit
    {
        private final String prefix;
        private final String suffix;
        
        public Unit(final String prefix, final String suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
        }
        
        public void writeSuffix(final StringBuffer sb) {
            sb.append(this.suffix);
        }
        
        public void writePrefix(final StringBuffer sb) {
            sb.append(this.prefix);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Unit)) {
                return false;
            }
            final Unit unit = (Unit)o;
            return this.prefix.equals(unit.prefix) && this.suffix.equals(unit.suffix);
        }
    }
    
    private static final class AffixForCurrency
    {
        private String negPrefixPatternForCurrency;
        private String negSuffixPatternForCurrency;
        private String posPrefixPatternForCurrency;
        private String posSuffixPatternForCurrency;
        private final int patternType;
        
        public AffixForCurrency(final String negPrefixPatternForCurrency, final String negSuffixPatternForCurrency, final String posPrefixPatternForCurrency, final String posSuffixPatternForCurrency, final int patternType) {
            this.negPrefixPatternForCurrency = null;
            this.negSuffixPatternForCurrency = null;
            this.posPrefixPatternForCurrency = null;
            this.posSuffixPatternForCurrency = null;
            this.negPrefixPatternForCurrency = negPrefixPatternForCurrency;
            this.negSuffixPatternForCurrency = negSuffixPatternForCurrency;
            this.posPrefixPatternForCurrency = posPrefixPatternForCurrency;
            this.posSuffixPatternForCurrency = posSuffixPatternForCurrency;
            this.patternType = patternType;
        }
        
        public String getNegPrefix() {
            return this.negPrefixPatternForCurrency;
        }
        
        public String getNegSuffix() {
            return this.negSuffixPatternForCurrency;
        }
        
        public String getPosPrefix() {
            return this.posPrefixPatternForCurrency;
        }
        
        public String getPosSuffix() {
            return this.posSuffixPatternForCurrency;
        }
        
        public int getPatternType() {
            return this.patternType;
        }
    }
}
