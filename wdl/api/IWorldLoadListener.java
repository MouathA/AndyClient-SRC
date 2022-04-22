package wdl.api;

import net.minecraft.client.multiplayer.*;

public interface IWorldLoadListener extends IWDLMod
{
    void onWorldLoad(final WorldClient p0, final boolean p1);
}
