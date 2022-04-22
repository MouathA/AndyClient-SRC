package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.lookup.*;
import org.apache.logging.log4j.util.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.util.*;
import java.io.*;
import java.net.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.status.*;

public abstract class ConfigurationFactory
{
    public static final String CONFIGURATION_FACTORY_PROPERTY = "log4j.configurationFactory";
    public static final String CONFIGURATION_FILE_PROPERTY = "log4j.configurationFile";
    protected static final Logger LOGGER;
    protected static final String TEST_PREFIX = "log4j2-test";
    protected static final String DEFAULT_PREFIX = "log4j2";
    private static final String CLASS_LOADER_SCHEME = "classloader";
    private static final String CLASS_PATH_SCHEME = "classpath";
    private static List factories;
    private static ConfigurationFactory configFactory;
    protected final StrSubstitutor substitutor;
    
    public ConfigurationFactory() {
        this.substitutor = new StrSubstitutor(new Interpolator());
    }
    
    public static ConfigurationFactory getInstance() {
        if (ConfigurationFactory.factories == null) {
            final String s = "log4j2-test";
            final String s2 = "log4j2-test";
            // monitorenter(s)
            if (ConfigurationFactory.factories == null) {
                final ArrayList<Object> list = new ArrayList<Object>();
                final String stringProperty = PropertiesUtil.getProperties().getStringProperty("log4j.configurationFactory");
                if (stringProperty != null) {
                    addFactory(list, stringProperty);
                }
                final PluginManager pluginManager = new PluginManager("ConfigurationFactory");
                pluginManager.collectPlugins();
                final Map plugins = pluginManager.getPlugins();
                final TreeSet<WeightedFactory> set = new TreeSet<WeightedFactory>();
                final Iterator<PluginType> iterator = plugins.values().iterator();
                while (iterator.hasNext()) {
                    final Class pluginClass = iterator.next().getPluginClass();
                    final Order order = pluginClass.getAnnotation(Order.class);
                    if (order != null) {
                        set.add(new WeightedFactory(order.value(), pluginClass));
                    }
                }
                final Iterator<Object> iterator2 = set.iterator();
                while (iterator2.hasNext()) {
                    addFactory(list, WeightedFactory.access$100(iterator2.next()));
                }
                ConfigurationFactory.factories = Collections.unmodifiableList((List<?>)list);
            }
        }
        // monitorexit(s2)
        return ConfigurationFactory.configFactory;
    }
    
    private static void addFactory(final List list, final String s) {
        addFactory(list, Class.forName(s));
    }
    
    private static void addFactory(final List list, final Class clazz) {
        list.add(clazz.newInstance());
    }
    
    public static void setConfigurationFactory(final ConfigurationFactory configFactory) {
        ConfigurationFactory.configFactory = configFactory;
    }
    
    public static void resetConfigurationFactory() {
        ConfigurationFactory.configFactory = new Factory(null);
    }
    
    public static void removeConfigurationFactory(final ConfigurationFactory configurationFactory) {
        if (ConfigurationFactory.configFactory == configurationFactory) {
            ConfigurationFactory.configFactory = new Factory(null);
        }
    }
    
    protected abstract String[] getSupportedTypes();
    
    protected boolean isActive() {
        return true;
    }
    
    public abstract Configuration getConfiguration(final ConfigurationSource p0);
    
    public Configuration getConfiguration(final String s, final URI uri) {
        if (!this.isActive()) {
            return null;
        }
        if (uri != null) {
            final ConfigurationSource inputFromURI = this.getInputFromURI(uri);
            if (inputFromURI != null) {
                return this.getConfiguration(inputFromURI);
            }
        }
        return null;
    }
    
