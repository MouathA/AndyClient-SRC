package org.apache.logging.log4j.core.config.plugins;

import org.apache.logging.log4j.*;
import java.util.concurrent.*;
import java.text.*;
import java.net.*;
import org.apache.logging.log4j.core.helpers.*;
import java.util.*;
import java.io.*;
import org.apache.logging.log4j.status.*;

public class PluginManager
{
    private static final long NANOS_PER_SECOND = 1000000000L;
    private static ConcurrentMap pluginTypeMap;
    private static final CopyOnWriteArrayList PACKAGES;
    private static final String PATH = "org/apache/logging/log4j/core/config/plugins/";
    private static final String FILENAME = "Log4j2Plugins.dat";
    private static final String LOG4J_PACKAGES = "org.apache.logging.log4j.core";
    private static final Logger LOGGER;
    private static String rootDir;
    private Map plugins;
    private final String type;
    private final Class clazz;
    
    public PluginManager(final String type) {
        this.plugins = new HashMap();
        this.type = type;
        this.clazz = null;
    }
    
    public PluginManager(final String type, final Class clazz) {
        this.plugins = new HashMap();
        this.type = type;
        this.clazz = clazz;
    }
    
    public static void main(final String[] array) throws Exception {
        if (array == null || array.length < 1) {
            System.err.println("A target directory must be specified");
            System.exit(-1);
        }
        PluginManager.rootDir = ((array[0].endsWith("/") || array[0].endsWith("\\")) ? array[0] : (array[0] + "/"));
        new PluginManager("Core").collectPlugins(false, (array.length == 2) ? array[1] : null);
        encode(PluginManager.pluginTypeMap);
    }
    
    public static void addPackage(final String s) {
        if (PluginManager.PACKAGES.addIfAbsent(s)) {
            PluginManager.pluginTypeMap.clear();
        }
    }
    
    public PluginType getPluginType(final String s) {
        return this.plugins.get(s.toLowerCase());
    }
    
    public Map getPlugins() {
        return this.plugins;
    }
    
    public void collectPlugins() {
        this.collectPlugins(true, null);
    }
    
    public void collectPlugins(final boolean b, final String s) {
        if (PluginManager.pluginTypeMap.containsKey(this.type)) {
            this.plugins = (Map)PluginManager.pluginTypeMap.get(this.type);
        }
        final long nanoTime = System.nanoTime();
        final ResolverUtil resolverUtil = new ResolverUtil();
        final ClassLoader classLoader = Loader.getClassLoader();
        if (classLoader != null) {
            resolverUtil.setClassLoader(classLoader);
        }
        if (false) {
            final ConcurrentMap decode = decode(classLoader);
            if (decode != null) {
                PluginManager.pluginTypeMap = decode;
                this.plugins = (Map)decode.get(this.type);
            }
            else {
                PluginManager.LOGGER.warn("Plugin preloads not available from class loader {}", classLoader);
            }
        }
        if (this.plugins == null || this.plugins.size() == 0) {
            if (s == null) {
                if (!PluginManager.PACKAGES.contains("org.apache.logging.log4j.core")) {
                    PluginManager.PACKAGES.add("org.apache.logging.log4j.core");
                }
            }
            else {
                final String[] split = s.split(",");
                while (0 < split.length) {
                    PluginManager.PACKAGES.add(split[0]);
                    int n = 0;
                    ++n;
                }
            }
        }
        final PluginTest pluginTest = new PluginTest(this.clazz);
        final Iterator<String> iterator = (Iterator<String>)PluginManager.PACKAGES.iterator();
        while (iterator.hasNext()) {
            resolverUtil.findInPackage(pluginTest, iterator.next());
        }
        for (final Class clazz : resolverUtil.getClasses()) {
            final Plugin plugin = (Plugin)clazz.getAnnotation(Plugin.class);
            final String category = plugin.category();
            if (!PluginManager.pluginTypeMap.containsKey(category)) {
                PluginManager.pluginTypeMap.putIfAbsent(category, new ConcurrentHashMap());
            }
            final Map map = (Map)PluginManager.pluginTypeMap.get(category);
            map.put(plugin.name().toLowerCase(), new PluginType(clazz, plugin.elementType().equals("") ? plugin.name() : plugin.elementType(), plugin.printObject(), plugin.deferChildren()));
            final PluginAliases pluginAliases = (PluginAliases)clazz.getAnnotation(PluginAliases.class);
            if (pluginAliases != null) {
                final String[] value = pluginAliases.value();
                while (0 < value.length) {
                    final String s2 = value[0];
                    map.put(s2.trim().toLowerCase(), new PluginType(clazz, plugin.elementType().equals("") ? s2 : plugin.elementType(), plugin.printObject(), plugin.deferChildren()));
                    int n2 = 0;
                    ++n2;
                }
            }
        }
        final long n3 = System.nanoTime() - nanoTime;
        this.plugins = (Map)PluginManager.pluginTypeMap.get(this.type);
        final StringBuilder sb = new StringBuilder("Generated plugins");
        sb.append(" in ");
        final DecimalFormat decimalFormat = new DecimalFormat("#0");
        final long n4 = n3 / 1000000000L;
        final long n5 = n3 % 1000000000L;
        sb.append(decimalFormat.format(n4)).append('.');
        sb.append(new DecimalFormat("000000000").format(n5)).append(" seconds");
        PluginManager.LOGGER.debug(sb.toString());
    }
    
