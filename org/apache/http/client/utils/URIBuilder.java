package org.apache.http.client.utils;

import org.apache.http.annotation.*;
import java.net.*;
import java.nio.charset.*;
import org.apache.http.conn.util.*;
import org.apache.http.*;
import org.apache.http.message.*;
import java.util.*;

@NotThreadSafe
public class URIBuilder
{
    private String scheme;
    private String encodedSchemeSpecificPart;
    private String encodedAuthority;
    private String userInfo;
    private String encodedUserInfo;
    private String host;
    private int port;
    private String path;
    private String encodedPath;
    private String encodedQuery;
    private List queryParams;
    private String query;
    private String fragment;
    private String encodedFragment;
    
    public URIBuilder() {
        this.port = -1;
    }
    
    public URIBuilder(final String s) throws URISyntaxException {
        this.digestURI(new URI(s));
    }
    
    public URIBuilder(final URI uri) {
        this.digestURI(uri);
    }
    
    private List parseQuery(final String s, final Charset charset) {
        if (s != null && s.length() > 0) {
            return URLEncodedUtils.parse(s, charset);
        }
        return null;
    }
    
    public URI build() throws URISyntaxException {
        return new URI(this.buildString());
    }
    
    private String buildString() {
        final StringBuilder sb = new StringBuilder();
        if (this.scheme != null) {
            sb.append(this.scheme).append(':');
        }
        if (this.encodedSchemeSpecificPart != null) {
            sb.append(this.encodedSchemeSpecificPart);
        }
        else {
            if (this.encodedAuthority != null) {
                sb.append("//").append(this.encodedAuthority);
            }
            else if (this.host != null) {
                sb.append("//");
                if (this.encodedUserInfo != null) {
                    sb.append(this.encodedUserInfo).append("@");
                }
                else if (this.userInfo != null) {
                    sb.append(this.encodeUserInfo(this.userInfo)).append("@");
                }
                if (InetAddressUtils.isIPv6Address(this.host)) {
                    sb.append("[").append(this.host).append("]");
                }
                else {
                    sb.append(this.host);
                }
                if (this.port >= 0) {
                    sb.append(":").append(this.port);
                }
            }
            if (this.encodedPath != null) {
                sb.append(normalizePath(this.encodedPath));
            }
            else if (this.path != null) {
                sb.append(this.encodePath(normalizePath(this.path)));
            }
            if (this.encodedQuery != null) {
                sb.append("?").append(this.encodedQuery);
            }
            else if (this.queryParams != null) {
                sb.append("?").append(this.encodeUrlForm(this.queryParams));
            }
            else if (this.query != null) {
                sb.append("?").append(this.encodeUric(this.query));
            }
        }
        if (this.encodedFragment != null) {
            sb.append("#").append(this.encodedFragment);
        }
        else if (this.fragment != null) {
            sb.append("#").append(this.encodeUric(this.fragment));
        }
        return sb.toString();
    }
    
    private void digestURI(final URI uri) {
        this.scheme = uri.getScheme();
        this.encodedSchemeSpecificPart = uri.getRawSchemeSpecificPart();
        this.encodedAuthority = uri.getRawAuthority();
        this.host = uri.getHost();
        this.port = uri.getPort();
        this.encodedUserInfo = uri.getRawUserInfo();
        this.userInfo = uri.getUserInfo();
        this.encodedPath = uri.getRawPath();
        this.path = uri.getPath();
        this.encodedQuery = uri.getRawQuery();
        this.queryParams = this.parseQuery(uri.getRawQuery(), Consts.UTF_8);
        this.encodedFragment = uri.getRawFragment();
        this.fragment = uri.getFragment();
    }
    
    private String encodeUserInfo(final String s) {
        return URLEncodedUtils.encUserInfo(s, Consts.UTF_8);
    }
    
    private String encodePath(final String s) {
        return URLEncodedUtils.encPath(s, Consts.UTF_8);
    }
    
