package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S41PacketServerDifficulty implements Packet
{
    private EnumDifficulty field_179833_a;
    private boolean field_179832_b;
    private static final String __OBFID;
    
    public S41PacketServerDifficulty() {
    }
    
    public S41PacketServerDifficulty(final EnumDifficulty field_179833_a, final boolean field_179832_b) {
        this.field_179833_a = field_179833_a;
        this.field_179832_b = field_179832_b;
    }
    
    public void func_179829_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.func_175101_a(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179833_a = EnumDifficulty.getDifficultyEnum(packetBuffer.readUnsignedByte());
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.field_179833_a.getDifficultyId());
    }
    
    public boolean func_179830_a() {
        return this.field_179832_b;
    }
    
    public EnumDifficulty func_179831_b() {
        return this.field_179833_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_179829_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00002303";
    }
}
