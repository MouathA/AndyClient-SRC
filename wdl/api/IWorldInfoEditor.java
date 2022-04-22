package wdl.api;

import net.minecraft.client.multiplayer.*;
import net.minecraft.world.storage.*;
import net.minecraft.nbt.*;

public interface IWorldInfoEditor extends IWDLMod
{
    void editWorldInfo(final WorldClient p0, final WorldInfo p1, final SaveHandler p2, final NBTTagCompound p3);
}
