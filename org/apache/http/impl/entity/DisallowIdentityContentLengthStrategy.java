package org.apache.http.impl.entity;

import org.apache.http.entity.*;
import org.apache.http.annotation.*;
import org.apache.http.*;

@Immutable
public class DisallowIdentityContentLengthStrategy implements ContentLengthStrategy
{
    public static final DisallowIdentityContentLengthStrategy INSTANCE;
    private final ContentLengthStrategy contentLengthStrategy;
    
    public DisallowIdentityContentLengthStrategy(final ContentLengthStrategy contentLengthStrategy) {
        this.contentLengthStrategy = contentLengthStrategy;
    }
    
    public long determineLength(final HttpMessage httpMessage) throws HttpException {
        final long determineLength = this.contentLengthStrategy.determineLength(httpMessage);
        if (determineLength == -1L) {
            throw new ProtocolException("Identity transfer encoding cannot be used");
        }
        return determineLength;
    }
    
    static {
        INSTANCE = new DisallowIdentityContentLengthStrategy(new LaxContentLengthStrategy(0));
    }
}
