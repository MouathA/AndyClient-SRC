package org.apache.logging.log4j.core.layout;

import java.nio.charset.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.message.*;
import java.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.io.*;

@Plugin(name = "JSONLayout", category = "Core", elementType = "layout", printObject = true)
public class JSONLayout extends AbstractStringLayout
{
    private static final int DEFAULT_SIZE = 256;
    private static final String DEFAULT_EOL = "\r\n";
    private static final String COMPACT_EOL = "";
    private static final String DEFAULT_INDENT = "  ";
    private static final String COMPACT_INDENT = "";
    private final boolean locationInfo;
    private final boolean properties;
    private final boolean complete;
    private final String eol;
    private final String indent1;
    private final String indent2;
    private final String indent3;
    private final String indent4;
    private boolean firstLayoutDone;
    
    protected JSONLayout(final boolean locationInfo, final boolean properties, final boolean complete, final boolean b, final Charset charset) {
        super(charset);
        this.locationInfo = locationInfo;
        this.properties = properties;
        this.complete = complete;
        this.eol = (b ? "" : "\r\n");
        this.indent1 = (b ? "" : "  ");
        this.indent2 = this.indent1 + this.indent1;
        this.indent3 = this.indent2 + this.indent1;
        this.indent4 = this.indent3 + this.indent1;
    }
    
