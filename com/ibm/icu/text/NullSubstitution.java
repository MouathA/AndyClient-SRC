package com.ibm.icu.text;

import java.text.*;

class NullSubstitution extends NFSubstitution
{
    static final boolean $assertionsDisabled;
    
    NullSubstitution(final int n, final NFRuleSet set, final RuleBasedNumberFormat ruleBasedNumberFormat, final String s) {
        super(n, set, ruleBasedNumberFormat, s);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Override
    public String toString() {
        return "";
    }
    
    @Override
    public void doSubstitution(final long n, final StringBuffer sb, final int n2) {
    }
    
    @Override
    public void doSubstitution(final double n, final StringBuffer sb, final int n2) {
    }
    
    @Override
    public long transformNumber(final long n) {
        return 0L;
    }
    
    @Override
    public double transformNumber(final double n) {
        return 0.0;
    }
    
    @Override
    public Number doParse(final String s, final ParsePosition parsePosition, final double n, final double n2, final boolean b) {
        if (n == (long)n) {
            return (long)n;
        }
        return new Double(n);
    }
    
    @Override
    public double composeRuleValue(final double n, final double n2) {
        return 0.0;
    }
    
    @Override
    public double calcUpperBound(final double n) {
        return 0.0;
    }
    
    @Override
    public boolean isNullSubstitution() {
        return true;
    }
    
    @Override
    char tokenChar() {
        return ' ';
    }
    
    static {
        $assertionsDisabled = !NullSubstitution.class.desiredAssertionStatus();
    }
}
