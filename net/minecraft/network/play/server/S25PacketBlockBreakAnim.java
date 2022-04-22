package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S25PacketBlockBreakAnim implements Packet
{
    private int breakerId;
    private BlockPos position;
    private int progress;
    private static final String __OBFID;
    
    public S25PacketBlockBreakAnim() {
    }
    
    public S25PacketBlockBreakAnim(final int breakerId, final BlockPos position, final int progress) {
        this.breakerId = breakerId;
        this.position = position;
        this.progress = progress;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.breakerId = packetBuffer.readVarIntFromBuffer();
        this.position = packetBuffer.readBlockPos();
        this.progress = packetBuffer.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.breakerId);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.progress);
    }
    
    public void handle(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleBlockBreakAnim(this);
    }
    
    public int func_148845_c() {
        return this.breakerId;
    }
    
    public BlockPos func_179821_b() {
        return this.position;
    }
    
    public int func_148846_g() {
        return this.progress;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.handle((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001284";
    }
}
