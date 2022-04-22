package com.ibm.icu.impl.locale;

import java.util.*;

public class UnicodeLocaleExtension extends Extension
{
    public static final char SINGLETON = 'u';
    private static final SortedSet EMPTY_SORTED_SET;
    private static final SortedMap EMPTY_SORTED_MAP;
    private SortedSet _attributes;
    private SortedMap _keywords;
    public static final UnicodeLocaleExtension CA_JAPANESE;
    public static final UnicodeLocaleExtension NU_THAI;
    
    private UnicodeLocaleExtension() {
        super('u');
        this._attributes = UnicodeLocaleExtension.EMPTY_SORTED_SET;
        this._keywords = UnicodeLocaleExtension.EMPTY_SORTED_MAP;
    }
    
    UnicodeLocaleExtension(final SortedSet attributes, final SortedMap keywords) {
        this();
        if (attributes != null && attributes.size() > 0) {
            this._attributes = attributes;
        }
        if (keywords != null && keywords.size() > 0) {
            this._keywords = keywords;
        }
        if (this._attributes.size() > 0 || this._keywords.size() > 0) {
            final StringBuilder sb = new StringBuilder();
            final Iterator iterator = this._attributes.iterator();
            while (iterator.hasNext()) {
                sb.append("-").append(iterator.next());
            }
            for (final Map.Entry<String, V> entry : this._keywords.entrySet()) {
                final String s = entry.getKey();
                final String s2 = (String)entry.getValue();
                sb.append("-").append(s);
                if (s2.length() > 0) {
                    sb.append("-").append(s2);
                }
            }
            this._value = sb.substring(1);
        }
    }
    
    public Set getUnicodeLocaleAttributes() {
        return Collections.unmodifiableSet((Set<?>)this._attributes);
    }
    
    public Set getUnicodeLocaleKeys() {
        return Collections.unmodifiableSet(this._keywords.keySet());
    }
    
    public String getUnicodeLocaleType(final String s) {
        return (String)this._keywords.get(s);
    }
    
    public static boolean isSingletonChar(final char c) {
        return 'u' == AsciiUtil.toLower(c);
    }
    
    public static boolean isAttribute(final String s) {
        return s.length() >= 3 && s.length() <= 8 && AsciiUtil.isAlphaNumericString(s);
    }
    
    public static boolean isKey(final String s) {
        return s.length() == 2 && AsciiUtil.isAlphaNumericString(s);
    }
    
    public static boolean isTypeSubtag(final String s) {
        return s.length() >= 3 && s.length() <= 8 && AsciiUtil.isAlphaNumericString(s);
    }
    
    static {
        EMPTY_SORTED_SET = new TreeSet();
        EMPTY_SORTED_MAP = new TreeMap();
        CA_JAPANESE = new UnicodeLocaleExtension();
        (UnicodeLocaleExtension.CA_JAPANESE._keywords = new TreeMap()).put("ca", "japanese");
        UnicodeLocaleExtension.CA_JAPANESE._value = "ca-japanese";
        NU_THAI = new UnicodeLocaleExtension();
        (UnicodeLocaleExtension.NU_THAI._keywords = new TreeMap()).put("nu", "thai");
        UnicodeLocaleExtension.NU_THAI._value = "nu-thai";
    }
}
