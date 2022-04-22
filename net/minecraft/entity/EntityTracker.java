package net.minecraft.entity;

import net.minecraft.world.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.world.chunk.*;

public class EntityTracker
{
    private static final Logger logger;
    private final WorldServer theWorld;
    private Set trackedEntities;
    private IntHashMap trackedEntityHashTable;
    private int maxTrackingDistanceThreshold;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001431";
        logger = LogManager.getLogger();
    }
    
    public EntityTracker(final WorldServer theWorld) {
        this.trackedEntities = Sets.newHashSet();
        this.trackedEntityHashTable = new IntHashMap();
        this.theWorld = theWorld;
        this.maxTrackingDistanceThreshold = theWorld.func_73046_m().getConfigurationManager().getEntityViewDistance();
    }
    
    public void trackEntity(final Entity entity) {
        if (entity instanceof EntityPlayerMP) {
            this.trackEntity(entity, 512, 2);
            final EntityPlayerMP entityPlayerMP = (EntityPlayerMP)entity;
            for (final EntityTrackerEntry entityTrackerEntry : this.trackedEntities) {
                if (entityTrackerEntry.trackedEntity != entityPlayerMP) {
                    entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
                }
            }
        }
        else if (entity instanceof EntityFishHook) {
            this.addEntityToTracker(entity, 64, 5, true);
        }
        else if (entity instanceof EntityArrow) {
            this.addEntityToTracker(entity, 64, 20, false);
        }
        else if (entity instanceof EntitySmallFireball) {
            this.addEntityToTracker(entity, 64, 10, false);
        }
        else if (entity instanceof EntityFireball) {
            this.addEntityToTracker(entity, 64, 10, false);
        }
        else if (entity instanceof EntitySnowball) {
            this.addEntityToTracker(entity, 64, 10, true);
        }
        else if (entity instanceof EntityEnderPearl) {
            this.addEntityToTracker(entity, 64, 10, true);
        }
        else if (entity instanceof EntityEnderEye) {
            this.addEntityToTracker(entity, 64, 4, true);
        }
        else if (entity instanceof EntityEgg) {
            this.addEntityToTracker(entity, 64, 10, true);
        }
        else if (entity instanceof EntityPotion) {
            this.addEntityToTracker(entity, 64, 10, true);
        }
        else if (entity instanceof EntityExpBottle) {
            this.addEntityToTracker(entity, 64, 10, true);
        }
        else if (entity instanceof EntityFireworkRocket) {
            this.addEntityToTracker(entity, 64, 10, true);
        }
        else if (entity instanceof EntityItem) {
            this.addEntityToTracker(entity, 64, 20, true);
        }
        else if (entity instanceof EntityMinecart) {
            this.addEntityToTracker(entity, 80, 3, true);
        }
        else if (entity instanceof EntityBoat) {
            this.addEntityToTracker(entity, 80, 3, true);
        }
        else if (entity instanceof EntitySquid) {
            this.addEntityToTracker(entity, 64, 3, true);
        }
        else if (entity instanceof EntityWither) {
            this.addEntityToTracker(entity, 80, 3, false);
        }
        else if (entity instanceof EntityBat) {
            this.addEntityToTracker(entity, 80, 3, false);
        }
        else if (entity instanceof EntityDragon) {
            this.addEntityToTracker(entity, 160, 3, true);
        }
        else if (entity instanceof IAnimals) {
            this.addEntityToTracker(entity, 80, 3, true);
        }
        else if (entity instanceof EntityTNTPrimed) {
            this.addEntityToTracker(entity, 160, 10, true);
        }
        else if (entity instanceof EntityFallingBlock) {
            this.addEntityToTracker(entity, 160, 20, true);
        }
        else if (entity instanceof EntityHanging) {
            this.addEntityToTracker(entity, 160, Integer.MAX_VALUE, false);
        }
        else if (entity instanceof EntityArmorStand) {
            this.addEntityToTracker(entity, 160, 3, true);
        }
        else if (entity instanceof EntityXPOrb) {
            this.addEntityToTracker(entity, 160, 20, true);
        }
        else if (entity instanceof EntityEnderCrystal) {
            this.addEntityToTracker(entity, 256, Integer.MAX_VALUE, false);
        }
    }
    
    public void trackEntity(final Entity entity, final int n, final int n2) {
        this.addEntityToTracker(entity, n, n2, false);
    }
    
    public void addEntityToTracker(final Entity entity, int maxTrackingDistanceThreshold, final int n, final boolean b) {
        if (maxTrackingDistanceThreshold > this.maxTrackingDistanceThreshold) {
            maxTrackingDistanceThreshold = this.maxTrackingDistanceThreshold;
        }
        if (this.trackedEntityHashTable.containsItem(entity.getEntityId())) {
            throw new IllegalStateException("Entity is already tracked!");
        }
        final EntityTrackerEntry entityTrackerEntry = new EntityTrackerEntry(entity, maxTrackingDistanceThreshold, n, b);
        this.trackedEntities.add(entityTrackerEntry);
        this.trackedEntityHashTable.addKey(entity.getEntityId(), entityTrackerEntry);
        entityTrackerEntry.updatePlayerEntities(this.theWorld.playerEntities);
    }
    
    public void untrackEntity(final Entity entity) {
        if (entity instanceof EntityPlayerMP) {
            final EntityPlayerMP entityPlayerMP = (EntityPlayerMP)entity;
            final Iterator<EntityTrackerEntry> iterator = (Iterator<EntityTrackerEntry>)this.trackedEntities.iterator();
            while (iterator.hasNext()) {
                iterator.next().removeFromTrackedPlayers(entityPlayerMP);
            }
        }
        final EntityTrackerEntry entityTrackerEntry = (EntityTrackerEntry)this.trackedEntityHashTable.removeObject(entity.getEntityId());
        if (entityTrackerEntry != null) {
            this.trackedEntities.remove(entityTrackerEntry);
            entityTrackerEntry.sendDestroyEntityPacketToTrackedPlayers();
        }
    }
    
    public void updateTrackedEntities() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore_1       
        //     4: aload_0        
        //     5: getfield        net/minecraft/entity/EntityTracker.trackedEntities:Ljava/util/Set;
        //     8: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    13: astore_2       
        //    14: goto            67
        //    17: aload_2        
        //    18: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    23: checkcast       Lnet/minecraft/entity/EntityTrackerEntry;
        //    26: astore_3       
        //    27: aload_3        
        //    28: aload_0        
        //    29: getfield        net/minecraft/entity/EntityTracker.theWorld:Lnet/minecraft/world/WorldServer;
        //    32: getfield        net/minecraft/world/WorldServer.playerEntities:Ljava/util/List;
        //    35: invokevirtual   net/minecraft/entity/EntityTrackerEntry.updatePlayerList:(Ljava/util/List;)V
        //    38: aload_3        
        //    39: getfield        net/minecraft/entity/EntityTrackerEntry.playerEntitiesUpdated:Z
        //    42: ifeq            67
        //    45: aload_3        
        //    46: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //    49: instanceof      Lnet/minecraft/entity/player/EntityPlayerMP;
        //    52: ifeq            67
        //    55: aload_1        
        //    56: aload_3        
        //    57: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //    60: checkcast       Lnet/minecraft/entity/player/EntityPlayerMP;
        //    63: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //    66: pop            
        //    67: aload_2        
        //    68: invokeinterface java/util/Iterator.hasNext:()Z
        //    73: ifne            17
        //    76: goto            145
        //    79: aload_1        
        //    80: iconst_0       
        //    81: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //    84: checkcast       Lnet/minecraft/entity/player/EntityPlayerMP;
        //    87: astore          4
        //    89: aload_0        
        //    90: getfield        net/minecraft/entity/EntityTracker.trackedEntities:Ljava/util/Set;
        //    93: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    98: astore          5
        //   100: goto            132
        //   103: aload           5
        //   105: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   110: checkcast       Lnet/minecraft/entity/EntityTrackerEntry;
        //   113: astore          6
        //   115: aload           6
        //   117: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   120: aload           4
        //   122: if_acmpeq       132
        //   125: aload           6
        //   127: aload           4
        //   129: invokevirtual   net/minecraft/entity/EntityTrackerEntry.updatePlayerEntity:(Lnet/minecraft/entity/player/EntityPlayerMP;)V
        //   132: aload           5
        //   134: invokeinterface java/util/Iterator.hasNext:()Z
        //   139: ifne            103
        //   142: iinc            3, 1
        //   145: iconst_0       
        //   146: aload_1        
        //   147: invokevirtual   java/util/ArrayList.size:()I
        //   150: if_icmplt       79
        //   153: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void func_180245_a(final EntityPlayerMP entityPlayerMP) {
        for (final EntityTrackerEntry entityTrackerEntry : this.trackedEntities) {
            if (entityTrackerEntry.trackedEntity == entityPlayerMP) {
                entityTrackerEntry.updatePlayerEntities(this.theWorld.playerEntities);
            }
            else {
                entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
            }
        }
    }
    
    public void sendToAllTrackingEntity(final Entity entity, final Packet packet) {
        final EntityTrackerEntry entityTrackerEntry = (EntityTrackerEntry)this.trackedEntityHashTable.lookup(entity.getEntityId());
        if (entityTrackerEntry != null) {
            entityTrackerEntry.func_151259_a(packet);
        }
    }
    
    public void func_151248_b(final Entity entity, final Packet packet) {
        final EntityTrackerEntry entityTrackerEntry = (EntityTrackerEntry)this.trackedEntityHashTable.lookup(entity.getEntityId());
        if (entityTrackerEntry != null) {
            entityTrackerEntry.func_151261_b(packet);
        }
    }
    
    public void removePlayerFromTrackers(final EntityPlayerMP entityPlayerMP) {
        final Iterator<EntityTrackerEntry> iterator = this.trackedEntities.iterator();
        while (iterator.hasNext()) {
            iterator.next().removeTrackedPlayerSymmetric(entityPlayerMP);
        }
    }
    
    public void func_85172_a(final EntityPlayerMP entityPlayerMP, final Chunk chunk) {
        for (final EntityTrackerEntry entityTrackerEntry : this.trackedEntities) {
            if (entityTrackerEntry.trackedEntity != entityPlayerMP && entityTrackerEntry.trackedEntity.chunkCoordX == chunk.xPosition && entityTrackerEntry.trackedEntity.chunkCoordZ == chunk.zPosition) {
                entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
            }
        }
    }
}
