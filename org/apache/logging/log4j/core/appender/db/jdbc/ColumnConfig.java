package org.apache.logging.log4j.core.appender.db.jdbc;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "Column", category = "Core", printObject = true)
public final class ColumnConfig
{
    private static final Logger LOGGER;
    private final String columnName;
    private final PatternLayout layout;
    private final String literalValue;
    private final boolean eventTimestamp;
    private final boolean unicode;
    private final boolean clob;
    
    private ColumnConfig(final String columnName, final PatternLayout layout, final String literalValue, final boolean eventTimestamp, final boolean unicode, final boolean clob) {
        this.columnName = columnName;
        this.layout = layout;
        this.literalValue = literalValue;
        this.eventTimestamp = eventTimestamp;
        this.unicode = unicode;
        this.clob = clob;
    }
    
    public String getColumnName() {
        return this.columnName;
    }
    
    public PatternLayout getLayout() {
        return this.layout;
    }
    
    public String getLiteralValue() {
        return this.literalValue;
    }
    
    public boolean isEventTimestamp() {
        return this.eventTimestamp;
    }
    
    public boolean isUnicode() {
        return this.unicode;
    }
    
    public boolean isClob() {
        return this.clob;
    }
    
    @Override
    public String toString() {
        return "{ name=" + this.columnName + ", layout=" + this.layout + ", literal=" + this.literalValue + ", timestamp=" + this.eventTimestamp + " }";
    }
    
    @PluginFactory
    public static ColumnConfig createColumnConfig(@PluginConfiguration final Configuration configuration, @PluginAttribute("name") final String s, @PluginAttribute("pattern") final String s2, @PluginAttribute("literal") final String s3, @PluginAttribute("isEventTimestamp") final String s4, @PluginAttribute("isUnicode") final String s5, @PluginAttribute("isClob") final String s6) {
        if (Strings.isEmpty(s)) {
            ColumnConfig.LOGGER.error("The column config is not valid because it does not contain a column name.");
            return null;
        }
        final boolean notEmpty = Strings.isNotEmpty(s2);
        final boolean notEmpty2 = Strings.isNotEmpty(s3);
        final boolean boolean1 = Boolean.parseBoolean(s4);
        final boolean boolean2 = Booleans.parseBoolean(s5, true);
        final boolean boolean3 = Boolean.parseBoolean(s6);
        if ((notEmpty && notEmpty2) || (notEmpty && boolean1) || (notEmpty2 && boolean1)) {
            ColumnConfig.LOGGER.error("The pattern, literal, and isEventTimestamp attributes are mutually exclusive.");
            return null;
        }
        if (boolean1) {
            return new ColumnConfig(s, null, null, true, false, false);
        }
        if (notEmpty2) {
            return new ColumnConfig(s, null, s3, false, false, false);
        }
        if (notEmpty) {
            return new ColumnConfig(s, PatternLayout.createLayout(s2, configuration, null, null, "false"), null, false, boolean2, boolean3);
        }
        ColumnConfig.LOGGER.error("To configure a column you must specify a pattern or literal or set isEventDate to true.");
        return null;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
