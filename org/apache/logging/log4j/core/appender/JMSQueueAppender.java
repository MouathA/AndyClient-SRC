package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.net.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "JMSQueue", category = "Core", elementType = "appender", printObject = true)
public final class JMSQueueAppender extends AbstractAppender
{
    private final JMSQueueManager manager;
    
    private JMSQueueAppender(final String s, final Filter filter, final Layout layout, final JMSQueueManager manager, final boolean b) {
        super(s, filter, layout, b);
        this.manager = manager;
    }
    
    @Override
    public void append(final LogEvent logEvent) {
        this.manager.send(this.getLayout().toSerializable(logEvent));
    }
    
    @PluginFactory
    public static JMSQueueAppender createAppender(@PluginAttribute("name") final String s, @PluginAttribute("factoryName") final String s2, @PluginAttribute("providerURL") final String s3, @PluginAttribute("urlPkgPrefixes") final String s4, @PluginAttribute("securityPrincipalName") final String s5, @PluginAttribute("securityCredentials") final String s6, @PluginAttribute("factoryBindingName") final String s7, @PluginAttribute("queueBindingName") final String s8, @PluginAttribute("userName") final String s9, @PluginAttribute("password") final String s10, @PluginElement("Layout") Layout layout, @PluginElement("Filter") final Filter filter, @PluginAttribute("ignoreExceptions") final String s11) {
        if (s == null) {
            JMSQueueAppender.LOGGER.error("No name provided for JMSQueueAppender");
            return null;
        }
        final boolean boolean1 = Booleans.parseBoolean(s11, true);
        final JMSQueueManager jmsQueueManager = JMSQueueManager.getJMSQueueManager(s2, s3, s4, s5, s6, s7, s8, s9, s10);
        if (jmsQueueManager == null) {
            return null;
        }
        if (layout == null) {
            layout = SerializedLayout.createLayout();
        }
        return new JMSQueueAppender(s, filter, layout, jmsQueueManager, boolean1);
    }
}
