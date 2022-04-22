package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "MethodLocationPatternConverter", category = "Converter")
@ConverterKeys({ "M", "method" })
public final class MethodLocationPatternConverter extends LogEventPatternConverter
{
    private static final MethodLocationPatternConverter INSTANCE;
    
    private MethodLocationPatternConverter() {
        super("Method", "method");
    }
    
    public static MethodLocationPatternConverter newInstance(final String[] array) {
        return MethodLocationPatternConverter.INSTANCE;
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final StackTraceElement source = logEvent.getSource();
        if (source != null) {
            sb.append(source.getMethodName());
        }
    }
    
    static {
        INSTANCE = new MethodLocationPatternConverter();
    }
}
