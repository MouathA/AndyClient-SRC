package com.viaversion.viaversion.protocols.protocol1_9to1_8.types;

import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.types.version.*;

public class Chunk1_8Type extends PartialType
{
    public Chunk1_8Type(final ClientWorld clientWorld) {
        super(clientWorld, Chunk.class);
    }
    
    @Override
    public Class getBaseClass() {
        return BaseChunkType.class;
    }
    
    public Chunk read(final ByteBuf byteBuf, final ClientWorld clientWorld) throws Exception {
        final int int1 = byteBuf.readInt();
        final int int2 = byteBuf.readInt();
        final boolean boolean1 = byteBuf.readBoolean();
        final int unsignedShort = byteBuf.readUnsignedShort();
        final byte[] array = new byte[Type.VAR_INT.readPrimitive(byteBuf)];
        byteBuf.readBytes(array);
        if (boolean1 && unsignedShort == 0) {
            return new BaseChunk(int1, int2, true, false, 0, new ChunkSection[16], null, new ArrayList());
        }
        return deserialize(int1, int2, boolean1, clientWorld.getEnvironment() == Environment.NORMAL, unsignedShort, array);
    }
    
    public void write(final ByteBuf byteBuf, final ClientWorld clientWorld, final Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        byteBuf.writeBoolean(chunk.isFullChunk());
        byteBuf.writeShort(chunk.getBitmask());
        final byte[] serialize = serialize(chunk);
        Type.VAR_INT.writePrimitive(byteBuf, serialize.length);
        byteBuf.writeBytes(serialize);
    }
    
    public static Chunk deserialize(final int n, final int n2, final boolean b, final boolean b2, final int n3, final byte[] array) throws Exception {
        final ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(array);
        final ChunkSection[] array2 = new ChunkSection[16];
        int[] array3 = null;
        int n4 = 0;
        while (0 < array2.length) {
            if ((n3 & 0x1) != 0x0) {
                array2[0] = (ChunkSection)Types1_8.CHUNK_SECTION.read(wrappedBuffer);
            }
            ++n4;
        }
        while (0 < array2.length) {
            if ((n3 & 0x1) != 0x0) {
                array2[0].getLight().readBlockLight(wrappedBuffer);
            }
            ++n4;
        }
        if (b2) {
            while (0 < array2.length) {
                if ((n3 & 0x1) != 0x0) {
                    array2[0].getLight().readSkyLight(wrappedBuffer);
                }
                ++n4;
            }
        }
        if (b) {
            array3 = new int[256];
            while (0 < 256) {
                array3[0] = wrappedBuffer.readUnsignedByte();
                ++n4;
            }
        }
        wrappedBuffer.release();
        return new BaseChunk(n, n2, b, false, n3, array2, array3, new ArrayList());
    }
    
    public static byte[] serialize(final Chunk chunk) throws Exception {
        final ByteBuf buffer = Unpooled.buffer();
        int n = 0;
        while (0 < chunk.getSections().length) {
            if ((chunk.getBitmask() & 0x1) != 0x0) {
                Types1_8.CHUNK_SECTION.write(buffer, chunk.getSections()[0]);
            }
            ++n;
        }
        while (0 < chunk.getSections().length) {
            if ((chunk.getBitmask() & 0x1) != 0x0) {
                chunk.getSections()[0].getLight().writeBlockLight(buffer);
            }
            ++n;
        }
        while (0 < chunk.getSections().length) {
            if ((chunk.getBitmask() & 0x1) != 0x0) {
                if (chunk.getSections()[0].getLight().hasSkyLight()) {
                    chunk.getSections()[0].getLight().writeSkyLight(buffer);
                }
            }
            ++n;
        }
        if (chunk.isFullChunk() && chunk.getBiomeData() != null) {
            final int[] biomeData = chunk.getBiomeData();
            while (0 < biomeData.length) {
                buffer.writeByte((byte)biomeData[0]);
                int n2 = 0;
                ++n2;
            }
        }
        final byte[] array = new byte[buffer.readableBytes()];
        buffer.readBytes(array);
        buffer.release();
        return array;
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o, final Object o2) throws Exception {
        this.write(byteBuf, (ClientWorld)o, (Chunk)o2);
    }
    
    @Override
    public Object read(final ByteBuf byteBuf, final Object o) throws Exception {
        return this.read(byteBuf, (ClientWorld)o);
    }
}
