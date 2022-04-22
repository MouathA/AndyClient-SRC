package com.viaversion.viaversion.exception;

import java.util.*;

public class InformativeException extends Exception
{
    private final Map info;
    private int sources;
    
    public InformativeException(final Throwable t) {
        super(t);
        this.info = new HashMap();
    }
    
    public InformativeException set(final String s, final Object o) {
        this.info.put(s, o);
        return this;
    }
    
    public InformativeException addSource(final Class clazz) {
        return this.set("Source " + this.sources++, this.getSource(clazz));
    }
    
    private String getSource(final Class clazz) {
        return clazz.isAnonymousClass() ? (clazz.getName() + " (Anonymous)") : clazz.getName();
    }
    
    @Override
    public String getMessage() {
        final StringBuilder sb = new StringBuilder("Please post this error to https://github.com/ViaVersion/ViaVersion/issues and follow the issue template\n{");
        for (final Map.Entry<String, V> entry : this.info.entrySet()) {
            sb.append(", ");
            sb.append(entry.getKey()).append(": ").append(entry.getValue());
        }
        return sb.append("}\nActual Error: ").toString();
    }
    
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
