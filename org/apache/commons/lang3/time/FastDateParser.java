package org.apache.commons.lang3.time;

import java.util.regex.*;
import java.io.*;
import java.util.concurrent.*;
import java.text.*;
import java.util.*;

public class FastDateParser implements DateParser, Serializable
{
    private static final long serialVersionUID = 2L;
    static final Locale JAPANESE_IMPERIAL;
    private final String pattern;
    private final TimeZone timeZone;
    private final Locale locale;
    private final int century;
    private final int startYear;
    private transient Pattern parsePattern;
    private transient Strategy[] strategies;
    private transient String currentFormatField;
    private transient Strategy nextStrategy;
    private static final Pattern formatPattern;
    private static final ConcurrentMap[] caches;
    private static final Strategy ABBREVIATED_YEAR_STRATEGY;
    private static final Strategy NUMBER_MONTH_STRATEGY;
    private static final Strategy LITERAL_YEAR_STRATEGY;
    private static final Strategy WEEK_OF_YEAR_STRATEGY;
    private static final Strategy WEEK_OF_MONTH_STRATEGY;
    private static final Strategy DAY_OF_YEAR_STRATEGY;
    private static final Strategy DAY_OF_MONTH_STRATEGY;
    private static final Strategy DAY_OF_WEEK_IN_MONTH_STRATEGY;
    private static final Strategy HOUR_OF_DAY_STRATEGY;
    private static final Strategy MODULO_HOUR_OF_DAY_STRATEGY;
    private static final Strategy MODULO_HOUR_STRATEGY;
    private static final Strategy HOUR_STRATEGY;
    private static final Strategy MINUTE_STRATEGY;
    private static final Strategy SECOND_STRATEGY;
    private static final Strategy MILLISECOND_STRATEGY;
    
    protected FastDateParser(final String s, final TimeZone timeZone, final Locale locale) {
        this(s, timeZone, locale, null);
    }
    
    protected FastDateParser(final String pattern, final TimeZone timeZone, final Locale locale, final Date time) {
        this.pattern = pattern;
        this.timeZone = timeZone;
        this.locale = locale;
        final Calendar instance = Calendar.getInstance(timeZone, locale);
        if (time != null) {
            instance.setTime(time);
            instance.get(1);
        }
        else if (!locale.equals(FastDateParser.JAPANESE_IMPERIAL)) {
            instance.setTime(new Date());
            final int n = instance.get(1) - 80;
        }
        this.century = 0;
        this.startYear = 0 - this.century;
        this.init(instance);
    }
    
    private void init(final Calendar calendar) {
        final StringBuilder sb = new StringBuilder();
        final ArrayList<Strategy> list = new ArrayList<Strategy>();
        final Matcher matcher = FastDateParser.formatPattern.matcher(this.pattern);
        if (!matcher.lookingAt()) {
            throw new IllegalArgumentException("Illegal pattern character '" + this.pattern.charAt(matcher.regionStart()) + "'");
        }
        this.currentFormatField = matcher.group();
        Strategy strategy = this.getStrategy(this.currentFormatField, calendar);
        while (true) {
            matcher.region(matcher.end(), matcher.regionEnd());
            if (!matcher.lookingAt()) {
                break;
            }
            final String group = matcher.group();
            this.nextStrategy = this.getStrategy(group, calendar);
            if (strategy.addRegex(this, sb)) {
                list.add(strategy);
            }
            this.currentFormatField = group;
            strategy = this.nextStrategy;
        }
        this.nextStrategy = null;
        if (matcher.regionStart() != matcher.regionEnd()) {
            throw new IllegalArgumentException("Failed to parse \"" + this.pattern + "\" ; gave up at index " + matcher.regionStart());
        }
        if (strategy.addRegex(this, sb)) {
            list.add(strategy);
        }
        this.currentFormatField = null;
        this.strategies = list.toArray(new Strategy[list.size()]);
        this.parsePattern = Pattern.compile(sb.toString());
    }
    
    @Override
    public String getPattern() {
        return this.pattern;
    }
    
    @Override
    public TimeZone getTimeZone() {
        return this.timeZone;
    }
    
    @Override
    public Locale getLocale() {
        return this.locale;
    }
    
