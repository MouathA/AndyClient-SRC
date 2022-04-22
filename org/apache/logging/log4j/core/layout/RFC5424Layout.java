package org.apache.logging.log4j.core.layout;

import org.apache.logging.log4j.core.config.*;
import java.nio.charset.*;
import java.util.regex.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.net.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.*;
import java.util.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.io.*;
import org.apache.logging.log4j.core.helpers.*;

@Plugin(name = "RFC5424Layout", category = "Core", elementType = "layout", printObject = true)
public class RFC5424Layout extends AbstractStringLayout
{
    private static final String LF = "\n";
    public static final int DEFAULT_ENTERPRISE_NUMBER = 18060;
    public static final String DEFAULT_ID = "Audit";
    public static final Pattern NEWLINE_PATTERN;
    public static final Pattern PARAM_VALUE_ESCAPE_PATTERN;
    protected static final String DEFAULT_MDCID = "mdc";
    private static final int TWO_DIGITS = 10;
    private static final int THREE_DIGITS = 100;
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final int MINUTES_PER_HOUR = 60;
    private static final String COMPONENT_KEY = "RFC5424-Converter";
    private final Facility facility;
    private final String defaultId;
    private final int enterpriseNumber;
    private final boolean includeMDC;
    private final String mdcId;
    private final StructuredDataId mdcSDID;
    private final String localHostName;
    private final String appName;
    private final String messageId;
    private final String configName;
    private final String mdcPrefix;
    private final String eventPrefix;
    private final List mdcExcludes;
    private final List mdcIncludes;
    private final List mdcRequired;
    private final ListChecker checker;
    private final ListChecker noopChecker;
    private final boolean includeNewLine;
    private final String escapeNewLine;
    private final boolean useTLSMessageFormat;
    private long lastTimestamp;
    private String timestamppStr;
    private final List exceptionFormatters;
    private final Map fieldFormatters;
    
    private RFC5424Layout(final Configuration configuration, final Facility facility, final String s, final int enterpriseNumber, final boolean includeMDC, final boolean includeNewLine, final String s2, final String mdcId, final String mdcPrefix, final String eventPrefix, final String appName, final String messageId, final String s3, final String s4, final String s5, final Charset charset, final String s6, final boolean useTLSMessageFormat, final LoggerFields[] array) {
        super(charset);
        this.noopChecker = new NoopChecker(null);
        this.lastTimestamp = -1L;
        final PatternParser patternParser = createPatternParser(configuration, ThrowablePatternConverter.class);
        this.exceptionFormatters = ((s6 == null) ? null : patternParser.parse(s6, false));
        this.facility = facility;
        this.defaultId = ((s == null) ? "Audit" : s);
        this.enterpriseNumber = enterpriseNumber;
        this.includeMDC = includeMDC;
        this.includeNewLine = includeNewLine;
        this.escapeNewLine = ((s2 == null) ? null : Matcher.quoteReplacement(s2));
        this.mdcId = mdcId;
        this.mdcSDID = new StructuredDataId(mdcId, this.enterpriseNumber, null, null);
        this.mdcPrefix = mdcPrefix;
        this.eventPrefix = eventPrefix;
        this.appName = appName;
        this.messageId = messageId;
        this.useTLSMessageFormat = useTLSMessageFormat;
        this.localHostName = NetUtils.getLocalHostname();
        ListChecker listChecker = null;
        int n = 0;
        if (s3 != null) {
            final String[] split = s3.split(",");
            if (split.length > 0) {
                listChecker = new ExcludeChecker(null);
                this.mdcExcludes = new ArrayList(split.length);
                final String[] array2 = split;
                while (0 < array2.length) {
                    this.mdcExcludes.add(array2[0].trim());
                    ++n;
                }
            }
            else {
                this.mdcExcludes = null;
            }
        }
        else {
            this.mdcExcludes = null;
        }
        if (s4 != null) {
            final String[] split2 = s4.split(",");
            if (split2.length > 0) {
                listChecker = new IncludeChecker(null);
                this.mdcIncludes = new ArrayList(split2.length);
                final String[] array3 = split2;
                while (0 < array3.length) {
                    this.mdcIncludes.add(array3[0].trim());
                    ++n;
                }
            }
            else {
                this.mdcIncludes = null;
            }
        }
        else {
            this.mdcIncludes = null;
        }
        if (s5 != null) {
            final String[] split3 = s5.split(",");
            if (split3.length > 0) {
                this.mdcRequired = new ArrayList(split3.length);
                final String[] array4 = split3;
                while (0 < array4.length) {
                    this.mdcRequired.add(array4[0].trim());
                    ++n;
                }
            }
            else {
                this.mdcRequired = null;
            }
        }
        else {
            this.mdcRequired = null;
        }
        this.checker = ((listChecker != null) ? listChecker : this.noopChecker);
        final String s7 = (configuration == null) ? null : configuration.getName();
        this.configName = ((s7 != null && s7.length() > 0) ? s7 : null);
        this.fieldFormatters = this.createFieldFormatters(array, configuration);
    }
    
