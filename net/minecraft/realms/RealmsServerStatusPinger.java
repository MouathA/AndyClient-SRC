package net.minecraft.realms;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.network.status.*;
import net.minecraft.network.status.server.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.status.client.*;
import java.net.*;
import java.util.*;

public class RealmsServerStatusPinger
{
    private static final Logger LOGGER;
    private final List connections;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001854";
        LOGGER = LogManager.getLogger();
    }
    
    public RealmsServerStatusPinger() {
        this.connections = Collections.synchronizedList((List<Object>)Lists.newArrayList());
    }
    
    public void pingServer(final String s, final RealmsServerPing realmsServerPing) throws UnknownHostException {
        if (s != null && !s.startsWith("0.0.0.0") && !s.isEmpty()) {
            final RealmsServerAddress string = RealmsServerAddress.parseString(s);
            final NetworkManager provideLanClient = NetworkManager.provideLanClient(InetAddress.getByName(string.getHost()), string.getPort());
            this.connections.add(provideLanClient);
            provideLanClient.setNetHandler(new INetHandlerStatusClient(realmsServerPing, provideLanClient, s) {
                private boolean field_154345_e = false;
                private static final String __OBFID;
                final RealmsServerStatusPinger this$0;
                private final RealmsServerPing val$p_pingServer_2_;
                private final NetworkManager val$var4;
                private final String val$p_pingServer_1_;
                
                @Override
                public void handleServerInfo(final S00PacketServerInfo s00PacketServerInfo) {
                    final ServerStatusResponse func_149294_c = s00PacketServerInfo.func_149294_c();
                    if (func_149294_c.getPlayerCountData() != null) {
                        this.val$p_pingServer_2_.nrOfPlayers = String.valueOf(func_149294_c.getPlayerCountData().getOnlinePlayerCount());
                    }
                    this.val$var4.sendPacket(new C01PacketPing(Realms.currentTimeMillis()));
                    this.field_154345_e = true;
                }
                
                @Override
                public void handlePong(final S01PacketPong s01PacketPong) {
                    this.val$var4.closeChannel(new ChatComponentText("Finished"));
                }
                
                @Override
                public void onDisconnect(final IChatComponent chatComponent) {
                    if (!this.field_154345_e) {
                        RealmsServerStatusPinger.access$0().error("Can't ping " + this.val$p_pingServer_1_ + ": " + chatComponent.getUnformattedText());
                    }
                }
                
                static {
                    __OBFID = "CL_00001807";
                }
            });
            provideLanClient.sendPacket(new C00Handshake(RealmsSharedConstants.NETWORK_PROTOCOL_VERSION, string.getHost(), string.getPort(), EnumConnectionState.STATUS));
            provideLanClient.sendPacket(new C00PacketServerQuery());
        }
    }
    
    public void tick() {
        final List connections = this.connections;
        // monitorenter(connections2 = this.connections)
        final Iterator<NetworkManager> iterator = (Iterator<NetworkManager>)this.connections.iterator();
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
    // monitorexit(connections2)
    
    public void removeAll() {
        final List connections = this.connections;
        // monitorenter(connections2 = this.connections)
        final Iterator<NetworkManager> iterator = (Iterator<NetworkManager>)this.connections.iterator();
        while (iterator.hasNext()) {
            final NetworkManager networkManager = iterator.next();
            if (networkManager.isChannelOpen()) {
                iterator.remove();
                networkManager.closeChannel(new ChatComponentText("Cancelled"));
            }
        }
    }
    // monitorexit(connections2)
    
    static Logger access$0() {
        return RealmsServerStatusPinger.LOGGER;
    }
}
