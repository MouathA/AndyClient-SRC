package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class ServerProtocolVersionSingleton implements ServerProtocolVersion
{
    private final int protocolVersion;
    
    public ServerProtocolVersionSingleton(final int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
    
    @Override
    public int lowestSupportedVersion() {
        return this.protocolVersion;
    }
    
    @Override
    public int highestSupportedVersion() {
        return this.protocolVersion;
    }
    
    @Override
    public IntSortedSet supportedVersions() {
        return IntSortedSets.singleton(this.protocolVersion);
    }
}
