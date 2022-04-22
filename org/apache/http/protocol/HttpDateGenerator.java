package org.apache.http.protocol;

import org.apache.http.annotation.*;
import java.text.*;
import java.util.*;

@ThreadSafe
public class HttpDateGenerator
{
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final TimeZone GMT;
    @GuardedBy("this")
    private final DateFormat dateformat;
    @GuardedBy("this")
    private long dateAsLong;
    @GuardedBy("this")
    private String dateAsText;
    
    public HttpDateGenerator() {
        this.dateAsLong = 0L;
        this.dateAsText = null;
        (this.dateformat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US)).setTimeZone(HttpDateGenerator.GMT);
    }
    
    public synchronized String getCurrentDate() {
        final long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.dateAsLong > 1000L) {
            this.dateAsText = this.dateformat.format(new Date(currentTimeMillis));
            this.dateAsLong = currentTimeMillis;
        }
        return this.dateAsText;
    }
    
    static {
        GMT = TimeZone.getTimeZone("GMT");
    }
}
