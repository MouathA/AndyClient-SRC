package com.ibm.icu.text;

import java.text.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;
import java.util.*;

public class TimeUnitFormat extends MeasureFormat
{
    public static final int FULL_NAME = 0;
    public static final int ABBREVIATED_NAME = 1;
    private static final int TOTAL_STYLES = 2;
    private static final long serialVersionUID = -3707773153184971529L;
    private static final String DEFAULT_PATTERN_FOR_SECOND = "{0} s";
    private static final String DEFAULT_PATTERN_FOR_MINUTE = "{0} min";
    private static final String DEFAULT_PATTERN_FOR_HOUR = "{0} h";
    private static final String DEFAULT_PATTERN_FOR_DAY = "{0} d";
    private static final String DEFAULT_PATTERN_FOR_WEEK = "{0} w";
    private static final String DEFAULT_PATTERN_FOR_MONTH = "{0} m";
    private static final String DEFAULT_PATTERN_FOR_YEAR = "{0} y";
    private NumberFormat format;
    private ULocale locale;
    private transient Map timeUnitToCountToPatterns;
    private transient PluralRules pluralRules;
    private transient boolean isReady;
    private int style;
    
    public TimeUnitFormat() {
        this.isReady = false;
        this.style = 0;
    }
    
    public TimeUnitFormat(final ULocale uLocale) {
        this(uLocale, 0);
    }
    
    public TimeUnitFormat(final Locale locale) {
        this(locale, 0);
    }
    
    public TimeUnitFormat(final ULocale locale, final int style) {
        if (style < 0 || style >= 2) {
            throw new IllegalArgumentException("style should be either FULL_NAME or ABBREVIATED_NAME style");
        }
        this.style = style;
        this.locale = locale;
        this.isReady = false;
    }
    
    public TimeUnitFormat(final Locale locale, final int n) {
        this(ULocale.forLocale(locale), n);
    }
    
    public TimeUnitFormat setLocale(final ULocale locale) {
        if (locale != this.locale) {
            this.locale = locale;
            this.isReady = false;
        }
        return this;
    }
    
    public TimeUnitFormat setLocale(final Locale locale) {
        return this.setLocale(ULocale.forLocale(locale));
    }
    
    public TimeUnitFormat setNumberFormat(final NumberFormat format) {
        if (format == this.format) {
            return this;
        }
        if (format == null) {
            if (this.locale == null) {
                this.isReady = false;
                return this;
            }
            this.format = NumberFormat.getNumberInstance(this.locale);
        }
        else {
            this.format = format;
        }
        if (!this.isReady) {
            return this;
        }
        final Iterator<Map<Object, Object[]>> iterator = this.timeUnitToCountToPatterns.values().iterator();
        while (iterator.hasNext()) {
            for (final Object[] array : iterator.next().values()) {
                ((MessageFormat)array[0]).setFormatByArgumentIndex(0, format);
                ((MessageFormat)array[1]).setFormatByArgumentIndex(0, format);
            }
        }
        return this;
    }
    
