package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "FullLocationPatternConverter", category = "Converter")
@ConverterKeys({ "l", "location" })
public final class FullLocationPatternConverter extends LogEventPatternConverter
{
    private static final FullLocationPatternConverter INSTANCE;
    
    private FullLocationPatternConverter() {
        super("Full Location", "fullLocation");
    }
    
    public static FullLocationPatternConverter newInstance(final String[] array) {
        return FullLocationPatternConverter.INSTANCE;
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final StackTraceElement source = logEvent.getSource();
        if (source != null) {
            sb.append(source.toString());
        }
    }
    
    static {
        INSTANCE = new FullLocationPatternConverter();
    }
}
