package net.minecraft.network.play.client;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C12PacketUpdateSign implements Packet
{
    private BlockPos field_179723_a;
    private IChatComponent[] lines;
    private static final String __OBFID;
    
    public C12PacketUpdateSign() {
    }
    
    public C12PacketUpdateSign(final BlockPos field_179723_a, final IChatComponent[] array) {
        this.field_179723_a = field_179723_a;
        this.lines = new IChatComponent[] { array[0], array[1], array[2], array[3] };
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179723_a = packetBuffer.readBlockPos();
        this.lines = new IChatComponent[4];
        while (0 < 4) {
            this.lines[0] = packetBuffer.readChatComponent();
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.field_179723_a);
        while (0 < 4) {
            packetBuffer.writeChatComponent(this.lines[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processUpdateSign(this);
    }
    
    public BlockPos func_179722_a() {
        return this.field_179723_a;
    }
    
    public IChatComponent[] func_180768_b() {
        return this.lines;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public IChatComponent[] getLines() {
        return this.lines;
    }
    
    static {
        __OBFID = "CL_00001370";
    }
}
