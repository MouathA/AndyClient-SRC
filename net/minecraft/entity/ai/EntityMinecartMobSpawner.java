package net.minecraft.entity.ai;

import net.minecraft.entity.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;

public class EntityMinecartMobSpawner extends EntityMinecart
{
    private final MobSpawnerBaseLogic mobSpawnerLogic;
    private static final String __OBFID;
    
    public EntityMinecartMobSpawner(final World world) {
        super(world);
        this.mobSpawnerLogic = new MobSpawnerBaseLogic() {
            private static final String __OBFID;
            final EntityMinecartMobSpawner this$0;
            
            @Override
            public void func_98267_a(final int n) {
                this.this$0.worldObj.setEntityState(this.this$0, (byte)n);
            }
            
            @Override
            public World getSpawnerWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public BlockPos func_177221_b() {
                return new BlockPos(this.this$0);
            }
            
            static {
                __OBFID = "CL_00001679";
            }
        };
    }
    
    public EntityMinecartMobSpawner(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.mobSpawnerLogic = new MobSpawnerBaseLogic() {
            private static final String __OBFID;
            final EntityMinecartMobSpawner this$0;
            
            @Override
            public void func_98267_a(final int n) {
                this.this$0.worldObj.setEntityState(this.this$0, (byte)n);
            }
            
            @Override
            public World getSpawnerWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public BlockPos func_177221_b() {
                return new BlockPos(this.this$0);
            }
            
            static {
                __OBFID = "CL_00001679";
            }
        };
    }
    
    @Override
    public EnumMinecartType func_180456_s() {
        return EnumMinecartType.SPAWNER;
    }
    
    @Override
    public IBlockState func_180457_u() {
        return Blocks.mob_spawner.getDefaultState();
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.mobSpawnerLogic.readFromNBT(nbtTagCompound);
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        this.mobSpawnerLogic.writeToNBT(nbtTagCompound);
    }
    
    @Override
    public void handleHealthUpdate(final byte delayToMin) {
        this.mobSpawnerLogic.setDelayToMin(delayToMin);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.mobSpawnerLogic.updateSpawner();
    }
    
    public MobSpawnerBaseLogic func_98039_d() {
        return this.mobSpawnerLogic;
    }
    
    static {
        __OBFID = "CL_00001678";
    }
}
