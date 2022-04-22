package org.apache.http.client.methods;

import org.apache.http.annotation.*;
import java.net.*;
import org.apache.http.client.config.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.message.*;
import org.apache.http.protocol.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.utils.*;

@NotThreadSafe
public class RequestBuilder
{
    private String method;
    private ProtocolVersion version;
    private URI uri;
    private HeaderGroup headergroup;
    private HttpEntity entity;
    private LinkedList parameters;
    private RequestConfig config;
    
    RequestBuilder(final String method) {
        this.method = method;
    }
    
    RequestBuilder() {
        this(null);
    }
    
    public static RequestBuilder create(final String s) {
        Args.notBlank(s, "HTTP method");
        return new RequestBuilder(s);
    }
    
    public static RequestBuilder get() {
        return new RequestBuilder("GET");
    }
    
    public static RequestBuilder head() {
        return new RequestBuilder("HEAD");
    }
    
    public static RequestBuilder post() {
        return new RequestBuilder("POST");
    }
    
    public static RequestBuilder put() {
        return new RequestBuilder("PUT");
    }
    
    public static RequestBuilder delete() {
        return new RequestBuilder("DELETE");
    }
    
    public static RequestBuilder trace() {
        return new RequestBuilder("TRACE");
    }
    
    public static RequestBuilder options() {
        return new RequestBuilder("OPTIONS");
    }
    
    public static RequestBuilder copy(final HttpRequest httpRequest) {
        Args.notNull(httpRequest, "HTTP request");
        return new RequestBuilder().doCopy(httpRequest);
    }
    
