package org.apache.http;

import java.io.*;
import org.apache.http.annotation.*;
import org.apache.http.util.*;

@Immutable
public class ProtocolVersion implements Serializable, Cloneable
{
    private static final long serialVersionUID = 8950662842175091068L;
    protected final String protocol;
    protected final int major;
    protected final int minor;
    
    public ProtocolVersion(final String s, final int n, final int n2) {
        this.protocol = (String)Args.notNull(s, "Protocol name");
        this.major = Args.notNegative(n, "Protocol minor version");
        this.minor = Args.notNegative(n2, "Protocol minor version");
    }
    
    public final String getProtocol() {
        return this.protocol;
    }
    
    public final int getMajor() {
        return this.major;
    }
    
    public final int getMinor() {
        return this.minor;
    }
    
    public ProtocolVersion forVersion(final int n, final int n2) {
        if (n == this.major && n2 == this.minor) {
            return this;
        }
        return new ProtocolVersion(this.protocol, n, n2);
    }
    
    @Override
    public final int hashCode() {
        return this.protocol.hashCode() ^ this.major * 100000 ^ this.minor;
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProtocolVersion)) {
            return false;
        }
        final ProtocolVersion protocolVersion = (ProtocolVersion)o;
        return this.protocol.equals(protocolVersion.protocol) && this.major == protocolVersion.major && this.minor == protocolVersion.minor;
    }
    
    public int compareToVersion(final ProtocolVersion protocolVersion) {
        Args.notNull(protocolVersion, "Protocol version");
        Args.check(this.protocol.equals(protocolVersion.protocol), "Versions for different protocols cannot be compared: %s %s", this, protocolVersion);
        int n = this.getMajor() - protocolVersion.getMajor();
        if (n == 0) {
            n = this.getMinor() - protocolVersion.getMinor();
        }
        return n;
    }
    
    public final boolean greaterEquals(final ProtocolVersion protocolVersion) {
        return protocolVersion != null && this.compareToVersion(protocolVersion) >= 0;
    }
    
    public final boolean lessEquals(final ProtocolVersion protocolVersion) {
        return protocolVersion != null && this.compareToVersion(protocolVersion) <= 0;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.protocol);
        sb.append('/');
        sb.append(Integer.toString(this.major));
        sb.append('.');
        sb.append(Integer.toString(this.minor));
        return sb.toString();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
