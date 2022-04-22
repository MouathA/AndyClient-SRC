package com.ibm.icu.text;

public class FilteredNormalizer2 extends Normalizer2
{
    private Normalizer2 norm2;
    private UnicodeSet set;
    
    public FilteredNormalizer2(final Normalizer2 norm2, final UnicodeSet set) {
        this.norm2 = norm2;
        this.set = set;
    }
    
    @Override
    public StringBuilder normalize(final CharSequence charSequence, final StringBuilder sb) {
        if (sb == charSequence) {
            throw new IllegalArgumentException();
        }
        sb.setLength(0);
        this.normalize(charSequence, sb, UnicodeSet.SpanCondition.SIMPLE);
        return sb;
    }
    
    @Override
    public Appendable normalize(final CharSequence charSequence, final Appendable appendable) {
        if (appendable == charSequence) {
            throw new IllegalArgumentException();
        }
        return this.normalize(charSequence, appendable, UnicodeSet.SpanCondition.SIMPLE);
    }
    
    @Override
    public StringBuilder normalizeSecondAndAppend(final StringBuilder sb, final CharSequence charSequence) {
        return this.normalizeSecondAndAppend(sb, charSequence, true);
    }
    
    @Override
    public StringBuilder append(final StringBuilder sb, final CharSequence charSequence) {
        return this.normalizeSecondAndAppend(sb, charSequence, false);
    }
    
    @Override
    public String getDecomposition(final int n) {
        return this.set.contains(n) ? this.norm2.getDecomposition(n) : null;
    }
    
    @Override
    public String getRawDecomposition(final int n) {
        return this.set.contains(n) ? this.norm2.getRawDecomposition(n) : null;
    }
    
    @Override
    public int composePair(final int n, final int n2) {
        return (this.set.contains(n) && this.set.contains(n2)) ? this.norm2.composePair(n, n2) : -1;
    }
    
    @Override
    public int getCombiningClass(final int n) {
        return this.set.contains(n) ? this.norm2.getCombiningClass(n) : 0;
    }
    
    @Override
    public boolean isNormalized(final CharSequence charSequence) {
        UnicodeSet.SpanCondition spanCondition = UnicodeSet.SpanCondition.SIMPLE;
        while (0 < charSequence.length()) {
            final int span = this.set.span(charSequence, 0, spanCondition);
            if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
                spanCondition = UnicodeSet.SpanCondition.SIMPLE;
            }
            else {
                if (!this.norm2.isNormalized(charSequence.subSequence(0, span))) {
                    return false;
                }
                spanCondition = UnicodeSet.SpanCondition.NOT_CONTAINED;
            }
        }
        return true;
    }
    
    @Override
    public Normalizer.QuickCheckResult quickCheck(final CharSequence charSequence) {
        Normalizer.QuickCheckResult yes = Normalizer.YES;
        UnicodeSet.SpanCondition spanCondition = UnicodeSet.SpanCondition.SIMPLE;
        while (0 < charSequence.length()) {
            final int span = this.set.span(charSequence, 0, spanCondition);
            if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
                spanCondition = UnicodeSet.SpanCondition.SIMPLE;
            }
            else {
                final Normalizer.QuickCheckResult quickCheck = this.norm2.quickCheck(charSequence.subSequence(0, span));
                if (quickCheck == Normalizer.NO) {
                    return quickCheck;
                }
                if (quickCheck == Normalizer.MAYBE) {
                    yes = quickCheck;
                }
                spanCondition = UnicodeSet.SpanCondition.NOT_CONTAINED;
            }
        }
        return yes;
    }
    
    @Override
    public int spanQuickCheckYes(final CharSequence charSequence) {
        UnicodeSet.SpanCondition spanCondition = UnicodeSet.SpanCondition.SIMPLE;
        while (0 < charSequence.length()) {
            final int span = this.set.span(charSequence, 0, spanCondition);
            if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
                spanCondition = UnicodeSet.SpanCondition.SIMPLE;
            }
            else {
                final int n = 0 + this.norm2.spanQuickCheckYes(charSequence.subSequence(0, span));
                if (n < span) {
                    return n;
                }
                spanCondition = UnicodeSet.SpanCondition.NOT_CONTAINED;
            }
        }
        return charSequence.length();
    }
    
    @Override
    public boolean hasBoundaryBefore(final int n) {
        return !this.set.contains(n) || this.norm2.hasBoundaryBefore(n);
    }
    
    @Override
    public boolean hasBoundaryAfter(final int n) {
        return !this.set.contains(n) || this.norm2.hasBoundaryAfter(n);
    }
    
    @Override
    public boolean isInert(final int n) {
        return !this.set.contains(n) || this.norm2.isInert(n);
    }
    
    private Appendable normalize(final CharSequence charSequence, final Appendable appendable, UnicodeSet.SpanCondition spanCondition) {
        final StringBuilder sb = new StringBuilder();
        while (0 < charSequence.length()) {
            final int span = this.set.span(charSequence, 0, spanCondition);
            final int n = span - 0;
            if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
                if (n != 0) {
                    appendable.append(charSequence, 0, span);
                }
                spanCondition = UnicodeSet.SpanCondition.SIMPLE;
            }
            else {
                if (n != 0) {
                    appendable.append(this.norm2.normalize(charSequence.subSequence(0, span), sb));
                }
                spanCondition = UnicodeSet.SpanCondition.NOT_CONTAINED;
            }
        }
        return appendable;
    }
    
    private StringBuilder normalizeSecondAndAppend(final StringBuilder sb, final CharSequence charSequence, final boolean b) {
        if (sb == charSequence) {
            throw new IllegalArgumentException();
        }
        if (sb.length() != 0) {
            final int span = this.set.span(charSequence, 0, UnicodeSet.SpanCondition.SIMPLE);
            if (span != 0) {
                final CharSequence subSequence = charSequence.subSequence(0, span);
                final int spanBack = this.set.spanBack(sb, Integer.MAX_VALUE, UnicodeSet.SpanCondition.SIMPLE);
                if (spanBack == 0) {
                    if (b) {
                        this.norm2.normalizeSecondAndAppend(sb, subSequence);
                    }
                    else {
                        this.norm2.append(sb, subSequence);
                    }
                }
                else {
                    final StringBuilder sb2 = new StringBuilder(sb.subSequence(spanBack, sb.length()));
                    if (b) {
                        this.norm2.normalizeSecondAndAppend(sb2, subSequence);
                    }
                    else {
                        this.norm2.append(sb2, subSequence);
                    }
                    sb.delete(spanBack, Integer.MAX_VALUE).append((CharSequence)sb2);
                }
            }
            if (span < charSequence.length()) {
                final CharSequence subSequence2 = charSequence.subSequence(span, charSequence.length());
                if (b) {
                    this.normalize(subSequence2, sb, UnicodeSet.SpanCondition.NOT_CONTAINED);
                }
                else {
                    sb.append(subSequence2);
                }
            }
            return sb;
        }
        if (b) {
            return this.normalize(charSequence, sb);
        }
        return sb.append(charSequence);
    }
}