    private Map createFieldFormatters(final LoggerFields[] array, final Configuration configuration) {
        final HashMap<String, FieldFormatter> hashMap = new HashMap<String, FieldFormatter>();
        if (array != null) {
            while (0 < array.length) {
                final LoggerFields loggerFields = array[0];
                final StructuredDataId structuredDataId = (loggerFields.getSdId() == null) ? this.mdcSDID : loggerFields.getSdId();
                final HashMap<Object, List> hashMap2 = new HashMap<Object, List>();
                final Map map = loggerFields.getMap();
                if (!map.isEmpty()) {
                    final PatternParser patternParser = createPatternParser(configuration, null);
                    for (final Map.Entry<K, String> entry : map.entrySet()) {
                        hashMap2.put(entry.getKey(), patternParser.parse(entry.getValue(), false));
                    }
                    hashMap.put(structuredDataId.toString(), new FieldFormatter(hashMap2, loggerFields.getDiscardIfAllFieldsAreEmpty()));
                }
                int n = 0;
                ++n;
            }
        }
        return (hashMap.size() > 0) ? hashMap : null;
    }
    
    private static PatternParser createPatternParser(final Configuration configuration, final Class clazz) {
        if (configuration == null) {
            return new PatternParser(configuration, "Converter", LogEventPatternConverter.class, clazz);
        }
        PatternParser patternParser = (PatternParser)configuration.getComponent("RFC5424-Converter");
        if (patternParser == null) {
            configuration.addComponent("RFC5424-Converter", new PatternParser(configuration, "Converter", ThrowablePatternConverter.class));
            patternParser = (PatternParser)configuration.getComponent("RFC5424-Converter");
        }
        return patternParser;
    }
    
    @Override
    public Map getContentFormat() {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("structured", "true");
        hashMap.put("formatType", "RFC5424");
        return hashMap;
    }
    
    @Override
    public String toSerializable(final LogEvent logEvent) {
        final StringBuilder sb = new StringBuilder();
        this.appendPriority(sb, logEvent.getLevel());
        this.appendTimestamp(sb, logEvent.getMillis());
        this.appendSpace(sb);
        this.appendHostName(sb);
        this.appendSpace(sb);
        this.appendAppName(sb);
        this.appendSpace(sb);
        this.appendProcessId(sb);
        this.appendSpace(sb);
        this.appendMessageId(sb, logEvent.getMessage());
        this.appendSpace(sb);
        this.appendStructuredElements(sb, logEvent);
        this.appendMessage(sb, logEvent);
        if (this.useTLSMessageFormat) {
            return new TLSSyslogFrame(sb.toString()).toString();
        }
        return sb.toString();
    }
    
    private void appendPriority(final StringBuilder sb, final Level level) {
        sb.append("<");
        sb.append(Priority.getPriority(this.facility, level));
        sb.append(">1 ");
    }
    
    private void appendTimestamp(final StringBuilder sb, final long n) {
        sb.append(this.computeTimeStampString(n));
    }
    
    private void appendSpace(final StringBuilder sb) {
        sb.append(" ");
    }
    
    private void appendHostName(final StringBuilder sb) {
        sb.append(this.localHostName);
    }
    
    private void appendAppName(final StringBuilder sb) {
        if (this.appName != null) {
            sb.append(this.appName);
        }
        else if (this.configName != null) {
            sb.append(this.configName);
        }
        else {
            sb.append("-");
        }
    }
    
    private void appendProcessId(final StringBuilder sb) {
        sb.append(this.getProcId());
    }
    
    private void appendMessageId(final StringBuilder sb, final Message message) {
        final String s = (message instanceof StructuredDataMessage) ? ((StructuredDataMessage)message).getType() : null;
        if (s != null) {
            sb.append(s);
        }
        else if (this.messageId != null) {
            sb.append(this.messageId);
        }
        else {
            sb.append("-");
        }
    }
    
