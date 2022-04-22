package com.viaversion.viaversion.classgenerator.generated;

import com.viaversion.viaversion.api.connection.*;
import io.netty.handler.codec.*;

public interface HandlerConstructor
{
    MessageToByteEncoder newEncodeHandler(final UserConnection p0, final MessageToByteEncoder p1);
    
    ByteToMessageDecoder newDecodeHandler(final UserConnection p0, final ByteToMessageDecoder p1);
}
