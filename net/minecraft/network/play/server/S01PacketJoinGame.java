package net.minecraft.network.play.server;

import net.minecraft.world.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S01PacketJoinGame implements Packet
{
    private int field_149206_a;
    private boolean field_149204_b;
    private WorldSettings.GameType field_149205_c;
    private int field_149202_d;
    private EnumDifficulty field_149203_e;
    private int field_149200_f;
    private WorldType field_149201_g;
    private boolean field_179745_h;
    private static final String __OBFID;
    
    public S01PacketJoinGame() {
    }
    
    public S01PacketJoinGame(final int field_149206_a, final WorldSettings.GameType field_149205_c, final boolean field_149204_b, final int field_149202_d, final EnumDifficulty field_149203_e, final int field_149200_f, final WorldType field_149201_g, final boolean field_179745_h) {
        this.field_149206_a = field_149206_a;
        this.field_149202_d = field_149202_d;
        this.field_149203_e = field_149203_e;
        this.field_149205_c = field_149205_c;
        this.field_149200_f = field_149200_f;
        this.field_149204_b = field_149204_b;
        this.field_149201_g = field_149201_g;
        this.field_179745_h = field_179745_h;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149206_a = packetBuffer.readInt();
        final short unsignedByte = packetBuffer.readUnsignedByte();
        this.field_149204_b = ((unsignedByte & 0x8) == 0x8);
        this.field_149205_c = WorldSettings.GameType.getByID(unsignedByte & 0xFFFFFFF7);
        this.field_149202_d = packetBuffer.readByte();
        this.field_149203_e = EnumDifficulty.getDifficultyEnum(packetBuffer.readUnsignedByte());
        this.field_149200_f = packetBuffer.readUnsignedByte();
        this.field_149201_g = WorldType.parseWorldType(packetBuffer.readStringFromBuffer(16));
        if (this.field_149201_g == null) {
            this.field_149201_g = WorldType.DEFAULT;
        }
        this.field_179745_h = packetBuffer.readBoolean();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.field_149206_a);
        int id = this.field_149205_c.getID();
        if (this.field_149204_b) {
            id |= 0x8;
        }
        packetBuffer.writeByte(id);
        packetBuffer.writeByte(this.field_149202_d);
        packetBuffer.writeByte(this.field_149203_e.getDifficultyId());
        packetBuffer.writeByte(this.field_149200_f);
        packetBuffer.writeString(this.field_149201_g.getWorldTypeName());
        packetBuffer.writeBoolean(this.field_179745_h);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleJoinGame(this);
    }
    
    public int func_149197_c() {
        return this.field_149206_a;
    }
    
    public boolean func_149195_d() {
        return this.field_149204_b;
    }
    
    public WorldSettings.GameType func_149198_e() {
        return this.field_149205_c;
    }
    
    public int func_149194_f() {
        return this.field_149202_d;
    }
    
    public EnumDifficulty func_149192_g() {
        return this.field_149203_e;
    }
    
    public int func_149193_h() {
        return this.field_149200_f;
    }
    
    public WorldType func_149196_i() {
        return this.field_149201_g;
    }
    
    public boolean func_179744_h() {
        return this.field_179745_h;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001310";
    }
}
