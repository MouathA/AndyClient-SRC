package org.apache.http.impl.cookie;

import org.apache.http.cookie.*;
import java.io.*;
import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.util.*;

@NotThreadSafe
public class BasicClientCookie implements SetCookie, ClientCookie, Cloneable, Serializable
{
    private static final long serialVersionUID = -3869795591041535538L;
    private final String name;
    private Map attribs;
    private String value;
    private String cookieComment;
    private String cookieDomain;
    private Date cookieExpiryDate;
    private String cookiePath;
    private boolean isSecure;
    private int cookieVersion;
    
    public BasicClientCookie(final String name, final String value) {
        Args.notNull(name, "Name");
        this.name = name;
        this.attribs = new HashMap();
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    public String getComment() {
        return this.cookieComment;
    }
    
    public void setComment(final String cookieComment) {
        this.cookieComment = cookieComment;
    }
    
    public String getCommentURL() {
        return null;
    }
    
    public Date getExpiryDate() {
        return this.cookieExpiryDate;
    }
    
    public void setExpiryDate(final Date cookieExpiryDate) {
        this.cookieExpiryDate = cookieExpiryDate;
    }
    
    public boolean isPersistent() {
        return null != this.cookieExpiryDate;
    }
    
    public String getDomain() {
        return this.cookieDomain;
    }
    
    public void setDomain(final String s) {
        if (s != null) {
            this.cookieDomain = s.toLowerCase(Locale.ENGLISH);
        }
        else {
            this.cookieDomain = null;
        }
    }
    
    public String getPath() {
        return this.cookiePath;
    }
    
    public void setPath(final String cookiePath) {
        this.cookiePath = cookiePath;
    }
    
    public boolean isSecure() {
        return this.isSecure;
    }
    
    public void setSecure(final boolean isSecure) {
        this.isSecure = isSecure;
    }
    
    public int[] getPorts() {
        return null;
    }
    
    public int getVersion() {
        return this.cookieVersion;
    }
    
    public void setVersion(final int cookieVersion) {
        this.cookieVersion = cookieVersion;
    }
    
    public boolean isExpired(final Date date) {
        Args.notNull(date, "Date");
        return this.cookieExpiryDate != null && this.cookieExpiryDate.getTime() <= date.getTime();
    }
    
    public void setAttribute(final String s, final String s2) {
        this.attribs.put(s, s2);
    }
    
    public String getAttribute(final String s) {
        return this.attribs.get(s);
    }
    
    public boolean containsAttribute(final String s) {
        return this.attribs.get(s) != null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final BasicClientCookie basicClientCookie = (BasicClientCookie)super.clone();
        basicClientCookie.attribs = new HashMap(this.attribs);
        return basicClientCookie;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[version: ");
        sb.append(Integer.toString(this.cookieVersion));
        sb.append("]");
        sb.append("[name: ");
        sb.append(this.name);
        sb.append("]");
        sb.append("[value: ");
        sb.append(this.value);
        sb.append("]");
        sb.append("[domain: ");
        sb.append(this.cookieDomain);
        sb.append("]");
        sb.append("[path: ");
        sb.append(this.cookiePath);
        sb.append("]");
        sb.append("[expiry: ");
        sb.append(this.cookieExpiryDate);
        sb.append("]");
        return sb.toString();
    }
}
