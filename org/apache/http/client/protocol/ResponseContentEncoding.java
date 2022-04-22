package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import java.util.*;
import org.apache.http.client.entity.*;
import org.apache.http.*;
import java.io.*;

@Immutable
public class ResponseContentEncoding implements HttpResponseInterceptor
{
    public static final String UNCOMPRESSED = "http.client.response.uncompressed";
    
    public void process(final HttpResponse httpResponse, final HttpContext httpContext) throws HttpException, IOException {
        final HttpEntity entity = httpResponse.getEntity();
        if (entity != null && entity.getContentLength() != 0L) {
            final Header contentEncoding = entity.getContentEncoding();
            if (contentEncoding != null) {
                final HeaderElement[] elements = contentEncoding.getElements();
                if (0 < elements.length) {
                    final HeaderElement headerElement = elements[0];
                    final String lowerCase = headerElement.getName().toLowerCase(Locale.US);
                    if ("gzip".equals(lowerCase) || "x-gzip".equals(lowerCase)) {
                        httpResponse.setEntity(new GzipDecompressingEntity(httpResponse.getEntity()));
                    }
                    else if ("deflate".equals(lowerCase)) {
                        httpResponse.setEntity(new DeflateDecompressingEntity(httpResponse.getEntity()));
                    }
                    else {
                        if ("identity".equals(lowerCase)) {
                            return;
                        }
                        throw new HttpException("Unsupported Content-Coding: " + headerElement.getName());
                    }
                }
                if (true) {
                    httpResponse.removeHeaders("Content-Length");
                    httpResponse.removeHeaders("Content-Encoding");
                    httpResponse.removeHeaders("Content-MD5");
                }
            }
        }
    }
}
