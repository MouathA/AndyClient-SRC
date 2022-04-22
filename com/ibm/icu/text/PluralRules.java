package com.ibm.icu.text;

import java.io.*;
import java.text.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;
import java.util.*;

public class PluralRules implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final RuleList rules;
    private final Set keywords;
    private int repeatLimit;
    private transient int hashCode;
    private transient Map _keySamplesMap;
    private transient Map _keyLimitedMap;
    public static final String KEYWORD_ZERO = "zero";
    public static final String KEYWORD_ONE = "one";
    public static final String KEYWORD_TWO = "two";
    public static final String KEYWORD_FEW = "few";
    public static final String KEYWORD_MANY = "many";
    public static final String KEYWORD_OTHER = "other";
    public static final double NO_UNIQUE_VALUE = -0.00123456777;
    private static final Constraint NO_CONSTRAINT;
    private static final Rule DEFAULT_RULE;
    public static final PluralRules DEFAULT;
    
    public static PluralRules parseDescription(String trim) throws ParseException {
        trim = trim.trim();
        if (trim.length() == 0) {
            return PluralRules.DEFAULT;
        }
        return new PluralRules(parseRuleChain(trim));
    }
    
    public static PluralRules createRules(final String s) {
        return parseDescription(s);
    }
    
    private static Constraint parseConstraint(String lowerCase) throws ParseException {
        lowerCase = lowerCase.trim().toLowerCase(Locale.ENGLISH);
        Constraint constraint = null;
        final String[] splitString = Utility.splitString(lowerCase, "or");
        while (0 < splitString.length) {
            Constraint constraint2 = null;
            final String[] splitString2 = Utility.splitString(splitString[0], "and");
            while (0 < splitString2.length) {
                Constraint no_CONSTRAINT = PluralRules.NO_CONSTRAINT;
                final String trim = splitString2[0].trim();
                final String[] splitWhitespace = Utility.splitWhitespace(trim);
                long n = Long.MAX_VALUE;
                long max = Long.MIN_VALUE;
                long[] array = null;
                final String[] array2 = splitWhitespace;
                final int n2 = 0;
                int n3 = 0;
                ++n3;
                final String s = array2[n2];
                if (!"n".equals(s)) {
                    throw unexpected(s, trim);
                }
                if (0 < splitWhitespace.length) {
                    final String[] array3 = splitWhitespace;
                    final int n4 = 0;
                    ++n3;
                    String s2 = array3[n4];
                    if ("mod".equals(s2)) {
                        final String[] array4 = splitWhitespace;
                        final int n5 = 0;
                        ++n3;
                        Integer.parseInt(array4[n5]);
                        final String[] array5 = splitWhitespace;
                        final int n6 = 0;
                        ++n3;
                        s2 = nextToken(array5, n6, trim);
                    }
                    String s3;
                    if ("is".equals(s2)) {
                        final String[] array6 = splitWhitespace;
                        final int n7 = 0;
                        ++n3;
                        s3 = nextToken(array6, n7, trim);
                        if ("not".equals(s3)) {
                            final String[] array7 = splitWhitespace;
                            final int n8 = 0;
                            ++n3;
                            s3 = nextToken(array7, n8, trim);
                        }
                    }
                    else {
                        if ("not".equals(s2)) {
                            final String[] array8 = splitWhitespace;
                            final int n9 = 0;
                            ++n3;
                            s2 = nextToken(array8, n9, trim);
                        }
                        if ("in".equals(s2)) {
                            final String[] array9 = splitWhitespace;
                            final int n10 = 0;
                            ++n3;
                            s3 = nextToken(array9, n10, trim);
                        }
                        else {
                            if (!"within".equals(s2)) {
                                throw unexpected(s2, trim);
                            }
                            final String[] array10 = splitWhitespace;
                            final int n11 = 0;
                            ++n3;
                            s3 = nextToken(array10, n11, trim);
                        }
                    }
                    if (true) {
                        final String[] splitString3 = Utility.splitString(s3, ",");
                        array = new long[splitString3.length * 2];
                        while (0 < splitString3.length) {
                            final String s4 = splitString3[0];
                            final String[] splitString4 = Utility.splitString(s4, "..");
                            long n12;
                            long long1;
                            if (splitString4.length == 2) {
                                n12 = Long.parseLong(splitString4[0]);
                                long1 = Long.parseLong(splitString4[1]);
                                if (n12 > long1) {
                                    throw unexpected(s4, trim);
                                }
                            }
                            else {
                                if (splitString4.length != 1) {
                                    throw unexpected(s4, trim);
                                }
                                long1 = (n12 = Long.parseLong(splitString4[0]));
                            }
                            array[0] = n12;
                            array[1] = long1;
                            n = Math.min(n, n12);
                            max = Math.max(max, long1);
                            int n13 = 0;
                            ++n13;
                            final int n14;
                            n14 += 2;
                        }
                        if (array.length == 2) {
                            array = null;
                        }
                    }
                    else {
                        max = (n = Long.parseLong(s3));
                    }
                    if (0 != splitWhitespace.length) {
                        throw unexpected(splitWhitespace[0], trim);
                    }
                    no_CONSTRAINT = new RangeConstraint(0, false, false, n, max, array);
                }
                if (constraint2 == null) {
                    constraint2 = no_CONSTRAINT;
                }
                else {
                    constraint2 = new AndConstraint(constraint2, no_CONSTRAINT);
                }
                int n15 = 0;
                ++n15;
            }
            if (constraint == null) {
                constraint = constraint2;
            }
            else {
                constraint = new OrConstraint(constraint, constraint2);
            }
            int n16 = 0;
            ++n16;
        }
        return constraint;
    }
    
    private static ParseException unexpected(final String s, final String s2) {
        return new ParseException("unexpected token '" + s + "' in '" + s2 + "'", -1);
    }
    
    private static String nextToken(final String[] array, final int n, final String s) throws ParseException {
        if (n < array.length) {
            return array[n];
        }
        throw new ParseException("missing token at end of '" + s + "'", -1);
    }
    
    private static Rule parseRule(String trim) throws ParseException {
        final int index = trim.indexOf(58);
        if (index == -1) {
            throw new ParseException("missing ':' in rule description '" + trim + "'", 0);
        }
        final String trim2 = trim.substring(0, index).trim();
        if (!isValidKeyword(trim2)) {
            throw new ParseException("keyword '" + trim2 + " is not valid", 0);
        }
        trim = trim.substring(index + 1).trim();
        if (trim.length() == 0) {
            throw new ParseException("missing constraint in '" + trim + "'", index + 1);
        }
        return new ConstrainedRule(trim2, parseConstraint(trim));
    }
    
    private static RuleChain parseRuleChain(final String s) throws ParseException {
        RuleChain addRule = null;
        final String[] split = Utility.split(s, ';');
        while (0 < split.length) {
            final Rule rule = parseRule(split[0].trim());
            if (addRule == null) {
                addRule = new RuleChain(rule);
            }
            else {
                addRule = addRule.addRule(rule);
            }
            int n = 0;
            ++n;
        }
        return addRule;
    }
    
    public static PluralRules forLocale(final ULocale uLocale) {
        return PluralRulesLoader.loader.forLocale(uLocale, PluralType.CARDINAL);
    }
    
    public static PluralRules forLocale(final ULocale uLocale, final PluralType pluralType) {
        return PluralRulesLoader.loader.forLocale(uLocale, pluralType);
    }
    
    private static boolean isValidKeyword(final String s) {
        return PatternProps.isIdentifier(s);
    }
    
    private PluralRules(final RuleList rules) {
        this.rules = rules;
        this.keywords = Collections.unmodifiableSet((Set<?>)rules.getKeywords());
    }
    
    public String select(final double n) {
        return this.rules.select(n);
    }
    
    public Set getKeywords() {
        return this.keywords;
    }
    
    public double getUniqueKeywordValue(final String s) {
        final Collection allKeywordValues = this.getAllKeywordValues(s);
        if (allKeywordValues != null && allKeywordValues.size() == 1) {
            return allKeywordValues.iterator().next();
        }
        return -0.00123456777;
    }
    
    public Collection getAllKeywordValues(final String s) {
        if (!this.keywords.contains(s)) {
            return Collections.emptyList();
        }
        final Collection collection = this.getKeySamplesMap().get(s);
        if (collection.size() > 2 && !this.getKeyLimitedMap().get(s)) {
            return null;
        }
        return collection;
    }
    
    public Collection getSamples(final String s) {
        if (!this.keywords.contains(s)) {
            return null;
        }
        return this.getKeySamplesMap().get(s);
    }
    
    private Map getKeyLimitedMap() {
        this.initKeyMaps();
        return this._keyLimitedMap;
    }
    
    private Map getKeySamplesMap() {
        this.initKeyMaps();
        return this._keySamplesMap;
    }
    
    private synchronized void initKeyMaps() {
        if (this._keySamplesMap == null) {
            final HashMap<String, Boolean> keyLimitedMap = new HashMap<String, Boolean>();
            for (final String s : this.keywords) {
                keyLimitedMap.put(s, this.rules.isLimited(s));
            }
            this._keyLimitedMap = keyLimitedMap;
            final HashMap<String, List<Object>> keySamplesMap = (HashMap<String, List<Object>>)new HashMap<Object, Object>();
            int size = this.keywords.size();
            final int n = Math.max(5, this.getRepeatLimit() * 3) * 2;
            while (size > 0 && 0 < n) {
                final double n2 = 0 / 2.0;
                final String select = this.select(n2);
                final boolean booleanValue = this._keyLimitedMap.get(select);
                List<?> list = keySamplesMap.get(select);
                Label_0248: {
                    if (list == null) {
                        list = new ArrayList<Object>(3);
                        keySamplesMap.put(select, (List<Object>)list);
                    }
                    else if (!booleanValue && list.size() == 3) {
                        break Label_0248;
                    }
                    list.add((Object)n2);
                    if (!booleanValue && list.size() == 3) {
                        --size;
                    }
                }
                int n3 = 0;
                ++n3;
            }
            if (size > 0) {
                for (final String s2 : this.keywords) {
                    if (!keySamplesMap.containsKey(s2)) {
                        keySamplesMap.put(s2, Collections.emptyList());
                        if (--size == 0) {
                            break;
                        }
                        continue;
                    }
                }
            }
            for (final Map.Entry<String, List<Object>> entry : keySamplesMap.entrySet()) {
                keySamplesMap.put(entry.getKey(), Collections.unmodifiableList((List<?>)entry.getValue()));
            }
            this._keySamplesMap = keySamplesMap;
        }
    }
    
    public static ULocale[] getAvailableULocales() {
        return PluralRulesLoader.loader.getAvailableULocales();
    }
    
    public static ULocale getFunctionalEquivalent(final ULocale uLocale, final boolean[] array) {
        return PluralRulesLoader.loader.getFunctionalEquivalent(uLocale, array);
    }
    
    @Override
    public String toString() {
        return "keywords: " + this.keywords + " limit: " + this.getRepeatLimit() + " rules: " + this.rules.toString();
    }
    
    @Override
    public int hashCode() {
        if (this.hashCode == 0) {
            this.keywords.hashCode();
            while (0 < 12) {
                final int n = 31 + this.select(0).hashCode();
                int n2 = 0;
                ++n2;
            }
            if (!true) {}
            this.hashCode = 1;
        }
        return this.hashCode;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof PluralRules && this.equals((PluralRules)o);
    }
    
    public boolean equals(final PluralRules pluralRules) {
        if (pluralRules == null) {
            return false;
        }
        if (pluralRules == this) {
            return true;
        }
        if (this.hashCode() != pluralRules.hashCode()) {
            return false;
        }
        if (!pluralRules.getKeywords().equals(this.keywords)) {
            return false;
        }
        while (0 < Math.max(this.getRepeatLimit(), pluralRules.getRepeatLimit()) * 2) {
            if (!this.select(0).equals(pluralRules.select(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private int getRepeatLimit() {
        if (this.repeatLimit == 0) {
            this.repeatLimit = this.rules.getRepeatLimit() + 1;
        }
        return this.repeatLimit;
    }
    
    public KeywordStatus getKeywordStatus(final String s, final int n, Set emptySet, final Output output) {
        if (output != null) {
            output.value = null;
        }
        if (!this.rules.getKeywords().contains(s)) {
            return KeywordStatus.INVALID;
        }
        final Collection allKeywordValues = this.getAllKeywordValues(s);
        if (allKeywordValues == null) {
            return KeywordStatus.UNBOUNDED;
        }
        final int size = allKeywordValues.size();
        if (emptySet == null) {
            emptySet = (Set<Double>)Collections.emptySet();
        }
        if (size > emptySet.size()) {
            if (size == 1) {
                if (output != null) {
                    output.value = allKeywordValues.iterator().next();
                }
                return KeywordStatus.UNIQUE;
            }
            return KeywordStatus.BOUNDED;
        }
        else {
            final HashSet set = new HashSet<Object>(allKeywordValues);
            final Iterator<Double> iterator = emptySet.iterator();
            while (iterator.hasNext()) {
                set.remove(iterator.next() - n);
            }
            if (set.size() == 0) {
                return KeywordStatus.SUPPRESSED;
            }
            if (output != null && set.size() == 1) {
                output.value = set.iterator().next();
            }
            return (size == 1) ? KeywordStatus.UNIQUE : KeywordStatus.BOUNDED;
        }
    }
    
    static {
        NO_CONSTRAINT = new Constraint() {
            private static final long serialVersionUID = 9163464945387899416L;
            
            public boolean isFulfilled(final double n) {
                return true;
            }
            
            public boolean isLimited() {
                return false;
            }
            
            @Override
            public String toString() {
                return "n is any";
            }
            
            public int updateRepeatLimit(final int n) {
                return n;
            }
        };
        DEFAULT_RULE = new Rule() {
            private static final long serialVersionUID = -5677499073940822149L;
            
            public String getKeyword() {
                return "other";
            }
            
            public boolean appliesTo(final double n) {
                return true;
            }
            
            public boolean isLimited() {
                return false;
            }
            
            @Override
            public String toString() {
                return "(other)";
            }
            
            public int updateRepeatLimit(final int n) {
                return n;
            }
        };
        DEFAULT = new PluralRules(new RuleChain(PluralRules.DEFAULT_RULE));
    }
    
    public enum KeywordStatus
    {
        INVALID("INVALID", 0), 
        SUPPRESSED("SUPPRESSED", 1), 
        UNIQUE("UNIQUE", 2), 
        BOUNDED("BOUNDED", 3), 
        UNBOUNDED("UNBOUNDED", 4);
        
        private static final KeywordStatus[] $VALUES;
        
        private KeywordStatus(final String s, final int n) {
        }
        
        static {
            $VALUES = new KeywordStatus[] { KeywordStatus.INVALID, KeywordStatus.SUPPRESSED, KeywordStatus.UNIQUE, KeywordStatus.BOUNDED, KeywordStatus.UNBOUNDED };
        }
    }
    
    private static class RuleChain implements RuleList, Serializable
    {
        private static final long serialVersionUID = 1L;
        private final Rule rule;
        private final RuleChain next;
        
        public RuleChain(final Rule rule) {
            this(rule, null);
        }
        
        private RuleChain(final Rule rule, final RuleChain next) {
            this.rule = rule;
            this.next = next;
        }
        
        public RuleChain addRule(final Rule rule) {
            return new RuleChain(rule, this);
        }
        
        private Rule selectRule(final double n) {
            Rule rule = null;
            if (this.next != null) {
                rule = this.next.selectRule(n);
            }
            if (rule == null && this.rule.appliesTo(n)) {
                rule = this.rule;
            }
            return rule;
        }
        
        public String select(final double n) {
            final Rule selectRule = this.selectRule(n);
            if (selectRule == null) {
                return "other";
            }
            return selectRule.getKeyword();
        }
        
        public Set getKeywords() {
            final HashSet<String> set = new HashSet<String>();
            set.add("other");
            for (RuleChain next = this; next != null; next = next.next) {
                set.add(next.rule.getKeyword());
            }
            return set;
        }
        
        public boolean isLimited(final String s) {
            for (RuleChain next = this; next != null; next = next.next) {
                if (s.equals(next.rule.getKeyword()) && !next.rule.isLimited()) {
                    return false;
                }
            }
            return true;
        }
        
        public int getRepeatLimit() {
            for (RuleChain next = this; next != null; next = next.next) {
                next.rule.updateRepeatLimit(0);
            }
            return 0;
        }
        
        @Override
        public String toString() {
            String s = this.rule.toString();
            if (this.next != null) {
                s = this.next.toString() + "; " + s;
            }
            return s;
        }
    }
    
    private interface Rule extends Serializable
    {
        String getKeyword();
        
        boolean appliesTo(final double p0);
        
        boolean isLimited();
        
        int updateRepeatLimit(final int p0);
    }
    
    private interface RuleList extends Serializable
    {
        String select(final double p0);
        
        Set getKeywords();
        
        int getRepeatLimit();
        
        boolean isLimited(final String p0);
    }
    
    private static class ConstrainedRule implements Rule, Serializable
    {
        private static final long serialVersionUID = 1L;
        private final String keyword;
        private final Constraint constraint;
        
        public ConstrainedRule(final String keyword, final Constraint constraint) {
            this.keyword = keyword;
            this.constraint = constraint;
        }
        
        public Rule and(final Constraint constraint) {
            return new ConstrainedRule(this.keyword, new AndConstraint(this.constraint, constraint));
        }
        
        public Rule or(final Constraint constraint) {
            return new ConstrainedRule(this.keyword, new OrConstraint(this.constraint, constraint));
        }
        
        public String getKeyword() {
            return this.keyword;
        }
        
        public boolean appliesTo(final double n) {
            return this.constraint.isFulfilled(n);
        }
        
        public int updateRepeatLimit(final int n) {
            return this.constraint.updateRepeatLimit(n);
        }
        
        public boolean isLimited() {
            return this.constraint.isLimited();
        }
        
        @Override
        public String toString() {
            return this.keyword + ": " + this.constraint;
        }
    }
    
    private interface Constraint extends Serializable
    {
        boolean isFulfilled(final double p0);
        
        boolean isLimited();
        
        int updateRepeatLimit(final int p0);
    }
    
    private static class AndConstraint extends BinaryConstraint
    {
        private static final long serialVersionUID = 7766999779862263523L;
        
        AndConstraint(final Constraint constraint, final Constraint constraint2) {
            super(constraint, constraint2, " && ");
        }
        
        public boolean isFulfilled(final double n) {
            return this.a.isFulfilled(n) && this.b.isFulfilled(n);
        }
        
        public boolean isLimited() {
            return this.a.isLimited() || this.b.isLimited();
        }
    }
    
    private abstract static class BinaryConstraint implements Constraint, Serializable
    {
        private static final long serialVersionUID = 1L;
        protected final Constraint a;
        protected final Constraint b;
        private final String conjunction;
        
        protected BinaryConstraint(final Constraint a, final Constraint b, final String conjunction) {
            this.a = a;
            this.b = b;
            this.conjunction = conjunction;
        }
        
        public int updateRepeatLimit(final int n) {
            return this.a.updateRepeatLimit(this.b.updateRepeatLimit(n));
        }
        
        @Override
        public String toString() {
            return this.a.toString() + this.conjunction + this.b.toString();
        }
    }
    
    private static class OrConstraint extends BinaryConstraint
    {
        private static final long serialVersionUID = 1405488568664762222L;
        
        OrConstraint(final Constraint constraint, final Constraint constraint2) {
            super(constraint, constraint2, " || ");
        }
        
        public boolean isFulfilled(final double n) {
            return this.a.isFulfilled(n) || this.b.isFulfilled(n);
        }
        
        public boolean isLimited() {
            return this.a.isLimited() && this.b.isLimited();
        }
    }
    
    private static class RangeConstraint implements Constraint, Serializable
    {
        private static final long serialVersionUID = 1L;
        private int mod;
        private boolean inRange;
        private boolean integersOnly;
        private long lowerBound;
        private long upperBound;
        private long[] range_list;
        
        RangeConstraint(final int mod, final boolean inRange, final boolean integersOnly, final long lowerBound, final long upperBound, final long[] range_list) {
            this.mod = mod;
            this.inRange = inRange;
            this.integersOnly = integersOnly;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.range_list = range_list;
        }
        
        public boolean isFulfilled(double n) {
            if (this.integersOnly && n - (long)n != 0.0) {
                return !this.inRange;
            }
            if (this.mod != 0) {
                n %= this.mod;
            }
            final boolean b = n >= this.lowerBound && n <= this.upperBound;
            if (false && this.range_list != null) {
                while (!false && 0 < this.range_list.length) {
                    final boolean b2 = n >= this.range_list[0] && n <= this.range_list[1];
                    final int n2;
                    n2 += 2;
                }
            }
            return !this.inRange;
        }
        
        public boolean isLimited() {
            return this.integersOnly && this.inRange && this.mod == 0;
        }
        
        public int updateRepeatLimit(final int n) {
            return Math.max((this.mod == 0) ? ((int)this.upperBound) : this.mod, n);
        }
        
        @Override
        public String toString() {
            final ListBuilder listBuilder = new ListBuilder();
            if (this.mod > 1) {
                listBuilder.add("mod", this.mod);
            }
            if (this.inRange) {
                listBuilder.add("in");
            }
            else {
                listBuilder.add("except");
            }
            if (this.integersOnly) {
                listBuilder.add("ints");
            }
            if (this.lowerBound == this.upperBound) {
                listBuilder.add(String.valueOf(this.lowerBound));
            }
            else {
                listBuilder.add(String.valueOf(this.lowerBound) + "-" + String.valueOf(this.upperBound));
            }
            if (this.range_list != null) {
                listBuilder.add(Arrays.toString(this.range_list));
            }
            return listBuilder.toString();
        }
        
        class ListBuilder
        {
            StringBuilder sb;
            final RangeConstraint this$0;
            
            ListBuilder(final RangeConstraint this$0) {
                this.this$0 = this$0;
                this.sb = new StringBuilder("[");
            }
            
            ListBuilder add(final String s) {
                return this.add(s, null);
            }
            
            ListBuilder add(final String s, final Object o) {
                if (this.sb.length() > 1) {
                    this.sb.append(", ");
                }
                this.sb.append(s);
                if (o != null) {
                    this.sb.append(": ").append(o.toString());
                }
                return this;
            }
            
            @Override
            public String toString() {
                final String string = this.sb.append(']').toString();
                this.sb = null;
                return string;
            }
        }
    }
    
    public enum PluralType
    {
        CARDINAL("CARDINAL", 0), 
        ORDINAL("ORDINAL", 1);
        
        private static final PluralType[] $VALUES;
        
        private PluralType(final String s, final int n) {
        }
        
        static {
            $VALUES = new PluralType[] { PluralType.CARDINAL, PluralType.ORDINAL };
        }
    }
}
