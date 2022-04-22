package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.*;

public abstract class LogEventPatternConverter extends AbstractPatternConverter
{
    protected LogEventPatternConverter(final String s, final String s2) {
        super(s, s2);
    }
    
    public abstract void format(final LogEvent p0, final StringBuilder p1);
    
    @Override
    public void format(final Object o, final StringBuilder sb) {
        if (o instanceof LogEvent) {
            this.format((LogEvent)o, sb);
        }
    }
    
    public boolean handlesThrowable() {
        return false;
    }
}
