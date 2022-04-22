package net.minecraft.network;

import java.util.concurrent.locks.*;
import io.netty.util.*;
import org.apache.logging.log4j.*;
import io.netty.channel.nio.*;
import com.google.common.util.concurrent.*;
import com.google.common.collect.*;
import Mood.*;
import DTool.modules.*;
import io.netty.util.concurrent.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.*;
import net.minecraft.network.handshake.client.*;
import java.util.*;
import net.minecraft.server.gui.*;
import io.netty.channel.local.*;
import java.net.*;
import io.netty.bootstrap.*;
import io.netty.handler.timeout.*;
import io.netty.channel.*;
import io.netty.channel.socket.nio.*;
import javax.crypto.*;
import java.security.*;
import net.minecraft.util.*;

public class NetworkManager extends SimpleChannelInboundHandler
{
    private static final Logger logger;
    public static final Marker logMarkerNetwork;
    public static final Marker logMarkerPackets;
    private final ReentrantReadWriteLock field_181680_j;
    public static final AttributeKey attrKeyConnectionState;
    public static final LazyLoadBase CLIENT_NIO_EVENTLOOP;
    public static final LazyLoadBase CLIENT_LOCAL_EVENTLOOP;
    private final EnumPacketDirection direction;
    private final Queue outboundPacketsQueue;
    public Channel channel;
    private SocketAddress socketAddress;
    private INetHandler packetListener;
    private IChatComponent terminationReason;
    private boolean isEncrypted;
    private boolean disconnected;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001240";
        logger = LogManager.getLogger();
        logMarkerNetwork = MarkerManager.getMarker("NETWORK");
        logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", NetworkManager.logMarkerNetwork);
        attrKeyConnectionState = AttributeKey.valueOf("protocol");
        CLIENT_NIO_EVENTLOOP = new LazyLoadBase() {
            private static final String __OBFID;
            
            protected NioEventLoopGroup genericLoad() {
                return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
            }
            
            @Override
            protected Object load() {
                return this.genericLoad();
            }
            
            static {
                __OBFID = "CL_00001241";
            }
        };
        CLIENT_LOCAL_EVENTLOOP = new LazyLoadBase() {
            private static final String __OBFID;
            
            protected LocalEventLoopGroup genericLoad() {
                return new LocalEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
            }
            
            @Override
            protected Object load() {
                return this.genericLoad();
            }
            
            static {
                __OBFID = "CL_00001242";
            }
        };
    }
    
    public NetworkManager(final EnumPacketDirection direction) {
        this.field_181680_j = new ReentrantReadWriteLock();
        this.outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
        this.direction = direction;
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelActive(channelHandlerContext);
        this.channel = channelHandlerContext.channel();
        this.socketAddress = this.channel.remoteAddress();
        this.setConnectionState(EnumConnectionState.HANDSHAKING);
    }
    
    public void setConnectionState(final EnumConnectionState enumConnectionState) {
        this.channel.attr(NetworkManager.attrKeyConnectionState).set(enumConnectionState);
        this.channel.config().setAutoRead(true);
        NetworkManager.logger.debug("Enabled auto read");
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) {
        this.closeChannel(new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) {
        NetworkManager.logger.debug("Disconnecting " + this.getRemoteAddress(), t);
        this.closeChannel(new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Internal Exception: " + t }));
    }
    
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final Packet packet) {
        if (this.channel.isOpen()) {
            packet.processPacket(this.packetListener);
        }
    }
    
    public void setNetHandler(final INetHandler packetListener) {
        Validate.notNull(packetListener, "packetListener", new Object[0]);
        NetworkManager.logger.debug("Set listener of {} to {}", this, packetListener);
        this.packetListener = packetListener;
    }
    
    public void sendPacket(Packet onClientSendPacket) {
        for (final Module module : Client.modules) {
            if (!module.toggled) {
                continue;
            }
            onClientSendPacket = module.onClientSendPacket(onClientSendPacket);
        }
        if (onClientSendPacket == null) {
            return;
        }
        if (this.isChannelOpen()) {
            this.flushOutboundQueue();
            this.dispatchPacket(onClientSendPacket, null);
        }
        else {
            this.field_181680_j.writeLock().lock();
            this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(onClientSendPacket, (GenericFutureListener[])null));
            this.field_181680_j.writeLock().unlock();
        }
    }
    
    public void sendPacket(final Packet packet, final GenericFutureListener genericFutureListener, final GenericFutureListener... array) {
        if (this.channel != null && this.channel.isOpen()) {
            this.flushOutboundQueue();
            this.dispatchPacket(packet, (GenericFutureListener[])ArrayUtils.add(array, 0, genericFutureListener));
        }
        else {
            this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packet, (GenericFutureListener[])ArrayUtils.add(array, 0, genericFutureListener)));
        }
    }
    
    private void dispatchPacket(final Packet packet, final GenericFutureListener[] array) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final EnumConnectionState fromPacket = EnumConnectionState.getFromPacket(packet);
        final EnumConnectionState enumConnectionState = (EnumConnectionState)this.channel.attr(NetworkManager.attrKeyConnectionState).get();
        if (packet instanceof C00Handshake) {
            if (Minecraft.BungeeHack && !Minecraft.SessionPremium && !Minecraft.PremiumUUID && ((C00Handshake)packet).getRequestedState() == EnumConnectionState.LOGIN) {
                ((C00Handshake)packet).setIp(String.valueOf(String.valueOf(String.valueOf(((C00Handshake)packet).getIp()))) + "\u0000" + Minecraft.IpBungeeHack + "\u0000" + UUID.nameUUIDFromBytes(("OfflinePlayer:" + minecraft.getFakeNick()).getBytes()).toString().replace("-", ""));
                System.out.println(((C00Handshake)packet).getIp());
                System.out.println(minecraft.getFakeNick());
            }
            if (Minecraft.BungeeHack && !Minecraft.SessionPremium && Minecraft.PremiumUUID && ((C00Handshake)packet).getRequestedState() == EnumConnectionState.LOGIN) {
                ((C00Handshake)packet).setIp(String.valueOf(String.valueOf(String.valueOf(((C00Handshake)packet).getIp()))) + "\u0000" + Minecraft.IpBungeeHack + "\u0000" + Minecraft.PreUUID);
                System.out.println(((C00Handshake)packet).getIp());
                System.out.println(Minecraft.getSession().getUsername());
            }
            if (Minecraft.BungeeHack && Minecraft.SessionPremium && ((C00Handshake)packet).getRequestedState() == EnumConnectionState.LOGIN) {
                ((C00Handshake)packet).setIp(String.valueOf(String.valueOf(String.valueOf(((C00Handshake)packet).getIp()))) + "\u0000" + Minecraft.IpBungeeHack + Minecraft.PreUUID);
                System.out.println(((C00Handshake)packet).getIp());
                System.out.println(Minecraft.getSession().getUsername());
            }
        }
        if (enumConnectionState != fromPacket) {
            NetworkManager.logger.debug("Disabled auto read");
            this.channel.config().setAutoRead(false);
        }
        if (this.channel.eventLoop().inEventLoop()) {
            if (fromPacket != enumConnectionState) {
                this.setConnectionState(fromPacket);
            }
            final ChannelFuture writeAndFlush = this.channel.writeAndFlush(packet);
            if (array != null) {
                writeAndFlush.addListeners(array);
            }
            writeAndFlush.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
        else {
            this.channel.eventLoop().execute(new Runnable(fromPacket, enumConnectionState, packet, array) {
                final NetworkManager this$0;
                private final EnumConnectionState val$enumconnectionstate;
                private final EnumConnectionState val$enumconnectionstate1;
                private final Packet val$inPacket;
                private final GenericFutureListener[] val$futureListeners;
                
                @Override
                public void run() {
                    if (this.val$enumconnectionstate != this.val$enumconnectionstate1) {
                        this.this$0.setConnectionState(this.val$enumconnectionstate);
                    }
                    final ChannelFuture writeAndFlush = this.this$0.channel.writeAndFlush(this.val$inPacket);
                    if (this.val$futureListeners != null) {
                        writeAndFlush.addListeners(this.val$futureListeners);
                    }
                    writeAndFlush.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                }
            });
        }
    }
    
    private void BungeeHack(final Packet packet, final GenericFutureListener[] array) {
        final Client instance = Client.INSTANCE;
        if (Client.getModuleByName("BungeeHack").toggled) {
            final Minecraft minecraft = Minecraft.getMinecraft();
            final EnumConnectionState fromPacket = EnumConnectionState.getFromPacket(packet);
            final EnumConnectionState enumConnectionState = (EnumConnectionState)this.channel.attr(NetworkManager.attrKeyConnectionState).get();
            if (packet instanceof C00Handshake) {
                if (!Client.getModuleByName("BungeeHack").isEnable() && ((C00Handshake)packet).getRequestedState() == EnumConnectionState.LOGIN) {
                    ((C00Handshake)packet).setIp(String.valueOf(String.valueOf(String.valueOf(((C00Handshake)packet).getIp()))) + "\u0000" + Minecraft.IpBungeeHack + "\u0000" + UUID.nameUUIDFromBytes(("OfflinePlayer:" + minecraft.getFakeNick()).getBytes()).toString().replace("-", ""));
                    System.out.println(((C00Handshake)packet).getIp());
                    System.out.println(minecraft.getFakeNick());
                }
                if (!Client.getModuleByName("BungeeHack").isEnable() && ((C00Handshake)packet).getRequestedState() == EnumConnectionState.LOGIN) {
                    ((C00Handshake)packet).setIp(String.valueOf(String.valueOf(String.valueOf(((C00Handshake)packet).getIp()))) + "\u0000" + Minecraft.IpBungeeHack + "\u0000" + Minecraft.PreUUID);
                    System.out.println(((C00Handshake)packet).getIp());
                    System.out.println(Minecraft.getSession().getUsername());
                }
                if (!Client.getModuleByName("BungeeHack").isEnable() && ((C00Handshake)packet).getRequestedState() == EnumConnectionState.LOGIN) {
                    ((C00Handshake)packet).setIp(String.valueOf(String.valueOf(String.valueOf(((C00Handshake)packet).getIp()))) + "\u0000" + Minecraft.IpBungeeHack + Minecraft.PreUUID);
                    System.out.println(((C00Handshake)packet).getIp());
                    System.out.println(Minecraft.getSession().getUsername());
                }
            }
            if (enumConnectionState != fromPacket) {
                NetworkManager.logger.debug("Disabled auto read");
                this.channel.config().setAutoRead(false);
            }
            if (this.channel.eventLoop().inEventLoop()) {
                if (fromPacket != enumConnectionState) {
                    this.setConnectionState(fromPacket);
                }
                final ChannelFuture writeAndFlush = this.channel.writeAndFlush(packet);
                if (array != null) {
                    writeAndFlush.addListeners(array);
                }
                writeAndFlush.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            }
            else {
                this.channel.eventLoop().execute(new Runnable(fromPacket, enumConnectionState, packet, array) {
                    final NetworkManager this$0;
                    private final EnumConnectionState val$enumconnectionstate;
                    private final EnumConnectionState val$enumconnectionstate1;
                    private final Packet val$inPacket;
                    private final GenericFutureListener[] val$futureListeners;
                    
                    @Override
                    public void run() {
                        if (this.val$enumconnectionstate != this.val$enumconnectionstate1) {
                            this.this$0.setConnectionState(this.val$enumconnectionstate);
                        }
                        final ChannelFuture writeAndFlush = this.this$0.channel.writeAndFlush(this.val$inPacket);
                        if (this.val$futureListeners != null) {
                            writeAndFlush.addListeners(this.val$futureListeners);
                        }
                        writeAndFlush.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                    }
                });
            }
        }
    }
    
    private void flushOutboundQueue() {
        if (this.channel != null && this.channel.isOpen()) {
            while (!this.outboundPacketsQueue.isEmpty()) {
                final InboundHandlerTuplePacketListener inboundHandlerTuplePacketListener = this.outboundPacketsQueue.poll();
                this.dispatchPacket(InboundHandlerTuplePacketListener.access$0(inboundHandlerTuplePacketListener), InboundHandlerTuplePacketListener.access$1(inboundHandlerTuplePacketListener));
            }
        }
    }
    
    public void processReceivedPackets() {
        this.flushOutboundQueue();
        if (this.packetListener instanceof IUpdatePlayerListBox) {
            ((IUpdatePlayerListBox)this.packetListener).update();
        }
        this.channel.flush();
    }
    
    public SocketAddress getRemoteAddress() {
        return this.socketAddress;
    }
    
    public void closeChannel(final IChatComponent terminationReason) {
        if (this.channel.isOpen()) {
            this.channel.close().awaitUninterruptibly();
            this.terminationReason = terminationReason;
        }
    }
    
    public boolean isLocalChannel() {
        return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
    }
    
    public static NetworkManager provideLanClient(final InetAddress inetAddress, final int n) {
        final NetworkManager networkManager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.CLIENT_NIO_EVENTLOOP.getValue())).handler(new ChannelInitializer() {
            private static final String __OBFID;
            private final NetworkManager val$var2;
            
            @Override
            protected void initChannel(final Channel channel) {
                channel.config().setOption(ChannelOption.IP_TOS, 24);
                channel.config().setOption(ChannelOption.TCP_NODELAY, false);
                channel.pipeline().addLast("timeout", new ReadTimeoutHandler(20)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(EnumPacketDirection.CLIENTBOUND)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(EnumPacketDirection.SERVERBOUND)).addLast("packet_handler", this.val$var2);
            }
            
            static {
                __OBFID = "CL_00002312";
            }
        })).channel(NioSocketChannel.class)).connect(inetAddress, n).syncUninterruptibly();
        return networkManager;
    }
    
    public static NetworkManager provideLocalClient(final SocketAddress socketAddress) {
        final NetworkManager networkManager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.CLIENT_LOCAL_EVENTLOOP.getValue())).handler(new ChannelInitializer() {
            private static final String __OBFID;
            private final NetworkManager val$var1;
            
            @Override
            protected void initChannel(final Channel channel) {
                channel.pipeline().addLast("packet_handler", this.val$var1);
            }
            
            static {
                __OBFID = "CL_00002311";
            }
        })).channel(LocalChannel.class)).connect(socketAddress).syncUninterruptibly();
        return networkManager;
    }
    
    public void enableEncryption(final SecretKey secretKey) {
        this.isEncrypted = true;
        this.channel.pipeline().addBefore("splitter", "decrypt", new NettyEncryptingDecoder(CryptManager.func_151229_a(2, secretKey)));
        this.channel.pipeline().addBefore("prepender", "encrypt", new NettyEncryptingEncoder(CryptManager.func_151229_a(1, secretKey)));
    }
    
    public boolean func_179292_f() {
        return this.isEncrypted;
    }
    
    public boolean isChannelOpen() {
        return this.channel != null && this.channel.isOpen();
    }
    
    public boolean hasNoChannel() {
        return this.channel == null;
    }
    
    public INetHandler getNetHandler() {
        return this.packetListener;
    }
    
    public IChatComponent getExitMessage() {
        return this.terminationReason;
    }
    
    public void disableAutoRead() {
        this.channel.config().setAutoRead(false);
    }
    
    public void setCompressionTreshold(final int n) {
        if (n >= 0) {
            if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                ((NettyCompressionDecoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(n);
            }
            else {
                this.channel.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(n));
            }
            if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                ((NettyCompressionEncoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(n);
            }
            else {
                this.channel.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(n));
            }
        }
        else {
            if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                this.channel.pipeline().remove("decompress");
            }
            if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                this.channel.pipeline().remove("compress");
            }
        }
    }
    
    public void checkDisconnected() {
        if (!this.hasNoChannel() && !this.isChannelOpen() && !this.disconnected) {
            this.disconnected = true;
            if (this.getExitMessage() != null) {
                this.getNetHandler().onDisconnect(this.getExitMessage());
            }
            else if (this.getNetHandler() != null) {
                this.getNetHandler().onDisconnect(new ChatComponentText("Disconnected"));
            }
        }
    }
    
    @Override
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final Object o) {
        this.channelRead0(channelHandlerContext, (Packet)o);
    }
    
    static class InboundHandlerTuplePacketListener
    {
        private final Packet packet;
        private final GenericFutureListener[] futureListeners;
        private static final String __OBFID;
        
        public InboundHandlerTuplePacketListener(final Packet packet, final GenericFutureListener... futureListeners) {
            this.packet = packet;
            this.futureListeners = futureListeners;
        }
        
        static Packet access$0(final InboundHandlerTuplePacketListener inboundHandlerTuplePacketListener) {
            return inboundHandlerTuplePacketListener.packet;
        }
        
        static GenericFutureListener[] access$1(final InboundHandlerTuplePacketListener inboundHandlerTuplePacketListener) {
            return inboundHandlerTuplePacketListener.futureListeners;
        }
        
        static {
            __OBFID = "CL_00001244";
        }
    }
}
