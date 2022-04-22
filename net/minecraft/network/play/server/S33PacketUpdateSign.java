package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S33PacketUpdateSign implements Packet
{
    private World field_179706_a;
    private BlockPos field_179705_b;
    private IChatComponent[] field_149349_d;
    private static final String __OBFID;
    
    public S33PacketUpdateSign() {
    }
    
    public S33PacketUpdateSign(final World field_179706_a, final BlockPos field_179705_b, final IChatComponent[] array) {
        this.field_179706_a = field_179706_a;
        this.field_179705_b = field_179705_b;
        this.field_149349_d = new IChatComponent[] { array[0], array[1], array[2], array[3] };
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179705_b = packetBuffer.readBlockPos();
        this.field_149349_d = new IChatComponent[4];
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.field_179705_b);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUpdateSign(this);
    }
    
    public BlockPos func_179704_a() {
        return this.field_179705_b;
    }
    
    public IChatComponent[] func_180753_b() {
        return this.field_149349_d;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public IChatComponent[] getLines() {
        return this.field_149349_d;
    }
    
    static {
        __OBFID = "CL_00001338";
    }
}
