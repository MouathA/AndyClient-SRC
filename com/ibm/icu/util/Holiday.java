package com.ibm.icu.util;

import java.util.*;

public abstract class Holiday implements DateRule
{
    private String name;
    private DateRule rule;
    private static Holiday[] noHolidays;
    
    public static Holiday[] getHolidays() {
        return getHolidays(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static Holiday[] getHolidays(final Locale locale) {
        return getHolidays(ULocale.forLocale(locale));
    }
    
    public static Holiday[] getHolidays(final ULocale uLocale) {
        final Holiday[] noHolidays = Holiday.noHolidays;
        return (Holiday[])UResourceBundle.getBundleInstance("com.ibm.icu.impl.data.HolidayBundle", uLocale).getObject("holidays");
    }
    
    public Date firstAfter(final Date date) {
        return this.rule.firstAfter(date);
    }
    
    public Date firstBetween(final Date date, final Date date2) {
        return this.rule.firstBetween(date, date2);
    }
    
    public boolean isOn(final Date date) {
        return this.rule.isOn(date);
    }
    
    public boolean isBetween(final Date date, final Date date2) {
        return this.rule.isBetween(date, date2);
    }
    
    protected Holiday(final String name, final DateRule rule) {
        this.name = name;
        this.rule = rule;
    }
    
    public String getDisplayName() {
        return this.getDisplayName(ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public String getDisplayName(final Locale locale) {
        return this.getDisplayName(ULocale.forLocale(locale));
    }
    
    public String getDisplayName(final ULocale uLocale) {
        final String name = this.name;
        return UResourceBundle.getBundleInstance("com.ibm.icu.impl.data.HolidayBundle", uLocale).getString(this.name);
    }
    
    public DateRule getRule() {
        return this.rule;
    }
    
    public void setRule(final DateRule rule) {
        this.rule = rule;
    }
    
    static {
        Holiday.noHolidays = new Holiday[0];
    }
}
