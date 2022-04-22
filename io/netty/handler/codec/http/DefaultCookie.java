package io.netty.handler.codec.http;

import java.util.*;

public class DefaultCookie implements Cookie
{
    private final String name;
    private String value;
    private String domain;
    private String path;
    private String comment;
    private String commentUrl;
    private boolean discard;
    private Set ports;
    private Set unmodifiablePorts;
    private long maxAge;
    private int version;
    private boolean secure;
    private boolean httpOnly;
    
    public DefaultCookie(String trim, final String value) {
        this.ports = Collections.emptySet();
        this.unmodifiablePorts = this.ports;
        this.maxAge = Long.MIN_VALUE;
        if (trim == null) {
            throw new NullPointerException("name");
        }
        trim = trim.trim();
        if (trim.isEmpty()) {
            throw new IllegalArgumentException("empty name");
        }
        while (0 < trim.length()) {
            final char char1 = trim.charAt(0);
            if (char1 > '\u007f') {
                throw new IllegalArgumentException("name contains non-ascii character: " + trim);
            }
            switch (char1) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 32:
                case 44:
                case 59:
                case 61: {
                    throw new IllegalArgumentException("name contains one of the following prohibited characters: =,; \\t\\r\\n\\v\\f: " + trim);
                }
                default: {
                    int n = 0;
                    ++n;
                    continue;
                }
            }
        }
        if (trim.charAt(0) == '$') {
            throw new IllegalArgumentException("name starting with '$' not allowed: " + trim);
        }
        this.name = trim;
        this.setValue(value);
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(final String value) {
        if (value == null) {
            throw new NullPointerException("value");
        }
        this.value = value;
    }
    
    @Override
    public String getDomain() {
        return this.domain;
    }
    
    @Override
    public void setDomain(final String s) {
        this.domain = validateValue("domain", s);
    }
    
    @Override
    public String getPath() {
        return this.path;
    }
    
    @Override
    public void setPath(final String s) {
        this.path = validateValue("path", s);
    }
    
    @Override
    public String getComment() {
        return this.comment;
    }
    
    @Override
    public void setComment(final String s) {
        this.comment = validateValue("comment", s);
    }
    
    @Override
    public String getCommentUrl() {
        return this.commentUrl;
    }
    
    @Override
    public void setCommentUrl(final String s) {
        this.commentUrl = validateValue("commentUrl", s);
    }
    
    @Override
    public boolean isDiscard() {
        return this.discard;
    }
    
    @Override
    public void setDiscard(final boolean discard) {
        this.discard = discard;
    }
    
    @Override
    public Set getPorts() {
        if (this.unmodifiablePorts == null) {
            this.unmodifiablePorts = Collections.unmodifiableSet((Set<?>)this.ports);
        }
        return this.unmodifiablePorts;
    }
    
    @Override
    public void setPorts(final int... array) {
        if (array == null) {
            throw new NullPointerException("ports");
        }
        final int[] array2 = array.clone();
        if (array2.length == 0) {
            final Set<Object> emptySet = Collections.emptySet();
            this.ports = emptySet;
            this.unmodifiablePorts = emptySet;
        }
        else {
            final TreeSet<Integer> ports = new TreeSet<Integer>();
            final int[] array3 = array2;
            while (0 < array3.length) {
                final int n = array3[0];
                if (n <= 0 || n > 65535) {
                    throw new IllegalArgumentException("port out of range: " + n);
                }
                ports.add(n);
                int n2 = 0;
                ++n2;
            }
            this.ports = ports;
            this.unmodifiablePorts = null;
        }
    }
    
