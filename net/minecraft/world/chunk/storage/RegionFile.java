package net.minecraft.world.chunk.storage;

import java.util.*;
import com.google.common.collect.*;
import java.util.zip.*;
import net.minecraft.server.*;
import java.io.*;

public class RegionFile
{
    private static final byte[] emptySector;
    private final File fileName;
    private RandomAccessFile dataFile;
    private final int[] offsets;
    private final int[] chunkTimestamps;
    private List sectorFree;
    private int sizeDelta;
    private long lastModified;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000381";
        emptySector = new byte[4096];
    }
    
    public RegionFile(final File fileName) {
        this.offsets = new int[1024];
        this.chunkTimestamps = new int[1024];
        this.fileName = fileName;
        this.sizeDelta = 0;
        if (fileName.exists()) {
            this.lastModified = fileName.lastModified();
        }
        this.dataFile = new RandomAccessFile(fileName, "rw");
        int n = 0;
        if (this.dataFile.length() < 4096L) {
            while (0 < 1024) {
                this.dataFile.writeInt(0);
                ++n;
            }
            while (0 < 1024) {
                this.dataFile.writeInt(0);
                ++n;
            }
            this.sizeDelta += 8192;
        }
        if ((this.dataFile.length() & 0xFFFL) != 0x0L) {
            while (0 < (this.dataFile.length() & 0xFFFL)) {
                this.dataFile.write(0);
                ++n;
            }
        }
        n = (int)this.dataFile.length() / 4096;
        this.sectorFree = Lists.newArrayListWithCapacity(0);
        int n2 = 0;
        while (0 < 0) {
            this.sectorFree.add(true);
            ++n2;
        }
        this.sectorFree.set(0, false);
        this.sectorFree.set(1, false);
        this.dataFile.seek(0L);
        while (0 < 1024) {
            final int int1 = this.dataFile.readInt();
            this.offsets[0] = int1;
            if (int1 != 0 && (int1 >> 8) + (int1 & 0xFF) <= this.sectorFree.size()) {
                while (0 < (int1 & 0xFF)) {
                    this.sectorFree.set((int1 >> 8) + 0, false);
                    int n3 = 0;
                    ++n3;
                }
            }
            ++n2;
        }
        while (0 < 1024) {
            this.chunkTimestamps[0] = this.dataFile.readInt();
            ++n2;
        }
    }
    
    public synchronized DataInputStream getChunkDataInputStream(final int n, final int n2) {
        if (this.outOfBounds(n, n2)) {
            return null;
        }
        final int offset = this.getOffset(n, n2);
        if (offset == 0) {
            return null;
        }
        final int n3 = offset >> 8;
        final int n4 = offset & 0xFF;
        if (n3 + n4 > this.sectorFree.size()) {
            return null;
        }
        this.dataFile.seek(n3 * 4096);
        final int int1 = this.dataFile.readInt();
        if (int1 > 4096 * n4) {
            return null;
        }
        if (int1 <= 0) {
            return null;
        }
        final byte byte1 = this.dataFile.readByte();
        if (byte1 == 1) {
            final byte[] array = new byte[int1 - 1];
            this.dataFile.read(array);
            return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(array))));
        }
        if (byte1 == 2) {
            final byte[] array2 = new byte[int1 - 1];
            this.dataFile.read(array2);
            return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(array2))));
        }
        return null;
    }
    
    public DataOutputStream getChunkDataOutputStream(final int n, final int n2) {
        DataOutputStream dataOutputStream;
        if (this.outOfBounds(n, n2)) {
            dataOutputStream = null;
        }
        else {
            final DeflaterOutputStream deflaterOutputStream;
            dataOutputStream = new DataOutputStream(deflaterOutputStream);
            deflaterOutputStream = new DeflaterOutputStream(new ChunkBuffer(n, n2));
        }
        return dataOutputStream;
    }
    
    protected synchronized void write(final int n, final int n2, final byte[] array, final int n3) {
        final int n4 = this.getOffset(n, n2) & 0xFF;
        final int n5 = (n3 + 5) / 4096 + 1;
        if (n5 >= 256) {
            return;
        }
        if (false && n4 == n5) {
            this.write(0, array, n3);
        }
        else {
            while (0 < n4) {
                this.sectorFree.set(0, true);
                int index = 0;
                ++index;
            }
            int index = this.sectorFree.indexOf(true);
            int n7 = 0;
            if (0 != -1) {
                while (0 < this.sectorFree.size()) {
                    if (true) {
                        if (this.sectorFree.get(0)) {
                            int n6 = 0;
                            ++n6;
                        }
                    }
                    else if (this.sectorFree.get(0)) {}
                    if (1 >= n5) {
                        break;
                    }
                    ++n7;
                }
            }
            if (1 >= n5) {
                this.setOffset(n, n2, 0x0 | n5);
                while (0 < n5) {
                    this.sectorFree.set(0, false);
                    ++n7;
                }
                this.write(0, array, n3);
            }
            else {
                this.dataFile.seek(this.dataFile.length());
                this.sectorFree.size();
                while (0 < n5) {
                    this.dataFile.write(RegionFile.emptySector);
                    this.sectorFree.add(false);
                    ++n7;
                }
                this.sizeDelta += 4096 * n5;
                this.write(0, array, n3);
                this.setOffset(n, n2, 0x0 | n5);
            }
        }
        this.setChunkTimestamp(n, n2, (int)(MinecraftServer.getCurrentTimeMillis() / 1000L));
    }
    
    private void write(final int n, final byte[] array, final int n2) throws IOException {
        this.dataFile.seek(n * 4096);
        this.dataFile.writeInt(n2 + 1);
        this.dataFile.writeByte(2);
        this.dataFile.write(array, 0, n2);
    }
    
    private boolean outOfBounds(final int n, final int n2) {
        return n < 0 || n >= 32 || n2 < 0 || n2 >= 32;
    }
    
    private int getOffset(final int n, final int n2) {
        return this.offsets[n + n2 * 32];
    }
    
    public boolean isChunkSaved(final int n, final int n2) {
        return this.getOffset(n, n2) != 0;
    }
    
    private void setOffset(final int n, final int n2, final int n3) throws IOException {
        this.offsets[n + n2 * 32] = n3;
        this.dataFile.seek((n + n2 * 32) * 4);
        this.dataFile.writeInt(n3);
    }
    
    private void setChunkTimestamp(final int n, final int n2, final int n3) throws IOException {
        this.chunkTimestamps[n + n2 * 32] = n3;
        this.dataFile.seek(4096 + (n + n2 * 32) * 4);
        this.dataFile.writeInt(n3);
    }
    
    public void close() throws IOException {
        if (this.dataFile != null) {
            this.dataFile.close();
        }
    }
    
    class ChunkBuffer extends ByteArrayOutputStream
    {
        private int chunkX;
        private int chunkZ;
        private static final String __OBFID;
        final RegionFile this$0;
        
        public ChunkBuffer(final RegionFile this$0, final int chunkX, final int chunkZ) {
            this.this$0 = this$0;
            super(8096);
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
        }
        
        @Override
        public void close() throws IOException {
            this.this$0.write(this.chunkX, this.chunkZ, this.buf, this.count);
        }
        
        static {
            __OBFID = "CL_00000382";
        }
    }
}
