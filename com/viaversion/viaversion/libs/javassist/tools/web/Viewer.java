package com.viaversion.viaversion.libs.javassist.tools.web;

import java.net.*;
import java.io.*;

public class Viewer extends ClassLoader
{
    private String server;
    private int port;
    
    public static void main(final String[] array) throws Throwable {
        if (array.length >= 3) {
            final Viewer viewer = new Viewer(array[0], Integer.parseInt(array[1]));
            final String[] array2 = new String[array.length - 3];
            System.arraycopy(array, 3, array2, 0, array.length - 3);
            viewer.run(array[2], array2);
        }
        else {
            System.err.println("Usage: java javassist.tools.web.Viewer <host> <port> class [args ...]");
        }
    }
    
    public Viewer(final String server, final int port) {
        this.server = server;
        this.port = port;
    }
    
    public String getServer() {
        return this.server;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void run(final String s, final String[] array) throws Throwable {
        this.loadClass(s).getDeclaredMethod("main", String[].class).invoke(null, array);
    }
    
    @Override
    protected synchronized Class loadClass(final String s, final boolean b) throws ClassNotFoundException {
        Class<?> clazz = this.findLoadedClass(s);
        if (clazz == null) {
            clazz = (Class<?>)this.findClass(s);
        }
        if (clazz == null) {
            throw new ClassNotFoundException(s);
        }
        if (b) {
            this.resolveClass(clazz);
        }
        return clazz;
    }
    
    @Override
    protected Class findClass(final String s) throws ClassNotFoundException {
        Class<?> clazz = null;
        if (s.startsWith("java.") || s.startsWith("javax.") || s.equals("com.viaversion.viaversion.libs.javassist.tools.web.Viewer")) {
            clazz = this.findSystemClass(s);
        }
        if (clazz == null) {
            final byte[] fetchClass = this.fetchClass(s);
            if (fetchClass != null) {
                clazz = this.defineClass(s, fetchClass, 0, fetchClass.length);
            }
        }
        return clazz;
    }
    
    protected byte[] fetchClass(final String s) throws Exception {
        final URLConnection openConnection = new URL("http", this.server, this.port, "/" + s.replace('.', '/') + ".class").openConnection();
        openConnection.connect();
        final int contentLength = openConnection.getContentLength();
        final InputStream inputStream = openConnection.getInputStream();
        byte[] stream = null;
        Label_0156: {
            if (contentLength > 0) {
                stream = new byte[contentLength];
                while (inputStream.read(stream, 0, contentLength - 0) >= 0) {
                    if (0 >= contentLength) {
                        break Label_0156;
                    }
                }
                inputStream.close();
                throw new IOException("the stream was closed: " + s);
            }
            stream = this.readStream(inputStream);
        }
        inputStream.close();
        return stream;
    }
    
    private byte[] readStream(final InputStream inputStream) throws IOException {
        byte[] array = new byte[4096];
        do {
            if (array.length - 0 <= 0) {
                final byte[] array2 = new byte[array.length * 2];
                System.arraycopy(array, 0, array2, 0, 0);
                array = array2;
            }
            inputStream.read(array, 0, array.length - 0);
        } while (0 >= 0);
        final byte[] array3 = new byte[0];
        System.arraycopy(array, 0, array3, 0, 0);
        return array3;
    }
}
