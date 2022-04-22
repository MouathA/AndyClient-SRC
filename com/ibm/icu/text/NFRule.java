package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.text.*;

final class NFRule
{
    public static final int NEGATIVE_NUMBER_RULE = -1;
    public static final int IMPROPER_FRACTION_RULE = -2;
    public static final int PROPER_FRACTION_RULE = -3;
    public static final int MASTER_RULE = -4;
    private long baseValue;
    private int radix;
    private short exponent;
    private String ruleText;
    private NFSubstitution sub1;
    private NFSubstitution sub2;
    private RuleBasedNumberFormat formatter;
    static final boolean $assertionsDisabled;
    
    public static Object makeRules(String ruleDescriptor, final NFRuleSet set, final NFRule nfRule, final RuleBasedNumberFormat ruleBasedNumberFormat) {
        final NFRule nfRule2 = new NFRule(ruleBasedNumberFormat);
        ruleDescriptor = nfRule2.parseRuleDescriptor(ruleDescriptor);
        final int index = ruleDescriptor.indexOf("[");
        final int index2 = ruleDescriptor.indexOf("]");
        if (index == -1 || index2 == -1 || index > index2 || nfRule2.getBaseValue() == -3L || nfRule2.getBaseValue() == -1L) {
            nfRule2.ruleText = ruleDescriptor;
            nfRule2.extractSubstitutions(set, nfRule, ruleBasedNumberFormat);
            return nfRule2;
        }
        NFRule nfRule3 = null;
        final StringBuilder sb = new StringBuilder();
        if ((nfRule2.baseValue > 0L && nfRule2.baseValue % Math.pow(nfRule2.radix, nfRule2.exponent) == 0.0) || nfRule2.baseValue == -2L || nfRule2.baseValue == -4L) {
            nfRule3 = new NFRule(ruleBasedNumberFormat);
            if (nfRule2.baseValue >= 0L) {
                nfRule3.baseValue = nfRule2.baseValue;
                if (!set.isFractionSet()) {
                    final NFRule nfRule4 = nfRule2;
                    ++nfRule4.baseValue;
                }
            }
            else if (nfRule2.baseValue == -2L) {
                nfRule3.baseValue = -3L;
            }
            else if (nfRule2.baseValue == -4L) {
                nfRule3.baseValue = nfRule2.baseValue;
                nfRule2.baseValue = -2L;
            }
            nfRule3.radix = nfRule2.radix;
            nfRule3.exponent = nfRule2.exponent;
            sb.append(ruleDescriptor.substring(0, index));
            if (index2 + 1 < ruleDescriptor.length()) {
                sb.append(ruleDescriptor.substring(index2 + 1));
            }
            nfRule3.ruleText = sb.toString();
            nfRule3.extractSubstitutions(set, nfRule, ruleBasedNumberFormat);
        }
        sb.setLength(0);
        sb.append(ruleDescriptor.substring(0, index));
        sb.append(ruleDescriptor.substring(index + 1, index2));
        if (index2 + 1 < ruleDescriptor.length()) {
            sb.append(ruleDescriptor.substring(index2 + 1));
        }
        nfRule2.ruleText = sb.toString();
        nfRule2.extractSubstitutions(set, nfRule, ruleBasedNumberFormat);
        if (nfRule3 == null) {
            return nfRule2;
        }
        return new NFRule[] { nfRule3, nfRule2 };
    }
    
    public NFRule(final RuleBasedNumberFormat formatter) {
        this.radix = 10;
        this.exponent = 0;
        this.ruleText = null;
        this.sub1 = null;
        this.sub2 = null;
        this.formatter = null;
        this.formatter = formatter;
    }
    
