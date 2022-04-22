package net.minecraft.tileentity;

import net.minecraft.server.gui.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;

public class TileEntityEnderChest extends TileEntity implements IUpdatePlayerListBox
{
    public float field_145972_a;
    public float prevLidAngle;
    public int field_145973_j;
    private int field_145974_k;
    private static final String __OBFID;
    
    @Override
    public void update() {
        if (++this.field_145974_k % 20 * 4 == 0) {
            this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.field_145973_j);
        }
        this.prevLidAngle = this.field_145972_a;
        final int x = this.pos.getX();
        final int y = this.pos.getY();
        final int z = this.pos.getZ();
        final float n = 0.1f;
        if (this.field_145973_j > 0 && this.field_145972_a == 0.0f) {
            this.worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.chestopen", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.field_145973_j == 0 && this.field_145972_a > 0.0f) || (this.field_145973_j > 0 && this.field_145972_a < 1.0f)) {
            final float field_145972_a = this.field_145972_a;
            if (this.field_145973_j > 0) {
                this.field_145972_a += n;
            }
            else {
                this.field_145972_a -= n;
            }
            if (this.field_145972_a > 1.0f) {
                this.field_145972_a = 1.0f;
            }
            final float n2 = 0.5f;
            if (this.field_145972_a < n2 && field_145972_a >= n2) {
                this.worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.chestclosed", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (this.field_145972_a < 0.0f) {
                this.field_145972_a = 0.0f;
            }
        }
    }
    
    @Override
    public boolean receiveClientEvent(final int n, final int field_145973_j) {
        if (n == 1) {
            this.field_145973_j = field_145973_j;
            return true;
        }
        return super.receiveClientEvent(n, field_145973_j);
    }
    
    @Override
    public void invalidate() {
        this.updateContainingBlockInfo();
        super.invalidate();
    }
    
    public void func_145969_a() {
        ++this.field_145973_j;
        this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.field_145973_j);
    }
    
    public void func_145970_b() {
        --this.field_145973_j;
        this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.field_145973_j);
    }
    
    public boolean func_145971_a(final EntityPlayer entityPlayer) {
        return this.worldObj.getTileEntity(this.pos) == this && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
    }
    
    static {
        __OBFID = "CL_00000355";
    }
}
