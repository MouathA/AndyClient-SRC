package net.minecraft.network.play.server;

import java.util.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S1CPacketEntityMetadata implements Packet
{
    private int field_149379_a;
    private List field_149378_b;
    private static final String __OBFID;
    
    public S1CPacketEntityMetadata() {
    }
    
    public S1CPacketEntityMetadata(final int field_149379_a, final DataWatcher dataWatcher, final boolean b) {
        this.field_149379_a = field_149379_a;
        if (b) {
            this.field_149378_b = dataWatcher.getAllWatched();
        }
        else {
            this.field_149378_b = dataWatcher.getChanged();
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149379_a = packetBuffer.readVarIntFromBuffer();
        this.field_149378_b = DataWatcher.readWatchedListFromPacketBuffer(packetBuffer);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149379_a);
        DataWatcher.writeWatchedListToPacketBuffer(this.field_149378_b, packetBuffer);
    }
    
    public void func_180748_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityMetadata(this);
    }
    
    public List func_149376_c() {
        return this.field_149378_b;
    }
    
    public int func_149375_d() {
        return this.field_149379_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180748_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001326";
    }
}
