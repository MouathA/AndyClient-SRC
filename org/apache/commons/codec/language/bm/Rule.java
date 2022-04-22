package org.apache.commons.codec.language.bm;

import java.io.*;
import java.util.regex.*;
import java.util.*;

public class Rule
{
    public static final RPattern ALL_STRINGS_RMATCHER;
    public static final String ALL = "ALL";
    private static final String DOUBLE_QUOTE = "\"";
    private static final String HASH_INCLUDE = "#include";
    private static final Map RULES;
    private final RPattern lContext;
    private final String pattern;
    private final PhonemeExpr phoneme;
    private final RPattern rContext;
    
    private static boolean contains(final CharSequence charSequence, final char c) {
        while (0 < charSequence.length()) {
            if (charSequence.charAt(0) == c) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    private static String createResourceName(final NameType nameType, final RuleType ruleType, final String s) {
        return String.format("org/apache/commons/codec/language/bm/%s_%s_%s.txt", nameType.getName(), ruleType.getName(), s);
    }
    
    private static Scanner createScanner(final NameType nameType, final RuleType ruleType, final String s) {
        final String resourceName = createResourceName(nameType, ruleType, s);
        final InputStream resourceAsStream = Languages.class.getClassLoader().getResourceAsStream(resourceName);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Unable to load resource: " + resourceName);
        }
        return new Scanner(resourceAsStream, "UTF-8");
    }
    
    private static Scanner createScanner(final String s) {
        final String format = String.format("org/apache/commons/codec/language/bm/%s.txt", s);
        final InputStream resourceAsStream = Languages.class.getClassLoader().getResourceAsStream(format);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Unable to load resource: " + format);
        }
        return new Scanner(resourceAsStream, "UTF-8");
    }
    
