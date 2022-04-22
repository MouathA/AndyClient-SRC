package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class S42PacketCombatEvent implements Packet
{
    public Event field_179776_a;
    public int field_179774_b;
    public int field_179775_c;
    public int field_179772_d;
    public String field_179773_e;
    private static final String __OBFID;
    
    public S42PacketCombatEvent() {
    }
    
    public S42PacketCombatEvent(final CombatTracker combatTracker, final Event field_179776_a) {
        this.field_179776_a = field_179776_a;
        final EntityLivingBase func_94550_c = combatTracker.func_94550_c();
        switch (SwitchEvent.field_179944_a[field_179776_a.ordinal()]) {
            case 1: {
                this.field_179772_d = combatTracker.func_180134_f();
                this.field_179775_c = ((func_94550_c == null) ? -1 : func_94550_c.getEntityId());
                break;
            }
            case 2: {
                this.field_179774_b = combatTracker.func_180135_h().getEntityId();
                this.field_179775_c = ((func_94550_c == null) ? -1 : func_94550_c.getEntityId());
                this.field_179773_e = combatTracker.func_151521_b().getUnformattedText();
                break;
            }
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179776_a = (Event)packetBuffer.readEnumValue(Event.class);
        if (this.field_179776_a == Event.END_COMBAT) {
            this.field_179772_d = packetBuffer.readVarIntFromBuffer();
            this.field_179775_c = packetBuffer.readInt();
        }
        else if (this.field_179776_a == Event.ENTITY_DIED) {
            this.field_179774_b = packetBuffer.readVarIntFromBuffer();
            this.field_179775_c = packetBuffer.readInt();
            this.field_179773_e = packetBuffer.readStringFromBuffer(32767);
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.field_179776_a);
        if (this.field_179776_a == Event.END_COMBAT) {
            packetBuffer.writeVarIntToBuffer(this.field_179772_d);
            packetBuffer.writeInt(this.field_179775_c);
        }
        else if (this.field_179776_a == Event.ENTITY_DIED) {
            packetBuffer.writeVarIntToBuffer(this.field_179774_b);
            packetBuffer.writeInt(this.field_179775_c);
            packetBuffer.writeString(this.field_179773_e);
        }
    }
    
    public void func_179771_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.func_175098_a(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_179771_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00002299";
    }
    
    public enum Event
    {
        ENTER_COMBAT("ENTER_COMBAT", 0, "ENTER_COMBAT", 0), 
        END_COMBAT("END_COMBAT", 1, "END_COMBAT", 1), 
        ENTITY_DIED("ENTITY_DIED", 2, "ENTITY_DIED", 2);
        
        private static final Event[] $VALUES;
        private static final String __OBFID;
        private static final Event[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002297";
            ENUM$VALUES = new Event[] { Event.ENTER_COMBAT, Event.END_COMBAT, Event.ENTITY_DIED };
            $VALUES = new Event[] { Event.ENTER_COMBAT, Event.END_COMBAT, Event.ENTITY_DIED };
        }
        
        private Event(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    static final class SwitchEvent
    {
        static final int[] field_179944_a;
        private static final String __OBFID;
        private static final String[] lllIlIllllIIIll;
        private static String[] lllIlIllllIIlII;
        
        static {
            lIlIllIlllIIllll();
            lIlIllIlllIIlllI();
            __OBFID = SwitchEvent.lllIlIllllIIIll[0];
            field_179944_a = new int[Event.values().length];
            try {
                SwitchEvent.field_179944_a[Event.END_COMBAT.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEvent.field_179944_a[Event.ENTITY_DIED.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
        
        private static void lIlIllIlllIIlllI() {
            (lllIlIllllIIIll = new String[1])[0] = lIlIllIlllIIllIl(SwitchEvent.lllIlIllllIIlII[0], SwitchEvent.lllIlIllllIIlII[1]);
            SwitchEvent.lllIlIllllIIlII = null;
        }
        
        private static void lIlIllIlllIIllll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEvent.lllIlIllllIIlII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIlIllIlllIIllIl(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
                final Cipher instance = Cipher.getInstance("DES");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
