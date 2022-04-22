package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.message.*;

@Plugin(name = "sd", category = "Lookup")
public class StructuredDataLookup implements StrLookup
{
    @Override
    public String lookup(final String s) {
        return null;
    }
    
    @Override
    public String lookup(final LogEvent logEvent, final String s) {
        if (logEvent == null || !(logEvent.getMessage() instanceof StructuredDataMessage)) {
            return null;
        }
        final StructuredDataMessage structuredDataMessage = (StructuredDataMessage)logEvent.getMessage();
        if (s.equalsIgnoreCase("id")) {
            return structuredDataMessage.getId().getName();
        }
        if (s.equalsIgnoreCase("type")) {
            return structuredDataMessage.getType();
        }
        return structuredDataMessage.get(s);
    }
}
