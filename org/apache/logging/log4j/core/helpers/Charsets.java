package org.apache.logging.log4j.core.helpers;

import java.nio.charset.*;
import org.apache.logging.log4j.status.*;

public final class Charsets
{
    public static final Charset UTF_8;
    
    public static Charset getSupportedCharset(final String s) {
        return getSupportedCharset(s, Charset.defaultCharset());
    }
    
    public static Charset getSupportedCharset(final String s, final Charset charset) {
        Charset forName = null;
        if (s != null && Charset.isSupported(s)) {
            forName = Charset.forName(s);
        }
        if (forName == null) {
            forName = charset;
            if (s != null) {
                StatusLogger.getLogger().error("Charset " + s + " is not supported for layout, using " + forName.displayName());
            }
        }
        return forName;
    }
    
    private Charsets() {
    }
    
    static {
        UTF_8 = Charset.forName("UTF-8");
    }
}
