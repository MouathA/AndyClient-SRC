package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import java.util.regex.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.*;
import java.util.*;

@Plugin(name = "replace", category = "Converter")
@ConverterKeys({ "replace" })
public final class RegexReplacementConverter extends LogEventPatternConverter
{
    private final Pattern pattern;
    private final String substitution;
    private final List formatters;
    
    private RegexReplacementConverter(final List formatters, final Pattern pattern, final String substitution) {
        super("replace", "replace");
        this.pattern = pattern;
        this.substitution = substitution;
        this.formatters = formatters;
    }
    
    public static RegexReplacementConverter newInstance(final Configuration configuration, final String[] array) {
        if (array.length != 3) {
            RegexReplacementConverter.LOGGER.error("Incorrect number of options on replace. Expected 3 received " + array.length);
            return null;
        }
        if (array[0] == null) {
            RegexReplacementConverter.LOGGER.error("No pattern supplied on replace");
            return null;
        }
        if (array[1] == null) {
            RegexReplacementConverter.LOGGER.error("No regular expression supplied on replace");
            return null;
        }
        if (array[2] == null) {
            RegexReplacementConverter.LOGGER.error("No substitution supplied on replace");
            return null;
        }
        return new RegexReplacementConverter(PatternLayout.createPatternParser(configuration).parse(array[0]), Pattern.compile(array[1]), array[2]);
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final StringBuilder sb2 = new StringBuilder();
        final Iterator<PatternFormatter> iterator = this.formatters.iterator();
        while (iterator.hasNext()) {
            iterator.next().format(logEvent, sb2);
        }
        sb.append(this.pattern.matcher(sb2.toString()).replaceAll(this.substitution));
    }
}
