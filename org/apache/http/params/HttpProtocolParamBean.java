package org.apache.http.params;

import org.apache.http.*;

@Deprecated
public class HttpProtocolParamBean extends HttpAbstractParamBean
{
    public HttpProtocolParamBean(final HttpParams httpParams) {
        super(httpParams);
    }
    
    public void setHttpElementCharset(final String s) {
        HttpProtocolParams.setHttpElementCharset(this.params, s);
    }
    
    public void setContentCharset(final String s) {
        HttpProtocolParams.setContentCharset(this.params, s);
    }
    
    public void setVersion(final HttpVersion httpVersion) {
        HttpProtocolParams.setVersion(this.params, httpVersion);
    }
    
    public void setUserAgent(final String s) {
        HttpProtocolParams.setUserAgent(this.params, s);
    }
    
    public void setUseExpectContinue(final boolean b) {
        HttpProtocolParams.setUseExpectContinue(this.params, b);
    }
}
