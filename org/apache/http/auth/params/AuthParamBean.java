package org.apache.http.auth.params;

import org.apache.http.params.*;

@Deprecated
public class AuthParamBean extends HttpAbstractParamBean
{
    public AuthParamBean(final HttpParams httpParams) {
        super(httpParams);
    }
    
    public void setCredentialCharset(final String s) {
        AuthParams.setCredentialCharset(this.params, s);
    }
}
