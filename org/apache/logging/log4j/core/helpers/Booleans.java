package org.apache.logging.log4j.core.helpers;

public class Booleans
{
    public static boolean parseBoolean(final String s, final boolean b) {
        return "true".equalsIgnoreCase(s) || (b && !"false".equalsIgnoreCase(s));
    }
}
