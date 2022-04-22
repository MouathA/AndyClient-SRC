package org.apache.http.client.utils;

import org.apache.http.annotation.*;
import java.net.*;
import org.apache.http.*;
import org.apache.http.util.*;
import java.util.*;

@Immutable
public class URIUtils
{
    @Deprecated
    public static URI createURI(final String s, final String s2, final int n, final String s3, final String s4, final String s5) throws URISyntaxException {
        final StringBuilder sb = new StringBuilder();
        if (s2 != null) {
            if (s != null) {
                sb.append(s);
                sb.append("://");
            }
            sb.append(s2);
            if (n > 0) {
                sb.append(':');
                sb.append(n);
            }
        }
        if (s3 == null || !s3.startsWith("/")) {
            sb.append('/');
        }
        if (s3 != null) {
            sb.append(s3);
        }
        if (s4 != null) {
            sb.append('?');
            sb.append(s4);
        }
        if (s5 != null) {
            sb.append('#');
            sb.append(s5);
        }
        return new URI(sb.toString());
    }
    
    public static URI rewriteURI(final URI uri, final HttpHost httpHost, final boolean b) throws URISyntaxException {
        Args.notNull(uri, "URI");
        if (uri.isOpaque()) {
            return uri;
        }
        final URIBuilder uriBuilder = new URIBuilder(uri);
        if (httpHost != null) {
            uriBuilder.setScheme(httpHost.getSchemeName());
            uriBuilder.setHost(httpHost.getHostName());
            uriBuilder.setPort(httpHost.getPort());
        }
        else {
            uriBuilder.setScheme(null);
            uriBuilder.setHost(null);
            uriBuilder.setPort(-1);
        }
        if (b) {
            uriBuilder.setFragment(null);
        }
        if (TextUtils.isEmpty(uriBuilder.getPath())) {
            uriBuilder.setPath("/");
        }
        return uriBuilder.build();
    }
    
    public static URI rewriteURI(final URI uri, final HttpHost httpHost) throws URISyntaxException {
        return rewriteURI(uri, httpHost, false);
    }
    
    public static URI rewriteURI(final URI uri) throws URISyntaxException {
        Args.notNull(uri, "URI");
        if (uri.isOpaque()) {
            return uri;
        }
        final URIBuilder uriBuilder = new URIBuilder(uri);
        if (uriBuilder.getUserInfo() != null) {
            uriBuilder.setUserInfo(null);
        }
        if (TextUtils.isEmpty(uriBuilder.getPath())) {
            uriBuilder.setPath("/");
        }
        if (uriBuilder.getHost() != null) {
            uriBuilder.setHost(uriBuilder.getHost().toLowerCase(Locale.ENGLISH));
        }
        uriBuilder.setFragment(null);
        return uriBuilder.build();
    }
    
    public static URI resolve(final URI uri, final String s) {
        return resolve(uri, URI.create(s));
    }
    
    public static URI resolve(final URI uri, final URI uri2) {
        Args.notNull(uri, "Base URI");
        Args.notNull(uri2, "Reference URI");
        URI create = uri2;
        final String string = create.toString();
        if (string.startsWith("?")) {
            return resolveReferenceStartingWithQueryString(uri, create);
        }
        final boolean b = string.length() == 0;
        if (b) {
            create = URI.create("#");
        }
        URI uri3 = uri.resolve(create);
        if (b) {
            final String string2 = uri3.toString();
            uri3 = URI.create(string2.substring(0, string2.indexOf(35)));
        }
        return normalizeSyntax(uri3);
    }
    
    private static URI resolveReferenceStartingWithQueryString(final URI uri, final URI uri2) {
        final String string = uri.toString();
        return URI.create(((string.indexOf(63) > -1) ? string.substring(0, string.indexOf(63)) : string) + uri2.toString());
    }
    
    private static URI normalizeSyntax(final URI uri) {
        if (uri.isOpaque() || uri.getAuthority() == null) {
            return uri;
        }
        Args.check(uri.isAbsolute(), "Base URI must be absolute");
        final String s = (uri.getPath() == null) ? "" : uri.getPath();
        final String[] split = s.split("/");
        final Stack<String> stack = new Stack<String>();
        final String[] array = split;
        while (0 < array.length) {
            final String s2 = array[0];
            if (s2.length() != 0) {
                if (!".".equals(s2)) {
                    if ("..".equals(s2)) {
                        if (!stack.isEmpty()) {
                            stack.pop();
                        }
                    }
                    else {
                        stack.push(s2);
                    }
                }
            }
            int n = 0;
            ++n;
        }
        final StringBuilder sb = new StringBuilder();
        final Iterator<Object> iterator = stack.iterator();
        while (iterator.hasNext()) {
            sb.append('/').append(iterator.next());
        }
        if (s.lastIndexOf(47) == s.length() - 1) {
            sb.append('/');
        }
        final URI uri2 = new URI(uri.getScheme().toLowerCase(), uri.getAuthority().toLowerCase(), sb.toString(), null, null);
        if (uri.getQuery() == null && uri.getFragment() == null) {
            return uri2;
        }
        final StringBuilder sb2 = new StringBuilder(uri2.toASCIIString());
        if (uri.getQuery() != null) {
            sb2.append('?').append(uri.getRawQuery());
        }
        if (uri.getFragment() != null) {
            sb2.append('#').append(uri.getRawFragment());
        }
        return URI.create(sb2.toString());
    }
    
    public static HttpHost extractHost(final URI uri) {
        if (uri == null) {
            return null;
        }
        HttpHost httpHost = null;
        if (uri.isAbsolute()) {
            int n = uri.getPort();
            String s = uri.getHost();
            if (s == null) {
                s = uri.getAuthority();
                if (s != null) {
                    final int index = s.indexOf(64);
                    if (index >= 0) {
                        if (s.length() > index + 1) {
                            s = s.substring(index + 1);
                        }
                        else {
                            s = null;
                        }
                    }
                    if (s != null) {
                        final int index2 = s.indexOf(58);
                        if (index2 >= 0) {
                            int n3;
                            int n2;
                            for (n2 = (n3 = index2 + 1); n3 < s.length() && Character.isDigit(s.charAt(n3)); ++n3) {
                                int n4 = 0;
                                ++n4;
                            }
                            if (0 > 0) {
                                n = Integer.parseInt(s.substring(n2, n2 + 0));
                            }
                            s = s.substring(0, index2);
                        }
                    }
                }
            }
            final String scheme = uri.getScheme();
            if (s != null) {
                httpHost = new HttpHost(s, n, scheme);
            }
        }
        return httpHost;
    }
    
    public static URI resolve(final URI uri, final HttpHost httpHost, final List list) throws URISyntaxException {
        Args.notNull(uri, "Request URI");
        URIBuilder uriBuilder;
        if (list == null || list.isEmpty()) {
            uriBuilder = new URIBuilder(uri);
        }
        else {
            uriBuilder = new URIBuilder(list.get(list.size() - 1));
            String fragment = uriBuilder.getFragment();
            for (int n = list.size() - 1; fragment == null && n >= 0; fragment = list.get(n).getFragment(), --n) {}
            uriBuilder.setFragment(fragment);
        }
        if (uriBuilder.getFragment() == null) {
            uriBuilder.setFragment(uri.getFragment());
        }
        if (httpHost != null && !uriBuilder.isAbsolute()) {
            uriBuilder.setScheme(httpHost.getSchemeName());
            uriBuilder.setHost(httpHost.getHostName());
            uriBuilder.setPort(httpHost.getPort());
        }
        return uriBuilder.build();
    }
    
    private URIUtils() {
    }
}
