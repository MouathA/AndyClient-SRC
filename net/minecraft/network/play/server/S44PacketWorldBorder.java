package net.minecraft.network.play.server;

import net.minecraft.world.border.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.util.*;
import java.nio.charset.*;

public class S44PacketWorldBorder implements Packet
{
    private Action field_179795_a;
    private int field_179793_b;
    private double field_179794_c;
    private double field_179791_d;
    private double field_179792_e;
    private double field_179789_f;
    private long field_179790_g;
    private int field_179796_h;
    private int field_179797_i;
    private static final String __OBFID;
    
    public S44PacketWorldBorder() {
    }
    
    public S44PacketWorldBorder(final WorldBorder worldBorder, final Action field_179795_a) {
        this.field_179795_a = field_179795_a;
        this.field_179794_c = worldBorder.getCenterX();
        this.field_179791_d = worldBorder.getCenterZ();
        this.field_179789_f = worldBorder.getDiameter();
        this.field_179792_e = worldBorder.getTargetSize();
        this.field_179790_g = worldBorder.getTimeUntilTarget();
        this.field_179793_b = worldBorder.getSize();
        this.field_179797_i = worldBorder.getWarningDistance();
        this.field_179796_h = worldBorder.getWarningTime();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179795_a = (Action)packetBuffer.readEnumValue(Action.class);
        switch (SwitchAction.field_179947_a[this.field_179795_a.ordinal()]) {
            case 1: {
                this.field_179792_e = packetBuffer.readDouble();
                break;
            }
            case 2: {
                this.field_179789_f = packetBuffer.readDouble();
                this.field_179792_e = packetBuffer.readDouble();
                this.field_179790_g = packetBuffer.readVarLong();
                break;
            }
            case 3: {
                this.field_179794_c = packetBuffer.readDouble();
                this.field_179791_d = packetBuffer.readDouble();
                break;
            }
            case 4: {
                this.field_179797_i = packetBuffer.readVarIntFromBuffer();
                break;
            }
            case 5: {
                this.field_179796_h = packetBuffer.readVarIntFromBuffer();
                break;
            }
            case 6: {
                this.field_179794_c = packetBuffer.readDouble();
                this.field_179791_d = packetBuffer.readDouble();
                this.field_179789_f = packetBuffer.readDouble();
                this.field_179792_e = packetBuffer.readDouble();
                this.field_179790_g = packetBuffer.readVarLong();
                this.field_179793_b = packetBuffer.readVarIntFromBuffer();
                this.field_179797_i = packetBuffer.readVarIntFromBuffer();
                this.field_179796_h = packetBuffer.readVarIntFromBuffer();
                break;
            }
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.field_179795_a);
        switch (SwitchAction.field_179947_a[this.field_179795_a.ordinal()]) {
            case 1: {
                packetBuffer.writeDouble(this.field_179792_e);
                break;
            }
            case 2: {
                packetBuffer.writeDouble(this.field_179789_f);
                packetBuffer.writeDouble(this.field_179792_e);
                packetBuffer.writeVarLong(this.field_179790_g);
                break;
            }
            case 3: {
                packetBuffer.writeDouble(this.field_179794_c);
                packetBuffer.writeDouble(this.field_179791_d);
                break;
            }
            case 4: {
                packetBuffer.writeVarIntToBuffer(this.field_179797_i);
                break;
            }
            case 5: {
                packetBuffer.writeVarIntToBuffer(this.field_179796_h);
                break;
            }
            case 6: {
                packetBuffer.writeDouble(this.field_179794_c);
                packetBuffer.writeDouble(this.field_179791_d);
                packetBuffer.writeDouble(this.field_179789_f);
                packetBuffer.writeDouble(this.field_179792_e);
                packetBuffer.writeVarLong(this.field_179790_g);
                packetBuffer.writeVarIntToBuffer(this.field_179793_b);
                packetBuffer.writeVarIntToBuffer(this.field_179797_i);
                packetBuffer.writeVarIntToBuffer(this.field_179796_h);
                break;
            }
        }
    }
    
