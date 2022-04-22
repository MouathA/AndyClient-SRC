package org.apache.http.message;

import java.io.*;
import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;

@Immutable
public class BasicHeader implements Header, Cloneable, Serializable
{
    private static final long serialVersionUID = -5427236326487562174L;
    private final String name;
    private final String value;
    
    public BasicHeader(final String s, final String value) {
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
        return BasicLineFormatter.INSTANCE.formatHeader(null, this).toString();
    }
    
    public HeaderElement[] getElements() throws ParseException {
        if (this.value != null) {
            return BasicHeaderValueParser.parseElements(this.value, null);
        }
        return new HeaderElement[0];
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
