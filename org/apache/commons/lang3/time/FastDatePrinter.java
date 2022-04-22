package org.apache.commons.lang3.time;

import java.text.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import org.apache.commons.lang3.*;

public class FastDatePrinter implements DatePrinter, Serializable
{
    private static final long serialVersionUID = 1L;
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private final String mPattern;
    private final TimeZone mTimeZone;
    private final Locale mLocale;
    private transient Rule[] mRules;
    private transient int mMaxLengthEstimate;
    private static final ConcurrentMap cTimeZoneDisplayCache;
    
    protected FastDatePrinter(final String mPattern, final TimeZone mTimeZone, final Locale mLocale) {
        this.mPattern = mPattern;
        this.mTimeZone = mTimeZone;
        this.mLocale = mLocale;
        this.init();
    }
    
    private void init() {
        final List pattern = this.parsePattern();
        this.mRules = pattern.toArray(new Rule[pattern.size()]);
        int length = this.mRules.length;
        while (--length >= 0) {
            final int n = 0 + this.mRules[length].estimateLength();
        }
        this.mMaxLengthEstimate = 0;
    }
    
    protected List parsePattern() {
        final DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(this.mLocale);
        final ArrayList<Rule> list = new ArrayList<Rule>();
        final String[] eras = dateFormatSymbols.getEras();
        final String[] months = dateFormatSymbols.getMonths();
        final String[] shortMonths = dateFormatSymbols.getShortMonths();
        final String[] weekdays = dateFormatSymbols.getWeekdays();
        final String[] shortWeekdays = dateFormatSymbols.getShortWeekdays();
        final String[] amPmStrings = dateFormatSymbols.getAmPmStrings();
        final int length = this.mPattern.length();
        final int[] array = { 0 };
        while (0 < length) {
            array[0] = 0;
            final String token = this.parseToken(this.mPattern, array);
            int n = array[0];
            final int length2 = token.length();
            if (length2 == 0) {
                break;
            }
            Rule rule = null;
            switch (token.charAt(0)) {
                case 'G': {
                    rule = new TextField(0, eras);
                    break;
                }
                case 'y': {
                    if (length2 == 2) {
                        rule = TwoDigitYearField.INSTANCE;
                        break;
                    }
                    rule = this.selectNumberRule(1, (length2 < 4) ? 4 : length2);
                    break;
                }
                case 'M': {
                    if (length2 >= 4) {
                        rule = new TextField(2, months);
                        break;
                    }
                    if (length2 == 3) {
                        rule = new TextField(2, shortMonths);
                        break;
                    }
                    if (length2 == 2) {
                        rule = TwoDigitMonthField.INSTANCE;
                        break;
                    }
                    rule = UnpaddedMonthField.INSTANCE;
                    break;
                }
                case 'd': {
                    rule = this.selectNumberRule(5, length2);
                    break;
                }
                case 'h': {
                    rule = new TwelveHourField(this.selectNumberRule(10, length2));
                    break;
                }
                case 'H': {
                    rule = this.selectNumberRule(11, length2);
                    break;
                }
                case 'm': {
                    rule = this.selectNumberRule(12, length2);
                    break;
                }
                case 's': {
                    rule = this.selectNumberRule(13, length2);
                    break;
                }
                case 'S': {
                    rule = this.selectNumberRule(14, length2);
                    break;
                }
                case 'E': {
                    rule = new TextField(7, (length2 < 4) ? shortWeekdays : weekdays);
                    break;
                }
                case 'D': {
                    rule = this.selectNumberRule(6, length2);
                    break;
                }
                case 'F': {
                    rule = this.selectNumberRule(8, length2);
                    break;
                }
                case 'w': {
                    rule = this.selectNumberRule(3, length2);
                    break;
                }
                case 'W': {
                    rule = this.selectNumberRule(4, length2);
                    break;
                }
                case 'a': {
                    rule = new TextField(9, amPmStrings);
                    break;
                }
                case 'k': {
                    rule = new TwentyFourHourField(this.selectNumberRule(11, length2));
                    break;
                }
                case 'K': {
                    rule = this.selectNumberRule(10, length2);
                    break;
                }
                case 'z': {
                    if (length2 >= 4) {
                        rule = new TimeZoneNameRule(this.mTimeZone, this.mLocale, 1);
                        break;
                    }
                    rule = new TimeZoneNameRule(this.mTimeZone, this.mLocale, 0);
                    break;
                }
                case 'Z': {
                    if (length2 == 1) {
                        rule = TimeZoneNumberRule.INSTANCE_NO_COLON;
                        break;
                    }
                    rule = TimeZoneNumberRule.INSTANCE_COLON;
                    break;
                }
                case '\'': {
                    final String substring = token.substring(1);
                    if (substring.length() == 1) {
                        rule = new CharacterLiteral(substring.charAt(0));
                        break;
                    }
                    rule = new StringLiteral(substring);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Illegal pattern component: " + token);
                }
            }
            list.add(rule);
            ++n;
        }
        return list;
    }
    
