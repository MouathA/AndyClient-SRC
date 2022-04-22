package com.ibm.icu.impl.locale;

import java.util.*;

public class LocaleExtensions
{
    private SortedMap _map;
    private String _id;
    private static final SortedMap EMPTY_MAP;
    public static final LocaleExtensions EMPTY_EXTENSIONS;
    public static final LocaleExtensions CALENDAR_JAPANESE;
    public static final LocaleExtensions NUMBER_THAI;
    static final boolean $assertionsDisabled;
    
    private LocaleExtensions() {
    }
    
    LocaleExtensions(final Map map, final Set set, final Map map2) {
        final boolean b = map != null && map.size() > 0;
        final boolean b2 = set != null && set.size() > 0;
        final boolean b3 = map2 != null && map2.size() > 0;
        if (!b && !b2 && !b3) {
            this._map = LocaleExtensions.EMPTY_MAP;
            this._id = "";
            return;
        }
        this._map = new TreeMap();
        if (b) {
            for (final Map.Entry<InternalLocaleBuilder.CaseInsensitiveChar, V> entry : map.entrySet()) {
                final char lower = AsciiUtil.toLower(entry.getKey().value());
                String removePrivateuseVariant = (String)entry.getValue();
                if (LanguageTag.isPrivateusePrefixChar(lower)) {
                    removePrivateuseVariant = InternalLocaleBuilder.removePrivateuseVariant(removePrivateuseVariant);
                    if (removePrivateuseVariant == null) {
                        continue;
                    }
                }
                this._map.put(lower, new Extension(lower, AsciiUtil.toLowerString(removePrivateuseVariant)));
            }
        }
        if (b2 || b3) {
            TreeSet<String> set2 = null;
            TreeMap<String, String> treeMap = null;
            if (b2) {
                set2 = new TreeSet<String>();
                final Iterator<InternalLocaleBuilder.CaseInsensitiveString> iterator2 = set.iterator();
                while (iterator2.hasNext()) {
                    set2.add(AsciiUtil.toLowerString(iterator2.next().value()));
                }
            }
            if (b3) {
                treeMap = new TreeMap<String, String>();
                for (final Map.Entry<InternalLocaleBuilder.CaseInsensitiveString, V> entry2 : map2.entrySet()) {
                    treeMap.put(AsciiUtil.toLowerString(entry2.getKey().value()), AsciiUtil.toLowerString((String)entry2.getValue()));
                }
            }
            this._map.put('u', new UnicodeLocaleExtension(set2, treeMap));
        }
        if (this._map.size() == 0) {
            this._map = LocaleExtensions.EMPTY_MAP;
            this._id = "";
        }
        else {
            this._id = toID(this._map);
        }
    }
    
    public Set getKeys() {
        return Collections.unmodifiableSet(this._map.keySet());
    }
    
    public Extension getExtension(final Character c) {
        return (Extension)this._map.get(AsciiUtil.toLower(c));
    }
    
    public String getExtensionValue(final Character c) {
        final Extension extension = (Extension)this._map.get(AsciiUtil.toLower(c));
        if (extension == null) {
            return null;
        }
        return extension.getValue();
    }
    
    public Set getUnicodeLocaleAttributes() {
        final Extension extension = (Extension)this._map.get('u');
        if (extension == null) {
            return Collections.emptySet();
        }
        assert extension instanceof UnicodeLocaleExtension;
        return ((UnicodeLocaleExtension)extension).getUnicodeLocaleAttributes();
    }
    
    public Set getUnicodeLocaleKeys() {
        final Extension extension = (Extension)this._map.get('u');
        if (extension == null) {
            return Collections.emptySet();
        }
        assert extension instanceof UnicodeLocaleExtension;
        return ((UnicodeLocaleExtension)extension).getUnicodeLocaleKeys();
    }
    
    public String getUnicodeLocaleType(final String s) {
        final Extension extension = (Extension)this._map.get('u');
        if (extension == null) {
            return null;
        }
        assert extension instanceof UnicodeLocaleExtension;
        return ((UnicodeLocaleExtension)extension).getUnicodeLocaleType(AsciiUtil.toLowerString(s));
    }
    
    public boolean isEmpty() {
        return this._map.isEmpty();
    }
    
    public static boolean isValidKey(final char c) {
        return LanguageTag.isExtensionSingletonChar(c) || LanguageTag.isPrivateusePrefixChar(c);
    }
    
    public static boolean isValidUnicodeLocaleKey(final String s) {
        return UnicodeLocaleExtension.isKey(s);
    }
    
    private static String toID(final SortedMap sortedMap) {
        final StringBuilder sb = new StringBuilder();
        Object o = null;
        for (final Map.Entry<Character, V> entry : sortedMap.entrySet()) {
            final char charValue = entry.getKey();
            final Extension extension = (Extension)entry.getValue();
            if (LanguageTag.isPrivateusePrefixChar(charValue)) {
                o = extension;
            }
            else {
                if (sb.length() > 0) {
                    sb.append("-");
                }
                sb.append(extension);
            }
        }
        if (o != null) {
            if (sb.length() > 0) {
                sb.append("-");
            }
            sb.append(o);
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return this._id;
    }
    
    public String getID() {
        return this._id;
    }
    
    @Override
    public int hashCode() {
        return this._id.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof LocaleExtensions && this._id.equals(((LocaleExtensions)o)._id));
    }
    
    static {
        $assertionsDisabled = !LocaleExtensions.class.desiredAssertionStatus();
        EMPTY_MAP = Collections.unmodifiableSortedMap((SortedMap<Object, ?>)new TreeMap<Object, Object>());
        EMPTY_EXTENSIONS = new LocaleExtensions();
        LocaleExtensions.EMPTY_EXTENSIONS._id = "";
        LocaleExtensions.EMPTY_EXTENSIONS._map = LocaleExtensions.EMPTY_MAP;
        CALENDAR_JAPANESE = new LocaleExtensions();
        LocaleExtensions.CALENDAR_JAPANESE._id = "u-ca-japanese";
        (LocaleExtensions.CALENDAR_JAPANESE._map = new TreeMap()).put('u', UnicodeLocaleExtension.CA_JAPANESE);
        NUMBER_THAI = new LocaleExtensions();
        LocaleExtensions.NUMBER_THAI._id = "u-nu-thai";
        (LocaleExtensions.NUMBER_THAI._map = new TreeMap()).put('u', UnicodeLocaleExtension.NU_THAI);
    }
}
