package wdl.api;

import net.minecraft.client.entity.*;
import net.minecraft.world.storage.*;
import net.minecraft.nbt.*;

public interface IPlayerInfoEditor extends IWDLMod
{
    void editPlayerInfo(final EntityPlayerSP p0, final SaveHandler p1, final NBTTagCompound p2);
}
