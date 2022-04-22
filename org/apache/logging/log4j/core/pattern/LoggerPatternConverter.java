package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "LoggerPatternConverter", category = "Converter")
@ConverterKeys({ "c", "logger" })
public final class LoggerPatternConverter extends NamePatternConverter
{
    private static final LoggerPatternConverter INSTANCE;
    
    private LoggerPatternConverter(final String[] array) {
        super("Logger", "logger", array);
    }
    
    public static LoggerPatternConverter newInstance(final String[] array) {
        if (array == null || array.length == 0) {
            return LoggerPatternConverter.INSTANCE;
        }
        return new LoggerPatternConverter(array);
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        sb.append(this.abbreviate(logEvent.getLoggerName()));
    }
    
    static {
        INSTANCE = new LoggerPatternConverter(null);
    }
}
