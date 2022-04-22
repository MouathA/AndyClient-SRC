package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.*;

public class BaseVersionProvider implements VersionProvider
{
    @Override
    public int getClosestServerProtocol(final UserConnection userConnection) throws Exception {
        return Via.getAPI().getServerVersion().lowestSupportedVersion();
    }
}
