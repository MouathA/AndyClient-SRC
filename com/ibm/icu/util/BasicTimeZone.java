package com.ibm.icu.util;

import com.ibm.icu.impl.*;
import java.util.*;

public abstract class BasicTimeZone extends TimeZone
{
    private static final long serialVersionUID = -3204278532246180932L;
    private static final long MILLIS_PER_YEAR = 31536000000L;
    @Deprecated
    public static final int LOCAL_STD = 1;
    @Deprecated
    public static final int LOCAL_DST = 3;
    @Deprecated
    public static final int LOCAL_FORMER = 4;
    @Deprecated
    public static final int LOCAL_LATTER = 12;
    @Deprecated
    protected static final int STD_DST_MASK = 3;
    @Deprecated
    protected static final int FORMER_LATTER_MASK = 12;
    
    public abstract TimeZoneTransition getNextTransition(final long p0, final boolean p1);
    
    public abstract TimeZoneTransition getPreviousTransition(final long p0, final boolean p1);
    
    public boolean hasEquivalentTransitions(final TimeZone timeZone, final long n, final long n2) {
        return this.hasEquivalentTransitions(timeZone, n, n2, false);
    }
    
    public boolean hasEquivalentTransitions(final TimeZone timeZone, final long n, final long n2, final boolean b) {
        if (this == timeZone) {
            return true;
        }
        if (!(timeZone instanceof BasicTimeZone)) {
            return false;
        }
        final int[] array = new int[2];
        final int[] array2 = new int[2];
        this.getOffset(n, false, array);
        timeZone.getOffset(n, false, array2);
        if (b) {
            if (array[0] + array[1] != array2[0] + array2[1] || (array[1] != 0 && array2[1] == 0) || (array[1] == 0 && array2[1] != 0)) {
                return false;
            }
        }
        else if (array[0] != array2[0] || array[1] != array2[1]) {
            return false;
        }
        long time = n;
        while (true) {
            TimeZoneTransition timeZoneTransition = this.getNextTransition(time, false);
            TimeZoneTransition timeZoneTransition2 = ((BasicTimeZone)timeZone).getNextTransition(time, false);
            if (b) {
                while (timeZoneTransition != null && timeZoneTransition.getTime() <= n2 && timeZoneTransition.getFrom().getRawOffset() + timeZoneTransition.getFrom().getDSTSavings() == timeZoneTransition.getTo().getRawOffset() + timeZoneTransition.getTo().getDSTSavings() && timeZoneTransition.getFrom().getDSTSavings() != 0 && timeZoneTransition.getTo().getDSTSavings() != 0) {
                    timeZoneTransition = this.getNextTransition(timeZoneTransition.getTime(), false);
                }
                while (timeZoneTransition2 != null && timeZoneTransition2.getTime() <= n2 && timeZoneTransition2.getFrom().getRawOffset() + timeZoneTransition2.getFrom().getDSTSavings() == timeZoneTransition2.getTo().getRawOffset() + timeZoneTransition2.getTo().getDSTSavings() && timeZoneTransition2.getFrom().getDSTSavings() != 0 && timeZoneTransition2.getTo().getDSTSavings() != 0) {
                    timeZoneTransition2 = ((BasicTimeZone)timeZone).getNextTransition(timeZoneTransition2.getTime(), false);
                }
            }
            if (timeZoneTransition == null || timeZoneTransition.getTime() <= n2) {}
            if (timeZoneTransition2 == null || timeZoneTransition2.getTime() <= n2) {}
            if (!true && !true) {
                return true;
            }
            if (!true || !true) {
                return false;
            }
            if (timeZoneTransition.getTime() != timeZoneTransition2.getTime()) {
                return false;
            }
            if (b) {
                if (timeZoneTransition.getTo().getRawOffset() + timeZoneTransition.getTo().getDSTSavings() != timeZoneTransition2.getTo().getRawOffset() + timeZoneTransition2.getTo().getDSTSavings() || (timeZoneTransition.getTo().getDSTSavings() != 0 && timeZoneTransition2.getTo().getDSTSavings() == 0) || (timeZoneTransition.getTo().getDSTSavings() == 0 && timeZoneTransition2.getTo().getDSTSavings() != 0)) {
                    return false;
                }
            }
            else if (timeZoneTransition.getTo().getRawOffset() != timeZoneTransition2.getTo().getRawOffset() || timeZoneTransition.getTo().getDSTSavings() != timeZoneTransition2.getTo().getDSTSavings()) {
                return false;
            }
            time = timeZoneTransition.getTime();
        }
    }
    
    public abstract TimeZoneRule[] getTimeZoneRules();
    