    protected ConfigurationSource getInputFromURI(final URI uri) {
        final File fileFromURI = FileUtils.fileFromURI(uri);
        if (fileFromURI != null && fileFromURI.exists() && fileFromURI.canRead()) {
            return new ConfigurationSource(new FileInputStream(fileFromURI), fileFromURI);
        }
        final String scheme = uri.getScheme();
        final boolean b = scheme != null && scheme.equals("classloader");
        final boolean b2 = scheme != null && !b && scheme.equals("classpath");
        if (scheme == null || b || b2) {
            final ClassLoader classLoader = this.getClass().getClassLoader();
            String s;
            if (b) {
                s = uri.toString().substring(12);
            }
            else if (b2) {
                s = uri.toString().substring(10);
            }
            else {
                s = uri.getPath();
            }
            final ConfigurationSource inputFromResource = this.getInputFromResource(s, classLoader);
            if (inputFromResource != null) {
                return inputFromResource;
            }
        }
        return new ConfigurationSource(uri.toURL().openStream(), uri.getPath());
    }
    
    protected ConfigurationSource getInputFromString(final String s, final ClassLoader classLoader) {
        final URL url = new URL(s);
        return new ConfigurationSource(url.openStream(), FileUtils.fileFromURI(url.toURI()));
    }
    
    protected ConfigurationSource getInputFromResource(final String s, final ClassLoader classLoader) {
        final URL resource = Loader.getResource(s, classLoader);
        if (resource == null) {
            return null;
        }
        final InputStream openStream = resource.openStream();
        if (openStream == null) {
            return null;
        }
        if (FileUtils.isFile(resource)) {
            return new ConfigurationSource(openStream, FileUtils.fileFromURI(resource.toURI()));
        }
        return new ConfigurationSource(openStream, s);
    }
    
    static List access$200() {
        return ConfigurationFactory.factories;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
        ConfigurationFactory.factories = null;
        ConfigurationFactory.configFactory = new Factory(null);
    }
    
    public static class ConfigurationSource
    {
        private File file;
        private String location;
        private InputStream stream;
        
        public ConfigurationSource() {
        }
        
        public ConfigurationSource(final InputStream stream) {
            this.stream = stream;
            this.file = null;
            this.location = null;
        }
        
        public ConfigurationSource(final InputStream stream, final File file) {
            this.stream = stream;
            this.file = file;
            this.location = file.getAbsolutePath();
        }
        
        public ConfigurationSource(final InputStream stream, final String location) {
            this.stream = stream;
            this.location = location;
            this.file = null;
        }
        
        public File getFile() {
            return this.file;
        }
        
        public void setFile(final File file) {
            this.file = file;
        }
        
        public String getLocation() {
            return this.location;
        }
        
        public void setLocation(final String location) {
            this.location = location;
        }
        
        public InputStream getInputStream() {
            return this.stream;
        }
        
        public void setInputStream(final InputStream stream) {
            this.stream = stream;
        }
    }
    
    private static class Factory extends ConfigurationFactory
    {
        private Factory() {
        }
        
