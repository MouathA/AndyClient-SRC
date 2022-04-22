package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.*;
import java.util.*;
import org.apache.logging.log4j.core.config.plugins.*;

public abstract class AbstractStyleNameConverter extends LogEventPatternConverter
{
    private final List formatters;
    private final String style;
    
    protected AbstractStyleNameConverter(final String s, final List formatters, final String style) {
        super(s, "style");
        this.formatters = formatters;
        this.style = style;
    }
    
    protected static AbstractStyleNameConverter newInstance(final Class clazz, final String s, final Configuration configuration, final String[] array) {
        final List patternFormatterList = toPatternFormatterList(configuration, array);
        if (patternFormatterList == null) {
            return null;
        }
        return clazz.getConstructor(List.class, String.class).newInstance(patternFormatterList, AnsiEscape.createSequence(s));
    }
    
    private static List toPatternFormatterList(final Configuration configuration, final String[] array) {
        if (array.length == 0 || array[0] == null) {
            AbstractStyleNameConverter.LOGGER.error("No pattern supplied on style for config=" + configuration);
            return null;
        }
        final PatternParser patternParser = PatternLayout.createPatternParser(configuration);
        if (patternParser == null) {
            AbstractStyleNameConverter.LOGGER.error("No PatternParser created for config=" + configuration + ", options=" + Arrays.toString(array));
            return null;
        }
        return patternParser.parse(array[0]);
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final StringBuilder sb2 = new StringBuilder();
        final Iterator<PatternFormatter> iterator = this.formatters.iterator();
        while (iterator.hasNext()) {
            iterator.next().format(logEvent, sb2);
        }
        if (sb2.length() > 0) {
            sb.append(this.style).append(sb2.toString()).append(AnsiEscape.getDefaultStyle());
        }
    }
    
    @Plugin(name = "yellow", category = "Converter")
    @ConverterKeys({ "yellow" })
    public static final class Yellow extends AbstractStyleNameConverter
    {
        protected static final String NAME = "yellow";
        
        public Yellow(final List list, final String s) {
            super("yellow", list, s);
        }
        
        public static Yellow newInstance(final Configuration configuration, final String[] array) {
            return (Yellow)AbstractStyleNameConverter.newInstance(Yellow.class, "yellow", configuration, array);
        }
    }
    
    @Plugin(name = "white", category = "Converter")
    @ConverterKeys({ "white" })
    public static final class White extends AbstractStyleNameConverter
    {
        protected static final String NAME = "white";
        
        public White(final List list, final String s) {
            super("white", list, s);
        }
        
        public static White newInstance(final Configuration configuration, final String[] array) {
            return (White)AbstractStyleNameConverter.newInstance(White.class, "white", configuration, array);
        }
    }
    
    @Plugin(name = "red", category = "Converter")
    @ConverterKeys({ "red" })
    public static final class Red extends AbstractStyleNameConverter
    {
        protected static final String NAME = "red";
        
        public Red(final List list, final String s) {
            super("red", list, s);
        }
        
        public static Red newInstance(final Configuration configuration, final String[] array) {
            return (Red)AbstractStyleNameConverter.newInstance(Red.class, "red", configuration, array);
        }
    }
    
    @Plugin(name = "magenta", category = "Converter")
    @ConverterKeys({ "magenta" })
    public static final class Magenta extends AbstractStyleNameConverter
    {
        protected static final String NAME = "magenta";
        
        public Magenta(final List list, final String s) {
            super("magenta", list, s);
        }
        
        public static Magenta newInstance(final Configuration configuration, final String[] array) {
            return (Magenta)AbstractStyleNameConverter.newInstance(Magenta.class, "magenta", configuration, array);
        }
    }
    
    @Plugin(name = "green", category = "Converter")
    @ConverterKeys({ "green" })
    public static final class Green extends AbstractStyleNameConverter
    {
        protected static final String NAME = "green";
        
        public Green(final List list, final String s) {
            super("green", list, s);
        }
        
        public static Green newInstance(final Configuration configuration, final String[] array) {
            return (Green)AbstractStyleNameConverter.newInstance(Green.class, "green", configuration, array);
        }
    }
    
    @Plugin(name = "cyan", category = "Converter")
    @ConverterKeys({ "cyan" })
    public static final class Cyan extends AbstractStyleNameConverter
    {
        protected static final String NAME = "cyan";
        
        public Cyan(final List list, final String s) {
            super("cyan", list, s);
        }
        
        public static Cyan newInstance(final Configuration configuration, final String[] array) {
            return (Cyan)AbstractStyleNameConverter.newInstance(Cyan.class, "cyan", configuration, array);
        }
    }
    
    @Plugin(name = "blue", category = "Converter")
    @ConverterKeys({ "blue" })
    public static final class Blue extends AbstractStyleNameConverter
    {
        protected static final String NAME = "blue";
        
        public Blue(final List list, final String s) {
            super("blue", list, s);
        }
        
        public static Blue newInstance(final Configuration configuration, final String[] array) {
            return (Blue)AbstractStyleNameConverter.newInstance(Blue.class, "blue", configuration, array);
        }
    }
    
    @Plugin(name = "black", category = "Converter")
    @ConverterKeys({ "black" })
    public static final class Black extends AbstractStyleNameConverter
    {
        protected static final String NAME = "black";
        
        public Black(final List list, final String s) {
            super("black", list, s);
        }
        
        public static Black newInstance(final Configuration configuration, final String[] array) {
            return (Black)AbstractStyleNameConverter.newInstance(Black.class, "black", configuration, array);
        }
    }
}
