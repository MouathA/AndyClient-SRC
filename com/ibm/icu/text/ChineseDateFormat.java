package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.util.*;
import java.text.*;
import java.io.*;

public class ChineseDateFormat extends SimpleDateFormat
{
    static final long serialVersionUID = -4610300753104099899L;
    
    @Deprecated
    public ChineseDateFormat(final String s, final Locale locale) {
        this(s, ULocale.forLocale(locale));
    }
    
    @Deprecated
    public ChineseDateFormat(final String s, final ULocale uLocale) {
        this(s, (String)null, uLocale);
    }
    
    @Deprecated
    public ChineseDateFormat(final String s, final String s2, final ULocale uLocale) {
        super(s, new ChineseDateFormatSymbols(uLocale), new ChineseCalendar(TimeZone.getDefault(), uLocale), uLocale, true, s2);
    }
    
    @Override
    @Deprecated
    protected void subFormat(final StringBuffer sb, final char c, final int n, final int n2, final int n3, final DisplayContext displayContext, final FieldPosition fieldPosition, final Calendar calendar) {
        super.subFormat(sb, c, n, n2, n3, displayContext, fieldPosition, calendar);
    }
    
    @Override
    @Deprecated
    protected int subParse(final String s, final int n, final char c, final int n2, final boolean b, final boolean b2, final boolean[] array, final Calendar calendar) {
        return super.subParse(s, n, c, n2, b, b2, array, calendar);
    }
    
    @Override
    @Deprecated
    protected DateFormat.Field patternCharToDateFormatField(final char c) {
        return super.patternCharToDateFormatField(c);
    }
    
    public static class Field extends DateFormat.Field
    {
        private static final long serialVersionUID = -5102130532751400330L;
        @Deprecated
        public static final Field IS_LEAP_MONTH;
        
        @Deprecated
        protected Field(final String s, final int n) {
            super(s, n);
        }
        
        @Deprecated
        public static DateFormat.Field ofCalendarField(final int n) {
            if (n == 22) {
                return Field.IS_LEAP_MONTH;
            }
            return DateFormat.Field.ofCalendarField(n);
        }
        
        @Override
        @Deprecated
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() != Field.class) {
                throw new InvalidObjectException("A subclass of ChineseDateFormat.Field must implement readResolve.");
            }
            if (this.getName().equals(Field.IS_LEAP_MONTH.getName())) {
                return Field.IS_LEAP_MONTH;
            }
            throw new InvalidObjectException("Unknown attribute name.");
        }
        
        static {
            IS_LEAP_MONTH = new Field("is leap month", 22);
        }
    }
}
