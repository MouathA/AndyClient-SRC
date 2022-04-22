package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "env", category = "Lookup")
public class EnvironmentLookup implements StrLookup
{
    @Override
    public String lookup(final String s) {
        return System.getenv(s);
    }
    
    @Override
    public String lookup(final LogEvent logEvent, final String s) {
        return System.getenv(s);
    }
}
