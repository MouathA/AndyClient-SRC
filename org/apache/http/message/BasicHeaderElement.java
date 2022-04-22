package org.apache.http.message;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.util.*;

@NotThreadSafe
public class BasicHeaderElement implements HeaderElement, Cloneable
{
    private final String name;
    private final String value;
    private final NameValuePair[] parameters;
    
    public BasicHeaderElement(final String s, final String value, final NameValuePair[] parameters) {
        this.name = (String)Args.notNull(s, "Name");
        this.value = value;
        if (parameters != null) {
            this.parameters = parameters;
        }
        else {
            this.parameters = new NameValuePair[0];
        }
    }
    
    public BasicHeaderElement(final String s, final String s2) {
        this(s, s2, null);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public NameValuePair[] getParameters() {
        return this.parameters.clone();
    }
    
    public int getParameterCount() {
        return this.parameters.length;
    }
    
    public NameValuePair getParameter(final int n) {
        return this.parameters[n];
    }
    
    public NameValuePair getParameterByName(final String s) {
        Args.notNull(s, "Name");
        NameValuePair nameValuePair = null;
        final NameValuePair[] parameters = this.parameters;
        while (0 < parameters.length) {
            final NameValuePair nameValuePair2 = parameters[0];
            if (nameValuePair2.getName().equalsIgnoreCase(s)) {
                nameValuePair = nameValuePair2;
                break;
            }
            int n = 0;
            ++n;
        }
        return nameValuePair;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof HeaderElement) {
            final BasicHeaderElement basicHeaderElement = (BasicHeaderElement)o;
            return this.name.equals(basicHeaderElement.name) && LangUtils.equals(this.value, basicHeaderElement.value) && LangUtils.equals(this.parameters, basicHeaderElement.parameters);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        LangUtils.hashCode(17, this.name);
        LangUtils.hashCode(17, this.value);
        final NameValuePair[] parameters = this.parameters;
        while (0 < parameters.length) {
            LangUtils.hashCode(17, parameters[0]);
            int n = 0;
            ++n;
        }
        return 17;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        if (this.value != null) {
            sb.append("=");
            sb.append(this.value);
        }
        final NameValuePair[] parameters = this.parameters;
        while (0 < parameters.length) {
            final NameValuePair nameValuePair = parameters[0];
            sb.append("; ");
            sb.append(nameValuePair);
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