    private void appendMessage(final StringBuilder sb, final LogEvent logEvent) {
        final String format = logEvent.getMessage().getFormat();
        if (format != null && format.length() > 0) {
            sb.append(" ").append(this.escapeNewlines(format, this.escapeNewLine));
        }
        if (this.exceptionFormatters != null && logEvent.getThrown() != null) {
            final StringBuilder sb2 = new StringBuilder("\n");
            final Iterator<PatternFormatter> iterator = this.exceptionFormatters.iterator();
            while (iterator.hasNext()) {
                iterator.next().format(logEvent, sb2);
            }
            sb.append(this.escapeNewlines(sb2.toString(), this.escapeNewLine));
        }
        if (this.includeNewLine) {
            sb.append("\n");
        }
    }
    
    private void appendStructuredElements(final StringBuilder sb, final LogEvent logEvent) {
        final Message message = logEvent.getMessage();
        final boolean b = message instanceof StructuredDataMessage;
        if (!b && this.fieldFormatters != null && this.fieldFormatters.size() == 0 && !this.includeMDC) {
            sb.append("-");
            return;
        }
        final HashMap<Object, StructuredDataElement> hashMap = new HashMap<Object, StructuredDataElement>();
        final Map contextMap = logEvent.getContextMap();
        if (this.mdcRequired != null) {
            this.checkRequired(contextMap);
        }
        if (this.fieldFormatters != null) {
            for (final Map.Entry<String, V> entry : this.fieldFormatters.entrySet()) {
                hashMap.put(entry.getKey(), ((FieldFormatter)entry.getValue()).format(logEvent));
            }
        }
        if (this.includeMDC && contextMap.size() > 0) {
            if (hashMap.containsKey(this.mdcSDID.toString())) {
                final StructuredDataElement structuredDataElement = hashMap.get(this.mdcSDID.toString());
                structuredDataElement.union(contextMap);
                hashMap.put(this.mdcSDID.toString(), structuredDataElement);
            }
            else {
                hashMap.put(this.mdcSDID.toString(), new StructuredDataElement(contextMap, false));
            }
        }
        if (b) {
            final StructuredDataMessage structuredDataMessage = (StructuredDataMessage)message;
            final Map data = structuredDataMessage.getData();
            final StructuredDataId id = structuredDataMessage.getId();
            if (hashMap.containsKey(id.toString())) {
                final StructuredDataElement structuredDataElement2 = hashMap.get(id.toString());
                structuredDataElement2.union(data);
                hashMap.put(id.toString(), structuredDataElement2);
            }
            else {
                hashMap.put(id.toString(), new StructuredDataElement(data, false));
            }
        }
        if (hashMap.size() == 0) {
            sb.append("-");
            return;
        }
        for (final Map.Entry<Object, Object> entry2 : hashMap.entrySet()) {
            this.formatStructuredElement(entry2.getKey(), this.mdcPrefix, entry2.getValue(), sb, this.checker);
        }
    }
    
    private String escapeNewlines(final String s, final String s2) {
        if (null == s2) {
            return s;
        }
        return RFC5424Layout.NEWLINE_PATTERN.matcher(s).replaceAll(s2);
    }
    
    protected String getProcId() {
        return "-";
    }
    
    protected List getMdcExcludes() {
        return this.mdcExcludes;
    }
    
    protected List getMdcIncludes() {
        return this.mdcIncludes;
    }
    
    private String computeTimeStampString(final long n) {
        // monitorenter(this)
        final long lastTimestamp = this.lastTimestamp;
        if (n == this.lastTimestamp) {
            // monitorexit(this)
            return this.timestamppStr;
        }
        // monitorexit(this)
        final StringBuilder sb = new StringBuilder();
        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(n);
        sb.append(Integer.toString(gregorianCalendar.get(1)));
        sb.append("-");
        this.pad(gregorianCalendar.get(2) + 1, 10, sb);
        sb.append("-");
        this.pad(gregorianCalendar.get(5), 10, sb);
        sb.append("T");
        this.pad(gregorianCalendar.get(11), 10, sb);
        sb.append(":");
        this.pad(gregorianCalendar.get(12), 10, sb);
        sb.append(":");
        this.pad(gregorianCalendar.get(13), 10, sb);
        final int value = gregorianCalendar.get(14);
        if (value != 0) {
            sb.append('.');
            this.pad(value, 100, sb);
        }
        int n2 = (gregorianCalendar.get(15) + gregorianCalendar.get(16)) / 60000;
        if (n2 == 0) {
            sb.append("Z");
        }
        else {
            if (n2 < 0) {
                n2 = -n2;
                sb.append("-");
            }
            else {
                sb.append("+");
            }
            final int n3 = n2 / 60;
            final int n4 = n2 - n3 * 60;
            this.pad(n3, 10, sb);
            sb.append(":");
            this.pad(n4, 10, sb);
        }
        // monitorenter(this)
        if (lastTimestamp == this.lastTimestamp) {
            this.lastTimestamp = n;
            this.timestamppStr = sb.toString();
        }
        // monitorexit(this)
        return sb.toString();
    }
    
