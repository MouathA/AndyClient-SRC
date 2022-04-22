package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "ctx", category = "Lookup")
public class ContextMapLookup implements StrLookup
{
    @Override
    public String lookup(final String s) {
        return ThreadContext.get(s);
    }
    
    @Override
    public String lookup(final LogEvent logEvent, final String s) {
        return logEvent.getContextMap().get(s);
    }
}