    public TimeZoneRule[] getTimeZoneRules(final long n) {
        final TimeZoneRule[] timeZoneRules = this.getTimeZoneRules();
        final TimeZoneTransition previousTransition = this.getPreviousTransition(n, true);
        if (previousTransition == null) {
            return timeZoneRules;
        }
        final BitSet set = new BitSet(timeZoneRules.length);
        final LinkedList<InitialTimeZoneRule> list = new LinkedList<InitialTimeZoneRule>();
        final InitialTimeZoneRule initialTimeZoneRule = new InitialTimeZoneRule(previousTransition.getTo().getName(), previousTransition.getTo().getRawOffset(), previousTransition.getTo().getDSTSavings());
        list.add(initialTimeZoneRule);
        set.set(0);
        while (1 < timeZoneRules.length) {
            if (timeZoneRules[1].getNextStart(n, initialTimeZoneRule.getRawOffset(), initialTimeZoneRule.getDSTSavings(), false) == null) {
                set.set(1);
            }
            int n2 = 0;
            ++n2;
        }
        long time = n;
        while (!true || !true) {
            final TimeZoneTransition nextTransition = this.getNextTransition(time, false);
            if (nextTransition == null) {
                break;
            }
            time = nextTransition.getTime();
            final TimeZoneRule to = nextTransition.getTo();
            while (1 < timeZoneRules.length && !timeZoneRules[1].equals(to)) {
                int n3 = 0;
                ++n3;
            }
            if (1 >= timeZoneRules.length) {
                throw new IllegalStateException("The rule was not found");
            }
            if (set.get(1)) {
                continue;
            }
            if (to instanceof TimeArrayTimeZoneRule) {
                final TimeArrayTimeZoneRule timeArrayTimeZoneRule = (TimeArrayTimeZoneRule)to;
                long time2 = n;
                TimeZoneTransition nextTransition2;
                while (true) {
                    nextTransition2 = this.getNextTransition(time2, false);
                    if (nextTransition2 == null) {
                        break;
                    }
                    if (nextTransition2.getTo().equals(timeArrayTimeZoneRule)) {
                        break;
                    }
                    time2 = nextTransition2.getTime();
                }
                if (nextTransition2 != null) {
                    if (timeArrayTimeZoneRule.getFirstStart(nextTransition2.getFrom().getRawOffset(), nextTransition2.getFrom().getDSTSavings()).getTime() > n) {
                        list.add((InitialTimeZoneRule)timeArrayTimeZoneRule);
                    }
                    else {
                        final long[] startTimes = timeArrayTimeZoneRule.getStartTimes();
                        final int timeType = timeArrayTimeZoneRule.getTimeType();
                        while (0 < startTimes.length) {
                            long n4 = startTimes[0];
                            if (timeType == 1) {
                                n4 -= nextTransition2.getFrom().getRawOffset();
                            }
                            if (timeType == 0) {
                                n4 -= nextTransition2.getFrom().getDSTSavings();
                            }
                            if (n4 > n) {
                                break;
                            }
                            int n5 = 0;
                            ++n5;
                        }
                        final int n6 = startTimes.length - 0;
                        if (n6 > 0) {
                            final long[] array = new long[n6];
                            System.arraycopy(startTimes, 0, array, 0, n6);
                            list.add((InitialTimeZoneRule)new TimeArrayTimeZoneRule(timeArrayTimeZoneRule.getName(), timeArrayTimeZoneRule.getRawOffset(), timeArrayTimeZoneRule.getDSTSavings(), array, timeArrayTimeZoneRule.getTimeType()));
                        }
                    }
                }
            }
            else if (to instanceof AnnualTimeZoneRule) {
                final AnnualTimeZoneRule annualTimeZoneRule = (AnnualTimeZoneRule)to;
                if (annualTimeZoneRule.getFirstStart(nextTransition.getFrom().getRawOffset(), nextTransition.getFrom().getDSTSavings()).getTime() == nextTransition.getTime()) {
                    list.add((InitialTimeZoneRule)annualTimeZoneRule);
                }
                else {
                    final int[] array2 = new int[6];
                    Grego.timeToFields(nextTransition.getTime(), array2);
                    list.add((InitialTimeZoneRule)new AnnualTimeZoneRule(annualTimeZoneRule.getName(), annualTimeZoneRule.getRawOffset(), annualTimeZoneRule.getDSTSavings(), annualTimeZoneRule.getRule(), array2[0], annualTimeZoneRule.getEndYear()));
                }
                if (annualTimeZoneRule.getEndYear() != Integer.MAX_VALUE || annualTimeZoneRule.getDSTSavings() == 0) {}
            }
            set.set(1);
        }
        return list.toArray(new TimeZoneRule[list.size()]);
    }
    
