package net.minecraft.network.play.server;

import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S20PacketEntityProperties implements Packet
{
    private int field_149445_a;
    private final List field_149444_b;
    private static final String __OBFID;
    
    public S20PacketEntityProperties() {
        this.field_149444_b = Lists.newArrayList();
    }
    
    public S20PacketEntityProperties(final int field_149445_a, final Collection collection) {
        this.field_149444_b = Lists.newArrayList();
        this.field_149445_a = field_149445_a;
        for (final IAttributeInstance attributeInstance : collection) {
            this.field_149444_b.add(new Snapshot(attributeInstance.getAttribute().getAttributeUnlocalizedName(), attributeInstance.getBaseValue(), attributeInstance.func_111122_c()));
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149445_a = packetBuffer.readVarIntFromBuffer();
        while (0 < packetBuffer.readInt()) {
            final String stringFromBuffer = packetBuffer.readStringFromBuffer(64);
            final double double1 = packetBuffer.readDouble();
            final ArrayList arrayList = Lists.newArrayList();
            while (0 < packetBuffer.readVarIntFromBuffer()) {
                arrayList.add(new AttributeModifier(packetBuffer.readUuid(), "Unknown synced attribute modifier", packetBuffer.readDouble(), packetBuffer.readByte()));
                int n = 0;
                ++n;
            }
            this.field_149444_b.add(new Snapshot(stringFromBuffer, double1, arrayList));
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149445_a);
        packetBuffer.writeInt(this.field_149444_b.size());
        for (final Snapshot snapshot : this.field_149444_b) {
            packetBuffer.writeString(snapshot.func_151409_a());
            packetBuffer.writeDouble(snapshot.func_151410_b());
            packetBuffer.writeVarIntToBuffer(snapshot.func_151408_c().size());
            for (final AttributeModifier attributeModifier : snapshot.func_151408_c()) {
                packetBuffer.writeUuid(attributeModifier.getID());
                packetBuffer.writeDouble(attributeModifier.getAmount());
                packetBuffer.writeByte(attributeModifier.getOperation());
            }
        }
    }
    
    public void func_180754_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityProperties(this);
    }
    
    public int func_149442_c() {
        return this.field_149445_a;
    }
    
    public List func_149441_d() {
        return this.field_149444_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180754_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001341";
    }
    
    public class Snapshot
    {
        private final String field_151412_b;
        private final double field_151413_c;
        private final Collection field_151411_d;
        private static final String __OBFID;
        final S20PacketEntityProperties this$0;
        
        public Snapshot(final S20PacketEntityProperties this$0, final String field_151412_b, final double field_151413_c, final Collection field_151411_d) {
            this.this$0 = this$0;
            this.field_151412_b = field_151412_b;
            this.field_151413_c = field_151413_c;
            this.field_151411_d = field_151411_d;
        }
        
        public String func_151409_a() {
            return this.field_151412_b;
        }
        
        public double func_151410_b() {
            return this.field_151413_c;
        }
        
        public Collection func_151408_c() {
            return this.field_151411_d;
        }
        
        static {
            __OBFID = "CL_00001342";
        }
    }
}
