package com.ibm.icu.util;

import com.ibm.icu.impl.*;
import java.util.*;
import java.util.regex.*;

public class LocaleMatcher
{
    private static final boolean DEBUG = false;
    private static final double DEFAULT_THRESHOLD = 0.5;
    private final ULocale defaultLanguage;
    Map maximizedLanguageToWeight;
    LanguageMatcherData matcherData;
    private static LanguageMatcherData defaultWritten;
    private static HashMap canonicalMap;
    
    public LocaleMatcher(final LocalePriorityList list) {
        this(list, LocaleMatcher.defaultWritten);
    }
    
    public LocaleMatcher(final String s) {
        this(LocalePriorityList.add(s).build());
    }
    
    @Deprecated
    public LocaleMatcher(final LocalePriorityList list, final LanguageMatcherData matcherData) {
        this.maximizedLanguageToWeight = new LinkedHashMap();
        this.matcherData = matcherData;
        for (final ULocale uLocale : list) {
            this.add(uLocale, list.getWeight(uLocale));
        }
        final Iterator iterator2 = list.iterator();
        this.defaultLanguage = (iterator2.hasNext() ? iterator2.next() : null);
    }
    
    public double match(final ULocale uLocale, final ULocale uLocale2, final ULocale uLocale3, final ULocale uLocale4) {
        return this.matcherData.match(uLocale, uLocale2, uLocale3, uLocale4);
    }
    
    public ULocale canonicalize(final ULocale uLocale) {
        final String language = uLocale.getLanguage();
        final String s = LocaleMatcher.canonicalMap.get(language);
        final String script = uLocale.getScript();
        final String s2 = LocaleMatcher.canonicalMap.get(script);
        final String country = uLocale.getCountry();
        final String s3 = LocaleMatcher.canonicalMap.get(country);
        if (s != null || s2 != null || s3 != null) {
            return new ULocale((s == null) ? language : s, (s2 == null) ? script : s2, (s3 == null) ? country : s3);
        }
        return uLocale;
    }
    
    public ULocale getBestMatch(final LocalePriorityList list) {
        double n = 0.0;
        ULocale defaultLanguage = null;
        for (final ULocale uLocale : list) {
            final Row.R2 bestMatchInternal = this.getBestMatchInternal(uLocale);
            final double n2 = (double)bestMatchInternal.get1() * list.getWeight(uLocale);
            if (n2 > n) {
                n = n2;
                defaultLanguage = (ULocale)bestMatchInternal.get0();
            }
        }
        if (n < 0.5) {
            defaultLanguage = this.defaultLanguage;
        }
        return defaultLanguage;
    }
    
    public ULocale getBestMatch(final String s) {
        return this.getBestMatch(LocalePriorityList.add(s).build());
    }
    
    public ULocale getBestMatch(final ULocale uLocale) {
        return (ULocale)this.getBestMatchInternal(uLocale).get0();
    }
    
    @Override
    public String toString() {
        return "{" + this.defaultLanguage + ", " + this.maximizedLanguageToWeight + "}";
    }
    
    private Row.R2 getBestMatchInternal(ULocale canonicalize) {
        canonicalize = this.canonicalize(canonicalize);
        final ULocale addLikelySubtags = this.addLikelySubtags(canonicalize);
        double n = 0.0;
        Object defaultLanguage = null;
        for (final ULocale uLocale : this.maximizedLanguageToWeight.keySet()) {
            final Row.R2 r2 = this.maximizedLanguageToWeight.get(uLocale);
            final double n2 = this.match(canonicalize, addLikelySubtags, uLocale, (ULocale)r2.get0()) * (double)r2.get1();
            if (n2 > n) {
                n = n2;
                defaultLanguage = uLocale;
            }
        }
        if (n < 0.5) {
            defaultLanguage = this.defaultLanguage;
        }
        return Row.of(defaultLanguage, n);
    }
    
    private void add(ULocale canonicalize, final Double n) {
        canonicalize = this.canonicalize(canonicalize);
        this.maximizedLanguageToWeight.put(canonicalize, Row.of(this.addLikelySubtags(canonicalize), n));
    }
    
