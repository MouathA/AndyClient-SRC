package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;

public class S14PacketEntity implements Packet
{
    protected int field_149074_a;
    protected byte field_149072_b;
    protected byte field_149073_c;
    protected byte field_149070_d;
    protected byte field_149071_e;
    protected byte field_149068_f;
    protected boolean field_179743_g;
    protected boolean field_149069_g;
    private static final String __OBFID;
    
    public S14PacketEntity() {
    }
    
    public S14PacketEntity(final int field_149074_a) {
        this.field_149074_a = field_149074_a;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149074_a = packetBuffer.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149074_a);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityMovement(this);
    }
    
    @Override
    public String toString() {
        return "Entity_" + super.toString();
    }
    
    public Entity func_149065_a(final World world) {
        return world.getEntityByID(this.field_149074_a);
    }
    
    public byte func_149062_c() {
        return this.field_149072_b;
    }
    
    public byte func_149061_d() {
        return this.field_149073_c;
    }
    
    public byte func_149064_e() {
        return this.field_149070_d;
    }
    
    public byte func_149066_f() {
        return this.field_149071_e;
    }
    
    public byte func_149063_g() {
        return this.field_149068_f;
    }
    
    public boolean func_149060_h() {
        return this.field_149069_g;
    }
    
    public boolean func_179742_g() {
        return this.field_179743_g;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001312";
    }
    
    public static class S15PacketEntityRelMove extends S14PacketEntity
    {
        private static final String __OBFID;
        
        public S15PacketEntityRelMove() {
        }
        
        public S15PacketEntityRelMove(final int n, final byte field_149072_b, final byte field_149073_c, final byte field_149070_d, final boolean field_179743_g) {
            super(n);
            this.field_149072_b = field_149072_b;
            this.field_149073_c = field_149073_c;
            this.field_149070_d = field_149070_d;
            this.field_179743_g = field_179743_g;
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            super.readPacketData(packetBuffer);
            this.field_149072_b = packetBuffer.readByte();
            this.field_149073_c = packetBuffer.readByte();
            this.field_149070_d = packetBuffer.readByte();
            this.field_179743_g = packetBuffer.readBoolean();
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            super.writePacketData(packetBuffer);
            packetBuffer.writeByte(this.field_149072_b);
            packetBuffer.writeByte(this.field_149073_c);
            packetBuffer.writeByte(this.field_149070_d);
            packetBuffer.writeBoolean(this.field_179743_g);
        }
        
        @Override
        public void processPacket(final INetHandler netHandler) {
            super.processPacket((INetHandlerPlayClient)netHandler);
        }
        
        static {
            __OBFID = "CL_00001313";
        }
    }
    
    public static class S16PacketEntityLook extends S14PacketEntity
    {
        private static final String __OBFID;
        
        public S16PacketEntityLook() {
            this.field_149069_g = true;
        }
        
        public S16PacketEntityLook(final int n, final byte field_149071_e, final byte field_149068_f, final boolean field_179743_g) {
            super(n);
            this.field_149071_e = field_149071_e;
            this.field_149068_f = field_149068_f;
            this.field_149069_g = true;
            this.field_179743_g = field_179743_g;
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            super.readPacketData(packetBuffer);
            this.field_149071_e = packetBuffer.readByte();
            this.field_149068_f = packetBuffer.readByte();
            this.field_179743_g = packetBuffer.readBoolean();
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            super.writePacketData(packetBuffer);
            packetBuffer.writeByte(this.field_149071_e);
            packetBuffer.writeByte(this.field_149068_f);
            packetBuffer.writeBoolean(this.field_179743_g);
        }
        
        @Override
        public void processPacket(final INetHandler netHandler) {
            super.processPacket((INetHandlerPlayClient)netHandler);
        }
        
        static {
            __OBFID = "CL_00001315";
        }
    }
    
    public static class S17PacketEntityLookMove extends S14PacketEntity
    {
        private static final String __OBFID;
        
        public S17PacketEntityLookMove() {
            this.field_149069_g = true;
        }
        
        public S17PacketEntityLookMove(final int n, final byte field_149072_b, final byte field_149073_c, final byte field_149070_d, final byte field_149071_e, final byte field_149068_f, final boolean field_179743_g) {
            super(n);
            this.field_149072_b = field_149072_b;
            this.field_149073_c = field_149073_c;
            this.field_149070_d = field_149070_d;
            this.field_149071_e = field_149071_e;
            this.field_149068_f = field_149068_f;
            this.field_179743_g = field_179743_g;
            this.field_149069_g = true;
        }
        
        @Override
        public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
            super.readPacketData(packetBuffer);
            this.field_149072_b = packetBuffer.readByte();
            this.field_149073_c = packetBuffer.readByte();
            this.field_149070_d = packetBuffer.readByte();
            this.field_149071_e = packetBuffer.readByte();
            this.field_149068_f = packetBuffer.readByte();
            this.field_179743_g = packetBuffer.readBoolean();
        }
        
        @Override
        public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
            super.writePacketData(packetBuffer);
            packetBuffer.writeByte(this.field_149072_b);
            packetBuffer.writeByte(this.field_149073_c);
            packetBuffer.writeByte(this.field_149070_d);
            packetBuffer.writeByte(this.field_149071_e);
            packetBuffer.writeByte(this.field_149068_f);
            packetBuffer.writeBoolean(this.field_179743_g);
        }
        
        @Override
        public void processPacket(final INetHandler netHandler) {
            super.processPacket((INetHandlerPlayClient)netHandler);
        }
        
        static {
            __OBFID = "CL_00001314";
        }
    }
}
