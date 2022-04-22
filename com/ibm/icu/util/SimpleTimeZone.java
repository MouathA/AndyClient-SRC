package com.ibm.icu.util;

import java.io.*;
import com.ibm.icu.impl.*;
import java.util.*;

public class SimpleTimeZone extends BasicTimeZone
{
    private static final long serialVersionUID = -7034676239311322769L;
    public static final int WALL_TIME = 0;
    public static final int STANDARD_TIME = 1;
    public static final int UTC_TIME = 2;
    private static final byte[] staticMonthLength;
    private static final int DOM_MODE = 1;
    private static final int DOW_IN_MONTH_MODE = 2;
    private static final int DOW_GE_DOM_MODE = 3;
    private static final int DOW_LE_DOM_MODE = 4;
    private int raw;
    private int dst;
    private STZInfo xinfo;
    private int startMonth;
    private int startDay;
    private int startDayOfWeek;
    private int startTime;
    private int startTimeMode;
    private int endTimeMode;
    private int endMonth;
    private int endDay;
    private int endDayOfWeek;
    private int endTime;
    private int startYear;
    private boolean useDaylight;
    private int startMode;
    private int endMode;
    private transient boolean transitionRulesInitialized;
    private transient InitialTimeZoneRule initialRule;
    private transient TimeZoneTransition firstTransition;
    private transient AnnualTimeZoneRule stdRule;
    private transient AnnualTimeZoneRule dstRule;
    private transient boolean isFrozen;
    static final boolean $assertionsDisabled;
    
    public SimpleTimeZone(final int n, final String s) {
        super(s);
        this.dst = 3600000;
        this.xinfo = null;
        this.isFrozen = false;
        this.construct(n, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3600000);
    }
    
    public SimpleTimeZone(final int n, final String s, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        super(s);
        this.dst = 3600000;
        this.xinfo = null;
        this.isFrozen = false;
        this.construct(n, n2, n3, n4, n5, 0, n6, n7, n8, n9, 0, 3600000);
    }
    
    public SimpleTimeZone(final int n, final String s, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int n12) {
        super(s);
        this.dst = 3600000;
        this.xinfo = null;
        this.isFrozen = false;
        this.construct(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12);
    }
    
    public SimpleTimeZone(final int n, final String s, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        super(s);
        this.dst = 3600000;
        this.xinfo = null;
        this.isFrozen = false;
        this.construct(n, n2, n3, n4, n5, 0, n6, n7, n8, n9, 0, n10);
    }
    
