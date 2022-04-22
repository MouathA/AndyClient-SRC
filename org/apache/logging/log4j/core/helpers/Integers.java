package org.apache.logging.log4j.core.helpers;

public class Integers
{
    public static int parseInt(final String s, final int n) {
        return Strings.isEmpty(s) ? n : Integer.parseInt(s);
    }
    
    public static int parseInt(final String s) {
        return parseInt(s, 0);
    }
}
