package net.minecraft.client.network;

import net.minecraft.network.login.*;
import net.minecraft.client.*;
import com.mojang.authlib.*;
import org.apache.logging.log4j.*;
import java.math.*;
import net.minecraft.network.login.client.*;
import javax.crypto.*;
import io.netty.util.concurrent.*;
import java.security.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.login.server.*;

public class NetHandlerLoginClient implements INetHandlerLoginClient
{
    private static final Logger logger;
    private final Minecraft field_147394_b;
    private final GuiScreen field_147395_c;
    private final NetworkManager field_147393_d;
    private GameProfile field_175091_e;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000876";
        logger = LogManager.getLogger();
    }
    
    public NetHandlerLoginClient(final NetworkManager field_147393_d, final Minecraft field_147394_b, final GuiScreen field_147395_c) {
        this.field_147393_d = field_147393_d;
        this.field_147394_b = field_147394_b;
        this.field_147395_c = field_147395_c;
    }
    
    @Override
    public void handleEncryptionRequest(final S01PacketEncryptionRequest s01PacketEncryptionRequest) {
        final SecretKey newSharedKey = CryptManager.createNewSharedKey();
        final String func_149609_c = s01PacketEncryptionRequest.func_149609_c();
        final PublicKey func_149608_d = s01PacketEncryptionRequest.func_149608_d();
        this.func_147391_c().joinServer(Minecraft.getSession().getProfile(), Minecraft.getSession().getToken(), new BigInteger(CryptManager.getServerIdHash(func_149609_c, func_149608_d, newSharedKey)).toString(16));
        this.field_147393_d.sendPacket(new C01PacketEncryptionResponse(newSharedKey, func_149608_d, s01PacketEncryptionRequest.func_149607_e()), new GenericFutureListener(newSharedKey) {
            private static final String __OBFID;
            final NetHandlerLoginClient this$0;
            private final SecretKey val$var2;
            
            @Override
            public void operationComplete(final Future future) {
                NetHandlerLoginClient.access$0(this.this$0).enableEncryption(this.val$var2);
            }
            
            static {
                __OBFID = "CL_00000877";
            }
        }, new GenericFutureListener[0]);
    }
    
    private MinecraftSessionService func_147391_c() {
        return this.field_147394_b.getSessionService();
    }
    
    @Override
    public void handleLoginSuccess(final S02PacketLoginSuccess s02PacketLoginSuccess) {
        this.field_175091_e = s02PacketLoginSuccess.func_179730_a();
        this.field_147393_d.setConnectionState(EnumConnectionState.PLAY);
        this.field_147393_d.setNetHandler(new NetHandlerPlayClient(this.field_147394_b, this.field_147395_c, this.field_147393_d, this.field_175091_e));
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
        this.field_147394_b.displayGuiScreen(new GuiDisconnected(this.field_147395_c, "connect.failed", chatComponent));
    }
    
    @Override
    public void handleDisconnect(final S00PacketDisconnect s00PacketDisconnect) {
        this.field_147393_d.closeChannel(s00PacketDisconnect.func_149603_c());
    }
    
    @Override
    public void func_180464_a(final S03PacketEnableCompression s03PacketEnableCompression) {
        if (!this.field_147393_d.isLocalChannel()) {
            this.field_147393_d.setCompressionTreshold(s03PacketEnableCompression.func_179731_a());
        }
    }
    
    static NetworkManager access$0(final NetHandlerLoginClient netHandlerLoginClient) {
        return netHandlerLoginClient.field_147393_d;
    }
}
