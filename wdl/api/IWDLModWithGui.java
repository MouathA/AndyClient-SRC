package wdl.api;

import net.minecraft.client.gui.*;

public interface IWDLModWithGui extends IWDLMod
{
    String getButtonName();
    
    void openGui(final GuiScreen p0);
}
