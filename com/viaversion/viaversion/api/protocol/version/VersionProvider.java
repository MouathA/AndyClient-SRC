package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.connection.*;

public interface VersionProvider extends Provider
{
    int getClosestServerProtocol(final UserConnection p0) throws Exception;
}
