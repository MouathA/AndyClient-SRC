package org.apache.commons.lang3.time;

import java.util.*;
import java.text.*;

public interface DatePrinter
{
    String format(final long p0);
    
    String format(final Date p0);
    
    String format(final Calendar p0);
    
    StringBuffer format(final long p0, final StringBuffer p1);
    
    StringBuffer format(final Date p0, final StringBuffer p1);
    
    StringBuffer format(final Calendar p0, final StringBuffer p1);
    
    String getPattern();
    
    TimeZone getTimeZone();
    
    Locale getLocale();
    
    StringBuffer format(final Object p0, final StringBuffer p1, final FieldPosition p2);
}