    private String parseRuleDescriptor(String s) {
        int index = s.indexOf(":");
        if (0 == -1) {
            this.setBaseValue(0L);
        }
        else {
            final String substring = s.substring(0, 0);
            ++index;
            while (0 < s.length() && PatternProps.isWhiteSpace(s.charAt(0))) {
                ++index;
            }
            s = s.substring(0);
            if (substring.equals("-x")) {
                this.setBaseValue(-1L);
            }
            else if (substring.equals("x.x")) {
                this.setBaseValue(-2L);
            }
            else if (substring.equals("0.x")) {
                this.setBaseValue(-3L);
            }
            else if (substring.equals("x.0")) {
                this.setBaseValue(-4L);
            }
            else if (substring.charAt(0) >= '0' && substring.charAt(0) <= '9') {
                final StringBuilder sb = new StringBuilder();
                while (0 < substring.length()) {
                    substring.charAt(0);
                    if (32 >= 48 && 32 <= 57) {
                        sb.append(' ');
                    }
                    else {
                        if (32 == 47) {
                            break;
                        }
                        if (32 == 62) {
                            break;
                        }
                        if (!PatternProps.isWhiteSpace(32) && 32 != 44) {
                            if (32 != 46) {
                                throw new IllegalArgumentException("Illegal character in rule descriptor");
                            }
                        }
                    }
                    ++index;
                }
                this.setBaseValue(Long.parseLong(sb.toString()));
                if (32 == 47) {
                    sb.setLength(0);
                    ++index;
                    while (0 < substring.length()) {
                        substring.charAt(0);
                        if (32 >= 48 && 32 <= 57) {
                            sb.append(' ');
                        }
                        else {
                            if (32 == 62) {
                                break;
                            }
                            if (!PatternProps.isWhiteSpace(32) && 32 != 44) {
                                if (32 != 46) {
                                    throw new IllegalArgumentException("Illegal character is rule descriptor");
                                }
                            }
                        }
                        ++index;
                    }
                    this.radix = Integer.parseInt(sb.toString());
                    if (this.radix == 0) {
                        throw new IllegalArgumentException("Rule can't have radix of 0");
                    }
                    this.exponent = this.expectedExponent();
                }
                if (32 == 62) {
                    while (0 < substring.length()) {
                        substring.charAt(0);
                        if (32 != 62 || this.exponent <= 0) {
                            throw new IllegalArgumentException("Illegal character in rule descriptor");
                        }
                        --this.exponent;
                        ++index;
                    }
                }
            }
        }
        if (s.length() > 0 && s.charAt(0) == '\'') {
            s = s.substring(1);
        }
        return s;
    }
    
    private void extractSubstitutions(final NFRuleSet set, final NFRule nfRule, final RuleBasedNumberFormat ruleBasedNumberFormat) {
        this.sub1 = this.extractSubstitution(set, nfRule, ruleBasedNumberFormat);
        this.sub2 = this.extractSubstitution(set, nfRule, ruleBasedNumberFormat);
    }
    
    private NFSubstitution extractSubstitution(final NFRuleSet set, final NFRule nfRule, final RuleBasedNumberFormat ruleBasedNumberFormat) {
        final int indexOfAny = this.indexOfAny(new String[] { "<<", "<%", "<#", "<0", ">>", ">%", ">#", ">0", "=%", "=#", "=0" });
        if (indexOfAny == -1) {
            return NFSubstitution.makeSubstitution(this.ruleText.length(), this, nfRule, set, ruleBasedNumberFormat, "");
        }
        int index;
        if (this.ruleText.substring(indexOfAny).startsWith(">>>")) {
            index = indexOfAny + 2;
        }
        else {
            final char char1 = this.ruleText.charAt(indexOfAny);
            index = this.ruleText.indexOf(char1, indexOfAny + 1);
            if (char1 == '<' && index != -1 && index < this.ruleText.length() - 1 && this.ruleText.charAt(index + 1) == char1) {
                ++index;
            }
        }
        if (index == -1) {
            return NFSubstitution.makeSubstitution(this.ruleText.length(), this, nfRule, set, ruleBasedNumberFormat, "");
        }
        final NFSubstitution substitution = NFSubstitution.makeSubstitution(indexOfAny, this, nfRule, set, ruleBasedNumberFormat, this.ruleText.substring(indexOfAny, index + 1));
        this.ruleText = this.ruleText.substring(0, indexOfAny) + this.ruleText.substring(index + 1);
        return substitution;
    }
    
