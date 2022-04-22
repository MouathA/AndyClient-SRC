package org.apache.http.util;

import java.nio.charset.*;
import java.io.*;

public class CharsetUtils
{
    public static Charset lookup(final String s) {
        if (s == null) {
            return null;
        }
        return Charset.forName(s);
    }
    
    public static Charset get(final String s) throws UnsupportedEncodingException {
        if (s == null) {
            return null;
        }
        return Charset.forName(s);
    }
}
