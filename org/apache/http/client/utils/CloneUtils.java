package org.apache.http.client.utils;

import org.apache.http.annotation.*;

@Immutable
public class CloneUtils
{
    public static Object cloneObject(final Object o) throws CloneNotSupportedException {
        if (o == null) {
            return null;
        }
        if (o instanceof Cloneable) {
            return o.getClass().getMethod("clone", (Class<?>[])null).invoke(o, (Object[])null);
        }
        throw new CloneNotSupportedException();
    }
    
    public static Object clone(final Object o) throws CloneNotSupportedException {
        return cloneObject(o);
    }
    
    private CloneUtils() {
    }
}
