package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import java.util.*;

@Plugin(name = "IntegerPatternConverter", category = "FileConverter")
@ConverterKeys({ "i", "index" })
public final class IntegerPatternConverter extends AbstractPatternConverter implements ArrayPatternConverter
{
    private static final IntegerPatternConverter INSTANCE;
    
    private IntegerPatternConverter() {
        super("Integer", "integer");
    }
    
    public static IntegerPatternConverter newInstance(final String[] array) {
        return IntegerPatternConverter.INSTANCE;
    }
    
    @Override
    public void format(final StringBuilder sb, final Object... array) {
        while (0 < array.length) {
            final Object o = array[0];
            if (o instanceof Integer) {
                this.format(o, sb);
                break;
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void format(final Object o, final StringBuilder sb) {
        if (o instanceof Integer) {
            sb.append(o.toString());
        }
        if (o instanceof Date) {
            sb.append(Long.toString(((Date)o).getTime()));
        }
    }
    
    static {
        INSTANCE = new IntegerPatternConverter();
    }
}
