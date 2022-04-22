package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.status.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;

public abstract class AbstractLogger implements Logger
{
    public static final Marker FLOW_MARKER;
    public static final Marker ENTRY_MARKER;
    public static final Marker EXIT_MARKER;
    public static final Marker EXCEPTION_MARKER;
    public static final Marker THROWING_MARKER;
    public static final Marker CATCHING_MARKER;
    public static final Class DEFAULT_MESSAGE_FACTORY_CLASS;
    private static final String FQCN;
    private static final String THROWING = "throwing";
    private static final String CATCHING = "catching";
    private final String name;
    private final MessageFactory messageFactory;
    
    public AbstractLogger() {
        this.name = this.getClass().getName();
        this.messageFactory = this.createDefaultMessageFactory();
    }
    
    public AbstractLogger(final String name) {
        this.name = name;
        this.messageFactory = this.createDefaultMessageFactory();
    }
    
    public AbstractLogger(final String name, final MessageFactory messageFactory) {
        this.name = name;
        this.messageFactory = ((messageFactory == null) ? this.createDefaultMessageFactory() : messageFactory);
    }
    
    public static void checkMessageFactory(final Logger logger, final MessageFactory messageFactory) {
        final String name = logger.getName();
        final MessageFactory messageFactory2 = logger.getMessageFactory();
        if (messageFactory != null && !messageFactory2.equals(messageFactory)) {
            StatusLogger.getLogger().warn("The Logger {} was created with the message factory {} and is now requested with the message factory {}, which may create log events with unexpected formatting.", name, messageFactory2, messageFactory);
        }
        else if (messageFactory == null && !messageFactory2.getClass().equals(AbstractLogger.DEFAULT_MESSAGE_FACTORY_CLASS)) {
            StatusLogger.getLogger().warn("The Logger {} was created with the message factory {} and is now requested with a null message factory (defaults to {}), which may create log events with unexpected formatting.", name, messageFactory2, AbstractLogger.DEFAULT_MESSAGE_FACTORY_CLASS.getName());
        }
    }
    
    @Override
    public void catching(final Level level, final Throwable t) {
        this.catching(AbstractLogger.FQCN, level, t);
    }
    
    @Override
    public void catching(final Throwable t) {
        this.catching(AbstractLogger.FQCN, Level.ERROR, t);
    }
    
    protected void catching(final String s, final Level level, final Throwable t) {
        if (this.isEnabled(level, AbstractLogger.CATCHING_MARKER, (Object)null, null)) {
            this.log(AbstractLogger.CATCHING_MARKER, s, level, this.messageFactory.newMessage("catching"), t);
        }
    }
    
    private MessageFactory createDefaultMessageFactory() {
        return AbstractLogger.DEFAULT_MESSAGE_FACTORY_CLASS.newInstance();
    }
    
    @Override
    public void debug(final Marker marker, final Message message) {
        if (this.isEnabled(Level.DEBUG, marker, message, null)) {
            this.log(marker, AbstractLogger.FQCN, Level.DEBUG, message, null);
        }
    }
    
    @Override
    public void debug(final Marker marker, final Message message, final Throwable t) {
        if (this.isEnabled(Level.DEBUG, marker, message, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.DEBUG, message, t);
        }
    }
    
