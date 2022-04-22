package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.network.*;

public class S43PacketCamera implements Packet
{
    public int field_179781_a;
    private static final String __OBFID;
    
    public S43PacketCamera() {
    }
    
    public S43PacketCamera(final Entity entity) {
        this.field_179781_a = entity.getEntityId();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179781_a = packetBuffer.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_179781_a);
    }
    
    public void func_179779_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.func_175094_a(this);
    }
    
    public Entity func_179780_a(final World world) {
        return world.getEntityByID(this.field_179781_a);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_179779_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00002289";
    }
}
