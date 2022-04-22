package net.minecraft.tileentity;

import net.minecraft.server.gui.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;

public class TileEntityMobSpawner extends TileEntity implements IUpdatePlayerListBox
{
    private final MobSpawnerBaseLogic field_145882_a;
    private static final String __OBFID;
    
    public TileEntityMobSpawner() {
        this.field_145882_a = new MobSpawnerBaseLogic() {
            private static final String __OBFID;
            final TileEntityMobSpawner this$0;
            
            @Override
            public void func_98267_a(final int n) {
                this.this$0.worldObj.addBlockEvent(this.this$0.pos, Blocks.mob_spawner, n, 0);
            }
            
            @Override
            public World getSpawnerWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public BlockPos func_177221_b() {
                return this.this$0.pos;
            }
            
            @Override
            public void setRandomEntity(final WeightedRandomMinecart randomEntity) {
                super.setRandomEntity(randomEntity);
                if (this.getSpawnerWorld() != null) {
                    this.getSpawnerWorld().markBlockForUpdate(this.this$0.pos);
                }
            }
            
            static {
                __OBFID = "CL_00000361";
            }
        };
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.field_145882_a.readFromNBT(nbtTagCompound);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        this.field_145882_a.writeToNBT(nbtTagCompound);
    }
    
    @Override
    public void update() {
        this.field_145882_a.updateSpawner();
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        nbtTagCompound.removeTag("SpawnPotentials");
        return new S35PacketUpdateTileEntity(this.pos, 1, nbtTagCompound);
    }
    
    @Override
    public boolean receiveClientEvent(final int delayToMin, final int n) {
        return this.field_145882_a.setDelayToMin(delayToMin) || super.receiveClientEvent(delayToMin, n);
    }
    
    public MobSpawnerBaseLogic getSpawnerBaseLogic() {
        return this.field_145882_a;
    }
    
    static {
        __OBFID = "CL_00000360";
    }
}