    @Override
    public String toSerializable(final LogEvent logEvent) {
        final StringBuilder sb = new StringBuilder(256);
        final boolean firstLayoutDone = this.firstLayoutDone;
        if (!this.firstLayoutDone) {
            // monitorenter(this)
            if (!this.firstLayoutDone) {
                this.firstLayoutDone = true;
            }
            else {
                sb.append(',');
                sb.append(this.eol);
            }
        }
        // monitorexit(this)
        else {
            sb.append(',');
            sb.append(this.eol);
        }
        sb.append(this.indent1);
        sb.append('{');
        sb.append(this.eol);
        sb.append(this.indent2);
        sb.append("\"logger\":\"");
        String loggerName = logEvent.getLoggerName();
        if (loggerName.isEmpty()) {
            loggerName = "root";
        }
        sb.append(Transform.escapeJsonControlCharacters(loggerName));
        sb.append("\",");
        sb.append(this.eol);
        sb.append(this.indent2);
        sb.append("\"timestamp\":\"");
        sb.append(logEvent.getMillis());
        sb.append("\",");
        sb.append(this.eol);
        sb.append(this.indent2);
        sb.append("\"level\":\"");
        sb.append(Transform.escapeJsonControlCharacters(String.valueOf(logEvent.getLevel())));
        sb.append("\",");
        sb.append(this.eol);
        sb.append(this.indent2);
        sb.append("\"thread\":\"");
        sb.append(Transform.escapeJsonControlCharacters(logEvent.getThreadName()));
        sb.append("\",");
        sb.append(this.eol);
        final Message message = logEvent.getMessage();
        if (message != null) {
            if (message instanceof MultiformatMessage) {
                final String[] formats = ((MultiformatMessage)message).getFormats();
                while (0 < formats.length) {
                    if (formats[0].equalsIgnoreCase("JSON")) {
                        break;
                    }
                    int n = 0;
                    ++n;
                }
            }
            sb.append(this.indent2);
            sb.append("\"message\":\"");
            if (true) {
                sb.append(((MultiformatMessage)message).getFormattedMessage(JSONLayout.FORMATS));
            }
            else {
                Transform.appendEscapingCDATA(sb, logEvent.getMessage().getFormattedMessage());
            }
            sb.append('\"');
        }
        if (logEvent.getContextStack().getDepth() > 0) {
            sb.append(",");
            sb.append(this.eol);
            sb.append("\"ndc\":");
            Transform.appendEscapingCDATA(sb, logEvent.getContextStack().toString());
            sb.append("\"");
        }
        final Throwable thrown = logEvent.getThrown();
        if (thrown != null) {
            sb.append(",");
            sb.append(this.eol);
            sb.append(this.indent2);
            sb.append("\"throwable\":\"");
            final Iterator iterator = Throwables.toStringList(thrown).iterator();
            while (iterator.hasNext()) {
                sb.append(Transform.escapeJsonControlCharacters(iterator.next()));
                sb.append("\\\\n");
            }
            sb.append("\"");
        }
        if (this.locationInfo) {
            final StackTraceElement source = logEvent.getSource();
            sb.append(",");
            sb.append(this.eol);
            sb.append(this.indent2);
            sb.append("\"LocationInfo\":{");
            sb.append(this.eol);
            sb.append(this.indent3);
            sb.append("\"class\":\"");
            sb.append(Transform.escapeJsonControlCharacters(source.getClassName()));
            sb.append("\",");
            sb.append(this.eol);
            sb.append(this.indent3);
            sb.append("\"method\":\"");
            sb.append(Transform.escapeJsonControlCharacters(source.getMethodName()));
            sb.append("\",");
            sb.append(this.eol);
            sb.append(this.indent3);
            sb.append("\"file\":\"");
            sb.append(Transform.escapeJsonControlCharacters(source.getFileName()));
            sb.append("\",");
            sb.append(this.eol);
            sb.append(this.indent3);
            sb.append("\"line\":\"");
            sb.append(source.getLineNumber());
            sb.append("\"");
            sb.append(this.eol);
            sb.append(this.indent2);
            sb.append("}");
        }
        if (this.properties && logEvent.getContextMap().size() > 0) {
            sb.append(",");
            sb.append(this.eol);
            sb.append(this.indent2);
            sb.append("\"Properties\":[");
            sb.append(this.eol);
            final Set<Map.Entry<String, V>> entrySet = (Set<Map.Entry<String, V>>)logEvent.getContextMap().entrySet();
            for (final Map.Entry<String, V> entry : entrySet) {
                sb.append(this.indent3);
                sb.append('{');
                sb.append(this.eol);
                sb.append(this.indent4);
                sb.append("\"name\":\"");
                sb.append(Transform.escapeJsonControlCharacters(entry.getKey()));
                sb.append("\",");
                sb.append(this.eol);
                sb.append(this.indent4);
                sb.append("\"value\":\"");
                sb.append(Transform.escapeJsonControlCharacters(String.valueOf(entry.getValue())));
                sb.append("\"");
                sb.append(this.eol);
                sb.append(this.indent3);
                sb.append("}");
                if (1 < entrySet.size()) {
                    sb.append(",");
                }
                sb.append(this.eol);
                int n2 = 0;
                ++n2;
            }
            sb.append(this.indent2);
            sb.append("]");
        }
        sb.append(this.eol);
        sb.append(this.indent1);
        sb.append("}");
        return sb.toString();
    }
    
    @Override
    public byte[] getHeader() {
        if (!this.complete) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(this.eol);
        return sb.toString().getBytes(this.getCharset());
    }
    
    @Override
    public byte[] getFooter() {
        if (!this.complete) {
            return null;
        }
        return (this.eol + "]" + this.eol).getBytes(this.getCharset());
    }
    
    @Override
    public Map getContentFormat() {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("version", "2.0");
        return hashMap;
    }
    
    @Override
    public String getContentType() {
        return "application/json; charset=" + this.getCharset();
    }
    
    @PluginFactory
    public static JSONLayout createLayout(@PluginAttribute("locationInfo") final String s, @PluginAttribute("properties") final String s2, @PluginAttribute("complete") final String s3, @PluginAttribute("compact") final String s4, @PluginAttribute("charset") final String s5) {
        return new JSONLayout(Boolean.parseBoolean(s), Boolean.parseBoolean(s2), Boolean.parseBoolean(s3), Boolean.parseBoolean(s4), Charsets.getSupportedCharset(s5, Charsets.UTF_8));
    }
    
    @Override
    public Serializable toSerializable(final LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }
    
    static {
        JSONLayout.FORMATS = new String[] { "json" };
    }
}