    public TimeZoneRule[] getSimpleTimeZoneRulesNear(final long n) {
        AnnualTimeZoneRule[] array = null;
        final TimeZoneTransition nextTransition = this.getNextTransition(n, false);
        InitialTimeZoneRule initialTimeZoneRule;
        if (nextTransition != null) {
            String s = nextTransition.getFrom().getName();
            int n2 = nextTransition.getFrom().getRawOffset();
            int n3 = nextTransition.getFrom().getDSTSavings();
            final long time = nextTransition.getTime();
            if (((nextTransition.getFrom().getDSTSavings() == 0 && nextTransition.getTo().getDSTSavings() != 0) || (nextTransition.getFrom().getDSTSavings() != 0 && nextTransition.getTo().getDSTSavings() == 0)) && n + 31536000000L > time) {
                array = new AnnualTimeZoneRule[2];
                int[] array2 = Grego.timeToFields(time + nextTransition.getFrom().getRawOffset() + nextTransition.getFrom().getDSTSavings(), null);
                array[0] = new AnnualTimeZoneRule(nextTransition.getTo().getName(), n2, nextTransition.getTo().getDSTSavings(), new DateTimeRule(array2[1], Grego.getDayOfWeekInMonth(array2[0], array2[1], array2[2]), array2[3], array2[5], 0), array2[0], Integer.MAX_VALUE);
                if (nextTransition.getTo().getRawOffset() == n2) {
                    final TimeZoneTransition nextTransition2 = this.getNextTransition(time, false);
                    if (nextTransition2 != null && ((nextTransition2.getFrom().getDSTSavings() == 0 && nextTransition2.getTo().getDSTSavings() != 0) || (nextTransition2.getFrom().getDSTSavings() != 0 && nextTransition2.getTo().getDSTSavings() == 0)) && time + 31536000000L > nextTransition2.getTime()) {
                        array2 = Grego.timeToFields(nextTransition2.getTime() + nextTransition2.getFrom().getRawOffset() + nextTransition2.getFrom().getDSTSavings(), array2);
                        final AnnualTimeZoneRule annualTimeZoneRule = new AnnualTimeZoneRule(nextTransition2.getTo().getName(), nextTransition2.getTo().getRawOffset(), nextTransition2.getTo().getDSTSavings(), new DateTimeRule(array2[1], Grego.getDayOfWeekInMonth(array2[0], array2[1], array2[2]), array2[3], array2[5], 0), array2[0] - 1, Integer.MAX_VALUE);
                        final Date previousStart = annualTimeZoneRule.getPreviousStart(n, nextTransition2.getFrom().getRawOffset(), nextTransition2.getFrom().getDSTSavings(), true);
                        if (previousStart != null && previousStart.getTime() <= n && n2 == nextTransition2.getTo().getRawOffset() && n3 == nextTransition2.getTo().getDSTSavings()) {
                            array[1] = annualTimeZoneRule;
                        }
                    }
                }
                if (array[1] == null) {
                    final TimeZoneTransition previousTransition = this.getPreviousTransition(n, true);
                    if (previousTransition != null && ((previousTransition.getFrom().getDSTSavings() == 0 && previousTransition.getTo().getDSTSavings() != 0) || (previousTransition.getFrom().getDSTSavings() != 0 && previousTransition.getTo().getDSTSavings() == 0))) {
                        final int[] timeToFields = Grego.timeToFields(previousTransition.getTime() + previousTransition.getFrom().getRawOffset() + previousTransition.getFrom().getDSTSavings(), array2);
                        final AnnualTimeZoneRule annualTimeZoneRule2 = new AnnualTimeZoneRule(previousTransition.getTo().getName(), n2, n3, new DateTimeRule(timeToFields[1], Grego.getDayOfWeekInMonth(timeToFields[0], timeToFields[1], timeToFields[2]), timeToFields[3], timeToFields[5], 0), array[0].getStartYear() - 1, Integer.MAX_VALUE);
                        if (annualTimeZoneRule2.getNextStart(n, previousTransition.getFrom().getRawOffset(), previousTransition.getFrom().getDSTSavings(), false).getTime() > time) {
                            array[1] = annualTimeZoneRule2;
                        }
                    }
                }
                if (array[1] == null) {
                    array = null;
                }
                else {
                    s = array[0].getName();
                    n2 = array[0].getRawOffset();
                    n3 = array[0].getDSTSavings();
                }
            }
            initialTimeZoneRule = new InitialTimeZoneRule(s, n2, n3);
        }
        else {
            final TimeZoneTransition previousTransition2 = this.getPreviousTransition(n, true);
            if (previousTransition2 != null) {
                initialTimeZoneRule = new InitialTimeZoneRule(previousTransition2.getTo().getName(), previousTransition2.getTo().getRawOffset(), previousTransition2.getTo().getDSTSavings());
            }
            else {
                final int[] array3 = new int[2];
                this.getOffset(n, false, array3);
                initialTimeZoneRule = new InitialTimeZoneRule(this.getID(), array3[0], array3[1]);
            }
        }
        TimeZoneRule[] array4;
        if (array == null) {
            array4 = new TimeZoneRule[] { initialTimeZoneRule };
        }
        else {
            array4 = new TimeZoneRule[] { initialTimeZoneRule, array[0], array[1] };
        }
        return array4;
    }
    
    @Deprecated
    public void getOffsetFromLocal(final long n, final int n2, final int n3, final int[] array) {
        throw new IllegalStateException("Not implemented");
    }
    
    protected BasicTimeZone() {
    }
    
    @Deprecated
    protected BasicTimeZone(final String s) {
        super(s);
    }
}
