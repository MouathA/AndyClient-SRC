package net.minecraft.server.network;

import net.minecraft.network.handshake.*;
import net.minecraft.server.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.login.server.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer
{
    private final MinecraftServer server;
    private final NetworkManager networkManager;
    private static final String __OBFID;
    
    public NetHandlerHandshakeTCP(final MinecraftServer server, final NetworkManager networkManager) {
        this.server = server;
        this.networkManager = networkManager;
    }
    
    @Override
    public void processHandshake(final C00Handshake c00Handshake) {
        switch (SwitchEnumConnectionState.VALUES[c00Handshake.getRequestedState().ordinal()]) {
            case 1: {
                this.networkManager.setConnectionState(EnumConnectionState.LOGIN);
                if (c00Handshake.getProtocolVersion() > 47) {
                    final ChatComponentText chatComponentText = new ChatComponentText("Outdated server! I'm still on 1.8");
                    this.networkManager.sendPacket(new S00PacketDisconnect(chatComponentText));
                    this.networkManager.closeChannel(chatComponentText);
                    break;
                }
                if (c00Handshake.getProtocolVersion() < 47) {
                    final ChatComponentText chatComponentText2 = new ChatComponentText("Outdated client! Please use 1.8");
                    this.networkManager.sendPacket(new S00PacketDisconnect(chatComponentText2));
                    this.networkManager.closeChannel(chatComponentText2);
                    break;
                }
                this.networkManager.setNetHandler(new NetHandlerLoginServer(this.server, this.networkManager));
                break;
            }
            case 2: {
                this.networkManager.setConnectionState(EnumConnectionState.STATUS);
                this.networkManager.setNetHandler(new NetHandlerStatusServer(this.server, this.networkManager));
                break;
            }
            default: {
                throw new UnsupportedOperationException("Invalid intention " + c00Handshake.getRequestedState());
            }
        }
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
    }
    
    static {
        __OBFID = "CL_00001456";
    }
    
    static final class SwitchEnumConnectionState
    {
        static final int[] VALUES;
        private static final String __OBFID;
        private static final String[] llIIIllIIlIlIIl;
        private static String[] llIIIllIIlIlIlI;
        
        static {
            lIIlIIlIIIIlIIlI();
            lIIlIIlIIIIlIIIl();
            __OBFID = SwitchEnumConnectionState.llIIIllIIlIlIIl[0];
            VALUES = new int[EnumConnectionState.values().length];
            try {
                SwitchEnumConnectionState.VALUES[EnumConnectionState.LOGIN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumConnectionState.VALUES[EnumConnectionState.STATUS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
        
        private static void lIIlIIlIIIIlIIIl() {
            (llIIIllIIlIlIIl = new String[1])[0] = lIIlIIlIIIIlIIII(SwitchEnumConnectionState.llIIIllIIlIlIlI[0], SwitchEnumConnectionState.llIIIllIIlIlIlI[1]);
            SwitchEnumConnectionState.llIIIllIIlIlIlI = null;
        }
        
        private static void lIIlIIlIIIIlIIlI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumConnectionState.llIIIllIIlIlIlI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIlIIlIIIIlIIII(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                final Cipher instance = Cipher.getInstance("Blowfish");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
