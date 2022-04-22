package org.apache.logging.log4j.core.appender.rewrite;

import org.apache.logging.log4j.core.appender.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.*;
import java.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "Rewrite", category = "Core", elementType = "appender", printObject = true)
public final class RewriteAppender extends AbstractAppender
{
    private final Configuration config;
    private final ConcurrentMap appenders;
    private final RewritePolicy rewritePolicy;
    private final AppenderRef[] appenderRefs;
    
    private RewriteAppender(final String s, final Filter filter, final boolean b, final AppenderRef[] appenderRefs, final RewritePolicy rewritePolicy, final Configuration config) {
        super(s, filter, null, b);
        this.appenders = new ConcurrentHashMap();
        this.config = config;
        this.rewritePolicy = rewritePolicy;
        this.appenderRefs = appenderRefs;
    }
    
    @Override
    public void start() {
        final Map appenders = this.config.getAppenders();
        final AppenderRef[] appenderRefs = this.appenderRefs;
        while (0 < appenderRefs.length) {
            final AppenderRef appenderRef = appenderRefs[0];
            final String ref = appenderRef.getRef();
            final Appender appender = appenders.get(ref);
            if (appender != null) {
                this.appenders.put(ref, new AppenderControl(appender, appenderRef.getLevel(), (appender instanceof AbstractAppender) ? ((AbstractAppender)appender).getFilter() : null));
            }
            else {
                RewriteAppender.LOGGER.error("Appender " + appenderRef + " cannot be located. Reference ignored");
            }
            int n = 0;
            ++n;
        }
        super.start();
    }
    
    @Override
    public void stop() {
        super.stop();
    }
    
    @Override
    public void append(LogEvent rewrite) {
        if (this.rewritePolicy != null) {
            rewrite = this.rewritePolicy.rewrite(rewrite);
        }
        final Iterator iterator = this.appenders.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().callAppender(rewrite);
        }
    }
    
    @PluginFactory
    public static RewriteAppender createAppender(@PluginAttribute("name") final String s, @PluginAttribute("ignoreExceptions") final String s2, @PluginElement("AppenderRef") final AppenderRef[] array, @PluginConfiguration final Configuration configuration, @PluginElement("RewritePolicy") final RewritePolicy rewritePolicy, @PluginElement("Filter") final Filter filter) {
        final boolean boolean1 = Booleans.parseBoolean(s2, true);
        if (s == null) {
            RewriteAppender.LOGGER.error("No name provided for RewriteAppender");
            return null;
        }
        if (array == null) {
            RewriteAppender.LOGGER.error("No appender references defined for RewriteAppender");
            return null;
        }
        return new RewriteAppender(s, filter, boolean1, array, rewritePolicy, configuration);
    }
}
