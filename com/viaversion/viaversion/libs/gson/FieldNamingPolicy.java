package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.*;
import java.util.*;

public enum FieldNamingPolicy implements FieldNamingStrategy
{
    IDENTITY(0) {
        @Override
        public String translateName(final Field field) {
            return field.getName();
        }
    }, 
    UPPER_CAMEL_CASE(1) {
        @Override
        public String translateName(final Field field) {
            return FieldNamingPolicy.upperCaseFirstLetter(field.getName());
        }
    }, 
    UPPER_CAMEL_CASE_WITH_SPACES(2) {
        @Override
        public String translateName(final Field field) {
            return FieldNamingPolicy.upperCaseFirstLetter(FieldNamingPolicy.separateCamelCase(field.getName(), " "));
        }
    }, 
    LOWER_CASE_WITH_UNDERSCORES(3) {
        @Override
        public String translateName(final Field field) {
            return FieldNamingPolicy.separateCamelCase(field.getName(), "_").toLowerCase(Locale.ENGLISH);
        }
    }, 
    LOWER_CASE_WITH_DASHES(4) {
        @Override
        public String translateName(final Field field) {
            return FieldNamingPolicy.separateCamelCase(field.getName(), "-").toLowerCase(Locale.ENGLISH);
        }
    }, 
    LOWER_CASE_WITH_DOTS(5) {
        @Override
        public String translateName(final Field field) {
            return FieldNamingPolicy.separateCamelCase(field.getName(), ".").toLowerCase(Locale.ENGLISH);
        }
    };
    
    private static final FieldNamingPolicy[] $VALUES;
    
    private FieldNamingPolicy(final String s, final int n) {
    }
    
    static String separateCamelCase(final String s, final String s2) {
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
    
    static String upperCaseFirstLetter(final String s) {
        final int n = s.length() - 1;
        while (!Character.isLetter(s.charAt(0)) && 0 < n) {
            int n2 = 0;
            ++n2;
        }
        final char char1 = s.charAt(0);
        if (Character.isUpperCase(char1)) {
            return s;
        }
        final char upperCase = Character.toUpperCase(char1);
        if (!false) {
            return upperCase + s.substring(1);
        }
        return s.substring(0, 0) + upperCase + s.substring(1);
    }
    
    FieldNamingPolicy(final String s, final int n, final FieldNamingPolicy$1 fieldNamingPolicy) {
        this(s, n);
    }
    
    static {
        $VALUES = new FieldNamingPolicy[] { FieldNamingPolicy.IDENTITY, FieldNamingPolicy.UPPER_CAMEL_CASE, FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES, FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES, FieldNamingPolicy.LOWER_CASE_WITH_DASHES, FieldNamingPolicy.LOWER_CASE_WITH_DOTS };
    }
}
