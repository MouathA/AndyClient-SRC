package org.apache.http.impl.entity;

import org.apache.http.annotation.*;
import org.apache.http.entity.*;
import org.apache.http.util.*;
import org.apache.http.io.*;
import org.apache.http.impl.io.*;
import java.io.*;
import org.apache.http.*;

@Deprecated
@Immutable
public class EntitySerializer
{
    private final ContentLengthStrategy lenStrategy;
    
    public EntitySerializer(final ContentLengthStrategy contentLengthStrategy) {
        this.lenStrategy = (ContentLengthStrategy)Args.notNull(contentLengthStrategy, "Content length strategy");
    }
    
    protected OutputStream doSerialize(final SessionOutputBuffer sessionOutputBuffer, final HttpMessage httpMessage) throws HttpException, IOException {
        final long determineLength = this.lenStrategy.determineLength(httpMessage);
        if (determineLength == -2L) {
            return new ChunkedOutputStream(sessionOutputBuffer);
        }
        if (determineLength == -1L) {
            return new IdentityOutputStream(sessionOutputBuffer);
        }
        return new ContentLengthOutputStream(sessionOutputBuffer, determineLength);
    }
    
    public void serialize(final SessionOutputBuffer sessionOutputBuffer, final HttpMessage httpMessage, final HttpEntity httpEntity) throws HttpException, IOException {
        Args.notNull(sessionOutputBuffer, "Session output buffer");
        Args.notNull(httpMessage, "HTTP message");
        Args.notNull(httpEntity, "HTTP entity");
        final OutputStream doSerialize = this.doSerialize(sessionOutputBuffer, httpMessage);
        httpEntity.writeTo(doSerialize);
        doSerialize.close();
    }
}
