package com.viaversion.viaversion.classgenerator.generated;

import com.viaversion.viaversion.api.connection.*;
import io.netty.handler.codec.*;
import com.viaversion.viaversion.bukkit.handlers.*;

public class BasicHandlerConstructor implements HandlerConstructor
{
    @Override
    public BukkitEncodeHandler newEncodeHandler(final UserConnection userConnection, final MessageToByteEncoder messageToByteEncoder) {
        return new BukkitEncodeHandler(userConnection, messageToByteEncoder);
    }
    
    @Override
    public BukkitDecodeHandler newDecodeHandler(final UserConnection userConnection, final ByteToMessageDecoder byteToMessageDecoder) {
        return new BukkitDecodeHandler(userConnection, byteToMessageDecoder);
    }
    
    @Override
    public ByteToMessageDecoder newDecodeHandler(final UserConnection userConnection, final ByteToMessageDecoder byteToMessageDecoder) {
        return this.newDecodeHandler(userConnection, byteToMessageDecoder);
    }
    
    @Override
    public MessageToByteEncoder newEncodeHandler(final UserConnection userConnection, final MessageToByteEncoder messageToByteEncoder) {
        return this.newEncodeHandler(userConnection, messageToByteEncoder);
    }
}
