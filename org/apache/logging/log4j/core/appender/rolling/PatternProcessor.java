package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.core.pattern.*;
import java.util.*;
import org.apache.logging.log4j.core.lookup.*;
import org.apache.logging.log4j.core.impl.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.*;

public class PatternProcessor
{
    private static final String KEY = "FileConverter";
    private static final char YEAR_CHAR = 'y';
    private static final char MONTH_CHAR = 'M';
    private static final char[] WEEK_CHARS;
    private static final char[] DAY_CHARS;
    private static final char[] HOUR_CHARS;
    private static final char MINUTE_CHAR = 'm';
    private static final char SECOND_CHAR = 's';
    private static final char MILLIS_CHAR = 'S';
    private final ArrayPatternConverter[] patternConverters;
    private final FormattingInfo[] patternFields;
    private long prevFileTime;
    private long nextFileTime;
    private RolloverFrequency frequency;
    
    public PatternProcessor(final String s) {
        this.prevFileTime = 0L;
        this.nextFileTime = 0L;
        this.frequency = null;
        final PatternParser patternParser = this.createPatternParser();
        final ArrayList list = new ArrayList();
        final ArrayList list2 = new ArrayList();
        patternParser.parse(s, list, list2);
        this.patternFields = (FormattingInfo[])list2.toArray(new FormattingInfo[list2.size()]);
        this.patternConverters = (ArrayPatternConverter[])list.toArray(new ArrayPatternConverter[list.size()]);
        final ArrayPatternConverter[] patternConverters = this.patternConverters;
        while (0 < patternConverters.length) {
            final ArrayPatternConverter arrayPatternConverter = patternConverters[0];
            if (arrayPatternConverter instanceof DatePatternConverter) {
                this.frequency = this.calculateFrequency(((DatePatternConverter)arrayPatternConverter).getPattern());
            }
            int n = 0;
            ++n;
        }
    }
    
    public long getNextTime(final long timeInMillis, final int n, final boolean b) {
        this.prevFileTime = this.nextFileTime;
        if (this.frequency == null) {
            throw new IllegalStateException("Pattern does not contain a date");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(timeInMillis);
        final Calendar instance2 = Calendar.getInstance();
        instance2.set(instance.get(1), 0, 1, 0, 0, 0);
        instance2.set(14, 0);
        if (this.frequency == RolloverFrequency.ANNUALLY) {
            this.increment(instance2, 1, n, b);
            final long timeInMillis2 = instance2.getTimeInMillis();
            instance2.add(1, -1);
            this.nextFileTime = instance2.getTimeInMillis();
            return timeInMillis2;
        }
        if (this.frequency == RolloverFrequency.MONTHLY) {
            this.increment(instance2, 2, n, b);
            final long timeInMillis3 = instance2.getTimeInMillis();
            instance2.add(2, -1);
            this.nextFileTime = instance2.getTimeInMillis();
            return timeInMillis3;
        }
        if (this.frequency == RolloverFrequency.WEEKLY) {
            this.increment(instance2, 3, n, b);
            final long timeInMillis4 = instance2.getTimeInMillis();
            instance2.add(3, -1);
            this.nextFileTime = instance2.getTimeInMillis();
            return timeInMillis4;
        }
        instance2.set(6, instance.get(6));
        if (this.frequency == RolloverFrequency.DAILY) {
            this.increment(instance2, 6, n, b);
            final long timeInMillis5 = instance2.getTimeInMillis();
            instance2.add(6, -1);
            this.nextFileTime = instance2.getTimeInMillis();
            return timeInMillis5;
        }
        instance2.set(10, instance.get(10));
        if (this.frequency == RolloverFrequency.HOURLY) {
            this.increment(instance2, 10, n, b);
            final long timeInMillis6 = instance2.getTimeInMillis();
            instance2.add(10, -1);
            this.nextFileTime = instance2.getTimeInMillis();
            return timeInMillis6;
        }
        instance2.set(12, instance.get(12));
        if (this.frequency == RolloverFrequency.EVERY_MINUTE) {
            this.increment(instance2, 12, n, b);
            final long timeInMillis7 = instance2.getTimeInMillis();
            instance2.add(12, -1);
            this.nextFileTime = instance2.getTimeInMillis();
            return timeInMillis7;
        }
        instance2.set(13, instance.get(13));
        if (this.frequency == RolloverFrequency.EVERY_SECOND) {
            this.increment(instance2, 13, n, b);
            final long timeInMillis8 = instance2.getTimeInMillis();
            instance2.add(13, -1);
            this.nextFileTime = instance2.getTimeInMillis();
            return timeInMillis8;
        }
        this.increment(instance2, 14, n, b);
        final long timeInMillis9 = instance2.getTimeInMillis();
        instance2.add(14, -1);
        this.nextFileTime = instance2.getTimeInMillis();
        return timeInMillis9;
    }
    
    private void increment(final Calendar calendar, final int n, final int n2, final boolean b) {
        calendar.add(n, b ? (n2 - calendar.get(n) % n2) : n2);
    }
    
    public final void formatFileName(final StringBuilder sb, final Object o) {
        this.formatFileName(sb, new Date((this.prevFileTime == 0L) ? System.currentTimeMillis() : this.prevFileTime), o);
    }
    
    public final void formatFileName(final StrSubstitutor strSubstitutor, final StringBuilder sb, final Object o) {
        final long n = (this.prevFileTime == 0L) ? System.currentTimeMillis() : this.prevFileTime;
        this.formatFileName(sb, new Date(n), o);
        final String replace = strSubstitutor.replace(new Log4jLogEvent(n), sb);
        sb.setLength(0);
        sb.append(replace);
    }
    
    protected final void formatFileName(final StringBuilder sb, final Object... array) {
        while (0 < this.patternConverters.length) {
            final int length = sb.length();
            this.patternConverters[0].format(sb, array);
            if (this.patternFields[0] != null) {
                this.patternFields[0].format(length, sb);
            }
            int n = 0;
            ++n;
        }
    }
    
    private RolloverFrequency calculateFrequency(final String s) {
        return RolloverFrequency.EVERY_MILLISECOND;
    }
    
    private PatternParser createPatternParser() {
        return new PatternParser(null, "FileConverter", null);
    }
    
    static {
        WEEK_CHARS = new char[] { 'w', 'W' };
        DAY_CHARS = new char[] { 'D', 'd', 'F', 'E' };
        HOUR_CHARS = new char[] { 'H', 'K', 'h', 'k' };
    }
}
