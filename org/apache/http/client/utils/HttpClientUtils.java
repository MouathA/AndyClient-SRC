package org.apache.http.client.utils;

import org.apache.http.util.*;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.*;
import java.io.*;

public class HttpClientUtils
{
    private HttpClientUtils() {
    }
    
    public static void closeQuietly(final HttpResponse httpResponse) {
        if (httpResponse != null) {
            final HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                EntityUtils.consume(entity);
            }
        }
    }
    
    public static void closeQuietly(final CloseableHttpResponse closeableHttpResponse) {
        if (closeableHttpResponse != null) {
            EntityUtils.consume(closeableHttpResponse.getEntity());
            closeableHttpResponse.close();
        }
    }
    
    public static void closeQuietly(final HttpClient httpClient) {
        if (httpClient != null && httpClient instanceof Closeable) {
            ((Closeable)httpClient).close();
        }
    }
}