    @Override
    public void debug(final Marker marker, final Object o) {
        if (this.isEnabled(Level.DEBUG, marker, o, null)) {
            this.log(marker, AbstractLogger.FQCN, Level.DEBUG, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void debug(final Marker marker, final Object o, final Throwable t) {
        if (this.isEnabled(Level.DEBUG, marker, o, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.DEBUG, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String s) {
        if (this.isEnabled(Level.DEBUG, marker, s)) {
            this.log(marker, AbstractLogger.FQCN, Level.DEBUG, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String s, final Object... array) {
        if (this.isEnabled(Level.DEBUG, marker, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(marker, AbstractLogger.FQCN, Level.DEBUG, message, message.getThrowable());
        }
    }
    
    @Override
    public void debug(final Marker marker, final String s, final Throwable t) {
        if (this.isEnabled(Level.DEBUG, marker, s, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.DEBUG, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public void debug(final Message message) {
        if (this.isEnabled(Level.DEBUG, null, message, null)) {
            this.log(null, AbstractLogger.FQCN, Level.DEBUG, message, null);
        }
    }
    
    @Override
    public void debug(final Message message, final Throwable t) {
        if (this.isEnabled(Level.DEBUG, null, message, t)) {
            this.log(null, AbstractLogger.FQCN, Level.DEBUG, message, t);
        }
    }
    
    @Override
    public void debug(final Object o) {
        if (this.isEnabled(Level.DEBUG, null, o, null)) {
            this.log(null, AbstractLogger.FQCN, Level.DEBUG, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void debug(final Object o, final Throwable t) {
        if (this.isEnabled(Level.DEBUG, null, o, t)) {
            this.log(null, AbstractLogger.FQCN, Level.DEBUG, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void debug(final String s) {
        if (this.isEnabled(Level.DEBUG, null, s)) {
            this.log(null, AbstractLogger.FQCN, Level.DEBUG, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void debug(final String s, final Object... array) {
        if (this.isEnabled(Level.DEBUG, null, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(null, AbstractLogger.FQCN, Level.DEBUG, message, message.getThrowable());
        }
    }
    
    @Override
    public void debug(final String s, final Throwable t) {
        if (this.isEnabled(Level.DEBUG, null, s, t)) {
            this.log(null, AbstractLogger.FQCN, Level.DEBUG, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public void entry() {
        this.entry(AbstractLogger.FQCN, new Object[0]);
    }
    
    @Override
    public void entry(final Object... array) {
        this.entry(AbstractLogger.FQCN, array);
    }
    
    protected void entry(final String s, final Object... array) {
        if (this.isEnabled(Level.TRACE, AbstractLogger.ENTRY_MARKER, (Object)null, null)) {
            this.log(AbstractLogger.ENTRY_MARKER, s, Level.TRACE, this.entryMsg(array.length, array), null);
        }
    }
    
    private Message entryMsg(final int n, final Object... array) {
        if (n == 0) {
            return this.messageFactory.newMessage("entry");
        }
        final StringBuilder sb = new StringBuilder("entry params(");
        while (0 < array.length) {
            final Object o = array[0];
            if (o != null) {
                sb.append(o.toString());
            }
            else {
                sb.append("null");
            }
            int n2 = 0;
            ++n2;
            if (0 < array.length) {
                sb.append(", ");
            }
            int n3 = 0;
            ++n3;
        }
        sb.append(")");
        return this.messageFactory.newMessage(sb.toString());
    }
    
    @Override
    public void error(final Marker marker, final Message message) {
        if (this.isEnabled(Level.ERROR, marker, message, null)) {
            this.log(marker, AbstractLogger.FQCN, Level.ERROR, message, null);
        }
    }
    
    @Override
    public void error(final Marker marker, final Message message, final Throwable t) {
        if (this.isEnabled(Level.ERROR, marker, message, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.ERROR, message, t);
        }
    }
    
    @Override
    public void error(final Marker marker, final Object o) {
        if (this.isEnabled(Level.ERROR, marker, o, null)) {
            this.log(marker, AbstractLogger.FQCN, Level.ERROR, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void error(final Marker marker, final Object o, final Throwable t) {
        if (this.isEnabled(Level.ERROR, marker, o, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.ERROR, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void error(final Marker marker, final String s) {
        if (this.isEnabled(Level.ERROR, marker, s)) {
            this.log(marker, AbstractLogger.FQCN, Level.ERROR, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void error(final Marker marker, final String s, final Object... array) {
        if (this.isEnabled(Level.ERROR, marker, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(marker, AbstractLogger.FQCN, Level.ERROR, message, message.getThrowable());
        }
    }
    
    @Override
    public void error(final Marker marker, final String s, final Throwable t) {
        if (this.isEnabled(Level.ERROR, marker, s, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.ERROR, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public void error(final Message message) {
        if (this.isEnabled(Level.ERROR, null, message, null)) {
            this.log(null, AbstractLogger.FQCN, Level.ERROR, message, null);
        }
    }
    
    @Override
    public void error(final Message message, final Throwable t) {
        if (this.isEnabled(Level.ERROR, null, message, t)) {
            this.log(null, AbstractLogger.FQCN, Level.ERROR, message, t);
        }
    }
    
    @Override
    public void error(final Object o) {
        if (this.isEnabled(Level.ERROR, null, o, null)) {
            this.log(null, AbstractLogger.FQCN, Level.ERROR, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void error(final Object o, final Throwable t) {
        if (this.isEnabled(Level.ERROR, null, o, t)) {
            this.log(null, AbstractLogger.FQCN, Level.ERROR, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void error(final String s) {
        if (this.isEnabled(Level.ERROR, null, s)) {
            this.log(null, AbstractLogger.FQCN, Level.ERROR, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void error(final String s, final Object... array) {
        if (this.isEnabled(Level.ERROR, null, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(null, AbstractLogger.FQCN, Level.ERROR, message, message.getThrowable());
        }
    }
    
    @Override
    public void error(final String s, final Throwable t) {
        if (this.isEnabled(Level.ERROR, null, s, t)) {
            this.log(null, AbstractLogger.FQCN, Level.ERROR, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public void exit() {
        this.exit(AbstractLogger.FQCN, null);
    }
    
    @Override
    public Object exit(final Object o) {
        return this.exit(AbstractLogger.FQCN, o);
    }
    
    protected Object exit(final String s, final Object o) {
        if (this.isEnabled(Level.TRACE, AbstractLogger.EXIT_MARKER, (Object)null, null)) {
            this.log(AbstractLogger.EXIT_MARKER, s, Level.TRACE, this.toExitMsg(o), null);
        }
        return o;
    }
    
    @Override
    public void fatal(final Marker marker, final Message message) {
        if (this.isEnabled(Level.FATAL, marker, message, null)) {
            this.log(marker, AbstractLogger.FQCN, Level.FATAL, message, null);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final Message message, final Throwable t) {
        if (this.isEnabled(Level.FATAL, marker, message, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.FATAL, message, t);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final Object o) {
        if (this.isEnabled(Level.FATAL, marker, o, null)) {
            this.log(marker, AbstractLogger.FQCN, Level.FATAL, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final Object o, final Throwable t) {
        if (this.isEnabled(Level.FATAL, marker, o, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.FATAL, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String s) {
        if (this.isEnabled(Level.FATAL, marker, s)) {
            this.log(marker, AbstractLogger.FQCN, Level.FATAL, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String s, final Object... array) {
        if (this.isEnabled(Level.FATAL, marker, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(marker, AbstractLogger.FQCN, Level.FATAL, message, message.getThrowable());
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String s, final Throwable t) {
        if (this.isEnabled(Level.FATAL, marker, s, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.FATAL, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public void fatal(final Message message) {
        if (this.isEnabled(Level.FATAL, null, message, null)) {
            this.log(null, AbstractLogger.FQCN, Level.FATAL, message, null);
        }
    }
    
    @Override
    public void fatal(final Message message, final Throwable t) {
        if (this.isEnabled(Level.FATAL, null, message, t)) {
            this.log(null, AbstractLogger.FQCN, Level.FATAL, message, t);
        }
    }
    
    @Override
    public void fatal(final Object o) {
        if (this.isEnabled(Level.FATAL, null, o, null)) {
            this.log(null, AbstractLogger.FQCN, Level.FATAL, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void fatal(final Object o, final Throwable t) {
        if (this.isEnabled(Level.FATAL, null, o, t)) {
            this.log(null, AbstractLogger.FQCN, Level.FATAL, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void fatal(final String s) {
        if (this.isEnabled(Level.FATAL, null, s)) {
            this.log(null, AbstractLogger.FQCN, Level.FATAL, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void fatal(final String s, final Object... array) {
        if (this.isEnabled(Level.FATAL, null, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(null, AbstractLogger.FQCN, Level.FATAL, message, message.getThrowable());
        }
    }
    
    @Override
    public void fatal(final String s, final Throwable t) {
        if (this.isEnabled(Level.FATAL, null, s, t)) {
            this.log(null, AbstractLogger.FQCN, Level.FATAL, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public MessageFactory getMessageFactory() {
        return this.messageFactory;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public void info(final Marker marker, final Message message) {
        if (this.isEnabled(Level.INFO, marker, message, null)) {
            this.log(marker, AbstractLogger.FQCN, Level.INFO, message, null);
        }
    }
    
    @Override
    public void info(final Marker marker, final Message message, final Throwable t) {
        if (this.isEnabled(Level.INFO, marker, message, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.INFO, message, t);
        }
    }
    
    @Override
    public void info(final Marker marker, final Object o) {
        if (this.isEnabled(Level.INFO, marker, o, null)) {
            this.log(marker, AbstractLogger.FQCN, Level.INFO, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void info(final Marker marker, final Object o, final Throwable t) {
        if (this.isEnabled(Level.INFO, marker, o, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.INFO, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void info(final Marker marker, final String s) {
        if (this.isEnabled(Level.INFO, marker, s)) {
            this.log(marker, AbstractLogger.FQCN, Level.INFO, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void info(final Marker marker, final String s, final Object... array) {
        if (this.isEnabled(Level.INFO, marker, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(marker, AbstractLogger.FQCN, Level.INFO, message, message.getThrowable());
        }
    }
    
    @Override
    public void info(final Marker marker, final String s, final Throwable t) {
        if (this.isEnabled(Level.INFO, marker, s, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.INFO, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public void info(final Message message) {
        if (this.isEnabled(Level.INFO, null, message, null)) {
            this.log(null, AbstractLogger.FQCN, Level.INFO, message, null);
        }
    }
    
    @Override
    public void info(final Message message, final Throwable t) {
        if (this.isEnabled(Level.INFO, null, message, t)) {
            this.log(null, AbstractLogger.FQCN, Level.INFO, message, t);
        }
    }
    
    @Override
    public void info(final Object o) {
        if (this.isEnabled(Level.INFO, null, o, null)) {
            this.log(null, AbstractLogger.FQCN, Level.INFO, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void info(final Object o, final Throwable t) {
        if (this.isEnabled(Level.INFO, null, o, t)) {
            this.log(null, AbstractLogger.FQCN, Level.INFO, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void info(final String s) {
        if (this.isEnabled(Level.INFO, null, s)) {
            this.log(null, AbstractLogger.FQCN, Level.INFO, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void info(final String s, final Object... array) {
        if (this.isEnabled(Level.INFO, null, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(null, AbstractLogger.FQCN, Level.INFO, message, message.getThrowable());
        }
    }
    
    @Override
    public void info(final String s, final Throwable t) {
        if (this.isEnabled(Level.INFO, null, s, t)) {
            this.log(null, AbstractLogger.FQCN, Level.INFO, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.isEnabled(Level.DEBUG, null, null);
    }
    
    @Override
    public boolean isDebugEnabled(final Marker marker) {
        return this.isEnabled(Level.DEBUG, marker, (Object)null, null);
    }
    
    @Override
    public boolean isEnabled(final Level level) {
        return this.isEnabled(level, null, (Object)null, null);
    }
    
    protected abstract boolean isEnabled(final Level p0, final Marker p1, final Message p2, final Throwable p3);
    
    protected abstract boolean isEnabled(final Level p0, final Marker p1, final Object p2, final Throwable p3);
    
    protected abstract boolean isEnabled(final Level p0, final Marker p1, final String p2);
    
    protected abstract boolean isEnabled(final Level p0, final Marker p1, final String p2, final Object... p3);
    
    protected abstract boolean isEnabled(final Level p0, final Marker p1, final String p2, final Throwable p3);
    
    @Override
    public boolean isErrorEnabled() {
        return this.isEnabled(Level.ERROR, null, (Object)null, null);
    }
    
    @Override
    public boolean isErrorEnabled(final Marker marker) {
        return this.isEnabled(Level.ERROR, marker, (Object)null, null);
    }
    
    @Override
    public boolean isFatalEnabled() {
        return this.isEnabled(Level.FATAL, null, (Object)null, null);
    }
    
    @Override
    public boolean isFatalEnabled(final Marker marker) {
        return this.isEnabled(Level.FATAL, marker, (Object)null, null);
    }
    
    @Override
    public boolean isInfoEnabled() {
        return this.isEnabled(Level.INFO, null, (Object)null, null);
    }
    
    @Override
    public boolean isInfoEnabled(final Marker marker) {
        return this.isEnabled(Level.INFO, marker, (Object)null, null);
    }
    
    @Override
    public boolean isTraceEnabled() {
        return this.isEnabled(Level.TRACE, null, (Object)null, null);
    }
    
    @Override
    public boolean isTraceEnabled(final Marker marker) {
        return this.isEnabled(Level.TRACE, marker, (Object)null, null);
    }
    
    @Override
    public boolean isWarnEnabled() {
        return this.isEnabled(Level.WARN, null, (Object)null, null);
    }
    
    @Override
    public boolean isWarnEnabled(final Marker marker) {
        return this.isEnabled(Level.WARN, marker, (Object)null, null);
    }
    
    @Override
    public boolean isEnabled(final Level level, final Marker marker) {
        return this.isEnabled(level, marker, (Object)null, null);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Message message) {
        if (this.isEnabled(level, marker, message, null)) {
            this.log(marker, AbstractLogger.FQCN, level, message, null);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Message message, final Throwable t) {
        if (this.isEnabled(level, marker, message, t)) {
            this.log(marker, AbstractLogger.FQCN, level, message, t);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Object o) {
        if (this.isEnabled(level, marker, o, null)) {
            this.log(marker, AbstractLogger.FQCN, level, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Object o, final Throwable t) {
        if (this.isEnabled(level, marker, o, t)) {
            this.log(marker, AbstractLogger.FQCN, level, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String s) {
        if (this.isEnabled(level, marker, s)) {
            this.log(marker, AbstractLogger.FQCN, level, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String s, final Object... array) {
        if (this.isEnabled(level, marker, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(marker, AbstractLogger.FQCN, level, message, message.getThrowable());
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String s, final Throwable t) {
        if (this.isEnabled(level, marker, s, t)) {
            this.log(marker, AbstractLogger.FQCN, level, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public void log(final Level level, final Message message) {
        if (this.isEnabled(level, null, message, null)) {
            this.log(null, AbstractLogger.FQCN, level, message, null);
        }
    }
    
    @Override
    public void log(final Level level, final Message message, final Throwable t) {
        if (this.isEnabled(level, null, message, t)) {
            this.log(null, AbstractLogger.FQCN, level, message, t);
        }
    }
    
    @Override
    public void log(final Level level, final Object o) {
        if (this.isEnabled(level, null, o, null)) {
            this.log(null, AbstractLogger.FQCN, level, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void log(final Level level, final Object o, final Throwable t) {
        if (this.isEnabled(level, null, o, t)) {
            this.log(null, AbstractLogger.FQCN, level, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void log(final Level level, final String s) {
        if (this.isEnabled(level, null, s)) {
            this.log(null, AbstractLogger.FQCN, level, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void log(final Level level, final String s, final Object... array) {
        if (this.isEnabled(level, null, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(null, AbstractLogger.FQCN, level, message, message.getThrowable());
        }
    }
    
    @Override
    public void log(final Level level, final String s, final Throwable t) {
        if (this.isEnabled(level, null, s, t)) {
            this.log(null, AbstractLogger.FQCN, level, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public void printf(final Level level, final String s, final Object... array) {
        if (this.isEnabled(level, null, s, array)) {
            final StringFormattedMessage stringFormattedMessage = new StringFormattedMessage(s, array);
            this.log(null, AbstractLogger.FQCN, level, stringFormattedMessage, stringFormattedMessage.getThrowable());
        }
    }
    
    @Override
    public void printf(final Level level, final Marker marker, final String s, final Object... array) {
        if (this.isEnabled(level, marker, s, array)) {
            final StringFormattedMessage stringFormattedMessage = new StringFormattedMessage(s, array);
            this.log(marker, AbstractLogger.FQCN, level, stringFormattedMessage, stringFormattedMessage.getThrowable());
        }
    }
    
    public abstract void log(final Marker p0, final String p1, final Level p2, final Message p3, final Throwable p4);
    
    @Override
    public Throwable throwing(final Level level, final Throwable t) {
        return this.throwing(AbstractLogger.FQCN, level, t);
    }
    
    @Override
    public Throwable throwing(final Throwable t) {
        return this.throwing(AbstractLogger.FQCN, Level.ERROR, t);
    }
    
    protected Throwable throwing(final String s, final Level level, final Throwable t) {
        if (this.isEnabled(level, AbstractLogger.THROWING_MARKER, (Object)null, null)) {
            this.log(AbstractLogger.THROWING_MARKER, s, level, this.messageFactory.newMessage("throwing"), t);
        }
        return t;
    }
    
    private Message toExitMsg(final Object o) {
        if (o == null) {
            return this.messageFactory.newMessage("exit");
        }
        return this.messageFactory.newMessage("exit with(" + o + ")");
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    public void trace(final Marker marker, final Message message) {
        if (this.isEnabled(Level.TRACE, marker, message, null)) {
            this.log(marker, AbstractLogger.FQCN, Level.TRACE, message, null);
        }
    }
    
    @Override
    public void trace(final Marker marker, final Message message, final Throwable t) {
        if (this.isEnabled(Level.TRACE, marker, message, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.TRACE, message, t);
        }
    }
    
    @Override
    public void trace(final Marker marker, final Object o) {
        if (this.isEnabled(Level.TRACE, marker, o, null)) {
            this.log(marker, AbstractLogger.FQCN, Level.TRACE, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void trace(final Marker marker, final Object o, final Throwable t) {
        if (this.isEnabled(Level.TRACE, marker, o, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.TRACE, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String s) {
        if (this.isEnabled(Level.TRACE, marker, s)) {
            this.log(marker, AbstractLogger.FQCN, Level.TRACE, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String s, final Object... array) {
        if (this.isEnabled(Level.TRACE, marker, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(marker, AbstractLogger.FQCN, Level.TRACE, message, message.getThrowable());
        }
    }
    
    @Override
    public void trace(final Marker marker, final String s, final Throwable t) {
        if (this.isEnabled(Level.TRACE, marker, s, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.TRACE, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public void trace(final Message message) {
        if (this.isEnabled(Level.TRACE, null, message, null)) {
            this.log(null, AbstractLogger.FQCN, Level.TRACE, message, null);
        }
    }
    
    @Override
    public void trace(final Message message, final Throwable t) {
        if (this.isEnabled(Level.TRACE, null, message, t)) {
            this.log(null, AbstractLogger.FQCN, Level.TRACE, message, t);
        }
    }
    
    @Override
    public void trace(final Object o) {
        if (this.isEnabled(Level.TRACE, null, o, null)) {
            this.log(null, AbstractLogger.FQCN, Level.TRACE, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void trace(final Object o, final Throwable t) {
        if (this.isEnabled(Level.TRACE, null, o, t)) {
            this.log(null, AbstractLogger.FQCN, Level.TRACE, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void trace(final String s) {
        if (this.isEnabled(Level.TRACE, null, s)) {
            this.log(null, AbstractLogger.FQCN, Level.TRACE, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void trace(final String s, final Object... array) {
        if (this.isEnabled(Level.TRACE, null, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(null, AbstractLogger.FQCN, Level.TRACE, message, message.getThrowable());
        }
    }
    
    @Override
    public void trace(final String s, final Throwable t) {
        if (this.isEnabled(Level.TRACE, null, s, t)) {
            this.log(null, AbstractLogger.FQCN, Level.TRACE, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public void warn(final Marker marker, final Message message) {
        if (this.isEnabled(Level.WARN, marker, message, null)) {
            this.log(marker, AbstractLogger.FQCN, Level.WARN, message, null);
        }
    }
    
    @Override
    public void warn(final Marker marker, final Message message, final Throwable t) {
        if (this.isEnabled(Level.WARN, marker, message, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.WARN, message, t);
        }
    }
    
    @Override
    public void warn(final Marker marker, final Object o) {
        if (this.isEnabled(Level.WARN, marker, o, null)) {
            this.log(marker, AbstractLogger.FQCN, Level.WARN, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void warn(final Marker marker, final Object o, final Throwable t) {
        if (this.isEnabled(Level.WARN, marker, o, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.WARN, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String s) {
        if (this.isEnabled(Level.WARN, marker, s)) {
            this.log(marker, AbstractLogger.FQCN, Level.WARN, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String s, final Object... array) {
        if (this.isEnabled(Level.WARN, marker, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(marker, AbstractLogger.FQCN, Level.WARN, message, message.getThrowable());
        }
    }
    
    @Override
    public void warn(final Marker marker, final String s, final Throwable t) {
        if (this.isEnabled(Level.WARN, marker, s, t)) {
            this.log(marker, AbstractLogger.FQCN, Level.WARN, this.messageFactory.newMessage(s), t);
        }
    }
    
    @Override
    public void warn(final Message message) {
        if (this.isEnabled(Level.WARN, null, message, null)) {
            this.log(null, AbstractLogger.FQCN, Level.WARN, message, null);
        }
    }
    
    @Override
    public void warn(final Message message, final Throwable t) {
        if (this.isEnabled(Level.WARN, null, message, t)) {
            this.log(null, AbstractLogger.FQCN, Level.WARN, message, t);
        }
    }
    
    @Override
    public void warn(final Object o) {
        if (this.isEnabled(Level.WARN, null, o, null)) {
            this.log(null, AbstractLogger.FQCN, Level.WARN, this.messageFactory.newMessage(o), null);
        }
    }
    
    @Override
    public void warn(final Object o, final Throwable t) {
        if (this.isEnabled(Level.WARN, null, o, t)) {
            this.log(null, AbstractLogger.FQCN, Level.WARN, this.messageFactory.newMessage(o), t);
        }
    }
    
    @Override
    public void warn(final String s) {
        if (this.isEnabled(Level.WARN, null, s)) {
            this.log(null, AbstractLogger.FQCN, Level.WARN, this.messageFactory.newMessage(s), null);
        }
    }
    
    @Override
    public void warn(final String s, final Object... array) {
        if (this.isEnabled(Level.WARN, null, s, array)) {
            final Message message = this.messageFactory.newMessage(s, array);
            this.log(null, AbstractLogger.FQCN, Level.WARN, message, message.getThrowable());
        }
    }
    
    @Override
    public void warn(final String s, final Throwable t) {
        if (this.isEnabled(Level.WARN, null, s, t)) {
            this.log(null, AbstractLogger.FQCN, Level.WARN, this.messageFactory.newMessage(s), t);
        }
    }
    
    static {
        FLOW_MARKER = MarkerManager.getMarker("FLOW");
        ENTRY_MARKER = MarkerManager.getMarker("ENTRY", AbstractLogger.FLOW_MARKER);
        EXIT_MARKER = MarkerManager.getMarker("EXIT", AbstractLogger.FLOW_MARKER);
        EXCEPTION_MARKER = MarkerManager.getMarker("EXCEPTION");
        THROWING_MARKER = MarkerManager.getMarker("THROWING", AbstractLogger.EXCEPTION_MARKER);
        CATCHING_MARKER = MarkerManager.getMarker("CATCHING", AbstractLogger.EXCEPTION_MARKER);
        DEFAULT_MESSAGE_FACTORY_CLASS = ParameterizedMessageFactory.class;
        FQCN = AbstractLogger.class.getName();
    }
}
