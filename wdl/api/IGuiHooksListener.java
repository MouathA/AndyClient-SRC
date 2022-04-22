package wdl.api;

import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;

public interface IGuiHooksListener extends IWDLMod
{
    boolean onBlockGuiClosed(final WorldClient p0, final BlockPos p1, final Container p2);
    
    boolean onEntityGuiClosed(final WorldClient p0, final Entity p1, final Container p2);
}
