package org.apache.http.impl.entity;

import org.apache.http.entity.*;
import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;

@Immutable
public class StrictContentLengthStrategy implements ContentLengthStrategy
{
    public static final StrictContentLengthStrategy INSTANCE;
    private final int implicitLen;
    
    public StrictContentLengthStrategy(final int implicitLen) {
        this.implicitLen = implicitLen;
    }
    
    public StrictContentLengthStrategy() {
        this(-1);
    }
    
    public long determineLength(final HttpMessage httpMessage) throws HttpException {
        Args.notNull(httpMessage, "HTTP message");
        final Header firstHeader = httpMessage.getFirstHeader("Transfer-Encoding");
        if (firstHeader != null) {
            final String value = firstHeader.getValue();
            if ("chunked".equalsIgnoreCase(value)) {
                if (httpMessage.getProtocolVersion().lessEquals(HttpVersion.HTTP_1_0)) {
                    throw new ProtocolException("Chunked transfer encoding not allowed for " + httpMessage.getProtocolVersion());
                }
                return -2L;
            }
            else {
                if ("identity".equalsIgnoreCase(value)) {
                    return -1L;
                }
                throw new ProtocolException("Unsupported transfer encoding: " + value);
            }
        }
        else {
            final Header firstHeader2 = httpMessage.getFirstHeader("Content-Length");
            if (firstHeader2 == null) {
                return this.implicitLen;
            }
            final String value2 = firstHeader2.getValue();
            final long long1 = Long.parseLong(value2);
            if (long1 < 0L) {
                throw new ProtocolException("Negative content length: " + value2);
            }
            return long1;
        }
    }
    
    static {
        INSTANCE = new StrictContentLengthStrategy();
    }
}
