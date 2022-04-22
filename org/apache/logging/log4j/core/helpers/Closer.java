package org.apache.logging.log4j.core.helpers;

import java.io.*;
import java.sql.*;

public class Closer
{
    public static void closeSilent(final Closeable closeable) {
        if (closeable != null) {
            closeable.close();
        }
    }
    
    public static void close(final Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }
    
    public static void closeSilent(final Statement statement) {
        if (statement != null) {
            statement.close();
        }
    }
    
    public static void close(final Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }
    
    public static void closeSilent(final Connection connection) {
        if (connection != null) {
            connection.close();
        }
    }
    
    public static void close(final Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
