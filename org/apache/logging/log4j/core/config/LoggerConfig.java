package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.core.filter.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.*;
import java.util.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.async.*;
import org.apache.logging.log4j.status.*;
import org.apache.logging.log4j.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.impl.*;

@Plugin(name = "logger", category = "Core", printObject = true)
public class LoggerConfig extends AbstractFilterable
{
    protected static final Logger LOGGER;
    private static final int MAX_RETRIES = 3;
    private static final long WAIT_TIME = 1000L;
    private static LogEventFactory LOG_EVENT_FACTORY;
    private List appenderRefs;
    private final Map appenders;
    private final String name;
    private LogEventFactory logEventFactory;
    private Level level;
    private boolean additive;
    private boolean includeLocation;
    private LoggerConfig parent;
    private final AtomicInteger counter;
    private boolean shutdown;
    private final Map properties;
    private final Configuration config;
    
    public LoggerConfig() {
        this.appenderRefs = new ArrayList();
        this.appenders = new ConcurrentHashMap();
        this.additive = true;
        this.includeLocation = true;
        this.counter = new AtomicInteger();
        this.shutdown = false;
        this.logEventFactory = LoggerConfig.LOG_EVENT_FACTORY;
        this.level = Level.ERROR;
        this.name = "";
        this.properties = null;
        this.config = null;
    }
    
    public LoggerConfig(final String name, final Level level, final boolean additive) {
        this.appenderRefs = new ArrayList();
        this.appenders = new ConcurrentHashMap();
        this.additive = true;
        this.includeLocation = true;
        this.counter = new AtomicInteger();
        this.shutdown = false;
        this.logEventFactory = LoggerConfig.LOG_EVENT_FACTORY;
        this.name = name;
        this.level = level;
        this.additive = additive;
        this.properties = null;
        this.config = null;
    }
    
    protected LoggerConfig(final String name, final List appenderRefs, final Filter filter, final Level level, final boolean additive, final Property[] array, final Configuration config, final boolean includeLocation) {
        super(filter);
        this.appenderRefs = new ArrayList();
        this.appenders = new ConcurrentHashMap();
        this.additive = true;
        this.includeLocation = true;
        this.counter = new AtomicInteger();
        this.shutdown = false;
        this.logEventFactory = LoggerConfig.LOG_EVENT_FACTORY;
        this.name = name;
        this.appenderRefs = appenderRefs;
        this.level = level;
        this.additive = additive;
        this.includeLocation = includeLocation;
        this.config = config;
        if (array != null && array.length > 0) {
            this.properties = new HashMap(array.length);
            while (0 < array.length) {
                final Property property = array[0];
                this.properties.put(property, property.getValue().contains("${"));
                int n = 0;
                ++n;
            }
        }
        else {
            this.properties = null;
        }
    }
    
