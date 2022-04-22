package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.*;

@FunctionalInterface
public interface ValueWriter
{
    void write(final PacketWrapper p0, final Object p1) throws Exception;
}
