package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C03PacketPlayer implements Packet
{
    protected double x;
    protected double y;
    protected double z;
    protected float yaw;
    protected float pitch;
    protected boolean field_149474_g;
    protected boolean field_149480_h;
    protected boolean rotating;
    private static final String __OBFID;
    
    public C03PacketPlayer() {
    }
    
    public C03PacketPlayer(final boolean field_149474_g) {
        this.field_149474_g = field_149474_g;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processPlayer(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149474_g = (packetBuffer.readUnsignedByte() != 0);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.field_149474_g ? 1 : 0);
    }
    
    public double getPositionX() {
        return this.x;
    }
    
    public double getPositionY() {
        return this.y;
    }
    
    public double getPositionZ() {
        return this.z;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public boolean func_149465_i() {
        return this.field_149474_g;
    }
    
    public boolean func_149466_j() {
        return this.field_149480_h;
    }
    
    public boolean getRotating() {
        return this.rotating;
    }
    
    public void func_149469_a(final boolean field_149480_h) {
        this.field_149480_h = field_149480_h;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001360";
    }
    
    public static class C04PacketPlayerPosition extends C03PacketPlayer
    {
        private static final String __OBFID;
        
        public C04PacketPlayerPosition() {
            this.field_149480_h = true;
        }
        
        public C04PacketPlayerPosition(final double x, final double y, final double z, final boolean field_149474_g) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.field_149474_g = field_149474_g;
            this.field_149480_h = true;
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            this.x = packetBuffer.readDouble();
            this.y = packetBuffer.readDouble();
            this.z = packetBuffer.readDouble();
            super.readPacketData(packetBuffer);
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            packetBuffer.writeDouble(this.x);
            packetBuffer.writeDouble(this.y);
            packetBuffer.writeDouble(this.z);
            super.writePacketData(packetBuffer);
        }
        
        @Override
        public void processPacket(final INetHandler netHandler) {
            super.processPacket((INetHandlerPlayServer)netHandler);
        }
        
        static {
            __OBFID = "CL_00001361";
        }
    }
    
    public static class C05PacketPlayerLook extends C03PacketPlayer
    {
        private static final String __OBFID;
        
        public C05PacketPlayerLook() {
            this.rotating = true;
        }
        
        public C05PacketPlayerLook(final float yaw, final float pitch, final boolean field_149474_g) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.field_149474_g = field_149474_g;
            this.rotating = true;
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            this.yaw = packetBuffer.readFloat();
            this.pitch = packetBuffer.readFloat();
            super.readPacketData(packetBuffer);
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            packetBuffer.writeFloat(this.yaw);
            packetBuffer.writeFloat(this.pitch);
            super.writePacketData(packetBuffer);
        }
        
        @Override
        public void processPacket(final INetHandler netHandler) {
            super.processPacket((INetHandlerPlayServer)netHandler);
        }
        
        static {
            __OBFID = "CL_00001363";
        }
    }
    
    public static class C06PacketPlayerPosLook extends C03PacketPlayer
    {
        private static final String __OBFID;
        
        public C06PacketPlayerPosLook() {
            this.field_149480_h = true;
            this.rotating = true;
        }
        
        public C06PacketPlayerPosLook(final double x, final double y, final double z, final float yaw, final float pitch, final boolean field_149474_g) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.field_149474_g = field_149474_g;
            this.rotating = true;
            this.field_149480_h = true;
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            this.x = packetBuffer.readDouble();
            this.y = packetBuffer.readDouble();
            this.z = packetBuffer.readDouble();
            this.yaw = packetBuffer.readFloat();
            this.pitch = packetBuffer.readFloat();
            super.readPacketData(packetBuffer);
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            packetBuffer.writeDouble(this.x);
            packetBuffer.writeDouble(this.y);
            packetBuffer.writeDouble(this.z);
            packetBuffer.writeFloat(this.yaw);
            packetBuffer.writeFloat(this.pitch);
            super.writePacketData(packetBuffer);
        }
        
        @Override
        public void processPacket(final INetHandler netHandler) {
            super.processPacket((INetHandlerPlayServer)netHandler);
        }
        
        static {
            __OBFID = "CL_00001362";
        }
    }
}
