package org.apache.http;

import java.io.*;
import org.apache.http.annotation.*;

@Immutable
public final class HttpVersion extends ProtocolVersion implements Serializable
{
    private static final long serialVersionUID = -5856653513894415344L;
    public static final String HTTP = "HTTP";
    public static final HttpVersion HTTP_0_9;
    public static final HttpVersion HTTP_1_0;
    public static final HttpVersion HTTP_1_1;
    
    public HttpVersion(final int n, final int n2) {
        super("HTTP", n, n2);
    }
    
    @Override
    public ProtocolVersion forVersion(final int n, final int n2) {
        if (n == this.major && n2 == this.minor) {
            return this;
        }
        if (n == 1) {
            if (n2 == 0) {
                return HttpVersion.HTTP_1_0;
            }
            if (n2 == 1) {
                return HttpVersion.HTTP_1_1;
            }
        }
        if (n == 0 && n2 == 9) {
            return HttpVersion.HTTP_0_9;
        }
        return new HttpVersion(n, n2);
    }
    
    static {
        HTTP_0_9 = new HttpVersion(0, 9);
        HTTP_1_0 = new HttpVersion(1, 0);
        HTTP_1_1 = new HttpVersion(1, 1);
    }
}
