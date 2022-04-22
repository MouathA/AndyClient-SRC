package com.ibm.icu.text;

import java.text.*;

class FractionalPartSubstitution extends NFSubstitution
{
    private boolean byDigits;
    private boolean useSpaces;
    
    FractionalPartSubstitution(final int n, final NFRuleSet set, final RuleBasedNumberFormat ruleBasedNumberFormat, final String s) {
        super(n, set, ruleBasedNumberFormat, s);
        this.byDigits = false;
        this.useSpaces = true;
        if (s.equals(">>") || s.equals(">>>") || set == this.ruleSet) {
            this.byDigits = true;
            if (s.equals(">>>")) {
                this.useSpaces = false;
            }
        }
        else {
            this.ruleSet.makeIntoFractionRuleSet();
        }
    }
    
    @Override
    public void doSubstitution(final double n, final StringBuffer sb, final int n2) {
        if (!this.byDigits) {
            super.doSubstitution(n, sb, n2);
        }
        else {
            final DigitList list = new DigitList();
            list.set(n, 20, true);
            while (list.count > Math.max(0, list.decimalAt)) {
                if (true && this.useSpaces) {
                    sb.insert(n2 + this.pos, ' ');
                }
                final NFRuleSet ruleSet = this.ruleSet;
                final byte[] digits = list.digits;
                final DigitList list2 = list;
                final int count = list2.count - 1;
                list2.count = count;
                ruleSet.format(digits[count] - 48, sb, n2 + this.pos);
            }
            while (list.decimalAt < 0) {
                if (true && this.useSpaces) {
                    sb.insert(n2 + this.pos, ' ');
                }
                this.ruleSet.format(0L, sb, n2 + this.pos);
                final DigitList list3 = list;
                ++list3.decimalAt;
            }
        }
    }
    
    @Override
    public long transformNumber(final long n) {
        return 0L;
    }
    
    @Override
    public double transformNumber(final double n) {
        return n - Math.floor(n);
    }
    
    @Override
    public Number doParse(final String s, final ParsePosition parsePosition, final double n, final double n2, final boolean b) {
        if (!this.byDigits) {
            return super.doParse(s, parsePosition, n, 0.0, b);
        }
        String s2 = s;
        final ParsePosition parsePosition2 = new ParsePosition(1);
        final DigitList list = new DigitList();
        while (s2.length() > 0 && parsePosition2.getIndex() != 0) {
            parsePosition2.setIndex(0);
            int n3 = this.ruleSet.parse(s2, parsePosition2, 10.0).intValue();
            if (b && parsePosition2.getIndex() == 0) {
                final Number parse = this.rbnf.getDecimalFormat().parse(s2, parsePosition2);
                if (parse != null) {
                    n3 = parse.intValue();
                }
            }
            if (parsePosition2.getIndex() != 0) {
                list.append(48 + n3);
                parsePosition.setIndex(parsePosition.getIndex() + parsePosition2.getIndex());
                s2 = s2.substring(parsePosition2.getIndex());
                while (s2.length() > 0 && s2.charAt(0) == ' ') {
                    s2 = s2.substring(1);
                    parsePosition.setIndex(parsePosition.getIndex() + 1);
                }
            }
        }
        return new Double(this.composeRuleValue((list.count == 0) ? 0.0 : list.getDouble(), n));
    }
    
    @Override
    public double composeRuleValue(final double n, final double n2) {
        return n + n2;
    }
    
    @Override
    public double calcUpperBound(final double n) {
        return 0.0;
    }
    
    @Override
    char tokenChar() {
        return '>';
    }
}
