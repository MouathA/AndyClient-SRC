package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import java.text.*;
import java.util.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "date", category = "Lookup")
public class DateLookup implements StrLookup
{
    private static final Logger LOGGER;
    
    @Override
    public String lookup(final String s) {
        return this.formatDate(System.currentTimeMillis(), s);
    }
    
    @Override
    public String lookup(final LogEvent logEvent, final String s) {
        return this.formatDate(logEvent.getMillis(), s);
    }
    
    private String formatDate(final long n, final String s) {
        DateFormat instance = null;
        if (s != null) {
            instance = new SimpleDateFormat(s);
        }
        if (instance == null) {
            instance = DateFormat.getInstance();
        }
        return instance.format(new Date(n));
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
