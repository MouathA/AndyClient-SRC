package com.ibm.icu.util;

import java.util.*;
import com.ibm.icu.impl.*;

public class AnnualTimeZoneRule extends TimeZoneRule
{
    private static final long serialVersionUID = -8870666707791230688L;
    public static final int MAX_YEAR = Integer.MAX_VALUE;
    private final DateTimeRule dateTimeRule;
    private final int startYear;
    private final int endYear;
    
    public AnnualTimeZoneRule(final String s, final int n, final int n2, final DateTimeRule dateTimeRule, final int startYear, final int n3) {
        super(s, n, n2);
        this.dateTimeRule = dateTimeRule;
        this.startYear = startYear;
        this.endYear = ((n3 > Integer.MAX_VALUE) ? Integer.MAX_VALUE : n3);
    }
    
    public DateTimeRule getRule() {
        return this.dateTimeRule;
    }
    
    public int getStartYear() {
        return this.startYear;
    }
    
    public int getEndYear() {
        return this.endYear;
    }
    
    public Date getStartInYear(final int n, final int n2, final int n3) {
        if (n < this.startYear || n > this.endYear) {
            return null;
        }
        final int dateRuleType = this.dateTimeRule.getDateRuleType();
        long fieldsToDay;
        if (dateRuleType == 0) {
            fieldsToDay = Grego.fieldsToDay(n, this.dateTimeRule.getRuleMonth(), this.dateTimeRule.getRuleDayOfMonth());
        }
        else {
            long fieldsToDay2;
            if (dateRuleType == 1) {
                final int ruleWeekInMonth = this.dateTimeRule.getRuleWeekInMonth();
                if (ruleWeekInMonth > 0) {
                    fieldsToDay2 = Grego.fieldsToDay(n, this.dateTimeRule.getRuleMonth(), 1) + 7 * (ruleWeekInMonth - 1);
                }
                else {
                    fieldsToDay2 = Grego.fieldsToDay(n, this.dateTimeRule.getRuleMonth(), Grego.monthLength(n, this.dateTimeRule.getRuleMonth())) + 7 * (ruleWeekInMonth + 1);
                }
            }
            else {
                final int ruleMonth = this.dateTimeRule.getRuleMonth();
                int ruleDayOfMonth = this.dateTimeRule.getRuleDayOfMonth();
                if (dateRuleType == 3 && ruleMonth == 1 && ruleDayOfMonth == 29 && !Grego.isLeapYear(n)) {
                    --ruleDayOfMonth;
                }
                fieldsToDay2 = Grego.fieldsToDay(n, ruleMonth, ruleDayOfMonth);
            }
            final int n4 = this.dateTimeRule.getRuleDayOfWeek() - Grego.dayOfWeek(fieldsToDay2);
            int n5;
            if (false) {
                n5 = ((n4 < 0) ? (n4 + 7) : n4);
            }
            else {
                n5 = ((n4 > 0) ? (n4 - 7) : n4);
            }
            fieldsToDay = fieldsToDay2 + n5;
        }
        long n6 = fieldsToDay * 86400000L + this.dateTimeRule.getRuleMillisInDay();
        if (this.dateTimeRule.getTimeRuleType() != 2) {
            n6 -= n2;
        }
        if (this.dateTimeRule.getTimeRuleType() == 0) {
            n6 -= n3;
        }
        return new Date(n6);
    }
    
    @Override
    public Date getFirstStart(final int n, final int n2) {
        return this.getStartInYear(this.startYear, n, n2);
    }
    
    @Override
    public Date getFinalStart(final int n, final int n2) {
        if (this.endYear == Integer.MAX_VALUE) {
            return null;
        }
        return this.getStartInYear(this.endYear, n, n2);
    }
    
    @Override
    public Date getNextStart(final long n, final int n2, final int n3, final boolean b) {
        final int n4 = Grego.timeToFields(n, null)[0];
        if (n4 < this.startYear) {
            return this.getFirstStart(n2, n3);
        }
        Date date = this.getStartInYear(n4, n2, n3);
        if (date != null && (date.getTime() < n || (!b && date.getTime() == n))) {
            date = this.getStartInYear(n4 + 1, n2, n3);
        }
        return date;
    }
    
    @Override
    public Date getPreviousStart(final long n, final int n2, final int n3, final boolean b) {
        final int n4 = Grego.timeToFields(n, null)[0];
        if (n4 > this.endYear) {
            return this.getFinalStart(n2, n3);
        }
        Date date = this.getStartInYear(n4, n2, n3);
        if (date != null && (date.getTime() > n || (!b && date.getTime() == n))) {
            date = this.getStartInYear(n4 - 1, n2, n3);
        }
        return date;
    }
    
    @Override
    public boolean isEquivalentTo(final TimeZoneRule timeZoneRule) {
        if (!(timeZoneRule instanceof AnnualTimeZoneRule)) {
            return false;
        }
        final AnnualTimeZoneRule annualTimeZoneRule = (AnnualTimeZoneRule)timeZoneRule;
        return this.startYear == annualTimeZoneRule.startYear && this.endYear == annualTimeZoneRule.endYear && this.dateTimeRule.equals(annualTimeZoneRule.dateTimeRule) && super.isEquivalentTo(timeZoneRule);
    }
    
    @Override
    public boolean isTransitionRule() {
        return true;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", rule={" + this.dateTimeRule + "}");
        sb.append(", startYear=" + this.startYear);
        sb.append(", endYear=");
        if (this.endYear == Integer.MAX_VALUE) {
            sb.append("max");
        }
        else {
            sb.append(this.endYear);
        }
        return sb.toString();
    }
}
