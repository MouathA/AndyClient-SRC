package org.apache.logging.log4j.core.layout;

import java.nio.charset.*;
import java.lang.management.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;
import java.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.io.*;

@Plugin(name = "HTMLLayout", category = "Core", elementType = "layout", printObject = true)
public final class HTMLLayout extends AbstractStringLayout
{
    private static final int BUF_SIZE = 256;
    private static final String TRACE_PREFIX = "<br />&nbsp;&nbsp;&nbsp;&nbsp;";
    private static final String REGEXP;
    private static final String DEFAULT_TITLE = "Log4j Log Messages";
    private static final String DEFAULT_CONTENT_TYPE = "text/html";
    private final long jvmStartTime;
    private final boolean locationInfo;
    private final String title;
    private final String contentType;
    private final String font;
    private final String fontSize;
    private final String headerSize;
    
    private HTMLLayout(final boolean locationInfo, final String title, final String contentType, final Charset charset, final String font, final String fontSize, final String headerSize) {
        super(charset);
        this.jvmStartTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        this.locationInfo = locationInfo;
        this.title = title;
        this.contentType = contentType;
        this.font = font;
        this.fontSize = fontSize;
        this.headerSize = headerSize;
    }
    
    @Override
    public String toSerializable(final LogEvent logEvent) {
        final StringBuilder sb = new StringBuilder(256);
        sb.append(Constants.LINE_SEP).append("<tr>").append(Constants.LINE_SEP);
        sb.append("<td>");
        sb.append(logEvent.getMillis() - this.jvmStartTime);
        sb.append("</td>").append(Constants.LINE_SEP);
        final String escapeHtmlTags = Transform.escapeHtmlTags(logEvent.getThreadName());
        sb.append("<td title=\"").append(escapeHtmlTags).append(" thread\">");
        sb.append(escapeHtmlTags);
        sb.append("</td>").append(Constants.LINE_SEP);
        sb.append("<td title=\"Level\">");
        if (logEvent.getLevel().equals(Level.DEBUG)) {
            sb.append("<font color=\"#339933\">");
            sb.append(Transform.escapeHtmlTags(String.valueOf(logEvent.getLevel())));
            sb.append("</font>");
        }
        else if (logEvent.getLevel().isAtLeastAsSpecificAs(Level.WARN)) {
            sb.append("<font color=\"#993300\"><strong>");
            sb.append(Transform.escapeHtmlTags(String.valueOf(logEvent.getLevel())));
            sb.append("</strong></font>");
        }
        else {
            sb.append(Transform.escapeHtmlTags(String.valueOf(logEvent.getLevel())));
        }
        sb.append("</td>").append(Constants.LINE_SEP);
        String escapeHtmlTags2 = Transform.escapeHtmlTags(logEvent.getLoggerName());
        if (escapeHtmlTags2.isEmpty()) {
            escapeHtmlTags2 = "root";
        }
        sb.append("<td title=\"").append(escapeHtmlTags2).append(" logger\">");
        sb.append(escapeHtmlTags2);
        sb.append("</td>").append(Constants.LINE_SEP);
        if (this.locationInfo) {
            final StackTraceElement source = logEvent.getSource();
            sb.append("<td>");
            sb.append(Transform.escapeHtmlTags(source.getFileName()));
            sb.append(':');
            sb.append(source.getLineNumber());
            sb.append("</td>").append(Constants.LINE_SEP);
        }
        sb.append("<td title=\"Message\">");
        sb.append(Transform.escapeHtmlTags(logEvent.getMessage().getFormattedMessage()).replaceAll(HTMLLayout.REGEXP, "<br />"));
        sb.append("</td>").append(Constants.LINE_SEP);
        sb.append("</tr>").append(Constants.LINE_SEP);
        if (logEvent.getContextStack().getDepth() > 0) {
            sb.append("<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : ").append(this.fontSize);
            sb.append(";\" colspan=\"6\" ");
            sb.append("title=\"Nested Diagnostic Context\">");
            sb.append("NDC: ").append(Transform.escapeHtmlTags(logEvent.getContextStack().toString()));
            sb.append("</td></tr>").append(Constants.LINE_SEP);
        }
        if (logEvent.getContextMap().size() > 0) {
            sb.append("<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : ").append(this.fontSize);
            sb.append(";\" colspan=\"6\" ");
            sb.append("title=\"Mapped Diagnostic Context\">");
            sb.append("MDC: ").append(Transform.escapeHtmlTags(logEvent.getContextMap().toString()));
            sb.append("</td></tr>").append(Constants.LINE_SEP);
        }
        final Throwable thrown = logEvent.getThrown();
        if (thrown != null) {
            sb.append("<tr><td bgcolor=\"#993300\" style=\"color:White; font-size : ").append(this.fontSize);
            sb.append(";\" colspan=\"6\">");
            this.appendThrowableAsHTML(thrown, sb);
            sb.append("</td></tr>").append(Constants.LINE_SEP);
        }
        return sb.toString();
    }
    
    @Override
    public Map getContentFormat() {
        return new HashMap();
    }
    
    @Override
    public String getContentType() {
        return this.contentType;
    }
    
