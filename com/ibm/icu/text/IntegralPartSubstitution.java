package com.ibm.icu.text;

class IntegralPartSubstitution extends NFSubstitution
{
    IntegralPartSubstitution(final int n, final NFRuleSet set, final RuleBasedNumberFormat ruleBasedNumberFormat, final String s) {
        super(n, set, ruleBasedNumberFormat, s);
    }
    
    @Override
    public long transformNumber(final long n) {
        return n;
    }
    
    @Override
    public double transformNumber(final double n) {
        return Math.floor(n);
    }
    
    @Override
    public double composeRuleValue(final double n, final double n2) {
        return n + n2;
    }
    
    @Override
    public double calcUpperBound(final double n) {
        return Double.MAX_VALUE;
    }
    
    @Override
    char tokenChar() {
        return '<';
    }
}
