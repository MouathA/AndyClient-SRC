package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S28PacketEffect implements Packet
{
    private int soundType;
    private BlockPos field_179747_b;
    private int soundData;
    private boolean serverWide;
    private static final String __OBFID;
    
    public S28PacketEffect() {
    }
    
    public S28PacketEffect(final int soundType, final BlockPos field_179747_b, final int soundData, final boolean serverWide) {
        this.soundType = soundType;
        this.field_179747_b = field_179747_b;
        this.soundData = soundData;
        this.serverWide = serverWide;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.soundType = packetBuffer.readInt();
        this.field_179747_b = packetBuffer.readBlockPos();
        this.soundData = packetBuffer.readInt();
        this.serverWide = packetBuffer.readBoolean();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.soundType);
        packetBuffer.writeBlockPos(this.field_179747_b);
        packetBuffer.writeInt(this.soundData);
        packetBuffer.writeBoolean(this.serverWide);
    }
    
    public void func_180739_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEffect(this);
    }
    
    public boolean isSoundServerwide() {
        return this.serverWide;
    }
    
    public int getSoundType() {
        return this.soundType;
    }
    
    public int getSoundData() {
        return this.soundData;
    }
    
    public BlockPos func_179746_d() {
        return this.field_179747_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180739_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001307";
    }
}
