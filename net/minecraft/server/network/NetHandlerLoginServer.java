package net.minecraft.server.network;

import net.minecraft.network.login.*;
import net.minecraft.server.gui.*;
import java.util.concurrent.atomic.*;
import net.minecraft.server.*;
import com.mojang.authlib.*;
import javax.crypto.*;
import org.apache.logging.log4j.*;
import net.minecraft.network.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import org.apache.commons.lang3.*;
import net.minecraft.network.login.server.*;
import net.minecraft.network.login.client.*;
import java.util.*;
import net.minecraft.util.*;
import java.math.*;
import java.security.*;
import com.google.common.base.*;

public class NetHandlerLoginServer implements INetHandlerLoginServer, IUpdatePlayerListBox
{
    private static final AtomicInteger AUTHENTICATOR_THREAD_ID;
    private static final Logger logger;
    private static final Random RANDOM;
    private final byte[] field_147330_e;
    private final MinecraftServer server;
    public final NetworkManager networkManager;
    private LoginState currentLoginState;
    private int connectionTimer;
    private GameProfile loginGameProfile;
    private String serverId;
    private SecretKey secretKey;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001458";
        AUTHENTICATOR_THREAD_ID = new AtomicInteger(0);
        logger = LogManager.getLogger();
        RANDOM = new Random();
    }
    
    public NetHandlerLoginServer(final MinecraftServer server, final NetworkManager networkManager) {
        this.field_147330_e = new byte[4];
        this.currentLoginState = LoginState.HELLO;
        this.serverId = "";
        this.server = server;
        this.networkManager = networkManager;
        NetHandlerLoginServer.RANDOM.nextBytes(this.field_147330_e);
    }
    
    @Override
    public void update() {
        if (this.currentLoginState == LoginState.READY_TO_ACCEPT) {
            this.func_147326_c();
        }
        if (this.connectionTimer++ == 600) {
            this.closeConnection("Took too long to log in");
        }
    }
    
    public void closeConnection(final String s) {
        NetHandlerLoginServer.logger.info("Disconnecting " + this.func_147317_d() + ": " + s);
        final ChatComponentText chatComponentText = new ChatComponentText(s);
        this.networkManager.sendPacket(new S00PacketDisconnect(chatComponentText));
        this.networkManager.closeChannel(chatComponentText);
    }
    
    public void func_147326_c() {
        if (!this.loginGameProfile.isComplete()) {
            this.loginGameProfile = this.getOfflineProfile(this.loginGameProfile);
        }
        final String allowUserToConnect = this.server.getConfigurationManager().allowUserToConnect(this.networkManager.getRemoteAddress(), this.loginGameProfile);
        if (allowUserToConnect != null) {
            this.closeConnection(allowUserToConnect);
        }
        else {
            this.currentLoginState = LoginState.ACCEPTED;
            if (this.server.getNetworkCompressionTreshold() >= 0 && !this.networkManager.isLocalChannel()) {
                this.networkManager.sendPacket(new S03PacketEnableCompression(this.server.getNetworkCompressionTreshold()), new ChannelFutureListener() {
                    private static final String __OBFID;
                    final NetHandlerLoginServer this$0;
                    
                    public void operationComplete(final ChannelFuture channelFuture) {
                        this.this$0.networkManager.setCompressionTreshold(NetHandlerLoginServer.access$0(this.this$0).getNetworkCompressionTreshold());
                    }
                    
                    @Override
                    public void operationComplete(final Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                    
                    static {
                        __OBFID = "CL_00001459";
                    }
                }, new GenericFutureListener[0]);
            }
            this.networkManager.sendPacket(new S02PacketLoginSuccess(this.loginGameProfile));
            this.server.getConfigurationManager().initializeConnectionToPlayer(this.networkManager, this.server.getConfigurationManager().createPlayerForUser(this.loginGameProfile));
        }
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
        NetHandlerLoginServer.logger.info(String.valueOf(this.func_147317_d()) + " lost connection: " + chatComponent.getUnformattedText());
    }
    
    public String func_147317_d() {
        return (this.loginGameProfile != null) ? (String.valueOf(this.loginGameProfile.toString()) + " (" + this.networkManager.getRemoteAddress().toString() + ")") : String.valueOf(this.networkManager.getRemoteAddress());
    }
    
    @Override
    public void processLoginStart(final C00PacketLoginStart c00PacketLoginStart) {
        Validate.validState(this.currentLoginState == LoginState.HELLO, "Unexpected hello packet", new Object[0]);
        this.loginGameProfile = c00PacketLoginStart.getProfile();
        if (this.server.isServerInOnlineMode() && !this.networkManager.isLocalChannel()) {
            this.currentLoginState = LoginState.KEY;
            this.networkManager.sendPacket(new S01PacketEncryptionRequest(this.serverId, this.server.getKeyPair().getPublic(), this.field_147330_e));
        }
        else {
            this.currentLoginState = LoginState.READY_TO_ACCEPT;
        }
    }
    
    @Override
    public void processEncryptionResponse(final C01PacketEncryptionResponse c01PacketEncryptionResponse) {
        Validate.validState(this.currentLoginState == LoginState.KEY, "Unexpected key packet", new Object[0]);
        final PrivateKey private1 = this.server.getKeyPair().getPrivate();
        if (!Arrays.equals(this.field_147330_e, c01PacketEncryptionResponse.func_149299_b(private1))) {
            throw new IllegalStateException("Invalid nonce!");
        }
        this.secretKey = c01PacketEncryptionResponse.func_149300_a(private1);
        this.currentLoginState = LoginState.AUTHENTICATING;
        this.networkManager.enableEncryption(this.secretKey);
        new Thread("User Authenticator #" + NetHandlerLoginServer.AUTHENTICATOR_THREAD_ID.incrementAndGet()) {
            private static final String __OBFID;
            final NetHandlerLoginServer this$0;
            
            @Override
            public void run() {
                final GameProfile access$1 = NetHandlerLoginServer.access$1(this.this$0);
                NetHandlerLoginServer.access$4(this.this$0, NetHandlerLoginServer.access$0(this.this$0).getMinecraftSessionService().hasJoinedServer(new GameProfile(null, access$1.getName()), new BigInteger(CryptManager.getServerIdHash(NetHandlerLoginServer.access$2(this.this$0), NetHandlerLoginServer.access$0(this.this$0).getKeyPair().getPublic(), NetHandlerLoginServer.access$3(this.this$0))).toString(16)));
                if (NetHandlerLoginServer.access$1(this.this$0) != null) {
                    NetHandlerLoginServer.access$5().info("UUID of player " + NetHandlerLoginServer.access$1(this.this$0).getName() + " is " + NetHandlerLoginServer.access$1(this.this$0).getId());
                    NetHandlerLoginServer.access$6(this.this$0, LoginState.READY_TO_ACCEPT);
                }
                else if (NetHandlerLoginServer.access$0(this.this$0).isSinglePlayer()) {
                    NetHandlerLoginServer.access$5().warn("Failed to verify username but will let them in anyway!");
                    NetHandlerLoginServer.access$4(this.this$0, this.this$0.getOfflineProfile(access$1));
                    NetHandlerLoginServer.access$6(this.this$0, LoginState.READY_TO_ACCEPT);
                }
                else {
                    this.this$0.closeConnection("Failed to verify username!");
                    NetHandlerLoginServer.access$5().error("Username '" + NetHandlerLoginServer.access$1(this.this$0).getName() + "' tried to join with an invalid session");
                }
            }
            
            static {
                __OBFID = "CL_00002268";
            }
        }.start();
    }
    
    protected GameProfile getOfflineProfile(final GameProfile gameProfile) {
        return new GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + gameProfile.getName()).getBytes(Charsets.UTF_8)), gameProfile.getName());
    }
    
    static MinecraftServer access$0(final NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.server;
    }
    
    static GameProfile access$1(final NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.loginGameProfile;
    }
    
    static String access$2(final NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.serverId;
    }
    
    static SecretKey access$3(final NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.secretKey;
    }
    
    static void access$4(final NetHandlerLoginServer netHandlerLoginServer, final GameProfile loginGameProfile) {
        netHandlerLoginServer.loginGameProfile = loginGameProfile;
    }
    
    static Logger access$5() {
        return NetHandlerLoginServer.logger;
    }
    
    static void access$6(final NetHandlerLoginServer netHandlerLoginServer, final LoginState currentLoginState) {
        netHandlerLoginServer.currentLoginState = currentLoginState;
    }
    
    enum LoginState
    {
        HELLO("HELLO", 0, "HELLO", 0), 
        KEY("KEY", 1, "KEY", 1), 
        AUTHENTICATING("AUTHENTICATING", 2, "AUTHENTICATING", 2), 
        READY_TO_ACCEPT("READY_TO_ACCEPT", 3, "READY_TO_ACCEPT", 3), 
        ACCEPTED("ACCEPTED", 4, "ACCEPTED", 4);
        
        private static final LoginState[] $VALUES;
        private static final String __OBFID;
        private static final LoginState[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001463";
            ENUM$VALUES = new LoginState[] { LoginState.HELLO, LoginState.KEY, LoginState.AUTHENTICATING, LoginState.READY_TO_ACCEPT, LoginState.ACCEPTED };
            $VALUES = new LoginState[] { LoginState.HELLO, LoginState.KEY, LoginState.AUTHENTICATING, LoginState.READY_TO_ACCEPT, LoginState.ACCEPTED };
        }
        
        private LoginState(final String s, final int n, final String s2, final int n2) {
        }
    }
}
