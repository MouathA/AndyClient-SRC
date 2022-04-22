package com.ibm.icu.text;

import java.text.*;

class ModulusSubstitution extends NFSubstitution
{
    double divisor;
    NFRule ruleToUse;
    static final boolean $assertionsDisabled;
    
    ModulusSubstitution(final int n, final double divisor, final NFRule ruleToUse, final NFRuleSet set, final RuleBasedNumberFormat ruleBasedNumberFormat, final String s) {
        super(n, set, ruleBasedNumberFormat, s);
        this.divisor = divisor;
        if (divisor == 0.0) {
            throw new IllegalStateException("Substitution with bad divisor (" + divisor + ") " + s.substring(0, n) + " | " + s.substring(n));
        }
        if (s.equals(">>>")) {
            this.ruleToUse = ruleToUse;
        }
        else {
            this.ruleToUse = null;
        }
    }
    
    @Override
    public void setDivisor(final int n, final int n2) {
        this.divisor = Math.pow(n, n2);
        if (this.divisor == 0.0) {
            throw new IllegalStateException("Substitution with bad divisor");
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.divisor == ((ModulusSubstitution)o).divisor;
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Override
    public void doSubstitution(final long n, final StringBuffer sb, final int n2) {
        if (this.ruleToUse == null) {
            super.doSubstitution(n, sb, n2);
        }
        else {
            this.ruleToUse.doFormat(this.transformNumber(n), sb, n2 + this.pos);
        }
    }
    
    @Override
    public void doSubstitution(final double n, final StringBuffer sb, final int n2) {
        if (this.ruleToUse == null) {
            super.doSubstitution(n, sb, n2);
        }
        else {
            this.ruleToUse.doFormat(this.transformNumber(n), sb, n2 + this.pos);
        }
    }
    
    @Override
    public long transformNumber(final long n) {
        return (long)Math.floor(n % this.divisor);
    }
    
    @Override
    public double transformNumber(final double n) {
        return Math.floor(n % this.divisor);
    }
    
    @Override
    public Number doParse(final String s, final ParsePosition parsePosition, final double n, final double n2, final boolean b) {
        if (this.ruleToUse == null) {
            return super.doParse(s, parsePosition, n, n2, b);
        }
        final Number doParse = this.ruleToUse.doParse(s, parsePosition, false, n2);
        if (parsePosition.getIndex() == 0) {
            return doParse;
        }
        final double composeRuleValue = this.composeRuleValue(doParse.doubleValue(), n);
        if (composeRuleValue == (long)composeRuleValue) {
            return (long)composeRuleValue;
        }
        return new Double(composeRuleValue);
    }
    
    @Override
    public double composeRuleValue(final double n, final double n2) {
        return n2 - n2 % this.divisor + n;
    }
    
    @Override
    public double calcUpperBound(final double n) {
        return this.divisor;
    }
    
    @Override
    public boolean isModulusSubstitution() {
        return true;
    }
    
    @Override
    char tokenChar() {
        return '>';
    }
    
    static {
        $assertionsDisabled = !ModulusSubstitution.class.desiredAssertionStatus();
    }
}
