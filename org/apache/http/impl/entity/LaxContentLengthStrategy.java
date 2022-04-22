package org.apache.http.impl.entity;

import org.apache.http.entity.*;
import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;

@Immutable
public class LaxContentLengthStrategy implements ContentLengthStrategy
{
    public static final LaxContentLengthStrategy INSTANCE;
    private final int implicitLen;
    
    public LaxContentLengthStrategy(final int implicitLen) {
        this.implicitLen = implicitLen;
    }
    
    public LaxContentLengthStrategy() {
        this(-1);
    }
    
    public long determineLength(final HttpMessage httpMessage) throws HttpException {
        Args.notNull(httpMessage, "HTTP message");
        final Header firstHeader = httpMessage.getFirstHeader("Transfer-Encoding");
        if (firstHeader != null) {
            final HeaderElement[] elements = firstHeader.getElements();
            final int length = elements.length;
            if ("identity".equalsIgnoreCase(firstHeader.getValue())) {
                return -1L;
            }
            if (length > 0 && "chunked".equalsIgnoreCase(elements[length - 1].getName())) {
                return -2L;
            }
            return -1L;
        }
        else {
            if (httpMessage.getFirstHeader("Content-Length") == null) {
                return this.implicitLen;
            }
            long long1 = -1L;
            final Header[] headers = httpMessage.getHeaders("Content-Length");
            final int n = headers.length - 1;
            if (n >= 0) {
                long1 = Long.parseLong(headers[n].getValue());
            }
            if (long1 >= 0L) {
                return long1;
            }
            return -1L;
        }
    }
    
    static {
        INSTANCE = new LaxContentLengthStrategy();
    }
}
