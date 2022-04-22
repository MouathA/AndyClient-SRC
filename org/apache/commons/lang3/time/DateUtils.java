package org.apache.commons.lang3.time;

import java.text.*;
import java.util.concurrent.*;
import java.util.*;

public class DateUtils
{
    public static final long MILLIS_PER_SECOND = 1000L;
    public static final long MILLIS_PER_MINUTE = 60000L;
    public static final long MILLIS_PER_HOUR = 3600000L;
    public static final long MILLIS_PER_DAY = 86400000L;
    public static final int SEMI_MONTH = 1001;
    private static final int[][] fields;
    public static final int RANGE_WEEK_SUNDAY = 1;
    public static final int RANGE_WEEK_MONDAY = 2;
    public static final int RANGE_WEEK_RELATIVE = 3;
    public static final int RANGE_WEEK_CENTER = 4;
    public static final int RANGE_MONTH_SUNDAY = 5;
    public static final int RANGE_MONTH_MONDAY = 6;
    private static final int MODIFY_TRUNCATE = 0;
    private static final int MODIFY_ROUND = 1;
    private static final int MODIFY_CEILING = 2;
    
    public static boolean isSameDay(final Date time, final Date time2) {
        if (time == null || time2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        final Calendar instance2 = Calendar.getInstance();
        instance2.setTime(time2);
        return isSameDay(instance, instance2);
    }
    
    public static boolean isSameDay(final Calendar calendar, final Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.get(0) == calendar2.get(0) && calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6);
    }
    
    public static boolean isSameInstant(final Date date, final Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date.getTime() == date2.getTime();
    }
    
    public static boolean isSameInstant(final Calendar calendar, final Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.getTime().getTime() == calendar2.getTime().getTime();
    }
    
    public static boolean isSameLocalTime(final Calendar calendar, final Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.get(14) == calendar2.get(14) && calendar.get(13) == calendar2.get(13) && calendar.get(12) == calendar2.get(12) && calendar.get(11) == calendar2.get(11) && calendar.get(6) == calendar2.get(6) && calendar.get(1) == calendar2.get(1) && calendar.get(0) == calendar2.get(0) && calendar.getClass() == calendar2.getClass();
    }
    
    public static Date parseDate(final String s, final String... array) throws ParseException {
        return parseDate(s, (Locale)null, array);
    }
    
    public static Date parseDate(final String s, final Locale locale, final String... array) throws ParseException {
        return parseDateWithLeniency(s, locale, array, true);
    }
    
    public static Date parseDateStrictly(final String s, final String... array) throws ParseException {
        return parseDateStrictly(s, (Locale)null, array);
    }
    
    public static Date parseDateStrictly(final String s, final Locale locale, final String... array) throws ParseException {
        return parseDateWithLeniency(s, null, array, false);
    }
    
    private static Date parseDateWithLeniency(final String s, final Locale locale, final String[] array, final boolean lenient) throws ParseException {
        if (s == null || array == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }
        SimpleDateFormat simpleDateFormat;
        if (locale == null) {
            simpleDateFormat = new SimpleDateFormat();
        }
        else {
            simpleDateFormat = new SimpleDateFormat("", locale);
        }
        simpleDateFormat.setLenient(lenient);
        final ParsePosition parsePosition = new ParsePosition(0);
        while (0 < array.length) {
            String substring;
            final String s2 = substring = array[0];
            if (s2.endsWith("ZZ")) {
                substring = substring.substring(0, substring.length() - 1);
            }
            simpleDateFormat.applyPattern(substring);
            parsePosition.setIndex(0);
            String replaceAll = s;
            if (s2.endsWith("ZZ")) {
                replaceAll = s.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2");
            }
            final Date parse = simpleDateFormat.parse(replaceAll, parsePosition);
            if (parse != null && parsePosition.getIndex() == replaceAll.length()) {
                return parse;
            }
            int n = 0;
            ++n;
        }
        throw new ParseException("Unable to parse the date: " + s, -1);
    }
    
    public static Date addYears(final Date date, final int n) {
        return add(date, 1, n);
    }
    
    public static Date addMonths(final Date date, final int n) {
        return add(date, 2, n);
    }
    
