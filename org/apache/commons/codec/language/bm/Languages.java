package org.apache.commons.codec.language.bm;

import java.io.*;
import java.util.*;

public class Languages
{
    public static final String ANY = "any";
    private static final Map LANGUAGES;
    private final Set languages;
    public static final LanguageSet NO_LANGUAGES;
    public static final LanguageSet ANY_LANGUAGE;
    
    public static Languages getInstance(final NameType nameType) {
        return Languages.LANGUAGES.get(nameType);
    }
    
    public static Languages getInstance(final String s) {
        final HashSet<String> set = new HashSet<String>();
        final InputStream resourceAsStream = Languages.class.getClassLoader().getResourceAsStream(s);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Unable to resolve required resource: " + s);
        }
        final Scanner scanner = new Scanner(resourceAsStream, "UTF-8");
        while (scanner.hasNextLine()) {
            final String trim = scanner.nextLine().trim();
            if (true) {
                if (trim.endsWith("*/")) {
                    continue;
                }
                continue;
            }
            else {
                if (trim.startsWith("/*")) {
                    continue;
                }
                if (trim.length() <= 0) {
                    continue;
                }
                set.add(trim);
            }
        }
        scanner.close();
        return new Languages(Collections.unmodifiableSet((Set<?>)set));
    }
    
    private static String langResourceName(final NameType nameType) {
        return String.format("org/apache/commons/codec/language/bm/%s_languages.txt", nameType.getName());
    }
    
    private Languages(final Set languages) {
        this.languages = languages;
    }
    
    public Set getLanguages() {
        return this.languages;
    }
    
    static {
        LANGUAGES = new EnumMap(NameType.class);
        final NameType[] values = NameType.values();
        while (0 < values.length) {
            final NameType nameType = values[0];
            Languages.LANGUAGES.put(nameType, getInstance(langResourceName(nameType)));
            int n = 0;
            ++n;
        }
        NO_LANGUAGES = new LanguageSet() {
            @Override
            public boolean contains(final String s) {
                return false;
            }
            
            @Override
            public String getAny() {
                throw new NoSuchElementException("Can't fetch any language from the empty language set.");
            }
            
            @Override
            public boolean isEmpty() {
                return true;
            }
            
            @Override
            public boolean isSingleton() {
                return false;
            }
            
            @Override
            public LanguageSet restrictTo(final LanguageSet set) {
                return this;
            }
            
            @Override
            public String toString() {
                return "NO_LANGUAGES";
            }
        };
        ANY_LANGUAGE = new LanguageSet() {
            @Override
            public boolean contains(final String s) {
                return true;
            }
            
            @Override
            public String getAny() {
                throw new NoSuchElementException("Can't fetch any language from the any language set.");
            }
            
            @Override
            public boolean isEmpty() {
                return false;
            }
            
            @Override
            public boolean isSingleton() {
                return false;
            }
            
            @Override
            public LanguageSet restrictTo(final LanguageSet set) {
                return set;
            }
            
            @Override
            public String toString() {
                return "ANY_LANGUAGE";
            }
        };
    }
    
    public static final class SomeLanguages extends LanguageSet
    {
        private final Set languages;
        
        private SomeLanguages(final Set set) {
            this.languages = Collections.unmodifiableSet((Set<?>)set);
        }
        
        @Override
        public boolean contains(final String s) {
            return this.languages.contains(s);
        }
        
        @Override
        public String getAny() {
            return this.languages.iterator().next();
        }
        
        public Set getLanguages() {
            return this.languages;
        }
        
        @Override
        public boolean isEmpty() {
            return this.languages.isEmpty();
        }
        
        @Override
        public boolean isSingleton() {
            return this.languages.size() == 1;
        }
        
        @Override
        public LanguageSet restrictTo(final LanguageSet set) {
            if (set == Languages.NO_LANGUAGES) {
                return set;
            }
            if (set == Languages.ANY_LANGUAGE) {
                return this;
            }
            final SomeLanguages someLanguages = (SomeLanguages)set;
            final HashSet set2 = new HashSet<String>(Math.min(this.languages.size(), someLanguages.languages.size()));
            for (final String s : this.languages) {
                if (someLanguages.languages.contains(s)) {
                    set2.add(s);
                }
            }
            return LanguageSet.from(set2);
        }
        
        @Override
        public String toString() {
            return "Languages(" + this.languages.toString() + ")";
        }
        
        SomeLanguages(final Set set, final Languages$1 languageSet) {
            this(set);
        }
    }
    
    public abstract static class LanguageSet
    {
        public static LanguageSet from(final Set set) {
            return set.isEmpty() ? Languages.NO_LANGUAGES : new SomeLanguages(set, null);
        }
        
        public abstract boolean contains(final String p0);
        
        public abstract String getAny();
        
        public abstract boolean isEmpty();
        
        public abstract boolean isSingleton();
        
        public abstract LanguageSet restrictTo(final LanguageSet p0);
    }
}
