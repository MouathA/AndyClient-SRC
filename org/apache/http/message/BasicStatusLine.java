package org.apache.http.message;

import java.io.*;
import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.util.*;

@Immutable
public class BasicStatusLine implements StatusLine, Cloneable, Serializable
{
    private static final long serialVersionUID = -2443303766890459269L;
    private final ProtocolVersion protoVersion;
    private final int statusCode;
    private final String reasonPhrase;
    
    public BasicStatusLine(final ProtocolVersion protocolVersion, final int n, final String reasonPhrase) {
        this.protoVersion = (ProtocolVersion)Args.notNull(protocolVersion, "Version");
        this.statusCode = Args.notNegative(n, "Status code");
        this.reasonPhrase = reasonPhrase;
    }
    
    public int getStatusCode() {
        return this.statusCode;
    }
    
    public ProtocolVersion getProtocolVersion() {
        return this.protoVersion;
    }
    
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
    
    @Override
    public String toString() {
        return BasicLineFormatter.INSTANCE.formatStatusLine(null, this).toString();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