    protected String parseToken(final String s, final int[] array) {
        final StringBuilder sb = new StringBuilder();
        int i = array[0];
        final int length = s.length();
        final char char1 = s.charAt(i);
        if ((char1 >= 'A' && char1 <= 'Z') || (char1 >= 'a' && char1 <= 'z')) {
            sb.append(char1);
            while (i + 1 < length) {
                s.charAt(i + 1);
                if (char1 != '\0') {
                    break;
                }
                sb.append(char1);
                ++i;
            }
        }
        else {
            sb.append('\'');
            while (i < length) {
                final char char2 = s.charAt(i);
                if (char2 == '\'') {
                    if (i + 1 < length && s.charAt(i + 1) == '\'') {
                        ++i;
                        sb.append(char2);
                    }
                    else {
                        final boolean b = !false;
                    }
                }
                else {
                    if (!false && ((char2 >= 'A' && char2 <= 'Z') || (char2 >= 'a' && char2 <= 'z'))) {
                        --i;
                        break;
                    }
                    sb.append(char2);
                }
                ++i;
            }
        }
        array[0] = i;
        return sb.toString();
    }
    
    protected NumberRule selectNumberRule(final int n, final int n2) {
        switch (n2) {
            case 1: {
                return new UnpaddedNumberField(n);
            }
            case 2: {
                return new TwoDigitNumberField(n);
            }
            default: {
                return new PaddedNumberField(n, n2);
            }
        }
    }
    
