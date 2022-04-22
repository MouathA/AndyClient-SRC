package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.*;

public class PatternFormatter
{
    private final LogEventPatternConverter converter;
    private final FormattingInfo field;
    
    public PatternFormatter(final LogEventPatternConverter converter, final FormattingInfo field) {
        this.converter = converter;
        this.field = field;
    }
    
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final int length = sb.length();
        this.converter.format(logEvent, sb);
        this.field.format(length, sb);
    }
    
    public LogEventPatternConverter getConverter() {
        return this.converter;
    }
    
    public FormattingInfo getFormattingInfo() {
        return this.field;
    }
    
    public boolean handlesThrowable() {
        return this.converter.handlesThrowable();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[converter=");
        sb.append(this.converter);
        sb.append(", field=");
        sb.append(this.field);
        sb.append("]");
        return sb.toString();
    }
}
