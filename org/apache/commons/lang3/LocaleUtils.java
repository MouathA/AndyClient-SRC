package org.apache.commons.lang3;

import java.util.concurrent.*;
import java.util.*;

public class LocaleUtils
{
    private static final ConcurrentMap cLanguagesByCountry;
    private static final ConcurrentMap cCountriesByLanguage;
    
    public static Locale toLocale(final String s) {
        if (s == null) {
            return null;
        }
        if (s.isEmpty()) {
            return new Locale("", "");
        }
        if (s.contains("#")) {
            throw new IllegalArgumentException("Invalid locale format: " + s);
        }
        final int length = s.length();
        if (length < 2) {
            throw new IllegalArgumentException("Invalid locale format: " + s);
        }
        if (s.charAt(0) != '_') {
            final String[] split = s.split("_", -1);
            switch (split.length - 1) {
                case 0: {
                    if (StringUtils.isAllLowerCase(s) && (length == 2 || length == 3)) {
                        return new Locale(s);
                    }
                    throw new IllegalArgumentException("Invalid locale format: " + s);
                }
                case 1: {
                    if (StringUtils.isAllLowerCase(split[0]) && (split[0].length() == 2 || split[0].length() == 3) && split[1].length() == 2 && StringUtils.isAllUpperCase(split[1])) {
                        return new Locale(split[0], split[1]);
                    }
                    throw new IllegalArgumentException("Invalid locale format: " + s);
                }
                case 2: {
                    if (StringUtils.isAllLowerCase(split[0]) && (split[0].length() == 2 || split[0].length() == 3) && (split[1].length() == 0 || (split[1].length() == 2 && StringUtils.isAllUpperCase(split[1]))) && split[2].length() > 0) {
                        return new Locale(split[0], split[1], split[2]);
                    }
                    break;
                }
            }
            throw new IllegalArgumentException("Invalid locale format: " + s);
        }
        if (length < 3) {
            throw new IllegalArgumentException("Invalid locale format: " + s);
        }
        final char char1 = s.charAt(1);
        final char char2 = s.charAt(2);
        if (!Character.isUpperCase(char1) || !Character.isUpperCase(char2)) {
            throw new IllegalArgumentException("Invalid locale format: " + s);
        }
        if (length == 3) {
            return new Locale("", s.substring(1, 3));
        }
        if (length < 5) {
            throw new IllegalArgumentException("Invalid locale format: " + s);
        }
        if (s.charAt(3) != '_') {
            throw new IllegalArgumentException("Invalid locale format: " + s);
        }
        return new Locale("", s.substring(1, 3), s.substring(4));
    }
    
    public static List localeLookupList(final Locale locale) {
        return localeLookupList(locale, locale);
    }
    
    public static List localeLookupList(final Locale locale, final Locale locale2) {
        final ArrayList list = new ArrayList<Locale>(4);
        if (locale != null) {
            list.add(locale);
            if (locale.getVariant().length() > 0) {
                list.add(new Locale(locale.getLanguage(), locale.getCountry()));
            }
            if (locale.getCountry().length() > 0) {
                list.add(new Locale(locale.getLanguage(), ""));
            }
            if (!list.contains(locale2)) {
                list.add(locale2);
            }
        }
        return Collections.unmodifiableList((List<?>)list);
    }
    
    public static List availableLocaleList() {
        return SyncAvoid.access$000();
    }
    
    public static Set availableLocaleSet() {
        return SyncAvoid.access$100();
    }
    
    public static boolean isAvailableLocale(final Locale locale) {
        return availableLocaleList().contains(locale);
    }
    
    public static List languagesByCountry(final String s) {
        if (s == null) {
            return Collections.emptyList();
        }
        List list = (List)LocaleUtils.cLanguagesByCountry.get(s);
        if (list == null) {
            final ArrayList<Locale> list2 = new ArrayList<Locale>();
            final List availableLocaleList = availableLocaleList();
            while (0 < availableLocaleList.size()) {
                final Locale locale = availableLocaleList.get(0);
                if (s.equals(locale.getCountry()) && locale.getVariant().isEmpty()) {
                    list2.add(locale);
                }
                int n = 0;
                ++n;
            }
            LocaleUtils.cLanguagesByCountry.putIfAbsent(s, Collections.unmodifiableList((List<?>)list2));
            list = (List)LocaleUtils.cLanguagesByCountry.get(s);
        }
        return list;
    }
    
    public static List countriesByLanguage(final String s) {
        if (s == null) {
            return Collections.emptyList();
        }
        List list = (List)LocaleUtils.cCountriesByLanguage.get(s);
        if (list == null) {
            final ArrayList<Locale> list2 = new ArrayList<Locale>();
            final List availableLocaleList = availableLocaleList();
            while (0 < availableLocaleList.size()) {
                final Locale locale = availableLocaleList.get(0);
                if (s.equals(locale.getLanguage()) && locale.getCountry().length() != 0 && locale.getVariant().isEmpty()) {
                    list2.add(locale);
                }
                int n = 0;
                ++n;
            }
            LocaleUtils.cCountriesByLanguage.putIfAbsent(s, Collections.unmodifiableList((List<?>)list2));
            list = (List)LocaleUtils.cCountriesByLanguage.get(s);
        }
        return list;
    }
    
    static {
        cLanguagesByCountry = new ConcurrentHashMap();
        cCountriesByLanguage = new ConcurrentHashMap();
    }
    
    static class SyncAvoid
    {
        private static final List AVAILABLE_LOCALE_LIST;
        private static final Set AVAILABLE_LOCALE_SET;
        
        static List access$000() {
            return SyncAvoid.AVAILABLE_LOCALE_LIST;
        }
        
        static Set access$100() {
            return SyncAvoid.AVAILABLE_LOCALE_SET;
        }
        
        static {
            final ArrayList<Object> list = new ArrayList<Object>(Arrays.asList(Locale.getAvailableLocales()));
            AVAILABLE_LOCALE_LIST = Collections.unmodifiableList((List<?>)list);
            AVAILABLE_LOCALE_SET = Collections.unmodifiableSet((Set<?>)new HashSet<Object>(list));
        }
    }
}
