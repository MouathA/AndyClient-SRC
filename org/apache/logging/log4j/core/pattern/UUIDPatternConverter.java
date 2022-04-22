package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;
import java.util.*;
import org.apache.logging.log4j.core.helpers.*;

@Plugin(name = "UUIDPatternConverter", category = "Converter")
@ConverterKeys({ "u", "uuid" })
public final class UUIDPatternConverter extends LogEventPatternConverter
{
    private final boolean isRandom;
    
    private UUIDPatternConverter(final boolean isRandom) {
        super("u", "uuid");
        this.isRandom = isRandom;
    }
    
    public static UUIDPatternConverter newInstance(final String[] array) {
        if (array.length == 0) {
            return new UUIDPatternConverter(false);
        }
        if (array.length > 1 || (!array[0].equalsIgnoreCase("RANDOM") && !array[0].equalsIgnoreCase("Time"))) {
            UUIDPatternConverter.LOGGER.error("UUID Pattern Converter only accepts a single option with the value \"RANDOM\" or \"TIME\"");
        }
        return new UUIDPatternConverter(array[0].equalsIgnoreCase("RANDOM"));
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        sb.append((this.isRandom ? UUID.randomUUID() : UUIDUtil.getTimeBasedUUID()).toString());
    }
}
