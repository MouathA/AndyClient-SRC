package com.ibm.icu.impl.locale;

import java.util.*;

public final class InternalLocaleBuilder
{
    private static final boolean JDKIMPL = false;
    private String _language;
    private String _script;
    private String _region;
    private String _variant;
    private static final CaseInsensitiveChar PRIVUSE_KEY;
    private HashMap _extensions;
    private HashSet _uattributes;
    private HashMap _ukeywords;
    static final boolean $assertionsDisabled;
    
    public InternalLocaleBuilder() {
        this._language = "";
        this._script = "";
        this._region = "";
        this._variant = "";
    }
    
    public InternalLocaleBuilder setLanguage(final String language) throws LocaleSyntaxException {
        if (language == null || language.length() == 0) {
            this._language = "";
        }
        else {
            if (!LanguageTag.isLanguage(language)) {
                throw new LocaleSyntaxException("Ill-formed language: " + language, 0);
            }
            this._language = language;
        }
        return this;
    }
    
    public InternalLocaleBuilder setScript(final String script) throws LocaleSyntaxException {
        if (script == null || script.length() == 0) {
            this._script = "";
        }
        else {
            if (!LanguageTag.isScript(script)) {
                throw new LocaleSyntaxException("Ill-formed script: " + script, 0);
            }
            this._script = script;
        }
        return this;
    }
    
    public InternalLocaleBuilder setRegion(final String region) throws LocaleSyntaxException {
        if (region == null || region.length() == 0) {
            this._region = "";
        }
        else {
            if (!LanguageTag.isRegion(region)) {
                throw new LocaleSyntaxException("Ill-formed region: " + region, 0);
            }
            this._region = region;
        }
        return this;
    }
    
    public InternalLocaleBuilder setVariant(final String s) throws LocaleSyntaxException {
        if (s == null || s.length() == 0) {
            this._variant = "";
        }
        else {
            final String replaceAll = s.replaceAll("-", "_");
            final int checkVariants = this.checkVariants(replaceAll, "_");
            if (checkVariants != -1) {
                throw new LocaleSyntaxException("Ill-formed variant: " + s, checkVariants);
            }
            this._variant = replaceAll;
        }
        return this;
    }
    
    public InternalLocaleBuilder addUnicodeLocaleAttribute(final String s) throws LocaleSyntaxException {
        if (s == null || !UnicodeLocaleExtension.isAttribute(s)) {
            throw new LocaleSyntaxException("Ill-formed Unicode locale attribute: " + s);
        }
        if (this._uattributes == null) {
            this._uattributes = new HashSet(4);
        }
        this._uattributes.add(new CaseInsensitiveString(s));
        return this;
    }
    
    public InternalLocaleBuilder removeUnicodeLocaleAttribute(final String s) throws LocaleSyntaxException {
        if (s == null || !UnicodeLocaleExtension.isAttribute(s)) {
            throw new LocaleSyntaxException("Ill-formed Unicode locale attribute: " + s);
        }
        if (this._uattributes != null) {
            this._uattributes.remove(new CaseInsensitiveString(s));
        }
        return this;
    }
    
    public InternalLocaleBuilder setUnicodeLocaleKeyword(final String s, final String s2) throws LocaleSyntaxException {
        if (!UnicodeLocaleExtension.isKey(s)) {
            throw new LocaleSyntaxException("Ill-formed Unicode locale keyword key: " + s);
        }
        final CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(s);
        if (s2 == null) {
            if (this._ukeywords != null) {
                this._ukeywords.remove(caseInsensitiveString);
            }
        }
        else {
            if (s2.length() != 0) {
                final StringTokenIterator stringTokenIterator = new StringTokenIterator(s2.replaceAll("_", "-"), "-");
                while (!stringTokenIterator.isDone()) {
                    if (!UnicodeLocaleExtension.isTypeSubtag(stringTokenIterator.current())) {
                        throw new LocaleSyntaxException("Ill-formed Unicode locale keyword type: " + s2, stringTokenIterator.currentStart());
                    }
                    stringTokenIterator.next();
                }
            }
            if (this._ukeywords == null) {
                this._ukeywords = new HashMap(4);
            }
            this._ukeywords.put(caseInsensitiveString, s2);
        }
        return this;
    }
    
