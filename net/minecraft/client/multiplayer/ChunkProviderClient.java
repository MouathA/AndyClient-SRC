package net.minecraft.client.multiplayer;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ChunkProviderClient implements IChunkProvider
{
    private static final Logger logger;
    private Chunk blankChunk;
    private LongHashMap chunkMapping;
    private List chunkListing;
    private World worldObj;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000880";
        logger = LogManager.getLogger();
    }
    
    public ChunkProviderClient(final World worldObj) {
        this.chunkMapping = new LongHashMap();
        this.chunkListing = Lists.newArrayList();
        this.blankChunk = new EmptyChunk(worldObj, 0, 0);
        this.worldObj = worldObj;
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return true;
    }
    
    public void unloadChunk(final int n, final int n2) {
        final Chunk provideChunk = this.provideChunk(n, n2);
        if (!provideChunk.isEmpty()) {
            provideChunk.onChunkUnload();
        }
        this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(n, n2));
        this.chunkListing.remove(provideChunk);
    }
    
    public Chunk loadChunk(final int n, final int n2) {
        final Chunk chunk = new Chunk(this.worldObj, n, n2);
        this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(n, n2), chunk);
        this.chunkListing.add(chunk);
        chunk.func_177417_c(true);
        return chunk;
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        final Chunk chunk = (Chunk)this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(n, n2));
        return (chunk == null) ? this.blankChunk : chunk;
    }
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return true;
    }
    
    @Override
    public void saveExtraData() {
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        final long currentTimeMillis = System.currentTimeMillis();
        final Iterator<Chunk> iterator = this.chunkListing.iterator();
        while (iterator.hasNext()) {
            iterator.next().func_150804_b(System.currentTimeMillis() - currentTimeMillis > 5L);
        }
        if (System.currentTimeMillis() - currentTimeMillis > 100L) {
            ChunkProviderClient.logger.info("Warning: Clientside chunk ticking took {} ms", System.currentTimeMillis() - currentTimeMillis);
        }
        return false;
    }
    
    @Override
    public boolean canSave() {
        return false;
    }
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
    }
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        return false;
    }
    
    @Override
    public String makeString() {
        return "MultiplayerChunkCache: " + this.chunkMapping.getNumHashElements() + ", " + this.chunkListing.size();
    }
    
    @Override
    public List func_177458_a(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public BlockPos func_180513_a(final World world, final String s, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return this.chunkListing.size();
    }
    
    @Override
    public void func_180514_a(final Chunk chunk, final int n, final int n2) {
    }
    
    @Override
    public Chunk func_177459_a(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }
}
