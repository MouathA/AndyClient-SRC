package org.apache.http.params;

@Deprecated
public class HttpConnectionParamBean extends HttpAbstractParamBean
{
    public HttpConnectionParamBean(final HttpParams httpParams) {
        super(httpParams);
    }
    
    public void setSoTimeout(final int n) {
        HttpConnectionParams.setSoTimeout(this.params, n);
    }
    
    public void setTcpNoDelay(final boolean b) {
        HttpConnectionParams.setTcpNoDelay(this.params, b);
    }
    
    public void setSocketBufferSize(final int n) {
        HttpConnectionParams.setSocketBufferSize(this.params, n);
    }
    
    public void setLinger(final int n) {
        HttpConnectionParams.setLinger(this.params, n);
    }
    
    public void setConnectionTimeout(final int n) {
        HttpConnectionParams.setConnectionTimeout(this.params, n);
    }
    
    public void setStaleCheckingEnabled(final boolean b) {
        HttpConnectionParams.setStaleCheckingEnabled(this.params, b);
    }
}
