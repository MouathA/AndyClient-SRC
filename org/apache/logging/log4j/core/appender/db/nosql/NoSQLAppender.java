package org.apache.logging.log4j.core.appender.db.nosql;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.db.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "NoSql", category = "Core", elementType = "appender", printObject = true)
public final class NoSQLAppender extends AbstractDatabaseAppender
{
    private final String description;
    
    private NoSQLAppender(final String s, final Filter filter, final boolean b, final NoSQLDatabaseManager noSQLDatabaseManager) {
        super(s, filter, b, noSQLDatabaseManager);
        this.description = this.getName() + "{ manager=" + this.getManager() + " }";
    }
    
    @Override
    public String toString() {
        return this.description;
    }
    
    @PluginFactory
    public static NoSQLAppender createAppender(@PluginAttribute("name") final String s, @PluginAttribute("ignoreExceptions") final String s2, @PluginElement("Filter") final Filter filter, @PluginAttribute("bufferSize") final String s3, @PluginElement("NoSqlProvider") final NoSQLProvider noSQLProvider) {
        if (noSQLProvider == null) {
            NoSQLAppender.LOGGER.error("NoSQL provider not specified for appender [{}].", s);
            return null;
        }
        final int int1 = AbstractAppender.parseInt(s3, 0);
        final boolean boolean1 = Booleans.parseBoolean(s2, true);
        final NoSQLDatabaseManager noSQLDatabaseManager = NoSQLDatabaseManager.getNoSQLDatabaseManager("noSqlManager{ description=" + s + ", bufferSize=" + int1 + ", provider=" + noSQLProvider + " }", int1, noSQLProvider);
        if (noSQLDatabaseManager == null) {
            return null;
        }
        return new NoSQLAppender(s, filter, boolean1, noSQLDatabaseManager);
    }
}
