package org.apache.http.impl.entity;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.io.*;
import org.apache.http.entity.*;
import org.apache.http.impl.io.*;
import java.io.*;
import org.apache.http.*;

@Deprecated
@Immutable
public class EntityDeserializer
{
    private final ContentLengthStrategy lenStrategy;
    
    public EntityDeserializer(final ContentLengthStrategy contentLengthStrategy) {
        this.lenStrategy = (ContentLengthStrategy)Args.notNull(contentLengthStrategy, "Content length strategy");
    }
    
    protected BasicHttpEntity doDeserialize(final SessionInputBuffer sessionInputBuffer, final HttpMessage httpMessage) throws HttpException, IOException {
        final BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
        final long determineLength = this.lenStrategy.determineLength(httpMessage);
        if (determineLength == -2L) {
            basicHttpEntity.setChunked(true);
            basicHttpEntity.setContentLength(-1L);
            basicHttpEntity.setContent(new ChunkedInputStream(sessionInputBuffer));
        }
        else if (determineLength == -1L) {
            basicHttpEntity.setChunked(false);
            basicHttpEntity.setContentLength(-1L);
            basicHttpEntity.setContent(new IdentityInputStream(sessionInputBuffer));
        }
        else {
            basicHttpEntity.setChunked(false);
            basicHttpEntity.setContentLength(determineLength);
            basicHttpEntity.setContent(new ContentLengthInputStream(sessionInputBuffer, determineLength));
        }
        final Header firstHeader = httpMessage.getFirstHeader("Content-Type");
        if (firstHeader != null) {
            basicHttpEntity.setContentType(firstHeader);
        }
        final Header firstHeader2 = httpMessage.getFirstHeader("Content-Encoding");
        if (firstHeader2 != null) {
            basicHttpEntity.setContentEncoding(firstHeader2);
        }
        return basicHttpEntity;
    }
    
    public HttpEntity deserialize(final SessionInputBuffer sessionInputBuffer, final HttpMessage httpMessage) throws HttpException, IOException {
        Args.notNull(sessionInputBuffer, "Session input buffer");
        Args.notNull(httpMessage, "HTTP message");
        return this.doDeserialize(sessionInputBuffer, httpMessage);
    }
}
