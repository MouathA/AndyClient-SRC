package org.apache.http.message;

import java.io.*;
import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.util.*;

@Immutable
public class BasicRequestLine implements RequestLine, Cloneable, Serializable
{
    private static final long serialVersionUID = 2810581718468737193L;
    private final ProtocolVersion protoversion;
    private final String method;
    private final String uri;
    
    public BasicRequestLine(final String s, final String s2, final ProtocolVersion protocolVersion) {
        this.method = (String)Args.notNull(s, "Method");
        this.uri = (String)Args.notNull(s2, "URI");
        this.protoversion = (ProtocolVersion)Args.notNull(protocolVersion, "Version");
    }
    
    public String getMethod() {
        return this.method;
    }
    
    public ProtocolVersion getProtocolVersion() {
        return this.protoversion;
    }
    
    public String getUri() {
        return this.uri;
    }
    
    @Override
    public String toString() {
        return BasicLineFormatter.INSTANCE.formatRequestLine(null, this).toString();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
