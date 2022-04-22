package io.netty.handler.codec.http;

import io.netty.util.concurrent.*;
import java.text.*;
import java.util.*;

final class HttpHeaderDateFormat extends SimpleDateFormat
{
    private static final long serialVersionUID = -925286159755905325L;
    private final SimpleDateFormat format1;
    private final SimpleDateFormat format2;
    private static final FastThreadLocal dateFormatThreadLocal;
    
    static HttpHeaderDateFormat get() {
        return (HttpHeaderDateFormat)HttpHeaderDateFormat.dateFormatThreadLocal.get();
    }
    
    private HttpHeaderDateFormat() {
        super("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        this.format1 = new HttpHeaderDateFormatObsolete1();
        this.format2 = new HttpHeaderDateFormatObsolete2();
        this.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    
    @Override
    public Date parse(final String s, final ParsePosition parsePosition) {
        Date date = super.parse(s, parsePosition);
        if (date == null) {
            date = this.format1.parse(s, parsePosition);
        }
        if (date == null) {
            date = this.format2.parse(s, parsePosition);
        }
        return date;
    }
    
    HttpHeaderDateFormat(final HttpHeaderDateFormat$1 fastThreadLocal) {
        this();
    }
    
    static {
        dateFormatThreadLocal = new FastThreadLocal() {
            @Override
            protected HttpHeaderDateFormat initialValue() {
                return new HttpHeaderDateFormat((HttpHeaderDateFormat$1)null);
            }
            
            @Override
            protected Object initialValue() throws Exception {
                return this.initialValue();
            }
        };
    }
    
    private static final class HttpHeaderDateFormatObsolete2 extends SimpleDateFormat
    {
        private static final long serialVersionUID = 3010674519968303714L;
        
        HttpHeaderDateFormatObsolete2() {
            super("E MMM d HH:mm:ss yyyy", Locale.ENGLISH);
            this.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
    }
    
    private static final class HttpHeaderDateFormatObsolete1 extends SimpleDateFormat
    {
        private static final long serialVersionUID = -3178072504225114298L;
        
        HttpHeaderDateFormatObsolete1() {
            super("E, dd-MMM-yy HH:mm:ss z", Locale.ENGLISH);
            this.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
    }
}
