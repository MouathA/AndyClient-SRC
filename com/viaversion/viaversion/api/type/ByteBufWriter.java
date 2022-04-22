package com.viaversion.viaversion.api.type;

import io.netty.buffer.*;

public interface ByteBufWriter
{
    void write(final ByteBuf p0, final Object p1) throws Exception;
}
