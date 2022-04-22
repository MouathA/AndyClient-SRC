package net.minecraft.network.play.server;

import net.minecraft.scoreboard.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S3BPacketScoreboardObjective implements Packet
{
    private String field_149343_a;
    private String field_149341_b;
    private IScoreObjectiveCriteria.EnumRenderType field_179818_c;
    private int field_149342_c;
    private static final String __OBFID;
    
    public S3BPacketScoreboardObjective() {
    }
    
    public S3BPacketScoreboardObjective(final ScoreObjective scoreObjective, final int field_149342_c) {
        this.field_149343_a = scoreObjective.getName();
        this.field_149341_b = scoreObjective.getDisplayName();
        this.field_179818_c = scoreObjective.getCriteria().func_178790_c();
        this.field_149342_c = field_149342_c;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149343_a = packetBuffer.readStringFromBuffer(16);
        this.field_149342_c = packetBuffer.readByte();
        if (this.field_149342_c == 0 || this.field_149342_c == 2) {
            this.field_149341_b = packetBuffer.readStringFromBuffer(32);
            this.field_179818_c = IScoreObjectiveCriteria.EnumRenderType.func_178795_a(packetBuffer.readStringFromBuffer(16));
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.field_149343_a);
        packetBuffer.writeByte(this.field_149342_c);
        if (this.field_149342_c == 0 || this.field_149342_c == 2) {
            packetBuffer.writeString(this.field_149341_b);
            packetBuffer.writeString(this.field_179818_c.func_178796_a());
        }
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleScoreboardObjective(this);
    }
    
    public String func_149339_c() {
        return this.field_149343_a;
    }
    
    public String func_149337_d() {
        return this.field_149341_b;
    }
    
    public int func_149338_e() {
        return this.field_149342_c;
    }
    
    public IScoreObjectiveCriteria.EnumRenderType func_179817_d() {
        return this.field_179818_c;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001333";
    }
}