    @Override
    public void setID(final String id) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        super.setID(id);
        this.transitionRulesInitialized = false;
    }
    
    @Override
    public void setRawOffset(final int raw) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.raw = raw;
        this.transitionRulesInitialized = false;
    }
    
    @Override
    public int getRawOffset() {
        return this.raw;
    }
    
    public void setStartYear(final int n) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().sy = n;
        this.startYear = n;
        this.transitionRulesInitialized = false;
    }
    
    public void setStartRule(final int n, final int n2, final int n3, final int n4) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().setStart(n, n2, n3, n4, -1, false);
        this.setStartRule(n, n2, n3, n4, 0);
    }
    
    private void setStartRule(final int startMonth, final int startDay, final int startDayOfWeek, final int startTime, final int startTimeMode) {
        assert !this.isFrozen();
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.startDayOfWeek = startDayOfWeek;
        this.startTime = startTime;
        this.startTimeMode = startTimeMode;
        this.decodeStartRule();
        this.transitionRulesInitialized = false;
    }
    
    public void setStartRule(final int n, final int n2, final int n3) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().setStart(n, -1, -1, n3, n2, false);
        this.setStartRule(n, n2, 0, n3, 0);
    }
    
    public void setStartRule(final int n, final int n2, final int n3, final int n4, final boolean b) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().setStart(n, -1, n3, n4, n2, b);
        this.setStartRule(n, b ? n2 : (-n2), -n3, n4, 0);
    }
    
    public void setEndRule(final int n, final int n2, final int n3, final int n4) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().setEnd(n, n2, n3, n4, -1, false);
        this.setEndRule(n, n2, n3, n4, 0);
    }
    
    public void setEndRule(final int n, final int n2, final int n3) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().setEnd(n, -1, -1, n3, n2, false);
        this.setEndRule(n, n2, 0, n3);
    }
    
    public void setEndRule(final int n, final int n2, final int n3, final int n4, final boolean b) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().setEnd(n, -1, n3, n4, n2, b);
        this.setEndRule(n, n2, n3, n4, 0, b);
    }
    
    private void setEndRule(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        assert !this.isFrozen();
        this.setEndRule(n, b ? n2 : (-n2), -n3, n4, n5);
    }
    
    private void setEndRule(final int endMonth, final int endDay, final int endDayOfWeek, final int endTime, final int endTimeMode) {
        assert !this.isFrozen();
        this.endMonth = endMonth;
        this.endDay = endDay;
        this.endDayOfWeek = endDayOfWeek;
        this.endTime = endTime;
        this.endTimeMode = endTimeMode;
        this.decodeEndRule();
        this.transitionRulesInitialized = false;
    }
    
    public void setDSTSavings(final int dst) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        if (dst <= 0) {
            throw new IllegalArgumentException();
        }
        this.dst = dst;
        this.transitionRulesInitialized = false;
    }
    
    @Override
    public int getDSTSavings() {
        return this.dst;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.xinfo != null) {
            this.xinfo.applyTo(this);
        }
    }
    
    @Override
    public String toString() {
        return "SimpleTimeZone: " + this.getID();
    }
    
    private STZInfo getSTZInfo() {
        if (this.xinfo == null) {
            this.xinfo = new STZInfo();
        }
        return this.xinfo;
    }
    
    @Override
    public int getOffset(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n3 < 0 || n3 > 11) {
            throw new IllegalArgumentException();
        }
        return this.getOffset(n, n2, n3, n4, n5, n6, Grego.monthLength(n2, n3));
    }
    
    @Deprecated
    public int getOffset(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        if (n3 < 0 || n3 > 11) {
            throw new IllegalArgumentException();
        }
        return this.getOffset(n, n2, n3, n4, n5, n6, Grego.monthLength(n2, n3), Grego.previousMonthLength(n2, n3));
    }
    
    private int getOffset(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        if ((n != 1 && n != 0) || n3 < 0 || n3 > 11 || n4 < 1 || n4 > n7 || n5 < 1 || n5 > 7 || n6 < 0 || n6 >= 86400000 || n7 < 28 || n7 > 31 || n8 < 28 || n8 > 31) {
            throw new IllegalArgumentException();
        }
        int raw = this.raw;
        if (!this.useDaylight || n2 < this.startYear || n != 1) {
            return raw;
        }
        final boolean b = this.startMonth > this.endMonth;
        final int compareToRule = this.compareToRule(n3, n7, n8, n4, n5, n6, (this.startTimeMode == 2) ? (-this.raw) : 0, this.startMode, this.startMonth, this.startDayOfWeek, this.startDay, this.startTime);
        if (b != compareToRule >= 0) {
            this.compareToRule(n3, n7, n8, n4, n5, n6, (this.endTimeMode == 0) ? this.dst : ((this.endTimeMode == 2) ? (-this.raw) : 0), this.endMode, this.endMonth, this.endDayOfWeek, this.endDay, this.endTime);
        }
        if ((!b && compareToRule >= 0 && 0 < 0) || (b && (compareToRule >= 0 || 0 < 0))) {
            raw += this.dst;
        }
        return raw;
    }
    
    @Override
    @Deprecated
    public void getOffsetFromLocal(long n, final int n2, final int n3, final int[] array) {
        array[0] = this.getRawOffset();
        final int[] array2 = new int[6];
        Grego.timeToFields(n, array2);
        array[1] = this.getOffset(1, array2[0], array2[1], array2[2], array2[3], array2[5]) - array[0];
        if (array[1] > 0) {
            if ((n2 & 0x3) == 0x1 || ((n2 & 0x3) != 0x3 && (n2 & 0xC) != 0xC)) {
                n -= this.getDSTSavings();
            }
        }
        else if ((n3 & 0x3) == 0x3 || ((n3 & 0x3) != 0x1 && (n3 & 0xC) == 0x4)) {
            n -= this.getDSTSavings();
        }
        if (true) {
            Grego.timeToFields(n, array2);
            array[1] = this.getOffset(1, array2[0], array2[1], array2[2], array2[3], array2[5]) - array[0];
        }
    }
    
    private int compareToRule(int n, final int n2, final int n3, int n4, int n5, int i, final int n6, final int n7, final int n8, final int n9, int n10, final int n11) {
        i += n6;
        while (i >= 86400000) {
            i -= 86400000;
            ++n4;
            n5 = 1 + n5 % 7;
            if (1 > n2) {
                ++n;
            }
        }
        while (i < 0) {
            --n4;
            n5 = 1 + (n5 + 5) % 7;
            if (1 < 1) {
                n4 = n3;
                --n;
            }
            i += 86400000;
        }
        if (n < n8) {
            return -1;
        }
        if (n > n8) {
            return 1;
        }
        if (n10 > n2) {
            n10 = n2;
        }
        switch (n7) {
            case 2: {
                if (n10 > 0) {
                    break;
                }
                break;
            }
            case 3: {}
        }
        if (1 < 0) {
            return -1;
        }
        if (1 > 0) {
            return 1;
        }
        if (i < n11) {
            return -1;
        }
        if (i > n11) {
            return 1;
        }
        return 0;
    }
    
    @Override
    public boolean useDaylightTime() {
        return this.useDaylight;
    }
    
    @Override
    public boolean observesDaylightTime() {
        return this.useDaylight;
    }
    
    @Override
    public boolean inDaylightTime(final Date time) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar(this);
        gregorianCalendar.setTime(time);
        return gregorianCalendar.inDaylightTime();
    }
    
    private void construct(final int raw, final int startMonth, final int startDay, final int startDayOfWeek, final int startTime, final int startTimeMode, final int endMonth, final int endDay, final int endDayOfWeek, final int endTime, final int endTimeMode, final int dst) {
        this.raw = raw;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.startDayOfWeek = startDayOfWeek;
        this.startTime = startTime;
        this.startTimeMode = startTimeMode;
        this.endMonth = endMonth;
        this.endDay = endDay;
        this.endDayOfWeek = endDayOfWeek;
        this.endTime = endTime;
        this.endTimeMode = endTimeMode;
        this.dst = dst;
        this.startYear = 0;
        this.startMode = 1;
        this.endMode = 1;
        this.decodeRules();
        if (dst <= 0) {
            throw new IllegalArgumentException();
        }
    }
    
    private void decodeRules() {
        this.decodeStartRule();
        this.decodeEndRule();
    }
    
    private void decodeStartRule() {
        this.useDaylight = (this.startDay != 0 && this.endDay != 0);
        if (this.useDaylight && this.dst == 0) {
            this.dst = 86400000;
        }
        if (this.startDay != 0) {
            if (this.startMonth < 0 || this.startMonth > 11) {
                throw new IllegalArgumentException();
            }
            if (this.startTime < 0 || this.startTime > 86400000 || this.startTimeMode < 0 || this.startTimeMode > 2) {
                throw new IllegalArgumentException();
            }
            if (this.startDayOfWeek == 0) {
                this.startMode = 1;
            }
            else {
                if (this.startDayOfWeek > 0) {
                    this.startMode = 2;
                }
                else {
                    this.startDayOfWeek = -this.startDayOfWeek;
                    if (this.startDay > 0) {
                        this.startMode = 3;
                    }
                    else {
                        this.startDay = -this.startDay;
                        this.startMode = 4;
                    }
                }
                if (this.startDayOfWeek > 7) {
                    throw new IllegalArgumentException();
                }
            }
            if (this.startMode == 2) {
                if (this.startDay < -5 || this.startDay > 5) {
                    throw new IllegalArgumentException();
                }
            }
            else if (this.startDay < 1 || this.startDay > SimpleTimeZone.staticMonthLength[this.startMonth]) {
                throw new IllegalArgumentException();
            }
        }
    }
    
    private void decodeEndRule() {
        this.useDaylight = (this.startDay != 0 && this.endDay != 0);
        if (this.useDaylight && this.dst == 0) {
            this.dst = 86400000;
        }
        if (this.endDay != 0) {
            if (this.endMonth < 0 || this.endMonth > 11) {
                throw new IllegalArgumentException();
            }
            if (this.endTime < 0 || this.endTime > 86400000 || this.endTimeMode < 0 || this.endTimeMode > 2) {
                throw new IllegalArgumentException();
            }
            if (this.endDayOfWeek == 0) {
                this.endMode = 1;
            }
            else {
                if (this.endDayOfWeek > 0) {
                    this.endMode = 2;
                }
                else {
                    this.endDayOfWeek = -this.endDayOfWeek;
                    if (this.endDay > 0) {
                        this.endMode = 3;
                    }
                    else {
                        this.endDay = -this.endDay;
                        this.endMode = 4;
                    }
                }
                if (this.endDayOfWeek > 7) {
                    throw new IllegalArgumentException();
                }
            }
            if (this.endMode == 2) {
                if (this.endDay < -5 || this.endDay > 5) {
                    throw new IllegalArgumentException();
                }
            }
            else if (this.endDay < 1 || this.endDay > SimpleTimeZone.staticMonthLength[this.endMonth]) {
                throw new IllegalArgumentException();
            }
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final SimpleTimeZone simpleTimeZone = (SimpleTimeZone)o;
        return this.raw == simpleTimeZone.raw && this.useDaylight == simpleTimeZone.useDaylight && this.idEquals(this.getID(), simpleTimeZone.getID()) && (!this.useDaylight || (this.dst == simpleTimeZone.dst && this.startMode == simpleTimeZone.startMode && this.startMonth == simpleTimeZone.startMonth && this.startDay == simpleTimeZone.startDay && this.startDayOfWeek == simpleTimeZone.startDayOfWeek && this.startTime == simpleTimeZone.startTime && this.startTimeMode == simpleTimeZone.startTimeMode && this.endMode == simpleTimeZone.endMode && this.endMonth == simpleTimeZone.endMonth && this.endDay == simpleTimeZone.endDay && this.endDayOfWeek == simpleTimeZone.endDayOfWeek && this.endTime == simpleTimeZone.endTime && this.endTimeMode == simpleTimeZone.endTimeMode && this.startYear == simpleTimeZone.startYear));
    }
    
    private boolean idEquals(final String s, final String s2) {
        return (s == null && s2 == null) || (s != null && s2 != null && s.equals(s2));
    }
    
    @Override
    public int hashCode() {
        int n = super.hashCode() + this.raw ^ (this.raw >>> 8) + (this.useDaylight ? 0 : 1);
        if (!this.useDaylight) {
            n += (this.dst ^ (this.dst >>> 10) + this.startMode ^ (this.startMode >>> 11) + this.startMonth ^ (this.startMonth >>> 12) + this.startDay ^ (this.startDay >>> 13) + this.startDayOfWeek ^ (this.startDayOfWeek >>> 14) + this.startTime ^ (this.startTime >>> 15) + this.startTimeMode ^ (this.startTimeMode >>> 16) + this.endMode ^ (this.endMode >>> 17) + this.endMonth ^ (this.endMonth >>> 18) + this.endDay ^ (this.endDay >>> 19) + this.endDayOfWeek ^ (this.endDayOfWeek >>> 20) + this.endTime ^ (this.endTime >>> 21) + this.endTimeMode ^ (this.endTimeMode >>> 22) + this.startYear ^ this.startYear >>> 23);
        }
        return n;
    }
    
    @Override
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }
    
    @Override
    public boolean hasSameRules(final TimeZone timeZone) {
        if (this == timeZone) {
            return true;
        }
        if (!(timeZone instanceof SimpleTimeZone)) {
            return false;
        }
        final SimpleTimeZone simpleTimeZone = (SimpleTimeZone)timeZone;
        return simpleTimeZone != null && this.raw == simpleTimeZone.raw && this.useDaylight == simpleTimeZone.useDaylight && (!this.useDaylight || (this.dst == simpleTimeZone.dst && this.startMode == simpleTimeZone.startMode && this.startMonth == simpleTimeZone.startMonth && this.startDay == simpleTimeZone.startDay && this.startDayOfWeek == simpleTimeZone.startDayOfWeek && this.startTime == simpleTimeZone.startTime && this.startTimeMode == simpleTimeZone.startTimeMode && this.endMode == simpleTimeZone.endMode && this.endMonth == simpleTimeZone.endMonth && this.endDay == simpleTimeZone.endDay && this.endDayOfWeek == simpleTimeZone.endDayOfWeek && this.endTime == simpleTimeZone.endTime && this.endTimeMode == simpleTimeZone.endTimeMode && this.startYear == simpleTimeZone.startYear));
    }
    
    @Override
    public TimeZoneTransition getNextTransition(final long n, final boolean b) {
        if (!this.useDaylight) {
            return null;
        }
        this.initTransitionRules();
        final long time = this.firstTransition.getTime();
        if (n < time || (b && n == time)) {
            return this.firstTransition;
        }
        final Date nextStart = this.stdRule.getNextStart(n, this.dstRule.getRawOffset(), this.dstRule.getDSTSavings(), b);
        final Date nextStart2 = this.dstRule.getNextStart(n, this.stdRule.getRawOffset(), this.stdRule.getDSTSavings(), b);
        if (nextStart != null && (nextStart2 == null || nextStart.before(nextStart2))) {
            return new TimeZoneTransition(nextStart.getTime(), this.dstRule, this.stdRule);
        }
        if (nextStart2 != null && (nextStart == null || nextStart2.before(nextStart))) {
            return new TimeZoneTransition(nextStart2.getTime(), this.stdRule, this.dstRule);
        }
        return null;
    }
    
    @Override
    public TimeZoneTransition getPreviousTransition(final long n, final boolean b) {
        if (!this.useDaylight) {
            return null;
        }
        this.initTransitionRules();
        final long time = this.firstTransition.getTime();
        if (n < time || (!b && n == time)) {
            return null;
        }
        final Date previousStart = this.stdRule.getPreviousStart(n, this.dstRule.getRawOffset(), this.dstRule.getDSTSavings(), b);
        final Date previousStart2 = this.dstRule.getPreviousStart(n, this.stdRule.getRawOffset(), this.stdRule.getDSTSavings(), b);
        if (previousStart != null && (previousStart2 == null || previousStart.after(previousStart2))) {
            return new TimeZoneTransition(previousStart.getTime(), this.dstRule, this.stdRule);
        }
        if (previousStart2 != null && (previousStart == null || previousStart2.after(previousStart))) {
            return new TimeZoneTransition(previousStart2.getTime(), this.stdRule, this.dstRule);
        }
        return null;
    }
    
    @Override
    public TimeZoneRule[] getTimeZoneRules() {
        this.initTransitionRules();
        final TimeZoneRule[] array = new TimeZoneRule[this.useDaylight ? 3 : 1];
        array[0] = this.initialRule;
        if (this.useDaylight) {
            array[1] = this.stdRule;
            array[2] = this.dstRule;
        }
        return array;
    }
    
    private synchronized void initTransitionRules() {
        if (this.transitionRulesInitialized) {
            return;
        }
        if (this.useDaylight) {
            DateTimeRule dateTimeRule = null;
            final int n = (this.startTimeMode == 1) ? 1 : ((this.startTimeMode == 2) ? 2 : 0);
            switch (this.startMode) {
                case 1: {
                    dateTimeRule = new DateTimeRule(this.startMonth, this.startDay, this.startTime, n);
                    break;
                }
                case 2: {
                    dateTimeRule = new DateTimeRule(this.startMonth, this.startDay, this.startDayOfWeek, this.startTime, n);
                    break;
                }
                case 3: {
                    dateTimeRule = new DateTimeRule(this.startMonth, this.startDay, this.startDayOfWeek, true, this.startTime, n);
                    break;
                }
                case 4: {
                    dateTimeRule = new DateTimeRule(this.startMonth, this.startDay, this.startDayOfWeek, false, this.startTime, n);
                    break;
                }
            }
            this.dstRule = new AnnualTimeZoneRule(this.getID() + "(DST)", this.getRawOffset(), this.getDSTSavings(), dateTimeRule, this.startYear, Integer.MAX_VALUE);
            final long time = this.dstRule.getFirstStart(this.getRawOffset(), 0).getTime();
            final int n2 = (this.endTimeMode == 1) ? 1 : ((this.endTimeMode == 2) ? 2 : 0);
            switch (this.endMode) {
                case 1: {
                    dateTimeRule = new DateTimeRule(this.endMonth, this.endDay, this.endTime, n2);
                    break;
                }
                case 2: {
                    dateTimeRule = new DateTimeRule(this.endMonth, this.endDay, this.endDayOfWeek, this.endTime, n2);
                    break;
                }
                case 3: {
                    dateTimeRule = new DateTimeRule(this.endMonth, this.endDay, this.endDayOfWeek, true, this.endTime, n2);
                    break;
                }
                case 4: {
                    dateTimeRule = new DateTimeRule(this.endMonth, this.endDay, this.endDayOfWeek, false, this.endTime, n2);
                    break;
                }
            }
            this.stdRule = new AnnualTimeZoneRule(this.getID() + "(STD)", this.getRawOffset(), 0, dateTimeRule, this.startYear, Integer.MAX_VALUE);
            final long time2 = this.stdRule.getFirstStart(this.getRawOffset(), this.dstRule.getDSTSavings()).getTime();
            if (time2 < time) {
                this.initialRule = new InitialTimeZoneRule(this.getID() + "(DST)", this.getRawOffset(), this.dstRule.getDSTSavings());
                this.firstTransition = new TimeZoneTransition(time2, this.initialRule, this.stdRule);
            }
            else {
                this.initialRule = new InitialTimeZoneRule(this.getID() + "(STD)", this.getRawOffset(), 0);
                this.firstTransition = new TimeZoneTransition(time, this.initialRule, this.dstRule);
            }
        }
        else {
            this.initialRule = new InitialTimeZoneRule(this.getID(), this.getRawOffset(), 0);
        }
        this.transitionRulesInitialized = true;
    }
    
    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }
    
    @Override
    public TimeZone freeze() {
        this.isFrozen = true;
        return this;
    }
    
    @Override
    public TimeZone cloneAsThawed() {
        final SimpleTimeZone simpleTimeZone = (SimpleTimeZone)super.cloneAsThawed();
        simpleTimeZone.isFrozen = false;
        return simpleTimeZone;
    }
    
    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    @Override
    public Object freeze() {
        return this.freeze();
    }
    
    static {
        $assertionsDisabled = !SimpleTimeZone.class.desiredAssertionStatus();
        staticMonthLength = new byte[] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    }
}
