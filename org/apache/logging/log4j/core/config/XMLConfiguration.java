package org.apache.logging.log4j.core.config;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.apache.logging.log4j.*;
import java.net.*;
import org.apache.logging.log4j.core.helpers.*;
import java.nio.charset.*;
import org.apache.logging.log4j.status.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.*;
import java.util.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import java.io.*;
import org.apache.logging.log4j.core.config.plugins.*;

public class XMLConfiguration extends BaseConfiguration implements Reconfigurable
{
    private static final String XINCLUDE_FIXUP_LANGUAGE = "http://apache.org/xml/features/xinclude/fixup-language";
    private static final String XINCLUDE_FIXUP_BASE_URIS = "http://apache.org/xml/features/xinclude/fixup-base-uris";
    private static final String LOG4J_XSD = "Log4j-config.xsd";
    private static final int BUF_SIZE = 16384;
    private final List status;
    private Element rootElement;
    private boolean strict;
    private String schema;
    private final File configFile;
    
    static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        final DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
        instance.setNamespaceAware(true);
        enableXInclude(instance);
        return instance.newDocumentBuilder();
    }
    
    private static void enableXInclude(final DocumentBuilderFactory documentBuilderFactory) {
        documentBuilderFactory.setXIncludeAware(true);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/xinclude/fixup-base-uris", true);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/xinclude/fixup-language", true);
    }
    
    public XMLConfiguration(final ConfigurationFactory.ConfigurationSource configurationSource) {
        this.status = new ArrayList();
        this.configFile = configurationSource.getFile();
        final ArrayList<String> list = new ArrayList<String>();
        final InputStream inputStream = configurationSource.getInputStream();
        final byte[] byteArray = this.toByteArray(inputStream);
        inputStream.close();
        this.rootElement = newDocumentBuilder().parse(new InputSource(new ByteArrayInputStream(byteArray))).getDocumentElement();
        final Map processAttributes = this.processAttributes(this.rootNode, this.rootElement);
        Level defaultStatus = this.getDefaultStatus();
        final PrintStream out = System.out;
        for (final Map.Entry<String, V> entry : processAttributes.entrySet()) {
            if ("status".equalsIgnoreCase(entry.getKey())) {
                final Level level = Level.toLevel(this.getStrSubstitutor().replace((String)entry.getValue()), null);
                if (level != null) {
                    defaultStatus = level;
                }
                else {
                    list.add("Invalid status specified: " + (String)entry.getValue() + ". Defaulting to " + defaultStatus);
                }
            }
            else if ("dest".equalsIgnoreCase(entry.getKey())) {
                final String replace = this.getStrSubstitutor().replace((String)entry.getValue());
                if (replace == null) {
                    continue;
                }
                if (replace.equalsIgnoreCase("err")) {
                    final PrintStream err = System.err;
                }
                else {
                    final PrintStream printStream = new PrintStream(new FileOutputStream(FileUtils.fileFromURI(new URI(replace))), true, Charset.defaultCharset().name());
                }
            }
            else if ("shutdownHook".equalsIgnoreCase(entry.getKey())) {
                this.isShutdownHookEnabled = !this.getStrSubstitutor().replace((String)entry.getValue()).equalsIgnoreCase("disable");
            }
            else if ("verbose".equalsIgnoreCase(entry.getKey())) {
                Boolean.parseBoolean(this.getStrSubstitutor().replace((String)entry.getValue()));
            }
            else if ("packages".equalsIgnoreCase(this.getStrSubstitutor().replace(entry.getKey()))) {
                final String[] split = ((String)entry.getValue()).split(",");
                while (0 < split.length) {
                    PluginManager.addPackage(split[0]);
                    int n = 0;
                    ++n;
                }
            }
            else if ("name".equalsIgnoreCase(entry.getKey())) {
                this.setName(this.getStrSubstitutor().replace((String)entry.getValue()));
            }
            else if ("strict".equalsIgnoreCase(entry.getKey())) {
                this.strict = Boolean.parseBoolean(this.getStrSubstitutor().replace((String)entry.getValue()));
            }
            else if ("schema".equalsIgnoreCase(entry.getKey())) {
                this.schema = this.getStrSubstitutor().replace((String)entry.getValue());
            }
            else if ("monitorInterval".equalsIgnoreCase(entry.getKey())) {
                final int int1 = Integer.parseInt(this.getStrSubstitutor().replace((String)entry.getValue()));
                if (int1 <= 0 || this.configFile == null) {
                    continue;
                }
                this.monitor = new FileConfigurationMonitor(this, this.configFile, this.listeners, int1);
            }
            else {
                if (!"advertiser".equalsIgnoreCase(entry.getKey())) {
                    continue;
                }
                this.createAdvertiser(this.getStrSubstitutor().replace((String)entry.getValue()), configurationSource, byteArray, "text/xml");
            }
        }
        final Iterator listeners = ((StatusLogger)XMLConfiguration.LOGGER).getListeners();
        while (listeners.hasNext()) {
            final StatusListener statusListener = listeners.next();
            if (statusListener instanceof StatusConsoleListener) {
                ((StatusConsoleListener)statusListener).setLevel(defaultStatus);
                ((StatusConsoleListener)statusListener).setFilters(XMLConfiguration.VERBOSE_CLASSES);
            }
        }
        if (this.strict && this.schema != null && byteArray != null) {
            final InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(this.schema);
            if (resourceAsStream != null) {
                final Schema schema = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema").newSchema(new StreamSource(resourceAsStream, "Log4j-config.xsd"));
                if (schema != null) {
                    schema.newValidator().validate(new StreamSource(new ByteArrayInputStream(byteArray)));
                }
            }
        }
        if (this.getName() == null) {
            this.setName(configurationSource.getLocation());
        }
    }
    
    public void setup() {
        if (this.rootElement == null) {
            XMLConfiguration.LOGGER.error("No logging configuration");
            return;
        }
        this.constructHierarchy(this.rootNode, this.rootElement);
        if (this.status.size() > 0) {
            for (final Status status : this.status) {
                XMLConfiguration.LOGGER.error("Error processing element " + Status.access$000(status) + ": " + Status.access$100(status));
            }
            return;
        }
        this.rootElement = null;
    }
    
    @Override
    public Configuration reconfigure() {
        if (this.configFile != null) {
            return new XMLConfiguration(new ConfigurationFactory.ConfigurationSource(new FileInputStream(this.configFile), this.configFile));
        }
        return null;
    }
    
    private void constructHierarchy(final Node node, final Element element) {
        this.processAttributes(node, element);
        final StringBuilder sb = new StringBuilder();
        final NodeList childNodes = element.getChildNodes();
        final List children = node.getChildren();
        while (0 < childNodes.getLength()) {
            final org.w3c.dom.Node item = childNodes.item(0);
            if (item instanceof Element) {
                final Element element2 = (Element)item;
                final String type = this.getType(element2);
                final PluginType pluginType = this.pluginManager.getPluginType(type);
                final Node node2 = new Node(node, type, pluginType);
                this.constructHierarchy(node2, element2);
                if (pluginType == null) {
                    final String value = node2.getValue();
                    if (!node2.hasChildren() && value != null) {
                        node.getAttributes().put(type, value);
                    }
                    else {
                        this.status.add(new Status(type, element, ErrorType.CLASS_NOT_FOUND));
                    }
                }
                else {
                    children.add(node2);
                }
            }
            else if (item instanceof Text) {
                sb.append(((Text)item).getData());
            }
            int n = 0;
            ++n;
        }
        final String trim = sb.toString().trim();
        if (trim.length() > 0 || (!node.hasChildren() && !node.isRoot())) {
            node.setValue(trim);
        }
    }
    
    private String getType(final Element element) {
        if (this.strict) {
            final NamedNodeMap attributes = element.getAttributes();
            while (0 < attributes.getLength()) {
                final org.w3c.dom.Node item = attributes.item(0);
                if (item instanceof Attr) {
                    final Attr attr = (Attr)item;
                    if (attr.getName().equalsIgnoreCase("type")) {
                        final String value = attr.getValue();
                        attributes.removeNamedItem(attr.getName());
                        return value;
                    }
                }
                int n = 0;
                ++n;
            }
        }
        return element.getTagName();
    }
    
    private byte[] toByteArray(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final byte[] array = new byte[16384];
        int read;
        while ((read = inputStream.read(array, 0, array.length)) != -1) {
            byteArrayOutputStream.write(array, 0, read);
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    private Map processAttributes(final Node node, final Element element) {
        final NamedNodeMap attributes = element.getAttributes();
        final Map attributes2 = node.getAttributes();
        while (0 < attributes.getLength()) {
            final org.w3c.dom.Node item = attributes.item(0);
            if (item instanceof Attr) {
                final Attr attr = (Attr)item;
                if (!attr.getName().equals("xml:base")) {
                    attributes2.put(attr.getName(), attr.getValue());
                }
            }
            int n = 0;
            ++n;
        }
        return attributes2;
    }
    
    static {
        XMLConfiguration.VERBOSE_CLASSES = new String[] { ResolverUtil.class.getName() };
    }
    
    private class Status
    {
        private final Element element;
        private final String name;
        private final ErrorType errorType;
        final XMLConfiguration this$0;
        
        public Status(final XMLConfiguration this$0, final String name, final Element element, final ErrorType errorType) {
            this.this$0 = this$0;
            this.name = name;
            this.element = element;
            this.errorType = errorType;
        }
        
        static String access$000(final Status status) {
            return status.name;
        }
        
        static ErrorType access$100(final Status status) {
            return status.errorType;
        }
    }
    
    private enum ErrorType
    {
        CLASS_NOT_FOUND("CLASS_NOT_FOUND", 0);
        
        private static final ErrorType[] $VALUES;
        
        private ErrorType(final String s, final int n) {
        }
        
        static {
            $VALUES = new ErrorType[] { ErrorType.CLASS_NOT_FOUND };
        }
    }
}