    public static Date addWeeks(final Date date, final int n) {
        return add(date, 3, n);
    }
    
    public static Date addDays(final Date date, final int n) {
        return add(date, 5, n);
    }
    
    public static Date addHours(final Date date, final int n) {
        return add(date, 11, n);
    }
    
    public static Date addMinutes(final Date date, final int n) {
        return add(date, 12, n);
    }
    
    public static Date addSeconds(final Date date, final int n) {
        return add(date, 13, n);
    }
    
    public static Date addMilliseconds(final Date date, final int n) {
        return add(date, 14, n);
    }
    
    private static Date add(final Date time, final int n, final int n2) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        instance.add(n, n2);
        return instance.getTime();
    }
    
    public static Date setYears(final Date date, final int n) {
        return set(date, 1, n);
    }
    
    public static Date setMonths(final Date date, final int n) {
        return set(date, 2, n);
    }
    
    public static Date setDays(final Date date, final int n) {
        return set(date, 5, n);
    }
    
    public static Date setHours(final Date date, final int n) {
        return set(date, 11, n);
    }
    
    public static Date setMinutes(final Date date, final int n) {
        return set(date, 12, n);
    }
    
    public static Date setSeconds(final Date date, final int n) {
        return set(date, 13, n);
    }
    
    public static Date setMilliseconds(final Date date, final int n) {
        return set(date, 14, n);
    }
    
    private static Date set(final Date time, final int n, final int n2) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setLenient(false);
        instance.setTime(time);
        instance.set(n, n2);
        return instance.getTime();
    }
    
    public static Calendar toCalendar(final Date time) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        return instance;
    }
    
    public static Date round(final Date time, final int n) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        modify(instance, n, 1);
        return instance.getTime();
    }
    
    public static Calendar round(final Calendar calendar, final int n) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar calendar2 = (Calendar)calendar.clone();
        modify(calendar2, n, 1);
        return calendar2;
    }
    
    public static Date round(final Object o, final int n) {
        if (o == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (o instanceof Date) {
            return round((Date)o, n);
        }
        if (o instanceof Calendar) {
            return round((Calendar)o, n).getTime();
        }
        throw new ClassCastException("Could not round " + o);
    }
    
    public static Date truncate(final Date time, final int n) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        modify(instance, n, 0);
        return instance.getTime();
    }
    
    public static Calendar truncate(final Calendar calendar, final int n) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar calendar2 = (Calendar)calendar.clone();
        modify(calendar2, n, 0);
        return calendar2;
    }
    
    public static Date truncate(final Object o, final int n) {
        if (o == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (o instanceof Date) {
            return truncate((Date)o, n);
        }
        if (o instanceof Calendar) {
            return truncate((Calendar)o, n).getTime();
        }
        throw new ClassCastException("Could not truncate " + o);
    }
    
    public static Date ceiling(final Date time, final int n) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        modify(instance, n, 2);
        return instance.getTime();
    }
    
    public static Calendar ceiling(final Calendar calendar, final int n) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar calendar2 = (Calendar)calendar.clone();
        modify(calendar2, n, 2);
        return calendar2;
    }
    
    public static Date ceiling(final Object o, final int n) {
        if (o == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (o instanceof Date) {
            return ceiling((Date)o, n);
        }
        if (o instanceof Calendar) {
            return ceiling((Calendar)o, n).getTime();
        }
        throw new ClassCastException("Could not find ceiling of for type: " + o.getClass());
    }
    
    private static void modify(final Calendar calendar, final int n, final int n2) {
        if (calendar.get(1) > 280000000) {
            throw new ArithmeticException("Calendar value too large for accurate calculations");
        }
        if (n == 14) {
            return;
        }
        final Date time = calendar.getTime();
        long time2 = time.getTime();
        final int value = calendar.get(14);
        if (0 == n2 || value < 500) {
            time2 -= value;
        }
        if (n == 13) {}
        final int value2 = calendar.get(13);
        if (!true && (0 == n2 || value2 < 30)) {
            time2 -= value2 * 1000L;
        }
        if (n == 12) {}
        final int value3 = calendar.get(12);
        if (!true && (0 == n2 || value3 < 30)) {
            time2 -= value3 * 60000L;
        }
        if (time.getTime() != time2) {
            time.setTime(time2);
            calendar.setTime(time);
        }
        final int[][] fields = DateUtils.fields;
        while (0 < fields.length) {
            final int[] array2;
            final int[] array = array2 = fields[0];
            final int length = array2.length;
            while (0 < 1) {
                if (array2[0] == n) {
                    if (n2 == 2 || (n2 == 1 && false)) {
                        if (n == 1001) {
                            if (calendar.get(5) == 1) {
                                calendar.add(5, 15);
                            }
                            else {
                                calendar.add(5, -15);
                                calendar.add(2, 1);
                            }
                        }
                        else if (n == 9) {
                            if (calendar.get(11) == 0) {
                                calendar.add(11, 12);
                            }
                            else {
                                calendar.add(11, -12);
                                calendar.add(5, 1);
                            }
                        }
                        else {
                            calendar.add(array[0], 1);
                        }
                    }
                    return;
                }
                int actualMinimum = 0;
                ++actualMinimum;
            }
            switch (n) {
                case 1001: {
                    if (array[0] == 5) {
                        int n3 = calendar.get(5) - 1;
                        if (0 >= 15) {
                            n3 -= 15;
                        }
                        final boolean b = 0 > 7;
                        break;
                    }
                    break;
                }
                case 9: {
                    if (array[0] == 11) {
                        int value4 = calendar.get(11);
                        if (0 >= 12) {
                            value4 -= 12;
                        }
                        final boolean b2 = 0 >= 6;
                        break;
                    }
                    break;
                }
            }
            if (!true) {
                final int actualMinimum = calendar.getActualMinimum(array[0]);
                final int actualMaximum = calendar.getActualMaximum(array[0]);
                final int n4 = calendar.get(array[0]) - 0;
                final boolean b3 = 0 > (actualMaximum - 0) / 2;
            }
            if (false) {
                calendar.set(array[0], calendar.get(array[0]) - 0);
            }
            int n5 = 0;
            ++n5;
        }
        throw new IllegalArgumentException("The field " + n + " is not supported");
    }
    
    public static Iterator iterator(final Date time, final int n) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        return iterator(instance, n);
    }
    
    public static Iterator iterator(final Calendar calendar, final int n) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar2 = null;
        Calendar truncate = null;
        int value = 0;
        int n2 = 0;
        Label_0213: {
            switch (n) {
                case 5:
                case 6: {
                    calendar2 = truncate(calendar, 2);
                    truncate = (Calendar)calendar2.clone();
                    truncate.add(2, 1);
                    truncate.add(5, -1);
                    if (n == 6) {
                        break;
                    }
                    break;
                }
                case 1:
                case 2:
                case 3:
                case 4: {
                    calendar2 = truncate(calendar, 5);
                    truncate = truncate(calendar, 5);
                    switch (n) {
                        case 1: {
                            break Label_0213;
                        }
                        case 2: {
                            break Label_0213;
                        }
                        case 3: {
                            value = calendar.get(7);
                            break Label_0213;
                        }
                        case 4: {
                            value = calendar.get(7) - 3;
                            n2 = calendar.get(7) + 3;
                            break Label_0213;
                        }
                        default: {
                            break Label_0213;
                        }
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException("The range style " + n + " is not valid.");
                }
            }
        }
        if (2 < 1) {
            value += 7;
        }
        if (2 > 7) {
            value -= 7;
        }
        if (1 < 1) {
            n2 += 7;
        }
        if (1 > 7) {
            n2 -= 7;
        }
        while (calendar2.get(7) != 2) {
            calendar2.add(5, -1);
        }
        while (truncate.get(7) != 1) {
            truncate.add(5, 1);
        }
        return new DateIterator(calendar2, truncate);
    }
    
    public static Iterator iterator(final Object o, final int n) {
        if (o == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (o instanceof Date) {
            return iterator((Date)o, n);
        }
        if (o instanceof Calendar) {
            return iterator((Calendar)o, n);
        }
        throw new ClassCastException("Could not iterate based on " + o);
    }
    
    public static long getFragmentInMilliseconds(final Date date, final int n) {
        return getFragment(date, n, TimeUnit.MILLISECONDS);
    }
    
    public static long getFragmentInSeconds(final Date date, final int n) {
        return getFragment(date, n, TimeUnit.SECONDS);
    }
    
    public static long getFragmentInMinutes(final Date date, final int n) {
        return getFragment(date, n, TimeUnit.MINUTES);
    }
    
    public static long getFragmentInHours(final Date date, final int n) {
        return getFragment(date, n, TimeUnit.HOURS);
    }
    
    public static long getFragmentInDays(final Date date, final int n) {
        return getFragment(date, n, TimeUnit.DAYS);
    }
    
    public static long getFragmentInMilliseconds(final Calendar calendar, final int n) {
        return getFragment(calendar, n, TimeUnit.MILLISECONDS);
    }
    
    public static long getFragmentInSeconds(final Calendar calendar, final int n) {
        return getFragment(calendar, n, TimeUnit.SECONDS);
    }
    
    public static long getFragmentInMinutes(final Calendar calendar, final int n) {
        return getFragment(calendar, n, TimeUnit.MINUTES);
    }
    
    public static long getFragmentInHours(final Calendar calendar, final int n) {
        return getFragment(calendar, n, TimeUnit.HOURS);
    }
    
    public static long getFragmentInDays(final Calendar calendar, final int n) {
        return getFragment(calendar, n, TimeUnit.DAYS);
    }
    
    private static long getFragment(final Date time, final int n, final TimeUnit timeUnit) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        return getFragment(instance, n, timeUnit);
    }
    
    private static long getFragment(final Calendar calendar, final int n, final TimeUnit timeUnit) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        long n2 = 0L;
        final int n3 = (timeUnit != TimeUnit.DAYS) ? 1 : 0;
        switch (n) {
            case 1: {
                n2 += timeUnit.convert(calendar.get(6) - n3, TimeUnit.DAYS);
                break;
            }
            case 2: {
                n2 += timeUnit.convert(calendar.get(5) - n3, TimeUnit.DAYS);
                break;
            }
        }
        switch (n) {
            case 1:
            case 2:
            case 5:
            case 6: {
                n2 += timeUnit.convert(calendar.get(11), TimeUnit.HOURS);
            }
            case 11: {
                n2 += timeUnit.convert(calendar.get(12), TimeUnit.MINUTES);
            }
            case 12: {
                n2 += timeUnit.convert(calendar.get(13), TimeUnit.SECONDS);
            }
            case 13: {
                n2 += timeUnit.convert(calendar.get(14), TimeUnit.MILLISECONDS);
                break;
            }
            case 14: {
                break;
            }
            default: {
                throw new IllegalArgumentException("The fragment " + n + " is not supported");
            }
        }
        return n2;
    }
    
    public static boolean truncatedEquals(final Calendar calendar, final Calendar calendar2, final int n) {
        return truncatedCompareTo(calendar, calendar2, n) == 0;
    }
    
    public static boolean truncatedEquals(final Date date, final Date date2, final int n) {
        return truncatedCompareTo(date, date2, n) == 0;
    }
    
    public static int truncatedCompareTo(final Calendar calendar, final Calendar calendar2, final int n) {
        return truncate(calendar, n).compareTo(truncate(calendar2, n));
    }
    
    public static int truncatedCompareTo(final Date date, final Date date2, final int n) {
        return truncate(date, n).compareTo(truncate(date2, n));
    }
    
    static {
        fields = new int[][] { { 14 }, { 13 }, { 12 }, { 11, 10 }, { 5, 5, 9 }, { 2, 1001 }, { 1 }, { 0 } };
    }
    
    static class DateIterator implements Iterator
    {
        private final Calendar endFinal;
        private final Calendar spot;
        
        DateIterator(final Calendar spot, final Calendar endFinal) {
            this.endFinal = endFinal;
            (this.spot = spot).add(5, -1);
        }
        
        @Override
        public boolean hasNext() {
            return this.spot.before(this.endFinal);
        }
        
        @Override
        public Calendar next() {
            if (this.spot.equals(this.endFinal)) {
                throw new NoSuchElementException();
            }
            this.spot.add(5, 1);
            return (Calendar)this.spot.clone();
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object next() {
            return this.next();
        }
    }
}