    private void appendThrowableAsHTML(final Throwable t, final StringBuilder sb) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        t.printStackTrace(printWriter);
        printWriter.flush();
        final LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(stringWriter.toString()));
        final ArrayList<String> list = new ArrayList<String>();
        for (String s = lineNumberReader.readLine(); s != null; s = lineNumberReader.readLine()) {
            list.add(s);
        }
        for (final String s2 : list) {
            if (!false) {
                sb.append("<br />&nbsp;&nbsp;&nbsp;&nbsp;");
            }
            sb.append(Transform.escapeHtmlTags(s2));
            sb.append(Constants.LINE_SEP);
        }
    }
    
    @Override
    public byte[] getHeader() {
        final StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" ");
        sb.append("\"http://www.w3.org/TR/html4/loose.dtd\">");
        sb.append(Constants.LINE_SEP);
        sb.append("<html>").append(Constants.LINE_SEP);
        sb.append("<head>").append(Constants.LINE_SEP);
        sb.append("<meta charset=\"").append(this.getCharset()).append("\"/>").append(Constants.LINE_SEP);
        sb.append("<title>").append(this.title).append("</title>").append(Constants.LINE_SEP);
        sb.append("<style type=\"text/css\">").append(Constants.LINE_SEP);
        sb.append("<!--").append(Constants.LINE_SEP);
        sb.append("body, table {font-family:").append(this.font).append("; font-size: ");
        sb.append(this.headerSize).append(";}").append(Constants.LINE_SEP);
        sb.append("th {background: #336699; color: #FFFFFF; text-align: left;}").append(Constants.LINE_SEP);
        sb.append("-->").append(Constants.LINE_SEP);
        sb.append("</style>").append(Constants.LINE_SEP);
        sb.append("</head>").append(Constants.LINE_SEP);
        sb.append("<body bgcolor=\"#FFFFFF\" topmargin=\"6\" leftmargin=\"6\">").append(Constants.LINE_SEP);
        sb.append("<hr size=\"1\" noshade>").append(Constants.LINE_SEP);
        sb.append("Log session start time " + new Date() + "<br>").append(Constants.LINE_SEP);
        sb.append("<br>").append(Constants.LINE_SEP);
        sb.append("<table cellspacing=\"0\" cellpadding=\"4\" border=\"1\" bordercolor=\"#224466\" width=\"100%\">");
        sb.append(Constants.LINE_SEP);
        sb.append("<tr>").append(Constants.LINE_SEP);
        sb.append("<th>Time</th>").append(Constants.LINE_SEP);
        sb.append("<th>Thread</th>").append(Constants.LINE_SEP);
        sb.append("<th>Level</th>").append(Constants.LINE_SEP);
        sb.append("<th>Logger</th>").append(Constants.LINE_SEP);
        if (this.locationInfo) {
            sb.append("<th>File:Line</th>").append(Constants.LINE_SEP);
        }
        sb.append("<th>Message</th>").append(Constants.LINE_SEP);
        sb.append("</tr>").append(Constants.LINE_SEP);
        return sb.toString().getBytes(this.getCharset());
    }
    
    @Override
    public byte[] getFooter() {
        final StringBuilder sb = new StringBuilder();
        sb.append("</table>").append(Constants.LINE_SEP);
        sb.append("<br>").append(Constants.LINE_SEP);
        sb.append("</body></html>");
        return sb.toString().getBytes(this.getCharset());
    }
    
    @PluginFactory
    public static HTMLLayout createLayout(@PluginAttribute("locationInfo") final String s, @PluginAttribute("title") String s2, @PluginAttribute("contentType") String string, @PluginAttribute("charset") final String s3, @PluginAttribute("fontSize") String fontSize, @PluginAttribute("fontName") String s4) {
        final Charset supportedCharset = Charsets.getSupportedCharset(s3, Charsets.UTF_8);
        if (s4 == null) {
            s4 = "arial,sans-serif";
        }
        final FontSize fontSize2 = FontSize.getFontSize(fontSize);
        fontSize = fontSize2.getFontSize();
        final String fontSize3 = fontSize2.larger().getFontSize();
        final boolean boolean1 = Boolean.parseBoolean(s);
        if (s2 == null) {
            s2 = "Log4j Log Messages";
        }
        if (string == null) {
            string = "text/html; charset=" + supportedCharset;
        }
        return new HTMLLayout(boolean1, s2, string, supportedCharset, s4, fontSize, fontSize3);
    }
    
    @Override
    public Serializable toSerializable(final LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }
    
    static {
        REGEXP = (Constants.LINE_SEP.equals("\n") ? "\n" : (Constants.LINE_SEP + "|\n"));
    }
    
    private enum FontSize
    {
        SMALLER("SMALLER", 0, "smaller"), 
        XXSMALL("XXSMALL", 1, "xx-small"), 
        XSMALL("XSMALL", 2, "x-small"), 
        SMALL("SMALL", 3, "small"), 
        MEDIUM("MEDIUM", 4, "medium"), 
        LARGE("LARGE", 5, "large"), 
        XLARGE("XLARGE", 6, "x-large"), 
        XXLARGE("XXLARGE", 7, "xx-large"), 
        LARGER("LARGER", 8, "larger");
        
        private final String size;
        private static final FontSize[] $VALUES;
        
        private FontSize(final String s, final int n, final String size) {
            this.size = size;
        }
        
        public String getFontSize() {
            return this.size;
        }
        
        public static FontSize getFontSize(final String s) {
            final FontSize[] values = values();
            while (0 < values.length) {
                final FontSize fontSize = values[0];
                if (fontSize.size.equals(s)) {
                    return fontSize;
                }
                int n = 0;
                ++n;
            }
            return FontSize.SMALL;
        }
        
        public FontSize larger() {
            return (this.ordinal() < FontSize.XXLARGE.ordinal()) ? values()[this.ordinal() + 1] : this;
        }
        
        static {
            $VALUES = new FontSize[] { FontSize.SMALLER, FontSize.XXSMALL, FontSize.XSMALL, FontSize.SMALL, FontSize.MEDIUM, FontSize.LARGE, FontSize.XLARGE, FontSize.XXLARGE, FontSize.LARGER };
        }
    }
}
