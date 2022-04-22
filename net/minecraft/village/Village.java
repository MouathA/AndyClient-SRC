package net.minecraft.village;

import com.google.common.collect.*;
import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class Village
{
    private World worldObj;
    private final List villageDoorInfoList;
    private BlockPos centerHelper;
    private BlockPos center;
    private int villageRadius;
    private int lastAddDoorTimestamp;
    private int tickCounter;
    private int numVillagers;
    private int noBreedTicks;
    private TreeMap playerReputation;
    private List villageAgressors;
    private int numIronGolems;
    private static final String __OBFID;
    
    public Village() {
        this.villageDoorInfoList = Lists.newArrayList();
        this.centerHelper = BlockPos.ORIGIN;
        this.center = BlockPos.ORIGIN;
        this.playerReputation = new TreeMap();
        this.villageAgressors = Lists.newArrayList();
    }
    
    public Village(final World worldObj) {
        this.villageDoorInfoList = Lists.newArrayList();
        this.centerHelper = BlockPos.ORIGIN;
        this.center = BlockPos.ORIGIN;
        this.playerReputation = new TreeMap();
        this.villageAgressors = Lists.newArrayList();
        this.worldObj = worldObj;
    }
    
    public void func_82691_a(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    public void tick(final int tickCounter) {
        this.tickCounter = tickCounter;
        this.removeDeadAndOutOfRangeDoors();
        this.removeDeadAndOldAgressors();
        if (tickCounter % 20 == 0) {
            this.updateNumVillagers();
        }
        if (tickCounter % 30 == 0) {
            this.updateNumIronGolems();
        }
        if (this.numIronGolems < this.numVillagers / 10 && this.villageDoorInfoList.size() > 20 && this.worldObj.rand.nextInt(7000) == 0) {
            final Vec3 func_179862_a = this.func_179862_a(this.center, 2, 4, 2);
            if (func_179862_a != null) {
                final EntityIronGolem entityIronGolem = new EntityIronGolem(this.worldObj);
                entityIronGolem.setPosition(func_179862_a.xCoord, func_179862_a.yCoord, func_179862_a.zCoord);
                this.worldObj.spawnEntityInWorld(entityIronGolem);
                ++this.numIronGolems;
            }
        }
    }
    
    private Vec3 func_179862_a(final BlockPos blockPos, final int n, final int n2, final int n3) {
        while (0 < 10) {
            final BlockPos add = blockPos.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
            if (this.func_179866_a(add) && this.func_179861_a(new BlockPos(n, n2, n3), add)) {
                return new Vec3(add.getX(), add.getY(), add.getZ());
            }
            int n4 = 0;
            ++n4;
        }
        return null;
    }
    
    private boolean func_179861_a(final BlockPos blockPos, final BlockPos blockPos2) {
        if (!World.doesBlockHaveSolidTopSurface(this.worldObj, blockPos2.offsetDown())) {
            return false;
        }
        final int n = blockPos2.getX() - blockPos.getX() / 2;
        final int n2 = blockPos2.getZ() - blockPos.getZ() / 2;
        for (int i = n; i < n + blockPos.getX(); ++i) {
            for (int j = blockPos2.getY(); j < blockPos2.getY() + blockPos.getY(); ++j) {
                for (int k = n2; k < n2 + blockPos.getZ(); ++k) {
                    if (this.worldObj.getBlockState(new BlockPos(i, j, k)).getBlock().isNormalCube()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private void updateNumIronGolems() {
        this.numIronGolems = this.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, new AxisAlignedBB(this.center.getX() - this.villageRadius, this.center.getY() - 4, this.center.getZ() - this.villageRadius, this.center.getX() + this.villageRadius, this.center.getY() + 4, this.center.getZ() + this.villageRadius)).size();
    }
    
    private void updateNumVillagers() {
        this.numVillagers = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, new AxisAlignedBB(this.center.getX() - this.villageRadius, this.center.getY() - 4, this.center.getZ() - this.villageRadius, this.center.getX() + this.villageRadius, this.center.getY() + 4, this.center.getZ() + this.villageRadius)).size();
        if (this.numVillagers == 0) {
            this.playerReputation.clear();
        }
    }
    
    public BlockPos func_180608_a() {
        return this.center;
    }
    
    public int getVillageRadius() {
        return this.villageRadius;
    }
    
    public int getNumVillageDoors() {
        return this.villageDoorInfoList.size();
    }
    
    public int getTicksSinceLastDoorAdding() {
        return this.tickCounter - this.lastAddDoorTimestamp;
    }
    
    public int getNumVillagers() {
        return this.numVillagers;
    }
    
    public boolean func_179866_a(final BlockPos blockPos) {
        return this.center.distanceSq(blockPos) < this.villageRadius * this.villageRadius;
    }
    
    public List getVillageDoorInfoList() {
        return this.villageDoorInfoList;
    }
    
    public VillageDoorInfo func_179865_b(final BlockPos blockPos) {
        VillageDoorInfo villageDoorInfo = null;
        for (final VillageDoorInfo villageDoorInfo2 : this.villageDoorInfoList) {
            if (villageDoorInfo2.func_179848_a(blockPos) < Integer.MAX_VALUE) {
                villageDoorInfo = villageDoorInfo2;
            }
        }
        return villageDoorInfo;
    }
    
    public VillageDoorInfo func_179863_c(final BlockPos blockPos) {
        VillageDoorInfo villageDoorInfo = null;
        for (final VillageDoorInfo villageDoorInfo2 : this.villageDoorInfoList) {
            final int func_179848_a = villageDoorInfo2.func_179848_a(blockPos);
            int doorOpeningRestrictionCounter;
            if (func_179848_a > 256) {
                doorOpeningRestrictionCounter = func_179848_a * 1000;
            }
            else {
                doorOpeningRestrictionCounter = villageDoorInfo2.getDoorOpeningRestrictionCounter();
            }
            if (doorOpeningRestrictionCounter < Integer.MAX_VALUE) {
                villageDoorInfo = villageDoorInfo2;
            }
        }
        return villageDoorInfo;
    }
    
    public VillageDoorInfo func_179864_e(final BlockPos blockPos) {
        if (this.center.distanceSq(blockPos) > this.villageRadius * this.villageRadius) {
            return null;
        }
        for (final VillageDoorInfo villageDoorInfo : this.villageDoorInfoList) {
            if (villageDoorInfo.func_179852_d().getX() == blockPos.getX() && villageDoorInfo.func_179852_d().getZ() == blockPos.getZ() && Math.abs(villageDoorInfo.func_179852_d().getY() - blockPos.getY()) <= 1) {
                return villageDoorInfo;
            }
        }
        return null;
    }
    
    public void addVillageDoorInfo(final VillageDoorInfo villageDoorInfo) {
        this.villageDoorInfoList.add(villageDoorInfo);
        this.centerHelper = this.centerHelper.add(villageDoorInfo.func_179852_d());
        this.updateVillageRadiusAndCenter();
        this.lastAddDoorTimestamp = villageDoorInfo.getInsidePosY();
    }
    
    public boolean isAnnihilated() {
        return this.villageDoorInfoList.isEmpty();
    }
    
    public void addOrRenewAgressor(final EntityLivingBase entityLivingBase) {
        for (final VillageAgressor villageAgressor : this.villageAgressors) {
            if (villageAgressor.agressor == entityLivingBase) {
                villageAgressor.agressionTime = this.tickCounter;
                return;
            }
        }
        this.villageAgressors.add(new VillageAgressor(entityLivingBase, this.tickCounter));
    }
    
    public EntityLivingBase findNearestVillageAggressor(final EntityLivingBase entityLivingBase) {
        double n = Double.MAX_VALUE;
        VillageAgressor villageAgressor = null;
        while (0 < this.villageAgressors.size()) {
            final VillageAgressor villageAgressor2 = this.villageAgressors.get(0);
            final double distanceSqToEntity = villageAgressor2.agressor.getDistanceSqToEntity(entityLivingBase);
            if (distanceSqToEntity <= n) {
                villageAgressor = villageAgressor2;
                n = distanceSqToEntity;
            }
            int n2 = 0;
            ++n2;
        }
        return (villageAgressor != null) ? villageAgressor.agressor : null;
    }
    
    public EntityPlayer func_82685_c(final EntityLivingBase entityLivingBase) {
        double n = Double.MAX_VALUE;
        EntityPlayer entityPlayer = null;
        for (final String s : this.playerReputation.keySet()) {
            if (this.isPlayerReputationTooLow(s)) {
                final EntityPlayer playerEntityByName = this.worldObj.getPlayerEntityByName(s);
                if (playerEntityByName == null) {
                    continue;
                }
                final double distanceSqToEntity = playerEntityByName.getDistanceSqToEntity(entityLivingBase);
                if (distanceSqToEntity > n) {
                    continue;
                }
                entityPlayer = playerEntityByName;
                n = distanceSqToEntity;
            }
        }
        return entityPlayer;
    }
    
    private void removeDeadAndOldAgressors() {
        final Iterator<VillageAgressor> iterator = (Iterator<VillageAgressor>)this.villageAgressors.iterator();
        while (iterator.hasNext()) {
            final VillageAgressor villageAgressor = iterator.next();
            if (!villageAgressor.agressor.isEntityAlive() || Math.abs(this.tickCounter - villageAgressor.agressionTime) > 300) {
                iterator.remove();
            }
        }
    }
    
    private void removeDeadAndOutOfRangeDoors() {
        final boolean b = this.worldObj.rand.nextInt(50) == 0;
        final Iterator<VillageDoorInfo> iterator = (Iterator<VillageDoorInfo>)this.villageDoorInfoList.iterator();
        while (iterator.hasNext()) {
            final VillageDoorInfo villageDoorInfo = iterator.next();
            if (b) {
                villageDoorInfo.resetDoorOpeningRestrictionCounter();
            }
            if (!this.func_179860_f(villageDoorInfo.func_179852_d()) || Math.abs(this.tickCounter - villageDoorInfo.getInsidePosY()) > 1200) {
                this.centerHelper = this.centerHelper.add(villageDoorInfo.func_179852_d().multiply(-1));
                villageDoorInfo.func_179853_a(true);
                iterator.remove();
            }
        }
        if (true) {
            this.updateVillageRadiusAndCenter();
        }
    }
    
    private boolean func_179860_f(final BlockPos blockPos) {
        final Block block = this.worldObj.getBlockState(blockPos).getBlock();
        return block instanceof BlockDoor && block.getMaterial() == Material.wood;
    }
    
    private void updateVillageRadiusAndCenter() {
        final int size = this.villageDoorInfoList.size();
        if (size == 0) {
            this.center = new BlockPos(0, 0, 0);
            this.villageRadius = 0;
        }
        else {
            this.center = new BlockPos(this.centerHelper.getX() / size, this.centerHelper.getY() / size, this.centerHelper.getZ() / size);
            final Iterator<VillageDoorInfo> iterator = this.villageDoorInfoList.iterator();
            while (iterator.hasNext()) {
                Math.max(iterator.next().func_179848_a(this.center), 0);
            }
            this.villageRadius = Math.max(32, (int)Math.sqrt(0) + 1);
        }
    }
    
    public int getReputationForPlayer(final String s) {
        final Integer n = this.playerReputation.get(s);
        return (n != null) ? n : 0;
    }
    
    public int setReputationForPlayer(final String s, final int n) {
        final int clamp_int = MathHelper.clamp_int(this.getReputationForPlayer(s) + n, -30, 10);
        this.playerReputation.put(s, clamp_int);
        return clamp_int;
    }
    
    public boolean isPlayerReputationTooLow(final String s) {
        return this.getReputationForPlayer(s) <= -15;
    }
    
    public void readVillageDataFromNBT(final NBTTagCompound p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: ldc_w           "PopSize"
        //     5: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //     8: putfield        net/minecraft/village/Village.numVillagers:I
        //    11: aload_0        
        //    12: aload_1        
        //    13: ldc_w           "Radius"
        //    16: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    19: putfield        net/minecraft/village/Village.villageRadius:I
        //    22: aload_0        
        //    23: aload_1        
        //    24: ldc_w           "Golems"
        //    27: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    30: putfield        net/minecraft/village/Village.numIronGolems:I
        //    33: aload_0        
        //    34: aload_1        
        //    35: ldc_w           "Stable"
        //    38: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    41: putfield        net/minecraft/village/Village.lastAddDoorTimestamp:I
        //    44: aload_0        
        //    45: aload_1        
        //    46: ldc_w           "Tick"
        //    49: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    52: putfield        net/minecraft/village/Village.tickCounter:I
        //    55: aload_0        
        //    56: aload_1        
        //    57: ldc_w           "MTick"
        //    60: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    63: putfield        net/minecraft/village/Village.noBreedTicks:I
        //    66: aload_0        
        //    67: new             Lnet/minecraft/util/BlockPos;
        //    70: dup            
        //    71: aload_1        
        //    72: ldc_w           "CX"
        //    75: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    78: aload_1        
        //    79: ldc_w           "CY"
        //    82: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    85: aload_1        
        //    86: ldc_w           "CZ"
        //    89: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    92: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //    95: putfield        net/minecraft/village/Village.center:Lnet/minecraft/util/BlockPos;
        //    98: aload_0        
        //    99: new             Lnet/minecraft/util/BlockPos;
        //   102: dup            
        //   103: aload_1        
        //   104: ldc_w           "ACX"
        //   107: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   110: aload_1        
        //   111: ldc_w           "ACY"
        //   114: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   117: aload_1        
        //   118: ldc_w           "ACZ"
        //   121: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   124: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   127: putfield        net/minecraft/village/Village.centerHelper:Lnet/minecraft/util/BlockPos;
        //   130: aload_1        
        //   131: ldc_w           "Doors"
        //   134: bipush          10
        //   136: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   139: astore_2       
        //   140: goto            229
        //   143: aload_2        
        //   144: iconst_0       
        //   145: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   148: astore          4
        //   150: new             Lnet/minecraft/village/VillageDoorInfo;
        //   153: dup            
        //   154: new             Lnet/minecraft/util/BlockPos;
        //   157: dup            
        //   158: aload           4
        //   160: ldc_w           "X"
        //   163: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   166: aload           4
        //   168: ldc_w           "Y"
        //   171: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   174: aload           4
        //   176: ldc_w           "Z"
        //   179: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   182: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   185: aload           4
        //   187: ldc_w           "IDX"
        //   190: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   193: aload           4
        //   195: ldc_w           "IDZ"
        //   198: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   201: aload           4
        //   203: ldc_w           "TS"
        //   206: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   209: invokespecial   net/minecraft/village/VillageDoorInfo.<init>:(Lnet/minecraft/util/BlockPos;III)V
        //   212: astore          5
        //   214: aload_0        
        //   215: getfield        net/minecraft/village/Village.villageDoorInfoList:Ljava/util/List;
        //   218: aload           5
        //   220: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   225: pop            
        //   226: iinc            3, 1
        //   229: iconst_0       
        //   230: aload_2        
        //   231: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   234: if_icmplt       143
        //   237: aload_1        
        //   238: ldc_w           "Players"
        //   241: bipush          10
        //   243: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   246: astore_3       
        //   247: goto            287
        //   250: aload_3        
        //   251: iconst_0       
        //   252: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   255: astore          5
        //   257: aload_0        
        //   258: getfield        net/minecraft/village/Village.playerReputation:Ljava/util/TreeMap;
        //   261: aload           5
        //   263: ldc_w           "Name"
        //   266: invokevirtual   net/minecraft/nbt/NBTTagCompound.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   269: aload           5
        //   271: ldc_w           "S"
        //   274: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   277: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   280: invokevirtual   java/util/TreeMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   283: pop            
        //   284: iinc            4, 1
        //   287: iconst_0       
        //   288: aload_3        
        //   289: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   292: if_icmplt       250
        //   295: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void writeVillageDataToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("PopSize", this.numVillagers);
        nbtTagCompound.setInteger("Radius", this.villageRadius);
        nbtTagCompound.setInteger("Golems", this.numIronGolems);
        nbtTagCompound.setInteger("Stable", this.lastAddDoorTimestamp);
        nbtTagCompound.setInteger("Tick", this.tickCounter);
        nbtTagCompound.setInteger("MTick", this.noBreedTicks);
        nbtTagCompound.setInteger("CX", this.center.getX());
        nbtTagCompound.setInteger("CY", this.center.getY());
        nbtTagCompound.setInteger("CZ", this.center.getZ());
        nbtTagCompound.setInteger("ACX", this.centerHelper.getX());
        nbtTagCompound.setInteger("ACY", this.centerHelper.getY());
        nbtTagCompound.setInteger("ACZ", this.centerHelper.getZ());
        final NBTTagList list = new NBTTagList();
        for (final VillageDoorInfo villageDoorInfo : this.villageDoorInfoList) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            nbtTagCompound2.setInteger("X", villageDoorInfo.func_179852_d().getX());
            nbtTagCompound2.setInteger("Y", villageDoorInfo.func_179852_d().getY());
            nbtTagCompound2.setInteger("Z", villageDoorInfo.func_179852_d().getZ());
            nbtTagCompound2.setInteger("IDX", villageDoorInfo.func_179847_f());
            nbtTagCompound2.setInteger("IDZ", villageDoorInfo.func_179855_g());
            nbtTagCompound2.setInteger("TS", villageDoorInfo.getInsidePosY());
            list.appendTag(nbtTagCompound2);
        }
        nbtTagCompound.setTag("Doors", list);
        final NBTTagList list2 = new NBTTagList();
        for (final String s : this.playerReputation.keySet()) {
            final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
            nbtTagCompound3.setString("Name", s);
            nbtTagCompound3.setInteger("S", this.playerReputation.get(s));
            list2.appendTag(nbtTagCompound3);
        }
        nbtTagCompound.setTag("Players", list2);
    }
    
    public void endMatingSeason() {
        this.noBreedTicks = this.tickCounter;
    }
    
    public boolean isMatingSeason() {
        return this.noBreedTicks == 0 || this.tickCounter - this.noBreedTicks >= 3600;
    }
    
    public void setDefaultPlayerReputation(final int n) {
        final Iterator<String> iterator = this.playerReputation.keySet().iterator();
        while (iterator.hasNext()) {
            this.setReputationForPlayer(iterator.next(), n);
        }
    }
    
    static {
        __OBFID = "CL_00001631";
    }
    
    class VillageAgressor
    {
        public EntityLivingBase agressor;
        public int agressionTime;
        private static final String __OBFID;
        final Village this$0;
        
        VillageAgressor(final Village this$0, final EntityLivingBase agressor, final int agressionTime) {
            this.this$0 = this$0;
            this.agressor = agressor;
            this.agressionTime = agressionTime;
        }
        
        static {
            __OBFID = "CL_00001632";
        }
    }
}
