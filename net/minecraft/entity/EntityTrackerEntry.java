package net.minecraft.entity;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;

public class EntityTrackerEntry
{
    private static final Logger logger;
    public Entity trackedEntity;
    public int trackingDistanceThreshold;
    public int updateFrequency;
    public int encodedPosX;
    public int encodedPosY;
    public int encodedPosZ;
    public int encodedRotationYaw;
    public int encodedRotationPitch;
    public int lastHeadMotion;
    public double lastTrackedEntityMotionX;
    public double lastTrackedEntityMotionY;
    public double motionZ;
    public int updateCounter;
    private double lastTrackedEntityPosX;
    private double lastTrackedEntityPosY;
    private double lastTrackedEntityPosZ;
    private boolean firstUpdateDone;
    private boolean sendVelocityUpdates;
    private int ticksSinceLastForcedTeleport;
    private Entity field_85178_v;
    private boolean ridingEntity;
    private boolean field_180234_y;
    public boolean playerEntitiesUpdated;
    public Set trackingPlayers;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001443";
        logger = LogManager.getLogger();
    }
    
    public EntityTrackerEntry(final Entity trackedEntity, final int trackingDistanceThreshold, final int updateFrequency, final boolean sendVelocityUpdates) {
        this.trackingPlayers = Sets.newHashSet();
        this.trackedEntity = trackedEntity;
        this.trackingDistanceThreshold = trackingDistanceThreshold;
        this.updateFrequency = updateFrequency;
        this.sendVelocityUpdates = sendVelocityUpdates;
        this.encodedPosX = MathHelper.floor_double(trackedEntity.posX * 32.0);
        this.encodedPosY = MathHelper.floor_double(trackedEntity.posY * 32.0);
        this.encodedPosZ = MathHelper.floor_double(trackedEntity.posZ * 32.0);
        this.encodedRotationYaw = MathHelper.floor_float(trackedEntity.rotationYaw * 256.0f / 360.0f);
        this.encodedRotationPitch = MathHelper.floor_float(trackedEntity.rotationPitch * 256.0f / 360.0f);
        this.lastHeadMotion = MathHelper.floor_float(trackedEntity.getRotationYawHead() * 256.0f / 360.0f);
        this.field_180234_y = trackedEntity.onGround;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof EntityTrackerEntry && ((EntityTrackerEntry)o).trackedEntity.getEntityId() == this.trackedEntity.getEntityId();
    }
    
    @Override
    public int hashCode() {
        return this.trackedEntity.getEntityId();
    }
    
    public void updatePlayerList(final List list) {
        this.playerEntitiesUpdated = false;
        if (!this.firstUpdateDone || this.trackedEntity.getDistanceSq(this.lastTrackedEntityPosX, this.lastTrackedEntityPosY, this.lastTrackedEntityPosZ) > 16.0) {
            this.lastTrackedEntityPosX = this.trackedEntity.posX;
            this.lastTrackedEntityPosY = this.trackedEntity.posY;
            this.lastTrackedEntityPosZ = this.trackedEntity.posZ;
            this.firstUpdateDone = true;
            this.playerEntitiesUpdated = true;
            this.updatePlayerEntities(list);
        }
        if (this.field_85178_v != this.trackedEntity.ridingEntity || (this.trackedEntity.ridingEntity != null && this.updateCounter % 60 == 0)) {
            this.field_85178_v = this.trackedEntity.ridingEntity;
            this.func_151259_a(new S1BPacketEntityAttach(0, this.trackedEntity, this.trackedEntity.ridingEntity));
        }
        if (this.trackedEntity instanceof EntityItemFrame && this.updateCounter % 10 == 0) {
            final ItemStack displayedItem = ((EntityItemFrame)this.trackedEntity).getDisplayedItem();
            if (displayedItem != null && displayedItem.getItem() instanceof ItemMap) {
                final MapData mapData = Items.filled_map.getMapData(displayedItem, this.trackedEntity.worldObj);
                for (final EntityPlayerMP entityPlayerMP : list) {
                    mapData.updateVisiblePlayers(entityPlayerMP, displayedItem);
                    final Packet mapDataPacket = Items.filled_map.createMapDataPacket(displayedItem, this.trackedEntity.worldObj, entityPlayerMP);
                    if (mapDataPacket != null) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(mapDataPacket);
                    }
                }
            }
            this.sendMetadataToAllAssociatedPlayers();
        }
        if (this.updateCounter % this.updateFrequency == 0 || this.trackedEntity.isAirBorne || this.trackedEntity.getDataWatcher().hasObjectChanged()) {
            if (this.trackedEntity.ridingEntity == null) {
                ++this.ticksSinceLastForcedTeleport;
                final int floor_double = MathHelper.floor_double(this.trackedEntity.posX * 32.0);
                final int floor_double2 = MathHelper.floor_double(this.trackedEntity.posY * 32.0);
                final int floor_double3 = MathHelper.floor_double(this.trackedEntity.posZ * 32.0);
                final int floor_float = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0f / 360.0f);
                final int floor_float2 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0f / 360.0f);
                final int n = floor_double - this.encodedPosX;
                final int n2 = floor_double2 - this.encodedPosY;
                final int n3 = floor_double3 - this.encodedPosZ;
                Packet packet = null;
                final boolean b = Math.abs(n) >= 4 || Math.abs(n2) >= 4 || Math.abs(n3) >= 4 || this.updateCounter % 60 == 0;
                final boolean b2 = Math.abs(floor_float - this.encodedRotationYaw) >= 4 || Math.abs(floor_float2 - this.encodedRotationPitch) >= 4;
                if (this.updateCounter > 0 || this.trackedEntity instanceof EntityArrow) {
                    if (n >= -128 && n < 128 && n2 >= -128 && n2 < 128 && n3 >= -128 && n3 < 128 && this.ticksSinceLastForcedTeleport <= 400 && !this.ridingEntity && this.field_180234_y == this.trackedEntity.onGround) {
                        if (b && b2) {
                            packet = new S14PacketEntity.S17PacketEntityLookMove(this.trackedEntity.getEntityId(), (byte)n, (byte)n2, (byte)n3, (byte)floor_float, (byte)floor_float2, this.trackedEntity.onGround);
                        }
                        else if (b) {
                            packet = new S14PacketEntity.S15PacketEntityRelMove(this.trackedEntity.getEntityId(), (byte)n, (byte)n2, (byte)n3, this.trackedEntity.onGround);
                        }
                        else if (b2) {
                            packet = new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)floor_float, (byte)floor_float2, this.trackedEntity.onGround);
                        }
                    }
                    else {
                        this.field_180234_y = this.trackedEntity.onGround;
                        this.ticksSinceLastForcedTeleport = 0;
                        packet = new S18PacketEntityTeleport(this.trackedEntity.getEntityId(), floor_double, floor_double2, floor_double3, (byte)floor_float, (byte)floor_float2, this.trackedEntity.onGround);
                    }
                }
                if (this.sendVelocityUpdates) {
                    final double n4 = this.trackedEntity.motionX - this.lastTrackedEntityMotionX;
                    final double n5 = this.trackedEntity.motionY - this.lastTrackedEntityMotionY;
                    final double n6 = this.trackedEntity.motionZ - this.motionZ;
                    final double n7 = 0.02;
                    final double n8 = n4 * n4 + n5 * n5 + n6 * n6;
                    if (n8 > n7 * n7 || (n8 > 0.0 && this.trackedEntity.motionX == 0.0 && this.trackedEntity.motionY == 0.0 && this.trackedEntity.motionZ == 0.0)) {
                        this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
                        this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
                        this.motionZ = this.trackedEntity.motionZ;
                        this.func_151259_a(new S12PacketEntityVelocity(this.trackedEntity.getEntityId(), this.lastTrackedEntityMotionX, this.lastTrackedEntityMotionY, this.motionZ));
                    }
                }
                if (packet != null) {
                    this.func_151259_a(packet);
                }
                this.sendMetadataToAllAssociatedPlayers();
                if (b) {
                    this.encodedPosX = floor_double;
                    this.encodedPosY = floor_double2;
                    this.encodedPosZ = floor_double3;
                }
                if (b2) {
                    this.encodedRotationYaw = floor_float;
                    this.encodedRotationPitch = floor_float2;
                }
                this.ridingEntity = false;
            }
            else {
                final int floor_float3 = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0f / 360.0f);
                final int floor_float4 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0f / 360.0f);
                if (Math.abs(floor_float3 - this.encodedRotationYaw) >= 4 || Math.abs(floor_float4 - this.encodedRotationPitch) >= 4) {
                    this.func_151259_a(new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)floor_float3, (byte)floor_float4, this.trackedEntity.onGround));
                    this.encodedRotationYaw = floor_float3;
                    this.encodedRotationPitch = floor_float4;
                }
                this.encodedPosX = MathHelper.floor_double(this.trackedEntity.posX * 32.0);
                this.encodedPosY = MathHelper.floor_double(this.trackedEntity.posY * 32.0);
                this.encodedPosZ = MathHelper.floor_double(this.trackedEntity.posZ * 32.0);
                this.sendMetadataToAllAssociatedPlayers();
                this.ridingEntity = true;
            }
            final int floor_float5 = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0f / 360.0f);
            if (Math.abs(floor_float5 - this.lastHeadMotion) >= 4) {
                this.func_151259_a(new S19PacketEntityHeadLook(this.trackedEntity, (byte)floor_float5));
                this.lastHeadMotion = floor_float5;
            }
            this.trackedEntity.isAirBorne = false;
        }
        ++this.updateCounter;
        if (this.trackedEntity.velocityChanged) {
            this.func_151261_b(new S12PacketEntityVelocity(this.trackedEntity));
            this.trackedEntity.velocityChanged = false;
        }
    }
    
    private void sendMetadataToAllAssociatedPlayers() {
        final DataWatcher dataWatcher = this.trackedEntity.getDataWatcher();
        if (dataWatcher.hasObjectChanged()) {
            this.func_151261_b(new S1CPacketEntityMetadata(this.trackedEntity.getEntityId(), dataWatcher, false));
        }
        if (this.trackedEntity instanceof EntityLivingBase) {
            final Set attributeInstanceSet = ((ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap()).getAttributeInstanceSet();
            if (!attributeInstanceSet.isEmpty()) {
                this.func_151261_b(new S20PacketEntityProperties(this.trackedEntity.getEntityId(), attributeInstanceSet));
            }
            attributeInstanceSet.clear();
        }
    }
    
    public void func_151259_a(final Packet packet) {
        final Iterator<EntityPlayerMP> iterator = this.trackingPlayers.iterator();
        while (iterator.hasNext()) {
            iterator.next().playerNetServerHandler.sendPacket(packet);
        }
    }
    
    public void func_151261_b(final Packet packet) {
        this.func_151259_a(packet);
        if (this.trackedEntity instanceof EntityPlayerMP) {
            ((EntityPlayerMP)this.trackedEntity).playerNetServerHandler.sendPacket(packet);
        }
    }
    
    public void sendDestroyEntityPacketToTrackedPlayers() {
        final Iterator<EntityPlayerMP> iterator = this.trackingPlayers.iterator();
        while (iterator.hasNext()) {
            iterator.next().func_152339_d(this.trackedEntity);
        }
    }
    
    public void removeFromTrackedPlayers(final EntityPlayerMP entityPlayerMP) {
        if (this.trackingPlayers.contains(entityPlayerMP)) {
            entityPlayerMP.func_152339_d(this.trackedEntity);
            this.trackingPlayers.remove(entityPlayerMP);
        }
    }
    
    public void updatePlayerEntity(final EntityPlayerMP p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //     5: if_acmpeq       620
        //     8: aload_0        
        //     9: aload_1        
        //    10: invokevirtual   net/minecraft/entity/EntityTrackerEntry.func_180233_c:(Lnet/minecraft/entity/player/EntityPlayerMP;)Z
        //    13: ifeq            588
        //    16: aload_0        
        //    17: getfield        net/minecraft/entity/EntityTrackerEntry.trackingPlayers:Ljava/util/Set;
        //    20: aload_1        
        //    21: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //    26: ifne            620
        //    29: aload_0        
        //    30: aload_1        
        //    31: invokespecial   net/minecraft/entity/EntityTrackerEntry.isPlayerWatchingThisChunk:(Lnet/minecraft/entity/player/EntityPlayerMP;)Z
        //    34: ifne            47
        //    37: aload_0        
        //    38: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //    41: getfield        net/minecraft/entity/Entity.forceSpawn:Z
        //    44: ifeq            620
        //    47: aload_0        
        //    48: getfield        net/minecraft/entity/EntityTrackerEntry.trackingPlayers:Ljava/util/Set;
        //    51: aload_1        
        //    52: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //    57: pop            
        //    58: aload_0        
        //    59: invokespecial   net/minecraft/entity/EntityTrackerEntry.func_151260_c:()Lnet/minecraft/network/Packet;
        //    62: astore_2       
        //    63: aload_1        
        //    64: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //    67: aload_2        
        //    68: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //    71: aload_0        
        //    72: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //    75: invokevirtual   net/minecraft/entity/Entity.getDataWatcher:()Lnet/minecraft/entity/DataWatcher;
        //    78: invokevirtual   net/minecraft/entity/DataWatcher.getIsBlank:()Z
        //    81: ifne            113
        //    84: aload_1        
        //    85: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //    88: new             Lnet/minecraft/network/play/server/S1CPacketEntityMetadata;
        //    91: dup            
        //    92: aload_0        
        //    93: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //    96: invokevirtual   net/minecraft/entity/Entity.getEntityId:()I
        //    99: aload_0        
        //   100: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   103: invokevirtual   net/minecraft/entity/Entity.getDataWatcher:()Lnet/minecraft/entity/DataWatcher;
        //   106: iconst_1       
        //   107: invokespecial   net/minecraft/network/play/server/S1CPacketEntityMetadata.<init>:(ILnet/minecraft/entity/DataWatcher;Z)V
        //   110: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   113: aload_0        
        //   114: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   117: invokevirtual   net/minecraft/entity/Entity.func_174819_aU:()Lnet/minecraft/nbt/NBTTagCompound;
        //   120: astore_3       
        //   121: aload_3        
        //   122: ifnull          147
        //   125: aload_1        
        //   126: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   129: new             Lnet/minecraft/network/play/server/S49PacketUpdateEntityNBT;
        //   132: dup            
        //   133: aload_0        
        //   134: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   137: invokevirtual   net/minecraft/entity/Entity.getEntityId:()I
        //   140: aload_3        
        //   141: invokespecial   net/minecraft/network/play/server/S49PacketUpdateEntityNBT.<init>:(ILnet/minecraft/nbt/NBTTagCompound;)V
        //   144: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   147: aload_0        
        //   148: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   151: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   154: ifeq            212
        //   157: aload_0        
        //   158: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   161: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   164: invokevirtual   net/minecraft/entity/EntityLivingBase.getAttributeMap:()Lnet/minecraft/entity/ai/attributes/BaseAttributeMap;
        //   167: checkcast       Lnet/minecraft/entity/ai/attributes/ServersideAttributeMap;
        //   170: astore          4
        //   172: aload           4
        //   174: invokevirtual   net/minecraft/entity/ai/attributes/ServersideAttributeMap.getWatchedAttributes:()Ljava/util/Collection;
        //   177: astore          5
        //   179: aload           5
        //   181: invokeinterface java/util/Collection.isEmpty:()Z
        //   186: ifne            212
        //   189: aload_1        
        //   190: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   193: new             Lnet/minecraft/network/play/server/S20PacketEntityProperties;
        //   196: dup            
        //   197: aload_0        
        //   198: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   201: invokevirtual   net/minecraft/entity/Entity.getEntityId:()I
        //   204: aload           5
        //   206: invokespecial   net/minecraft/network/play/server/S20PacketEntityProperties.<init>:(ILjava/util/Collection;)V
        //   209: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   212: aload_0        
        //   213: aload_0        
        //   214: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   217: getfield        net/minecraft/entity/Entity.motionX:D
        //   220: putfield        net/minecraft/entity/EntityTrackerEntry.lastTrackedEntityMotionX:D
        //   223: aload_0        
        //   224: aload_0        
        //   225: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   228: getfield        net/minecraft/entity/Entity.motionY:D
        //   231: putfield        net/minecraft/entity/EntityTrackerEntry.lastTrackedEntityMotionY:D
        //   234: aload_0        
        //   235: aload_0        
        //   236: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   239: getfield        net/minecraft/entity/Entity.motionZ:D
        //   242: putfield        net/minecraft/entity/EntityTrackerEntry.motionZ:D
        //   245: aload_0        
        //   246: getfield        net/minecraft/entity/EntityTrackerEntry.sendVelocityUpdates:Z
        //   249: ifeq            301
        //   252: aload_2        
        //   253: instanceof      Lnet/minecraft/network/play/server/S0FPacketSpawnMob;
        //   256: ifne            301
        //   259: aload_1        
        //   260: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   263: new             Lnet/minecraft/network/play/server/S12PacketEntityVelocity;
        //   266: dup            
        //   267: aload_0        
        //   268: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   271: invokevirtual   net/minecraft/entity/Entity.getEntityId:()I
        //   274: aload_0        
        //   275: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   278: getfield        net/minecraft/entity/Entity.motionX:D
        //   281: aload_0        
        //   282: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   285: getfield        net/minecraft/entity/Entity.motionY:D
        //   288: aload_0        
        //   289: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   292: getfield        net/minecraft/entity/Entity.motionZ:D
        //   295: invokespecial   net/minecraft/network/play/server/S12PacketEntityVelocity.<init>:(IDDD)V
        //   298: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   301: aload_0        
        //   302: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   305: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   308: ifnull          337
        //   311: aload_1        
        //   312: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   315: new             Lnet/minecraft/network/play/server/S1BPacketEntityAttach;
        //   318: dup            
        //   319: iconst_0       
        //   320: aload_0        
        //   321: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   324: aload_0        
        //   325: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   328: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   331: invokespecial   net/minecraft/network/play/server/S1BPacketEntityAttach.<init>:(ILnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;)V
        //   334: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   337: aload_0        
        //   338: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   341: instanceof      Lnet/minecraft/entity/EntityLiving;
        //   344: ifeq            389
        //   347: aload_0        
        //   348: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   351: checkcast       Lnet/minecraft/entity/EntityLiving;
        //   354: invokevirtual   net/minecraft/entity/EntityLiving.getLeashedToEntity:()Lnet/minecraft/entity/Entity;
        //   357: ifnull          389
        //   360: aload_1        
        //   361: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   364: new             Lnet/minecraft/network/play/server/S1BPacketEntityAttach;
        //   367: dup            
        //   368: iconst_1       
        //   369: aload_0        
        //   370: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   373: aload_0        
        //   374: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   377: checkcast       Lnet/minecraft/entity/EntityLiving;
        //   380: invokevirtual   net/minecraft/entity/EntityLiving.getLeashedToEntity:()Lnet/minecraft/entity/Entity;
        //   383: invokespecial   net/minecraft/network/play/server/S1BPacketEntityAttach.<init>:(ILnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;)V
        //   386: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   389: aload_0        
        //   390: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   393: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   396: ifeq            452
        //   399: goto            447
        //   402: aload_0        
        //   403: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   406: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   409: iconst_0       
        //   410: invokevirtual   net/minecraft/entity/EntityLivingBase.getEquipmentInSlot:(I)Lnet/minecraft/item/ItemStack;
        //   413: astore          5
        //   415: aload           5
        //   417: ifnull          444
        //   420: aload_1        
        //   421: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   424: new             Lnet/minecraft/network/play/server/S04PacketEntityEquipment;
        //   427: dup            
        //   428: aload_0        
        //   429: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   432: invokevirtual   net/minecraft/entity/Entity.getEntityId:()I
        //   435: iconst_0       
        //   436: aload           5
        //   438: invokespecial   net/minecraft/network/play/server/S04PacketEntityEquipment.<init>:(IILnet/minecraft/item/ItemStack;)V
        //   441: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   444: iinc            4, 1
        //   447: iconst_0       
        //   448: iconst_5       
        //   449: if_icmplt       402
        //   452: aload_0        
        //   453: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   456: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //   459: ifeq            506
        //   462: aload_0        
        //   463: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   466: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   469: astore          4
        //   471: aload           4
        //   473: invokevirtual   net/minecraft/entity/player/EntityPlayer.isPlayerSleeping:()Z
        //   476: ifeq            506
        //   479: aload_1        
        //   480: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   483: new             Lnet/minecraft/network/play/server/S0APacketUseBed;
        //   486: dup            
        //   487: aload           4
        //   489: new             Lnet/minecraft/util/BlockPos;
        //   492: dup            
        //   493: aload_0        
        //   494: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   497: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //   500: invokespecial   net/minecraft/network/play/server/S0APacketUseBed.<init>:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/BlockPos;)V
        //   503: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   506: aload_0        
        //   507: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   510: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   513: ifeq            620
        //   516: aload_0        
        //   517: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   520: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   523: astore          4
        //   525: aload           4
        //   527: invokevirtual   net/minecraft/entity/EntityLivingBase.getActivePotionEffects:()Ljava/util/Collection;
        //   530: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //   535: astore          5
        //   537: goto            575
        //   540: aload           5
        //   542: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   547: checkcast       Lnet/minecraft/potion/PotionEffect;
        //   550: astore          6
        //   552: aload_1        
        //   553: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   556: new             Lnet/minecraft/network/play/server/S1DPacketEntityEffect;
        //   559: dup            
        //   560: aload_0        
        //   561: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   564: invokevirtual   net/minecraft/entity/Entity.getEntityId:()I
        //   567: aload           6
        //   569: invokespecial   net/minecraft/network/play/server/S1DPacketEntityEffect.<init>:(ILnet/minecraft/potion/PotionEffect;)V
        //   572: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   575: aload           5
        //   577: invokeinterface java/util/Iterator.hasNext:()Z
        //   582: ifne            540
        //   585: goto            620
        //   588: aload_0        
        //   589: getfield        net/minecraft/entity/EntityTrackerEntry.trackingPlayers:Ljava/util/Set;
        //   592: aload_1        
        //   593: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //   598: ifeq            620
        //   601: aload_0        
        //   602: getfield        net/minecraft/entity/EntityTrackerEntry.trackingPlayers:Ljava/util/Set;
        //   605: aload_1        
        //   606: invokeinterface java/util/Set.remove:(Ljava/lang/Object;)Z
        //   611: pop            
        //   612: aload_1        
        //   613: aload_0        
        //   614: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   617: invokevirtual   net/minecraft/entity/player/EntityPlayerMP.func_152339_d:(Lnet/minecraft/entity/Entity;)V
        //   620: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean func_180233_c(final EntityPlayerMP entityPlayerMP) {
        final double n = entityPlayerMP.posX - this.encodedPosX / 32;
        final double n2 = entityPlayerMP.posZ - this.encodedPosZ / 32;
        return n >= -this.trackingDistanceThreshold && n <= this.trackingDistanceThreshold && n2 >= -this.trackingDistanceThreshold && n2 <= this.trackingDistanceThreshold && this.trackedEntity.func_174827_a(entityPlayerMP);
    }
    
    private boolean isPlayerWatchingThisChunk(final EntityPlayerMP entityPlayerMP) {
        return entityPlayerMP.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(entityPlayerMP, this.trackedEntity.chunkCoordX, this.trackedEntity.chunkCoordZ);
    }
    
    public void updatePlayerEntities(final List list) {
        while (0 < list.size()) {
            this.updatePlayerEntity(list.get(0));
            int n = 0;
            ++n;
        }
    }
    
    private Packet func_151260_c() {
        if (this.trackedEntity.isDead) {
            EntityTrackerEntry.logger.warn("Fetching addPacket for removed entity");
        }
        if (this.trackedEntity instanceof EntityItem) {
            return new S0EPacketSpawnObject(this.trackedEntity, 2, 1);
        }
        if (this.trackedEntity instanceof EntityPlayerMP) {
            return new S0CPacketSpawnPlayer((EntityPlayer)this.trackedEntity);
        }
        if (this.trackedEntity instanceof EntityMinecart) {
            return new S0EPacketSpawnObject(this.trackedEntity, 10, ((EntityMinecart)this.trackedEntity).func_180456_s().func_180039_a());
        }
        if (this.trackedEntity instanceof EntityBoat) {
            return new S0EPacketSpawnObject(this.trackedEntity, 1);
        }
        if (this.trackedEntity instanceof IAnimals) {
            this.lastHeadMotion = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0f / 360.0f);
            return new S0FPacketSpawnMob((EntityLivingBase)this.trackedEntity);
        }
        if (this.trackedEntity instanceof EntityFishHook) {
            final EntityPlayer angler = ((EntityFishHook)this.trackedEntity).angler;
            return new S0EPacketSpawnObject(this.trackedEntity, 90, (angler != null) ? angler.getEntityId() : this.trackedEntity.getEntityId());
        }
        if (this.trackedEntity instanceof EntityArrow) {
            final Entity shootingEntity = ((EntityArrow)this.trackedEntity).shootingEntity;
            return new S0EPacketSpawnObject(this.trackedEntity, 60, (shootingEntity != null) ? shootingEntity.getEntityId() : this.trackedEntity.getEntityId());
        }
        if (this.trackedEntity instanceof EntitySnowball) {
            return new S0EPacketSpawnObject(this.trackedEntity, 61);
        }
        if (this.trackedEntity instanceof EntityPotion) {
            return new S0EPacketSpawnObject(this.trackedEntity, 73, ((EntityPotion)this.trackedEntity).getPotionDamage());
        }
        if (this.trackedEntity instanceof EntityExpBottle) {
            return new S0EPacketSpawnObject(this.trackedEntity, 75);
        }
        if (this.trackedEntity instanceof EntityEnderPearl) {
            return new S0EPacketSpawnObject(this.trackedEntity, 65);
        }
        if (this.trackedEntity instanceof EntityEnderEye) {
            return new S0EPacketSpawnObject(this.trackedEntity, 72);
        }
        if (this.trackedEntity instanceof EntityFireworkRocket) {
            return new S0EPacketSpawnObject(this.trackedEntity, 76);
        }
        if (this.trackedEntity instanceof EntityFireball) {
            final EntityFireball entityFireball = (EntityFireball)this.trackedEntity;
            if (!(this.trackedEntity instanceof EntitySmallFireball)) {
                if (this.trackedEntity instanceof EntityWitherSkull) {}
            }
            S0EPacketSpawnObject s0EPacketSpawnObject;
            if (entityFireball.shootingEntity != null) {
                s0EPacketSpawnObject = new S0EPacketSpawnObject(this.trackedEntity, 66, ((EntityFireball)this.trackedEntity).shootingEntity.getEntityId());
            }
            else {
                s0EPacketSpawnObject = new S0EPacketSpawnObject(this.trackedEntity, 66, 0);
            }
            s0EPacketSpawnObject.func_149003_d((int)(entityFireball.accelerationX * 8000.0));
            s0EPacketSpawnObject.func_149000_e((int)(entityFireball.accelerationY * 8000.0));
            s0EPacketSpawnObject.func_149007_f((int)(entityFireball.accelerationZ * 8000.0));
            return s0EPacketSpawnObject;
        }
        if (this.trackedEntity instanceof EntityEgg) {
            return new S0EPacketSpawnObject(this.trackedEntity, 62);
        }
        if (this.trackedEntity instanceof EntityTNTPrimed) {
            return new S0EPacketSpawnObject(this.trackedEntity, 50);
        }
        if (this.trackedEntity instanceof EntityEnderCrystal) {
            return new S0EPacketSpawnObject(this.trackedEntity, 51);
        }
        if (this.trackedEntity instanceof EntityFallingBlock) {
            return new S0EPacketSpawnObject(this.trackedEntity, 70, Block.getStateId(((EntityFallingBlock)this.trackedEntity).getBlock()));
        }
        if (this.trackedEntity instanceof EntityArmorStand) {
            return new S0EPacketSpawnObject(this.trackedEntity, 78);
        }
        if (this.trackedEntity instanceof EntityPainting) {
            return new S10PacketSpawnPainting((EntityPainting)this.trackedEntity);
        }
        if (this.trackedEntity instanceof EntityItemFrame) {
            final EntityItemFrame entityItemFrame = (EntityItemFrame)this.trackedEntity;
            final S0EPacketSpawnObject s0EPacketSpawnObject2 = new S0EPacketSpawnObject(this.trackedEntity, 71, entityItemFrame.field_174860_b.getHorizontalIndex());
            final BlockPos func_174857_n = entityItemFrame.func_174857_n();
            s0EPacketSpawnObject2.func_148996_a(MathHelper.floor_float((float)(func_174857_n.getX() * 32)));
            s0EPacketSpawnObject2.func_148995_b(MathHelper.floor_float((float)(func_174857_n.getY() * 32)));
            s0EPacketSpawnObject2.func_149005_c(MathHelper.floor_float((float)(func_174857_n.getZ() * 32)));
            return s0EPacketSpawnObject2;
        }
        if (this.trackedEntity instanceof EntityLeashKnot) {
            final EntityLeashKnot entityLeashKnot = (EntityLeashKnot)this.trackedEntity;
            final S0EPacketSpawnObject s0EPacketSpawnObject3 = new S0EPacketSpawnObject(this.trackedEntity, 77);
            final BlockPos func_174857_n2 = entityLeashKnot.func_174857_n();
            s0EPacketSpawnObject3.func_148996_a(MathHelper.floor_float((float)(func_174857_n2.getX() * 32)));
            s0EPacketSpawnObject3.func_148995_b(MathHelper.floor_float((float)(func_174857_n2.getY() * 32)));
            s0EPacketSpawnObject3.func_149005_c(MathHelper.floor_float((float)(func_174857_n2.getZ() * 32)));
            return s0EPacketSpawnObject3;
        }
        if (this.trackedEntity instanceof EntityXPOrb) {
            return new S11PacketSpawnExperienceOrb((EntityXPOrb)this.trackedEntity);
        }
        throw new IllegalArgumentException("Don't know how to add " + this.trackedEntity.getClass() + "!");
    }
    
    public void removeTrackedPlayerSymmetric(final EntityPlayerMP entityPlayerMP) {
        if (this.trackingPlayers.contains(entityPlayerMP)) {
            this.trackingPlayers.remove(entityPlayerMP);
            entityPlayerMP.func_152339_d(this.trackedEntity);
        }
    }
}
