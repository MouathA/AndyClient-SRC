package net.minecraft.network.play.client;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C0BPacketEntityAction implements Packet
{
    private int field_149517_a;
    private Action field_149515_b;
    private int field_149516_c;
    private static final String __OBFID;
    
    public C0BPacketEntityAction() {
    }
    
    public C0BPacketEntityAction(final Entity entity, final Action action) {
        this(entity, action, 0);
    }
    
    public C0BPacketEntityAction(final Entity entity, final Action field_149515_b, final int field_149516_c) {
        this.field_149517_a = entity.getEntityId();
        this.field_149515_b = field_149515_b;
        this.field_149516_c = field_149516_c;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149517_a = packetBuffer.readVarIntFromBuffer();
        this.field_149515_b = (Action)packetBuffer.readEnumValue(Action.class);
        this.field_149516_c = packetBuffer.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149517_a);
        packetBuffer.writeEnumValue(this.field_149515_b);
        packetBuffer.writeVarIntToBuffer(this.field_149516_c);
    }
    
    public void func_180765_a(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processEntityAction(this);
    }
    
    public Action func_180764_b() {
        return this.field_149515_b;
    }
    
    public int func_149512_e() {
        return this.field_149516_c;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180765_a((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001366";
    }
    
    public enum Action
    {
        START_SNEAKING("START_SNEAKING", 0, "START_SNEAKING", 0), 
        STOP_SNEAKING("STOP_SNEAKING", 1, "STOP_SNEAKING", 1), 
        STOP_SLEEPING("STOP_SLEEPING", 2, "STOP_SLEEPING", 2), 
        START_SPRINTING("START_SPRINTING", 3, "START_SPRINTING", 3), 
        STOP_SPRINTING("STOP_SPRINTING", 4, "STOP_SPRINTING", 4), 
        RIDING_JUMP("RIDING_JUMP", 5, "RIDING_JUMP", 5), 
        OPEN_INVENTORY("OPEN_INVENTORY", 6, "OPEN_INVENTORY", 6);
        
        private static final Action[] $VALUES;
        private static final String __OBFID;
        private static final Action[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002283";
            ENUM$VALUES = new Action[] { Action.START_SNEAKING, Action.STOP_SNEAKING, Action.STOP_SLEEPING, Action.START_SPRINTING, Action.STOP_SPRINTING, Action.RIDING_JUMP, Action.OPEN_INVENTORY };
            $VALUES = new Action[] { Action.START_SNEAKING, Action.STOP_SNEAKING, Action.STOP_SLEEPING, Action.START_SPRINTING, Action.STOP_SPRINTING, Action.RIDING_JUMP, Action.OPEN_INVENTORY };
        }
        
        private Action(final String s, final int n, final String s2, final int n2) {
        }
    }
}
