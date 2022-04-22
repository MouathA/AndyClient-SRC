package com.viaversion.viaversion.handlers;

import io.netty.buffer.*;
import io.netty.channel.*;

public interface ViaCodecHandler
{
    void transform(final ByteBuf p0) throws Exception;
    
    void exceptionCaught(final ChannelHandlerContext p0, final Throwable p1) throws Exception;
}
