package com.viaversion.viaversion.util;

import com.viaversion.viaversion.api.configuration.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.representer.*;
import org.yaml.snakeyaml.constructor.*;
import java.util.function.*;

public abstract class Config implements ConfigurationProvider
{
    private static final ThreadLocal YAML;
    private final CommentStore commentStore;
    private final File configFile;
    private Map config;
    
    public Config(final File configFile) {
        this.commentStore = new CommentStore('.', 2);
        this.configFile = configFile;
    }
    
    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viaversion/config.yml");
    }
    
    public synchronized Map loadConfig(final File file) {
        final List unsupportedOptions = this.getUnsupportedOptions();
        final URL defaultConfigURL = this.getDefaultConfigURL();
        this.commentStore.storeComments(defaultConfigURL.openStream());
        final Iterator<String> iterator = unsupportedOptions.iterator();
        while (iterator.hasNext()) {
            final List header = this.commentStore.header(iterator.next());
            if (header != null) {
                header.clear();
            }
        }
        Object o = null;
        if (file.exists()) {
            final FileInputStream fileInputStream = new FileInputStream(file);
            final Object o2 = null;
            o = Config.YAML.get().load(fileInputStream);
            if (fileInputStream != null) {
                if (o2 != null) {
                    fileInputStream.close();
                }
                else {
                    fileInputStream.close();
                }
            }
        }
        if (o == null) {
            o = new HashMap<Object, Object>();
        }
        final InputStream openStream = defaultConfigURL.openStream();
        final Object o3 = null;
        final Map map = (Map)Config.YAML.get().load(openStream);
        final Iterator<String> iterator2 = unsupportedOptions.iterator();
        while (iterator2.hasNext()) {
            map.remove(iterator2.next());
        }
        for (final Map.Entry<Object, V> entry : ((Map<Object, V>)o).entrySet()) {
            if (map.containsKey(entry.getKey()) && !unsupportedOptions.contains(entry.getKey())) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        if (openStream != null) {
            if (o3 != null) {
                openStream.close();
            }
            else {
                openStream.close();
            }
        }
        this.handleConfig(map);
        this.saveConfig(file, map);
        return map;
    }
    
    protected abstract void handleConfig(final Map p0);
    
    public synchronized void saveConfig(final File file, final Map map) {
        this.commentStore.writeComments(Config.YAML.get().dump(map), file);
    }
    
    public abstract List getUnsupportedOptions();
    
    @Override
    public void set(final String s, final Object o) {
        this.config.put(s, o);
    }
    
    @Override
    public void saveConfig() {
        this.configFile.getParentFile().mkdirs();
        this.saveConfig(this.configFile, this.config);
    }
    
    @Override
    public void reloadConfig() {
        this.configFile.getParentFile().mkdirs();
        this.config = new ConcurrentSkipListMap(this.loadConfig(this.configFile));
    }
    
    @Override
    public Map getValues() {
        return this.config;
    }
    
    public Object get(final String s, final Class clazz, final Object o) {
        final Object value = this.config.get(s);
        if (value != null) {
            return value;
        }
        return o;
    }
    
    public boolean getBoolean(final String s, final boolean b) {
        final Boolean value = this.config.get(s);
        if (value != null) {
            return value;
        }
        return b;
    }
    
    public String getString(final String s, final String s2) {
        final String value = this.config.get(s);
        if (value != null) {
            return value;
        }
        return s2;
    }
    
    public int getInt(final String s, final int n) {
        final Number value = this.config.get(s);
        if (value == null) {
            return n;
        }
        if (value instanceof Number) {
            return value.intValue();
        }
        return n;
    }
    
    public double getDouble(final String s, final double n) {
        final Number value = this.config.get(s);
        if (value == null) {
            return n;
        }
        if (value instanceof Number) {
            return value.doubleValue();
        }
        return n;
    }
    
    public List getIntegerList(final String s) {
        final List value = this.config.get(s);
        return (value != null) ? ((List)value) : new ArrayList();
    }
    
    public List getStringList(final String s) {
        final List value = this.config.get(s);
        return (value != null) ? ((List)value) : new ArrayList();
    }
    
    public JsonElement getSerializedComponent(final String s) {
        final String value = this.config.get(s);
        if (value != null && !value.isEmpty()) {
            return GsonComponentSerializer.gson().serializeToTree(LegacyComponentSerializer.legacySection().deserialize(value));
        }
        return null;
    }
    
    private static Yaml lambda$static$0() {
        final DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setPrettyFlow(false);
        dumperOptions.setIndent(2);
        return new Yaml(new YamlConstructor(), new Representer(), dumperOptions);
    }
    
    static {
        YAML = ThreadLocal.withInitial((Supplier<?>)Config::lambda$static$0);
    }
}