    @Override
    public void setPorts(final Iterable iterable) {
        final TreeSet<Integer> ports = new TreeSet<Integer>();
        for (final int intValue : iterable) {
            if (intValue <= 0 || intValue > 65535) {
                throw new IllegalArgumentException("port out of range: " + intValue);
            }
            ports.add(intValue);
        }
        if (ports.isEmpty()) {
            final Set<Object> emptySet = Collections.emptySet();
            this.ports = emptySet;
            this.unmodifiablePorts = emptySet;
        }
        else {
            this.ports = ports;
            this.unmodifiablePorts = null;
        }
    }
    
    @Override
    public long getMaxAge() {
        return this.maxAge;
    }
    
    @Override
    public void setMaxAge(final long maxAge) {
        this.maxAge = maxAge;
    }
    
    @Override
    public int getVersion() {
        return this.version;
    }
    
    @Override
    public void setVersion(final int version) {
        this.version = version;
    }
    
    @Override
    public boolean isSecure() {
        return this.secure;
    }
    
    @Override
    public void setSecure(final boolean secure) {
        this.secure = secure;
    }
    
    @Override
    public boolean isHttpOnly() {
        return this.httpOnly;
    }
    
    @Override
    public void setHttpOnly(final boolean httpOnly) {
        this.httpOnly = httpOnly;
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Cookie)) {
            return false;
        }
        final Cookie cookie = (Cookie)o;
        if (!this.getName().equalsIgnoreCase(cookie.getName())) {
            return false;
        }
        if (this.getPath() == null) {
            if (cookie.getPath() != null) {
                return false;
            }
        }
        else {
            if (cookie.getPath() == null) {
                return false;
            }
            if (!this.getPath().equals(cookie.getPath())) {
                return false;
            }
        }
        if (this.getDomain() == null) {
            return cookie.getDomain() == null;
        }
        return cookie.getDomain() != null && this.getDomain().equalsIgnoreCase(cookie.getDomain());
    }
    
    public int compareTo(final Cookie cookie) {
        final int compareToIgnoreCase = this.getName().compareToIgnoreCase(cookie.getName());
        if (compareToIgnoreCase != 0) {
            return compareToIgnoreCase;
        }
        if (this.getPath() == null) {
            if (cookie.getPath() != null) {
                return -1;
            }
        }
        else {
            if (cookie.getPath() == null) {
                return 1;
            }
            final int compareTo = this.getPath().compareTo(cookie.getPath());
            if (compareTo != 0) {
                return compareTo;
            }
        }
        if (this.getDomain() == null) {
            if (cookie.getDomain() != null) {
                return -1;
            }
            return 0;
        }
        else {
            if (cookie.getDomain() == null) {
                return 1;
            }
            return this.getDomain().compareToIgnoreCase(cookie.getDomain());
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getName());
        sb.append('=');
        sb.append(this.getValue());
        if (this.getDomain() != null) {
            sb.append(", domain=");
            sb.append(this.getDomain());
        }
        if (this.getPath() != null) {
            sb.append(", path=");
            sb.append(this.getPath());
        }
        if (this.getComment() != null) {
            sb.append(", comment=");
            sb.append(this.getComment());
        }
        if (this.getMaxAge() >= 0L) {
            sb.append(", maxAge=");
            sb.append(this.getMaxAge());
            sb.append('s');
        }
        if (this.isSecure()) {
            sb.append(", secure");
        }
        if (this.isHttpOnly()) {
            sb.append(", HTTPOnly");
        }
        return sb.toString();
    }
    
    private static String validateValue(final String s, String trim) {
        if (trim == null) {
            return null;
        }
        trim = trim.trim();
        if (trim.isEmpty()) {
            return null;
        }
        while (0 < trim.length()) {
            switch (trim.charAt(0)) {
                case '\n':
                case '\u000b':
                case '\f':
                case '\r':
                case ';': {
                    throw new IllegalArgumentException(s + " contains one of the following prohibited characters: " + ";\\r\\n\\f\\v (" + trim + ')');
                }
                default: {
                    int n = 0;
                    ++n;
                    continue;
                }
            }
        }
        return trim;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Cookie)o);
    }
}
