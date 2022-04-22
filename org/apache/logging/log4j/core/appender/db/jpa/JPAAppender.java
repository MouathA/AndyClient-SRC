package org.apache.logging.log4j.core.appender.db.jpa;

import org.apache.logging.log4j.core.appender.db.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.*;
import java.lang.reflect.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "JPA", category = "Core", elementType = "appender", printObject = true)
public final class JPAAppender extends AbstractDatabaseAppender
{
    private final String description;
    
    private JPAAppender(final String s, final Filter filter, final boolean b, final JPADatabaseManager jpaDatabaseManager) {
        super(s, filter, b, jpaDatabaseManager);
        this.description = this.getName() + "{ manager=" + this.getManager() + " }";
    }
    
    @Override
    public String toString() {
        return this.description;
    }
    
    @PluginFactory
    public static JPAAppender createAppender(@PluginAttribute("name") final String s, @PluginAttribute("ignoreExceptions") final String s2, @PluginElement("Filter") final Filter filter, @PluginAttribute("bufferSize") final String s3, @PluginAttribute("entityClassName") final String s4, @PluginAttribute("persistenceUnitName") final String s5) {
        if (Strings.isEmpty(s4) || Strings.isEmpty(s5)) {
            JPAAppender.LOGGER.error("Attributes entityClassName and persistenceUnitName are required for JPA Appender.");
            return null;
        }
        final int int1 = AbstractAppender.parseInt(s3, 0);
        final boolean boolean1 = Booleans.parseBoolean(s2, true);
        final Class<?> forName = Class.forName(s4);
        if (!AbstractLogEventWrapperEntity.class.isAssignableFrom(forName)) {
            JPAAppender.LOGGER.error("Entity class [{}] does not extend AbstractLogEventWrapperEntity.", s4);
            return null;
        }
        forName.getConstructor((Class<?>[])new Class[0]);
        final JPADatabaseManager jpaDatabaseManager = JPADatabaseManager.getJPADatabaseManager("jpaManager{ description=" + s + ", bufferSize=" + int1 + ", persistenceUnitName=" + s5 + ", entityClass=" + forName.getName() + "}", int1, forName, forName.getConstructor(LogEvent.class), s5);
        if (jpaDatabaseManager == null) {
            return null;
        }
        return new JPAAppender(s, filter, boolean1, jpaDatabaseManager);
    }
}
