package net.minecraft.network.play.client;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.network.*;

public class C02PacketUseEntity implements Packet
{
    private int entityId;
    private Action action;
    private Vec3 field_179713_c;
    private static final String __OBFID;
    
    public C02PacketUseEntity() {
    }
    
    public C02PacketUseEntity(final Entity entity, final Action action) {
        this.entityId = entity.getEntityId();
        this.action = action;
    }
    
    public C02PacketUseEntity(final Entity entity, final Vec3 field_179713_c) {
        this(entity, Action.INTERACT_AT);
        this.field_179713_c = field_179713_c;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.action = (Action)packetBuffer.readEnumValue(Action.class);
        if (this.action == Action.INTERACT_AT) {
            this.field_179713_c = new Vec3(packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readFloat());
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeEnumValue(this.action);
        if (this.action == Action.INTERACT_AT) {
            packetBuffer.writeFloat((float)this.field_179713_c.xCoord);
            packetBuffer.writeFloat((float)this.field_179713_c.yCoord);
            packetBuffer.writeFloat((float)this.field_179713_c.zCoord);
        }
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processUseEntity(this);
    }
    
    public Entity getEntityFromWorld(final World world) {
        return world.getEntityByID(this.entityId);
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public Vec3 func_179712_b() {
        return this.field_179713_c;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001357";
    }
    
    public enum Action
    {
        INTERACT("INTERACT", 0, "INTERACT", 0), 
        ATTACK("ATTACK", 1, "ATTACK", 1), 
        INTERACT_AT("INTERACT_AT", 2, "INTERACT_AT", 2);
        
        private static final Action[] $VALUES;
        private static final String __OBFID;
        private static final Action[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001358";
            ENUM$VALUES = new Action[] { Action.INTERACT, Action.ATTACK, Action.INTERACT_AT };
            $VALUES = new Action[] { Action.INTERACT, Action.ATTACK, Action.INTERACT_AT };
        }
        
        private Action(final String s, final int n, final String s2, final int n2) {
        }
    }
}
