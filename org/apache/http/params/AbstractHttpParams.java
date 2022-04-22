package org.apache.http.params;

import java.util.*;

@Deprecated
public abstract class AbstractHttpParams implements HttpParams, HttpParamsNames
{
    protected AbstractHttpParams() {
    }
    
    public long getLongParameter(final String s, final long n) {
        final Object parameter = this.getParameter(s);
        if (parameter == null) {
            return n;
        }
        return (long)parameter;
    }
    
    public HttpParams setLongParameter(final String s, final long n) {
        this.setParameter(s, n);
        return this;
    }
    
    public int getIntParameter(final String s, final int n) {
        final Object parameter = this.getParameter(s);
        if (parameter == null) {
            return n;
        }
        return (int)parameter;
    }
    
    public HttpParams setIntParameter(final String s, final int n) {
        this.setParameter(s, n);
        return this;
    }
    
    public double getDoubleParameter(final String s, final double n) {
        final Object parameter = this.getParameter(s);
        if (parameter == null) {
            return n;
        }
        return (double)parameter;
    }
    
    public HttpParams setDoubleParameter(final String s, final double n) {
        this.setParameter(s, n);
        return this;
    }
    
    public boolean getBooleanParameter(final String s, final boolean b) {
        final Object parameter = this.getParameter(s);
        if (parameter == null) {
            return b;
        }
        return (boolean)parameter;
    }
    
    public HttpParams setBooleanParameter(final String s, final boolean b) {
        this.setParameter(s, b ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }
    
    public boolean isParameterTrue(final String s) {
        return this.getBooleanParameter(s, false);
    }
    
    public boolean isParameterFalse(final String s) {
        return !this.getBooleanParameter(s, false);
    }
    
    public Set getNames() {
        throw new UnsupportedOperationException();
    }
}
