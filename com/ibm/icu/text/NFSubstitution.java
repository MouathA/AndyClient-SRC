package com.ibm.icu.text;

import java.text.*;

abstract class NFSubstitution
{
    int pos;
    NFRuleSet ruleSet;
    DecimalFormat numberFormat;
    RuleBasedNumberFormat rbnf;
    static final boolean $assertionsDisabled;
    
    public static NFSubstitution makeSubstitution(final int n, final NFRule nfRule, final NFRule nfRule2, final NFRuleSet set, final RuleBasedNumberFormat ruleBasedNumberFormat, final String s) {
        if (s.length() == 0) {
            return new NullSubstitution(n, set, ruleBasedNumberFormat, s);
        }
        switch (s.charAt(0)) {
            case '<': {
                if (nfRule.getBaseValue() == -1L) {
                    throw new IllegalArgumentException("<< not allowed in negative-number rule");
                }
                if (nfRule.getBaseValue() == -2L || nfRule.getBaseValue() == -3L || nfRule.getBaseValue() == -4L) {
                    return new IntegralPartSubstitution(n, set, ruleBasedNumberFormat, s);
                }
                if (set.isFractionSet()) {
                    return new NumeratorSubstitution(n, (double)nfRule.getBaseValue(), ruleBasedNumberFormat.getDefaultRuleSet(), ruleBasedNumberFormat, s);
                }
                return new MultiplierSubstitution(n, nfRule.getDivisor(), set, ruleBasedNumberFormat, s);
            }
            case '>': {
                if (nfRule.getBaseValue() == -1L) {
                    return new AbsoluteValueSubstitution(n, set, ruleBasedNumberFormat, s);
                }
                if (nfRule.getBaseValue() == -2L || nfRule.getBaseValue() == -3L || nfRule.getBaseValue() == -4L) {
                    return new FractionalPartSubstitution(n, set, ruleBasedNumberFormat, s);
                }
                if (set.isFractionSet()) {
                    throw new IllegalArgumentException(">> not allowed in fraction rule set");
                }
                return new ModulusSubstitution(n, nfRule.getDivisor(), nfRule2, set, ruleBasedNumberFormat, s);
            }
            case '=': {
                return new SameValueSubstitution(n, set, ruleBasedNumberFormat, s);
            }
            default: {
                throw new IllegalArgumentException("Illegal substitution character");
            }
        }
    }
    
    NFSubstitution(final int pos, final NFRuleSet set, final RuleBasedNumberFormat rbnf, String substring) {
        this.ruleSet = null;
        this.numberFormat = null;
        this.rbnf = null;
        this.pos = pos;
        this.rbnf = rbnf;
        if (substring.length() >= 2 && substring.charAt(0) == substring.charAt(substring.length() - 1)) {
            substring = substring.substring(1, substring.length() - 1);
        }
        else if (substring.length() != 0) {
            throw new IllegalArgumentException("Illegal substitution syntax");
        }
        if (substring.length() == 0) {
            this.ruleSet = set;
        }
        else if (substring.charAt(0) == '%') {
            this.ruleSet = rbnf.findRuleSet(substring);
        }
        else if (substring.charAt(0) == '#' || substring.charAt(0) == '0') {
            (this.numberFormat = new DecimalFormat(substring)).setDecimalFormatSymbols(rbnf.getDecimalFormatSymbols());
        }
        else {
            if (substring.charAt(0) != '>') {
                throw new IllegalArgumentException("Illegal substitution syntax");
            }
            this.ruleSet = set;
            this.numberFormat = null;
        }
    }
    
    public void setDivisor(final int n, final int n2) {
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() == o.getClass()) {
            final NFSubstitution nfSubstitution = (NFSubstitution)o;
            return this.pos == nfSubstitution.pos && (this.ruleSet != null || nfSubstitution.ruleSet == null) && ((this.numberFormat != null) ? this.numberFormat.equals(nfSubstitution.numberFormat) : (nfSubstitution.numberFormat == null));
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
        if (this.ruleSet != null) {
            return this.tokenChar() + this.ruleSet.getName() + this.tokenChar();
        }
        return this.tokenChar() + this.numberFormat.toPattern() + this.tokenChar();
    }
    
    public void doSubstitution(final long n, final StringBuffer sb, final int n2) {
        if (this.ruleSet != null) {
            this.ruleSet.format(this.transformNumber(n), sb, n2 + this.pos);
        }
        else {
            double n3 = this.transformNumber((double)n);
            if (this.numberFormat.getMaximumFractionDigits() == 0) {
                n3 = Math.floor(n3);
            }
            sb.insert(n2 + this.pos, this.numberFormat.format(n3));
        }
    }
    
    public void doSubstitution(final double n, final StringBuffer sb, final int n2) {
        final double transformNumber = this.transformNumber(n);
        if (transformNumber == Math.floor(transformNumber) && this.ruleSet != null) {
            this.ruleSet.format((long)transformNumber, sb, n2 + this.pos);
        }
        else if (this.ruleSet != null) {
            this.ruleSet.format(transformNumber, sb, n2 + this.pos);
        }
        else {
            sb.insert(n2 + this.pos, this.numberFormat.format(transformNumber));
        }
    }
    
    public abstract long transformNumber(final long p0);
    
    public abstract double transformNumber(final double p0);
    
    public Number doParse(final String s, final ParsePosition parsePosition, final double n, double calcUpperBound, final boolean b) {
        calcUpperBound = this.calcUpperBound(calcUpperBound);
        Number n2;
        if (this.ruleSet != null) {
            n2 = this.ruleSet.parse(s, parsePosition, calcUpperBound);
            if (b && !this.ruleSet.isFractionSet() && parsePosition.getIndex() == 0) {
                n2 = this.rbnf.getDecimalFormat().parse(s, parsePosition);
            }
        }
        else {
            n2 = this.numberFormat.parse(s, parsePosition);
        }
        if (parsePosition.getIndex() == 0) {
            return n2;
        }
        final double composeRuleValue = this.composeRuleValue(n2.doubleValue(), n);
        if (composeRuleValue == (long)composeRuleValue) {
            return (long)composeRuleValue;
        }
        return new Double(composeRuleValue);
    }
    
    public abstract double composeRuleValue(final double p0, final double p1);
    
    public abstract double calcUpperBound(final double p0);
    
    public final int getPos() {
        return this.pos;
    }
    
    abstract char tokenChar();
    
    public boolean isNullSubstitution() {
        return false;
    }
    
    public boolean isModulusSubstitution() {
        return false;
    }
    
    static {
        $assertionsDisabled = !NFSubstitution.class.desiredAssertionStatus();
    }
}
