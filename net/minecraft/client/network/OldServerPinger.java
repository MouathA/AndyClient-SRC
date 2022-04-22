package net.minecraft.client.network;

import org.apache.logging.log4j.*;
import net.minecraft.client.multiplayer.*;
import java.net.*;
import net.minecraft.network.status.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.*;
import com.mojang.authlib.*;
import net.minecraft.network.status.server.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.status.client.*;
import io.netty.bootstrap.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import io.netty.buffer.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import io.netty.channel.socket.nio.*;
import java.util.*;

public class OldServerPinger
{
    private static final Splitter PING_RESPONSE_SPLITTER;
    private static final Logger logger;
    private final List pingDestinations;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000892";
        PING_RESPONSE_SPLITTER = Splitter.on('\0').limit(6);
        logger = LogManager.getLogger();
    }
    
    public OldServerPinger() {
        this.pingDestinations = Collections.synchronizedList((List<Object>)Lists.newArrayList());
    }
    
    public void ping(final ServerData serverData) throws UnknownHostException {
        this.ping(serverData, null);
    }
    
    public void ping(final ServerData serverData, final Runnable runnable) throws UnknownHostException {
        final ServerAddress func_78860_a = ServerAddress.func_78860_a(serverData.serverIP);
        final NetworkManager provideLanClient = NetworkManager.provideLanClient(InetAddress.getByName(func_78860_a.getIP()), func_78860_a.getPort());
        this.pingDestinations.add(provideLanClient);
        serverData.serverMOTD = "Pinging...";
        serverData.pingToServer = -1L;
        serverData.playerList = null;
        provideLanClient.setNetHandler(new INetHandlerStatusClient(serverData, provideLanClient) {
            private boolean field_147403_d = false;
            private long field_175092_e = 0L;
            private static final String __OBFID;
            final OldServerPinger this$0;
            private final ServerData val$server;
            private final NetworkManager val$var3;
            
            @Override
            public void handleServerInfo(final S00PacketServerInfo s00PacketServerInfo) {
                final ServerStatusResponse func_149294_c = s00PacketServerInfo.func_149294_c();
                if (func_149294_c.getServerDescription() != null) {
                    this.val$server.serverMOTD = func_149294_c.getServerDescription().getFormattedText();
                }
                else {
                    this.val$server.serverMOTD = "";
                }
                if (func_149294_c.getProtocolVersionInfo() != null) {
                    this.val$server.gameVersion = func_149294_c.getProtocolVersionInfo().getName();
                    this.val$server.version = func_149294_c.getProtocolVersionInfo().getProtocol();
                }
                else {
                    this.val$server.gameVersion = "Old";
                    this.val$server.version = 0;
                }
                if (func_149294_c.getPlayerCountData() != null) {
                    this.val$server.populationInfo = new StringBuilder().append(EnumChatFormatting.GRAY).append(func_149294_c.getPlayerCountData().getOnlinePlayerCount()).append(EnumChatFormatting.DARK_GRAY).append("/").append(EnumChatFormatting.GRAY).append(func_149294_c.getPlayerCountData().getMaxPlayers()).toString();
                    if (ArrayUtils.isNotEmpty(func_149294_c.getPlayerCountData().getPlayers())) {
                        final StringBuilder sb = new StringBuilder();
                        final GameProfile[] players = func_149294_c.getPlayerCountData().getPlayers();
                        while (0 < players.length) {
                            final GameProfile gameProfile = players[0];
                            if (sb.length() > 0) {
                                sb.append("\n");
                            }
                            sb.append(gameProfile.getName());
                            int n = 0;
                            ++n;
                        }
                        if (func_149294_c.getPlayerCountData().getPlayers().length < func_149294_c.getPlayerCountData().getOnlinePlayerCount()) {
                            if (sb.length() > 0) {
                                sb.append("\n");
                            }
                            sb.append("... and ").append(func_149294_c.getPlayerCountData().getOnlinePlayerCount() - func_149294_c.getPlayerCountData().getPlayers().length).append(" more ...");
                        }
                        this.val$server.playerList = sb.toString();
                    }
                }
                else {
                    this.val$server.populationInfo = EnumChatFormatting.DARK_GRAY + "???";
                }
                if (func_149294_c.getFavicon() != null) {
                    final String favicon = func_149294_c.getFavicon();
                    if (favicon.startsWith("data:image/png;base64,")) {
                        this.val$server.setBase64EncodedIconData(favicon.substring(22));
                    }
                    else {
                        OldServerPinger.access$0().error("Invalid server icon (unknown format)");
                    }
                }
                else {
                    this.val$server.setBase64EncodedIconData(null);
                }
                this.field_175092_e = Minecraft.getSystemTime();
                this.val$var3.sendPacket(new C01PacketPing(this.field_175092_e));
                this.field_147403_d = true;
            }
            
            @Override
            public void handlePong(final S01PacketPong s01PacketPong) {
                this.val$server.pingToServer = Minecraft.getSystemTime() - this.field_175092_e;
                this.val$var3.closeChannel(new ChatComponentText("Finished"));
            }
            
            @Override
            public void onDisconnect(final IChatComponent chatComponent) {
                if (!this.field_147403_d) {
                    OldServerPinger.access$0().error("Can't ping " + this.val$server.serverIP + ": " + chatComponent.getUnformattedText());
                    this.val$server.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
                    this.val$server.populationInfo = "";
                    OldServerPinger.access$1(this.this$0, this.val$server);
                }
            }
            
            static {
                __OBFID = "CL_00000893";
            }
        });
        provideLanClient.sendPacket(new C00Handshake(47, func_78860_a.getIP(), func_78860_a.getPort(), EnumConnectionState.STATUS));
        provideLanClient.sendPacket(new C00PacketServerQuery());
    }
    
    private void tryCompatibilityPing(final ServerData serverData) {
        final ServerAddress func_78860_a = ServerAddress.func_78860_a(serverData.serverIP);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.CLIENT_NIO_EVENTLOOP.getValue())).handler(new ChannelInitializer(func_78860_a, serverData) {
            private static final String __OBFID;
            final OldServerPinger this$0;
            private final ServerAddress val$var2;
            private final ServerData val$server;
            
            @Override
            protected void initChannel(final Channel channel) {
                channel.config().setOption(ChannelOption.IP_TOS, 24);
                channel.config().setOption(ChannelOption.TCP_NODELAY, false);
                channel.pipeline().addLast(new SimpleChannelInboundHandler(this.val$var2, this.val$server) {
                    private static final String __OBFID;
                    final OldServerPinger$2 this$1;
                    private final ServerAddress val$var2;
                    private final ServerData val$server;
                    
                    @Override
                    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
                        super.channelActive(channelHandlerContext);
                        final ByteBuf buffer = Unpooled.buffer();
                        buffer.writeByte(254);
                        buffer.writeByte(1);
                        buffer.writeByte(250);
                        final char[] charArray = "MC|PingHost".toCharArray();
                        buffer.writeShort(charArray.length);
                        final char[] array = charArray;
                        int n = 0;
                        while (0 < charArray.length) {
                            buffer.writeChar(array[0]);
                            ++n;
                        }
                        buffer.writeShort(7 + 2 * this.val$var2.getIP().length());
                        buffer.writeByte(127);
                        final char[] charArray2 = this.val$var2.getIP().toCharArray();
                        buffer.writeShort(charArray2.length);
                        final char[] array2 = charArray2;
                        while (0 < charArray2.length) {
                            buffer.writeChar(array2[0]);
                            ++n;
                        }
                        buffer.writeInt(this.val$var2.getPort());
                        channelHandlerContext.channel().writeAndFlush(buffer).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
                        buffer.release();
                    }
                    
                    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) {
                        if (byteBuf.readUnsignedByte() == 255) {
                            final String[] array = (String[])Iterables.toArray(OldServerPinger.access$2().split(new String(byteBuf.readBytes(byteBuf.readShort() * 2).array(), Charsets.UTF_16BE)), String.class);
                            if ("§1".equals(array[0])) {
                                MathHelper.parseIntWithDefault(array[1], 0);
                                final String gameVersion = array[2];
                                final String serverMOTD = array[3];
                                final int intWithDefault = MathHelper.parseIntWithDefault(array[4], -1);
                                final int intWithDefault2 = MathHelper.parseIntWithDefault(array[5], -1);
                                this.val$server.version = -1;
                                this.val$server.gameVersion = gameVersion;
                                this.val$server.serverMOTD = serverMOTD;
                                this.val$server.populationInfo = new StringBuilder().append(EnumChatFormatting.GRAY).append(intWithDefault).append(EnumChatFormatting.DARK_GRAY).append("/").append(EnumChatFormatting.GRAY).append(intWithDefault2).toString();
                            }
                        }
                        channelHandlerContext.close();
                    }
                    
                    @Override
                    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) {
                        channelHandlerContext.close();
                    }
                    
                    @Override
                    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final Object o) {
                        this.channelRead0(channelHandlerContext, (ByteBuf)o);
                    }
                    
                    static {
                        __OBFID = "CL_00000895";
                    }
                });
            }
            
            static {
                __OBFID = "CL_00000894";
            }
        })).channel(NioSocketChannel.class)).connect(func_78860_a.getIP(), func_78860_a.getPort());
    }
    
    public void pingPendingNetworks() {
        final List pingDestinations = this.pingDestinations;
        // monitorenter(pingDestinations2 = this.pingDestinations)
        final Iterator<NetworkManager> iterator = (Iterator<NetworkManager>)this.pingDestinations.iterator();
        while (iterator.hasNext()) {
            final NetworkManager networkManager = iterator.next();
            if (networkManager.isChannelOpen()) {
                networkManager.processReceivedPackets();
            }
            else {
                iterator.remove();
                networkManager.checkDisconnected();
            }
        }
    }
    // monitorexit(pingDestinations2)
    
    public void clearPendingNetworks() {
        final List pingDestinations = this.pingDestinations;
        // monitorenter(pingDestinations2 = this.pingDestinations)
        final Iterator<NetworkManager> iterator = (Iterator<NetworkManager>)this.pingDestinations.iterator();
        while (iterator.hasNext()) {
            final NetworkManager networkManager = iterator.next();
            if (networkManager.isChannelOpen()) {
                iterator.remove();
                networkManager.closeChannel(new ChatComponentText("Cancelled"));
            }
        }
    }
    // monitorexit(pingDestinations2)
    
    static Logger access$0() {
        return OldServerPinger.logger;
    }
    
    static void access$1(final OldServerPinger oldServerPinger, final ServerData serverData) {
        oldServerPinger.tryCompatibilityPing(serverData);
    }
    
    static Splitter access$2() {
        return OldServerPinger.PING_RESPONSE_SPLITTER;
    }
}
