package net.minecraft.network.play.client;

import net.minecraft.util.*;
import java.io.*;
import org.apache.commons.lang3.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C14PacketTabComplete implements Packet
{
    private String message;
    private BlockPos field_179710_b;
    private static final String __OBFID;
    
    public C14PacketTabComplete() {
    }
    
    public C14PacketTabComplete(final String s) {
        this(s, null);
    }
    
    public C14PacketTabComplete(final String message, final BlockPos field_179710_b) {
        this.message = message;
        this.field_179710_b = field_179710_b;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.message = packetBuffer.readStringFromBuffer(32767);
        if (packetBuffer.readBoolean()) {
            this.field_179710_b = packetBuffer.readBlockPos();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(StringUtils.substring(this.message, 0, 32767));
        final boolean b = this.field_179710_b != null;
        packetBuffer.writeBoolean(b);
        if (b) {
            packetBuffer.writeBlockPos(this.field_179710_b);
        }
    }
    
    public void func_180756_a(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processTabComplete(this);
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public BlockPos func_179709_b() {
        return this.field_179710_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180756_a((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001346";
    }
}
