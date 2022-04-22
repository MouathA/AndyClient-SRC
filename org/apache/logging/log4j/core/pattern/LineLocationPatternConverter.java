package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "LineLocationPatternConverter", category = "Converter")
@ConverterKeys({ "L", "line" })
public final class LineLocationPatternConverter extends LogEventPatternConverter
{
    private static final LineLocationPatternConverter INSTANCE;
    
    private LineLocationPatternConverter() {
        super("Line", "line");
    }
    
    public static LineLocationPatternConverter newInstance(final String[] array) {
        return LineLocationPatternConverter.INSTANCE;
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final StackTraceElement source = logEvent.getSource();
        if (source != null) {
            sb.append(source.getLineNumber());
        }
    }
    
    static {
        INSTANCE = new LineLocationPatternConverter();
    }
}
