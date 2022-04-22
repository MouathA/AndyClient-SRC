package org.apache.http.io;

import java.io.*;
import org.apache.http.*;

public interface HttpMessageParser
{
    HttpMessage parse() throws IOException, HttpException;
}
