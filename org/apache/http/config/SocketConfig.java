package org.apache.http.config;

import org.apache.http.annotation.*;
import org.apache.http.util.*;

@Immutable
public class SocketConfig implements Cloneable
{
    public static final SocketConfig DEFAULT;
    private final int soTimeout;
    private final boolean soReuseAddress;
    private final int soLinger;
    private final boolean soKeepAlive;
    private final boolean tcpNoDelay;
    
    SocketConfig(final int soTimeout, final boolean soReuseAddress, final int soLinger, final boolean soKeepAlive, final boolean tcpNoDelay) {
        this.soTimeout = soTimeout;
        this.soReuseAddress = soReuseAddress;
        this.soLinger = soLinger;
        this.soKeepAlive = soKeepAlive;
        this.tcpNoDelay = tcpNoDelay;
    }
    
    public int getSoTimeout() {
        return this.soTimeout;
    }
    
    public boolean isSoReuseAddress() {
        return this.soReuseAddress;
    }
    
    public int getSoLinger() {
        return this.soLinger;
    }
    
    public boolean isSoKeepAlive() {
        return this.soKeepAlive;
    }
    
    public boolean isTcpNoDelay() {
        return this.tcpNoDelay;
    }
    
    @Override
    protected SocketConfig clone() throws CloneNotSupportedException {
        return (SocketConfig)super.clone();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[soTimeout=").append(this.soTimeout).append(", soReuseAddress=").append(this.soReuseAddress).append(", soLinger=").append(this.soLinger).append(", soKeepAlive=").append(this.soKeepAlive).append(", tcpNoDelay=").append(this.tcpNoDelay).append("]");
        return sb.toString();
    }
    
    public static Builder custom() {
        return new Builder();
    }
    
    public static Builder copy(final SocketConfig socketConfig) {
        Args.notNull(socketConfig, "Socket config");
        return new Builder().setSoTimeout(socketConfig.getSoTimeout()).setSoReuseAddress(socketConfig.isSoReuseAddress()).setSoLinger(socketConfig.getSoLinger()).setSoKeepAlive(socketConfig.isSoKeepAlive()).setTcpNoDelay(socketConfig.isTcpNoDelay());
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static {
        DEFAULT = new Builder().build();
    }
    
    public static class Builder
    {
        private int soTimeout;
        private boolean soReuseAddress;
        private int soLinger;
        private boolean soKeepAlive;
        private boolean tcpNoDelay;
        
        Builder() {
            this.soLinger = -1;
            this.tcpNoDelay = true;
        }
        
        public Builder setSoTimeout(final int soTimeout) {
            this.soTimeout = soTimeout;
            return this;
        }
        
        public Builder setSoReuseAddress(final boolean soReuseAddress) {
            this.soReuseAddress = soReuseAddress;
            return this;
        }
        
        public Builder setSoLinger(final int soLinger) {
            this.soLinger = soLinger;
            return this;
        }
        
        public Builder setSoKeepAlive(final boolean soKeepAlive) {
            this.soKeepAlive = soKeepAlive;
            return this;
        }
        
        public Builder setTcpNoDelay(final boolean tcpNoDelay) {
            this.tcpNoDelay = tcpNoDelay;
            return this;
        }
        
        public SocketConfig build() {
            return new SocketConfig(this.soTimeout, this.soReuseAddress, this.soLinger, this.soKeepAlive, this.tcpNoDelay);
        }
    }
}