    @Override
    public Filter getFilter() {
        return super.getFilter();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setParent(final LoggerConfig parent) {
        this.parent = parent;
    }
    
    public LoggerConfig getParent() {
        return this.parent;
    }
    
    public void addAppender(final Appender appender, final Level level, final Filter filter) {
        this.appenders.put(appender.getName(), new AppenderControl(appender, level, filter));
    }
    
    public void removeAppender(final String s) {
        final AppenderControl appenderControl = this.appenders.remove(s);
        if (appenderControl != null) {
            this.cleanupFilter(appenderControl);
        }
    }
    
    public Map getAppenders() {
        final HashMap<Object, Appender> hashMap = new HashMap<Object, Appender>();
        for (final Map.Entry<Object, V> entry : this.appenders.entrySet()) {
            hashMap.put(entry.getKey(), ((AppenderControl)entry.getValue()).getAppender());
        }
        return hashMap;
    }
    
    protected void clearAppenders() {
        this.waitForCompletion();
        final Iterator<AppenderControl> iterator = this.appenders.values().iterator();
        while (iterator.hasNext()) {
            final AppenderControl appenderControl = iterator.next();
            iterator.remove();
            this.cleanupFilter(appenderControl);
        }
    }
    
    private void cleanupFilter(final AppenderControl appenderControl) {
        final Filter filter = appenderControl.getFilter();
        if (filter != null) {
            appenderControl.removeFilter(filter);
            if (filter instanceof LifeCycle) {
                ((LifeCycle)filter).stop();
            }
        }
    }
    
    public List getAppenderRefs() {
        return this.appenderRefs;
    }
    
    public void setLevel(final Level level) {
        this.level = level;
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    public LogEventFactory getLogEventFactory() {
        return this.logEventFactory;
    }
    
    public void setLogEventFactory(final LogEventFactory logEventFactory) {
        this.logEventFactory = logEventFactory;
    }
    
    public boolean isAdditive() {
        return this.additive;
    }
    
    public void setAdditive(final boolean additive) {
        this.additive = additive;
    }
    
    public boolean isIncludeLocation() {
        return this.includeLocation;
    }
    
    public Map getProperties() {
        return (this.properties == null) ? null : Collections.unmodifiableMap((Map<?, ?>)this.properties);
    }
    
    public void log(final String s, final Marker marker, final String s2, final Level level, final Message message, final Throwable t) {
        List<Property> list = null;
        if (this.properties != null) {
            list = new ArrayList<Property>(this.properties.size());
            for (final Map.Entry<Property, V> entry : this.properties.entrySet()) {
                final Property property = entry.getKey();
                list.add(Property.createProperty(property.getName(), entry.getValue() ? this.config.getStrSubstitutor().replace(property.getValue()) : property.getValue()));
            }
        }
        this.log(this.logEventFactory.createEvent(s, marker, s2, level, message, list, t));
    }
    
    private synchronized void waitForCompletion() {
        if (this.shutdown) {
            return;
        }
        this.shutdown = true;
        while (this.counter.get() > 0) {
            this.wait(1000L * 1);
        }
    }
    
    public void log(final LogEvent logEvent) {
        this.counter.incrementAndGet();
        if (this.isFiltered(logEvent)) {
            if (this.counter.decrementAndGet() == 0) {
                // monitorenter(this)
                if (this.shutdown) {
                    this.notifyAll();
                }
            }
            // monitorexit(this)
            return;
        }
        logEvent.setIncludeLocation(this.isIncludeLocation());
        this.callAppenders(logEvent);
        if (this.additive && this.parent != null) {
            this.parent.log(logEvent);
        }
        if (this.counter.decrementAndGet() == 0) {
            // monitorenter(this)
            if (this.shutdown) {
                this.notifyAll();
            }
        }
        // monitorexit(this)
    }
    
    protected void callAppenders(final LogEvent logEvent) {
        final Iterator<AppenderControl> iterator = this.appenders.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().callAppender(logEvent);
        }
    }
    
    @Override
    public String toString() {
        return Strings.isEmpty(this.name) ? "root" : this.name;
    }
    
    @PluginFactory
    public static LoggerConfig createLogger(@PluginAttribute("additivity") final String s, @PluginAttribute("level") final String s2, @PluginAttribute("name") final String s3, @PluginAttribute("includeLocation") final String s4, @PluginElement("AppenderRef") final AppenderRef[] array, @PluginElement("Properties") final Property[] array2, @PluginConfiguration final Configuration configuration, @PluginElement("Filters") final Filter filter) {
        if (s3 == null) {
            LoggerConfig.LOGGER.error("Loggers cannot be configured without a name");
            return null;
        }
        return new LoggerConfig(s3.equals("root") ? "" : s3, Arrays.asList(array), filter, Level.toLevel(s2, Level.ERROR), Booleans.parseBoolean(s, true), array2, configuration, includeLocation(s4));
    }
    
    protected static boolean includeLocation(final String s) {
        if (s == null) {
            return !AsyncLoggerContextSelector.class.getName().equals(System.getProperty("Log4jContextSelector"));
        }
        return Boolean.parseBoolean(s);
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
        LoggerConfig.LOG_EVENT_FACTORY = null;
        final String stringProperty = PropertiesUtil.getProperties().getStringProperty("Log4jLogEventFactory");
        if (stringProperty != null) {
            final Class loadClass = Loader.loadClass(stringProperty);
            if (loadClass != null && LogEventFactory.class.isAssignableFrom(loadClass)) {
                LoggerConfig.LOG_EVENT_FACTORY = (LogEventFactory)loadClass.newInstance();
            }
        }
        if (LoggerConfig.LOG_EVENT_FACTORY == null) {
            LoggerConfig.LOG_EVENT_FACTORY = new DefaultLogEventFactory();
        }
    }
    
    @Plugin(name = "root", category = "Core", printObject = true)
    public static class RootLogger extends LoggerConfig
    {
        @PluginFactory
        public static LoggerConfig createLogger(@PluginAttribute("additivity") final String s, @PluginAttribute("level") final String s2, @PluginAttribute("includeLocation") final String s3, @PluginElement("AppenderRef") final AppenderRef[] array, @PluginElement("Properties") final Property[] array2, @PluginConfiguration final Configuration configuration, @PluginElement("Filters") final Filter filter) {
            return new LoggerConfig("", Arrays.asList(array), filter, Level.toLevel(s2, Level.ERROR), Booleans.parseBoolean(s, true), array2, configuration, LoggerConfig.includeLocation(s3));
        }
    }
}
