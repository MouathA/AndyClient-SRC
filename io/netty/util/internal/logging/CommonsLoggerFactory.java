package io.netty.util.internal.logging;

import java.util.*;
import org.apache.commons.logging.*;

public class CommonsLoggerFactory extends InternalLoggerFactory
{
    Map loggerMap;
    
    public CommonsLoggerFactory() {
        this.loggerMap = new HashMap();
    }
    
    public InternalLogger newInstance(final String s) {
        return new CommonsLogger(LogFactory.getLog(s), s);
    }
}