    public final void setBaseValue(final long baseValue) {
        this.baseValue = baseValue;
        if (this.baseValue >= 1L) {
            this.radix = 10;
            this.exponent = this.expectedExponent();
            if (this.sub1 != null) {
                this.sub1.setDivisor(this.radix, this.exponent);
            }
            if (this.sub2 != null) {
                this.sub2.setDivisor(this.radix, this.exponent);
            }
        }
        else {
            this.radix = 10;
            this.exponent = 0;
        }
    }
    
    private short expectedExponent() {
        if (this.radix == 0 || this.baseValue < 1L) {
            return 0;
        }
        final short n = (short)(Math.log((double)this.baseValue) / Math.log(this.radix));
        if (Math.pow(this.radix, n + 1) <= this.baseValue) {
            return (short)(n + 1);
        }
        return n;
    }
    
    private int indexOfAny(final String[] array) {
        while (0 < array.length) {
            final int index = this.ruleText.indexOf(array[0]);
            if (index != -1 && (-1 == -1 || index < -1)) {}
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof NFRule) {
            final NFRule nfRule = (NFRule)o;
            return this.baseValue == nfRule.baseValue && this.radix == nfRule.radix && this.exponent == nfRule.exponent && this.ruleText.equals(nfRule.ruleText) && this.sub1.equals(nfRule.sub1) && this.sub2.equals(nfRule.sub2);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (this.baseValue == -1L) {
            sb.append("-x: ");
        }
        else if (this.baseValue == -2L) {
            sb.append("x.x: ");
        }
        else if (this.baseValue == -3L) {
            sb.append("0.x: ");
        }
        else if (this.baseValue == -4L) {
            sb.append("x.0: ");
        }
        else {
            sb.append(String.valueOf(this.baseValue));
            if (this.radix != 10) {
                sb.append('/');
                sb.append(String.valueOf(this.radix));
            }
            while (0 < this.expectedExponent() - this.exponent) {
                sb.append('>');
                int n = 0;
                ++n;
            }
            sb.append(": ");
        }
        if (this.ruleText.startsWith(" ") && (this.sub1 == null || this.sub1.getPos() != 0)) {
            sb.append("'");
        }
        final StringBuilder sb2 = new StringBuilder(this.ruleText);
        sb2.insert(this.sub2.getPos(), this.sub2.toString());
        sb2.insert(this.sub1.getPos(), this.sub1.toString());
        sb.append(sb2.toString());
        sb.append(';');
        return sb.toString();
    }
    
    public final long getBaseValue() {
        return this.baseValue;
    }
    
    public double getDivisor() {
        return Math.pow(this.radix, this.exponent);
    }
    
    public void doFormat(final long n, final StringBuffer sb, final int n2) {
        sb.insert(n2, this.ruleText);
        this.sub2.doSubstitution(n, sb, n2);
        this.sub1.doSubstitution(n, sb, n2);
    }
    
    public void doFormat(final double n, final StringBuffer sb, final int n2) {
        sb.insert(n2, this.ruleText);
        this.sub2.doSubstitution(n, sb, n2);
        this.sub1.doSubstitution(n, sb, n2);
    }
    
    public boolean shouldRollBack(final double n) {
        return (this.sub1.isModulusSubstitution() || this.sub2.isModulusSubstitution()) && n % Math.pow(this.radix, this.exponent) == 0.0 && this.baseValue % Math.pow(this.radix, this.exponent) != 0.0;
    }
    
    public Number doParse(final String s, final ParsePosition parsePosition, final boolean b, final double n) {
        final ParsePosition parsePosition2 = new ParsePosition(0);
        final String stripPrefix = this.stripPrefix(s, this.ruleText.substring(0, this.sub1.getPos()), parsePosition2);
        final int n2 = s.length() - stripPrefix.length();
        if (parsePosition2.getIndex() == 0 && this.sub1.getPos() != 0) {
            return 0L;
        }
        double n3 = 0.0;
        final double n4 = (double)Math.max(0L, this.baseValue);
        do {
            parsePosition2.setIndex(0);
            final double doubleValue = this.matchToDelimiter(stripPrefix, 0, n4, this.ruleText.substring(this.sub1.getPos(), this.sub2.getPos()), parsePosition2, this.sub1, n).doubleValue();
            if (parsePosition2.getIndex() != 0 || this.sub1.isNullSubstitution()) {
                parsePosition2.getIndex();
                final String substring = stripPrefix.substring(parsePosition2.getIndex());
                final ParsePosition parsePosition3 = new ParsePosition(0);
                final double doubleValue2 = this.matchToDelimiter(substring, 0, doubleValue, this.ruleText.substring(this.sub2.getPos()), parsePosition3, this.sub2, n).doubleValue();
                if ((parsePosition3.getIndex() == 0 && !this.sub2.isNullSubstitution()) || n2 + parsePosition2.getIndex() + parsePosition3.getIndex() <= 0) {
                    continue;
                }
                final int n5 = n2 + parsePosition2.getIndex() + parsePosition3.getIndex();
                n3 = doubleValue2;
            }
        } while (this.sub1.getPos() != this.sub2.getPos() && parsePosition2.getIndex() > 0 && parsePosition2.getIndex() < stripPrefix.length() && parsePosition2.getIndex() != 0);
        parsePosition.setIndex(0);
        if (b && 0 > 0 && this.sub1.isNullSubstitution()) {
            n3 = 1.0 / n3;
        }
        if (n3 == (long)n3) {
            return (long)n3;
        }
        return new Double(n3);
    }
    
    private String stripPrefix(final String s, final String s2, final ParsePosition parsePosition) {
        if (s2.length() == 0) {
            return s;
        }
        final int prefixLength = this.prefixLength(s, s2);
        if (prefixLength != 0) {
            parsePosition.setIndex(parsePosition.getIndex() + prefixLength);
            return s.substring(prefixLength);
        }
        return s;
    }
    
    private Number matchToDelimiter(final String s, final int n, final double n2, final String s2, final ParsePosition parsePosition, final NFSubstitution nfSubstitution, final double n3) {
        if (!this.allIgnorable(s2)) {
            final ParsePosition parsePosition2 = new ParsePosition(0);
            final int[] text = this.findText(s, s2, n);
            int i = text[0];
            int n4 = text[1];
            while (i >= 0) {
                final String substring = s.substring(0, i);
                if (substring.length() > 0) {
                    final Number doParse = nfSubstitution.doParse(substring, parsePosition2, n2, n3, this.formatter.lenientParseEnabled());
                    if (parsePosition2.getIndex() == i) {
                        parsePosition.setIndex(i + n4);
                        return doParse;
                    }
                }
                parsePosition2.setIndex(0);
                final int[] text2 = this.findText(s, s2, i + n4);
                i = text2[0];
                n4 = text2[1];
            }
            parsePosition.setIndex(0);
            return 0L;
        }
        final ParsePosition parsePosition3 = new ParsePosition(0);
        Long value = 0L;
        final Number doParse2 = nfSubstitution.doParse(s, parsePosition3, n2, n3, this.formatter.lenientParseEnabled());
        if (parsePosition3.getIndex() != 0 || nfSubstitution.isNullSubstitution()) {
            parsePosition.setIndex(parsePosition3.getIndex());
            if (doParse2 != null) {
                value = (Long)doParse2;
            }
        }
        return value;
    }
    
    private int prefixLength(final String s, final String s2) {
        if (s2.length() == 0) {
            return 0;
        }
        final RbnfLenientScanner lenientScanner = this.formatter.getLenientScanner();
        if (lenientScanner != null) {
            return lenientScanner.prefixLength(s, s2);
        }
        if (s.startsWith(s2)) {
            return s2.length();
        }
        return 0;
    }
    
    private int[] findText(final String s, final String s2, final int n) {
        final RbnfLenientScanner lenientScanner = this.formatter.getLenientScanner();
        if (lenientScanner == null) {
            return new int[] { s.indexOf(s2, n), s2.length() };
        }
        return lenientScanner.findText(s, s2, n);
    }
    
    private boolean allIgnorable(final String s) {
        if (s.length() == 0) {
            return true;
        }
        final RbnfLenientScanner lenientScanner = this.formatter.getLenientScanner();
        return lenientScanner != null && lenientScanner.allIgnorable(s);
    }
    
    static {
        $assertionsDisabled = !NFRule.class.desiredAssertionStatus();
    }
}
