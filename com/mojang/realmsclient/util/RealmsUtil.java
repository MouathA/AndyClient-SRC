package com.mojang.realmsclient.util;

import java.net.*;
import org.apache.logging.log4j.*;

public class RealmsUtil
{
    private static final Logger LOGGER;
    private static final int MINUTES = 60;
    private static final int HOURS = 3600;
    private static final int DAYS = 86400;
    
    public static void browseTo(final String s) {
        final URI uri = new URI(s);
        final Class<?> forName = Class.forName("java.awt.Desktop");
        forName.getMethod("browse", URI.class).invoke(forName.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]), uri);
    }
    
    public static String convertToAgePresentation(final Long n) {
        if (n < 0L) {
            return "right now";
        }
        final long n2 = n / 1000L;
        if (n2 < 60L) {
            return ((n2 == 1L) ? "1 second" : (n2 + " seconds")) + " ago";
        }
        if (n2 < 3600L) {
            final long n3 = n2 / 60L;
            return ((n3 == 1L) ? "1 minute" : (n3 + " minutes")) + " ago";
        }
        if (n2 < 86400L) {
            final long n4 = n2 / 3600L;
            return ((n4 == 1L) ? "1 hour" : (n4 + " hours")) + " ago";
        }
        final long n5 = n2 / 86400L;
        return ((n5 == 1L) ? "1 day" : (n5 + " days")) + " ago";
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
