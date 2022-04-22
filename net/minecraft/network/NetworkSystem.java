package net.minecraft.network;

import net.minecraft.server.*;
import org.apache.logging.log4j.*;
import io.netty.channel.nio.*;
import com.google.common.util.concurrent.*;
import com.google.common.collect.*;
import io.netty.bootstrap.*;
import io.netty.channel.socket.nio.*;
import io.netty.handler.timeout.*;
import net.minecraft.util.*;
import net.minecraft.server.network.*;
import java.io.*;
import java.net.*;
import net.minecraft.client.network.*;
import io.netty.channel.local.*;
import io.netty.channel.*;
import java.util.*;

public class NetworkSystem
{
    private static final Logger logger;
    public static final LazyLoadBase eventLoops;
    public static final LazyLoadBase SERVER_LOCAL_EVENTLOOP;
    private final MinecraftServer mcServer;
    public boolean isAlive;
    private final List endpoints;
    private final List networkManagers;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001447";
        logger = LogManager.getLogger();
        eventLoops = new LazyLoadBase() {
            private static final String __OBFID;
            
            protected NioEventLoopGroup genericLoad() {
                return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(true).build());
            }
            
            @Override
            protected Object load() {
                return this.genericLoad();
            }
            
            static {
                __OBFID = "CL_00001448";
            }
        };
        SERVER_LOCAL_EVENTLOOP = new LazyLoadBase() {
            private static final String __OBFID;
            
            protected LocalEventLoopGroup genericLoad() {
                return new LocalEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
            }
            
            @Override
            protected Object load() {
                return this.genericLoad();
            }
            
            static {
                __OBFID = "CL_00001449";
            }
        };
    }
    
    public NetworkSystem(final MinecraftServer mcServer) {
        this.endpoints = Collections.synchronizedList((List<Object>)Lists.newArrayList());
        this.networkManagers = Collections.synchronizedList((List<Object>)Lists.newArrayList());
        this.mcServer = mcServer;
        this.isAlive = true;
    }
    
    public void addLanEndpoint(final InetAddress inetAddress, final int n) throws IOException {
        final List endpoints = this.endpoints;
        // monitorenter(endpoints2 = this.endpoints)
        this.endpoints.add(((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(NioServerSocketChannel.class)).childHandler(new ChannelInitializer() {
            private static final String __OBFID;
            final NetworkSystem this$0;
            
            @Override
            protected void initChannel(final Channel channel) {
                channel.config().setOption(ChannelOption.IP_TOS, 24);
                channel.config().setOption(ChannelOption.TCP_NODELAY, false);
                channel.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("legacy_query", new PingResponseHandler(this.this$0)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(EnumPacketDirection.SERVERBOUND)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(EnumPacketDirection.CLIENTBOUND));
                final NetworkManager networkManager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
                NetworkSystem.access$0(this.this$0).add(networkManager);
                channel.pipeline().addLast("packet_handler", networkManager);
                networkManager.setNetHandler(new NetHandlerHandshakeTCP(NetworkSystem.access$1(this.this$0), networkManager));
            }
            
            static {
                __OBFID = "CL_00001450";
            }
        }).group((EventLoopGroup)NetworkSystem.eventLoops.getValue()).localAddress(inetAddress, n)).bind().syncUninterruptibly());
    }
    // monitorexit(endpoints2)
    
    public SocketAddress addLocalEndpoint() {
        final List endpoints = this.endpoints;
        // monitorenter(endpoints2 = this.endpoints)
        final ChannelFuture syncUninterruptibly = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(LocalServerChannel.class)).childHandler(new ChannelInitializer() {
            private static final String __OBFID;
            final NetworkSystem this$0;
            
            @Override
            protected void initChannel(final Channel channel) {
                final NetworkManager networkManager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
                networkManager.setNetHandler(new NetHandlerHandshakeMemory(NetworkSystem.access$1(this.this$0), networkManager));
                NetworkSystem.access$0(this.this$0).add(networkManager);
                channel.pipeline().addLast("packet_handler", networkManager);
            }
            
            static {
                __OBFID = "CL_00001451";
            }
        }).group((EventLoopGroup)NetworkSystem.eventLoops.getValue()).localAddress(LocalAddress.ANY)).bind().syncUninterruptibly();
        this.endpoints.add(syncUninterruptibly);
        // monitorexit(endpoints2)
        return syncUninterruptibly.channel().localAddress();
    }
    
    public void terminateEndpoints() {
        this.isAlive = false;
        final Iterator<ChannelFuture> iterator = this.endpoints.iterator();
        while (iterator.hasNext()) {
            iterator.next().channel().close().sync();
        }
    }
    
    public void networkTick() {
        final List networkManagers = this.networkManagers;
        // monitorenter(networkManagers2 = this.networkManagers)
        final Iterator<NetworkManager> iterator = (Iterator<NetworkManager>)this.networkManagers.iterator();
        while (iterator.hasNext()) {
            final NetworkManager networkManager = iterator.next();
            if (!networkManager.hasNoChannel()) {
                if (!networkManager.isChannelOpen()) {
                    iterator.remove();
                    networkManager.checkDisconnected();
                }
                else {
                    networkManager.processReceivedPackets();
                }
            }
        }
    }
    // monitorexit(networkManagers2)
    
    public MinecraftServer getServer() {
        return this.mcServer;
    }
    
    static List access$0(final NetworkSystem networkSystem) {
        return networkSystem.networkManagers;
    }
    
    static MinecraftServer access$1(final NetworkSystem networkSystem) {
        return networkSystem.mcServer;
    }
}
