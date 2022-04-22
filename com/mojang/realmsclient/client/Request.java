package com.mojang.realmsclient.client;

import java.net.*;
import java.io.*;

public abstract class Request
{
    protected HttpURLConnection connection;
    private boolean connected;
    protected String url;
    private static final int DEFAULT_READ_TIMEOUT = 60000;
    private static final int DEFAULT_CONNECT_TIMEOUT = 5000;
    
    public Request(final String url, final int connectTimeout, final int readTimeout) {
        this.url = url;
        final Proxy proxy = RealmsClientConfig.getProxy();
        if (proxy != null) {
            this.connection = (HttpURLConnection)new URL(url).openConnection(proxy);
        }
        else {
            this.connection = (HttpURLConnection)new URL(url).openConnection();
        }
        this.connection.setConnectTimeout(connectTimeout);
        this.connection.setReadTimeout(readTimeout);
    }
    
    public void cookie(final String s, final String s2) {
        cookie(this.connection, s, s2);
    }
    
    public static void cookie(final HttpURLConnection httpURLConnection, final String s, final String s2) {
        final String requestProperty = httpURLConnection.getRequestProperty("Cookie");
        if (requestProperty == null) {
            httpURLConnection.setRequestProperty("Cookie", s + "=" + s2);
        }
        else {
            httpURLConnection.setRequestProperty("Cookie", requestProperty + ";" + s + "=" + s2);
        }
    }
    
    public Request header(final String s, final String s2) {
        this.connection.addRequestProperty(s, s2);
        return this;
    }
    
    public int getRetryAfterHeader() {
        return getRetryAfterHeader(this.connection);
    }
    
    public static int getRetryAfterHeader(final HttpURLConnection httpURLConnection) {
        return Integer.valueOf(httpURLConnection.getHeaderField("Retry-After"));
    }
    
    public int responseCode() {
        this.connect();
        return this.connection.getResponseCode();
    }
    
    public String text() {
        this.connect();
        String s;
        if (this.responseCode() >= 400) {
            s = this.read(this.connection.getErrorStream());
        }
        else {
            s = this.read(this.connection.getInputStream());
        }
        this.dispose();
        return s;
    }
    
    private String read(final InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        final StringBuilder sb = new StringBuilder();
        for (int i = inputStreamReader.read(); i != -1; i = inputStreamReader.read()) {
            sb.append((char)i);
        }
        return sb.toString();
    }
    
    private void dispose() {
        final byte[] array = new byte[1024];
        final InputStream inputStream = this.connection.getInputStream();
        while (inputStream.read(array) > 0) {}
        inputStream.close();
        if (this.connection != null) {
            this.connection.disconnect();
        }
    }
    
    protected Request connect() {
        if (!this.connected) {
            final Request doConnect = this.doConnect();
            this.connected = true;
            return doConnect;
        }
        return this;
    }
    
    protected abstract Request doConnect();
    
    public static Request get(final String s) {
        return new Get(s, 5000, 60000);
    }
    
    public static Request get(final String s, final int n, final int n2) {
        return new Get(s, n, n2);
    }
    
    public static Request post(final String s, final String s2) {
        return new Post(s, s2.getBytes(), 5000, 60000);
    }
    
    public static Request post(final String s, final String s2, final int n, final int n2) {
        return new Post(s, s2.getBytes(), n, n2);
    }
    
    public static Request delete(final String s) {
        return new Delete(s, 5000, 60000);
    }
    
    public static Request put(final String s, final String s2) {
        return new Put(s, s2.getBytes(), 5000, 60000);
    }
    
    public static Request put(final String s, final String s2, final int n, final int n2) {
        return new Put(s, s2.getBytes(), n, n2);
    }
    
    public String getHeader(final String s) {
        return getHeader(this.connection, s);
    }
    
    public static String getHeader(final HttpURLConnection httpURLConnection, final String s) {
        return httpURLConnection.getHeaderField(s);
    }
    
    public static class Post extends Request
    {
        private byte[] content;
        
        public Post(final String s, final byte[] content, final int n, final int n2) {
            super(s, n, n2);
            this.content = content;
        }
        
        public Post doConnect() {
            if (this.content != null) {
                this.connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            }
            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);
            this.connection.setUseCaches(false);
            this.connection.setRequestMethod("POST");
            final OutputStream outputStream = this.connection.getOutputStream();
            outputStream.write(this.content);
            outputStream.flush();
            return this;
        }
        
        public Request doConnect() {
            return this.doConnect();
        }
    }
    
    public static class Put extends Request
    {
        private byte[] content;
        
        public Put(final String s, final byte[] content, final int n, final int n2) {
            super(s, n, n2);
            this.content = content;
        }
        
        public Put doConnect() {
            if (this.content != null) {
                this.connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            }
            this.connection.setDoOutput(true);
            this.connection.setDoInput(true);
            this.connection.setRequestMethod("PUT");
            final OutputStream outputStream = this.connection.getOutputStream();
            outputStream.write(this.content);
            outputStream.flush();
            return this;
        }
        
        public Request doConnect() {
            return this.doConnect();
        }
    }
    
    public static class Get extends Request
    {
        public Get(final String s, final int n, final int n2) {
            super(s, n, n2);
        }
        
        public Get doConnect() {
            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);
            this.connection.setUseCaches(false);
            this.connection.setRequestMethod("GET");
            return this;
        }
        
        public Request doConnect() {
            return this.doConnect();
        }
    }
    
    public static class Delete extends Request
    {
        public Delete(final String s, final int n, final int n2) {
            super(s, n, n2);
        }
        
        public Delete doConnect() {
            this.connection.setDoOutput(true);
            this.connection.setRequestMethod("DELETE");
            this.connection.connect();
            return this;
        }
        
        public Request doConnect() {
            return this.doConnect();
        }
    }
}
