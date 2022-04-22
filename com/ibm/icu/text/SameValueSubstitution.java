package com.ibm.icu.text;

class SameValueSubstitution extends NFSubstitution
{
    SameValueSubstitution(final int n, final NFRuleSet set, final RuleBasedNumberFormat ruleBasedNumberFormat, final String s) {
        super(n, set, ruleBasedNumberFormat, s);
        if (s.equals("==")) {
            throw new IllegalArgumentException("== is not a legal token");
        }
    }
    
    @Override
    public long transformNumber(final long n) {
        return n;
    }
    
    @Override
    public double transformNumber(final double n) {
        return n;
    }
    
    @Override
    public double composeRuleValue(final double n, final double n2) {
        return n;
    }
    
    @Override
    public double calcUpperBound(final double n) {
        return n;
    }
    
    @Override
    char tokenChar() {
        return '=';
    }
}