    private String encodeUrlForm(final List list) {
        return URLEncodedUtils.format(list, Consts.UTF_8);
    }
    
    private String encodeUric(final String s) {
        return URLEncodedUtils.encUric(s, Consts.UTF_8);
    }
    
    public URIBuilder setScheme(final String scheme) {
        this.scheme = scheme;
        return this;
    }
    
    public URIBuilder setUserInfo(final String userInfo) {
        this.userInfo = userInfo;
        this.encodedSchemeSpecificPart = null;
        this.encodedAuthority = null;
        this.encodedUserInfo = null;
        return this;
    }
    
    public URIBuilder setUserInfo(final String s, final String s2) {
        return this.setUserInfo(s + ':' + s2);
    }
    
    public URIBuilder setHost(final String host) {
        this.host = host;
        this.encodedSchemeSpecificPart = null;
        this.encodedAuthority = null;
        return this;
    }
    
    public URIBuilder setPort(final int n) {
        this.port = ((n < 0) ? -1 : n);
        this.encodedSchemeSpecificPart = null;
        this.encodedAuthority = null;
        return this;
    }
    
    public URIBuilder setPath(final String path) {
        this.path = path;
        this.encodedSchemeSpecificPart = null;
        this.encodedPath = null;
        return this;
    }
    
    public URIBuilder removeQuery() {
        this.queryParams = null;
        this.query = null;
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        return this;
    }
    
    @Deprecated
    public URIBuilder setQuery(final String s) {
        this.queryParams = this.parseQuery(s, Consts.UTF_8);
        this.query = null;
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        return this;
    }
    
    public URIBuilder setParameters(final List list) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList();
        }
        else {
            this.queryParams.clear();
        }
        this.queryParams.addAll(list);
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }
    
    public URIBuilder addParameters(final List list) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList();
        }
        this.queryParams.addAll(list);
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }
    
    public URIBuilder setParameters(final NameValuePair... array) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList();
        }
        else {
            this.queryParams.clear();
        }
        while (0 < array.length) {
            this.queryParams.add(array[0]);
            int n = 0;
            ++n;
        }
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }
    
    public URIBuilder addParameter(final String s, final String s2) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList();
        }
        this.queryParams.add(new BasicNameValuePair(s, s2));
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }
    
    public URIBuilder setParameter(final String s, final String s2) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList();
        }
        if (!this.queryParams.isEmpty()) {
            final Iterator<NameValuePair> iterator = (Iterator<NameValuePair>)this.queryParams.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getName().equals(s)) {
                    iterator.remove();
                }
            }
        }
        this.queryParams.add(new BasicNameValuePair(s, s2));
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }
    
    public URIBuilder clearParameters() {
        this.queryParams = null;
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        return this;
    }
    
    public URIBuilder setCustomQuery(final String query) {
        this.query = query;
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.queryParams = null;
        return this;
    }
    
    public URIBuilder setFragment(final String fragment) {
        this.fragment = fragment;
        this.encodedFragment = null;
        return this;
    }
    
    public boolean isAbsolute() {
        return this.scheme != null;
    }
    
    public boolean isOpaque() {
        return this.path == null;
    }
    
    public String getScheme() {
        return this.scheme;
    }
    
    public String getUserInfo() {
        return this.userInfo;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public List getQueryParams() {
        if (this.queryParams != null) {
            return new ArrayList(this.queryParams);
        }
        return new ArrayList();
    }
    
    public String getFragment() {
        return this.fragment;
    }
    
    @Override
    public String toString() {
        return this.buildString();
    }
    
    private static String normalizePath(final String s) {
        String substring = s;
        if (substring == null) {
            return null;
        }
        while (0 < substring.length() && substring.charAt(0) == '/') {
            int n = 0;
            ++n;
        }
        if (0 > 1) {
            substring = substring.substring(-1);
        }
        return substring;
    }
}
