package net.minecraft.network.handshake.client;

import java.io.*;
import net.minecraft.network.handshake.*;
import net.minecraft.network.*;

public class C00Handshake implements Packet
{
    private int protocolVersion;
    private String ip;
    private int port;
    private EnumConnectionState requestedState;
    private static final String __OBFID;
    
    public C00Handshake() {
    }
    
    public C00Handshake(final int protocolVersion, final String ip, final int port, final EnumConnectionState requestedState) {
        this.protocolVersion = protocolVersion;
        this.ip = ip;
        this.port = port;
        this.requestedState = requestedState;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.protocolVersion = packetBuffer.readVarIntFromBuffer();
        this.ip = packetBuffer.readStringFromBuffer(255);
        this.port = packetBuffer.readUnsignedShort();
        this.requestedState = EnumConnectionState.getById(packetBuffer.readVarIntFromBuffer());
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.protocolVersion);
        packetBuffer.writeString(this.ip);
        packetBuffer.writeShort(this.port);
        packetBuffer.writeVarIntToBuffer(this.requestedState.getId());
    }
    
    public void func_180770_a(final INetHandlerHandshakeServer netHandlerHandshakeServer) {
        netHandlerHandshakeServer.processHandshake(this);
    }
    
    public EnumConnectionState getRequestedState() {
        return this.requestedState;
    }
    
    public int getProtocolVersion() {
        return this.protocolVersion;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180770_a((INetHandlerHandshakeServer)netHandler);
    }
    
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(final String ip) {
        this.ip = ip;
    }
    
    static {
        __OBFID = "CL_00001372";
    }
}
