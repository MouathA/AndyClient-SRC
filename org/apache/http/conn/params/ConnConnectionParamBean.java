package org.apache.http.conn.params;

import org.apache.http.params.*;

@Deprecated
public class ConnConnectionParamBean extends HttpAbstractParamBean
{
    public ConnConnectionParamBean(final HttpParams httpParams) {
        super(httpParams);
    }
    
    @Deprecated
    public void setMaxStatusLineGarbage(final int n) {
        this.params.setIntParameter("http.connection.max-status-line-garbage", n);
    }
}
