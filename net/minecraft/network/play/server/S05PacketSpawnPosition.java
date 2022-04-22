package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S05PacketSpawnPosition implements Packet
{
    private BlockPos field_179801_a;
    private static final String __OBFID;
    
    public S05PacketSpawnPosition() {
    }
    
    public S05PacketSpawnPosition(final BlockPos field_179801_a) {
        this.field_179801_a = field_179801_a;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179801_a = packetBuffer.readBlockPos();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.field_179801_a);
    }
    
    public void func_180752_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnPosition(this);
    }
    
    public BlockPos func_179800_a() {
        return this.field_179801_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180752_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001336";
    }
}
