package com.ibm.icu.impl.locale;

public final class BaseLocale
{
    private static final boolean JDKIMPL = false;
    public static final String SEP = "_";
    private static final Cache CACHE;
    public static final BaseLocale ROOT;
    private String _language;
    private String _script;
    private String _region;
    private String _variant;
    private transient int _hash;
    
    private BaseLocale(final String s, final String s2, final String s3, final String s4) {
        this._language = "";
        this._script = "";
        this._region = "";
        this._variant = "";
        this._hash = 0;
        if (s != null) {
            this._language = AsciiUtil.toLowerString(s).intern();
        }
        if (s2 != null) {
            this._script = AsciiUtil.toTitleString(s2).intern();
        }
        if (s3 != null) {
            this._region = AsciiUtil.toUpperString(s3).intern();
        }
        if (s4 != null) {
            this._variant = AsciiUtil.toUpperString(s4).intern();
        }
    }
    
    public static BaseLocale getInstance(final String s, final String s2, final String s3, final String s4) {
        return (BaseLocale)BaseLocale.CACHE.get(new Key(s, s2, s3, s4));
    }
    
    public String getLanguage() {
        return this._language;
    }
    
    public String getScript() {
        return this._script;
    }
    
    public String getRegion() {
        return this._region;
    }
    
    public String getVariant() {
        return this._variant;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseLocale)) {
            return false;
        }
        final BaseLocale baseLocale = (BaseLocale)o;
        return this.hashCode() == baseLocale.hashCode() && this._language.equals(baseLocale._language) && this._script.equals(baseLocale._script) && this._region.equals(baseLocale._region) && this._variant.equals(baseLocale._variant);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (this._language.length() > 0) {
            sb.append("language=");
            sb.append(this._language);
        }
        if (this._script.length() > 0) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append("script=");
            sb.append(this._script);
        }
        if (this._region.length() > 0) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append("region=");
            sb.append(this._region);
        }
        if (this._variant.length() > 0) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append("variant=");
            sb.append(this._variant);
        }
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        int hash = this._hash;
        if (hash == 0) {
            int n = 0;
            while (0 < this._language.length()) {
                hash = 31 * hash + this._language.charAt(0);
                ++n;
            }
            while (0 < this._script.length()) {
                hash = 31 * hash + this._script.charAt(0);
                ++n;
            }
            while (0 < this._region.length()) {
                hash = 31 * hash + this._region.charAt(0);
                ++n;
            }
            while (0 < this._variant.length()) {
                hash = 31 * hash + this._variant.charAt(0);
                ++n;
            }
            this._hash = hash;
        }
        return hash;
    }
    
    BaseLocale(final String s, final String s2, final String s3, final String s4, final BaseLocale$1 object) {
        this(s, s2, s3, s4);
    }
    
    static {
        CACHE = new Cache();
        ROOT = getInstance("", "", "", "");
    }
    
    private static class Cache extends LocaleObjectCache
    {
        public Cache() {
        }
        
        protected Key normalizeKey(final Key key) {
            return Key.normalize(key);
        }
        
        protected BaseLocale createObject(final Key key) {
            return new BaseLocale(Key.access$000(key), Key.access$100(key), Key.access$200(key), Key.access$300(key), null);
        }
        
        @Override
        protected Object normalizeKey(final Object o) {
            return this.normalizeKey((Key)o);
        }
        
        @Override
        protected Object createObject(final Object o) {
            return this.createObject((Key)o);
        }
    }
    
    private static class Key implements Comparable
    {
        private String _lang;
        private String _scrt;
        private String _regn;
        private String _vart;
        private int _hash;
        
        public Key(final String lang, final String scrt, final String regn, final String vart) {
            this._lang = "";
            this._scrt = "";
            this._regn = "";
            this._vart = "";
            if (lang != null) {
                this._lang = lang;
            }
            if (scrt != null) {
                this._scrt = scrt;
            }
            if (regn != null) {
                this._regn = regn;
            }
            if (vart != null) {
                this._vart = vart;
            }
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o instanceof Key && AsciiUtil.caseIgnoreMatch(((Key)o)._lang, this._lang) && AsciiUtil.caseIgnoreMatch(((Key)o)._scrt, this._scrt) && AsciiUtil.caseIgnoreMatch(((Key)o)._regn, this._regn) && AsciiUtil.caseIgnoreMatch(((Key)o)._vart, this._vart));
        }
        
        public int compareTo(final Key key) {
            int n = AsciiUtil.caseIgnoreCompare(this._lang, key._lang);
            if (n == 0) {
                n = AsciiUtil.caseIgnoreCompare(this._scrt, key._scrt);
                if (n == 0) {
                    n = AsciiUtil.caseIgnoreCompare(this._regn, key._regn);
                    if (n == 0) {
                        n = AsciiUtil.caseIgnoreCompare(this._vart, key._vart);
                    }
                }
            }
            return n;
        }
        
        @Override
        public int hashCode() {
            int hash = this._hash;
            if (hash == 0) {
                int n = 0;
                while (0 < this._lang.length()) {
                    hash = 31 * hash + AsciiUtil.toLower(this._lang.charAt(0));
                    ++n;
                }
                while (0 < this._scrt.length()) {
                    hash = 31 * hash + AsciiUtil.toLower(this._scrt.charAt(0));
                    ++n;
                }
                while (0 < this._regn.length()) {
                    hash = 31 * hash + AsciiUtil.toLower(this._regn.charAt(0));
                    ++n;
                }
                while (0 < this._vart.length()) {
                    hash = 31 * hash + AsciiUtil.toLower(this._vart.charAt(0));
                    ++n;
                }
                this._hash = hash;
            }
            return hash;
        }
        
        public static Key normalize(final Key key) {
            return new Key(AsciiUtil.toLowerString(key._lang).intern(), AsciiUtil.toTitleString(key._scrt).intern(), AsciiUtil.toUpperString(key._regn).intern(), AsciiUtil.toUpperString(key._vart).intern());
        }
        
        public int compareTo(final Object o) {
            return this.compareTo((Key)o);
        }
        
        static String access$000(final Key key) {
            return key._lang;
        }
        
        static String access$100(final Key key) {
            return key._scrt;
        }
        
        static String access$200(final Key key) {
            return key._regn;
        }
        
        static String access$300(final Key key) {
            return key._vart;
        }
    }
}
