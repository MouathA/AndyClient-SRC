package net.minecraft.tileentity;

import net.minecraft.nbt.*;

public class TileEntityComparator extends TileEntity
{
    private int outputSignal;
    private static final String __OBFID;
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("OutputSignal", this.outputSignal);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.outputSignal = nbtTagCompound.getInteger("OutputSignal");
    }
    
    public int getOutputSignal() {
        return this.outputSignal;
    }
    
    public void setOutputSignal(final int outputSignal) {
        this.outputSignal = outputSignal;
    }
    
    static {
        __OBFID = "CL_00000349";
    }
}
