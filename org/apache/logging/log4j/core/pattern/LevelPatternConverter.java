package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import java.util.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "LevelPatternConverter", category = "Converter")
@ConverterKeys({ "p", "level" })
public final class LevelPatternConverter extends LogEventPatternConverter
{
    private static final String OPTION_LENGTH = "length";
    private static final String OPTION_LOWER = "lowerCase";
    private static final LevelPatternConverter INSTANCE;
    private final EnumMap levelMap;
    
    private LevelPatternConverter(final EnumMap levelMap) {
        super("Level", "level");
        this.levelMap = levelMap;
    }
    
    public static LevelPatternConverter newInstance(final String[] array) {
        if (array == null || array.length == 0) {
            return LevelPatternConverter.INSTANCE;
        }
        final EnumMap<Level, String> enumMap = new EnumMap<Level, String>(Level.class);
        final String[] split = array[0].split(",");
        int n = 0;
        while (0 < split.length) {
            final String s = split[0];
            final String[] split2 = s.split("=");
            if (split2 == null || split2.length != 2) {
                LevelPatternConverter.LOGGER.error("Invalid option {}", s);
            }
            else {
                final String trim = split2[0].trim();
                final String trim2 = split2[1].trim();
                if ("length".equalsIgnoreCase(trim)) {
                    Integer.parseInt(trim2);
                }
                else if ("lowerCase".equalsIgnoreCase(trim)) {
                    Boolean.parseBoolean(trim2);
                }
                else {
                    final Level level = Level.toLevel(trim, null);
                    if (level == null) {
                        LevelPatternConverter.LOGGER.error("Invalid Level {}", trim);
                    }
                    else {
                        enumMap.put(level, trim2);
                    }
                }
            }
            ++n;
        }
        if (enumMap.size() == 0) {
            return LevelPatternConverter.INSTANCE;
        }
        final Level[] values = Level.values();
        while (0 < values.length) {
            final Level level2 = values[0];
            if (!enumMap.containsKey(level2)) {
                enumMap.put(level2, left(level2, Integer.MAX_VALUE));
            }
            ++n;
        }
        return new LevelPatternConverter(enumMap);
    }
    
    private static String left(final Level level, final int n) {
        final String string = level.toString();
        if (n >= string.length()) {
            return string;
        }
        return string.substring(0, n);
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        sb.append((this.levelMap == null) ? logEvent.getLevel().toString() : this.levelMap.get(logEvent.getLevel()));
    }
    
    @Override
    public String getStyleClass(final Object o) {
        if (!(o instanceof LogEvent)) {
            return "level";
        }
        switch (((LogEvent)o).getLevel()) {
            case TRACE: {
                return "level trace";
            }
            case DEBUG: {
                return "level debug";
            }
            case INFO: {
                return "level info";
            }
            case WARN: {
                return "level warn";
            }
            case ERROR: {
                return "level error";
            }
            case FATAL: {
                return "level fatal";
            }
            default: {
                return "level " + ((LogEvent)o).getLevel().toString();
            }
        }
    }
    
    static {
        INSTANCE = new LevelPatternConverter(null);
    }
}