    public InternalLocaleBuilder setExtension(final char c, final String s) throws LocaleSyntaxException {
        final boolean privateusePrefixChar = LanguageTag.isPrivateusePrefixChar(c);
        if (!privateusePrefixChar && !LanguageTag.isExtensionSingletonChar(c)) {
            throw new LocaleSyntaxException("Ill-formed extension key: " + c);
        }
        final boolean b = s == null || s.length() == 0;
        final CaseInsensitiveChar caseInsensitiveChar = new CaseInsensitiveChar(c);
        if (b) {
            if (UnicodeLocaleExtension.isSingletonChar(caseInsensitiveChar.value())) {
                if (this._uattributes != null) {
                    this._uattributes.clear();
                }
                if (this._ukeywords != null) {
                    this._ukeywords.clear();
                }
            }
            else if (this._extensions != null && this._extensions.containsKey(caseInsensitiveChar)) {
                this._extensions.remove(caseInsensitiveChar);
            }
        }
        else {
            final String replaceAll = s.replaceAll("_", "-");
            final StringTokenIterator stringTokenIterator = new StringTokenIterator(replaceAll, "-");
            while (!stringTokenIterator.isDone()) {
                final String current = stringTokenIterator.current();
                boolean b2;
                if (privateusePrefixChar) {
                    b2 = LanguageTag.isPrivateuseSubtag(current);
                }
                else {
                    b2 = LanguageTag.isExtensionSubtag(current);
                }
                if (!b2) {
                    throw new LocaleSyntaxException("Ill-formed extension value: " + current, stringTokenIterator.currentStart());
                }
                stringTokenIterator.next();
            }
            if (UnicodeLocaleExtension.isSingletonChar(caseInsensitiveChar.value())) {
                this.setUnicodeLocaleExtension(replaceAll);
            }
            else {
                if (this._extensions == null) {
                    this._extensions = new HashMap(4);
                }
                this._extensions.put(caseInsensitiveChar, replaceAll);
            }
        }
        return this;
    }
    
