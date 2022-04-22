package net.minecraft.server.management;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.tileentity.*;

public class PlayerManager
{
    private static final Logger field_152627_a;
    private final WorldServer theWorldServer;
    private final List players;
    private final LongHashMap playerInstances;
    private final List playerInstancesToUpdate;
    private final List playerInstanceList;
    private int playerViewRadius;
    private long previousTotalWorldTime;
    private final int[][] xzDirectionsConst;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001434";
        field_152627_a = LogManager.getLogger();
    }
    
    public PlayerManager(final WorldServer theWorldServer) {
        this.players = Lists.newArrayList();
        this.playerInstances = new LongHashMap();
        this.playerInstancesToUpdate = Lists.newArrayList();
        this.playerInstanceList = Lists.newArrayList();
        this.xzDirectionsConst = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        this.theWorldServer = theWorldServer;
        this.func_152622_a(theWorldServer.func_73046_m().getConfigurationManager().getViewDistance());
    }
    
    public WorldServer getMinecraftServer() {
        return this.theWorldServer;
    }
    
    public void updatePlayerInstances() {
        final long totalWorldTime = this.theWorldServer.getTotalWorldTime();
        if (totalWorldTime - this.previousTotalWorldTime > 8000L) {
            this.previousTotalWorldTime = totalWorldTime;
            while (0 < this.playerInstanceList.size()) {
                final PlayerInstance playerInstance = this.playerInstanceList.get(0);
                playerInstance.onUpdate();
                playerInstance.processChunk();
                int n = 0;
                ++n;
            }
        }
        else {
            while (0 < this.playerInstancesToUpdate.size()) {
                this.playerInstancesToUpdate.get(0).onUpdate();
                int n = 0;
                ++n;
            }
        }
        this.playerInstancesToUpdate.clear();
        if (this.players.isEmpty() && !this.theWorldServer.provider.canRespawnHere()) {
            this.theWorldServer.theChunkProviderServer.unloadAllChunks();
        }
    }
    
    public boolean func_152621_a(final int n, final int n2) {
        return this.playerInstances.getValueByKey(n + 2147483647L | n2 + 2147483647L << 32) != null;
    }
    
    private PlayerInstance getPlayerInstance(final int n, final int n2, final boolean b) {
        final long n3 = n + 2147483647L | n2 + 2147483647L << 32;
        PlayerInstance playerInstance = (PlayerInstance)this.playerInstances.getValueByKey(n3);
        if (playerInstance == null && b) {
            playerInstance = new PlayerInstance(n, n2);
            this.playerInstances.add(n3, playerInstance);
            this.playerInstanceList.add(playerInstance);
        }
        return playerInstance;
    }
    
    public void func_180244_a(final BlockPos blockPos) {
        final PlayerInstance playerInstance = this.getPlayerInstance(blockPos.getX() >> 4, blockPos.getZ() >> 4, false);
        if (playerInstance != null) {
            playerInstance.flagChunkForUpdate(blockPos.getX() & 0xF, blockPos.getY(), blockPos.getZ() & 0xF);
        }
    }
    
    public void addPlayer(final EntityPlayerMP entityPlayerMP) {
        final int n = (int)entityPlayerMP.posX >> 4;
        final int n2 = (int)entityPlayerMP.posZ >> 4;
        entityPlayerMP.managedPosX = entityPlayerMP.posX;
        entityPlayerMP.managedPosZ = entityPlayerMP.posZ;
        for (int i = n - this.playerViewRadius; i <= n + this.playerViewRadius; ++i) {
            for (int j = n2 - this.playerViewRadius; j <= n2 + this.playerViewRadius; ++j) {
                this.getPlayerInstance(i, j, true).addPlayer(entityPlayerMP);
            }
        }
        this.players.add(entityPlayerMP);
        this.filterChunkLoadQueue(entityPlayerMP);
    }
    
    public void filterChunkLoadQueue(final EntityPlayerMP entityPlayerMP) {
        final ArrayList arrayList = Lists.newArrayList(entityPlayerMP.loadedChunks);
        final int playerViewRadius = this.playerViewRadius;
        final int n = (int)entityPlayerMP.posX >> 4;
        final int n2 = (int)entityPlayerMP.posZ >> 4;
        final ChunkCoordIntPair access$0 = PlayerInstance.access$0(this.getPlayerInstance(n, n2, true));
        entityPlayerMP.loadedChunks.clear();
        if (arrayList.contains(access$0)) {
            entityPlayerMP.loadedChunks.add(access$0);
        }
        int n9 = 0;
        while (0 <= playerViewRadius * 2) {
            while (0 < 2) {
                final int[][] xzDirectionsConst = this.xzDirectionsConst;
                final int n3 = 0;
                int n4 = 0;
                ++n4;
                final int[] array = xzDirectionsConst[n3 % 4];
                while (0 < 0) {
                    final int n5 = 0 + array[0];
                    final int n6 = 0 + array[1];
                    final ChunkCoordIntPair access$2 = PlayerInstance.access$0(this.getPlayerInstance(n + 0, n2 + 0, true));
                    if (arrayList.contains(access$2)) {
                        entityPlayerMP.loadedChunks.add(access$2);
                    }
                    int n7 = 0;
                    ++n7;
                }
                int n8 = 0;
                ++n8;
            }
            ++n9;
        }
        while (0 < playerViewRadius * 2) {
            final int n10 = 0 + this.xzDirectionsConst[0][0];
            final int n11 = 0 + this.xzDirectionsConst[0][1];
            final ChunkCoordIntPair access$3 = PlayerInstance.access$0(this.getPlayerInstance(n + 0, n2 + 0, true));
            if (arrayList.contains(access$3)) {
                entityPlayerMP.loadedChunks.add(access$3);
            }
            ++n9;
        }
    }
    
    public void removePlayer(final EntityPlayerMP entityPlayerMP) {
        final int n = (int)entityPlayerMP.managedPosX >> 4;
        final int n2 = (int)entityPlayerMP.managedPosZ >> 4;
        for (int i = n - this.playerViewRadius; i <= n + this.playerViewRadius; ++i) {
            for (int j = n2 - this.playerViewRadius; j <= n2 + this.playerViewRadius; ++j) {
                final PlayerInstance playerInstance = this.getPlayerInstance(i, j, false);
                if (playerInstance != null) {
                    playerInstance.removePlayer(entityPlayerMP);
                }
            }
        }
        this.players.remove(entityPlayerMP);
    }
    
    private boolean overlaps(final int n, final int n2, final int n3, final int n4, final int n5) {
        final int n6 = n - n3;
        final int n7 = n2 - n4;
        return n6 >= -n5 && n6 <= n5 && (n7 >= -n5 && n7 <= n5);
    }
    
    public void updateMountedMovingPlayer(final EntityPlayerMP entityPlayerMP) {
        final int n = (int)entityPlayerMP.posX >> 4;
        final int n2 = (int)entityPlayerMP.posZ >> 4;
        final double n3 = entityPlayerMP.managedPosX - entityPlayerMP.posX;
        final double n4 = entityPlayerMP.managedPosZ - entityPlayerMP.posZ;
        if (n3 * n3 + n4 * n4 >= 64.0) {
            final int n5 = (int)entityPlayerMP.managedPosX >> 4;
            final int n6 = (int)entityPlayerMP.managedPosZ >> 4;
            final int playerViewRadius = this.playerViewRadius;
            final int n7 = n - n5;
            final int n8 = n2 - n6;
            if (n7 != 0 || n8 != 0) {
                for (int i = n - playerViewRadius; i <= n + playerViewRadius; ++i) {
                    for (int j = n2 - playerViewRadius; j <= n2 + playerViewRadius; ++j) {
                        if (!this.overlaps(i, j, n5, n6, playerViewRadius)) {
                            this.getPlayerInstance(i, j, true).addPlayer(entityPlayerMP);
                        }
                        if (!this.overlaps(i - n7, j - n8, n, n2, playerViewRadius)) {
                            final PlayerInstance playerInstance = this.getPlayerInstance(i - n7, j - n8, false);
                            if (playerInstance != null) {
                                playerInstance.removePlayer(entityPlayerMP);
                            }
                        }
                    }
                }
                this.filterChunkLoadQueue(entityPlayerMP);
                entityPlayerMP.managedPosX = entityPlayerMP.posX;
                entityPlayerMP.managedPosZ = entityPlayerMP.posZ;
            }
        }
    }
    
    public boolean isPlayerWatchingChunk(final EntityPlayerMP entityPlayerMP, final int n, final int n2) {
        final PlayerInstance playerInstance = this.getPlayerInstance(n, n2, false);
        return playerInstance != null && PlayerInstance.access$1(playerInstance).contains(entityPlayerMP) && !entityPlayerMP.loadedChunks.contains(PlayerInstance.access$0(playerInstance));
    }
    
    public void func_152622_a(int clamp_int) {
        clamp_int = MathHelper.clamp_int(clamp_int, 3, 32);
        if (clamp_int != this.playerViewRadius) {
            final int n = clamp_int - this.playerViewRadius;
            for (final EntityPlayerMP entityPlayerMP : Lists.newArrayList(this.players)) {
                final int n2 = (int)entityPlayerMP.posX >> 4;
                final int n3 = (int)entityPlayerMP.posZ >> 4;
                if (n > 0) {
                    for (int i = n2 - clamp_int; i <= n2 + clamp_int; ++i) {
                        for (int j = n3 - clamp_int; j <= n3 + clamp_int; ++j) {
                            final PlayerInstance playerInstance = this.getPlayerInstance(i, j, true);
                            if (!PlayerInstance.access$1(playerInstance).contains(entityPlayerMP)) {
                                playerInstance.addPlayer(entityPlayerMP);
                            }
                        }
                    }
                }
                else {
                    for (int k = n2 - this.playerViewRadius; k <= n2 + this.playerViewRadius; ++k) {
                        for (int l = n3 - this.playerViewRadius; l <= n3 + this.playerViewRadius; ++l) {
                            if (!this.overlaps(k, l, n2, n3, clamp_int)) {
                                this.getPlayerInstance(k, l, true).removePlayer(entityPlayerMP);
                            }
                        }
                    }
                }
            }
            this.playerViewRadius = clamp_int;
        }
    }
    
    public static int getFurthestViewableBlock(final int n) {
        return n * 16 - 16;
    }
    
    static Logger access$0() {
        return PlayerManager.field_152627_a;
    }
    
    static WorldServer access$1(final PlayerManager playerManager) {
        return playerManager.theWorldServer;
    }
    
    static LongHashMap access$2(final PlayerManager playerManager) {
        return playerManager.playerInstances;
    }
    
    static List access$3(final PlayerManager playerManager) {
        return playerManager.playerInstanceList;
    }
    
    static List access$4(final PlayerManager playerManager) {
        return playerManager.playerInstancesToUpdate;
    }
    
    class PlayerInstance
    {
        private final List playersWatchingChunk;
        private final ChunkCoordIntPair currentChunk;
        private short[] locationOfBlockChange;
        private int numBlocksToUpdate;
        private int flagsYAreasToUpdate;
        private long previousWorldTime;
        private static final String __OBFID;
        final PlayerManager this$0;
        
        public PlayerInstance(final PlayerManager this$0, final int n, final int n2) {
            this.this$0 = this$0;
            this.playersWatchingChunk = Lists.newArrayList();
            this.locationOfBlockChange = new short[64];
            this.currentChunk = new ChunkCoordIntPair(n, n2);
            this$0.getMinecraftServer().theChunkProviderServer.loadChunk(n, n2);
        }
        
        public void addPlayer(final EntityPlayerMP entityPlayerMP) {
            if (this.playersWatchingChunk.contains(entityPlayerMP)) {
                PlayerManager.access$0().debug("Failed to add player. {} already is in chunk {}, {}", entityPlayerMP, this.currentChunk.chunkXPos, this.currentChunk.chunkZPos);
            }
            else {
                if (this.playersWatchingChunk.isEmpty()) {
                    this.previousWorldTime = PlayerManager.access$1(this.this$0).getTotalWorldTime();
                }
                this.playersWatchingChunk.add(entityPlayerMP);
                entityPlayerMP.loadedChunks.add(this.currentChunk);
            }
        }
        
        public void removePlayer(final EntityPlayerMP entityPlayerMP) {
            if (this.playersWatchingChunk.contains(entityPlayerMP)) {
                final Chunk chunkFromChunkCoords = PlayerManager.access$1(this.this$0).getChunkFromChunkCoords(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos);
                if (chunkFromChunkCoords.isPopulated()) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(new S21PacketChunkData(chunkFromChunkCoords, true, 0));
                }
                this.playersWatchingChunk.remove(entityPlayerMP);
                entityPlayerMP.loadedChunks.remove(this.currentChunk);
                if (this.playersWatchingChunk.isEmpty()) {
                    final long n = this.currentChunk.chunkXPos + 2147483647L | this.currentChunk.chunkZPos + 2147483647L << 32;
                    this.increaseInhabitedTime(chunkFromChunkCoords);
                    PlayerManager.access$2(this.this$0).remove(n);
                    PlayerManager.access$3(this.this$0).remove(this);
                    if (this.numBlocksToUpdate > 0) {
                        PlayerManager.access$4(this.this$0).remove(this);
                    }
                    this.this$0.getMinecraftServer().theChunkProviderServer.dropChunk(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos);
                }
            }
        }
        
        public void processChunk() {
            this.increaseInhabitedTime(PlayerManager.access$1(this.this$0).getChunkFromChunkCoords(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos));
        }
        
        private void increaseInhabitedTime(final Chunk chunk) {
            chunk.setInhabitedTime(chunk.getInhabitedTime() + PlayerManager.access$1(this.this$0).getTotalWorldTime() - this.previousWorldTime);
            this.previousWorldTime = PlayerManager.access$1(this.this$0).getTotalWorldTime();
        }
        
        public void flagChunkForUpdate(final int n, final int n2, final int n3) {
            if (this.numBlocksToUpdate == 0) {
                PlayerManager.access$4(this.this$0).add(this);
            }
            this.flagsYAreasToUpdate |= 1 << (n2 >> 4);
            if (this.numBlocksToUpdate < 64) {
                final short n4 = (short)(n << 12 | n3 << 8 | n2);
                while (0 < this.numBlocksToUpdate) {
                    if (this.locationOfBlockChange[0] == n4) {
                        return;
                    }
                    int n5 = 0;
                    ++n5;
                }
                this.locationOfBlockChange[this.numBlocksToUpdate++] = n4;
            }
        }
        
        public void sendToAllPlayersWatchingChunk(final Packet packet) {
            while (0 < this.playersWatchingChunk.size()) {
                final EntityPlayerMP entityPlayerMP = this.playersWatchingChunk.get(0);
                if (!entityPlayerMP.loadedChunks.contains(this.currentChunk)) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(packet);
                }
                int n = 0;
                ++n;
            }
        }
        
        public void onUpdate() {
            if (this.numBlocksToUpdate != 0) {
                if (this.numBlocksToUpdate == 1) {
                    final int n = (this.locationOfBlockChange[0] >> 12 & 0xF) + this.currentChunk.chunkXPos * 16;
                    final int n2 = this.locationOfBlockChange[0] & 0xFF;
                    final int n3 = (this.locationOfBlockChange[0] >> 8 & 0xF) + this.currentChunk.chunkZPos * 16;
                    final BlockPos blockPos = new BlockPos(0, n2, 0);
                    this.sendToAllPlayersWatchingChunk(new S23PacketBlockChange(PlayerManager.access$1(this.this$0), blockPos));
                    if (PlayerManager.access$1(this.this$0).getBlockState(blockPos).getBlock().hasTileEntity()) {
                        this.sendTileToAllPlayersWatchingChunk(PlayerManager.access$1(this.this$0).getTileEntity(blockPos));
                    }
                }
                else if (this.numBlocksToUpdate == 64) {
                    final int n = this.currentChunk.chunkXPos * 16;
                    final int n4 = this.currentChunk.chunkZPos * 16;
                    this.sendToAllPlayersWatchingChunk(new S21PacketChunkData(PlayerManager.access$1(this.this$0).getChunkFromChunkCoords(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos), false, this.flagsYAreasToUpdate));
                }
                else {
                    this.sendToAllPlayersWatchingChunk(new S22PacketMultiBlockChange(this.numBlocksToUpdate, this.locationOfBlockChange, PlayerManager.access$1(this.this$0).getChunkFromChunkCoords(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos)));
                    while (0 < this.numBlocksToUpdate) {
                        final int n5 = (this.locationOfBlockChange[0] >> 12 & 0xF) + this.currentChunk.chunkXPos * 16;
                        final int n6 = this.locationOfBlockChange[0] & 0xFF;
                        final int n7 = (this.locationOfBlockChange[0] >> 8 & 0xF) + this.currentChunk.chunkZPos * 16;
                        final BlockPos blockPos2 = new BlockPos(n5, 0, 0);
                        if (PlayerManager.access$1(this.this$0).getBlockState(blockPos2).getBlock().hasTileEntity()) {
                            this.sendTileToAllPlayersWatchingChunk(PlayerManager.access$1(this.this$0).getTileEntity(blockPos2));
                        }
                        int n = 0;
                        ++n;
                    }
                }
                this.numBlocksToUpdate = 0;
                this.flagsYAreasToUpdate = 0;
            }
        }
        
        private void sendTileToAllPlayersWatchingChunk(final TileEntity tileEntity) {
            if (tileEntity != null) {
                final Packet descriptionPacket = tileEntity.getDescriptionPacket();
                if (descriptionPacket != null) {
                    this.sendToAllPlayersWatchingChunk(descriptionPacket);
                }
            }
        }
        
        static ChunkCoordIntPair access$0(final PlayerInstance playerInstance) {
            return playerInstance.currentChunk;
        }
        
        static List access$1(final PlayerInstance playerInstance) {
            return playerInstance.playersWatchingChunk;
        }
        
        static {
            __OBFID = "CL_00001435";
        }
    }
}
