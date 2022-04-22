package org.apache.logging.log4j.core.appender.db.jdbc;

import org.apache.logging.log4j.core.appender.db.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.*;
import java.sql.*;
import java.io.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.layout.*;
import java.util.*;

public final class JDBCDatabaseManager extends AbstractDatabaseManager
{
    private static final JDBCDatabaseManagerFactory FACTORY;
    private final List columns;
    private final ConnectionSource connectionSource;
    private final String sqlStatement;
    private Connection connection;
    private PreparedStatement statement;
    
    private JDBCDatabaseManager(final String s, final int n, final ConnectionSource connectionSource, final String sqlStatement, final List columns) {
        super(s, n);
        this.connectionSource = connectionSource;
        this.sqlStatement = sqlStatement;
        this.columns = columns;
    }
    
    @Override
    protected void connectInternal() throws SQLException {
        this.connection = this.connectionSource.getConnection();
        this.statement = this.connection.prepareStatement(this.sqlStatement);
    }
    
    @Override
    protected void disconnectInternal() throws SQLException {
        Closer.close(this.statement);
        Closer.close(this.connection);
    }
    
    @Override
    protected void writeInternal(final LogEvent logEvent) {
        Reader reader = null;
        if (!this.isConnected() || this.connection == null || this.connection.isClosed()) {
            throw new AppenderLoggingException("Cannot write logging event; JDBC manager not connected to the database.");
        }
        for (final Column column : this.columns) {
            if (Column.access$100(column)) {
                final PreparedStatement statement = this.statement;
                final int n = 1;
                int n2 = 0;
                ++n2;
                statement.setTimestamp(n, new Timestamp(logEvent.getMillis()));
            }
            else if (Column.access$200(column)) {
                reader = new StringReader(Column.access$300(column).toSerializable(logEvent));
                if (Column.access$400(column)) {
                    final PreparedStatement statement2 = this.statement;
                    final int n3 = 1;
                    int n2 = 0;
                    ++n2;
                    statement2.setNClob(n3, reader);
                }
                else {
                    final PreparedStatement statement3 = this.statement;
                    final int n4 = 1;
                    int n2 = 0;
                    ++n2;
                    statement3.setClob(n4, reader);
                }
            }
            else if (Column.access$400(column)) {
                final PreparedStatement statement4 = this.statement;
                final int n5 = 1;
                int n2 = 0;
                ++n2;
                statement4.setNString(n5, Column.access$300(column).toSerializable(logEvent));
            }
            else {
                final PreparedStatement statement5 = this.statement;
                final int n6 = 1;
                int n2 = 0;
                ++n2;
                statement5.setString(n6, Column.access$300(column).toSerializable(logEvent));
            }
        }
        if (this.statement.executeUpdate() == 0) {
            throw new AppenderLoggingException("No records inserted in database table for log event in JDBC manager.");
        }
        Closer.closeSilent(reader);
    }
    
    public static JDBCDatabaseManager getJDBCDatabaseManager(final String s, final int n, final ConnectionSource connectionSource, final String s2, final ColumnConfig[] array) {
        return (JDBCDatabaseManager)AbstractDatabaseManager.getManager(s, new FactoryData(n, connectionSource, s2, array), JDBCDatabaseManager.FACTORY);
    }
    
    JDBCDatabaseManager(final String s, final int n, final ConnectionSource connectionSource, final String s2, final List list, final JDBCDatabaseManager$1 object) {
        this(s, n, connectionSource, s2, list);
    }
    
    static {
        FACTORY = new JDBCDatabaseManagerFactory(null);
    }
    
    private static final class Column
    {
        private final PatternLayout layout;
        private final boolean isEventTimestamp;
        private final boolean isUnicode;
        private final boolean isClob;
        
        private Column(final PatternLayout layout, final boolean isEventTimestamp, final boolean isUnicode, final boolean isClob) {
            this.layout = layout;
            this.isEventTimestamp = isEventTimestamp;
            this.isUnicode = isUnicode;
            this.isClob = isClob;
        }
        
        static boolean access$100(final Column column) {
            return column.isEventTimestamp;
        }
        
        static boolean access$200(final Column column) {
            return column.isClob;
        }
        
        static PatternLayout access$300(final Column column) {
            return column.layout;
        }
        
        static boolean access$400(final Column column) {
            return column.isUnicode;
        }
        
        Column(final PatternLayout patternLayout, final boolean b, final boolean b2, final boolean b3, final JDBCDatabaseManager$1 object) {
            this(patternLayout, b, b2, b3);
        }
    }
    
    private static final class JDBCDatabaseManagerFactory implements ManagerFactory
    {
        private JDBCDatabaseManagerFactory() {
        }
        
        public JDBCDatabaseManager createManager(final String s, final FactoryData factoryData) {
            final StringBuilder sb = new StringBuilder();
            final StringBuilder sb2 = new StringBuilder();
            final ArrayList<Column> list = new ArrayList<Column>();
            final ColumnConfig[] access$500 = FactoryData.access$500(factoryData);
            while (0 < access$500.length) {
                final ColumnConfig columnConfig = access$500[0];
                final int n = 0;
                int n2 = 0;
                ++n2;
                if (n > 0) {
                    sb.append(',');
                    sb2.append(',');
                }
                sb.append(columnConfig.getColumnName());
                if (columnConfig.getLiteralValue() != null) {
                    sb2.append(columnConfig.getLiteralValue());
                }
                else {
                    list.add(new Column(columnConfig.getLayout(), columnConfig.isEventTimestamp(), columnConfig.isUnicode(), columnConfig.isClob(), null));
                    sb2.append('?');
                }
                int n3 = 0;
                ++n3;
            }
            return new JDBCDatabaseManager(s, factoryData.getBufferSize(), FactoryData.access$800(factoryData), "INSERT INTO " + FactoryData.access$700(factoryData) + " (" + (Object)sb + ") VALUES (" + (Object)sb2 + ")", list, null);
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
        
        JDBCDatabaseManagerFactory(final JDBCDatabaseManager$1 object) {
            this();
        }
    }
    
    private static final class FactoryData extends AbstractFactoryData
    {
        private final ColumnConfig[] columnConfigs;
        private final ConnectionSource connectionSource;
        private final String tableName;
        
        protected FactoryData(final int n, final ConnectionSource connectionSource, final String tableName, final ColumnConfig[] columnConfigs) {
            super(n);
            this.connectionSource = connectionSource;
            this.tableName = tableName;
            this.columnConfigs = columnConfigs;
        }
        
        static ColumnConfig[] access$500(final FactoryData factoryData) {
            return factoryData.columnConfigs;
        }
        
        static String access$700(final FactoryData factoryData) {
            return factoryData.tableName;
        }
        
        static ConnectionSource access$800(final FactoryData factoryData) {
            return factoryData.connectionSource;
        }
    }
}
