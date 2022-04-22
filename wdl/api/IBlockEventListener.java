package wdl.api;

import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public interface IBlockEventListener extends IWDLMod
{
    void onBlockEvent(final WorldClient p0, final BlockPos p1, final Block p2, final int p3, final int p4);
}
