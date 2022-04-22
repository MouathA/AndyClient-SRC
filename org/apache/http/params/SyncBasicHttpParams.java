package org.apache.http.params;

import org.apache.http.annotation.*;

@Deprecated
@ThreadSafe
public class SyncBasicHttpParams extends BasicHttpParams
{
    private static final long serialVersionUID = 5387834869062660642L;
    
    @Override
    public synchronized boolean removeParameter(final String s) {
        return super.removeParameter(s);
    }
    
    @Override
    public synchronized HttpParams setParameter(final String s, final Object o) {
        return super.setParameter(s, o);
    }
    
    @Override
    public synchronized Object getParameter(final String s) {
        return super.getParameter(s);
    }
    
    @Override
    public synchronized boolean isParameterSet(final String s) {
        return super.isParameterSet(s);
    }
    
    @Override
    public synchronized boolean isParameterSetLocally(final String s) {
        return super.isParameterSetLocally(s);
    }
    
    @Override
    public synchronized void setParameters(final String[] array, final Object o) {
        super.setParameters(array, o);
    }
    
    @Override
    public synchronized void clear() {
        super.clear();
    }
    
    @Override
    public synchronized Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
