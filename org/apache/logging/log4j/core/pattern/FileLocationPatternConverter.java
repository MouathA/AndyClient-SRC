package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "FileLocationPatternConverter", category = "Converter")
@ConverterKeys({ "F", "file" })
public final class FileLocationPatternConverter extends LogEventPatternConverter
{
    private static final FileLocationPatternConverter INSTANCE;
    
    private FileLocationPatternConverter() {
        super("File Location", "file");
    }
    
    public static FileLocationPatternConverter newInstance(final String[] array) {
        return FileLocationPatternConverter.INSTANCE;
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final StackTraceElement source = logEvent.getSource();
        if (source != null) {
            sb.append(source.getFileName());
        }
    }
    
    static {
        INSTANCE = new FileLocationPatternConverter();
    }
}
