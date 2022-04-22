package io.netty.channel.udt.nio;

import com.barchart.udt.*;
import java.util.*;
import io.netty.channel.*;
import com.barchart.udt.nio.*;

public class NioUdtMessageAcceptorChannel extends NioUdtAcceptorChannel
{
    private static final ChannelMetadata METADATA;
    
    public NioUdtMessageAcceptorChannel() {
        super(TypeUDT.DATAGRAM);
    }
    
    @Override
    protected int doReadMessages(final List list) throws Exception {
        final SocketChannelUDT accept = this.javaChannel().accept();
        if (accept == null) {
            return 0;
        }
        list.add(new NioUdtMessageConnectorChannel(this, accept));
        return 1;
    }
    
    @Override
    public ChannelMetadata metadata() {
        return NioUdtMessageAcceptorChannel.METADATA;
    }
    
    static {
        METADATA = new ChannelMetadata(false);
    }
}