    private ULocale addLikelySubtags(final ULocale uLocale) {
        final ULocale addLikelySubtags = ULocale.addLikelySubtags(uLocale);
        if (addLikelySubtags == null || addLikelySubtags.equals(uLocale)) {
            final String language = uLocale.getLanguage();
            final String script = uLocale.getScript();
            final String country = uLocale.getCountry();
            return new ULocale(((language.length() == 0) ? "und" : language) + "_" + ((script.length() == 0) ? "Zzzz" : script) + "_" + ((country.length() == 0) ? "ZZ" : country));
        }
        return addLikelySubtags;
    }
    
    static {
        LocaleMatcher.defaultWritten = LanguageMatcherData.access$000(LanguageMatcherData.access$000(LanguageMatcherData.access$000(LanguageMatcherData.access$000(LanguageMatcherData.access$100(LanguageMatcherData.access$000(LanguageMatcherData.access$000(LanguageMatcherData.access$000(LanguageMatcherData.access$000(LanguageMatcherData.access$000(LanguageMatcherData.access$000(LanguageMatcherData.access$000(LanguageMatcherData.access$000(new LanguageMatcherData().addDistance("no", "nb", 100, "The language no is normally taken as nb in content; we might alias this for lookup."), "nn", "nb", 96), "nn", "no", 96).addDistance("da", "no", 90, "Danish and norwegian are reasonably close."), "da", "nb", 90).addDistance("hr", "br", 96, "Serbo-croatian variants are all very close."), "sh", "br", 96), "sr", "br", 96), "sh", "hr", 96), "sr", "hr", 96), "sh", "sr", 96).addDistance("sr-Latn", "sr-Cyrl", 90, "Most serbs can read either script."), "*-Hans", "*-Hant", 85, true, "Readers of simplified can read traditional much better than reverse.").addDistance("*-Hant", "*-Hans", 75, true).addDistance("en-*-US", "en-*-CA", 98, "US is different than others, and Canadian is inbetween."), "en-*-US", "en-*-*", 97), "en-*-CA", "en-*-*", 98), "en-*-*", "en-*-*", 99).addDistance("es-*-ES", "es-*-ES", 100, "Latin American Spanishes are closer to each other. Approximate by having es-ES be further from everything else."), "es-*-ES", "es-*-*", 93).addDistance("*", "*", 1, "[Default value -- must be at end!] Normally there is no comprehension of different languages.").addDistance("*-*", "*-*", 20, "[Default value -- must be at end!] Normally there is little comprehension of different scripts.").addDistance("*-*-*", "*-*-*", 96, "[Default value -- must be at end!] Normally there are small differences across regions.").freeze();
        (LocaleMatcher.canonicalMap = new HashMap()).put("iw", "he");
        LocaleMatcher.canonicalMap.put("mo", "ro");
        LocaleMatcher.canonicalMap.put("tl", "fil");
    }
    
    enum Level
    {
        language("language", 0), 
        script("script", 1), 
        region("region", 2);
        
        private static final Level[] $VALUES;
        
        private Level(final String s, final int n) {
        }
        
        static {
            $VALUES = new Level[] { Level.language, Level.script, Level.region };
        }
    }
    
    public static class LanguageMatcherData implements Freezable
    {
        ScoreData languageScores;
        ScoreData scriptScores;
        ScoreData regionScores;
        private boolean frozen;
        
        @Deprecated
        public LanguageMatcherData() {
            this.languageScores = new ScoreData(Level.language);
            this.scriptScores = new ScoreData(Level.script);
            this.regionScores = new ScoreData(Level.region);
            this.frozen = false;
        }
        
