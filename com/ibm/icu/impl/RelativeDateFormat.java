package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import java.text.*;
import com.ibm.icu.util.*;
import java.util.*;

public class RelativeDateFormat extends DateFormat
{
    private static final long serialVersionUID = 1131984966440549435L;
    private DateFormat fDateFormat;
    private DateFormat fTimeFormat;
    private MessageFormat fCombinedFormat;
    private SimpleDateFormat fDateTimeFormat;
    private String fDatePattern;
    private String fTimePattern;
    int fDateStyle;
    int fTimeStyle;
    ULocale fLocale;
    private transient URelativeString[] fDates;
    
    public RelativeDateFormat(final int fTimeStyle, final int fDateStyle, final ULocale fLocale) {
        this.fDateTimeFormat = null;
        this.fDatePattern = null;
        this.fTimePattern = null;
        this.fDates = null;
        this.fLocale = fLocale;
        this.fTimeStyle = fTimeStyle;
        this.fDateStyle = fDateStyle;
        if (this.fDateStyle != -1) {
            final DateFormat dateInstance = DateFormat.getDateInstance(this.fDateStyle & 0xFFFFFF7F, fLocale);
            if (!(dateInstance instanceof SimpleDateFormat)) {
                throw new IllegalArgumentException("Can't create SimpleDateFormat for date style");
            }
            this.fDateTimeFormat = (SimpleDateFormat)dateInstance;
            this.fDatePattern = this.fDateTimeFormat.toPattern();
            if (this.fTimeStyle != -1) {
                final DateFormat timeInstance = DateFormat.getTimeInstance(this.fTimeStyle & 0xFFFFFF7F, fLocale);
                if (timeInstance instanceof SimpleDateFormat) {
                    this.fTimePattern = ((SimpleDateFormat)timeInstance).toPattern();
                }
            }
        }
        else {
            final DateFormat timeInstance2 = DateFormat.getTimeInstance(this.fTimeStyle & 0xFFFFFF7F, fLocale);
            if (!(timeInstance2 instanceof SimpleDateFormat)) {
                throw new IllegalArgumentException("Can't create SimpleDateFormat for time style");
            }
            this.fDateTimeFormat = (SimpleDateFormat)timeInstance2;
            this.fTimePattern = this.fDateTimeFormat.toPattern();
        }
        this.initializeCalendar(null, this.fLocale);
        this.loadDates();
        this.initializeCombinedFormat(this.calendar, this.fLocale);
    }
    
    @Override
    public StringBuffer format(final Calendar calendar, final StringBuffer sb, final FieldPosition fieldPosition) {
        String stringForDay = null;
        if (this.fDateStyle != -1) {
            stringForDay = this.getStringForDay(dayDifference(calendar));
        }
        if (this.fDateTimeFormat != null && (this.fDatePattern != null || this.fTimePattern != null)) {
            if (this.fDatePattern == null) {
                this.fDateTimeFormat.applyPattern(this.fTimePattern);
                this.fDateTimeFormat.format(calendar, sb, fieldPosition);
            }
            else if (this.fTimePattern == null) {
                if (stringForDay != null) {
                    sb.append(stringForDay);
                }
                else {
                    this.fDateTimeFormat.applyPattern(this.fDatePattern);
                    this.fDateTimeFormat.format(calendar, sb, fieldPosition);
                }
            }
            else {
                String s = this.fDatePattern;
                if (stringForDay != null) {
                    s = "'" + stringForDay.replace("'", "''") + "'";
                }
                final StringBuffer sb2 = new StringBuffer("");
                this.fCombinedFormat.format(new Object[] { this.fTimePattern, s }, sb2, new FieldPosition(0));
                this.fDateTimeFormat.applyPattern(sb2.toString());
                this.fDateTimeFormat.format(calendar, sb, fieldPosition);
            }
        }
        else if (this.fDateFormat != null) {
            if (stringForDay != null) {
                sb.append(stringForDay);
            }
            else {
                this.fDateFormat.format(calendar, sb, fieldPosition);
            }
        }
        return sb;
    }
    
    @Override
    public void parse(final String s, final Calendar calendar, final ParsePosition parsePosition) {
        throw new UnsupportedOperationException("Relative Date parse is not implemented yet");
    }
    
    private String getStringForDay(final int n) {
        if (this.fDates == null) {
            this.loadDates();
        }
        while (0 < this.fDates.length) {
            if (this.fDates[0].offset == n) {
                return this.fDates[0].string;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    private synchronized void loadDates() {
        final ICUResourceBundle withFallback = ((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", this.fLocale)).getWithFallback("fields/day/relative");
        final TreeSet<URelativeString> set = new TreeSet<URelativeString>(new Comparator() {
            final RelativeDateFormat this$0;
            
            public int compare(final URelativeString uRelativeString, final URelativeString uRelativeString2) {
                if (uRelativeString.offset == uRelativeString2.offset) {
                    return 0;
                }
                if (uRelativeString.offset < uRelativeString2.offset) {
                    return -1;
                }
                return 1;
            }
            
            public int compare(final Object o, final Object o2) {
                return this.compare((URelativeString)o, (URelativeString)o2);
            }
        });
        final UResourceBundleIterator iterator = withFallback.getIterator();
        while (iterator.hasNext()) {
            final UResourceBundle next = iterator.next();
            set.add(new URelativeString(next.getKey(), next.getString()));
        }
        this.fDates = set.toArray(new URelativeString[0]);
    }
    
    private static int dayDifference(final Calendar calendar) {
        final Calendar calendar2 = (Calendar)calendar.clone();
        final Date time = new Date(System.currentTimeMillis());
        calendar2.clear();
        calendar2.setTime(time);
        return calendar.get(20) - calendar2.get(20);
    }
    
    private Calendar initializeCalendar(final TimeZone timeZone, final ULocale uLocale) {
        if (this.calendar == null) {
            if (timeZone == null) {
                this.calendar = Calendar.getInstance(uLocale);
            }
            else {
                this.calendar = Calendar.getInstance(timeZone, uLocale);
            }
        }
        return this.calendar;
    }
    
    private MessageFormat initializeCombinedFormat(final Calendar calendar, final ULocale uLocale) {
        String s = "{1} {0}";
        final String[] dateTimePatterns = new CalendarData(uLocale, calendar.getType()).getDateTimePatterns();
        if (dateTimePatterns != null && dateTimePatterns.length >= 9) {
            if (dateTimePatterns.length >= 13) {
                switch (this.fDateStyle) {
                    case 0:
                    case 128: {
                        int n = 0;
                        ++n;
                        break;
                    }
                    case 1:
                    case 129: {
                        final int n;
                        n += 2;
                        break;
                    }
                    case 2:
                    case 130: {
                        final int n;
                        n += 3;
                        break;
                    }
                    case 3:
                    case 131: {
                        final int n;
                        n += 4;
                        break;
                    }
                }
            }
            s = dateTimePatterns[8];
        }
        return this.fCombinedFormat = new MessageFormat(s, uLocale);
    }
    
    public static class URelativeString
    {
        public int offset;
        public String string;
        
        URelativeString(final int offset, final String string) {
            this.offset = offset;
            this.string = string;
        }
        
        URelativeString(final String s, final String string) {
            this.offset = Integer.parseInt(s);
            this.string = string;
        }
    }
}
