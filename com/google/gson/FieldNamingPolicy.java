package com.google.gson;

import java.lang.reflect.*;

public enum FieldNamingPolicy implements FieldNamingStrategy
{
    IDENTITY {
        public String translateName(final Field field) {
            return field.getName();
        }
    }, 
    UPPER_CAMEL_CASE {
        public String translateName(final Field field) {
            return FieldNamingPolicy.access$100(field.getName());
        }
    }, 
    UPPER_CAMEL_CASE_WITH_SPACES {
        public String translateName(final Field field) {
            return FieldNamingPolicy.access$100(FieldNamingPolicy.access$200(field.getName(), " "));
        }
    }, 
    LOWER_CASE_WITH_UNDERSCORES {
        public String translateName(final Field field) {
            return FieldNamingPolicy.access$200(field.getName(), "_").toLowerCase();
        }
    }, 
    LOWER_CASE_WITH_DASHES {
        public String translateName(final Field field) {
            return FieldNamingPolicy.access$200(field.getName(), "-").toLowerCase();
        }
    };
    
    private static final FieldNamingPolicy[] $VALUES;
    
    private FieldNamingPolicy(final String s, final int n) {
    }
    
    private static String separateCamelCase(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (Character.isUpperCase(char1) && sb.length() != 0) {
                sb.append(s2);
            }
            sb.append(char1);
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    private static String upperCaseFirstLetter(final String s) {
        final StringBuilder sb = new StringBuilder();
        char c;
        int n = 0;
        for (c = s.charAt(0); 0 < s.length() - 1 && !Character.isLetter(c); c = s.charAt(0)) {
            sb.append(c);
            ++n;
        }
        if (0 == s.length()) {
            return sb.toString();
        }
        if (!Character.isUpperCase(c)) {
            final char upperCase = Character.toUpperCase(c);
            ++n;
            return sb.append(modifyString(upperCase, s, 0)).toString();
        }
        return s;
    }
    
    private static String modifyString(final char c, final String s, final int n) {
        return (n < s.length()) ? (c + s.substring(n)) : String.valueOf(c);
    }
    
    FieldNamingPolicy(final String s, final int n, final FieldNamingPolicy$1 fieldNamingPolicy) {
        this(s, n);
    }
    
    static String access$100(final String s) {
        return upperCaseFirstLetter(s);
    }
    
    static String access$200(final String s, final String s2) {
        return separateCamelCase(s, s2);
    }
    
    static {
        $VALUES = new FieldNamingPolicy[] { FieldNamingPolicy.IDENTITY, FieldNamingPolicy.UPPER_CAMEL_CASE, FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES, FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES, FieldNamingPolicy.LOWER_CASE_WITH_DASHES };
    }
}