    public void func_179787_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.func_175093_a(this);
    }
    
    public void func_179788_a(final WorldBorder worldBorder) {
        switch (SwitchAction.field_179947_a[this.field_179795_a.ordinal()]) {
            case 1: {
                worldBorder.setTransition(this.field_179792_e);
                break;
            }
            case 2: {
                worldBorder.setTransition(this.field_179789_f, this.field_179792_e, this.field_179790_g);
                break;
            }
            case 3: {
                worldBorder.setCenter(this.field_179794_c, this.field_179791_d);
                break;
            }
            case 4: {
                worldBorder.setWarningDistance(this.field_179797_i);
                break;
            }
            case 5: {
                worldBorder.setWarningTime(this.field_179796_h);
                break;
            }
            case 6: {
                worldBorder.setCenter(this.field_179794_c, this.field_179791_d);
                if (this.field_179790_g > 0L) {
                    worldBorder.setTransition(this.field_179789_f, this.field_179792_e, this.field_179790_g);
                }
                else {
                    worldBorder.setTransition(this.field_179792_e);
                }
                worldBorder.setSize(this.field_179793_b);
                worldBorder.setWarningDistance(this.field_179797_i);
                worldBorder.setWarningTime(this.field_179796_h);
                break;
            }
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_179787_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00002292";
    }
    
    public enum Action
    {
        SET_SIZE("SET_SIZE", 0, "SET_SIZE", 0), 
        LERP_SIZE("LERP_SIZE", 1, "LERP_SIZE", 1), 
        SET_CENTER("SET_CENTER", 2, "SET_CENTER", 2), 
        INITIALIZE("INITIALIZE", 3, "INITIALIZE", 3), 
        SET_WARNING_TIME("SET_WARNING_TIME", 4, "SET_WARNING_TIME", 4), 
        SET_WARNING_BLOCKS("SET_WARNING_BLOCKS", 5, "SET_WARNING_BLOCKS", 5);
        
        private static final Action[] $VALUES;
        private static final String __OBFID;
        private static final Action[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002290";
            ENUM$VALUES = new Action[] { Action.SET_SIZE, Action.LERP_SIZE, Action.SET_CENTER, Action.INITIALIZE, Action.SET_WARNING_TIME, Action.SET_WARNING_BLOCKS };
            $VALUES = new Action[] { Action.SET_SIZE, Action.LERP_SIZE, Action.SET_CENTER, Action.INITIALIZE, Action.SET_WARNING_TIME, Action.SET_WARNING_BLOCKS };
        }
        
        private Action(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    static final class SwitchAction
    {
        static final int[] field_179947_a;
        private static final String __OBFID;
        private static final String[] llIlIlllllIllll;
        private static String[] llIlIllllllIIII;
        
        static {
            lIIllllIllIlIllI();
            lIIllllIllIlIlIl();
            __OBFID = SwitchAction.llIlIlllllIllll[0];
            field_179947_a = new int[Action.values().length];
            try {
                SwitchAction.field_179947_a[Action.SET_SIZE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchAction.field_179947_a[Action.LERP_SIZE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchAction.field_179947_a[Action.SET_CENTER.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchAction.field_179947_a[Action.SET_WARNING_BLOCKS.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchAction.field_179947_a[Action.SET_WARNING_TIME.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchAction.field_179947_a[Action.INITIALIZE.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
        
        private static void lIIllllIllIlIlIl() {
            (llIlIlllllIllll = new String[1])[0] = lIIllllIllIlIlII(SwitchAction.llIlIllllllIIII[0], SwitchAction.llIlIllllllIIII[1]);
            SwitchAction.llIlIllllllIIII = null;
        }
        
        private static void lIIllllIllIlIllI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchAction.llIlIllllllIIII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIllllIllIlIlII(String s, final String s2) {
            s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int n = 0;
            final char[] charArray2 = s.toCharArray();
            for (int length = charArray2.length, i = 0; i < length; ++i) {
                sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
                ++n;
            }
            return sb.toString();
        }
    }
}