    @Override
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (!(o instanceof TimeUnitAmount)) {
            throw new IllegalArgumentException("can not format non TimeUnitAmount object");
        }
        if (!this.isReady) {
            this.setup();
        }
        final TimeUnitAmount timeUnitAmount = (TimeUnitAmount)o;
        return ((MessageFormat)((Object[])((Map)this.timeUnitToCountToPatterns.get(timeUnitAmount.getTimeUnit())).get(this.pluralRules.select(timeUnitAmount.getNumber().doubleValue())))[this.style]).format(new Object[] { timeUnitAmount.getNumber() }, sb, fieldPosition);
    }
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        if (!this.isReady) {
            this.setup();
        }
        Integer n = null;
        TimeUnit timeUnit = null;
        final int index = parsePosition.getIndex();
        String s2 = null;
        for (final TimeUnit timeUnit2 : this.timeUnitToCountToPatterns.keySet()) {
            for (final Map.Entry<String, V> entry : this.timeUnitToCountToPatterns.get(timeUnit2).entrySet()) {
                final String s3 = entry.getKey();
                while (0 < 2) {
                    final MessageFormat messageFormat = (MessageFormat)((Object[])(Object)entry.getValue())[0];
                    parsePosition.setErrorIndex(-1);
                    parsePosition.setIndex(index);
                    final Object object = messageFormat.parseObject(s, parsePosition);
                    Label_0282: {
                        if (parsePosition.getErrorIndex() == -1) {
                            if (parsePosition.getIndex() != index) {
                                Number n2 = null;
                                if (((Object[])object).length != 0) {
                                    n2 = (Number)((Object[])object)[0];
                                    if (!s3.equals(this.pluralRules.select(n2.doubleValue()))) {
                                        break Label_0282;
                                    }
                                }
                                if (parsePosition.getIndex() - index > 0) {
                                    n = (Integer)n2;
                                    timeUnit = timeUnit2;
                                    parsePosition.getIndex();
                                    s2 = s3;
                                }
                            }
                        }
                    }
                    int n3 = 0;
                    ++n3;
                }
            }
        }
        if (n == null && false) {
            if (s2.equals("zero")) {
                n = 0;
            }
            else if (s2.equals("one")) {
                n = 1;
            }
            else if (s2.equals("two")) {
                n = 2;
            }
            else {
                n = 3;
            }
        }
        if (!false) {
            parsePosition.setIndex(index);
            parsePosition.setErrorIndex(0);
            return null;
        }
        parsePosition.setIndex(-1);
        parsePosition.setErrorIndex(-1);
        return new TimeUnitAmount(n, timeUnit);
    }
    
    private void setup() {
        if (this.locale == null) {
            if (this.format != null) {
                this.locale = this.format.getLocale(null);
            }
            else {
                this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
            }
        }
        if (this.format == null) {
            this.format = NumberFormat.getNumberInstance(this.locale);
        }
        this.pluralRules = PluralRules.forLocale(this.locale);
        this.timeUnitToCountToPatterns = new HashMap();
        final Set keywords = this.pluralRules.getKeywords();
        this.setup("units", this.timeUnitToCountToPatterns, 0, keywords);
        this.setup("unitsShort", this.timeUnitToCountToPatterns, 1, keywords);
        this.isReady = true;
    }
    
    private void setup(final String s, final Map map, final int n, final Set set) {
        final ICUResourceBundle withFallback = ((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", this.locale)).getWithFallback(s);
        int size = withFallback.getSize();
        while (0 < 0) {
            final String key = withFallback.get(0).getKey();
            Label_0357: {
                TimeUnit timeUnit;
                if (key.equals("year")) {
                    timeUnit = TimeUnit.YEAR;
                }
                else if (key.equals("month")) {
                    timeUnit = TimeUnit.MONTH;
                }
                else if (key.equals("day")) {
                    timeUnit = TimeUnit.DAY;
                }
                else if (key.equals("hour")) {
                    timeUnit = TimeUnit.HOUR;
                }
                else if (key.equals("minute")) {
                    timeUnit = TimeUnit.MINUTE;
                }
                else if (key.equals("second")) {
                    timeUnit = TimeUnit.SECOND;
                }
                else {
                    if (!key.equals("week")) {
                        break Label_0357;
                    }
                    timeUnit = TimeUnit.WEEK;
                }
                final ICUResourceBundle withFallback2 = withFallback.getWithFallback(key);
                final int size2 = withFallback2.getSize();
                Map<?, ?> map2 = map.get(timeUnit);
                if (map2 == null) {
                    map2 = new TreeMap<Object, Object>();
                    map.put(timeUnit, map2);
                }
                while (0 < size2) {
                    final String key2 = withFallback2.get(0).getKey();
                    if (set.contains(key2)) {
                        final MessageFormat messageFormat = new MessageFormat(withFallback2.get(0).getString(), this.locale);
                        if (this.format != null) {
                            messageFormat.setFormatByArgumentIndex(0, this.format);
                        }
                        Object[] array = (Object)map2.get(key2);
                        if (array == null) {
                            array = new Object[2];
                            map2.put(key2, array);
                        }
                        array[n] = messageFormat;
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
            int n3 = 0;
            ++n3;
        }
        final TimeUnit[] values = TimeUnit.values();
        final Set keywords = this.pluralRules.getKeywords();
        while (0 < values.length) {
            final TimeUnit timeUnit2 = values[0];
            Map<?, ?> map3 = map.get(timeUnit2);
            if (map3 == null) {
                map3 = new TreeMap<Object, Object>();
                map.put(timeUnit2, map3);
            }
            for (final String s2 : keywords) {
                if (map3.get(s2) == null || ((Object[])(Object)map3.get(s2))[n] == null) {
                    this.searchInTree(s, n, timeUnit2, s2, s2, map3);
                }
            }
            ++size;
        }
    }
    
    private void searchInTree(final String s, final int n, final TimeUnit timeUnit, final String s2, final String s3, final Map map) {
        final ULocale locale = this.locale;
        final String string = timeUnit.toString();
        if (locale != null) {
            final MessageFormat messageFormat = new MessageFormat(((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", locale)).getWithFallback(s).getWithFallback(string).getStringWithFallback(s3), this.locale);
            if (this.format != null) {
                messageFormat.setFormatByArgumentIndex(0, this.format);
            }
            Object[] array = map.get(s2);
            if (array == null) {
                array = new Object[2];
                map.put(s2, array);
            }
            array[n] = messageFormat;
            return;
        }
        if (locale == null && s.equals("unitsShort")) {
            this.searchInTree("units", n, timeUnit, s2, s3, map);
            if (map != null && map.get(s2) != null && map.get(s2)[n] != null) {
                return;
            }
        }
        if (s3.equals("other")) {
            MessageFormat messageFormat2 = null;
            if (timeUnit == TimeUnit.SECOND) {
                messageFormat2 = new MessageFormat("{0} s", this.locale);
            }
            else if (timeUnit == TimeUnit.MINUTE) {
                messageFormat2 = new MessageFormat("{0} min", this.locale);
            }
            else if (timeUnit == TimeUnit.HOUR) {
                messageFormat2 = new MessageFormat("{0} h", this.locale);
            }
            else if (timeUnit == TimeUnit.WEEK) {
                messageFormat2 = new MessageFormat("{0} w", this.locale);
            }
            else if (timeUnit == TimeUnit.DAY) {
                messageFormat2 = new MessageFormat("{0} d", this.locale);
            }
            else if (timeUnit == TimeUnit.MONTH) {
                messageFormat2 = new MessageFormat("{0} m", this.locale);
            }
            else if (timeUnit == TimeUnit.YEAR) {
                messageFormat2 = new MessageFormat("{0} y", this.locale);
            }
            if (this.format != null && messageFormat2 != null) {
                messageFormat2.setFormatByArgumentIndex(0, this.format);
            }
            Object[] array2 = map.get(s2);
            if (array2 == null) {
                array2 = new Object[2];
                map.put(s2, array2);
            }
            array2[n] = messageFormat2;
        }
        else {
            this.searchInTree(s, n, timeUnit, s2, "other", map);
        }
    }
}
