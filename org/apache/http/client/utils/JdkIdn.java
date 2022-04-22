package org.apache.http.client.utils;

import org.apache.http.annotation.*;
import java.lang.reflect.*;

@Immutable
public class JdkIdn implements Idn
{
    private final Method toUnicode;
    
    public JdkIdn() throws ClassNotFoundException {
        this.toUnicode = Class.forName("java.net.IDN").getMethod("toUnicode", String.class);
    }
    
    public String toUnicode(final String s) {
        return (String)this.toUnicode.invoke(null, s);
    }
}
