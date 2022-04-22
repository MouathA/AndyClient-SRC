package wdl.api;

import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.world.chunk.*;

public interface ITileEntityImportationIdentifier extends IWDLMod
{
    boolean shouldImportTileEntity(final String p0, final BlockPos p1, final Block p2, final NBTTagCompound p3, final Chunk p4);
}
