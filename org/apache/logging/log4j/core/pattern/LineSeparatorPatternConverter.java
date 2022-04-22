package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "LineSeparatorPatternConverter", category = "Converter")
@ConverterKeys({ "n" })
public final class LineSeparatorPatternConverter extends LogEventPatternConverter
{
    private static final LineSeparatorPatternConverter INSTANCE;
    private final String lineSep;
    
    private LineSeparatorPatternConverter() {
        super("Line Sep", "lineSep");
        this.lineSep = Constants.LINE_SEP;
    }
    
    public static LineSeparatorPatternConverter newInstance(final String[] array) {
        return LineSeparatorPatternConverter.INSTANCE;
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        sb.append(this.lineSep);
    }
    
    static {
        INSTANCE = new LineSeparatorPatternConverter();
    }
}
