package net.minecraft.network.play.client;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C07PacketPlayerDigging implements Packet
{
    private BlockPos field_179717_a;
    private EnumFacing field_179716_b;
    private Action status;
    private static final String __OBFID;
    
    public C07PacketPlayerDigging() {
    }
    
    public C07PacketPlayerDigging(final Action status, final BlockPos field_179717_a, final EnumFacing field_179716_b) {
        this.status = status;
        this.field_179717_a = field_179717_a;
        this.field_179716_b = field_179716_b;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.status = (Action)packetBuffer.readEnumValue(Action.class);
        this.field_179717_a = packetBuffer.readBlockPos();
        this.field_179716_b = EnumFacing.getFront(packetBuffer.readUnsignedByte());
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.status);
        packetBuffer.writeBlockPos(this.field_179717_a);
        packetBuffer.writeByte(this.field_179716_b.getIndex());
    }
    
    public void func_180763_a(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processPlayerDigging(this);
    }
    
    public BlockPos func_179715_a() {
        return this.field_179717_a;
    }
    
    public EnumFacing func_179714_b() {
        return this.field_179716_b;
    }
    
    public Action func_180762_c() {
        return this.status;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180763_a((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001365";
    }
    
    public enum Action
    {
        START_DESTROY_BLOCK("START_DESTROY_BLOCK", 0, "START_DESTROY_BLOCK", 0), 
        ABORT_DESTROY_BLOCK("ABORT_DESTROY_BLOCK", 1, "ABORT_DESTROY_BLOCK", 1), 
        STOP_DESTROY_BLOCK("STOP_DESTROY_BLOCK", 2, "STOP_DESTROY_BLOCK", 2), 
        DROP_ALL_ITEMS("DROP_ALL_ITEMS", 3, "DROP_ALL_ITEMS", 3), 
        DROP_ITEM("DROP_ITEM", 4, "DROP_ITEM", 4), 
        RELEASE_USE_ITEM("RELEASE_USE_ITEM", 5, "RELEASE_USE_ITEM", 5);
        
        private static final Action[] $VALUES;
        private static final String __OBFID;
        private static final Action[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002284";
            ENUM$VALUES = new Action[] { Action.START_DESTROY_BLOCK, Action.ABORT_DESTROY_BLOCK, Action.STOP_DESTROY_BLOCK, Action.DROP_ALL_ITEMS, Action.DROP_ITEM, Action.RELEASE_USE_ITEM };
            $VALUES = new Action[] { Action.START_DESTROY_BLOCK, Action.ABORT_DESTROY_BLOCK, Action.STOP_DESTROY_BLOCK, Action.DROP_ALL_ITEMS, Action.DROP_ITEM, Action.RELEASE_USE_ITEM };
        }
        
        private Action(final String s, final int n, final String s2, final int n2) {
        }
    }
}
