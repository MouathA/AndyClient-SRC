package com.mojang.authlib;

import org.apache.commons.lang3.*;
import org.apache.commons.io.*;
import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.logging.log4j.*;

public abstract class HttpAuthenticationService extends BaseAuthenticationService
{
    private static final Logger LOGGER;
    private final Proxy proxy;
    
    protected HttpAuthenticationService(final Proxy proxy) {
        Validate.notNull(proxy);
        this.proxy = proxy;
    }
    
    public Proxy getProxy() {
        return this.proxy;
    }
    
    protected HttpURLConnection createUrlConnection(final URL url) throws IOException {
        Validate.notNull(url);
        HttpAuthenticationService.LOGGER.debug("Opening connection to " + url);
        final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection(this.proxy);
        httpURLConnection.setConnectTimeout(15000);
        httpURLConnection.setReadTimeout(15000);
        httpURLConnection.setUseCaches(false);
        return httpURLConnection;
    }
    
    public String performPostRequest(final URL url, final String s, final String s2) throws IOException {
        Validate.notNull(url);
        Validate.notNull(s);
        Validate.notNull(s2);
        final HttpURLConnection urlConnection = this.createUrlConnection(url);
        final byte[] bytes = s.getBytes(Charsets.UTF_8);
        urlConnection.setRequestProperty("Content-Type", s2 + "; charset=utf-8");
        urlConnection.setRequestProperty("Content-Length", "" + bytes.length);
        urlConnection.setDoOutput(true);
        HttpAuthenticationService.LOGGER.debug("Writing POST data to " + url + ": " + s);
        final OutputStream outputStream = urlConnection.getOutputStream();
        IOUtils.write(bytes, outputStream);
        IOUtils.closeQuietly(outputStream);
        HttpAuthenticationService.LOGGER.debug("Reading data from " + url);
        final InputStream inputStream = urlConnection.getInputStream();
        final String string = IOUtils.toString(inputStream, Charsets.UTF_8);
        HttpAuthenticationService.LOGGER.debug("Successful read, server response was " + urlConnection.getResponseCode());
        HttpAuthenticationService.LOGGER.debug("Response: " + string);
        final String s3 = string;
        IOUtils.closeQuietly(inputStream);
        return s3;
    }
    
    public String performGetRequest(final URL url) throws IOException {
        Validate.notNull(url);
        final HttpURLConnection urlConnection = this.createUrlConnection(url);
        HttpAuthenticationService.LOGGER.debug("Reading data from " + url);
        final InputStream inputStream = urlConnection.getInputStream();
        final String string = IOUtils.toString(inputStream, Charsets.UTF_8);
        HttpAuthenticationService.LOGGER.debug("Successful read, server response was " + urlConnection.getResponseCode());
        HttpAuthenticationService.LOGGER.debug("Response: " + string);
        final String s = string;
        IOUtils.closeQuietly(inputStream);
        return s;
    }
    
    public static URL constantURL(final String s) {
        return new URL(s);
    }
    
    public static String buildQuery(final Map map) {
        if (map == null) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            if (entry.getValue() != null) {
                sb.append('=');
                sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
            }
        }
        return sb.toString();
    }
    
    public static URL concatenateURL(final URL url, final String s) {
        if (url.getQuery() != null && url.getQuery().length() > 0) {
            return new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getFile() + "&" + s);
        }
        return new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getFile() + "?" + s);
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
