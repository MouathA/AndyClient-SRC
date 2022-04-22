package net.minecraft.tileentity;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class TileEntityNote extends TileEntity
{
    public byte note;
    public boolean previousRedstoneState;
    private static final String __OBFID;
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte("note", this.note);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.note = nbtTagCompound.getByte("note");
        this.note = (byte)MathHelper.clamp_int(this.note, 0, 24);
    }
    
    public void changePitch() {
        this.note = (byte)((this.note + 1) % 25);
        this.markDirty();
    }
    
    public void func_175108_a(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos.offsetUp()).getBlock().getMaterial() == Material.air) {
            final Material material = world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial();
            if (material == Material.rock) {}
            if (material == Material.sand) {}
            if (material == Material.glass) {}
            if (material == Material.wood) {}
            world.addBlockEvent(blockPos, Blocks.noteblock, 4, this.note);
        }
    }
    
    static {
        __OBFID = "CL_00000362";
    }
}
