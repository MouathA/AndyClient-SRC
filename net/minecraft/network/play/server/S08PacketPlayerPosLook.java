package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.util.*;

public class S08PacketPlayerPosLook implements Packet
{
    private double field_148940_a;
    private double field_148938_b;
    private double field_148939_c;
    private float field_148936_d;
    private float field_148937_e;
    private Set field_179835_f;
    private static final String __OBFID;
    
    public S08PacketPlayerPosLook() {
    }
    
    public S08PacketPlayerPosLook(final double field_148940_a, final double field_148938_b, final double field_148939_c, final float field_148936_d, final float field_148937_e, final Set field_179835_f) {
        this.field_148940_a = field_148940_a;
        this.field_148938_b = field_148938_b;
        this.field_148939_c = field_148939_c;
        this.field_148936_d = field_148936_d;
        this.field_148937_e = field_148937_e;
        this.field_179835_f = field_179835_f;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_148940_a = packetBuffer.readDouble();
        this.field_148938_b = packetBuffer.readDouble();
        this.field_148939_c = packetBuffer.readDouble();
        this.field_148936_d = packetBuffer.readFloat();
        this.field_148937_e = packetBuffer.readFloat();
        this.field_179835_f = EnumFlags.func_180053_a(packetBuffer.readUnsignedByte());
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeDouble(this.field_148940_a);
        packetBuffer.writeDouble(this.field_148938_b);
        packetBuffer.writeDouble(this.field_148939_c);
        packetBuffer.writeFloat(this.field_148936_d);
        packetBuffer.writeFloat(this.field_148937_e);
        packetBuffer.writeByte(EnumFlags.func_180056_a(this.field_179835_f));
    }
    
    public void func_180718_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handlePlayerPosLook(this);
    }
    
    public double func_148932_c() {
        return this.field_148940_a;
    }
    
    public double func_148928_d() {
        return this.field_148938_b;
    }
    
    public double func_148933_e() {
        return this.field_148939_c;
    }
    
    public float func_148931_f() {
        return this.field_148936_d;
    }
    
    public float func_148930_g() {
        return this.field_148937_e;
    }
    
    public Set func_179834_f() {
        return this.field_179835_f;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180718_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001273";
    }
    
    public enum EnumFlags
    {
        X("X", 0, "X", 0, 0), 
        Y("Y", 1, "Y", 1, 1), 
        Z("Z", 2, "Z", 2, 2), 
        Y_ROT("Y_ROT", 3, "Y_ROT", 3, 3), 
        X_ROT("X_ROT", 4, "X_ROT", 4, 4);
        
        private int field_180058_f;
        private static final EnumFlags[] $VALUES;
        private static final String __OBFID;
        private static final EnumFlags[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002304";
            ENUM$VALUES = new EnumFlags[] { EnumFlags.X, EnumFlags.Y, EnumFlags.Z, EnumFlags.Y_ROT, EnumFlags.X_ROT };
            $VALUES = new EnumFlags[] { EnumFlags.X, EnumFlags.Y, EnumFlags.Z, EnumFlags.Y_ROT, EnumFlags.X_ROT };
        }
        
        private EnumFlags(final String s, final int n, final String s2, final int n2, final int field_180058_f) {
            this.field_180058_f = field_180058_f;
        }
        
        private int func_180055_a() {
            return 1 << this.field_180058_f;
        }
        
        private boolean func_180054_b(final int n) {
            return (n & this.func_180055_a()) == this.func_180055_a();
        }
        
        public static Set func_180053_a(final int n) {
            final EnumSet<EnumFlags> none = EnumSet.noneOf(EnumFlags.class);
            final EnumFlags[] values = values();
            while (0 < values.length) {
                final EnumFlags enumFlags = values[0];
                if (enumFlags.func_180054_b(n)) {
                    none.add(enumFlags);
                }
                int n2 = 0;
                ++n2;
            }
            return none;
        }
        
        public static int func_180056_a(final Set set) {
            final Iterator<EnumFlags> iterator = set.iterator();
            while (iterator.hasNext()) {
                final int n = 0x0 | iterator.next().func_180055_a();
            }
            return 0;
        }
    }
}