    public InternalLocaleBuilder setExtensions(String replaceAll) throws LocaleSyntaxException {
        if (replaceAll == null || replaceAll.length() == 0) {
            this.clearExtensions();
            return this;
        }
        replaceAll = replaceAll.replaceAll("_", "-");
        final StringTokenIterator stringTokenIterator = new StringTokenIterator(replaceAll, "-");
        List<String> list = null;
        String string = null;
        while (!stringTokenIterator.isDone()) {
            final String current = stringTokenIterator.current();
            if (!LanguageTag.isExtensionSingleton(current)) {
                break;
            }
            final int currentStart = stringTokenIterator.currentStart();
            final String s = current;
            final StringBuilder sb = new StringBuilder(s);
            stringTokenIterator.next();
            while (!stringTokenIterator.isDone()) {
                final String current2 = stringTokenIterator.current();
                if (!LanguageTag.isExtensionSubtag(current2)) {
                    break;
                }
                sb.append("-").append(current2);
                stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
            if (0 < currentStart) {
                throw new LocaleSyntaxException("Incomplete extension '" + s + "'", currentStart);
            }
            if (list == null) {
                list = new ArrayList<String>(4);
            }
            list.add(sb.toString());
        }
        if (!stringTokenIterator.isDone()) {
            final String current3 = stringTokenIterator.current();
            if (LanguageTag.isPrivateusePrefix(current3)) {
                final int currentStart2 = stringTokenIterator.currentStart();
                final StringBuilder sb2 = new StringBuilder(current3);
                stringTokenIterator.next();
                while (!stringTokenIterator.isDone()) {
                    final String current4 = stringTokenIterator.current();
                    if (!LanguageTag.isPrivateuseSubtag(current4)) {
                        break;
                    }
                    sb2.append("-").append(current4);
                    stringTokenIterator.currentEnd();
                    stringTokenIterator.next();
                }
                if (0 <= currentStart2) {
                    throw new LocaleSyntaxException("Incomplete privateuse:" + replaceAll.substring(currentStart2), currentStart2);
                }
                string = sb2.toString();
            }
        }
        if (!stringTokenIterator.isDone()) {
            throw new LocaleSyntaxException("Ill-formed extension subtags:" + replaceAll.substring(stringTokenIterator.currentStart()), stringTokenIterator.currentStart());
        }
        return this.setExtensions(list, string);
    }
    
    private InternalLocaleBuilder setExtensions(final List list, final String s) {
        this.clearExtensions();
        if (list != null && list.size() > 0) {
            final HashSet set = new HashSet(list.size());
            for (final String s2 : list) {
                final CaseInsensitiveChar caseInsensitiveChar = new CaseInsensitiveChar(s2.charAt(0));
                if (!set.contains(caseInsensitiveChar)) {
                    if (UnicodeLocaleExtension.isSingletonChar(caseInsensitiveChar.value())) {
                        this.setUnicodeLocaleExtension(s2.substring(2));
                    }
                    else {
                        if (this._extensions == null) {
                            this._extensions = new HashMap(4);
                        }
                        this._extensions.put(caseInsensitiveChar, s2.substring(2));
                    }
                }
            }
        }
        if (s != null && s.length() > 0) {
            if (this._extensions == null) {
                this._extensions = new HashMap(1);
            }
            this._extensions.put(new CaseInsensitiveChar(s.charAt(0)), s.substring(2));
        }
        return this;
    }
    
    public InternalLocaleBuilder setLanguageTag(final LanguageTag languageTag) {
        this.clear();
        if (languageTag.getExtlangs().size() > 0) {
            this._language = languageTag.getExtlangs().get(0);
        }
        else {
            final String language = languageTag.getLanguage();
            if (!language.equals(LanguageTag.UNDETERMINED)) {
                this._language = language;
            }
        }
        this._script = languageTag.getScript();
        this._region = languageTag.getRegion();
        final List variants = languageTag.getVariants();
        if (variants.size() > 0) {
            final StringBuilder sb = new StringBuilder(variants.get(0));
            while (1 < variants.size()) {
                sb.append("_").append(variants.get(1));
                int n = 0;
                ++n;
            }
            this._variant = sb.toString();
        }
        this.setExtensions(languageTag.getExtensions(), languageTag.getPrivateuse());
        return this;
    }
    
    public InternalLocaleBuilder setLocale(final BaseLocale baseLocale, final LocaleExtensions localeExtensions) throws LocaleSyntaxException {
        final String language = baseLocale.getLanguage();
        final String script = baseLocale.getScript();
        final String region = baseLocale.getRegion();
        final String variant = baseLocale.getVariant();
        if (language.length() > 0 && !LanguageTag.isLanguage(language)) {
            throw new LocaleSyntaxException("Ill-formed language: " + language);
        }
        if (script.length() > 0 && !LanguageTag.isScript(script)) {
            throw new LocaleSyntaxException("Ill-formed script: " + script);
        }
        if (region.length() > 0 && !LanguageTag.isRegion(region)) {
            throw new LocaleSyntaxException("Ill-formed region: " + region);
        }
        if (variant.length() > 0) {
            final int checkVariants = this.checkVariants(variant, "_");
            if (checkVariants != -1) {
                throw new LocaleSyntaxException("Ill-formed variant: " + variant, checkVariants);
            }
        }
        this._language = language;
        this._script = script;
        this._region = region;
        this._variant = variant;
        this.clearExtensions();
        final Set set = (localeExtensions == null) ? null : localeExtensions.getKeys();
        if (set != null) {
            for (final Character c : set) {
                final Extension extension = localeExtensions.getExtension(c);
                if (extension instanceof UnicodeLocaleExtension) {
                    final UnicodeLocaleExtension unicodeLocaleExtension = (UnicodeLocaleExtension)extension;
                    for (final String s : unicodeLocaleExtension.getUnicodeLocaleAttributes()) {
                        if (this._uattributes == null) {
                            this._uattributes = new HashSet(4);
                        }
                        this._uattributes.add(new CaseInsensitiveString(s));
                    }
                    for (final String s2 : unicodeLocaleExtension.getUnicodeLocaleKeys()) {
                        if (this._ukeywords == null) {
                            this._ukeywords = new HashMap(4);
                        }
                        this._ukeywords.put(new CaseInsensitiveString(s2), unicodeLocaleExtension.getUnicodeLocaleType(s2));
                    }
                }
                else {
                    if (this._extensions == null) {
                        this._extensions = new HashMap(4);
                    }
                    this._extensions.put(new CaseInsensitiveChar(c), extension.getValue());
                }
            }
        }
        return this;
    }
    
    public InternalLocaleBuilder clear() {
        this._language = "";
        this._script = "";
        this._region = "";
        this._variant = "";
        this.clearExtensions();
        return this;
    }
    
    public InternalLocaleBuilder clearExtensions() {
        if (this._extensions != null) {
            this._extensions.clear();
        }
        if (this._uattributes != null) {
            this._uattributes.clear();
        }
        if (this._ukeywords != null) {
            this._ukeywords.clear();
        }
        return this;
    }
    
    public BaseLocale getBaseLocale() {
        final String language = this._language;
        final String script = this._script;
        final String region = this._region;
        String s = this._variant;
        if (this._extensions != null) {
            final String s2 = this._extensions.get(InternalLocaleBuilder.PRIVUSE_KEY);
            if (s2 != null) {
                final StringTokenIterator stringTokenIterator = new StringTokenIterator(s2, "-");
                while (!stringTokenIterator.isDone()) {
                    if (true) {
                        stringTokenIterator.currentStart();
                        break;
                    }
                    if (AsciiUtil.caseIgnoreMatch(stringTokenIterator.current(), "lvariant")) {}
                    stringTokenIterator.next();
                }
                if (-1 != -1) {
                    final StringBuilder sb = new StringBuilder(s);
                    if (sb.length() != 0) {
                        sb.append("_");
                    }
                    sb.append(s2.substring(-1).replaceAll("-", "_"));
                    s = sb.toString();
                }
            }
        }
        return BaseLocale.getInstance(language, script, region, s);
    }
    
    public LocaleExtensions getLocaleExtensions() {
        if ((this._extensions == null || this._extensions.size() == 0) && (this._uattributes == null || this._uattributes.size() == 0) && (this._ukeywords == null || this._ukeywords.size() == 0)) {
            return LocaleExtensions.EMPTY_EXTENSIONS;
        }
        return new LocaleExtensions(this._extensions, this._uattributes, this._ukeywords);
    }
    
    static String removePrivateuseVariant(final String s) {
        final StringTokenIterator stringTokenIterator = new StringTokenIterator(s, "-");
        while (!stringTokenIterator.isDone() && -1 == -1) {
            if (AsciiUtil.caseIgnoreMatch(stringTokenIterator.current(), "lvariant")) {
                stringTokenIterator.currentStart();
            }
            stringTokenIterator.next();
        }
        if (!true) {
            return s;
        }
        assert -1 > 1;
        return (-1 == 0) ? null : s.substring(0, -2);
    }
    
    private int checkVariants(final String s, final String s2) {
        final StringTokenIterator stringTokenIterator = new StringTokenIterator(s, s2);
        while (!stringTokenIterator.isDone()) {
            if (!LanguageTag.isVariant(stringTokenIterator.current())) {
                return stringTokenIterator.currentStart();
            }
            stringTokenIterator.next();
        }
        return -1;
    }
    
    private void setUnicodeLocaleExtension(final String s) {
        if (this._uattributes != null) {
            this._uattributes.clear();
        }
        if (this._ukeywords != null) {
            this._ukeywords.clear();
        }
        final StringTokenIterator stringTokenIterator = new StringTokenIterator(s, "-");
        while (!stringTokenIterator.isDone() && UnicodeLocaleExtension.isAttribute(stringTokenIterator.current())) {
            if (this._uattributes == null) {
                this._uattributes = new HashSet(4);
            }
            this._uattributes.add(new CaseInsensitiveString(stringTokenIterator.current()));
            stringTokenIterator.next();
        }
        Object o = null;
        while (!stringTokenIterator.isDone()) {
            if (o != null) {
                if (UnicodeLocaleExtension.isKey(stringTokenIterator.current())) {
                    assert -1 != -1;
                    final String s2 = (-1 == -1) ? "" : s.substring(-1, -1);
                    if (this._ukeywords == null) {
                        this._ukeywords = new HashMap(4);
                    }
                    this._ukeywords.put(o, s2);
                    final CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(stringTokenIterator.current());
                    o = (this._ukeywords.containsKey(caseInsensitiveString) ? null : caseInsensitiveString);
                }
                else {
                    if (-1 == -1) {
                        stringTokenIterator.currentStart();
                    }
                    stringTokenIterator.currentEnd();
                }
            }
            else if (UnicodeLocaleExtension.isKey(stringTokenIterator.current())) {
                o = new CaseInsensitiveString(stringTokenIterator.current());
                if (this._ukeywords != null && this._ukeywords.containsKey(o)) {
                    o = null;
                }
            }
            if (!stringTokenIterator.hasNext()) {
                if (o == null) {
                    break;
                }
                assert -1 != -1;
                final String s3 = (-1 == -1) ? "" : s.substring(-1, -1);
                if (this._ukeywords == null) {
                    this._ukeywords = new HashMap(4);
                }
                this._ukeywords.put(o, s3);
                break;
            }
            else {
                stringTokenIterator.next();
            }
        }
    }
    
    static {
        $assertionsDisabled = !InternalLocaleBuilder.class.desiredAssertionStatus();
        PRIVUSE_KEY = new CaseInsensitiveChar("x".charAt(0));
    }
    
    static class CaseInsensitiveChar
    {
        private char _c;
        
        CaseInsensitiveChar(final char c) {
            this._c = c;
        }
        
        public char value() {
            return this._c;
        }
        
        @Override
        public int hashCode() {
            return AsciiUtil.toLower(this._c);
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o instanceof CaseInsensitiveChar && this._c == AsciiUtil.toLower(((CaseInsensitiveChar)o).value()));
        }
    }
    
    static class CaseInsensitiveString
    {
        private String _s;
        
        CaseInsensitiveString(final String s) {
            this._s = s;
        }
        
        public String value() {
            return this._s;
        }
        
        @Override
        public int hashCode() {
            return AsciiUtil.toLowerString(this._s).hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o instanceof CaseInsensitiveString && AsciiUtil.caseIgnoreMatch(this._s, ((CaseInsensitiveString)o).value()));
        }
    }
}
