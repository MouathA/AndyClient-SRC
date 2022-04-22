package optifine;

import java.io.*;
import java.net.*;
import java.util.*;

public class HttpPipeline
{
    private static Map mapConnections;
    public static final String HEADER_USER_AGENT;
    public static final String HEADER_HOST;
    public static final String HEADER_ACCEPT;
    public static final String HEADER_LOCATION;
    public static final String HEADER_KEEP_ALIVE;
    public static final String HEADER_CONNECTION;
    public static final String HEADER_VALUE_KEEP_ALIVE;
    public static final String HEADER_TRANSFER_ENCODING;
    public static final String HEADER_VALUE_CHUNKED;
    
    static {
        HEADER_HOST = "Host";
        HEADER_LOCATION = "Location";
        HEADER_KEEP_ALIVE = "Keep-Alive";
        HEADER_VALUE_KEEP_ALIVE = "keep-alive";
        HEADER_TRANSFER_ENCODING = "Transfer-Encoding";
        HEADER_USER_AGENT = "User-Agent";
        HEADER_VALUE_CHUNKED = "chunked";
        HEADER_ACCEPT = "Accept";
        HEADER_CONNECTION = "Connection";
        HttpPipeline.mapConnections = new HashMap();
    }
    
    public static void addRequest(final String s, final HttpListener httpListener) throws IOException {
        addRequest(s, httpListener, Proxy.NO_PROXY);
    }
    
    public static void addRequest(final String s, final HttpListener httpListener, final Proxy proxy) throws IOException {
        addRequest(new HttpPipelineRequest(makeRequest(s, proxy), httpListener));
    }
    
    public static HttpRequest makeRequest(final String s, final Proxy proxy) throws IOException {
        final URL url = new URL(s);
        if (!url.getProtocol().equals("http")) {
            throw new IOException("Only protocol http is supported: " + url);
        }
        final String file = url.getFile();
        final String host = url.getHost();
        url.getPort();
        if (80 <= 0) {}
        final String s2 = "GET";
        final String s3 = "HTTP/1.1";
        final LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        linkedHashMap.put("User-Agent", "Java/" + System.getProperty("java.version"));
        linkedHashMap.put("Host", host);
        linkedHashMap.put("Accept", "text/html, image/gif, image/png");
        linkedHashMap.put("Connection", "keep-alive");
        return new HttpRequest(host, 80, proxy, s2, file, s3, linkedHashMap, new byte[0]);
    }
    
    public static void addRequest(final HttpPipelineRequest httpPipelineRequest) {
        final HttpRequest httpRequest = httpPipelineRequest.getHttpRequest();
        for (HttpPipelineConnection httpPipelineConnection = getConnection(httpRequest.getHost(), httpRequest.getPort(), httpRequest.getProxy()); !httpPipelineConnection.addRequest(httpPipelineRequest); httpPipelineConnection = getConnection(httpRequest.getHost(), httpRequest.getPort(), httpRequest.getProxy())) {
            removeConnection(httpRequest.getHost(), httpRequest.getPort(), httpRequest.getProxy(), httpPipelineConnection);
        }
    }
    
    private static synchronized HttpPipelineConnection getConnection(final String s, final int n, final Proxy proxy) {
        final String connectionKey = makeConnectionKey(s, n, proxy);
        HttpPipelineConnection httpPipelineConnection = HttpPipeline.mapConnections.get(connectionKey);
        if (httpPipelineConnection == null) {
            httpPipelineConnection = new HttpPipelineConnection(s, n, proxy);
            HttpPipeline.mapConnections.put(connectionKey, httpPipelineConnection);
        }
        return httpPipelineConnection;
    }
    
    private static synchronized void removeConnection(final String s, final int n, final Proxy proxy, final HttpPipelineConnection httpPipelineConnection) {
        final String connectionKey = makeConnectionKey(s, n, proxy);
        if (HttpPipeline.mapConnections.get(connectionKey) == httpPipelineConnection) {
            HttpPipeline.mapConnections.remove(connectionKey);
        }
    }
    
    private static String makeConnectionKey(final String s, final int n, final Proxy proxy) {
        return String.valueOf(s) + ":" + n + "-" + proxy;
    }
    
    public static byte[] get(final String s) throws IOException {
        return get(s, Proxy.NO_PROXY);
    }
    
    public static byte[] get(final String s, final Proxy proxy) throws IOException {
        final HttpResponse executeRequest = executeRequest(makeRequest(s, proxy));
        if (executeRequest.getStatus() / 100 != 2) {
            throw new IOException("HTTP response: " + executeRequest.getStatus());
        }
        return executeRequest.getBody();
    }
    
    public static HttpResponse executeRequest(final HttpRequest httpRequest) throws IOException {
        final HashMap<Object, Exception> hashMap = new HashMap<Object, Exception>();
        final HttpListener httpListener = new HttpListener() {
            private final HashMap val$map;
            
            @Override
            public void finished(final HttpRequest httpRequest, final HttpResponse httpResponse) {
                final HashMap val$map = this.val$map;
                // monitorenter(val$map2 = this.val$map)
                this.val$map.put("Response", httpResponse);
                this.val$map.notifyAll();
            }
            // monitorexit(val$map2)
            
            @Override
            public void failed(final HttpRequest httpRequest, final Exception ex) {
                final HashMap val$map = this.val$map;
                // monitorenter(val$map2 = this.val$map)
                this.val$map.put("Exception", ex);
                this.val$map.notifyAll();
            }
            // monitorexit(val$map2)
        };
        // monitorenter(hashMap2 = hashMap)
        addRequest(new HttpPipelineRequest(httpRequest, httpListener));
        hashMap.wait();
        final Exception ex = hashMap.get("Exception");
        if (ex != null) {
            if (ex instanceof IOException) {
                throw (IOException)ex;
            }
            if (ex instanceof RuntimeException) {
                throw (RuntimeException)ex;
            }
            throw new RuntimeException(ex.getMessage(), ex);
        }
        else {
            final HttpResponse httpResponse = (HttpResponse)hashMap.get("Response");
            if (httpResponse == null) {
                throw new IOException("Response is null");
            }
            // monitorexit(hashMap2)
            return httpResponse;
        }
    }
    
    public static boolean hasActiveRequests() {
        final Iterator<HttpPipelineConnection> iterator = HttpPipeline.mapConnections.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().hasActiveRequests()) {
                return true;
            }
        }
        return false;
    }
}
