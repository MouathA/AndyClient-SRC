package optifine;

import java.util.*;

public class HttpResponse
{
    private int status;
    private String statusLine;
    private Map headers;
    private byte[] body;
    
    public HttpResponse(final int status, final String statusLine, final Map headers, final byte[] body) {
        this.status = 0;
        this.statusLine = null;
        this.headers = new LinkedHashMap();
        this.body = null;
        this.status = status;
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public String getStatusLine() {
        return this.statusLine;
    }
    
    public Map getHeaders() {
        return this.headers;
    }
    
    public String getHeader(final String s) {
        return this.headers.get(s);
    }
    
    public byte[] getBody() {
        return this.body;
    }
}
