package net.minecraft.village;

import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class VillageSiege
{
    private World worldObj;
    private boolean field_75535_b;
    private int field_75536_c;
    private int field_75533_d;
    private int field_75534_e;
    private Village theVillage;
    private int field_75532_g;
    private int field_75538_h;
    private int field_75539_i;
    private static final String __OBFID;
    
    public VillageSiege(final World worldObj) {
        this.field_75536_c = -1;
        this.worldObj = worldObj;
    }
    
    public void tick() {
        if (this.worldObj.isDaytime()) {
            this.field_75536_c = 0;
        }
        else if (this.field_75536_c != 2) {
            if (this.field_75536_c == 0) {
                final float celestialAngle = this.worldObj.getCelestialAngle(0.0f);
                if (celestialAngle < 0.5 || celestialAngle > 0.501) {
                    return;
                }
                this.field_75536_c = ((this.worldObj.rand.nextInt(10) == 0) ? 1 : 2);
                this.field_75535_b = false;
                if (this.field_75536_c == 2) {
                    return;
                }
            }
            if (this.field_75536_c != -1) {
                if (!this.field_75535_b) {
                    if (!this.func_75529_b()) {
                        return;
                    }
                    this.field_75535_b = true;
                }
                if (this.field_75534_e > 0) {
                    --this.field_75534_e;
                }
                else {
                    this.field_75534_e = 2;
                    if (this.field_75533_d > 0) {
                        this.spawnZombie();
                        --this.field_75533_d;
                    }
                    else {
                        this.field_75536_c = 2;
                    }
                }
            }
        }
    }
    
    private boolean func_75529_b() {
        for (final EntityPlayer entityPlayer : this.worldObj.playerEntities) {
            if (!entityPlayer.func_175149_v()) {
                this.theVillage = this.worldObj.getVillageCollection().func_176056_a(new BlockPos(entityPlayer), 1);
                if (this.theVillage == null || this.theVillage.getNumVillageDoors() < 10 || this.theVillage.getTicksSinceLastDoorAdding() < 20 || this.theVillage.getNumVillagers() < 20) {
                    continue;
                }
                final BlockPos func_180608_a = this.theVillage.func_180608_a();
                final float n = (float)this.theVillage.getVillageRadius();
                while (0 < 10) {
                    final float n2 = this.worldObj.rand.nextFloat() * 3.1415927f * 2.0f;
                    this.field_75532_g = func_180608_a.getX() + (int)(MathHelper.cos(n2) * n * 0.9);
                    this.field_75538_h = func_180608_a.getY();
                    this.field_75539_i = func_180608_a.getZ() + (int)(MathHelper.sin(n2) * n * 0.9);
                    for (final Village village : this.worldObj.getVillageCollection().getVillageList()) {
                        if (village != this.theVillage && village.func_179866_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i))) {
                            break;
                        }
                    }
                    if (!true) {
                        break;
                    }
                    int n3 = 0;
                    ++n3;
                }
                if (true) {
                    return false;
                }
                if (this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i)) != null) {
                    this.field_75534_e = 0;
                    this.field_75533_d = 20;
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    private boolean spawnZombie() {
        final Vec3 func_179867_a = this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
        if (func_179867_a == null) {
            return false;
        }
        final EntityZombie entityZombie = new EntityZombie(this.worldObj);
        entityZombie.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(entityZombie)), null);
        entityZombie.setVillager(false);
        entityZombie.setLocationAndAngles(func_179867_a.xCoord, func_179867_a.yCoord, func_179867_a.zCoord, this.worldObj.rand.nextFloat() * 360.0f, 0.0f);
        this.worldObj.spawnEntityInWorld(entityZombie);
        entityZombie.func_175449_a(this.theVillage.func_180608_a(), this.theVillage.getVillageRadius());
        return true;
    }
    
    private Vec3 func_179867_a(final BlockPos blockPos) {
        while (0 < 10) {
            final BlockPos add = blockPos.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
            if (this.theVillage.func_179866_a(add) && SpawnerAnimals.func_180267_a(EntityLiving.SpawnPlacementType.ON_GROUND, this.worldObj, add)) {
                return new Vec3(add.getX(), add.getY(), add.getZ());
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    static {
        __OBFID = "CL_00001634";
    }
}
