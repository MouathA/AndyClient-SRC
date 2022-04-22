package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;
import java.util.*;

@Plugin(name = "MDCPatternConverter", category = "Converter")
@ConverterKeys({ "X", "mdc", "MDC" })
public final class MDCPatternConverter extends LogEventPatternConverter
{
    private final String key;
    
    private MDCPatternConverter(final String[] array) {
        super((array != null && array.length > 0) ? ("MDC{" + array[0] + "}") : "MDC", "mdc");
        this.key = ((array != null && array.length > 0) ? array[0] : null);
    }
    
    public static MDCPatternConverter newInstance(final String[] array) {
        return new MDCPatternConverter(array);
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final Map contextMap = logEvent.getContextMap();
        if (this.key == null) {
            if (contextMap == null || contextMap.size() == 0) {
                sb.append("{}");
                return;
            }
            final StringBuilder sb2 = new StringBuilder("{");
            for (final String s : new TreeSet<Object>(contextMap.keySet())) {
                if (sb2.length() > 1) {
                    sb2.append(", ");
                }
                sb2.append(s).append("=").append(contextMap.get(s));
            }
            sb2.append("}");
            sb.append((CharSequence)sb2);
        }
        else if (contextMap != null) {
            final String value = contextMap.get(this.key);
            if (value != null) {
                sb.append((Object)value);
            }
        }
    }
}
