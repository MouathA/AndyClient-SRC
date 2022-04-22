package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.*;
import com.viaversion.viaversion.api.*;

public abstract class ConnectionHandler
{
    public abstract int connect(final UserConnection p0, final Position p1, final int p2);
    
    public int getBlockData(final UserConnection userConnection, final Position position) {
        return ((BlockConnectionProvider)Via.getManager().getProviders().get(BlockConnectionProvider.class)).getBlockData(userConnection, position.x(), position.y(), position.z());
    }
}
