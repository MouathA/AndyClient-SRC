package org.apache.http.impl.client;

import org.apache.http.client.*;
import org.apache.http.*;

public class NullBackoffStrategy implements ConnectionBackoffStrategy
{
    public boolean shouldBackoff(final Throwable t) {
        return false;
    }
    
    public boolean shouldBackoff(final HttpResponse httpResponse) {
        return false;
    }
}
