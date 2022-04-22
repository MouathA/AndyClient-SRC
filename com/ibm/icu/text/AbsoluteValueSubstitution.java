package com.ibm.icu.text;

class AbsoluteValueSubstitution extends NFSubstitution
{
    AbsoluteValueSubstitution(final int n, final NFRuleSet set, final RuleBasedNumberFormat ruleBasedNumberFormat, final String s) {
        super(n, set, ruleBasedNumberFormat, s);
    }
    
    @Override
    public long transformNumber(final long n) {
        return Math.abs(n);
    }
    
    @Override
    public double transformNumber(final double n) {
        return Math.abs(n);
    }
    
    @Override
    public double composeRuleValue(final double n, final double n2) {
        return -n;
    }
    
    @Override
    public double calcUpperBound(final double n) {
        return Double.MAX_VALUE;
    }
    
    @Override
    char tokenChar() {
        return '>';
    }
}
