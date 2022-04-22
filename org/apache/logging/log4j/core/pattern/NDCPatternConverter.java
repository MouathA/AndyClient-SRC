package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "NDCPatternConverter", category = "Converter")
@ConverterKeys({ "x", "NDC" })
public final class NDCPatternConverter extends LogEventPatternConverter
{
    private static final NDCPatternConverter INSTANCE;
    
    private NDCPatternConverter() {
        super("NDC", "ndc");
    }
    
    public static NDCPatternConverter newInstance(final String[] array) {
        return NDCPatternConverter.INSTANCE;
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        sb.append(logEvent.getContextStack());
    }
    
    static {
        INSTANCE = new NDCPatternConverter();
    }
}
