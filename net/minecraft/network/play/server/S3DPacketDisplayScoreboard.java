package net.minecraft.network.play.server;

import net.minecraft.scoreboard.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S3DPacketDisplayScoreboard implements Packet
{
    private int field_149374_a;
    private String field_149373_b;
    private static final String __OBFID;
    
    public S3DPacketDisplayScoreboard() {
    }
    
    public S3DPacketDisplayScoreboard(final int field_149374_a, final ScoreObjective scoreObjective) {
        this.field_149374_a = field_149374_a;
        if (scoreObjective == null) {
            this.field_149373_b = "";
        }
        else {
            this.field_149373_b = scoreObjective.getName();
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149374_a = packetBuffer.readByte();
        this.field_149373_b = packetBuffer.readStringFromBuffer(16);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.field_149374_a);
        packetBuffer.writeString(this.field_149373_b);
    }
    
    public void func_180747_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleDisplayScoreboard(this);
    }
    
    public int func_149371_c() {
        return this.field_149374_a;
    }
    
    public String func_149370_d() {
        return this.field_149373_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180747_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001325";
    }
}
