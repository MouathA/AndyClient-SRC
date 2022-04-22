package org.apache.logging.log4j.core.appender.db.jdbc;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.db.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "JDBC", category = "Core", elementType = "appender", printObject = true)
public final class JDBCAppender extends AbstractDatabaseAppender
{
    private final String description;
    
    private JDBCAppender(final String s, final Filter filter, final boolean b, final JDBCDatabaseManager jdbcDatabaseManager) {
        super(s, filter, b, jdbcDatabaseManager);
        this.description = this.getName() + "{ manager=" + this.getManager() + " }";
    }
    
    @Override
    public String toString() {
        return this.description;
    }
    
    @PluginFactory
    public static JDBCAppender createAppender(@PluginAttribute("name") final String s, @PluginAttribute("ignoreExceptions") final String s2, @PluginElement("Filter") final Filter filter, @PluginElement("ConnectionSource") final ConnectionSource connectionSource, @PluginAttribute("bufferSize") final String s3, @PluginAttribute("tableName") final String s4, @PluginElement("ColumnConfigs") final ColumnConfig[] array) {
        final int int1 = AbstractAppender.parseInt(s3, 0);
        final boolean boolean1 = Booleans.parseBoolean(s2, true);
        final StringBuilder append = new StringBuilder("jdbcManager{ description=").append(s).append(", bufferSize=").append(int1).append(", connectionSource=").append(connectionSource.toString()).append(", tableName=").append(s4).append(", columns=[ ");
        while (0 < array.length) {
            final ColumnConfig columnConfig = array[0];
            final int n = 0;
            int n2 = 0;
            ++n2;
            if (n > 0) {
                append.append(", ");
            }
            append.append(columnConfig.toString());
            int n3 = 0;
            ++n3;
        }
        append.append(" ] }");
        final JDBCDatabaseManager jdbcDatabaseManager = JDBCDatabaseManager.getJDBCDatabaseManager(append.toString(), int1, connectionSource, s4, array);
        if (jdbcDatabaseManager == null) {
            return null;
        }
        return new JDBCAppender(s, filter, boolean1, jdbcDatabaseManager);
    }
}
