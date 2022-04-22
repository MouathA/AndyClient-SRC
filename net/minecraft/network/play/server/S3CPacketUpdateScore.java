package net.minecraft.network.play.server;

import net.minecraft.scoreboard.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S3CPacketUpdateScore implements Packet
{
    private String name;
    private String objective;
    private int value;
    private Action action;
    private static final String __OBFID;
    
    public S3CPacketUpdateScore() {
        this.name = "";
        this.objective = "";
    }
    
    public S3CPacketUpdateScore(final Score score) {
        this.name = "";
        this.objective = "";
        this.name = score.getPlayerName();
        this.objective = score.getObjective().getName();
        this.value = score.getScorePoints();
        this.action = Action.CHANGE;
    }
    
    public S3CPacketUpdateScore(final String name) {
        this.name = "";
        this.objective = "";
        this.name = name;
        this.objective = "";
        this.value = 0;
        this.action = Action.REMOVE;
    }
    
    public S3CPacketUpdateScore(final String name, final ScoreObjective scoreObjective) {
        this.name = "";
        this.objective = "";
        this.name = name;
        this.objective = scoreObjective.getName();
        this.value = 0;
        this.action = Action.REMOVE;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.name = packetBuffer.readStringFromBuffer(40);
        this.action = (Action)packetBuffer.readEnumValue(Action.class);
        this.objective = packetBuffer.readStringFromBuffer(16);
        if (this.action != Action.REMOVE) {
            this.value = packetBuffer.readVarIntFromBuffer();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.name);
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeString(this.objective);
        if (this.action != Action.REMOVE) {
            packetBuffer.writeVarIntToBuffer(this.value);
        }
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUpdateScore(this);
    }
    
    public String func_149324_c() {
        return this.name;
    }
    
    public String func_149321_d() {
        return this.objective;
    }
    
    public int func_149323_e() {
        return this.value;
    }
    
    public Action func_180751_d() {
        return this.action;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001335";
    }
    
    public enum Action
    {
        CHANGE("CHANGE", 0, "CHANGE", 0), 
        REMOVE("REMOVE", 1, "REMOVE", 1);
        
        private static final Action[] $VALUES;
        private static final String __OBFID;
        private static final Action[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002288";
            ENUM$VALUES = new Action[] { Action.CHANGE, Action.REMOVE };
            $VALUES = new Action[] { Action.CHANGE, Action.REMOVE };
        }
        
        private Action(final String s, final int n, final String s2, final int n2) {
        }
    }
}
