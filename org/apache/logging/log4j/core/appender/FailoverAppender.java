package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;
import java.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "Failover", category = "Core", elementType = "appender", printObject = true)
public final class FailoverAppender extends AbstractAppender
{
    private static final int DEFAULT_INTERVAL_SECONDS = 60;
    private final String primaryRef;
    private final String[] failovers;
    private final Configuration config;
    private AppenderControl primary;
    private final List failoverAppenders;
    private final long intervalMillis;
    private long nextCheckMillis;
    private boolean failure;
    
    private FailoverAppender(final String s, final Filter filter, final String primaryRef, final String[] failovers, final int n, final Configuration config, final boolean b) {
        super(s, filter, null, b);
        this.failoverAppenders = new ArrayList();
        this.nextCheckMillis = 0L;
        this.failure = false;
        this.primaryRef = primaryRef;
        this.failovers = failovers;
        this.config = config;
        this.intervalMillis = n;
    }
    
    @Override
    public void start() {
        final Map appenders = this.config.getAppenders();
        int n = 0;
        if (appenders.containsKey(this.primaryRef)) {
            this.primary = new AppenderControl(appenders.get(this.primaryRef), null, null);
        }
        else {
            FailoverAppender.LOGGER.error("Unable to locate primary Appender " + this.primaryRef);
            ++n;
        }
        final String[] failovers = this.failovers;
        while (0 < failovers.length) {
            final String s = failovers[0];
            if (appenders.containsKey(s)) {
                this.failoverAppenders.add(new AppenderControl(appenders.get(s), null, null));
            }
            else {
                FailoverAppender.LOGGER.error("Failover appender " + s + " is not configured");
            }
            int n2 = 0;
            ++n2;
        }
        if (this.failoverAppenders.size() == 0) {
            FailoverAppender.LOGGER.error("No failover appenders are available");
            ++n;
        }
        if (!false) {
            super.start();
        }
    }
    
    @Override
    public void append(final LogEvent logEvent) {
        if (!this.isStarted()) {
            this.error("FailoverAppender " + this.getName() + " did not start successfully");
            return;
        }
        if (!this.failure) {
            this.callAppender(logEvent);
        }
        else if (System.currentTimeMillis() >= this.nextCheckMillis) {
            this.callAppender(logEvent);
        }
        else {
            this.failover(logEvent, null);
        }
    }
    
    private void callAppender(final LogEvent logEvent) {
        this.primary.callAppender(logEvent);
    }
    
    private void failover(final LogEvent logEvent, final Exception ex) {
        final LoggingException ex2 = (LoggingException)((ex != null) ? ((ex instanceof LoggingException) ? ex : new LoggingException(ex)) : null);
        final Throwable t = null;
        final Iterator<AppenderControl> iterator = this.failoverAppenders.iterator();
        if (iterator.hasNext()) {
            iterator.next().callAppender(logEvent);
        }
        if (true || this.ignoreExceptions()) {
            return;
        }
        if (ex2 != null) {
            throw ex2;
        }
        throw new LoggingException("Unable to write to failover appenders", t);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getName());
        sb.append(" primary=").append(this.primary).append(", failover={");
        final String[] failovers = this.failovers;
        while (0 < failovers.length) {
            final String s = failovers[0];
            if (!false) {
                sb.append(", ");
            }
            sb.append(s);
            int n = 0;
            ++n;
        }
        sb.append("}");
        return sb.toString();
    }
    
    @PluginFactory
    public static FailoverAppender createAppender(@PluginAttribute("name") final String s, @PluginAttribute("primary") final String s2, @PluginElement("Failovers") final String[] array, @PluginAttribute("retryInterval") final String s3, @PluginConfiguration final Configuration configuration, @PluginElement("Filters") final Filter filter, @PluginAttribute("ignoreExceptions") final String s4) {
        if (s == null) {
            FailoverAppender.LOGGER.error("A name for the Appender must be specified");
            return null;
        }
        if (s2 == null) {
            FailoverAppender.LOGGER.error("A primary Appender must be specified");
            return null;
        }
        if (array == null || array.length == 0) {
            FailoverAppender.LOGGER.error("At least one failover Appender must be specified");
            return null;
        }
        if (AbstractAppender.parseInt(s3, 60) < 0) {
            FailoverAppender.LOGGER.warn("Interval " + s3 + " is less than zero. Using default");
        }
        return new FailoverAppender(s, filter, s2, array, 60000, configuration, Booleans.parseBoolean(s4, true));
    }
}
