package org.apache.logging.log4j.core.layout;

import java.nio.charset.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.message.*;
import java.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.io.*;

@Plugin(name = "XMLLayout", category = "Core", elementType = "layout", printObject = true)
public class XMLLayout extends AbstractStringLayout
{
    private static final String XML_NAMESPACE = "http://logging.apache.org/log4j/2.0/events";
    private static final String ROOT_TAG = "Events";
    private static final int DEFAULT_SIZE = 256;
    private static final String DEFAULT_EOL = "\r\n";
    private static final String COMPACT_EOL = "";
    private static final String DEFAULT_INDENT = "  ";
    private static final String COMPACT_INDENT = "";
    private static final String DEFAULT_NS_PREFIX = "log4j";
    private final boolean locationInfo;
    private final boolean properties;
    private final boolean complete;
    private final String namespacePrefix;
    private final String eol;
    private final String indent1;
    private final String indent2;
    private final String indent3;
    
    protected XMLLayout(final boolean locationInfo, final boolean properties, final boolean complete, final boolean b, final String s, final Charset charset) {
        super(charset);
        this.locationInfo = locationInfo;
        this.properties = properties;
        this.complete = complete;
        this.eol = (b ? "" : "\r\n");
        this.indent1 = (b ? "" : "  ");
        this.indent2 = this.indent1 + this.indent1;
        this.indent3 = this.indent2 + this.indent1;
        this.namespacePrefix = (Strings.isEmpty(s) ? "log4j" : s) + ":";
    }
    
    @Override
    public String toSerializable(final LogEvent logEvent) {
        final StringBuilder sb = new StringBuilder(256);
        sb.append(this.indent1);
        sb.append('<');
        if (!this.complete) {
            sb.append(this.namespacePrefix);
        }
        sb.append("Event logger=\"");
        String loggerName = logEvent.getLoggerName();
        if (loggerName.isEmpty()) {
            loggerName = "root";
        }
        sb.append(Transform.escapeHtmlTags(loggerName));
        sb.append("\" timestamp=\"");
        sb.append(logEvent.getMillis());
        sb.append("\" level=\"");
        sb.append(Transform.escapeHtmlTags(String.valueOf(logEvent.getLevel())));
        sb.append("\" thread=\"");
        sb.append(Transform.escapeHtmlTags(logEvent.getThreadName()));
        sb.append("\">");
        sb.append(this.eol);
        final Message message = logEvent.getMessage();
        if (message != null) {
            if (message instanceof MultiformatMessage) {
                final String[] formats = ((MultiformatMessage)message).getFormats();
                while (0 < formats.length) {
                    if (formats[0].equalsIgnoreCase("XML")) {
                        break;
                    }
                    int n = 0;
                    ++n;
                }
            }
            sb.append(this.indent2);
            sb.append('<');
            if (!this.complete) {
                sb.append(this.namespacePrefix);
            }
            sb.append("Message>");
            sb.append(((MultiformatMessage)message).getFormattedMessage(XMLLayout.FORMATS));
            sb.append("</");
            if (!this.complete) {
                sb.append(this.namespacePrefix);
            }
            sb.append("Message>");
            sb.append(this.eol);
        }
        if (logEvent.getContextStack().getDepth() > 0) {
            sb.append(this.indent2);
            sb.append('<');
            if (!this.complete) {
                sb.append(this.namespacePrefix);
            }
            sb.append("NDC><![CDATA[");
            Transform.appendEscapingCDATA(sb, logEvent.getContextStack().toString());
            sb.append("]]></");
            if (!this.complete) {
                sb.append(this.namespacePrefix);
            }
            sb.append("NDC>");
            sb.append(this.eol);
        }
        final Throwable thrown = logEvent.getThrown();
        if (thrown != null) {
            final List stringList = Throwables.toStringList(thrown);
            sb.append(this.indent2);
            sb.append('<');
            if (!this.complete) {
                sb.append(this.namespacePrefix);
            }
            sb.append("Throwable><![CDATA[");
            final Iterator<String> iterator = stringList.iterator();
            while (iterator.hasNext()) {
                Transform.appendEscapingCDATA(sb, iterator.next());
                sb.append(this.eol);
            }
            sb.append("]]></");
            if (!this.complete) {
                sb.append(this.namespacePrefix);
            }
            sb.append("Throwable>");
            sb.append(this.eol);
        }
        if (this.locationInfo) {
            final StackTraceElement source = logEvent.getSource();
            sb.append(this.indent2);
            sb.append('<');
            if (!this.complete) {
                sb.append(this.namespacePrefix);
            }
            sb.append("LocationInfo class=\"");
            sb.append(Transform.escapeHtmlTags(source.getClassName()));
            sb.append("\" method=\"");
            sb.append(Transform.escapeHtmlTags(source.getMethodName()));
            sb.append("\" file=\"");
            sb.append(Transform.escapeHtmlTags(source.getFileName()));
            sb.append("\" line=\"");
            sb.append(source.getLineNumber());
            sb.append("\"/>");
            sb.append(this.eol);
        }
        if (this.properties && logEvent.getContextMap().size() > 0) {
            sb.append(this.indent2);
            sb.append('<');
            if (!this.complete) {
                sb.append(this.namespacePrefix);
            }
            sb.append("Properties>");
            sb.append(this.eol);
            for (final Map.Entry<String, V> entry : logEvent.getContextMap().entrySet()) {
                sb.append(this.indent3);
                sb.append('<');
                if (!this.complete) {
                    sb.append(this.namespacePrefix);
                }
                sb.append("Data name=\"");
                sb.append(Transform.escapeHtmlTags(entry.getKey()));
                sb.append("\" value=\"");
                sb.append(Transform.escapeHtmlTags(String.valueOf(entry.getValue())));
                sb.append("\"/>");
                sb.append(this.eol);
            }
            sb.append(this.indent2);
            sb.append("</");
            if (!this.complete) {
                sb.append(this.namespacePrefix);
            }
            sb.append("Properties>");
            sb.append(this.eol);
        }
        sb.append(this.indent1);
        sb.append("</");
        if (!this.complete) {
            sb.append(this.namespacePrefix);
        }
        sb.append("Event>");
        sb.append(this.eol);
        return sb.toString();
    }
    
