package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "FileDatePatternConverter", category = "FileConverter")
@ConverterKeys({ "d", "date" })
public final class FileDatePatternConverter
{
    private FileDatePatternConverter() {
    }
    
    public static PatternConverter newInstance(final String[] array) {
        if (array == null || array.length == 0) {
            return DatePatternConverter.newInstance(new String[] { "yyyy-MM-dd" });
        }
        return DatePatternConverter.newInstance(array);
    }
}
