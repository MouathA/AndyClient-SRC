package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S36PacketSignEditorOpen implements Packet
{
    private BlockPos field_179778_a;
    private static final String __OBFID;
    
    public S36PacketSignEditorOpen() {
    }
    
    public S36PacketSignEditorOpen(final BlockPos field_179778_a) {
        this.field_179778_a = field_179778_a;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSignEditorOpen(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179778_a = packetBuffer.readBlockPos();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.field_179778_a);
    }
    
    public BlockPos func_179777_a() {
        return this.field_179778_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001316";
    }
}
