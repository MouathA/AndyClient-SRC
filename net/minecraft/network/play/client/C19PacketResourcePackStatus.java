package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C19PacketResourcePackStatus implements Packet
{
    private String field_179720_a;
    private Action field_179719_b;
    private static final String __OBFID;
    
    public C19PacketResourcePackStatus() {
    }
    
    public C19PacketResourcePackStatus(String substring, final Action field_179719_b) {
        if (substring.length() > 40) {
            substring = substring.substring(0, 40);
        }
        this.field_179720_a = substring;
        this.field_179719_b = field_179719_b;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179720_a = packetBuffer.readStringFromBuffer(40);
        this.field_179719_b = (Action)packetBuffer.readEnumValue(Action.class);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.field_179720_a);
        packetBuffer.writeEnumValue(this.field_179719_b);
    }
    
    public void func_179718_a(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.func_175086_a(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_179718_a((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00002282";
    }
    
    public enum Action
    {
        SUCCESSFULLY_LOADED("SUCCESSFULLY_LOADED", 0, "SUCCESSFULLY_LOADED", 0), 
        DECLINED("DECLINED", 1, "DECLINED", 1), 
        FAILED_DOWNLOAD("FAILED_DOWNLOAD", 2, "FAILED_DOWNLOAD", 2), 
        ACCEPTED("ACCEPTED", 3, "ACCEPTED", 3);
        
        private static final Action[] $VALUES;
        private static final String __OBFID;
        private static final Action[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002281";
            ENUM$VALUES = new Action[] { Action.SUCCESSFULLY_LOADED, Action.DECLINED, Action.FAILED_DOWNLOAD, Action.ACCEPTED };
            $VALUES = new Action[] { Action.SUCCESSFULLY_LOADED, Action.DECLINED, Action.FAILED_DOWNLOAD, Action.ACCEPTED };
        }
        
        private Action(final String s, final int n, final String s2, final int n2) {
        }
    }
}
