package org.apache.logging.log4j.status;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import java.text.*;
import java.util.*;
import java.io.*;

public class StatusData implements Serializable
{
    private static final long serialVersionUID = -4341916115118014017L;
    private final long timestamp;
    private final StackTraceElement caller;
    private final Level level;
    private final Message msg;
    private final Throwable throwable;
    
    public StatusData(final StackTraceElement caller, final Level level, final Message msg, final Throwable throwable) {
        this.timestamp = System.currentTimeMillis();
        this.caller = caller;
        this.level = level;
        this.msg = msg;
        this.throwable = throwable;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public StackTraceElement getStackTraceElement() {
        return this.caller;
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    public Message getMessage() {
        return this.msg;
    }
    
    public Throwable getThrowable() {
        return this.throwable;
    }
    
    public String getFormattedStatus() {
        final StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(new Date(this.timestamp)));
        sb.append(" ");
        sb.append(this.level.toString());
        sb.append(" ");
        sb.append(this.msg.getFormattedMessage());
        final Object[] parameters = this.msg.getParameters();
        Throwable throwable;
        if (this.throwable == null && parameters != null && parameters[parameters.length - 1] instanceof Throwable) {
            throwable = (Throwable)parameters[parameters.length - 1];
        }
        else {
            throwable = this.throwable;
        }
        if (throwable != null) {
            sb.append(" ");
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            throwable.printStackTrace(new PrintStream(byteArrayOutputStream));
            sb.append(byteArrayOutputStream.toString());
        }
        return sb.toString();
    }
}
