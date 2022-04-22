package org.apache.http.message;

import org.apache.http.*;
import java.io.*;
import org.apache.http.annotation.*;
import org.apache.http.util.*;

@Immutable
public class BasicNameValuePair implements NameValuePair, Cloneable, Serializable
{
    private static final long serialVersionUID = -6437800749411518984L;
    private final String name;
    private final String value;
    
    public BasicNameValuePair(final String s, final String value) {
        this.name = (String)Args.notNull(s, "Name");
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        if (this.value == null) {
            return this.name;
        }
        final StringBuilder sb = new StringBuilder(this.name.length() + 1 + this.value.length());
        sb.append(this.name);
        sb.append("=");
        sb.append(this.value);
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof NameValuePair) {
            final BasicNameValuePair basicNameValuePair = (BasicNameValuePair)o;
            return this.name.equals(basicNameValuePair.name) && LangUtils.equals(this.value, basicNameValuePair.value);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        LangUtils.hashCode(17, this.name);
        LangUtils.hashCode(17, this.value);
        return 17;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
