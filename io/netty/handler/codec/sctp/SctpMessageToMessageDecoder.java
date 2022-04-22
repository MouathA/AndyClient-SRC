package io.netty.handler.codec.sctp;

import io.netty.channel.sctp.*;
import io.netty.handler.codec.*;

public abstract class SctpMessageToMessageDecoder extends MessageToMessageDecoder
{
    @Override
    public boolean acceptInboundMessage(final Object o) throws Exception {
        if (!(o instanceof SctpMessage)) {
            return false;
        }
        if (((SctpMessage)o).isComplete()) {
            return true;
        }
        throw new CodecException(String.format("Received SctpMessage is not complete, please add %s in the pipeline before this handler", SctpMessageCompletionHandler.class.getSimpleName()));
    }
}