    private static ConcurrentMap decode(final ClassLoader classLoader) {
        final Enumeration<URL> resources = classLoader.getResources("org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat");
        final ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<Object, Object>();
        while (resources.hasMoreElements()) {
            final URL url = resources.nextElement();
            PluginManager.LOGGER.debug("Found Plugin Map at {}", url.toExternalForm());
            final DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(url.openStream()));
            final int int1 = dataInputStream.readInt();
            while (0 < int1) {
                final String utf = dataInputStream.readUTF();
                final int int2 = dataInputStream.readInt();
                ConcurrentMap<?, ?> concurrentMap = concurrentHashMap.get(utf);
                if (concurrentMap == null) {
                    concurrentMap = new ConcurrentHashMap<Object, Object>(int1);
                }
                while (0 < int2) {
                    concurrentMap.put(dataInputStream.readUTF(), new PluginType(Class.forName(dataInputStream.readUTF()), dataInputStream.readUTF(), dataInputStream.readBoolean(), dataInputStream.readBoolean()));
                    int n = 0;
                    ++n;
                }
                concurrentHashMap.putIfAbsent(utf, concurrentMap);
                int n2 = 0;
                ++n2;
            }
            Closer.closeSilent(dataInputStream);
        }
        return (concurrentHashMap.size() == 0) ? null : concurrentHashMap;
    }
    
    private static void encode(final ConcurrentMap concurrentMap) {
        final String string = PluginManager.rootDir + "org/apache/logging/log4j/core/config/plugins/" + "Log4j2Plugins.dat";
        new File(PluginManager.rootDir + "org/apache/logging/log4j/core/config/plugins/").mkdirs();
        final DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(string)));
        dataOutputStream.writeInt(concurrentMap.size());
        for (final Map.Entry<String, V> entry : concurrentMap.entrySet()) {
            dataOutputStream.writeUTF(entry.getKey());
            dataOutputStream.writeInt(((ConcurrentMap)entry.getValue()).size());
            for (final Map.Entry<String, V> entry2 : ((ConcurrentMap)entry.getValue()).entrySet()) {
                dataOutputStream.writeUTF(entry2.getKey());
                final PluginType pluginType = (PluginType)entry2.getValue();
                dataOutputStream.writeUTF(pluginType.getPluginClass().getName());
                dataOutputStream.writeUTF(pluginType.getElementName());
                dataOutputStream.writeBoolean(pluginType.isObjectPrintable());
                dataOutputStream.writeBoolean(pluginType.isDeferChildren());
            }
        }
        Closer.closeSilent(dataOutputStream);
    }
    
    static {
        PluginManager.pluginTypeMap = new ConcurrentHashMap();
        PACKAGES = new CopyOnWriteArrayList();
        LOGGER = StatusLogger.getLogger();
    }
    
    public static class PluginTest extends ResolverUtil.ClassTest
    {
        private final Class isA;
        
        public PluginTest(final Class isA) {
            this.isA = isA;
        }
        
        @Override
        public boolean matches(final Class clazz) {
            return clazz != null && clazz.isAnnotationPresent(Plugin.class) && (this.isA == null || this.isA.isAssignableFrom(clazz));
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("annotated with @" + Plugin.class.getSimpleName());
            if (this.isA != null) {
                sb.append(" is assignable to " + this.isA.getSimpleName());
            }
            return sb.toString();
        }
    }
}
