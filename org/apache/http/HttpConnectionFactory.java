package org.apache.http;

import java.net.*;
import java.io.*;

public interface HttpConnectionFactory
{
    HttpConnection createConnection(final Socket p0) throws IOException;
}
