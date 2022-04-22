package wdl.api;

import net.minecraft.client.multiplayer.*;

public interface IPluginChannelListener extends IWDLMod
{
    void onPluginChannelPacket(final WorldClient p0, final String p1, final byte[] p2);
}
