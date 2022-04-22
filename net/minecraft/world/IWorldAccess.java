package net.minecraft.world;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public interface IWorldAccess
{
    void markBlockForUpdate(final BlockPos p0);
    
    void notifyLightSet(final BlockPos p0);
    
    void markBlockRangeForRenderUpdate(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void playSound(final String p0, final double p1, final double p2, final double p3, final float p4, final float p5);
    
    void playSoundToNearExcept(final EntityPlayer p0, final String p1, final double p2, final double p3, final double p4, final float p5, final float p6);
    
    void func_180442_a(final int p0, final boolean p1, final double p2, final double p3, final double p4, final double p5, final double p6, final double p7, final int... p8);
    
    void onEntityAdded(final Entity p0);
    
    void onEntityRemoved(final Entity p0);
    
    void func_174961_a(final String p0, final BlockPos p1);
    
    void func_180440_a(final int p0, final BlockPos p1, final int p2);
    
    void func_180439_a(final EntityPlayer p0, final int p1, final BlockPos p2, final int p3);
    
    void sendBlockBreakProgress(final int p0, final BlockPos p1, final int p2);
}