        @Override
        public Configuration getConfiguration(final String s, final URI uri) {
            if (uri == null) {
                final String replace = this.substitutor.replace(PropertiesUtil.getProperties().getStringProperty("log4j.configurationFile"));
                if (replace != null) {
                    ConfigurationSource configurationSource = this.getInputFromURI(new URI(replace));
                    if (configurationSource == null) {
                        configurationSource = this.getInputFromString(replace, this.getClass().getClassLoader());
                    }
                    if (configurationSource != null) {
                        for (final ConfigurationFactory configurationFactory : ConfigurationFactory.access$200()) {
                            final String[] supportedTypes = configurationFactory.getSupportedTypes();
                            if (supportedTypes != null) {
                                final String[] array = supportedTypes;
                                while (0 < array.length) {
                                    final String s2 = array[0];
                                    if (s2.equals("*") || replace.endsWith(s2)) {
                                        final Configuration configuration = configurationFactory.getConfiguration(configurationSource);
                                        if (configuration != null) {
                                            return configuration;
                                        }
                                    }
                                    int n = 0;
                                    ++n;
                                }
                            }
                        }
                    }
                }
            }
            else {
                for (final ConfigurationFactory configurationFactory2 : ConfigurationFactory.access$200()) {
                    final String[] supportedTypes2 = configurationFactory2.getSupportedTypes();
                    if (supportedTypes2 != null) {
                        final String[] array2 = supportedTypes2;
                        while (0 < array2.length) {
                            final String s3 = array2[0];
                            if (s3.equals("*") || uri.toString().endsWith(s3)) {
                                final Configuration configuration2 = configurationFactory2.getConfiguration(s, uri);
                                if (configuration2 != null) {
                                    return configuration2;
                                }
                            }
                            int n2 = 0;
                            ++n2;
                        }
                    }
                }
            }
            Configuration configuration3 = this.getConfiguration(true, s);
            if (configuration3 == null) {
                configuration3 = this.getConfiguration(true, null);
                if (configuration3 == null) {
                    configuration3 = this.getConfiguration(false, s);
                    if (configuration3 == null) {
                        configuration3 = this.getConfiguration(false, null);
                    }
                }
            }
            return (configuration3 != null) ? configuration3 : new DefaultConfiguration();
        }
        
        private Configuration getConfiguration(final boolean b, final String s) {
            final boolean b2 = s != null && s.length() > 0;
            final ClassLoader classLoader = this.getClass().getClassLoader();
            for (final ConfigurationFactory configurationFactory : ConfigurationFactory.access$200()) {
                final String s2 = b ? "log4j2-test" : "log4j2";
                final String[] supportedTypes = configurationFactory.getSupportedTypes();
                if (supportedTypes == null) {
                    continue;
                }
                final String[] array = supportedTypes;
                while (0 < array.length) {
                    final String s3 = array[0];
                    if (!s3.equals("*")) {
                        final ConfigurationSource inputFromResource = this.getInputFromResource(b2 ? (s2 + s + s3) : (s2 + s3), classLoader);
                        if (inputFromResource != null) {
                            return configurationFactory.getConfiguration(inputFromResource);
                        }
                    }
                    int n = 0;
                    ++n;
                }
            }
            return null;
        }
        
        public String[] getSupportedTypes() {
            return null;
        }
        
        @Override
        public Configuration getConfiguration(final ConfigurationSource configurationSource) {
            if (configurationSource != null) {
                final String location = configurationSource.getLocation();
                for (final ConfigurationFactory configurationFactory : ConfigurationFactory.access$200()) {
                    final String[] supportedTypes = configurationFactory.getSupportedTypes();
                    if (supportedTypes != null) {
                        final String[] array = supportedTypes;
                        while (0 < array.length) {
                            final String s = array[0];
                            if (s.equals("*") || (location != null && location.endsWith(s))) {
                                final Configuration configuration = configurationFactory.getConfiguration(configurationSource);
                                if (configuration != null) {
                                    return configuration;
                                }
                                Factory.LOGGER.error("Cannot determine the ConfigurationFactory to use for {}", location);
                                return null;
                            }
                            else {
                                int n = 0;
                                ++n;
                            }
                        }
                    }
                }
            }
            Factory.LOGGER.error("Cannot process configuration, input source is null");
            return null;
        }
        
        Factory(final ConfigurationFactory$1 object) {
            this();
        }
    }
    
    private static class WeightedFactory implements Comparable
    {
        private final int weight;
        private final Class factoryClass;
        
        public WeightedFactory(final int weight, final Class factoryClass) {
            this.weight = weight;
            this.factoryClass = factoryClass;
        }
        
        public int compareTo(final WeightedFactory weightedFactory) {
            final int weight = weightedFactory.weight;
            if (this.weight == weight) {
                return 0;
            }
            if (this.weight > weight) {
                return -1;
            }
            return 1;
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((WeightedFactory)o);
        }
        
        static Class access$100(final WeightedFactory weightedFactory) {
            return weightedFactory.factoryClass;
        }
    }
}
