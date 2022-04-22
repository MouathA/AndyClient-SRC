package com.viaversion.viaversion.libs.javassist;

import java.io.*;
import java.net.*;

public class URLClassPath implements ClassPath
{
    protected String hostname;
    protected int port;
    protected String directory;
    protected String packageName;
    
    public URLClassPath(final String hostname, final int port, final String directory, final String packageName) {
        this.hostname = hostname;
        this.port = port;
        this.directory = directory;
        this.packageName = packageName;
    }
    
    @Override
    public String toString() {
        return this.hostname + ":" + this.port + this.directory;
    }
    
    @Override
    public InputStream openClassfile(final String s) {
        final URLConnection openClassfile0 = this.openClassfile0(s);
        if (openClassfile0 != null) {
            return openClassfile0.getInputStream();
        }
        return null;
    }
    
    private URLConnection openClassfile0(final String s) throws IOException {
        if (this.packageName == null || s.startsWith(this.packageName)) {
            return fetchClass0(this.hostname, this.port, this.directory + s.replace('.', '/') + ".class");
        }
        return null;
    }
    
    @Override
    public URL find(final String s) {
        final URLConnection openClassfile0 = this.openClassfile0(s);
        final InputStream inputStream = openClassfile0.getInputStream();
        if (inputStream != null) {
            inputStream.close();
            return openClassfile0.getURL();
        }
        return null;
    }
    
    public static byte[] fetchClass(final String s, final int n, final String s2, final String s3) throws IOException {
        final URLConnection fetchClass0 = fetchClass0(s, n, s2 + s3.replace('.', '/') + ".class");
        final int contentLength = fetchClass0.getContentLength();
        final InputStream inputStream = fetchClass0.getInputStream();
        byte[] stream = null;
        Label_0130: {
            if (contentLength > 0) {
                stream = new byte[contentLength];
                while (inputStream.read(stream, 0, contentLength - 0) >= 0) {
                    if (0 >= contentLength) {
                        break Label_0130;
                    }
                }
                throw new IOException("the stream was closed: " + s3);
            }
            stream = ClassPoolTail.readStream(inputStream);
        }
        inputStream.close();
        return stream;
    }
    
    private static URLConnection fetchClass0(final String s, final int n, final String s2) throws IOException {
        final URLConnection openConnection = new URL("http", s, n, s2).openConnection();
        openConnection.connect();
        return openConnection;
    }
}
