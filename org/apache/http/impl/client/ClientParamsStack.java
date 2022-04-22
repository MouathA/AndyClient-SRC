package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.util.*;

@Deprecated
@NotThreadSafe
public class ClientParamsStack extends AbstractHttpParams
{
    protected final HttpParams applicationParams;
    protected final HttpParams clientParams;
    protected final HttpParams requestParams;
    protected final HttpParams overrideParams;
    
    public ClientParamsStack(final HttpParams applicationParams, final HttpParams clientParams, final HttpParams requestParams, final HttpParams overrideParams) {
        this.applicationParams = applicationParams;
        this.clientParams = clientParams;
        this.requestParams = requestParams;
        this.overrideParams = overrideParams;
    }
    
    public ClientParamsStack(final ClientParamsStack clientParamsStack) {
        this(clientParamsStack.getApplicationParams(), clientParamsStack.getClientParams(), clientParamsStack.getRequestParams(), clientParamsStack.getOverrideParams());
    }
    
    public ClientParamsStack(final ClientParamsStack clientParamsStack, final HttpParams httpParams, final HttpParams httpParams2, final HttpParams httpParams3, final HttpParams httpParams4) {
        this((httpParams != null) ? httpParams : clientParamsStack.getApplicationParams(), (httpParams2 != null) ? httpParams2 : clientParamsStack.getClientParams(), (httpParams3 != null) ? httpParams3 : clientParamsStack.getRequestParams(), (httpParams4 != null) ? httpParams4 : clientParamsStack.getOverrideParams());
    }
    
    public final HttpParams getApplicationParams() {
        return this.applicationParams;
    }
    
    public final HttpParams getClientParams() {
        return this.clientParams;
    }
    
    public final HttpParams getRequestParams() {
        return this.requestParams;
    }
    
    public final HttpParams getOverrideParams() {
        return this.overrideParams;
    }
    
    public Object getParameter(final String s) {
        Args.notNull(s, "Parameter name");
        Object o = null;
        if (this.overrideParams != null) {
            o = this.overrideParams.getParameter(s);
        }
        if (o == null && this.requestParams != null) {
            o = this.requestParams.getParameter(s);
        }
        if (o == null && this.clientParams != null) {
            o = this.clientParams.getParameter(s);
        }
        if (o == null && this.applicationParams != null) {
            o = this.applicationParams.getParameter(s);
        }
        return o;
    }
    
    public HttpParams setParameter(final String s, final Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Setting parameters in a stack is not supported.");
    }
    
    public boolean removeParameter(final String s) {
        throw new UnsupportedOperationException("Removing parameters in a stack is not supported.");
    }
    
    public HttpParams copy() {
        return this;
    }
}