    private RequestBuilder doCopy(final HttpRequest httpRequest) {
        if (httpRequest == null) {
            return this;
        }
        this.method = httpRequest.getRequestLine().getMethod();
        this.version = httpRequest.getRequestLine().getProtocolVersion();
        if (httpRequest instanceof HttpUriRequest) {
            this.uri = ((HttpUriRequest)httpRequest).getURI();
        }
        else {
            this.uri = URI.create(httpRequest.getRequestLine().getMethod());
        }
        if (this.headergroup == null) {
            this.headergroup = new HeaderGroup();
        }
        this.headergroup.clear();
        this.headergroup.setHeaders(httpRequest.getAllHeaders());
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            this.entity = ((HttpEntityEnclosingRequest)httpRequest).getEntity();
        }
        else {
            this.entity = null;
        }
        if (httpRequest instanceof Configurable) {
            this.config = ((Configurable)httpRequest).getConfig();
        }
        else {
            this.config = null;
        }
        this.parameters = null;
        return this;
    }
    
    public String getMethod() {
        return this.method;
    }
    
    public ProtocolVersion getVersion() {
        return this.version;
    }
    
    public RequestBuilder setVersion(final ProtocolVersion version) {
        this.version = version;
        return this;
    }
    
    public URI getUri() {
        return this.uri;
    }
    
    public RequestBuilder setUri(final URI uri) {
        this.uri = uri;
        return this;
    }
    
    public RequestBuilder setUri(final String s) {
        this.uri = ((s != null) ? URI.create(s) : null);
        return this;
    }
    
    public Header getFirstHeader(final String s) {
        return (this.headergroup != null) ? this.headergroup.getFirstHeader(s) : null;
    }
    
    public Header getLastHeader(final String s) {
        return (this.headergroup != null) ? this.headergroup.getLastHeader(s) : null;
    }
    
    public Header[] getHeaders(final String s) {
        return (Header[])((this.headergroup != null) ? this.headergroup.getHeaders(s) : null);
    }
    
    public RequestBuilder addHeader(final Header header) {
        if (this.headergroup == null) {
            this.headergroup = new HeaderGroup();
        }
        this.headergroup.addHeader(header);
        return this;
    }
    
    public RequestBuilder addHeader(final String s, final String s2) {
        if (this.headergroup == null) {
            this.headergroup = new HeaderGroup();
        }
        this.headergroup.addHeader(new BasicHeader(s, s2));
        return this;
    }
    
    public RequestBuilder removeHeader(final Header header) {
        if (this.headergroup == null) {
            this.headergroup = new HeaderGroup();
        }
        this.headergroup.removeHeader(header);
        return this;
    }
    
    public RequestBuilder removeHeaders(final String s) {
        if (s == null || this.headergroup == null) {
            return this;
        }
        final HeaderIterator iterator = this.headergroup.iterator();
        while (iterator.hasNext()) {
            if (s.equalsIgnoreCase(iterator.nextHeader().getName())) {
                iterator.remove();
            }
        }
        return this;
    }
    
    public RequestBuilder setHeader(final Header header) {
        if (this.headergroup == null) {
            this.headergroup = new HeaderGroup();
        }
        this.headergroup.updateHeader(header);
        return this;
    }
    
    public RequestBuilder setHeader(final String s, final String s2) {
        if (this.headergroup == null) {
            this.headergroup = new HeaderGroup();
        }
        this.headergroup.updateHeader(new BasicHeader(s, s2));
        return this;
    }
    
    public HttpEntity getEntity() {
        return this.entity;
    }
    
    public RequestBuilder setEntity(final HttpEntity entity) {
        this.entity = entity;
        return this;
    }
    
    public List getParameters() {
        return (this.parameters != null) ? new ArrayList(this.parameters) : new ArrayList();
    }
    
    public RequestBuilder addParameter(final NameValuePair nameValuePair) {
        Args.notNull(nameValuePair, "Name value pair");
        if (this.parameters == null) {
            this.parameters = new LinkedList();
        }
        this.parameters.add(nameValuePair);
        return this;
    }
    
    public RequestBuilder addParameter(final String s, final String s2) {
        return this.addParameter(new BasicNameValuePair(s, s2));
    }
    
    public RequestBuilder addParameters(final NameValuePair... array) {
        while (0 < array.length) {
            this.addParameter(array[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public RequestConfig getConfig() {
        return this.config;
    }
    
    public RequestBuilder setConfig(final RequestConfig config) {
        this.config = config;
        return this;
    }
    
    public HttpUriRequest build() {
        URI build = (this.uri != null) ? this.uri : URI.create("/");
        HttpEntity entity = this.entity;
        if (this.parameters != null && !this.parameters.isEmpty()) {
            if (entity == null && ("POST".equalsIgnoreCase(this.method) || "PUT".equalsIgnoreCase(this.method))) {
                entity = new UrlEncodedFormEntity(this.parameters, HTTP.DEF_CONTENT_CHARSET);
            }
            else {
                build = new URIBuilder(build).addParameters(this.parameters).build();
            }
        }
        HttpRequestBase httpRequestBase;
        if (entity == null) {
            httpRequestBase = new InternalRequest(this.method);
        }
        else {
            final InternalEntityEclosingRequest internalEntityEclosingRequest = new InternalEntityEclosingRequest(this.method);
            internalEntityEclosingRequest.setEntity(entity);
            httpRequestBase = internalEntityEclosingRequest;
        }
        httpRequestBase.setProtocolVersion(this.version);
        httpRequestBase.setURI(build);
        if (this.headergroup != null) {
            httpRequestBase.setHeaders(this.headergroup.getAllHeaders());
        }
        httpRequestBase.setConfig(this.config);
        return httpRequestBase;
    }
    
    static class InternalEntityEclosingRequest extends HttpEntityEnclosingRequestBase
    {
        private final String method;
        
        InternalEntityEclosingRequest(final String method) {
            this.method = method;
        }
        
        @Override
        public String getMethod() {
            return this.method;
        }
    }
    
    static class InternalRequest extends HttpRequestBase
    {
        private final String method;
        
        InternalRequest(final String method) {
            this.method = method;
        }
        
        @Override
        public String getMethod() {
            return this.method;
        }
    }
}
