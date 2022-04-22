package org.apache.http.util;

import org.apache.http.*;
import java.nio.charset.*;
import org.apache.http.entity.*;
import org.apache.http.protocol.*;
import java.io.*;

public final class EntityUtils
{
    private EntityUtils() {
    }
    
    public static void consumeQuietly(final HttpEntity httpEntity) {
        consume(httpEntity);
    }
    
    public static void consume(final HttpEntity httpEntity) throws IOException {
        if (httpEntity == null) {
            return;
        }
        if (httpEntity.isStreaming()) {
            final InputStream content = httpEntity.getContent();
            if (content != null) {
                content.close();
            }
        }
    }
    
    public static void updateEntity(final HttpResponse httpResponse, final HttpEntity entity) throws IOException {
        Args.notNull(httpResponse, "Response");
        consume(httpResponse.getEntity());
        httpResponse.setEntity(entity);
    }
    
    public static byte[] toByteArray(final HttpEntity httpEntity) throws IOException {
        Args.notNull(httpEntity, "Entity");
        final InputStream content = httpEntity.getContent();
        if (content == null) {
            return null;
        }
        Args.check(httpEntity.getContentLength() <= 2147483647L, "HTTP entity too large to be buffered in memory");
        final int n = (int)httpEntity.getContentLength();
        if (4096 < 0) {}
        final ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(4096);
        final byte[] array = new byte[4096];
        int read;
        while ((read = content.read(array)) != -1) {
            byteArrayBuffer.append(array, 0, read);
        }
        final byte[] byteArray = byteArrayBuffer.toByteArray();
        content.close();
        return byteArray;
    }
    
    @Deprecated
    public static String getContentCharSet(final HttpEntity httpEntity) throws ParseException {
        Args.notNull(httpEntity, "Entity");
        String value = null;
        if (httpEntity.getContentType() != null) {
            final HeaderElement[] elements = httpEntity.getContentType().getElements();
            if (elements.length > 0) {
                final NameValuePair parameterByName = elements[0].getParameterByName("charset");
                if (parameterByName != null) {
                    value = parameterByName.getValue();
                }
            }
        }
        return value;
    }
    
    @Deprecated
    public static String getContentMimeType(final HttpEntity httpEntity) throws ParseException {
        Args.notNull(httpEntity, "Entity");
        String name = null;
        if (httpEntity.getContentType() != null) {
            final HeaderElement[] elements = httpEntity.getContentType().getElements();
            if (elements.length > 0) {
                name = elements[0].getName();
            }
        }
        return name;
    }
    
    public static String toString(final HttpEntity httpEntity, final Charset charset) throws IOException, ParseException {
        Args.notNull(httpEntity, "Entity");
        final InputStream content = httpEntity.getContent();
        if (content == null) {
            return null;
        }
        Args.check(httpEntity.getContentLength() <= 2147483647L, "HTTP entity too large to be buffered in memory");
        final int n = (int)httpEntity.getContentLength();
        if (4096 < 0) {}
        Charset charset2 = null;
        final ContentType value = ContentType.get(httpEntity);
        if (value != null) {
            charset2 = value.getCharset();
        }
        if (charset2 == null) {
            charset2 = charset;
        }
        if (charset2 == null) {
            charset2 = HTTP.DEF_CONTENT_CHARSET;
        }
        final InputStreamReader inputStreamReader = new InputStreamReader(content, charset2);
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(4096);
        final char[] array = new char[1024];
        int read;
        while ((read = inputStreamReader.read(array)) != -1) {
            charArrayBuffer.append(array, 0, read);
        }
        final String string = charArrayBuffer.toString();
        content.close();
        return string;
    }
    
    public static String toString(final HttpEntity httpEntity, final String s) throws IOException, ParseException {
        return toString(httpEntity, (s != null) ? Charset.forName(s) : null);
    }
    
    public static String toString(final HttpEntity httpEntity) throws IOException, ParseException {
        return toString(httpEntity, (Charset)null);
    }
}
