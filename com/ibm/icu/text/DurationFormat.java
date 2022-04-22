package com.ibm.icu.text;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.duration.*;
import java.text.*;
import java.util.*;

public abstract class DurationFormat extends UFormat
{
    private static final long serialVersionUID = -2076961954727774282L;
    
    public static DurationFormat getInstance(final ULocale uLocale) {
        return BasicDurationFormat.getInstance(uLocale);
    }
    
    @Deprecated
    protected DurationFormat() {
    }
    
    @Deprecated
    protected DurationFormat(final ULocale uLocale) {
        this.setLocale(uLocale, uLocale);
    }
    
    @Override
    public abstract StringBuffer format(final Object p0, final StringBuffer p1, final FieldPosition p2);
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }
    
    public abstract String formatDurationFromNowTo(final Date p0);
    
    public abstract String formatDurationFromNow(final long p0);
    
    public abstract String formatDurationFrom(final long p0, final long p1);
}
