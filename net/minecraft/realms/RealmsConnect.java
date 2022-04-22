package net.minecraft.realms;

import org.apache.logging.log4j.*;
import java.net.*;
import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;

public class RealmsConnect
{
    private static final Logger LOGGER;
    private final RealmsScreen onlineScreen;
    private boolean aborted;
    private NetworkManager connection;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001844";
        LOGGER = LogManager.getLogger();
    }
    
    public RealmsConnect(final RealmsScreen onlineScreen) {
        this.aborted = false;
        this.onlineScreen = onlineScreen;
    }
    
    public void connect(final String s, final int n) {
        new Thread("Realms-connect-task", s, n) {
            private static final String __OBFID;
            final RealmsConnect this$0;
            private final String val$p_connect_1_;
            private final int val$p_connect_2_;
            
            @Override
            public void run() {
                final InetAddress byName = InetAddress.getByName(this.val$p_connect_1_);
                if (RealmsConnect.access$0(this.this$0)) {
                    return;
                }
                RealmsConnect.access$1(this.this$0, NetworkManager.provideLanClient(byName, this.val$p_connect_2_));
                if (RealmsConnect.access$0(this.this$0)) {
                    return;
                }
                RealmsConnect.access$2(this.this$0).setNetHandler(new NetHandlerLoginClient(RealmsConnect.access$2(this.this$0), Minecraft.getMinecraft(), RealmsConnect.access$3(this.this$0).getProxy()));
                if (RealmsConnect.access$0(this.this$0)) {
                    return;
                }
                RealmsConnect.access$2(this.this$0).sendPacket(new C00Handshake(47, this.val$p_connect_1_, this.val$p_connect_2_, EnumConnectionState.LOGIN));
                if (RealmsConnect.access$0(this.this$0)) {
                    return;
                }
                final NetworkManager access$2 = RealmsConnect.access$2(this.this$0);
                Minecraft.getMinecraft();
                access$2.sendPacket(new C00PacketLoginStart(Minecraft.getSession().getProfile()));
            }
            
            static {
                __OBFID = "CL_00001808";
            }
        }.start();
    }
    
    public void abort() {
        this.aborted = true;
    }
    
    public void tick() {
        if (this.connection != null) {
            if (this.connection.isChannelOpen()) {
                this.connection.processReceivedPackets();
            }
            else {
                this.connection.checkDisconnected();
            }
        }
    }
    
    static boolean access$0(final RealmsConnect realmsConnect) {
        return realmsConnect.aborted;
    }
    
    static void access$1(final RealmsConnect realmsConnect, final NetworkManager connection) {
        realmsConnect.connection = connection;
    }
    
    static NetworkManager access$2(final RealmsConnect realmsConnect) {
        return realmsConnect.connection;
    }
    
    static RealmsScreen access$3(final RealmsConnect realmsConnect) {
        return realmsConnect.onlineScreen;
    }
    
    static Logger access$4() {
        return RealmsConnect.LOGGER;
    }
}