    @Override
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (o instanceof Date) {
            return this.format((Date)o, sb);
        }
        if (o instanceof Calendar) {
            return this.format((Calendar)o, sb);
        }
        if (o instanceof Long) {
            return this.format((long)o, sb);
        }
        throw new IllegalArgumentException("Unknown class: " + ((o == null) ? "<null>" : o.getClass().getName()));
    }
    
    @Override
    public String format(final long timeInMillis) {
        final GregorianCalendar calendar = this.newCalendar();
        calendar.setTimeInMillis(timeInMillis);
        return this.applyRulesToString(calendar);
    }
    
    private String applyRulesToString(final Calendar calendar) {
        return this.applyRules(calendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
    }
    
    private GregorianCalendar newCalendar() {
        return new GregorianCalendar(this.mTimeZone, this.mLocale);
    }
    
    @Override
    public String format(final Date time) {
        final GregorianCalendar calendar = this.newCalendar();
        calendar.setTime(time);
        return this.applyRulesToString(calendar);
    }
    
    @Override
    public String format(final Calendar calendar) {
        return this.format(calendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
    }
    
    @Override
    public StringBuffer format(final long n, final StringBuffer sb) {
        return this.format(new Date(n), sb);
    }
    
    @Override
    public StringBuffer format(final Date time, final StringBuffer sb) {
        final GregorianCalendar calendar = this.newCalendar();
        calendar.setTime(time);
        return this.applyRules(calendar, sb);
    }
    
    @Override
    public StringBuffer format(final Calendar calendar, final StringBuffer sb) {
        return this.applyRules(calendar, sb);
    }
    
    protected StringBuffer applyRules(final Calendar calendar, final StringBuffer sb) {
        final Rule[] mRules = this.mRules;
        while (0 < mRules.length) {
            mRules[0].appendTo(sb, calendar);
            int n = 0;
            ++n;
        }
        return sb;
    }
    
    @Override
    public String getPattern() {
        return this.mPattern;
    }
    
    @Override
    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }
    
    @Override
    public Locale getLocale() {
        return this.mLocale;
    }
    
    public int getMaxLengthEstimate() {
        return this.mMaxLengthEstimate;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof FastDatePrinter)) {
            return false;
        }
        final FastDatePrinter fastDatePrinter = (FastDatePrinter)o;
        return this.mPattern.equals(fastDatePrinter.mPattern) && this.mTimeZone.equals(fastDatePrinter.mTimeZone) && this.mLocale.equals(fastDatePrinter.mLocale);
    }
    
    @Override
    public int hashCode() {
        return this.mPattern.hashCode() + 13 * (this.mTimeZone.hashCode() + 13 * this.mLocale.hashCode());
    }
    
    @Override
    public String toString() {
        return "FastDatePrinter[" + this.mPattern + "," + this.mLocale + "," + this.mTimeZone.getID() + "]";
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.init();
    }
    
    static String getTimeZoneDisplay(final TimeZone timeZone, final boolean b, final int n, final Locale locale) {
        final TimeZoneDisplayKey timeZoneDisplayKey = new TimeZoneDisplayKey(timeZone, b, n, locale);
        String displayName = (String)FastDatePrinter.cTimeZoneDisplayCache.get(timeZoneDisplayKey);
        if (displayName == null) {
            displayName = timeZone.getDisplayName(b, n, locale);
            final String s = FastDatePrinter.cTimeZoneDisplayCache.putIfAbsent(timeZoneDisplayKey, displayName);
            if (s != null) {
                displayName = s;
            }
        }
        return displayName;
    }
    
    static {
        cTimeZoneDisplayCache = new ConcurrentHashMap(7);
    }
    
    private static class TimeZoneDisplayKey
    {
        private final TimeZone mTimeZone;
        private final int mStyle;
        private final Locale mLocale;
        
        TimeZoneDisplayKey(final TimeZone mTimeZone, final boolean b, final int mStyle, final Locale mLocale) {
            this.mTimeZone = mTimeZone;
            if (b) {
                this.mStyle = (mStyle | Integer.MIN_VALUE);
            }
            else {
                this.mStyle = mStyle;
            }
            this.mLocale = mLocale;
        }
        
        @Override
        public int hashCode() {
            return (this.mStyle * 31 + this.mLocale.hashCode()) * 31 + this.mTimeZone.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof TimeZoneDisplayKey) {
                final TimeZoneDisplayKey timeZoneDisplayKey = (TimeZoneDisplayKey)o;
                return this.mTimeZone.equals(timeZoneDisplayKey.mTimeZone) && this.mStyle == timeZoneDisplayKey.mStyle && this.mLocale.equals(timeZoneDisplayKey.mLocale);
            }
            return false;
        }
    }
    
    private static class TimeZoneNumberRule implements Rule
    {
        static final TimeZoneNumberRule INSTANCE_COLON;
        static final TimeZoneNumberRule INSTANCE_NO_COLON;
        final boolean mColon;
        
        TimeZoneNumberRule(final boolean mColon) {
            this.mColon = mColon;
        }
        
        @Override
        public int estimateLength() {
            return 5;
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            int n = calendar.get(15) + calendar.get(16);
            if (n < 0) {
                sb.append('-');
                n = -n;
            }
            else {
                sb.append('+');
            }
            final int n2 = n / 3600000;
            sb.append((char)(n2 / 10 + 48));
            sb.append((char)(n2 % 10 + 48));
            if (this.mColon) {
                sb.append(':');
            }
            final int n3 = n / 60000 - 60 * n2;
            sb.append((char)(n3 / 10 + 48));
            sb.append((char)(n3 % 10 + 48));
        }
        
        static {
            INSTANCE_COLON = new TimeZoneNumberRule(true);
            INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
        }
    }
    
    private interface Rule
    {
        int estimateLength();
        
        void appendTo(final StringBuffer p0, final Calendar p1);
    }
    
    private static class TimeZoneNameRule implements Rule
    {
        private final Locale mLocale;
        private final int mStyle;
        private final String mStandard;
        private final String mDaylight;
        
        TimeZoneNameRule(final TimeZone timeZone, final Locale mLocale, final int mStyle) {
            this.mLocale = mLocale;
            this.mStyle = mStyle;
            this.mStandard = FastDatePrinter.getTimeZoneDisplay(timeZone, false, mStyle, mLocale);
            this.mDaylight = FastDatePrinter.getTimeZoneDisplay(timeZone, true, mStyle, mLocale);
        }
        
        @Override
        public int estimateLength() {
            return Math.max(this.mStandard.length(), this.mDaylight.length());
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            final TimeZone timeZone = calendar.getTimeZone();
            if (timeZone.useDaylightTime() && calendar.get(16) != 0) {
                sb.append(FastDatePrinter.getTimeZoneDisplay(timeZone, true, this.mStyle, this.mLocale));
            }
            else {
                sb.append(FastDatePrinter.getTimeZoneDisplay(timeZone, false, this.mStyle, this.mLocale));
            }
        }
    }
    
    private static class TwentyFourHourField implements NumberRule
    {
        private final NumberRule mRule;
        
        TwentyFourHourField(final NumberRule mRule) {
            this.mRule = mRule;
        }
        
        @Override
        public int estimateLength() {
            return this.mRule.estimateLength();
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            int value = calendar.get(11);
            if (value == 0) {
                value = calendar.getMaximum(11) + 1;
            }
            this.mRule.appendTo(sb, value);
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final int n) {
            this.mRule.appendTo(sb, n);
        }
    }
    
    private interface NumberRule extends Rule
    {
        void appendTo(final StringBuffer p0, final int p1);
    }
    
    private static class TwelveHourField implements NumberRule
    {
        private final NumberRule mRule;
        
        TwelveHourField(final NumberRule mRule) {
            this.mRule = mRule;
        }
        
        @Override
        public int estimateLength() {
            return this.mRule.estimateLength();
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            int value = calendar.get(10);
            if (value == 0) {
                value = calendar.getLeastMaximum(10) + 1;
            }
            this.mRule.appendTo(sb, value);
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final int n) {
            this.mRule.appendTo(sb, n);
        }
    }
    
    private static class TwoDigitMonthField implements NumberRule
    {
        static final TwoDigitMonthField INSTANCE;
        
        TwoDigitMonthField() {
        }
        
        @Override
        public int estimateLength() {
            return 2;
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            this.appendTo(sb, calendar.get(2) + 1);
        }
        
        @Override
        public final void appendTo(final StringBuffer sb, final int n) {
            sb.append((char)(n / 10 + 48));
            sb.append((char)(n % 10 + 48));
        }
        
        static {
            INSTANCE = new TwoDigitMonthField();
        }
    }
    
    private static class TwoDigitYearField implements NumberRule
    {
        static final TwoDigitYearField INSTANCE;
        
        TwoDigitYearField() {
        }
        
        @Override
        public int estimateLength() {
            return 2;
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            this.appendTo(sb, calendar.get(1) % 100);
        }
        
        @Override
        public final void appendTo(final StringBuffer sb, final int n) {
            sb.append((char)(n / 10 + 48));
            sb.append((char)(n % 10 + 48));
        }
        
        static {
            INSTANCE = new TwoDigitYearField();
        }
    }
    
    private static class TwoDigitNumberField implements NumberRule
    {
        private final int mField;
        
        TwoDigitNumberField(final int mField) {
            this.mField = mField;
        }
        
        @Override
        public int estimateLength() {
            return 2;
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            this.appendTo(sb, calendar.get(this.mField));
        }
        
        @Override
        public final void appendTo(final StringBuffer sb, final int n) {
            if (n < 100) {
                sb.append((char)(n / 10 + 48));
                sb.append((char)(n % 10 + 48));
            }
            else {
                sb.append(Integer.toString(n));
            }
        }
    }
    
    private static class PaddedNumberField implements NumberRule
    {
        private final int mField;
        private final int mSize;
        
        PaddedNumberField(final int mField, final int mSize) {
            if (mSize < 3) {
                throw new IllegalArgumentException();
            }
            this.mField = mField;
            this.mSize = mSize;
        }
        
        @Override
        public int estimateLength() {
            return 4;
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            this.appendTo(sb, calendar.get(this.mField));
        }
        
        @Override
        public final void appendTo(final StringBuffer sb, final int n) {
            if (n < 100) {
                int mSize = this.mSize;
                while (true) {
                    --mSize;
                    if (3 < 2) {
                        break;
                    }
                    sb.append('0');
                }
                sb.append((char)(n / 10 + 48));
                sb.append((char)(n % 10 + 48));
            }
            else {
                if (n >= 1000) {
                    Validate.isTrue(n > -1, "Negative values should not be possible", n);
                    Integer.toString(n).length();
                }
                int mSize2 = this.mSize;
                while (--mSize2 >= 3) {
                    sb.append('0');
                }
                sb.append(Integer.toString(n));
            }
        }
    }
    
    private static class UnpaddedMonthField implements NumberRule
    {
        static final UnpaddedMonthField INSTANCE;
        
        UnpaddedMonthField() {
        }
        
        @Override
        public int estimateLength() {
            return 2;
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            this.appendTo(sb, calendar.get(2) + 1);
        }
        
        @Override
        public final void appendTo(final StringBuffer sb, final int n) {
            if (n < 10) {
                sb.append((char)(n + 48));
            }
            else {
                sb.append((char)(n / 10 + 48));
                sb.append((char)(n % 10 + 48));
            }
        }
        
        static {
            INSTANCE = new UnpaddedMonthField();
        }
    }
    
    private static class UnpaddedNumberField implements NumberRule
    {
        private final int mField;
        
        UnpaddedNumberField(final int mField) {
            this.mField = mField;
        }
        
        @Override
        public int estimateLength() {
            return 4;
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            this.appendTo(sb, calendar.get(this.mField));
        }
        
        @Override
        public final void appendTo(final StringBuffer sb, final int n) {
            if (n < 10) {
                sb.append((char)(n + 48));
            }
            else if (n < 100) {
                sb.append((char)(n / 10 + 48));
                sb.append((char)(n % 10 + 48));
            }
            else {
                sb.append(Integer.toString(n));
            }
        }
    }
    
    private static class TextField implements Rule
    {
        private final int mField;
        private final String[] mValues;
        
        TextField(final int mField, final String[] mValues) {
            this.mField = mField;
            this.mValues = mValues;
        }
        
        @Override
        public int estimateLength() {
            int length = this.mValues.length;
            while (--length >= 0) {
                if (this.mValues[length].length() > 0) {
                    continue;
                }
            }
            return 0;
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            sb.append(this.mValues[calendar.get(this.mField)]);
        }
    }
    
    private static class StringLiteral implements Rule
    {
        private final String mValue;
        
        StringLiteral(final String mValue) {
            this.mValue = mValue;
        }
        
        @Override
        public int estimateLength() {
            return this.mValue.length();
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            sb.append(this.mValue);
        }
    }
    
    private static class CharacterLiteral implements Rule
    {
        private final char mValue;
        
        CharacterLiteral(final char mValue) {
            this.mValue = mValue;
        }
        
        @Override
        public int estimateLength() {
            return 1;
        }
        
        @Override
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            sb.append(this.mValue);
        }
    }
}
