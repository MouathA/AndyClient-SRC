package net.java.games.util.plugins;

import java.io.*;
import java.net.*;

public class PluginLoader extends URLClassLoader
{
    static final boolean DEBUG;
    File parentDir;
    boolean localDLLs;
    static Class class$net$java$games$util$plugins$Plugin;
    
    public PluginLoader(final File file) throws MalformedURLException {
        super(new URL[] { file.toURL() }, Thread.currentThread().getContextClassLoader());
        this.localDLLs = true;
        this.parentDir = file.getParentFile();
        if (System.getProperty("net.java.games.util.plugins.nolocalnative") != null) {
            this.localDLLs = false;
        }
    }
    
    protected String findLibrary(final String s) {
        if (this.localDLLs) {
            return this.parentDir.getPath() + File.separator + System.mapLibraryName(s);
        }
        return super.findLibrary(s);
    }
    
    public boolean attemptPluginDefine(final Class clazz) {
        return !clazz.isInterface() && this.classImplementsPlugin(clazz);
    }
    
    private boolean classImplementsPlugin(final Class clazz) {
        if (clazz == null) {
            return false;
        }
        final Class[] interfaces = clazz.getInterfaces();
        int n = 0;
        while (0 < interfaces.length) {
            if (interfaces[0] == ((PluginLoader.class$net$java$games$util$plugins$Plugin == null) ? (PluginLoader.class$net$java$games$util$plugins$Plugin = class$("net.java.games.util.plugins.Plugin")) : PluginLoader.class$net$java$games$util$plugins$Plugin)) {
                return true;
            }
            ++n;
        }
        while (0 < interfaces.length) {
            if (this.classImplementsPlugin(interfaces[0])) {
                return true;
            }
            ++n;
        }
        return this.classImplementsPlugin(clazz.getSuperclass());
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static {
        DEBUG = false;
    }
}
