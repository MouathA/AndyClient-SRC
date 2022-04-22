package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.libs.fastutil.ints.*;

public interface ServerProtocolVersion
{
    int lowestSupportedVersion();
    
    int highestSupportedVersion();
    
    IntSortedSet supportedVersions();
    
    default boolean isKnown() {
        return this.lowestSupportedVersion() != -1 && this.highestSupportedVersion() != -1;
    }
}
