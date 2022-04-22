package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C0CPacketInput implements Packet
{
    private float strafeSpeed;
    private float forwardSpeed;
    private boolean jumping;
    private boolean sneaking;
    private static final String __OBFID;
    
    public C0CPacketInput() {
    }
    
    public C0CPacketInput(final float strafeSpeed, final float forwardSpeed, final boolean jumping, final boolean sneaking) {
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.jumping = jumping;
        this.sneaking = sneaking;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.strafeSpeed = packetBuffer.readFloat();
        this.forwardSpeed = packetBuffer.readFloat();
        final byte byte1 = packetBuffer.readByte();
        this.jumping = ((byte1 & 0x1) > 0);
        this.sneaking = ((byte1 & 0x2) > 0);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.strafeSpeed);
        packetBuffer.writeFloat(this.forwardSpeed);
        if (this.jumping) {
            final byte b = 1;
        }
        if (this.sneaking) {
            final byte b2 = 2;
        }
        packetBuffer.writeByte(0);
    }
    
    public void func_180766_a(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processInput(this);
    }
    
    public float getStrafeSpeed() {
        return this.strafeSpeed;
    }
    
    public float getForwardSpeed() {
        return this.forwardSpeed;
    }
    
    public boolean isJumping() {
        return this.jumping;
    }
    
    public boolean isSneaking() {
        return this.sneaking;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180766_a((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001367";
    }
}