    @Override
    public byte[] getHeader() {
        if (!this.complete) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"");
        sb.append(this.getCharset().name());
        sb.append("\"?>");
        sb.append(this.eol);
        sb.append('<');
        sb.append("Events");
        sb.append(" xmlns=\"http://logging.apache.org/log4j/2.0/events\">");
        sb.append(this.eol);
        return sb.toString().getBytes(this.getCharset());
    }
    
    @Override
    public byte[] getFooter() {
        if (!this.complete) {
            return null;
        }
        return ("</Events>" + this.eol).getBytes(this.getCharset());
    }
    
    @Override
    public Map getContentFormat() {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("xsd", "log4j-events.xsd");
        hashMap.put("version", "2.0");
        return hashMap;
    }
    
    @Override
    public String getContentType() {
        return "text/xml; charset=" + this.getCharset();
    }
    
    @PluginFactory
    public static XMLLayout createLayout(@PluginAttribute("locationInfo") final String s, @PluginAttribute("properties") final String s2, @PluginAttribute("complete") final String s3, @PluginAttribute("compact") final String s4, @PluginAttribute("namespacePrefix") final String s5, @PluginAttribute("charset") final String s6) {
        return new XMLLayout(Boolean.parseBoolean(s), Boolean.parseBoolean(s2), Boolean.parseBoolean(s3), Boolean.parseBoolean(s4), s5, Charsets.getSupportedCharset(s6, Charsets.UTF_8));
    }
    
    @Override
    public Serializable toSerializable(final LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }
    
    static {
        XMLLayout.FORMATS = new String[] { "xml" };
    }
}
