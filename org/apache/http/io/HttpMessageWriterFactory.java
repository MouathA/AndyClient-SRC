package org.apache.http.io;

public interface HttpMessageWriterFactory
{
    HttpMessageWriter create(final SessionOutputBuffer p0);
}
