package org.apache.logging.log4j.core.jmx;

import org.apache.logging.log4j.status.*;
import java.util.concurrent.atomic.*;
import org.apache.logging.log4j.core.*;
import java.util.concurrent.*;
import javax.management.*;
import java.net.*;
import java.beans.*;
import java.nio.charset.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.helpers.*;
import java.io.*;
import java.util.*;

public class LoggerContextAdmin extends NotificationBroadcasterSupport implements LoggerContextAdminMBean, PropertyChangeListener
{
    private static final int PAGE = 4096;
    private static final int TEXT_BUFFER = 65536;
    private static final int BUFFER_SIZE = 2048;
    private static final StatusLogger LOGGER;
    private final AtomicLong sequenceNo;
    private final ObjectName objectName;
    private final LoggerContext loggerContext;
    private String customConfigText;
    
    public LoggerContextAdmin(final LoggerContext loggerContext, final Executor executor) {
        super(executor, new MBeanNotificationInfo[] { createNotificationInfo() });
        this.sequenceNo = new AtomicLong();
        this.loggerContext = (LoggerContext)Assert.isNotNull(loggerContext, "loggerContext");
        this.objectName = new ObjectName(String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s", Server.escape(loggerContext.getName())));
        loggerContext.addPropertyChangeListener(this);
    }
    
    private static MBeanNotificationInfo createNotificationInfo() {
        return new MBeanNotificationInfo(new String[] { "com.apache.logging.log4j.core.jmx.config.reconfigured" }, Notification.class.getName(), "Configuration reconfigured");
    }
    
    @Override
    public String getStatus() {
        return this.loggerContext.getStatus().toString();
    }
    
    @Override
    public String getName() {
        return this.loggerContext.getName();
    }
    
    private Configuration getConfig() {
        return this.loggerContext.getConfiguration();
    }
    
    @Override
    public String getConfigLocationURI() {
        if (this.loggerContext.getConfigLocation() != null) {
            return String.valueOf(this.loggerContext.getConfigLocation());
        }
        if (this.getConfigName() != null) {
            return String.valueOf(new File(this.getConfigName()).toURI());
        }
        return "";
    }
    
    @Override
    public void setConfigLocationURI(final String s) throws URISyntaxException, IOException {
        LoggerContextAdmin.LOGGER.debug("---------");
        LoggerContextAdmin.LOGGER.debug("Remote request to reconfigure using location " + s);
        final URI configLocation = new URI(s);
        configLocation.toURL().openStream().close();
        this.loggerContext.setConfigLocation(configLocation);
        LoggerContextAdmin.LOGGER.debug("Completed remote request to reconfigure.");
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent propertyChangeEvent) {
        if (!"config".equals(propertyChangeEvent.getPropertyName())) {
            return;
        }
        if (this.loggerContext.getConfiguration().getName() != null) {
            this.customConfigText = null;
        }
        this.sendNotification(new Notification("com.apache.logging.log4j.core.jmx.config.reconfigured", this.getObjectName(), this.nextSeqNo(), this.now(), null));
    }
    
    @Override
    public String getConfigText() throws IOException {
        return this.getConfigText(Charsets.UTF_8.name());
    }
    
    @Override
    public String getConfigText(final String s) throws IOException {
        if (this.customConfigText != null) {
            return this.customConfigText;
        }
        return this.readContents(new URI(this.getConfigLocationURI()), Charset.forName(s));
    }
    
    @Override
    public void setConfigText(final String s, final String s2) {
        final String customConfigText = this.customConfigText;
        this.customConfigText = (String)Assert.isNotNull(s, "configText");
        LoggerContextAdmin.LOGGER.debug("---------");
        LoggerContextAdmin.LOGGER.debug("Remote request to reconfigure from config text.");
        this.loggerContext.start(ConfigurationFactory.getInstance().getConfiguration(new ConfigurationFactory.ConfigurationSource(new ByteArrayInputStream(s.getBytes(s2)))));
        LoggerContextAdmin.LOGGER.debug("Completed remote request to reconfigure from config text.");
    }
    
    private String readContents(final URI uri, final Charset charset) throws IOException {
        final InputStream openStream = uri.toURL().openStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(openStream, charset);
        final StringBuilder sb = new StringBuilder(65536);
        final char[] array = new char[4096];
        while (inputStreamReader.read(array) >= 0) {
            sb.append(array, 0, -1);
        }
        final String string = sb.toString();
        Closer.closeSilent(openStream);
        Closer.closeSilent(inputStreamReader);
        return string;
    }
    
    @Override
    public String getConfigName() {
        return this.getConfig().getName();
    }
    
    @Override
    public String getConfigClassName() {
        return this.getConfig().getClass().getName();
    }
    
    @Override
    public String getConfigFilter() {
        return String.valueOf(this.getConfig().getFilter());
    }
    
    @Override
    public String getConfigMonitorClassName() {
        return this.getConfig().getConfigurationMonitor().getClass().getName();
    }
    
    @Override
    public Map getConfigProperties() {
        return this.getConfig().getProperties();
    }
    
    public ObjectName getObjectName() {
        return this.objectName;
    }
    
    private long nextSeqNo() {
        return this.sequenceNo.getAndIncrement();
    }
    
    private long now() {
        return System.currentTimeMillis();
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
