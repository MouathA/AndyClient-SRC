package org.apache.logging.log4j.simple;

import org.apache.logging.log4j.spi.*;
import org.apache.logging.log4j.util.*;
import java.text.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.*;
import java.io.*;
import java.util.*;

public class SimpleLogger extends AbstractLogger
{
    private static final char SPACE = ' ';
    private DateFormat dateFormatter;
    private Level level;
    private final boolean showDateTime;
    private final boolean showContextMap;
    private PrintStream stream;
    private final String logName;
    
    public SimpleLogger(final String s, final Level level, final boolean b, final boolean b2, final boolean showDateTime, final boolean showContextMap, final String s2, final MessageFactory messageFactory, final PropertiesUtil propertiesUtil, final PrintStream stream) {
        super(s, messageFactory);
        this.level = Level.toLevel(propertiesUtil.getStringProperty("org.apache.logging.log4j.simplelog." + s + ".level"), level);
        if (b2) {
            final int lastIndex = s.lastIndexOf(".");
            if (lastIndex > 0 && lastIndex < s.length()) {
                this.logName = s.substring(lastIndex + 1);
            }
            else {
                this.logName = s;
            }
        }
        else if (b) {
            this.logName = s;
        }
        else {
            this.logName = null;
        }
        this.showDateTime = showDateTime;
        this.showContextMap = showContextMap;
        this.stream = stream;
        if (showDateTime) {
            this.dateFormatter = new SimpleDateFormat(s2);
        }
    }
    
    public void setStream(final PrintStream stream) {
        this.stream = stream;
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    public void setLevel(final Level level) {
        if (level != null) {
            this.level = level;
        }
    }
    
    @Override
    public void log(final Marker marker, final String s, final Level level, final Message message, final Throwable t) {
        final StringBuilder sb = new StringBuilder();
        if (this.showDateTime) {
            final Date date = new Date();
            // monitorenter(dateFormatter = this.dateFormatter)
            final String format = this.dateFormatter.format(date);
            // monitorexit(dateFormatter)
            sb.append(format);
            sb.append(' ');
        }
        sb.append(level.toString());
        sb.append(' ');
        if (this.logName != null && this.logName.length() > 0) {
            sb.append(this.logName);
            sb.append(' ');
        }
        sb.append(message.getFormattedMessage());
        if (this.showContextMap) {
            final Map context = ThreadContext.getContext();
            if (context.size() > 0) {
                sb.append(' ');
                sb.append(context.toString());
                sb.append(' ');
            }
        }
        final Object[] parameters = message.getParameters();
        Throwable t2;
        if (t == null && parameters != null && parameters[parameters.length - 1] instanceof Throwable) {
            t2 = (Throwable)parameters[parameters.length - 1];
        }
        else {
            t2 = t;
        }
        if (t2 != null) {
            sb.append(' ');
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            t2.printStackTrace(new PrintStream(byteArrayOutputStream));
            sb.append(byteArrayOutputStream.toString());
        }
        this.stream.println(sb.toString());
    }
    
    @Override
    protected boolean isEnabled(final Level level, final Marker marker, final String s) {
        return this.level.intLevel() >= level.intLevel();
    }
    
    @Override
    protected boolean isEnabled(final Level level, final Marker marker, final String s, final Throwable t) {
        return this.level.intLevel() >= level.intLevel();
    }
    
    @Override
    protected boolean isEnabled(final Level level, final Marker marker, final String s, final Object... array) {
        return this.level.intLevel() >= level.intLevel();
    }
    
    @Override
    protected boolean isEnabled(final Level level, final Marker marker, final Object o, final Throwable t) {
        return this.level.intLevel() >= level.intLevel();
    }
    
    @Override
    protected boolean isEnabled(final Level level, final Marker marker, final Message message, final Throwable t) {
        return this.level.intLevel() >= level.intLevel();
    }
}
