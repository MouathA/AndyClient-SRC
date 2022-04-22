package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C0FPacketConfirmTransaction implements Packet
{
    private int id;
    private short uid;
    private boolean accepted;
    private static final String __OBFID;
    
    public C0FPacketConfirmTransaction() {
    }
    
    public C0FPacketConfirmTransaction(final int id, final short uid, final boolean accepted) {
        this.id = id;
        this.uid = uid;
        this.accepted = accepted;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processConfirmTransaction(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.id = packetBuffer.readByte();
        this.uid = packetBuffer.readShort();
        this.accepted = (packetBuffer.readByte() != 0);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.id);
        packetBuffer.writeShort(this.uid);
        packetBuffer.writeByte(this.accepted ? 1 : 0);
    }
    
    public int getId() {
        return this.id;
    }
    
    public short getUid() {
        return this.uid;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001351";
    }
}
