package net.minecraft.network.login.server;

import java.security.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;

public class S01PacketEncryptionRequest implements Packet
{
    private String hashedServerId;
    private PublicKey publicKey;
    private byte[] field_149611_c;
    private static final String __OBFID;
    
    public S01PacketEncryptionRequest() {
    }
    
    public S01PacketEncryptionRequest(final String hashedServerId, final PublicKey publicKey, final byte[] field_149611_c) {
        this.hashedServerId = hashedServerId;
        this.publicKey = publicKey;
        this.field_149611_c = field_149611_c;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.hashedServerId = packetBuffer.readStringFromBuffer(20);
        this.publicKey = CryptManager.decodePublicKey(packetBuffer.readByteArray());
        this.field_149611_c = packetBuffer.readByteArray();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.hashedServerId);
        packetBuffer.writeByteArray(this.publicKey.getEncoded());
        packetBuffer.writeByteArray(this.field_149611_c);
    }
    
    public void processPacket(final INetHandlerLoginClient netHandlerLoginClient) {
        netHandlerLoginClient.handleEncryptionRequest(this);
    }
    
    public String func_149609_c() {
        return this.hashedServerId;
    }
    
    public PublicKey func_149608_d() {
        return this.publicKey;
    }
    
    public byte[] func_149607_e() {
        return this.field_149611_c;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001376";
    }
}
