package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;

public class ItemMapBase extends Item
{
    private static final String __OBFID;
    
    @Override
    public boolean isMap() {
        return true;
    }
    
    public Packet createMapDataPacket(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        return null;
    }
    
    static {
        __OBFID = "CL_00000004";
    }
}
