package wdl.api;

import net.minecraft.client.multiplayer.*;

public interface IChatMessageListener extends IWDLMod
{
    void onChat(final WorldClient p0, final String p1);
}