        @Deprecated
        public double match(final ULocale uLocale, final ULocale uLocale2, final ULocale uLocale3, final ULocale uLocale4) {
            double n = 0.0 + this.languageScores.getScore(uLocale, uLocale2, uLocale.getLanguage(), uLocale2.getLanguage(), uLocale3, uLocale4, uLocale3.getLanguage(), uLocale4.getLanguage()) + this.scriptScores.getScore(uLocale, uLocale2, uLocale.getScript(), uLocale2.getScript(), uLocale3, uLocale4, uLocale3.getScript(), uLocale4.getScript()) + this.regionScores.getScore(uLocale, uLocale2, uLocale.getCountry(), uLocale2.getCountry(), uLocale3, uLocale4, uLocale3.getCountry(), uLocale4.getCountry());
            if (!uLocale.getVariant().equals(uLocale3.getVariant())) {
                ++n;
            }
            if (n < 0.0) {
                n = 0.0;
            }
            else if (n > 1.0) {
                n = 1.0;
            }
            return 1.0 - n;
        }
        
        @Deprecated
        private LanguageMatcherData addDistance(final String s, final String s2, final int n) {
            return this.addDistance(s, s2, n, false, null);
        }
        
        @Deprecated
        public LanguageMatcherData addDistance(final String s, final String s2, final int n, final String s3) {
            return this.addDistance(s, s2, n, false, s3);
        }
        
        @Deprecated
        public LanguageMatcherData addDistance(final String s, final String s2, final int n, final boolean b) {
            return this.addDistance(s, s2, n, b, null);
        }
        
        private LanguageMatcherData addDistance(final String s, final String s2, final int n, final boolean b, final String s3) {
            final double n2 = 1.0 - n / 100.0;
            final LocalePatternMatcher localePatternMatcher = new LocalePatternMatcher(s);
            final Level level = localePatternMatcher.getLevel();
            final LocalePatternMatcher localePatternMatcher2 = new LocalePatternMatcher(s2);
            if (level != localePatternMatcher2.getLevel()) {
                throw new IllegalArgumentException();
            }
            final Row.R3 of = Row.of(localePatternMatcher, localePatternMatcher2, n2);
            final Row.R3 r3 = b ? null : Row.of(localePatternMatcher2, localePatternMatcher, n2);
            final String language;
            final String script;
            final String region;
            switch (level) {
                case language: {
                    language = localePatternMatcher.getLanguage();
                    final String language2 = localePatternMatcher2.getLanguage();
                    this.languageScores.addDataToScores(language, language2, of);
                    if (!b) {
                        this.languageScores.addDataToScores(language2, language, r3);
                        break;
                    }
                    break;
                }
                case script: {
                    script = localePatternMatcher.getScript();
                    final String script2 = localePatternMatcher2.getScript();
                    this.scriptScores.addDataToScores(script, script2, of);
                    if (!b) {
                        this.scriptScores.addDataToScores(script2, script, r3);
                        break;
                    }
                    break;
                }
                case region: {
                    region = localePatternMatcher.getRegion();
                    final String region2 = localePatternMatcher2.getRegion();
                    this.regionScores.addDataToScores(region, region2, of);
                    if (!b) {
                        this.regionScores.addDataToScores(region2, region, r3);
                        break;
                    }
                    break;
                }
            }
            return this;
        }
        
        @Deprecated
        public LanguageMatcherData cloneAsThawed() {
            final LanguageMatcherData languageMatcherData = (LanguageMatcherData)this.clone();
            languageMatcherData.languageScores = this.languageScores.cloneAsThawed();
            languageMatcherData.scriptScores = this.scriptScores.cloneAsThawed();
            languageMatcherData.regionScores = this.regionScores.cloneAsThawed();
            languageMatcherData.frozen = false;
            return languageMatcherData;
        }
        
        @Deprecated
        public LanguageMatcherData freeze() {
            return this;
        }
        
        @Deprecated
        public boolean isFrozen() {
            return this.frozen;
        }
        
        public Object cloneAsThawed() {
            return this.cloneAsThawed();
        }
        
        public Object freeze() {
            return this.freeze();
        }
        
        static LanguageMatcherData access$000(final LanguageMatcherData languageMatcherData, final String s, final String s2, final int n) {
            return languageMatcherData.addDistance(s, s2, n);
        }
        
        static LanguageMatcherData access$100(final LanguageMatcherData languageMatcherData, final String s, final String s2, final int n, final boolean b, final String s3) {
            return languageMatcherData.addDistance(s, s2, n, b, s3);
        }
    }
    
