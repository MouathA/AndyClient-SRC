package org.apache.logging.log4j.core.layout;

import org.apache.logging.log4j.core.config.*;
import java.nio.charset.*;
import org.apache.logging.log4j.core.*;
import java.util.*;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.io.*;

@Plugin(name = "PatternLayout", category = "Core", elementType = "layout", printObject = true)
public final class PatternLayout extends AbstractStringLayout
{
    public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
    public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %x - %m%n";
    public static final String SIMPLE_CONVERSION_PATTERN = "%d [%t] %p %c - %m%n";
    public static final String KEY = "Converter";
    private List formatters;
    private final String conversionPattern;
    private final Configuration config;
    private final RegexReplacement replace;
    private final boolean alwaysWriteExceptions;
    
    private PatternLayout(final Configuration config, final RegexReplacement replace, final String conversionPattern, final Charset charset, final boolean alwaysWriteExceptions) {
        super(charset);
        this.replace = replace;
        this.conversionPattern = conversionPattern;
        this.config = config;
        this.alwaysWriteExceptions = alwaysWriteExceptions;
        this.formatters = createPatternParser(config).parse((conversionPattern == null) ? "%m%n" : conversionPattern, this.alwaysWriteExceptions);
    }
    
    public void setConversionPattern(final String s) {
        final String convertSpecialChars = OptionConverter.convertSpecialChars(s);
        if (convertSpecialChars == null) {
            return;
        }
        this.formatters = createPatternParser(this.config).parse(convertSpecialChars, this.alwaysWriteExceptions);
    }
    
    public String getConversionPattern() {
        return this.conversionPattern;
    }
    
    @Override
    public Map getContentFormat() {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("structured", "false");
        hashMap.put("formatType", "conversion");
        hashMap.put("format", this.conversionPattern);
        return hashMap;
    }
    
    @Override
    public String toSerializable(final LogEvent logEvent) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<PatternFormatter> iterator = this.formatters.iterator();
        while (iterator.hasNext()) {
            iterator.next().format(logEvent, sb);
        }
        String s = sb.toString();
        if (this.replace != null) {
            s = this.replace.format(s);
        }
        return s;
    }
    
    public static PatternParser createPatternParser(final Configuration configuration) {
        if (configuration == null) {
            return new PatternParser(configuration, "Converter", LogEventPatternConverter.class);
        }
        PatternParser patternParser = (PatternParser)configuration.getComponent("Converter");
        if (patternParser == null) {
            configuration.addComponent("Converter", new PatternParser(configuration, "Converter", LogEventPatternConverter.class));
            patternParser = (PatternParser)configuration.getComponent("Converter");
        }
        return patternParser;
    }
    
    @Override
    public String toString() {
        return this.conversionPattern;
    }
    
    @PluginFactory
    public static PatternLayout createLayout(@PluginAttribute("pattern") final String s, @PluginConfiguration final Configuration configuration, @PluginElement("Replace") final RegexReplacement regexReplacement, @PluginAttribute("charset") final String s2, @PluginAttribute("alwaysWriteExceptions") final String s3) {
        return new PatternLayout(configuration, regexReplacement, (s == null) ? "%m%n" : s, Charsets.getSupportedCharset(s2), Booleans.parseBoolean(s3, true));
    }
    
    @Override
    public Serializable toSerializable(final LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }
}
