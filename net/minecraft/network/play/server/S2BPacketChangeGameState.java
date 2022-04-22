package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class S2BPacketChangeGameState implements Packet
{
    public static final String[] MESSAGE_NAMES;
    private int state;
    private float field_149141_c;
    private static final String __OBFID;
    private static final String[] lIllIIIlIllIIllI;
    private static String[] lIllIIIlIllIIlll;
    
    static {
        lIIIIIlIIllllIlIl();
        lIIIIIlIIllllIlII();
        __OBFID = S2BPacketChangeGameState.lIllIIIlIllIIllI[0];
        MESSAGE_NAMES = new String[] { S2BPacketChangeGameState.lIllIIIlIllIIllI[1] };
    }
    
    public S2BPacketChangeGameState() {
    }
    
    public S2BPacketChangeGameState(final int state, final float field_149141_c) {
        this.state = state;
        this.field_149141_c = field_149141_c;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.state = packetBuffer.readUnsignedByte();
        this.field_149141_c = packetBuffer.readFloat();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.state);
        packetBuffer.writeFloat(this.field_149141_c);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleChangeGameState(this);
    }
    
    public int func_149138_c() {
        return this.state;
    }
    
    public float func_149137_d() {
        return this.field_149141_c;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    private static void lIIIIIlIIllllIlII() {
        (lIllIIIlIllIIllI = new String[2])[0] = lIIIIIlIIllllIIIl(S2BPacketChangeGameState.lIllIIIlIllIIlll[0], S2BPacketChangeGameState.lIllIIIlIllIIlll[1]);
        S2BPacketChangeGameState.lIllIIIlIllIIllI[1] = lIIIIIlIIllllIIll(S2BPacketChangeGameState.lIllIIIlIllIIlll[2], S2BPacketChangeGameState.lIllIIIlIllIIlll[3]);
        S2BPacketChangeGameState.lIllIIIlIllIIlll = null;
    }
    
    private static void lIIIIIlIIllllIlIl() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        S2BPacketChangeGameState.lIllIIIlIllIIlll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIIIIIlIIllllIIIl(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIIIIlIIllllIIll(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
}
