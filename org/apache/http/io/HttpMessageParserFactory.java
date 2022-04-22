package org.apache.http.io;

import org.apache.http.config.*;

public interface HttpMessageParserFactory
{
    HttpMessageParser create(final SessionInputBuffer p0, final MessageConstraints p1);
}
