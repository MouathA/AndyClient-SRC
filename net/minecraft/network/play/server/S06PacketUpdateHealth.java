package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S06PacketUpdateHealth implements Packet
{
    private float health;
    private int foodLevel;
    private float saturationLevel;
    private static final String __OBFID;
    
    public S06PacketUpdateHealth() {
    }
    
    public S06PacketUpdateHealth(final float health, final int foodLevel, final float saturationLevel) {
        this.health = health;
        this.foodLevel = foodLevel;
        this.saturationLevel = saturationLevel;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.health = packetBuffer.readFloat();
        this.foodLevel = packetBuffer.readVarIntFromBuffer();
        this.saturationLevel = packetBuffer.readFloat();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.health);
        packetBuffer.writeVarIntToBuffer(this.foodLevel);
        packetBuffer.writeFloat(this.saturationLevel);
    }
    
    public void func_180750_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUpdateHealth(this);
    }
    
    public float getHealth() {
        return this.health;
    }
    
    public int getFoodLevel() {
        return this.foodLevel;
    }
    
    public float getSaturationLevel() {
        return this.saturationLevel;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180750_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001332";
    }
}
