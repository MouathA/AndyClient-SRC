package org.apache.http.impl.client;

import org.apache.http.client.*;
import java.net.*;
import org.apache.http.*;

public class DefaultBackoffStrategy implements ConnectionBackoffStrategy
{
    public boolean shouldBackoff(final Throwable t) {
        return t instanceof SocketTimeoutException || t instanceof ConnectException;
    }
    
    public boolean shouldBackoff(final HttpResponse httpResponse) {
        return httpResponse.getStatusLine().getStatusCode() == 503;
    }
}