    private void pad(final int n, int i, final StringBuilder sb) {
        while (i > 1) {
            if (n < i) {
                sb.append("0");
            }
            i /= 10;
        }
        sb.append(Integer.toString(n));
    }
    
    private void formatStructuredElement(final String s, final String s2, final StructuredDataElement structuredDataElement, final StringBuilder sb, final ListChecker listChecker) {
        if ((s == null && this.defaultId == null) || structuredDataElement.discard()) {
            return;
        }
        sb.append("[");
        sb.append(s);
        if (!this.mdcSDID.toString().equals(s)) {
            this.appendMap(s2, structuredDataElement.getFields(), sb, this.noopChecker);
        }
        else {
            this.appendMap(s2, structuredDataElement.getFields(), sb, listChecker);
        }
        sb.append("]");
    }
    
    private String getId(final StructuredDataId structuredDataId) {
        final StringBuilder sb = new StringBuilder();
        if (structuredDataId == null || structuredDataId.getName() == null) {
            sb.append(this.defaultId);
        }
        else {
            sb.append(structuredDataId.getName());
        }
        int enterpriseNumber = (structuredDataId != null) ? structuredDataId.getEnterpriseNumber() : this.enterpriseNumber;
        if (enterpriseNumber < 0) {
            enterpriseNumber = this.enterpriseNumber;
        }
        if (enterpriseNumber >= 0) {
            sb.append("@").append(enterpriseNumber);
        }
        return sb.toString();
    }
    
    private void checkRequired(final Map map) {
        for (final String s : this.mdcRequired) {
            if (map.get(s) == null) {
                throw new LoggingException("Required key " + s + " is missing from the " + this.mdcId);
            }
        }
    }
    
    private void appendMap(final String s, final Map map, final StringBuilder sb, final ListChecker listChecker) {
        for (final Map.Entry<String, Object> entry : new TreeMap<String, Object>(map).entrySet()) {
            if (listChecker.check(entry.getKey()) && entry.getValue() != null) {
                sb.append(" ");
                if (s != null) {
                    sb.append(s);
                }
                sb.append(this.escapeNewlines(this.escapeSDParams(entry.getKey()), this.escapeNewLine)).append("=\"").append(this.escapeNewlines(this.escapeSDParams(entry.getValue()), this.escapeNewLine)).append("\"");
            }
        }
    }
    
    private String escapeSDParams(final String s) {
        return RFC5424Layout.PARAM_VALUE_ESCAPE_PATTERN.matcher(s).replaceAll("\\\\$0");
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("facility=").append(this.facility.name());
        sb.append(" appName=").append(this.appName);
        sb.append(" defaultId=").append(this.defaultId);
        sb.append(" enterpriseNumber=").append(this.enterpriseNumber);
        sb.append(" newLine=").append(this.includeNewLine);
        sb.append(" includeMDC=").append(this.includeMDC);
        sb.append(" messageId=").append(this.messageId);
        return sb.toString();
    }
    
    @PluginFactory
    public static RFC5424Layout createLayout(@PluginAttribute("facility") final String s, @PluginAttribute("id") final String s2, @PluginAttribute("enterpriseNumber") final String s3, @PluginAttribute("includeMDC") final String s4, @PluginAttribute("mdcId") String s5, @PluginAttribute("mdcPrefix") final String s6, @PluginAttribute("eventPrefix") final String s7, @PluginAttribute("newLine") final String s8, @PluginAttribute("newLineEscape") final String s9, @PluginAttribute("appName") final String s10, @PluginAttribute("messageId") final String s11, @PluginAttribute("mdcExcludes") final String s12, @PluginAttribute("mdcIncludes") String s13, @PluginAttribute("mdcRequired") final String s14, @PluginAttribute("exceptionPattern") final String s15, @PluginAttribute("useTLSMessageFormat") final String s16, @PluginElement("LoggerFields") final LoggerFields[] array, @PluginConfiguration final Configuration configuration) {
        final Charset utf_8 = Charsets.UTF_8;
        if (s13 != null && s12 != null) {
            RFC5424Layout.LOGGER.error("mdcIncludes and mdcExcludes are mutually exclusive. Includes wil be ignored");
            s13 = null;
        }
        final Facility facility = Facility.toFacility(s, Facility.LOCAL0);
        final int int1 = Integers.parseInt(s3, 18060);
        final boolean boolean1 = Booleans.parseBoolean(s4, true);
        final boolean boolean2 = Boolean.parseBoolean(s8);
        final boolean boolean3 = Booleans.parseBoolean(s16, false);
        if (s5 == null) {
            s5 = "mdc";
        }
        return new RFC5424Layout(configuration, facility, s2, int1, boolean1, boolean2, s9, s5, s6, s7, s10, s11, s12, s13, s14, utf_8, s15, boolean3, array);
    }
    
