package org.apache.http.conn;

import org.apache.http.config.*;
import org.apache.http.*;

public interface HttpConnectionFactory
{
    HttpConnection create(final Object p0, final ConnectionConfig p1);
}
