package org.apache.logging.log4j.core.layout;

import java.text.*;
import java.nio.charset.*;
import java.util.regex.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.net.*;
import java.net.*;
import java.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.io.*;

@Plugin(name = "SyslogLayout", category = "Core", elementType = "layout", printObject = true)
public class SyslogLayout extends AbstractStringLayout
{
    public static final Pattern NEWLINE_PATTERN;
    private final Facility facility;
    private final boolean includeNewLine;
    private final String escapeNewLine;
    private final SimpleDateFormat dateFormat;
    private final String localHostname;
    
    protected SyslogLayout(final Facility facility, final boolean includeNewLine, final String s, final Charset charset) {
        super(charset);
        this.dateFormat = new SimpleDateFormat("MMM dd HH:mm:ss ", Locale.ENGLISH);
        this.localHostname = this.getLocalHostname();
        this.facility = facility;
        this.includeNewLine = includeNewLine;
        this.escapeNewLine = ((s == null) ? null : Matcher.quoteReplacement(s));
    }
    
    @Override
    public String toSerializable(final LogEvent logEvent) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(Priority.getPriority(this.facility, logEvent.getLevel()));
        sb.append(">");
        this.addDate(logEvent.getMillis(), sb);
        sb.append(" ");
        sb.append(this.localHostname);
        sb.append(" ");
        String s = logEvent.getMessage().getFormattedMessage();
        if (null != this.escapeNewLine) {
            s = SyslogLayout.NEWLINE_PATTERN.matcher(s).replaceAll(this.escapeNewLine);
        }
        sb.append(s);
        if (this.includeNewLine) {
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private String getLocalHostname() {
        return InetAddress.getLocalHost().getHostName();
    }
    
    private synchronized void addDate(final long n, final StringBuilder sb) {
        final int n2 = sb.length() + 4;
        sb.append(this.dateFormat.format(new Date(n)));
        if (sb.charAt(n2) == '0') {
            sb.setCharAt(n2, ' ');
        }
    }
    
    @Override
    public Map getContentFormat() {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("structured", "false");
        hashMap.put("formatType", "logfilepatternreceiver");
        hashMap.put("dateFormat", this.dateFormat.toPattern());
        hashMap.put("format", "<LEVEL>TIMESTAMP PROP(HOSTNAME) MESSAGE");
        return hashMap;
    }
    
    @PluginFactory
    public static SyslogLayout createLayout(@PluginAttribute("facility") final String s, @PluginAttribute("newLine") final String s2, @PluginAttribute("newLineEscape") final String s3, @PluginAttribute("charset") final String s4) {
        return new SyslogLayout(Facility.toFacility(s, Facility.LOCAL0), Boolean.parseBoolean(s2), s3, Charsets.getSupportedCharset(s4));
    }
    
    @Override
    public Serializable toSerializable(final LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }
    
    static {
        NEWLINE_PATTERN = Pattern.compile("\\r?\\n");
    }
}
