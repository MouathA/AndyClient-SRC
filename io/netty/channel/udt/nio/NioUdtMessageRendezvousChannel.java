package io.netty.channel.udt.nio;

import com.barchart.udt.*;
import com.barchart.udt.nio.*;

public class NioUdtMessageRendezvousChannel extends NioUdtMessageConnectorChannel
{
    public NioUdtMessageRendezvousChannel() {
        super((SocketChannelUDT)NioUdtProvider.newRendezvousChannelUDT(TypeUDT.DATAGRAM));
    }
}
