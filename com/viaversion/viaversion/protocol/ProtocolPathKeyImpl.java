package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.*;

public class ProtocolPathKeyImpl implements ProtocolPathKey
{
    private final int clientProtocolVersion;
    private final int serverProtocolVersion;
    
    public ProtocolPathKeyImpl(final int clientProtocolVersion, final int serverProtocolVersion) {
        this.clientProtocolVersion = clientProtocolVersion;
        this.serverProtocolVersion = serverProtocolVersion;
    }
    
    @Override
    public int clientProtocolVersion() {
        return this.clientProtocolVersion;
    }
    
    @Override
    public int serverProtocolVersion() {
        return this.serverProtocolVersion;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ProtocolPathKeyImpl protocolPathKeyImpl = (ProtocolPathKeyImpl)o;
        return this.clientProtocolVersion == protocolPathKeyImpl.clientProtocolVersion && this.serverProtocolVersion == protocolPathKeyImpl.serverProtocolVersion;
    }
    
    @Override
    public int hashCode() {
        return 31 * this.clientProtocolVersion + this.serverProtocolVersion;
    }
}
