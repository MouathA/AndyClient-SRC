package net.minecraft.village;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

public class VillageCollection extends WorldSavedData
{
    private World worldObj;
    private final List villagerPositionsList;
    private final List newDoors;
    private final List villageList;
    private int tickCounter;
    private static final String __OBFID;
    
    public VillageCollection(final String s) {
        super(s);
        this.villagerPositionsList = Lists.newArrayList();
        this.newDoors = Lists.newArrayList();
        this.villageList = Lists.newArrayList();
    }
    
    public VillageCollection(final World worldObj) {
        super(func_176062_a(worldObj.provider));
        this.villagerPositionsList = Lists.newArrayList();
        this.newDoors = Lists.newArrayList();
        this.villageList = Lists.newArrayList();
        this.worldObj = worldObj;
        this.markDirty();
    }
    
    public void func_82566_a(final World worldObj) {
        this.worldObj = worldObj;
        final Iterator<Village> iterator = this.villageList.iterator();
        while (iterator.hasNext()) {
            iterator.next().func_82691_a(worldObj);
        }
    }
    
    public void func_176060_a(final BlockPos blockPos) {
        if (this.villagerPositionsList.size() <= 64 && !this.func_176057_e(blockPos)) {
            this.villagerPositionsList.add(blockPos);
        }
    }
    
    public void tick() {
        ++this.tickCounter;
        final Iterator<Village> iterator = this.villageList.iterator();
        while (iterator.hasNext()) {
            iterator.next().tick(this.tickCounter);
        }
        this.removeAnnihilatedVillages();
        this.dropOldestVillagerPosition();
        this.addNewDoorsToVillageOrCreateVillage();
        if (this.tickCounter % 400 == 0) {
            this.markDirty();
        }
    }
    
    private void removeAnnihilatedVillages() {
        final Iterator<Village> iterator = (Iterator<Village>)this.villageList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isAnnihilated()) {
                iterator.remove();
                this.markDirty();
            }
        }
    }
    
    public List getVillageList() {
        return this.villageList;
    }
    
    public Village func_176056_a(final BlockPos blockPos, final int n) {
        Village village = null;
        double n2 = 3.4028234663852886E38;
        for (final Village village2 : this.villageList) {
            final double distanceSq = village2.func_180608_a().distanceSq(blockPos);
            if (distanceSq < n2) {
                final float n3 = (float)(n + village2.getVillageRadius());
                if (distanceSq > n3 * n3) {
                    continue;
                }
                village = village2;
                n2 = distanceSq;
            }
        }
        return village;
    }
    
    private void dropOldestVillagerPosition() {
        if (!this.villagerPositionsList.isEmpty()) {
            this.func_180609_b(this.villagerPositionsList.remove(0));
        }
    }
    
    private void addNewDoorsToVillageOrCreateVillage() {
        while (0 < this.newDoors.size()) {
            final VillageDoorInfo villageDoorInfo = this.newDoors.get(0);
            Village func_176056_a = this.func_176056_a(villageDoorInfo.func_179852_d(), 32);
            if (func_176056_a == null) {
                func_176056_a = new Village(this.worldObj);
                this.villageList.add(func_176056_a);
                this.markDirty();
            }
            func_176056_a.addVillageDoorInfo(villageDoorInfo);
            int n = 0;
            ++n;
        }
        this.newDoors.clear();
    }
    
    private void func_180609_b(final BlockPos blockPos) {
        while (-16 < 16) {
            while (-4 < 4) {
                while (-16 < 16) {
                    final BlockPos add = blockPos.add(-16, -4, -16);
                    if (this.func_176058_f(add)) {
                        final VillageDoorInfo func_176055_c = this.func_176055_c(add);
                        if (func_176055_c == null) {
                            this.func_176059_d(add);
                        }
                        else {
                            func_176055_c.func_179849_a(this.tickCounter);
                        }
                    }
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    private VillageDoorInfo func_176055_c(final BlockPos blockPos) {
        for (final VillageDoorInfo villageDoorInfo : this.newDoors) {
            if (villageDoorInfo.func_179852_d().getX() == blockPos.getX() && villageDoorInfo.func_179852_d().getZ() == blockPos.getZ() && Math.abs(villageDoorInfo.func_179852_d().getY() - blockPos.getY()) <= 1) {
                return villageDoorInfo;
            }
        }
        final Iterator<Village> iterator2 = this.villageList.iterator();
        while (iterator2.hasNext()) {
            final VillageDoorInfo func_179864_e = iterator2.next().func_179864_e(blockPos);
            if (func_179864_e != null) {
                return func_179864_e;
            }
        }
        return null;
    }
    
    private void func_176059_d(final BlockPos blockPos) {
        final EnumFacing func_176517_h = BlockDoor.func_176517_h(this.worldObj, blockPos);
        final EnumFacing opposite = func_176517_h.getOpposite();
        final int func_176061_a = this.func_176061_a(blockPos, func_176517_h, 5);
        final int func_176061_a2 = this.func_176061_a(blockPos, opposite, func_176061_a + 1);
        if (func_176061_a != func_176061_a2) {
            this.newDoors.add(new VillageDoorInfo(blockPos, (func_176061_a < func_176061_a2) ? func_176517_h : opposite, this.tickCounter));
        }
    }
    
    private int func_176061_a(final BlockPos blockPos, final EnumFacing enumFacing, final int n) {
        while (1 <= 5) {
            if (this.worldObj.isAgainstSky(blockPos.offset(enumFacing, 1))) {
                int n2 = 0;
                ++n2;
                if (0 >= n) {
                    return 0;
                }
            }
            int n3 = 0;
            ++n3;
        }
        return 0;
    }
    
    private boolean func_176057_e(final BlockPos blockPos) {
        final Iterator<BlockPos> iterator = this.villagerPositionsList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(blockPos)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean func_176058_f(final BlockPos blockPos) {
        final Block block = this.worldObj.getBlockState(blockPos).getBlock();
        return block instanceof BlockDoor && block.getMaterial() == Material.wood;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.tickCounter = nbtTagCompound.getInteger("Tick");
        final NBTTagList tagList = nbtTagCompound.getTagList("Villages", 10);
        while (0 < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
            final Village village = new Village();
            village.readVillageDataFromNBT(compoundTag);
            this.villageList.add(village);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("Tick", this.tickCounter);
        final NBTTagList list = new NBTTagList();
        for (final Village village : this.villageList) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            village.writeVillageDataToNBT(nbtTagCompound2);
            list.appendTag(nbtTagCompound2);
        }
        nbtTagCompound.setTag("Villages", list);
    }
    
    public static String func_176062_a(final WorldProvider worldProvider) {
        return "villages" + worldProvider.getInternalNameSuffix();
    }
    
    static {
        __OBFID = "CL_00001635";
    }
}