    private static class ScoreData implements Freezable
    {
        LinkedHashSet scores;
        final double worst;
        final Level level;
        private boolean frozen;
        
        public ScoreData(final Level level) {
            this.scores = new LinkedHashSet();
            this.frozen = false;
            this.level = level;
            this.worst = (1 - ((level == Level.language) ? 90 : ((level == Level.script) ? 20 : 4))) / 100.0;
        }
        
        void addDataToScores(final String s, final String s2, final Row.R3 r3) {
            this.scores.add(r3);
        }
        
        double getScore(final ULocale uLocale, final ULocale uLocale2, final String s, final String s2, final ULocale uLocale3, final ULocale uLocale4, final String s3, final String s4) {
            final boolean equals = s.equals(s2);
            final boolean equals2 = s3.equals(s4);
            double rawScore;
            if (!s2.equals(s4)) {
                rawScore = this.getRawScore(uLocale2, uLocale4);
                if (equals == equals2) {
                    rawScore *= 0.75;
                }
                else if (equals) {
                    rawScore *= 0.5;
                }
            }
            else if (equals == equals2) {
                rawScore = 0.0;
            }
            else {
                rawScore = 0.25 * this.worst;
            }
            return rawScore;
        }
        
        private double getRawScore(final ULocale uLocale, final ULocale uLocale2) {
            for (final Row.R3 r3 : this.scores) {
                if (((LocalePatternMatcher)r3.get0()).matches(uLocale) && ((LocalePatternMatcher)r3.get1()).matches(uLocale2)) {
                    return (double)r3.get2();
                }
            }
            return this.worst;
        }
        
        @Override
        public String toString() {
            return this.level + ", " + this.scores;
        }
        
        public ScoreData cloneAsThawed() {
            final ScoreData scoreData = (ScoreData)this.clone();
            scoreData.scores = (LinkedHashSet)scoreData.scores.clone();
            scoreData.frozen = false;
            return scoreData;
        }
        
        public ScoreData freeze() {
            return this;
        }
        
        public boolean isFrozen() {
            return this.frozen;
        }
        
        public Object cloneAsThawed() {
            return this.cloneAsThawed();
        }
        
        public Object freeze() {
            return this.freeze();
        }
    }
    
    private static class LocalePatternMatcher
    {
        private String lang;
        private String script;
        private String region;
        private Level level;
        static Pattern pattern;
        
        public LocalePatternMatcher(final String s) {
            final Matcher matcher = LocalePatternMatcher.pattern.matcher(s);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Bad pattern: " + s);
            }
            this.lang = matcher.group(1);
            this.script = matcher.group(2);
            this.region = matcher.group(3);
            this.level = ((this.region != null) ? Level.region : ((this.script != null) ? Level.script : Level.language));
            if (this.lang.equals("*")) {
                this.lang = null;
            }
            if (this.script != null && this.script.equals("*")) {
                this.script = null;
            }
            if (this.region != null && this.region.equals("*")) {
                this.region = null;
            }
        }
        
        boolean matches(final ULocale uLocale) {
            return (this.lang == null || this.lang.equals(uLocale.getLanguage())) && (this.script == null || this.script.equals(uLocale.getScript())) && (this.region == null || this.region.equals(uLocale.getCountry()));
        }
        
        public Level getLevel() {
            return this.level;
        }
        
        public String getLanguage() {
            return (this.lang == null) ? "*" : this.lang;
        }
        
        public String getScript() {
            return (this.script == null) ? "*" : this.script;
        }
        
        public String getRegion() {
            return (this.region == null) ? "*" : this.region;
        }
        
        @Override
        public String toString() {
            String s = this.getLanguage();
            if (this.level != Level.language) {
                s = s + "-" + this.getScript();
                if (this.level != Level.script) {
                    s = s + "-" + this.getRegion();
                }
            }
            return s;
        }
        
        static {
            LocalePatternMatcher.pattern = Pattern.compile("([a-zA-Z]{1,8}|\\*)(?:-([a-zA-Z]{4}|\\*))?(?:-([a-zA-Z]{2}|[0-9]{3}|\\*))?");
        }
    }
}
