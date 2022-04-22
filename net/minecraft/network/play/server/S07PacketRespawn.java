package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S07PacketRespawn implements Packet
{
    private int field_149088_a;
    private EnumDifficulty field_149086_b;
    private WorldSettings.GameType field_149087_c;
    private WorldType field_149085_d;
    private static final String __OBFID;
    
    public S07PacketRespawn() {
    }
    
    public S07PacketRespawn(final int field_149088_a, final EnumDifficulty field_149086_b, final WorldType field_149085_d, final WorldSettings.GameType field_149087_c) {
        this.field_149088_a = field_149088_a;
        this.field_149086_b = field_149086_b;
        this.field_149087_c = field_149087_c;
        this.field_149085_d = field_149085_d;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleRespawn(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149088_a = packetBuffer.readInt();
        this.field_149086_b = EnumDifficulty.getDifficultyEnum(packetBuffer.readUnsignedByte());
        this.field_149087_c = WorldSettings.GameType.getByID(packetBuffer.readUnsignedByte());
        this.field_149085_d = WorldType.parseWorldType(packetBuffer.readStringFromBuffer(16));
        if (this.field_149085_d == null) {
            this.field_149085_d = WorldType.DEFAULT;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.field_149088_a);
        packetBuffer.writeByte(this.field_149086_b.getDifficultyId());
        packetBuffer.writeByte(this.field_149087_c.getID());
        packetBuffer.writeString(this.field_149085_d.getWorldTypeName());
    }
    
    public int func_149082_c() {
        return this.field_149088_a;
    }
    
    public EnumDifficulty func_149081_d() {
        return this.field_149086_b;
    }
    
    public WorldSettings.GameType func_149083_e() {
        return this.field_149087_c;
    }
    
    public WorldType func_149080_f() {
        return this.field_149085_d;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001322";
    }
}
