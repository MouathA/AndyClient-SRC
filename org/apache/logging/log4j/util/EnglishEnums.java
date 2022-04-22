package org.apache.logging.log4j.util;

import java.util.*;

public final class EnglishEnums
{
    private EnglishEnums() {
    }
    
    public static Enum valueOf(final Class clazz, final String s) {
        return valueOf(clazz, s, null);
    }
    
    public static Enum valueOf(final Class clazz, final String s, final Enum enum1) {
        return (s == null) ? enum1 : Enum.valueOf((Class<Enum>)clazz, s.toUpperCase(Locale.ENGLISH));
    }
}
