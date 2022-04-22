package org.apache.http.params;

import org.apache.http.util.*;
import java.util.*;

@Deprecated
public final class DefaultedHttpParams extends AbstractHttpParams
{
    private final HttpParams local;
    private final HttpParams defaults;
    
    public DefaultedHttpParams(final HttpParams httpParams, final HttpParams defaults) {
        this.local = (HttpParams)Args.notNull(httpParams, "Local HTTP parameters");
        this.defaults = defaults;
    }
    
    public HttpParams copy() {
        return new DefaultedHttpParams(this.local.copy(), this.defaults);
    }
    
    public Object getParameter(final String s) {
        Object o = this.local.getParameter(s);
        if (o == null && this.defaults != null) {
            o = this.defaults.getParameter(s);
        }
        return o;
    }
    
    public boolean removeParameter(final String s) {
        return this.local.removeParameter(s);
    }
    
    public HttpParams setParameter(final String s, final Object o) {
        return this.local.setParameter(s, o);
    }
    
    public HttpParams getDefaults() {
        return this.defaults;
    }
    
    @Override
    public Set getNames() {
        final HashSet set = new HashSet(this.getNames(this.defaults));
        set.addAll(this.getNames(this.local));
        return set;
    }
    
    public Set getDefaultNames() {
        return new HashSet(this.getNames(this.defaults));
    }
    
    public Set getLocalNames() {
        return new HashSet(this.getNames(this.local));
    }
    
    private Set getNames(final HttpParams httpParams) {
        if (httpParams instanceof HttpParamsNames) {
            return ((HttpParamsNames)httpParams).getNames();
        }
        throw new UnsupportedOperationException("HttpParams instance does not implement HttpParamsNames");
    }
}
