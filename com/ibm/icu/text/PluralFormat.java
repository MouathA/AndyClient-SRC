package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.util.*;
import java.text.*;
import com.ibm.icu.impl.*;
import java.io.*;

public class PluralFormat extends UFormat
{
    private static final long serialVersionUID = 1L;
    private ULocale ulocale;
    private PluralRules pluralRules;
    private String pattern;
    private transient MessagePattern msgPattern;
    private Map parsedValues;
    private NumberFormat numberFormat;
    private transient double offset;
    private transient PluralSelectorAdapter pluralRulesWrapper;
    static final boolean $assertionsDisabled;
    
    public PluralFormat() {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter(null);
        this.init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public PluralFormat(final ULocale uLocale) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter(null);
        this.init(null, PluralRules.PluralType.CARDINAL, uLocale);
    }
    
    public PluralFormat(final PluralRules pluralRules) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter(null);
        this.init(pluralRules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public PluralFormat(final ULocale uLocale, final PluralRules pluralRules) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter(null);
        this.init(pluralRules, PluralRules.PluralType.CARDINAL, uLocale);
    }
    
    public PluralFormat(final ULocale uLocale, final PluralRules.PluralType pluralType) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter(null);
        this.init(null, pluralType, uLocale);
    }
    
    public PluralFormat(final String s) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter(null);
        this.init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT));
        this.applyPattern(s);
    }
    
    public PluralFormat(final ULocale uLocale, final String s) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter(null);
        this.init(null, PluralRules.PluralType.CARDINAL, uLocale);
        this.applyPattern(s);
    }
    
    public PluralFormat(final PluralRules pluralRules, final String s) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter(null);
        this.init(pluralRules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT));
        this.applyPattern(s);
    }
    
    public PluralFormat(final ULocale uLocale, final PluralRules pluralRules, final String s) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter(null);
        this.init(pluralRules, PluralRules.PluralType.CARDINAL, uLocale);
        this.applyPattern(s);
    }
    
    public PluralFormat(final ULocale uLocale, final PluralRules.PluralType pluralType, final String s) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter(null);
        this.init(null, pluralType, uLocale);
        this.applyPattern(s);
    }
    
    private void init(final PluralRules pluralRules, final PluralRules.PluralType pluralType, final ULocale ulocale) {
        this.ulocale = ulocale;
        this.pluralRules = ((pluralRules == null) ? PluralRules.forLocale(this.ulocale, pluralType) : pluralRules);
        this.resetPattern();
        this.numberFormat = NumberFormat.getInstance(this.ulocale);
    }
    
    private void resetPattern() {
        this.pattern = null;
        if (this.msgPattern != null) {
            this.msgPattern.clear();
        }
        this.offset = 0.0;
    }
    
    public void applyPattern(final String pattern) {
        this.pattern = pattern;
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        this.msgPattern.parsePluralStyle(pattern);
        this.offset = this.msgPattern.getPluralOffset(0);
    }
    
    public String toPattern() {
        return this.pattern;
    }
    
    static int findSubMessage(final MessagePattern messagePattern, int limitPartIndex, final PluralSelector pluralSelector, final double n) {
        final int countParts = messagePattern.countParts();
        final MessagePattern.Part part = messagePattern.getPart(limitPartIndex);
        double numericValue;
        if (part.getType().hasNumericValue()) {
            numericValue = messagePattern.getNumericValue(part);
            ++limitPartIndex;
        }
        else {
            numericValue = 0.0;
        }
        String select = null;
        do {
            final MessagePattern.Part part2 = messagePattern.getPart(limitPartIndex++);
            final MessagePattern.Part.Type type = part2.getType();
            if (type == MessagePattern.Part.Type.ARG_LIMIT) {
                break;
            }
            assert type == MessagePattern.Part.Type.ARG_SELECTOR;
            if (messagePattern.getPartType(limitPartIndex).hasNumericValue()) {
                if (n == messagePattern.getNumericValue(messagePattern.getPart(limitPartIndex++))) {
                    return limitPartIndex;
                }
            }
            else if (!true) {
                if (messagePattern.partSubstringMatches(part2, "other")) {
                    if (!false && select != null && select.equals("other")) {}
                }
                else {
                    if (select == null) {
                        select = pluralSelector.select(n - numericValue);
                        if (!false || select.equals("other")) {}
                    }
                    if (!true && messagePattern.partSubstringMatches(part2, select)) {}
                }
            }
            limitPartIndex = messagePattern.getLimitPartIndex(limitPartIndex);
        } while (++limitPartIndex < countParts);
        return 0;
    }
    
    public final String format(double n) {
        if (this.msgPattern == null || this.msgPattern.countParts() == 0) {
            return this.numberFormat.format(n);
        }
        int n2 = findSubMessage(this.msgPattern, 0, this.pluralRulesWrapper, n);
        n -= this.offset;
        StringBuilder sb = null;
        int n3 = this.msgPattern.getPart(n2).getLimit();
        int index;
        while (true) {
            final MessagePattern.Part part = this.msgPattern.getPart(++n2);
            final MessagePattern.Part.Type type = part.getType();
            index = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                break;
            }
            if (type == MessagePattern.Part.Type.REPLACE_NUMBER || (type == MessagePattern.Part.Type.SKIP_SYNTAX && this.msgPattern.jdkAposMode())) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(this.pattern, n3, index);
                if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                    sb.append(this.numberFormat.format(n));
                }
                n3 = part.getLimit();
            }
            else {
                if (type != MessagePattern.Part.Type.ARG_START) {
                    continue;
                }
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(this.pattern, n3, index);
                final int n4 = index;
                n2 = this.msgPattern.getLimitPartIndex(n2);
                final int limit = this.msgPattern.getPart(n2).getLimit();
                MessagePattern.appendReducedApostrophes(this.pattern, n4, limit, sb);
                n3 = limit;
            }
        }
        if (sb == null) {
            return this.pattern.substring(n3, index);
        }
        return sb.append(this.pattern, n3, index).toString();
    }
    
    @Override
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (o instanceof Number) {
            sb.append(this.format(((Number)o).doubleValue()));
            return sb;
        }
        throw new IllegalArgumentException("'" + o + "' is not a Number");
    }
    
    public Number parse(final String s, final ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public void setLocale(ULocale default1) {
        if (default1 == null) {
            default1 = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        this.init(null, PluralRules.PluralType.CARDINAL, default1);
    }
    
    public void setNumberFormat(final NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final PluralFormat pluralFormat = (PluralFormat)o;
        return Utility.objectEquals(this.ulocale, pluralFormat.ulocale) && Utility.objectEquals(this.pluralRules, pluralFormat.pluralRules) && Utility.objectEquals(this.msgPattern, pluralFormat.msgPattern) && Utility.objectEquals(this.numberFormat, pluralFormat.numberFormat);
    }
    
    public boolean equals(final PluralFormat pluralFormat) {
        return this.equals((Object)pluralFormat);
    }
    
    @Override
    public int hashCode() {
        return this.pluralRules.hashCode() ^ this.parsedValues.hashCode();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("locale=" + this.ulocale);
        sb.append(", rules='" + this.pluralRules + "'");
        sb.append(", pattern='" + this.pattern + "'");
        sb.append(", format='" + this.numberFormat + "'");
        return sb.toString();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.pluralRulesWrapper = new PluralSelectorAdapter(null);
        this.parsedValues = null;
        if (this.pattern != null) {
            this.applyPattern(this.pattern);
        }
    }
    
    static PluralRules access$000(final PluralFormat pluralFormat) {
        return pluralFormat.pluralRules;
    }
    
    static {
        $assertionsDisabled = !PluralFormat.class.desiredAssertionStatus();
    }
    
    private final class PluralSelectorAdapter implements PluralSelector
    {
        final PluralFormat this$0;
        
        private PluralSelectorAdapter(final PluralFormat this$0) {
            this.this$0 = this$0;
        }
        
        public String select(final double n) {
            return PluralFormat.access$000(this.this$0).select(n);
        }
        
        PluralSelectorAdapter(final PluralFormat pluralFormat, final PluralFormat$1 object) {
            this(pluralFormat);
        }
    }
    
    interface PluralSelector
    {
        String select(final double p0);
    }
}
