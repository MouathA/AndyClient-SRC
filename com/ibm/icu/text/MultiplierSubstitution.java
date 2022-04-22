package com.ibm.icu.text;

class MultiplierSubstitution extends NFSubstitution
{
    double divisor;
    static final boolean $assertionsDisabled;
    
    MultiplierSubstitution(final int n, final double divisor, final NFRuleSet set, final RuleBasedNumberFormat ruleBasedNumberFormat, final String s) {
        super(n, set, ruleBasedNumberFormat, s);
        this.divisor = divisor;
        if (divisor == 0.0) {
            throw new IllegalStateException("Substitution with bad divisor (" + divisor + ") " + s.substring(0, n) + " | " + s.substring(n));
        }
    }
    
    @Override
    public void setDivisor(final int n, final int n2) {
        this.divisor = Math.pow(n, n2);
        if (this.divisor == 0.0) {
            throw new IllegalStateException("Substitution with divisor 0");
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.divisor == ((MultiplierSubstitution)o).divisor;
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Override
    public long transformNumber(final long n) {
        return (long)Math.floor(n / this.divisor);
    }
    
    @Override
    public double transformNumber(final double n) {
        if (this.ruleSet == null) {
            return n / this.divisor;
        }
        return Math.floor(n / this.divisor);
    }
    
    @Override
    public double composeRuleValue(final double n, final double n2) {
        return n * this.divisor;
    }
    
    @Override
    public double calcUpperBound(final double n) {
        return this.divisor;
    }
    
    @Override
    char tokenChar() {
        return '<';
    }
    
    static {
        $assertionsDisabled = !MultiplierSubstitution.class.desiredAssertionStatus();
    }
}
