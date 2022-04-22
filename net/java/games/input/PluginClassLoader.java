package net.java.games.input;

import java.util.*;
import java.util.zip.*;
import java.util.jar.*;
import java.io.*;

class PluginClassLoader extends ClassLoader
{
    private static String pluginDirectory;
    private static final FileFilter JAR_FILTER;
    static final boolean $assertionsDisabled;
    static Class class$net$java$games$input$PluginClassLoader;
    
    public PluginClassLoader() {
        super(Thread.currentThread().getContextClassLoader());
    }
    
    protected Class findClass(final String s) throws ClassNotFoundException {
        final byte[] loadClassData = this.loadClassData(s);
        return this.defineClass(s, loadClassData, 0, loadClassData.length);
    }
    
    private byte[] loadClassData(final String s) throws ClassNotFoundException {
        if (PluginClassLoader.pluginDirectory == null) {
            PluginClassLoader.pluginDirectory = DefaultControllerEnvironment.libPath + File.separator + "controller";
        }
        return this.loadClassFromDirectory(s);
    }
    
    private byte[] loadClassFromDirectory(final String s) throws ClassNotFoundException, IOException {
        final StringTokenizer stringTokenizer = new StringTokenizer(s, ".");
        final StringBuffer sb = new StringBuffer(PluginClassLoader.pluginDirectory);
        while (stringTokenizer.hasMoreTokens()) {
            sb.append(File.separator);
            sb.append(stringTokenizer.nextToken());
        }
        sb.append(".class");
        final File file = new File(sb.toString());
        if (!file.exists()) {
            throw new ClassNotFoundException(s);
        }
        final FileInputStream fileInputStream = new FileInputStream(file);
        assert file.length() <= 2147483647L;
        final int n = (int)file.length();
        final byte[] array = new byte[n];
        final int read = fileInputStream.read(array);
        assert n == read;
        return array;
    }
    
    private byte[] loadClassFromJAR(final String s) throws ClassNotFoundException, IOException {
        final File[] listFiles = new File(PluginClassLoader.pluginDirectory).listFiles(PluginClassLoader.JAR_FILTER);
        if (listFiles == null) {
            throw new ClassNotFoundException("Could not find class " + s);
        }
        while (0 < listFiles.length) {
            final JarFile jarFile = new JarFile(listFiles[0]);
            final JarEntry jarEntry = jarFile.getJarEntry(s + ".class");
            if (jarEntry != null) {
                final InputStream inputStream = jarFile.getInputStream(jarEntry);
                assert jarEntry.getSize() <= 2147483647L;
                final int n = (int)jarEntry.getSize();
                assert n >= 0;
                final byte[] array = new byte[n];
                final int read = inputStream.read(array);
                assert n == read;
                return array;
            }
            else {
                int n2 = 0;
                ++n2;
            }
        }
        throw new FileNotFoundException(s);
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static {
        $assertionsDisabled = !((PluginClassLoader.class$net$java$games$input$PluginClassLoader == null) ? (PluginClassLoader.class$net$java$games$input$PluginClassLoader = class$("net.java.games.input.PluginClassLoader")) : PluginClassLoader.class$net$java$games$input$PluginClassLoader).desiredAssertionStatus();
        JAR_FILTER = new JarFileFilter(null);
    }
    
    private static class JarFileFilter implements FileFilter
    {
        private JarFileFilter() {
        }
        
        public boolean accept(final File file) {
            return file.getName().toUpperCase().endsWith(".JAR");
        }
        
        JarFileFilter(final PluginClassLoader$1 object) {
            this();
        }
    }
}
