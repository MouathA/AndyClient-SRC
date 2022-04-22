package org.apache.logging.log4j.core.impl;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import java.util.*;
import org.apache.logging.log4j.core.*;

public class DefaultLogEventFactory implements LogEventFactory
{
    @Override
    public LogEvent createEvent(final String s, final Marker marker, final String s2, final Level level, final Message message, final List list, final Throwable t) {
        return new Log4jLogEvent(s, marker, s2, level, message, list, t);
    }
}
