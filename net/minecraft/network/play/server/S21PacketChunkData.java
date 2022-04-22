package net.minecraft.network.play.server;

import net.minecraft.world.chunk.*;
import java.io.*;
import net.minecraft.network.play.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.storage.*;
import java.util.*;
import net.minecraft.network.*;

public class S21PacketChunkData implements Packet
{
    private int field_149284_a;
    private int field_149282_b;
    private Extracted field_179758_c;
    private boolean field_149279_g;
    private static final String __OBFID;
    
    public S21PacketChunkData() {
    }
    
    public S21PacketChunkData(final Chunk chunk, final boolean field_149279_g, final int n) {
        this.field_149284_a = chunk.xPosition;
        this.field_149282_b = chunk.zPosition;
        this.field_149279_g = field_149279_g;
        this.field_179758_c = func_179756_a(chunk, field_149279_g, !chunk.getWorld().provider.getHasNoSky(), n);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149284_a = packetBuffer.readInt();
        this.field_149282_b = packetBuffer.readInt();
        this.field_149279_g = packetBuffer.readBoolean();
        this.field_179758_c = new Extracted();
        this.field_179758_c.field_150280_b = packetBuffer.readShort();
        this.field_179758_c.field_150282_a = packetBuffer.readByteArray();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.field_149284_a);
        packetBuffer.writeInt(this.field_149282_b);
        packetBuffer.writeBoolean(this.field_149279_g);
        packetBuffer.writeShort((short)(this.field_179758_c.field_150280_b & 0xFFFF));
        packetBuffer.writeByteArray(this.field_179758_c.field_150282_a);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleChunkData(this);
    }
    
    public byte[] func_149272_d() {
        return this.field_179758_c.field_150282_a;
    }
    
    protected static int func_180737_a(final int n, final boolean b, final boolean b2) {
        return n * 2 * 16 * 16 * 16 + n * 16 * 16 * 16 / 2 + (b ? (n * 16 * 16 * 16 / 2) : 0) + (b2 ? 256 : 0);
    }
    
    public static Extracted func_179756_a(final Chunk chunk, final boolean b, final boolean b2, final int n) {
        final ExtendedBlockStorage[] blockStorageArray = chunk.getBlockStorageArray();
        final Extracted extracted = new Extracted();
        final ArrayList arrayList = Lists.newArrayList();
        int n2 = 0;
        while (0 < blockStorageArray.length) {
            final ExtendedBlockStorage extendedBlockStorage = blockStorageArray[0];
            if (extendedBlockStorage != null && (!b || !extendedBlockStorage.isEmpty()) && (n & 0x1) != 0x0) {
                final Extracted extracted2 = extracted;
                extracted2.field_150280_b |= 0x1;
                arrayList.add(extendedBlockStorage);
            }
            ++n2;
        }
        extracted.field_150282_a = new byte[func_180737_a(Integer.bitCount(extracted.field_150280_b), b2, b)];
        final Iterator<ExtendedBlockStorage> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            char[] data;
            while (0 < (data = iterator.next().getData()).length) {
                final char c = data[0];
                final byte[] field_150282_a = extracted.field_150282_a;
                final int n3 = 0;
                ++n2;
                field_150282_a[n3] = (byte)(c & '\u00ff');
                final byte[] field_150282_a2 = extracted.field_150282_a;
                final int n4 = 0;
                ++n2;
                field_150282_a2[n4] = (byte)(c >> 8 & 0xFF);
                int n5 = 0;
                ++n5;
            }
        }
        final Iterator<ExtendedBlockStorage> iterator2 = arrayList.iterator();
        while (iterator2.hasNext()) {
            n2 = func_179757_a(iterator2.next().getBlocklightArray().getData(), extracted.field_150282_a, 0);
        }
        if (b2) {
            final Iterator<ExtendedBlockStorage> iterator3 = arrayList.iterator();
            while (iterator3.hasNext()) {
                n2 = func_179757_a(iterator3.next().getSkylightArray().getData(), extracted.field_150282_a, 0);
            }
        }
        if (b) {
            func_179757_a(chunk.getBiomeArray(), extracted.field_150282_a, 0);
        }
        return extracted;
    }
    
    private static int func_179757_a(final byte[] array, final byte[] array2, final int n) {
        System.arraycopy(array, 0, array2, n, array.length);
        return n + array.length;
    }
    
    public int func_149273_e() {
        return this.field_149284_a;
    }
    
    public int func_149271_f() {
        return this.field_149282_b;
    }
    
    public int func_149276_g() {
        return this.field_179758_c.field_150280_b;
    }
    
    public boolean func_149274_i() {
        return this.field_149279_g;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001304";
    }
    
    public static class Extracted
    {
        public byte[] field_150282_a;
        public int field_150280_b;
        private static final String __OBFID;
        
        static {
            __OBFID = "CL_00001305";
        }
    }
}
