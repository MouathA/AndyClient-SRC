package com.ibm.icu.util;

import java.io.*;

public class DateTimeRule implements Serializable
{
    private static final long serialVersionUID = 2183055795738051443L;
    public static final int DOM = 0;
    public static final int DOW = 1;
    public static final int DOW_GEQ_DOM = 2;
    public static final int DOW_LEQ_DOM = 3;
    public static final int WALL_TIME = 0;
    public static final int STANDARD_TIME = 1;
    public static final int UTC_TIME = 2;
    private final int dateRuleType;
    private final int month;
    private final int dayOfMonth;
    private final int dayOfWeek;
    private final int weekInMonth;
    private final int timeRuleType;
    private final int millisInDay;
    private static final String[] MONSTR;
    
    public DateTimeRule(final int month, final int dayOfMonth, final int millisInDay, final int timeRuleType) {
        this.dateRuleType = 0;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.millisInDay = millisInDay;
        this.timeRuleType = timeRuleType;
        this.dayOfWeek = 0;
        this.weekInMonth = 0;
    }
    
    public DateTimeRule(final int month, final int weekInMonth, final int dayOfWeek, final int millisInDay, final int timeRuleType) {
        this.dateRuleType = 1;
        this.month = month;
        this.weekInMonth = weekInMonth;
        this.dayOfWeek = dayOfWeek;
        this.millisInDay = millisInDay;
        this.timeRuleType = timeRuleType;
        this.dayOfMonth = 0;
    }
    
    public DateTimeRule(final int month, final int dayOfMonth, final int dayOfWeek, final boolean b, final int millisInDay, final int timeRuleType) {
        this.dateRuleType = (b ? 2 : 3);
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
        this.millisInDay = millisInDay;
        this.timeRuleType = timeRuleType;
        this.weekInMonth = 0;
    }
    
    public int getDateRuleType() {
        return this.dateRuleType;
    }
    
    public int getRuleMonth() {
        return this.month;
    }
    
    public int getRuleDayOfMonth() {
        return this.dayOfMonth;
    }
    
    public int getRuleDayOfWeek() {
        return this.dayOfWeek;
    }
    
    public int getRuleWeekInMonth() {
        return this.weekInMonth;
    }
    
    public int getTimeRuleType() {
        return this.timeRuleType;
    }
    
    public int getRuleMillisInDay() {
        return this.millisInDay;
    }
    
    @Override
    public String toString() {
        String s = null;
        String s2 = null;
        switch (this.dateRuleType) {
            case 0: {
                s = Integer.toString(this.dayOfMonth);
                break;
            }
            case 1: {
                s = Integer.toString(this.weekInMonth) + DateTimeRule.DOWSTR[this.dayOfWeek];
                break;
            }
            case 2: {
                s = DateTimeRule.DOWSTR[this.dayOfWeek] + ">=" + Integer.toString(this.dayOfMonth);
                break;
            }
            case 3: {
                s = DateTimeRule.DOWSTR[this.dayOfWeek] + "<=" + Integer.toString(this.dayOfMonth);
                break;
            }
        }
        switch (this.timeRuleType) {
            case 0: {
                s2 = "WALL";
                break;
            }
            case 1: {
                s2 = "STD";
                break;
            }
            case 2: {
                s2 = "UTC";
                break;
            }
        }
        final int millisInDay = this.millisInDay;
        final int n = millisInDay % 1000;
        final int n2 = millisInDay / 1000;
        final int n3 = n2 % 60;
        final int n4 = n2 / 60;
        final int n5 = n4 % 60;
        final int n6 = n4 / 60;
        final StringBuilder sb = new StringBuilder();
        sb.append("month=");
        sb.append(DateTimeRule.MONSTR[this.month]);
        sb.append(", date=");
        sb.append(s);
        sb.append(", time=");
        sb.append(n6);
        sb.append(":");
        sb.append(n5 / 10);
        sb.append(n5 % 10);
        sb.append(":");
        sb.append(n3 / 10);
        sb.append(n3 % 10);
        sb.append(".");
        sb.append(n / 100);
        sb.append(n / 10 % 10);
        sb.append(n % 10);
        sb.append("(");
        sb.append(s2);
        sb.append(")");
        return sb.toString();
    }
    
    static {
        DateTimeRule.DOWSTR = new String[] { "", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        MONSTR = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    }
}
