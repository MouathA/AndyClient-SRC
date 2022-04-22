package com.viaversion.viaversion.protocols.protocol1_9to1_8.types;

import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;

public class ChunkBulk1_8Type extends PartialType
{
    private static final int BLOCKS_PER_SECTION = 4096;
    private static final int BLOCKS_BYTES = 8192;
    private static final int LIGHT_BYTES = 2048;
    private static final int BIOME_BYTES = 256;
    
    public ChunkBulk1_8Type(final ClientWorld clientWorld) {
        super(clientWorld, Chunk[].class);
    }
    
    @Override
    public Class getBaseClass() {
        return BaseChunkBulkType.class;
    }
    
    public Chunk[] read(final ByteBuf byteBuf, final ClientWorld clientWorld) throws Exception {
        final boolean boolean1 = byteBuf.readBoolean();
        final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        final Chunk[] array = new Chunk[primitive];
        final ChunkBulkSection[] array2 = new ChunkBulkSection[primitive];
        int n = 0;
        while (0 < array2.length) {
            array2[0] = new ChunkBulkSection(byteBuf, boolean1);
            ++n;
        }
        while (0 < array.length) {
            final ChunkBulkSection chunkBulkSection = array2[0];
            chunkBulkSection.readData(byteBuf);
            array[0] = Chunk1_8Type.deserialize(ChunkBulkSection.access$000(chunkBulkSection), ChunkBulkSection.access$100(chunkBulkSection), true, boolean1, ChunkBulkSection.access$200(chunkBulkSection), chunkBulkSection.getData());
            ++n;
        }
        return array;
    }
    
    public void write(final ByteBuf byteBuf, final ClientWorld clientWorld, final Chunk[] array) throws Exception {
        int n2 = 0;
    Label_0081:
        while (0 < array.length) {
            final ChunkSection[] sections = array[0].getSections();
            while (0 < sections.length) {
                final ChunkSection chunkSection = sections[0];
                if (chunkSection != null && chunkSection.getLight().hasSkyLight()) {
                    break Label_0081;
                }
                int n = 0;
                ++n;
            }
            ++n2;
        }
        byteBuf.writeBoolean(true);
        Type.VAR_INT.writePrimitive(byteBuf, array.length);
        while (0 < array.length) {
            final Chunk chunk = array[0];
            byteBuf.writeInt(chunk.getX());
            byteBuf.writeInt(chunk.getZ());
            byteBuf.writeShort(chunk.getBitmask());
            ++n2;
        }
        while (0 < array.length) {
            byteBuf.writeBytes(Chunk1_8Type.serialize(array[0]));
            ++n2;
        }
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o, final Object o2) throws Exception {
        this.write(byteBuf, (ClientWorld)o, (Chunk[])o2);
    }
    
    @Override
    public Object read(final ByteBuf byteBuf, final Object o) throws Exception {
        return this.read(byteBuf, (ClientWorld)o);
    }
    
    public static final class ChunkBulkSection
    {
        private final int chunkX;
        private final int chunkZ;
        private final int bitmask;
        private final byte[] data;
        
        public ChunkBulkSection(final ByteBuf byteBuf, final boolean b) {
            this.chunkX = byteBuf.readInt();
            this.chunkZ = byteBuf.readInt();
            this.bitmask = byteBuf.readUnsignedShort();
            this.data = new byte[Integer.bitCount(this.bitmask) * (8192 + (b ? 4096 : 2048)) + 256];
        }
        
        public void readData(final ByteBuf byteBuf) {
            byteBuf.readBytes(this.data);
        }
        
        public int getChunkX() {
            return this.chunkX;
        }
        
        public int getChunkZ() {
            return this.chunkZ;
        }
        
        public int getBitmask() {
            return this.bitmask;
        }
        
        public byte[] getData() {
            return this.data;
        }
        
        static int access$000(final ChunkBulkSection chunkBulkSection) {
            return chunkBulkSection.chunkX;
        }
        
        static int access$100(final ChunkBulkSection chunkBulkSection) {
            return chunkBulkSection.chunkZ;
        }
        
        static int access$200(final ChunkBulkSection chunkBulkSection) {
            return chunkBulkSection.bitmask;
        }
    }
}
