package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;
import javax.naming.*;

@Plugin(name = "jndi", category = "Lookup")
public class JndiLookup implements StrLookup
{
    static final String CONTAINER_JNDI_RESOURCE_PATH_PREFIX = "java:comp/env/";
    
    @Override
    public String lookup(final String s) {
        return this.lookup(null, s);
    }
    
    @Override
    public String lookup(final LogEvent logEvent, final String s) {
        if (s == null) {
            return null;
        }
        return (String)new InitialContext().lookup(this.convertJndiName(s));
    }
    
    private String convertJndiName(String string) {
        if (!string.startsWith("java:comp/env/") && string.indexOf(58) == -1) {
            string = "java:comp/env/" + string;
        }
        return string;
    }
}
