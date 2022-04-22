package net.minecraft.network.play.server;

import java.util.*;
import net.minecraft.world.chunk.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S26PacketMapChunkBulk implements Packet
{
    private int[] field_149266_a;
    private int[] field_149264_b;
    private S21PacketChunkData.Extracted[] field_179755_c;
    private boolean field_149267_h;
    private static final String __OBFID;
    
    public S26PacketMapChunkBulk() {
    }
    
    public S26PacketMapChunkBulk(final List list) {
        final int size = list.size();
        this.field_149266_a = new int[size];
        this.field_149264_b = new int[size];
        this.field_179755_c = new S21PacketChunkData.Extracted[size];
        this.field_149267_h = !list.get(0).getWorld().provider.getHasNoSky();
        while (0 < size) {
            final Chunk chunk = list.get(0);
            final S21PacketChunkData.Extracted func_179756_a = S21PacketChunkData.func_179756_a(chunk, true, this.field_149267_h, 65535);
            this.field_149266_a[0] = chunk.xPosition;
            this.field_149264_b[0] = chunk.zPosition;
            this.field_179755_c[0] = func_179756_a;
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149267_h = packetBuffer.readBoolean();
        final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
        this.field_149266_a = new int[varIntFromBuffer];
        this.field_149264_b = new int[varIntFromBuffer];
        this.field_179755_c = new S21PacketChunkData.Extracted[varIntFromBuffer];
        int n = 0;
        while (0 < varIntFromBuffer) {
            this.field_149266_a[0] = packetBuffer.readInt();
            this.field_149264_b[0] = packetBuffer.readInt();
            this.field_179755_c[0] = new S21PacketChunkData.Extracted();
            this.field_179755_c[0].field_150280_b = (packetBuffer.readShort() & 0xFFFF);
            this.field_179755_c[0].field_150282_a = new byte[S21PacketChunkData.func_180737_a(Integer.bitCount(this.field_179755_c[0].field_150280_b), this.field_149267_h, true)];
            ++n;
        }
        while (0 < varIntFromBuffer) {
            packetBuffer.readBytes(this.field_179755_c[0].field_150282_a);
            ++n;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBoolean(this.field_149267_h);
        packetBuffer.writeVarIntToBuffer(this.field_179755_c.length);
        int n = 0;
        while (0 < this.field_149266_a.length) {
            packetBuffer.writeInt(this.field_149266_a[0]);
            packetBuffer.writeInt(this.field_149264_b[0]);
            packetBuffer.writeShort((short)(this.field_179755_c[0].field_150280_b & 0xFFFF));
            ++n;
        }
        while (0 < this.field_149266_a.length) {
            packetBuffer.writeBytes(this.field_179755_c[0].field_150282_a);
            ++n;
        }
    }
    
    public void func_180738_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleMapChunkBulk(this);
    }
    
    public int func_149255_a(final int n) {
        return this.field_149266_a[n];
    }
    
    public int func_149253_b(final int n) {
        return this.field_149264_b[n];
    }
    
    public int func_149254_d() {
        return this.field_149266_a.length;
    }
    
    public byte[] func_149256_c(final int n) {
        return this.field_179755_c[n].field_150282_a;
    }
    
    public int func_179754_d(final int n) {
        return this.field_179755_c[n].field_150280_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180738_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001306";
    }
}
