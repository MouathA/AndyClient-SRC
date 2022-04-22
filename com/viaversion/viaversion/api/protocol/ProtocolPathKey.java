package com.viaversion.viaversion.api.protocol;

public interface ProtocolPathKey
{
    int clientProtocolVersion();
    
    int serverProtocolVersion();
    
    @Deprecated
    default int getClientProtocolVersion() {
        return this.clientProtocolVersion();
    }
    
    @Deprecated
    default int getServerProtocolVersion() {
        return this.serverProtocolVersion();
    }
}
