package io.netty.channel.udt.nio;

import io.netty.bootstrap.*;
import java.nio.channels.spi.*;
import io.netty.channel.*;
import com.barchart.udt.nio.*;
import com.barchart.udt.*;
import io.netty.channel.udt.*;

public final class NioUdtProvider implements ChannelFactory
{
    public static final ChannelFactory BYTE_ACCEPTOR;
    public static final ChannelFactory BYTE_CONNECTOR;
    public static final SelectorProvider BYTE_PROVIDER;
    public static final ChannelFactory BYTE_RENDEZVOUS;
    public static final ChannelFactory MESSAGE_ACCEPTOR;
    public static final ChannelFactory MESSAGE_CONNECTOR;
    public static final SelectorProvider MESSAGE_PROVIDER;
    public static final ChannelFactory MESSAGE_RENDEZVOUS;
    private final KindUDT kind;
    private final TypeUDT type;
    
    public static ChannelUDT channelUDT(final Channel channel) {
        if (channel instanceof NioUdtByteAcceptorChannel) {
            return (ChannelUDT)((NioUdtByteAcceptorChannel)channel).javaChannel();
        }
        if (channel instanceof NioUdtByteConnectorChannel) {
            return (ChannelUDT)((NioUdtByteConnectorChannel)channel).javaChannel();
        }
        if (channel instanceof NioUdtByteRendezvousChannel) {
            return (ChannelUDT)((NioUdtByteRendezvousChannel)channel).javaChannel();
        }
        if (channel instanceof NioUdtMessageAcceptorChannel) {
            return (ChannelUDT)((NioUdtMessageAcceptorChannel)channel).javaChannel();
        }
        if (channel instanceof NioUdtMessageConnectorChannel) {
            return (ChannelUDT)((NioUdtMessageConnectorChannel)channel).javaChannel();
        }
        if (channel instanceof NioUdtMessageRendezvousChannel) {
            return (ChannelUDT)((NioUdtMessageRendezvousChannel)channel).javaChannel();
        }
        return null;
    }
    
    static ServerSocketChannelUDT newAcceptorChannelUDT(final TypeUDT typeUDT) {
        return SelectorProviderUDT.from(typeUDT).openServerSocketChannel();
    }
    
    static SocketChannelUDT newConnectorChannelUDT(final TypeUDT typeUDT) {
        return SelectorProviderUDT.from(typeUDT).openSocketChannel();
    }
    
    static RendezvousChannelUDT newRendezvousChannelUDT(final TypeUDT typeUDT) {
        return SelectorProviderUDT.from(typeUDT).openRendezvousChannel();
    }
    
    public static SocketUDT socketUDT(final Channel channel) {
        final ChannelUDT channelUDT = channelUDT(channel);
        if (channelUDT == null) {
            return null;
        }
        return channelUDT.socketUDT();
    }
    
    private NioUdtProvider(final TypeUDT type, final KindUDT kind) {
        this.type = type;
        this.kind = kind;
    }
    
    public KindUDT kind() {
        return this.kind;
    }
    
    @Override
    public UdtChannel newChannel() {
        switch (this.kind) {
            case ACCEPTOR: {
                switch (this.type) {
                    case DATAGRAM: {
                        return new NioUdtMessageAcceptorChannel();
                    }
                    case STREAM: {
                        return new NioUdtByteAcceptorChannel();
                    }
                    default: {
                        throw new IllegalStateException("wrong type=" + this.type);
                    }
                }
                break;
            }
            case CONNECTOR: {
                switch (this.type) {
                    case DATAGRAM: {
                        return new NioUdtMessageConnectorChannel();
                    }
                    case STREAM: {
                        return new NioUdtByteConnectorChannel();
                    }
                    default: {
                        throw new IllegalStateException("wrong type=" + this.type);
                    }
                }
                break;
            }
            case RENDEZVOUS: {
                switch (this.type) {
                    case DATAGRAM: {
                        return new NioUdtMessageRendezvousChannel();
                    }
                    case STREAM: {
                        return new NioUdtByteRendezvousChannel();
                    }
                    default: {
                        throw new IllegalStateException("wrong type=" + this.type);
                    }
                }
                break;
            }
            default: {
                throw new IllegalStateException("wrong kind=" + this.kind);
            }
        }
    }
    
    public TypeUDT type() {
        return this.type;
    }
    
    @Override
    public Channel newChannel() {
        return this.newChannel();
    }
    
    static {
        BYTE_ACCEPTOR = new NioUdtProvider(TypeUDT.STREAM, KindUDT.ACCEPTOR);
        BYTE_CONNECTOR = new NioUdtProvider(TypeUDT.STREAM, KindUDT.CONNECTOR);
        BYTE_PROVIDER = (SelectorProvider)SelectorProviderUDT.STREAM;
        BYTE_RENDEZVOUS = new NioUdtProvider(TypeUDT.STREAM, KindUDT.RENDEZVOUS);
        MESSAGE_ACCEPTOR = new NioUdtProvider(TypeUDT.DATAGRAM, KindUDT.ACCEPTOR);
        MESSAGE_CONNECTOR = new NioUdtProvider(TypeUDT.DATAGRAM, KindUDT.CONNECTOR);
        MESSAGE_PROVIDER = (SelectorProvider)SelectorProviderUDT.DATAGRAM;
        MESSAGE_RENDEZVOUS = new NioUdtProvider(TypeUDT.DATAGRAM, KindUDT.RENDEZVOUS);
    }
}
