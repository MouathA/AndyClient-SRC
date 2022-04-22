package org.apache.http.pool;

import java.io.*;

public interface ConnFactory
{
    Object create(final Object p0) throws IOException;
}
