package com.viaversion.viaversion.libs.gson.internal;

import java.util.*;
import java.text.*;

public class PreJava9DateFormatProvider
{
    public static DateFormat getUSDateFormat(final int n) {
        return new SimpleDateFormat(getDateFormatPattern(n), Locale.US);
    }
    
    public static DateFormat getUSDateTimeFormat(final int n, final int n2) {
        return new SimpleDateFormat(getDatePartOfDateTimePattern(n) + " " + getTimePartOfDateTimePattern(n2), Locale.US);
    }
    
    private static String getDateFormatPattern(final int n) {
        switch (n) {
            case 3: {
                return "M/d/yy";
            }
            case 2: {
                return "MMM d, y";
            }
            case 1: {
                return "MMMM d, y";
            }
            case 0: {
                return "EEEE, MMMM d, y";
            }
            default: {
                throw new IllegalArgumentException("Unknown DateFormat style: " + n);
            }
        }
    }
    
    private static String getDatePartOfDateTimePattern(final int n) {
        switch (n) {
            case 3: {
                return "M/d/yy";
            }
            case 2: {
                return "MMM d, yyyy";
            }
            case 1: {
                return "MMMM d, yyyy";
            }
            case 0: {
                return "EEEE, MMMM d, yyyy";
            }
            default: {
                throw new IllegalArgumentException("Unknown DateFormat style: " + n);
            }
        }
    }
    
    private static String getTimePartOfDateTimePattern(final int n) {
        switch (n) {
            case 3: {
                return "h:mm a";
            }
            case 2: {
                return "h:mm:ss a";
            }
            case 0:
            case 1: {
                return "h:mm:ss a z";
            }
            default: {
                throw new IllegalArgumentException("Unknown DateFormat style: " + n);
            }
        }
    }
}
