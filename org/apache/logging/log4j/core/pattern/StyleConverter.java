package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.*;
import java.util.*;

@Plugin(name = "style", category = "Converter")
@ConverterKeys({ "style" })
public final class StyleConverter extends LogEventPatternConverter
{
    private final List patternFormatters;
    private final String style;
    
    private StyleConverter(final List patternFormatters, final String style) {
        super("style", "style");
        this.patternFormatters = patternFormatters;
        this.style = style;
    }
    
    public static StyleConverter newInstance(final Configuration configuration, final String[] array) {
        if (array.length < 1) {
            StyleConverter.LOGGER.error("Incorrect number of options on style. Expected at least 1, received " + array.length);
            return null;
        }
        if (array[0] == null) {
            StyleConverter.LOGGER.error("No pattern supplied on style");
            return null;
        }
        if (array[1] == null) {
            StyleConverter.LOGGER.error("No style attributes provided");
            return null;
        }
        return new StyleConverter(PatternLayout.createPatternParser(configuration).parse(array[0]), AnsiEscape.createSequence(array[1].split("\\s*,\\s*")));
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final StringBuilder sb2 = new StringBuilder();
        final Iterator<PatternFormatter> iterator = this.patternFormatters.iterator();
        while (iterator.hasNext()) {
            iterator.next().format(logEvent, sb2);
        }
        if (sb2.length() > 0) {
            sb.append(this.style).append(sb2.toString()).append(AnsiEscape.getDefaultStyle());
        }
    }
    
    @Override
    public boolean handlesThrowable() {
        final Iterator<PatternFormatter> iterator = this.patternFormatters.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().handlesThrowable()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[style=");
        sb.append(this.style);
        sb.append(", patternFormatters=");
        sb.append(this.patternFormatters);
        sb.append("]");
        return sb.toString();
    }
}
