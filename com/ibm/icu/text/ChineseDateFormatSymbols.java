package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;

public class ChineseDateFormatSymbols extends DateFormatSymbols
{
    static final long serialVersionUID = 6827816119783952890L;
    String[] isLeapMonth;
    
    @Deprecated
    public ChineseDateFormatSymbols() {
        this(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    @Deprecated
    public ChineseDateFormatSymbols(final Locale locale) {
        super(ChineseCalendar.class, ULocale.forLocale(locale));
    }
    
    @Deprecated
    public ChineseDateFormatSymbols(final ULocale uLocale) {
        super(ChineseCalendar.class, uLocale);
    }
    
    @Deprecated
    public ChineseDateFormatSymbols(final Calendar calendar, final Locale locale) {
        super((calendar == null) ? null : calendar.getClass(), locale);
    }
    
    @Deprecated
    public ChineseDateFormatSymbols(final Calendar calendar, final ULocale uLocale) {
        super((calendar == null) ? null : calendar.getClass(), uLocale);
    }
    
    @Deprecated
    public String getLeapMonth(final int n) {
        return this.isLeapMonth[n];
    }
    
    @Override
    @Deprecated
    protected void initializeData(final ULocale uLocale, final CalendarData calendarData) {
        super.initializeData(uLocale, calendarData);
        this.initializeIsLeapMonth();
    }
    
    @Override
    void initializeData(final DateFormatSymbols dateFormatSymbols) {
        super.initializeData(dateFormatSymbols);
        if (dateFormatSymbols instanceof ChineseDateFormatSymbols) {
            this.isLeapMonth = ((ChineseDateFormatSymbols)dateFormatSymbols).isLeapMonth;
        }
        else {
            this.initializeIsLeapMonth();
        }
    }
    
    private void initializeIsLeapMonth() {
        (this.isLeapMonth = new String[2])[0] = "";
        this.isLeapMonth[1] = ((this.leapMonthPatterns != null) ? this.leapMonthPatterns[0].replace("{0}", "") : "");
    }
}