    Pattern getParsePattern() {
        return this.parsePattern;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof FastDateParser)) {
            return false;
        }
        final FastDateParser fastDateParser = (FastDateParser)o;
        return this.pattern.equals(fastDateParser.pattern) && this.timeZone.equals(fastDateParser.timeZone) && this.locale.equals(fastDateParser.locale);
    }
    
    @Override
    public int hashCode() {
        return this.pattern.hashCode() + 13 * (this.timeZone.hashCode() + 13 * this.locale.hashCode());
    }
    
    @Override
    public String toString() {
        return "FastDateParser[" + this.pattern + "," + this.locale + "," + this.timeZone.getID() + "]";
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.init(Calendar.getInstance(this.timeZone, this.locale));
    }
    
    @Override
    public Object parseObject(final String s) throws ParseException {
        return this.parse(s);
    }
    
    @Override
    public Date parse(final String s) throws ParseException {
        final Date parse = this.parse(s, new ParsePosition(0));
        if (parse != null) {
            return parse;
        }
        if (this.locale.equals(FastDateParser.JAPANESE_IMPERIAL)) {
            throw new ParseException("(The " + this.locale + " locale does not support dates before 1868 AD)\n" + "Unparseable date: \"" + s + "\" does not match " + this.parsePattern.pattern(), 0);
        }
        throw new ParseException("Unparseable date: \"" + s + "\" does not match " + this.parsePattern.pattern(), 0);
    }
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        return this.parse(s, parsePosition);
    }
    
    @Override
    public Date parse(final String s, final ParsePosition parsePosition) {
        final int index = parsePosition.getIndex();
        final Matcher matcher = this.parsePattern.matcher(s.substring(index));
        if (!matcher.lookingAt()) {
            return null;
        }
        final Calendar instance = Calendar.getInstance(this.timeZone, this.locale);
        instance.clear();
        while (0 < this.strategies.length) {
            final Strategy[] strategies = this.strategies;
            final int n = 0;
            int n2 = 0;
            ++n2;
            strategies[n].setCalendar(this, instance, matcher.group(0));
        }
        parsePosition.setIndex(index + matcher.end());
        return instance.getTime();
    }
    
    private static StringBuilder escapeRegex(final StringBuilder sb, final String s, final boolean b) {
        sb.append("\\Q");
        while (0 < s.length()) {
            s.charAt(0);
            int n = 0;
            switch (81) {
                case 39: {
                    if (!b) {
                        break;
                    }
                    ++n;
                    if (0 == s.length()) {
                        return sb;
                    }
                    s.charAt(0);
                    break;
                }
                case 92: {
                    ++n;
                    if (0 == s.length()) {
                        break;
                    }
                    sb.append('Q');
                    s.charAt(0);
                    if (81 == 69) {
                        sb.append("E\\\\E\\");
                        break;
                    }
                    break;
                }
            }
            sb.append('Q');
            ++n;
        }
        sb.append("\\E");
        return sb;
    }
    
    private static Map getDisplayNames(final int n, final Calendar calendar, final Locale locale) {
        return calendar.getDisplayNames(n, 0, locale);
    }
    
    private int adjustYear(final int n) {
        final int n2 = this.century + n;
        return (n >= this.startYear) ? n2 : (n2 + 100);
    }
    
    boolean isNextNumber() {
        return this.nextStrategy != null && this.nextStrategy.isNumber();
    }
    
    int getFieldWidth() {
        return this.currentFormatField.length();
    }
    
    private Strategy getStrategy(final String s, final Calendar calendar) {
        switch (s.charAt(0)) {
            case '\'': {
                if (s.length() > 2) {
                    return new CopyQuotedStrategy(s.substring(1, s.length() - 1));
                }
                break;
            }
            case 'D': {
                return FastDateParser.DAY_OF_YEAR_STRATEGY;
            }
            case 'E': {
                return this.getLocaleSpecificStrategy(7, calendar);
            }
            case 'F': {
                return FastDateParser.DAY_OF_WEEK_IN_MONTH_STRATEGY;
            }
            case 'G': {
                return this.getLocaleSpecificStrategy(0, calendar);
            }
            case 'H': {
                return FastDateParser.MODULO_HOUR_OF_DAY_STRATEGY;
            }
            case 'K': {
                return FastDateParser.HOUR_STRATEGY;
            }
            case 'M': {
                return (s.length() >= 3) ? this.getLocaleSpecificStrategy(2, calendar) : FastDateParser.NUMBER_MONTH_STRATEGY;
            }
            case 'S': {
                return FastDateParser.MILLISECOND_STRATEGY;
            }
            case 'W': {
                return FastDateParser.WEEK_OF_MONTH_STRATEGY;
            }
            case 'a': {
                return this.getLocaleSpecificStrategy(9, calendar);
            }
            case 'd': {
                return FastDateParser.DAY_OF_MONTH_STRATEGY;
            }
            case 'h': {
                return FastDateParser.MODULO_HOUR_STRATEGY;
            }
            case 'k': {
                return FastDateParser.HOUR_OF_DAY_STRATEGY;
            }
            case 'm': {
                return FastDateParser.MINUTE_STRATEGY;
            }
            case 's': {
                return FastDateParser.SECOND_STRATEGY;
            }
            case 'w': {
                return FastDateParser.WEEK_OF_YEAR_STRATEGY;
            }
            case 'y': {
                return (s.length() > 2) ? FastDateParser.LITERAL_YEAR_STRATEGY : FastDateParser.ABBREVIATED_YEAR_STRATEGY;
            }
            case 'Z':
            case 'z': {
                return this.getLocaleSpecificStrategy(15, calendar);
            }
        }
        return new CopyQuotedStrategy(s);
    }
    
    private static ConcurrentMap getCache(final int n) {
        // monitorenter(caches = FastDateParser.caches)
        if (FastDateParser.caches[n] == null) {
            FastDateParser.caches[n] = new ConcurrentHashMap(3);
        }
        // monitorexit(caches)
        return FastDateParser.caches[n];
    }
    
    private Strategy getLocaleSpecificStrategy(final int n, final Calendar calendar) {
        final ConcurrentMap cache = getCache(n);
        Strategy strategy = cache.get(this.locale);
        if (strategy == null) {
            strategy = ((n == 15) ? new TimeZoneStrategy(this.locale) : new TextStrategy(n, calendar, this.locale));
            final Strategy strategy2 = cache.putIfAbsent(this.locale, strategy);
            if (strategy2 != null) {
                return strategy2;
            }
        }
        return strategy;
    }
    
    static StringBuilder access$100(final StringBuilder sb, final String s, final boolean b) {
        return escapeRegex(sb, s, b);
    }
    
    static Map access$200(final int n, final Calendar calendar, final Locale locale) {
        return getDisplayNames(n, calendar, locale);
    }
    
    static int access$300(final FastDateParser fastDateParser, final int n) {
        return fastDateParser.adjustYear(n);
    }
    
    static {
        JAPANESE_IMPERIAL = new Locale("ja", "JP", "JP");
        formatPattern = Pattern.compile("D+|E+|F+|G+|H+|K+|M+|S+|W+|Z+|a+|d+|h+|k+|m+|s+|w+|y+|z+|''|'[^']++(''[^']*+)*+'|[^'A-Za-z]++");
        caches = new ConcurrentMap[17];
        ABBREVIATED_YEAR_STRATEGY = new NumberStrategy(1) {
            @Override
            void setCalendar(final FastDateParser fastDateParser, final Calendar calendar, final String s) {
                int n = Integer.parseInt(s);
                if (n < 100) {
                    n = FastDateParser.access$300(fastDateParser, n);
                }
                calendar.set(1, n);
            }
        };
        NUMBER_MONTH_STRATEGY = new NumberStrategy(2) {
            @Override
            int modify(final int n) {
                return n - 1;
            }
        };
        LITERAL_YEAR_STRATEGY = new NumberStrategy(1);
        WEEK_OF_YEAR_STRATEGY = new NumberStrategy(3);
        WEEK_OF_MONTH_STRATEGY = new NumberStrategy(4);
        DAY_OF_YEAR_STRATEGY = new NumberStrategy(6);
        DAY_OF_MONTH_STRATEGY = new NumberStrategy(5);
        DAY_OF_WEEK_IN_MONTH_STRATEGY = new NumberStrategy(8);
        HOUR_OF_DAY_STRATEGY = new NumberStrategy(11);
        MODULO_HOUR_OF_DAY_STRATEGY = new NumberStrategy(11) {
            @Override
            int modify(final int n) {
                return n % 24;
            }
        };
        MODULO_HOUR_STRATEGY = new NumberStrategy(10) {
            @Override
            int modify(final int n) {
                return n % 12;
            }
        };
        HOUR_STRATEGY = new NumberStrategy(10);
        MINUTE_STRATEGY = new NumberStrategy(12);
        SECOND_STRATEGY = new NumberStrategy(13);
        MILLISECOND_STRATEGY = new NumberStrategy(14);
    }
    
    private static class TimeZoneStrategy extends Strategy
    {
        private final String validTimeZoneChars;
        private final SortedMap tzNames;
        private static final int ID = 0;
        private static final int LONG_STD = 1;
        private static final int SHORT_STD = 2;
        private static final int LONG_DST = 3;
        private static final int SHORT_DST = 4;
        
        TimeZoneStrategy(final Locale locale) {
            super(null);
            this.tzNames = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            final String[][] zoneStrings = DateFormatSymbols.getInstance(locale).getZoneStrings();
            while (0 < zoneStrings.length) {
                final String[] array = zoneStrings[0];
                if (!array[0].startsWith("GMT")) {
                    final TimeZone timeZone = TimeZone.getTimeZone(array[0]);
                    if (!this.tzNames.containsKey(array[1])) {
                        this.tzNames.put(array[1], timeZone);
                    }
                    if (!this.tzNames.containsKey(array[2])) {
                        this.tzNames.put(array[2], timeZone);
                    }
                    if (timeZone.useDaylightTime()) {
                        if (!this.tzNames.containsKey(array[3])) {
                            this.tzNames.put(array[3], timeZone);
                        }
                        if (!this.tzNames.containsKey(array[4])) {
                            this.tzNames.put(array[4], timeZone);
                        }
                    }
                }
                int n = 0;
                ++n;
            }
            final StringBuilder sb = new StringBuilder();
            sb.append("(GMT[+\\-]\\d{0,1}\\d{2}|[+\\-]\\d{2}:?\\d{2}|");
            final Iterator<String> iterator = this.tzNames.keySet().iterator();
            while (iterator.hasNext()) {
                FastDateParser.access$100(sb, iterator.next(), false).append('|');
            }
            sb.setCharAt(sb.length() - 1, ')');
            this.validTimeZoneChars = sb.toString();
        }
        
        @Override
        boolean addRegex(final FastDateParser fastDateParser, final StringBuilder sb) {
            sb.append(this.validTimeZoneChars);
            return true;
        }
        
        @Override
        void setCalendar(final FastDateParser fastDateParser, final Calendar calendar, final String s) {
            TimeZone timeZone;
            if (s.charAt(0) == '+' || s.charAt(0) == '-') {
                timeZone = TimeZone.getTimeZone("GMT" + s);
            }
            else if (s.startsWith("GMT")) {
                timeZone = TimeZone.getTimeZone(s);
            }
            else {
                timeZone = (TimeZone)this.tzNames.get(s);
                if (timeZone == null) {
                    throw new IllegalArgumentException(s + " is not a supported timezone name");
                }
            }
            calendar.setTimeZone(timeZone);
        }
    }
    
    private abstract static class Strategy
    {
        private Strategy() {
        }
        
        boolean isNumber() {
            return false;
        }
        
        void setCalendar(final FastDateParser fastDateParser, final Calendar calendar, final String s) {
        }
        
        abstract boolean addRegex(final FastDateParser p0, final StringBuilder p1);
        
        Strategy(final FastDateParser$1 numberStrategy) {
            this();
        }
    }
    
    private static class NumberStrategy extends Strategy
    {
        private final int field;
        
        NumberStrategy(final int field) {
            super(null);
            this.field = field;
        }
        
        @Override
        boolean isNumber() {
            return true;
        }
        
        @Override
        boolean addRegex(final FastDateParser fastDateParser, final StringBuilder sb) {
            if (fastDateParser.isNextNumber()) {
                sb.append("(\\p{Nd}{").append(fastDateParser.getFieldWidth()).append("}+)");
            }
            else {
                sb.append("(\\p{Nd}++)");
            }
            return true;
        }
        
        @Override
        void setCalendar(final FastDateParser fastDateParser, final Calendar calendar, final String s) {
            calendar.set(this.field, this.modify(Integer.parseInt(s)));
        }
        
        int modify(final int n) {
            return n;
        }
    }
    
    private static class TextStrategy extends Strategy
    {
        private final int field;
        private final Map keyValues;
        
        TextStrategy(final int field, final Calendar calendar, final Locale locale) {
            super(null);
            this.field = field;
            this.keyValues = FastDateParser.access$200(field, calendar, locale);
        }
        
        @Override
        boolean addRegex(final FastDateParser fastDateParser, final StringBuilder sb) {
            sb.append('(');
            final Iterator<String> iterator = this.keyValues.keySet().iterator();
            while (iterator.hasNext()) {
                FastDateParser.access$100(sb, iterator.next(), false).append('|');
            }
            sb.setCharAt(sb.length() - 1, ')');
            return true;
        }
        
        @Override
        void setCalendar(final FastDateParser fastDateParser, final Calendar calendar, final String s) {
            final Integer n = this.keyValues.get(s);
            if (n == null) {
                final StringBuilder sb = new StringBuilder(s);
                sb.append(" not in (");
                final Iterator<String> iterator = this.keyValues.keySet().iterator();
                while (iterator.hasNext()) {
                    sb.append(iterator.next()).append(' ');
                }
                sb.setCharAt(sb.length() - 1, ')');
                throw new IllegalArgumentException(sb.toString());
            }
            calendar.set(this.field, n);
        }
    }
    
    private static class CopyQuotedStrategy extends Strategy
    {
        private final String formatField;
        
        CopyQuotedStrategy(final String formatField) {
            super(null);
            this.formatField = formatField;
        }
        
        @Override
        boolean isNumber() {
            char c = this.formatField.charAt(0);
            if (c == '\'') {
                c = this.formatField.charAt(1);
            }
            return Character.isDigit(c);
        }
        
        @Override
        boolean addRegex(final FastDateParser fastDateParser, final StringBuilder sb) {
            FastDateParser.access$100(sb, this.formatField, true);
            return false;
        }
    }
}
