package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C16PacketClientStatus implements Packet
{
    private EnumState status;
    private static final String __OBFID;
    
    public C16PacketClientStatus() {
    }
    
    public C16PacketClientStatus(final EnumState status) {
        this.status = status;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.status = (EnumState)packetBuffer.readEnumValue(EnumState.class);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.status);
    }
    
    public void func_180758_a(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processClientStatus(this);
    }
    
    public EnumState getStatus() {
        return this.status;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180758_a((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001348";
    }
    
    public enum EnumState
    {
        PERFORM_RESPAWN("PERFORM_RESPAWN", 0, "PERFORM_RESPAWN", 0), 
        REQUEST_STATS("REQUEST_STATS", 1, "REQUEST_STATS", 1), 
        OPEN_INVENTORY_ACHIEVEMENT("OPEN_INVENTORY_ACHIEVEMENT", 2, "OPEN_INVENTORY_ACHIEVEMENT", 2);
        
        private static final EnumState[] $VALUES;
        private static final String __OBFID;
        private static final EnumState[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001349";
            ENUM$VALUES = new EnumState[] { EnumState.PERFORM_RESPAWN, EnumState.REQUEST_STATS, EnumState.OPEN_INVENTORY_ACHIEVEMENT };
            $VALUES = new EnumState[] { EnumState.PERFORM_RESPAWN, EnumState.REQUEST_STATS, EnumState.OPEN_INVENTORY_ACHIEVEMENT };
        }
        
        private EnumState(final String s, final int n, final String s2, final int n2) {
        }
    }
}
