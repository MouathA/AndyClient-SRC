package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.message.*;
import java.util.*;

@Plugin(name = "MapPatternConverter", category = "Converter")
@ConverterKeys({ "K", "map", "MAP" })
public final class MapPatternConverter extends LogEventPatternConverter
{
    private final String key;
    
    private MapPatternConverter(final String[] array) {
        super((array != null && array.length > 0) ? ("MAP{" + array[0] + "}") : "MAP", "map");
        this.key = ((array != null && array.length > 0) ? array[0] : null);
    }
    
    public static MapPatternConverter newInstance(final String[] array) {
        return new MapPatternConverter(array);
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        if (logEvent.getMessage() instanceof MapMessage) {
            final Map data = ((MapMessage)logEvent.getMessage()).getData();
            if (this.key == null) {
                if (data.size() == 0) {
                    sb.append("{}");
                    return;
                }
                final StringBuilder sb2 = new StringBuilder("{");
                for (final String s : new TreeSet<Object>(data.keySet())) {
                    if (sb2.length() > 1) {
                        sb2.append(", ");
                    }
                    sb2.append(s).append("=").append(data.get(s));
                }
                sb2.append("}");
                sb.append((CharSequence)sb2);
            }
            else {
                final String s2 = data.get(this.key);
                if (s2 != null) {
                    sb.append(s2);
                }
            }
        }
    }
}
