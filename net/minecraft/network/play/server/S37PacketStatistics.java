package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import com.google.common.collect.*;
import net.minecraft.stats.*;
import java.io.*;
import java.util.*;
import net.minecraft.network.*;

public class S37PacketStatistics implements Packet
{
    private Map field_148976_a;
    private static final String __OBFID;
    
    public S37PacketStatistics() {
    }
    
    public S37PacketStatistics(final Map field_148976_a) {
        this.field_148976_a = field_148976_a;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleStatistics(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
        this.field_148976_a = Maps.newHashMap();
        while (0 < varIntFromBuffer) {
            final StatBase oneShotStat = StatList.getOneShotStat(packetBuffer.readStringFromBuffer(32767));
            final int varIntFromBuffer2 = packetBuffer.readVarIntFromBuffer();
            if (oneShotStat != null) {
                this.field_148976_a.put(oneShotStat, varIntFromBuffer2);
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_148976_a.size());
        for (final Map.Entry<StatBase, V> entry : this.field_148976_a.entrySet()) {
            packetBuffer.writeString(entry.getKey().statId);
            packetBuffer.writeVarIntToBuffer((int)entry.getValue());
        }
    }
    
    public Map func_148974_c() {
        return this.field_148976_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001283";
    }
}
