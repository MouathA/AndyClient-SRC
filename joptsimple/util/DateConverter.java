package joptsimple.util;

import java.util.*;
import java.text.*;
import joptsimple.*;

public class DateConverter implements ValueConverter
{
    private final DateFormat formatter;
    
    public DateConverter(final DateFormat formatter) {
        if (formatter == null) {
            throw new NullPointerException("illegal null formatter");
        }
        this.formatter = formatter;
    }
    
    public static DateConverter datePattern(final String s) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(s);
        simpleDateFormat.setLenient(false);
        return new DateConverter(simpleDateFormat);
    }
    
    public Date convert(final String s) {
        final ParsePosition parsePosition = new ParsePosition(0);
        final Date parse = this.formatter.parse(s, parsePosition);
        if (parsePosition.getIndex() != s.length()) {
            throw new ValueConversionException(this.message(s));
        }
        return parse;
    }
    
    public Class valueType() {
        return Date.class;
    }
    
    public String valuePattern() {
        return (this.formatter instanceof SimpleDateFormat) ? ((SimpleDateFormat)this.formatter).toPattern() : "";
    }
    
    private String message(final String s) {
        String s2 = "Value [" + s + "] does not match date/time pattern";
        if (this.formatter instanceof SimpleDateFormat) {
            s2 = s2 + " [" + ((SimpleDateFormat)this.formatter).toPattern() + ']';
        }
        return s2;
    }
    
    public Object convert(final String s) {
        return this.convert(s);
    }
}
