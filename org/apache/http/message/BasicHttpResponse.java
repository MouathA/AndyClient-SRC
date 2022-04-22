package org.apache.http.message;

import org.apache.http.annotation.*;
import java.util.*;
import org.apache.http.util.*;
import org.apache.http.*;

@NotThreadSafe
public class BasicHttpResponse extends AbstractHttpMessage implements HttpResponse
{
    private StatusLine statusline;
    private ProtocolVersion ver;
    private int code;
    private String reasonPhrase;
    private HttpEntity entity;
    private final ReasonPhraseCatalog reasonCatalog;
    private Locale locale;
    
    public BasicHttpResponse(final StatusLine statusLine, final ReasonPhraseCatalog reasonCatalog, final Locale locale) {
        this.statusline = (StatusLine)Args.notNull(statusLine, "Status line");
        this.ver = statusLine.getProtocolVersion();
        this.code = statusLine.getStatusCode();
        this.reasonPhrase = statusLine.getReasonPhrase();
        this.reasonCatalog = reasonCatalog;
        this.locale = locale;
    }
    
    public BasicHttpResponse(final StatusLine statusLine) {
        this.statusline = (StatusLine)Args.notNull(statusLine, "Status line");
        this.ver = statusLine.getProtocolVersion();
        this.code = statusLine.getStatusCode();
        this.reasonPhrase = statusLine.getReasonPhrase();
        this.reasonCatalog = null;
        this.locale = null;
    }
    
    public BasicHttpResponse(final ProtocolVersion ver, final int code, final String reasonPhrase) {
        Args.notNegative(code, "Status code");
        this.statusline = null;
        this.ver = ver;
        this.code = code;
        this.reasonPhrase = reasonPhrase;
        this.reasonCatalog = null;
        this.locale = null;
    }
    
    public ProtocolVersion getProtocolVersion() {
        return this.ver;
    }
    
    public StatusLine getStatusLine() {
        if (this.statusline == null) {
            this.statusline = new BasicStatusLine((this.ver != null) ? this.ver : HttpVersion.HTTP_1_1, this.code, (this.reasonPhrase != null) ? this.reasonPhrase : this.getReason(this.code));
        }
        return this.statusline;
    }
    
    public HttpEntity getEntity() {
        return this.entity;
    }
    
    public Locale getLocale() {
        return this.locale;
    }
    
    public void setStatusLine(final StatusLine statusLine) {
        this.statusline = (StatusLine)Args.notNull(statusLine, "Status line");
        this.ver = statusLine.getProtocolVersion();
        this.code = statusLine.getStatusCode();
        this.reasonPhrase = statusLine.getReasonPhrase();
    }
    
    public void setStatusLine(final ProtocolVersion ver, final int code) {
        Args.notNegative(code, "Status code");
        this.statusline = null;
        this.ver = ver;
        this.code = code;
        this.reasonPhrase = null;
    }
    
    public void setStatusLine(final ProtocolVersion ver, final int code, final String reasonPhrase) {
        Args.notNegative(code, "Status code");
        this.statusline = null;
        this.ver = ver;
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }
    
    public void setStatusCode(final int code) {
        Args.notNegative(code, "Status code");
        this.statusline = null;
        this.code = code;
        this.reasonPhrase = null;
    }
    
    public void setReasonPhrase(final String reasonPhrase) {
        this.statusline = null;
        this.reasonPhrase = reasonPhrase;
    }
    
    public void setEntity(final HttpEntity entity) {
        this.entity = entity;
    }
    
    public void setLocale(final Locale locale) {
        this.locale = (Locale)Args.notNull(locale, "Locale");
        this.statusline = null;
    }
    
    protected String getReason(final int n) {
        return (this.reasonCatalog != null) ? this.reasonCatalog.getReason(n, (this.locale != null) ? this.locale : Locale.getDefault()) : null;
    }
    
    @Override
    public String toString() {
        return this.getStatusLine() + " " + this.headergroup;
    }
}
