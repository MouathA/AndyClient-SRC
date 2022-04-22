package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.*;
import java.util.*;

@Plugin(name = "highlight", category = "Converter")
@ConverterKeys({ "highlight" })
public final class HighlightConverter extends LogEventPatternConverter
{
    private static final EnumMap DEFAULT_STYLES;
    private static final EnumMap LOGBACK_STYLES;
    private static final String STYLE_KEY = "STYLE";
    private static final String STYLE_KEY_DEFAULT = "DEFAULT";
    private static final String STYLE_KEY_LOGBACK = "LOGBACK";
    private static final Map STYLES;
    private final EnumMap levelStyles;
    private final List patternFormatters;
    
    private static EnumMap createLevelStyleMap(final String[] array) {
        if (array.length < 2) {
            return HighlightConverter.DEFAULT_STYLES;
        }
        final Map map = AnsiEscape.createMap(array[1], new String[] { "STYLE" });
        final EnumMap<Level, String> enumMap = new EnumMap<Level, String>(HighlightConverter.DEFAULT_STYLES);
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            final String upperCase = entry.getKey().toUpperCase(Locale.ENGLISH);
            final String s = (String)entry.getValue();
            if ("STYLE".equalsIgnoreCase(upperCase)) {
                final EnumMap<? extends Level, ? extends String> enumMap2 = HighlightConverter.STYLES.get(s.toUpperCase(Locale.ENGLISH));
                if (enumMap2 == null) {
                    HighlightConverter.LOGGER.error("Unknown level style: " + s + ". Use one of " + Arrays.toString(HighlightConverter.STYLES.keySet().toArray()));
                }
                else {
                    enumMap.putAll(enumMap2);
                }
            }
            else {
                final Level value = Level.valueOf(upperCase);
                if (value == null) {
                    HighlightConverter.LOGGER.error("Unknown level name: " + upperCase + ". Use one of " + Arrays.toString(HighlightConverter.DEFAULT_STYLES.keySet().toArray()));
                }
                else {
                    enumMap.put(value, s);
                }
            }
        }
        return enumMap;
    }
    
    public static HighlightConverter newInstance(final Configuration configuration, final String[] array) {
        if (array.length < 1) {
            HighlightConverter.LOGGER.error("Incorrect number of options on style. Expected at least 1, received " + array.length);
            return null;
        }
        if (array[0] == null) {
            HighlightConverter.LOGGER.error("No pattern supplied on style");
            return null;
        }
        return new HighlightConverter(PatternLayout.createPatternParser(configuration).parse(array[0]), createLevelStyleMap(array));
    }
    
    private HighlightConverter(final List patternFormatters, final EnumMap levelStyles) {
        super("style", "style");
        this.patternFormatters = patternFormatters;
        this.levelStyles = levelStyles;
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final StringBuilder sb2 = new StringBuilder();
        final Iterator<PatternFormatter> iterator = this.patternFormatters.iterator();
        while (iterator.hasNext()) {
            iterator.next().format(logEvent, sb2);
        }
        if (sb2.length() > 0) {
            sb.append(this.levelStyles.get(logEvent.getLevel())).append(sb2.toString()).append(AnsiEscape.getDefaultStyle());
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
    
    static {
        DEFAULT_STYLES = new EnumMap((Class<K>)Level.class);
        LOGBACK_STYLES = new EnumMap((Class<K>)Level.class);
        STYLES = new HashMap();
        HighlightConverter.DEFAULT_STYLES.put(Level.FATAL, AnsiEscape.createSequence("BRIGHT", "RED"));
        HighlightConverter.DEFAULT_STYLES.put(Level.ERROR, AnsiEscape.createSequence("BRIGHT", "RED"));
        HighlightConverter.DEFAULT_STYLES.put(Level.WARN, AnsiEscape.createSequence("YELLOW"));
        HighlightConverter.DEFAULT_STYLES.put(Level.INFO, AnsiEscape.createSequence("GREEN"));
        HighlightConverter.DEFAULT_STYLES.put(Level.DEBUG, AnsiEscape.createSequence("CYAN"));
        HighlightConverter.DEFAULT_STYLES.put(Level.TRACE, AnsiEscape.createSequence("BLACK"));
        HighlightConverter.LOGBACK_STYLES.put(Level.FATAL, AnsiEscape.createSequence("BLINK", "BRIGHT", "RED"));
        HighlightConverter.LOGBACK_STYLES.put(Level.ERROR, AnsiEscape.createSequence("BRIGHT", "RED"));
        HighlightConverter.LOGBACK_STYLES.put(Level.WARN, AnsiEscape.createSequence("RED"));
        HighlightConverter.LOGBACK_STYLES.put(Level.INFO, AnsiEscape.createSequence("BLUE"));
        HighlightConverter.LOGBACK_STYLES.put(Level.DEBUG, AnsiEscape.createSequence((String[])null));
        HighlightConverter.LOGBACK_STYLES.put(Level.TRACE, AnsiEscape.createSequence((String[])null));
        HighlightConverter.STYLES.put("DEFAULT", HighlightConverter.DEFAULT_STYLES);
        HighlightConverter.STYLES.put("LOGBACK", HighlightConverter.LOGBACK_STYLES);
    }
}
