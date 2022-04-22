package net.minecraft.network.login.client;

import javax.crypto.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.login.*;
import java.security.*;
import net.minecraft.network.*;

public class C01PacketEncryptionResponse implements Packet
{
    private byte[] field_149302_a;
    private byte[] field_149301_b;
    private static final String __OBFID;
    
    public C01PacketEncryptionResponse() {
        this.field_149302_a = new byte[0];
        this.field_149301_b = new byte[0];
    }
    
    public C01PacketEncryptionResponse(final SecretKey secretKey, final PublicKey publicKey, final byte[] array) {
        this.field_149302_a = new byte[0];
        this.field_149301_b = new byte[0];
        this.field_149302_a = CryptManager.encryptData(publicKey, secretKey.getEncoded());
        this.field_149301_b = CryptManager.encryptData(publicKey, array);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149302_a = packetBuffer.readByteArray();
        this.field_149301_b = packetBuffer.readByteArray();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByteArray(this.field_149302_a);
        packetBuffer.writeByteArray(this.field_149301_b);
    }
    
    public void processPacket(final INetHandlerLoginServer netHandlerLoginServer) {
        netHandlerLoginServer.processEncryptionResponse(this);
    }
    
    public SecretKey func_149300_a(final PrivateKey privateKey) {
        return CryptManager.decryptSharedKey(privateKey, this.field_149302_a);
    }
    
    public byte[] func_149299_b(final PrivateKey privateKey) {
        return (privateKey == null) ? this.field_149301_b : CryptManager.decryptData(privateKey, this.field_149301_b);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001380";
    }
}