    private static boolean endsWith(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence2.length() > charSequence.length()) {
            return false;
        }
        int n = charSequence.length() - 1;
        for (int i = charSequence2.length() - 1; i >= 0; --i) {
            if (charSequence.charAt(n) != charSequence2.charAt(i)) {
                return false;
            }
            --n;
        }
        return true;
    }
    
    public static List getInstance(final NameType nameType, final RuleType ruleType, final Languages.LanguageSet set) {
        final Map instanceMap = getInstanceMap(nameType, ruleType, set);
        final ArrayList list = new ArrayList();
        final Iterator<List> iterator = instanceMap.values().iterator();
        while (iterator.hasNext()) {
            list.addAll(iterator.next());
        }
        return list;
    }
    
    public static List getInstance(final NameType nameType, final RuleType ruleType, final String s) {
        return getInstance(nameType, ruleType, Languages.LanguageSet.from(new HashSet(Arrays.asList(s))));
    }
    
    public static Map getInstanceMap(final NameType nameType, final RuleType ruleType, final Languages.LanguageSet set) {
        return set.isSingleton() ? getInstanceMap(nameType, ruleType, set.getAny()) : getInstanceMap(nameType, ruleType, "any");
    }
    
    public static Map getInstanceMap(final NameType nameType, final RuleType ruleType, final String s) {
        final Map map = Rule.RULES.get(nameType).get(ruleType).get(s);
        if (map == null) {
            throw new IllegalArgumentException(String.format("No rules found for %s, %s, %s.", nameType.getName(), ruleType.getName(), s));
        }
        return map;
    }
    
    private static Phoneme parsePhoneme(final String s) {
        final int index = s.indexOf("[");
        if (index < 0) {
            return new Phoneme(s, Languages.ANY_LANGUAGE);
        }
        if (!s.endsWith("]")) {
            throw new IllegalArgumentException("Phoneme expression contains a '[' but does not end in ']'");
        }
        return new Phoneme(s.substring(0, index), Languages.LanguageSet.from(new HashSet(Arrays.asList(s.substring(index + 1, s.length() - 1).split("[+]")))));
    }
    
    private static PhonemeExpr parsePhonemeExpr(final String s) {
        if (!s.startsWith("(")) {
            return parsePhoneme(s);
        }
        if (!s.endsWith(")")) {
            throw new IllegalArgumentException("Phoneme starts with '(' so must end with ')'");
        }
        final ArrayList<Phoneme> list = new ArrayList<Phoneme>();
        final String substring = s.substring(1, s.length() - 1);
        final String[] split = substring.split("[|]");
        while (0 < split.length) {
            list.add(parsePhoneme(split[0]));
            int n = 0;
            ++n;
        }
        if (substring.startsWith("|") || substring.endsWith("|")) {
            list.add(new Phoneme("", Languages.ANY_LANGUAGE));
        }
        return new PhonemeList(list);
    }
    
    private static Map parseRules(final Scanner scanner, final String s) {
        final HashMap<String, List<Rule>> hashMap = new HashMap<String, List<Rule>>();
        while (scanner.hasNextLine()) {
            int n = 0;
            ++n;
            String s3;
            final String s2 = s3 = scanner.nextLine();
            if (true) {
                if (s3.endsWith("*/")) {
                    continue;
                }
                continue;
            }
            else {
                if (s3.startsWith("/*")) {
                    continue;
                }
                final int index = s3.indexOf("//");
                if (index >= 0) {
                    s3 = s3.substring(0, index);
                }
                final String trim = s3.trim();
                if (trim.length() == 0) {
                    continue;
                }
                if (trim.startsWith("#include")) {
                    final String trim2 = trim.substring(8).trim();
                    if (trim2.contains(" ")) {
                        throw new IllegalArgumentException("Malformed import statement '" + s2 + "' in " + s);
                    }
                    hashMap.putAll((Map<?, ?>)parseRules(createScanner(trim2), s + "->" + trim2));
                }
                else {
                    final String[] split = trim.split("\\s+");
                    if (split.length != 4) {
                        throw new IllegalArgumentException("Malformed rule statement split into " + split.length + " parts: " + s2 + " in " + s);
                    }
                    final Rule rule = new Rule(stripQuotes(split[0]), stripQuotes(split[1]), stripQuotes(split[2]), parsePhonemeExpr(stripQuotes(split[3])), 0, s) {
                        private final int myLine = this.val$cLine;
                        private final String loc = this.val$location;
                        final int val$cLine;
                        final String val$location;
                        
                        @Override
                        public String toString() {
                            final StringBuilder sb = new StringBuilder();
                            sb.append("Rule");
                            sb.append("{line=").append(this.myLine);
                            sb.append(", loc='").append(this.loc).append('\'');
                            sb.append('}');
                            return sb.toString();
                        }
                    };
                    final String substring = rule.pattern.substring(0, 1);
                    Object o = hashMap.get(substring);
                    if (o == null) {
                        o = new ArrayList<Rule$2>();
                        hashMap.put(substring, (List<Rule>)o);
                    }
                    ((List<Rule$2>)o).add(rule);
                }
            }
        }
        return hashMap;
    }
    
    private static RPattern pattern(final String s) {
        final int startsWith = s.startsWith("^") ? 1 : 0;
        final boolean endsWith = s.endsWith("$");
        final String substring = s.substring(startsWith, endsWith ? (s.length() - 1) : s.length());
        if (!substring.contains("[")) {
            if (startsWith && endsWith) {
                if (substring.length() == 0) {
                    return new RPattern() {
                        @Override
                        public boolean isMatch(final CharSequence charSequence) {
                            return charSequence.length() == 0;
                        }
                    };
                }
                return new RPattern(substring) {
                    final String val$content;
                    
                    @Override
                    public boolean isMatch(final CharSequence charSequence) {
                        return charSequence.equals(this.val$content);
                    }
                };
            }
            else {
                if ((startsWith || endsWith) && substring.length() == 0) {
                    return Rule.ALL_STRINGS_RMATCHER;
                }
                if (startsWith != 0) {
                    return new RPattern(substring) {
                        final String val$content;
                        
                        @Override
                        public boolean isMatch(final CharSequence charSequence) {
                            return Rule.access$100(charSequence, this.val$content);
                        }
                    };
                }
                if (endsWith) {
                    return new RPattern(substring) {
                        final String val$content;
                        
                        @Override
                        public boolean isMatch(final CharSequence charSequence) {
                            return Rule.access$200(charSequence, this.val$content);
                        }
                    };
                }
            }
        }
        else {
            final boolean startsWith2 = substring.startsWith("[");
            final boolean endsWith2 = substring.endsWith("]");
            if (startsWith2 && endsWith2) {
                String s2 = substring.substring(1, substring.length() - 1);
                if (!s2.contains("[")) {
                    final boolean startsWith3 = s2.startsWith("^");
                    if (startsWith3) {
                        s2 = s2.substring(1);
                    }
                    final String s3 = s2;
                    final boolean b = !startsWith3;
                    if (startsWith && endsWith) {
                        return new RPattern(s3, b) {
                            final String val$bContent;
                            final boolean val$shouldMatch;
                            
                            @Override
                            public boolean isMatch(final CharSequence charSequence) {
                                return charSequence.length() == 1 && Rule.access$300(this.val$bContent, charSequence.charAt(0)) == this.val$shouldMatch;
                            }
                        };
                    }
                    if (startsWith != 0) {
                        return new RPattern(s3, b) {
                            final String val$bContent;
                            final boolean val$shouldMatch;
                            
                            @Override
                            public boolean isMatch(final CharSequence charSequence) {
                                return charSequence.length() > 0 && Rule.access$300(this.val$bContent, charSequence.charAt(0)) == this.val$shouldMatch;
                            }
                        };
                    }
                    if (endsWith) {
                        return new RPattern(s3, b) {
                            final String val$bContent;
                            final boolean val$shouldMatch;
                            
                            @Override
                            public boolean isMatch(final CharSequence charSequence) {
                                return charSequence.length() > 0 && Rule.access$300(this.val$bContent, charSequence.charAt(charSequence.length() - 1)) == this.val$shouldMatch;
                            }
                        };
                    }
                }
            }
        }
        return new RPattern(s) {
            Pattern pattern = Pattern.compile(this.val$regex);
            final String val$regex;
            
            @Override
            public boolean isMatch(final CharSequence charSequence) {
                return this.pattern.matcher(charSequence).find();
            }
        };
    }
    
    private static boolean startsWith(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence2.length() > charSequence.length()) {
            return false;
        }
        while (0 < charSequence2.length()) {
            if (charSequence.charAt(0) != charSequence2.charAt(0)) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private static String stripQuotes(String s) {
        if (s.startsWith("\"")) {
            s = s.substring(1);
        }
        if (s.endsWith("\"")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
    
    public Rule(final String pattern, final String s, final String s2, final PhonemeExpr phoneme) {
        this.pattern = pattern;
        this.lContext = pattern(s + "$");
        this.rContext = pattern("^" + s2);
        this.phoneme = phoneme;
    }
    
    public RPattern getLContext() {
        return this.lContext;
    }
    
    public String getPattern() {
        return this.pattern;
    }
    
    public PhonemeExpr getPhoneme() {
        return this.phoneme;
    }
    
    public RPattern getRContext() {
        return this.rContext;
    }
    
    public boolean patternAndContextMatches(final CharSequence charSequence, final int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Can not match pattern at negative indexes");
        }
        final int n2 = n + this.pattern.length();
        return n2 <= charSequence.length() && charSequence.subSequence(n, n2).equals(this.pattern) && this.rContext.isMatch(charSequence.subSequence(n2, charSequence.length())) && this.lContext.isMatch(charSequence.subSequence(0, n));
    }
    
    static boolean access$100(final CharSequence charSequence, final CharSequence charSequence2) {
        return startsWith(charSequence, charSequence2);
    }
    
    static boolean access$200(final CharSequence charSequence, final CharSequence charSequence2) {
        return endsWith(charSequence, charSequence2);
    }
    
    static boolean access$300(final CharSequence charSequence, final char c) {
        return contains(charSequence, c);
    }
    
    static {
        ALL_STRINGS_RMATCHER = new RPattern() {
            @Override
            public boolean isMatch(final CharSequence charSequence) {
                return true;
            }
        };
        RULES = new EnumMap(NameType.class);
        final NameType[] values = NameType.values();
        while (0 < values.length) {
            final NameType nameType = values[0];
            final EnumMap<RuleType, Map<Object, Object>> enumMap = new EnumMap<RuleType, Map<Object, Object>>(RuleType.class);
            final RuleType[] values2 = RuleType.values();
            while (0 < values2.length) {
                final RuleType ruleType = values2[0];
                final HashMap<String, Map> hashMap = new HashMap<String, Map>();
                for (final String s : Languages.getInstance(nameType).getLanguages()) {
                    hashMap.put(s, parseRules(createScanner(nameType, ruleType, s), createResourceName(nameType, ruleType, s)));
                }
                if (!ruleType.equals(RuleType.RULES)) {
                    hashMap.put("common", parseRules(createScanner(nameType, ruleType, "common"), createResourceName(nameType, ruleType, "common")));
                }
                enumMap.put(ruleType, Collections.unmodifiableMap((Map<?, ?>)hashMap));
                int n = 0;
                ++n;
            }
            Rule.RULES.put(nameType, Collections.unmodifiableMap((Map<?, ?>)enumMap));
            int n2 = 0;
            ++n2;
        }
    }
    
    public interface RPattern
    {
        boolean isMatch(final CharSequence p0);
    }
    
    public static final class PhonemeList implements PhonemeExpr
    {
        private final List phonemes;
        
        public PhonemeList(final List phonemes) {
            this.phonemes = phonemes;
        }
        
        @Override
        public List getPhonemes() {
            return this.phonemes;
        }
        
        @Override
        public Iterable getPhonemes() {
            return this.getPhonemes();
        }
    }
    
    public static final class Phoneme implements PhonemeExpr
    {
        public static final Comparator COMPARATOR;
        private final StringBuilder phonemeText;
        private final Languages.LanguageSet languages;
        
        public Phoneme(final CharSequence charSequence, final Languages.LanguageSet languages) {
            this.phonemeText = new StringBuilder(charSequence);
            this.languages = languages;
        }
        
        public Phoneme(final Phoneme phoneme, final Phoneme phoneme2) {
            this(phoneme.phonemeText, phoneme.languages);
            this.phonemeText.append((CharSequence)phoneme2.phonemeText);
        }
        
        public Phoneme(final Phoneme phoneme, final Phoneme phoneme2, final Languages.LanguageSet set) {
            this(phoneme.phonemeText, set);
            this.phonemeText.append((CharSequence)phoneme2.phonemeText);
        }
        
        public Phoneme append(final CharSequence charSequence) {
            this.phonemeText.append(charSequence);
            return this;
        }
        
        public Languages.LanguageSet getLanguages() {
            return this.languages;
        }
        
        @Override
        public Iterable getPhonemes() {
            return Collections.singleton(this);
        }
        
        public CharSequence getPhonemeText() {
            return this.phonemeText;
        }
        
        @Deprecated
        public Phoneme join(final Phoneme phoneme) {
            return new Phoneme(this.phonemeText.toString() + phoneme.phonemeText.toString(), this.languages.restrictTo(phoneme.languages));
        }
        
        static StringBuilder access$000(final Phoneme phoneme) {
            return phoneme.phonemeText;
        }
        
        static {
            COMPARATOR = new Comparator() {
                public int compare(final Phoneme phoneme, final Phoneme phoneme2) {
                    while (0 < Phoneme.access$000(phoneme).length()) {
                        if (0 >= Phoneme.access$000(phoneme2).length()) {
                            return 1;
                        }
                        final int n = Phoneme.access$000(phoneme).charAt(0) - Phoneme.access$000(phoneme2).charAt(0);
                        if (n != 0) {
                            return n;
                        }
                        int n2 = 0;
                        ++n2;
                    }
                    if (Phoneme.access$000(phoneme).length() < Phoneme.access$000(phoneme2).length()) {
                        return -1;
                    }
                    return 0;
                }
                
                @Override
                public int compare(final Object o, final Object o2) {
                    return this.compare((Phoneme)o, (Phoneme)o2);
                }
            };
        }
    }
    
    public interface PhonemeExpr
    {
        Iterable getPhonemes();
    }
}
