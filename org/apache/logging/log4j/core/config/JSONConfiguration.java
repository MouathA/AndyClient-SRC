package org.apache.logging.log4j.core.config;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;
import org.apache.logging.log4j.*;
import java.net.*;
import org.apache.logging.log4j.core.helpers.*;
import java.nio.charset.*;
import org.apache.logging.log4j.status.*;
import java.util.*;
import java.io.*;
import org.apache.logging.log4j.core.config.plugins.*;

public class JSONConfiguration extends BaseConfiguration implements Reconfigurable
{
    private static final int BUF_SIZE = 16384;
    private final List status;
    private JsonNode root;
    private final List messages;
    private final File configFile;
    
    public JSONConfiguration(final ConfigurationFactory.ConfigurationSource configurationSource) {
        this.status = new ArrayList();
        this.messages = new ArrayList();
        this.configFile = configurationSource.getFile();
        final InputStream inputStream = configurationSource.getInputStream();
        final byte[] byteArray = this.toByteArray(inputStream);
        inputStream.close();
        this.root = new ObjectMapper().configure(JsonParser.Feature.ALLOW_COMMENTS, true).readTree((InputStream)new ByteArrayInputStream(byteArray));
        if (this.root.size() == 1) {
            this.root = this.root.elements().next();
        }
        this.processAttributes(this.rootNode, this.root);
        Level level = this.getDefaultStatus();
        PrintStream printStream = System.out;
        for (final Map.Entry<String, V> entry : this.rootNode.getAttributes().entrySet()) {
            if ("status".equalsIgnoreCase(entry.getKey())) {
                level = Level.toLevel(this.getStrSubstitutor().replace((String)entry.getValue()), null);
                if (level != null) {
                    continue;
                }
                level = Level.ERROR;
                this.messages.add("Invalid status specified: " + (String)entry.getValue() + ". Defaulting to ERROR");
            }
            else if ("dest".equalsIgnoreCase(entry.getKey())) {
                final String s = (String)entry.getValue();
                if (s == null) {
                    continue;
                }
                if (s.equalsIgnoreCase("err")) {
                    printStream = System.err;
                }
                else {
                    printStream = new PrintStream(new FileOutputStream(FileUtils.fileFromURI(new URI(s))), true, Charset.defaultCharset().name());
                }
            }
            else if ("shutdownHook".equalsIgnoreCase(entry.getKey())) {
                this.isShutdownHookEnabled = !this.getStrSubstitutor().replace((String)entry.getValue()).equalsIgnoreCase("disable");
            }
            else if ("verbose".equalsIgnoreCase(entry.getKey())) {
                Boolean.parseBoolean(this.getStrSubstitutor().replace((String)entry.getValue()));
            }
            else if ("packages".equalsIgnoreCase(entry.getKey())) {
                final String[] split = this.getStrSubstitutor().replace((String)entry.getValue()).split(",");
                while (0 < split.length) {
                    PluginManager.addPackage(split[0]);
                    int n = 0;
                    ++n;
                }
            }
            else if ("name".equalsIgnoreCase(entry.getKey())) {
                this.setName(this.getStrSubstitutor().replace((String)entry.getValue()));
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
                this.createAdvertiser(this.getStrSubstitutor().replace((String)entry.getValue()), configurationSource, byteArray, "application/json");
            }
        }
        final Iterator listeners = ((StatusLogger)JSONConfiguration.LOGGER).getListeners();
        while (listeners.hasNext()) {
            final StatusListener statusListener = listeners.next();
            if (statusListener instanceof StatusConsoleListener) {
                ((StatusConsoleListener)statusListener).setLevel(level);
                if (false) {
                    continue;
                }
                ((StatusConsoleListener)statusListener).setFilters(JSONConfiguration.VERBOSE_CLASSES);
            }
        }
        if (!true && level != Level.OFF) {
            final StatusConsoleListener statusConsoleListener = new StatusConsoleListener(level, printStream);
            if (!false) {
                statusConsoleListener.setFilters(JSONConfiguration.VERBOSE_CLASSES);
            }
            ((StatusLogger)JSONConfiguration.LOGGER).registerListener(statusConsoleListener);
            final Iterator<String> iterator2 = this.messages.iterator();
            while (iterator2.hasNext()) {
                JSONConfiguration.LOGGER.error(iterator2.next());
            }
        }
        if (this.getName() == null) {
            this.setName(configurationSource.getLocation());
        }
    }
    
    @Override
    public void stop() {
        super.stop();
    }
    
    public void setup() {
        final Iterator fields = this.root.fields();
        final List children = this.rootNode.getChildren();
        while (fields.hasNext()) {
            final Map.Entry<K, JsonNode> entry = fields.next();
            final JsonNode jsonNode = entry.getValue();
            if (jsonNode.isObject()) {
                JSONConfiguration.LOGGER.debug("Processing node for object " + (String)entry.getKey());
                children.add(this.constructNode((String)entry.getKey(), this.rootNode, jsonNode));
            }
            else {
                if (!jsonNode.isArray()) {
                    continue;
                }
                JSONConfiguration.LOGGER.error("Arrays are not supported at the root configuration.");
            }
        }
        JSONConfiguration.LOGGER.debug("Completed parsing configuration");
        if (this.status.size() > 0) {
            for (final Status status : this.status) {
                JSONConfiguration.LOGGER.error("Error processing element " + Status.access$000(status) + ": " + Status.access$100(status));
            }
        }
    }
    
