package com.ibm.icu.text;

import java.text.*;

class NumeratorSubstitution extends NFSubstitution
{
    double denominator;
    boolean withZeros;
    static final boolean $assertionsDisabled;
    
    NumeratorSubstitution(final int n, final double denominator, final NFRuleSet set, final RuleBasedNumberFormat ruleBasedNumberFormat, final String s) {
        super(n, set, ruleBasedNumberFormat, fixdesc(s));
        this.denominator = denominator;
        this.withZeros = s.endsWith("<<");
    }
    
    static String fixdesc(final String s) {
        return s.endsWith("<<") ? s.substring(0, s.length() - 1) : s;
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.denominator == ((NumeratorSubstitution)o).denominator;
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Override
    public void doSubstitution(final double n, final StringBuffer sb, int n2) {
        final double transformNumber = this.transformNumber(n);
        if (this.withZeros && this.ruleSet != null) {
            long n3 = (long)transformNumber;
            final int length = sb.length();
            while ((n3 *= 10L) < this.denominator) {
                sb.insert(n2 + this.pos, ' ');
                this.ruleSet.format(0L, sb, n2 + this.pos);
            }
            n2 += sb.length() - length;
        }
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
    
    @Override
    public long transformNumber(final long n) {
        return Math.round(n * this.denominator);
    }
    
    @Override
    public double transformNumber(final double n) {
        return (double)Math.round(n * this.denominator);
    }
    
    @Override
    public Number doParse(String substring, final ParsePosition parsePosition, final double n, final double n2, final boolean b) {
        int n3 = 0;
        if (this.withZeros) {
            String s = substring;
            final ParsePosition parsePosition2 = new ParsePosition(1);
            while (s.length() > 0 && parsePosition2.getIndex() != 0) {
                parsePosition2.setIndex(0);
                this.ruleSet.parse(s, parsePosition2, 1.0).intValue();
                if (parsePosition2.getIndex() == 0) {
                    break;
                }
                ++n3;
                parsePosition.setIndex(parsePosition.getIndex() + parsePosition2.getIndex());
                s = s.substring(parsePosition2.getIndex());
                while (s.length() > 0 && s.charAt(0) == ' ') {
                    s = s.substring(1);
                    parsePosition.setIndex(parsePosition.getIndex() + 1);
                }
            }
            substring = substring.substring(parsePosition.getIndex());
            parsePosition.setIndex(0);
        }
        Number doParse = super.doParse(substring, parsePosition, this.withZeros ? 1.0 : n, n2, false);
        if (this.withZeros) {
            long longValue;
            long n4;
            for (longValue = doParse.longValue(), n4 = 1L; n4 <= longValue; n4 *= 10L) {}
            while (0 > 0) {
                n4 *= 10L;
                --n3;
            }
            doParse = new Double(longValue / (double)n4);
        }
        return doParse;
    }
    
    @Override
    public double composeRuleValue(final double n, final double n2) {
        return n / n2;
    }
    
    @Override
    public double calcUpperBound(final double n) {
        return this.denominator;
    }
    
    @Override
    char tokenChar() {
        return '<';
    }
    
    static {
        $assertionsDisabled = !NumeratorSubstitution.class.desiredAssertionStatus();
    }
}
