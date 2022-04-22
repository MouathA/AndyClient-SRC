package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.*;

@FunctionalInterface
public interface PacketHandler
{
    void handle(final PacketWrapper p0) throws Exception;
}
