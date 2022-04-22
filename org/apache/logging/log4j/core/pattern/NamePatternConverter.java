package org.apache.logging.log4j.core.pattern;

public abstract class NamePatternConverter extends LogEventPatternConverter
{
    private final NameAbbreviator abbreviator;
    
    protected NamePatternConverter(final String s, final String s2, final String[] array) {
        super(s, s2);
        if (array != null && array.length > 0) {
            this.abbreviator = NameAbbreviator.getAbbreviator(array[0]);
        }
        else {
            this.abbreviator = NameAbbreviator.getDefaultAbbreviator();
        }
    }
    
    protected final String abbreviate(final String s) {
        return this.abbreviator.abbreviate(s);
    }
}
