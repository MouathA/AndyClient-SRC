package net.java.games.util.plugins;

import java.io.*;
import java.util.jar.*;
import java.util.*;

public class Plugins
{
    static final boolean DEBUG;
    List pluginList;
    
    public Plugins(final File file) throws IOException {
        this.pluginList = new ArrayList();
        this.scanPlugins(file);
    }
    
    private void scanPlugins(final File file) throws IOException {
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            throw new FileNotFoundException("Plugin directory " + file.getName() + " not found.");
        }
        while (0 < listFiles.length) {
            final File file2 = listFiles[0];
            if (file2.getName().endsWith(".jar")) {
                this.processJar(file2);
            }
            else if (file2.isDirectory()) {
                this.scanPlugins(file2);
            }
            int n = 0;
            ++n;
        }
    }
    
    private void processJar(final File file) {
        System.out.println("Scanning jar: " + file.getName());
        final PluginLoader pluginLoader = new PluginLoader(file);
        final Enumeration<JarEntry> entries = new JarFile(file).entries();
        while (entries.hasMoreElements()) {
            final JarEntry jarEntry = entries.nextElement();
            System.out.println("Examining file : " + jarEntry.getName());
            if (jarEntry.getName().endsWith("Plugin.class")) {
                System.out.println("Found candidate class: " + jarEntry.getName());
                final String name = jarEntry.getName();
                final Class<?> loadClass = pluginLoader.loadClass(name.substring(0, name.length() - 6).replace('/', '.'));
                if (!pluginLoader.attemptPluginDefine(loadClass)) {
                    continue;
                }
                System.out.println("Adding class to plugins:" + loadClass.getName());
                this.pluginList.add(loadClass);
            }
        }
    }
    
    public Class[] get() {
        return this.pluginList.toArray(new Class[this.pluginList.size()]);
    }
    
    public Class[] getImplementsAny(final Class[] array) {
        final ArrayList list = new ArrayList<Class>(this.pluginList.size());
        final HashSet<Class> set = new HashSet<Class>();
        while (0 < array.length) {
            set.add(array[0]);
            int n = 0;
            ++n;
        }
        for (final Class clazz : this.pluginList) {
            if (this.classImplementsAny(clazz, set)) {
                list.add(clazz);
            }
        }
        return (Class[])list.toArray(new Class[list.size()]);
    }
    
    private boolean classImplementsAny(final Class clazz, final Set set) {
        if (clazz == null) {
            return false;
        }
        final Class[] interfaces = clazz.getInterfaces();
        int n = 0;
        while (0 < interfaces.length) {
            if (set.contains(interfaces[0])) {
                return true;
            }
            ++n;
        }
        while (0 < interfaces.length) {
            if (this.classImplementsAny(interfaces[0], set)) {
                return true;
            }
            ++n;
        }
        return this.classImplementsAny(clazz.getSuperclass(), set);
    }
    
    public Class[] getImplementsAll(final Class[] array) {
        final ArrayList list = new ArrayList<Class>(this.pluginList.size());
        final HashSet<Class> set = new HashSet<Class>();
        while (0 < array.length) {
            set.add(array[0]);
            int n = 0;
            ++n;
        }
        for (final Class clazz : this.pluginList) {
            if (this.classImplementsAll(clazz, set)) {
                list.add(clazz);
            }
        }
        return (Class[])list.toArray(new Class[list.size()]);
    }
    
    private boolean classImplementsAll(final Class clazz, final Set set) {
        if (clazz == null) {
            return false;
        }
        final Class[] interfaces = clazz.getInterfaces();
        int n = 0;
        while (0 < interfaces.length) {
            if (set.contains(interfaces[0])) {
                set.remove(interfaces[0]);
                if (set.size() == 0) {
                    return true;
                }
            }
            ++n;
        }
        while (0 < interfaces.length) {
            if (this.classImplementsAll(interfaces[0], set)) {
                return true;
            }
            ++n;
        }
        return this.classImplementsAll(clazz.getSuperclass(), set);
    }
    
    public Class[] getExtends(final Class clazz) {
        final ArrayList list = new ArrayList<Class>(this.pluginList.size());
        for (final Class clazz2 : this.pluginList) {
            if (this.classExtends(clazz2, clazz)) {
                list.add(clazz2);
            }
        }
        return (Class[])list.toArray(new Class[list.size()]);
    }
    
    private boolean classExtends(final Class clazz, final Class clazz2) {
        return clazz != null && (clazz == clazz2 || this.classExtends(clazz.getSuperclass(), clazz2));
    }
    
    static {
        DEBUG = true;
    }
}
