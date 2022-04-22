package org.apache.http.params;

import java.io.*;
import org.apache.http.annotation.*;
import java.util.concurrent.*;
import java.util.*;

@Deprecated
@ThreadSafe
public class BasicHttpParams extends AbstractHttpParams implements Serializable, Cloneable
{
    private static final long serialVersionUID = -7086398485908701455L;
    private final Map parameters;
    
    public BasicHttpParams() {
        this.parameters = new ConcurrentHashMap();
    }
    
    public Object getParameter(final String s) {
        return this.parameters.get(s);
    }
    
    public HttpParams setParameter(final String s, final Object o) {
        if (s == null) {
            return this;
        }
        if (o != null) {
            this.parameters.put(s, o);
        }
        else {
            this.parameters.remove(s);
        }
        return this;
    }
    
    public boolean removeParameter(final String s) {
        if (this.parameters.containsKey(s)) {
            this.parameters.remove(s);
            return true;
        }
        return false;
    }
    
    public void setParameters(final String[] array, final Object o) {
        while (0 < array.length) {
            this.setParameter(array[0], o);
            int n = 0;
            ++n;
        }
    }
    
    public boolean isParameterSet(final String s) {
        return this.getParameter(s) != null;
    }
    
    public boolean isParameterSetLocally(final String s) {
        return this.parameters.get(s) != null;
    }
    
    public void clear() {
        this.parameters.clear();
    }
    
    public HttpParams copy() {
        return (HttpParams)this.clone();
    }
    
    public Object clone() throws CloneNotSupportedException {
        final BasicHttpParams basicHttpParams = (BasicHttpParams)super.clone();
        this.copyParams(basicHttpParams);
        return basicHttpParams;
    }
    
    public void copyParams(final HttpParams httpParams) {
        for (final Map.Entry<String, V> entry : this.parameters.entrySet()) {
            httpParams.setParameter(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public Set getNames() {
        return new HashSet(this.parameters.keySet());
    }
}
