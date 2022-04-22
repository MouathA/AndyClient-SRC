package net.minecraft.network.play.client;

import java.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;

public class C18PacketSpectate implements Packet
{
    private UUID field_179729_a;
    private static final String __OBFID;
    
    public C18PacketSpectate() {
    }
    
    public C18PacketSpectate(final UUID field_179729_a) {
        this.field_179729_a = field_179729_a;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179729_a = packetBuffer.readUuid();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeUuid(this.field_179729_a);
    }
    
    public void func_179728_a(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.func_175088_a(this);
    }
    
    public Entity func_179727_a(final WorldServer worldServer) {
        return worldServer.getEntityFromUuid(this.field_179729_a);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_179728_a((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00002280";
    }
}
