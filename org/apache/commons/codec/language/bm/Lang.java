package org.apache.commons.codec.language.bm;

import java.util.regex.*;
import java.io.*;
import java.util.*;

public class Lang
{
    private static final Map Langs;
    private static final String LANGUAGE_RULES_RN = "org/apache/commons/codec/language/bm/lang.txt";
    private final Languages languages;
    private final List rules;
    
    public static Lang instance(final NameType nameType) {
        return Lang.Langs.get(nameType);
    }
    
    public static Lang loadFromResource(final String s, final Languages languages) {
        final ArrayList<LangRule> list = new ArrayList<LangRule>();
        final InputStream resourceAsStream = Lang.class.getClassLoader().getResourceAsStream(s);
        if (resourceAsStream == null) {
            throw new IllegalStateException("Unable to resolve required resource:org/apache/commons/codec/language/bm/lang.txt");
        }
        final Scanner scanner = new Scanner(resourceAsStream, "UTF-8");
        while (scanner.hasNextLine()) {
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
                final String[] split = trim.split("\\s+");
                if (split.length != 3) {
                    throw new IllegalArgumentException("Malformed line '" + s2 + "' in language resource '" + s + "'");
                }
                list.add(new LangRule(Pattern.compile(split[0]), new HashSet(Arrays.asList(split[1].split("\\+"))), split[2].equals("true"), null));
            }
        }
        scanner.close();
        return new Lang(list, languages);
    }
    
    private Lang(final List list, final Languages languages) {
        this.rules = Collections.unmodifiableList((List<?>)list);
        this.languages = languages;
    }
    
    public String guessLanguage(final String s) {
        final Languages.LanguageSet guessLanguages = this.guessLanguages(s);
        return guessLanguages.isSingleton() ? guessLanguages.getAny() : "any";
    }
    
    public Languages.LanguageSet guessLanguages(final String s) {
        final String lowerCase = s.toLowerCase(Locale.ENGLISH);
        final HashSet set = new HashSet(this.languages.getLanguages());
        for (final LangRule langRule : this.rules) {
            if (langRule.matches(lowerCase)) {
                if (LangRule.access$100(langRule)) {
                    set.retainAll(LangRule.access$200(langRule));
                }
                else {
                    set.removeAll(LangRule.access$200(langRule));
                }
            }
        }
        final Languages.LanguageSet from = Languages.LanguageSet.from(set);
        return from.equals(Languages.NO_LANGUAGES) ? Languages.ANY_LANGUAGE : from;
    }
    
    static {
        Langs = new EnumMap(NameType.class);
        final NameType[] values = NameType.values();
        while (0 < values.length) {
            final NameType nameType = values[0];
            Lang.Langs.put(nameType, loadFromResource("org/apache/commons/codec/language/bm/lang.txt", Languages.getInstance(nameType)));
            int n = 0;
            ++n;
        }
    }
    
    private static final class LangRule
    {
        private final boolean acceptOnMatch;
        private final Set languages;
        private final Pattern pattern;
        
        private LangRule(final Pattern pattern, final Set languages, final boolean acceptOnMatch) {
            this.pattern = pattern;
            this.languages = languages;
            this.acceptOnMatch = acceptOnMatch;
        }
        
        public boolean matches(final String s) {
            return this.pattern.matcher(s).find();
        }
        
        LangRule(final Pattern pattern, final Set set, final boolean b, final Lang$1 object) {
            this(pattern, set, b);
        }
        
        static boolean access$100(final LangRule langRule) {
            return langRule.acceptOnMatch;
        }
        
        static Set access$200(final LangRule langRule) {
            return langRule.languages;
        }
    }
}
