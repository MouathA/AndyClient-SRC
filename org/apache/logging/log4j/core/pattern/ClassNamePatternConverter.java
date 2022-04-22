package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "ClassNamePatternConverter", category = "Converter")
@ConverterKeys({ "C", "class" })
public final class ClassNamePatternConverter extends NamePatternConverter
{
    private static final String NA = "?";
    
    private ClassNamePatternConverter(final String[] array) {
        super("Class Name", "class name", array);
    }
    
    public static ClassNamePatternConverter newInstance(final String[] array) {
        return new ClassNamePatternConverter(array);
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final StackTraceElement source = logEvent.getSource();
        if (source == null) {
            sb.append("?");
        }
        else {
            sb.append(this.abbreviate(source.getClassName()));
        }
    }
}
