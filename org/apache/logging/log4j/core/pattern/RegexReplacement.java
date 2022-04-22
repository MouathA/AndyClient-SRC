package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.*;
import java.util.regex.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "replace", category = "Core", printObject = true)
public final class RegexReplacement
{
    private static final Logger LOGGER;
    private final Pattern pattern;
    private final String substitution;
    
    private RegexReplacement(final Pattern pattern, final String substitution) {
        this.pattern = pattern;
        this.substitution = substitution;
    }
    
    public String format(final String s) {
        return this.pattern.matcher(s).replaceAll(this.substitution);
    }
    
    @Override
    public String toString() {
        return "replace(regex=" + this.pattern.pattern() + ", replacement=" + this.substitution + ")";
    }
    
    @PluginFactory
    public static RegexReplacement createRegexReplacement(@PluginAttribute("regex") final String s, @PluginAttribute("replacement") final String s2) {
        if (s == null) {
            RegexReplacement.LOGGER.error("A regular expression is required for replacement");
            return null;
        }
        if (s2 == null) {
            RegexReplacement.LOGGER.error("A replacement string is required to perform replacement");
        }
        return new RegexReplacement(Pattern.compile(s), s2);
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
