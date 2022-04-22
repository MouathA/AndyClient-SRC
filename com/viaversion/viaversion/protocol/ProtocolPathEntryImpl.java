package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.*;

public class ProtocolPathEntryImpl implements ProtocolPathEntry
{
    private final int outputProtocolVersion;
    private final Protocol protocol;
    
    public ProtocolPathEntryImpl(final int outputProtocolVersion, final Protocol protocol) {
        this.outputProtocolVersion = outputProtocolVersion;
        this.protocol = protocol;
    }
    
    @Override
    public int outputProtocolVersion() {
        return this.outputProtocolVersion;
    }
    
    @Override
    public Protocol protocol() {
        return this.protocol;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ProtocolPathEntryImpl protocolPathEntryImpl = (ProtocolPathEntryImpl)o;
        return this.outputProtocolVersion == protocolPathEntryImpl.outputProtocolVersion && this.protocol.equals(protocolPathEntryImpl.protocol);
    }
    
    @Override
    public int hashCode() {
        return 31 * this.outputProtocolVersion + this.protocol.hashCode();
    }
}
