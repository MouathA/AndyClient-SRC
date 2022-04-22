package joptsimple.util;

import java.util.regex.*;
import joptsimple.*;

public class RegexMatcher implements ValueConverter
{
    private final Pattern pattern;
    
    public RegexMatcher(final String s, final int n) {
        this.pattern = Pattern.compile(s, n);
    }
    
    public static ValueConverter regex(final String s) {
        return new RegexMatcher(s, 0);
    }
    
    public String convert(final String s) {
        if (!this.pattern.matcher(s).matches()) {
            throw new ValueConversionException("Value [" + s + "] did not match regex [" + this.pattern.pattern() + ']');
        }
        return s;
    }
    
    public Class valueType() {
        return String.class;
    }
    
    public String valuePattern() {
        return this.pattern.pattern();
    }
    
    public Object convert(final String s) {
        return this.convert(s);
    }
}
