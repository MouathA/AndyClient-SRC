package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.status.*;

public abstract class AbstractPatternConverter implements PatternConverter
{
    protected static final Logger LOGGER;
    private final String name;
    private final String style;
    
    protected AbstractPatternConverter(final String name, final String style) {
        this.name = name;
        this.style = style;
    }
    
    @Override
    public final String getName() {
        return this.name;
    }
    
    @Override
    public String getStyleClass(final Object o) {
        return this.style;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
