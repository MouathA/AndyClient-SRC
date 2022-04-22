package org.apache.logging.log4j.core.helpers;

public final class Assert
{
    private Assert() {
    }
    
    public static Object isNotNull(final Object o, final String s) {
        if (o == null) {
            throw new NullPointerException(s + " is null");
        }
        return o;
    }
}
