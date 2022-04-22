package com.ibm.icu.impl;

import java.lang.reflect.*;
import java.net.*;
import java.util.jar.*;
import java.util.*;
import java.io.*;

public abstract class URLHandler
{
    public static final String PROPNAME = "urlhandler.props";
    private static final Map handlers;
    private static final boolean DEBUG;
    
    public static URLHandler get(final URL url) {
        if (url == null) {
            return null;
        }
        final String protocol = url.getProtocol();
        if (URLHandler.handlers != null) {
            final Method method = URLHandler.handlers.get(protocol);
            if (method != null) {
                final URLHandler urlHandler = (URLHandler)method.invoke(null, url);
                if (urlHandler != null) {
                    return urlHandler;
                }
            }
        }
        return getDefault(url);
    }
    
    protected static URLHandler getDefault(final URL url) {
        URLHandler urlHandler = null;
        final String protocol = url.getProtocol();
        if (protocol.equals("file")) {
            urlHandler = new FileURLHandler(url);
        }
        else if (protocol.equals("jar") || protocol.equals("wsjar")) {
            urlHandler = new JarURLHandler(url);
        }
        return urlHandler;
    }
    
    public void guide(final URLVisitor urlVisitor, final boolean b) {
        this.guide(urlVisitor, b, true);
    }
    
    public abstract void guide(final URLVisitor p0, final boolean p1, final boolean p2);
    
    static boolean access$000() {
        return URLHandler.DEBUG;
    }
    
    static {
        DEBUG = ICUDebug.enabled("URLHandler");
        Map<String, Method> handlers2 = null;
        InputStream inputStream = URLHandler.class.getResourceAsStream("urlhandler.props");
        if (inputStream == null) {
            inputStream = Utility.getFallbackClassLoader().getResourceAsStream("urlhandler.props");
        }
        if (inputStream != null) {
            final Class[] array = { URL.class };
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
                final String trim = s.trim();
                if (trim.length() != 0) {
                    if (trim.charAt(0) != '#') {
                        final int index = trim.indexOf(61);
                        if (index == -1) {
                            if (URLHandler.DEBUG) {
                                System.err.println("bad urlhandler line: '" + trim + "'");
                                break;
                            }
                            break;
                        }
                        else {
                            final String trim2 = trim.substring(0, index).trim();
                            final Method declaredMethod = Class.forName(trim.substring(index + 1).trim()).getDeclaredMethod("get", (Class<?>[])array);
                            if (handlers2 == null) {
                                handlers2 = new HashMap<String, Method>();
                            }
                            handlers2.put(trim2, declaredMethod);
                        }
                    }
                }
            }
            bufferedReader.close();
        }
        handlers = handlers2;
    }
    
    public interface URLVisitor
    {
        void visit(final String p0);
    }
    
    private static class JarURLHandler extends URLHandler
    {
        JarFile jarFile;
        String prefix;
        
        JarURLHandler(URL url) {
            this.prefix = url.getPath();
            final int lastIndex = this.prefix.lastIndexOf("!/");
            if (lastIndex >= 0) {
                this.prefix = this.prefix.substring(lastIndex + 2);
            }
            if (!url.getProtocol().equals("jar")) {
                final String string = url.toString();
                final int index = string.indexOf(":");
                if (index != -1) {
                    url = new URL("jar" + string.substring(index));
                }
            }
            this.jarFile = ((JarURLConnection)url.openConnection()).getJarFile();
        }
        
        @Override
        public void guide(final URLVisitor urlVisitor, final boolean b, final boolean b2) {
            final Enumeration<JarEntry> entries = this.jarFile.entries();
            while (entries.hasMoreElements()) {
                final JarEntry jarEntry = entries.nextElement();
                if (!jarEntry.isDirectory()) {
                    final String name = jarEntry.getName();
                    if (!name.startsWith(this.prefix)) {
                        continue;
                    }
                    String s = name.substring(this.prefix.length());
                    final int lastIndex = s.lastIndexOf(47);
                    if (lastIndex != -1) {
                        if (!b) {
                            continue;
                        }
                        if (b2) {
                            s = s.substring(lastIndex + 1);
                        }
                    }
                    urlVisitor.visit(s);
                }
            }
        }
    }
    
    private static class FileURLHandler extends URLHandler
    {
        File file;
        
        FileURLHandler(final URL url) {
            this.file = new File(url.toURI());
            if (this.file == null || !this.file.exists()) {
                if (URLHandler.access$000()) {
                    System.err.println("file does not exist - " + url.toString());
                }
                throw new IllegalArgumentException();
            }
        }
        
        @Override
        public void guide(final URLVisitor urlVisitor, final boolean b, final boolean b2) {
            if (this.file.isDirectory()) {
                this.process(urlVisitor, b, b2, "/", this.file.listFiles());
            }
            else {
                urlVisitor.visit(this.file.getName());
            }
        }
        
        private void process(final URLVisitor urlVisitor, final boolean b, final boolean b2, final String s, final File[] array) {
            while (0 < array.length) {
                final File file = array[0];
                if (file.isDirectory()) {
                    if (b) {
                        this.process(urlVisitor, b, b2, s + file.getName() + '/', file.listFiles());
                    }
                }
                else {
                    urlVisitor.visit(b2 ? file.getName() : (s + file.getName()));
                }
                int n = 0;
                ++n;
            }
        }
    }
}