    @Override
    public Serializable toSerializable(final LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }
    
    static List access$300(final RFC5424Layout rfc5424Layout) {
        return rfc5424Layout.mdcIncludes;
    }
    
    static List access$400(final RFC5424Layout rfc5424Layout) {
        return rfc5424Layout.mdcExcludes;
    }
    
    static {
        NEWLINE_PATTERN = Pattern.compile("\\r?\\n");
        PARAM_VALUE_ESCAPE_PATTERN = Pattern.compile("[\\\"\\]\\\\]");
    }
    
    private class StructuredDataElement
    {
        private final Map fields;
        private final boolean discardIfEmpty;
        final RFC5424Layout this$0;
        
        public StructuredDataElement(final RFC5424Layout this$0, final Map fields, final boolean discardIfEmpty) {
            this.this$0 = this$0;
            this.discardIfEmpty = discardIfEmpty;
            this.fields = fields;
        }
        
        boolean discard() {
            if (!this.discardIfEmpty) {
                return false;
            }
            final Iterator<Map.Entry<K, CharSequence>> iterator = this.fields.entrySet().iterator();
            while (iterator.hasNext() && !Strings.isNotEmpty(iterator.next().getValue())) {}
            return !true;
        }
        
        void union(final Map map) {
            this.fields.putAll(map);
        }
        
        Map getFields() {
            return this.fields;
        }
    }
    
    private class FieldFormatter
    {
        private final Map delegateMap;
        private final boolean discardIfEmpty;
        final RFC5424Layout this$0;
        
        public FieldFormatter(final RFC5424Layout this$0, final Map delegateMap, final boolean discardIfEmpty) {
            this.this$0 = this$0;
            this.discardIfEmpty = discardIfEmpty;
            this.delegateMap = delegateMap;
        }
        
        public StructuredDataElement format(final LogEvent logEvent) {
            final HashMap<Object, String> hashMap = new HashMap<Object, String>();
            for (final Map.Entry<K, List> entry : this.delegateMap.entrySet()) {
                final StringBuilder sb = new StringBuilder();
                final Iterator<PatternFormatter> iterator2 = entry.getValue().iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().format(logEvent, sb);
                }
                hashMap.put(entry.getKey(), sb.toString());
            }
            return this.this$0.new StructuredDataElement(hashMap, this.discardIfEmpty);
        }
    }
    
    private class NoopChecker implements ListChecker
    {
        final RFC5424Layout this$0;
        
        private NoopChecker(final RFC5424Layout this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean check(final String s) {
            return true;
        }
        
        NoopChecker(final RFC5424Layout rfc5424Layout, final RFC5424Layout$1 object) {
            this(rfc5424Layout);
        }
    }
    
    private interface ListChecker
    {
        boolean check(final String p0);
    }
    
    private class ExcludeChecker implements ListChecker
    {
        final RFC5424Layout this$0;
        
        private ExcludeChecker(final RFC5424Layout this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean check(final String s) {
            return !RFC5424Layout.access$400(this.this$0).contains(s);
        }
        
        ExcludeChecker(final RFC5424Layout rfc5424Layout, final RFC5424Layout$1 object) {
            this(rfc5424Layout);
        }
    }
    
    private class IncludeChecker implements ListChecker
    {
        final RFC5424Layout this$0;
        
        private IncludeChecker(final RFC5424Layout this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean check(final String s) {
            return RFC5424Layout.access$300(this.this$0).contains(s);
        }
        
        IncludeChecker(final RFC5424Layout rfc5424Layout, final RFC5424Layout$1 object) {
            this(rfc5424Layout);
        }
    }
}