    @Override
    public Configuration reconfigure() {
        if (this.configFile != null) {
            return new JSONConfiguration(new ConfigurationFactory.ConfigurationSource(new FileInputStream(this.configFile), this.configFile));
        }
        return null;
    }
    
    private Node constructNode(final String s, final Node node, final JsonNode jsonNode) {
        final PluginType pluginType = this.pluginManager.getPluginType(s);
        final Node node2 = new Node(node, s, pluginType);
        this.processAttributes(node2, jsonNode);
        final Iterator fields = jsonNode.fields();
        final List children = node2.getChildren();
        while (fields.hasNext()) {
            final Map.Entry<K, JsonNode> entry = fields.next();
            final JsonNode jsonNode2 = entry.getValue();
            if (jsonNode2.isArray() || jsonNode2.isObject()) {
                if (pluginType == null) {
                    this.status.add(new Status(s, jsonNode2, ErrorType.CLASS_NOT_FOUND));
                }
                if (jsonNode2.isArray()) {
                    JSONConfiguration.LOGGER.debug("Processing node for array " + (String)entry.getKey());
                    while (0 < jsonNode2.size()) {
                        final String type = this.getType(jsonNode2.get(0), (String)entry.getKey());
                        final Node node3 = new Node(node2, (String)entry.getKey(), this.pluginManager.getPluginType(type));
                        this.processAttributes(node3, jsonNode2.get(0));
                        if (type.equals(entry.getKey())) {
                            JSONConfiguration.LOGGER.debug("Processing " + (String)entry.getKey() + "[" + 0 + "]");
                        }
                        else {
                            JSONConfiguration.LOGGER.debug("Processing " + type + " " + (String)entry.getKey() + "[" + 0 + "]");
                        }
                        final Iterator fields2 = jsonNode2.get(0).fields();
                        final List children2 = node3.getChildren();
                        while (fields2.hasNext()) {
                            final Map.Entry<String, V> entry2 = fields2.next();
                            if (((JsonNode)entry2.getValue()).isObject()) {
                                JSONConfiguration.LOGGER.debug("Processing node for object " + entry2.getKey());
                                children2.add(this.constructNode(entry2.getKey(), node3, (JsonNode)entry2.getValue()));
                            }
                        }
                        children.add(node3);
                        int n = 0;
                        ++n;
                    }
                }
                else {
                    JSONConfiguration.LOGGER.debug("Processing node for object " + (String)entry.getKey());
                    children.add(this.constructNode((String)entry.getKey(), node2, jsonNode2));
                }
            }
        }
        String string;
        if (pluginType == null) {
            string = "null";
        }
        else {
            string = pluginType.getElementName() + ":" + pluginType.getPluginClass();
        }
        JSONConfiguration.LOGGER.debug("Returning " + node2.getName() + " with parent " + ((node2.getParent() == null) ? "null" : ((node2.getParent().getName() == null) ? "root" : node2.getParent().getName())) + " of type " + string);
        return node2;
    }
    
    private String getType(final JsonNode jsonNode, final String s) {
        final Iterator fields = jsonNode.fields();
        while (fields.hasNext()) {
            final Map.Entry<String, V> entry = fields.next();
            if (entry.getKey().equalsIgnoreCase("type")) {
                final JsonNode jsonNode2 = (JsonNode)entry.getValue();
                if (jsonNode2.isValueNode()) {
                    return jsonNode2.asText();
                }
                continue;
            }
        }
        return s;
    }
    
    private void processAttributes(final Node node, final JsonNode jsonNode) {
        final Map attributes = node.getAttributes();
        final Iterator fields = jsonNode.fields();
        while (fields.hasNext()) {
            final Map.Entry<String, V> entry = fields.next();
            if (!entry.getKey().equalsIgnoreCase("type")) {
                final JsonNode jsonNode2 = (JsonNode)entry.getValue();
                if (!jsonNode2.isValueNode()) {
                    continue;
                }
                attributes.put(entry.getKey(), jsonNode2.asText());
            }
        }
    }
    
    protected byte[] toByteArray(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final byte[] array = new byte[16384];
        int read;
        while ((read = inputStream.read(array, 0, array.length)) != -1) {
            byteArrayOutputStream.write(array, 0, read);
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    static {
        JSONConfiguration.VERBOSE_CLASSES = new String[] { ResolverUtil.class.getName() };
    }
    
    private class Status
    {
        private final JsonNode node;
        private final String name;
        private final ErrorType errorType;
        final JSONConfiguration this$0;
        
        public Status(final JSONConfiguration this$0, final String name, final JsonNode node, final ErrorType errorType) {
            this.this$0 = this$0;
            this.name = name;
            this.node = node;
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
